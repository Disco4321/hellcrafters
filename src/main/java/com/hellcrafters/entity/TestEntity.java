package com.hellcrafters.entity;


import com.hellcrafters.HellCrafters;
import dev.xylonity.knightlib.api.entity.hitbox.BoneHitbox;
import dev.xylonity.knightlib.api.entity.hitbox.BoneHitboxManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;


public class TestEntity extends HellcrafterEntity {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    private final BoneHitboxManager hitboxManager = new BoneHitboxManager(this);
    private final ResourceLocation hitBoxLocation = ResourceLocation.fromNamespaceAndPath(HellCrafters.MODID, "hitboxes/test_entity.json");




    /*@Override
    protected void setOnHitBehavior() {
        HellCrafters.LOGGER.info("Overridden setOnHitBehavior!");
    }*/

    // the big check post-hit detection calculating damage
    /*
    private final BiConsumer<BoneHitbox, Entity> onHit = (hitBox, target) -> {
        // some quick checks to not waste server cycles
        if(target.level().isClientSide) return;
        if(!(target instanceof Projectile projectile)) return;

        // determining which hitbox was hit, and determining damage accordingly
        // we know a projectile entity hit an obb at this point in time, and simply need to
        // trigger damage events specific to which hitbox/entity shot it
        HellCrafters.LOGGER.info(hitBox.getBoneName());
        EventHooks.onProjectileImpact(projectile, new EntityHitResult(this));

        //NeoForge.EVENT_BUS.post(new ProjectileImpactEvent(projectile, new EntityHitResult(this)));



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
    //};


    public TestEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);

        // adds all our OBB boneHitboxes, based off the geckolib bones
        assert this.getBoneHitboxManager() != null;
        this.getBoneHitboxManager().add(BoneHitbox.create("red_bone"));
        this.getBoneHitboxManager().add(BoneHitbox.create("green_bone"));
        this.getBoneHitboxManager().add(BoneHitbox.create("head"));
        this.getBoneHitboxManager().add(BoneHitbox.create("blue_bone"));

        // determines the onHit behavior when an entity touches another entity
        // TODO - Decide if this is the approach I want to take with dealing damage to the entity. Already sounds
        //  like I'm gonna call the hurt function manually server-side, but whether I filter through here or not idk.
        this.getBoneHitboxManager().onHit(((boneHitbox, entity) -> {
            HellCrafters.LOGGER.info(boneHitbox.getBoneName());
        }));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10000d)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 24D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 12.0F));
        //this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1.0d));
        super.registerGoals();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if(this.level().isClientSide) return super.hurt(source, amount);
        HellCrafters.LOGGER.info("Position: {}", source.getSourcePosition());
        return super.hurt(source, amount);
    }

    /**
     * The big tick method
     */
    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            this.getBoneHitboxManager().tick();
        }
    }


    // This method is called when your entity is first being used for animations, and
    // is where we define our actual animation handling
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        //controllers.add(new AnimationController<>(this, "Flying", 5, this::flyAnimController));
        controllers.add(new AnimationController<>(this, "Idle", 0, state -> state.setAndContinue(DefaultAnimations.IDLE)));
        controllers.add(new AnimationController<>(this, "Walk", 0, state -> state.setAndContinue(DefaultAnimations.WALK)));
    }

    // Boilerplate code
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return geoCache; }
    @Override
    public @Nullable BoneHitboxManager getBoneHitboxManager() { return hitboxManager; }
}