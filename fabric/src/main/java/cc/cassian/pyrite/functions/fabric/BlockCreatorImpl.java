package cc.cassian.pyrite.functions.fabric;

import cc.cassian.pyrite.blocks.*;
import cc.cassian.pyrite.functions.BlockCreator;
import cc.cassian.pyrite.functions.ModHelpers;
import cc.cassian.pyrite.functions.ModLists;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static cc.cassian.pyrite.Pyrite.LOGGER;
import static cc.cassian.pyrite.Pyrite.MOD_ID;
import static cc.cassian.pyrite.functions.ModHelpers.*;
import static cc.cassian.pyrite.functions.ModLists.getDyes;
import static cc.cassian.pyrite.functions.ModLists.getVanillaResourceBlocks;
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
    // Sublists for Item Groups
    public static final ArrayList<Object> WOOD_BLOCKS = new ArrayList<>();
    public static final ArrayList<Object> RESOURCE_BLOCKS = new ArrayList<>();
    public static final ArrayList<Object> BRICK_BLOCKS = new ArrayList<>();
    public static final ArrayList<Object> REDSTONE_BLOCKS = new ArrayList<>();
    public static final ArrayList<Object> MISC_BLOCKS = new ArrayList<>();
    public static final ArrayList<Item> SIGNS = new ArrayList<>();
    public static final ArrayList<Block> CRAFTING_TABLES = new ArrayList<>();
    public static final ArrayList<Block> FLOWERS = new ArrayList<>();
    public static final ArrayList<Item> DYES = new ArrayList<>();
    public static final ArrayList<Block> IRON_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> GOLD_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> EMERALD_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> LAPIS_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> REDSTONE_RESOURCE_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> DIAMOND_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> NETHERITE_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> QUARTZ_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> AMETHYST_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> COPPER_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> EXPOSED_COPPER_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> WEATHERED_COPPER_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> OXIDIZED_COPPER_BLOCKS = new ArrayList<>();


    /**
     * Implements {@link BlockCreator#createWoodType(String, BlockSetType)} on Fabric.
     */
    public static WoodType createWoodType(String blockID, BlockSetType setType) {
        return WoodTypeBuilder.copyOf(WoodType.OAK).register(identifier(blockID), setType);
    }

    /**
     * Implements {@link BlockCreator#platformRegister(String, String, AbstractBlock.Settings, WoodType, BlockSetType, ParticleEffect, Block, String)} on Fabric.
     */
    public static void platformRegister(String blockID, String blockType, AbstractBlock.Settings blockSettings, WoodType woodType, BlockSetType blockSetType, ParticleEffect particle, Block copyBlock, String group) {
        int index = -1;
        int power;
        if (blockID.contains("redstone")) power = 15;
        else power = 0;
        Block newBlock;
        Block woodBlock;
        boolean isWood = blockID.contains("_stained") || blockID.contains("mushroom") || blockID.contains("azalea");
        switch (blockType.toLowerCase()) {
            case "block":
                newBlock = new ModBlock(blockSettings, power);
                if (power == 15)
                    if (!blockID.equals("lit_redstone_lamp"))
                        REDSTONE_BLOCKS.add(newBlock);
                    else
                        MISC_BLOCKS.add(newBlock);
                if (Objects.equals(copyBlock, Blocks.OAK_PLANKS))
                    WOOD_BLOCKS.add(newBlock);
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
                newBlock = new ModCraftingTable(craftingSettings);
                CRAFTING_TABLES.add(newBlock);
                // If block is not composed of flammable wood, make it furnace fuel.
                if (burnable)
                    FUEL_BLOCKS.put(newBlock, 300);
                break;
            case "ladder":
                newBlock = new LadderBlock(blockSettings);
                WOOD_BLOCKS.add(newBlock);
                addTransparentBlock(newBlock);
                break;
            case "carpet":
                newBlock = new ModCarpet(blockSettings);
                break;
            case "slab":
                newBlock = new ModSlab(blockSettings, power);
                if (Objects.equals(copyBlock, Blocks.OAK_SLAB))
                    WOOD_BLOCKS.add(newBlock);
                if (power == 15)
                    REDSTONE_BLOCKS.add(newBlock);
                break;
            case "stairs":
                newBlock = new ModStairs(copyBlock.getDefaultState(), blockSettings);
                if (Objects.equals(copyBlock, Blocks.OAK_STAIRS))
                    WOOD_BLOCKS.add(newBlock);
                break;
            case "wall":
                newBlock = new ModWall(blockSettings, power);
                if (power == 15)
                    REDSTONE_BLOCKS.add(newBlock);
                break;
            case "fence":
                newBlock = new FenceBlock(blockSettings);
                WOOD_BLOCKS.add(newBlock);
                break;
            case "log":
                newBlock = new ModPillar(blockSettings, power);
                if (blockID.contains("mushroom") || blockID.contains("log"))
                    WOOD_BLOCKS.add(newBlock);
                else if (power == 15)
                    REDSTONE_BLOCKS.add(newBlock);
                break;
            case "wood":
                newBlock = new ModWood(blockSettings);
                WOOD_BLOCKS.add(newBlock);
                break;
            case "facing":
                newBlock = new ModFacingBlock(blockSettings, power);
                if (power == 15)
                    REDSTONE_BLOCKS.add(newBlock);
                break;
            case "bars", "glass_pane":
                newBlock = new ModPane(blockSettings, power);
                if (power == 15)
                    REDSTONE_BLOCKS.add(newBlock);
                addTransparentBlock(newBlock);
                break;
            case "tinted_glass_pane":
                newBlock = new ModPane(blockSettings, power);
                addTranslucentBlock(newBlock);
                break;
            case "stained_framed_glass_pane":
                newBlock = new StainedGlassPaneBlock(getDyeColorFromFramedId(blockID), blockSettings);
                addTranslucentBlock(newBlock);
                break;
            case "glass":
                newBlock = new ModGlass(blockSettings);
                addTransparentBlock(newBlock);
                break;
            case "tinted_glass":
                newBlock = new ModGlass(blockSettings);
                addTranslucentBlock(newBlock);
                break;
            case "stained_framed_glass":
                newBlock = new StainedFramedGlass(ModHelpers.getDyeColorFromFramedId(blockID), blockSettings);
                addTranslucentBlock(newBlock);
                break;
            case "gravel":
                newBlock = new GravelBlock(blockSettings);
                break;
            case "flower":
                newBlock = new FlowerBlock(StatusEffects.NIGHT_VISION, 5, blockSettings);
                addTransparentBlock(newBlock);
                FLOWERS.add(newBlock);
                break;
            case "fence_gate":
                newBlock = new FenceGateBlock(woodType, blockSettings);
                if (blockID.contains("_stained") || blockID.contains("mushroom"))
                    WOOD_BLOCKS.add(newBlock);
                break;
            case "wall_gate":
                newBlock = new WallGateBlock(blockSettings);
                break;
            case "sign":
                //Sign Blocks
                newBlock = new SignBlock(woodType, blockSettings);
                BLOCKS_ITEMLESS.add(newBlock);
                BLOCK_IDS_ITEMLESS.add(blockID);
                //Wall Sign Blocks
                final WallSignBlock WALL_SIGN = new WallSignBlock(woodType, blockSettings);
                BLOCKS_ITEMLESS.add(WALL_SIGN);
                BLOCK_IDS_ITEMLESS.add(blockID.replace("_sign", "_wall_sign"));
                // Register item for signs.
                final Item SIGN_ITEM = new SignItem(new Item.Settings().maxCount(16), newBlock, WALL_SIGN);
                ITEMS.add(SIGN_ITEM);
                ITEM_IDS.add(blockID);
                SIGNS.add(SIGNS.size(), SIGN_ITEM);
                BlockEntityType.SIGN.addSupportedBlock(newBlock);
                BlockEntityType.SIGN.addSupportedBlock(WALL_SIGN);
                break;
            case "hanging_sign":
                //Sign Blocks
                newBlock = new HangingSignBlock(woodType, blockSettings);
                BLOCKS_ITEMLESS.add(newBlock);
                BLOCK_IDS_ITEMLESS.add(blockID);
                //Wall Sign Blocks
                final WallHangingSignBlock HANGING_WALL_SIGN = new WallHangingSignBlock(woodType, blockSettings);
                BLOCKS_ITEMLESS.add(HANGING_WALL_SIGN);
                BLOCK_IDS_ITEMLESS.add(blockID.replace("_sign", "_wall_sign"));
                // Register item for signs.
                final Item HANGING_SIGN_ITEM = new HangingSignItem(newBlock, HANGING_WALL_SIGN, new Item.Settings().maxCount(16));
                ITEMS.add(HANGING_SIGN_ITEM);
                ITEM_IDS.add(blockID);
                SIGNS.add(HANGING_SIGN_ITEM);
                BlockEntityType.HANGING_SIGN.addSupportedBlock(newBlock);
                BlockEntityType.HANGING_SIGN.addSupportedBlock(HANGING_WALL_SIGN);
                break;
            case "door":
                newBlock = new DoorBlock(blockSetType, blockSettings.nonOpaque());
                addTransparentBlock(newBlock);
                if (isWood)
                    WOOD_BLOCKS.add(newBlock);
                break;
            case "trapdoor":
                newBlock = new TrapdoorBlock(blockSetType, blockSettings.nonOpaque());
                addTransparentBlock(newBlock);
                if (isWood)
                    WOOD_BLOCKS.add(newBlock);
                break;
            case "button":
                newBlock = new ModWoodenButton(blockSettings, blockSetType);
                if (isWood)
                    WOOD_BLOCKS.add(newBlock);
                break;
            case "pressure_plate":
                newBlock = new ModPressurePlate(blockSettings, blockSetType);
                if (isWood)
                    WOOD_BLOCKS.add(newBlock);
                break;
            case "torch":
                var torchParticle = particle;
                if (particle == null)
                    torchParticle = ParticleTypes.FLAME;
                newBlock = new ModTorch(blockSettings.nonOpaque(), torchParticle);
                addTransparentBlock(newBlock);
                break;
            case "torch_lever":
                newBlock = new TorchLever(blockSettings.nonOpaque(), particle);
                REDSTONE_BLOCKS.add(newBlock);
                addTransparentBlock(newBlock);
                break;
            case "concrete_powder":
                newBlock = new ConcretePowderBlock(getLastBlock(), blockSettings);
                index = BLOCK_IDS.indexOf(blockID.replace("_powder", ""));
                break;
            case "switchable_glass":
                newBlock = new SwitchableGlass(blockSettings);
                REDSTONE_BLOCKS.add(newBlock);
                addTranslucentBlock(newBlock);
                break;
            default:
                LOGGER.error("{}created as a generic block, block provided type: {}", blockID, blockType);
                newBlock = new Block(blockSettings);
                break;
        }
        if (!blockType.contains("sign")) {
            if (index == -1) {
                BLOCKS.add(newBlock);
                BLOCK_IDS.add(blockID);
            }
            else {
                BLOCKS.add(index, newBlock);
                BLOCK_IDS.add(index, blockID);
            }
        }
//        for (Block block : ModLists.getVanillaResourceBlocks()) {
//            if (blockID.contains(Registries.BLOCK.getId(block).getPath().replace("_block", "")) && !inGroup(newBlock))
//                ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register((itemGroup) -> itemGroup.addAfter(block, newBlock.asItem().getDefaultStack()));
//        }
        if (blockID.contains("brick") && !inGroup(newBlock))
            BRICK_BLOCKS.add(newBlock);
        if (blockID.contains("grass")) {
            addGrassBlock();
        }
        if (Objects.equals(copyBlock, Blocks.IRON_BLOCK))
            IRON_BLOCKS.add(newBlock);
        else if (Objects.equals(copyBlock, Blocks.GOLD_BLOCK))
            GOLD_BLOCKS.add(newBlock);
        else if (Objects.equals(copyBlock, Blocks.EMERALD_BLOCK))
            EMERALD_BLOCKS.add(newBlock);
        else if (Objects.equals(copyBlock, Blocks.LAPIS_BLOCK))
            LAPIS_BLOCKS.add(newBlock);
        else if (Objects.equals(copyBlock, Blocks.REDSTONE_BLOCK))
            REDSTONE_RESOURCE_BLOCKS.add(newBlock);
        else if (Objects.equals(copyBlock, Blocks.DIAMOND_BLOCK))
            DIAMOND_BLOCKS.add(newBlock);
        else if (Objects.equals(copyBlock, Blocks.NETHERITE_BLOCK))
            NETHERITE_BLOCKS.add(newBlock);
        else if (Objects.equals(copyBlock, Blocks.QUARTZ_BLOCK))
            QUARTZ_BLOCKS.add(newBlock);
        else if (Objects.equals(copyBlock, Blocks.AMETHYST_BLOCK))
            AMETHYST_BLOCKS.add(newBlock);
        else if (Objects.equals(copyBlock, Blocks.COPPER_BLOCK))
            COPPER_BLOCKS.add(newBlock);
        else if (Objects.equals(copyBlock, Blocks.EXPOSED_COPPER))
            EXPOSED_COPPER_BLOCKS.add(newBlock);
        else if (Objects.equals(copyBlock, Blocks.WEATHERED_COPPER))
            WEATHERED_COPPER_BLOCKS.add(newBlock);
        else if (Objects.equals(copyBlock, Blocks.OXIDIZED_COPPER))
            OXIDIZED_COPPER_BLOCKS.add(newBlock);
    }

    /**
     * Implements {@link BlockCreator#registerPyriteItem(String)} on Fabric.
     * This registers a basic item with no additional settings - primarily used for Dye.
     */
    public static void registerPyriteItem(String itemID) {
        var item = new Item(new Item.Settings());
        ITEMS.add(item);
        ITEM_IDS.add(itemID);
        DYES.add(item);
    }

    public static boolean inGroup(Object obj) {
        return WOOD_BLOCKS.contains(obj) ||
                BRICK_BLOCKS.contains(obj) ||
                RESOURCE_BLOCKS.contains(obj) ||
                REDSTONE_BLOCKS.contains(obj) ||
                MISC_BLOCKS.contains(obj) ||
                FLOWERS.contains(obj) ||
                SIGNS.contains(obj) ||
                CRAFTING_TABLES.contains(obj);
    }

    public static void addItemGroup(String id, String icon, ArrayList<Object> blocks) {
        ItemGroup group = FabricItemGroup.builder()
                .icon(() -> new ItemStack(BLOCKS.get(BLOCK_IDS.indexOf(icon))))
                .displayName(Text.translatable("itemGroup.pyrite." + id))
                .entries((context, entries) -> {
                    for (Object obj : blocks) {
                        if (obj instanceof Block block)
                            entries.add(block);
                        else if (obj instanceof Item item)
                            entries.add(item);
                    }
                })
                .build();
        Registry.register(Registries.ITEM_GROUP, Identifier.of(MOD_ID, id), group);
    }

    public static BlockItem addBlockItem(String blockID, Block block) {
        Item.Settings settings = new Item.Settings();
        if (blockID.contains("netherite"))
            settings = settings.fireproof();
        return new BlockItem(block, settings);
    }

    public static Collection<ItemStack> getCollectionList(ArrayList<?> items) {
        ArrayList<ItemStack> stacks = new ArrayList<>();
        for (Object object : items) {
            if (object instanceof Item item)
                stacks.add(item.getDefaultStack());
            else if (object instanceof Block block)
                stacks.add(block.asItem().getDefaultStack());
        }
        return stacks;
    }

    public static void register() {
        //Register blocks and block items.
        for (int x = 0; x < BLOCK_IDS.size(); x++) {
            final var block = BLOCKS.get(x);
            final var blockID = BLOCK_IDS.get(x);
            Registry.register(Registries.BLOCK, identifier(blockID), block);
            Registry.register(Registries.ITEM, identifier(blockID), addBlockItem(blockID, block));
            if (!inGroup(block))
                MISC_BLOCKS.add(block);
        }
        //Registers blocks without block items.
        for (int x = 0; x < BLOCK_IDS_ITEMLESS.size(); x++) {
            final var block = BLOCKS_ITEMLESS.get(x);
            final var blockID = BLOCK_IDS_ITEMLESS.get(x);
            Registry.register(Registries.BLOCK, identifier(blockID), block);
        }
        //Registers items.
        for (int x = 0; x < ITEM_IDS.size(); x++) {
            Item item = ITEMS.get(x);
            String itemID = ITEM_IDS.get(x);
            Registry.register(Registries.ITEM, identifier(itemID), item);
        }
        // Add Pyrite Concrete to vanilla item group.
        for (int dyeIndex = 0; dyeIndex < 15; dyeIndex++) {
            final var concrete = getDyes()[dyeIndex]+"_concrete";
            final var stairs = BLOCKS.get(BLOCK_IDS.indexOf(concrete + "_stairs"));
            final var slab = BLOCKS.get(BLOCK_IDS.indexOf(concrete + "_slab"));
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register((itemGroup) -> itemGroup.addAfter(Registries.BLOCK.get(Identifier.ofVanilla(concrete)), stairs, slab));
        }
        // Register item groups.
        addItemGroup("wood_group", "dragon_stained_crafting_table", WOOD_BLOCKS);
        addItemGroup("resource_group", "cut_emerald", RESOURCE_BLOCKS);
        addItemGroup("brick_group", "cobblestone_bricks", BRICK_BLOCKS);
        addItemGroup("pyrite_group", "glowing_obsidian", MISC_BLOCKS);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register((itemGroup) -> itemGroup.addAfter(Items.CAULDRON, getCollectionList(REDSTONE_BLOCKS)));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register((itemGroup) -> {
            itemGroup.addAfter(Items.WARPED_HANGING_SIGN, getCollectionList(SIGNS));
            itemGroup.addAfter(Items.CRAFTING_TABLE, getCollectionList(CRAFTING_TABLES));
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register((itemGroup) -> itemGroup.addAfter(Items.WITHER_ROSE, getCollectionList(FLOWERS)));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((itemGroup) -> itemGroup.addAfter(Items.PINK_DYE, getCollectionList(DYES)));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register((itemGroup) -> {
            itemGroup.addAfter(Items.IRON_BLOCK, getCollectionList(IRON_BLOCKS));
            itemGroup.addAfter(Items.GOLD_BLOCK, getCollectionList(GOLD_BLOCKS));
            itemGroup.addAfter(Items.EMERALD_BLOCK, getCollectionList(EMERALD_BLOCKS));
            itemGroup.addAfter(Items.LAPIS_BLOCK, getCollectionList(LAPIS_BLOCKS));
            itemGroup.addAfter(Items.REDSTONE_BLOCK, getCollectionList(REDSTONE_RESOURCE_BLOCKS));
            itemGroup.addAfter(Items.DIAMOND_BLOCK, getCollectionList(DIAMOND_BLOCKS));
            itemGroup.addAfter(Items.NETHERITE_BLOCK, getCollectionList(NETHERITE_BLOCKS));
            itemGroup.addAfter(Items.QUARTZ_BLOCK, getCollectionList(QUARTZ_BLOCKS));
            itemGroup.addAfter(Items.AMETHYST_BLOCK, getCollectionList(AMETHYST_BLOCKS));
            itemGroup.addAfter(Items.COPPER_BLOCK, getCollectionList(COPPER_BLOCKS));
            itemGroup.addAfter(Items.EXPOSED_COPPER, getCollectionList(EXPOSED_COPPER_BLOCKS));
            itemGroup.addAfter(Items.WEATHERED_COPPER, getCollectionList(WEATHERED_COPPER_BLOCKS));
            itemGroup.addAfter(Items.OXIDIZED_COPPER, getCollectionList(OXIDIZED_COPPER_BLOCKS));
        });



    }
}
