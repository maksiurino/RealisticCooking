package pl.maksiuhrino.realcook.block.custom;

import com.mojang.serialization.MapCodec;
import pl.maksiuhrino.realcook.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class MillBlock extends HorizontalFacingBlock {

    private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 7, 16);

    public MillBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return null;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    private static boolean isWheatItem(ItemStack stack) {
        return stack.isOf(Items.WHEAT);
    }


    public static void rotateMill(@Nullable Entity user, World world, BlockPos pos, BlockState state) {
        BlockState blockStateNorth = state.with(FACING, Direction.NORTH);
        BlockState blockStateSouth = state.with(FACING, Direction.SOUTH);
        BlockState blockStateEast = state.with(FACING, Direction.EAST);
        BlockState blockStateWest = state.with(FACING, Direction.WEST);

        if(state.get(FACING) == Direction.NORTH){
            world.setBlockState(pos, blockStateEast, Block.NOTIFY_ALL);
        } else if(state.get(FACING) == Direction.EAST){
            world.setBlockState(pos, blockStateSouth, Block.NOTIFY_ALL);
        } else if(state.get(FACING) == Direction.SOUTH){
            world.setBlockState(pos, blockStateWest, Block.NOTIFY_ALL);
        } else if(state.get(FACING) == Direction.WEST){
            world.setBlockState(pos, blockStateNorth, Block.NOTIFY_ALL);
        }
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity user, Hand hand, BlockHitResult hit) {
        rotateMill(user, world,pos, state);
        world.playSound(user, pos, SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS, 1, 1);
        if (isWheatItem(stack) && state.get(FACING) == Direction.NORTH) {
            stack.decrementUnlessCreative(1, user);
            dropStack(world, pos, new ItemStack(ModItems.FLOUR));
        }
        return ActionResult.SUCCESS;
    }






    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }


}