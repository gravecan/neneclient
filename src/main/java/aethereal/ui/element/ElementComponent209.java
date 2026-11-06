package aethereal.ui.element;

import aethereal.core.NeneClient;
import aethereal.render.Animation;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent184;
import aethereal.render.RenderComponent186;
import aethereal.render.RenderComponent188;
import aethereal.util.UtilComponent279;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.joml.Matrix3x2fStack;

public class ElementComponent209 {
   private final Animation animationField1 = new Animation();
   private final float width;
   private final float height;
   private final String label;
   private final Runnable action;
   private float floatValueField2;
   private float floatValueField3;

   public Animation getAnimationField1_2997() {
      return this.animationField1;
   }

   public float getWidth_2995() {
      return this.width;
   }

   public float getHeight_2996() {
      return this.height;
   }

   public String getLabel_3001() {
      return this.label;
   }

   public Runnable getAction_2998() {
      return this.action;
   }

   public float getFloatValueField2_2994() {
      return this.floatValueField2;
   }

   public float getFloatValueField3_2993() {
      return this.floatValueField3;
   }

   public ElementComponent209(float f, float g, String string, Runnable runnable) {
      this.width = f;
      this.height = g;
      this.label = string;
      this.action = runnable;
   }

   public void performAction_2999(float f, float g) {
      this.floatValueField2 = f;
      this.floatValueField3 = g;
   }

   public void performAction_3000(DrawContext drawContext, int i, int j, float f, float g) {
      this.animationField1
         .performAction_2528(
            this.action != null && UtilComponent279.checkCondition_3576(i, j, this.floatValueField2, this.floatValueField3, this.width, this.height)
         );
      this.animationField1.performAction_2535(0.0F, 1.0F, 0.35F, RenderComponent186.contract372Field9, f);
      float h = Math.min(1.0F, this.animationField1.getFloatValueField4_2530() / 0.9F);
      float k = (0.85F + 0.15F * RenderComponent186.contract372Field19.ease(g)) * (1.0F + 0.03F * h);
      Matrix3x2fStack matrix3x2fStack = drawContext.getMatrices();
      float l = this.floatValueField2 + this.width / 2.0F;
      float m = this.floatValueField3 + this.height / 2.0F;
      matrix3x2fStack.pushMatrix();
      matrix3x2fStack.translate(l, m);
      matrix3x2fStack.scale(k, k);
      matrix3x2fStack.translate(-l, -m);
      RenderComponent184 renderComponent184 = NeneClient.getInstance().getProcessors().getRenderComponent184Field3_0677();
      renderComponent184.performAction_2562(
         matrix3x2fStack, this.floatValueField2, this.floatValueField3, this.width, this.height, 8.0F, RenderComponent183.computeValue_2558(11, 11, 13, 120), g
      );
      renderComponent184.performAction_2576(
         matrix3x2fStack,
         this.floatValueField2,
         this.floatValueField3,
         this.width,
         this.height,
         8.0F,
         0.5F,
         RenderComponent183.computeValue_2558(255, 255, 255, (int)(h * 20.0F * g))
      );
      if (this.label != null) {
         float n = (float)(System.currentTimeMillis() % 3000L) / 3000.0F;
         MutableText mutableText = Text.literal("");

         for (int o = 0; o < this.label.length(); o++) {
            float p = (float)(Math.sin((n + o * 0.5F / this.label.length()) * 3.141592654293742 * 2.0) * 0.5 + 0.5);
            int q = (int)(180.0F + 65.0F * p * h);
            mutableText.append(Text.literal(String.valueOf(this.label.charAt(o))).setStyle(Style.EMPTY.withColor(q << 16 | q << 8 | q)));
         }

         float r = RenderComponent188.renderComponent187Field5.computeValue_2626(this.label, 8.0F);
         RenderComponent188.renderComponent187Field5
            .performAction_2635(
               matrix3x2fStack,
               mutableText,
               this.floatValueField2 + (this.width - r) / 2.0F,
               this.floatValueField3 + (this.height - 9.0F) / 2.0F,
               8.0F,
               0.0F,
               g
            );
      }

      matrix3x2fStack.popMatrix();
   }
}
