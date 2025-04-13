package net.theobl.netherboats.entity;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.theobl.netherboats.NetherBoats;

public class ModModelLayers {
    public static final ModelLayerLocation CRIMSON_BOAT_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(NetherBoats.MODID, "boat/crimson"), "main");
    public static final ModelLayerLocation WARPED_BOAT_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(NetherBoats.MODID, "boat/warped"), "main");
    public static final ModelLayerLocation CRIMSON_CHEST_BOAT_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(NetherBoats.MODID, "chest_boat/crimson"), "main");
    public static final ModelLayerLocation WARPED_CHEST_BOAT_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(NetherBoats.MODID, "chest_boat/warped"), "main");
}
