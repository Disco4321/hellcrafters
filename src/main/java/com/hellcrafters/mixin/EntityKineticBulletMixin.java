package com.hellcrafters.mixin;

import com.hellcrafters.HellCrafters;
import com.hellcrafters.entity.HellcrafterEntity;
import com.hellcrafters.util.CollisionUtil;
import com.tacz.guns.entity.EntityKineticBullet;
import com.tacz.guns.util.TacHitResult;
import dev.xylonity.knightlib.api.entity.hitbox.BoneHitbox;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.Map;

@Mixin(EntityKineticBullet.class)
interface EntityKineticBulletAccessor {
    @Invoker("onHitEntity")
    void hellcrafters$onHitEntity(TacHitResult result, Vec3 startVec, Vec3 endVec);

    @Invoker("tacAttackEntity")
    void hellcrafters$tacAttackEntity(EntityKineticBullet.MaybeMultipartEntity parts, float damage, Pair<DamageSource, DamageSource> sources);
}


@Mixin(value = EntityKineticBullet.class, priority = 999)
public class EntityKineticBulletMixin {
    @Shadow int pierce;
    @Shadow int owner;
    @Shadow ResourceLocation gunId;
    @Shadow ResourceLocation gunDisplayId;

    /**
     * This method redirects the onHitEntity call within the onBulletTick method, letting us dynamically modify the
     * piercing values of the bullet, and allowing it to hit several sub parts within a minecraft entity's bounding AABB
     * This also allows us to calculate which part is affected, and damage that part individually
     * @param bullet The bullet in question
     * @param result The HitResult of the entity interaction
     * @param startVec The starting global coordinates of the hit ray
     * @param endVec The ending global coordinates of the hit ray
     */
    @Redirect(method = "onBulletTick", at = @At(
            value = "INVOKE",
            target = "Lcom/tacz/guns/entity/EntityKineticBullet;onHitEntity(Lcom/tacz/guns/util/TacHitResult;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;)V"
    ))
    private void hellcrafters$onHitEntityRedirect(EntityKineticBullet bullet, TacHitResult result, Vec3 startVec, Vec3 endVec) {
        HellCrafters.LOGGER.info("onHitEntity: ");

        // forwards the onHitEntity call to the actual method if this isn't a relevant entity
        if( !(result.getEntity() instanceof HellcrafterEntity)) {
            HellCrafters.LOGGER.info("Did not hit a Hellcrafter Entity");
            ((EntityKineticBulletAccessor) bullet).hellcrafters$onHitEntity(result, startVec, endVec);
            return;
        }

        Entity entity = result.getEntity();

        // calculate how many parts it passes through
        List<Map.Entry<BoneHitbox, Double>> boneHitboxes = CollisionUtil.getSortedRaycast(entity, startVec, endVec);

        // Since we're taking over this entire method, we gotta follow some boilerplate code
        //EntityHurtByGunEvent.Pre preEvent = new EntityHurtByGunEvent.Pre(this, entity, attacker, gunId, gunDisplayId, damage, sources, false, 0, LogicalSide.SERVER);

        /*
        TODO - If i just can have access to the hit vector within the neoforge event, we should be able to calculate
         everything we need to deal damage. Might be able to access widen bullet's private variables and use them in
         a different class file
         */

        // pulls specific info about each piece
        boneHitboxes.forEach(boneHitbox -> {
            // determines the armor level of the part, and compares to bullet's armor piercing

            // detects whether bullet should pierce if it has a pierce value

            // modifies private pierce variable of bullet
            pierce = 5;

            // calculates and applies damage to entity based on the part

        });
    }

    /**
     * This may even be a red herring, if I can damage individual sub hitboxes from the other method,
     * then we're set.
     * @param bullet The bullet in question
     * @param parts The entity that's been hit
     * @param damage The amount of damage to apply
     * @param sources The pair of damage values, calculate armor penetration
     */
    @Redirect(method = "onHitEntity", at = @At(
            value = "INVOKE",
            target = "Lcom/tacz/guns/entity/EntityKineticBullet;tacAttackEntity(Lcom/tacz/guns/entity/EntityKineticBullet$MaybeMultipartEntity;FLorg/apache/commons/lang3/tuple/Pair;)V"
    ))
    private void hellcrafters$attackEntityRedirect(EntityKineticBullet bullet, EntityKineticBullet.MaybeMultipartEntity parts, float damage, Pair<DamageSource, DamageSource> sources) {
        HellCrafters.LOGGER.info("HCAttackEntity: ");


        ((EntityKineticBulletAccessor) bullet).hellcrafters$tacAttackEntity(parts, damage, sources);
    }
}
