package com.hellcrafters.entity;


import com.hellcrafters.HellCrafters;
import com.hellcrafters.util.Collisions;
import dev.xylonity.knightlib.api.entity.hitbox.BoneHitbox;
import dev.xylonity.knightlib.api.entity.hitbox.BoneHitboxHolder;
import dev.xylonity.knightlib.api.entity.hitbox.BoneHitboxManager;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.BiConsumer;


public class TestEntity extends PathfinderMob implements GeoEntity, BoneHitboxHolder {

    // Boilerplate code
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    private final BoneHitboxManager hitboxManager = new BoneHitboxManager(this);

    // the big check post-hit detection calculating damage
    private final BiConsumer<BoneHitbox, Entity> onHit = (hitBox, target) -> {
        // some quick checks to not waste server cycles
        if(target.level().isClientSide) return;
        if(!(target instanceof Projectile projectile)) return;

        // determining which hitbox was hit, and determining damage accordingly
        // we know a projectile entity hit an obb at this point in time, and simply need to
        // trigger damage events specific to which hitbox/entity shot it
        HellCrafters.LOGGER.info(hitBox.getBoneName());
        NeoForge.EVENT_BUS.post(new ProjectileImpactEvent(projectile, new EntityHitResult(this)));



        /*switch(hitBox.getBoneName()) {
            case "red_bone":
                HellCrafters.LOGGER.info(hitBox.getBoneName());
                NeoForge.EVENT_BUS.post(new ProjectileImpactEvent(projectile, new EntityHitResult(this)));
                break;
            case "green_bone":
                HellCrafters.LOGGER.info(hitBox.getBoneName());
                NeoForge.EVENT_BUS.post(new ProjectileImpactEvent(projectile, new EntityHitResult(this)));
                break;
            case "head":
                HellCrafters.LOGGER.info(hitBox.getBoneName());
                NeoForge.EVENT_BUS.post(new ProjectileImpactEvent(projectile, new EntityHitResult(this)));
                break;
            case "blue_bone":
                HellCrafters.LOGGER.info(hitBox.getBoneName());
                NeoForge.EVENT_BUS.post(new ProjectileImpactEvent(projectile, new EntityHitResult(this)));
                break;
            default:
                HellCrafters.LOGGER.warn("No bone name found in onHit function!");
        }*/
    };


    public TestEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        hitboxManager.onHit(onHit);
        hitboxManager.add(BoneHitbox.create("red_bone", 1)
                .filter(Collisions.projectileCollisionFilter)
                .cooldown(2));

                /*(hitBox, target) -> {
            HellCrafters.LOGGER.info("I'm hit!");
            if( hitBox.getBoneName().equalsIgnoreCase("red_bone"))
                HellCrafters.LOGGER.info("OUCH");
        });*/
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10d)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 24D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 12.0F));
        super.registerGoals();
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            hitboxManager.tick();
        }
    }

    // This method is called when your entity is first being used for animations, and
    // is where we define our actual animation handling
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        //controllers.add(new AnimationController<>(this, "Flying", 5, this::flyAnimController));
        controllers.add(new AnimationController<>(this, "Idle", 0, state -> state.setAndContinue(DefaultAnimations.IDLE)));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        //ModDamage.calcDamageMultiplier(source, source);
        HellCrafters.LOGGER.info(String.valueOf(hitboxManager.get("red_bone").isEnabled()));
        return super.hurt(source, amount);
    }



    // Boilerplate code from geckolib, and KnightLib
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return this.geoCache; }
    @Override
    public @Nullable BoneHitboxManager getBoneHitboxManager() { return hitboxManager; }
}