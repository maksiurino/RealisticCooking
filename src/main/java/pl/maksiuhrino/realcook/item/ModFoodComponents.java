package pl.maksiuhrino.realcook.item;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.FoodComponents;

public class ModFoodComponents {
    public static final FoodComponent RAW_TOAST = new FoodComponent.Builder().nutrition(1).saturationModifier(0.3F).build();
    public static final FoodComponent COOKED_TOAST = FoodComponents.BREAD;
    public static final FoodComponent BURNT_TOAST = new FoodComponent.Builder().nutrition(2).saturationModifier(0.3F).build();

    public static final FoodComponent DONUT = new FoodComponent.Builder().nutrition(4).saturationModifier(0.35f).build();
    public static final FoodComponent UNICED_DONUT = new FoodComponent.Builder().nutrition(3).saturationModifier(0.3f).build();
    public static final FoodComponent BURNT_DONUT = new FoodComponent.Builder().nutrition(1).saturationModifier(0.01f).build();
    public static final FoodComponent UNCOOKED_DONUT = new FoodComponent.Builder().nutrition(3).saturationModifier(0.3f).build();
    public static final FoodComponent SWEET_DOUGH = new FoodComponent.Builder().nutrition(3).saturationModifier(0.3f).build();

    public static final FoodComponent EXTRA_FOOD = new FoodComponent.Builder().nutrition(4).saturationModifier(0.35f).build();

    public static final FoodComponent ICING = new FoodComponent.Builder().nutrition(2).saturationModifier(0.25f).build();
}
