package aethereal.module.misc;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent096;

@ModuleRegister(name = "Portal Bypass", description = "Allows you to open windows while in the portal", category = Category.Misc)
public class PortalBypass extends Module {
   @EventTarget
   public void onEventComponent096_1946(EventComponent096 eventComponent096) {
      eventComponent096.setInPortal_1092(false);
   }
}
