package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.event.EventComponent099;
import aethereal.util.WindowStyle;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Window.class)
public class WindowMixin {
   @Shadow
   private long handle;

   @Inject(method = "setIcon", at = @At("TAIL"))
   private void onSetIcon(CallbackInfo callbackInfo) {
      EventBus.post(new EventComponent099());
   }

   @Inject(method = "onFramebufferSizeChanged", at = @At("TAIL"))
   private void onFramebufferSizeChanged(long l, int i, int j, CallbackInfo callbackInfo) {
      WindowStyle.performAction_3491(this.handle, true);
      EventBus.post(new EventComponent099());
   }

   @Inject(method = "<init>", at = @At("TAIL"))
   private void onInit(CallbackInfo callbackInfo) {
      WindowStyle.performAction_3491(this.handle, true);
   }
}
