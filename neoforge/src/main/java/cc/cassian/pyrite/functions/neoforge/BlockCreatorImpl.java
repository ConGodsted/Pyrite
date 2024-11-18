package cc.cassian.pyrite.functions.neoforge;

import cc.cassian.pyrite.blocks.*;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.function.Supplier;

import static cc.cassian.pyrite.Pyrite.LOGGER;
import static cc.cassian.pyrite.Pyrite.modID;
import static cc.cassian.pyrite.functions.ModHelpers.identifier;
import static cc.cassian.pyrite.functions.neoforge.NeoHelpers.*;

@SuppressWarnings("unused")
public class BlockCreatorImpl {
    public static DeferredHolder<Block, ?> creativeTabIcon;
    //Deferred registry entries
    public static final DeferredRegister<Block> pyriteBlocks = DeferredRegister.create(RegistryKeys.BLOCK, modID);
    public static final DeferredRegister<Item> pyriteItems = DeferredRegister.create(RegistryKeys.ITEM, modID);
    public static final ArrayList<DeferredHolder<Item, ?>> ALL_ITEMS = new ArrayList<>();
    public static final ArrayList<DeferredHolder<Block, ?>> pyriteSigns = new ArrayList<>();

    public static WoodType createWoodType(String blockID, BlockSetType setType) {
        WoodType woodType = new WoodType(identifier(blockID).toString(), setType);
        WoodType.register(woodType);
        return woodType;
    }

    @SuppressWarnings("unused")
    public static void platfomRegister(String blockID, String blockType, AbstractBlock.Settings blockSettings, WoodType woodType, BlockSetType blockSetType, ParticleEffect particle, Block copyBlock) {
        int power;
        if (blockID.contains("redstone")) power = 15;
        else power = 0;
        DeferredHolder<Block, ?> newBlock;
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
                DeferredHolder<Block, WallSignBlock> wallSign = pyriteBlocks.register(blockID.replace("_sign", "_wall_sign"), () -> new WallSignBlock(woodType, blockSettings));
                pyriteSigns.add(newBlock);
                pyriteSigns.add(wallSign);
                addSignItem(newBlock, wallSign);
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
                newBlock = pyriteBlocks.register(blockID, () -> new ConcretePowderBlock(pyriteBlocks.getRegistry().get().get(Identifier.of(modID, blockID.replace("_powder", ""))), blockSettings));
                break;
            case "switchable_glass":
                newBlock = pyriteBlocks.register(blockID, () -> new SwitchableGlass(blockSettings));
                break;
            default:
                LOGGER.error("{}created as a generic block, block provided{}", blockID, blockType);
                newBlock = pyriteBlocks.register(blockID, () -> new Block(blockSettings));
                break;
        }
        if (!blockType.equals("sign"))
            addBlockItem(newBlock);
        if (blockID.contains("grass")) addGrassBlock(newBlock);
        else if (blockID.equals("cobblestone_bricks")) creativeTabIcon = newBlock;
    }

    public static void addBlockItem(DeferredHolder<Block, ? extends Block> newBlock) {
        ALL_ITEMS.add(pyriteItems.register(newBlock.getId().getPath(), () -> new BlockItem(newBlock.get(), new Item.Settings())));
    }

    public static void addSignItem(DeferredHolder<Block, ? extends Block> newBlock, DeferredHolder<Block, ? extends Block> wallSign) {
        ALL_ITEMS.add(pyriteItems.register(newBlock.getId().getPath(), () -> new SignItem(new Item.Settings().maxCount(16), newBlock.get(), wallSign.get())));
    }

    public static final DeferredRegister<ItemGroup> pyriteTabs =
            DeferredRegister.create(RegistryKeys.ITEM_GROUP, modID);

    public static final Supplier<ItemGroup> PYRITE_GROUP = pyriteTabs.register(modID, () -> ItemGroup.builder()
            //Set the title of the tab.
            .displayName(Text.translatable("itemGroup." + modID + ".group"))
            //Set the icon of the tab.
            .icon(() -> new ItemStack(creativeTabIcon.get()))
            //Add your items to the tab.
            .entries((params, output) -> {
                for (DeferredHolder<Item, ?> item : ALL_ITEMS) {
                    output.add(item.get());
                }
            })
            .build()
    );


    //Create and add Pyrite items.
    @SuppressWarnings("unused")
    public static void registerPyriteItem(String itemID) {
        ALL_ITEMS.add(pyriteItems.register(itemID, () -> (new Item(new Item.Settings()))));
    }

    @SuppressWarnings("unused")
    public static void register() {
    }

    @SubscribeEvent
    public static void signBlockEntityType(BlockEntityTypeAddBlocksEvent event) {
        for (DeferredHolder<Block, ?> sign : pyriteSigns) {
            event.modify(BlockEntityType.SIGN, sign.get());
        }
    }
}
