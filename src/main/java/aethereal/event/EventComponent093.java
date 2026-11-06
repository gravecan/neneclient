package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;

public class EventComponent093 extends CancellableEvent implements Event {
   private final int key;
   private final int scanCode;
   private final int action;
   private final int modifiers;

   public int getKey_1068() {
      return this.key;
   }

   public int getScanCode_1069() {
      return this.scanCode;
   }

   public int getAction_1070() {
      return this.action;
   }

   public int getModifiers_1071() {
      return this.modifiers;
   }

   public EventComponent093(int i, int j, int k, int l) {
      this.key = i;
      this.scanCode = j;
      this.action = k;
      this.modifiers = l;
   }
}
