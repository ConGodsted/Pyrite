package cc.cassian.pyrite.functions.fabric;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.HashMap;

import static cc.cassian.pyrite.functions.fabric.BlockCreatorImpl.BLOCKS;

public class FabricHelpers {

    public static final ArrayList<Block> TRANSPARENT_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> TRANSLUCENT_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> GRASS_BLOCKS = new ArrayList<>();
    public static final HashMap<Block, Integer> FUEL_BLOCKS = new HashMap<>();

    public static void registerFuelBlocks() {
        FUEL_BLOCKS.forEach(FuelRegistry.INSTANCE::add);
    }

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
