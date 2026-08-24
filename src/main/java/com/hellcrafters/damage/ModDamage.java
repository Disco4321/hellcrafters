package com.hellcrafters.damage;

import com.hellcrafters.HellCrafters;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;


public class ModDamage {
    public static final Logger LOGGER = LogUtils.getLogger();

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

    public static void convertSourceToValue(DamageSource source) {
    }

    public static void calcDamageMultiplier(DamageSource penetrationLevel, DamageSource armorLevel) {
        //ArmorValue penetration  = ArmorValue.valueOf(penetrationLevel.type().msgId());
        //ArmorValue armor        = ArmorValue.valueOf(armorLevel.type().msgId());
        HellCrafters.LOGGER.info("penetration value: " + penetrationLevel.getMsgId().toUpperCase());
        HellCrafters.LOGGER.info("armor value: " + armorLevel.getMsgId());
    }
}
