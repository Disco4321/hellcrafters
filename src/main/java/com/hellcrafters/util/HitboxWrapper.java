package com.hellcrafters.util;

import dev.xylonity.knightlib.api.entity.hitbox.BoneHitbox;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class HitboxWrapper {
    private final BoneHitbox boneHitbox;

    public HitboxWrapper(String boneName, @Nullable Vec3 halfExtents, boolean autoSize) {
        this.boneHitbox = BoneHitbox.create(boneName);
    }


}
