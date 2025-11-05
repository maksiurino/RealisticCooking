package pl.maksiuhrino.realcook.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;
import pl.maksiuhrino.realcook.block.custom.enums.ToastType;
import pl.maksiuhrino.realcook.block.custom.vanilla.TransparentHorizontalFacingBlock;
import pl.maksiuhrino.realcook.item.ModItems;
import pl.maksiuhrino.realcook.util.StuffTimer;

import java.util.Map;

public class ToasterBlock extends TransparentHorizontalFacingBlock implements Waterloggable {
    public static final MapCodec<ToasterBlock> CODEC = createCodec(ToasterBlock::new);
    public static final EnumProperty<ToastType> TOAST_TYPE = EnumProperty.of("toast", ToastType.class);
    public static final BooleanProperty POWERED = BooleanProperty.of("powered");
    public static final BooleanProperty COOKING = BooleanProperty.of("cooking");
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    private static final Map<Direction, VoxelShape> COLLISION_SHAPE = VoxelShapes.createFacingShapeMap(Block.createCuboidShape(2, 0, 4, 14, 8, 12));
    private static final VoxelShape OUTLINE_HANDLES = VoxelShapes.combineAndSimplify(Block.createCuboidShape(14, 3, 7, 15, 7, 9), Block.createCuboidShape(1, 3, 7, 2, 7, 9), BooleanBiFunction.OR);
    private static final VoxelShape OUTLINE_HANDLES_COOKING = VoxelShapes.combineAndSimplify(Block.createCuboidShape(14, 1, 7, 15, 5, 9), Block.createCuboidShape(1, 1, 7, 2, 5, 9), BooleanBiFunction.OR);
    private static final VoxelShape OUTLINE_BASE = Block.createCuboidShape(2, 0, 4, 14, 8, 12);
    private static final Map<Direction, VoxelShape> OUTLINE_SHAPE = VoxelShapes.createFacingShapeMap(VoxelShapes.combineAndSimplify(OUTLINE_HANDLES, OUTLINE_BASE, BooleanBiFunction.OR));
    private static final Map<Direction, VoxelShape> OUTLINE_SHAPE_COOKING = VoxelShapes.createFacingShapeMap(VoxelShapes.combineAndSimplify(OUTLINE_HANDLES_COOKING, OUTLINE_BASE, BooleanBiFunction.OR));
    private WorldView world;

    @Override
    public MapCodec<? extends ToasterBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        this.world = (WorldView) world;
        if (state.get(COOKING)) {
            return OUTLINE_SHAPE_COOKING.get(state.get(FACING));
        }
        return OUTLINE_SHAPE.get(state.get(FACING));
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        this.world = (WorldView) world;
        return COLLISION_SHAPE.get(state.get(FACING));
    }

    @Override
    protected VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        this.world = (WorldView) world;
        return OUTLINE_SHAPE.get(state.get(FACING));
    }

    public ToasterBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(TOAST_TYPE, ToastType.NONE).with(POWERED, false).with(COOKING, false).with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TOAST_TYPE, FACING, POWERED, COOKING, WATERLOGGED);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        this.world = (WorldView) ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
        return this.getDefaultState()
                .with(FACING, ctx.getHorizontalPlayerFacing().getOpposite())
                .with(TOAST_TYPE, ToastType.NONE)
                .with(POWERED, false)
                .with(COOKING, false)
                .with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        this.world = (WorldView) world;
        if (stack.isOf(ModItems.RAW_TOAST)) {
            if (state.get(TOAST_TYPE) == ToastType.NONE) {
                world.setBlockState(pos, state.with(TOAST_TYPE, ToastType.RAW));
                stack.decrementUnlessCreative(1, player);
                player.playSound(SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM);
                return ActionResult.SUCCESS;
            }
        } else if (stack.isOf(ModItems.COOKED_TOAST)) {
            if (state.get(TOAST_TYPE) == ToastType.NONE) {
                world.setBlockState(pos, state.with(TOAST_TYPE, ToastType.COOKED));
                stack.decrementUnlessCreative(1, player);
                player.playSound(SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM);
                return ActionResult.SUCCESS;
            }
        } else if (stack.isOf(ModItems.BURNT_TOAST)) {
            if (state.get(TOAST_TYPE) == ToastType.NONE) {
                world.setBlockState(pos, state.with(TOAST_TYPE, ToastType.BURNT));
                stack.decrementUnlessCreative(1, player);
                player.playSound(SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM);
                return ActionResult.SUCCESS;
            }
        } else {
            if (!(player.isSneaking() || player.isInSneakingPose())) {
                if (!(state.get(TOAST_TYPE) == ToastType.NONE)) {
                    if (stack.isOf(Items.AIR)) {
                        player.playSound(SoundEvents.ENTITY_ITEM_FRAME_REMOVE_ITEM);
                        if (!player.isInCreativeMode()) {
                            if (state.get(TOAST_TYPE) == ToastType.RAW) {
                                dropStack(world, pos, new ItemStack(ModItems.RAW_TOAST));
                            } else if (state.get(TOAST_TYPE) == ToastType.COOKED) {
                                dropStack(world, pos, new ItemStack(ModItems.COOKED_TOAST));
                            } else if (state.get(TOAST_TYPE) == ToastType.BURNT) {
                                dropStack(world, pos, new ItemStack(ModItems.BURNT_TOAST));
                            }
                        }
                        world.setBlockState(pos, state.with(TOAST_TYPE, ToastType.NONE));
                        return ActionResult.SUCCESS;
                    }
                }
            } else {
                if (stack.isOf(Items.AIR)) {
                    if (state.get(COOKING)) {
                        world.setBlockState(pos, state.with(COOKING, false));
                        player.playSound(SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF);
                        if (state.get(COOKING) && state.get(POWERED) && !(state.get(TOAST_TYPE) == ToastType.NONE)) {
                            runTimer();
                        }
                        return ActionResult.SUCCESS;
                    } else if (!state.get(COOKING)) {
                        world.setBlockState(pos, state.with(COOKING, true));
                        player.playSound(SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON);
                        return ActionResult.SUCCESS;
                    }
                }
            }
        }
        return ActionResult.FAIL;
    }

    private void runTimer() {
        StuffTimer.INSTANCE.setTimer(100, () -> {

        });
    }

    @Override
    protected boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        this.world = (WorldView) world;
        if ((Boolean) state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if (hasPower(world, pos, state)) {
            return state.with(POWERED, true);
        }
        return state.with(POWERED, false);
    }

    protected boolean hasPower(WorldView world, BlockPos pos, BlockState state) {
        return this.getPower(world, pos, state, Direction.NORTH) > 0 || this.getPower(world, pos, state, Direction.EAST) > 0 || this.getPower(world, pos, state, Direction.SOUTH) > 0 || this.getPower(world, pos, state, Direction.WEST) > 0;
    }

    protected int getPower(WorldView world, BlockPos pos, BlockState state, Direction direction) {
        BlockPos blockPos = pos.offset(direction);
        int i = world.getEmittedRedstonePower(blockPos, direction);
        if (i >= 15) {
            return i;
        } else {
            BlockState blockState = world.getBlockState(blockPos);
            return Math.max(i, blockState.isOf(Blocks.REDSTONE_WIRE) ? (Integer)blockState.get(RedstoneWireBlock.POWER) : 0);
        }
    }

    protected boolean hasPower(World world, BlockPos pos, BlockState state) {
        return this.getPower(world, pos, state, Direction.NORTH) > 0 || this.getPower(world, pos, state, Direction.EAST) > 0 || this.getPower(world, pos, state, Direction.SOUTH) > 0 || this.getPower(world, pos, state, Direction.WEST) > 0;
    }

    protected int getPower(World world, BlockPos pos, BlockState state, Direction direction) {
        BlockPos blockPos = pos.offset(direction);
        int i = world.getEmittedRedstonePower(blockPos, direction);
        if (i >= 15) {
            return i;
        } else {
            BlockState blockState = world.getBlockState(blockPos);
            return Math.max(i, blockState.isOf(Blocks.REDSTONE_WIRE) ? (Integer)blockState.get(RedstoneWireBlock.POWER) : 0);
        }
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }
}
