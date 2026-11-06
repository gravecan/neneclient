package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.core.MinecraftAccess;
import aethereal.event.AmbienceEvent;
import net.minecraft.client.render.fog.FogRenderer;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FogRenderer.class)
public class FogRendererMixin {
   @Inject(method = "getFogColor", at = @At("RETURN"))
   private void onFogColor(CallbackInfoReturnable<Vector4f> callbackInfoReturnable) {
      AmbienceEvent.NestedType311 nestedType311 = new AmbienceEvent.NestedType311(
         MinecraftAccess.minecraft.gameRenderer.getCamera(), null, (Vector4f)callbackInfoReturnable.getReturnValue()
      );
      EventBus.post(nestedType311);
   }
}
