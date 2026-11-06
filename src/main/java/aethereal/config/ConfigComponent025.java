package aethereal.config;

import java.util.stream.StreamSupport;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;

public class ConfigComponent025 {
   private final RegistryEntry<StatusEffect> effect;
   private final int requiredLevel;
   private final int requiredDuration;

   public RegistryEntry<StatusEffect> getEffect_0515() {
      return this.effect;
   }

   public int getRequiredLevel_0513() {
      return this.requiredLevel;
   }

   public int getRequiredDuration_0518() {
      return this.requiredDuration;
   }

   public ConfigComponent025(RegistryEntry<StatusEffect> registryEntry, int i, int j) {
      if (registryEntry == null) {
         throw new IllegalArgumentException("Effect cannot be null");
      }

      this.effect = registryEntry;
      this.requiredLevel = i;
      this.requiredDuration = j;
   }

   public boolean checkCondition_0514(ItemStack itemStack) {
      PotionContentsComponent potionContentsComponent = (PotionContentsComponent)itemStack.get(DataComponentTypes.POTION_CONTENTS);
      return potionContentsComponent == null
         ? false
         : StreamSupport.<StatusEffectInstance>stream(potionContentsComponent.getEffects().spliterator(), false)
            .anyMatch(
               statusEffectInstance -> statusEffectInstance.getEffectType().equals(this.effect)
                  && statusEffectInstance.getAmplifier() + 1 == this.requiredLevel
                  && statusEffectInstance.getDuration() >= this.requiredDuration
            );
   }

   @Override
   public String toString() {
      return "PotionCondition{effect="
         + this.effect.getKey().map(registryKey -> registryKey.getValue().toString()).orElse("unknown")
         + ", requiredLevel="
         + this.requiredLevel
         + ", requiredDuration="
         + this.requiredDuration
         + "}";
   }
}
