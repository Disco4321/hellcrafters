package com.hellcrafters.entity;

import dev.xylonity.knightlib.api.entity.hitbox.BoneHitboxHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;

// This entity is our base for every entity in this mod. Every entity will utilize geckolib for animations,
// and at least have the ability for extended hitboxes. This gives us an easy class to use instanceof checks on
public abstract class HellcrafterEntity extends PathfinderMob implements GeoEntity, BoneHitboxHolder {
    protected HellcrafterEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }



}
