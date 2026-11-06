package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;

public class EventComponent073 extends CancellableEvent implements Event {
   private String text;

   public void setText_0996(String string) {
      this.text = string;
   }

   public EventComponent073(String string) {
      this.text = string;
   }

   public String getText_0995() {
      return this.text;
   }
}
