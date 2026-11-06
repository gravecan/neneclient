package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;

public class EventComponent091 extends CancellableEvent implements Event {
   private float forward;
   private float strafe;
   private boolean jump;
   private boolean sneak;

   public void setForward_1065(float f) {
      this.forward = f;
   }

   public void setStrafe_1060(float f) {
      this.strafe = f;
   }

   public void setJump_1066(boolean bl) {
      this.jump = bl;
   }

   public void setSneak_1061(boolean bl) {
      this.sneak = bl;
   }

   public float getForward_1062() {
      return this.forward;
   }

   public float getStrafe_1063() {
      return this.strafe;
   }

   public boolean isJump_1059() {
      return this.jump;
   }

   public boolean isSneak_1064() {
      return this.sneak;
   }

   public EventComponent091(float f, float g, boolean bl, boolean bl1) {
      this.forward = f;
      this.strafe = g;
      this.jump = bl;
      this.sneak = bl1;
   }
}
