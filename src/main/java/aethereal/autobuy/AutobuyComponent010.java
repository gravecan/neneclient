package aethereal.autobuy;

import aethereal.config.ConfigComponent019;
import aethereal.config.ConfigComponent021;
import aethereal.config.ConfigContract017;
import aethereal.config.ThemeInfo;
import aethereal.config.ThemeManager;
import aethereal.core.NeneClient;
import aethereal.module.misc.Collector;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent184;
import aethereal.render.RenderComponent185;
import aethereal.render.RenderComponent186;
import aethereal.render.RenderComponent188;
import aethereal.render.RenderComponent193;
import aethereal.ui.element.ElementComponent211;
import aethereal.util.UtilComponent279;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.client.gui.DrawContext;
import org.joml.Matrix3x2fStack;
import org.joml.Vector4f;

public class AutobuyComponent010 extends ElementComponent211 {
   private final Vector4f vector4fField1 = new Vector4f();
   private final Vector4f vector4fField2 = new Vector4f();
   private Collector.NestedType343 nestedType343Field3;
   private Collector.NestedType343 nestedType343Field4;
   private ConfigContract017 configContract017Field5;
   private int integerField6;
   private float floatValueField7;

   public AutobuyComponent010() {
      super("S", "collector section");
   }

   @Override
   public void performAction_0050(DrawContext drawContext, Vector4f vector4f, int i, int j, float f, float g) {
      float h = vector4f.x + 8.0F;
      float k = vector4f.y;
      float l = h + 240.0F - 4.0F + 8.0F;
      this.performAction_0076(drawContext, vector4f, 20.0F, 4.0F, 10, k, f, i, j, g);
      this.performAction_0077(drawContext, vector4f, l, k, i, j, g);
   }

   private void performAction_0076(DrawContext drawContext, Vector4f vector4f, float f, float g, int i, float h, float j, int k, int l, float m) {
      Matrix3x2fStack matrix3x2fStack = drawContext.getMatrices();
      RenderComponent184 renderComponent184 = NeneClient.getInstance().getProcessors().getRenderComponent184Field3_0677();
      RenderComponent185 renderComponent185 = NeneClient.getInstance().getProcessors().getRenderComponent185Field4_0678();
      ThemeManager themeManager = NeneClient.getInstance().getProcessors().getThemes();
      float n = vector4f.x + 8.0F;
      float o = vector4f.y + vector4f.w;
      float p = f / 16.0F * 0.75F;
      float q = (f - 16.0F * p) / 2.0F;
      List list = NeneClient.getInstance().getProcessors().getAutobuyComponent009Field16_0665().getEntries();
      this.floatValueField7 = 0.0F;
      this.nestedType343Field4 = null;
      if (this.nestedType343Field3 == null && !list.isEmpty()) {
         this.nestedType343Field3 = (Collector.NestedType343)list.getFirst();
      }

      RenderComponent193.performAction_2681(matrix3x2fStack, vector4f.x, h, vector4f.z, o - h);

      for (int r = 0; r < list.size(); r++) {
         Collector.NestedType343 nestedType343 = (Collector.NestedType343)list.get(r);
         float s = n + r % i * (f + g);
         float t = h + r / i * (f + g) + j;
         this.floatValueField7 = Math.max(this.floatValueField7, (r / i + 1) * (f + g) - g);
         if (t + f >= h && t <= o) {
            if (UtilComponent279.checkCondition_3576(k, l, s, t, f, f)) {
               this.nestedType343Field4 = nestedType343;
            }

            nestedType343.getAnimationField4_1923().performAction_2528(nestedType343.isFlagField5_1925());
            nestedType343.getAnimationField4_1923().performAction_2535(0.0F, 1.0F, 0.4F, RenderComponent186.contract372Field9, m);
            float u = nestedType343.getAnimationField4_1923().getFloatValueField4_2530();
            renderComponent184.performAction_2577(
               matrix3x2fStack,
               s,
               t,
               f,
               f,
               4.0F,
               RenderComponent183.computeValue_2549(
                  RenderComponent183.computeValue_2558(255, 255, 255, 255),
                  0.023529412F * (nestedType343 == this.nestedType343Field3 ? 1.0F : (this.nestedType343Field4 == nestedType343 ? 0.5F : 0.0F))
               )
            );
            renderComponent184.performAction_2576(
               matrix3x2fStack,
               s,
               t,
               f,
               f,
               4.0F,
               0.5F,
               RenderComponent183.computeValue_2549(
                  themeManager.computeValue_0557(ThemeInfo.OUTLINE_SMALL).computeValue_0547(),
                  themeManager.computeValue_0557(ThemeInfo.OUTLINE_SMALL).computeValue_0548()
               )
            );
            renderComponent185.performAction_2592(drawContext, nestedType343.computeValue_1903(), s + q, t + q, 0, 0.5F + 0.5F * u, p, false);
            if (nestedType343.getCount_1909() > 1) {
               String string = String.valueOf(nestedType343.getCount_1909());
               RenderComponent188.renderComponent187Field3
                  .performAction_2630(
                     matrix3x2fStack,
                     string,
                     s + f - 2.0F - RenderComponent188.renderComponent187Field3.computeValue_2626(string, 6.5F),
                     t + f - 8.0F,
                     6.5F,
                     RenderComponent183.computeValue_2549(
                        themeManager.computeValue_0557(ThemeInfo.TEXT).computeValue_0547(),
                        0.25F + 0.75F * nestedType343.getAnimationField4_1923().getFloatValueField4_2530()
                     )
                  );
            }
         }
      }

      RenderComponent193.performAction_2680(matrix3x2fStack);
   }

   private void performAction_0077(DrawContext drawContext, Vector4f vector4f, float f, float g, int i, int j, float h) {
      Matrix3x2fStack matrix3x2fStack = drawContext.getMatrices();
      RenderComponent184 renderComponent184 = NeneClient.getInstance().getProcessors().getRenderComponent184Field3_0677();
      RenderComponent185 renderComponent185 = NeneClient.getInstance().getProcessors().getRenderComponent185Field4_0678();
      ThemeManager themeManager = NeneClient.getInstance().getProcessors().getThemes();
      float k = vector4f.x + vector4f.z - 8.0F - f;
      this.configContract017Field5 = null;
      if (this.nestedType343Field3 != null) {
         float l = f + 8.0F;
         float m = k - 16.0F;
         float n = g + 10.0F;
         this.nestedType343Field3.getAnimationField4_1923().performAction_2528(this.nestedType343Field3.isFlagField5_1925());
         this.nestedType343Field3.getAnimationField4_1923().performAction_2535(0.0F, 1.0F, 0.4F, RenderComponent186.contract372Field9, h);
         float o = this.nestedType343Field3.getAnimationField4_1923().getFloatValueField4_2530();
         float p = 0.35F + 0.65F * o;
         this.vector4fField1.set(l + m - 6.0F - 14.0F, n - 4.25F, 14.0F, 8.5F);
         float q = l + 20.0F;
         float r = (this.nestedType343Field3.checkCondition_1907() ? this.vector4fField1.x - 6.0F - 40.0F : this.vector4fField1.x) - 6.0F;
         renderComponent185.performAction_2592(drawContext, this.nestedType343Field3.computeValue_1903(), l, n - 6.0F, 0, p, 0.75F, false);
         RenderComponent188.renderComponent187Field3
            .performAction_2631(
               matrix3x2fStack,
               this.nestedType343Field3.getName_1924(),
               q,
               n - RenderComponent188.renderComponent187Field3.computeValue_2622(7.5F) / 2.0F - 0.75F,
               7.5F,
               RenderComponent183.computeValue_2550(
                  themeManager.computeValue_0557(ThemeInfo.TEXT_DISABLED).computeValue_0547(),
                  themeManager.computeValue_0557(ThemeInfo.TEXT).computeValue_0547(),
                  o
               ),
               r - q
            );
         renderComponent184.performAction_2577(
            matrix3x2fStack,
            this.vector4fField1.x,
            this.vector4fField1.y,
            this.vector4fField1.z,
            this.vector4fField1.w,
            3.25F,
            RenderComponent183.computeValue_2549(themeManager.computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(), o)
         );
         renderComponent184.performAction_2576(
            matrix3x2fStack,
            this.vector4fField1.x,
            this.vector4fField1.y,
            this.vector4fField1.z,
            this.vector4fField1.w,
            3.25F,
            0.3F,
            themeManager.computeValue_0557(ThemeInfo.OUTLINE_SMALL).computeValue_0547()
         );
         renderComponent184.performAction_2577(
            matrix3x2fStack,
            this.vector4fField1.x + 1.5F + 5.5F * o,
            this.vector4fField1.y + 1.5F,
            5.5F,
            5.5F,
            1.75F,
            RenderComponent183.computeValue_2550(
               RenderComponent183.computeValue_2558(150, 150, 155, 255), RenderComponent183.computeValue_2558(255, 255, 255, 255), o
            )
         );
         this.vector4fField2.set(0.0F, 0.0F, 0.0F, 0.0F);
         if (this.nestedType343Field3.checkCondition_1907()) {
            this.vector4fField2.set(this.vector4fField1.x - 6.0F - 32.0F, n - 5.0F, 32.0F, 10.0F);
            RenderComponent188.renderComponent187Field3
               .performAction_2620(
                  matrix3x2fStack,
                  "✓",
                  this.vector4fField2.x + 6.0F,
                  n - RenderComponent188.renderComponent187Field3.computeValue_2622(11.0F) / 2.0F - 2.0F,
                  11.0F,
                  RenderComponent183.computeValue_2549(themeManager.computeValue_0557(ThemeInfo.TEXT_DISABLED).computeValue_0547(), p)
               );
            RenderComponent188.renderComponent187Field3
               .performAction_2620(
                  matrix3x2fStack,
                  String.valueOf(this.nestedType343Field3.getCount_1909()),
                  this.vector4fField2.x + this.vector4fField2.z / 2.0F,
                  n - RenderComponent188.renderComponent187Field3.computeValue_2622(6.75F) / 2.0F - 0.75F,
                  6.75F,
                  RenderComponent183.computeValue_2550(
                     themeManager.computeValue_0557(ThemeInfo.TEXT_DISABLED).computeValue_0547(),
                     themeManager.computeValue_0557(ThemeInfo.TEXT).computeValue_0547(),
                     o
                  )
               );
            RenderComponent188.renderComponent187Field3
               .performAction_2620(
                  matrix3x2fStack,
                  "✗",
                  this.vector4fField2.x + this.vector4fField2.z - 6.0F,
                  n - RenderComponent188.renderComponent187Field3.computeValue_2622(11.0F) / 2.0F - 1.25F,
                  11.0F,
                  RenderComponent183.computeValue_2549(themeManager.computeValue_0557(ThemeInfo.TEXT_DISABLED).computeValue_0547(), p)
               );
         }

         List list = Stream.concat(
               this.nestedType343Field3.getConfigComponent020Field1_1902() == null
                  ? Stream.empty()
                  : this.nestedType343Field3
                     .getConfigComponent020Field1_1902()
                     .getListField1_0370()
                     .stream()
                     .map(configComponent019 -> (ConfigComponent019)configComponent019),
               this.nestedType343Field3.getConfigComponent022Field2_1911() == null
                  ? Stream.empty()
                  : this.nestedType343Field3
                     .getConfigComponent022Field2_1911()
                     .getListField1_0382()
                     .stream()
                     .map(configComponent021 -> (ConfigComponent021)configComponent021)
            )
            .toList();
         float s = g + 20.0F + 8.0F;

         for (ConfigContract017 configContract017 : list) {
            if (!configContract017.checkCondition_0344()) {
               configContract017.computeValue_0351().performAction_2528(configContract017.checkCondition_0348());
               configContract017.computeValue_0351().performAction_2535(0.0F, 1.0F, 0.4F, RenderComponent186.contract372Field9, h);
               float t = configContract017.computeValue_0351().getFloatValueField4_2530() * o;
               float u = s + 5.0F;
               float v = l + m - 6.0F - 13.0F;
               float w = u - 3.8475F;
               float x = v - 6.0F - 28.0F;
               RenderComponent188.renderComponent187Field3
                  .performAction_2630(
                     matrix3x2fStack,
                     configContract017.computeValue_0343(),
                     l,
                     u - RenderComponent188.renderComponent187Field3.computeValue_2622(6.75F) / 2.0F - 0.75F,
                     6.75F,
                     RenderComponent183.computeValue_2550(
                        themeManager.computeValue_0557(ThemeInfo.TEXT_DISABLED).computeValue_0547(),
                        themeManager.computeValue_0557(ThemeInfo.TEXT).computeValue_0547(),
                        t
                     )
                  );
               if (configContract017.checkCondition_0349()) {
                  renderComponent184.performAction_2574(
                     drawContext,
                     x + 13.625F,
                     u - 2.5F,
                     0.75F,
                     5.0F,
                     RenderComponent183.computeValue_2549(
                        themeManager.computeValue_0557(ThemeInfo.OUTLINE_MEDIUM).computeValue_0547(),
                        themeManager.computeValue_0557(ThemeInfo.OUTLINE_MEDIUM).computeValue_0548() * p
                     )
                  );
                  RenderComponent188.renderComponent187Field3
                     .performAction_2620(
                        matrix3x2fStack,
                        "✗",
                        x + 7.0F,
                        u - RenderComponent188.renderComponent187Field3.computeValue_2622(11.0F) / 2.0F - 1.25F,
                        11.0F,
                        RenderComponent183.computeValue_2549(themeManager.computeValue_0557(ThemeInfo.TEXT_DISABLED).computeValue_0547(), p)
                     );
                  RenderComponent188.renderComponent187Field3
                     .performAction_2620(
                        matrix3x2fStack,
                        "✓",
                        x + 21.0F,
                        u - RenderComponent188.renderComponent187Field3.computeValue_2622(11.0F) / 2.0F - 2.0F,
                        11.0F,
                        RenderComponent183.computeValue_2549(themeManager.computeValue_0557(ThemeInfo.TEXT_DISABLED).computeValue_0547(), p)
                     );
               }

               renderComponent184.performAction_2577(
                  matrix3x2fStack,
                  v,
                  w,
                  13.0F,
                  7.695F,
                  3.078F,
                  RenderComponent183.computeValue_2549(themeManager.computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(), t)
               );
               renderComponent184.performAction_2576(
                  matrix3x2fStack,
                  v,
                  w,
                  13.0F,
                  7.695F,
                  3.078F,
                  0.5F,
                  RenderComponent183.computeValue_2549(
                     themeManager.computeValue_0557(ThemeInfo.OUTLINE_MEDIUM).computeValue_0547(),
                     themeManager.computeValue_0557(ThemeInfo.OUTLINE_MEDIUM).computeValue_0548() * p
                  )
               );
               renderComponent184.performAction_2577(
                  matrix3x2fStack,
                  v + 1.496F + 5.216F * t,
                  w + 1.496F,
                  4.703F,
                  4.703F,
                  1.539F,
                  RenderComponent183.computeValue_2550(
                     RenderComponent183.computeValue_2558(150, 150, 155, 255), RenderComponent183.computeValue_2558(255, 255, 255, 255), t
                  )
               );
               if (UtilComponent279.checkCondition_3576(i, j, v, w, 13.0F, 7.695F)) {
                  this.configContract017Field5 = configContract017;
                  this.integerField6 = 0;
               } else if (configContract017.checkCondition_0349() && UtilComponent279.checkCondition_3576(i, j, x, u - 5.0F, 14.0F, 10.0F)) {
                  this.configContract017Field5 = configContract017;
                  this.integerField6 = 1;
               } else if (configContract017.checkCondition_0349() && UtilComponent279.checkCondition_3576(i, j, x + 14.0F, u - 5.0F, 14.0F, 10.0F)) {
                  this.configContract017Field5 = configContract017;
                  this.integerField6 = 2;
               }

               s += 14.0F;
            }
         }
      }
   }

   @Override
   public boolean checkCondition_0044(double d, double e, int i) {
      if (this.nestedType343Field3 != null
         && UtilComponent279.checkCondition_3576(d, e, this.vector4fField1.x, this.vector4fField1.y, this.vector4fField1.z, this.vector4fField1.w)) {
         this.nestedType343Field3.computeValue_1922(!this.nestedType343Field3.isFlagField5_1925());
         return true;
      }

      if (this.nestedType343Field3 != null
         && UtilComponent279.checkCondition_3576(d, e, this.vector4fField2.x, this.vector4fField2.y, this.vector4fField2.z / 2.0F, this.vector4fField2.w)) {
         this.nestedType343Field3.setCount_1916(Math.max(1, this.nestedType343Field3.getCount_1909() - 1));
         return true;
      }

      if (this.nestedType343Field3 != null
         && UtilComponent279.checkCondition_3576(
            d, e, this.vector4fField2.x + this.vector4fField2.z / 2.0F, this.vector4fField2.y, this.vector4fField2.z / 2.0F, this.vector4fField2.w
         )) {
         this.nestedType343Field3.setCount_1916(Math.min(this.nestedType343Field3.computeValue_1905(), this.nestedType343Field3.getCount_1909() + 1));
         return true;
      }

      if (this.configContract017Field5 != null) {
         switch (this.integerField6) {
            case 0:
               this.configContract017Field5.performAction_0347(!this.configContract017Field5.checkCondition_0348());
               return true;
            case 1:
               this.configContract017Field5.performAction_0352(1);
               return true;
            case 2:
               this.configContract017Field5.performAction_0352(-1);
               return true;
            default:
               return true;
         }
      } else if (this.nestedType343Field4 != null) {
         this.nestedType343Field3 = this.nestedType343Field4;
         return true;
      } else {
         return false;
      }
   }

   @Override
   public float getFloatValueField9_0051(Vector4f vector4f) {
      return this.floatValueField7;
   }
}
