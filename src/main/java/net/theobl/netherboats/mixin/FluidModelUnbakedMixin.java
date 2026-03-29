package net.theobl.netherboats.mixin;

import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(FluidModel.Unbaked.class)
public abstract class FluidModelUnbakedMixin {
    @ModifyArgs(method = "<init>(Lnet/minecraft/client/resources/model/sprite/Material;Lnet/minecraft/client/resources/model/sprite/Material;Lnet/minecraft/client/resources/model/sprite/Material;Lnet/neoforged/neoforge/client/fluid/FluidTintSource;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/FluidModel$Unbaked;<init>(Lnet/minecraft/client/resources/model/sprite/Material;Lnet/minecraft/client/resources/model/sprite/Material;Lnet/minecraft/client/resources/model/sprite/Material;Lnet/neoforged/neoforge/client/fluid/FluidTintSource;Lnet/neoforged/neoforge/client/fluid/CustomFluidRenderer;)V"))
    private static void makeLavaTranslucent(Args args) {
        Material fluidStill = args.get(0);
        Material fluidFlow = args.get(1);
        
        if(fluidStill.sprite().equals(Identifier.withDefaultNamespace("block/lava_still"))) {
            args.set(0, fluidStill.withForceTranslucent(true));
        }
        if(fluidFlow.sprite().equals(Identifier.withDefaultNamespace("block/lava_flow"))) {
            args.set(1, fluidFlow.withForceTranslucent(true));
        }
    }
}
