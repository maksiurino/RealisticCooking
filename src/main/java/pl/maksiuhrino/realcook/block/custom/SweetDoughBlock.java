package pl.maksiuhrino.realcook.block.custom;

import pl.maksiuhrino.realcook.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class SweetDoughBlock extends Block {

    public static final IntProperty DOUGH_STAGE = ModBlocks.DOUGH_STAGE;

    private static final VoxelShape SHAPE = Block.createCuboidShape(3, 0, 3, 13, 4, 13);

    public SweetDoughBlock(Settings settings) {
        super(settings);

        this.setDefaultState(this.stateManager.getDefaultState().with(DOUGH_STAGE, 0));

    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockPos blockPos = pos.down();
        BlockState blockStateFuel = world.getBlockState(blockPos);

        if (blockStateFuel.isOf(Blocks.CAMPFIRE)
                || blockStateFuel.isOf(Blocks.SOUL_CAMPFIRE)
                || blockStateFuel.isOf(Blocks.FIRE)
                || blockStateFuel.isOf(Blocks.SOUL_FIRE)
                || blockStateFuel.isOf(Blocks.SMOKER)
                || blockStateFuel.isOf(Blocks.BLAST_FURNACE)
                || blockStateFuel.isOf(Blocks.FURNACE)
                || blockStateFuel.isOf(Blocks.SOUL_FIRE)
        ) {
            if(state.get(DOUGH_STAGE) <= 2) {
                BlockState blockStateRise = state.with(DOUGH_STAGE, state.get(DOUGH_STAGE) + 1);
                world.setBlockState(pos, blockStateRise, Block.NOTIFY_ALL);
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockStateRise));
            }
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(DOUGH_STAGE, 0);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DOUGH_STAGE);
    }


}