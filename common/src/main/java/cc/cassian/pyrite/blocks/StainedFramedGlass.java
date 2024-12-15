package cc.cassian.pyrite.blocks;

import cc.cassian.pyrite.functions.ModHelpers;
import net.minecraft.block.StainedGlassBlock;
import net.minecraft.util.DyeColor;

public class StainedFramedGlass extends StainedGlassBlock {
    public StainedFramedGlass(DyeColor color, Settings settings) {
        super (color, settings.nonOpaque());
    }
}