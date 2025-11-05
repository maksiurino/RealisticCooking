package pl.maksiuhrino.realcook.item.custom;

import pl.maksiuhrino.realcook.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class IcedSpoonItem extends Item {
    public IcedSpoonItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack itemStack = super.finishUsing(stack, world, user);
        if (user instanceof PlayerEntity && ((PlayerEntity)user).getAbilities().creativeMode) {
            user.emitGameEvent(GameEvent.EAT);
            return itemStack;
        }
        return new ItemStack(ModItems.SPOON);
    }
}
