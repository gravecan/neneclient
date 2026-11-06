package platform.inject.mixin;

import aethereal.core.NeneClient;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {
   @Inject(method = "renderLabelIfPresent", at = @At("HEAD"), cancellable = true)
   private void renderLabelIfPresent(
      PlayerEntityRenderState playerEntityRenderState,
      MatrixStack matrixStack,
      OrderedRenderCommandQueue orderedRenderCommandQueue,
      CameraRenderState cameraRenderState,
      CallbackInfo callbackInfo
   ) {
      if (NeneClient.getInstance().getProcessors().getModules().getEntityESPField31_0487().isEnabled()) {
         callbackInfo.cancel();
      }
   }
}
