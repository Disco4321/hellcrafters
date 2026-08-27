package com.hellcrafters.entity;

import com.hellcrafters.HellCrafters;
import dev.xylonity.knightlib.api.entity.hitbox.BoneHitbox;
import dev.xylonity.knightlib.api.entity.hitbox.BoneHitboxHolder;
import dev.xylonity.knightlib.api.entity.hitbox.BoneHitboxManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.BiConsumer;

public class HellcrafterEntity extends PathfinderMob implements GeoEntity, BoneHitboxHolder {

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    private final BoneHitboxManager hitboxManager = new BoneHitboxManager(this);

    private BiConsumer<BoneHitbox, Entity> onHit;
    protected Boolean mainHitboxDisabled;



    protected final ResourceLocation hitboxLocation = ResourceLocation.fromNamespaceAndPath(HellCrafters.MODID, "hitboxes/test_entity.json");
    //protected final Boolean disableMainCollision;




    protected HellcrafterEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        mainHitboxDisabled = false;
    }

    public static void loadHitboxes() {}


    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10d)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 24D);
    }

    protected void setOnHitBehavior(BiConsumer<BoneHitbox, Entity> behavior) { onHit = behavior; }
    protected BiConsumer<BoneHitbox, Entity> getOnHitBehavior() { return this.onHit; }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return this.geoCache; }
    @Override
    public @NotNull BoneHitboxManager getBoneHitboxManager() { return this.hitboxManager; }
}
