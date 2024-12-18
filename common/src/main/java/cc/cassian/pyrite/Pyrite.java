package cc.cassian.pyrite;

import cc.cassian.pyrite.functions.ModHelpers;
import cc.cassian.pyrite.functions.ModLists;
import net.minecraft.block.*;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static cc.cassian.pyrite.functions.BlockCreator.*;
import static cc.cassian.pyrite.functions.ModLists.*;
import static cc.cassian.pyrite.functions.ModHelpers.*;

public class Pyrite {
	public static final String MOD_ID = "pyrite";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static void init() {
		ModLists.populateLinkedHashMaps();
		// Framed Glass
		createPyriteBlock("framed_glass","glass", 2.0f, MapColor.CLEAR, 0, "misc");
		// Framed Glass Pane
		createPyriteBlock( "framed_glass_pane","glass_pane", 2.0f, MapColor.CLEAR, 0, "misc");
		// Switchable Glass
		createPyriteBlock("switchable_glass", "switchable_glass", Blocks.GLASS, "misc");
		// Cobblestone Bricks
		generateBrickSet("cobblestone_brick", Blocks.COBBLESTONE, MapColor.STONE_GRAY, true);
		// Smooth Stone Bricks
		generateBrickSet("smooth_stone_brick", Blocks.SMOOTH_STONE, MapColor.STONE_GRAY, true);
		// Granite Bricks
		generateBrickSet("granite_brick", Blocks.GRANITE, MapColor.DIRT_BROWN, true);
		// Andesite Bricks
		generateBrickSet("andesite_brick", Blocks.ANDESITE, MapColor.STONE_GRAY, true);
		// Diorite Bricks
		generateBrickSet("diorite_brick", Blocks.DIORITE, MapColor.OFF_WHITE, true);
		// Calcite Bricks
		generateBrickSet("calcite_brick", Blocks.CALCITE, MapColor.OFF_WHITE, true);
		// Mossy Tuff Bricks
		generateBrickSet("mossy_tuff_brick", Blocks.TUFF_BRICKS, MapColor.STONE_GRAY);
		// Mossy Deepslate Bricks
		generateBrickSet("mossy_deepslate_brick", Blocks.DEEPSLATE_BRICKS, MapColor.BLACK);
		// Sandstone Bricks
		generateBrickSet("sandstone_brick", Blocks.SANDSTONE, MapColor.PALE_YELLOW);
		// Grass Set
		generateTurfSets();
		// Nether Brick Fence Gate
		createPyriteBlock("nether_brick_fence_gate","fence_gate", Blocks.NETHER_BRICK_FENCE, "misc");
		// Resource Blocks
		generateResourceBlocks();
		// Torch Levers
		createTorchLever("torch_lever", Blocks.TORCH, ParticleTypes.FLAME);
		createTorchLever("redstone_torch_lever", Blocks.SOUL_TORCH, DustParticleEffect.DEFAULT);
		createTorchLever("soul_torch_lever", Blocks.TORCH, ParticleTypes.SOUL_FIRE_FLAME);
		// Lamps
		createPyriteBlock("lit_redstone_lamp", "block", Blocks.REDSTONE_LAMP, 15);
		createPyriteBlock("glowstone_lamp","block", 0.3f, MapColor.YELLOW, 15, "misc");
		// Classic Features
		createPyriteBlock("glowing_obsidian","obsidian", 50f, MapColor.RED, 15, "misc");
		createPyriteBlock("nostalgia_glowing_obsidian","obsidian", 50f, MapColor.RED, 15, "misc");
		createPyriteBlock("locked_chest", "facing", Blocks.CHEST, 15);
		generateNostalgiaBlocks();
		// Classic Flowers
		generateFlowers();
		// Charred Nether Bricks
		generateBrickSet("charred_nether_brick", Blocks.NETHER_BRICKS, MapColor.BLACK, 0);
		// Blue Nether Bricks
		generateBrickSet("blue_nether_brick", Blocks.NETHER_BRICKS, MapColor.BLUE, 0);
		// Vanilla Crafting Tables
		generateVanillaCraftingTables();
		// Modded Crafting Tables
		if (ModHelpers.isModLoaded("aether")) {
			createPyriteBlock("skyroot_crafting_table","crafting", Blocks.CRAFTING_TABLE, "misc");
			createPyriteBlock( "holystone_wall_gate","wall_gate", Blocks.STONE, BlockSetType.STONE, "misc");
			createPyriteBlock( "mossy_holystone_wall_gate","wall_gate", Blocks.STONE, BlockSetType.STONE, "misc");
			createPyriteBlock( "holystone_brick_wall_gate","wall_gate", Blocks.STONE, BlockSetType.STONE, "misc");
			createPyriteBlock( "icestone_wall_gate","wall_gate", Blocks.STONE, BlockSetType.STONE, "misc");
			createPyriteBlock( "aerogel_wall_gate","wall_gate", Blocks.STONE, BlockSetType.STONE, "misc");
			createPyriteBlock( "carved_wall_gate","wall_gate", Blocks.STONE, BlockSetType.STONE, "misc");
			createPyriteBlock( "angelic_wall_gate","wall_gate", Blocks.STONE, BlockSetType.STONE, "misc");
			createPyriteBlock( "hellfire_wall_gate","wall_gate", Blocks.STONE, BlockSetType.STONE, "misc");
		}
		// Red Mushroom Wood Set
		createPyriteBlock("red_mushroom_stem", "log", Blocks.MUSHROOM_STEM, "misc");
		createWoodSet("red_mushroom", MapColor.RED, 0);
		// Brown Mushroom Wood Set
		createPyriteBlock("brown_mushroom_stem", "log", Blocks.MUSHROOM_STEM, "misc");
		createWoodSet("brown_mushroom", MapColor.BROWN, 0);
		// Azalea Wood Set
		createWoodSetWithLog("azalea", MapColor.DULL_RED, 0);
		// Autogenerate dye blocks.
		final String[] dyes = getDyes();
		for (int dyeIndex = 0; dyeIndex < dyes.length; dyeIndex++) {
			String dye = dyes[dyeIndex];
			int blockLux = checkDyeLux(dye);
			MapColor color = checkDyeMapColour(dye);
			if (dyeIndex > 15) {
				// Dye items.
				registerPyriteItem(dye + "_dye");
				// Dyed Wool
				createPyriteBlock(dye + "_wool", "block", Blocks.WHITE_WOOL, color, blockLux, "dyed");
				// Dyed Carpet
				createPyriteBlock(dye + "_carpet", "carpet", Blocks.WHITE_CARPET, color, blockLux, "dyed");
				// Dyed Concrete
				createPyriteBlock(dye+"_concrete", "block", Blocks.WHITE_CONCRETE, color, blockLux, "dyed");
				// Dyed Concrete Powder
				createPyriteBlock(dye+"_concrete_powder", "concrete_powder", Blocks.WHITE_CONCRETE_POWDER, color, blockLux, "misc");
			}
			// Dyed Concrete Stairs
			createPyriteBlock(dye+"_concrete_stairs", "stairs", Blocks.WHITE_CONCRETE, color, blockLux, "dyed");
			// Dyed Concrete Slab
			createPyriteBlock(dye+"_concrete_slab", "slab", Blocks.WHITE_CONCRETE, color, blockLux, "dyed");
			//Dyed Planks and plank products
			createWoodSet(dye + "_stained", color, blockLux);
			// Dyed Bricks and brick products
			generateBrickSet(dye + "_brick", Blocks.BRICKS, color, blockLux);
			if (dyeIndex > 15) {
				// Dyed Terracotta
				createPyriteBlock(dye+"_terracotta", "block", Blocks.TERRACOTTA,color, blockLux, "dyed");
				// Dyed Glazed Terracotta
				//coming soon - createPyriteBlock(dye+"_glazed_terracotta", "block", Blocks.TERRACOTTA,color, blockLux);
			}
			// Dyed Terracotta Bricks
			generateBrickSet(dye+"_terracotta_brick", Blocks.TERRACOTTA, color, blockLux);
			// Dyed Torches
			createTorch(dye+"_torch", getTorchParticle(dye));
			if (dyeIndex > 15) {
				// Dyed Stained Glass
				createPyriteBlock(dye+"_stained_glass","stained_framed_glass", 0.3f, color, blockLux, "misc");
				// Dyed Stained Glass Pane
				createPyriteBlock(dye+"_stained_glass_pane","stained_framed_glass_pane", 0.3f, color, blockLux, "misc");
			}
			// Dyed Lamps
			createPyriteBlock(dye + "_lamp","block", 0.3f, color, 15, "dyed");
			// Dyed Framed Glass
			createPyriteBlock(dye+"_framed_glass","stained_framed_glass", 2.0f, color, blockLux, "dyed");
			// Dyed Framed Glass Pane
			createPyriteBlock( dye+"_framed_glass_pane","stained_framed_glass_pane", 2.0f, color, blockLux, "dyed");
			// Dyed Torch Levers
			createTorchLever(dye+"_torch_lever", Blocks.TORCH, getTorchParticle(dye));
		}
		// Autogenerate Wall Gates
		for (Block wallsBlock : getVanillaWalls()) {
			//Find block ID
			String block = findVanillaBlockID(wallsBlock);
			//If the block provided isn't a wall block, add the wall tag.
			if (!block.contains("wall")) {
				block = block + "_wall";
			}
			//Create block.
			createPyriteBlock(block + "_gate","wall_gate", wallsBlock, BlockSetType.STONE, "misc");
		}
	}
}