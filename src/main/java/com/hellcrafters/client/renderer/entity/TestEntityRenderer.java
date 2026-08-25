package com.hellcrafters.client.renderer.entity;

import com.hellcrafters.client.model.entity.TestEntityModel;
import com.hellcrafters.entity.TestEntity;
import dev.xylonity.knightlib.client.entity.layer.BoneHitboxLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TestEntityRenderer extends GeoEntityRenderer<TestEntity> {
    public TestEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new TestEntityModel());
        addRenderLayer(new BoneHitboxLayer<>(this, TestEntity::getBoneHitboxManager));
    }


    //@Override
    //public ResourceLocation getTextureLocation(TestEntity entity) {
    //    return ResourceLocation.fromNamespaceAndPath(HellCrafters.MODID, "textures/entity/test_entity/test.png");
    //}
}
