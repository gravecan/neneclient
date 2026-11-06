package aethereal.handler;

import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.event.ClickEvent;
import aethereal.event.EventComponent074;
import aethereal.event.EventComponent090;
import aethereal.util.UtilComponent265;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.Hand;

@HandlerAnnotation107
public class HandlerComponent109 extends HandlerComponent104 implements MinecraftAccess {
   private final List<HandlerComponent109.NestedType322> listField1 = new ArrayList<>();

   public List<HandlerComponent109.NestedType322> getListField1_1167() {
      return this.listField1;
   }

   public void performAction_1162(int i) {
      if (this.listField1.isEmpty() && HandlerComponent108.getINVENTORY_1158().getListField1_1177().isEmpty()) {
         this.listField1.add(new HandlerComponent109.NestedType322(i));
      }
   }

   public boolean checkCondition_1163() {
      return !this.listField1.isEmpty();
   }

   @EventTarget
   public void onEventComponent074_1165(EventComponent074 eventComponent074) {
      if (!this.listField1.isEmpty() && minecraft.player.age > 40) {
         HandlerComponent110 handlerComponent110 = HandlerComponent108.getINVENTORY_1158();
         HandlerComponent109.NestedType322 nestedType322 = this.listField1.getFirst();
         boolean bl = nestedType322.getEatSlot_1169() > 8;
         nestedType322.setIntegerField3_1168(nestedType322.getIntegerField3_1173() + 1);
         if (nestedType322.getIntegerField3_1173() == 1) {
            if (bl) {
               handlerComponent110.performAction_1179(nestedType322.getEatSlot_1169(), nestedType322.getIntegerField1_1170(), 2);
            } else {
               minecraft.player.getInventory().setSelectedSlot(nestedType322.getEatSlot_1169());
            }
         } else if (!nestedType322.isFlagField2_1172() && nestedType322.getIntegerField3_1173() > 0 && handlerComponent110.getListField1_1177().isEmpty()) {
            if (minecraft.player.isUsingItem()) {
               nestedType322.setFlagField2_1171(true);
            } else {
               minecraft.interactionManager.interactItem(minecraft.player, Hand.MAIN_HAND);
            }
         } else if (nestedType322.isFlagField2_1172() && !minecraft.player.isUsingItem() && handlerComponent110.getListField1_1177().isEmpty()) {
            if (bl) {
               handlerComponent110.performAction_1179(nestedType322.getIntegerField1_1170(), nestedType322.getEatSlot_1169(), 2);
            } else {
               minecraft.player.getInventory().setSelectedSlot(nestedType322.getIntegerField1_1170());
            }

            this.listField1.remove(nestedType322);
         }

         if (nestedType322.getIntegerField3_1173() >= 60) {
            UtilComponent265.performAction_3505("Item use failed for an unknown reason");
            this.listField1.remove(nestedType322);
         }
      }
   }

   @EventTarget
   public void onEventComponent090_1166(EventComponent090 eventComponent090) {
      if (this.checkCondition_1163()) {
         eventComponent090.performAction_0597(true);
      }
   }

   @EventTarget
   public void onClickEvent_1164(ClickEvent clickEvent) {
      if (this.checkCondition_1163() && clickEvent.getButton_0980() == 1) {
         clickEvent.performAction_0597(true);
      }
   }

   public static final class NestedType322 {
      private final int integerField1 = MinecraftAccess.minecraft.player.getInventory().getSelectedSlot();
      private final int eatSlot;
      private boolean flagField2;
      private int integerField3;

      public void setFlagField2_1171(boolean bl) {
         this.flagField2 = bl;
      }

      public void setIntegerField3_1168(int i) {
         this.integerField3 = i;
      }

      public int getIntegerField1_1170() {
         return this.integerField1;
      }

      public int getEatSlot_1169() {
         return this.eatSlot;
      }

      public boolean isFlagField2_1172() {
         return this.flagField2;
      }

      public int getIntegerField3_1173() {
         return this.integerField3;
      }

      public NestedType322(int i) {
         this.eatSlot = i;
      }
   }
}
