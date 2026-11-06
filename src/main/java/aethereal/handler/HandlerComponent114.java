package aethereal.handler;

import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent074;
import aethereal.event.EventComponent091;
import aethereal.event.PacketEvent;
import aethereal.util.UtilComponent250;
import aethereal.util.UtilComponent252;
import aethereal.util.UtilComponent265;
import aethereal.util.UtilComponent270;
import aethereal.util.UtilComponent280;
import java.util.ArrayDeque;
import java.util.Deque;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;

@HandlerAnnotation107
public class HandlerComponent114 extends HandlerComponent104 implements MinecraftAccess {
   private final Deque<HandlerComponent114.Contract325> dequeField1 = new ArrayDeque<>();

   public Deque<HandlerComponent114.Contract325> getDequeField1_1208() {
      return this.dequeField1;
   }

   @EventTarget
   public void onEventComponent074_1205(EventComponent074 eventComponent074) {
      if (!this.dequeField1.isEmpty() && this.dequeField1.peek().checkCondition_1211()) {
         this.dequeField1.poll();
      }
   }

   @EventTarget(priority = 4)
   public void onEventComponent091_1207(EventComponent091 eventComponent091) {
      if (!this.dequeField1.isEmpty()) {
         UtilComponent280.onEventComponent091_3591(eventComponent091);
      }
   }

   @EventTarget
   public void onPacketEvent_1206(PacketEvent packetEvent) {
      if (!this.dequeField1.isEmpty() && packetEvent.checkCondition_1013()) {
         GameMessageS2CPacket gameMessageS2CPacket = (GameMessageS2CPacket)packetEvent.getPacket_1015();
         if (gameMessageS2CPacket instanceof GameMessageS2CPacket gameMessageS2CPacket1
            && gameMessageS2CPacket1.content().getString().equals("This command is not available in AFK")) {
            NeneClient.getInstance().getProcessors().getHandlerComponent106Field14_0673().getMiscComponent167Field4_1155().setIntegerField1_1806(7);
         }
      }
   }

   public boolean checkCondition_1210(ItemStack itemStack, double d, double e) {
      for (HandlerComponent114.Contract325 contract325 : this.dequeField1) {
         if (contract325 instanceof HandlerComponent114.NestedType326) {
            return false;
         }
      }

      if (computeValue_1209(itemStack) > d) {
         return true;
      }

      if (!UtilComponent270.checkCondition_3544(itemStack, Enchantments.MENDING, 1)) {
         UtilComponent265.performAction_3505("There is no repair on the item, cancelled.");
         return true;
      }

      ItemStack itemStack3 = minecraft.player.getMainHandStack();
      ItemStack itemStack4 = minecraft.player.getOffHandStack();
      ItemStack itemStack1;
      if (!itemStack3.isEmpty() && !ItemStack.areItemsAndComponentsEqual(itemStack3, itemStack)) {
         itemStack1 = itemStack3.copy();
      } else {
         itemStack1 = !itemStack4.isEmpty() && itemStack4.getItem() != itemStack.getItem() ? itemStack4.copy() : ItemStack.EMPTY;
      }

      ItemStack itemStack2 = itemStack1;
      this.dequeField1.add(new HandlerComponent114.NestedType326(itemStack.getItem(), e, minecraft.player.getInventory().getSelectedSlot(), itemStack2));
      return false;
   }

   static double computeValue_1209(ItemStack itemStack) {
      return itemStack.isDamageable() && itemStack.getMaxDamage() > 0 ? (1.0 - (double)itemStack.getDamage() / itemStack.getMaxDamage()) * 100.0 : 100.0;
   }

   interface Contract325 {
      boolean checkCondition_1211();
   }

   static final class NestedType324 implements MinecraftAccess, HandlerComponent114.Contract325 {
      final Item targetItem;
      final int need;
      final int priceLimit;
      int integerField1;
      int integerField2;

      NestedType324(Item item, int i, int j) {
         this.targetItem = item;
         this.need = i;
         this.priceLimit = j;
      }

      @Override
      public boolean checkCondition_1211() {
         switch (this.integerField1) {
            case 0:
               if (minecraft.currentScreen != null) {
                  minecraft.currentScreen.close();
               }

               int i = this.integerField2 + 1;
               this.integerField2 = i;
               if (i >= 3) {
                  minecraft.player
                     .networkHandler
                     .sendChatMessage(
                        "/ah search " + (this.targetItem == Items.EXPERIENCE_BOTTLE ? "Experience" : new ItemStack(this.targetItem).getName().getString())
                     );
                  this.integerField1 = 1;
                  this.integerField2 = 0;
                  return false;
               }

               return false;
            case 1:
               GenericContainerScreen genericContainerScreen = (GenericContainerScreen)minecraft.currentScreen;
               if (!(genericContainerScreen instanceof GenericContainerScreen genericContainerScreen1)) {
                  int l = this.integerField2 + 1;
                  this.integerField2 = l;
                  if (l > 20) {
                     UtilComponent265.performAction_3505("The auction has not opened, I repeat.");
                     this.integerField2 = 0;
                     this.integerField1 = 0;
                     return false;
                  }

                  return false;
               } else {
                  if (!genericContainerScreen1.getTitle().getString().startsWith("☃") && !genericContainerScreen1.getTitle().getString().startsWith("0A2z")) {
                     return false;
                  }

                  this.integerField1 = 2;
                  this.integerField2 = 0;
                  return false;
               }
            case 2:
               int j = this.integerField2 + 1;
               this.integerField2 = j;
               if (j >= 15) {
                  this.integerField1 = 3;
                  this.integerField2 = 0;
                  return false;
               }

               return false;
            case 3:
               GenericContainerScreen genericContainerScreen2 = (GenericContainerScreen)minecraft.currentScreen;
               if (!(genericContainerScreen2 instanceof GenericContainerScreen genericContainerScreen3)) {
                  this.integerField2 = 0;
                  this.integerField1 = 0;
                  return false;
               } else if (UtilComponent270.computeValue_3538(this.targetItem) >= this.need) {
                  int m = this.integerField2 + 1;
                  this.integerField2 = m;
                  if (m >= 6) {
                     minecraft.currentScreen.close();
                     return true;
                  }

                  return false;
               } else {
                  this.integerField2 = 0;
                  if (minecraft.player.age % 7 != 0) {
                     return false;
                  } else {
                     Slot slot = null;
                     int k = 0;

                     for (; k < ((GenericContainerScreenHandler)genericContainerScreen3.getScreenHandler()).slots.size() - 36; k++) {
                        Slot slot1 = (Slot)((GenericContainerScreenHandler)genericContainerScreen3.getScreenHandler()).slots.get(k);
                        if (!slot1.getStack().isEmpty()
                           && slot1.getStack().getItem() == this.targetItem
                           && UtilComponent252.NestedType400.computeValue_3405(slot1.getStack()) > 0
                           && UtilComponent252.NestedType400.computeValue_3405(slot1.getStack()) <= this.priceLimit
                           && (
                              slot == null
                                 || UtilComponent252.NestedType400.computeValue_3405(slot1.getStack())
                                    < UtilComponent252.NestedType400.computeValue_3405(slot.getStack())
                           )) {
                           slot = slot1;
                        }
                     }

                     if (slot == null) {
                        minecraft.interactionManager
                           .clickSlot(
                              ((GenericContainerScreenHandler)genericContainerScreen3.getScreenHandler()).syncId,
                              50,
                              0,
                              SlotActionType.QUICK_MOVE,
                              minecraft.player
                           );
                        return false;
                     } else {
                        minecraft.interactionManager
                           .clickSlot(
                              ((GenericContainerScreenHandler)genericContainerScreen3.getScreenHandler()).syncId,
                              slot.id,
                              0,
                              SlotActionType.QUICK_MOVE,
                              minecraft.player
                           );
                        return false;
                     }
                  }
               }
            default:
               return false;
         }
      }
   }

   static final class NestedType326 implements MinecraftAccess, HandlerComponent114.Contract325 {
      private final Item tool;
      private final double endPct;
      private final int toolBarSlot;
      private final ItemStack restore;
      private int integerField1;
      private int integerField2;

      NestedType326(Item item, double d, int i, ItemStack itemStack) {
         this.tool = item;
         this.endPct = d;
         this.toolBarSlot = i;
         this.restore = itemStack;
      }

      @Override
      public boolean checkCondition_1211() {
         HandlerComponent110 handlerComponent110 = HandlerComponent108.getINVENTORY_1158();
         ItemStack itemStack = minecraft.player.getOffHandStack();
         ItemStack itemStack1 = minecraft.player.getMainHandStack();
         switch (this.integerField1) {
            case 0:
               if (minecraft.currentScreen != null) {
                  minecraft.currentScreen.close();
               }

               int i = this.integerField2 + 1;
               this.integerField2 = i;
               if (i >= 3 && handlerComponent110.getListField1_1177().isEmpty()) {
                  handlerComponent110.performAction_1179(this.toolBarSlot, 40, 1);
                  this.integerField1 = 1;
                  return false;
               }

               return false;
            case 1:
               if (handlerComponent110.getListField1_1177().isEmpty() && itemStack.getItem() == this.tool) {
                  if (HandlerComponent114.computeValue_1209(itemStack) >= this.endPct) {
                     this.integerField1 = 3;
                     return false;
                  }

                  int j = UtilComponent270.computeValue_3539(Items.EXPERIENCE_BOTTLE, true);
                  if (j != -1) {
                     minecraft.player.getInventory().setSelectedSlot(j);
                     this.integerField1 = 2;
                     return false;
                  }

                  int k = UtilComponent270.computeValue_3526(Items.EXPERIENCE_BOTTLE);
                  if (k != -1) {
                     handlerComponent110.performAction_1179(k, this.toolBarSlot, 1);
                     this.integerField1 = 2;
                     return false;
                  }

                  NeneClient.getInstance()
                     .getProcessors()
                     .getHandlerComponent106Field14_0673()
                     .getHandlerComponent114Field6_1148()
                     .getDequeField1_1208()
                     .addFirst(new HandlerComponent114.NestedType324(Items.EXPERIENCE_BOTTLE, 128, 2000));
                  return false;
               }

               return false;
            case 2:
               if (handlerComponent110.getListField1_1177().isEmpty() && itemStack.getItem() == this.tool) {
                  if (HandlerComponent114.computeValue_1209(itemStack) >= this.endPct) {
                     this.integerField2 = 0;
                     this.integerField1 = 3;
                     return false;
                  }

                  if (itemStack1.isEmpty()) {
                     this.integerField1 = 1;
                     return false;
                  }

                  if (itemStack1.getItem() != Items.EXPERIENCE_BOTTLE) {
                     int n = UtilComponent270.computeValue_3539(Items.EXPERIENCE_BOTTLE, true);
                     minecraft.player.getInventory().setSelectedSlot(n != -1 ? n : this.toolBarSlot);
                     return false;
                  }

                  HandlerComponent108.getROTATION_1157().performAction_1141(new UtilComponent250(minecraft.player.getYaw(), 90.0F), 360.0F, 1, 1);
                  minecraft.interactionManager.interactItem(minecraft.player, Hand.MAIN_HAND);
                  return false;
               }

               return false;
            case 3:
               int l = this.integerField2 + 1;
               this.integerField2 = l;
               if (l >= 3) {
                  handlerComponent110.performAction_1179(this.toolBarSlot, 40, 1);
                  this.integerField2 = 0;
                  this.integerField1 = 4;
                  return false;
               }

               return false;
            case 4:
               int m = this.integerField2 + 1;
               this.integerField2 = m;
               if (m >= 3) {
                  if (!this.restore.isEmpty() && UtilComponent270.computeValue_3545(this.restore, false) != -1) {
                     handlerComponent110.performAction_1179(UtilComponent270.computeValue_3545(this.restore, false), 40, 1);
                     return true;
                  }

                  return true;
               }

               return false;
            default:
               return false;
         }
      }
   }
}
