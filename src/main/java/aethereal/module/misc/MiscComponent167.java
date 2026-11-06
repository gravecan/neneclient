package aethereal.module.misc;

import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent074;
import aethereal.event.EventComponent091;
import aethereal.handler.HandlerAnnotation107;
import aethereal.handler.HandlerComponent104;
import aethereal.util.UtilComponent250;
import aethereal.util.UtilComponent279;
import java.util.concurrent.ThreadLocalRandom;

@HandlerAnnotation107
public class MiscComponent167 extends HandlerComponent104 implements MinecraftAccess {
   private int integerField1 = -1;

   public int getIntegerField1_1807() {
      return this.integerField1;
   }

   public void setIntegerField1_1806(int i) {
      this.integerField1 = i;
   }

   public boolean checkCondition_1808() {
      return this.integerField1 > 0;
   }

   @EventTarget
   public void onEventComponent074_1809(EventComponent074 eventComponent074) {
      if (this.integerField1 > 0) {
         this.integerField1--;
      }
   }

   @EventTarget
   public void onEventComponent091_1810(EventComponent091 eventComponent091) {
      if (this.integerField1 > 0) {
         eventComponent091.setForward_1065(ThreadLocalRandom.current().nextBoolean() ? 1.0F : -1.0F);
         eventComponent091.setStrafe_1060(ThreadLocalRandom.current().nextBoolean() ? 1.0F : -1.0F);
         NeneClient.getInstance()
            .getProcessors()
            .computeValue_0672()
            .performAction_1141(
               new UtilComponent250(
                  minecraft.player.getYaw() + UtilComponent279.computeValue_3582(-2.0F, 2.0F),
                  UtilComponent279.computeValue_3577(minecraft.player.getPitch() + UtilComponent279.computeValue_3582(-1.0F, 1.0F), -90.0F, 90.0F)
               ),
               150.0F,
               10,
               1
            );
      }
   }
}
