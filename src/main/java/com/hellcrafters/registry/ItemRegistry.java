package com.hellcrafters.registry;

import com.hellcrafters.HellCrafters;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public final class ItemRegistry {
    // Create a Deferred Register to hold Items which will all be registered under the "hellcrafters" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(HellCrafters.MODID);

    // Creates a new food item with the id "hellcrafters:example_id", nutrition 1 and saturation 2
    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEdible().nutrition(1).saturationModifier(2f).build()));













    // --------------------------------------------------
    // Spawn Egg Item Helpers
    //
    // --------------------------------------------------

    // another helper method shortening and consolidating code to make spawn eggs
    //public static <T extends Mob> Supplier<SpawnEggItem> makeSpawnEggFor(Supplier<EntityType<T>> entityType, int primaryEggColour, int secondaryEggColour, Item.Properties itemProperties) {
    //    return pls
    //}



    // --------------------------------------------------
    // Creative Tab Registration Section
    //
    // --------------------------------------------------

    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "hellcrafters" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HellCrafters.MODID);

    // Creates a creative tab with the id "hellcrafters:example_tab" for the example item, that is placed after the combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> HELLCRAFTERS_TAB = ItemRegistry.CREATIVE_MODE_TABS.register("hellcrafters_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.hellcrafters")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(Items.STICK::getDefaultInstance)
            .displayItems((parameters, output) -> {
                output.accept(EXAMPLE_ITEM.get());// Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());
}
