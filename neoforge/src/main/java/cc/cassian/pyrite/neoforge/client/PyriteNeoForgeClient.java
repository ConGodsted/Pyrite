package cc.cassian.pyrite.neoforge.client;


import cc.cassian.pyrite.Pyrite;
import net.minecraft.block.Block;
import net.minecraft.client.color.world.BiomeColors;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import static cc.cassian.pyrite.functions.neoforge.NeoHelpers.GRASS_BLOCKS;


@Mod(Pyrite.modID)
public class PyriteNeoForgeClient {

    public static void init(IEventBus eventBus) {
        if (FMLEnvironment.dist.equals(Dist.CLIENT)) {
            eventBus.addListener(PyriteNeoForgeClient::registerBlockColors);
            eventBus.addListener(PyriteNeoForgeClient::registerItemColorHandlers);
        }
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event){
        for (DeferredHolder<Block, ?> pyriteBlock : GRASS_BLOCKS) {
            event.register(((state, view, pos, tintIndex) -> {
                assert view != null;
                return BiomeColors.getGrassColor(view, pos);
            }), pyriteBlock.get());

        }
    }

    // Client-side mod bus event handler
    @SubscribeEvent
    public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        for (DeferredHolder<Block, ?> pyriteBlock : GRASS_BLOCKS) {
            event.register((stack, tintIndex) -> 9551193, pyriteBlock.get());
        }
    }

}