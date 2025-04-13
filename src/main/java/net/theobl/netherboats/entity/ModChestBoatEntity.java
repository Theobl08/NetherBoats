package net.theobl.netherboats.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractChestBoat;
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
import java.util.function.Supplier;

public class ModChestBoatEntity extends AbstractChestBoat {

    public ModChestBoatEntity(EntityType<? extends AbstractChestBoat> entityType, Level level, Supplier<Item> dropItem) {
        super(entityType, level, dropItem);
    }

    @Override
    protected double rideHeight(EntityDimensions dimensions) {
        return (double)(dimensions.height() / 3.0F);
    }

    @Override
    public boolean fireImmune() {
        return Config.canNetherBoatUsedOnLava;
    }


    @Override
    public boolean canBoatInFluid(FluidState state) {
        return state.supportsBoating(this) ||
                (state.getFluidType().equals(Fluids.LAVA.getFluidType()) && Config.canNetherBoatUsedOnLava);
    }

    @Override
    public boolean canBoatInFluid(FluidType type) {
        return type.supportsBoating(this) ||
                (type.equals(Fluids.LAVA.getFluidType()) && Config.canNetherBoatUsedOnLava);
    }
}
