package com.hellcrafters.registry;

import com.hellcrafters.HellCrafters;
import com.hellcrafters.entities.test_entity.TestEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public final class EntityRegistry {
    // creating the deferred register, to add all our custom mobs to
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, HellCrafters.MODID);

    // helper method to shorten each line that registers entities
    private static <T extends Mob> Supplier<EntityType<T>> registerEntity(String name, EntityType.EntityFactory<T> entity, float width, float height, int primaryEggColor, int secondaryEggColor) {
        return ENTITY_TYPES.register(name, () -> EntityType.Builder.of(entity, MobCategory.CREATURE).sized(width, height).build(name));
    }

    // defining all our custom mob's default size, and adding it to the deferred registry
    public static final Supplier<EntityType<TestEntity>> TEST_ENTITY = registerEntity("test_entity", TestEntity::new, 0.75f, 0.35f, 0x1F1F1F, 0x0D0D0D);

    public static final List<Supplier<? extends EntityType<?>>> ENTITY_LIST = List.of(
            TEST_ENTITY,
            TEST_ENTITY
    );





    // finally loads the deferred register to the modEventBus
    public static void register(IEventBus eventBus) { ENTITY_TYPES.register(eventBus); }
}
