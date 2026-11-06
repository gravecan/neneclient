package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Hand;

public class EventComponent087 extends CancellableEvent implements Event {
   private final MatrixStack matrices;
   private final Hand hand;
   private final float swingProgress;
   private final int armX;

   public EventComponent087(MatrixStack matrixStack, Hand hand, float f, int i) {
      this.matrices = matrixStack;
      this.hand = handx;
      this.swingProgress = f;
      this.armX = i;
   }

   public MatrixStack getMatrices_1042() {
      return this.matrices;
   }

   public Hand getHand_1044() {
      return this.hand;
   }

   public float getSwingProgress_1043() {
      return this.swingProgress;
   }

   public int getArmX_1045() {
      return this.armX;
   }
}
