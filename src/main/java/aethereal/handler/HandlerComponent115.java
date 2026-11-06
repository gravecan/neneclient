package aethereal.handler;

import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.event.EventComponent074;
import aethereal.event.EventComponent091;

@HandlerAnnotation107
public class HandlerComponent115 extends HandlerComponent104 implements MinecraftAccess {
   private int integerField1 = -1;

   public int getIntegerField1_1217() {
      return this.integerField1;
   }

   public void setIntegerField1_1212(int i) {
      this.integerField1 = i;
   }

   public boolean checkCondition_1213() {
      return this.integerField1 != -1;
   }

   public boolean checkCondition_1216() {
      return this.integerField1 == 0;
   }

   @EventTarget
   public void onEventComponent074_1214(EventComponent074 eventComponent074) {
      if (this.integerField1 > 0) {
         this.integerField1--;
      } else if (this.integerField1 == 0) {
         this.integerField1 = -1;
      }
   }

   @EventTarget(priority = 0)
   public void onEventComponent091_1215(EventComponent091 eventComponent091) {
      if (this.integerField1 >= 0) {
         eventComponent091.setForward_1065(0.0F);
         eventComponent091.setStrafe_1060(0.0F);
         eventComponent091.setJump_1066(false);
      }
   }
}
