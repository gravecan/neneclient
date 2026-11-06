package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;

public class ClickEvent extends CancellableEvent implements Event {
   private final ClickEvent.State314 type;
   private final double mouseX;
   private final double mouseY;
   private final int button;

   public ClickEvent.State314 getType_0985() {
      return this.type;
   }

   public double getMouseX_0979() {
      return this.mouseX;
   }

   public double getMouseY_0982() {
      return this.mouseY;
   }

   public int getButton_0980() {
      return this.button;
   }

   public ClickEvent(double d, double e, int i, ClickEvent.State314 state314) {
      this.mouseX = d;
      this.mouseY = e;
      this.button = i;
      this.type = state314;
   }

   public boolean checkCondition_0983() {
      return this.type == ClickEvent.State314.PRESS;
   }

   public boolean checkCondition_0984() {
      return this.type == ClickEvent.State314.RELEASE;
   }

   public boolean checkCondition_0981() {
      return this.type == ClickEvent.State314.DRAG;
   }

   public enum State314 {
      PRESS,
      RELEASE,
      DRAG;
   }
}
