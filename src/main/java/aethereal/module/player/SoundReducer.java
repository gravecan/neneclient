package aethereal.module.player;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent071;
import aethereal.setting.BooleanSetting;
import aethereal.setting.ChoiceSetting;
import aethereal.setting.FloatSetting;
import aethereal.setting.MultiSelectSetting;

@ModuleRegister(name = "Sound Reducer", description = "Reduces the volume of selected game sounds", category = Category.Player)
public class SoundReducer extends Module {
   private final ChoiceSetting choiceSettingField1 = new ChoiceSetting("Sound processing method", "Mute", "Mute", "Shutdown");
   private final FloatSetting floatSettingField2 = new FloatSetting("Sound Producing Volume", 0.5F, 0.0F, 1.0F, 0.01F)
      .withVisibility(() -> this.choiceSettingField1.checkCondition_2850("Mute"));
   private final MultiSelectSetting multiSelectSettingField3 = new MultiSelectSetting(
      "Select sounds",
      new BooleanSetting("Throwing potions/experience", true),
      new BooleanSetting("Creeper Explosion", false),
      new BooleanSetting("TNT Explosion", false),
      new BooleanSetting("Anvils", true),
      new BooleanSetting("Bow shot", false),
      new BooleanSetting("Ghast's Scream", false),
      new BooleanSetting("Skeleton Steps", false),
      new BooleanSetting("Hell Portal Sound", false),
      new BooleanSetting("Event", true),
      new BooleanSetting("Withering", true),
      new BooleanSetting("Phantoms", true),
      new BooleanSetting("Trident", true),
      new BooleanSetting("Totem", false),
      new BooleanSetting("Vardena", false),
      new BooleanSetting("Skalk Sensor", false)
   );

   public SoundReducer() {
      this.addSettings(this.choiceSettingField1, this.multiSelectSettingField3, this.floatSettingField2);
   }

   @EventTarget
   public void onEventComponent071_2205(EventComponent071 eventComponent071) {
      if (this.checkCondition_2206(eventComponent071.getSound_0970().getId().getPath())) {
         eventComponent071.setVolume_0971(
            this.choiceSettingField1.checkCondition_2850("Shutdown") ? 0.0F : eventComponent071.getVolume_0969() * this.floatSettingField2.getCurrentValue()
         );
      }
   }

   public boolean checkCondition_2206(String string) {
      return this.multiSelectSettingField3.computeValue_2852("Throwing potions/experience").getCurrentValue()
            && (string.contains("splash_potion") || string.contains("experience_orb") || string.contains("lingering_potion"))
         || this.multiSelectSettingField3.computeValue_2852("Creeper Explosion").getCurrentValue()
            && (string.contains("creeper") || string.contains("generic") && string.contains("explode"))
         || this.multiSelectSettingField3.computeValue_2852("TNT Explosion").getCurrentValue()
            && (string.contains("tnt") || string.contains("generic") && string.contains("explode"))
         || this.multiSelectSettingField3.computeValue_2852("Anvils").getCurrentValue() && string.contains("anvil")
         || this.multiSelectSettingField3.computeValue_2852("Bow shot").getCurrentValue() && (string.contains("arrow") || string.contains("bow"))
         || this.multiSelectSettingField3.computeValue_2852("Ghast's Scream").getCurrentValue() && string.contains("ghast")
         || this.multiSelectSettingField3.computeValue_2852("Skeleton Steps").getCurrentValue()
            && string.contains("skeleton")
            && !string.contains("skeleton_horse")
         || this.multiSelectSettingField3.computeValue_2852("Hell Portal Sound").getCurrentValue() && string.contains("portal")
         || this.multiSelectSettingField3.computeValue_2852("Event").getCurrentValue() && string.contains("entity.ender_dragon.ambient")
         || this.multiSelectSettingField3.computeValue_2852("Withering").getCurrentValue() && string.contains("wither") && !string.contains("wither_skeleton")
         || this.multiSelectSettingField3.computeValue_2852("Phantoms").getCurrentValue() && string.contains("phantom")
         || this.multiSelectSettingField3.computeValue_2852("Trident").getCurrentValue() && string.contains("trident")
         || this.multiSelectSettingField3.computeValue_2852("Totem").getCurrentValue() && string.contains("totem")
         || this.multiSelectSettingField3.computeValue_2852("Vardena").getCurrentValue() && string.contains("warden")
         || this.multiSelectSettingField3.computeValue_2852("Skalk Sensor").getCurrentValue() && string.contains("sculk");
   }
}
