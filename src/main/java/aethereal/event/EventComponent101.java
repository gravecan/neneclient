package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;
import net.minecraft.text.Text;

public class EventComponent101 extends CancellableEvent implements Event {
   private Text title;

   public void setTitle_1100(Text text) {
      this.title = text;
   }

   public Text getTitle_1099() {
      return this.title;
   }

   public EventComponent101(Text text) {
      this.title = text;
   }
}
