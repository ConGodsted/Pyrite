package cc.cassian.pyrite.functions.forge;

import net.minecraft.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;


public class ForgeHelpers {

    public static final ArrayList<RegistryObject<Block>> GRASS_BLOCKS = new ArrayList<>();
    public static final HashMap<RegistryObject<Block >, Integer> FUEL_BLOCKS = new HashMap<>();
    public static final HashMap<RegistryObject<Block>, Integer> FLAMMABLE_BLOCKS = new HashMap<>();

    public static void addGrassBlock(RegistryObject<Block> newBlock) {
        GRASS_BLOCKS.add(newBlock);
    }

}