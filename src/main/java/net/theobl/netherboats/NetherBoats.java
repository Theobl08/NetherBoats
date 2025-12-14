package net.theobl.netherboats;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.theobl.netherboats.entity.*;
import org.slf4j.Logger;

import static net.minecraft.world.item.CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(NetherBoats.MODID)
public class NetherBoats {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "netherboats";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // Create a Deferred Register to hold Items which will all be registered under the "netherboats" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> CRIMSON_BOAT = ITEMS.registerItem("crimson_boat",
            p -> new BoatItem(ModEntities.CRIMSON_BOAT.get(), p), p -> p.stacksTo(1));

    public static final DeferredItem<Item> CRIMSON_CHEST_BOAT = ITEMS.registerItem("crimson_chest_boat",
            p -> new BoatItem(ModEntities.CRIMSON_CHEST_BOAT.get(), p), p -> p.stacksTo(1));

    public static final DeferredItem<Item> WARPED_BOAT = ITEMS.registerItem("warped_boat",
            p -> new BoatItem(ModEntities.WARPED_BOAT.get(), p), p-> p.stacksTo(1));

    public static final DeferredItem<Item> WARPED_CHEST_BOAT = ITEMS.registerItem("warped_chest_boat",
            p -> new BoatItem(ModEntities.WARPED_CHEST_BOAT.get(), p), p ->p.stacksTo(1));

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public NetherBoats(IEventBus modEventBus, ModContainer modContainer) {
        ModEntities.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        ITEMS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (NetherBoats) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("Registering Crimson and Warped boats");
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.insertAfter(Items.BAMBOO_CHEST_RAFT.getDefaultInstance(), CRIMSON_BOAT.get().getDefaultInstance(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(CRIMSON_BOAT.get().getDefaultInstance(), CRIMSON_CHEST_BOAT.get().getDefaultInstance(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(CRIMSON_CHEST_BOAT.get().getDefaultInstance(), WARPED_BOAT.get().getDefaultInstance(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(WARPED_BOAT.get().getDefaultInstance(), WARPED_CHEST_BOAT.get().getDefaultInstance(), PARENT_AND_SEARCH_TABS);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("Welcome to a world with nether boats !");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            EntityRenderers.register(ModEntities.CRIMSON_BOAT.get(), context -> new BoatRenderer(context, ModModelLayers.CRIMSON_BOAT_LAYER));
            EntityRenderers.register(ModEntities.CRIMSON_CHEST_BOAT.get(), context -> new BoatRenderer(context, ModModelLayers.CRIMSON_CHEST_BOAT_LAYER));
            EntityRenderers.register(ModEntities.WARPED_BOAT.get(), context -> new BoatRenderer(context, ModModelLayers.WARPED_BOAT_LAYER));
            EntityRenderers.register(ModEntities.WARPED_CHEST_BOAT.get(), context -> new BoatRenderer(context, ModModelLayers.WARPED_CHEST_BOAT_LAYER));

            ItemBlockRenderTypes.setRenderLayer(Fluids.LAVA, ChunkSectionLayer.TRANSLUCENT);
            ItemBlockRenderTypes.setRenderLayer(Fluids.FLOWING_LAVA, ChunkSectionLayer.TRANSLUCENT);
        }

        @SubscribeEvent
        public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event){
            event.registerLayerDefinition(ModModelLayers.CRIMSON_BOAT_LAYER, BoatModel::createBoatModel);
            event.registerLayerDefinition(ModModelLayers.WARPED_BOAT_LAYER, BoatModel::createBoatModel);
            event.registerLayerDefinition(ModModelLayers.CRIMSON_CHEST_BOAT_LAYER, BoatModel::createChestBoatModel);
            event.registerLayerDefinition(ModModelLayers.WARPED_CHEST_BOAT_LAYER, BoatModel::createChestBoatModel);
        }
    }
}
