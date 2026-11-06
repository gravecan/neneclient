package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.event.RemovalsEvent;
import net.minecraft.client.gui.hud.BossBarHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossBarHud.class)
public class BossBarHudMixin {
   @Inject(method = "render", at = @At("HEAD"), cancellable = true)
   private void render(CallbackInfo callbackInfo) {
      RemovalsEvent removalsEvent = new RemovalsEvent(RemovalsEvent.State319.BOSS_BAR);
      EventBus.post(removalsEvent);
      if (removalsEvent.checkCondition_0596()) {
         callbackInfo.cancel();
      }
   }
}
