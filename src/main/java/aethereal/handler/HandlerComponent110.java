package aethereal.handler;

import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.event.EventComponent074;
import aethereal.util.UtilComponent270;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.screen.slot.SlotActionType;

@HandlerAnnotation107
public class HandlerComponent110 extends HandlerComponent104 implements MinecraftAccess {
   private final List<HandlerComponent110.NestedType323> listField1 = new ArrayList<>();

   public List<HandlerComponent110.NestedType323> getListField1_1177() {
      return this.listField1;
   }

   @EventTarget
   public void onEventComponent074_1176(EventComponent074 eventComponent074) {
      if (!this.listField1.isEmpty()) {
         HandlerComponent110.NestedType323 nestedType323 = this.listField1.getFirst();
         HandlerComponent115 handlerComponent115 = HandlerComponent108.getSTOP_1159();
         if (handlerComponent115.getIntegerField1_1217() < nestedType323.getBypass_1186()) {
            int i = this.computeValue_1174(nestedType323.getFrom_1185());
            int j = nestedType323.isRaw_1183() ? nestedType323.getIntegerField1_1184() : this.computeValue_1174(nestedType323.getIntegerField1_1184());
            if (minecraft.player.playerScreenHandler.getSlot(i).getStack().contains(DataComponentTypes.BUNDLE_CONTENTS)) {
               minecraft.interactionManager.clickSlot(minecraft.player.playerScreenHandler.syncId, i, 1, SlotActionType.PICKUP, minecraft.player);
               minecraft.interactionManager.clickSlot(minecraft.player.playerScreenHandler.syncId, j, 0, SlotActionType.PICKUP, minecraft.player);
               if (!minecraft.player.playerScreenHandler.getCursorStack().isEmpty()) {
                  minecraft.interactionManager.clickSlot(minecraft.player.playerScreenHandler.syncId, i, 0, SlotActionType.PICKUP, minecraft.player);
               }
            } else {
               int k = this.computeValue_1178(nestedType323.getIntegerField1_1184(), j);
               if (k != -1) {
                  minecraft.interactionManager.clickSlot(minecraft.player.playerScreenHandler.syncId, i, k, SlotActionType.SWAP, minecraft.player);
               } else {
                  int l = this.computeValue_1178(nestedType323.getFrom_1185(), i);
                  if (l != -1) {
                     minecraft.interactionManager.clickSlot(minecraft.player.playerScreenHandler.syncId, j, l, SlotActionType.SWAP, minecraft.player);
                  } else if (i != j) {
                     minecraft.interactionManager.clickSlot(minecraft.player.playerScreenHandler.syncId, i, 0, SlotActionType.SWAP, minecraft.player);
                     minecraft.interactionManager.clickSlot(minecraft.player.playerScreenHandler.syncId, j, 0, SlotActionType.SWAP, minecraft.player);
                     minecraft.interactionManager.clickSlot(minecraft.player.playerScreenHandler.syncId, i, 0, SlotActionType.SWAP, minecraft.player);
                  }
               }
            }

            minecraft.player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(minecraft.player.currentScreenHandler.syncId));
            this.listField1.remove(nestedType323);
            if (!this.listField1.isEmpty()) {
               handlerComponent115.setIntegerField1_1212(this.listField1.getFirst().getBypass_1186());
            }
         }
      }
   }

   public void performAction_1179(int i, int j, int k) {
      this.performAction_1180(new HandlerComponent110.NestedType323(i, j, k, false));
   }

   public void performAction_1175(int i, int j, int k) {
      this.performAction_1180(new HandlerComponent110.NestedType323(i, 5 + j, k, true));
   }

   public void performAction_1181(Item item, int i, int j) {
      int k = UtilComponent270.computeValue_3526(item);
      if (k != -1) {
         this.performAction_1180(new HandlerComponent110.NestedType323(k, i, j, false));
      }
   }

   public void performAction_1182(ItemStack itemStack, int i, int j) {
      int k = UtilComponent270.computeValue_3545(itemStack, false);
      if (k != -1) {
         this.performAction_1180(new HandlerComponent110.NestedType323(k, i, j, false));
      }
   }

   private void performAction_1180(HandlerComponent110.NestedType323 nestedType323) {
      if (nestedType323.getFrom_1185() != -1 && nestedType323.getIntegerField1_1184() != -1) {
         if (this.listField1.isEmpty() && nestedType323.bypass > 0) {
            HandlerComponent108.getSTOP_1159().setIntegerField1_1212(nestedType323.bypass);
         }

         this.listField1.add(nestedType323);
      }
   }

   private int computeValue_1174(int i) {
      return i >= 0 && i <= 8 ? i + 36 : i;
   }

   private int computeValue_1178(int i, int j) {
      if (i == 40 || i == 45 || j == 45) {
         return 40;
      } else {
         return j >= 36 && j <= 44 ? j - 36 : -1;
      }
   }

   static final class NestedType323 {
      private final int from;
      private final int integerField1;
      final int bypass;
      private final boolean raw;

      public int getFrom_1185() {
         return this.from;
      }

      public int getIntegerField1_1184() {
         return this.integerField1;
      }

      public int getBypass_1186() {
         return this.bypass;
      }

      public boolean isRaw_1183() {
         return this.raw;
      }

      public NestedType323(int i, int j, int k, boolean bl) {
         this.from = i;
         this.integerField1 = j;
         this.bypass = k;
         this.raw = bl;
      }
   }
}
