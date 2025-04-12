package net.theobl.netherboats.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidType;
import net.theobl.netherboats.Config;
import net.theobl.netherboats.NetherBoats;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.IntFunction;

public class ModBoatEntity extends Boat {
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(ModBoatEntity.class, EntityDataSerializers.INT);

    public ModBoatEntity(EntityType<? extends Boat> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ModBoatEntity(Level pLevel, double pX, double pY, double pZ) {
        this(ModEntities.NETHER_BOAT.get(), pLevel);
        this.setPos(pX, pY, pZ);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ID_TYPE, Type.CRIMSON.ordinal());
    }

    public Item getDropItem() {
        Item item = switch (this.getModVariant()) {
            case CRIMSON -> NetherBoats.CRIMSON_BOAT.get();
            case WARPED -> NetherBoats.WARPED_BOAT.get();
        };
        return item;
    }

    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putString("Type", this.getModVariant().getSerializedName());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.contains("Type", 8)) {
            this.setVariant(Type.byName(pCompound.getString("Type")));
        }
    }

    public void setVariant(Type pVariant) {
        this.entityData.set(DATA_ID_TYPE, pVariant.ordinal());
    }

    @NotNull
    public ModBoatEntity.Type getModVariant() {
        return Type.byId(this.entityData.get(DATA_ID_TYPE));
    }

    public static enum Type implements StringRepresentable {
        CRIMSON(Blocks.CRIMSON_PLANKS, "crimson"),
        WARPED(Blocks.WARPED_PLANKS, "warped");

        private final String name;
        private final Block planks;

        private static final IntFunction<Type> BY_ID = ByIdMap.continuous(Enum::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);

        private Type(Block pPlanks, String pName) {
            this.name = pName;
            this.planks = pPlanks;
        }

        @NotNull
        public String getSerializedName() {
            return this.name;
        }

        public String getName() {
            return this.name;
        }

        public Block getPlanks() {
            return this.planks;
        }

        public String toString() {
            return this.name;
        }

        /**
         * Get a boat type by its enum ordinal
         */
        public static Type byId(int pId) {
            return BY_ID.apply(pId);
        }

        public static Type byName(String pName) {
            Type[] type = values();
            return Arrays.stream(type).filter(t -> t.getName().equals(pName)).findFirst().orElse(type[0]);
        }
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

    public boolean isNetherWood(Type boatType){
        return ((FireBlock) Blocks.FIRE).getBurnOdds(boatType.getPlanks().defaultBlockState()) == 0;
    }
}
