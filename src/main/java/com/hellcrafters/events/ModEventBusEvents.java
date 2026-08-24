package com.hellcrafters.events;

import com.github.darkpred.morehitboxes.api.GeckoLibMultiPartEntity;
import com.github.darkpred.morehitboxes.api.MultiPart;
import com.github.darkpred.morehitboxes.internal.HitboxDataLoader;
import com.hellcrafters.HellCrafters;
import com.hellcrafters.entities.ModEntities;
import com.hellcrafters.entities.test_entity.TestEntity;
import com.hellcrafters.registry.EntityRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Supplier;
import java.util.stream.Collectors;


@EventBusSubscriber(modid = HellCrafters.MODID)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {

        // dynamically iterating through every ENTITY_TYPE in the registry, adding default attributes to each
        for (Supplier<? extends EntityType<?>> i : EntityRegistry.ENTITY_LIST) {
            try {
                assert i.get().getBaseClass().getMethod("createAttributes") != null;
            } catch (Exception ignored) {
                HellCrafters.LOGGER.warn("createAttributes method not found");
            }
            if(i.get().getBaseClass().getMethod("createAttributes", ))
            event.put(i.get().getBaseClass()., i.get().createAttributes());
        }



        event.put(ModEntities.TEST_ENTITY.get(), TestEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.EntityInteract event) {
        if(event.getSide().isServer()) {
            if(event.getItemStack().is(Items.STICK))
                if(event.getTarget() instanceof GeckoLibMultiPartEntity<?> entity) {
                    HellCrafters.LOGGER.info("Entity Type: " + entity);
                    HellCrafters.LOGGER.info("Entity Parts: " + entity.getEntityHitboxData().getCustomParts().stream().map(p -> p.getEntity().getX()).toList());
                    //HellCrafters.LOGGER.info(HitboxDataLoader.HITBOX_DATA.getHitboxes(EntityType.getKey(EntityType.byString("TEST_ENTITY").orElseThrow())).stream().toString());
                    HellCrafters.LOGGER.info(HitboxDataLoader.HITBOX_DATA.getHitboxes(EntityType.getKey(ModEntities.TEST_ENTITY.get())).toString());
                }
        }
    }

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        //HellCrafters.LOGGER.info(event.getEntity().toString());
    }
}
