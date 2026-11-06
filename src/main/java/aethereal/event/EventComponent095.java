package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;

public class EventComponent095 extends CancellableEvent implements Event {
   private double doubleValueField1;
   private double doubleValueField2;
   private double doubleValueField3;
   private float yaw;
   private float pitch;
   private boolean onGround;
   private boolean isCrouching;
   private boolean isSprinting;

   @Override
   public boolean equals(Object object) {
      if (object == this) {
         return true;
      } else {
         return !(object instanceof EventComponent095 eventComponent0951)
            ? false
            : eventComponent0951.checkCondition_1086(this)
               && super.equals(object)
               && Double.compare(this.getDoubleValueField1_1089(), eventComponent0951.getDoubleValueField1_1089()) == 0
               && Double.compare(this.getDoubleValueField2_1082(), eventComponent0951.getDoubleValueField2_1082()) == 0
               && Double.compare(this.getDoubleValueField3_1077(), eventComponent0951.getDoubleValueField3_1077()) == 0
               && Float.compare(this.getYaw_1083(), eventComponent0951.getYaw_1083()) == 0
               && Float.compare(this.getPitch_1078(), eventComponent0951.getPitch_1078()) == 0
               && this.isOnGround_1079() == eventComponent0951.isOnGround_1079()
               && this.isIsCrouching_1081() == eventComponent0951.isIsCrouching_1081()
               && this.isIsSprinting_1088() == eventComponent0951.isIsSprinting_1088();
      }
   }

   protected boolean checkCondition_1086(Object object) {
      return object instanceof EventComponent095;
   }

   @Override
   public int hashCode() {
      int i = super.hashCode();
      long l = Double.doubleToLongBits(this.getDoubleValueField1_1089());
      int j = i * 59 + (int)(l >>> 32 ^ l);
      long m = Double.doubleToLongBits(this.getDoubleValueField2_1082());
      int k = j * 59 + (int)(m >>> 32 ^ m);
      long n = Double.doubleToLongBits(this.getDoubleValueField3_1077());
      return (
               (
                        (((k * 59 + (int)(n >>> 32 ^ n)) * 59 + Float.floatToIntBits(this.getYaw_1083())) * 59 + Float.floatToIntBits(this.getPitch_1078()))
                              * 59
                           + (this.isOnGround_1079() ? 79 : 97)
                     )
                     * 59
                  + (this.isIsCrouching_1081() ? 79 : 97)
            )
            * 59
         + (this.isIsSprinting_1088() ? 79 : 97);
   }

   public void setDoubleValueField1_1080(double d) {
      this.doubleValueField1 = d;
   }

   public void setDoubleValueField2_1084(double d) {
      this.doubleValueField2 = d;
   }

   public void setDoubleValueField3_1074(double d) {
      this.doubleValueField3 = d;
   }

   public void setYaw_1085(float f) {
      this.yaw = f;
   }

   public void setPitch_1075(float f) {
      this.pitch = f;
   }

   public void setOnGround_1087(boolean bl) {
      this.onGround = bl;
   }

   public void setIsCrouching_1076(boolean bl) {
      this.isCrouching = bl;
   }

   public void setIsSprinting_1090(boolean bl) {
      this.isSprinting = bl;
   }

   @Override
   public String toString() {
      double d = this.getDoubleValueField1_1089();
      double e = this.getDoubleValueField2_1082();
      double f = this.getDoubleValueField3_1077();
      float g = this.getYaw_1083();
      float h = this.getPitch_1078();
      this.isOnGround_1079();
      this.isIsCrouching_1081();
      this.isIsSprinting_1088();
      return "MotionEvent(x="
         + d
         + ", y="
         + d
         + ", z="
         + e
         + ", yaw="
         + d
         + ", pitch="
         + f
         + ", onGround="
         + d
         + ", isCrouching="
         + g
         + ", isSprinting="
         + h
         + ")";
   }

   public double getDoubleValueField1_1089() {
      return this.doubleValueField1;
   }

   public double getDoubleValueField2_1082() {
      return this.doubleValueField2;
   }

   public double getDoubleValueField3_1077() {
      return this.doubleValueField3;
   }

   public float getYaw_1083() {
      return this.yaw;
   }

   public float getPitch_1078() {
      return this.pitch;
   }

   public boolean isOnGround_1079() {
      return this.onGround;
   }

   public boolean isIsCrouching_1081() {
      return this.isCrouching;
   }

   public boolean isIsSprinting_1088() {
      return this.isSprinting;
   }

   public EventComponent095(double d, double e, double f, float g, float h, boolean bl, boolean bl1, boolean bl2) {
      this.doubleValueField1 = d;
      this.doubleValueField2 = e;
      this.doubleValueField3 = f;
      this.yaw = g;
      this.pitch = h;
      this.onGround = bl;
      this.isCrouching = bl1;
      this.isSprinting = bl2;
   }
}
