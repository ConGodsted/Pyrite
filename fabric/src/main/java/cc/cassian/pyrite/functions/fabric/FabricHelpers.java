package cc.cassian.pyrite.functions.fabric;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static cc.cassian.pyrite.functions.fabric.BlockCreatorImpl.BLOCKS;
import static cc.cassian.pyrite.functions.fabric.BlockCreatorImpl.ITEMS;

public class FabricHelpers {

    public static final ArrayList<Block> TRANSPARENT_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> TRANSLUCENT_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> GRASS_BLOCKS = new ArrayList<>();
    public static final HashMap<Block, Integer> FUEL_BLOCKS = new HashMap<>();

    public static void registerFuelBlocks() {
        FUEL_BLOCKS.forEach(FuelRegistry.INSTANCE::add);
    }

    //Add items to the Pyrite Item Group
    public static final ItemGroup PYRITE_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(BLOCKS.get(3)))
            .displayName(Text.translatable("itemGroup.pyrite.group"))
            .entries((context, entries) -> {
                for (Block block : BLOCKS) {
                    entries.add(block);
                }
                for (Item item : ITEMS) {
                    entries.add(item);
                }
            })
            .build();

    public static void addGrassBlock() {
        GRASS_BLOCKS.add(getLastBlock());
    }
    public static void addTransparentBlock() {
        TRANSPARENT_BLOCKS.add(getLastBlock());
    }
    public static void addTranslucentBlock() {
        TRANSLUCENT_BLOCKS.add(getLastBlock());
    }
    public static Block getLastBlock() {
        return BLOCKS.getLast();
    }


}
