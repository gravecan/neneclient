package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.event.EventComponent077;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
   @Inject(method = "attackEntity", at = @At("HEAD"), cancellable = true)
   private void onAttackEntity(PlayerEntity playerEntity, Entity entity, CallbackInfo callbackInfo) {
      EventComponent077 eventComponent077 = new EventComponent077(entity);
      EventBus.post(eventComponent077);
      if (eventComponent077.checkCondition_0596()) {
         callbackInfo.cancel();
      }
   }
}
