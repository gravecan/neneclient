package aethereal.module.player;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent074;
import aethereal.util.UtilComponent265;

@ModuleRegister(name = "Death Coords", description = "Displays the coordinates of the last death", category = Category.Player)
public class DeathCoords extends Module {
   @EventTarget
   public void onEventComponent074_2174(EventComponent074 eventComponent074) {
      if (minecraft.player.deathTime == 1) {
         UtilComponent265.performAction_3505(
            String.format(
               "You died at coordinates: &c[%d, %d, %d]",
               minecraft.player.getBlockPos().getX(),
               minecraft.player.getBlockPos().getY(),
               minecraft.player.getBlockPos().getZ()
            )
         );
      }
   }
}
