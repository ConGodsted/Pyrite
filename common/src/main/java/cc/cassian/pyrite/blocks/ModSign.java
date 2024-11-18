package cc.cassian.pyrite.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.util.math.BlockPos;

import static cc.cassian.pyrite.functions.ModHelpers.signBlocks;

public class ModSign extends SignBlockEntity {
    public ModSign(BlockPos pos, BlockState state) {
        super(signBlocks.getFirst(), pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return signBlocks.getFirst();
    }

    public boolean supports(BlockState state) {
        return true;
    }

}