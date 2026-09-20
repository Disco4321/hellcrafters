package com.hellcrafters.mixin;

import com.hellcrafters.HellCrafters;
import com.tacz.guns.entity.EntityKineticBullet;
import com.tacz.guns.util.TacHitResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = EntityKineticBullet.class, priority = 999)
public class EntityKineticBulletMixin {


    /**
     * This method redirects the onHitEntity call within the onBulletTick method, letting us dynamically modify the
     * piercing values of the bullet, and allowing it to hit several sub parts within a minecraft entity's bounding AABB
     * @param bullet The bullet in question
     * @param result The HitResult of the entity interaction
     * @param startVec The starting global coords of the hit ray
     * @param endVec The ending global coords of the hit ray
     */
    @Redirect(method = "onBulletTick", at = @At(
            value = "INVOKE",
            target = "Lcom/tacz/guns/entity/EntityKineticBullet;onHitEntity(Lcom/tacz/guns/util/TacHitResult;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;)V"
    ))
    private void hellcrafters$onHitEntityRedirect(EntityKineticBullet bullet, TacHitResult result, Vec3 startVec, Vec3 endVec) {
        HellCrafters.LOGGER.info("onHitEntity: ");
        /*
         *  TODO - Create an Invoke Mixin in order to access the real onHitEntity() method,
         *   as that's protected and I can't quite access it from here
         */
    }

    @Redirect(method = "onHitEntity", at = @At(
            value = "INVOKE",
            target = "Lcom/tacz/guns/entity/EntityKineticBullet;tacAttackEntity(Lcom/tacz/guns/entity/EntityKineticBullet$MaybeMultipartEntity;FLorg/apache/commons/lang3/tuple/Pair;)V"
    ))
    private void hellcrafters$attackEntityRedirect(EntityKineticBullet entity, EntityKineticBullet.MaybeMultipartEntity parts, float damage, Pair<DamageSource, DamageSource> sources) {
        HellCrafters.LOGGER.info("HCAttackEntity: ");
        /*
         *  TODO - Likewise above, I need to Invoke the attackEntity() method from here when
         *   we're done modifying what I need to modify, but it's private... Sooooo deeper we go
         */
    }

    /*
    private void hellcrafters$onHitEntity(TacHitResult result, Vec3 startVec, Vec3 endVec, CallbackInfo ci) {
        HellCrafters.LOGGER.info("onHitEntity: ");
        HellCrafters.LOGGER.info("Result: {}", a);
    }

     */
}
