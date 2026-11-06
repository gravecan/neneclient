package platform.inject.mixin;

import aethereal.core.EventBus;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent080;
import aethereal.event.RemovalsEvent;
import aethereal.module.movement.NoPush;
import aethereal.render.Animations;
import aethereal.util.UtilComponent277;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.block.enums.CameraSubmersionType;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import platform.inject.accessors.CameraAccessor;

@Mixin(Camera.class)
public abstract class CameraMixin {
   @ModifyExpressionValue(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getYaw(F)F"))
   private float a_freeLookYaw(float f) {
      if (UtilComponent277.checkCondition_3572()) {
         return UtilComponent277.getFloatValueField2_3570();
      }

      UtilComponent277.setFloatValueField2_3574(f);
      return f;
   }

   @ModifyExpressionValue(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getPitch(F)F"))
   private float a_freeLookPitch(float f) {
      if (UtilComponent277.checkCondition_3572()) {
         return UtilComponent277.getFloatValueField3_3571();
      }

      UtilComponent277.setFloatValueField3_3565(f);
      return f;
   }

   @ModifyExpressionValue(method = "update", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F"))
   private float a_perspectiveDistance(float f) {
      Animations animations = NeneClient.getInstance().getProcessors().getModules().getAnimationsField5_0502();
      return animations.isEnabled() && animations.getMultiSelectSettingField1_2544().computeValue_2852("Change of perspective").getCurrentValue()
         ? f * animations.getAnimationField7_2548().getFloatValueField4_2530()
         : f;
   }

   @Inject(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;setPos(DDD)V", shift = Shift.AFTER))
   private void a_cameraPosition(World world, Entity entity, boolean bl, boolean bl1, float f, CallbackInfo callbackInfo) {
      Camera camera = (Camera)this;
      EventComponent080 eventComponent080 = new EventComponent080(camera.getCameraPos());
      EventBus.post(eventComponent080);
      if (eventComponent080.checkCondition_0596() && eventComponent080.getPosition_1029() != null) {
         Vec3d vec3d = eventComponent080.getPosition_1029();
         ((CameraAccessor)this).invokeSetPos(vec3d.getX(), vec3d.getY(), vec3d.getZ());
      }
   }

   @Inject(method = "getSubmersionType", at = @At("HEAD"), cancellable = true)
   private void a_submersion(CallbackInfoReturnable<CameraSubmersionType> callbackInfoReturnable) {
      RemovalsEvent removalsEvent = new RemovalsEvent(RemovalsEvent.State319.WATER);
      EventBus.post(removalsEvent);
      if (removalsEvent.checkCondition_0596()) {
         callbackInfoReturnable.setReturnValue(CameraSubmersionType.NONE);
      }
   }

   @Inject(method = "clipToSpace", at = @At("HEAD"), cancellable = true)
   private void a_clipToSpace(float f, CallbackInfoReturnable<Float> callbackInfoReturnable) {
      Animations animations = NeneClient.getInstance() != null
            && NeneClient.getInstance().getProcessors() != null
            && NeneClient.getInstance().getProcessors().getModules() != null
         ? NeneClient.getInstance().getProcessors().getModules().getAnimationsField5_0502()
         : null;
      if (animations != null
         && animations.isEnabled()
         && animations.getMultiSelectSettingField1_2544().computeValue_2852("Change of perspective").getCurrentValue()
         && animations.getBooleanSettingField3_2547().getCurrentValue()) {
         callbackInfoReturnable.setReturnValue(f);
      } else {
         NoPush noPush = NeneClient.getInstance() != null
               && NeneClient.getInstance().getProcessors() != null
               && NeneClient.getInstance().getProcessors().getModules() != null
            ? NeneClient.getInstance().getProcessors().getModules().getNoPushField19_0449()
            : null;
         if (noPush != null && noPush.isEnabled() && noPush.getMultiSelectSettingField1_2047().computeValue_2852("Blocks").getCurrentValue()) {
            callbackInfoReturnable.setReturnValue(f);
         } else {
            RemovalsEvent removalsEvent = new RemovalsEvent(RemovalsEvent.State319.CLIP);
            EventBus.post(removalsEvent);
            if (removalsEvent.checkCondition_0596()) {
               callbackInfoReturnable.setReturnValue(f);
            }
         }
      }
   }
}
