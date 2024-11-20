package cc.cassian.pyrite.functions;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.SharedConstants;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.ToIntFunction;

import static cc.cassian.pyrite.Pyrite.modID;


public class ModHelpers {

    public static AbstractBlock.Settings copyBlock(Block copyBlock) {
        return AbstractBlock.Settings.copy(copyBlock);
    }

    public static ToIntFunction<BlockState> parseLux(int lux) {
        return state -> lux;
    }

    public static String findVanillaBlockID(Block block) {
        return block.toString().substring(block.toString().indexOf(":") + 1, block.toString().indexOf("}"));
    }

    public static Identifier identifier(String id) {
        return Identifier.of(modID, id);
    }

    public static RegistryKey<Block> registryKeyBlock(String id) {
        return RegistryKey.of(RegistryKeys.BLOCK, identifier(id));
    }

    public static RegistryKey<Item> registryKeyItem(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, identifier(id));
    }

    public static Item.Settings newItemSettings(String id) {
        return new Item.Settings().registryKey(registryKeyItem(id));
    }

    public static Item.Settings newBlockItemSettings(String id) {
        return newItemSettings(id).useBlockPrefixedTranslationKey();
    }

    public static MapColor checkDyeMapColour(String dye) {
        return switch (dye) {
            case "glow" -> MapColor.CYAN;
            case "dragon" -> MapColor.BLACK;
            case "star" -> MapColor.OFF_WHITE;
            case "honey" -> MapColor.YELLOW;
            case "nostalgia" -> MapColor.BROWN;
            case "rose" -> MapColor.BRIGHT_RED;
            case "poisonous" -> MapColor.LIME;
            default -> DyeColor.valueOf(dye.toUpperCase()).getMapColor();
        };
    }

    public static int checkDyeLux(String dye) {
        return switch (dye) {
            case "glow" -> 8;
            case "star" -> 15;
            default -> 0;
        };
    }

    public static ParticleEffect getTorchParticle(String dye) {
        return switch (dye) {
            case "dragon" -> ParticleTypes.DRAGON_BREATH;
            case "glow" -> ParticleTypes.GLOW;
            case "star" -> ParticleTypes.ENCHANT;
            default -> ParticleTypes.SMOKE;
        };
    }

    public static boolean isFabric(String platform) {
        return platform.contains("fabric");
    }

    public static boolean isPoisonousSnapshot() {
        return (SharedConstants.getGameVersion().getName().contains("potato"));
    }



    public static @NotNull BlockSetType getBlockSetType(String blockID) {
        boolean openByHand = !Objects.equals(blockID, "emerald") && (!Objects.equals(blockID, "netherite") && (!Objects.equals(blockID, "diamond")));
        return new BlockSetType(blockID, openByHand, openByHand, openByHand, BlockSetType.ActivationRule.EVERYTHING, BlockSoundGroup.METAL, SoundEvents.BLOCK_IRON_DOOR_CLOSE, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN, SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON);
    }

    @ExpectPlatform
    public static boolean isModLoaded(String modID) {
        throw new AssertionError();
    }
}