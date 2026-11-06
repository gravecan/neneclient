package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;
import net.minecraft.util.hit.HitResult;

public class EventComponent084 extends CancellableEvent implements Event {
   private final float tickNeneClient;
   private HitResult hitResultField1;

   public void setHitResultField1_1038(HitResult hitResult) {
      this.hitResultField1 = hitResult;
   }

   public float getTickNeneClient_1036() {
      return this.tickNeneClient;
   }

   public HitResult getHitResultField1_1037() {
      return this.hitResultField1;
   }

   public EventComponent084(float f) {
      this.tickNeneClient = f;
   }
}
