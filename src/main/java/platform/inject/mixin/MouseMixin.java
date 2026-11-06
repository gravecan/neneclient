package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.core.MinecraftAccess;
import aethereal.event.ClickEvent;
import aethereal.event.EventComponent093;
import aethereal.event.EventComponent094;
import aethereal.event.EventComponent102;
import aethereal.ui.screen.ScreenComponent213;
import aethereal.ui.screen.ScreenComponent225;
import net.minecraft.client.Mouse;
import net.minecraft.client.input.MouseInput;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {
   @Inject(method = "onMouseButton", at = @At("HEAD"), cancellable = true)
   private void onMouseButton(long l, MouseInput mouseInput, int i, CallbackInfo callbackInfo) {
      int j = mouseInput.button();
      int k = mouseInput.comp_4797();
      if (MinecraftAccess.minecraft.currentScreen == null
         || MinecraftAccess.minecraft.currentScreen instanceof ScreenComponent225
         || MinecraftAccess.minecraft.currentScreen instanceof ScreenComponent213) {
         EventComponent093 eventComponent093 = new EventComponent093(j >= 0 && j <= 7 ? -100 + j : j, 0, i, k);
         EventBus.post(eventComponent093);
         if (eventComponent093.checkCondition_0596()) {
            callbackInfo.cancel();
            return;
         }
      }

      if (i == 1) {
         ClickEvent clickEvent = new ClickEvent(
            MinecraftAccess.minecraft.mouse.getX() / 2.0, MinecraftAccess.minecraft.mouse.getY() / 2.0, j, ClickEvent.State314.PRESS
         );
         EventBus.post(clickEvent);
         if (clickEvent.checkCondition_0596()) {
            callbackInfo.cancel();
         }
      } else if (i == 0) {
         ClickEvent clickEvent1 = new ClickEvent(
            MinecraftAccess.minecraft.mouse.getX() / 2.0, MinecraftAccess.minecraft.mouse.getY() / 2.0, j, ClickEvent.State314.RELEASE
         );
         EventBus.post(clickEvent1);
         if (clickEvent1.checkCondition_0596()) {
            callbackInfo.cancel();
         }
      }
   }

   @Inject(method = "onCursorPos", at = @At("HEAD"), cancellable = true)
   private void onCursorPos(long l, double d, double e, CallbackInfo callbackInfo) {
      ClickEvent clickEvent = new ClickEvent(
         MinecraftAccess.minecraft.mouse.getX() / 2.0, MinecraftAccess.minecraft.mouse.getY() / 2.0, 0, ClickEvent.State314.DRAG
      );
      EventBus.post(clickEvent);
      if (clickEvent.checkCondition_0596()) {
         callbackInfo.cancel();
      }
   }

   @Inject(method = "onMouseScroll", at = @At("RETURN"))
   private void onMouseScroll(long l, double d, double e, CallbackInfo callbackInfo) {
      EventBus.post(new EventComponent102(d, e));
   }

   @Redirect(method = "updateMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;changeLookDirection(DD)V"))
   private void redirectChangeLookDirection(ClientPlayerEntity clientPlayerEntity, double d, double e) {
      EventComponent094 eventComponent094 = new EventComponent094((float)d, (float)e);
      EventBus.post(eventComponent094);
      if (!eventComponent094.checkCondition_0596()) {
         clientPlayerEntity.changeLookDirection(d, e);
      }
   }
}
