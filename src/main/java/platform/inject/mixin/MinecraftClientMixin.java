package platform.inject.mixin;

import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.module.player.OpenWalls;
import aethereal.ui.screen.ScreenComponent218;
import java.util.Objects;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ReconfiguringScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin implements MinecraftAccess {
   @Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
   private void setScreen(Screen screen, CallbackInfo callbackInfo) {
      if (minecraft.currentScreen instanceof ScreenComponent218 && (screen == null || screen instanceof ReconfiguringScreen)) {
         for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            if (stackTraceElement.getClassName().equals(Screen.class.getName()) || stackTraceElement.getClassName().equals(Keyboard.class.getName())) {
               return;
            }
         }

         callbackInfo.cancel();
      }
   }

   @Inject(method = "doItemUse", at = @At("HEAD"), cancellable = true)
   private void doItemUseCancel(CallbackInfo callbackInfo) {
      ItemStack itemStack = minecraft.player.getStackInHand(Hand.MAIN_HAND);
      if (itemStack.getItem() instanceof PotionItem && minecraft.player.getItemCooldownManager().isCoolingDown(itemStack)) {
         callbackInfo.cancel();
      } else {
         if (itemStack.getItem() instanceof CrossbowItem
            && CrossbowItem.isCharged(itemStack)
            && minecraft.player.getItemCooldownManager().isCoolingDown(itemStack)) {
            callbackInfo.cancel();
         }
      }
   }

   @Redirect(
      method = "doItemUse",
      at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;crosshairTarget:Lnet/minecraft/util/hit/HitResult;", opcode = 180)
   )
   private HitResult doItemUseOpenWalls(MinecraftClient minecraftClient) {
      OpenWalls openWalls = NeneClient.getInstance().getProcessors().getModules().getOpenWallsField78_0477();
      if (!openWalls.isEnabled()) {
         return minecraftClient.crosshairTarget;
      }

      BlockHitResult blockHitResult = openWalls.computeValue_2203(Objects.requireNonNull(minecraftClient.player));
      return (HitResult)(blockHitResult != null && blockHitResult.getType() == Type.BLOCK ? blockHitResult : minecraftClient.crosshairTarget);
   }
}
