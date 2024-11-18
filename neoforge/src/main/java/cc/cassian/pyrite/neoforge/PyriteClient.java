package cc.cassian.pyrite.neoforge;


import cc.cassian.pyrite.Pyrite;
import net.minecraft.block.Block;
import net.minecraft.client.color.world.BiomeColors;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import static cc.cassian.pyrite.functions.neoforge.NeoHelpers.grassBlocks;


@Mod(Pyrite.modID)
public class PyriteClient {

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event){
        for (DeferredHolder<Block, ?> pyriteBlock : grassBlocks) {
            event.register(((state, view, pos, tintIndex) -> {
                assert view != null;
                return BiomeColors.getGrassColor(view, pos);
            }), pyriteBlock.get());

        }
    }

    // Client-side mod bus event handler
    @SubscribeEvent
    public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        for (DeferredHolder<Block, ?> pyriteBlock : grassBlocks) {
            event.register((stack, tintIndex) -> 9551193, pyriteBlock.get());
        }
    }

}