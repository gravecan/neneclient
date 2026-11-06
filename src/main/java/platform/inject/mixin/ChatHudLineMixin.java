package platform.inject.mixin;

import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.render.Animations;
import aethereal.render.RenderComponent186;
import aethereal.util.UtilComponent264;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.hud.ChatHud.Backend;
import net.minecraft.client.gui.hud.ChatHudLine.Visible;
import net.minecraft.text.OrderedText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net/minecraft/client/gui/hud/ChatHud$1")
public class ChatHudLineMixin {
   @WrapOperation(
      method = "accept",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/ChatHud$Backend;text(IFLnet/minecraft/text/OrderedText;)Z")
   )
   private boolean neneclient$fadeIn(
      Backend backend, int i, float f, OrderedText orderedText, Operation<Boolean> operation, @Local(argsOnly = true, ordinal = 0) Visible visible
   ) {
      Animations animations = NeneClient.getInstance().getProcessors().getModules().getAnimationsField5_0502();
      if (animations.isEnabled() && animations.getMultiSelectSettingField1_2544().computeValue_2852("Appearing of messages").getCurrentValue()) {
         float g = MinecraftAccess.minecraft.getRenderTickCounter().getTickProgress(false);
         int j = MinecraftAccess.minecraft.inGameHud.getTicks();
         double d = Math.max(0.0, Math.min(1.0, (j - visible.addedTime() + g) / 9.0));
         float h = RenderComponent186.contract372Field16.ease((float)d);
         UtilComponent264.lineProgress = h;

         try {
            return (Boolean)operation.call(new Object[]{backend, i, f * h, orderedText});
         } finally {
            UtilComponent264.lineProgress = 1.0F;
         }
      } else {
         return (Boolean)operation.call(new Object[]{backend, i, f, orderedText});
      }
   }
}
