package pl.maksiuhrino.realcook.block;

import net.minecraft.block.*;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;
import pl.maksiuhrino.realcook.RealisticCooking;
import pl.maksiuhrino.realcook.block.custom.*;

import java.util.function.Function;

public class ModBlocks {
    public static final Block TOASTER = register(
            "toaster",
            ToasterBlock::new,
            AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).nonOpaque().luminance(ignored -> 0),
            true,
            null
    );

    public static final Block OVEN = register(
            "oven",
            OvenBlock::new,
            AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).nonOpaque(),
            true,
            null
    );



    public static final Block SMOOTH_STONE_STAIRS = registerStairsBlock(
            "smooth_stone_stairs",
            Blocks.SMOOTH_STONE
    );



    public static final Block IRON_SLAB = register(
            "iron_slab",
            SlabBlock::new,
            AbstractBlock.Settings.copy(Blocks.IRON_BLOCK),
            true,
            "minecraft"
    );


    public static final Block IRON_VERTICAL_SLAB = register(
            "iron_vertical_slab",
            VerticalSlabBlock::new,
            AbstractBlock.Settings.copy(IRON_SLAB).nonOpaque(),
            true,
            "minecraft"
    );



    public static final IntProperty DOUGH_STAGE = IntProperty.of("stage", 0, 3);

    public static final Block MILL = registerBlock("mill",
            MillBlock::new, Block.Settings.copy(Blocks.STONE));

    public static final Block SWEET_DOUGH = registerBlock("sweet_dough",
            SweetDoughBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque().ticksRandomly().strength(1.0F, 2.0F));


    public static Block registerStairsBlock(String id, Block base) {
        return registerr(id, settings -> new StairsBlock(base.getDefaultState(), settings), AbstractBlock.Settings.copy(base));
    }

    private static Block registerOldStairsBlock(String id, Block base) {
        return registerr(id, settings -> new StairsBlock(base.getDefaultState(), settings), AbstractBlock.Settings.copyShallow(base));
    }

    private static Block registerr(String id, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        return Blocks.register(keyOf(id), factory, settings);
    }

    private static Block register(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings, boolean shouldRegisterItem, String namespace) {
        String usedNamespace = namespace;
        if (namespace == null) {
            usedNamespace = RealisticCooking.MOD_ID;
        }

        RegistryKey<Block> blockKey = keyOfBlock(name, usedNamespace);

        Block block = blockFactory.apply(settings.registryKey(blockKey));

        if (shouldRegisterItem) {
            RegistryKey<Item> itemKey = keyOfItem(name, usedNamespace);

            BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey).useBlockPrefixedTranslationKey());
            Registry.register(Registries.ITEM, itemKey, blockItem);
        }

        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    private static RegistryKey<Block> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.ofVanilla(id));
    }

    private static RegistryKey<Block> keyOfBlock(String name, String namespace) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(namespace, name));
    }

    private static RegistryKey<Item> keyOfItem(String name, String namespace) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(namespace, name));
    }

    private static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings) {
        return register(name, blockFactory, settings, true, "bakingaway");
    }

    public static void initialize() {}
}