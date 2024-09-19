package cc.cassian.pyrite.functions;

import cc.cassian.pyrite.blocks.*;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Map;
import java.util.Objects;

import static cc.cassian.pyrite.Pyrite.LOGGER;
import static cc.cassian.pyrite.Pyrite.modID;
import static cc.cassian.pyrite.functions.ModHelpers.*;
import static cc.cassian.pyrite.functions.ModLists.*;
import static cc.cassian.pyrite.functions.architectury.ArchitecturyHelpers.*;
import static cc.cassian.pyrite.functions.architectury.ArchitecturyHelpers.newItem;

public class BlockCreator {
    final static Block[] vanillaWood = getVanillaWood();
    final static Block[] resource_blocks = getVanillaResourceBlocks();

    public static RegistrySupplier<Block> creativeTabIcon;
    //Deferred registry entries
    public static final DeferredRegister<Block> pyriteBlocks = DeferredRegister.create(modID, Registry.BLOCK_KEY);
    public static final DeferredRegister<Item> pyriteItems = DeferredRegister.create(modID, Registry.ITEM_KEY);

    @SuppressWarnings("unused")
    public static void platfomRegister(String blockID, String blockType, AbstractBlock.Settings blockSettings, ParticleEffect particle, Block copyBlock) {
        int power;
        if (blockID.contains("redstone")) power = 15;
        else power = 0;
        RegistrySupplier<Block> newBlock = null;
        switch (blockType.toLowerCase()) {
            case "block":
                newBlock = pyriteBlocks.register(blockID, () -> new ModBlock(blockSettings, power));
                break;
            case "crafting":
                newBlock = pyriteBlocks.register(blockID, () -> new ModCraftingTable(blockSettings));
                if (!(blockID.contains("crimson") || blockID.contains("warped"))) {
                    fuel.put(newBlock, 300);
                }
                break;
            case "ladder":
                newBlock = pyriteBlocks.register(blockID, () -> new LadderBlock(blockSettings));
                addTransparentBlock(newBlock);
                break;
            case "carpet":
                newBlock = pyriteBlocks.register(blockID, () -> new ModCarpet(blockSettings));
                break;
            case "slab":
                newBlock = pyriteBlocks.register(blockID, () -> new ModSlab(blockSettings, power));
                break;
            case "stairs":
                newBlock = pyriteBlocks.register(blockID, () -> new ModStairs(copyBlock.getDefaultState(), blockSettings));
                break;
            case "wall":
                newBlock = pyriteBlocks.register(blockID, () -> new ModWall(blockSettings, power));
                break;
            case "fence":
                newBlock = pyriteBlocks.register(blockID, () -> new FenceBlock(blockSettings));
                break;
            case "fence_gate", "wall_gate":
                newBlock = pyriteBlocks.register(blockID, () -> new FenceGateBlock(blockSettings));
                break;
            case "log":
                newBlock = pyriteBlocks.register(blockID, () -> new ModPillar(blockSettings, power));
                break;
            case "facing":
                newBlock = pyriteBlocks.register(blockID, () -> new ModFacingBlock(blockSettings, power));
                break;
            case "bars", "glass_pane":
                newBlock = pyriteBlocks.register(blockID, () -> new ModPane(blockSettings, power));
                addTransparentBlock(newBlock);
                break;
            case "tinted_glass_pane":
                newBlock = pyriteBlocks.register(blockID, () -> new ModPane(blockSettings, power));
                addTranslucentBlock(newBlock);
                break;
            case "glass":
                newBlock = pyriteBlocks.register(blockID, () -> new ModGlass(blockSettings));
                addTransparentBlock(newBlock);
                break;
            case "tinted_glass":
                newBlock = pyriteBlocks.register(blockID, () -> new ModGlass(blockSettings));
                break;
            case "gravel":
                newBlock = pyriteBlocks.register(blockID, () -> new GravelBlock(blockSettings));
                break;
            case "flower":
                newBlock = pyriteBlocks.register(blockID, () -> new FlowerBlock(StatusEffects.NIGHT_VISION, 5, blockSettings));
                addTransparentBlock(newBlock);
                break;
            case "sign":
//                newBlock = pyriteBlocks.register(blockID, () -> new SignBlock(blockSettings, woodType));
                break;
            case "door":
                newBlock = pyriteBlocks.register(blockID, () -> new DoorBlock(blockSettings.nonOpaque()));
                addTransparentBlock(newBlock);
                break;
            case "trapdoor":
                newBlock = pyriteBlocks.register(blockID, () -> new TrapdoorBlock(blockSettings.nonOpaque()));
                addTransparentBlock(newBlock);
                break;
            case "button":
                newBlock = pyriteBlocks.register(blockID, () -> new ModWoodenButton(blockSettings));
                break;
            case "pressure_plate":
                newBlock = pyriteBlocks.register(blockID, () -> new ModPressurePlate(blockSettings));
                break;
            case "torch":
                if (particle == null)
                    newBlock = pyriteBlocks.register(blockID, () -> new ModTorch(blockSettings.nonOpaque(), ParticleTypes.FLAME));
                else
                    newBlock = pyriteBlocks.register(blockID, () -> new ModTorch(blockSettings.nonOpaque(), particle));
                addTransparentBlock(newBlock);
                break;
            case "torch_lever":
                newBlock = pyriteBlocks.register(blockID, () -> new TorchLever(blockSettings.nonOpaque(), particle));
                addTransparentBlock(newBlock);
                break;
            default:
                LOGGER.error("{}created as a generic block, block provided{}", blockID, blockType);
                newBlock = pyriteBlocks.register(blockID, () -> new Block(blockSettings));
                break;
        }
        addBlockItem(newBlock);
        if (blockID.contains("grass")) addGrassBlock(newBlock);
        else if (blockID.equals("cobblestone_bricks")) creativeTabIcon = newBlock;
    }

    public static void addBlockItem(RegistrySupplier<Block> newBlock) {
        pyriteItems.register(newBlock.getId(), () -> new BlockItem(newBlock.get(), newItem(PYRITE_GROUP)));
    }
    public static final ItemGroup PYRITE_GROUP = CreativeTabRegistry.create(
            Identifier.of("pyrite", "group"), // Tab ID
            () -> new ItemStack(creativeTabIcon.get()) // Icon
    );


    //Create and add Pyrite items.
    @SuppressWarnings("unused")
    public static void registerPyriteItem(String itemID) {
        pyriteItems.register(itemID, () -> (new Item(newItem(PYRITE_GROUP))));
    }

    @SuppressWarnings("unused")
    public static void register() {
        pyriteBlocks.register();
        pyriteItems.register();
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
        AbstractBlock.Settings settings = AbstractBlock.Settings.of(Material.GLASS).strength(strength).luminance(state -> lightLevel).mapColor(color);
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
        platfomRegister(blockID, blockType, copyBlock(copyBlock), null, copyBlock);
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
        platfomRegister(blockID, blockType, blockSettings, null, null);

    }

    private static void sendToRegistry(String blockID, String blockType, AbstractBlock.Settings blockSettings) {
        platfomRegister(blockID, blockType, blockSettings, null, null);

    }
    private static void sendToRegistry(String blockID, Block copyBlock, AbstractBlock.Settings blockSettings) {
        platfomRegister(blockID, "stairs", blockSettings, null, copyBlock);
    }
    
    //Add blocks with particles - Torches/Torch Levers
    private static void sendToRegistry(String blockID, String blockType, AbstractBlock.Settings blockSettings, ParticleEffect particle) {
        platfomRegister(blockID, blockType, blockSettings, particle, null);

    }

    //Create most of the generic Stained Blocks, then add them.
    public static void createPyriteBlock(String blockID, String blockType, Block copyBlock, MapColor color, int lux) {
        AbstractBlock.Settings blockSettings = copyBlock(copyBlock).mapColor(color).luminance(parseLux(lux));
        platfomRegister(blockID, blockType, blockSettings, null, copyBlock);
    }

    //Create basic blocks.
    public static void createPyriteBlock(String blockID, Block copyBlock) {
        AbstractBlock.Settings blockSettings = copyBlock(copyBlock);
        platfomRegister(blockID, "block", blockSettings, null, null);

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
        //Stained Planks
        createPyriteBlock( blockID+"_planks", "block", Blocks.OAK_PLANKS, color, blockLux);
        //Stained Stairs
        createPyriteBlock(blockID+"_stairs", "stairs",Blocks.OAK_STAIRS, color, blockLux);
        //Stained Slabs
        createPyriteBlock( blockID+"_slab", "slab", Blocks.OAK_SLAB, color, blockLux);
        //Stained Pressure Plates
        createPyriteBlock( blockID+"_pressure_plate", "pressure_plate", Blocks.OAK_PRESSURE_PLATE, color, blockLux);
        //Stained Buttons
        createPyriteBlock(blockID+"_button", "button", Blocks.OAK_BUTTON, color, blockLux);
        //Stained Fences
        createPyriteBlock(blockID+"_fence", "fence", Blocks.OAK_FENCE, color, blockLux);
        //Stained Fence Gates
        createPyriteBlock(blockID+"_fence_gate", "fence_gate", Blocks.OAK_FENCE_GATE, color, blockLux);
        //Stained Doors
        createPyriteBlock(blockID+"_door", "door", Blocks.OAK_DOOR, color, blockLux);
        //Stained Trapdoors
        createPyriteBlock(blockID+"_trapdoor", "trapdoor", Blocks.OAK_TRAPDOOR, color, blockLux);
        //Crafting Tables
        createPyriteBlock( blockID+"_crafting_table", "crafting", Blocks.CRAFTING_TABLE, color, blockLux);
        createPyriteBlock( blockID+"_ladder", "ladder", Blocks.LADDER, color, blockLux);
//        createPyriteBlock(blockID+"_sign", "sign", Blocks.OAK_SIGN, color, blockLux);



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
            createPyriteBlock("chiseled_"+blockID+"_block", "log", block);
            //Pillar Blocks
            createPyriteBlock(blockID+"_pillar", "log", block);
        }
        //Smooth Blocks
        createSmoothBlocks(blockID, block);
        createPyriteBlock("nostalgia_"+blockID+"_block", block);
        //Block set for modded blocks
        //Create Bars/Doors/Trapdoors/Plates for those that don't already exist (Iron)
        if (!Objects.equals(blockID, "iron")) {
            //Bars
            createPyriteBlock(blockID+"_bars","bars", block);
            createPyriteBlock(blockID+"_door","door", block);
            createPyriteBlock(blockID+"_trapdoor","trapdoor", block);
            //Create Plates for those that don't already exist (Iron and Gold)
            if (!Objects.equals(blockID, "gold")) {
                createPyriteBlock(blockID+"_pressure_plate","pressure_plate", block);
            }
        }
        //Create buttons for all blocks.
        createPyriteBlock(blockID+"_button","button", block);
    }

}
