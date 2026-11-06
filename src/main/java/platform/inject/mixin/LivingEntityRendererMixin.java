package platform.inject.mixin;

import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.render.RenderComponent183;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> {
   @Unique
   private T currentEntity;
   @Unique
   private float pitch;

   @Inject(method = "updateRenderState*", at = @At("HEAD"))
   private void onUpdateRenderState(T livingEntity, S s, float f, CallbackInfo callbackInfo) {
      this.currentEntity = (T)livingEntity;
   }

   @Inject(method = "updateRenderState*", at = @At("TAIL"))
   private void updateRenderState(T livingEntity, S livingEntityRenderState, float f, CallbackInfo callbackInfo) {
      if (livingEntity == MinecraftAccess.minecraft.player) {
         this.pitch = NeneClient.getInstance().getProcessors().computeValue_0672().getUtilComponent277Field1_1136().isFlagField1_3566()
            ? MathHelper.lerp(0.5F, this.pitch, livingEntityRenderState.pitch)
            : livingEntityRenderState.pitch;
         livingEntityRenderState.pitch = this.pitch;
      }
   }

   @Inject(method = "isVisible", at = @At("HEAD"), cancellable = true)
   private void onIsVisible(S livingEntityRenderState, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
      if (NeneClient.getInstance().getProcessors().getModules().getSeeInvisiblesField29_0495().isEnabled()
         && livingEntityRenderState.invisible
         && this.currentEntity instanceof PlayerEntity) {
         callbackInfoReturnable.setReturnValue(true);
      }
   }

   @ModifyReturnValue(method = "getMixColor", at = @At("RETURN"))
   private int modifyMixColor(int i, S livingEntityRenderState) {
      return NeneClient.getInstance().getProcessors().getModules().getSeeInvisiblesField29_0495().isEnabled()
            && livingEntityRenderState.invisible
            && this.currentEntity instanceof PlayerEntity
         ? RenderComponent183.computeValue_2549(i, NeneClient.getInstance().getProcessors().getModules().getSeeInvisiblesField29_0495().computeValue_2297())
         : i;
   }
}
