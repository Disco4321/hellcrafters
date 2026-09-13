package com.hellcrafters.mixin;

import com.hellcrafters.HellCrafters;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LivingEntity.class, priority = 999)
public abstract class LivingEntityMixin {


    @Inject(
            method = "tick",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true)
    public void hellcraftersTick(CallbackInfo callbackInfo) {
        //HellCrafters.LOGGER.info("Hello WORLD!!!!!!!!!");
    }


    @Inject(method = "handleDamageEvent", at = @At("HEAD"), cancellable = true)
    public void hellcraftersHandleDamageEvent(CallbackInfo ci) {
        HellCrafters.LOGGER.info("OUCHERS");
    }
}
/*
@Mixin(value = ClassName.class, priority = 1000 (default))
public abstract class MixinName {

    @Inject(method = "methodName", at = @At("HEAD"), cancellable = true)
    public void mixinFunctionName(final CallbackInfo ci) {

    }

 */