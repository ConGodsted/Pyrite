package cc.cassian.pyrite;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Objects;

public class Pyrite implements ModInitializer {
    //List of Blocks and Block IDS.
    public static ArrayList<Block> pyriteBlocks = new ArrayList<>();
    public static ArrayList<Item> pyriteItems = new ArrayList<>();
    public static ArrayList<Block> transparentBlocks = new ArrayList<>();
    static ArrayList<String> pyriteBlockIDs = new ArrayList<>();
    static ArrayList<String> pyriteItemIDs = new ArrayList<>();
    //List of dyes to autogenerate blocks for.
    String[] dyes = {
            "white",
            "orange",
            "magenta",
            "light_blue",
            "yellow",
            "lime",
            "pink",
            "gray",
            "light_gray",
            "cyan",
            "purple",
            "blue",
            "brown",
            "green",
            "red",
            "black",
            "glow",
            "dragon",
            "star",
            "honey",
            "nostalgia",
            "rose"
    };

    //List of Wall Blocks to generated Wall Gates for.
    Block[] walls_blocks = {
            Blocks.COBBLESTONE_WALL,
            Blocks.MOSSY_COBBLESTONE_WALL,
            Blocks.STONE_BRICK_WALL,
            Blocks.MOSSY_STONE_BRICK_WALL,
            Blocks.GRANITE_WALL,
            Blocks.DIORITE_WALL,
            Blocks.ANDESITE_WALL,
            Blocks.COBBLED_DEEPSLATE_WALL,
            Blocks.POLISHED_DEEPSLATE_WALL,
            Blocks.DEEPSLATE_BRICK_WALL,
            Blocks.DEEPSLATE_TILE_WALL,
            Blocks.BRICK_WALL,
            Blocks.SANDSTONE_WALL,
            Blocks.RED_SANDSTONE_WALL,
            Blocks.PRISMARINE_WALL,
            Blocks.NETHER_BRICK_WALL,
            Blocks.RED_NETHER_BRICK_WALL,
            Blocks.BLACKSTONE_WALL,
            Blocks.POLISHED_BLACKSTONE_WALL,
            Blocks.POLISHED_BLACKSTONE_BRICK_WALL,
            Blocks.END_STONE_BRICK_WALL

    };

    //Primarily used for Framed Glass, Glowstone/Dyed Lamps, Glowing Obsidian
    public void createPyriteBlock(String blockID, String blockType, Float strength, MapColor color, int lightLevel) {
        pyriteBlockIDs.add(blockID);
        AbstractBlock.Settings settings = AbstractBlock.Settings.of(Material.GLASS).strength(strength).luminance(state -> lightLevel).mapColor(color);
        if (Objects.equals(blockType, "block")) {
            pyriteBlocks.add(new Block(settings));
        }
        else if (Objects.equals(blockType, "glass")) {
            pyriteBlocks.add(new ModGlass(settings.nonOpaque()));
            transparentBlocks.add(pyriteBlocks.get(pyriteBlocks.size()-1));
        }
        else if (Objects.equals(blockType, "glass_pane")) {
            pyriteBlocks.add(new PaneBlock(settings.nonOpaque()));
            transparentBlocks.add(pyriteBlocks.get(pyriteBlocks.size()-1));
        }
        else if (Objects.equals(blockType, "obsidian")) {
            pyriteBlocks.add(new Block(AbstractBlock.Settings.of(Material.STONE).strength(strength).luminance(state -> lightLevel).mapColor(color).strength(strength, 1200f)));
        }

    }

    //Most of the manually generated blocks.
    public void createPyriteBlock(String blockID, String blockType, Block copyBlock) {
        pyriteBlockIDs.add(blockID);
        AbstractBlock.Settings blockSettings = AbstractBlock.Settings.copy(copyBlock);
        if (Objects.equals(blockType, "block")) {
            pyriteBlocks.add(new Block(blockSettings));
        }
        else if (Objects.equals(blockType, "log")) {
            pyriteBlocks.add(new PillarBlock(blockSettings));
        }
        else if (Objects.equals(blockType, "slab")) {
            pyriteBlocks.add(new SlabBlock(blockSettings));
        }
        else if (Objects.equals(blockType, "wall")) {
            pyriteBlocks.add(new WallBlock(blockSettings));
        }
        else if (Objects.equals(blockType, "fence_gate")) {
            pyriteBlocks.add(new FenceGateBlock(blockSettings));
        }
        else if (Objects.equals(blockType, "carpet")) {
            pyriteBlocks.add(new CarpetBlock(blockSettings));
        }

    }

    //Most of the generic Stained Blocks.
    public void createPyriteBlock(String blockID, String blockType, Block copyBlock, MapColor color, int lux) {
        pyriteBlockIDs.add(blockID);
        AbstractBlock.Settings blockSettings = AbstractBlock.Settings.copy(copyBlock).mapColor(color).luminance(state -> lux);
        if (Objects.equals(blockType, "block")) {
            pyriteBlocks.add(new Block(blockSettings));
        }
        if (Objects.equals(blockType, "carpet")) {
            pyriteBlocks.add(new CarpetBlock(blockSettings));
        }
        else if (Objects.equals(blockType, "slab")) {
            pyriteBlocks.add(new SlabBlock(blockSettings));
        }
        else if (Objects.equals(blockType, "wall")) {
            pyriteBlocks.add(new WallBlock(blockSettings));
        }
        else if (Objects.equals(blockType, "stairs")) {
            pyriteBlocks.add(new ModStairs(copyBlock.getDefaultState(), blockSettings));
        }
        else if (Objects.equals(blockType, "door")) {
            pyriteBlocks.add(new DoorBlock(blockSettings.nonOpaque()));
            transparentBlocks.add(pyriteBlocks.get(pyriteBlocks.size()-1));
        }
        else if (Objects.equals(blockType, "trapdoor")) {
            pyriteBlocks.add(new TrapdoorBlock(blockSettings.nonOpaque()));
            transparentBlocks.add(pyriteBlocks.get(pyriteBlocks.size()-1));
        }
        else if (Objects.equals(blockType, "pressure_plate")) {
            pyriteBlocks.add(new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, blockSettings));
        }
        else if (Objects.equals(blockType, "button")) {
            pyriteBlocks.add(new ModButton(blockSettings));
        }
        else if (Objects.equals(blockType, "fence_gate")) {
            pyriteBlocks.add(new FenceGateBlock(blockSettings));
        }
        else if (Objects.equals(blockType, "fence")) {
            pyriteBlocks.add(new FenceBlock(blockSettings));
        }
    }

    //Various stair blocks.
    public void createPyriteBlock(String blockID, String blockType, Block copyBlock, Integer baseBlock) {
        pyriteBlockIDs.add(blockID);
        AbstractBlock.Settings blockSettings = AbstractBlock.Settings.copy(copyBlock);
        if (Objects.equals(blockType, "stairs")) {
            pyriteBlocks.add(new ModStairs(pyriteBlocks.get(baseBlock).getDefaultState(), blockSettings));
        }

    }

    public void createPyriteItem(String itemID) {
        pyriteItems.add(new Item(new Item.Settings().group(PYRITE_GROUP)));
        pyriteItemIDs.add(itemID);
    }

    @Override
    public void onInitialize() {
        //Framed Glass - 0
        createPyriteBlock("framed_glass","glass", 2.0f, MapColor.CLEAR, 0);
        //Framed Glass Pane - 1
        createPyriteBlock( "framed_glass_pane","glass_pane", 2.0f, MapColor.CLEAR, 0);
        //Cobblestone Bricks - 2
        createPyriteBlock("cobblestone_bricks","block", Blocks.STONE_BRICKS);
        //Cobblestone Brick Stairs - 3
        createPyriteBlock("cobblestone_brick_stairs","stairs", Blocks.STONE_BRICK_STAIRS, 2);
        //Cobblestone Brick Slab - 4
        createPyriteBlock("cobblestone_brick_slab", "slab", Blocks.STONE_BRICK_SLAB);
        //Cobblestone Brick Walls - 5
        createPyriteBlock("cobblestone_brick_wall","wall", Blocks.STONE_BRICK_WALL);
        //Cobblestone Brick Wall Gate
        createPyriteBlock("cobblestone_brick_wall_gate","fence_gate", Blocks.STONE_BRICK_WALL);
        //Mossy Cobblestone Bricks - 6
        createPyriteBlock("mossy_cobblestone_bricks", "block", Blocks.MOSSY_STONE_BRICKS);
        //Mossy Cobblestone Brick Stairs - 7
        createPyriteBlock( "mossy_cobblestone_brick_stairs","stairs", Blocks.MOSSY_STONE_BRICK_STAIRS, 2);
        //Mossy Cobblestone Brick Slabs - 8
        createPyriteBlock("mossy_cobblestone_brick_slab","slab", Blocks.MOSSY_STONE_BRICK_SLAB);
        //Mossy Cobblestone Brick Walls - 9
        createPyriteBlock("mossy_cobblestone_brick_wall","wall", Blocks.MOSSY_STONE_BRICK_WALL);
        createPyriteBlock("mossy_cobblestone_brick_wall_gate","fence_gate", Blocks.MOSSY_STONE_BRICK_WALL);
        //Grass Carpet - 10
        createPyriteBlock("grass_carpet", "carpet", Blocks.MOSS_CARPET);
        //Mycelium Carpet - 11
        createPyriteBlock("mycelium_carpet", "carpet", Blocks.MOSS_CARPET);
        //Podzol Carpet - 12
        createPyriteBlock("podzol_carpet", "carpet", Blocks.MOSS_CARPET);
        //Path Carpet - 13
        createPyriteBlock("path_carpet","carpet", Blocks.MOSS_CARPET);
        //Nether Brick Fence Gate - 14
        createPyriteBlock("nether_brick_fence_gate","fence_gate", Blocks.NETHER_BRICK_FENCE);
        //Cut Iron - 15
        createPyriteBlock("cut_iron","block", Blocks.IRON_BLOCK);
        //Cut Iron Stairs - 16
        createPyriteBlock("cut_iron_stairs", "stairs", Blocks.IRON_BLOCK, 16);
        //Cut Iron Slab - 17
        createPyriteBlock("cut_iron_slab", "slab", Blocks.IRON_BLOCK);
        //Cut Iron Wall - 18
        createPyriteBlock("cut_iron_wall", "wall", Blocks.IRON_BLOCK);
        //Cut Iron Wall Gate - 19
        createPyriteBlock("cut_iron_wall_gate","fence_gate", Blocks.IRON_BLOCK);
        //Glowstone Lamp
        createPyriteBlock("glowstone_lamp","block", 0.3f, MapColor.YELLOW, 15);
        createPyriteBlock("glowing_obsidian","obsidian", 50f, MapColor.RED, 15);
        //Charred Nether Bricks
        createPyriteBlock( "charred_nether_bricks", "block", Blocks.NETHER_BRICKS, MapColor.BLACK, 0);
        //Charred Nether Bricks Stairs
        createPyriteBlock( "charred_nether_brick_stairs", "stairs", pyriteBlocks.get(pyriteBlocks.size()-1), MapColor.BLACK, 0);
        //Charred Nether Bricks Slab
        createPyriteBlock( "charred_nether_brick_slab", "slab", Blocks.NETHER_BRICK_SLAB, MapColor.BLACK, 0);
        //Charred Nether Bricks Wall
        createPyriteBlock( "charred_nether_brick_wall", "wall", Blocks.NETHER_BRICK_WALL, MapColor.BLACK, 0);

        //Red Mushroom Blocks
        createPyriteBlock("red_mushroom_stem", "log", Blocks.MUSHROOM_STEM);
        int blockLux = 0;
        MapColor color = MapColor.RED;
        //Stained Planks
        createPyriteBlock( "red_mushroom_planks", "block", Blocks.RED_MUSHROOM_BLOCK, color, blockLux);
        //Stained Stairs
        createPyriteBlock("red_mushroom_stairs", "stairs", pyriteBlocks.get(pyriteBlocks.size()-1), color, blockLux);
        //Stained Slabs
        createPyriteBlock( "red_mushroom_slab", "slab", pyriteBlocks.get(pyriteBlocks.size()-2), color, blockLux);
        //Stained Pressure Plates
        createPyriteBlock( "red_mushroom_pressure_plate", "pressure_plate", pyriteBlocks.get(pyriteBlocks.size()-3), color, blockLux);
        //Stained Buttons
        createPyriteBlock("red_mushroom_button", "button", pyriteBlocks.get(pyriteBlocks.size()-4), color, blockLux);
        //Stained Fences
        createPyriteBlock("red_mushroom_fence", "fence", pyriteBlocks.get(pyriteBlocks.size()-5), color, blockLux);
        //Stained Fence Gates
        createPyriteBlock("red_mushroom_fence_gate", "fence_gate", pyriteBlocks.get(pyriteBlocks.size()-5), color, blockLux);
        //Stained Doors
        createPyriteBlock("red_mushroom_door", "door", pyriteBlocks.get(pyriteBlocks.size()-6), color, blockLux);
        //Stained Trapdoors
        createPyriteBlock("red_mushroom_trapdoor", "trapdoor", pyriteBlocks.get(pyriteBlocks.size()-7), color, blockLux);
        createPyriteBlock("brown_mushroom_stem", "log", Blocks.MUSHROOM_STEM);

        //Stained Planks
        createPyriteBlock( "brown_mushroom_planks", "block", Blocks.BROWN_MUSHROOM_BLOCK, color, blockLux);
        //Stained Stairs
        createPyriteBlock("brown_mushroom_stairs", "stairs", pyriteBlocks.get(pyriteBlocks.size()-1), color, blockLux);
        //Stained Slabs
        createPyriteBlock( "brown_mushroom_slab", "slab", pyriteBlocks.get(pyriteBlocks.size()-2), color, blockLux);
        //Stained Pressure Plates
        createPyriteBlock( "brown_mushroom_pressure_plate", "pressure_plate", pyriteBlocks.get(pyriteBlocks.size()-3), color, blockLux);
        //Stained Buttons
        createPyriteBlock("brown_mushroom_button", "button", pyriteBlocks.get(pyriteBlocks.size()-4), color, blockLux);
        //Stained Fences
        createPyriteBlock("brown_mushroom_fence", "fence", pyriteBlocks.get(pyriteBlocks.size()-5), color, blockLux);
        //Stained Fence Gates
        createPyriteBlock("brown_mushroom_fence_gate", "fence_gate", pyriteBlocks.get(pyriteBlocks.size()-5), color, blockLux);
        //Stained Doors
        createPyriteBlock("brown_mushroom_door", "door", pyriteBlocks.get(pyriteBlocks.size()-6), color, blockLux);
        //Stained Trapdoors
        createPyriteBlock("brown_mushroom_trapdoor", "trapdoor", pyriteBlocks.get(pyriteBlocks.size()-7), color, blockLux);



        //Autogenerate dye blocks.
        for (int dyeIndex = 0; dyeIndex < dyes.length; dyeIndex++) {
            String dye = dyes[dyeIndex];
            if (dyeIndex > 15) {
                //Glow planks overrides
                if (Objects.equals(dye, "glow")) {
                    blockLux = 8;
                    color = MapColor.CYAN;
                }
                //Dragon planks overrides
                else if (Objects.equals(dye, "dragon")) {
                    color = MapColor.BLACK;
                    blockLux = 0;
                }
                //Star planks overrides
                else if (Objects.equals(dye, "star")) {
                    blockLux = 15;
                    color = MapColor.OFF_WHITE;
                }
                //Honey planks overrides
                else if (Objects.equals(dye, "honey")) {
                    color = MapColor.YELLOW;
                    blockLux = 0;

                }
                else if (Objects.equals(dye, "nostalgia")) {
                    color = MapColor.BROWN;
                    blockLux = 0;
                }
                else if (Objects.equals(dye, "rose")) {
                    color = MapColor.BRIGHT_RED;
                    blockLux = 0;

                }
                //Dye
                createPyriteItem(dye + "_dye");
                //Dyed Wool
                createPyriteBlock(dye + "_wool", "block", Blocks.WHITE_WOOL, color, blockLux);
                //Terracotta Block
                //Carpet block
                createPyriteBlock(dye + "_carpet", "carpet", Blocks.WHITE_WOOL, color, blockLux);


            }
            //Normal dye colours.
            else {
                color = DyeColor.valueOf(dye.toUpperCase()).getMapColor();
            }

            //Stained Planks
            createPyriteBlock(dye + "_stained_planks", "block", Blocks.OAK_PLANKS, color, blockLux);
            //Stained Stairs
            createPyriteBlock(dye + "_stained_stairs", "stairs", pyriteBlocks.get(pyriteBlocks.size()-1), color, blockLux);
            //Stained Slabs
            createPyriteBlock(dye + "_stained_slab", "slab", pyriteBlocks.get(pyriteBlocks.size()-2), color, blockLux);
            //Stained Pressure Plates
            createPyriteBlock(dye + "_stained_pressure_plate", "pressure_plate", pyriteBlocks.get(pyriteBlocks.size()-3), color, blockLux);
            //Stained Buttons
            createPyriteBlock(dye + "_stained_button", "button", pyriteBlocks.get(pyriteBlocks.size()-4), color, blockLux);
            //Stained Fences
            createPyriteBlock(dye + "_stained_fence", "fence", pyriteBlocks.get(pyriteBlocks.size()-5), color, blockLux);
            //Stained Fence Gates
            createPyriteBlock(dye + "_stained_fence_gate", "fence_gate", pyriteBlocks.get(pyriteBlocks.size()-5), color, blockLux);
            //Stained Doors
            createPyriteBlock(dye + "_stained_door", "door", pyriteBlocks.get(pyriteBlocks.size()-6), color, blockLux);
            //Stained Trapdoors
            createPyriteBlock(dye + "_stained_trapdoor", "trapdoor", pyriteBlocks.get(pyriteBlocks.size()-7), color, blockLux);
            //Dyed Bricks
            createPyriteBlock(dye + "_bricks", "block", Blocks.BRICKS, color, blockLux);
            //Dyed Brick Stairs
            createPyriteBlock(dye + "_brick_stairs", "stairs", pyriteBlocks.get(pyriteBlocks.size()-1), color, blockLux);
            //Dyed Brick Slab
            createPyriteBlock(dye + "_brick_slab", "slab", Blocks.BRICK_SLAB, color, blockLux);
            //Dyed Brick Wall
            createPyriteBlock(dye + "_brick_wall", "wall", Blocks.BRICK_WALL, color, blockLux);
            //Dyed Lamps
            createPyriteBlock(dye + "_lamp","block", 0.3f, color, 15);

        }
        //Autogenerate Wall Gates
        for (Block wallsBlock : walls_blocks) {
            //Find block ID
            String block = wallsBlock.toString().substring(wallsBlock.toString().indexOf(":") + 1, wallsBlock.toString().indexOf("}"));
            //If the block provided isn't a wall block, add the wall tag.
            if (!block.contains("wall")) {
                block = block + "_wall";
            }
            //Create block.
            createPyriteBlock(block + "_gate","fence_gate", wallsBlock);
        }


        //Register blocks and block items.
        for (int x = 0; x < pyriteBlockIDs.size(); x++) {
            Registry.register(Registry.BLOCK, new Identifier("pyrite", pyriteBlockIDs.get(x)), pyriteBlocks.get(x));
            Registry.register(Registry.ITEM, new Identifier("pyrite", pyriteBlockIDs.get(x)), new BlockItem(pyriteBlocks.get(x), new FabricItemSettings().group(PYRITE_GROUP)));
        }
        //Registers items.
        for (int x = 0; x < pyriteItemIDs.size(); x++) {
            Registry.register(Registry.ITEM, new Identifier("pyrite", pyriteItemIDs.get(x)), pyriteItems.get(x));
        }
    }
    public static final ItemGroup PYRITE_GROUP = FabricItemGroupBuilder.build(
            new Identifier("pyrite"),
            () -> new ItemStack(pyriteBlocks.get(0))
    );
}