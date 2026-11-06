package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.event.RemovalsEvent;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net/minecraft/client/gui/hud/InGameHud$HeartType")
public class HeartTypeMixin {
   @ModifyExpressionValue(
      method = "fromPlayerState",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/player/PlayerEntity;hasStatusEffect(Lnet/minecraft/registry/entry/RegistryEntry;)Z",
         ordinal = 1
      )
   )
   private static boolean fromPlayerState(boolean bl) {
      if (!bl) {
         return false;
      }

      RemovalsEvent removalsEvent = new RemovalsEvent(RemovalsEvent.State319.BLACK_HEARTS);
      EventBus.post(removalsEvent);
      return !removalsEvent.checkCondition_0596();
   }
}
