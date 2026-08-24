package com.hellcrafters.entities;

import com.hellcrafters.damage.ArmorValue;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;

public abstract class Automaton extends PathfinderMob implements GeoEntity {
    public static final ArmorValue defaultArmorValue = ArmorValue.LIGHT;

    protected Automaton(EntityType<? extends PathfinderMob> entityType, Level level) {super(entityType, level);}

}
