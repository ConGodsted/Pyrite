package cc.cassian.pyrite.fabric.client;

import cc.cassian.pyrite.blocks.ModSign;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;

import static cc.cassian.pyrite.functions.fabric.FabricHelpers.*;


@Environment(EnvType.CLIENT)
public class PyriteClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        for (Block grassBlock : grassBlocks) {
            ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> BiomeColors.getGrassColor(view, pos), grassBlock);
            ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 9551193, grassBlock);
        }
        for (Block transparentBlock : transparentBlocks) {
            BlockRenderLayerMap.INSTANCE.putBlock(transparentBlock, RenderLayer.getCutout());
        }
        for (Block translucentBlock : translucentBlocks) {
            BlockRenderLayerMap.INSTANCE.putBlock(translucentBlock, RenderLayer.getTranslucent());
        }
        for (BlockEntityType<ModSign> signBlock : signBlocks) {
            System.out.println(signBlock);
            BlockEntityRendererFactories.register(signBlock, SignBlockEntityRenderer::new);
        }

    }
}