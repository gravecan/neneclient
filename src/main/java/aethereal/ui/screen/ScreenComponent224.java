package aethereal.ui.screen;

import aethereal.api.ApiAnnotation001;
import aethereal.autobuy.AutobuyComponent007;
import aethereal.autobuy.AutobuyComponent010;
import aethereal.config.ThemeInfo;
import aethereal.config.ThemeManager;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.core.ObfuscatorRuntime;
import aethereal.render.Animation;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent184;
import aethereal.render.RenderComponent186;
import aethereal.render.RenderComponent188;
import aethereal.ui.element.ElementComponent211;
import aethereal.util.UtilComponent279;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Matrix3x2fStack;
import org.joml.Vector4f;

public class ScreenComponent224 extends Screen {
   private final List<ElementComponent211> listField1 = new ArrayList<>();
   private final Vector4f vector4fField2 = new Vector4f(0.0F, 0.0F, 425.0F, 235.0F);
   private final Vector4f vector4fField3 = new Vector4f(0.0F, 0.0F, 0.0F, 16.0F);
   private final Vector4f vector4fField4 = new Vector4f(0.0F, 0.0F, 0.0F, 0.0F);
   private final Animation animationField5 = new Animation();
   private final Animation animationField6 = new Animation();
   private int selected;
   private float floatValueField7;

   @ApiAnnotation001
   public void render(DrawContext drawContext, int i, int j, float f) {
      super.render(drawContext, i, j, f);
      Vector4f vector4f = this.vector4fField2;
      MinecraftClient minecraftClient = MinecraftAccess.minecraft;
      vector4f.x = (minecraftClient.getWindow().getScaledWidth() - this.vector4fField2.z) * 0.5F;
      this.vector4fField2.y = (minecraftClient.getWindow().getScaledHeight() - this.vector4fField2.w) * 0.5F;
      this.animationField6.performAction_2535(0.0F, 1.0F, 0.3F, RenderComponent186.contract372Field7, f);
      this.animationField6.performAction_2528(true);
      Vector4f vector4f1 = this.vector4fField2;
      float g = vector4f1.x;
      float h = 0.5F * vector4f1.z;
      float k = vector4f1.y;
      float l = 0.5F * vector4f1.w;
      float m = 0.85F + RenderComponent186.contract372Field19.ease(this.animationField6.getFloatValueField4_2530()) * 0.15F;
      Matrix3x2fStack matrix3x2fStack = drawContext.getMatrices();
      matrix3x2fStack.pushMatrix();
      float n = h + g;
      float o = l + k;
      matrix3x2fStack.translate(n, (1.0F - RenderComponent186.contract372Field16.ease(this.animationField6.getFloatValueField4_2530())) * 14.0F + o);
      matrix3x2fStack.scale(m, m);
      matrix3x2fStack.translate(-n, -o);
      RenderComponent184 renderComponent184 = NeneClient.getInstance().getProcessors().getRenderComponent184Field3_0677();
      ThemeManager themeManager = NeneClient.getInstance().getProcessors().getThemes();
      int p = themeManager.computeValue_0557(ThemeInfo.BACKGROUND_GUI).computeValue_0547();
      ThemeInfo themeInfo = ThemeInfo.PRIMARY;
      int q = RenderComponent183.computeValue_2556(
         RenderComponent183.computeValue_2550(
            p, themeManager.computeValue_0557(themeInfo).computeValue_0547(), themeManager.computeValue_0557(themeInfo).computeValue_0548() * 0.25F
         ),
         220
      );
      renderComponent184.performAction_2580(drawContext.getMatrices(), vector4f1.x, vector4f1.y, vector4f1.z, vector4f1.w, 8.0F, q, 1.0F, q, 16.0F);
      renderComponent184.performAction_2576(
         drawContext.getMatrices(),
         vector4f1.x,
         vector4f1.y,
         vector4f1.z,
         vector4f1.w,
         8.0F,
         0.5F,
         themeManager.computeValue_0557(ThemeInfo.OUTLINE_MEDIUM).computeValue_0547()
      );
      this.performAction_3246(drawContext, f, q);
      this.vector4fField4
         .set(
            vector4f1.x,
            this.vector4fField3.y + this.vector4fField3.w + 8.0F,
            vector4f1.z,
            vector4f1.y + vector4f1.w - 8.0F - (this.vector4fField3.y + this.vector4fField3.w + 12.0F)
         );
      this.computeValue_3245().performAction_0050(drawContext, this.vector4fField4, i, j, this.animationField5.getFloatValueField1_2527(), f);
      RenderComponent184 renderComponent1841 = NeneClient.getInstance().getProcessors().getRenderComponent184Field3_0677();
      float r = this.floatValueField7;
      renderComponent1841.performAction_2574(
         drawContext,
         r - 3.0F,
         this.vector4fField3.y + this.vector4fField3.w,
         6.0F,
         0.5F,
         NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547()
      );
      matrix3x2fStack.popMatrix();
      this.animationField5
         .computeValue_2534(Math.min(0.0F, this.vector4fField4.w - this.computeValue_3245().getFloatValueField9_0051(this.vector4fField4)), 0.0F, 1.0F);
   }

   @ApiAnnotation001
   public boolean mouseScrolled(double d, double e, double g, double f) {
      if (this.computeValue_3245().checkCondition_3036(d, e, f)) {
         return true;
      }

      this.animationField5.performAction_2532((float)f * 15.0F);
      return true;
   }

   @ApiAnnotation001
   public boolean mouseClicked(Click click, boolean bl) {
      double d = click.x();
      double e = click.y();
      int i = click.button();
      Vector4f vector4f = this.vector4fField3;
      List list = this.listField1;
      Vector4f vector4f1 = this.vector4fField4;
      Animation animation = this.animationField5;
      float f = vector4f.x + 12.0F;

      for (int j = 0; list.size() > j; j++) {
         float g = RenderComponent188.renderComponent187Field1.computeValue_2618(((ElementComponent211)list.get(j)).getIcon_3033(), 8.5F);
         if (UtilComponent279.checkCondition_3576(d, e, f - 6.0F, vector4f.y, g + 12.0F, vector4f.w)) {
            this.selected = j;
            animation.performAction_2524(0.0F);
            return true;
         }

         f += g + 12.0F;
      }

      return !(vector4f1.y > e) && this.computeValue_3245().checkCondition_0044(d, e, i) ? true : super.mouseClicked(click, bl);
   }

   @ApiAnnotation001
   public boolean mouseReleased(Click click) {
      double d = click.x();
      double e = click.y();
      int i = click.button();
      return this.computeValue_3245().checkCondition_3032(d, e, i) ? true : super.mouseReleased(click);
   }

   @ApiAnnotation001
   public boolean mouseDragged(Click click, double d, double e) {
      double f = click.x();
      double g = click.y();
      int i = click.button();
      return this.computeValue_3245().checkCondition_0045(f, g, i, d, e) ? true : super.mouseDragged(click, d, e);
   }

   @ApiAnnotation001
   public boolean charTyped(CharInput charInput) {
      char c = charInput.isValidChar() ? charInput.asString().charAt(0) : '\u0000';
      int i = charInput.modifiers();
      return this.computeValue_3245().checkCondition_0043(c, i) ? true : super.charTyped(charInput);
   }

   @ApiAnnotation001
   public boolean keyPressed(KeyInput keyInput) {
      int i = keyInput.key();
      int j = keyInput.scancode();
      int k = keyInput.comp_4797();
      ElementComponent211 elementComponent211 = this.computeValue_3245();
      if (elementComponent211 == null) {
         throw new NullPointerException();
      } else {
         return elementComponent211.checkCondition_0046(i, j, k) ? true : super.keyPressed(keyInput);
      }
   }

   public Animation getAnimationField5_3244() {
      return this.animationField5;
   }

   public ScreenComponent224(Text text) {
      this(text, 0);
   }

   public ScreenComponent224(Text text, int i) {
      super(text);
      this.listField1.add(new AutobuyComponent010());
      this.listField1.add(new AutobuyComponent007());
      this.selected = i;
   }

   public void close() {
      super.close();
      this.animationField6.setFloatValueField1_2526(0.0F);
   }

   public void renderBackground(DrawContext drawContext, int i, int j, float f) {
   }

   private ElementComponent211 computeValue_3245() {
      return this.listField1.get(this.selected);
   }

   private void performAction_3246(DrawContext drawContext, float f, int p) {
      RenderComponent184 renderComponent184 = NeneClient.getInstance().getProcessors().getRenderComponent184Field3_0677();
      ThemeManager themeManager = NeneClient.getInstance().getProcessors().getThemes();
      float g = 12.0F * (this.listField1.size() - 1);
      Iterator iterator = this.listField1.iterator();

      while (iterator.hasNext()) {
         g += RenderComponent188.renderComponent187Field1.computeValue_2618(((ElementComponent211)iterator.next()).getIcon_3033(), 8.5F);
      }

      this.vector4fField3.z = g + 24.0F;
      this.vector4fField3.x = this.vector4fField2.x + (this.vector4fField2.z - this.vector4fField3.z) / 2.0F;
      this.vector4fField3.y = this.vector4fField2.y + 8.0F;
      renderComponent184.performAction_2576(
         drawContext.getMatrices(),
         this.vector4fField3.x,
         this.vector4fField3.y,
         this.vector4fField3.z,
         this.vector4fField3.w,
         6.0F,
         0.5F,
         RenderComponent183.computeValue_2558(255, 255, 255, 4)
      );
      RenderComponent188.renderComponent187Field1
         .performAction_2630(
            drawContext.getMatrices(),
            "a",
            this.vector4fField2.x + 8.0F,
            this.vector4fField3.y + (this.vector4fField3.w - RenderComponent188.renderComponent187Field1.computeValue_2622(12.0F)) / 2.0F,
            12.0F,
            RenderComponent183.computeValue_2549(themeManager.computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(), 0.75F)
         );
      float h = this.vector4fField2.x + 8.0F + RenderComponent188.renderComponent187Field1.computeValue_2626("a", 12.0F) + 8.0F;
      renderComponent184.performAction_2577(
         drawContext.getMatrices(),
         h,
         this.vector4fField3.y + (this.vector4fField3.w - 8.0F) / 2.0F,
         0.75F,
         8.0F,
         0.0F,
         RenderComponent183.computeValue_2558(255, 255, 255, 25)
      );
      RenderComponent188.renderComponent187Field3
         .performAction_2630(
            drawContext.getMatrices(),
            "By Ded",
            h + 8.0F,
            this.vector4fField3.y + (this.vector4fField3.w - RenderComponent188.renderComponent187Field3.computeValue_2622(6.75F)) / 2.0F - 0.5F,
            6.75F,
            themeManager.computeValue_0557(ThemeInfo.TEXT_DISABLED).computeValue_0547()
         );
      float i = this.vector4fField2.x + this.vector4fField2.z - 12.0F - 8.0F;
      renderComponent184.performAction_2585(
         drawContext.getMatrices(),
         Identifier.of("neneclient", "icon.png"),
         i,
         this.vector4fField3.y + (this.vector4fField3.w - 12.0F) / 2.0F,
         12.0F,
         12.0F,
         5.0F,
         -1
      );
      String string = this.computeValue_3245().getName_3034();
      float j = i - 8.0F;
      renderComponent184.performAction_2577(
         drawContext.getMatrices(),
         j,
         this.vector4fField3.y + (this.vector4fField3.w - 8.0F) / 2.0F,
         0.75F,
         8.0F,
         0.0F,
         RenderComponent183.computeValue_2558(255, 255, 255, 25)
      );
      RenderComponent188.renderComponent187Field3
         .performAction_2630(
            drawContext.getMatrices(),
            string,
            j - 8.0F - RenderComponent188.renderComponent187Field3.computeValue_2626(string, 6.75F),
            this.vector4fField3.y + (this.vector4fField3.w - RenderComponent188.renderComponent187Field3.computeValue_2622(6.75F)) / 2.0F - 0.5F,
            6.75F,
            themeManager.computeValue_0557(ThemeInfo.TEXT_DISABLED).computeValue_0547()
         );
      float k = this.vector4fField3.x + 12.0F;
      float l = this.vector4fField3.y + (this.vector4fField3.w - RenderComponent188.renderComponent187Field1.computeValue_2622(8.5F)) / 2.0F;
      float m = this.floatValueField7;

      for (int n = 0; n < this.listField1.size(); n++) {
         ElementComponent211 elementComponent211 = this.listField1.get(n);
         float o = RenderComponent188.renderComponent187Field1.computeValue_2618(elementComponent211.getIcon_3033(), 8.5F);
         elementComponent211.getAnimationField1_3035().performAction_2528(n == this.selected);
         elementComponent211.getAnimationField1_3035().performAction_2535(0.0F, 1.0F, 0.3F, RenderComponent186.contract372Field9, f);
         RenderComponent188.renderComponent187Field1
            .performAction_2630(
               drawContext.getMatrices(),
               elementComponent211.getIcon_3033(),
               k,
               l,
               8.5F,
               RenderComponent183.computeValue_2550(
                  themeManager.computeValue_0557(ThemeInfo.TEXT_DISABLED).computeValue_0547(),
                  RenderComponent183.computeValue_2549(themeManager.computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(), 1.0F),
                  elementComponent211.getAnimationField1_3035().getFloatValueField4_2530()
               )
            );
         if (n == this.selected) {
            m = k + o / 2.0F;
         }

         k += o + 12.0F;
      }

      this.floatValueField7 = this.floatValueField7 == 0.0F ? m : UtilComponent279.computeValue_3586(this.floatValueField7, m, 1.25F);
   }

   static {
      ObfuscatorRuntime.registerClass(ScreenComponent224.class, 17);
   }
}
