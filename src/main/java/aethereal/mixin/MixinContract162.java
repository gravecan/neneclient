package aethereal.mixin;

import aethereal.render.Animation;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.entry.RegistryEntry;

public interface MixinContract162 {
   Animation getAnimation();

   int getInitialDuration();

   void setInitialDuration(int i);

   int getDuration();

   int getAmplifier();

   RegistryEntry<StatusEffect> getEffectType();
}
