package net.theobl.netherboats.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.theobl.netherboats.NetherBoats;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, NetherBoats.MODID);

    public static final Supplier<EntityType<ModBoatEntity>> NETHER_BOAT = ENTITY_TYPES.register("nether_boat",
            () -> EntityType.Builder.<ModBoatEntity>of(ModBoatEntity::new, MobCategory.MISC).sized(1.375f, 0.5625f).build("nether_boat"));
    public static final Supplier<EntityType<ModChestBoatEntity>> NETHER_CHEST_BOAT = ENTITY_TYPES.register("nether_chest_boat",
            () -> EntityType.Builder.<ModChestBoatEntity>of(ModChestBoatEntity::new, MobCategory.MISC).sized(1.375f, 0.5625f).build("nether_chest_boat"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
