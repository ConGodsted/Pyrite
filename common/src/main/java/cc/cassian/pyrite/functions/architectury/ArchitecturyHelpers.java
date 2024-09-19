package cc.cassian.pyrite.functions.architectury;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import java.util.ArrayList;
import java.util.HashMap;


public class ArchitecturyHelpers {
    public static ArrayList<RegistrySupplier<Block>> transparentBlocks = new ArrayList<>();
    public static ArrayList<RegistrySupplier<Block>> translucentBlocks = new ArrayList<>();
    public static ArrayList<RegistrySupplier<Block>> grassBlocks = new ArrayList<>();
    public static HashMap<RegistrySupplier<Block>, Integer> fuel = new HashMap<>();

    public static void addTransparentBlock(RegistrySupplier<Block> newBlock) {
        transparentBlocks.add(newBlock);
    }
    public static void addTranslucentBlock(RegistrySupplier<Block> newBlock) {
        translucentBlocks.add(newBlock);
    }
    public static void addGrassBlock(RegistrySupplier<Block> newBlock) {
        grassBlocks.add(newBlock);
    }

    public static Item.Settings newItem(ItemGroup group) {
        return new Item.Settings().group(group);
    }

}