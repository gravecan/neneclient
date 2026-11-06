package platform.inject.mixin;

import aethereal.core.MinecraftAccess;
import aethereal.ui.screen.MainMenuScreen;
import aethereal.ui.screen.ScreenComponent212;
import net.minecraft.client.option.InactivityFpsLimiter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InactivityFpsLimiter.class)
public class InactivityFpsLimiterMixin {
   @Inject(method = "update", at = @At("HEAD"), cancellable = true)
   private void onUpdate(CallbackInfoReturnable<Integer> callbackInfoReturnable) {
      if (MinecraftAccess.minecraft.currentScreen instanceof MainMenuScreen || MinecraftAccess.minecraft.currentScreen instanceof ScreenComponent212) {
         callbackInfoReturnable.setReturnValue(200);
      }
   }
}
