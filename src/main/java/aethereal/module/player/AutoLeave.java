package aethereal.module.player;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent074;
import aethereal.setting.BooleanSetting;
import aethereal.setting.FloatSetting;
import aethereal.setting.MultiSelectSetting;
import aethereal.util.UtilComponent252;
import aethereal.util.UtilComponent265;
import net.minecraft.entity.player.PlayerEntity;

@ModuleRegister(name = "Auto Leave", description = "Automatically goes to the hub based on triggers", category = Category.Player)
public class AutoLeave extends Module {
   private final MultiSelectSetting multiSelectSettingField1 = new MultiSelectSetting(
      "Trigger conditions", new BooleanSetting("Low HP", true), new BooleanSetting("Players nearby", true)
   );
   private final FloatSetting floatSettingField2 = new FloatSetting("Minimum HP", 8.0F, 1.0F, 20.0F, 0.5F)
      .withVisibility(() -> this.multiSelectSettingField1.computeValue_2852("Low HP").getCurrentValue());
   private final FloatSetting floatSettingField3 = new FloatSetting("Player distance", 8.0F, 8.0F, 128.0F, 1.0F)
      .withVisibility(() -> this.multiSelectSettingField1.computeValue_2852("Players nearby").getCurrentValue());

   public AutoLeave() {
      this.addSettings(this.multiSelectSettingField1, this.floatSettingField2, this.floatSettingField3);
   }

   @EventTarget
   public void onEventComponent074_2146(EventComponent074 eventComponent074) {
      if (!minecraft.world.getRegistryKey().getValue().toString().equals("minecraft:lobby")) {
         if (!UtilComponent252.NestedType400.checkCondition_3406() || UtilComponent252.NestedType400.computeValue_3414() != -1) {
            PlayerEntity playerEntity = minecraft.world
               .getPlayers()
               .stream()
               .filter(
                  abstractClientPlayerEntity -> abstractClientPlayerEntity != minecraft.player
                     && !(
                        minecraft.player.squaredDistanceTo(abstractClientPlayerEntity)
                           > this.floatSettingField3.getCurrentValue() * this.floatSettingField3.getCurrentValue()
                     )
                     && !NeneClient.getInstance().getProcessors().getFriends().checkCondition_1107(abstractClientPlayerEntity.getName().getString())
               )
               .findFirst()
               .orElse(null);
            if ((
                  this.multiSelectSettingField1.computeValue_2852("Low HP").getCurrentValue()
                        && minecraft.player.getHealth() <= this.floatSettingField2.getCurrentValue()
                     || this.multiSelectSettingField1.computeValue_2852("Players nearby").getCurrentValue() && playerEntity != null
               )
               && !UtilComponent252.checkCondition_3401()) {
               minecraft.player.networkHandler.sendChatCommand("hub");
               if (playerEntity != null) {
                  UtilComponent265.performAction_3505(
                     "Left anarchy: player &c nearby"
                        + playerEntity.getName().getString()
                        + "&7 to &c"
                        + Math.round(Math.sqrt(minecraft.player.squaredDistanceTo(playerEntity)))
                        + "&7 blocks."
                  );
               } else {
                  UtilComponent265.performAction_3505("Left &canarchy: critically low health&7.");
               }

               this.toggle();
            }
         }
      }
   }
}
