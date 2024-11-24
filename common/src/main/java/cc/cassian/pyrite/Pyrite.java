package cc.cassian.pyrite;

import cc.cassian.pyrite.functions.ModHelpers;
import net.minecraft.block.*;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static cc.cassian.pyrite.functions.BlockCreator.*;
import static cc.cassian.pyrite.functions.ModLists.*;
import static cc.cassian.pyrite.functions.ModHelpers.*;

public class Pyrite {
	public final static String modID = "pyrite";
	public static final Logger LOGGER = LogManager.getLogger(modID);


    public static void init() {
		// Framed Glass
		createPyriteBlock("framed_glass","glass", 2.0f, MapColor.CLEAR, 0);
		// Framed Glass Pane
		createPyriteBlock( "framed_glass_pane","glass_pane", 2.0f, MapColor.CLEAR, 0);
		// Switchable Glass
		createPyriteBlock("switchable_glass", "switchable_glass", Blocks.GLASS);
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
		// Grass Set
		generateTurfSets();
		// Nether Brick Fence Gate
		createPyriteBlock("nether_brick_fence_gate","fence_gate", Blocks.NETHER_BRICK_FENCE);
		// Resource Blocks
		generateResourceBlocks();
		// Torch Levers
		createTorchLever("torch_lever", Blocks.TORCH, ParticleTypes.FLAME);
		createTorchLever("redstone_torch_lever", Blocks.SOUL_TORCH, DustParticleEffect.DEFAULT);
		createTorchLever("soul_torch_lever", Blocks.TORCH, ParticleTypes.SOUL_FIRE_FLAME);
		// Lamps
		createPyriteBlock("lit_redstone_lamp", "block", Blocks.REDSTONE_LAMP, 15);
		createPyriteBlock("glowstone_lamp","block", 0.3f, MapColor.YELLOW, 15);
		// Classic Features
		createPyriteBlock("glowing_obsidian","obsidian", 50f, MapColor.RED, 15);
		createPyriteBlock("nostalgia_glowing_obsidian","obsidian", 50f, MapColor.RED, 15);
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
			createPyriteBlock("skyroot_crafting_table","crafting", Blocks.CRAFTING_TABLE);
			createPyriteBlock( "holystone_wall_gate","fence_gate", Blocks.STONE);
			createPyriteBlock( "mossy_holystone_wall_gate","fence_gate", Blocks.STONE);
			createPyriteBlock( "holystone_brick_wall_gate","fence_gate", Blocks.STONE);
			createPyriteBlock( "icestone_wall_gate","fence_gate", Blocks.STONE);
			createPyriteBlock( "aerogel_wall_gate","fence_gate", Blocks.STONE);
			createPyriteBlock( "carved_wall_gate","fence_gate", Blocks.STONE);
			createPyriteBlock( "angelic_wall_gate","fence_gate", Blocks.STONE);
			createPyriteBlock( "hellfire_wall_gate","fence_gate", Blocks.STONE);


		}
		// Red Mushroom Blocks
		createPyriteBlock("red_mushroom_stem", "log", Blocks.MUSHROOM_STEM);
		createWoodSet("red_mushroom", MapColor.RED, 0);
		// Brown Mushroom Blocks
		createPyriteBlock("brown_mushroom_stem", "log", Blocks.MUSHROOM_STEM);
		createWoodSet("brown_mushroom", MapColor.BROWN, 0);
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
				createPyriteBlock(dye + "_wool", "block", Blocks.WHITE_WOOL, color, blockLux);
				// Dyed Carpet
				createPyriteBlock(dye + "_carpet", "carpet", Blocks.WHITE_CARPET, color, blockLux);
				// Dyed Concrete
				createPyriteBlock(dye+"_concrete", "block", Blocks.WHITE_CONCRETE, color, blockLux);
				// Dyed Concrete Powder
				createPyriteBlock(dye+"_concrete_powder", "concrete_powder", Blocks.WHITE_CONCRETE_POWDER, color, blockLux);
			}
			// Dyed Concrete Stairs
			createPyriteBlock( dye+"_concrete_stairs", "stairs", Blocks.WHITE_CONCRETE, color, blockLux);
			// Dyed Concrete Slab
			createPyriteBlock( dye+"_concrete_slab", "slab", Blocks.WHITE_CONCRETE, color, blockLux);
			//Dyed Planks and plank products
			createWoodSet(dye + "_stained", color, blockLux);
			// Dyed Bricks and brick products
			generateBrickSet(dye + "_brick", Blocks.BRICKS, color, blockLux);
			if (dyeIndex > 15) {
				// Dyed Terracotta
				createPyriteBlock(dye+"_terracotta", "block", Blocks.TERRACOTTA,color, blockLux);
				// Dyed Glazed Terracotta
				//coming soon - createPyriteBlock(dye+"_glazed_terracotta", "block", Blocks.TERRACOTTA,color, blockLux);
			}
			// Dyed Terracotta Bricks
			generateBrickSet(dye+"_terracotta_brick", Blocks.TERRACOTTA, color, blockLux);
			// Dyed Torches
			createTorch(dye+"_torch", getTorchParticle(dye));
			// Dyed Lamps
			createPyriteBlock(dye + "_lamp","block", 0.3f, color, 15);
			// Dyed Framed Glass
			createPyriteBlock(dye+"_framed_glass","tinted_glass", 2.0f, color, blockLux);
			// Dyed Framed Glass Pane
			createPyriteBlock( dye+"_framed_glass_pane","tinted_glass_pane", 2.0f, color, blockLux);
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
			createPyriteBlock(block + "_gate","fence_gate", wallsBlock);
		}
	}




}