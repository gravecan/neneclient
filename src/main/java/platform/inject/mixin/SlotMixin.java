package platform.inject.mixin;

import aethereal.mixin.MixinContract161;
import aethereal.render.Animation;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Slot.class)
public abstract class SlotMixin implements MixinContract161 {
   @Unique
   private Animation animation;

   @Override
   public Animation getAnimation() {
      if (this.animation == null) {
         this.animation = new Animation();
      }

      return this.animation;
   }

   @Inject(method = "<init>*", at = @At("TAIL"))
   private void init(CallbackInfo callbackInfo) {
      this.animation = new Animation();
      this.animation.setFloatValueField1_2526(1.0F);
   }
}
