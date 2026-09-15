package com.hellcrafters.mixin;

import com.hellcrafters.HellCrafters;
import com.tacz.guns.entity.EntityKineticBullet;
import com.tacz.guns.util.EntityUtil;
import dev.xylonity.knightlib.api.entity.hitbox.BoneHitbox;
import dev.xylonity.knightlib.api.entity.hitbox.BoneHitboxHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Predicate;

@Mixin(value = EntityUtil.class, priority = 999)
public class EntityUtilMixin {

    private static final Predicate<Entity> PROJECTILE_TARGETS = (input) -> {
        return input != null && input.isPickable() && !input.isSpectator();
    };
    @Inject(method = "findEntityOnPath", at = @At("TAIL"))
    private static void hellcrafters$findEntityOnPath(
            Projectile bulletEntity,
            Vec3 startVec,
            Vec3 endVec,
            CallbackInfoReturnable<EntityKineticBullet.@Nullable EntityResult> ci) {

        HellCrafters.LOGGER.info("Entity List : {}", bulletEntity.level().getEntities(bulletEntity, bulletEntity.getBoundingBox().expandTowards(bulletEntity.getDeltaMovement()).inflate(1.0), PROJECTILE_TARGETS).stream().toList());
        if(ci.getReturnValue() != null) HellCrafters.LOGGER.info("Final Entity: {}", ci.getReturnValue().getEntity().toString());
    }

    //
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

        // only run this code if it's a custom OBB hitbox entity
        if(entity instanceof BoneHitboxHolder) {

            // grabs a list of every bone OBB hitbox in the entity
            List<BoneHitbox> boneHitboxes = ((BoneHitboxHolder) entity).getBoneHitboxManager().getAll().stream().toList();

            // sets up some variables to help calculations
            BoneHitbox closestBoneHitbox = null;
            double minDistance = Double.MAX_VALUE;
            double distance;

            // loops through every boneHitbox in the entity, to find the closest
            for(BoneHitbox boneHitbox : boneHitboxes) {
                distance = (boneHitbox.getCurrentOBB() != null ? boneHitbox.getCurrentOBB().rayIntersects(startVec, endVec) : -1);

                // rayIntersects returns -1 if no collision is found with current boneHitbox
                if (distance != -1) {

                    // calculates and updates the closest hitbox variables
                    //HellCrafters.LOGGER.info("Collision detected");
                    if (distance < minDistance) {
                        //HellCrafters.LOGGER.info("Updated minDistance: {}", distance);
                        closestBoneHitbox = boneHitbox;
                        minDistance = distance;
                    }
                }
            }

            // if the bullet hit a custom OBB hitbox, we'll register that as an actual hit
            if(closestBoneHitbox != null) {
                ci.setReturnValue(new EntityKineticBullet.EntityResult(entity, startVec.lerp(endVec, 0.5), false));

                HellCrafters.LOGGER.info("Final Result: {}", closestBoneHitbox.getBoneName());
                HellCrafters.LOGGER.info("            : {}", minDistance);
            } else {
                // if the bullet missed every custom hitbox, we want it to continue travelling
                ci.setReturnValue(null);
            }

            /**
             * TODO - Now that I've narrowed down exactly which OBB hitbox gets damaged, figure out how to damage it
             *  likely will require mixing in to EntityResult, and adding data to pass through. Alternatively, lets
             *  just use this mixin to cancel detection if the bullet misses any OBBs. We can assign damage elsewhere
             *  we need to dig into EntityKineticBullet$onBulletTick and EntityKineticBullet$onHitEntity
             */


            /**
             * TODO Figure out what the clipped ray-cast is required for in EntityResult, and how I can calculate
             *  something similar with the OBB boxes.
             */
        }
    }
}
