package aethereal.ui.widget;

import aethereal.config.ThemeInfo;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.event.DrawEvent;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent186;
import aethereal.render.RenderComponent188;
import aethereal.setting.BooleanSetting;
import aethereal.ui.element.ElementComponent210;
import aethereal.util.UtilComponent252;
import aethereal.util.UtilComponent279;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import platform.inject.accessors.BossBarHudAccessor;

public class WidgetComponent248 extends WidgetComponent249 implements MinecraftAccess {
   private float floatValueField1;
   private final BooleanSetting booleanSettingField2 = new BooleanSetting("Side display", false);
   private final BooleanSetting booleanSettingField3 = new BooleanSetting("Separate elements", false);
   private final BooleanSetting booleanSettingField4 = new BooleanSetting("Frame Rate", true);
   private final BooleanSetting booleanSettingField5 = new BooleanSetting("Player delay", true);
   private final BooleanSetting booleanSettingField6 = new BooleanSetting("Current Time", true);
   private final BooleanSetting booleanSettingField7 = new BooleanSetting("Login in the client", true);
   private final BooleanSetting booleanSettingField8 = new BooleanSetting("Coordinates", true);
   private final BooleanSetting booleanSettingField9 = new BooleanSetting("Server Latency", true);
   private final BooleanSetting booleanSettingField10 = new BooleanSetting("Player speed", true);

   public WidgetComponent248() {
      super(new ElementComponent210("Info panel", 0.0F, 0.0F, 0.0F, 0.0F));
      this.getDragInfo_3360().setWidgetComponent249Field1_3016(this);
      this.getDragInfo_3360().setIntegerField6_3004(2);
      this.performAction_3362(
         this.booleanSettingField2,
         this.booleanSettingField3,
         this.booleanSettingField4,
         this.booleanSettingField5,
         this.booleanSettingField6,
         this.booleanSettingField7,
         this.booleanSettingField8,
         this.booleanSettingField9,
         this.booleanSettingField10
      );
   }

   @Override
   public void onDrawEvent_3315(DrawEvent drawEvent) {
      this.getAnimationField3_3363().performAction_2528(true);
      this.getAnimationField3_3363().performAction_2535(0.0F, 1.0F, 0.3F, RenderComponent186.contract372Field7, drawEvent.getTickNeneClient_0997());
      this.floatValueField1 = UtilComponent279.computeValue_3586(this.floatValueField1, minecraft.getCurrentFps(), 0.1F);
      float g = this.floatValueField8 - 0.5F;
      float h = this.floatValueField8 + 1.0F;
      float i = !this.booleanSettingField3.getCurrentValue() ? 5.0F : 2.0F;
      String[][] strings = this.computeValue_3355();
      String[][] strings1 = this.computeValue_3354();
      float j = this.computeValue_3358(strings, true, g, h, 5.0F, i, 3.0F, 4.0F);
      float k = this.computeValue_3358(strings1, false, g, h, 5.0F, i, 3.0F, 4.0F);
      float l = this.booleanSettingField2.getCurrentValue() ? 5.0F : (minecraft.getWindow().getScaledWidth() - j) / 2.0F;
      float f;
      if (!this.booleanSettingField2.getCurrentValue() && !((BossBarHudAccessor)minecraft.inGameHud.getBossBarHud()).getBossBars().isEmpty()) {
         int m = ((BossBarHudAccessor)minecraft.inGameHud.getBossBarHud()).getBossBars().size() - 1;
         f = (12 + m * 19 + 5)
            * minecraft.getWindow().calculateScaleFactor((Integer)minecraft.options.getGuiScale().getValue(), minecraft.forcesUnicodeFont())
            / minecraft.getWindow().calculateScaleFactor(2, minecraft.forcesUnicodeFont());
      } else {
         f = 0.0F;
      }

      float p = 5.0F + f;
      float n = this.booleanSettingField2.getCurrentValue() ? l : l + (j - k) / 2.0F;
      this.getDragInfo_3360().performAction_3015(l);
      this.getDragInfo_3360().performAction_3003(p);
      this.getDragInfo_3360().performAction_3006(j);
      this.getDragInfo_3360().performAction_3018(strings1.length > 0 ? this.floatValueField7 + 3.0F + this.floatValueField7 : this.floatValueField7);
      int o = RenderComponent183.computeValue_2549(
         NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(), 1.0F
      );
      this.performAction_3356(drawEvent, l, p, j, strings, true, o, g, h, 5.0F, i, 3.0F, 4.0F, -0.5F);
      if (strings1.length > 0) {
         this.performAction_3356(drawEvent, n, p + this.floatValueField7 + 3.0F, k, strings1, false, o, g, h, 5.0F, i, 3.0F, 4.0F, -0.5F);
      }

      super.onDrawEvent_3315(drawEvent);
   }

   private void performAction_3356(
      DrawEvent drawEvent, float f, float g, float h, String[][] strings, boolean bl, int i, float j, float k, float l, float m, float n, float o, float p
   ) {
      if (this.booleanSettingField3.getCurrentValue()) {
         this.performAction_3357(drawEvent, f, g, strings, bl, i, j, k, l, m, n, p);
      } else {
         this.performAction_3372(drawEvent, f, g, h, this.floatValueField7, true, 1.0F);
         float q = f + l;
         float r = g + (this.floatValueField7 - RenderComponent188.renderComponent187Field5.computeValue_2622(this.floatValueField8)) / 2.0F + p;
         if (bl) {
            RenderComponent188.renderComponent187Field1
               .performAction_2630(
                  drawEvent.getStack_1005(),
                  "a",
                  q,
                  g + (this.floatValueField7 - RenderComponent188.renderComponent187Field1.computeValue_2622(k)) / 2.0F,
                  k,
                  i
               );
            float s = q + RenderComponent188.renderComponent187Field1.computeValue_2626("a", k) + o;
            this.performAction_3371(drawEvent, s, g, this.floatValueField7, 1.0F);
            q = s + 1.0F + m;
         }

         for (int u = 0; u < strings.length; u++) {
            if (u > 0) {
               this.performAction_3371(drawEvent, q, g, this.floatValueField7, 1.0F);
               q += 1.0F + m;
            }

            RenderComponent188.renderComponent187Field1
               .performAction_2630(
                  drawEvent.getStack_1005(),
                  strings[u][0],
                  q,
                  g + (this.floatValueField7 - RenderComponent188.renderComponent187Field1.computeValue_2622(j)) / 2.0F,
                  j,
                  i
               );
            float t = q + RenderComponent188.renderComponent187Field1.computeValue_2626(strings[u][0], j) + n;
            RenderComponent188.renderComponent187Field5.performAction_2630(drawEvent.getStack_1005(), strings[u][1], t, r, this.floatValueField8, -1);
            q = t + RenderComponent188.renderComponent187Field5.computeValue_2626(strings[u][1], this.floatValueField8) + m;
         }
      }
   }

   private void performAction_3357(
      DrawEvent drawEvent, float f, float g, String[][] strings, boolean bl, int i, float h, float j, float k, float l, float m, float n
   ) {
      float o = f;
      float p = g + (this.floatValueField7 - RenderComponent188.renderComponent187Field5.computeValue_2622(this.floatValueField8)) / 2.0F + n;
      if (bl) {
         float q = k * 2.0F + RenderComponent188.renderComponent187Field1.computeValue_2626("a", j);
         this.performAction_3372(drawEvent, o, g, q, this.floatValueField7, true, 1.0F);
         RenderComponent188.renderComponent187Field1
            .performAction_2630(
               drawEvent.getStack_1005(),
               "a",
               o + k,
               g + (this.floatValueField7 - RenderComponent188.renderComponent187Field1.computeValue_2622(j)) / 2.0F,
               j,
               i
            );
         o += q + l;
      }

      for (String[] strings1 : strings) {
         float r = k * 2.0F
            + RenderComponent188.renderComponent187Field1.computeValue_2626(strings1[0], h)
            + m
            + RenderComponent188.renderComponent187Field5.computeValue_2626(strings1[1], this.floatValueField8);
         this.performAction_3372(drawEvent, o, g, r, this.floatValueField7, true, 1.0F);
         float s = o + k;
         RenderComponent188.renderComponent187Field1
            .performAction_2630(
               drawEvent.getStack_1005(),
               strings1[0],
               s,
               g + (this.floatValueField7 - RenderComponent188.renderComponent187Field1.computeValue_2622(h)) / 2.0F,
               h,
               i
            );
         RenderComponent188.renderComponent187Field5
            .performAction_2630(
               drawEvent.getStack_1005(),
               strings1[1],
               s + RenderComponent188.renderComponent187Field1.computeValue_2626(strings1[0], h) + m,
               p,
               this.floatValueField8,
               -1
            );
         o += r + l;
      }
   }

   private float computeValue_3358(String[][] strings, boolean bl, float f, float g, float h, float i, float j, float k) {
      if (!this.booleanSettingField3.getCurrentValue()) {
         float m = h;
         if (bl) {
            m = m + RenderComponent188.renderComponent187Field1.computeValue_2626("a", g) + k + 1.0F + i;
         }

         for (int n = 0; n < strings.length; n++) {
            if (n > 0) {
               m += 1.0F + i;
            }

            m = m
               + RenderComponent188.renderComponent187Field1.computeValue_2626(strings[n][0], f)
               + j
               + RenderComponent188.renderComponent187Field5.computeValue_2626(strings[n][1], this.floatValueField8)
               + i;
         }

         return m;
      } else {
         float l = 0.0F;
         if (bl) {
            l = 0.0F + h * 2.0F + RenderComponent188.renderComponent187Field1.computeValue_2626("a", g) + i;
         }

         for (String[] strings1 : strings) {
            l += h * 2.0F
               + RenderComponent188.renderComponent187Field1.computeValue_2626(strings1[0], f)
               + j
               + RenderComponent188.renderComponent187Field5.computeValue_2626(strings1[1], this.floatValueField8)
               + i;
         }

         return Math.max(0.0F, l - i);
      }
   }

   private String[][] computeValue_3355() {
      ArrayList arrayList = new ArrayList();
      if (this.booleanSettingField7.getCurrentValue()) {
         arrayList.add(new String[]{"L", NeneClient.getInstance().getUser().getUsername_0687()});
      }

      if (this.booleanSettingField4.getCurrentValue()) {
         arrayList.add(new String[]{"q", (int)this.floatValueField1 + " FPS"});
      }

      if (this.booleanSettingField5.getCurrentValue()) {
         arrayList.add(new String[]{"P", UtilComponent252.computeValue_3403() + " ms"});
      }

      if (this.booleanSettingField6.getCurrentValue()) {
         arrayList.add(new String[]{"T", LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))});
      }

      return arrayList.toArray(new String[0][]);
   }

   private String[][] computeValue_3354() {
      ArrayList arrayList = new ArrayList();
      if (this.booleanSettingField8.getCurrentValue()) {
         arrayList.add(new String[]{"b", "x " + (int)minecraft.player.getX() + " y " + (int)minecraft.player.getY() + " z " + (int)minecraft.player.getZ()});
      }

      if (this.booleanSettingField9.getCurrentValue()) {
         float f = minecraft.world != null && minecraft.world.getTickManager() != null ? minecraft.world.getTickManager().getTickRate() : 20.0F;
         arrayList.add(new String[]{"g", String.format("%.1f TPS", f)});
      }

      if (this.booleanSettingField10.getCurrentValue()) {
         arrayList.add(new String[]{"e", String.format("%.2f BPS", UtilComponent252.computeValue_3402())});
      }

      return arrayList.toArray(new String[0][]);
   }
}
