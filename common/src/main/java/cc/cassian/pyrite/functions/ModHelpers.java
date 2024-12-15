package cc.cassian.pyrite.functions;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.SharedConstants;
import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.ToIntFunction;

import static cc.cassian.pyrite.Pyrite.MOD_ID;


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
        return Identifier.of(MOD_ID, id);
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

    public static DyeColor getDyeColorFromFramedId(String blockID) {
        var dye = blockID.split("_framed")[0];
        return switch (dye) {
            case "glow" -> DyeColor.CYAN;
            case "dragon" -> DyeColor.BLACK;
            case "star" -> DyeColor.LIGHT_BLUE;
            case "honey" -> DyeColor.YELLOW;
            case "nostalgia" -> DyeColor.BROWN;
            case "rose" -> DyeColor.PINK;
            case "poisonous" -> DyeColor.LIME;
            default -> DyeColor.byName(dye, DyeColor.WHITE);
        };
    }



    public static @NotNull BlockSetType getBlockSetType(String blockID) {
        boolean openByHand = !Objects.equals(blockID, "emerald") && (!Objects.equals(blockID, "netherite") && (!Objects.equals(blockID, "diamond")));
        return new BlockSetType(blockID, openByHand, openByHand, openByHand, BlockSetType.ActivationRule.EVERYTHING, BlockSoundGroup.METAL, SoundEvents.BLOCK_IRON_DOOR_CLOSE, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN, SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON);
    }

    @ExpectPlatform
    public static boolean isModLoaded(String modID) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isShield(ItemStack stack) {
        throw new AssertionError();
    }
}