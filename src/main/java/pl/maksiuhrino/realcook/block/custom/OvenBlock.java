package pl.maksiuhrino.realcook.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.util.math.Direction;
import pl.maksiuhrino.realcook.block.custom.vanilla.TransparentHorizontalFacingBlock;

public class OvenBlock extends TransparentHorizontalFacingBlock {
    public static final MapCodec<OvenBlock> CODEC = createCodec(OvenBlock::new);

    @Override
    protected MapCodec<? extends OvenBlock> getCodec() {
        return CODEC;
    }

    public OvenBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }
}
