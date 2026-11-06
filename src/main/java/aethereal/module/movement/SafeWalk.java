package aethereal.module.movement;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent091;

@ModuleRegister(name = "Safe Walk", description = "Prevents you from falling off the edge of blocks", category = Category.Movement)
public class SafeWalk extends Module {
   @EventTarget
   public void onEventComponent091_2049(EventComponent091 eventComponent091) {
      this.onEventComponent091_2050(eventComponent091);
   }

   public void onEventComponent091_2050(EventComponent091 eventComponent091) {
      eventComponent091.setSneak_1061(
         eventComponent091.isSneak_1064()
            || minecraft.world
                  .getBlockState(minecraft.player.getBlockPos().down())
                  .getCollisionShape(minecraft.world, minecraft.player.getBlockPos().down())
                  .isEmpty()
               && minecraft.player.isOnGround()
      );
   }
}
