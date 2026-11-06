package aethereal.ui.widget;

import aethereal.config.ThemeInfo;
import aethereal.config.ThemeManager;
import aethereal.core.ClientTickEvent;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.event.DrawEvent;
import aethereal.render.Animation;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent186;
import aethereal.render.RenderComponent188;
import aethereal.setting.Setting;
import aethereal.ui.element.ElementComponent210;
import aethereal.ui.element.SettingElement;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.joml.Vector4f;

public class WidgetComponent249 {
   private final List<Setting<?>> listField1 = new ObjectArrayList();
   private final List<SettingElement<?>> listField2 = new ObjectArrayList();
   protected final Animation animationField3 = new Animation();
   protected final Animation animationField4 = new Animation();
   protected final Animation animationField5 = new Animation();
   private boolean flagField6 = false;
   protected float floatValueField7 = 12.5F;
   protected float floatValueField8 = 7.0F;
   private final ElementComponent210 dragInfo;
   private final Vector4f vector4fField9 = new Vector4f(-1.0F, -1.0F, 0.0F, 0.0F);

   public List<Setting<?>> getListField1_3369() {
      return this.listField1;
   }

   public List<SettingElement<?>> getListField2_3361() {
      return this.listField2;
   }

   public Animation getAnimationField3_3363() {
      return this.animationField3;
   }

   public Animation getAnimationField4_3382() {
      return this.animationField4;
   }

   public Animation getAnimationField5_3359() {
      return this.animationField5;
   }

   public void setFlagField6_3368(boolean bl) {
      this.flagField6 = bl;
   }

   public boolean isFlagField6_3365() {
      return this.flagField6;
   }

   public float getFloatValueField7_3381() {
      return this.floatValueField7;
   }

   public float getFloatValueField8_3380() {
      return this.floatValueField8;
   }

   public ElementComponent210 getDragInfo_3360() {
      return this.dragInfo;
   }

   public Vector4f getVector4fField9_3364() {
      return this.vector4fField9;
   }

   public WidgetComponent249(ElementComponent210 elementComponent210) {
      this.dragInfo = elementComponent210;
      elementComponent210.setWidgetComponent249Field1_3016(this);
   }

   protected final void performAction_3362(Setting<?>... settings) {
      for (Setting setting : settings) {
         this.listField1.add(setting);
         this.listField2.add(setting.createElement());
      }
   }

   public void onDrawEvent_3315(DrawEvent drawEvent) {
      this.getAnimationField4_3382().performAction_2535(0.0F, 1.0F, 0.3F, RenderComponent186.contract372Field7, drawEvent.getTickNeneClient_0997());
      this.animationField5.performAction_2528(this.flagField6 && MinecraftAccess.minecraft.currentScreen instanceof ChatScreen);
      this.animationField5.performAction_2535(0.0F, 1.0F, 0.3F, RenderComponent186.contract372Field7, drawEvent.getTickNeneClient_0997());
      if (this.animationField5.getFloatValueField4_2530() > 0.0F) {
         this.onDrawEvent_3370(drawEvent);
      }
   }

   public void performAction_3316(ClientTickEvent clientTickEvent) {
      this.getAnimationField4_3382()
         .performAction_2528(this.dragInfo == NeneClient.getInstance().getProcessors().getDragProcessorField7_0680().getElementComponent210Field3_2926());
   }

   protected void onDrawEvent_3370(DrawEvent drawEvent) {
      List list = this.listField2.stream().filter(settingElement1 -> settingElement1.getSetting_3021().getVisibility().get()).toList();
      if (!list.isEmpty()) {
         float f = list.stream()
            .map(
               settingElement1 -> 19.5F
                  + RenderComponent188.renderComponent187Field5.computeValue_2626(settingElement1.getSetting_3021().getName(), 6.5F)
                  + 25.0F
            )
            .reduce(0.0F, (float1, float2) -> Math.max(float1, float2));
         float g = 12.0F * list.size() + (list.size() - 1);
         float h = this.animationField5.getFloatValueField4_2530() * this.computeValue_3367();
         float i = this.dragInfo.computeValue_3009() - g - 2.0F >= 0.0F
            ? this.dragInfo.computeValue_3007() + this.dragInfo.getWidth_3005() / 2.0F - f / 2.0F
            : this.dragInfo.computeValue_3007() + this.dragInfo.getWidth_3005() + 2.0F;
         float j = this.dragInfo.computeValue_3009() - g - 2.0F >= 0.0F ? this.dragInfo.computeValue_3009() - g - 2.0F : this.dragInfo.computeValue_3009();
         float k = Math.min(Math.max(i, 0.0F), MinecraftAccess.minecraft.getWindow().getScaledWidth() - f - 2.0F);
         float l = Math.min(Math.max(j, 0.0F), MinecraftAccess.minecraft.getWindow().getScaledHeight() - g);
         this.performAction_3372(drawEvent, k, l, f, g, true, h);
         this.vector4fField9.set(k, l, f, g);
         float m = l;

         for (SettingElement settingElement : list) {
            settingElement.getVector4fField3_3024().set(k, m, f, 12.0F);
            settingElement.performAction_2984(drawEvent, k, m, f, h);
            m += 13.0F;
            if (settingElement != list.getLast()) {
               drawEvent.getRenderComponent184Field1_0998()
                  .performAction_2577(
                     drawEvent.getContext_1000().getMatrices(),
                     k,
                     m - 1.0F,
                     f,
                     0.75F,
                     0.0F,
                     RenderComponent183.computeValue_2549(RenderComponent183.computeValue_2558(200, 200, 200, 255), 0.2F * h)
                  );
            }
         }
      }
   }

   protected void performAction_3376(DrawEvent drawEvent, String string, Object object, float f, float g) {
      this.performAction_3377(
         drawEvent, string, object, f, g, NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547()
      );
   }

   protected void performAction_3377(DrawEvent drawEvent, String string, Object object, float f, float g, int i) {
      this.performAction_3373(drawEvent, this.dragInfo.computeValue_3007(), this.dragInfo.computeValue_3009(), string, object, f, g, i);
   }

   protected void performAction_3373(DrawEvent drawEvent, float f, float g, String string, Object object, float h, float i, int j) {
      this.performAction_3374(drawEvent, f, g, string, null, object, h, i, j);
   }

   protected void performAction_3375(DrawEvent drawEvent, float f, float g, ItemStack itemStack, Object object, float h, float i, int j) {
      this.performAction_3374(drawEvent, f, g, null, itemStack, object, h, i, j);
   }

   private void performAction_3374(DrawEvent drawEvent, float f, float g, String string, ItemStack itemStack, Object object, float h, float i, int j) {
      if (i > 0.0F) {
         float k = this.floatValueField8 + 1.0F;
         this.performAction_3372(drawEvent, f, g, h, this.floatValueField7, true, i);
         if (itemStack != null) {
            drawEvent.getContext_1000().drawItem(itemStack, (int)(f + 3.0F), (int)(g + (this.floatValueField7 - 8.0F) / 2.0F - 0.25F));
         } else {
            RenderComponent188.renderComponent187Field1
               .performAction_2630(
                  drawEvent.getStack_1005(),
                  string,
                  f + 3.0F,
                  g + (this.floatValueField7 - RenderComponent188.renderComponent187Field1.computeValue_2622(k)) / 2.0F,
                  k,
                  RenderComponent183.computeValue_2549(j, i)
               );
         }

         this.performAction_3371(drawEvent, f + 13.5F, g, this.floatValueField7, i);
         if (object instanceof Text text) {
            RenderComponent188.renderComponent187Field5
               .performAction_2630(
                  drawEvent.getStack_1005(),
                  text.getString(),
                  f + 17.5F,
                  g + (this.floatValueField7 - RenderComponent188.renderComponent187Field5.computeValue_2622(this.floatValueField8)) / 2.0F - 0.5F,
                  this.floatValueField8,
                  RenderComponent183.computeValue_2549(-1, i)
               );
         } else {
            RenderComponent188.renderComponent187Field5
               .performAction_2630(
                  drawEvent.getStack_1005(),
                  String.valueOf(object),
                  f + 17.5F,
                  g + (this.floatValueField7 - RenderComponent188.renderComponent187Field5.computeValue_2622(this.floatValueField8)) / 2.0F - 0.5F,
                  this.floatValueField8,
                  RenderComponent183.computeValue_2549(-1, i)
               );
         }
      }
   }

   protected void performAction_3372(DrawEvent drawEvent, float f, float g, float h, float i, boolean bl, float j) {
      if (j > 0.0F) {
         ThemeManager themeManager = NeneClient.getInstance().getProcessors().getThemes();
         float k = Math.max(0.38F, themeManager.computeValue_0557(ThemeInfo.BACKGROUND_HUD).computeValue_0548() * 0.55F) * j;
         int l = RenderComponent183.computeValue_2550(
            themeManager.computeValue_0557(ThemeInfo.BACKGROUND_HUD).computeValue_0547(),
            themeManager.computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(),
            themeManager.computeValue_0557(ThemeInfo.PRIMARY).computeValue_0548() / 6.0F
         );
         themeManager.computeValue_0557(ThemeInfo.BACKGROUND_HUD).performAction_0545(170);
         if (bl) {
            drawEvent.getRenderComponent184Field1_0998()
               .performAction_2580(
                  drawEvent.getStack_1005(),
                  f,
                  g,
                  h,
                  i,
                  5.0F + 1.0F * this.animationField4.getFloatValueField4_2530(),
                  RenderComponent183.computeValue_2549(l, k),
                  j,
                  RenderComponent183.computeValue_2549(l, k),
                  8.0F + 2.0F * this.animationField4.getFloatValueField4_2530()
               );
         } else {
            drawEvent.getRenderComponent184Field1_0998()
               .performAction_2562(drawEvent.getStack_1005(), f, g, h, i, 5.0F, RenderComponent183.computeValue_2549(l, k), j);
         }
      }
   }

   protected void performAction_3371(DrawEvent drawEvent, float f, float g, float h, float i) {
      float j = h / 2.0F;
      drawEvent.getRenderComponent184Field1_0998()
         .performAction_2577(
            drawEvent.getContext_1000().getMatrices(),
            f,
            g + (h - j) / 2.0F,
            0.75F,
            j,
            0.0F,
            RenderComponent183.computeValue_2549(RenderComponent183.computeValue_2558(200, 200, 200, 255), 0.5F * i)
         );
   }

   public float computeValue_3367() {
      return this.animationField3.getFloatValueField4_2530() * (1.0F - 0.1F * this.animationField4.getFloatValueField4_2530());
   }
}
