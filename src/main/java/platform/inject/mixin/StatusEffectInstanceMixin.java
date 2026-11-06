package platform.inject.mixin;

import aethereal.mixin.MixinContract162;
import aethereal.render.Animation;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StatusEffectInstance.class)
public abstract class StatusEffectInstanceMixin implements MixinContract162 {
   @Unique
   private Animation animation;
   @Unique
   private int initialDuration;

   @Shadow
   @Override
   public abstract int getDuration();

   @Shadow
   @Override
   public abstract int getAmplifier();

   @Shadow
   @Override
   public abstract RegistryEntry<StatusEffect> getEffectType();

   @Override
   public Animation getAnimation() {
      if (this.animation == null) {
         this.animation = new Animation();
      }

      return this.animation;
   }

   @Override
   public int getInitialDuration() {
      return this.initialDuration;
   }

   @Override
   public void setInitialDuration(int i) {
      this.initialDuration = i;
   }

   @Inject(method = "<init>*", at = @At("TAIL"))
   private void onInit(CallbackInfo callbackInfo) {
      this.animation = new Animation();
      this.initialDuration = ((StatusEffectInstance)this).getDuration();
   }
}
