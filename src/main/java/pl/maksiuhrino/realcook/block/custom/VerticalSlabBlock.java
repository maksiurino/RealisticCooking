package pl.maksiuhrino.realcook.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class VerticalSlabBlock extends HorizontalFacingBlock implements Waterloggable {
    public static final MapCodec<VerticalSlabBlock> CODEC = createCodec(VerticalSlabBlock::new);
    public static final EnumProperty<SlabType> TYPE = Properties.SLAB_TYPE;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    private static final Map<Direction, VoxelShape> BOTTOM_SHAPE = VoxelShapes.createHorizontalFacingShapeMap(Block.createCuboidShape(0, 0, 8, 16, 16, 16));
    private static final Map<Direction, VoxelShape> TOP_SHAPE = VoxelShapes.createHorizontalFacingShapeMap(Block.createCuboidShape(0, 0, 0, 16, 16, 8));

    @Override
    protected MapCodec<? extends VerticalSlabBlock> getCodec() {
        return CODEC;
    }

    public VerticalSlabBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(TYPE, SlabType.BOTTOM).with(WATERLOGGED, false).with(FACING, Direction.NORTH));
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(TYPE)) {
            case TOP -> TOP_SHAPE.get(state.get(FACING));
            case BOTTOM -> BOTTOM_SHAPE.get(state.get(FACING));
            case DOUBLE -> VoxelShapes.fullCube();
        };
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if ((Boolean) state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TYPE, WATERLOGGED, FACING);
    }
}
