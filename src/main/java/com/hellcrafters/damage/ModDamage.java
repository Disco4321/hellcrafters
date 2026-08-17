package com.hellcrafters.damage;

import com.hellcrafters.HellCrafters;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

import java.util.HashMap;
import java.util.Map;

public class ModDamage {
    // Damage type declarations
    public static final ResourceKey<DamageType> UNARMORED =
            ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(HellCrafters.MODID, "unarmored"));
    public static final ResourceKey<DamageType> LIGHT =
            ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(HellCrafters.MODID, "light"));
    public static final ResourceKey<DamageType> MEDIUM =
            ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(HellCrafters.MODID, "medium"));
    public static final ResourceKey<DamageType> HEAVY =
            ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(HellCrafters.MODID, "heavy"));
    public static final ResourceKey<DamageType> TANK =
            ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(HellCrafters.MODID, "tank"));
    public static final ResourceKey<DamageType> BUILDING =
            ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(HellCrafters.MODID, "building"));

    // Damage type comparison helper
    public static final Map<ResourceKey<DamageType>, Integer> dictionary = Map.of(
            UNARMORED,  0,
            LIGHT,      1,
            MEDIUM,     2,
            HEAVY,      3,
            TANK,       4,
            BUILDING,   5
    );

    public void calcDamageMultiplier(DamageSource source, DamageSource source2) {

    }
}
