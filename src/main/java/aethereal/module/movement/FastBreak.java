package aethereal.module.movement;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent074;
import aethereal.setting.FloatSetting;
import net.minecraft.util.hit.BlockHitResult;

@ModuleRegister(
   name = "Fast Break",
   description = "Speeds up the destruction of blocks by processing loot several times per tick",
   category = Category.Movement
)
public class FastBreak extends Module {
   private final FloatSetting floatSettingField1 = new FloatSetting("Digging intensity", 2.0F, 1.0F, 5.0F, 0.25F);

   public FastBreak() {
      this.addSettings(this.floatSettingField1);
   }

   @EventTarget
   public void onEventComponent074_2035(EventComponent074 eventComponent074) {
      if (minecraft.options.attackKey.isPressed()
         && minecraft.interactionManager.isBreakingBlock()
         && minecraft.crosshairTarget instanceof BlockHitResult blockHitResult) {
         BlockHitResult blockHitResult1 = blockHitResult;

         for (int i = 0; i < this.floatSettingField1.getCurrentValue().intValue() - 1; i++) {
            minecraft.interactionManager.updateBlockBreakingProgress(blockHitResult1.getBlockPos(), blockHitResult1.getSide());
         }
      }
   }
}
