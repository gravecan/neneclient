package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.event.RemovalsEvent;
import net.minecraft.block.enums.CameraSubmersionType;
import net.minecraft.client.render.fog.DarknessEffectFogModifier;
import net.minecraft.client.render.fog.StatusEffectFogModifier;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffectFogModifier.class)
public class BackgroundRendererMixin {
   @Inject(method = "shouldApply", at = @At("RETURN"), cancellable = true)
   private void onShouldApply(CameraSubmersionType cameraSubmersionType, Entity entity, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
      RemovalsEvent.State319 state319 = this instanceof DarknessEffectFogModifier ? RemovalsEvent.State319.DARKNESS : RemovalsEvent.State319.BLINDNESS;
      RemovalsEvent removalsEvent = new RemovalsEvent(state319);
      EventBus.post(removalsEvent);
      if (removalsEvent.checkCondition_0596()) {
         callbackInfoReturnable.setReturnValue(Boolean.FALSE);
      }
   }
}
