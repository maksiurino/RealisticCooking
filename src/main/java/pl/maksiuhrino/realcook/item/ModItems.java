package pl.maksiuhrino.realcook.item;

import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import pl.maksiuhrino.realcook.RealisticCooking;
import pl.maksiuhrino.realcook.block.ModBlocks;
import pl.maksiuhrino.realcook.item.ModFoodComponents;
import pl.maksiuhrino.realcook.item.custom.IcedSpoonItem;

import java.util.function.Function;

public class ModItems {
    public static final Item RAW_TOAST = register("raw_toast", Item::new, new Item.Settings().food(ModFoodComponents.RAW_TOAST), null);
    public static final Item COOKED_TOAST = register("cooked_toast", Item::new, new Item.Settings().food(ModFoodComponents.COOKED_TOAST), null);
    public static final Item BURNT_TOAST = register("burnt_toast", Item::new, new Item.Settings().food(ModFoodComponents.BURNT_TOAST), null);

    public static final Item TOASTER = registerBlockItem("toaster", ModBlocks.TOASTER);

    public static final Item OVEN = registerBlockItem("oven", ModBlocks.OVEN);


    public static final Item SMOOTH_STONE_STAIRS = Items.register(ModBlocks.SMOOTH_STONE_STAIRS);
    public static final Item IRON_SLAB = registerBlockItem("iron_slab", ModBlocks.IRON_SLAB);

    public static final Item IRON_VERTICAL_SLAB = registerBlockItem("iron_vertical_slab", ModBlocks.IRON_VERTICAL_SLAB);



    public static final Item FLOUR = registerItem("flour", Item::new, new Item.Settings());

    public static final Item DONUT = registerItem("donut", Item::new, new Item.Settings()
            .food(ModFoodComponents.DONUT));

    public static final Item SUSHI = registerItem("sushi", Item::new, new Item.Settings()
            .food(ModFoodComponents.EXTRA_FOOD));
    public static final Item PIE = registerItem("pie", Item::new, new Item.Settings()
            .food(ModFoodComponents.EXTRA_FOOD));
    public static final Item BAKED_COOKIE = registerItem("baked_cookie", Item::new, new Item.Settings()
            .food(ModFoodComponents.EXTRA_FOOD));

    public static final Item BURNT_DONUT = registerItem("burnt_donut", Item::new, new Item.Settings()
            .food(ModFoodComponents.BURNT_DONUT,
                    ModConsumableComponents.BURNT_DONUT));


    public static final Item UNCOOKED_DONUT =
            registerItem("uncooked_donut", Item::new, new Item.Settings().food(ModFoodComponents.UNCOOKED_DONUT));
    public static final Item SPOON =
            registerItem("spoon", Item::new, new Item.Settings());

    public static final Item ICED_SPOON =
            registerItem("iced_spoon", IcedSpoonItem::new, new Item.Settings().component(DataComponentTypes.CONSUMABLE, ModConsumableComponents.LICK));

    public static final Item RISEN_SWEET_DOUGH =
            registerItem("risen_sweet_dough", Item::new, new Item.Settings().food(ModFoodComponents.SWEET_DOUGH));
    public static final Item UNICED_DONUT =
            registerItem("uniced_donut", Item::new, new Item.Settings().food(ModFoodComponents.UNICED_DONUT));

    public static final Item SWEET_DOUGH = registerBlockItem("sweet_dough", ModBlocks.SWEET_DOUGH);
    public static final Item MILL = registerBlockItem("mill", ModBlocks.MILL);

    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings, String namespace) {
        String used_namespace = namespace;
        if (namespace == null) {
            used_namespace = RealisticCooking.MOD_ID;
        }
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(used_namespace, name));

        Item item = itemFactory.apply(settings.registryKey(itemKey));

        Registry.register(Registries.ITEM, itemKey, item);

        return item;
    }

    public static Item registerItem(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        return register(name, itemFactory, settings, "bakingaway");
    }

    public static Item registerBlockItem(String name, Block block) {
        return block.asItem();
    }

    public static void initialize() {}
}
