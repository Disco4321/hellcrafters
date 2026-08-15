package com.hellcrafters.events;

import com.hellcrafters.HellCrafters;
import com.hellcrafters.entities.ModEntities;
import com.hellcrafters.entities.test_entity.TestEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;



@EventBusSubscriber(modid = HellCrafters.MODID)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.TEST_ENTITY.get(), TestEntity.createAttributes().build());
    }
}
