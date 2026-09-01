package com.hellcrafters.mixin;

import com.hellcrafters.HellCrafters;
import com.vicmatskiv.pointblank.util.HitScan;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.List;
import java.util.function.Predicate;

@Mixin(HitScan.class)
public class HitScanMixin {

    @Inject(method = "getNearestObjectInCrosshair(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/phys/Vec3)", at = @At("HEAD"), cancellable = true)
    private static void hellcrafters$getNearestObjectInCrosshair(LivingEntity player, Vec3 startPos, Vec3 directionVector, float partialTicks, double maxDistance, Predicate<Block> isBreakableBlock, Predicate<Block> isPassThroughBlock, List<BlockPos> blockPosToBreakOutput) {
        HellCrafters.LOGGER.info("Hello world from mixin");
    }
}
