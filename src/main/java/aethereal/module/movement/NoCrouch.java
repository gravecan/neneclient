package aethereal.module.movement;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent091;

@ModuleRegister(name = "No Crouch", description = "Removes crouch slow on your side", category = Category.Movement)
public class NoCrouch extends Module {
   @EventTarget
   public void onEventComponent091_2044(EventComponent091 eventComponent091) {
      eventComponent091.setSneak_1061(false);
   }
}
