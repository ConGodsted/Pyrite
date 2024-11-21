package cc.cassian.pyrite.forge.client;


import cc.cassian.pyrite.Pyrite;
import net.minecraft.block.Block;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.RegistryObject;

import static cc.cassian.pyrite.functions.forge.ForgeHelpers.GRASS_BLOCKS;


@Mod(Pyrite.modID)
public class PyriteForgeClient {

    public static void init(IEventBus eventBus) {
        if (FMLEnvironment.dist.equals(Dist.CLIENT)) {
            eventBus.addListener(PyriteForgeClient::registerBlockColors);
            eventBus.addListener(PyriteForgeClient::registerItemColorHandlers);
        }
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event){
        for (RegistryObject<Block> pyriteBlock : GRASS_BLOCKS) {
            event.register(((state, view, pos, tintIndex) -> {
                assert view != null;
                return BiomeColors.getGrassColor(view, pos);
            }), pyriteBlock.get());

        }
    }

    // Client-side mod bus event handler
    @SubscribeEvent
    public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        for (RegistryObject<Block> pyriteBlock : GRASS_BLOCKS) {
            event.register((stack, tintIndex) -> 9551193, pyriteBlock.get());
        }
    }

}