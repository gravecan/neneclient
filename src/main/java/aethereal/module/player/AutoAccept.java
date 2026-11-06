package aethereal.module.player;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.PacketEvent;
import aethereal.setting.BooleanSetting;
import aethereal.setting.MultiSelectSetting;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;

@ModuleRegister(name = "Auto Accept", description = "Automatically accepts selected requests", category = Category.Player)
public class AutoAccept extends Module {
   public final MultiSelectSetting multiSelectSettingField1 = new MultiSelectSetting(
      "Accept requests", new BooleanSetting("To the clan team", true), new BooleanSetting("Teleportation", true)
   );
   private final BooleanSetting booleanSettingField2 = new BooleanSetting("Accept only friends", true);
   private final List<String> listField3 = List.of(
      "asks to teleport",
      "wants to teleport",
      "The application will be automatically canceled after",
      "asks to teleport to you.",
      "120 seconds",
      "has requested to teleport",
      "teleport to you",
      "This request will timeout after",
      "120 seconds"
   );
   private final List<String> listField4 = List.of("invites you to the clan", "invites you to the clan");

   public AutoAccept() {
      this.addSettings(this.multiSelectSettingField1, this.booleanSettingField2);
   }

   @EventTarget
   public void onPacketEvent_2128(PacketEvent packetEvent) {
      if (packetEvent.checkCondition_1013()) {
         GameMessageS2CPacket gameMessageS2CPacket = (GameMessageS2CPacket)packetEvent.getPacket_1015();
         if (gameMessageS2CPacket instanceof GameMessageS2CPacket gameMessageS2CPacket1) {
            String string = gameMessageS2CPacket1.content().getString().toLowerCase();
            if (this.booleanSettingField2.getCurrentValue()
               && NeneClient.getInstance()
                  .getProcessors()
                  .getFriends()
                  .computeValue_1108()
                  .stream()
                  .noneMatch(friend -> string.contains(friend.getName_1103().toLowerCase()))) {
               return;
            }

            if (this.multiSelectSettingField1.computeValue_2852("Teleportation").getCurrentValue()) {
               Stream stream = this.listField3.stream();
               if (stream.anyMatch(string2 -> string.contains(string2))) {
                  minecraft.player.networkHandler.sendChatCommand("tpaccept");
               }
            }

            if (this.multiSelectSettingField1.computeValue_2852("To the clan team").getCurrentValue()) {
               Stream stream1 = this.listField4.stream();
               if (stream1.anyMatch(string2 -> string.contains(string2))) {
                  minecraft.player.networkHandler.sendChatCommand("clan accept " + string.split("")[1]);
               }
            }
         }
      }
   }
}
