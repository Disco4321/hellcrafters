package com.hellcrafters.registry;

import com.hellcrafters.HellCrafters;
import com.hellcrafters.entity.TestEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, HellCrafters.MODID);

    // --------------------------------------------------
    // Entity Registration Section
    // massive list of entities, created and registered through helper methods
    // --------------------------------------------------
    public static final Supplier<EntityType<TestEntity>> TEST_ENTITY = registerEntity("test_entity", TestEntity::new, 0.75f, 0.35f, 0x1F1F1F, 0x0D0D0D);
    //public static final Supplier<EntityType<TestEntity>> TEST_ENTITY = registerEntity("test_entity", TestEntity::new, 0.75f, 0.35f, 0x1F1F1F, 0x0D0D0D);
    //public static final Supplier<EntityType<TestEntity>> TEST_ENTITY = registerEntity("test_entity", TestEntity::new, 0.75f, 0.35f, 0x1F1F1F, 0x0D0D0D);



    // --------------------------------------------------
    // Entity Attribute Registration Section
    //
    // --------------------------------------------------
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        AttributeSupplier.Builder genericAttribs = PathfinderMob.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.MAX_HEALTH, 1);
        AttributeSupplier.Builder genericMovingAttribs = PathfinderMob.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.MAX_HEALTH, 1)
                .add(Attributes.MOVEMENT_SPEED, 0.25f);
        AttributeSupplier.Builder genericMonsterAttribs = Monster.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.MAX_HEALTH, 1)
                .add(Attributes.MOVEMENT_SPEED, 0.25f)
                .add(Attributes.ATTACK_DAMAGE, 5)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1);

        event.put(TEST_ENTITY.get(), genericMonsterAttribs.build());
    }


    // --------------------------------------------------
    // Helper Method Section
    //
    // --------------------------------------------------
    private static <T extends Mob> Supplier<EntityType<T>> registerEntity(String name, EntityType.EntityFactory<T> entity, float width, float height, int primaryEggColor, int secondaryEggColor) {
        //DeferredHolder<EntityType<?>, EntityType<T>> i = ENTITY_TYPES.register(name, () -> EntityType.Builder.of(entity, MobCategory.CREATURE).sized(width, height).build(name));

        //ItemRegistry.makeSpawnEggFor(TEST_ENTITY, primaryEggColor, secondaryEggColor, new Item.Properties());
        return ENTITY_TYPES.register(name, () -> EntityType.Builder.of(entity, MobCategory.CREATURE).sized(width, height).build(name));
    }

    public static void register(IEventBus modEventBus) {
        ENTITY_TYPES.register(modEventBus);
    }
}
