package aethereal.module.combat;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent074;
import aethereal.event.EventComponent090;
import aethereal.handler.HandlerComponent108;
import aethereal.setting.KeybindSetting;
import aethereal.util.UtilComponent250;
import aethereal.util.UtilComponent270;
import aethereal.util.UtilComponent277;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

@ModuleRegister(name = "Auto EXP", description = "Throws bottles of experience under itself while the specified key is held.", category = Category.Combat)
public class AutoEXP extends Module {
   private boolean flagField1;
   private final KeybindSetting keybindSettingField2 = new KeybindSetting("Activation button", -1, 0)
      .computeValue_2847(() -> this.performAction_1571(true))
      .computeValue_2843(() -> this.performAction_1571(false));
   private final int[] integerArrayField3 = new int[]{-1, -1};

   public AutoEXP() {
      this.addSettings(this.keybindSettingField2);
   }

   @EventTarget
   public void onEventComponent090_1570(EventComponent090 eventComponent090) {
      if (this.flagField1) {
         eventComponent090.performAction_0597(true);
      }
   }

   @EventTarget
   public void onEventComponent074_1569(EventComponent074 eventComponent074) {
      if (minecraft.player != null) {
         if (minecraft.currentScreen != null) {
            this.performAction_1571(false);
         }

         if (this.flagField1) {
            this.performAction_1572();
         }

         if (!this.flagField1 && this.integerArrayField3[0] != -1) {
            minecraft.player.getInventory().setSelectedSlot(this.integerArrayField3[0]);
            if (this.integerArrayField3[1] != -1) {
               HandlerComponent108.getINVENTORY_1158().performAction_1179(7, this.integerArrayField3[1], 1);
            }

            this.integerArrayField3[0] = -1;
            this.integerArrayField3[1] = -1;
         }
      }
   }

   private void performAction_1572() {
      boolean bl = minecraft.player.getMainHandStack().getItem() == Items.EXPERIENCE_BOTTLE;
      if (!bl) {
         int j = UtilComponent270.computeValue_3539(Items.EXPERIENCE_BOTTLE, true);
         if (j != -1) {
            if (minecraft.player.getInventory().getSelectedSlot() != j) {
               minecraft.player.getInventory().setSelectedSlot(j);
            }
         } else {
            int i;
            if (HandlerComponent108.getINVENTORY_1158().getListField1_1177().isEmpty()
               && this.flagField1
               && (i = UtilComponent270.computeValue_3526(Items.EXPERIENCE_BOTTLE)) != -1) {
               if (this.integerArrayField3[1] == -1) {
                  this.integerArrayField3[1] = i;
               }

               if (minecraft.player.getInventory().getSelectedSlot() != 7) {
                  minecraft.player.getInventory().setSelectedSlot(7);
               }

               HandlerComponent108.getINVENTORY_1158().performAction_1179(i, 7, 1);
            }
         }
      } else {
         float f = minecraft.player.age + minecraft.getRenderTickCounter().getTickProgress(false);
         float g = (float)(
               Math.sin(f * 0.8F) * 11.0
                  + Math.sin(f * 0.04000000011823444 + 17.20000385061287) * 1.5
                  + Math.sin(f * 0.10999997043280933 + 5.800000963109878) * 3.0
                  + Math.sin(f * 0.07000004669766619 + 12.300002384186381) * 1.0
            )
            / 3.0F;
         float h = (float)(Math.sin(f * 0.10000000392993033) + Math.sin(f * 0.029999989348000328 + 54.099982886210135) * 0.5);
         HandlerComponent108.getROTATION_1157()
            .performAction_1141(new UtilComponent250(UtilComponent277.getFloatValueField2_3570() + g, 86.0F + h), 70.0F, 1, 3);
         if (UtilComponent250.computeValue_3385().getPitch_3387() > 83.0F) {
            minecraft.interactionManager.interactItem(minecraft.player, Hand.MAIN_HAND);
            minecraft.player.swingHand(Hand.MAIN_HAND);
         }
      }
   }

   private void performAction_1571(boolean bl) {
      this.flagField1 = bl;
      if (bl && minecraft.player != null) {
         this.integerArrayField3[0] = minecraft.player.getInventory().getSelectedSlot();
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.flagField1 = false;
      this.integerArrayField3[0] = -1;
      this.integerArrayField3[1] = -1;
   }
}
