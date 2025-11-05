package pl.maksiuhrino.realcook.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Blocks;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;
import pl.maksiuhrino.realcook.block.ModBlocks;
import pl.maksiuhrino.realcook.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        BlockStateModelGenerator.BlockTexturePool smoothStonePool = blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.SMOOTH_STONE);

        smoothStonePool.stairs(ModBlocks.SMOOTH_STONE_STAIRS);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.FLOUR, Models.GENERATED);
        itemModelGenerator.register(ModItems.MILL, Models.GENERATED);
        itemModelGenerator.register(ModItems.BURNT_DONUT, Models.GENERATED);
        itemModelGenerator.register(ModItems.RISEN_SWEET_DOUGH, Models.GENERATED);
        itemModelGenerator.register(ModItems.ICED_SPOON, Models.HANDHELD);
        itemModelGenerator.register(ModItems.UNCOOKED_DONUT, Models.GENERATED);
        itemModelGenerator.register(ModItems.UNICED_DONUT, Models.GENERATED);
        itemModelGenerator.register(ModItems.SWEET_DOUGH, Models.GENERATED);
        itemModelGenerator.register(ModItems.SPOON, Models.HANDHELD);
    }
}
