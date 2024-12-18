package cc.cassian.pyrite.functions.fabric;

import cc.cassian.pyrite.blocks.*;
import cc.cassian.pyrite.functions.BlockCreator;
import cc.cassian.pyrite.functions.ModHelpers;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.*;

import static cc.cassian.pyrite.Pyrite.LOGGER;
import static cc.cassian.pyrite.Pyrite.MOD_ID;
import static cc.cassian.pyrite.functions.ModHelpers.*;
import static cc.cassian.pyrite.functions.ModLists.getDyes;
import static cc.cassian.pyrite.functions.fabric.FabricHelpers.*;

@SuppressWarnings("unused")
public class BlockCreatorImpl {
    // All blocks and their IDs.
    public static final LinkedHashMap<String, Block> BLOCKS = new LinkedHashMap<>();
    // All blocks without block items and their IDs.
    public static final LinkedHashMap<String, Block> ITEMLESS_BLOCKS = new LinkedHashMap<>();
    // All items and their IDs.
    public static final LinkedHashMap<String, Item> ITEMS = new LinkedHashMap<>();
    public static final ArrayList<Object> REDSTONE_BLOCKS = new ArrayList<>();
    public static final ArrayList<Item> SIGNS = new ArrayList<>();
    public static final ArrayList<Block> CRAFTING_TABLES = new ArrayList<>();
    public static final ArrayList<Block> FLOWERS = new ArrayList<>();
    public static final ArrayList<Item> DYES = new ArrayList<>();
    public static final ArrayList<Block> IRON_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> GOLD_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> EMERALD_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> LAPIS_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> REDSTONE_RESOURCE_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> DIAMOND_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> NETHERITE_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> QUARTZ_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> AMETHYST_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> COPPER_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> EXPOSED_COPPER_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> WEATHERED_COPPER_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> OXIDIZED_COPPER_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> COLOURED_NETHER_BRICKS = new ArrayList<>();
    public static final ArrayList<Block> COBBLESTONE = new ArrayList<>();
    public static final ArrayList<Block> SMOOTH_STONE = new ArrayList<>();
    public static final ArrayList<Block> ANDESITE = new ArrayList<>();
    public static final ArrayList<Block> GRANITE = new ArrayList<>();
    public static final ArrayList<Block> DIORITE = new ArrayList<>();
    public static final ArrayList<Block> CALCITE = new ArrayList<>();
    public static final ArrayList<Block> SANDSTONE = new ArrayList<>();
    public static final ArrayList<Block> STAINED_GLASS = new ArrayList<>();
    public static final ArrayList<Block> STAINED_GLASS_PANES = new ArrayList<>();
    public static final ArrayList<Block> FRAMED_GLASS = new ArrayList<>();
    public static final ArrayList<Block> FRAMED_GLASS_PANES = new ArrayList<>();
    public static final LinkedHashMap<Block, Block> FUNCTIONAL = new LinkedHashMap<>();
    public static final LinkedHashMap<Block, Block> BUILDING_BLOCKS = new LinkedHashMap<>();


    /**
     * Implements {@link BlockCreator#createWoodType(String, BlockSetType)} on Fabric.
     */
    public static WoodType createWoodType(String blockID, BlockSetType setType) {
        return WoodTypeBuilder.copyOf(WoodType.OAK).register(identifier(blockID), setType);
    }

    /**
     * Implements {@link BlockCreator#platformRegister(String, String, AbstractBlock.Settings, WoodType, BlockSetType, ParticleEffect, Block, String)} on Fabric.
     */
    public static void platformRegister(String blockID, String blockType, AbstractBlock.Settings blockSettings, WoodType woodType, BlockSetType blockSetType, ParticleEffect particle, Block copyBlock, String group) {
        int power = power(blockID);
        Block newBlock;
        switch (blockType.toLowerCase()) {
            case "block":
                newBlock = new ModBlock(blockSettings, power);
                if (power == 15)
                    if (!blockID.equals("lit_redstone_lamp"))
                        REDSTONE_BLOCKS.add(newBlock);
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
                // If block is not composed of flammable wood, make it furnace fuel.
                if (burnable)
                    FUEL_BLOCKS.put(newBlock, 300);
                break;
            case "ladder":
                newBlock = new LadderBlock(blockSettings);
                addTransparentBlock(newBlock);
                break;
            case "carpet":
                newBlock = new ModCarpet(blockSettings);
                break;
            case "slab":
                newBlock = new ModSlab(blockSettings, power);
                if (power == 15)
                    REDSTONE_BLOCKS.add(newBlock);
                break;
            case "stairs":
                newBlock = new ModStairs(copyBlock.getDefaultState(), blockSettings);
                break;
            case "wall":
                newBlock = new ModWall(blockSettings, power);
                if (power == 15)
                    REDSTONE_BLOCKS.add(newBlock);
                break;
            case "fence":
                newBlock = new FenceBlock(blockSettings);
                break;
            case "log":
                newBlock = new ModPillar(blockSettings, power);
                if (power == 15)
                    REDSTONE_BLOCKS.add(newBlock);
                break;
            case "wood":
                newBlock = new ModWood(blockSettings);
                break;
            case "facing":
                newBlock = new ModFacingBlock(blockSettings, power);
                if (power == 15)
                    REDSTONE_BLOCKS.add(newBlock);
                break;
            case "bars", "glass_pane":
                newBlock = new ModPane(blockSettings, power);
                if (power == 15)
                    REDSTONE_BLOCKS.add(newBlock);
                addTransparentBlock(newBlock);
                break;
            case "tinted_glass_pane":
                newBlock = new ModPane(blockSettings, power);
                addTranslucentBlock(newBlock);
                break;
            case "stained_framed_glass_pane":
                newBlock = new StainedGlassPaneBlock(getDyeColorFromFramedId(blockID), blockSettings);
                addTranslucentBlock(newBlock);
                break;
            case "glass":
                newBlock = new ModGlass(blockSettings);
                addTransparentBlock(newBlock);
                break;
            case "tinted_glass":
                newBlock = new ModGlass(blockSettings);
                addTranslucentBlock(newBlock);
                break;
            case "stained_framed_glass":
                newBlock = new StainedFramedGlass(ModHelpers.getDyeColorFromFramedId(blockID), blockSettings);
                addTranslucentBlock(newBlock);
                break;
            case "gravel":
                newBlock = new GravelBlock(blockSettings);
                break;
            case "flower":
                newBlock = new FlowerBlock(StatusEffects.NIGHT_VISION, 5, blockSettings);
                addTransparentBlock(newBlock);
                FLOWERS.add(newBlock);
                break;
            case "fence_gate":
                newBlock = new FenceGateBlock(woodType, blockSettings);
                break;
            case "wall_gate":
                newBlock = new WallGateBlock(blockSettings);
                break;
            case "sign":
                //Sign Blocks
                newBlock = new SignBlock(woodType, blockSettings);
                ITEMLESS_BLOCKS.put(blockID, newBlock);
                //Wall Sign Blocks
                final WallSignBlock WALL_SIGN = new WallSignBlock(woodType, blockSettings);
                ITEMLESS_BLOCKS.put(blockID.replace("_sign", "_wall_sign"), WALL_SIGN);
                // Register item for signs.
                final Item SIGN_ITEM = new SignItem(new Item.Settings().maxCount(16), newBlock, WALL_SIGN);
                ITEMS.put(blockID, SIGN_ITEM);
                SIGNS.add(SIGNS.size(), SIGN_ITEM);
                BlockEntityType.SIGN.addSupportedBlock(newBlock);
                BlockEntityType.SIGN.addSupportedBlock(WALL_SIGN);
                break;
            case "hanging_sign":
                //Sign Blocks
                newBlock = new HangingSignBlock(woodType, blockSettings);
                ITEMLESS_BLOCKS.put(blockID, newBlock);
                //Wall Sign Blocks
                final WallHangingSignBlock HANGING_WALL_SIGN = new WallHangingSignBlock(woodType, blockSettings);
                ITEMLESS_BLOCKS.put(blockID.replace("_sign", "_wall_sign"), HANGING_WALL_SIGN);
                // Register item for signs.
                final Item HANGING_SIGN_ITEM = new HangingSignItem(newBlock, HANGING_WALL_SIGN, new Item.Settings().maxCount(16));
                ITEMS.put(blockID, HANGING_SIGN_ITEM);
                SIGNS.add(HANGING_SIGN_ITEM);
                BlockEntityType.HANGING_SIGN.addSupportedBlock(newBlock);
                BlockEntityType.HANGING_SIGN.addSupportedBlock(HANGING_WALL_SIGN);
                break;
            case "door":
                newBlock = new DoorBlock(blockSetType, blockSettings.nonOpaque());
                addTransparentBlock(newBlock);
                break;
            case "trapdoor":
                newBlock = new TrapdoorBlock(blockSetType, blockSettings.nonOpaque());
                addTransparentBlock(newBlock);
                break;
            case "button":
                newBlock = new ModWoodenButton(blockSettings, blockSetType);
                break;
            case "pressure_plate":
                newBlock = new ModPressurePlate(blockSettings, blockSetType);
                break;
            case "torch":
                var torchParticle = particle;
                if (particle == null)
                    torchParticle = ParticleTypes.FLAME;
                newBlock = new ModTorch(blockSettings.nonOpaque(), torchParticle);
                addTransparentBlock(newBlock);
                break;
            case "torch_lever":
                newBlock = new TorchLever(blockSettings.nonOpaque(), particle);
                REDSTONE_BLOCKS.add(newBlock);
                addTransparentBlock(newBlock);
                break;
            case "concrete_powder":
                newBlock = new ConcretePowderBlock(getLastBlock(), blockSettings);
                break;
            case "switchable_glass":
                newBlock = new SwitchableGlass(blockSettings);
                REDSTONE_BLOCKS.add(newBlock);
                addTranslucentBlock(newBlock);
                break;
            default:
                LOGGER.error("{}created as a generic block, block provided type: {}", blockID, blockType);
                newBlock = new Block(blockSettings);
                break;
        }
        if (!blockType.contains("sign")) {
            BLOCKS.put(blockID, newBlock);
        }
        if (blockID.contains("grass")) {
            addGrassBlock();
        }
        switch (group) {
            case "iron":
                IRON_BLOCKS.add(newBlock);
                break;
            case "gold":
                GOLD_BLOCKS.add(newBlock);
                break;
            case "emerald":
                EMERALD_BLOCKS.add(newBlock);
                break;
            case "lapis":
                LAPIS_BLOCKS.add(newBlock);
                break;
            case "diamond":
                DIAMOND_BLOCKS.add(newBlock);
                break;
            case "redstone":
                REDSTONE_RESOURCE_BLOCKS.add(newBlock);
                break;
            case "netherite":
                NETHERITE_BLOCKS.add(newBlock);
                break;
            case "quartz":
                QUARTZ_BLOCKS.add(newBlock);
                break;
            case "amethyst":
                AMETHYST_BLOCKS.add(newBlock);
                break;
            case "copper":
                COPPER_BLOCKS.add(newBlock);
                break;
            case "exposed_copper":
                EXPOSED_COPPER_BLOCKS.add(newBlock);
                break;
            case "weathered_copper":
                WEATHERED_COPPER_BLOCKS.add(newBlock);
                break;
            case "oxidized_copper":
                OXIDIZED_COPPER_BLOCKS.add(newBlock);
                break;
            case "charred_nether_brick", "blue_nether_brick":
                COLOURED_NETHER_BRICKS.add(newBlock);
                break;
            case "cobblestone_brick", "mossy_cobblestone_brick":
                COBBLESTONE.add(newBlock);
                break;
            case "smooth_stone_brick", "mossy_smooth_stone_brick":
                SMOOTH_STONE.add(newBlock);
                break;
            case "granite_brick", "mossy_granite_brick":
                GRANITE.add(newBlock);
                break;
            case "diorite_brick", "mossy_diorite_brick":
                DIORITE.add(newBlock);
                break;
            case "andesite_brick", "mossy_andesite_brick":
                ANDESITE.add(newBlock);
                break;
            case "calcite_brick", "mossy_calcite_brick":
                CALCITE.add(newBlock);
                break;
            case "sandstone_brick":
                SANDSTONE.add(newBlock);
                break;
            case "crafting_table":
                CRAFTING_TABLES.add(newBlock);
                break;
            case "stained_glass":
                STAINED_GLASS.add(newBlock);
                break;
            case "stained_glass_pane":
                STAINED_GLASS_PANES.add(newBlock);
                break;
            case "framed_glass":
                FRAMED_GLASS.add(newBlock);
                break;
            case "framed_glass_pane":
                FRAMED_GLASS_PANES.add(newBlock);
                break;
            case "functional":
                FUNCTIONAL.put(copyBlock, newBlock);
                break;
            case "building_blocks":
                BUILDING_BLOCKS.put(copyBlock, newBlock);
                break;
            default:
//                System.out.println(group);
        }
    }

    /**
     * Implements {@link BlockCreator#registerPyriteItem(String)} on Fabric.
     * This registers a basic item with no additional settings - primarily used for Dye.
     */
    public static void registerPyriteItem(String itemID) {
        var item = new Item(new Item.Settings());
        ITEMS.put(itemID, item);
        DYES.add(item);
    }

    public static void addItemGroup(String id, String icon, LinkedHashMap<String, Block> blocks) {
        ItemGroup group = FabricItemGroup.builder()
                .icon(() -> new ItemStack(BLOCKS.get(icon)))
                .displayName(Text.translatable("itemGroup.pyrite." + id))
                .entries((context, entries) -> {
                    for (Block block : blocks.values()) {
                        entries.add(block);
                    }
                })
                .build();
        Registry.register(Registries.ITEM_GROUP, Identifier.of(MOD_ID, id), group);
    }

    public static BlockItem addBlockItem(String blockID, Block block) {
        Item.Settings settings = new Item.Settings();
        if (blockID.contains("netherite"))
            settings = settings.fireproof();
        return new BlockItem(block, settings);
    }

    public static Collection<ItemStack> getCollectionList(ArrayList<?> items) {
        ArrayList<ItemStack> stacks = new ArrayList<>();
        for (Object object : items) {
            if (object instanceof Item item)
                stacks.add(item.getDefaultStack());
            else if (object instanceof Block block)
                stacks.add(block.asItem().getDefaultStack());
        }
        return stacks;
    }

    public static void register() {
        //Register blocks and block items.
        for (Map.Entry<String, Block> entry : BLOCKS.entrySet()) {
            final Block block = entry.getValue();
            final String blockID = entry.getKey();
            Registry.register(Registries.BLOCK, identifier(blockID), block);
            Registry.register(Registries.ITEM, identifier(blockID), addBlockItem(blockID, block));
        }
        //Registers blocks without block items.
        for (Map.Entry<String, Block> entry : ITEMLESS_BLOCKS.entrySet()) {
            final Block block = entry.getValue();
            final String blockID = entry.getKey();
            Registry.register(Registries.BLOCK, identifier(blockID), block);
        }
        //Registers items.
        for (Map.Entry<String, Item> entry : ITEMS.entrySet()) {
            final Item item = entry.getValue();
            final String itemID = entry.getKey();
            Registry.register(Registries.ITEM, identifier(itemID), item);
        }
        // Add Pyrite Concrete to vanilla item group.
        for (int dyeIndex = 0; dyeIndex < 15; dyeIndex++) {
            final var concrete = getDyes()[dyeIndex]+"_concrete";
            final var stairs = BLOCKS.get(concrete + "_stairs");
            final var slab = BLOCKS.get(concrete + "_slab");
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register((itemGroup) ->
                    itemGroup.addAfter(Registries.BLOCK.get(Identifier.ofVanilla(concrete)), stairs, slab));
        }
        // Register item group.
        addItemGroup("pyrite_group", "glowing_obsidian", BLOCKS);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register((itemGroup) -> {
            itemGroup.addAfter(Items.IRON_BLOCK, getCollectionList(IRON_BLOCKS));
            itemGroup.addAfter(Items.GOLD_BLOCK, getCollectionList(GOLD_BLOCKS));
            itemGroup.addAfter(Items.EMERALD_BLOCK, getCollectionList(EMERALD_BLOCKS));
            itemGroup.addAfter(Items.LAPIS_BLOCK, getCollectionList(LAPIS_BLOCKS));
            itemGroup.addAfter(Items.REDSTONE_BLOCK, getCollectionList(REDSTONE_RESOURCE_BLOCKS));
            itemGroup.addAfter(Items.DIAMOND_BLOCK, getCollectionList(DIAMOND_BLOCKS));
            itemGroup.addAfter(Items.NETHERITE_BLOCK, getCollectionList(NETHERITE_BLOCKS));
            itemGroup.addAfter(Items.QUARTZ_BLOCK, getCollectionList(QUARTZ_BLOCKS));
            itemGroup.addAfter(Items.AMETHYST_BLOCK, getCollectionList(AMETHYST_BLOCKS));
            itemGroup.addAfter(Items.CUT_COPPER_SLAB, getCollectionList(COPPER_BLOCKS));
            itemGroup.addAfter(Items.EXPOSED_CUT_COPPER_SLAB, getCollectionList(EXPOSED_COPPER_BLOCKS));
            itemGroup.addAfter(Items.WEATHERED_CUT_COPPER_SLAB, getCollectionList(WEATHERED_COPPER_BLOCKS));
            itemGroup.addAfter(Items.OXIDIZED_CUT_COPPER_SLAB, getCollectionList(OXIDIZED_COPPER_BLOCKS));
            itemGroup.addAfter(Items.RED_NETHER_BRICK_WALL, getCollectionList(COLOURED_NETHER_BRICKS));
            itemGroup.addAfter(Items.COBBLESTONE_WALL, getCollectionList(COBBLESTONE));
            itemGroup.addAfter(Items.GRANITE_SLAB, getCollectionList(GRANITE));
            itemGroup.addAfter(Items.ANDESITE_SLAB, getCollectionList(ANDESITE));
            itemGroup.addAfter(Items.POLISHED_DIORITE_SLAB, getCollectionList(DIORITE));
            itemGroup.addBefore(Items.TUFF, Items.CALCITE);
            itemGroup.addAfter(Items.CALCITE, getCollectionList(CALCITE));
            itemGroup.addAfter(Items.CUT_SANDSTONE_SLAB, getCollectionList(SANDSTONE));
            addMapToItemGroup(itemGroup, BUILDING_BLOCKS);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register((itemGroup) -> {
            itemGroup.addAfter(Blocks.PINK_STAINED_GLASS, getCollectionList(STAINED_GLASS));
            itemGroup.addAfter(Blocks.PINK_STAINED_GLASS_PANE, getCollectionList(STAINED_GLASS_PANES));
            itemGroup.addBefore(Blocks.SHULKER_BOX, getCollectionList(FRAMED_GLASS));
            itemGroup.addBefore(Blocks.SHULKER_BOX, getCollectionList(FRAMED_GLASS_PANES));
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register((itemGroup) ->
                itemGroup.addAfter(Items.WITHER_ROSE, getCollectionList(FLOWERS)));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register((itemGroup) -> {
            itemGroup.addAfter(Items.WARPED_HANGING_SIGN, getCollectionList(SIGNS));
            itemGroup.addAfter(Items.CRAFTING_TABLE, getCollectionList(CRAFTING_TABLES));
            addMapToItemGroup(itemGroup, FUNCTIONAL);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register((itemGroup) ->
                itemGroup.addAfter(Items.CAULDRON, getCollectionList(REDSTONE_BLOCKS)));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((itemGroup) ->
                itemGroup.addAfter(Items.PINK_DYE, getCollectionList(DYES)));
    }

    public static void addMapToItemGroup(RegistryKey<ItemGroup> group, LinkedHashMap<Block, Block> map) {
        for (Map.Entry<Block, Block> entry : map.entrySet()) {
            Block key = entry.getKey();
            Block value = entry.getValue();
            ItemGroupEvents.modifyEntriesEvent(group).register((itemGroup) -> itemGroup.addAfter(key, value));
        }
    }

    public static void addMapToItemGroup(FabricItemGroupEntries group, LinkedHashMap<Block, Block> map) {
        for (Map.Entry<Block, Block> entry : map.entrySet()) {
            Block key = entry.getKey();
            Block value = entry.getValue();
            group.addAfter(key, value);
        }
    }
}
