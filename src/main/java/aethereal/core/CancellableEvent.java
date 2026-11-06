package aethereal.core;

public class CancellableEvent implements CoreContract032, Event {
   private boolean flagField1;

   protected CancellableEvent() {
   }

   @Override
   public boolean checkCondition_0596() {
      return this.flagField1;
   }

   @Override
   public void performAction_0597(boolean bl) {
      this.flagField1 = bl;
   }
}
