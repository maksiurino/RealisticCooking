package pl.maksiuhrino.realcook;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.BlockRenderLayer;
import pl.maksiuhrino.realcook.block.ModBlocks;

public class RealisticCookingClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.putBlock(ModBlocks.TOASTER, BlockRenderLayer.TRANSLUCENT);
    }
}
