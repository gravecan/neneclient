package aethereal.module.movement;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent074;
import aethereal.event.PacketEvent;
import aethereal.handler.HandlerComponent108;
import aethereal.handler.HandlerComponent115;
import aethereal.setting.ChoiceSetting;
import aethereal.util.UtilComponent280;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.BlockItem;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.s2c.play.CloseScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import platform.inject.accessors.ClientConnectionAccessor;

@ModuleRegister(name = "Screen Walk", description = "Allows movement with an open container, delaying inventory packages", category = Category.Movement)
public class ScreenWalk extends Module {
   private final ChoiceSetting choiceSettingField1 = new ChoiceSetting("Bypassing item movement", "Accelerated", "Accelerated", "Slow");
   private final List<ScreenWalk.NestedType351> listField2 = new ArrayList<>();
   private boolean flagField3 = false;

   public ScreenWalk() {
      this.addSettings(this.choiceSettingField1);
   }

   @EventTarget(priority = 0)
   public void onPacketEvent_2084(PacketEvent packetEvent) {
      HandlerComponent115 handlerComponent115 = HandlerComponent108.getSTOP_1159();
      if (packetEvent.checkCondition_1012()) {
         if (minecraft.currentScreen instanceof InventoryScreen) {
            ClickSlotC2SPacket clickSlotC2SPacket = (ClickSlotC2SPacket)packetEvent.getPacket_1015();
            if (clickSlotC2SPacket instanceof ClickSlotC2SPacket clickSlotC2SPacket1 && UtilComponent280.checkCondition_3587()) {
               boolean bl;
               if (clickSlotC2SPacket1.button() == 1) {
                  BlockItem blockItem = (BlockItem)minecraft.player.currentScreenHandler.getCursorStack().getItem();
                  if (blockItem instanceof BlockItem blockItem1) {
                     if (blockItem1.getBlock() instanceof ShulkerBoxBlock) {
                        bl = true;
                     } else {
                        bl = false;
                     }
                  } else {
                     bl = false;
                  }
               } else {
                  bl = false;
               }

               boolean bl1 = bl;
               if (bl1) {
                  handlerComponent115.setIntegerField1_1212(2);
               }

               this.listField2
                  .add(
                     new ScreenWalk.NestedType351(
                        packetEvent.getPacket_1015(),
                        this.choiceSettingField1.checkCondition_2850("Slow") ? (this.listField2.isEmpty() ? 1 : this.listField2.size() + 1) : 2,
                        bl1
                     )
                  );
               packetEvent.performAction_0597(true);
            }
         }

         if (packetEvent.getPacket_1015() instanceof CloseHandledScreenC2SPacket) {
            if (UtilComponent280.checkCondition_3587() && minecraft.currentScreen instanceof InventoryScreen) {
               packetEvent.performAction_0597(true);

               for (ScreenWalk.NestedType351 nestedType351 : this.listField2) {
                  handlerComponent115.setIntegerField1_1212(nestedType351.getDelay_2087());
               }
            }

            this.flagField3 = false;
         }
      }

      if (packetEvent.checkCondition_1013() && this.choiceSettingField1.checkCondition_2850("Slow")) {
         if (packetEvent.getPacket_1015() instanceof OpenScreenS2CPacket) {
            this.flagField3 = true;
         }

         if (packetEvent.getPacket_1015() instanceof CloseScreenS2CPacket) {
            this.flagField3 = false;
         }
      }
   }

   @EventTarget
   public void onEventComponent074_2083(EventComponent074 eventComponent074) {
      if (!this.flagField3
         && minecraft.currentScreen != null
         && !(minecraft.currentScreen instanceof ChatScreen)
         && !(minecraft.currentScreen instanceof SignEditScreen)
         && !(minecraft.currentScreen instanceof AnvilScreen)
         && !(minecraft.currentScreen instanceof CreativeInventoryScreen)) {
         for (KeyBinding keyBinding : new KeyBinding[]{
            minecraft.options.forwardKey, minecraft.options.backKey, minecraft.options.leftKey, minecraft.options.rightKey, minecraft.options.jumpKey
         }) {
            keyBinding.setPressed(InputUtil.isKeyPressed(minecraft.getWindow(), keyBinding.getDefaultKey().getCode()));
         }
      }

      if (!UtilComponent280.checkCondition_3587() && !this.listField2.isEmpty()) {
         ClientConnectionAccessor clientConnectionAccessor = (ClientConnectionAccessor)minecraft.player.networkHandler.getConnection();
         if (this.choiceSettingField1.checkCondition_2850("Slow")) {
            clientConnectionAccessor.sendWithoutEvent(this.listField2.removeFirst().getPacket_2086(), null, true);
         } else {
            this.listField2.forEach(nestedType351 -> clientConnectionAccessor.sendWithoutEvent(nestedType351.getPacket_2086(), null, true));
            this.listField2.clear();
         }

         if (this.listField2.isEmpty() && minecraft.currentScreen == null) {
            clientConnectionAccessor.sendWithoutEvent(new CloseHandledScreenC2SPacket(minecraft.player.currentScreenHandler.syncId), null, true);
         }
      }
   }

   static final class NestedType351 {
      private final Packet<?> packet;
      private final int delay;
      private final boolean shulker;

      NestedType351(Packet<?> packet, int i, boolean bl) {
         this.packet = packetx;
         this.delay = i;
         this.shulker = bl;
      }

      public Packet<?> getPacket_2086() {
         return this.packet;
      }

      public int getDelay_2087() {
         return this.delay;
      }

      public boolean isShulker_2088() {
         return this.shulker;
      }
   }
}
