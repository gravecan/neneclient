package aethereal.module.render;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent074;
import aethereal.event.EventComponent086;
import aethereal.setting.ChoiceSetting;
import aethereal.setting.FloatSetting;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

@ModuleRegister(name = "Full Bright", description = "Completely illuminates the world through gamma or night vision", category = Category.Render)
public class FullBright extends Module {
   private final ChoiceSetting choiceSettingField1 = new ChoiceSetting("Vision mode", "Gamma", "Gamma", "Night vision");
   private final FloatSetting floatSettingField2 = new FloatSetting("Gamma level", 4.0F, 1.0F, 8.0F, 0.5F)
      .withVisibility(() -> this.choiceSettingField1.checkCondition_2850("Gamma"));

   public FullBright() {
      this.addSettings(this.choiceSettingField1, this.floatSettingField2);
   }

   @EventTarget
   public void onEventComponent074_2258(EventComponent074 eventComponent074) {
      if (this.choiceSettingField1.checkCondition_2850("Night vision")) {
         minecraft.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 240, 1, false, false, false));
      } else {
         minecraft.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
      }
   }

   @EventTarget
   public void onEventComponent086_2259(EventComponent086 eventComponent086) {
      if (this.choiceSettingField1.checkCondition_2850("Gamma")) {
         eventComponent086.setGamma_1040(this.floatSettingField2.getCurrentValue().floatValue());
      }
   }
}
