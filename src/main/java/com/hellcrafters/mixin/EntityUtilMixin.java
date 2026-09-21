package com.hellcrafters.mixin;

import com.hellcrafters.HellCrafters;
import com.hellcrafters.entity.HellcrafterEntity;
import com.hellcrafters.registry.CollisionUtil;
import com.tacz.guns.entity.EntityKineticBullet;
import com.tacz.guns.util.EntityUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EntityUtil.class, priority = 999)
public class EntityUtilMixin {

    /**
     * This method runs on every single entity within a bullet's AABB delta inflation check. We're mixing in to
     * just before the return call in order to calculate whether an OBB was actually hit within an entity's AABB
     * bounding hitbox.
     * @param bulletEntity  The bullet
     * @param entity        The entity whose AABB was contacted
     * @param startVec      The starting position of the bullet in global coords
     * @param endVec        Pretty sure this is the bullet's coords after the delta movement
     * @param ci            The return value we can modify
     */
    @Inject(method = "getHitResult", at = @At("TAIL"), cancellable = true)
    private static void hellcrafters$getHitResult(
            Projectile bulletEntity,
            Entity entity,
            Vec3 startVec,
            Vec3 endVec,
            CallbackInfoReturnable<EntityKineticBullet.@Nullable EntityResult> ci) {

        // AABB inflation checks cover a massive area, even with the size of the bullet. So if the initial ray check
        // didn't connect with any vanilla hitboxes, we can ignore this further check
        if(ci.getReturnValue() == null) return;

        // OBB hitboxes don't exist on the client, which I'm sure will have ramifications later
        if(entity.level().isClientSide) return;

        // ensures this check only applies on our custom entities
        if(!(entity instanceof HellcrafterEntity)) return;

        if(CollisionUtil.getUnsortedRaycast(entity, startVec, endVec)) {
            // tests if the bullet collides with any OBB boxes, updating the return value if so
            ci.setReturnValue(new EntityKineticBullet.EntityResult(entity, startVec.lerp(endVec, 0.5), false));
            HellCrafters.LOGGER.info("getHitResult: Detected collision");
        } else {
            // negates the interaction, allowing the bullet to pass cleanly through
            ci.setReturnValue(null);
        }
        /*
         * TODO Figure out what the clipped ray-cast is required for in EntityResult, and how I can calculate
         *  something similar with the OBB boxes.
         */
    }
}
