package net.theobl.netherboats;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = NetherBoats.MODID)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue NETHER_BOAT_ON_LAVA = BUILDER.comment("Whether Crimson and Warped boat can be used on lava").define("canNetherBoatUsedOnInLava", true);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean canNetherBoatUsedOnLava;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        canNetherBoatUsedOnLava = NETHER_BOAT_ON_LAVA.get();
    }
}
