package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.core.NeneClient;
import aethereal.event.DrawEvent;
import aethereal.event.EventComponent084;
import aethereal.event.EventComponent097;
import aethereal.event.HandEvent;
import aethereal.event.RemovalsEvent;
import aethereal.render.Animations;
import aethereal.render.RenderComponent189;
import aethereal.ui.screen.ScreenComponent218;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
   @Inject(method = "getBasicProjectionMatrix", at = @At("RETURN"))
   private void onBasicProjectionMatrix(float f, CallbackInfoReturnable<Matrix4f> callbackInfoReturnable) {
      RenderComponent189.performAction_2654((Matrix4f)callbackInfoReturnable.getReturnValue());
   }

   @Inject(
      method = "renderWorld",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/WorldRenderer;render(Lnet/minecraft/client/util/memory/ObjectAllocator;Lnet/minecraft/client/render/RenderTickCounter;ZLnet/minecraft/client/render/Camera;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lorg/joml/Vector4f;Z)V",
         shift = Shift.AFTER
      )
   )
   public void renderWorld(RenderTickCounter renderTickCounter, CallbackInfo callbackInfo, @Local(ordinal = 0) MatrixStack matrixStack) {
      MatrixStack matrixStack1 = new MatrixStack();
      matrixStack1.multiplyPositionMatrix(matrixStack.peek().getPositionMatrix());
      EventBus.post(new DrawEvent(matrixStack1, renderTickCounter.getTickProgress(false), DrawEvent.State315.D3D));
   }

   @ModifyExpressionValue(
      method = "updateCrosshairTarget",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/network/ClientPlayerEntity;getCrosshairTarget(FLnet/minecraft/entity/Entity;)Lnet/minecraft/util/hit/HitResult;"
      )
   )
   private HitResult a_crosshairTarget(HitResult hitResult, float f) {
      EventComponent084 eventComponent084 = new EventComponent084(f);
      EventBus.post(eventComponent084);
      return eventComponent084.checkCondition_0596() && eventComponent084.getHitResultField1_1037() != null
         ? eventComponent084.getHitResultField1_1037()
         : hitResult;
   }

   @ModifyArg(
      method = "updateCamera",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;update(Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;ZZF)V"),
      index = 2
   )
   private boolean updateCameraPerspective(boolean bl) {
      Animations animations = NeneClient.getInstance().getProcessors().getModules().getAnimationsField5_0502();
      return animations.isEnabled() && animations.getMultiSelectSettingField1_2544().computeValue_2852("Change of perspective").getCurrentValue()
         ? bl || animations.getAnimationField7_2548().getFloatValueField4_2530() > 0.01F
         : bl;
   }

   @Inject(method = "renderHand", at = @At("HEAD"), cancellable = true)
   private void preRenderHand(float f, boolean bl, Matrix4f matrix4f, CallbackInfo callbackInfo) {
      Animations animations = NeneClient.getInstance().getProcessors().getModules().getAnimationsField5_0502();
      if (animations.isEnabled()
         && animations.getMultiSelectSettingField1_2544().computeValue_2852("Change of perspective").getCurrentValue()
         && !this.thirdPersonNow()
         && animations.getAnimationField7_2548().getFloatValueField4_2530() > 0.01F) {
         callbackInfo.cancel();
      } else {
         EventBus.post(new HandEvent(HandEvent.State316.PRE));
      }
   }

   @Unique
   private boolean thirdPersonNow() {
      return !MinecraftClient.getInstance().options.getPerspective().isFirstPerson();
   }

   @Inject(
      method = "renderWorld",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;draw()V", shift = Shift.AFTER)
   )
   private void postRenderHand(RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
      Animations animations = NeneClient.getInstance().getProcessors().getModules().getAnimationsField5_0502();
      if (!animations.isEnabled()
         || !animations.getMultiSelectSettingField1_2544().computeValue_2852("Change of perspective").getCurrentValue()
         || this.thirdPersonNow()
         || !(animations.getAnimationField7_2548().getFloatValueField4_2530() > 0.01F)) {
         EventBus.post(new HandEvent(HandEvent.State316.POST));
      }
   }

   @Inject(method = "tiltViewWhenHurt", at = @At("HEAD"), cancellable = true)
   private void tiltViewWhenHurt(MatrixStack matrixStack, float f, CallbackInfo callbackInfo) {
      RemovalsEvent removalsEvent = new RemovalsEvent(RemovalsEvent.State319.HURT_CAM);
      EventBus.post(removalsEvent);
      if (removalsEvent.checkCondition_0596()) {
         callbackInfo.cancel();
      }
   }

   @Redirect(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;lerp(FFF)F"))
   private float renderWorldNausea(float f, float g, float h) {
      RemovalsEvent removalsEvent = new RemovalsEvent(RemovalsEvent.State319.NAUSEA);
      EventBus.post(removalsEvent);
      return removalsEvent.checkCondition_0596() ? 0.0F : MathHelper.lerp(f, g, h);
   }

   @ModifyArg(method = "getBasicProjectionMatrix", at = @At(value = "INVOKE", target = "Lorg/joml/Matrix4f;perspective(FFFF)Lorg/joml/Matrix4f;"), index = 1)
   private float getBasicProjectionMatrix(float f) {
      EventComponent097 eventComponent097 = new EventComponent097(f);
      EventBus.post(eventComponent097);
      return eventComponent097.getRatio_1093();
   }

   @Inject(method = "render", at = @At("TAIL"))
   private void onRenderTail(RenderTickCounter renderTickCounter, boolean bl, CallbackInfo callbackInfo) {
      if (MinecraftClient.getInstance() != null && MinecraftClient.getInstance().currentScreen instanceof ScreenComponent218 screenComponent218) {
         screenComponent218.performAction_3155();
      }
   }
}
