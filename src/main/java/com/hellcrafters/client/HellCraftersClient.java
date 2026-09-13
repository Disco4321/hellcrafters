package com.hellcrafters.client;

import com.hellcrafters.HellCrafters;
import com.hellcrafters.client.renderer.entity.TestEntityRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.joml.Vector3f;

import static com.hellcrafters.registry.EntityRegistry.TEST_ENTITY;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = HellCrafters.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = HellCrafters.MODID, value = Dist.CLIENT)
public class HellCraftersClient {
    public HellCraftersClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        HellCrafters.LOGGER.info("HELLO FROM CLIENT SETUP");
        HellCrafters.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

        HellCrafters.LOGGER.info("Registering EntityRenderers...");
        EntityRenderers.register(TEST_ENTITY.get(), TestEntityRenderer::new);
    }

    @SubscribeEvent
    static void onRender(RenderLivingEvent.Post<?, ?> event) {

        // setting up our main variables
        VertexConsumer buffer = event.getMultiBufferSource().getBuffer(RenderType.LINES);
        PoseStack poseStack = event.getPoseStack();

        // creates a new pose on top of the poseStack
        poseStack.pushPose();

        // Creates a box around the entity
        AABB box = new AABB(0,0, 0, 4, 4, 4);
        LevelRenderer.renderLineBox(poseStack, buffer, box, 0.0f, 0.0f, 0.0f, 1.0f);

        // Should add a ray-cast line in a certain direction, 10 blocks up
        renderLine(poseStack, buffer, new Vector3f(), new Vector3f(0,1,0));

        // pops the latest Pose off the PoseStack
        poseStack.popPose();
    }

    public static void renderLine(PoseStack poseStack, VertexConsumer consumer, Vector3f startPos, Vector3f normal) {
        PoseStack.Pose posestack$pose = poseStack.last();
        consumer.addVertex(posestack$pose, startPos).setColor(0f,0f,0f,1f).setNormal(posestack$pose, normal.x, normal.y, normal.z);
        consumer.addVertex(posestack$pose, startPos.add(new Vector3f(0,10,3))).setColor(0f,0f,0f,1f).setNormal(posestack$pose, normal.x, normal.y, normal.z);
    }
}
