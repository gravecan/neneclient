package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.core.MinecraftAccess;
import aethereal.event.PushEvent;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
   @Inject(method = "isPushedByFluids", at = @At("HEAD"), cancellable = true)
   private void removePushFromFluids(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
      if ((PlayerEntity)this == MinecraftAccess.minecraft.player) {
         PushEvent pushEvent = new PushEvent(PushEvent.State318.FLUIDS);
         EventBus.post(pushEvent);
         if (pushEvent.checkCondition_0596()) {
            callbackInfoReturnable.setReturnValue(false);
         }
      }
   }
}
