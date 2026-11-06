package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.event.EventComponent096;
import net.minecraft.world.dimension.PortalManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PortalManager.class)
public abstract class PortalManagerMixin {
   @Inject(method = "isInPortal", at = @At("RETURN"), cancellable = true)
   private void onIsInPortal(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
      EventComponent096 eventComponent096 = new EventComponent096((Boolean)callbackInfoReturnable.getReturnValue());
      EventBus.post(eventComponent096);
      callbackInfoReturnable.setReturnValue(eventComponent096.isInPortal_1091());
   }
}
