package cc.cassian.pyrite.functions.fabric;

import cc.cassian.pyrite.blocks.*;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.HangingSignItem;
import net.minecraft.item.Item;
import net.minecraft.item.SignItem;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

import static cc.cassian.pyrite.Pyrite.LOGGER;
import static cc.cassian.pyrite.Pyrite.modID;
import static cc.cassian.pyrite.functions.ModHelpers.identifier;
import static cc.cassian.pyrite.functions.fabric.FabricHelpers.*;

@SuppressWarnings("unused")
public class BlockCreatorImpl {
    // All blocks and their IDs.
    public static final ArrayList<Block> BLOCKS = new ArrayList<>();
    public static final ArrayList<String> BLOCK_IDS = new ArrayList<>();
    // All blocks without block items and their IDs.
    public static final ArrayList<Block> BLOCKS_ITEMLESS = new ArrayList<>();
    public static final ArrayList<String> BLOCK_IDS_ITEMLESS = new ArrayList<>();
    // All items and their IDs.
    public static final ArrayList<Item> ITEMS = new ArrayList<>();
    public static final ArrayList<String> ITEM_IDS = new ArrayList<>();

    /**
     * Implements {@link cc.cassian.pyrite.functions.BlockCreator#createWoodType(String, BlockSetType)} on Fabric.
     */
    public static WoodType createWoodType(String blockID, BlockSetType setType) {
        return WoodTypeBuilder.copyOf(WoodType.OAK).register(identifier(blockID), setType);
    }

    /**
     * Implements {@link cc.cassian.pyrite.functions.BlockCreator#platfomRegister(String, String, AbstractBlock.Settings, WoodType, BlockSetType, ParticleEffect, Block)} on Fabric.
     */
    public static void platfomRegister(String blockID, String blockType, AbstractBlock.Settings blockSettings, WoodType woodType, BlockSetType blockSetType, ParticleEffect particle, Block copyBlock) {
        int power;
        if (blockID.contains("redstone")) power = 15;
        else power = 0;
        switch (blockType.toLowerCase()) {
            case "block":
                BLOCKS.add(new ModBlock(blockSettings, power));
                BLOCK_IDS.add(blockID);
                break;
            case "crafting":
                AbstractBlock.Settings craftingSettings;
                boolean burnable;
                // If block is not composed of flammable wood, make it burnable.
                if (!(blockID.contains("crimson") || blockID.contains("warped"))) {
                    craftingSettings = blockSettings.burnable();
                    burnable = true;
                }
                else {
                    craftingSettings = blockSettings;
                    burnable = false;
                }
                // Register Crafting table.
                ModCraftingTable block = new ModCraftingTable(craftingSettings);
                BLOCKS.add(block);
                BLOCK_IDS.add(blockID);
                // If block is not composed of flammable wood, make it furnace fuel.
                if (burnable)
                    FUEL_BLOCKS.put(block, 300);
                break;
            case "ladder":
                BLOCKS.add(new LadderBlock(blockSettings));
                BLOCK_IDS.add(blockID);
                addTransparentBlock();
                break;
            case "carpet":
                BLOCKS.add(new ModCarpet(blockSettings));
                BLOCK_IDS.add(blockID);
                break;
            case "slab":
                BLOCKS.add(new ModSlab(blockSettings, power));
                BLOCK_IDS.add(blockID);
                break;
            case "stairs":
                BLOCKS.add(new ModStairs(copyBlock.getDefaultState(), blockSettings));
                BLOCK_IDS.add(blockID);
                break;
            case "wall":
                BLOCKS.add(new ModWall(blockSettings, power));
                BLOCK_IDS.add(blockID);
                break;
            case "fence":
                BLOCKS.add(new FenceBlock(blockSettings));
                BLOCK_IDS.add(blockID);
                break;
            case "log":
                BLOCKS.add(new ModPillar(blockSettings, power));
                BLOCK_IDS.add(blockID);
                break;
            case "facing":
                BLOCKS.add(new ModFacingBlock(blockSettings, power));
                BLOCK_IDS.add(blockID);
                break;
            case "bars", "glass_pane":
                BLOCKS.add(new ModPane(blockSettings, power));
                BLOCK_IDS.add(blockID);
                addTransparentBlock();
                break;
            case "tinted_glass_pane":
                BLOCKS.add(new ModPane(blockSettings, power));
                BLOCK_IDS.add(blockID);
                addTranslucentBlock();
                break;
            case "glass":
                BLOCKS.add(new ModGlass(blockSettings));
                BLOCK_IDS.add(blockID);
                addTransparentBlock();
                break;
            case "tinted_glass":
                BLOCKS.add(new ModGlass(blockSettings));
                BLOCK_IDS.add(blockID);
                addTranslucentBlock();
                break;
            case "gravel":
                BLOCKS.add(new FallingBlock(blockSettings) {
                    @Override
                    protected MapCodec<? extends FallingBlock> getCodec() {
                        return null;
                    }
                });
                BLOCK_IDS.add(blockID);
                break;
            case "flower":
                BLOCKS.add(new FlowerBlock(StatusEffects.NIGHT_VISION, 5, blockSettings));
                BLOCK_IDS.add(blockID);
                addTransparentBlock();
                break;
            case "fence_gate", "wall_gate":
                BLOCKS.add(new FenceGateBlock(woodType, blockSettings));
                BLOCK_IDS.add(blockID);
                break;
            case "sign":
                //Sign Blocks
                final SignBlock SIGN = new SignBlock(woodType, blockSettings);
                BLOCKS_ITEMLESS.add(SIGN);
                BLOCK_IDS_ITEMLESS.add(blockID);
                //Wall Sign Blocks
                final WallSignBlock WALL_SIGN = new WallSignBlock(woodType, blockSettings);
                BLOCKS_ITEMLESS.add(WALL_SIGN);
                BLOCK_IDS_ITEMLESS.add(blockID.replace("_sign", "_wall_sign"));
                // Register item for signs.
                ITEMS.add(new SignItem(new Item.Settings().maxCount(16), SIGN, WALL_SIGN));
                ITEM_IDS.add(blockID);
                BlockEntityType.SIGN.addSupportedBlock(SIGN);
                BlockEntityType.SIGN.addSupportedBlock(WALL_SIGN);
                break;
            case "hanging_sign":
                //Sign Blocks
                final HangingSignBlock HANGING_SIGN = new HangingSignBlock(woodType, blockSettings);
                BLOCKS_ITEMLESS.add(HANGING_SIGN);
                BLOCK_IDS_ITEMLESS.add(blockID);
                //Wall Sign Blocks
                final WallHangingSignBlock HANGING_WALL_SIGN = new WallHangingSignBlock(woodType, blockSettings);
                BLOCKS_ITEMLESS.add(HANGING_WALL_SIGN);
                BLOCK_IDS_ITEMLESS.add(blockID.replace("_sign", "_wall_sign"));
                // Register item for signs.
                ITEMS.add(new HangingSignItem(HANGING_SIGN, HANGING_WALL_SIGN, new Item.Settings().maxCount(16)));
                ITEM_IDS.add(blockID);
                BlockEntityType.HANGING_SIGN.addSupportedBlock(HANGING_SIGN);
                BlockEntityType.HANGING_SIGN.addSupportedBlock(HANGING_WALL_SIGN);
                break;
            case "door":
                BLOCKS.add(new DoorBlock(blockSetType, blockSettings.nonOpaque()));
                BLOCK_IDS.add(blockID);
                addTransparentBlock();
                break;
            case "trapdoor":
                BLOCKS.add(new TrapdoorBlock(blockSetType, blockSettings.nonOpaque()));
                BLOCK_IDS.add(blockID);
                addTransparentBlock();
                break;
            case "button":
                BLOCKS.add(new ModWoodenButton(blockSettings, blockSetType));
                BLOCK_IDS.add(blockID);
                break;
            case "pressure_plate":
                BLOCKS.add(new ModPressurePlate(blockSettings, blockSetType));
                BLOCK_IDS.add(blockID);
                break;
            case "torch":
                if (particle == null)
                    BLOCKS.add(new ModTorch(blockSettings.nonOpaque(), ParticleTypes.FLAME));
                else
                    BLOCKS.add(new ModTorch(blockSettings.nonOpaque(), particle));
                BLOCK_IDS.add(blockID);
                addTransparentBlock();
                break;
            case "torch_lever":
                BLOCKS.add(new TorchLever(blockSettings.nonOpaque(), particle));
                BLOCK_IDS.add(blockID);
                addTransparentBlock();
                break;
            case "concrete_powder":
                BLOCKS.add(new ConcretePowderBlock(getLastBlock(), blockSettings));
                BLOCK_IDS.add(BLOCK_IDS.indexOf(blockID.replace("_powder", "")), blockID);
                break;
            case "switchable_glass":
                BLOCKS.add(new SwitchableGlass(blockSettings));
                BLOCK_IDS.add(blockID);
                addTranslucentBlock();
                break;
            default:
                LOGGER.error("{}created as a generic block, block provided{}", blockID, blockType);
                BLOCKS.add(new Block(blockSettings));
                BLOCK_IDS.add(blockID);
                break;
        }
        if (blockID.contains("grass")) {
            addGrassBlock();
        }
    }

    /**
     * Implements {@link cc.cassian.pyrite.functions.BlockCreator#registerPyriteItem(String)} on Fabric.
     * This registers a basic item with no additional settings - primarily used for Dye.
     */
    public static void registerPyriteItem(String itemID) {
        ITEMS.add(new Item(new Item.Settings()));
        ITEM_IDS.add(itemID);
    }

    public static void register() {
        //Register blocks and block items.
        for (int x = 0; x < BLOCK_IDS.size(); x++) {
            Registry.register(Registries.BLOCK, identifier(BLOCK_IDS.get(x)), BLOCKS.get(x));
            Registry.register(Registries.ITEM, identifier(BLOCK_IDS.get(x)), new BlockItem(BLOCKS.get(x), new Item.Settings()));
        }
        //Registers blocks without block items.
        for (int x = 0; x < BLOCK_IDS_ITEMLESS.size(); x++) {
            Registry.register(Registries.BLOCK, identifier(BLOCK_IDS_ITEMLESS.get(x)), BLOCKS_ITEMLESS.get(x));
        }
        //Registers items.
        for (int x = 0; x < ITEM_IDS.size(); x++) {
            Registry.register(Registries.ITEM, identifier(ITEM_IDS.get(x)), ITEMS.get(x));
        }
        Registry.register(Registries.ITEM_GROUP, Identifier.of(modID, "pyrite_group"), PYRITE_GROUP);
    }
}
