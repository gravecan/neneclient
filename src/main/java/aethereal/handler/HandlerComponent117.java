package aethereal.handler;

import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent074;
import aethereal.module.player.WindHop;
import aethereal.util.UtilComponent250;
import aethereal.util.UtilComponent270;
import aethereal.util.UtilComponent277;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.BundleItemSelectedC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.util.Hand;
import platform.inject.invokers.ClientPlayerInteractionManagerInvoker;

@HandlerAnnotation107
public class HandlerComponent117 extends HandlerComponent104 implements MinecraftAccess {
   private final List<HandlerComponent117.NestedType327> listField1 = new ArrayList<>();

   public List<HandlerComponent117.NestedType327> getListField1_1222() {
      return this.listField1;
   }

   @EventTarget
   public void onEventComponent074_1221(EventComponent074 eventComponent074) {
      if (!this.listField1.isEmpty()) {
         HandlerComponent117.NestedType327 nestedType327 = this.listField1.getFirst();
         WindHop windHop = NeneClient.getInstance().getProcessors().getModules().getWindHopField10_0480();
         int i = nestedType327.getItemStack_1229().getItem() == Items.SPLASH_POTION
            ? UtilComponent270.computeValue_3527(nestedType327.getItemStack_1229(), true)
            : UtilComponent270.computeValue_3539(nestedType327.getItemStack_1229().getItem(), true);
         int j = nestedType327.getItemStack_1229().getItem() == Items.SPLASH_POTION
            ? UtilComponent270.computeValue_3527(nestedType327.getItemStack_1229(), false)
            : UtilComponent270.computeValue_3539(nestedType327.getItemStack_1229().getItem(), false);
         if (nestedType327.getIntegerField3_1232() == -1 && i == -1 && j == -1) {
            this.listField1.remove(nestedType327);
            return;
         }

         nestedType327.setIntegerField3_1228(nestedType327.getIntegerField3_1232() + 1);
         if (nestedType327.getIntegerField3_1232() == 0) {
            nestedType327.setIntegerField1_1226(minecraft.player.getInventory().getSelectedSlot());
            if (i != -1) {
               nestedType327.setIntegerField2_1230(i);
               if (i != minecraft.player.getInventory().getSelectedSlot()) {
                  this.performAction_1220(i);
                  return;
               }

               return;
            }

            if (j != -1) {
               int k = UtilComponent270.computeValue_3543(minecraft.player.getInventory().getStack(j), nestedType327.getItemStack_1229());
               if (k != -1) {
                  minecraft.player.networkHandler.sendPacket(new BundleItemSelectedC2SPacket(j < 9 ? 36 + j : j, k));
               }

               nestedType327.setIntegerField2_1230(k != -1 && minecraft.player.getMainHandStack().isEmpty() ? nestedType327.getIntegerField1_1227() : j);
               HandlerComponent108.getINVENTORY_1158().performAction_1179(j, minecraft.player.getInventory().getSelectedSlot(), 1);
               return;
            }

            return;
         }

         if (nestedType327.getIntegerField3_1232() == 1) {
            if (nestedType327.getItemStack_1229().getItem() == Items.WIND_CHARGE
               && windHop.isEnabled()
               && windHop.getBooleanSettingField1_2215().getCurrentValue()) {
               float f = minecraft.player.age + minecraft.getRenderTickCounter().getTickProgress(false);
               float g = (float)(Math.sin(f * 0.31F) * 6.600001001477404 + Math.sin(f * 0.73F + 1.1F) * 0.3000001491338646);
               HandlerComponent108.getROTATION_1157()
                  .performAction_1141(new UtilComponent250(UtilComponent277.getFloatValueField2_3570() + g, 90.0F + g / 2.0F), 180.0F, 1, 3);
            }

            this.performAction_1224(nestedType327);
            if (minecraft.player.getInventory().getStack(nestedType327.getIntegerField2_1231()).contains(DataComponentTypes.BUNDLE_CONTENTS)) {
               minecraft.player.getInventory().setStack(nestedType327.getIntegerField1_1227(), ItemStack.EMPTY);
               HandlerComponent108.getINVENTORY_1158().performAction_1179(nestedType327.getIntegerField2_1231(), 36 + nestedType327.getIntegerField1_1227(), 1);
            } else if (nestedType327.getIntegerField2_1231() > 8) {
               HandlerComponent108.getINVENTORY_1158().performAction_1179(nestedType327.getIntegerField1_1227(), nestedType327.getIntegerField2_1231(), 1);
            } else if (nestedType327.getIntegerField1_1227() != minecraft.player.getInventory().getSelectedSlot()) {
               this.performAction_1220(nestedType327.getIntegerField1_1227());
            }

            this.listField1.remove(nestedType327);
         }
      }
   }

   public void performAction_1220(int i) {
      minecraft.player.getInventory().setSelectedSlot(i);
   }

   public void performAction_1224(HandlerComponent117.NestedType327 nestedType327) {
      ((ClientPlayerInteractionManagerInvoker)minecraft.interactionManager)
         .invokeSendSequencedPacket(
            minecraft.world, i -> new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, i, minecraft.player.getYaw(), minecraft.player.getPitch())
         );
   }

   public void performAction_1225(ItemStack itemStack) {
      this.listField1.add(new HandlerComponent117.NestedType327(itemStack));
   }

   public static final class NestedType327 {
      private final ItemStack itemStack;
      private int integerField1;
      private int integerField2;
      private int integerField3 = -1;

      public void setIntegerField1_1226(int i) {
         this.integerField1 = i;
      }

      public void setIntegerField2_1230(int i) {
         this.integerField2 = i;
      }

      public void setIntegerField3_1228(int i) {
         this.integerField3 = i;
      }

      public ItemStack getItemStack_1229() {
         return this.itemStack;
      }

      public int getIntegerField1_1227() {
         return this.integerField1;
      }

      public int getIntegerField2_1231() {
         return this.integerField2;
      }

      public int getIntegerField3_1232() {
         return this.integerField3;
      }

      public NestedType327(ItemStack itemStack) {
         this.itemStack = itemStackx;
      }
   }
}
