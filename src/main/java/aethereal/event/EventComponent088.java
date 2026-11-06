package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class EventComponent088 extends CancellableEvent implements Event {
   private final MatrixStack matrices;
   private final ItemStack stack;
   private final Hand hand;

   public EventComponent088(MatrixStack matrixStack, ItemStack itemStack, Hand hand) {
      this.matrices = matrixStack;
      this.stack = itemStack;
      this.hand = handx;
   }

   public MatrixStack getMatrices_1048() {
      return this.matrices;
   }

   public ItemStack getStack_1047() {
      return this.stack;
   }

   public Hand getHand_1046() {
      return this.hand;
   }
}
