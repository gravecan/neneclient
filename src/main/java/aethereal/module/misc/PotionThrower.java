package aethereal.module.misc;

import aethereal.autobuy.AutoBuyEntry;
import aethereal.core.Category;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.setting.ChoiceSetting;
import aethereal.setting.KeybindSetting;
import aethereal.ui.screen.ScreenComponent213;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

@ModuleRegister(name = "Potion Thrower", description = "Quickly throw buffs through the wheel or on a key", category = Category.Misc)
public class PotionThrower extends Module implements MinecraftAccess {
   private final ChoiceSetting choiceSettingField1 = new ChoiceSetting("Method of using potions", "Selection wheel", "Selection wheel", "Key");
   private final ScreenComponent213 screenComponent213Field2 = new ScreenComponent213(Text.literal("Potion Thrower"));
   private final KeybindSetting keybindSettingField3 = new KeybindSetting("Open potions menu", 86, 0)
      .computeValue_2847(() -> minecraft.setScreen(this.screenComponent213Field2))
      .computeValue_2843(() -> {
         if (minecraft.currentScreen == this.screenComponent213Field2) {
            this.screenComponent213Field2.performAction_3073(this.screenComponent213Field2.getIntegerField6_3068());
            if (minecraft.currentScreen == this.screenComponent213Field2) {
               minecraft.setScreen((Screen)null);
            }
         }
      })
      .withVisibility(() -> this.choiceSettingField1.checkCondition_2850("Selection wheel"));

   public ScreenComponent213 getScreenComponent213Field2_1949() {
      return this.screenComponent213Field2;
   }

   public PotionThrower() {
      this.addSettings(this.choiceSettingField1, this.keybindSettingField3);

      for (AutoBuyEntry autoBuyEntry : AutoBuyEntry.values()) {
         if (autoBuyEntry.getItem_0031() == Items.SPLASH_POTION) {
            this.addSettings(
               new KeybindSetting(autoBuyEntry.getDisplayName_0029(), -1)
                  .computeValue_2847(
                     () -> NeneClient.getInstance()
                        .getProcessors()
                        .getHandlerComponent106Field14_0673()
                        .computeValue_1151()
                        .performAction_1225(autoBuyEntry.computeValue_0025())
                  )
                  .withVisibility(() -> this.choiceSettingField1.checkCondition_2850("Key"))
            );
         }
      }
   }
}
