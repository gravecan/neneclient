package aethereal.module.player;

import aethereal.core.Category;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.setting.KeybindSetting;
import aethereal.util.UtilComponent265;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.hit.EntityHitResult;

@ModuleRegister(name = "Click Action", description = "Performs the action associated with the selected key", category = Category.Player)
public class ClickAction extends Module implements MinecraftAccess {
   private final KeybindSetting keybindSettingField1 = new KeybindSetting("Ender pearl", -1)
      .computeValue_2847(
         () -> NeneClient.getInstance()
            .getProcessors()
            .getHandlerComponent106Field14_0673()
            .computeValue_1151()
            .performAction_1225(Items.ENDER_PEARL.getDefaultStack())
      );
   private final KeybindSetting keybindSettingField2 = new KeybindSetting("Adding a friend", -1)
      .computeValue_2847(
         () -> {
            if ((minecraft.crosshairTarget instanceof EntityHitResult ? (EntityHitResult)minecraft.crosshairTarget : null) instanceof EntityHitResult entityHitResult
               && entityHitResult.getEntity() instanceof AbstractClientPlayerEntity abstractClientPlayerEntity1) {
               AbstractClientPlayerEntity abstractClientPlayerEntity = abstractClientPlayerEntity1;
               if (abstractClientPlayerEntity != minecraft.player) {
                  String string = abstractClientPlayerEntity.getName().getString();
                  if (NeneClient.getInstance().getProcessors().getFriends().checkCondition_1107(string)) {
                     NeneClient.getInstance().getProcessors().getFriends().performAction_1111(string);
                     NeneClient.getInstance().getProcessors().getFriends().save();
                     UtilComponent265.performAction_3505("Comrade " + string + "was successfully removed from your friends list.");
                  } else {
                     NeneClient.getInstance().getProcessors().getFriends().performAction_1106(string);
                     NeneClient.getInstance().getProcessors().getFriends().save();
                     UtilComponent265.performAction_3505("Comrade " + string + "was successfully added to your friends list.");
                  }
               }
            }
         }
      );

   public KeybindSetting getKeybindSettingField1_2170() {
      return this.keybindSettingField1;
   }

   public KeybindSetting getKeybindSettingField2_2171() {
      return this.keybindSettingField2;
   }

   public ClickAction() {
      this.addSettings(this.keybindSettingField1, this.keybindSettingField2);
   }
}
