package cc.cassian.pyrite;

import cc.cassian.pyrite.functions.ModHelpers;
import cc.cassian.pyrite.functions.architectury.CommonRegistry;
import cc.cassian.pyrite.functions.fabric.FabricRegistry;
import net.minecraft.block.*;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;

import java.util.Objects;

import static cc.cassian.pyrite.functions.BlockCreator.*;
import static cc.cassian.pyrite.functions.ModLists.*;
import static cc.cassian.pyrite.functions.ModHelpers.*;
import static cc.cassian.pyrite.functions.fabric.FabricRegistry.*;

public class Pyrite {
	public final static String modID = "pyrite";


    public static void init(String platform) {
		//Framed Glass
		createPyriteBlock("framed_glass","glass", 2.0f, MapColor.CLEAR, 0, platform);
		//Framed Glass Pane
		createPyriteBlock( "framed_glass_pane","glass_pane", 2.0f, MapColor.CLEAR, 0, platform);
		//Cobblestone Bricks
		generateBrickSet("cobblestone_brick", Blocks.COBBLESTONE, MapColor.STONE_GRAY, 0, platform);
		//Mossy Cobblestone Bricks
		generateBrickSet("mossy_cobblestone_brick", Blocks.MOSSY_COBBLESTONE, MapColor.STONE_GRAY, 0, platform);
		generateBrickSet("smooth_stone_brick", Blocks.COBBLESTONE, MapColor.STONE_GRAY, 0, platform);
		//Grass Set
		createTurfSets(platform);
		//Nether Brick Fence Gate
		createPyriteBlock("nether_brick_fence_gate","fence_gate", Blocks.NETHER_BRICK_FENCE, platform);
		//Resource Blocks
		generateResourceBlocks(platform);
		//Torch Levers
		createTorchLever("torch_lever", Blocks.TORCH, ParticleTypes.FLAME, platform);
		createTorchLever("redstone_torch_lever", Blocks.SOUL_TORCH, DustParticleEffect.DEFAULT, platform);
		createTorchLever("soul_torch_lever", Blocks.TORCH, ParticleTypes.SOUL_FIRE_FLAME, platform);
		//Lamps
		createPyriteBlock("lit_redstone_lamp", "block", Blocks.REDSTONE_LAMP, 15, platform);
		createPyriteBlock("glowstone_lamp","block", 0.3f, MapColor.YELLOW, 15, platform);
		//Classic Features
		createPyriteBlock("glowing_obsidian","obsidian", 50f, MapColor.RED, 15, platform);
		createPyriteBlock("nostalgia_glowing_obsidian","obsidian", 50f, MapColor.RED, 15, platform);
		createPyriteBlock("locked_chest", "facing", Blocks.CHEST, 15, platform);
		createNostalgia(platform);
		//Classic Flowers
		createFlowers(platform);
		//Charred Nether Bricks
		generateBrickSet("charred_nether_brick", Blocks.NETHER_BRICKS, MapColor.BLACK, 0, platform);
		//Blue Nether Bricks
		generateBrickSet("blue_nether_brick", Blocks.NETHER_BRICKS, MapColor.BLUE, 0, platform);
		//Vanilla Crafting Tables
		generateVanillaCraftingTables(platform);
		//Red Mushroom Blocks
		createPyriteBlock("red_mushroom_stem", "log", Blocks.MUSHROOM_STEM, platform);
		createWoodSet("red_mushroom", MapColor.RED, 0, platform);
		//Brown Mushroom Blocks
		createPyriteBlock("brown_mushroom_stem", "log", Blocks.MUSHROOM_STEM, platform);
		createWoodSet("brown_mushroom", MapColor.BROWN, 0, platform);
		//Autogenerate dye blocks.
		final String[] dyes = getDyes();
		for (int dyeIndex = 0; dyeIndex < dyes.length; dyeIndex++) {
			String dye = dyes[dyeIndex];
			int blockLux = checkDyeLux(dye);
			MapColor color = checkDyeMapColour(dye);
			if (dyeIndex > 15) {
				//Dye items.
				createPyriteItem(dye + "_dye", platform);
				//Dyed Wool
				createPyriteBlock(dye + "_wool", "block", Blocks.WHITE_WOOL, color, blockLux, platform);
				//Terracotta Block
				//coming soon - createPyriteBlock(dye+"_terracotta", "block", Blocks.TERRACOTTA,color, blockLux);
				//Glazed Terracotta Block
				//coming soon - createPyriteBlock(dye+"_glazed_terracotta", "block", Blocks.TERRACOTTA,color, blockLux);
				//Concrete Powder Block
				//coming soon - createPyriteBlock(dye+"_concrete", "block", Blocks.TERRACOTTA,color, blockLux);
				//Concrete Block
				//coming soon - createPyriteBlock(dye+"_concrete", "block", Blocks.CONCRETE,color, blockLux);
				//Carpet block
				createPyriteBlock(dye + "_carpet", "carpet", Blocks.WHITE_WOOL, color, blockLux, platform);
			}
			//Planks and plank products
			createWoodSet(dye + "_stained", color, blockLux, platform);
			//Bricks and brick products
			generateBrickSet(dye + "_brick", Blocks.BRICKS, color, blockLux, platform);
			//Dyed Lamps
			createPyriteBlock(dye + "_lamp","block", 0.3f, color, 15, platform);

		}
		//Autogenerate Wall Gates
		for (Block wallsBlock : getVanillaWalls()) {
			//Find block ID
			String block = findVanillaBlockID(wallsBlock);
			//If the block provided isn't a wall block, add the wall tag.
			if (!block.contains("wall")) {
				block = block + "_wall";
			}
			//Create block.
			createPyriteBlock(block + "_gate","fence_gate", wallsBlock, platform);
		}
		register(platform);

	}




}