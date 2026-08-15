package com.hellcrafters.entities.test_entity;

import com.hellcrafters.HellCrafters;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TestEntityRenderer extends GeoEntityRenderer<TestEntity> {
    public TestEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new TestEntityModel());
    }


    //@Override
    //public ResourceLocation getTextureLocation(TestEntity entity) {
    //    return ResourceLocation.fromNamespaceAndPath(HellCrafters.MODID, "textures/entity/test_entity/test.png");
    //}
}
