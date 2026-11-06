package aethereal.autobuy;

import aethereal.config.ThemeInfo;
import aethereal.config.ThemeManager;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.render.Animation;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent184;
import aethereal.render.RenderComponent185;
import aethereal.render.RenderComponent186;
import aethereal.render.RenderComponent188;
import aethereal.render.RenderComponent193;
import aethereal.ui.element.ElementComponent211;
import aethereal.ui.element.TextField;
import aethereal.util.UtilComponent265;
import aethereal.util.UtilComponent279;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.gui.DrawContext;
import org.joml.Matrix3x2fStack;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class AutobuyComponent007 extends ElementComponent211 implements MinecraftAccess {
   private final TextField textFieldField1 = new TextField(TextField.State384.GUI_SETTING, true);
   private final Animation animationField2 = new Animation();
   private final Vector4f vector4fField3 = new Vector4f();
   private final Vector4f vector4fField4 = new Vector4f();
   private final Vector4f vector4fField5 = new Vector4f();
   private final Vector4f vector4fField6 = new Vector4f();
   private AutoBuyEntry autoBuyEntryField7;
   private AutoBuyEntry autoBuyEntryField8;
   private float floatValueField9;

   public AutobuyComponent007() {
      super("h", "auto-buy section");
      this.textFieldField1.setStringField3_2969("Item Price");
   }

   @Override
   public void performAction_0050(DrawContext drawContext, Vector4f vector4f, int i, int j, float f, float g) {
      float h = vector4f.x + 8.0F;
      float k = vector4f.y;
      float l = h + 240.0F - 4.0F + 8.0F;
      this.performAction_0048(drawContext, vector4f, 20.0F, 4.0F, 10, k, f, i, j, g);
      this.performAction_0049(drawContext, vector4f, l, k, i, j, g);
   }

   private void performAction_0047(AutoBuyEntry autoBuyEntry) {
      this.autoBuyEntryField7 = autoBuyEntry;
      this.textFieldField1.getStringBuilderField4_2956().setLength(0);
      this.textFieldField1.performAction_2950(false);
      if (autoBuyEntry != null && autoBuyEntry.getDoubleValueField2_0038() > 0.0) {
         this.textFieldField1.getStringBuilderField4_2956().append((long)autoBuyEntry.getDoubleValueField2_0038());
      }
   }

   private void performAction_0048(DrawContext drawContext, Vector4f vector4f, float f, float g, int i, float h, float j, int k, int l, float m) {
      Matrix3x2fStack matrix3x2fStack = drawContext.getMatrices();
      RenderComponent184 renderComponent184 = NeneClient.getInstance().getProcessors().getRenderComponent184Field3_0677();
      RenderComponent185 renderComponent185 = NeneClient.getInstance().getProcessors().getRenderComponent185Field4_0678();
      ThemeManager themeManager = NeneClient.getInstance().getProcessors().getThemes();
      float n = vector4f.x + 8.0F;
      float o = vector4f.y + vector4f.w;
      float p = f / 16.0F * 0.75F;
      float q = (f - 16.0F * p) / 2.0F;
      List list = NeneClient.getInstance().getProcessors().getAutoBuyManagerField12_0663().getEntries();
      this.floatValueField9 = 0.0F;
      this.autoBuyEntryField8 = null;
      if (this.autoBuyEntryField7 == null && !list.isEmpty()) {
         this.performAction_0047((AutoBuyEntry)list.getFirst());
      }

      RenderComponent193.performAction_2681(matrix3x2fStack, vector4f.x, h, vector4f.z, o - h);

      for (int r = 0; r < list.size(); r++) {
         AutoBuyEntry autoBuyEntry = (AutoBuyEntry)list.get(r);
         float s = n + r % i * (f + g);
         float t = h + r / i * (f + g) + j;
         this.floatValueField9 = Math.max(this.floatValueField9, (r / i + 1) * (f + g) - g);
         if (t + f >= h && t <= o) {
            if (UtilComponent279.checkCondition_3576(k, l, s, t, f, f)) {
               this.autoBuyEntryField8 = autoBuyEntry;
            }

            autoBuyEntry.getAnimationField1_0037().performAction_2528(autoBuyEntry.isFlagField3_0039());
            autoBuyEntry.getAnimationField1_0037().performAction_2535(0.0F, 1.0F, 0.4F, RenderComponent186.contract372Field9, m);
            renderComponent184.performAction_2577(
               matrix3x2fStack,
               s,
               t,
               f,
               f,
               4.0F,
               RenderComponent183.computeValue_2549(
                  RenderComponent183.computeValue_2558(255, 255, 255, 255),
                  0.023529412F * (autoBuyEntry == this.autoBuyEntryField7 ? 1.0F : (this.autoBuyEntryField8 == autoBuyEntry ? 0.5F : 0.0F))
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
            float u = autoBuyEntry.getAnimationField1_0037().getFloatValueField4_2530();
            renderComponent185.performAction_2592(drawContext, autoBuyEntry.computeValue_0025(), s + q, t + q, 0, 0.5F + 0.5F * u, p, false);
         }
      }

      RenderComponent193.performAction_2680(matrix3x2fStack);
   }

   private void performAction_0049(DrawContext drawContext, Vector4f vector4f, float f, float g, int i, int j, float h) {
      Matrix3x2fStack matrix3x2fStack = drawContext.getMatrices();
      RenderComponent184 renderComponent184 = NeneClient.getInstance().getProcessors().getRenderComponent184Field3_0677();
      RenderComponent185 renderComponent185 = NeneClient.getInstance().getProcessors().getRenderComponent185Field4_0678();
      ThemeManager themeManager = NeneClient.getInstance().getProcessors().getThemes();
      float k = vector4f.x + vector4f.z - 8.0F - f;
      if (this.autoBuyEntryField7 != null) {
         float l = f + 8.0F;
         float m = k - 16.0F;
         float n = g + 10.0F;
         this.autoBuyEntryField7.getAnimationField1_0037().performAction_2528(this.autoBuyEntryField7.isFlagField3_0039());
         this.autoBuyEntryField7.getAnimationField1_0037().performAction_2535(0.0F, 1.0F, 0.4F, RenderComponent186.contract372Field9, h);
         float o = this.autoBuyEntryField7.getAnimationField1_0037().getFloatValueField4_2530();
         this.vector4fField6.set(l + m - 6.0F - 14.0F, n - 4.25F, 14.0F, 8.5F);
         this.vector4fField3.set(this.vector4fField6.x - 4.0F - 10.0F, n - 5.0F, 10.0F, 10.0F);
         float p = l + 20.0F;
         renderComponent185.performAction_2592(drawContext, this.autoBuyEntryField7.computeValue_0025(), l, n - 6.0F, 0, 0.35F + 0.65F * o, 0.75F, false);
         RenderComponent188.renderComponent187Field3
            .performAction_2631(
               matrix3x2fStack,
               this.autoBuyEntryField7.getDisplayName_0029(),
               p,
               n - RenderComponent188.renderComponent187Field3.computeValue_2622(7.5F) / 2.0F - 0.75F,
               7.5F,
               RenderComponent183.computeValue_2550(
                  themeManager.computeValue_0557(ThemeInfo.TEXT_DISABLED).computeValue_0547(),
                  themeManager.computeValue_0557(ThemeInfo.TEXT).computeValue_0547(),
                  o
               ),
               this.vector4fField3.x - 6.0F - p
            );
         this.animationField2
            .performAction_2528(
               UtilComponent279.checkCondition_3576(i, j, this.vector4fField3.x, this.vector4fField3.y, this.vector4fField3.z, this.vector4fField3.w)
            );
         this.animationField2.performAction_2535(0.0F, 1.0F, 0.4F, RenderComponent186.contract372Field9, h);
         float q = this.animationField2.getFloatValueField4_2530();
         RenderComponent188.renderComponent187Field1
            .performAction_2630(
               matrix3x2fStack,
               "G",
               this.vector4fField3.x + (this.vector4fField3.z - RenderComponent188.renderComponent187Field1.computeValue_2618("G", 8.0F + q)) / 2.0F,
               this.vector4fField3.y + (this.vector4fField3.w - RenderComponent188.renderComponent187Field1.computeValue_2622(8.0F + q)) / 2.0F,
               8.0F + q,
               RenderComponent183.computeValue_2550(
                  themeManager.computeValue_0557(ThemeInfo.TEXT_DISABLED).computeValue_0547(),
                  themeManager.computeValue_0557(ThemeInfo.TEXT).computeValue_0547(),
                  o
               )
            );
         renderComponent184.performAction_2577(
            matrix3x2fStack,
            this.vector4fField6.x,
            this.vector4fField6.y,
            this.vector4fField6.z,
            this.vector4fField6.w,
            3.25F,
            RenderComponent183.computeValue_2549(themeManager.computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(), o)
         );
         renderComponent184.performAction_2576(
            matrix3x2fStack,
            this.vector4fField6.x,
            this.vector4fField6.y,
            this.vector4fField6.z,
            this.vector4fField6.w,
            3.25F,
            0.3F,
            themeManager.computeValue_0557(ThemeInfo.OUTLINE_SMALL).computeValue_0547()
         );
         renderComponent184.performAction_2577(
            matrix3x2fStack,
            this.vector4fField6.x + 1.5F + 5.5F * o,
            this.vector4fField6.y + 1.5F,
            5.5F,
            5.5F,
            1.75F,
            RenderComponent183.computeValue_2550(
               RenderComponent183.computeValue_2558(150, 150, 155, 255), RenderComponent183.computeValue_2558(255, 255, 255, 255), o
            )
         );
         this.vector4fField5.set(l + m - 14.0F, g + 20.0F + 8.0F, 14.0F, 14.0F);
         this.vector4fField4.set(this.vector4fField5.x - 4.0F - 14.0F, g + 20.0F + 8.0F, 14.0F, 14.0F);
         String string1 = this.textFieldField1.getStringBuilderField4_2956().toString().replace(",", "");
         String string;
         if (string1.isEmpty()) {
            string = "";
         } else {
            Locale locale = Locale.US;
            Object[] objects = new Object[]{Long.parseLong(string1.length() > 15 ? string1.substring(0, 15) : string1)};
            string = String.format(locale, "%,d", objects);
         }

         String string2 = string;
         if (!string2.contentEquals(this.textFieldField1.getStringBuilderField4_2956())) {
            this.textFieldField1.getStringBuilderField4_2956().setLength(0);
            this.textFieldField1.getStringBuilderField4_2956().append(string2);
            this.textFieldField1.performAction_2950(this.textFieldField1.isFlagField7_2939());
         }

         float r = this.vector4fField4.x - 4.0F - l;
         this.textFieldField1.setVector2fField2_2944(new Vector2f(r, 14.0F));
         this.textFieldField1.setVector2fField1_2966(new Vector2f(l, g + 20.0F + 8.0F));
         this.textFieldField1.performAction_2964(drawContext, i, j, h, 1.0F);
         this.autoBuyEntryField7.setDoubleValueField2_0026(string1.isEmpty() ? 0.0 : Double.parseDouble(string1));
         boolean bl = UtilComponent279.checkCondition_3576(i, j, this.vector4fField4.x, this.vector4fField4.y, this.vector4fField4.z, this.vector4fField4.w);
         boolean bl1 = UtilComponent279.checkCondition_3576(i, j, this.vector4fField5.x, this.vector4fField5.y, this.vector4fField5.z, this.vector4fField5.w);
         renderComponent184.performAction_2577(
            matrix3x2fStack,
            this.vector4fField4.x,
            this.vector4fField4.y,
            this.vector4fField4.z,
            this.vector4fField4.w,
            4.0F,
            RenderComponent183.computeValue_2549(
               themeManager.computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(), (10.0F + 20.0F * (bl ? 1.0F : 0.0F)) / 255.0F
            )
         );
         renderComponent184.performAction_2576(
            matrix3x2fStack,
            this.vector4fField4.x,
            this.vector4fField4.y,
            this.vector4fField4.z,
            this.vector4fField4.w,
            4.0F,
            0.5F,
            themeManager.computeValue_0557(ThemeInfo.OUTLINE_SMALL).computeValue_0547()
         );
         RenderComponent188.renderComponent187Field1
            .performAction_2630(
               matrix3x2fStack,
               "N",
               this.vector4fField4.x + (14.0F - RenderComponent188.renderComponent187Field1.computeValue_2618("N", 8.0F)) / 2.0F,
               this.vector4fField4.y + (14.0F - RenderComponent188.renderComponent187Field1.computeValue_2622(8.0F)) / 2.0F,
               8.0F,
               themeManager.computeValue_0557(ThemeInfo.TEXT).computeValue_0547()
            );
         renderComponent184.performAction_2577(
            matrix3x2fStack,
            this.vector4fField5.x,
            this.vector4fField5.y,
            this.vector4fField5.z,
            this.vector4fField5.w,
            4.0F,
            RenderComponent183.computeValue_2549(
               themeManager.computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(), (10.0F + 20.0F * (bl1 ? 1.0F : 0.0F)) / 255.0F
            )
         );
         renderComponent184.performAction_2576(
            matrix3x2fStack,
            this.vector4fField5.x,
            this.vector4fField5.y,
            this.vector4fField5.z,
            this.vector4fField5.w,
            4.0F,
            0.5F,
            themeManager.computeValue_0557(ThemeInfo.OUTLINE_SMALL).computeValue_0547()
         );
         RenderComponent188.renderComponent187Field1
            .performAction_2630(
               matrix3x2fStack,
               "A",
               this.vector4fField5.x + (14.0F - RenderComponent188.renderComponent187Field1.computeValue_2618("A", 8.0F)) / 2.0F,
               this.vector4fField5.y + (14.0F - RenderComponent188.renderComponent187Field1.computeValue_2622(8.0F)) / 2.0F,
               8.0F,
               themeManager.computeValue_0557(ThemeInfo.TEXT).computeValue_0547()
            );
      }
   }

   @Override
   public boolean checkCondition_0044(double d, double e, int i) {
      if (i == 0 && UtilComponent279.checkCondition_3576(d, e, this.vector4fField4.x, this.vector4fField4.y, this.vector4fField4.z, this.vector4fField4.w)) {
         NeneClient.getInstance().getProcessors().getAutoBuyManagerField12_0663().save();
         UtilComponent265.performAction_3505("Auto-purchase configuration successfully saved");
         return true;
      }

      if (i == 0 && UtilComponent279.checkCondition_3576(d, e, this.vector4fField5.x, this.vector4fField5.y, this.vector4fField5.z, this.vector4fField5.w)) {
         NeneClient.getInstance().getProcessors().getAutoBuyManagerField12_0663().load();
         UtilComponent265.performAction_3505("Auto-purchase configuration successfully loaded");
         this.performAction_0047(this.autoBuyEntryField7);
         return true;
      }

      if (this.autoBuyEntryField7 != null
         && UtilComponent279.checkCondition_3576(d, e, this.vector4fField3.x, this.vector4fField3.y, this.vector4fField3.z, this.vector4fField3.w)) {
         minecraft.player
            .networkHandler
            .sendChatCommand(
               "ah search "
                  + this.autoBuyEntryField7
                     .getDisplayName_0029()
                     .replaceAll("\\[\\d+x\\d+]", "")
                     .replace("⚡", "")
                     .replace("xxx", "")
                     .replace("[", "")
                     .replace("]", "")
                     .replace("★", "")
                     .trim()
                     .replaceAll("\\s+", "")
            );
         return true;
      }

      if (this.autoBuyEntryField7 != null
         && UtilComponent279.checkCondition_3576(d, e, this.vector4fField6.x, this.vector4fField6.y, this.vector4fField6.z, this.vector4fField6.w)) {
         this.autoBuyEntryField7.setFlagField3_0028(!this.autoBuyEntryField7.isFlagField3_0039());
         return true;
      }

      if (this.autoBuyEntryField7 != null) {
         this.textFieldField1.performAction_2959(d, e, i);
         if (this.textFieldField1.isFlagField7_2939()) {
            return true;
         }
      }

      if (this.autoBuyEntryField8 != null && this.autoBuyEntryField8 != this.autoBuyEntryField7) {
         this.performAction_0047(this.autoBuyEntryField8);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean checkCondition_0045(double d, double e, int i, double f, double g) {
      this.textFieldField1.performAction_2941(d, e, i);
      return this.textFieldField1.isFlagField7_2939();
   }

   @Override
   public boolean checkCondition_0046(int i, int j, int k) {
      if (this.textFieldField1.isFlagField7_2939()) {
         this.textFieldField1.performAction_2963(i, j, k);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean checkCondition_0043(char c, int i) {
      if (this.textFieldField1.isFlagField7_2939()) {
         this.textFieldField1.performAction_2958(c, i);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public float getFloatValueField9_0051(Vector4f vector4f) {
      return this.floatValueField9;
   }
}
