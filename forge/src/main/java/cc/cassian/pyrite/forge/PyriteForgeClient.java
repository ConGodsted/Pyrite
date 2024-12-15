package cc.cassian.pyrite.forge;

import cc.cassian.pyrite.Pyrite;
import cc.cassian.pyrite.functions.forge.ForgeHelpers;
import net.minecraft.block.Block;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;


@Mod.EventBusSubscriber(modid = Pyrite.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PyriteForgeClient {

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event){
        BlockColors blockColors = event.getBlockColors();
        for (RegistryObject<Block> pyriteBlock : ForgeHelpers.GRASS_BLOCKS) {
            event.register(((state, view, pos, tintIndex) -> {
                assert view != null;
                return BiomeColors.getGrassColor(view, pos);
            }), pyriteBlock.get());

        }
    }

    // Client-side mod bus event handler
    @SubscribeEvent
    public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        for (RegistryObject<Block> pyriteBlock : ForgeHelpers.GRASS_BLOCKS) {
            event.register((stack, tintIndex) -> 9551193, pyriteBlock.get());
        }
    }
}
