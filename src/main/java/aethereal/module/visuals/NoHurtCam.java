package aethereal.module.visuals;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.RemovalsEvent;

@ModuleRegister(name = "No Hurt Cam", description = "Removes damage camera shake without changing other overlays", category = Category.Visuals)
public class NoHurtCam extends Module {
   @EventTarget
   public void onRemovalsEvent_2344(RemovalsEvent removalsEvent) {
      if (removalsEvent.getType_1020() == RemovalsEvent.State319.HURT_CAM) {
         removalsEvent.performAction_0597(true);
      }
   }
}
