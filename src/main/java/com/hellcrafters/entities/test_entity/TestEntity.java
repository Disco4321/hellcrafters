package com.hellcrafters.entities.test_entity;


import com.github.darkpred.morehitboxes.api.*;
import com.hellcrafters.HellCrafters;
import com.hellcrafters.damage.ModDamage;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;


public class TestEntity extends PathfinderMob implements GeoEntity, GeckoLibMultiPartEntity<TestEntity> {

    // Boilerplate code
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    private final EntityHitboxData<TestEntity> hitboxData = EntityHitboxDataFactory.create(this);


    // We inherit this constructor without the bound on the generic wildcard.
    public TestEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
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


    // This method is called when your entity is first being used for animations, and
    // is where we define our actual animation handling
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        //controllers.add(new AnimationController<>(this, "Flying", 5, this::flyAnimController));
        controllers.add(new AnimationController<>(this, "Idle", 0, state -> state.setAndContinue(DefaultAnimations.IDLE)));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        ModDamage.calcDamageMultiplier(source, source);
        return super.hurt(source, amount);
    }


    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if( player.getMainHandItem().equals(new ItemStack(Items.STICK, 1)) ) {
            HellCrafters.LOGGER.info(hand.name());
            HellCrafters.LOGGER.info(this.getEntityHitboxData().getCustomParts().toString());
        }
        return super.mobInteract(player, hand);
    }

    // required by morehitboxes, gives logic for when a part is hurt
    @Override
    public boolean partHurt(MultiPart<TestEntity> multiPart, @NotNull DamageSource source, float amount) {
        return hurt(source, amount);
    }

    // Boilerplate code from geckolib and morehitboxes
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return this.geoCache; }
    @Override
    public EntityHitboxData<TestEntity> getEntityHitboxData() { return hitboxData; }
}