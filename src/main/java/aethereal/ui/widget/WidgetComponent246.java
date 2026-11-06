package aethereal.ui.widget;

import aethereal.config.ThemeInfo;
import aethereal.core.ClientTickEvent;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.event.DrawEvent;
import aethereal.render.Animation;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent186;
import aethereal.render.RenderComponent188;
import aethereal.staff.StaffEntry;
import aethereal.ui.element.ElementComponent210;
import aethereal.util.UtilComponent279;
import aethereal.util.UtilComponent284;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.regex.Pattern;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;

public class WidgetComponent246 extends WidgetComponent249 implements MinecraftAccess {
   private static final long STAFF_SCAN_INTERVAL_MS = 1000L;
   private static final Pattern STAFF_MARKER = Pattern.compile(
      "(?i)(?<![\\p{L}\\p{N}])(owner|admin|administrator|moderator|mod|helper|staff|support|yetkili|rehber|kurucu|y[oö]netici|moderat[oö]r|destek|g[oö]revli)(?![\\p{L}\\p{N}])"
   );
   private long lastStaffScan;

   public WidgetComponent246() {
      super(new ElementComponent210("Staff", 0.0F, 0.0F, 0.0F, 0.0F));
      this.getDragInfo_3360().setWidgetComponent249Field1_3016(this);
   }

   @EventTarget
   @Override
   public void onDrawEvent_3315(DrawEvent drawEvent) {
      this.getAnimationField3_3363().performAction_2535(0.0F, 1.0F, 0.3F, RenderComponent186.contract372Field7, drawEvent.getTickNeneClient_0997());
      float f = this.getDragInfo_3360().computeValue_3007();
      float g = this.getDragInfo_3360().computeValue_3009();
      float h = 14.5F + RenderComponent188.renderComponent187Field5.computeValue_2626("Staff-list", this.floatValueField8) + 5.0F + 2.0F;
      float i = g + this.floatValueField7 + 3.0F;
      boolean bl = false;

      for (StaffEntry staffEntry : NeneClient.getInstance().getProcessors().getStaff().computeValue_2878()) {
         if (staffEntry.getAnimationField1_2871().getFloatValueField4_2530() > 0.0F) {
            h = Math.max(
               h,
               19.0F
                  + RenderComponent188.renderComponent187Field5.computeValue_2626(staffEntry.getName_2870(), 6.5F)
                  + 8.0F
                  + RenderComponent188.renderComponent187Field5
                     .computeValue_2626(this.checkCondition_3352(staffEntry.getName_2870()) ? "Near" : "Online", 6.5F)
                  + 5.0F
                  + 2.0F
            );
            bl = true;
         }
      }

      float o = UtilComponent279.computeValue_3586(this.getDragInfo_3360().getWidth_3005(), h, 0.5F);
      this.getDragInfo_3360().performAction_3006(o);
      if (this.computeValue_3367() > 0.0F) {
         this.performAction_3376(drawEvent, "i", "Staff-list", o, this.computeValue_3367());
      }

      for (StaffEntry staffEntry1 : NeneClient.getInstance().getProcessors().getStaff().computeValue_2878()) {
         Animation animation = staffEntry1.getAnimationField1_2871();
         animation.performAction_2535(0.0F, 1.0F, 0.3F, RenderComponent186.contract372Field7, drawEvent.getTickNeneClient_0997());
         float j = animation.getFloatValueField4_2530() * this.computeValue_3367();
         if (j > 0.0F) {
            float k = -8.0F * (1.0F - j);
            float l = -(1.0F - j);
            float m = i + l;
            float n = m + (11.5F - RenderComponent188.renderComponent187Field5.computeValue_2622(6.5F)) / 2.0F - 0.5F;
            this.performAction_3372(drawEvent, f + k, m, o, 11.5F, false, j);
            this.performAction_3371(drawEvent, f + k + 15.0F, m, 11.5F, j);
            PlayerListEntry playerListEntry = minecraft.getNetworkHandler() == null
               ? null
               : minecraft.getNetworkHandler()
                  .getPlayerList()
                  .stream()
                  .filter(playerListEntry1 -> playerListEntry1.getProfile().name().equalsIgnoreCase(staffEntry1.getName_2870()))
                  .findFirst()
                  .orElse(null);
            if (playerListEntry != null) {
               drawEvent.getRenderComponent184Field1_0998()
                  .performAction_2579(
                     drawEvent.getStack_1005(),
                     f + k + 5.0F,
                     m + 2.0F,
                     7.5F,
                     7.5F,
                     2.0F,
                     RenderComponent183.computeValue_2549(-1, j),
                     0.125F,
                     0.125F,
                     0.125F,
                     0.125F,
                     UtilComponent284.computeValue_3617(playerListEntry.getSkinTextures().body().comp_3626())
                  );
            } else {
               RenderComponent188.renderComponent187Field1
                  .performAction_2630(
                     drawEvent.getStack_1005(),
                     "y",
                     f + k + 5.0F,
                     m + (11.5F - RenderComponent188.renderComponent187Field1.computeValue_2622(8.0F)) / 2.0F,
                     8.0F,
                     RenderComponent183.computeValue_2549(
                        NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(), j
                     )
                  );
            }

            RenderComponent188.renderComponent187Field5
               .performAction_2630(drawEvent.getStack_1005(), staffEntry1.getName_2870(), f + k + 19.0F, n, 6.5F, RenderComponent183.computeValue_2549(-1, j));
            boolean bl1 = this.checkCondition_3352(staffEntry1.getName_2870());
            RenderComponent188.renderComponent187Field5
               .performAction_2630(
                  drawEvent.getStack_1005(),
                  bl1 ? "Near" : "Online",
                  f + k + o - 5.0F - RenderComponent188.renderComponent187Field5.computeValue_2626(bl1 ? "Near" : "Online", 6.5F) - 1.0F,
                  n,
                  6.5F,
                  RenderComponent183.computeValue_2549(bl1 ? -1529792 : -9711765, j)
               );
            i += 13.5F * j;
         }
      }

      this.getDragInfo_3360().performAction_3018(bl ? i - g - 2.0F : this.floatValueField7);
      super.onDrawEvent_3315(drawEvent);
   }

   @EventTarget
   @Override
   public void performAction_3316(ClientTickEvent clientTickEvent) {
      this.performAction_3349();
      boolean bl = minecraft.currentScreen instanceof ChatScreen;

      for (StaffEntry staffEntry : NeneClient.getInstance().getProcessors().getStaff().computeValue_2878()) {
         staffEntry.getAnimationField1_2871()
            .performAction_2528(
               minecraft.getNetworkHandler() != null
                     && minecraft.getNetworkHandler()
                        .getPlayerList()
                        .stream()
                        .anyMatch(playerListEntry -> playerListEntry.getProfile().name().equalsIgnoreCase(staffEntry.getName_2870()))
                  || this.checkCondition_3352(staffEntry.getName_2870())
            );
         if (staffEntry.getAnimationField1_2871().getFloatValueField4_2530() > 0.0F) {
            bl = true;
         }
      }

      this.getAnimationField3_3363().performAction_2528(bl);
      super.performAction_3316(clientTickEvent);
   }

   private void performAction_3349() {
      long l = System.currentTimeMillis();
      if (l - this.lastStaffScan >= 1000L) {
         this.lastStaffScan = l;
         LinkedHashSet linkedHashSet = new LinkedHashSet();
         if (minecraft.getNetworkHandler() != null) {
            for (PlayerListEntry playerListEntry : minecraft.getNetworkHandler().getPlayerList()) {
               StringBuilder stringBuilder = new StringBuilder();
               Text text = playerListEntry.getDisplayName();
               if (text != null) {
                  stringBuilder.append(text.getString()).append(' ');
               }

               if (playerListEntry.getScoreboardTeam() != null) {
                  stringBuilder.append(playerListEntry.getScoreboardTeam().getPrefix().getString()).append(' ');
                  stringBuilder.append(playerListEntry.getScoreboardTeam().getSuffix().getString());
               }

               if (STAFF_MARKER.matcher(stringBuilder.toString().toLowerCase(Locale.ROOT)).find()) {
                  linkedHashSet.add(playerListEntry.getProfile().name());
               }
            }
         }

         NeneClient.getInstance().getProcessors().getStaff().performAction_2874(linkedHashSet);
      }
   }

   private boolean checkCondition_3352(String string) {
      return minecraft.world != null
         && minecraft.world
            .getPlayers()
            .stream()
            .anyMatch(abstractClientPlayerEntity -> abstractClientPlayerEntity.getName().getString().equalsIgnoreCase(string));
   }
}
