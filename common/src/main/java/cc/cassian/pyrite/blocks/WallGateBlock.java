package cc.cassian.pyrite.blocks;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class WallGateBlock extends FenceGateBlock {

    protected static final VoxelShape Z_AXIS_SHAPE;
    protected static final VoxelShape X_AXIS_SHAPE;
    protected static final VoxelShape IN_WALL_Z_AXIS_SHAPE;
    protected static final VoxelShape IN_WALL_X_AXIS_SHAPE;

    public WallGateBlock(Settings settings) {
        super(settings, WoodType.CRIMSON);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(IN_WALL)) {
            return state.get(FACING).getAxis() == Direction.Axis.X ? IN_WALL_X_AXIS_SHAPE : IN_WALL_Z_AXIS_SHAPE;
        } else {
            return state.get(FACING).getAxis() == Direction.Axis.X ? X_AXIS_SHAPE : Z_AXIS_SHAPE;
        }
    }

    static {
        Z_AXIS_SHAPE = Block.createCuboidShape(0.0F, 2.0F, 6.0F, 16.0F, 13.0F, 10.0F);
        X_AXIS_SHAPE = Block.createCuboidShape(6.0F, 2.0F, 0.0F, 10.0F, 13.0F, 16.0F);
        IN_WALL_Z_AXIS_SHAPE = Block.createCuboidShape(0.0F, 0.0F, 6.0F, 16.0F, 13.0F, 10.0F);
        IN_WALL_X_AXIS_SHAPE = Block.createCuboidShape(6.0F, 0.0F, 0.0F, 10.0F, 13.0F, 16.0F);
    }
}