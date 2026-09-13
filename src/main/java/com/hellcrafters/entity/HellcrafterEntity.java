package com.hellcrafters.entity;

import com.hellcrafters.HellCrafters;
import dev.customhitboxlib.api.ICustomMultipart;
import dev.customhitboxlib.api.PartDefinition;
import dev.xylonity.knightlib.api.entity.hitbox.BoneHitboxHolder;
import dev.xylonity.knightlib.api.entity.hitbox.BoneHitboxManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.entity.PartEntity;
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
public class HellcrafterEntity extends PathfinderMob implements GeoEntity, BoneHitboxHolder, ICustomMultipart {

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

        // snagging the geoModel of the entity, as it'll give us bone positions
        GeoModel<?> geoModel = RenderUtil.getGeoModelForEntity(this);

        // notably this doesn't exist in certain weird cases, like when the player is still loading in
        if(geoModel == null) {
            HellCrafters.LOGGER.warn("The geoModel for ${this} returned null");
            return;
        }

        /*
        // loops through every bone name with an added hitbox, updating the position of the OBB hitbox
        additionalHitboxes.forEach(boneName -> {
            HellCrafters.LOGGER.info(this.stringUUID);
            HellCrafters.LOGGER.info(boneName);
            //HellCrafters.LOGGER.info(geoModel.getBone(boneName).get().toString());
            //HellCrafters.LOGGER.info(geoCache.getManagerForId(this.getId()).);
            //HellCrafters.LOGGER.info(boneSnapshot);//.getBone().getScaleVector().toString());
            //HellCrafters.LOGGER.info(boneSnapshot.getBone().getWorldSpaceMatrix().toString());
            //OBB.updateHitbox(geoCache.getManagerForId(this.getId()).getBoneSnapshotCollection().get(boneName));
        });
         */
    }

    @Override
    public  boolean isPickable() { return false; } // means the main hitbox can't be targeted



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

    /**
     * @return Returns a list of PartEntities
     */
    @Override
    public @Nullable PartEntity<?>[] getCustomParts() {
        return new PartEntity[0];
    }

    /**
     *  This method by defualt repositions all parts using their positioners
     */
    @Override
    public void tickCustomParts() {
    }

    /**
     * Takes in a String name and a PartDefinition record to add a custom part to the model
     * @param name
     * @param partDefinition
     */
    @Override
    public void addCustomPart(String name, PartDefinition partDefinition) {

    }

    /**
     * @param s
     */
    @Override
    public void removeCustomPart(String s) {

    }

    /**
     * Determines whether
     * @return
     */
    @Override
    public boolean hasCustomParts() {
        return true;
    }

    /**
     * Sets whether the main hitbox can be targeted
     * @param b
     */
    @Override
    public void setMainHitboxPickable(boolean b) {}

    /**
     * @return Whether the main hitbox is targetable
     */
    @Override
    public boolean isMainHitboxPickable() {
        return false;
    }

    /**
     * Sets whether the main hitbox pushes entities
     * @param b
     */
    @Override
    public void setMainHitboxPushable(boolean b) {}

    /**
     * Whether the main hitbox pushes entities
     * @return
     */
    @Override
    public boolean isMainHitboxPushable() {
        return true;
    }

    /**
     * Sets whether the main hitbox collides with blocks
     * @param b
     */
    @Override
    public void setMainHitboxCollision(boolean b) {

    }

    /**
     * Whether the main hitbox collides with blocks
     * @return
     */
    @Override
    public boolean isMainHitboxCollision() {
        return true;
    }

}
