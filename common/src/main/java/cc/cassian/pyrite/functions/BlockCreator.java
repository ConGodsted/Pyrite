package cc.cassian.pyrite.functions;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.Map;
import java.util.Objects;

import static cc.cassian.pyrite.functions.ModHelpers.*;
import static cc.cassian.pyrite.functions.ModHelpers.getBlockSetType;
import static cc.cassian.pyrite.functions.ModLists.*;

public class BlockCreator {
    final static Block[] vanillaWood = getVanillaWood();
    final static Block[] resource_blocks = getVanillaResourceBlocks();

    @ExpectPlatform
    public static void platformRegister(String blockID, String blockType, AbstractBlock.Settings blockSettings, WoodType type, BlockSetType blockSetType, ParticleEffect particle, Block copyBlock) {
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
        AbstractBlock.Settings settings = AbstractBlock.Settings.create().strength(strength).luminance(state -> lightLevel).mapColor(color);
        if (Objects.equals(blockType, "obsidian")) {
            sendToRegistry(blockID, "block", settings.strength(strength, 1200f).pistonBehavior(PistonBehavior.BLOCK));
        }
        else if (blockType.equals("lamp")) {
            sendToRegistry(blockID, blockType, settings.sounds(BlockSoundGroup.GLASS));
        }
        else {
            sendToRegistry(blockID, blockType, settings.sounds(BlockSoundGroup.GLASS).nonOpaque().solidBlock(BlockCreator::never));
        }
    }

    private static boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    //Create and then add carpets
    private static void createCarpet(String blockID) {
        AbstractBlock.Settings blockSettings = copyBlock(Blocks.MOSS_CARPET);
        sendToRegistry(blockID, "carpet", blockSettings);
    }

    //Create and then add most of the manually generated blocks.
    public static void createPyriteBlock(String blockID, String blockType, Block copyBlock) {
        platformRegister(blockID, blockType, copyBlock(copyBlock), WoodType.CRIMSON, BlockSetType.IRON, null, copyBlock);
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
        platformRegister(blockID, blockType, blockSettings, null, null, null, null);
    }

    private static void sendToRegistry(String blockID, String blockType, AbstractBlock.Settings blockSettings) {
        platformRegister(blockID, blockType, blockSettings, null, null, null, null);

    }
    private static void sendToRegistry(String blockID, Block copyBlock, AbstractBlock.Settings blockSettings) {
        platformRegister(blockID, "stairs", blockSettings,  null, null, null, copyBlock);
    }
    
    //Add blocks with particles - Torches/Torch Levers
    private static void sendToRegistry(String blockID, String blockType, AbstractBlock.Settings blockSettings, ParticleEffect particle) {
        platformRegister(blockID, blockType, blockSettings, null, null, particle, null);
    }

    //Create blocks that require a Block Set.
    public static void createPyriteBlock(String blockID, String blockType, Block copyBlock, BlockSetType set) {
        platformRegister(blockID, blockType, copyBlock(copyBlock),  null, set, null, null);
    }

    //Create most of the generic Stained Blocks, then add them.
    public static void createPyriteBlock(String blockID, String blockType, Block copyBlock, MapColor color, int lux) {
        AbstractBlock.Settings blockSettings = copyBlock(copyBlock).mapColor(color).luminance(parseLux(lux));
        if ((copyBlock.equals(Blocks.OAK_PLANKS)) || (copyBlock.equals(Blocks.OAK_SLAB) || (copyBlock.equals(Blocks.OAK_STAIRS)))) {
            blockSettings = blockSettings.burnable();
        }
        platformRegister(blockID, blockType, blockSettings,  null, null, null, copyBlock);
    }

    //Create basic blocks.
    public static void createPyriteBlock(String blockID, Block copyBlock) {
        AbstractBlock.Settings blockSettings = copyBlock(copyBlock);
        platformRegister(blockID, "block", blockSettings,  null, null, null, null);
    }

    //Create Stained blocks that require a wood set or wood type, then add them.
    public static void createPyriteBlock(String blockID, String blockType, Block copyBlock, MapColor color, int lux, BlockSetType set, WoodType type) {
        AbstractBlock.Settings blockSettings = copyBlock(copyBlock).mapColor(color).luminance(parseLux(lux));
        if (!blockType.equals("button")) {
            blockSettings = blockSettings.burnable();
        }
        platformRegister(blockID, blockType, blockSettings,  type, set, null, null);
    }

    public static void generateFlowers() {
        for (Map.Entry<String, Block> entry : FLOWERS.entrySet()) {
            createPyriteBlock(entry.getKey(), "flower", entry.getValue());
        }
    }

    public static void generateTurfSets() {
        for (Map.Entry<String, Block> entry : TURF_SETS.entrySet()) {
            createTurfSet(entry.getKey(), entry.getValue());
        }
    }

    public static void generateNostalgiaBlocks() {
        for (Map.Entry<String, Block> entry : NOSTALGIA_BLOCKS.entrySet()) {
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
        createPyriteBlock(blockID+"_wall_gate","wall_gate", copyBlock, BlockSetType.STONE);
    }

    public static void generateBrickSet(String blockID, Block copyBlock, MapColor color) {
        generateBrickSet(blockID, copyBlock, color, 0);
    }

    public static void generateBrickSet(String blockID, Block copyBlock) {
        generateBrickSet(blockID, copyBlock, copyBlock.getDefaultMapColor(), 0);
    }

    public static void generateBrickSet(String blockID, Block copyBlock, MapColor color, boolean generateMossySet) {
        generateBrickSet(blockID, copyBlock, color, 0);
        if (generateMossySet)
            generateBrickSet("mossy_"+blockID, copyBlock, color, 0);
    }

        //Generate a Turf block set - including block and its slab, stair, and carpet variants.
    public static void createTurfSet(String blockID, Block copyBlock) {
        createPyriteBlock( blockID+"_turf", "block", copyBlock);
        createStair(blockID, copyBlock);
        createSlab(blockID, copyBlock);
        createCarpet(blockID+"_carpet");
    }
    
    @ExpectPlatform
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
        createPyriteBlock("%s_planks".formatted(blockID), "block", Blocks.OAK_PLANKS, color, blockLux);
        // Stairs
        createPyriteBlock("%s_stairs".formatted(blockID), "stairs",Blocks.OAK_STAIRS, color, blockLux);
        // Slabs
        createPyriteBlock("%s_slab".formatted(blockID), "slab", Blocks.OAK_SLAB, color, blockLux);
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
        createPyriteBlock("%s_crafting_table".formatted(blockID), "crafting", Blocks.CRAFTING_TABLE, color, blockLux);
        // Ladders
        createPyriteBlock("%s_ladder".formatted(blockID), "ladder", Blocks.LADDER, color, blockLux);
        // Signs
        createPyriteBlock("%s_sign".formatted(blockID), "sign", Blocks.OAK_SIGN, color, blockLux, GENERATED_SET, GENERATED_TYPE);
        // Hanging Signs
        createPyriteBlock("%s_hanging_sign".formatted(blockID), "hanging_sign", Blocks.OAK_HANGING_SIGN, color, blockLux, GENERATED_SET, GENERATED_TYPE);
    }

    /**
     * Generate an entire wood set, alongside Logs, Wood, and Stripped Logs/Wood.
     */
    public static void createWoodSetWithLog(String blockID, MapColor color, int blockLux) {
        createPyriteBlock("%s_log".formatted(blockID), "log", Blocks.OAK_LOG, color, blockLux);
        createPyriteBlock("stripped_%s_log".formatted(blockID), "log", Blocks.STRIPPED_OAK_LOG, color, blockLux);
        createPyriteBlock("%s_wood".formatted(blockID), "wood", Blocks.OAK_WOOD, color, blockLux);
        createPyriteBlock("stripped_%s_wood".formatted(blockID), "wood", Blocks.STRIPPED_OAK_WOOD, color, blockLux);
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
        createPyriteBlock("%s_wall".formatted(cutBlockID), "wall", block);
        //Cut Wall Gate
        createPyriteBlock("%s_wall_gate".formatted(cutBlockID),"wall_gate", block);
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
        createPyriteBlock("%s_wall".formatted(smoothBlockID), "wall", block);
        //Smooth Wall Gate
        createPyriteBlock("%s_wall_gate".formatted(smoothBlockID),"wall_gate", block);
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
                createPyriteBlock("chiseled_%s_block".formatted(blockID), "log", block);
            }
            //Pillar Blocks
            createPyriteBlock("%s_pillar".formatted(blockID), "log", block);
        }
        //Smooth Blocks
        createSmoothBlocks(blockID, block);
        createPyriteBlock("nostalgia_%s_block".formatted(blockID), block);
        //Block set for modded blocks
        BlockSetType set = getBlockSetType(blockID);
        //Create Bars/Doors/Trapdoors/Plates for those that don't already exist (Iron)
        if (!Objects.equals(blockID, "iron")) {
            //Bars
            createPyriteBlock("%s_bars".formatted(blockID),"bars", block);
            //Disable Copper doors in 1.21+
            if (!blockID.contains("copper")) {
                createPyriteBlock("%s_door".formatted(blockID),"door", block, set);
                createPyriteBlock("%s_trapdoor".formatted(blockID),"trapdoor", block, set);
            }
            //Create Plates for those that don't already exist (Iron and Gold)
            if (!blockID.equals("gold")) {
                createPyriteBlock("%s_pressure_plate".formatted(blockID),"pressure_plate", block, set);
            }
        }
        //Create buttons for all blocks.
        createPyriteBlock("%s_button".formatted(blockID),"button", block, set);
    }

}
