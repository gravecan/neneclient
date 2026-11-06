package aethereal.module.combat;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent074;
import aethereal.event.EventComponent077;
import aethereal.event.EventComponent091;
import net.minecraft.entity.player.PlayerEntity;

@ModuleRegister(name = "Shift TAP", description = "Automatically crouches when a player is hit", category = Category.Combat)
public class ShiftTAP extends Module {
   private int integerField1;

   @EventTarget
   public void onEventComponent077_1640(EventComponent077 eventComponent077) {
      if (eventComponent077.getEntity_1021() instanceof PlayerEntity) {
         this.integerField1 = 2;
      }
   }

   @EventTarget
   public void onEventComponent074_1639(EventComponent074 eventComponent074) {
      if (this.integerField1 > 0) {
         this.integerField1--;
      }
   }

   @EventTarget
   public void onEventComponent091_1641(EventComponent091 eventComponent091) {
      if (this.integerField1 > 0) {
         eventComponent091.setSneak_1061(true);
      }
   }
}
