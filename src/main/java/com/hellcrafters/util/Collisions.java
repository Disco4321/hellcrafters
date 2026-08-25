package com.hellcrafters.util;

import com.hellcrafters.HellCrafters;
import dev.xylonity.knightlib.api.entity.hitbox.BoneHitbox;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class Collisions {
    public static Predicate<Entity> projectileCollisionFilter = entity -> {
        if (entity instanceof Projectile) {
            if (entity instanceof AbstractArrow) {
                //HellCrafters.LOGGER.info(entity.getEntityData().toString());
                return !entity.getPersistentData().getBoolean("inGround");
            }
            return true;
        }
        return false;
    };


    public static BiConsumer<BoneHitbox, Entity> hit = (hitBox, target) -> {
    };
}
