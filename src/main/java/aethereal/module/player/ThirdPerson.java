package aethereal.module.player;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent074;
import aethereal.event.EventComponent093;
import aethereal.setting.ChoiceSetting;
import aethereal.setting.KeybindSetting;
import aethereal.util.UtilComponent250;
import aethereal.util.UtilComponent277;
import aethereal.util.UtilComponent279;
import net.minecraft.client.option.Perspective;

@ModuleRegister(name = "Third Person", description = "Free third-person view without changing the direction of movement", category = Category.Player)
public class ThirdPerson extends Module {
   private boolean flagField1;
   private UtilComponent250 utilComponent250Field2;
   private final ChoiceSetting choiceSettingField3 = new ChoiceSetting("Inspection activation mode", "On click", "On click", "By clamping");
   private final KeybindSetting keybindSettingField4 = new KeybindSetting("Inspect button", 342, 0).computeValue_2847(() -> {
      if (this.choiceSettingField3.checkCondition_2850("By clamping")) {
         this.performAction_2209(true);
      } else {
         this.performAction_2209(!this.flagField1);
      }
   }).computeValue_2843(() -> {
      if (this.flagField1 && this.choiceSettingField3.checkCondition_2850("By clamping")) {
         this.performAction_2209(false);
      }
   });

   public ThirdPerson() {
      this.addSettings(this.choiceSettingField3, this.keybindSettingField4);
   }

   @EventTarget
   public void onEventComponent093_2208(EventComponent093 eventComponent093) {
      if (this.flagField1 && eventComponent093.getKey_1068() == minecraft.options.togglePerspectiveKey.getDefaultKey().getCode()) {
         eventComponent093.performAction_0597(true);
      }
   }

   @EventTarget
   public void onEventComponent074_2207(EventComponent074 eventComponent074) {
      if (this.flagField1) {
         if (minecraft.currentScreen != null) {
            this.performAction_2209(false);
         } else {
            NeneClient.getInstance()
               .getProcessors()
               .computeValue_0672()
               .performAction_1141(
                  new UtilComponent250(minecraft.player.getYaw(), UtilComponent279.computeValue_3577(minecraft.player.getPitch(), -89.0F, 89.0F)), 360.0F, 0, 1
               );
         }
      }
   }

   private void performAction_2209(boolean bl) {
      UtilComponent277 utilComponent277 = NeneClient.getInstance().getProcessors().computeValue_0672().getUtilComponent277Field1_1136();
      if (bl) {
         this.utilComponent250Field2 = new UtilComponent250(UtilComponent277.getFloatValueField2_3570(), UtilComponent277.getFloatValueField3_3571());
         UtilComponent277.setFloatValueField2_3574(this.utilComponent250Field2.getYaw_3386());
         UtilComponent277.setFloatValueField3_3565(this.utilComponent250Field2.getPitch_3387());
      } else if (this.utilComponent250Field2 != null) {
         UtilComponent277.setFloatValueField2_3574(this.utilComponent250Field2.getYaw_3386());
         UtilComponent277.setFloatValueField3_3565(this.utilComponent250Field2.getPitch_3387());
      }

      if (utilComponent277.isFlagField1_3566() != bl) {
         utilComponent277.performAction_3569(bl);
      }

      minecraft.options.setPerspective(bl ? Perspective.THIRD_PERSON_BACK : Perspective.FIRST_PERSON);
      this.flagField1 = bl;
   }
}
