package cc.cassian.pyrite.functions.fabric;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import static cc.cassian.pyrite.Pyrite.MOD_ID;
import static cc.cassian.pyrite.functions.ModLists.getDyes;
import static cc.cassian.pyrite.functions.fabric.BlockCreatorImpl.BLOCKS;

public class PyriteItemGroups {
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
    public static final ArrayList<Block> TUFF = new ArrayList<>();
    public static final ArrayList<Block> DEEPSLATE = new ArrayList<>();
    public static final ArrayList<Block> SANDSTONE = new ArrayList<>();
    public static final ArrayList<Block> STAINED_GLASS = new ArrayList<>();
    public static final ArrayList<Block> STAINED_GLASS_PANES = new ArrayList<>();
    public static final ArrayList<Block> FRAMED_GLASS = new ArrayList<>();
    public static final ArrayList<Block> FRAMED_GLASS_PANES = new ArrayList<>();
    public static final ArrayList<Block> CONCRETE = new ArrayList<>();
    public static final ArrayList<Block> CONCRETE_POWDER = new ArrayList<>();
    public static final ArrayList<Block> WOOL = new ArrayList<>();
    public static final ArrayList<Block> CARPET = new ArrayList<>();
    public static final ArrayList<Block> TERRACOTTA = new ArrayList<>();
    public static final ArrayList<Block> TERRACOTTA_BRICKS = new ArrayList<>();
    public static final ArrayList<Block> TORCH = new ArrayList<>();
    public static final LinkedHashMap<Block, Block> FUNCTIONAL = new LinkedHashMap<>();
    public static final LinkedHashMap<Block, Block> BUILDING_BLOCKS = new LinkedHashMap<>();

    public static void addMapToItemGroup(FabricItemGroupEntries group, LinkedHashMap<Block, Block> map) {
        for (Map.Entry<Block, Block> entry : map.entrySet()) {
            Block key = entry.getKey();
            Block value = entry.getValue();
            group.addAfter(key, value);
        }
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

    public static void match(Block newBlock, Block copyBlock, String group) {
        if (group.contains("terracotta_brick"))
            group = "terracotta_bricks";
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
            case "mossy_tuff_brick":
                TUFF.add(newBlock);
                break;
            case "mossy_deepslate_brick":
                DEEPSLATE.add(newBlock);
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
            case "wool":
                WOOL.add(newBlock);
                break;
            case "concrete":
                CONCRETE.add(newBlock);
                break;
            case "carpet":
                CARPET.add(newBlock);
                break;
            case "concrete_powder":
                CONCRETE_POWDER.add(newBlock);
                break;
            case "terracotta":
                TERRACOTTA.add(newBlock);
                break;
            case "terracotta_bricks":
                TERRACOTTA_BRICKS.add(newBlock);
                break;
            case "torch":
                TORCH.add(newBlock);
                break;
            case "functional":
                FUNCTIONAL.put(copyBlock, newBlock);
                break;
            case "building_blocks":
                BUILDING_BLOCKS.put(copyBlock, newBlock);
                break;
            default:
                System.out.println(group);
        }
    }

    public static void modifyEntries() {
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
            itemGroup.addAfter(Items.SMOOTH_STONE_SLAB, getCollectionList(SMOOTH_STONE));
            itemGroup.addAfter(Items.TUFF_BRICK_SLAB, getCollectionList(TUFF));
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
            itemGroup.addAfter(Blocks.PINK_CONCRETE, getCollectionList(CONCRETE));
            itemGroup.addAfter(Blocks.PINK_CONCRETE_POWDER, getCollectionList(CONCRETE_POWDER));
            itemGroup.addAfter(Blocks.PINK_TERRACOTTA, getCollectionList(TERRACOTTA));
            itemGroup.addBefore(Blocks.WHITE_CONCRETE, getCollectionList(TERRACOTTA_BRICKS));
            itemGroup.addAfter(Blocks.PINK_WOOL, getCollectionList(WOOL));
            itemGroup.addAfter(Blocks.PINK_CARPET, getCollectionList(CARPET));
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register((itemGroup) ->
                itemGroup.addAfter(Items.WITHER_ROSE, getCollectionList(FLOWERS)));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register((itemGroup) -> {
            itemGroup.addAfter(Items.WARPED_HANGING_SIGN, getCollectionList(SIGNS));
            itemGroup.addAfter(Items.CRAFTING_TABLE, getCollectionList(CRAFTING_TABLES));
            itemGroup.addAfter(Items.TORCH, getCollectionList(TORCH));
            addMapToItemGroup(itemGroup, FUNCTIONAL);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register((itemGroup) ->
                itemGroup.addAfter(Items.CAULDRON, getCollectionList(REDSTONE_BLOCKS)));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((itemGroup) ->
                itemGroup.addAfter(Items.PINK_DYE, getCollectionList(DYES)));
        
        // Add Pyrite Concrete to vanilla item group.
        for (int dyeIndex = 0; dyeIndex < 15; dyeIndex++) {
            final var concrete = getDyes()[dyeIndex]+"_concrete";
            final var stairs = BLOCKS.get(concrete + "_stairs");
            final var slab = BLOCKS.get(concrete + "_slab");
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register((itemGroup) ->
                    itemGroup.addAfter(Registries.BLOCK.get(Identifier.ofVanilla(concrete)), stairs, slab));
        }
    }
}
