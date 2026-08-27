package com.hellcrafters.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;

import java.util.function.Predicate;

public class Collisions {
    public static Predicate<Entity> projectileCollisionFilter = entity -> {
        // cancels early for various specific entity type checks
        // we only want things that should interact with hitboxes in a way
        // that physically affects them
        if(!(entity instanceof Projectile)) return false;

        Boolean returnValue = false;

        // prevents detection if the arrow's hitbox is currently in the ground
        if (entity instanceof AbstractArrow arrow) {
            returnValue = !arrow.saveWithoutId(new CompoundTag()).getBoolean("inGround");
        }

        return returnValue;
    };
}
