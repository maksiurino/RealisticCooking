package pl.maksiuhrino.realcook.util;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import pl.maksiuhrino.realcook.RealisticCooking;

public class ModSounds {
    private ModSounds() {}

    public static final RegistryEntry.Reference<SoundEvent> ENTITY_GENERIC_LICK = registerReference(Identifier.of("bakingaway", "lick"), Identifier.of("bakingaway", "lick"));

    private static RegistryEntry.Reference<SoundEvent> registerReference(String id) {
        return registerReference(Identifier.ofVanilla(id));
    }

    private static RegistryEntry.Reference<SoundEvent> registerReference(Identifier id) {
        return registerReference(id, id);
    }

    private static RegistryEntry.Reference<SoundEvent> registerReference(Identifier id, Identifier soundId) {
        return Registry.registerReference(Registries.SOUND_EVENT, id, SoundEvent.of(soundId));
    }

    public static void initialize() {}
}
