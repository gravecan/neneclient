package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;
import net.minecraft.item.ItemStack;

public class EventComponent072 extends CancellableEvent implements Event {
   private final int slot;
   private ItemStack stack;

   public void setStack_0978(ItemStack itemStack) {
      this.stack = itemStack;
   }

   public int getSlot_0977() {
      return this.slot;
   }

   public ItemStack getStack_0976() {
      return this.stack;
   }

   public EventComponent072(int i, ItemStack itemStack) {
      this.slot = i;
      this.stack = itemStack;
   }
}
