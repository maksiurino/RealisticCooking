package pl.maksiuhrino.realcook.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import pl.maksiuhrino.realcook.RealisticCooking;
import pl.maksiuhrino.realcook.block.ModBlocks;
import pl.maksiuhrino.realcook.block.entity.custom.OvenBlockEntity;

public class ModBlockEntities {
    public static final BlockEntityType<OvenBlockEntity> OVEN_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(RealisticCooking.MOD_ID, "oven_be"),
                    FabricBlockEntityTypeBuilder.create(OvenBlockEntity::new, ModBlocks.OVEN).build(null));

    public static void initialize() {}
}
