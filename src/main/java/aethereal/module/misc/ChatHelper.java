package aethereal.module.misc;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent074;
import aethereal.setting.BooleanSetting;
import aethereal.util.UtilComponent252;

@ModuleRegister(name = "Chat Helper", description = "Expands chat capabilities and settings", category = Category.Misc)
public class ChatHelper extends Module {
   private final BooleanSetting booleanSettingField1 = new BooleanSetting("Message Width", false);
   private final BooleanSetting booleanSettingField2 = new BooleanSetting("Automatic /event delay", false);
   private int integerField3 = -1;

   public BooleanSetting getBooleanSettingField1_1860() {
      return this.booleanSettingField1;
   }

   public ChatHelper() {
      this.addSettings(this.booleanSettingField1, this.booleanSettingField2);
   }

   @EventTarget
   public void onEventComponent074_1859(EventComponent074 eventComponent074) {
      if (this.booleanSettingField2.getCurrentValue()) {
         int i;
         if (UtilComponent252.NestedType400.checkCondition_3406()) {
            i = UtilComponent252.NestedType400.computeValue_3414();
         } else {
            i = UtilComponent252.NestedType403.checkCondition_3420() ? UtilComponent252.NestedType403.computeValue_3418() : -1;
         }

         int j = i;
         if (j == -1) {
            this.integerField3 = 0;
            return;
         }

         if (this.integerField3 == -1) {
            this.integerField3 = j;
         } else if (j != this.integerField3 && minecraft.player.age >= 5) {
            minecraft.player.networkHandler.sendChatCommand("event delay");
            this.integerField3 = j;
         }
      }
   }
}
