package cc.cassian.pyrite.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.FallingBlock;

public class GravelBlock extends FallingBlock {
    public static final MapCodec<GravelBlock> CODEC = createCodec(GravelBlock::new);

    public GravelBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends FallingBlock> getCodec() {
        return CODEC;
    }
}
