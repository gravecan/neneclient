package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;
import net.minecraft.item.Item;

public class EventComponent082 extends CancellableEvent implements Event {
   private final Item item;
   private final int cooldown;

   public Item getItem_1032() {
      return this.item;
   }

   public int getCooldown_1033() {
      return this.cooldown;
   }

   public EventComponent082(Item item, int i) {
      this.item = itemx;
      this.cooldown = i;
   }
}
