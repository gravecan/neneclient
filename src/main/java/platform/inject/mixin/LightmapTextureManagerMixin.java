package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.event.EventComponent086;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LightmapTextureManager.class)
public class LightmapTextureManagerMixin {
   @ModifyExpressionValue(
      method = "update",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/SimpleOption;getValue()Ljava/lang/Object;", ordinal = 2)
   )
   private Object onGetGamma(Object object) {
      EventComponent086 eventComponent086 = new EventComponent086(((Number)object).doubleValue());
      EventBus.post(eventComponent086);
      return eventComponent086.getGamma_1041();
   }
}
