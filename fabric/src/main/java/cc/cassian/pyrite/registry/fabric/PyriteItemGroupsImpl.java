package cc.cassian.pyrite.registry.fabric;

import cc.cassian.pyrite.functions.ModLists;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.function.Supplier;

import static cc.cassian.pyrite.Pyrite.MOD_ID;
import static cc.cassian.pyrite.functions.ModLists.VANILLA_DYES;
import static cc.cassian.pyrite.registry.fabric.BlockCreatorImpl.BLOCKS;
import static cc.cassian.pyrite.registry.PyriteItemGroups.*;

public class PyriteItemGroupsImpl {

    public static void addMapToItemGroup(FabricItemGroupEntries group, LinkedHashMap<Block, Supplier<Block>> map) {
        for (Map.Entry<Block, Supplier<Block>> entry : map.entrySet()) {
            Block anchor = entry.getKey();
            Block value = entry.getValue().get();
            if (anchor != null)
                group.addAfter(anchor, value);
            else
                group.add(value);
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

    @SuppressWarnings("unused")
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
            addMapToItemGroup(itemGroup, COLORED_BLOCKS);
            itemGroup.addAfter(Blocks.PINK_CARPET, getCollectionList(CARPET));
            itemGroup.addAfter(Blocks.PINK_SHULKER_BOX, getCollectionList(DYED_BRICKS));
            itemGroup.addBefore(Blocks.SHULKER_BOX, getCollectionList(LAMPS));
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register((itemGroup) -> {
            itemGroup.addAfter(Items.WITHER_ROSE, getCollectionList(FLOWERS));
            itemGroup.addAfter(Items.DIRT_PATH, getCollectionList(DIRT_PATH));
            itemGroup.addAfter(Items.GRASS_BLOCK, getCollectionList(NOSTALGIA_GRASS));
            itemGroup.addAfter(Items.GRASS_BLOCK, getCollectionList(GRASS));
            itemGroup.addAfter(Items.PODZOL, getCollectionList(PODZOL));
            itemGroup.addAfter(Items.MYCELIUM, getCollectionList(MYCELIUM));
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register((itemGroup) -> {
            itemGroup.addAfter(Items.WARPED_HANGING_SIGN, getCollectionList(SIGNS));
            itemGroup.addAfter(Items.CRAFTING_TABLE, getCollectionList(CRAFTING_TABLES));
            itemGroup.addAfter(Items.TORCH, getCollectionList(TORCH));
            addMapToItemGroup(itemGroup, FUNCTIONAL);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register((itemGroup) -> {
            itemGroup.addAfter(Items.CAULDRON, getCollectionList(REDSTONE_BLOCKS));
            itemGroup.addAfter(Items.REDSTONE_BLOCK, getCollectionList(REDSTONE_RESOURCE_BLOCKS));
            itemGroup.addAfter(Items.LEVER, getCollectionList(TORCH_LEVER));

        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((itemGroup) ->
                itemGroup.addAfter(Items.PINK_DYE, getCollectionList(DYES)));
        
        // Add Pyrite Concrete to vanilla item group.
        for (int dyeIndex = 0; dyeIndex < ModLists.DYES.length; dyeIndex++) {
            String dye = ModLists.DYES[dyeIndex];
            String namespace;
            if (!Arrays.asList(VANILLA_DYES).contains(dye))
                namespace = MOD_ID;
            else {
                namespace = "minecraft";
            }
            final var concrete = dye+"_concrete";
            final var stairs = BLOCKS.get(concrete + "_stairs");
            final var slab = BLOCKS.get(concrete + "_slab");
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register((itemGroup) ->
                    itemGroup.addAfter(Registries.BLOCK.get(Identifier.of(namespace, concrete)), stairs, slab));
        }
    }
}
