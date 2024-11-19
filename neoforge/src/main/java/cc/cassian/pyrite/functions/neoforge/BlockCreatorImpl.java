package cc.cassian.pyrite.functions.neoforge;

import cc.cassian.pyrite.blocks.*;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.neoforged.bus.api.IEventBus;
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
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(RegistryKeys.BLOCK, modID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(RegistryKeys.ITEM, modID);
    public static final DeferredRegister<ItemGroup> TABS = DeferredRegister.create(RegistryKeys.ITEM_GROUP, modID);
    public static final ArrayList<DeferredHolder<Item, ?>> ALL_ITEMS = new ArrayList<>();
    public static final ArrayList<DeferredHolder<Block, ?>> SIGN_BLOCKS = new ArrayList<>();
    public static final ArrayList<DeferredHolder<Block, ?>> HANGING_SIGN_BLOCKS = new ArrayList<>();

    /**
     * Implements {@link cc.cassian.pyrite.functions.BlockCreator#createWoodType(String, BlockSetType)} on NeoForge.
     */
    public static WoodType createWoodType(String blockID, BlockSetType setType) {
        WoodType woodType = new WoodType(identifier(blockID).toString(), setType);
        WoodType.register(woodType);
        return woodType;
    }

    /**
     * Implements {@link cc.cassian.pyrite.functions.BlockCreator#platfomRegister(String, String, AbstractBlock.Settings, WoodType, BlockSetType, ParticleEffect, Block)} on NeoForge.
     */
    public static void platfomRegister(String blockID, String blockType, AbstractBlock.Settings blockSettings, WoodType woodType, BlockSetType blockSetType, ParticleEffect particle, Block copyBlock) {
        int power;
        if (blockID.contains("redstone")) power = 15;
        else power = 0;
        DeferredHolder<Block, ?> newBlock;
        switch (blockType.toLowerCase()) {
            case "block":
                newBlock = BLOCKS.register(blockID, () -> new ModBlock(blockSettings, power));
                break;
            case "crafting":
                AbstractBlock.Settings craftingSettings;
                if (!(blockID.contains("crimson") || blockID.contains("warped"))) {
                    craftingSettings = blockSettings.burnable();
                }
                else craftingSettings = blockSettings;
                newBlock = BLOCKS.register(blockID, () -> new ModCraftingTable(craftingSettings));
                if (!(blockID.contains("crimson") || blockID.contains("warped"))) {
                    FUEL_BLOCKS.put(newBlock, 300);
                }
                break;
            case "ladder":
                newBlock = BLOCKS.register(blockID, () -> new LadderBlock(blockSettings));
                break;
            case "carpet":
                newBlock = BLOCKS.register(blockID, () -> new ModCarpet(blockSettings));
                break;
            case "slab":
                newBlock = BLOCKS.register(blockID, () -> new ModSlab(blockSettings, power));
                break;
            case "stairs":
                newBlock = BLOCKS.register(blockID, () -> new ModStairs(copyBlock.getDefaultState(), blockSettings));
                break;
            case "wall":
                newBlock = BLOCKS.register(blockID, () -> new ModWall(blockSettings, power));
                break;
            case "fence":
                newBlock = BLOCKS.register(blockID, () -> new FenceBlock(blockSettings));
                break;
            case "log":
                newBlock = BLOCKS.register(blockID, () -> new ModPillar(blockSettings, power));
                break;
            case "facing":
                newBlock = BLOCKS.register(blockID, () -> new ModFacingBlock(blockSettings, power));
                break;
            case "bars", "glass_pane", "tinted_glass_pane":
                newBlock = BLOCKS.register(blockID, () -> new ModPane(blockSettings, power));
                break;
            case "glass", "tinted_glass":
                newBlock = BLOCKS.register(blockID, () -> new ModGlass(blockSettings));
                break;
            case "gravel":
                newBlock = BLOCKS.register(blockID, () -> new FallingBlock(blockSettings) {
                    @Override
                    protected MapCodec<? extends FallingBlock> getCodec() {
                        return null;
                    }
                });
                break;
            case "flower":
                newBlock = BLOCKS.register(blockID, () -> new FlowerBlock(StatusEffects.NIGHT_VISION, 5, blockSettings));
                break;
            case "fence_gate", "wall_gate":
                newBlock = BLOCKS.register(blockID, () -> new FenceGateBlock(woodType, blockSettings));
                break;
            case "sign":
                newBlock = BLOCKS.register(blockID, () -> new SignBlock(woodType, blockSettings));
                DeferredHolder<Block, WallSignBlock> wallSign = BLOCKS.register(blockID.replace("_sign", "_wall_sign"), () -> new WallSignBlock(woodType, blockSettings));
                SIGN_BLOCKS.add(newBlock);
                SIGN_BLOCKS.add(wallSign);
                addSignItem(newBlock, wallSign);
                break;
            case "hanging_sign":
                newBlock = BLOCKS.register(blockID, () -> new HangingSignBlock(woodType, blockSettings));
                DeferredHolder<Block, WallHangingSignBlock> hangingWallSign = BLOCKS.register(blockID.replace("_sign", "_wall_sign"), () -> new WallHangingSignBlock(woodType, blockSettings));
                HANGING_SIGN_BLOCKS.add(newBlock);
                HANGING_SIGN_BLOCKS.add(hangingWallSign);
                addHangingSignItem(newBlock, hangingWallSign);
                break;
            case "door":
                newBlock = BLOCKS.register(blockID, () -> new DoorBlock(blockSetType, blockSettings.nonOpaque()));
                break;
            case "trapdoor":
                newBlock = BLOCKS.register(blockID, () -> new TrapdoorBlock(blockSetType, blockSettings.nonOpaque()));
                break;
            case "button":
                newBlock = BLOCKS.register(blockID, () -> new ModWoodenButton(blockSettings, blockSetType));
                break;
            case "pressure_plate":
                newBlock = BLOCKS.register(blockID, () -> new ModPressurePlate(blockSettings, blockSetType));
                break;
            case "torch":
                if (particle == null)
                    newBlock = BLOCKS.register(blockID, () -> new ModTorch(blockSettings.nonOpaque(), ParticleTypes.FLAME));
                else
                    newBlock = BLOCKS.register(blockID, () -> new ModTorch(blockSettings.nonOpaque(), particle));
                break;
            case "torch_lever":
                newBlock = BLOCKS.register(blockID, () -> new TorchLever(blockSettings.nonOpaque(), particle));
                break;
            case "concrete_powder":
                newBlock = BLOCKS.register(blockID, () -> new ConcretePowderBlock(BLOCKS.getRegistry().get().get(Identifier.of(modID, blockID.replace("_powder", ""))), blockSettings));
                break;
            case "switchable_glass":
                newBlock = BLOCKS.register(blockID, () -> new SwitchableGlass(blockSettings));
                break;
            default:
                LOGGER.error("{}created as a generic block, block provided{}", blockID, blockType);
                newBlock = BLOCKS.register(blockID, () -> new Block(blockSettings));
                break;
        }
        if (!blockType.equals("sign"))
            addBlockItem(newBlock);
        if (blockID.contains("grass")) addGrassBlock(newBlock);
        else if (blockID.equals("cobblestone_bricks")) creativeTabIcon = newBlock;
    }

    public static void addBlockItem(DeferredHolder<Block, ? extends Block> newBlock) {
        ALL_ITEMS.add(ITEMS.register(newBlock.getId().getPath(), () -> new BlockItem(newBlock.get(), new Item.Settings())));
    }

    public static void addSignItem(DeferredHolder<Block, ? extends Block> newBlock, DeferredHolder<Block, ? extends Block> wallSign) {
        ALL_ITEMS.add(ITEMS.register(newBlock.getId().getPath(), () -> new SignItem(new Item.Settings().maxCount(16), newBlock.get(), wallSign.get())));
    }

    public static void addHangingSignItem(DeferredHolder<Block, ? extends Block> newBlock, DeferredHolder<Block, ? extends Block> wallSign) {
        ALL_ITEMS.add(ITEMS.register(newBlock.getId().getPath(), () -> new HangingSignItem(newBlock.get(), wallSign.get(), new Item.Settings().maxCount(16))));
    }

    public static final Supplier<ItemGroup> PYRITE_GROUP = TABS.register(modID, () -> ItemGroup.builder()
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

    /**
     * Implements {@link cc.cassian.pyrite.functions.BlockCreator#registerPyriteItem(String)} on NeoForge.
     * This registers a basic item with no additional settings - primarily used for Dye.
     */
    public static void registerPyriteItem(String itemID) {
        ALL_ITEMS.add(ITEMS.register(itemID, () -> (new Item(new Item.Settings()))));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        TABS.register(eventBus);
    }

    // Adds Pyrite's signs to the list of blockstates that the Sign block entities support.
    @SubscribeEvent
    public static void addSignsToSupports(BlockEntityTypeAddBlocksEvent event) {
        for (DeferredHolder<Block, ?> sign : SIGN_BLOCKS) {
            event.modify(BlockEntityType.SIGN, sign.get());
        }
        for (DeferredHolder<Block, ?> sign : HANGING_SIGN_BLOCKS) {
            event.modify(BlockEntityType.HANGING_SIGN, sign.get());
        }
    }
}
