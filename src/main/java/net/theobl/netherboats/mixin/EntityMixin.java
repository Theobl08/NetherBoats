package net.theobl.netherboats.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.theobl.netherboats.entity.ModBoatEntity;
import net.theobl.netherboats.entity.ModChestBoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    private int remainingFireTicks;

    @Shadow @Nullable public abstract Entity getVehicle();

    // Remove the fire overlay when riding the boat
    @Inject(method = "setRemainingFireTicks", at = @At("TAIL"))
    private void setRemainingFireTicks(int remainingFireTicks, CallbackInfo ci) {
        Entity vehicle = getVehicle();
        if(vehicle instanceof ModBoatEntity || vehicle instanceof ModChestBoatEntity) {
            if (((Entity) (Object) this) instanceof LivingEntity && vehicle.isInLava()) {
                this.remainingFireTicks = 0;
            }
        }
    }
}
