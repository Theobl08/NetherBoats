package net.theobl.netherboats.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidType;
import net.theobl.netherboats.Config;
import net.theobl.netherboats.NetherBoats;
import org.jetbrains.annotations.NotNull;

public class ModChestBoatEntity extends ChestBoat {
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(ModChestBoatEntity.class, EntityDataSerializers.INT);

    public ModChestBoatEntity(EntityType<? extends Boat> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ModChestBoatEntity(Level pLevel, double pX, double pY, double pZ) {
        this(ModEntities.NETHER_CHEST_BOAT.get(), pLevel);
        this.setPos(pX, pY, pZ);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ID_TYPE, ModBoatEntity.Type.CRIMSON.ordinal());
    }

    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putString("Type", this.getModVariant().getSerializedName());
        this.addChestVehicleSaveData(pCompound, this.registryAccess());
    }

    protected void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.contains("Type", 8)) {
            this.setVariant(ModBoatEntity.Type.byName(pCompound.getString("Type")));
        }
        this.readChestVehicleSaveData(pCompound, this.registryAccess());
    }

    public Item getDropItem() {
        Item item = switch (this.getModVariant()) {
            case CRIMSON -> NetherBoats.CRIMSON_CHEST_BOAT.get();
            case WARPED -> NetherBoats.WARPED_CHEST_BOAT.get();
        };
        return item;
    }

    public void setVariant(ModBoatEntity.Type pVariant) {
        this.entityData.set(DATA_ID_TYPE, pVariant.ordinal());
    }

    @NotNull
    public ModBoatEntity.Type getModVariant() {
        return ModBoatEntity.Type.byId(this.entityData.get(DATA_ID_TYPE));
    }

    @Override
    public boolean fireImmune() {
        return this.isNetherWood(this.getModVariant()) && Config.canNetherBoatUsedOnLava;
    }


    @Override
    public boolean canBoatInFluid(FluidState state) {
        return state.supportsBoating(this) ||
                (state.getFluidType().equals(Fluids.LAVA.getFluidType()) && this.isNetherWood(this.getModVariant()) && Config.canNetherBoatUsedOnLava);
    }

    @Override
    public boolean canBoatInFluid(FluidType type) {
        return type.supportsBoating(this) ||
                (type.equals(Fluids.LAVA.getFluidType()) && this.isNetherWood(this.getModVariant()) && Config.canNetherBoatUsedOnLava);
    }

    public boolean isNetherWood(ModBoatEntity.Type boatType){
        return ((FireBlock) Blocks.FIRE).getBurnOdds(boatType.getPlanks().defaultBlockState()) == 0;
    }
}
