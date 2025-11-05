package pl.maksiuhrino.realcook.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Blocks;
import net.minecraft.component.Component;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import pl.maksiuhrino.realcook.RealisticCooking;
import pl.maksiuhrino.realcook.block.ModBlocks;

public class ModItemGroups {
    public static final RegistryKey<ItemGroup> REALISTIC_COOKING_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(RealisticCooking.MOD_ID, "realistic_cooking_group"));
    public static final ItemGroup REALISTIC_COOKING_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.TOASTER))
            .displayName(Text.translatable("itemGroup.cook_real"))
            .build();

    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, REALISTIC_COOKING_GROUP_KEY, REALISTIC_COOKING_GROUP);

        ItemGroupEvents.modifyEntriesEvent(REALISTIC_COOKING_GROUP_KEY).register(entries -> {
            entries.add(ModItems.FLOUR);
            entries.add(ModItems.SWEET_DOUGH);
            entries.add(ModItems.RISEN_SWEET_DOUGH);
            entries.add(ModItems.UNCOOKED_DONUT);
            entries.add(ModItems.UNICED_DONUT);

            ItemStack donut = new ItemStack(ModItems.DONUT);
            donut.set(DataComponentTypes.DYED_COLOR, new DyedColorComponent(16675227));
            entries.add(donut);

            entries.add(ModItems.BURNT_DONUT);
            entries.add(ModItems.SPOON);
            entries.add(ModItems.ICED_SPOON);
            entries.add(ModItems.MILL);

            ItemStack pie = new ItemStack(ModItems.PIE);
            pie.set(DataComponentTypes.DYED_COLOR, new DyedColorComponent(14210737));
            entries.add(pie);

            ItemStack sushi = new ItemStack(ModItems.SUSHI);
            sushi.set(DataComponentTypes.DYED_COLOR, new DyedColorComponent(15719874));
            entries.add(sushi);

            ItemStack baked_cookie = new ItemStack(ModItems.BAKED_COOKIE);
            baked_cookie.set(DataComponentTypes.DYED_COLOR, new DyedColorComponent(14210737));
            entries.add(baked_cookie);

            entries.add(ModItems.TOASTER);
            entries.add(ModItems.RAW_TOAST);
            entries.add(ModItems.COOKED_TOAST);
            entries.add(ModItems.BURNT_TOAST);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.addAfter(Items.SMOOTH_STONE, ModItems.SMOOTH_STONE_STAIRS);
            entries.addAfter(Items.IRON_BLOCK, ModItems.IRON_SLAB);
        });
    }
}
