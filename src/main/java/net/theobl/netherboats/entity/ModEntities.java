package net.theobl.netherboats.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.theobl.netherboats.NetherBoats;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, NetherBoats.MODID);

    public static final Supplier<EntityType<ModBoatEntity>> CRIMSON_BOAT = ENTITY_TYPES.register("crimson_boat",
        () -> EntityType.Builder.of(boatFactory(NetherBoats.CRIMSON_BOAT), MobCategory.MISC).noLootTable().sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10).build(entityId("crimson_boat")));
    public static final Supplier<EntityType<ModChestBoatEntity>> CRIMSON_CHEST_BOAT = ENTITY_TYPES.register("crimson_chest_boat",
            () -> EntityType.Builder.of(chestBoatFactory(NetherBoats.CRIMSON_CHEST_BOAT), MobCategory.MISC).noLootTable().sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10).build(entityId("crimson_chest_boat")));
    public static final Supplier<EntityType<ModBoatEntity>> WARPED_BOAT = ENTITY_TYPES.register("warped_boat",
        () -> EntityType.Builder.of(boatFactory(NetherBoats.WARPED_BOAT), MobCategory.MISC).noLootTable().sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10).build(entityId("warped_boat")));
    public static final Supplier<EntityType<ModChestBoatEntity>> WARPED_CHEST_BOAT = ENTITY_TYPES.register("warped_chest_boat",
            () -> EntityType.Builder.of(chestBoatFactory(NetherBoats.WARPED_CHEST_BOAT), MobCategory.MISC).noLootTable().sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10).build(entityId("warped_chest_boat")));


    private static ResourceKey<EntityType<?>> entityId(String name) {
        return ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(NetherBoats.MODID, name));
    }

    private static EntityType.EntityFactory<ModBoatEntity> boatFactory(Supplier<Item> boatItemGetter) {
        return (entityType, level) -> new ModBoatEntity(entityType, level, boatItemGetter);
    }

    private static EntityType.EntityFactory<ModChestBoatEntity> chestBoatFactory(Supplier<Item> boatItemGetter) {
        return (entityType, level) -> new ModChestBoatEntity(entityType, level, boatItemGetter);
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
