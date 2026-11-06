package aethereal.handler;

import aethereal.core.ClientBackend;
import aethereal.core.ClientTickEvent;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent081;
import aethereal.event.EventComponent082;
import aethereal.event.PacketEvent;
import aethereal.mixin.MixinContract158;
import aethereal.util.UtilComponent252;
import aethereal.util.UtilComponent265;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.StreamSupport;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ChatCommandSignedC2SPacket;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.network.packet.c2s.play.CommandExecutionC2SPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.text.HoverEvent.Action;
import net.minecraft.text.HoverEvent.ShowText;

@HandlerAnnotation107
public class HandlerComponent111 extends HandlerComponent104 implements MinecraftAccess {
   private ServerInfo serverInfoField1;
   private String stringField2;

   public void setServerInfoField1_1198(ServerInfo serverInfo) {
      this.serverInfoField1 = serverInfo;
   }

   public ServerInfo getServerInfoField1_1196() {
      return this.serverInfoField1;
   }

   public void setStringField2_1200(String string) {
      this.stringField2 = string;
   }

   public String getStringField2_1195() {
      return this.stringField2;
   }

   @EventTarget
   public void onPacketEvent_1192(PacketEvent packetEvent) throws MatchException {
      if (packetEvent.checkCondition_1013() && packetEvent.getPacket_1015() instanceof GameMessageS2CPacket gameMessageS2CPacket) {
         for (Text text1 : this.computeValue_1197(gameMessageS2CPacket.content())) {
            HoverEvent hoverEvent;
            Text text;
            if (text1.getString().contains("More details")
               && (hoverEvent = text1.getStyle().getHoverEvent()) != null
               && hoverEvent.getAction() == Action.SHOW_TEXT
               && hoverEvent instanceof ShowText showText
               && (text = showText.value()) != null) {
               List list = Arrays.stream(text.getString().split("\n")).map(string6 -> string6.trim()).toList();
               String string = list.stream().filter(string6 -> string6.startsWith("[") && string6.contains("]")).findFirst().orElse(null);
               String string1 = list.stream()
                  .filter(string6 -> string6.startsWith("Cause:"))
                  .map(string6 -> string6.substring("Cause:".length()).trim())
                  .findFirst()
                  .orElse(null);
               String string2 = list.stream()
                  .filter(string6 -> string6.startsWith("End:"))
                  .map(string6 -> string6.substring("End:".length()).trim())
                  .findFirst()
                  .orElse(null);
               if (string != null && string1 != null && string2 != null) {
                  minecraft.player
                     .sendMessage(
                        Text.literal(
                              "§c[♨] §6"
                                 + string.substring(string.indexOf(93) + 1).trim()
                                 + "§e banned with reason: §c\""
                                 + string1
                                 + "\"§e to §c\""
                                 + string2
                                 + "\" "
                           )
                           .append(UtilComponent265.computeValue_3506("&c[More]", text)),
                        false
                     );
                  packetEvent.performAction_0597(true);
                  break;
               }
            }
         }
      }

      if (packetEvent.checkCondition_1012()) {
         String string3 = packetEvent.getPacket_1015() instanceof ChatMessageC2SPacket chatMessageC2SPacket
            ? chatMessageC2SPacket.chatMessage()
            : (
               packetEvent.getPacket_1015() instanceof CommandExecutionC2SPacket commandExecutionC2SPacket
                  ? commandExecutionC2SPacket.command()
                  : (
                     packetEvent.getPacket_1015() instanceof ChatCommandSignedC2SPacket chatCommandSignedC2SPacket
                        ? chatCommandSignedC2SPacket.command()
                        : null
                  )
            );
         if (string3 != null && minecraft.player != null) {
            int i = string3.trim().toLowerCase(Locale.ROOT).startsWith("/ah sell")
               ? 8
               : (string3.trim().toLowerCase(Locale.ROOT).startsWith("ah sell") ? 7 : -1);
            if (i >= 0 && string3.trim().endsWith("!")) {
               String string5 = string3.trim().substring(i, string3.trim().length() - 1).trim();
               int k = 0;

               while (k < string5.length() && (Character.isDigit(string5.charAt(k)) || string5.charAt(k) == '.')) {
                  k++;
               }

               if (k > 0) {
                  ClientPlayNetworkHandler clientPlayNetworkHandler = minecraft.player.networkHandler;
                  long m = Math.round(Double.parseDouble(string5.substring(0, k)) * Math.max(1, minecraft.player.getMainHandStack().getCount()));
                  clientPlayNetworkHandler.sendChatMessage("/ah sell " + m);
                  packetEvent.performAction_0597(true);
                  return;
               }
            }
         }

         if (string3 != null && minecraft.player != null) {
            int j = string3.trim().toLowerCase(Locale.ROOT).startsWith("/pay ") ? 5 : (string3.trim().toLowerCase(Locale.ROOT).startsWith("pay ") ? 4 : -1);
            if (j >= 0) {
               String[] strings = string3.trim().substring(j).trim().split("\\s+");
               long l = UtilComponent252.NestedType400.computeValue_3413();
               if (strings.length == 2 && strings[1].equalsIgnoreCase("all") && l > 0L) {
                  minecraft.player.networkHandler.sendChatMessage("/pay " + strings[0] + " " + l);
                  packetEvent.performAction_0597(true);
                  return;
               }
            }
         }

         if (string3 != null) {
            String string4 = string3.trim();
            boolean bl = string4.toLowerCase().startsWith("hub") || string4.toLowerCase().startsWith("an");
            if (!UtilComponent252.checkCondition_3401() || !bl) {
               this.stringField2 = null;
               return;
            }

            if (string4.equals(this.stringField2)) {
               this.stringField2 = null;
               return;
            }

            this.stringField2 = string4;
            UtilComponent265.performAction_3505("&cYou are in PvP mode! ");
            NeneClient.getInstance().getProcessors().getCommandManagerField13_0666().getRctCommandField6_0218().performAction_0312();
            packetEvent.performAction_0597(true);
         }
      }
   }

   @EventTarget
   public void onEventComponent082_1194(EventComponent082 eventComponent082) {
      if (UtilComponent252.NestedType400.checkCondition_3407()
         && (
            eventComponent082.getItem_1032() == Items.WIND_CHARGE && eventComponent082.getCooldown_1033() <= 10
               || eventComponent082.getItem_1032() == Items.CHORUS_FRUIT && eventComponent082.getCooldown_1033() <= 20
         )) {
         eventComponent082.performAction_0597(true);
      }
   }

   private List<Text> computeValue_1197(Text text) {
      ArrayList arrayList = new ArrayList();
      arrayList.add(text);

      for (Text text1 : text.getSiblings()) {
         arrayList.addAll(this.computeValue_1197(text1));
      }

      return arrayList;
   }

   @EventTarget
   public void onEventComponent081_1193(EventComponent081 eventComponent081) {
      PotionContentsComponent potionContentsComponent;
      if (UtilComponent252.checkCondition_3401()
         && (UtilComponent252.NestedType400.checkCondition_3406() || UtilComponent252.NestedType403.checkCondition_3420())
         && eventComponent081.getStack_1031().getItem() == Items.POTION
         && (potionContentsComponent = (PotionContentsComponent)eventComponent081.getStack_1031().get(DataComponentTypes.POTION_CONTENTS)) != null) {
         boolean bl = StreamSupport.<StatusEffectInstance>stream(potionContentsComponent.getEffects().spliterator(), false)
            .anyMatch(statusEffectInstance -> statusEffectInstance.getEffectType() == StatusEffects.INSTANT_HEALTH);
         if (bl) {
            ((MixinContract158)minecraft.player.getItemCooldownManager()).setHealCooldown(300);
         }
      }
   }

   @EventTarget
   public void performAction_1191(ClientTickEvent clientTickEvent) {
      if (minecraft.player != null || minecraft.currentScreen instanceof MultiplayerScreen) {
         ClientBackend clientBackend = NeneClient.getInstance().getBackend();
         Object[] objects = new Object[]{
            "uuid", minecraft.player != null ? minecraft.player.getUuid() : null, "minecraft", minecraft.getSession().getUsername(), "server", null
         };
         String string;
         if (UtilComponent252.NestedType400.checkCondition_3406()) {
            string = "funtime";
         } else if (UtilComponent252.NestedType401.checkCondition_3416()) {
            string = "holyworld";
         } else {
            string = UtilComponent252.NestedType403.checkCondition_3420() ? "spookytime" : null;
         }

         objects[5] = string;
         clientBackend.performAction_0599(true, "minecraft", objects);
      }
   }
}
