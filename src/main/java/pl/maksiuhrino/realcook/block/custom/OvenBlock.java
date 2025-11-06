package pl.maksiuhrino.realcook.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import pl.maksiuhrino.realcook.block.custom.vanilla.TransparentHorizontalFacingBlock;

public class OvenBlock extends TransparentHorizontalFacingBlock {
    public static final MapCodec<OvenBlock> CODEC = createCodec(OvenBlock::new);
    public static final BooleanProperty OPEN = BooleanProperty.of("open");

    @Override
    protected MapCodec<? extends OvenBlock> getCodec() {
        return CODEC;
    }

    public OvenBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(OPEN, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState()
                .with(FACING, ctx.getHorizontalPlayerFacing().getOpposite())
                .with(OPEN, false);
    }

    public boolean isPlayerCrouching(PlayerEntity player) {
        return player.isSneaking() || player.isInSneakingPose() || player.isSneaky();
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (stack.isEmpty() && isPlayerCrouching(player)) {
            if (state.get(OPEN)) {
                world.setBlockState(pos, state.with(OPEN, false));
            } else {
                world.setBlockState(pos, state.with(OPEN, true));
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }
}
