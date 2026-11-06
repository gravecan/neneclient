package aethereal.module.movement;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent074;
import aethereal.setting.BooleanSetting;
import aethereal.setting.MultiSelectSetting;
import net.minecraft.item.BlockItem;
import platform.inject.accessors.LivingEntityAccessor;
import platform.inject.accessors.MinecraftClientAccessor;

@ModuleRegister(name = "No Delay", description = "Removes delay from selected actions", category = Category.Movement)
public class NoDelay extends Module {
   public final MultiSelectSetting multiSelectSettingField1 = new MultiSelectSetting(
      "Disable delay on", new BooleanSetting("Delivery of blocks", true), new BooleanSetting("Jumping", true)
   );

   public NoDelay() {
      this.addSettings(this.multiSelectSettingField1);
   }

   @EventTarget
   public void onEventComponent074_2045(EventComponent074 eventComponent074) {
      if (this.multiSelectSettingField1.computeValue_2852("Delivery of blocks").getCurrentValue()
         && minecraft.player.getMainHandStack().getItem() instanceof BlockItem
         && !NeneClient.getInstance().getProcessors().getModules().getScaffoldField26_0452().isEnabled()) {
         ((MinecraftClientAccessor)minecraft).setItemUseCooldown(0);
      }

      if (this.multiSelectSettingField1.computeValue_2852("Jumping").getCurrentValue()) {
         ((LivingEntityAccessor)minecraft.player).setJumpingCooldown(0);
      }
   }
}
