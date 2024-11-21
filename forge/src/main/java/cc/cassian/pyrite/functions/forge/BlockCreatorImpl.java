package cc.cassian.pyrite.functions.forge;

import cc.cassian.pyrite.blocks.*;
import cc.cassian.pyrite.functions.ModLists;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Supplier;

import static cc.cassian.pyrite.Pyrite.LOGGER;
import static cc.cassian.pyrite.Pyrite.modID;
import static cc.cassian.pyrite.functions.ModHelpers.identifier;
import static cc.cassian.pyrite.functions.forge.ForgeHelpers.*;

@SuppressWarnings("unused")
public class BlockCreatorImpl {
    // Creative tab icon holders
    public static RegistryObject<Block> WOOD_ICON;
    public static RegistryObject<Block> RESOURCE_ICON;
    public static RegistryObject<Block> REDSTONE_ICON;
    public static RegistryObject<Block> BRICK_ICON;
    public static RegistryObject<Block> MISC_ICON;
    //Deferred registry entries
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(RegistryKeys.BLOCK, modID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(RegistryKeys.ITEM, modID);
    public static final DeferredRegister<ItemGroup> TABS = DeferredRegister.create(RegistryKeys.ITEM_GROUP, modID);
    public static final ArrayList<RegistryObject<Item>> ALL_ITEMS = new ArrayList<>();
    public static final ArrayList<RegistryObject<Block>> SIGN_BLOCKS = new ArrayList<>();
    public static final ArrayList<RegistryObject<Block>> HANGING_SIGN_BLOCKS = new ArrayList<>();
    // Sublists for Item Groups
    public static final ArrayList<RegistryObject<?>> WOOD_BLOCKS = new ArrayList<>();
    public static final ArrayList<RegistryObject<?>> RESOURCE_BLOCKS = new ArrayList<>();
    public static final ArrayList<RegistryObject<?>> BRICK_BLOCKS = new ArrayList<>();
    public static final ArrayList<RegistryObject<?>> REDSTONE_BLOCKS = new ArrayList<>();
    public static final ArrayList<RegistryObject<?>> MISC_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> MISC_BLOCKS_UNSORTED = new ArrayList<>();


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
        RegistryObject<Block> newBlock;
        switch (blockType.toLowerCase()) {
            case "block":
                newBlock = BLOCKS.register(blockID, () -> new ModBlock(blockSettings, power));
                if (power == 15)
                    if (blockID.equals("lit_redstone_lamp"))
                        REDSTONE_BLOCKS.add(0, newBlock);
                    else
                        REDSTONE_BLOCKS.add(newBlock);
                if (Objects.equals(copyBlock, Blocks.OAK_PLANKS))
                    WOOD_BLOCKS.add(newBlock);
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
                WOOD_BLOCKS.add(newBlock);
                break;
            case "ladder":
                newBlock = BLOCKS.register(blockID, () -> new LadderBlock(blockSettings));
                WOOD_BLOCKS.add(newBlock);
                break;
            case "carpet":
                newBlock = BLOCKS.register(blockID, () -> new ModCarpet(blockSettings));
                break;
            case "slab":
                newBlock = BLOCKS.register(blockID, () -> new ModSlab(blockSettings, power));
                if (Objects.equals(copyBlock, Blocks.OAK_SLAB))
                    WOOD_BLOCKS.add(newBlock);
                if (power == 15)
                    REDSTONE_BLOCKS.add(newBlock);
                break;
            case "stairs":
                newBlock = BLOCKS.register(blockID, () -> new ModStairs(copyBlock.getDefaultState(), blockSettings));
                if (Objects.equals(copyBlock, Blocks.OAK_STAIRS))
                    WOOD_BLOCKS.add(newBlock);
                break;
            case "wall":
                newBlock = BLOCKS.register(blockID, () -> new ModWall(blockSettings, power));
                if (power == 15)
                    REDSTONE_BLOCKS.add(newBlock);
                break;
            case "fence":
                newBlock = BLOCKS.register(blockID, () -> new FenceBlock(blockSettings));
                if (power == 15)
                    REDSTONE_BLOCKS.add(newBlock);
                break;
            case "log":
                newBlock = BLOCKS.register(blockID, () -> new ModPillar(blockSettings, power));
                if (blockID.contains("mushroom"))
                    WOOD_BLOCKS.add(newBlock);
                else if (power == 15)
                    REDSTONE_BLOCKS.add(newBlock);
                break;
            case "facing":
                newBlock = BLOCKS.register(blockID, () -> new ModFacingBlock(blockSettings, power));
                if (power == 15)
                    REDSTONE_BLOCKS.add(newBlock);
                break;
            case "bars", "glass_pane", "tinted_glass_pane":
                newBlock = BLOCKS.register(blockID, () -> new ModPane(blockSettings, power));
                if (power == 15)
                    REDSTONE_BLOCKS.add(newBlock);
                break;
            case "glass", "tinted_glass":
                newBlock = BLOCKS.register(blockID, () -> new ModGlass(blockSettings));
                break;
            case "gravel":
                newBlock = BLOCKS.register(blockID, () -> new FallingBlock(blockSettings));
                break;
            case "flower":
                newBlock = BLOCKS.register(blockID, () -> new FlowerBlock(StatusEffects.NIGHT_VISION, 5, blockSettings));
                break;
            case "fence_gate", "wall_gate":
                newBlock = BLOCKS.register(blockID, () -> new FenceGateBlock(blockSettings, woodType));
                if (blockID.contains("_stained") || blockID.contains("mushroom"))
                    WOOD_BLOCKS.add(newBlock);
                break;
            case "sign":
                newBlock = BLOCKS.register(blockID, () -> new SignBlock(blockSettings, woodType));
                RegistryObject<Block> wallSign = BLOCKS.register(blockID.replace("_sign", "_wall_sign"), () -> new WallSignBlock(blockSettings, woodType));
                SIGN_BLOCKS.add(newBlock);
                SIGN_BLOCKS.add(wallSign);
                addSignItem(newBlock, wallSign);
                break;
            case "hanging_sign":
                newBlock = BLOCKS.register(blockID, () -> new HangingSignBlock(blockSettings, woodType));
                RegistryObject<Block> hangingWallSign = BLOCKS.register(blockID.replace("_sign", "_wall_sign"), () -> new WallHangingSignBlock(blockSettings, woodType));
                HANGING_SIGN_BLOCKS.add(newBlock);
                HANGING_SIGN_BLOCKS.add(hangingWallSign);
                addHangingSignItem(newBlock, hangingWallSign);
                break;
            case "door":
                newBlock = BLOCKS.register(blockID, () -> new DoorBlock(blockSettings.nonOpaque(), blockSetType));
                if (blockID.contains("_stained") || blockID.contains("mushroom"))
                    WOOD_BLOCKS.add(newBlock);
                break;
            case "trapdoor":
                newBlock = BLOCKS.register(blockID, () -> new TrapdoorBlock(blockSettings.nonOpaque(), blockSetType));
                if (blockID.contains("_stained") || blockID.contains("mushroom"))
                    WOOD_BLOCKS.add(newBlock);
                break;
            case "button":
                newBlock = BLOCKS.register(blockID, () -> new ModWoodenButton(blockSettings, blockSetType));
                if (blockID.contains("_stained") || blockID.contains("mushroom"))
                    WOOD_BLOCKS.add(newBlock);
                break;
            case "pressure_plate":
                newBlock = BLOCKS.register(blockID, () -> new ModPressurePlate(blockSettings, blockSetType));
                if (blockID.contains("_stained") || blockID.contains("mushroom"))
                    WOOD_BLOCKS.add(newBlock);
                break;
            case "torch":
                if (particle == null)
                    newBlock = BLOCKS.register(blockID, () -> new ModTorch(blockSettings.nonOpaque(), ParticleTypes.FLAME));
                else
                    newBlock = BLOCKS.register(blockID, () -> new ModTorch(blockSettings.nonOpaque(), particle));
                break;
            case "torch_lever":
                newBlock = BLOCKS.register(blockID, () -> new TorchLever(blockSettings.nonOpaque(), particle));
                REDSTONE_BLOCKS.add(newBlock);
                break;
            case "concrete_powder":
                newBlock = BLOCKS.register(blockID, () -> new ConcretePowderBlock(Blocks.WHITE_CONCRETE, blockSettings));
                break;
            case "switchable_glass":
                newBlock = BLOCKS.register(blockID, () -> new SwitchableGlass(blockSettings));
                REDSTONE_BLOCKS.add(newBlock);
                break;
            default:
                LOGGER.error("{}created as a generic block, block provided{}", blockID, blockType);
                newBlock = BLOCKS.register(blockID, () -> new Block(blockSettings));
                break;
        }
        for (Block block : ModLists.getVanillaResourceBlocks()) {
            if (blockID.contains(Registries.BLOCK.getId(block).getPath().replace("_block", "")) && !inGroup(newBlock))
                RESOURCE_BLOCKS.add(newBlock);
        }
        if (blockID.contains("brick") && !inGroup(newBlock))
            BRICK_BLOCKS.add(newBlock);
        if (blockID.equals("dragon_stained_crafting_table")) WOOD_ICON = newBlock;
        else if (blockID.equals("cut_emerald")) RESOURCE_ICON = newBlock;
        else if (blockID.equals("cobblestone_bricks")) BRICK_ICON = newBlock;
        else if (blockID.equals("chiseled_redstone_block")) REDSTONE_ICON = newBlock;
        else if (blockID.equals("glowing_obsidian")) MISC_ICON = newBlock;
        else if (blockID.contains("grass")) addGrassBlock(newBlock);
        if (!blockType.equals("sign") && !blockType.equals("hanging_sign")) {
            addBlockItem(newBlock);
            if (!inGroup(newBlock)) {
                MISC_BLOCKS.add(newBlock);
            }
        }

    }

    public static void addBlockItem(RegistryObject<Block> newBlock) {
        ALL_ITEMS.add(ITEMS.register(newBlock.getId().getPath(), () -> new BlockItem(newBlock.get(), new Item.Settings())));
    }

    public static void addSignItem(RegistryObject<Block> newBlock, RegistryObject<Block> wallSign) {
        RegistryObject<Item> newItem = ITEMS.register(newBlock.getId().getPath(), () -> new SignItem(new Item.Settings().maxCount(16), newBlock.get(), wallSign.get()));
        ALL_ITEMS.add(newItem);
        WOOD_BLOCKS.add(newItem);
    }

    public static void addHangingSignItem(RegistryObject<Block> newBlock, RegistryObject<Block> wallSign) {
        RegistryObject<Item> newItem = ITEMS.register(newBlock.getId().getPath(), () -> new HangingSignItem(newBlock.get(), wallSign.get(), new Item.Settings().maxCount(16)));
        ALL_ITEMS.add(newItem);
        WOOD_BLOCKS.add(newItem);
    }

    public static boolean inGroup(Object obj) {
        return WOOD_BLOCKS.contains(obj) || BRICK_BLOCKS.contains(obj) || RESOURCE_BLOCKS.contains(obj) || REDSTONE_BLOCKS.contains(obj) || MISC_BLOCKS.contains(obj);
    }

    public static void addItemGroup(String id, RegistryObject<Block> icon, ArrayList<RegistryObject<?>> blocks) {
        Supplier<ItemGroup> PYRITE_GROUP = TABS.register(id, () -> ItemGroup.builder()
                //Set the title of the tab.
                .displayName(Text.translatable("itemGroup." + modID + "." + id))
                //Set the icon of the tab.
                .icon(() -> new ItemStack(icon.get()))
                //Add your items to the tab.
                .entries((params, entries) -> {
                    for (RegistryObject<?> obj : blocks) {
                        if (obj.get() instanceof Block block)
                            entries.add(block);
                        else if (obj.get() instanceof Item item)
                            entries.add(item);
                    }
                })
                .build()
        );
    }

    /**
     * Implements {@link cc.cassian.pyrite.functions.BlockCreator#registerPyriteItem(String)} on NeoForge.
     * This registers a basic item with no additional settings - primarily used for Dye.
     */
    public static void registerPyriteItem(String itemID) {
        ALL_ITEMS.add(ITEMS.register(itemID, () -> (new Item(new Item.Settings()))));
    }

    public static void register(IEventBus eventBus) {
        // TODO - Add vanilla Concrete to Pyrite item group.
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        TABS.register(eventBus);
        // Add item groups
        addItemGroup("wood_group", WOOD_ICON, WOOD_BLOCKS);
        addItemGroup("resource_group", RESOURCE_ICON, RESOURCE_BLOCKS);
        addItemGroup("brick_group", BRICK_ICON, BRICK_BLOCKS);
        addItemGroup("redstone_group", REDSTONE_ICON, REDSTONE_BLOCKS);
        addItemGroup("pyrite_group", MISC_ICON, MISC_BLOCKS);
    }
}