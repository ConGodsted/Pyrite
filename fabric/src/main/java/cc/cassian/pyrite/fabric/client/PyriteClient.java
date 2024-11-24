package cc.cassian.pyrite.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.*;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.RenderLayer;

import static cc.cassian.pyrite.functions.fabric.FabricHelpers.*;


@Environment(EnvType.CLIENT)
public class PyriteClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        for (Block grassBlock : GRASS_BLOCKS) {
            ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> BiomeColors.getGrassColor(view, pos), grassBlock);
            ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 9551193, grassBlock);
        }
        for (Block transparentBlock : TRANSPARENT_BLOCKS) {
            BlockRenderLayerMap.INSTANCE.putBlock(transparentBlock, RenderLayer.getCutout());
        }
        for (Block translucentBlock : TRANSLUCENT_BLOCKS) {
            BlockRenderLayerMap.INSTANCE.putBlock(translucentBlock, RenderLayer.getTranslucent());
        }
    }
}