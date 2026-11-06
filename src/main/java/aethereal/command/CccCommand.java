package aethereal.command;

import aethereal.core.EventTarget;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent074;
import aethereal.friend.Friend;
import aethereal.util.UtilComponent252;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.command.CommandSource;

@CommandRegister(name = "ccc")
public class CccCommand extends Command {
   private int integerField1;

   @Override
   public void performAction_0094(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
      literalArgumentBuilder.executes(
         commandContext -> {
            if (!UtilComponent252.NestedType400.checkCondition_3406() && !UtilComponent252.NestedType403.checkCondition_3420()) {
               return 1;
            }

            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < 3 + (int)(Math.random() * 3.0); i++) {
               stringBuilder.append(
                  "абвгдежзийклмнопрстуфхцчшщъыьэюяАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
                     .charAt(
                        (int)(
                           Math.random()
                              * "абвгдежзийклмнопрстуфхцчшщъыьэюяАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
                                 .length()
                        )
                     )
               );
            }

            minecraft.player.networkHandler.sendChatMessage("/clan create " + stringBuilder);
            this.integerField1 = minecraft.player.age + 2;
            return 1;
         }
      );
   }

   @EventTarget
   public void onEventComponent074_0196(EventComponent074 eventComponent074) {
      if (this.integerField1 >= minecraft.player.age) {
         for (Friend friend : NeneClient.getInstance().getProcessors().getFriends().getEntries()) {
            PlayerListEntry playerListEntry = minecraft.player
               .networkHandler
               .getPlayerList()
               .stream()
               .filter(playerListEntry1 -> playerListEntry1.getProfile().name().equalsIgnoreCase(friend.getName_1103()))
               .findFirst()
               .orElse(null);
            if (playerListEntry != null && !friend.getName_1103().equals(minecraft.getSession().getUsername())) {
               minecraft.player.networkHandler.sendChatMessage("/clan invite " + friend.getName_1103());
            }
         }

         this.integerField1 = -3;
      }
   }
}
