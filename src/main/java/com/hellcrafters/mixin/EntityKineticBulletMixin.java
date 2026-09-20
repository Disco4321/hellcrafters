package com.hellcrafters.mixin;

import com.hellcrafters.HellCrafters;
import com.tacz.guns.entity.EntityKineticBullet;
import net.minecraft.world.damagesource.DamageSource;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = EntityKineticBullet.class, priority = 999)
public class EntityKineticBulletMixin {
    /*
    @Redirect(method = "onBulletTick", at = @At(
            value = "INVOKE",
            target = "LcoLcomm/tacz/guns/entity/EntityKineticBullet:tacAttackEntity(Lcom/tacz/guns/entity/EntityKineticBullet:MaybeMultipartEntityFLoshi/util/tuples/Pair)V"
    )/*,
    slice = @Slice(
            from = @At(value = "", target = ""),
            to = @At(value = "", target = "")
    )*//*)
    private void hellcrafters$HCattackEntity(EntityKineticBullet bullet, EntityKineticBullet.MaybeMultipartEntity entity, float damage, Pair<DamageSource, DamageSource> sources) {
        HellCrafters.LOGGER.info("onAttackEntity:");
    }*/

    @Redirect(method = "onHitEntity", at = @At(
            value = "INVOKE",
            target = "Lcom/tacz/guns/entity/EntityKineticBullet;tacAttackEntity(Lcom/tacz/guns/entity/EntityKineticBullet$MaybeMultipartEntity;FLorg/apache/commons/lang3/tuple/Pair;)V"
    ))
    private void HCAttackEntity(EntityKineticBullet entity, EntityKineticBullet.MaybeMultipartEntity parts, float damage, Pair<DamageSource, DamageSource> sources) {
        HellCrafters.LOGGER.info("HCAttackEntity: ");
    }

    /*
    private void hellcrafters$onHitEntity(TacHitResult result, Vec3 startVec, Vec3 endVec, CallbackInfo ci) {
        HellCrafters.LOGGER.info("onHitEntity: ");
        HellCrafters.LOGGER.info("Result: {}", a);
    }

     */
}
