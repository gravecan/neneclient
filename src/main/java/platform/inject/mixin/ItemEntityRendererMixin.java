package platform.inject.mixin;

import aethereal.core.NeneClient;
import aethereal.mixin.MixinContract159;
import aethereal.module.render.ItemPhysic;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.render.entity.state.ItemEntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionfc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntityRenderer.class)
@Environment(EnvType.CLIENT)
public class ItemEntityRendererMixin {
   @Inject(method = "updateRenderState", at = @At("TAIL"))
   private void captureGround(ItemEntity itemEntity, ItemEntityRenderState itemEntityRenderState, float f, CallbackInfo callbackInfo) {
      ((MixinContract159)itemEntityRenderState).setOnGround(itemEntity.isOnGround());
   }

   @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V"))
   private void translate(MatrixStack matrixStack, float f, float g, float h) {
      if (NeneClient.getInstance().getProcessors().getModules().getItemPhysicField33_0491().isEnabled()) {
         g = 0.0F;
      }

      matrixStack.translate(f, g, h);
   }

   @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;multiply(Lorg/joml/Quaternionfc;)V"))
   private void cancelHover(MatrixStack matrixStack, Quaternionfc quaternionfc) {
      if (!NeneClient.getInstance().getProcessors().getModules().getItemPhysicField33_0491().isEnabled()) {
         matrixStack.multiply(quaternionfc);
      }
   }

   @Inject(
      method = "render",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/entity/ItemEntityRenderer;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;ILnet/minecraft/client/render/entity/state/ItemStackEntityRenderState;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/Box;)V",
         shift = Shift.BEFORE
      )
   )
   private void applyPhysics(
      ItemEntityRenderState itemEntityRenderState,
      MatrixStack matrixStack,
      OrderedRenderCommandQueue orderedRenderCommandQueue,
      CameraRenderState cameraRenderState,
      CallbackInfo callbackInfo
   ) {
      ItemPhysic itemPhysic = NeneClient.getInstance().getProcessors().getModules().getItemPhysicField33_0491();
      if (itemPhysic.isEnabled()) {
         if (itemPhysic.getBooleanSettingField1_2261().getCurrentValue()) {
            matrixStack.scale(0.5F, 0.5F, 0.5F);
         }

         if (((MixinContract159)itemEntityRenderState).isOnGround()) {
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
         } else {
            float f = ItemEntity.getRotation(itemEntityRenderState.age, itemEntityRenderState.uniqueOffset) * 300.0F;
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(f));
         }
      }
   }
}
