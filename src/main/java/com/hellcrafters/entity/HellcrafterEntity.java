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
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.BiConsumer;

// This entity is our base for every entity in this mod. Every entity will utilize geckolib for animations,
// and at least have the ability for extended hitboxes.
public class HellcrafterEntity extends PathfinderMob implements GeoEntity, BoneHitboxHolder {

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    private final BoneHitboxManager hitboxManager = new BoneHitboxManager(this);

    protected BiConsumer<BoneHitbox, Entity> onHit;
    protected Boolean mainHitboxDisabled;



    protected final ResourceLocation hitboxLocation = ResourceLocation.fromNamespaceAndPath(HellCrafters.MODID, "hitboxes/test_entity.json");
    //protected final Boolean disableMainCollision;




    protected HellcrafterEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        mainHitboxDisabled = false;
        setOnHitBehavior();

    }

    public static void loadHitboxes() {}


    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10d)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 24D);
    }

    protected void setOnHitBehavior() {
        onHit = (BoneHitbox hitbox, Entity entity) -> {
            if(entity.level().isClientSide) return;

            HellCrafters.LOGGER.info("Hitbox hit: "+hitbox.getBoneName());
            HellCrafters.LOGGER.info("Entity hitting: "+entity.getName());
            HellCrafters.LOGGER.info("Entity classname: "+entity.getClass().toString());
        };
    }
    protected BiConsumer<BoneHitbox, Entity> getOnHitBehavior() { return this.onHit; }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return this.geoCache; }
    @Override
    public @NotNull BoneHitboxManager getBoneHitboxManager() { return this.hitboxManager; }
}
