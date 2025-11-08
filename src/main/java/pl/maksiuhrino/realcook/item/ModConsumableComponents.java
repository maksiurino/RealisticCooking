package pl.maksiuhrino.realcook.item;


import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.item.consume.UseAction;
import pl.maksiuhrino.realcook.util.ModSounds;

import static net.minecraft.component.type.ConsumableComponents.drink;
import static net.minecraft.component.type.ConsumableComponents.food;

public class ModConsumableComponents {

    public static final ConsumableComponent BURNT_DONUT = food()
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(
                    StatusEffects.NAUSEA, 180, 1), 1.0F))
            .build();

    public static final ConsumableComponent BUTTER = food().sound(ModSounds.ENTITY_GENERIC_LICK).build();

    public static final ConsumableComponent RAPE_OIL = drink()
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(
                    StatusEffects.NAUSEA, 180, 1), 1.0F))
            .build();

    public static final ConsumableComponent LICK = spoon().build();

    public static ConsumableComponent.Builder spoon() {
        return ConsumableComponent.builder().useAction(UseAction.DRINK).consumeSeconds(3.2F).sound(ModSounds.ENTITY_GENERIC_LICK).consumeParticles(true);
    }
}
