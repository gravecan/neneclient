package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;
import net.minecraft.item.ItemStack;

public class EventComponent081 extends CancellableEvent implements Event {
   private final ItemStack stack;

   public EventComponent081(ItemStack itemStack) {
      this.stack = itemStack;
   }

   public ItemStack getStack_1031() {
      return this.stack;
   }
}
