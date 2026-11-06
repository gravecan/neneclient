package aethereal.module.player;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent074;
import aethereal.event.PacketEvent;
import aethereal.util.UtilComponent252;
import aethereal.util.UtilComponent265;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.screen.sync.ItemStackHash;

@ModuleRegister(name = "Auc Reissue", description = "Automatically relists items at auction", category = Category.Player)
public class AucReissue extends Module implements MinecraftAccess {
   private boolean flagField1;

   @Override
   public void onEnable() {
      super.onEnable();
      this.flagField1 = false;
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.flagField1 = false;
   }

   @EventTarget
   public void onEventComponent074_2124(EventComponent074 eventComponent074) {
      if (!UtilComponent252.checkCondition_3401()
         && (UtilComponent252.NestedType400.computeValue_3414() != -1 || UtilComponent252.NestedType403.computeValue_3418() != -1)
         && minecraft.player.age >= 220
         && !NeneClient.getInstance().getProcessors().getHandlerComponent106Field14_0673().getMiscComponent167Field4_1155().checkCondition_1808()
         && !minecraft.player.getItemCooldownManager().isCoolingDown(Items.CLOCK.getDefaultStack())) {
         HandledScreen handledScreen = (HandledScreen)minecraft.currentScreen;
         if (handledScreen instanceof HandledScreen handledScreen1) {
            if (handledScreen1 instanceof GenericContainerScreen) {
               String string = handledScreen1.getTitle().getString();
               if (minecraft.player.age % 5 == 0) {
                  if (string.matches(".*Auction.*")) {
                     minecraft.player
                        .networkHandler
                        .sendPacket(
                           new ClickSlotC2SPacket(
                              handledScreen1.getScreenHandler().syncId,
                              handledScreen1.getScreenHandler().getRevision(),
                              (short)46,
                              (byte)1,
                              SlotActionType.PICKUP,
                              Int2ObjectMaps.emptyMap(),
                              ItemStackHash.EMPTY
                           )
                        );
                  } else if (string.matches(".*H.*r.*a.*n.*i.*l.*i.*sch.*e.*")) {
                     minecraft.player
                        .networkHandler
                        .sendPacket(
                           new ClickSlotC2SPacket(
                              handledScreen1.getScreenHandler().syncId,
                              handledScreen1.getScreenHandler().getRevision(),
                              (short)52,
                              (byte)1,
                              SlotActionType.PICKUP,
                              Int2ObjectMaps.emptyMap(),
                              ItemStackHash.EMPTY
                           )
                        );
                  }
               }
            } else if (minecraft.player.age % 20 == 0) {
               minecraft.player.networkHandler.sendChatCommand("ah");
            }
         } else if (minecraft.player.age % 20 == 0) {
            minecraft.player.networkHandler.sendChatCommand("ah");
         }
      }

      if (this.flagField1 && minecraft.currentScreen instanceof GenericContainerScreen) {
         minecraft.player.closeHandledScreen();
         this.flagField1 = false;
      }
   }

   @EventTarget
   public void onPacketEvent_2125(PacketEvent packetEvent) {
      if (!UtilComponent252.checkCondition_3401()
         && (UtilComponent252.NestedType400.computeValue_3414() != -1 || UtilComponent252.NestedType403.computeValue_3418() != -1)
         && minecraft.player.age >= 220
         && packetEvent.checkCondition_1013()) {
         GameMessageS2CPacket gameMessageS2CPacket = (GameMessageS2CPacket)packetEvent.getPacket_1015();
         if (gameMessageS2CPacket instanceof GameMessageS2CPacket gameMessageS2CPacket1) {
            String string = gameMessageS2CPacket1.content().getString();
            if (string.equals("This command is not available in AFK")) {
               NeneClient.getInstance().getProcessors().getHandlerComponent106Field14_0673().getMiscComponent167Field4_1155().setIntegerField1_1806(10);
            }

            if (string.equals("[☃] There are no items in the storage for relisting.")) {
               UtilComponent265.performAction_3505("Auto-shutdown: there are no items in storage to re-arrange");
               this.toggle();
            }

            if (string.contains("[☃] Items successfully relisted") || string.contains("[✔] Items have been successfully relisted!")) {
               minecraft.player.getItemCooldownManager().set(Items.CLOCK.getDefaultStack(), 1200);
               this.flagField1 = true;
            }

            if (string.contains("[☃] You can rearrange items once per minute! ")) {
               int i = Integer.parseInt(string.replaceAll(".*Wait (\\d+) seconds\\..*", "$1"));
               minecraft.player.getItemCooldownManager().set(Items.CLOCK.getDefaultStack(), i * 20 + 20);
               this.flagField1 = true;
            }
         }
      }
   }
}
