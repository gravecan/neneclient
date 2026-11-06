package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.event.PushEvent;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingBobberEntity.class)
public class FishingBobberEntityMixin {
   @Inject(method = "pullHookedEntity", at = @At("HEAD"), cancellable = true)
   private void pullHookedEntity(Entity entity, CallbackInfo callbackInfo) {
      if (entity instanceof ClientPlayerEntity) {
         PushEvent pushEvent = new PushEvent(PushEvent.State318.FISHING_HOOK);
         EventBus.post(pushEvent);
         if (pushEvent.checkCondition_0596()) {
            callbackInfo.cancel();
         }
      }
   }
}
