package cc.cassian.pyrite.functions.fabric;

import cc.cassian.pyrite.blocks.*;
import cc.cassian.pyrite.functions.ModLists;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Objects;

import static cc.cassian.pyrite.Pyrite.LOGGER;
import static cc.cassian.pyrite.Pyrite.modID;
import static cc.cassian.pyrite.functions.ModHelpers.*;
import static cc.cassian.pyrite.functions.ModLists.getDyes;
import static cc.cassian.pyrite.functions.fabric.FabricHelpers.*;

@SuppressWarnings("unused")
public class BlockCreatorImpl {
    // All blocks and their IDs.
    public static final ArrayList<Block> BLOCKS = new ArrayList<>();
    public static final ArrayList<String> BLOCK_IDS = new ArrayList<>();
    // All blocks without block items and their IDs.
    public static final ArrayList<Block> BLOCKS_ITEMLESS = new ArrayList<>();
    public static final ArrayList<String> BLOCK_IDS_ITEMLESS = new ArrayList<>();
    // All items and their IDs.
    public static final ArrayList<Item> ITEMS = new ArrayList<>();
    public static final ArrayList<String> ITEM_IDS = new ArrayList<>();
    // Sublists for Item Groups
    public static final ArrayList<Object> WOOD_BLOCKS = new ArrayList<>();
    public static final ArrayList<Object> RESOURCE_BLOCKS = new ArrayList<>();
    public static final ArrayList<Object> BRICK_BLOCKS = new ArrayList<>();
    public static final ArrayList<Object> REDSTONE_BLOCKS = new ArrayList<>();
    public static final ArrayList<Object> MISC_BLOCKS = new ArrayList<>();

    /**
     * Implements {@link cc.cassian.pyrite.functions.BlockCreator#createWoodType(String, BlockSetType)} on Fabric.
     */
    public static WoodType createWoodType(String blockID, BlockSetType setType) {
        return WoodTypeBuilder.copyOf(WoodType.OAK).register(identifier(blockID), setType);
    }

    /**
     * Implements {@link cc.cassian.pyrite.functions.BlockCreator#platfomRegister(String, String, AbstractBlock.Settings, WoodType, BlockSetType, ParticleEffect, Block)} on Fabric.
     */
    public static void platfomRegister(String blockID, String blockType, AbstractBlock.Settings blockSettings, WoodType woodType, BlockSetType blockSetType, ParticleEffect particle, Block copyBlock) {
        int power;
        if (blockID.contains("redstone")) power = 15;
        else power = 0;
        Block newBlock;
        blockSettings = blockSettings.registryKey(registryKeyBlock(blockID));
        switch (blockType.toLowerCase()) {
            case "block":
                newBlock = new ModBlock(blockSettings, power);
                BLOCKS.add(newBlock);
                BLOCK_IDS.add(blockID);
                if (power == 15)
                    if (blockID.equals("lit_redstone_lamp"))
                        REDSTONE_BLOCKS.addFirst(newBlock);
                    else
                        REDSTONE_BLOCKS.add(newBlock);
                if (Objects.equals(copyBlock, Blocks.OAK_PLANKS))
                    WOOD_BLOCKS.add(newBlock);
                break;
            case "crafting":
                AbstractBlock.Settings craftingSettings;
                boolean burnable;
                // If block is not composed of flammable wood, make it burnable.
                if (!(blockID.contains("crimson") || blockID.contains("warped"))) {
                    craftingSettings = blockSettings.burnable();
                    burnable = true;
                }
                else {
                    craftingSettings = blockSettings;
                    burnable = false;
                }
                // Register Crafting table.
                newBlock = new ModCraftingTable(craftingSettings);
                BLOCKS.add(newBlock);
                BLOCK_IDS.add(blockID);
                WOOD_BLOCKS.add(newBlock);
                // If block is not composed of flammable wood, make it furnace fuel.
                if (burnable)
                    FUEL_BLOCKS.put(newBlock, 300);
                break;
            case "ladder":
                newBlock = new LadderBlock(blockSettings);
                BLOCKS.add(newBlock);
                BLOCK_IDS.add(blockID);
                WOOD_BLOCKS.add(newBlock);
                addTransparentBlock();
                break;
            case "carpet":
                newBlock = new ModCarpet(blockSettings);
                BLOCKS.add(newBlock);
                BLOCK_IDS.add(blockID);
                break;
            case "slab":
                newBlock = new ModSlab(blockSettings, power);
                BLOCKS.add(newBlock);
                BLOCK_IDS.add(blockID);
                if (Objects.equals(copyBlock, Blocks.OAK_SLAB))
                    WOOD_BLOCKS.add(newBlock);
                if (power == 15)
                    REDSTONE_BLOCKS.add(newBlock);
                break;
            case "stairs":
                newBlock = new ModStairs(copyBlock.getDefaultState(), blockSettings);
                BLOCKS.add(newBlock);
                BLOCK_IDS.add(blockID);
                if (Objects.equals(copyBlock, Blocks.OAK_STAIRS))
                    WOOD_BLOCKS.add(newBlock);
                break;
            case "wall":
                newBlock = new ModWall(blockSettings, power);
                BLOCKS.add(newBlock);
                BLOCK_IDS.add(blockID);
                if (power == 15)
                    REDSTONE_BLOCKS.add(newBlock);
                break;
            case "fence":
                newBlock = new FenceBlock(blockSettings);
                BLOCKS.add(newBlock);
                BLOCK_IDS.add(blockID);
                WOOD_BLOCKS.add(newBlock);
                break;
            case "log":
                newBlock = new ModPillar(blockSettings, power);
                BLOCKS.add(newBlock);
                BLOCK_IDS.add(blockID);
                if (blockID.contains("mushroom"))
                    WOOD_BLOCKS.add(newBlock);
                else if (power == 15)
                    REDSTONE_BLOCKS.add(newBlock);
                break;
            case "facing":
                newBlock = new ModFacingBlock(blockSettings, power);
                BLOCKS.add(newBlock);
                BLOCK_IDS.add(blockID);
                if (power == 15)
                    REDSTONE_BLOCKS.add(newBlock);
                break;
            case "bars", "glass_pane":
                newBlock = new ModPane(blockSettings, power);
                BLOCKS.add(newBlock);
                BLOCK_IDS.add(blockID);
                if (power == 15)
                    REDSTONE_BLOCKS.add(newBlock);
                addTransparentBlock();
                break;
            case "tinted_glass_pane":
                newBlock = new ModPane(blockSettings, power);
                BLOCKS.add(newBlock);
                BLOCK_IDS.add(blockID);
                addTranslucentBlock();
                break;
            case "glass":
                newBlock = new ModGlass(blockSettings);
                BLOCKS.add(newBlock);
                BLOCK_IDS.add(blockID);
                addTransparentBlock();
                break;
            case "tinted_glass":
                newBlock = new ModGlass(blockSettings);
                BLOCKS.add(newBlock);
                BLOCK_IDS.add(blockID);
                addTranslucentBlock();
                break;
            case "gravel":
                newBlock = new FallingBlock(blockSettings) {
                    @Override
                    protected MapCodec<? extends FallingBlock> getCodec() {
                        return null;
                    }
                };
                BLOCKS.add(newBlock);
                BLOCK_IDS.add(blockID);
                break;
            case "flower":
                newBlock = new FlowerBlock(StatusEffects.NIGHT_VISION, 5, blockSettings);
                BLOCKS.add(newBlock);
                BLOCK_IDS.add(blockID);
                addTransparentBlock();
                break;
            case "fence_gate", "wall_gate":
                newBlock = new FenceGateBlock(woodType, blockSettings);
                BLOCKS.add(newBlock);
                BLOCK_IDS.add(blockID);
                if (blockID.contains("_stained") || blockID.contains("mushroom"))
                    WOOD_BLOCKS.add(newBlock);
                break;
            case "sign":
                //Sign Blocks
                newBlock = new SignBlock(woodType, blockSettings);
                BLOCKS_ITEMLESS.add(newBlock);
                BLOCK_IDS_ITEMLESS.add(blockID);
                //Wall Sign Blocks
                final WallSignBlock WALL_SIGN = new WallSignBlock(woodType, blockSettings);
                BLOCKS_ITEMLESS.add(WALL_SIGN);
                BLOCK_IDS_ITEMLESS.add(blockID.replace("_sign", "_wall_sign"));
                // Register item for signs.
                final Item SIGN_ITEM = new SignItem(newBlock, WALL_SIGN, newItemSettings(blockID).maxCount(16));
                ITEMS.add(SIGN_ITEM);
                ITEM_IDS.add(blockID);
                WOOD_BLOCKS.add(SIGN_ITEM);
                BlockEntityType.SIGN.addSupportedBlock(newBlock);
                BlockEntityType.SIGN.addSupportedBlock(WALL_SIGN);
                break;
            case "hanging_sign":
                //Sign Blocks
                newBlock = new HangingSignBlock(woodType, blockSettings);
                BLOCKS_ITEMLESS.add(newBlock);
                BLOCK_IDS_ITEMLESS.add(blockID);
                //Wall Sign Blocks
                final WallHangingSignBlock HANGING_WALL_SIGN = new WallHangingSignBlock(woodType, blockSettings);
                BLOCKS_ITEMLESS.add(HANGING_WALL_SIGN);
                BLOCK_IDS_ITEMLESS.add(blockID.replace("_sign", "_wall_sign"));
                // Register item for signs.
                final Item HANGING_SIGN_ITEM = new HangingSignItem(newBlock, HANGING_WALL_SIGN, newItemSettings(blockID).maxCount(16));
                ITEMS.add(HANGING_SIGN_ITEM);
                ITEM_IDS.add(blockID);
                WOOD_BLOCKS.add(HANGING_SIGN_ITEM);
                BlockEntityType.HANGING_SIGN.addSupportedBlock(newBlock);
                BlockEntityType.HANGING_SIGN.addSupportedBlock(HANGING_WALL_SIGN);
                break;
            case "door":
                newBlock = new DoorBlock(blockSetType, blockSettings.nonOpaque());
                BLOCKS.add(newBlock);
                BLOCK_IDS.add(blockID);
                addTransparentBlock();
                if (blockID.contains("_stained") || blockID.contains("mushroom"))
                    WOOD_BLOCKS.add(newBlock);
                break;
            case "trapdoor":
                newBlock = new TrapdoorBlock(blockSetType, blockSettings.nonOpaque());
                BLOCKS.add(newBlock);
                BLOCK_IDS.add(blockID);
                addTransparentBlock();
                if (blockID.contains("_stained") || blockID.contains("mushroom"))
                    WOOD_BLOCKS.add(newBlock);
                break;
            case "button":
                newBlock = new ModWoodenButton(blockSettings, blockSetType);
                BLOCKS.add(newBlock);
                BLOCK_IDS.add(blockID);
                if (blockID.contains("_stained") || blockID.contains("mushroom"))
                    WOOD_BLOCKS.add(newBlock);
                break;
            case "pressure_plate":
                newBlock = new ModPressurePlate(blockSettings, blockSetType);
                BLOCKS.add(newBlock);
                BLOCK_IDS.add(blockID);
                if (blockID.contains("_stained") || blockID.contains("mushroom"))
                    WOOD_BLOCKS.add(newBlock);
                break;
            case "torch":
                if (particle == null)
                    newBlock = new ModTorch(blockSettings.nonOpaque(), ParticleTypes.FLAME);
                else
                    newBlock = new ModTorch(blockSettings.nonOpaque(), particle);
                BLOCKS.add(newBlock);
                BLOCK_IDS.add(blockID);
                addTransparentBlock();
                break;
            case "torch_lever":
                newBlock = new TorchLever(blockSettings.nonOpaque(), particle);
                BLOCKS.add(newBlock);
                BLOCK_IDS.add(blockID);
                REDSTONE_BLOCKS.add(newBlock);
                addTransparentBlock();
                break;
            case "concrete_powder":
                newBlock = new ConcretePowderBlock(getLastBlock(), blockSettings);
                BLOCKS.add(newBlock);
                BLOCK_IDS.add(BLOCK_IDS.indexOf(blockID.replace("_powder", "")), blockID);
                break;
            case "switchable_glass":
                newBlock = new SwitchableGlass(blockSettings);
                BLOCKS.add(newBlock);
                BLOCK_IDS.add(blockID);
                REDSTONE_BLOCKS.add(newBlock);
                addTranslucentBlock();
                break;
            default:
                LOGGER.error("{}created as a generic block, block provided type: {}", blockID, blockType);
                newBlock = new Block(blockSettings);
                BLOCKS.add(newBlock);
                BLOCK_IDS.add(blockID);
                break;
        }
        for (Block block : ModLists.getVanillaResourceBlocks()) {
            if (blockID.contains(Registries.BLOCK.getId(block).getPath().replace("_block", "")) && !inGroup(newBlock))
                RESOURCE_BLOCKS.add(newBlock);
            }
        if (blockID.contains("brick") && !inGroup(newBlock))
            BRICK_BLOCKS.add(newBlock);
        if (blockID.contains("grass")) {
            addGrassBlock();
        }
    }

    /**
     * Implements {@link cc.cassian.pyrite.functions.BlockCreator#registerPyriteItem(String)} on Fabric.
     * This registers a basic item with no additional settings - primarily used for Dye.
     */
    public static void registerPyriteItem(String itemID) {
        ITEMS.add(new Item(newItemSettings(itemID)));
        ITEM_IDS.add(itemID);
    }

    public static boolean inGroup(Object obj) {
        return WOOD_BLOCKS.contains(obj) || BRICK_BLOCKS.contains(obj) || RESOURCE_BLOCKS.contains(obj) || REDSTONE_BLOCKS.contains(obj) || MISC_BLOCKS.contains(obj);
    }

    public static void addItemGroup(String id, String icon, ArrayList<Object> blocks) {
        ItemGroup group = FabricItemGroup.builder()
                .icon(() -> new ItemStack(BLOCKS.get(BLOCK_IDS.indexOf(icon))))
                .displayName(Text.translatable("itemGroup.pyrite." + id))
                .entries((context, entries) -> {
                    for (Object obj : blocks) {
                        if (obj instanceof Block block)
                            entries.add(block);
                        else if (obj instanceof Item item)
                            entries.add(item);
                    }
                })
                .build();
        Registry.register(Registries.ITEM_GROUP, Identifier.of(modID, id), group);
    }

    public static Item.Settings newItemSettings(String id) {
        return new Item.Settings().registryKey(registryKeyItem(id));
    }

    public static Item.Settings newBlockItemSettings(String id) {
        return newItemSettings(id).useBlockPrefixedTranslationKey();
    }

    public static void register() {
        //Register blocks and block items.
        for (int x = 0; x < BLOCK_IDS.size(); x++) {
            final var block = BLOCKS.get(x);
            final var blockID = BLOCK_IDS.get(x);
            Registry.register(Registries.BLOCK, identifier(blockID), block);
            Registry.register(Registries.ITEM, identifier(blockID), new BlockItem(block, newBlockItemSettings(blockID)));
            if (!inGroup(block))
                MISC_BLOCKS.add(block);
        }
        //Registers blocks without block items.
        for (int x = 0; x < BLOCK_IDS_ITEMLESS.size(); x++) {
            final var block = BLOCKS_ITEMLESS.get(x);
            final var blockID = BLOCK_IDS_ITEMLESS.get(x);
            Registry.register(Registries.BLOCK, identifier(blockID), block);
        }
        //Registers items.
        for (int x = 0; x < ITEM_IDS.size(); x++) {
            Registry.register(Registries.ITEM, identifier(ITEM_IDS.get(x)), ITEMS.get(x));
            if (!inGroup(ITEMS.get(x)))
                MISC_BLOCKS.add(ITEMS.get(x));
        }
        // Add vanilla Concrete to Pyrite item group.
        for (int dyeIndex = 0; dyeIndex < 15; dyeIndex++) {
            final var concrete = getDyes()[dyeIndex]+"_concrete";
            final var concreteStairs = concrete + "_stairs";
            final var item = BLOCKS.get(BLOCK_IDS.indexOf(concreteStairs));
            MISC_BLOCKS.add(
                    MISC_BLOCKS.indexOf(item),
                    Registries.BLOCK.get(Identifier.of("minecraft",concrete)));
        }
        // Register item groups.
        addItemGroup("wood_group", "dragon_stained_crafting_table", WOOD_BLOCKS);
        addItemGroup("resource_group", "cut_emerald", RESOURCE_BLOCKS);
        addItemGroup("brick_group", "cobblestone_bricks", BRICK_BLOCKS);
        addItemGroup("redstone_group", "chiseled_redstone_block", REDSTONE_BLOCKS);
        addItemGroup("pyrite_group", "glowing_obsidian", MISC_BLOCKS);
    }
}
