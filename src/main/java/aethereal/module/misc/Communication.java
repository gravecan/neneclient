package aethereal.module.misc;

import aethereal.config.ThemeInfo;
import aethereal.config.ThemeManager;
import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.BackendEvent;
import aethereal.event.DrawEvent;
import aethereal.event.EventComponent074;
import aethereal.event.PacketEvent;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent184;
import aethereal.render.RenderComponent188;
import aethereal.setting.BooleanSetting;
import aethereal.setting.KeybindSetting;
import aethereal.util.UtilComponent265;
import aethereal.util.UtilComponent268;
import aethereal.util.UtilComponent284;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.StyleSpriteSource.Font;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2f;

@ModuleRegister(
   name = "Communication",
   description = "Connects you with other players through group and global messages (party, IRC, etc.)",
   category = Category.Misc
)
public class Communication extends Module implements MinecraftAccess {
   private final KeybindSetting keybindSettingField1 = new KeybindSetting("Sending a tag to friends", -1).computeValue_2847(() -> {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("x", minecraft.player.getEntityPos().x);
      jsonObject.addProperty("y", minecraft.player.getEntityPos().y);
      jsonObject.addProperty("z", minecraft.player.getEntityPos().z);
      NeneClient.getInstance().getBackend().performAction_0599(false, "friend", "type", "mark", "pos", jsonObject);
   });
   private final BooleanSetting booleanSettingField2 = new BooleanSetting("Client chat", false);
   private final List<Communication.NestedType341> listField3 = new ArrayList<>();

   public Communication() {
      this.addSettings(this.keybindSettingField1, this.booleanSettingField2);
   }

   @EventTarget
   public void onEventComponent074_1795(EventComponent074 eventComponent074) {
      this.listField3.removeIf(nestedType341 -> nestedType341.getCounterUtil_1803().checkCondition_3522(5000L));
   }

   @EventTarget
   public void onPacketEvent_1796(PacketEvent packetEvent) {
      if (this.booleanSettingField2.getCurrentValue() && packetEvent.checkCondition_1012()) {
         ChatMessageC2SPacket chatMessageC2SPacket = (ChatMessageC2SPacket)packetEvent.getPacket_1015();
         if (chatMessageC2SPacket instanceof ChatMessageC2SPacket chatMessageC2SPacket1) {
            String string = chatMessageC2SPacket1.chatMessage();
            if (string.startsWith("@")) {
               NeneClient.getInstance().getBackend().performAction_0599(false, "irc", "message", string.substring(1));
               packetEvent.performAction_0597(true);
            }
         }
      }
   }

   @EventTarget
   public void onDrawEvent_1794(DrawEvent drawEvent) {
      if (drawEvent.checkCondition_1001()) {
         for (Communication.NestedType341 nestedType341 : this.listField3) {
            this.performAction_1797(drawEvent, nestedType341, minecraft.player.getEyePos());
         }
      }
   }

   private void performAction_1797(DrawEvent drawEvent, Communication.NestedType341 nestedType341, Vec3d vec3d) {
      String[] strings = nestedType341.getPosition_1805().split(",\\s*");
      if (strings.length >= 3) {
         Vec3d vec3d1 = new Vec3d(Double.parseDouble(strings[0]), Double.parseDouble(strings[1]), Double.parseDouble(strings[2]));
         Vector2f vector2f = UtilComponent284.computeValue_3615(vec3d1.x, vec3d1.y, vec3d1.z);
         if (UtilComponent284.checkCondition_3618(vector2f)) {
            ThemeManager themeManager = NeneClient.getInstance().getProcessors().getThemes();
            int i = themeManager.computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547();
            int j = RenderComponent183.computeValue_2549(
               themeManager.computeValue_0557(ThemeInfo.BACKGROUND_HUD).computeValue_0547(),
               themeManager.computeValue_0557(ThemeInfo.BACKGROUND_HUD).computeValue_0548()
            );
            MutableText mutableText = Text.literal(nestedType341.getLogin_1804().toUpperCase(Locale.ROOT))
               .append(Text.literal(" /  ").setStyle(Style.EMPTY.withColor(i)))
               .append(Text.literal(String.format(Locale.US, "%.1fm", vec3d.distanceTo(vec3d1))));
            float f = 16.5F + RenderComponent188.renderComponent187Field5.computeValue_2628(mutableText, 6.25F);
            float g = 12.0F;
            float h = vector2f.x() - f / 2.0F;
            float k = vector2f.y() - g / 2.0F;
            RenderComponent184 renderComponent184 = drawEvent.getRenderComponent184Field1_0998();
            renderComponent184.performAction_2580(drawEvent.getStack_1005(), h, k, f - 0.5F, g, 3.5F, j, 1.0F, j, 6.0F);
            renderComponent184.performAction_2585(
               drawEvent.getStack_1005(), Identifier.of("neneclient", "pictures/marker.png"), h + 3.0F - 0.5F, k + 3.0F - 1.0F, 8.0F, 8.0F, 1.0F, -1
            );
            RenderComponent188.renderComponent187Field5
               .performAction_2634(
                  drawEvent.getStack_1005(),
                  mutableText,
                  h + 3.0F + 8.0F + 2.0F,
                  k + (g - RenderComponent188.renderComponent187Field5.computeValue_2622(6.25F)) / 2.0F - 0.5F,
                  6.25F
               );
         }
      }
   }

   @EventTarget
   public void onBackendEvent_1793(BackendEvent backendEvent) {
      String string = backendEvent.getPacket_0974().getPayload_0657();
      String string1 = backendEvent.getPacket_0974().getSecurity_0658().computeValue_2474(string, "type");
      String string2 = backendEvent.getPacket_0974().getSecurity_0658().computeValue_2474(string, "user");
      String string3 = backendEvent.getPacket_0974().getSecurity_0658().computeValue_2474(string, "message");
      String string4 = backendEvent.getPacket_0974().getSecurity_0658().computeValue_2474(string, "priority");
      if ("irc".equals(backendEvent.getPacket_0974().getStringField1_0656()) && this.booleanSettingField2.getCurrentValue()) {
         Communication.Prefix prefix = Communication.Prefix.computeValue_1802(string4);
         MutableText mutableText = Text.empty();
         if (prefix != null) {
            mutableText.append(Text.literal(prefix.getGlyph_1801()).setStyle(Style.EMPTY.withFont(new Font(Identifier.of("neneclient", "prefixes")))))
               .append(Text.literal(""));
         }

         mutableText.append(UtilComponent265.computeValue_3504("[" + string2 + "] → " + string3));
         UtilComponent265.computeValue_3506("[IRC]", mutableText);
      }

      if ("friend".equals(backendEvent.getPacket_0974().getStringField1_0656()) && "mark".equals(string1)) {
         JsonObject jsonObject = backendEvent.getPacket_0974().getSecurity_0658().computeValue_2479(string, "pos").getAsJsonObject();
         String string6 = String.format(
            "%.0f, %.0f, %.0f", jsonObject.get("x").getAsDouble(), jsonObject.get("y").getAsDouble(), jsonObject.get("z").getAsDouble()
         );
         String string5 = backendEvent.getPacket_0974().getSecurity_0658().computeValue_2474(string, "minecraft");
         this.listField3.removeIf(nestedType341 -> nestedType341.getLogin_1804().equalsIgnoreCase(string5));
         this.listField3.add(new Communication.NestedType341(new UtilComponent268(), string5, string6));
      }
   }

   public static final class NestedType341 {
      private final UtilComponent268 counterUtil;
      private final String login;
      private final String position;

      public NestedType341(UtilComponent268 utilComponent268, String string, String string1) {
         this.counterUtil = utilComponent268;
         this.login = string;
         this.position = string1;
      }

      public UtilComponent268 getCounterUtil_1803() {
         return this.counterUtil;
      }

      public String getLogin_1804() {
         return this.login;
      }

      public String getPosition_1805() {
         return this.position;
      }
   }

   public enum Prefix {
      ADMIN("Administrator", "\ue100"),
      STAFF("Employee", "\ue101"),
      YOUTUBER("Youtuber", "\ue102"),
      SHADE("shade", "\ue103"),
      DANGEROUS("dangerous", "\ue104"),
      DEVSTVENIK("Virgin", "\ue105"),
      DRUN("Drun", "\ue106"),
      QCOLD("qcold", "\ue107"),
      WIN("win", "\ue108"),
      BURMALDA("burmalda", "\ue109"),
      VOZDUXAN("Aired", "\ue110");

      private final String role;
      private final String glyph;

      Prefix(String string1, String string2) {
         this.role = string1;
         this.glyph = string2;
      }

      public String getGlyph_1801() {
         return this.glyph;
      }

      public static Communication.Prefix computeValue_1802(String string) {
         return Arrays.stream(values()).filter(prefix -> prefix.role.equalsIgnoreCase(string)).findFirst().orElse(null);
      }
   }
}
