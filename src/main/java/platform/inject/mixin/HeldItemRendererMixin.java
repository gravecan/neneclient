package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.event.EventComponent087;
import aethereal.event.EventComponent088;
import aethereal.util.UtilComponent277;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
   @Redirect(method = "renderItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getLerpedPitch(F)F"))
   private float redirectPitchArg(ClientPlayerEntity clientPlayerEntity, float f) {
      return UtilComponent277.getFloatValueField3_3571();
   }

   @Redirect(method = "renderItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getPitch(F)F"))
   private float redirectPitchSway(ClientPlayerEntity clientPlayerEntity, float f) {
      return UtilComponent277.getFloatValueField3_3571();
   }

   @Redirect(method = "renderItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getYaw(F)F"))
   private float redirectYawSway(ClientPlayerEntity clientPlayerEntity, float f) {
      return UtilComponent277.getFloatValueField2_3570();
   }

   @Redirect(method = "renderItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;lerp(FFF)F", ordinal = 0))
   private float redirectPitchLerp(float f, float g, float h) {
      return UtilComponent277.getFloatValueField3_3571();
   }

   @Redirect(method = "renderItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;lerp(FFF)F", ordinal = 1))
   private float redirectYawLerp(float f, float g, float h) {
      return UtilComponent277.getFloatValueField2_3570();
   }

   @WrapOperation(
      method = "renderFirstPersonItem",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/item/HeldItemRenderer;swingArm(FLnet/minecraft/client/util/math/MatrixStack;ILnet/minecraft/util/Arm;)V",
         ordinal = 2
      )
   )
   private void wrapHandAnimation(
      HeldItemRenderer heldItemRenderer,
      float f,
      MatrixStack matrixStack,
      int i,
      Arm arm,
      Operation<Void> operation,
      @Local(ordinal = 0, argsOnly = true) Hand hand
   ) {
      EventComponent087 eventComponent087 = new EventComponent087(matrixStack, hand, f, i);
      EventBus.post(eventComponent087);
      if (!eventComponent087.checkCondition_0596()) {
         operation.call(new Object[]{heldItemRenderer, f, matrixStack, i, arm});
      }
   }

   @Inject(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;push()V", shift = Shift.AFTER))
   private void onRenderFirstPersonItem(
      AbstractClientPlayerEntity abstractClientPlayerEntity,
      float f,
      float g,
      Hand hand,
      float h,
      ItemStack itemStack,
      float i,
      MatrixStack matrixStack,
      OrderedRenderCommandQueue orderedRenderCommandQueue,
      int j,
      CallbackInfo callbackInfo
   ) {
      EventBus.post(new EventComponent088(matrixStack, itemStack, hand));
   }
}
