package com.hellcrafters.entities;

import com.hellcrafters.HellCrafters;
import com.hellcrafters.entities.test_entity.TestEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    // creating the ENTITY_TYPES registry
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, HellCrafters.MODID);

    // Defining specific mod entities
    public static final Supplier<EntityType<TestEntity>> TEST_ENTITY =
            ENTITY_TYPES.register("test_entity", () -> EntityType.Builder.of(TestEntity::new, MobCategory.CREATURE)
                    .sized(0.75f, 0.35f).build("test_entity"));



    // registering the ENTITY_TYPE registry, called in main mod class
    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

}
