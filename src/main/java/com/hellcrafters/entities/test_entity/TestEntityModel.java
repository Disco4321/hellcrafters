package com.hellcrafters.entities.test_entity;

import com.hellcrafters.HellCrafters;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.GeoModel;

public class TestEntityModel extends GeoModel<TestEntity> {
    //public static final ModelLayerLocation LAYER_LOCATION =
    //        new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(HellCrafters.MODID, "test_entity"), "main");

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
        return null;
    }
}
