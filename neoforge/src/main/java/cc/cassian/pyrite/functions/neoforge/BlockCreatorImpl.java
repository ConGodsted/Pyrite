package cc.cassian.pyrite.functions.neoforge;

import cc.cassian.pyrite.blocks.*;
import com.mojang.serialization.MapCodec;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static cc.cassian.pyrite.Pyrite.LOGGER;
import static cc.cassian.pyrite.Pyrite.modID;
import static cc.cassian.pyrite.functions.architectury.ArchitecturyHelpers.*;

@SuppressWarnings("unused")
public class BlockCreatorImpl {
    public static RegistrySupplier<Block> creativeTabIcon;
    //Deferred registry entries
    public static final DeferredRegister<Block> pyriteBlocks = DeferredRegister.create(modID, RegistryKeys.BLOCK);
    public static final DeferredRegister<Item> pyriteItems = DeferredRegister.create(modID, RegistryKeys.ITEM);

    @SuppressWarnings("unused")
    public static void platfomRegister(String blockID, String blockType, AbstractBlock.Settings blockSettings, WoodType woodType, BlockSetType blockSetType, ParticleEffect particle, Block copyBlock) {
        int power;
        if (blockID.contains("redstone")) power = 15;
        else power = 0;
        RegistrySupplier<Block> newBlock;
        switch (blockType.toLowerCase()) {
            case "block":
                newBlock = pyriteBlocks.register(blockID, () -> new ModBlock(blockSettings, power));
                break;
            case "crafting":
                newBlock = pyriteBlocks.register(blockID, () -> new ModCraftingTable(blockSettings));
                if (!(blockID.contains("crimson") || blockID.contains("warped"))) {
                    fuel.put(newBlock, 300);
                }
                break;
            case "ladder":
                newBlock = pyriteBlocks.register(blockID, () -> new LadderBlock(blockSettings));
                addTransparentBlock(newBlock);
                break;
            case "carpet":
                newBlock = pyriteBlocks.register(blockID, () -> new ModCarpet(blockSettings));
                break;
            case "slab":
                newBlock = pyriteBlocks.register(blockID, () -> new ModSlab(blockSettings, power));
                break;
            case "stairs":
                newBlock = pyriteBlocks.register(blockID, () -> new ModStairs(copyBlock.getDefaultState(), blockSettings));
                break;
            case "wall":
                newBlock = pyriteBlocks.register(blockID, () -> new ModWall(blockSettings, power));
                break;
            case "fence":
                newBlock = pyriteBlocks.register(blockID, () -> new FenceBlock(blockSettings));
                break;
            case "log":
                newBlock = pyriteBlocks.register(blockID, () -> new ModPillar(blockSettings, power));
                break;
            case "facing":
                newBlock = pyriteBlocks.register(blockID, () -> new ModFacingBlock(blockSettings, power));
                break;
            case "bars", "glass_pane":
                newBlock = pyriteBlocks.register(blockID, () -> new ModPane(blockSettings, power));
                addTransparentBlock(newBlock);
                break;
            case "tinted_glass_pane":
                newBlock = pyriteBlocks.register(blockID, () -> new ModPane(blockSettings, power));
                addTranslucentBlock(newBlock);
                break;
            case "glass":
                newBlock = pyriteBlocks.register(blockID, () -> new ModGlass(blockSettings));
                addTranslucentBlock(newBlock);
                break;
            case "tinted_glass":
                newBlock = pyriteBlocks.register(blockID, () -> new ModGlass(blockSettings));
                break;
            case "gravel":
                newBlock = pyriteBlocks.register(blockID, () -> new FallingBlock(blockSettings) {
                    @Override
                    protected MapCodec<? extends FallingBlock> getCodec() {
                        return null;
                    }
                });
                break;
            case "flower":
                newBlock = pyriteBlocks.register(blockID, () -> new FlowerBlock(StatusEffects.NIGHT_VISION, 5, blockSettings));
                addTransparentBlock(newBlock);
                break;
            case "fence_gate", "wall_gate":
                newBlock = pyriteBlocks.register(blockID, () -> new FenceGateBlock(woodType, blockSettings));
                break;
            case "sign":
                newBlock = pyriteBlocks.register(blockID, () -> new SignBlock(woodType, blockSettings));
                break;
            case "door":
                newBlock = pyriteBlocks.register(blockID, () -> new DoorBlock(blockSetType, blockSettings.nonOpaque()));
                addTransparentBlock(newBlock);
                break;
            case "trapdoor":
                newBlock = pyriteBlocks.register(blockID, () -> new TrapdoorBlock(blockSetType, blockSettings.nonOpaque()));
                addTransparentBlock(newBlock);
                break;
            case "button":
                newBlock = pyriteBlocks.register(blockID, () -> new ModWoodenButton(blockSettings, blockSetType));
                break;
            case "pressure_plate":
                newBlock = pyriteBlocks.register(blockID, () -> new ModPressurePlate(blockSettings, blockSetType));
                break;
            case "torch":
                if (particle == null)
                    newBlock = pyriteBlocks.register(blockID, () -> new ModTorch(blockSettings.nonOpaque(), ParticleTypes.FLAME));
                else
                    newBlock = pyriteBlocks.register(blockID, () -> new ModTorch(blockSettings.nonOpaque(), particle));
                addTransparentBlock(newBlock);
                break;
            case "torch_lever":
                newBlock = pyriteBlocks.register(blockID, () -> new TorchLever(blockSettings.nonOpaque(), particle));
                addTransparentBlock(newBlock);
                break;
            case "concrete_powder":
                newBlock = pyriteBlocks.register(blockID, () -> new ConcretePowderBlock(pyriteBlocks.getRegistrar().get(Identifier.of(modID, blockID.replace("_powder", ""))), blockSettings));
                break;
            case "switchable_glass":
                newBlock = pyriteBlocks.register(blockID, () -> new SwitchableGlass(blockSettings));
                break;
            default:
                LOGGER.error("{}created as a generic block, block provided{}", blockID, blockType);
                newBlock = pyriteBlocks.register(blockID, () -> new Block(blockSettings));
                break;
        }
        addBlockItem(newBlock);
        if (blockID.contains("grass")) addGrassBlock(newBlock);
        else if (blockID.equals("cobblestone_bricks")) creativeTabIcon = newBlock;
    }

    public static void addBlockItem(RegistrySupplier<Block> newBlock) {
        pyriteItems.register(newBlock.getId(), () -> new BlockItem(newBlock.get(), newItem(PYRITE_GROUP)));
    }
    public static final DeferredRegister<ItemGroup> pyriteTabs =
            DeferredRegister.create(modID, RegistryKeys.ITEM_GROUP);

    public static final RegistrySupplier<ItemGroup> PYRITE_GROUP = pyriteTabs.register(
            "pyrite", // Tab ID
            () -> CreativeTabRegistry.create(
                    Text.translatable("itemGroup.pyrite.group"), // Tab Name
                    () -> new ItemStack(creativeTabIcon.get()) // Icon
            )
    );


    //Create and add Pyrite items.
    @SuppressWarnings("unused")
    public static void registerPyriteItem(String itemID) {
        pyriteItems.register(itemID, () -> (new Item(newItem(PYRITE_GROUP))));
    }

    @SuppressWarnings("unused")
    public static void register() {
        pyriteBlocks.register();
        pyriteItems.register();
        pyriteTabs.register();
    }
}
