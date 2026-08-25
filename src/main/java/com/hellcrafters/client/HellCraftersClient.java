package com.hellcrafters.client;

import com.hellcrafters.HellCrafters;
import com.hellcrafters.client.renderer.entity.TestEntityRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import static com.hellcrafters.registry.EntityRegistry.*;

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
}
