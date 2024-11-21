package cc.cassian.pyrite.functions;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.block.*;
import net.minecraft.particle.ParticleEffect;
import java.util.Map;
import java.util.Objects;

import static cc.cassian.pyrite.functions.ModHelpers.*;
import static cc.cassian.pyrite.functions.ModLists.*;

public class BlockCreator {
    final static Block[] vanillaWood = getVanillaWood();
    final static Block[] resource_blocks = getVanillaResourceBlocks();

    @ExpectPlatform
    public static void platfomRegister(String blockID, String blockType, AbstractBlock.Settings blockSettings, String type, String blockSetType, ParticleEffect particle, Block copyBlock) {
        throw new AssertionError();
    }

    //Create and add Pyrite items.
    @ExpectPlatform
    public static void registerPyriteItem(String itemID) {
        throw new AssertionError();
    }

    //Create and add Pyrite items.
    @ExpectPlatform
    public static void register() {
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
            createPyriteBlock(block + "_crafting_table","crafting", plankBlock);
        }
    }

    //Primarily used for Framed Glass, Glowstone/Dyed Lamps, Glowing Obsidian
    public static void createPyriteBlock(String blockID, String blockType, Float strength, MapColor color, int lightLevel) {
        AbstractBlock.Settings settings;
        if (blockID.contains("obsidian")) {
            settings = AbstractBlock.Settings.copy(Blocks.OBSIDIAN);
        }
        else settings = AbstractBlock.Settings.of(Material.GLASS);
        settings = settings.strength(strength).luminance(state -> lightLevel).mapColor(color);
        if (Objects.equals(blockType, "obsidian")) {
            sendToRegistry(blockID, "block", settings.strength(strength, 1200f));
        }
        else {
            sendToRegistry(blockID, blockType, settings);
        }
    }

    //Create and then add carpets
    private static void createCarpet(String blockID) {
        AbstractBlock.Settings blockSettings = copyBlock(Blocks.MOSS_CARPET);
        sendToRegistry(blockID, "carpet", blockSettings);
    }

    //Create and then add most of the manually generated blocks.
    public static void createPyriteBlock(String blockID, String blockType, Block copyBlock) {
        platfomRegister(blockID, blockType, copyBlock(copyBlock), "WoodType.CRIMSON", "BlockSetType.IRON", null, copyBlock);
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
        platfomRegister(blockID, blockType, blockSettings, null, null, null, null);
    }

    private static void sendToRegistry(String blockID, String blockType, AbstractBlock.Settings blockSettings) {
        platfomRegister(blockID, blockType, blockSettings, null, null, null, null);

    }
    private static void sendToRegistry(String blockID, Block copyBlock, AbstractBlock.Settings blockSettings) {
        platfomRegister(blockID, "stairs", blockSettings,  null, null, null, copyBlock);
    }

    //Add blocks with particles - Torches/Torch Levers
    private static void sendToRegistry(String blockID, String blockType, AbstractBlock.Settings blockSettings, ParticleEffect particle) {
        platfomRegister(blockID, blockType, blockSettings, null, null, particle, null);
    }

    //Create blocks that require a Block Set.
    public static void createPyriteBlock(String blockID, String blockType, Block copyBlock, String set) {
        platfomRegister(blockID, blockType, copyBlock(copyBlock),  null, set, null, null);
    }

    //Create most of the generic Stained Blocks, then add them.
    public static void createPyriteBlock(String blockID, String blockType, Block copyBlock, MapColor color, int lux) {
        AbstractBlock.Settings blockSettings = copyBlock(copyBlock).mapColor(color).luminance(parseLux(lux));
        platfomRegister(blockID, blockType, blockSettings,  null, null, null, copyBlock);
    }

    //Create basic blocks.
    public static void createPyriteBlock(String blockID, Block copyBlock) {
        AbstractBlock.Settings blockSettings = copyBlock(copyBlock);
        platfomRegister(blockID, "block", blockSettings,  null, null, null, null);
    }

    //Create Stained blocks that require a wood set or wood type, then add them.
    public static void createPyriteBlock(String blockID, String blockType, Block copyBlock, MapColor color, int lux, String set, String type) {
        AbstractBlock.Settings blockSettings = copyBlock(copyBlock).mapColor(color).luminance(parseLux(lux));
        platfomRegister(blockID, blockType, blockSettings,  type, set, null, null);
    }

    public static void generateFlowers() {
        for (Map.Entry<String, Block> entry : flowers.entrySet()) {
            createPyriteBlock(entry.getKey(), "flower", entry.getValue());
        }
    }

    public static void generateTurfSets() {
        for (Map.Entry<String, Block> entry : turfSets.entrySet()) {
            createTurfSet(entry.getKey(), entry.getValue());
        }
    }

    public static void generateNostalgiaBlocks() {
        for (Map.Entry<String, Block> entry : nostalgiaBlocks.entrySet()) {
            createPyriteBlock(entry.getKey(), "block", entry.getValue());
        }
        createPyriteBlock("nostalgia_gravel", "gravel", Blocks.GRAVEL);
    }

    //Generate an entire brick set.
    public static void generateBrickSet(String blockID, Block copyBlock, MapColor color, int lux) {
        //Bricks
        createPyriteBlock( blockID+"s", "block", copyBlock, color, lux);
        //Brick Stairs
        createPyriteBlock( blockID+"_stairs", "stairs", copyBlock, color, lux);
        //Brick Slab
        createPyriteBlock( blockID+"_slab", "slab", copyBlock, color, lux);
        //Brick Wall
        createPyriteBlock( blockID+"_wall", "wall", copyBlock, color, lux);
        //Brick Wall Gate
        createPyriteBlock(blockID+"_wall_gate","fence_gate", copyBlock);
    }


    //Generate a Turf block set - including block and its slab, stair, and carpet variants.
    public static void createTurfSet(String blockID, Block copyBlock) {
        createPyriteBlock( blockID+"_turf", "block", copyBlock);
        createStair(blockID, copyBlock);
        createSlab(blockID, copyBlock);
        createCarpet(blockID+"_carpet");
    }

    //Generate an entire wood set.
    public static void createWoodSet(String blockID, MapColor color, int blockLux) {
        String GENERATED_SET = blockID;
        String GENERATED_TYPE = blockID;
        // Planks
        createPyriteBlock( blockID+"_planks", "block", Blocks.OAK_PLANKS, color, blockLux);
        // Stairs
        createPyriteBlock(blockID+"_stairs", "stairs",Blocks.OAK_STAIRS, color, blockLux);
        // Slabs
        createPyriteBlock( blockID+"_slab", "slab", Blocks.OAK_SLAB, color, blockLux);
        // Fences
        createPyriteBlock(blockID+"_fence", "fence", Blocks.OAK_FENCE, color, blockLux, GENERATED_SET, GENERATED_TYPE);
        // Fence Gates
        createPyriteBlock(blockID+"_fence_gate", "fence_gate", Blocks.OAK_FENCE_GATE, color, blockLux, GENERATED_SET, GENERATED_TYPE);
        // Doors
        createPyriteBlock(blockID+"_door", "door", Blocks.OAK_DOOR, color, blockLux, GENERATED_SET, GENERATED_TYPE);
        // Trapdoors
        createPyriteBlock(blockID+"_trapdoor", "trapdoor", Blocks.OAK_TRAPDOOR, color, blockLux, GENERATED_SET, GENERATED_TYPE);
        // Pressure Plates
        createPyriteBlock( blockID+"_pressure_plate", "pressure_plate", Blocks.OAK_PRESSURE_PLATE, color, blockLux, GENERATED_SET, GENERATED_TYPE);
        // Buttons
        createPyriteBlock(blockID+"_button", "button", Blocks.OAK_BUTTON, color, blockLux, GENERATED_SET, GENERATED_TYPE);
        // Crafting Tables
        createPyriteBlock( blockID+"_crafting_table", "crafting", Blocks.CRAFTING_TABLE, color, blockLux);
        // Ladders
        createPyriteBlock( blockID+"_ladder", "ladder", Blocks.LADDER, color, blockLux);
        // Signs
        createPyriteBlock(blockID+"_sign", "sign", Blocks.OAK_SIGN, color, blockLux, GENERATED_SET, GENERATED_TYPE);
    }

    //Generate an entire Cut Block set.
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
        createPyriteBlock(cutBlockID+"_wall", "wall", block);
        //Cut Wall Gate
        createPyriteBlock(cutBlockID+"_wall_gate","fence_gate", block);
    }

    //Generate an entire Smooth Block set.
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
        createPyriteBlock(smoothBlockID+"_wall", "wall", block);
        //Smooth Wall Gate
        createPyriteBlock(smoothBlockID+"_wall_gate","fence_gate", block);
    }

    //Create a set of Resource Blocks
    public static void createResourceBlockSet(String blockID, Block block) {
        //Create Cut Blocks for those that don't already exist (Copper)
        createCutBlocks(blockID, block);
        //Create Bricks/Chiseled/Pillar/Smooth for those that don't already exist (Quartz)
        if (!Objects.equals(blockID, "quartz")) {
            //Brick Blocks
            createPyriteBlock(blockID+"_bricks", block);
            //Chiseled Blocks - Copper Blocks
            if (!blockID.contains("copper")) {
                createPyriteBlock("chiseled_"+blockID+"_block", "log", block);
            }
            //Pillar Blocks
            createPyriteBlock(blockID+"_pillar", "log", block);
        }
        //Smooth Blocks
        createSmoothBlocks(blockID, block);
        createPyriteBlock("nostalgia_"+blockID+"_block", block);
        //Block set for modded blocks
        String set = blockID;
        //Create Bars/Doors/Trapdoors/Plates for those that don't already exist (Iron)
        if (!Objects.equals(blockID, "iron")) {
            //Bars
            createPyriteBlock(blockID+"_bars","bars", block);
            //Disable Copper doors in 1.21+
            if (!blockID.contains("copper")) {
                createPyriteBlock(blockID+"_door","door", block, set);
                createPyriteBlock(blockID+"_trapdoor","trapdoor", block, set);
            }
            //Create Plates for those that don't already exist (Iron and Gold)
            if (!blockID.equals("gold")) {
                createPyriteBlock(blockID+"_pressure_plate","pressure_plate", block, set);
            }
        }
        //Create buttons for all blocks.
        createPyriteBlock(blockID+"_button","button", block, set);
    }

}