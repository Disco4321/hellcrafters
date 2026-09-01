package com.hellcrafters.mixin;

import com.hellcrafters.HellCrafters;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LivingEntity.class, priority = 999)
public class LivingEntityMixin {


    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void hellcrafters$tick(CallbackInfo callbackInfo) {
        HellCrafters.LOGGER.info("Hello WORLD!!!!!!!!!");
    }
}
