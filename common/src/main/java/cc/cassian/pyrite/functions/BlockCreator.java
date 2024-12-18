package cc.cassian.pyrite.functions;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.BlockSoundGroup;

import java.util.Map;
import java.util.Objects;

import static cc.cassian.pyrite.functions.ModHelpers.*;
import static cc.cassian.pyrite.functions.ModHelpers.getBlockSetType;
import static cc.cassian.pyrite.functions.ModLists.*;

public class BlockCreator {
    final static Block[] vanillaWood = getVanillaWood();
    final static Block[] resource_blocks = getVanillaResourceBlocks();

    @ExpectPlatform @SuppressWarnings("unused")
    public static void platformRegister(String blockID, String blockType, AbstractBlock.Settings blockSettings, WoodType type, BlockSetType blockSetType, ParticleEffect particle, Block copyBlock, String group) {
        throw new AssertionError();
    }

    //Create and add Pyrite items.
    @ExpectPlatform @SuppressWarnings("unused")
    public static void registerPyriteItem(String itemID) {
        throw new AssertionError();
    }

    public static void generateResourceBlocks() {
        for (Block resourceBlock : resource_blocks) {
            String block = findVanillaBlockID(resourceBlock);
            //If the block provided isn't a wall block, add the wall tag.
            if (block.contains("block")) {
                block = block.substring(0, block.indexOf("_block"));
            }
            createResourceBlockSet(block, resourceBlock);
        }

    }

    public static void createTorchLever(String blockID, Block baseTorch, ParticleEffect particle) {
        sendToRegistry(blockID, "torch_lever", AbstractBlock.Settings.copy(baseTorch), particle);
    }
    public static void createTorch(String blockID, ParticleEffect particle) {
        sendToRegistry(blockID, "torch", AbstractBlock.Settings.copy(Blocks.TORCH), particle);
    }

    public static void generateVanillaCraftingTables() {
        //Autogenerate Vanilla Crafting Tables
        for (Block plankBlock : vanillaWood) {
            //Find block ID
            String block = findVanillaBlockID(plankBlock);
            //If the block provided isn't a wall block, add the wall tag.
            if (block.contains("planks")) {
                block = block.substring(0, block.indexOf("_planks"));
            }
            //Create block.
            createPyriteBlock(block + "_crafting_table","crafting", plankBlock, "misc");
        }
    }

    //Primarily used for Framed Glass, Glowstone/Dyed Lamps, Glowing Obsidian
    public static void createPyriteBlock(String blockID, String blockType, Float strength, MapColor color, int lightLevel, String group) {
        AbstractBlock.Settings settings = AbstractBlock.Settings.create().strength(strength).luminance(state -> lightLevel).mapColor(color);
        if (Objects.equals(blockType, "obsidian")) {
            sendToRegistry(blockID, "block", settings.strength(strength, 1200f).pistonBehavior(PistonBehavior.BLOCK));
        }
        else {
            sendToRegistry(blockID, blockType, settings.sounds(BlockSoundGroup.GLASS));
        }
    }

    //Create and then add carpets
    private static void createCarpet(String blockID) {
        AbstractBlock.Settings blockSettings = copyBlock(Blocks.MOSS_CARPET);
        sendToRegistry(blockID, "carpet", blockSettings);
    }

    //Create and then add most of the manually generated blocks.
    public static void createPyriteBlock(String blockID, String blockType, Block copyBlock, String group) {
        platformRegister(blockID, blockType, copyBlock(copyBlock), WoodType.CRIMSON, BlockSetType.IRON, null, copyBlock, group);
    }

    //Create a slab from the last block added.
    public static void createStair(String blockID, Block copyBlock) {
        AbstractBlock.Settings blockSettings = copyBlock(copyBlock);
        sendToRegistry(blockID+"_stairs", copyBlock, blockSettings);
    }

    //Create a slab from the last block added.
    public static void createSlab(String blockID, Block copyBlock) {
        AbstractBlock.Settings blockSettings = copyBlock(copyBlock);
        sendToRegistry(blockID+"_slab", "slab", blockSettings);
    }

    //Create blocks that require a change in light level, e.g. Locked Chests
    public static void createPyriteBlock(String blockID, String blockType, Block copyBlock, int lux) {
        AbstractBlock.Settings blockSettings = copyBlock(copyBlock).luminance(parseLux(lux));
        platformRegister(blockID, blockType, blockSettings, null, null, null, null, "misc");
    }

    private static void sendToRegistry(String blockID, String blockType, AbstractBlock.Settings blockSettings) {
        platformRegister(blockID, blockType, blockSettings, null, null, null, null, "misc");

    }
    private static void sendToRegistry(String blockID, Block copyBlock, AbstractBlock.Settings blockSettings) {
        platformRegister(blockID, "stairs", blockSettings,  null, null, null, copyBlock, "misc");
    }
    
    //Add blocks with particles - Torches/Torch Levers
    private static void sendToRegistry(String blockID, String blockType, AbstractBlock.Settings blockSettings, ParticleEffect particle) {
        platformRegister(blockID, blockType, blockSettings, null, null, particle, null, "misc");
    }

    //Create blocks that require a Block Set.
    public static void createPyriteBlock(String blockID, String blockType, Block copyBlock, BlockSetType set, String group) {
        platformRegister(blockID, blockType, copyBlock(copyBlock),  null, set, null, null, group);
    }

    //Create most of the generic Stained Blocks, then add them.
    public static void createPyriteBlock(String blockID, String blockType, Block copyBlock, MapColor color, int lux, String group) {
        AbstractBlock.Settings blockSettings = copyBlock(copyBlock).mapColor(color).luminance(parseLux(lux));
        if ((copyBlock.equals(Blocks.OAK_PLANKS)) || (copyBlock.equals(Blocks.OAK_SLAB) || (copyBlock.equals(Blocks.OAK_STAIRS)))) {
            blockSettings = blockSettings.burnable();
        }
        platformRegister(blockID, blockType, blockSettings,  null, null, null, copyBlock, group);
    }

    //Create basic blocks.
    public static void createPyriteBlock(String blockID, Block copyBlock) {
        AbstractBlock.Settings blockSettings = copyBlock(copyBlock);
        platformRegister(blockID, "block", blockSettings,  null, null, null, null, "misc");
    }

    //Create Stained blocks that require a wood set or wood type, then add them.
    public static void createPyriteBlock(String blockID, String blockType, Block copyBlock, MapColor color, int lux, BlockSetType set, WoodType type) {
        AbstractBlock.Settings blockSettings = copyBlock(copyBlock).mapColor(color).luminance(parseLux(lux));
        if (!blockType.equals("button")) {
            blockSettings = blockSettings.burnable();
        }
        platformRegister(blockID, blockType, blockSettings,  type, set, null, null, "wood");
    }

    public static void generateFlowers() {
        for (Map.Entry<String, Block> entry : FLOWERS.entrySet()) {
            createPyriteBlock(entry.getKey(), "flower", entry.getValue(), "misc");
        }
    }

    public static void generateTurfSets() {
        for (Map.Entry<String, Block> entry : TURF_SETS.entrySet()) {
            createTurfSet(entry.getKey(), entry.getValue());
        }
    }

    public static void generateNostalgiaBlocks() {
        for (Map.Entry<String, Block> entry : NOSTALGIA_BLOCKS.entrySet()) {
            createPyriteBlock(entry.getKey(), "block", entry.getValue(), "misc");
        }
        createPyriteBlock("nostalgia_gravel", "gravel", Blocks.GRAVEL, "misc");
    }

    //Generate an entire brick set.
    public static void generateBrickSet(String blockID, Block copyBlock, MapColor color, int lux) {
        //Bricks
        createPyriteBlock( blockID+"s", "block", copyBlock, color, lux, "misc");
        //Brick Stairs
        createPyriteBlock( blockID+"_stairs", "stairs", copyBlock, color, lux, "misc");
        //Brick Slab
        createPyriteBlock( blockID+"_slab", "slab", copyBlock, color, lux, "misc");
        //Brick Wall
        createPyriteBlock( blockID+"_wall", "wall", copyBlock, color, lux, "misc");
        //Brick Wall Gate
        createPyriteBlock(blockID+"_wall_gate","wall_gate", copyBlock, BlockSetType.STONE, "misc");
    }

    public static void generateBrickSet(String blockID, Block copyBlock, MapColor color) {
        generateBrickSet(blockID, copyBlock, color, 0);
    }

    public static void generateBrickSet(String blockID, Block copyBlock, MapColor color, boolean generateMossySet) {
        generateBrickSet(blockID, copyBlock, color, 0);
        if (generateMossySet)
            generateBrickSet("mossy_"+blockID, copyBlock, color, 0);
    }

        //Generate a Turf block set - including block and its slab, stair, and carpet variants.
    public static void createTurfSet(String blockID, Block copyBlock) {
        createPyriteBlock( blockID+"_turf", "block", copyBlock, "misc");
        createStair(blockID, copyBlock);
        createSlab(blockID, copyBlock);
        createCarpet(blockID+"_carpet");
    }
    
    @ExpectPlatform @SuppressWarnings("unused")
    public static WoodType createWoodType(String blockID, BlockSetType setType) {
        throw new AssertionError();
    }

    /**
     * Generate an entire wood set.
     */
    public static void createWoodSet(String blockID, MapColor color, int blockLux) {
        BlockSetType GENERATED_SET = new BlockSetType(blockID);
        WoodType GENERATED_TYPE = createWoodType(blockID, GENERATED_SET);
        // Planks
        createPyriteBlock("%s_planks".formatted(blockID), "block", Blocks.OAK_PLANKS, color, blockLux, "wood");
        // Stairs
        createPyriteBlock("%s_stairs".formatted(blockID), "stairs",Blocks.OAK_STAIRS, color, blockLux, "wood");
        // Slabs
        createPyriteBlock("%s_slab".formatted(blockID), "slab", Blocks.OAK_SLAB, color, blockLux, "wood");
        // Fences
        createPyriteBlock("%s_fence".formatted(blockID), "fence", Blocks.OAK_FENCE, color, blockLux, GENERATED_SET, GENERATED_TYPE);
        // Fence Gates
        createPyriteBlock("%s_fence_gate".formatted(blockID), "fence_gate", Blocks.OAK_FENCE_GATE, color, blockLux, GENERATED_SET, GENERATED_TYPE);
        // Doors
        createPyriteBlock("%s_door".formatted(blockID), "door", Blocks.OAK_DOOR, color, blockLux, GENERATED_SET, GENERATED_TYPE);
        // Trapdoors
        createPyriteBlock("%s_trapdoor".formatted(blockID), "trapdoor", Blocks.OAK_TRAPDOOR, color, blockLux, GENERATED_SET, GENERATED_TYPE);
        // Pressure Plates
        createPyriteBlock("%s_pressure_plate".formatted(blockID), "pressure_plate", Blocks.OAK_PRESSURE_PLATE, color, blockLux, GENERATED_SET, GENERATED_TYPE);
        // Buttons
        createPyriteBlock("%s_button".formatted(blockID), "button", Blocks.OAK_BUTTON, color, blockLux, GENERATED_SET, GENERATED_TYPE);
        // Crafting Tables
        createPyriteBlock("%s_crafting_table".formatted(blockID), "crafting", Blocks.CRAFTING_TABLE, color, blockLux, "wood");
        // Ladders
        createPyriteBlock("%s_ladder".formatted(blockID), "ladder", Blocks.LADDER, color, blockLux, "wood");
        // Signs
        createPyriteBlock("%s_sign".formatted(blockID), "sign", Blocks.OAK_SIGN, color, blockLux, GENERATED_SET, GENERATED_TYPE);
        // Hanging Signs
        createPyriteBlock("%s_hanging_sign".formatted(blockID), "hanging_sign", Blocks.OAK_HANGING_SIGN, color, blockLux, GENERATED_SET, GENERATED_TYPE);
    }

    /**
     * Generate an entire wood set, alongside Logs, Wood, and Stripped Logs/Wood.
     */
    public static void createWoodSetWithLog(String blockID, MapColor color, int blockLux) {
        createPyriteBlock("%s_log".formatted(blockID), "log", Blocks.OAK_LOG, color, blockLux, "wood");
        createPyriteBlock("stripped_%s_log".formatted(blockID), "log", Blocks.STRIPPED_OAK_LOG, color, blockLux, "wood");
        createPyriteBlock("%s_wood".formatted(blockID), "wood", Blocks.OAK_WOOD, color, blockLux, "wood");
        createPyriteBlock("stripped_%s_wood".formatted(blockID), "wood", Blocks.STRIPPED_OAK_WOOD, color, blockLux, "wood");
        createWoodSet(blockID, color, blockLux);
    }

    /**
     * Generate an entire Cut Block set.
     */
    public static void createCutBlocks(String blockID, Block block) {
        String cutBlockID = "cut_" + blockID;
        if (!blockID.contains("copper")) {
            //Cut Block
            createPyriteBlock(cutBlockID, block);
            //Cut Stairs
            createStair(cutBlockID, block);
            //Cut Slab
            createSlab(cutBlockID, block);
        }
        //Cut Wall
        createPyriteBlock("%s_wall".formatted(cutBlockID), "wall", block, "resource-blocks");
        //Cut Wall Gate
        createPyriteBlock("%s_wall_gate".formatted(cutBlockID),"wall_gate", block, "resource-blocks");
    }

    /**
     * Generate an entire Smooth Block set.
     */
    public static void createSmoothBlocks(String blockID, Block block) {
        String smoothBlockID = "smooth_" + blockID;
        if (!Objects.equals(blockID, "quartz")) {
            //Smooth Block
            createPyriteBlock(smoothBlockID, block);
            //Smooth Stairs
            createStair(smoothBlockID, block);
            //Smooth Slab
            createSlab(smoothBlockID, block);
        }
        //Smooth Wall
        createPyriteBlock("%s_wall".formatted(smoothBlockID), "wall", block, "resource-blocks");
        //Smooth Wall Gate
        createPyriteBlock("%s_wall_gate".formatted(smoothBlockID),"wall_gate", block, "resource-blocks");
    }

    //Create a set of Resource Blocks
    public static void createResourceBlockSet(String blockID, Block block) {
        //Create Cut Blocks for those that don't already exist (Copper)
        createCutBlocks(blockID, block);
        //Create Bricks/Chiseled/Pillar/Smooth for those that don't already exist (Quartz)
        if (!Objects.equals(blockID, "quartz")) {
            //Brick Blocks
            createPyriteBlock("%s_bricks".formatted(blockID), block);
            //Chiseled Blocks - Copper Blocks
            if (!blockID.contains("copper")) {
                createPyriteBlock("chiseled_%s_block".formatted(blockID), "log", block, "resource-blocks");
            }
            //Pillar Blocks
            createPyriteBlock("%s_pillar".formatted(blockID), "log", block, "resource-blocks");
        }
        //Smooth Blocks
        createSmoothBlocks(blockID, block);
        createPyriteBlock("nostalgia_%s_block".formatted(blockID), block);
        //Block set for modded blocks
        BlockSetType set = getBlockSetType(blockID);
        //Create Bars/Doors/Trapdoors/Plates for those that don't already exist (Iron)
        if (!Objects.equals(blockID, "iron")) {
            //Bars
            createPyriteBlock("%s_bars".formatted(blockID),"bars", block, "resource-blocks");
            //Disable Copper doors in 1.21+
            if (!blockID.contains("copper")) {
                createPyriteBlock("%s_door".formatted(blockID),"door", block, set, "resource-blocks");
                createPyriteBlock("%s_trapdoor".formatted(blockID),"trapdoor", block, set, "resource-blocks");
            }
            //Create Plates for those that don't already exist (Iron and Gold)
            if (!blockID.equals("gold")) {
                createPyriteBlock("%s_pressure_plate".formatted(blockID),"pressure_plate", block, set, "resource-blocks");
            }
        }
        //Create buttons for all blocks.
        createPyriteBlock("%s_button".formatted(blockID),"button", block, set, "resource-blocks");
    }

}
