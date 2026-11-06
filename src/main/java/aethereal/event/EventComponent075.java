package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class EventComponent075 extends CancellableEvent implements Event {
   private final ItemStack stack;
   private final List<Text> lines;

   public ItemStack getStack_1007() {
      return this.stack;
   }

   public List<Text> getLines_1006() {
      return this.lines;
   }

   public EventComponent075(ItemStack itemStack, List<Text> list) {
      this.stack = itemStack;
      this.lines = list;
   }
}
