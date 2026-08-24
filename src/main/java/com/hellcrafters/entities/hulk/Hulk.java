package com.hellcrafters.entities.hulk;

import com.github.darkpred.morehitboxes.api.EntityHitboxData;
import com.github.darkpred.morehitboxes.api.EntityHitboxDataFactory;
import com.github.darkpred.morehitboxes.api.GeckoLibMultiPartEntity;
import com.github.darkpred.morehitboxes.api.MultiPart;
import com.hellcrafters.entities.Automaton;
import com.hellcrafters.entities.test_entity.TestEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Hulk extends Automaton implements GeckoLibMultiPartEntity<Hulk>, GeoEntity {

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    private final EntityHitboxData<Hulk> hitboxData = EntityHitboxDataFactory.create(this);

    protected Hulk(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    // This method is called when your entity is first being used for animations
    // and is where we define our actual animation handling
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    // required by morehitboxes, gives logic for when a part is hurt
    @Override
    public boolean partHurt(MultiPart<Hulk> multiPart, @NotNull DamageSource source, float amount) {
        return hurt(source, amount);
    }

    // Boilerplate code from geckolib and morehitboxes
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return this.geoCache; }
    @Override
    public EntityHitboxData<Hulk> getEntityHitboxData() { return hitboxData; }
}
