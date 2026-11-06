package aethereal.module.combat;

import aethereal.core.Category;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.handler.HandlerComponent108;
import aethereal.setting.ChoiceSetting;
import aethereal.setting.KeybindSetting;
import aethereal.ui.screen.ScreenComponent225;
import aethereal.util.UtilComponent270;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

@ModuleRegister(name = "Auto Swap", description = "Instantly transfers selected items to the second hand by pressing a key", category = Category.Combat)
public class AutoSwap extends Module implements MinecraftAccess {
   private final ChoiceSetting choiceSettingField1 = new ChoiceSetting("Move mode", "Double", "Double", "Triple");
   private final ChoiceSetting choiceSettingField2 = new ChoiceSetting("First item", "Sphere", "Sphere", "Totem", "Golden Apple", "Shield")
      .withVisibility(() -> this.choiceSettingField1.checkCondition_2850("Double"));
   private final ChoiceSetting choiceSettingField3 = new ChoiceSetting("Second item", "Totem", "Sphere", "Totem", "Golden Apple", "Shield")
      .withVisibility(() -> this.choiceSettingField1.checkCondition_2850("Double"));
   private final ScreenComponent225 screenComponent225Field4 = new ScreenComponent225(Text.literal("SwapMenu"));
   private final KeybindSetting keybindSettingField5 = new KeybindSetting("Move button", 86, 0)
      .computeValue_2847(
         () -> {
            if (!NeneClient.getInstance().getProcessors().getModules().getAutoTotemField8_0407().flagField7) {
               if (this.choiceSettingField1.checkCondition_2850("Double")) {
                  HandlerComponent108.getINVENTORY_1158()
                     .performAction_1179(
                        UtilComponent270.computeValue_3530(
                           minecraft.player.getOffHandStack().getItem() == this.computeValue_1585(this.choiceSettingField2)
                              ? this.computeValue_1585(this.choiceSettingField3)
                              : this.computeValue_1585(this.choiceSettingField2)
                        ),
                        45,
                        1
                     );
               } else if (this.choiceSettingField1.checkCondition_2850("Triple")) {
                  minecraft.setScreen(this.screenComponent225Field4);
               }
            }
         }
      )
      .computeValue_2843(() -> {
         if (minecraft.currentScreen instanceof ScreenComponent225) {
            ((ScreenComponent225)minecraft.currentScreen).performAction_3252();
            if (minecraft.currentScreen instanceof ScreenComponent225) {
               minecraft.setScreen((Screen)null);
            }
         }
      });

   public ScreenComponent225 getScreenComponent225Field4_1584() {
      return this.screenComponent225Field4;
   }

   public AutoSwap() {
      this.addSettings(this.keybindSettingField5, this.choiceSettingField1, this.choiceSettingField2, this.choiceSettingField3);
   }

   private Item computeValue_1585(ChoiceSetting choiceSetting) {
      switch ((String)choiceSetting.getCurrentValue()) {
         case "Sphere":
            return Items.PLAYER_HEAD;
         case "Totem":
            return Items.TOTEM_OF_UNDYING;
         case "Golden Apple":
            return Items.GOLDEN_APPLE;
         case "Shield":
            return Items.SHIELD;
         default:
            return null;
      }
   }
}
