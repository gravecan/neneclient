package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;
import net.minecraft.client.gui.DrawContext;

public class EventComponent083 extends CancellableEvent implements Event {
   private final DrawContext context;
   private final float partialTicks;

   public DrawContext getContext_1034() {
      return this.context;
   }

   public float getPartialTicks_1035() {
      return this.partialTicks;
   }

   public EventComponent083(DrawContext drawContext, float f) {
      this.context = drawContext;
      this.partialTicks = f;
   }
}
