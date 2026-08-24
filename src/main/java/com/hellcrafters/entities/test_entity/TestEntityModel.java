package com.hellcrafters.entities.test_entity;

import com.hellcrafters.HellCrafters;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TestEntityModel extends GeoModel<TestEntity> {

    @Override
    public ResourceLocation getModelResource(TestEntity testEntity) {
        return ResourceLocation.fromNamespaceAndPath(HellCrafters.MODID, "geo/entity/test_entity.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TestEntity testEntity) {
        return ResourceLocation.fromNamespaceAndPath(HellCrafters.MODID, "textures/entity/test_entity.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TestEntity testEntity) {
        return ResourceLocation.fromNamespaceAndPath(HellCrafters.MODID, "animations/entity/test_entity.animation.json");
    }
}
