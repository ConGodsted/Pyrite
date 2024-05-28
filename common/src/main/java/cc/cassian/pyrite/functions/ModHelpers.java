package cc.cassian.pyrite.functions;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.DyeColor;

import java.util.ArrayList;
import java.util.function.ToIntFunction;

import static cc.cassian.pyrite.Pyrite.ITEMS;

public class ModHelpers {
    public static ArrayList<Block> transparentBlocks = new ArrayList<>();
    public static ArrayList<Block> grassBlocks = new ArrayList<>();

    public static void addTransparentBlock(RegistrySupplier<Block> newBlock) {
//        transparentBlocks.add(newBlock.get());
    }

    public static void addGrassBlock(RegistrySupplier<Block> newBlock) {
     grassBlocks.add(newBlock.get());
    }
    public static void addFuelBlock(RegistrySupplier<Block> newBlock, int fuelTime) {
        System.out.println("Fuel time needs to be set for " + newBlock.getId());
    }

    public static void addBlockItem(RegistrySupplier<Block> newBlock) {
        ITEMS.register(newBlock.getId(), () -> new BlockItem(newBlock.get(), new Item.Settings()));

    }

    public static AbstractBlock.Settings copyBlock(Block copyBlock) {
        return AbstractBlock.Settings.copy(copyBlock);
    }

    public static ToIntFunction<BlockState> parseLux(int lux) {
        return state -> lux;
    }

    public static String findVanillaBlockID(Block block) {
        return block.toString().substring(block.toString().indexOf(":") + 1, block.toString().indexOf("}"));
    }

    public static MapColor checkDyeMapColour(String dye) {
        return switch (dye) {
            case "glow" -> MapColor.CYAN;
            case "dragon" -> MapColor.BLACK;
            case "star" -> MapColor.OFF_WHITE;
            case "honey" -> MapColor.YELLOW;
            case "nostalgia" -> MapColor.BROWN;
            case "rose" -> MapColor.BRIGHT_RED;
            default -> DyeColor.valueOf(dye.toUpperCase()).getMapColor();
        };
    }

    public static int checkDyeLux(String dye) {
        return switch (dye) {
            case "glow" -> 8;
            case "star" -> 15;
            default -> 0;
        };
    }
}