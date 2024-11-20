package cc.cassian.pyrite.functions.neoforge;

import net.minecraft.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.HashMap;


public class NeoHelpers {

    public static final ArrayList<DeferredHolder<Block, ? extends Block>> GRASS_BLOCKS = new ArrayList<>();
    public static final HashMap<DeferredHolder<Block, ? extends Block>, Integer> FUEL_BLOCKS = new HashMap<>();
    public static final HashMap<DeferredHolder<Block, ? extends Block>, Integer> FLAMMABLE_BLOCKS = new HashMap<>();

    public static void addGrassBlock(DeferredHolder<Block, ? extends Block> newBlock) {
        GRASS_BLOCKS.add(newBlock);
    }

}