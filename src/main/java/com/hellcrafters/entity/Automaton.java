package com.hellcrafters.entity;

import com.hellcrafters.damage.ArmorValue;
import dev.xylonity.knightlib.api.entity.hitbox.BoneHitbox;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;

import java.util.function.BiConsumer;

public abstract class Automaton extends PathfinderMob implements GeoEntity {
    public static final ArmorValue defaultArmorValue = ArmorValue.LIGHT;
    private final BiConsumer<BoneHitbox, Entity> onHit;

    protected Automaton(EntityType<? extends PathfinderMob> entityType, Level level, BiConsumer<BoneHitbox, Entity> onHit) {
        super(entityType, level);
        this.onHit = onHit;
    }



}
