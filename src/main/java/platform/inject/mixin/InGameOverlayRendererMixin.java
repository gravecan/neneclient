package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.core.NeneClient;
import aethereal.event.RemovalsEvent;
import aethereal.module.movement.NoPush;
import aethereal.render.Animations;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameOverlayRenderer.class)
public class InGameOverlayRendererMixin {
   @Inject(method = "renderFireOverlay", at = @At("HEAD"), cancellable = true)
   private static void renderFireOverlay(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, Sprite sprite, CallbackInfo callbackInfo) {
      RemovalsEvent removalsEvent = new RemovalsEvent(RemovalsEvent.State319.FIRE);
      EventBus.post(removalsEvent);
      if (removalsEvent.checkCondition_0596()) {
         callbackInfo.cancel();
      }
   }

   @Inject(method = "renderInWallOverlay", at = @At("HEAD"), cancellable = true)
   private static void renderInWallOverlay(Sprite sprite, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, CallbackInfo callbackInfo) {
      Animations animations = NeneClient.getInstance() != null
            && NeneClient.getInstance().getProcessors() != null
            && NeneClient.getInstance().getProcessors().getModules() != null
         ? NeneClient.getInstance().getProcessors().getModules().getAnimationsField5_0502()
         : null;
      if (animations != null
         && animations.isEnabled()
         && animations.getMultiSelectSettingField1_2544().computeValue_2852("Change of perspective").getCurrentValue()
         && animations.getBooleanSettingField3_2547().getCurrentValue()) {
         callbackInfo.cancel();
      } else {
         NoPush noPush = NeneClient.getInstance() != null
               && NeneClient.getInstance().getProcessors() != null
               && NeneClient.getInstance().getProcessors().getModules() != null
            ? NeneClient.getInstance().getProcessors().getModules().getNoPushField19_0449()
            : null;
         if (noPush != null && noPush.isEnabled() && noPush.getMultiSelectSettingField1_2047().computeValue_2852("Blocks").getCurrentValue()) {
            callbackInfo.cancel();
         } else {
            RemovalsEvent removalsEvent = new RemovalsEvent(RemovalsEvent.State319.CLIP);
            EventBus.post(removalsEvent);
            if (removalsEvent.checkCondition_0596()) {
               callbackInfo.cancel();
            }
         }
      }
   }
}
