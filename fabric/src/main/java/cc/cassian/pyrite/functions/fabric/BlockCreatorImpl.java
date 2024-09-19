package cc.cassian.pyrite.functions.fabric;

import cc.cassian.pyrite.blocks.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

import static cc.cassian.pyrite.Pyrite.LOGGER;
import static cc.cassian.pyrite.functions.ModHelpers.identifier;
import static cc.cassian.pyrite.functions.fabric.FabricHelpers.*;

public class BlockCreatorImpl {
    public static ArrayList<Block> pyriteBlocks = new ArrayList<>();
    public static ArrayList<Block> pyriteItemlessBlocks = new ArrayList<>();
    public static ArrayList<Item> pyriteItems = new ArrayList<>();
    public static ArrayList<String> pyriteBlockIDs = new ArrayList<>();
    public static ArrayList<String> pyriteItemlessBlockIDs = new ArrayList<>();
    public static ArrayList<String> pyriteItemIDs = new ArrayList<>();

    @SuppressWarnings("unused")
    public static void platfomRegister(String blockID, String blockType, AbstractBlock.Settings blockSettings, WoodType woodType, BlockSetType blockSetType, ParticleEffect particle, Block copyBlock) {
        int power;
        if (blockID.contains("redstone")) power = 15;
        else power = 0;
        switch (blockType.toLowerCase()) {
            case "block":
                pyriteBlocks.add(new ModBlock(blockSettings, power));
                pyriteBlockIDs.add(blockID);
                break;
            case "crafting":
                pyriteBlocks.add(new ModCraftingTable(blockSettings));
                pyriteBlockIDs.add(blockID);
                if (!(blockID.contains("crimson") || blockID.contains("warped"))) {
                    fuel.put(getLastBlock(), 300);
                }
                break;
            case "ladder":
                pyriteBlocks.add(new LadderBlock(blockSettings));
                pyriteBlockIDs.add(blockID);
                addTransparentBlock();
                break;
            case "carpet":
                pyriteBlocks.add(new ModCarpet(blockSettings));
                pyriteBlockIDs.add(blockID);
                break;
            case "slab":
                pyriteBlocks.add(new ModSlab(blockSettings, power));
                pyriteBlockIDs.add(blockID);
                break;
            case "stairs":
                pyriteBlocks.add(new ModStairs(copyBlock.getDefaultState(), blockSettings));
                pyriteBlockIDs.add(blockID);
                break;
            case "wall":
                pyriteBlocks.add(new ModWall(blockSettings, power));
                pyriteBlockIDs.add(blockID);
                break;
            case "fence":
                pyriteBlocks.add(new FenceBlock(blockSettings));
                pyriteBlockIDs.add(blockID);
                break;
            case "log":
                pyriteBlocks.add(new ModPillar(blockSettings, power));
                pyriteBlockIDs.add(blockID);
                break;
            case "facing":
                pyriteBlocks.add(new ModFacingBlock(blockSettings, power));
                pyriteBlockIDs.add(blockID);
                break;
            case "bars", "glass_pane":
                pyriteBlocks.add(new ModPane(blockSettings, power));
                pyriteBlockIDs.add(blockID);
                addTransparentBlock();
                break;
            case "tinted_glass_pane":
                pyriteBlocks.add(new ModPane(blockSettings, power));
                pyriteBlockIDs.add(blockID);
                addTranslucentBlock();
                break;
            case "glass":
                pyriteBlocks.add(new ModGlass(blockSettings));
                pyriteBlockIDs.add(blockID);
                addTransparentBlock();
                break;
            case "tinted_glass":
                pyriteBlocks.add(new ModGlass(blockSettings));
                pyriteBlockIDs.add(blockID);
                addTranslucentBlock();
                break;
            case "gravel":
                pyriteBlocks.add(new GravelBlock(blockSettings));
                pyriteBlockIDs.add(blockID);
                break;
            case "flower":
                pyriteBlocks.add(new FlowerBlock(StatusEffects.NIGHT_VISION, 5, blockSettings));
                pyriteBlockIDs.add(blockID);
                addTransparentBlock();
                break;
            case "fence_gate", "wall_gate":
                pyriteBlocks.add(new FenceGateBlock(blockSettings, woodType));
                pyriteBlockIDs.add(blockID);
                break;
            case "sign":
                //Sign Blocks
                pyriteItemlessBlocks.add(new SignBlock(blockSettings, woodType) {
                    public ModSign createBlockEntity(BlockPos pos, BlockState state) {
                        return new ModSign(pos, state);
                    }
                });
                pyriteItemlessBlockIDs.add(blockID);
                //Wall Sign Blocks
                pyriteItemlessBlocks.add(new WallSignBlock(blockSettings, woodType) {
                    public ModSign createBlockEntity(BlockPos pos, BlockState state) {
                        return new ModSign(pos, state);
                    }
                });
                pyriteItemlessBlockIDs.add(blockID + "_wall");
                //Register block entity for standard signs.
                registerSignBlockEntity(pyriteItemlessBlocks.get(pyriteItemlessBlocks.size()-2), pyriteItemlessBlocks.get(pyriteItemlessBlockIDs.size()-1));
                break;
            case "door":
                pyriteBlocks.add(new DoorBlock(blockSettings.nonOpaque(), blockSetType));
                pyriteBlockIDs.add(blockID);
                addTransparentBlock();
                break;
            case "trapdoor":
                pyriteBlocks.add(new TrapdoorBlock(blockSettings.nonOpaque(), blockSetType));
                pyriteBlockIDs.add(blockID);
                addTransparentBlock();
                break;
            case "button":
                pyriteBlocks.add(new ModWoodenButton(blockSettings, blockSetType));
                pyriteBlockIDs.add(blockID);
                break;
            case "pressure_plate":
                pyriteBlocks.add(new ModPressurePlate(blockSettings, blockSetType));
                pyriteBlockIDs.add(blockID);
                break;
            case "torch":
                if (particle == null)
                    pyriteBlocks.add(new ModTorch(blockSettings.nonOpaque(), ParticleTypes.FLAME));
                else
                    pyriteBlocks.add(new ModTorch(blockSettings.nonOpaque(), particle));
                pyriteBlockIDs.add(blockID);
                addTransparentBlock();
                break;
            case "torch_lever":
                pyriteBlocks.add(new TorchLever(blockSettings.nonOpaque(), particle));
                pyriteBlockIDs.add(blockID);
                addTransparentBlock();
                break;
            default:
                LOGGER.error("{}created as a generic block, block provided{}", blockID, blockType);
                pyriteBlocks.add(new Block(blockSettings));
                pyriteBlockIDs.add(blockID);
                break;
        }
        if (blockID.contains("grass")) {
            addGrassBlock();
        }
    }


    //Create and add Pyrite items.
    @SuppressWarnings("unused")
    public static void registerPyriteItem(String itemID) {
        pyriteItems.add(new Item(new Item.Settings()));
        pyriteItemIDs.add(itemID);
    }

    @SuppressWarnings("unused")
    public static void register() {
        //Register blocks and block items.
        for (int x = 0; x < pyriteBlockIDs.size(); x++) {
            Registry.register(Registries.BLOCK, identifier(pyriteBlockIDs.get(x)), pyriteBlocks.get(x));
            Registry.register(Registries.ITEM, identifier(pyriteBlockIDs.get(x)), new BlockItem(pyriteBlocks.get(x), new Item.Settings()));
        }
        //Registers blocks without block items.
        for (int x = 0; x < pyriteItemlessBlockIDs.size(); x++) {
            Registry.register(Registries.BLOCK, identifier(pyriteItemlessBlockIDs.get(x)), pyriteItemlessBlocks.get(x));
        }
        //Registers items.
        for (int x = 0; x < pyriteItemIDs.size(); x++) {
            Registry.register(Registries.ITEM, identifier(pyriteItemIDs.get(x)), pyriteItems.get(x));
        }
    }

    public static BlockEntityType<ModSign> registerSignBlockEntity(Block sign, Block wall_sign) {
        return FabricBlockEntityTypeBuilder.create(ModSign::new, sign, wall_sign).build();

    }
}
