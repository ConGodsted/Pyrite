package cc.cassian.pyrite;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import java.util.ArrayList;
import java.util.Objects;

public class Pyrite implements ModInitializer {

    //FRAMED GLASS
    public static final Block FRAMED_GLASS = new ModGlass();
    public static final Block FRAMED_GLASS_PANE = new PaneBlock(FabricBlockSettings.create().nonOpaque().strength(2.0f).sounds(BlockSoundGroup.GLASS));

//    COBBLESTONE BRICKS

    public static final Block COBBLESTONE_BRICKS = new Block(FabricBlockSettings.copyOf(Blocks.STONE_BRICKS));
    public static final Block COBBLESTONE_BRICK_STAIRS = new ModStairs(Pyrite.COBBLESTONE_BRICKS.getDefaultState(),FabricBlockSettings.copyOf(Blocks.STONE_BRICK_STAIRS));
    public static final Block COBBLESTONE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.copyOf(Blocks.STONE_BRICK_SLAB));
    public static final Block COBBLESTONE_BRICK_WALL = new WallBlock(FabricBlockSettings.copyOf(Blocks.STONE_BRICK_WALL));

    public static final Block MOSSY_COBBLESTONE_BRICKS = new Block(FabricBlockSettings.copyOf(Blocks.MOSSY_STONE_BRICKS).strength(3.0f));
    public static final Block MOSSY_COBBLESTONE_BRICK_STAIRS = new ModStairs(Pyrite.MOSSY_COBBLESTONE_BRICKS.getDefaultState(),FabricBlockSettings.copyOf(Blocks.MOSSY_STONE_BRICK_STAIRS).strength(3.0f));
    public static final Block MOSSY_COBBLESTONE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.copyOf(Blocks.MOSSY_STONE_BRICK_SLAB).strength(3.0f));
    public static final Block MOSSY_COBBLESTONE_BRICK_WALL = new WallBlock(FabricBlockSettings.copyOf(Blocks.MOSSY_STONE_BRICK_WALL));

    public static final Block GRASS_CARPET = new CarpetBlock(FabricBlockSettings.copyOf(Blocks.MOSS_CARPET));
    public static final Block MYCELIUM_CARPET = new CarpetBlock(FabricBlockSettings.copyOf(Blocks.MOSS_CARPET));
    public static final Block PODZOL_CARPET = new CarpetBlock(FabricBlockSettings.copyOf(Blocks.MOSS_CARPET));
    public static final Block NETHER_BRICK_FENCE_GATE = new FenceGateBlock(FabricBlockSettings.copyOf(Blocks.NETHER_BRICK_FENCE), WoodType.CRIMSON  );


    static Block[] pyriteBlocks = {
            COBBLESTONE_BRICKS, COBBLESTONE_BRICK_STAIRS,COBBLESTONE_BRICK_SLAB,COBBLESTONE_BRICK_WALL,
            MOSSY_COBBLESTONE_BRICKS, MOSSY_COBBLESTONE_BRICK_STAIRS,MOSSY_COBBLESTONE_BRICK_SLAB, MOSSY_COBBLESTONE_BRICK_WALL,
            FRAMED_GLASS, FRAMED_GLASS_PANE, GRASS_CARPET, MYCELIUM_CARPET, PODZOL_CARPET, NETHER_BRICK_FENCE_GATE
    };
    String[] pyriteBlockIDs = {
            "cobblestone_bricks", "cobblestone_brick_stairs", "cobblestone_brick_slab","cobblestone_brick_wall",
            "mossy_cobblestone_bricks", "mossy_cobblestone_brick_stairs", "mossy_cobblestone_brick_slab", "mossy_cobblestone_brick_wall",
            "framed_glass", "framed_glass_pane", "grass_carpet", "mycelium_carpet", "podzol_carpet", "nether_brick_fence_gate"

    };

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
            "star"
    };

    String[] walls = {
            "cobblestone",
            "mossy_cobblestone",
            "stone_brick",
            "mossy_stone_brick",
            "granite",
            "diorite",
            "andesite",
            "cobbled_deepslate",
            "polished_deepslate",
            "deepslate_brick",
            "deepslate_tile",
            "brick",
            "mud_brick",
            "sandstone",
            "red_sandstone",
            "prismarine",
            "nether_brick",
            "red_nether_brick",
            "blackstone",
            "polished_blackstone",
            "polished_blackstone_brick",
            "end_stone_brick"

    };

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
            Blocks.MUD_BRICK_WALL,
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

    String[] generated = {
            "stained_planks",
            "stained_stairs",
            "stained_slab",
            "stained_pressure_plate",
            "stained_button",
            "stained_fence",
            "stained_fence_gate",
            "bricks"
    };


    static ArrayList<Block> generatedBlocks = new ArrayList<Block>();

    @Override
    public void onInitialize() {
        int x = 0;
        int blockLux;
        DyeColor color;
        for (String dye : dyes) {
            //Glow planks overrides
            if (Objects.equals(dye, "glow")) {
                blockLux = 8;
                color = DyeColor.GREEN;
            }
            else if (Objects.equals(dye, "dragon")) {
                blockLux = 0;
                color = DyeColor.PURPLE;
            }
            else if (Objects.equals(dye, "star")) {
                blockLux = 15;
                color = DyeColor.LIGHT_BLUE;
            }
            else {
                color = DyeColor.valueOf(dye.toUpperCase());
                blockLux = 0;
            }
            BlockSetType DYED_WOOD_SET = BlockSetTypeBuilder.copyOf(BlockSetType.CHERRY).register(new Identifier("pyrite", dye + "wood"));
            WoodType DYED_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.CHERRY).register(new Identifier("pyrite", dye + "wood"), DYED_WOOD_SET);
            //PLANKS
            Block DYE_PLANKS = new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).luminance(blockLux).mapColor(color));
            generatedBlocks.add(DYE_PLANKS);
            //STAIRS
            Block DYE_STAIRS = new ModStairs(DYE_PLANKS.getDefaultState(), FabricBlockSettings.copyOf(Blocks.OAK_STAIRS).luminance(blockLux).mapColor(color));
            generatedBlocks.add(DYE_STAIRS);
            //SLABS
            Block DYE_SLABS = new SlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_STAIRS).luminance(blockLux).mapColor(color));
            generatedBlocks.add(DYE_SLABS);
            //PRESSURE PLATES
            Block DYE_PLATES = new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.copyOf(Blocks.OAK_PRESSURE_PLATE).luminance(blockLux).mapColor(color), DYED_WOOD_SET);
            generatedBlocks.add(DYE_PLATES);
            //BUTTON
            Block DYE_BUTTONS = new ButtonBlock(FabricBlockSettings.copyOf(Blocks.OAK_BUTTON).mapColor(color).luminance(blockLux), DYED_WOOD_SET, 40, true);
            generatedBlocks.add(DYE_BUTTONS);
            //FENCE
            Block DYE_FENCE = new FenceBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE).mapColor(color).luminance(blockLux));
            generatedBlocks.add(DYE_FENCE);
            //FENCE GATES
            Block DYE_FENCE_GATE = new FenceGateBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE_GATE).mapColor(color).luminance(blockLux), DYED_WOOD_TYPE);
            generatedBlocks.add(DYE_FENCE_GATE);
            //Bricks
            Block DYE_BRICKS = new Block(FabricBlockSettings.copyOf(Blocks.BRICKS).mapColor(color));
            generatedBlocks.add(DYE_BRICKS);
            //Register
            for (String generatedID : generated) {
                Registry.register(Registries.BLOCK, new Identifier("pyrite", dye + "_" + generatedID), generatedBlocks.get(x));
                Registry.register(Registries.ITEM, new Identifier("pyrite", dye + "_" + generatedID), new BlockItem(generatedBlocks.get(x), new FabricItemSettings()));
                x = x + 1;
            }
        }

        //Wall Gates
        int w = 0;
        for (String wall : walls) {
            //WALL GATES
            Block DYE_FENCE_GATE = new FenceGateBlock(FabricBlockSettings.copyOf(walls_blocks[w]), WoodType.CRIMSON);
            generatedBlocks.add(DYE_FENCE_GATE);
            //Register
            Registry.register(Registries.BLOCK, new Identifier("pyrite", wall + "_wall_gate"), generatedBlocks.get(x));
            Registry.register(Registries.ITEM, new Identifier("pyrite", wall + "_wall_gate"), new BlockItem(generatedBlocks.get(x), new FabricItemSettings()));
            x = x + 1;
            w = w + 1;

        }

        for (int i = 0; i < pyriteBlockIDs.length; i++) {
            Registry.register(Registries.BLOCK, new Identifier("pyrite", pyriteBlockIDs[i]), pyriteBlocks[i]);
            Registry.register(Registries.ITEM, new Identifier("pyrite", pyriteBlockIDs[i]), new BlockItem(pyriteBlocks[i], new FabricItemSettings()));
        }
        Registry.register(Registries.ITEM_GROUP, new Identifier("pyrite", "pyrite_group"), PYRITE_GROUP);


        //FRAMED GLASS
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(content -> {
            content.addAfter(Items.TINTED_GLASS, FRAMED_GLASS_PANE);
            content.addAfter(Items.TINTED_GLASS, FRAMED_GLASS);


        });
    }

    private static final ItemGroup PYRITE_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(COBBLESTONE_BRICKS))
            .displayName(Text.translatable("itemGroup.pyrite.group"))
            .entries((context, entries) -> {
                for (Block block : pyriteBlocks) {
                    entries.add(block);
                }
                for (Block block : generatedBlocks) {
                    entries.add(block);
                }
            })
            .build();
}
