package cc.cassian.pyrite.functions.neoforge;

import net.minecraft.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.HashMap;


public class NeoHelpers {
    public static ArrayList<DeferredHolder<Block, ? extends Block>> transparentBlocks = new ArrayList<>();
    public static ArrayList<DeferredHolder<Block, ? extends Block>> translucentBlocks = new ArrayList<>();
    public static ArrayList<DeferredHolder<Block, ? extends Block>> grassBlocks = new ArrayList<>();
    public static HashMap<DeferredHolder<Block, ? extends Block>, Integer> fuel = new HashMap<>();

    public static void addTransparentBlock(DeferredHolder<Block, ? extends Block> newBlock) {
        transparentBlocks.add(newBlock);
    }
    public static void addTranslucentBlock(DeferredHolder<Block, ? extends Block> newBlock) {
        translucentBlocks.add(newBlock);
    }
    public static void addGrassBlock(DeferredHolder<Block, ? extends Block> newBlock) {
        grassBlocks.add(newBlock);
    }

}