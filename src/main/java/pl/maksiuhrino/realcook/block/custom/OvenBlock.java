package pl.maksiuhrino.realcook.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import pl.maksiuhrino.realcook.block.custom.vanilla.HorizontalFacingBlockWithEntity;
import pl.maksiuhrino.realcook.block.entity.custom.OvenBlockEntity;

public class OvenBlock extends HorizontalFacingBlockWithEntity implements BlockEntityProvider {
    public static final MapCodec<OvenBlock> CODEC = createCodec(OvenBlock::new);

    @Override
    protected MapCodec<? extends OvenBlock> getCodec() {
        return CODEC;
    }

    public OvenBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new OvenBlockEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void onStateReplaced(BlockState state, ServerWorld world, BlockPos pos, boolean moved) {
        if (state.getBlock() != world.getBlockState(pos).getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof OvenBlockEntity) {
                ItemScatterer.spawn(world, pos, ((OvenBlockEntity) blockEntity));
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, moved);
        }
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof OvenBlockEntity ovenBlockEntity) {
            if (ovenBlockEntity.isEmpty() && !stack.isEmpty()) {
                ovenBlockEntity.setStack(0, stack.copyWithCount(1));
                world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1f, 2f);
                stack.decrement(1);

                ovenBlockEntity.markDirty();
                world.updateListeners(pos, state, state, 0);
            } else if (stack.isEmpty() && !player.isSneaking()) {
                ItemStack stackInOven = ovenBlockEntity.getStack(0);
                player.setStackInHand(Hand.MAIN_HAND, stackInOven);
                world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1f, 1f);
                ovenBlockEntity.clear();

                ovenBlockEntity.markDirty();
                world.updateListeners(pos, state, state, 0);
            }
        }
        return ActionResult.SUCCESS;
    }
}
