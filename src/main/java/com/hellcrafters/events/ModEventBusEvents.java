package com.hellcrafters.events;

import com.hellcrafters.HellCrafters;
import com.hellcrafters.registry.EntityRegistry;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;


@EventBusSubscriber(modid = HellCrafters.MODID)
public class ModEventBusEvents {
    private static long currentTick = 0;

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {

        // dynamically iterating through every ENTITY_TYPE in the registry, adding default attributes to each
        // ideally this would let me add entities without modifying this file every time,
        // but damn, java's got hands
        //for (Supplier<? extends EntityType<? extends HellCrafterMob>> i : EntityRegistry.ENTITY_LIST) {
        //    try {
        //        i.get().getBaseClass().getMethod("createAttributes");
        //        event.put(i.get().getBaseClass(), i.get().getBaseClass());
        //    } catch (Exception ignored) {
        //        HellCrafters.LOGGER.warn("createAttributes method not found");
        //    }
        //}
        EntityRegistry.registerEntityAttributes(event);
        //event.put(ModEntities.TEST_ENTITY.get(), TestEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.EntityInteract event) {
        if(event.getSide().isServer()) {
            if(event.getItemStack().is(Items.STICK))
                HellCrafters.LOGGER.info("right click!");
        }
    }

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        //HellCrafters.LOGGER.info(event.getEntity().toString());
    }

    @SubscribeEvent
    public static void onEntityTickEvent(EntityTickEvent.Post event) {
        if (event.getEntity().level().isClientSide) return;
        if (!(event.getEntity() instanceof Projectile)) return;

        //if (event.getEntity() instanceof AbstractArrow arrow)
            //HellCrafters.LOGGER.info("hello");

    }

    @SubscribeEvent
    public static void onProjectileImpactEvent(ProjectileImpactEvent event) {
        if(event.getEntity().level().isClientSide) return;
        HellCrafters.LOGGER.info(event.getRayTraceResult().getType().toString());
        HellCrafters.LOGGER.info("SOMETHING WAS HIT EVERYBODY PANIC");
    }


    /*
    An attempt in hooking into the geoentity render event in order to find decent timing in updating
    the mob's hitboxes. Pretty sure this runs every frame tho instead of every tick, so it's currently waaaay too fast
    for our needs
     */
    /*
    @SubscribeEvent
    public static void onGeoEntityRenderEvent(GeoRenderEvent.Entity.Post event) {
        HellCrafters.LOGGER.info("Render successful");
        if(event.getPartialTick() != 0.0) return;
        HellCrafters.LOGGER.info("Partial Tick 0.0!");

        // filters any entity that's not ours, and doesn't have OBB hitboxes
        if(!(event.getEntity() instanceof HellcrafterEntity entity)) return;
        if(entity.hitboxBoneNames.isEmpty()) return;
        entity.hitboxBoneNames.forEach( boneName -> {
            event.getModel().getBone(boneName).get();
        });
            //event.getModel().searchForChildBone()
    }
     */
}
