package aethereal.ui.element;

import aethereal.config.ThemeInfo;
import aethereal.config.ThemeManager;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent184;
import aethereal.render.RenderComponent187;
import aethereal.render.RenderComponent188;
import aethereal.render.RenderComponent193;
import aethereal.ui.screen.MenuRenderer;
import aethereal.util.CursorUtil;
import aethereal.util.UtilComponent279;
import net.minecraft.client.gui.DrawContext;
import org.joml.Matrix3x2f;
import org.joml.Matrix3x2fStack;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;

public class TextField {
   private final TextField.State384 type;
   private final boolean numbers;
   private Vector2f vector2fField1 = new Vector2f(0.0F, 0.0F);
   private Vector2f vector2fField2 = new Vector2f(0.0F, 0.0F);
   private String stringField3 = "";
   private final StringBuilder stringBuilderField4 = new StringBuilder();
   private Vector2f vector2fField5 = new Vector2f(0.0F, 0.0F);
   private int integerField6;
   private boolean flagField7;
   private boolean flagField8;
   private float floatValueField9;

   public TextField.State384 getType_2955() {
      return this.type;
   }

   public boolean isNumbers_2954() {
      return this.numbers;
   }

   public void setVector2fField1_2966(Vector2f vector2f) {
      this.vector2fField1 = vector2f;
   }

   public Vector2f getVector2fField1_2957() {
      return this.vector2fField1;
   }

   public void setVector2fField2_2944(Vector2f vector2f) {
      this.vector2fField2 = vector2f;
   }

   public Vector2f getVector2fField2_2940() {
      return this.vector2fField2;
   }

   public void setStringField3_2969(String string) {
      this.stringField3 = string;
   }

   public String getStringField3_2946() {
      return this.stringField3;
   }

   public StringBuilder getStringBuilderField4_2956() {
      return this.stringBuilderField4;
   }

   public void setVector2fField5_2949(Vector2f vector2f) {
      this.vector2fField5 = vector2f;
   }

   public Vector2f getVector2fField5_2947() {
      return this.vector2fField5;
   }

   public void setIntegerField6_2968(int i) {
      this.integerField6 = i;
   }

   public int getIntegerField6_2970() {
      return this.integerField6;
   }

   public boolean isFlagField7_2939() {
      return this.flagField7;
   }

   public boolean isFlagField8_2974() {
      return this.flagField8;
   }

   public float getFloatValueField9_2973() {
      return this.floatValueField9;
   }

   public TextField(TextField.State384 state384) {
      this(state384, false);
   }

   public TextField(TextField.State384 state384, boolean bl) {
      this.type = state384;
      this.numbers = bl;
   }

   public void performAction_2964(DrawContext drawContext, double d, double e, float l, float f) {
      Matrix3x2fStack matrix3x2fStack = drawContext.getMatrices();
      RenderComponent184 renderComponent184 = NeneClient.getInstance().getProcessors().getRenderComponent184Field3_0677();
      float g = this.type.font.computeValue_2622(this.type.fontSize);
      float h = this.vector2fField1.x + this.type.paddingX;
      float i = this.vector2fField1.y + (this.vector2fField2.y - g) / 2.0F + this.type.textOffset;
      float j = this.vector2fField2.x - this.type.paddingX * 2.0F;
      this.performAction_2960(j);
      boolean bl = UtilComponent279.checkCondition_3576(d, e, this.vector2fField1.x, this.vector2fField1.y, this.vector2fField2.x, this.vector2fField2.y);
      if (bl != this.flagField8) {
         this.flagField8 = bl;
         CursorUtil.performAction_3422(bl ? CursorUtil.State404.TEXT : CursorUtil.State404.DEFAULT);
      }

      boolean bl1 = !this.flagField7 && this.stringBuilderField4.isEmpty() && this.type.placeholder;
      String string = bl1 ? this.stringField3 : this.stringBuilderField4.toString();
      int k = RenderComponent183.computeValue_2549(
         NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(bl1 ? ThemeInfo.TEXT_DISABLED : ThemeInfo.TEXT).computeValue_0547(), f
      );
      this.type
         .performAction_2976(renderComponent184, matrix3x2fStack, this.vector2fField1.x, this.vector2fField1.y, this.vector2fField2.x, this.vector2fField2.y, f);
      RenderComponent193.performAction_2681(matrix3x2fStack, h, this.vector2fField1.y, j, this.vector2fField2.y);
      this.performAction_2965(drawContext, h, i, g, j, f);
      this.performAction_2943(drawContext, h, i, g, j, f);
      this.type.font.performAction_2630(matrix3x2fStack, string, h - this.floatValueField9, i, this.type.fontSize, k);
      RenderComponent193.performAction_2680(matrix3x2fStack);
   }

   private void performAction_2965(DrawContext drawContext, float f, float g, float h, float i, float j) {
      if (this.flagField7 && this.vector2fField5.x != this.vector2fField5.y) {
         int k = (int)Math.min(this.vector2fField5.x, this.vector2fField5.y);
         int l = (int)Math.max(this.vector2fField5.x, this.vector2fField5.y);
         float m = Math.max(f + this.computeValue_2961(k) - this.floatValueField9, f);
         float n = Math.min(f + this.computeValue_2961(l) - this.floatValueField9, f + i);
         if (m < n) {
            RenderComponent184 renderComponent184 = NeneClient.getInstance().getProcessors().getRenderComponent184Field3_0677();
            renderComponent184.performAction_2574(
               drawContext,
               m,
               g,
               n - m,
               h,
               RenderComponent183.computeValue_2549(
                  NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(), 0.47F * j
               )
            );
         }
      }
   }

   private void performAction_2943(DrawContext drawContext, float f, float g, float h, float i, float j) {
      if (this.flagField7) {
         float k = f + this.computeValue_2961((int)this.vector2fField5.y) - this.floatValueField9;
         if (k >= f && k <= f + i) {
            float l = (float)(Math.sin(System.currentTimeMillis() / 150.0) * 0.5 + 0.5);
            float m = this.type.fontSize / 1.01F;
            float n = g + (h - m) / 2.0F;
            RenderComponent184 renderComponent184 = NeneClient.getInstance().getProcessors().getRenderComponent184Field3_0677();
            renderComponent184.performAction_2574(
               drawContext,
               k,
               n,
               0.5F,
               m,
               RenderComponent183.computeValue_2549(
                  NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.TEXT).computeValue_0547(), l * j
               )
            );
         }
      }
   }

   private float computeValue_2961(int i) {
      return this.type.font.computeValue_2626(this.stringBuilderField4.substring(0, Math.min(i, this.stringBuilderField4.length())), this.type.fontSize) + 0.5F;
   }

   private String computeValue_2971(String string) {
      if (string != null && !string.isEmpty()) {
         for (int i = 0; i < string.length(); i++) {
            if (this.type.font.computeValue_2626(string.substring(0, i), this.type.fontSize) >= this.floatValueField9) {
               return string.substring(i);
            }
         }

         return "";
      } else {
         return string == null ? "" : string;
      }
   }

   private void performAction_2960(float f) {
      float g = this.computeValue_2961((int)this.vector2fField5.y);
      if (g - this.floatValueField9 > f) {
         this.floatValueField9 = g - f;
      } else if (g < this.floatValueField9) {
         this.floatValueField9 = g;
      }

      if (this.type.font.computeValue_2626(this.stringBuilderField4.toString(), this.type.fontSize) < f) {
         this.floatValueField9 = 0.0F;
      }
   }

   public void performAction_2959(double d, double e, int i) {
      if (UtilComponent279.checkCondition_3576(d, e, this.vector2fField1.x, this.vector2fField1.y, this.vector2fField2.x, this.vector2fField2.y)) {
         this.flagField7 = true;
         if (i == 0) {
            int j = this.computeValue_2942((float)d);
            this.integerField6 = j;
            this.vector2fField5 = new Vector2f(j, j);
         }
      } else {
         if (this.flagField7) {
            this.performAction_2950(false);
         }
      }
   }

   public void performAction_2941(double d, double e, int i) {
      if (this.flagField7 && i == 0) {
         int j = this.computeValue_2942((float)d);
         this.vector2fField5 = new Vector2f(Math.min(this.integerField6, j), Math.max(this.integerField6, j));
      }
   }

   private int computeValue_2942(float f) {
      float g = this.vector2fField1.x + this.type.paddingX;
      float h = f + this.floatValueField9;

      for (int i = 0; i <= this.stringBuilderField4.length(); i++) {
         if (g + this.computeValue_2961(i) > h) {
            return i;
         }
      }

      return this.stringBuilderField4.length();
   }

   public void performAction_2963(int i, int k, int j) {
      if (this.flagField7) {
         boolean bl = (j & 2) != 0;
         boolean bl1 = this.vector2fField5.x != this.vector2fField5.y;
         if (bl && i == 65) {
            this.vector2fField5 = new Vector2f(0.0F, this.stringBuilderField4.length());
            return;
         }

         if (bl && i == 67) {
            this.performAction_2951();
            return;
         }

         if (bl && i == 88) {
            this.performAction_2951();
            this.performAction_2953();
            return;
         }

         if (bl && i == 86) {
            this.performAction_2952();
            return;
         }

         if (i == 259) {
            this.performAction_2967(bl1);
            return;
         }

         if (i == 261) {
            this.performAction_2945(bl1);
            return;
         }

         if (i == 263) {
            this.performAction_2962(-1);
            return;
         }

         if (i == 262) {
            this.performAction_2962(1);
         } else if (i == 257 || i == 256) {
            this.performAction_2950(false);
         }
      }
   }

   public void performAction_2958(char c, int i) {
      if (this.flagField7 && (!this.numbers || c >= '0' && c <= '9')) {
         this.performAction_2972(String.valueOf(c));
      }
   }

   private void performAction_2972(String string) {
      if (this.vector2fField5.x != this.vector2fField5.y) {
         this.performAction_2953();
      }

      int i = (int)this.vector2fField5.y;
      this.stringBuilderField4.insert(i, string);
      this.vector2fField5 = new Vector2f(i + string.length(), i + string.length());
      this.integerField6 = (int)this.vector2fField5.y;
   }

   private void performAction_2951() {
      int i = (int)Math.min(this.vector2fField5.x, this.vector2fField5.y);
      int j = (int)Math.max(this.vector2fField5.x, this.vector2fField5.y);
      if (i < j) {
         GLFW.glfwSetClipboardString(MinecraftAccess.minecraft.getWindow().getHandle(), this.stringBuilderField4.substring(i, j));
      }
   }

   private void performAction_2952() {
      String string = GLFW.glfwGetClipboardString(MinecraftAccess.minecraft.getWindow().getHandle());
      if (string != null && !string.isEmpty()) {
         this.performAction_2972(this.numbers ? string.replaceAll("[^0-9]", "") : string);
      }
   }

   private void performAction_2967(boolean bl) {
      if (bl) {
         this.performAction_2953();
      } else if (this.vector2fField5.y > 0.0F) {
         int i = (int)this.vector2fField5.y;
         this.stringBuilderField4.deleteCharAt(i - 1);
         this.vector2fField5 = new Vector2f(i - 1, i - 1);
         this.integerField6 = i - 1;
      }
   }

   private void performAction_2945(boolean bl) {
      if (bl) {
         this.performAction_2953();
      } else if (this.vector2fField5.y < this.stringBuilderField4.length()) {
         this.stringBuilderField4.deleteCharAt((int)this.vector2fField5.y);
      }
   }

   private void performAction_2962(int i) {
      int j = (int)this.vector2fField5.y + i;
      if (j >= 0 && j <= this.stringBuilderField4.length()) {
         this.vector2fField5 = new Vector2f(j, j);
         this.integerField6 = j;
      }
   }

   private void performAction_2953() {
      int i = (int)Math.min(this.vector2fField5.x, this.vector2fField5.y);
      int j = (int)Math.max(this.vector2fField5.x, this.vector2fField5.y);
      this.stringBuilderField4.delete(i, j);
      this.vector2fField5 = new Vector2f(i, i);
      this.integerField6 = i;
   }

   public void performAction_2948() {
      this.stringBuilderField4.setLength(0);
      this.vector2fField5 = new Vector2f(0.0F, 0.0F);
      this.integerField6 = 0;
      this.flagField7 = false;
      if (this.flagField8) {
         this.flagField8 = false;
         CursorUtil.performAction_3422(CursorUtil.State404.DEFAULT);
      }
   }

   public void performAction_2950(boolean bl) {
      this.flagField7 = bl;
      this.vector2fField5 = new Vector2f(bl ? this.stringBuilderField4.length() : 0.0F, bl ? this.stringBuilderField4.length() : 0.0F);
      this.integerField6 = (int)this.vector2fField5.y;
      if (!bl && this.flagField8) {
         this.flagField8 = false;
         CursorUtil.performAction_3422(CursorUtil.State404.DEFAULT);
      }
   }

   public enum State384 {
      ALT_MANAGER(RenderComponent188.renderComponent187Field2, 7.0F, 6.0F, -0.5F, true) {
         @Override
         public void performAction_2976(RenderComponent184 renderComponent184, Matrix3x2f matrix3x2f, float f, float g, float h, float i, float j) {
            renderComponent184.performAction_2583(
               matrix3x2f,
               f,
               g,
               h + 2.0F,
               i,
               new Vector4f(5.0F, 1.0F, 5.0F, 1.0F),
               RenderComponent183.computeValue_2549(16777215, MenuRenderer.glassAlpha(0.6F) * j)
            );
         }
      },
      GUI(RenderComponent188.renderComponent187Field3, 7.0F, 6.0F, 0.0F, true) {
         @Override
         public void performAction_2976(RenderComponent184 renderComponent184, Matrix3x2f matrix3x2f, float f, float g, float h, float i, float j) {
            ThemeManager themeManager = NeneClient.getInstance().getProcessors().getThemes();
            int k = RenderComponent183.computeValue_2549(
               RenderComponent183.computeValue_2550(
                  themeManager.computeValue_0557(ThemeInfo.BACKGROUND_GUI).computeValue_0547(),
                  themeManager.computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(),
                  0.05F
               ),
               0.78431374F * j
            );
            renderComponent184.performAction_2580(matrix3x2f, f, g, h, i, 6.0F, k, j, k, 2.0F);
            renderComponent184.performAction_2576(
               matrix3x2f,
               f,
               g,
               h,
               i,
               6.0F,
               0.5F,
               RenderComponent183.computeValue_2549(
                  themeManager.computeValue_0557(ThemeInfo.OUTLINE_MEDIUM).computeValue_0547(),
                  themeManager.computeValue_0557(ThemeInfo.OUTLINE_MEDIUM).computeValue_0548() * j
               )
            );
         }
      },
      GUI_SETTING(RenderComponent188.renderComponent187Field3, 6.5F, 4.0F, 0.0F, true) {
         @Override
         public void performAction_2976(RenderComponent184 renderComponent184, Matrix3x2f matrix3x2f, float f, float g, float h, float i, float j) {
            ThemeManager themeManager = NeneClient.getInstance().getProcessors().getThemes();
            renderComponent184.performAction_2577(
               matrix3x2f,
               f,
               g,
               h,
               i,
               2.0F,
               RenderComponent183.computeValue_2549(themeManager.computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(), 0.011764706F * j)
            );
            renderComponent184.performAction_2576(
               matrix3x2f,
               f,
               g,
               h,
               i,
               2.0F,
               0.5F,
               RenderComponent183.computeValue_2549(
                  themeManager.computeValue_0557(ThemeInfo.OUTLINE_SMALL).computeValue_0547(),
                  themeManager.computeValue_0557(ThemeInfo.OUTLINE_SMALL).computeValue_0548() * j
               )
            );
         }
      };

      final RenderComponent187 font;
      final float fontSize;
      final float paddingX;
      final float textOffset;
      final boolean placeholder;

      public abstract void performAction_2976(RenderComponent184 renderComponent184, Matrix3x2f matrix3x2f, float f, float g, float h, float i, float j);

      State384(final RenderComponent187 renderComponent187, final float f, final float g, final float h, final boolean bl) {
         this.font = renderComponent187;
         this.fontSize = f;
         this.paddingX = g;
         this.textOffset = h;
         this.placeholder = bl;
      }

      public RenderComponent187 getFont_2975() {
         return this.font;
      }

      public float getFontSize_2977() {
         return this.fontSize;
      }

      public float getPaddingX_2978() {
         return this.paddingX;
      }

      public float getTextOffset_2979() {
         return this.textOffset;
      }

      public boolean isPlaceholder_2980() {
         return this.placeholder;
      }
   }
}
