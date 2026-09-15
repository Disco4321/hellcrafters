package com.hellcrafters.entity;

import com.hellcrafters.HellCrafters;
import dev.xylonity.knightlib.api.entity.hitbox.BoneHitboxHolder;
import dev.xylonity.knightlib.api.entity.hitbox.BoneHitboxManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtil;

import java.util.ArrayList;
import java.util.List;

// This entity is our base for every entity in this mod. Every entity will utilize geckolib for animations,
// and at least have the ability for extended hitboxes.
public class HellcrafterEntity extends PathfinderMob implements GeoEntity, BoneHitboxHolder {

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    private final BoneHitboxManager hitboxManager = new BoneHitboxManager(this);
    protected final ResourceLocation hitboxLocation = ResourceLocation.fromNamespaceAndPath(HellCrafters.MODID, "hitboxes/test_entity.json");

    public final List<String> additionalHitboxes = new ArrayList<>();


    protected HellcrafterEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        additionalHitboxes.add("head");

    }

    /*
        The purpose of this tick method is to update the OBB hitboxes
         */
    @Override
    public void tick() {
        super.tick();


        // ensure this only runs once on server side
        if(this.level().isClientSide) return;

        // returns early if no hitboxes are added
        if(additionalHitboxes.isEmpty()) return;

        // snagging the geoModel of the entity, as it'll hopefully give us bone positions
        GeoModel<?> geoModel = RenderUtil.getGeoModelForEntity(this);

        // notably this doesn't exist in certain weird cases, like when the player is still loading in
        if(geoModel == null) {
            HellCrafters.LOGGER.warn("The geoModel for ${this} returned null");
            return;
        }




    }

    @Override
    public  boolean isPickable() { return true; } // means the main hitbox can't be targeted



    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10d)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 24D);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return this.geoCache; }
    @Override
    public @Nullable BoneHitboxManager getBoneHitboxManager() {
        return hitboxManager;
    }
}
