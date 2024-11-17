package cc.cassian.pyrite.functions.fabric;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.HashMap;

import static cc.cassian.pyrite.functions.fabric.BlockCreatorImpl.pyriteBlocks;
import static cc.cassian.pyrite.functions.fabric.BlockCreatorImpl.pyriteItems;

public class FabricHelpers {
    public static void registerFuelBlocks() {
        fuel.forEach(FuelRegistry.INSTANCE::add);
    }

    //Add items to the Pyrite Item Group
    public static final ItemGroup PYRITE_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(pyriteBlocks.get(3)))
            .displayName(Text.translatable("itemGroup.pyrite.group"))
            .entries((context, entries) -> {
                for (Block block : pyriteBlocks) {
                    entries.add(block);
                }
                for (Item item : pyriteItems) {
                    entries.add(item);
                }
            })
            .build();

    public static ArrayList<Block> transparentBlocks = new ArrayList<>();
    public static ArrayList<Block> translucentBlocks = new ArrayList<>();
    public static ArrayList<Block> grassBlocks = new ArrayList<>();
    public static HashMap<Block, Integer> fuel = new HashMap<>();

    public static void addGrassBlock() {
        grassBlocks.add(getLastBlock());
    }
    public static void addTransparentBlock() {
        transparentBlocks.add(getLastBlock());
    }
    public static void addTranslucentBlock() {
        translucentBlocks.add(getLastBlock());
    }
    public static Block getLastBlock() {
        return pyriteBlocks.getLast();
    }


}
