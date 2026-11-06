package aethereal.ui.screen;

import aethereal.api.ApiAnnotation001;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.core.ObfuscatorRuntime;
import aethereal.network.ProxyEntry;
import aethereal.render.Animation;
import aethereal.render.RenderComponent184;
import aethereal.render.RenderComponent188;
import aethereal.render.RenderComponent192;
import aethereal.render.RenderComponent193;
import aethereal.render.pipeline.PipelineComponent196;
import aethereal.ui.element.TextField;
import aethereal.ui.shader.ShaderComponent231;
import aethereal.util.UtilComponent279;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.session.Session;
import net.minecraft.text.Text;
import org.joml.Matrix3x2fStack;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import platform.inject.accessors.MinecraftClientAccessor;

public class ScreenComponent212 extends Screen {
   private static final float floatValueField1 = 240.0F;
   private static final float HEAD = 44.0F;
   private static final float ROW = 28.0F;
   private static final float STEP = 32.0F;
   private final Animation scroll = new Animation();
   private final TextField field;
   private final List<ScreenComponent212.NestedType385> rows = new ArrayList<>();
   private final List<ScreenComponent212.NestedType385> visualRows = new ArrayList<>();
   private boolean visualOrderDirty = true;
   ScreenComponent212.NestedType385 hovered;
   private ScreenComponent212.NestedType385 pressed;
   private ProxyEntry lastSelected;
   private float grabOffset;
   private boolean dragging;
   private boolean closing;
   private boolean finished;
   private float openT;
   private float delArm;
   private float addReady;
   private float shownCount = -1.0F;
   private float floatValueField2;
   private float floatValueField3;
   private float floatValueField4;
   private float floatValueField5;
   private final float[] hov = new float[4];
   private float addX;
   private float addY;
   private float addW;
   private float addH;
   private float rndX;
   private float rndW;
   private float delX;
   private float delY;
   private float delW;
   private float delH;
   private float clsX;
   private float clsY;

   @ApiAnnotation001
   public void render(DrawContext drawContext, int i, int j, float f) {
      super.render(drawContext, i, j, f);
      MenuRenderer.updateFrameTime();
      this.openT = MenuRenderer.approach(this.openT, this.closing ? 0.0F : 1.0F, 8.0F);
      if (this.closing && this.openT <= 0.02F) {
         this.performAction_3044();
      } else {
         float g = MenuRenderer.easeOutCubic(this.openT);
         double d = UtilComponent279.computeValue_3580(i, 2);
         double e = UtilComponent279.computeValue_3580(j, 2);
         RenderComponent192.performAction_2676(drawContext, 2);
         int k = MinecraftAccess.minecraft.getWindow().getScaledWidth();
         int l = MinecraftAccess.minecraft.getWindow().getScaledHeight();
         MainMenuScreen.performAction_3174(drawContext, k, l, (int)d, (int)e, g * 0.2F + 1.05F);
         Matrix3x2fStack matrix3x2fStack = drawContext.getMatrices();
         MenuRenderer.fillRoundedRect(matrix3x2fStack, 0.0F, 0.0F, k, l, 0.0F, MenuRenderer.withAlpha(328714, 0.45F * g));
         this.floatValueField4 = Math.min(240.0F, k - 16.0F);
         this.floatValueField5 = Math.min(300.0F, l - 24.0F);
         this.floatValueField2 = (k - this.floatValueField4) / 2.0F;
         this.floatValueField3 = (l - this.floatValueField5) / 2.0F;
         if (this.delArm > 0.0F) {
            this.delArm = Math.max(0.0F, this.delArm - MenuRenderer.floatValueField1);
         }

         float h = g * 0.15F + 0.85F;
         matrix3x2fStack.pushMatrix();
         matrix3x2fStack.translate(k * 0.5F, l * 0.5F);
         matrix3x2fStack.scale(h, h);
         matrix3x2fStack.translate(-k * 0.5F, -l * 0.5F);
         this.performAction_3059(matrix3x2fStack, (int)d, (int)e, g);
         this.performAction_3060(matrix3x2fStack, k, l, h, (int)d, (int)e, g);
         this.performAction_3057(drawContext, matrix3x2fStack, (int)d, (int)e, f, g);
         matrix3x2fStack.popMatrix();
         RenderComponent192.performAction_2675(drawContext);
         PipelineComponent196.performAction_2728();
      }
   }

   private void performAction_3059(Matrix3x2fStack matrix3x2fStack, int i, int j, float f) {
      int k = MenuRenderer.getAccent();
      float g = MenuRenderer.time;
      MenuRenderer.fillRoundedRect(
         matrix3x2fStack,
         this.floatValueField2 + 1.0F,
         this.floatValueField3 + 2.0F,
         this.floatValueField4,
         this.floatValueField5,
         10.0F,
         MenuRenderer.withAlpha(0, 0.35F * f)
      );
      MenuRenderer.fillRoundedRect(
         matrix3x2fStack,
         this.floatValueField2,
         this.floatValueField3,
         this.floatValueField4,
         this.floatValueField5,
         10.0F,
         MenuRenderer.withAlpha(MenuRenderer.PANEL, MenuRenderer.glassAlpha(0.9F) * f)
      );
      MenuRenderer.strokeRoundedRect(
         matrix3x2fStack,
         this.floatValueField2,
         this.floatValueField3,
         this.floatValueField4,
         this.floatValueField5,
         10.0F,
         0.8F,
         MenuRenderer.replaceAlpha(k, (0.3F + 0.12F * (float)Math.sin(g * 1.5F)) * f)
      );
      MenuRenderer.fillRoundedRect(
         matrix3x2fStack,
         this.floatValueField2,
         this.floatValueField3,
         this.floatValueField4,
         44.0F,
         10.0F,
         MenuRenderer.withAlpha(1577504, MenuRenderer.glassAlpha(0.76F) * f)
      );
      float h = Math.min(this.floatValueField4, this.floatValueField5) * 0.62F;
      MenuRenderer.drawTexture(
         matrix3x2fStack,
         MenuRenderer.LOGO,
         this.floatValueField2 + (this.floatValueField4 - h) / 2.0F,
         this.floatValueField3 + 44.0F + (this.floatValueField5 - 44.0F - h) / 2.0F + (float)Math.sin(g * 0.8F) * 3.0F,
         h,
         h,
         MenuRenderer.withAlpha(16777215, 0.035F * f)
      );
      MenuRenderer.fillRoundedRect(
         matrix3x2fStack,
         this.floatValueField2 + 1.0F,
         this.floatValueField3 + 44.0F,
         this.floatValueField4 - 2.0F,
         1.0F,
         0.0F,
         MenuRenderer.withAlpha(16777215, 0.06F * f)
      );
      MenuRenderer.fillRoundedRect(
         matrix3x2fStack,
         this.floatValueField2 + g * 0.22F % 1.0F * (this.floatValueField4 - 70.0F),
         this.floatValueField3 + 44.0F,
         70.0F,
         1.0F,
         0.0F,
         MenuRenderer.replaceAlpha(k, 0.55F * f)
      );
      float l = 22.0F;
      float m = this.floatValueField2 + 14.0F;
      float n = this.floatValueField3 + (44.0F - l) / 2.0F;
      float o = 0.5F + 0.5F * (float)Math.sin(g * 2.0F);
      MenuRenderer.fillCircle(matrix3x2fStack, m + l / 2.0F, n + l / 2.0F, 16.0F + o * 2.0F, MenuRenderer.replaceAlpha(k, (0.08F + 0.06F * o) * f));
      MenuRenderer.drawTexture(matrix3x2fStack, MenuRenderer.LOGO, m, n, l, l, MenuRenderer.withAlpha(16777215, f));
      RenderComponent188.renderComponent187Field5
         .performAction_2635(
            matrix3x2fStack,
            ShaderComponent231.computeValue_3295("ACCOUNTS", MenuRenderer.replaceAlpha(k, f), 5.0F, 0.5F),
            this.floatValueField2 + 46.0F,
            this.floatValueField3 + 9.0F,
            11.0F,
            0.0F,
            f
         );
      ProxyEntry proxyEntry = NeneClient.getInstance().getProcessors().getProxyManagerField2_0675().getSelected_2367();
      int p = this.computeValue_3062().size();
      this.shownCount = this.shownCount < 0.0F ? p : MenuRenderer.approach(this.shownCount, p, 12.0F);
      String string = (proxyEntry != null ? proxyEntry.getName_2359().toUpperCase() : "NOT SELECTED") + "  |  " + Math.round(this.shownCount) + " ACCOUNTS";
      MenuRenderer.drawText(
         matrix3x2fStack,
         MenuRenderer.ellipsize(string, 5.5F, this.floatValueField4 - 46.0F - 40.0F),
         this.floatValueField2 + 46.0F,
         this.floatValueField3 + 31.0F,
         5.5F,
         MenuRenderer.withAlpha(16777215, 0.45F * f)
      );
      this.clsX = this.floatValueField2 + this.floatValueField4 - 28.0F;
      this.clsY = this.floatValueField3 + 13.0F;
      this.hov[3] = MenuRenderer.approach(
         this.hov[3], UtilComponent279.checkCondition_3576(i, j, this.clsX, this.clsY, 18.0F, 18.0F) ? 1.0F : 0.0F, 14.0F
      );
      MenuRenderer.fillRoundedRect(
         matrix3x2fStack, this.clsX, this.clsY, 18.0F, 18.0F, 6.0F, MenuRenderer.replaceAlpha(k, (0.06F + 0.26F * this.hov[3]) * f)
      );
      MenuRenderer.drawCenteredText(
         matrix3x2fStack,
         "x",
         this.clsX + 9.0F,
         this.clsY + 9.0F,
         7.0F,
         MenuRenderer.withAlpha(MenuRenderer.blendRgb(9077648, 16777215, this.hov[3]), f)
      );
   }

   private void performAction_3060(Matrix3x2fStack matrix3x2fStack, int i, int j, float f, int k, int l, float g) {
      this.performAction_3045();
      RenderComponent184 renderComponent184 = MenuRenderer.getRenderer();
      ProxyEntry proxyEntry = NeneClient.getInstance().getProcessors().getProxyManagerField2_0675().getSelected_2367();
      int m = MenuRenderer.getAccent();
      float h = this.floatValueField3 + 44.0F + 8.0F;
      float n = this.floatValueField3 + this.floatValueField5 - 64.0F;
      float o = n - h;
      float p = this.floatValueField2 + 10.0F;
      float q = this.floatValueField4 - 20.0F - 6.0F;
      float r = Math.min(0.0F, o - this.rows.size() * 32.0F);
      float s = this.scroll.computeValue_2534(r, 0.0F, 0.5F);
      boolean bl = this.pressed != null && this.dragging;
      float t = h + s;
      float u = l - this.grabOffset - t;
      if (this.visualOrderDirty) {
         this.visualRows.clear();
         this.visualRows.addAll(this.rows);
         this.visualRows
            .sort((nestedType3851, nestedType3852) -> Boolean.compare(!nestedType3851.acc.isFavorited_2357(), !nestedType3852.acc.isFavorited_2357()));
         this.visualOrderDirty = false;
      }

      List list = this.visualRows;
      if (bl) {
         this.performAction_3055(list, u);
      }

      this.hovered = null;
      float v = -1.0F;
      int w = 0;
      RenderComponent193.performAction_2681(
         matrix3x2fStack, i / 2.0F + (this.floatValueField2 - i / 2.0F) * f, j / 2.0F + (h - 2.0F - j / 2.0F) * f, this.floatValueField4 * f, (o + 1.0F) * f
      );

      for (ScreenComponent212.NestedType385 nestedType385 : list) {
         float x = nestedType385.removing ? nestedType385.slot : w * 32.0F;
         if (nestedType385 != this.pressed || !bl) {
            nestedType385.performAction_3064(matrix3x2fStack, renderComponent184, m, p, q, t, x, h, n, k, l, g, false);
         }

         if (nestedType385.acc == proxyEntry) {
            v = x;
         }

         if (!nestedType385.removing) {
            w++;
         }
      }

      if (bl) {
         this.pressed.slot = u;
         this.pressed.performAction_3064(matrix3x2fStack, renderComponent184, m, p, q, t, u, h, n, k, l, g, true);
      }

      RenderComponent193.performAction_2680(matrix3x2fStack);
      if (this.rows.removeIf(ScreenComponent212.NestedType385::checkCondition_3063)) {
         this.visualOrderDirty = true;
      }

      if (this.rows.isEmpty()) {
         float y = (float)Math.sin(MenuRenderer.time * 2.0F) * 2.0F;
         float bb = h + o / 2.0F + y;
         MenuRenderer.drawCenteredText(
            matrix3x2fStack,
            "NO ACCOUNTS YET",
            this.floatValueField2 + this.floatValueField4 / 2.0F,
            bb,
            8.0F,
            MenuRenderer.withAlpha(16777215, 0.45F * g)
         );
         MenuRenderer.drawCenteredText(
            matrix3x2fStack,
            "ADD ONE BELOW",
            this.floatValueField2 + this.floatValueField4 / 2.0F,
            bb + 12.0F,
            5.5F,
            MenuRenderer.replaceAlpha(m, 0.7F * g)
         );
      }

      if (proxyEntry != this.lastSelected) {
         this.lastSelected = proxyEntry;
         if (v >= 0.0F) {
            float z = v + s;
            float bc = z < 0.0F ? -v : (z + 32.0F > o ? o - 32.0F - v : s);
            this.scroll.performAction_2532(UtilComponent279.computeValue_3577(bc, r, 0.0F) - s);
         }
      }

      float ba = Math.max(o, this.rows.size() * 32.0F);
      float bd = o * o / ba;
      MenuRenderer.fillRoundedRect(
         matrix3x2fStack, this.floatValueField2 + this.floatValueField4 - 9.0F, h, 2.0F, o, 1.0F, MenuRenderer.withAlpha(16777215, 0.05F * g)
      );
      MenuRenderer.fillRoundedRect(
         matrix3x2fStack,
         this.floatValueField2 + this.floatValueField4 - 9.0F,
         h - s / Math.max(1.0F, ba - o) * (o - bd),
         2.0F,
         bd,
         1.0F,
         MenuRenderer.replaceAlpha(m, 0.8F * g)
      );
   }

   private void performAction_3057(DrawContext drawContext, Matrix3x2fStack matrix3x2fStack, int i, int j, float f, float g) {
      int k = MenuRenderer.getAccent();
      float h = this.floatValueField3 + this.floatValueField5 - 56.0F;
      float l = 22.0F;
      this.addW = 34.0F;
      this.rndW = MenuRenderer.textWidth("RANDOM", 6.5F) + 16.0F;
      float m = this.floatValueField4 - 20.0F - this.addW - this.rndW - 8.0F;
      this.field.setVector2fField1_2966(new Vector2f(this.floatValueField2 + 10.0F, h));
      this.field.setVector2fField2_2944(new Vector2f(m, l));
      this.field.performAction_2964(drawContext, i, j, f, g);
      this.addX = this.floatValueField2 + 10.0F + m + 4.0F;
      this.addY = h;
      this.addH = l;
      this.rndX = this.addX + this.addW + 4.0F;
      boolean bl = !this.field.getStringBuilderField4_2956().toString().trim().isEmpty();
      this.addReady = MenuRenderer.approach(this.addReady, bl ? 1.0F : 0.0F, 10.0F);
      this.performAction_3058(matrix3x2fStack, 0, this.addX, this.addY, this.addW, this.addH, "ADD", k, this.addReady, true, g, i, j);
      this.performAction_3058(matrix3x2fStack, 1, this.rndX, this.addY, this.rndW, this.addH, "RANDOM", 16777215, 0.0F, true, g, i, j);
      this.delX = this.floatValueField2 + 10.0F;
      this.delY = this.floatValueField3 + this.floatValueField5 - 28.0F;
      this.delW = this.floatValueField4 - 20.0F;
      this.delH = 20.0F;
      boolean bl1 = !this.computeValue_3062().isEmpty();
      boolean bl2 = this.delArm > 0.0F;
      boolean bl3 = bl1 && UtilComponent279.checkCondition_3576(i, j, this.delX, this.delY, this.delW, this.delH);
      this.hov[2] = MenuRenderer.approach(this.hov[2], bl3 ? 1.0F : 0.0F, 14.0F);
      int n = 14438480;
      float o = bl2 ? 0.6F + 0.4F * (float)Math.sin(MenuRenderer.time * 10.0F) : 1.0F;
      MenuRenderer.fillRoundedRect(
         matrix3x2fStack,
         this.delX,
         this.delY,
         this.delW,
         this.delH,
         6.0F,
         MenuRenderer.withAlpha(n, (0.07F + 0.08F * this.hov[2] + (bl2 ? 0.2F * o : 0.0F)) * g)
      );
      MenuRenderer.strokeRoundedRect(
         matrix3x2fStack,
         this.delX,
         this.delY,
         this.delW,
         this.delH,
         6.0F,
         0.5F,
         MenuRenderer.withAlpha(n, (0.2F + 0.25F * this.hov[2] + (bl2 ? 0.35F : 0.0F)) * g)
      );
      if (bl2) {
         RenderComponent193.performAction_2681(matrix3x2fStack, this.delX, this.delY, this.delW, this.delH);
         MenuRenderer.fillRoundedRect(
            matrix3x2fStack, this.delX, this.delY + this.delH - 2.0F, this.delW * (this.delArm / 2.5F), 2.0F, 0.0F, MenuRenderer.withAlpha(n, 0.9F * g)
         );
         RenderComponent193.performAction_2680(matrix3x2fStack);
      }

      MenuRenderer.drawCenteredText(
         matrix3x2fStack,
         bl2 ? "CLICK AGAIN TO CONFIRM" : "DELETE ALL ACCOUNTS",
         this.delX + this.delW / 2.0F,
         this.delY + this.delH / 2.0F,
         6.5F,
         MenuRenderer.withAlpha(n, (bl1 ? 1.0F : 0.35F) * g)
      );
   }

   private void performAction_3058(
      Matrix3x2fStack matrix3x2fStack, int i, float f, float g, float h, float j, String string, int k, float l, boolean bl, float m, int n, int o
   ) {
      boolean bl1 = bl && UtilComponent279.checkCondition_3576(n, o, f, g, h, j);
      this.hov[i] = MenuRenderer.approach(this.hov[i], bl1 ? 1.0F : 0.0F, 14.0F);
      float p = this.hov[i];
      MenuRenderer.fillRoundedRect(matrix3x2fStack, f, g, h, j, 6.0F, MenuRenderer.withAlpha(16777215, (0.05F + 0.06F * p) * m));
      if (l > 0.01F) {
         MenuRenderer.fillRoundedRect(matrix3x2fStack, f, g, h, j, 6.0F, MenuRenderer.replaceAlpha(k, 0.22F * l * m));
         MenuRenderer.strokeRoundedRect(matrix3x2fStack, f, g, h, j, 6.0F, 0.5F, MenuRenderer.replaceAlpha(k, 0.55F * l * m));
      } else {
         MenuRenderer.strokeRoundedRect(matrix3x2fStack, f, g, h, j, 6.0F, 0.5F, MenuRenderer.withAlpha(16777215, (0.08F + 0.12F * p) * m));
      }

      int q = MenuRenderer.blendArgb(
         MenuRenderer.withAlpha(MenuRenderer.blendRgb(12103872, 16777215, p), m), MenuRenderer.replaceAlpha(k, m), l
      );
      MenuRenderer.drawCenteredText(matrix3x2fStack, string, f + h / 2.0F, g + j / 2.0F, 6.5F, q);
   }

   private boolean checkCondition_3048(double d, double e, float f, float g, float h, float i) {
      return UtilComponent279.checkCondition_3576(d, e, f, g, h, i);
   }

   @ApiAnnotation001
   public boolean mouseClicked(Click click, boolean bl) {
      if (this.closing) {
         return true;
      }

      double d = UtilComponent279.computeValue_3580(click.x(), 2);
      double e = UtilComponent279.computeValue_3580(click.y(), 2);
      int i = click.button();
      this.field.performAction_2959(d, e, i);
      if (this.checkCondition_3048(d, e, this.clsX, this.clsY, 18.0F, 18.0F)) {
         MenuRenderer.playClickSound(0.8F, 0.8F);
         this.performAction_3043();
         return true;
      }

      if (this.checkCondition_3048(d, e, this.addX, this.addY, this.addW, this.addH)) {
         this.performAction_3042(this.field.getStringBuilderField4_2956().toString());
         return true;
      }

      if (this.checkCondition_3048(d, e, this.rndX, this.addY, this.rndW, this.addH)) {
         this.performAction_3042(this.computeValue_3041());
         return true;
      }

      if (this.checkCondition_3048(d, e, this.delX, this.delY, this.delW, this.delH)) {
         if (this.computeValue_3062().isEmpty()) {
            return true;
         }

         if (this.delArm > 0.0F) {
            this.delArm = 0.0F;
            this.rows.forEach(nestedType385 -> nestedType385.removing = true);
            this.visualOrderDirty = true;
            this.computeValue_3062().clear();
            this.performAction_3040(null);
            NeneClient.getInstance().getProcessors().getProxyManagerField2_0675().performAction_2365();
            MenuRenderer.playClickSound(0.6F, 0.9F);
         } else {
            this.delArm = 2.5F;
            MenuRenderer.playClickSound(0.9F, 0.7F);
         }

         return true;
      } else if (this.hovered == null) {
         if (!this.checkCondition_3048(d, e, this.floatValueField2, this.floatValueField3, this.floatValueField4, this.floatValueField5)) {
            this.performAction_3043();
            return true;
         } else {
            return super.mouseClicked(click, bl);
         }
      } else if (i == 1) {
         this.performAction_3051(this.hovered);
         MenuRenderer.playClickSound(0.7F, 0.8F);
         return true;
      } else {
         this.pressed = this.hovered;
         this.grabOffset = (float)e - this.hovered.floatValueField1;
         this.dragging = false;
         return true;
      }
   }

   @ApiAnnotation001
   public boolean mouseDragged(Click click, double d, double e) {
      double f = click.x();
      double g = click.y();
      this.field.performAction_2941(UtilComponent279.computeValue_3580(f, 2), UtilComponent279.computeValue_3580(g, 2), click.button());
      if (this.pressed == null) {
         return super.mouseDragged(click, d, e);
      }

      double h = UtilComponent279.computeValue_3580(g, 2) - this.grabOffset;
      if (Math.abs(h - this.pressed.floatValueField1) <= 8.0) {
         return true;
      }

      if (!this.dragging) {
         MenuRenderer.playClickSound(1.5F, 0.5F);
      }

      this.dragging = true;
      return true;
   }

   @ApiAnnotation001
   public boolean mouseReleased(Click click) {
      if (this.pressed == null) {
         return super.mouseReleased(click);
      }

      if (!this.dragging) {
         if (this.pressed.starHover) {
            boolean bl = !this.pressed.acc.isFavorited_2357();
            this.pressed.acc.setFavorited_2362(bl);
            this.visualOrderDirty = true;
            NeneClient.getInstance().getProcessors().getProxyManagerField2_0675().performAction_2365();
            MenuRenderer.playToggleSound(bl);
         } else {
            this.performAction_3040(this.pressed.acc);
            MenuRenderer.playClickSound(1.3F, 0.8F);
         }

         this.pressed = null;
         return true;
      } else {
         this.performAction_3046();
         MenuRenderer.playClickSound(1.0F, 0.5F);
         this.pressed = null;
         this.dragging = false;
         return true;
      }
   }

   @ApiAnnotation001
   public boolean mouseScrolled(double e, double f, double g, double d) {
      this.scroll.performAction_2532((float)d * 32.0F);
      return true;
   }

   @ApiAnnotation001
   public boolean charTyped(CharInput charInput) {
      if (!this.field.isFlagField7_2939()) {
         return super.charTyped(charInput);
      }

      this.field.performAction_2958((char)charInput.codepoint(), charInput.modifiers());
      return true;
   }

   @ApiAnnotation001
   public boolean keyPressed(KeyInput keyInput) {
      int i = keyInput.key();
      int j = keyInput.scancode();
      int k = keyInput.comp_4797();
      if (this.closing) {
         return true;
      }

      if ((k & 2) != 0 && i == 86) {
         this.performAction_3047();
         return true;
      }

      if (!this.field.isFlagField7_2939()) {
         if (i != 256) {
            return super.keyPressed(keyInput);
         }

         this.performAction_3043();
         return true;
      } else if (i == 257) {
         this.performAction_3042(this.field.getStringBuilderField4_2956().toString());
         return true;
      } else {
         this.field.performAction_2963(i, j, k);
         return true;
      }
   }

   public ScreenComponent212() {
      super(Text.empty());
      this.field = new TextField(TextField.State384.ALT_MANAGER);
      this.field.setStringField3_2969("Nickname");
      this.computeValue_3062().forEach(proxyEntry -> this.rows.add(new ScreenComponent212.NestedType385(proxyEntry)));
   }

   public void renderBackground(DrawContext drawContext, int i, int j, float f) {
   }

   private void performAction_3043() {
      if (!this.closing) {
         this.closing = true;
         this.field.performAction_2950(false);
         MenuRenderer.close();
      }
   }

   private void performAction_3044() {
      if (!this.finished) {
         this.finished = true;
         MinecraftAccess.minecraft.setScreen(new MainMenuScreen());
      }
   }

   public void close() {
      this.performAction_3043();
   }

   public boolean shouldCloseOnEsc() {
      return false;
   }

   private List<ProxyEntry> computeValue_3062() {
      return NeneClient.getInstance().getProcessors().getProxyManagerField2_0675().getEntries();
   }

   private void performAction_3045() {
      List list = this.computeValue_3062();
      boolean bl = this.rows.removeIf(nestedType385 -> !list.contains(nestedType385.acc));

      for (ProxyEntry proxyEntry : list) {
         if (this.rows.stream().noneMatch(nestedType385 -> nestedType385.acc == proxyEntry)) {
            this.rows.add(new ScreenComponent212.NestedType385(proxyEntry));
            bl = true;
         }
      }

      if (bl) {
         this.visualOrderDirty = true;
      }
   }

   private void performAction_3040(ProxyEntry proxyEntry) {
      NeneClient.getInstance().getProcessors().getProxyManagerField2_0675().performAction_2368(proxyEntry);
      if (proxyEntry != null) {
         if (!proxyEntry.getName_2359().matches("[A-Za-z0-9_]{1,16}")) {
            MenuRenderer.playClickSound(0.6F, 0.6F);
         } else if (MinecraftAccess.minecraft.world != null) {
            System.err.println("[XenoClient] Select offline nicknames from the main menu, not while in a world.");
         } else {
            UUID uUID = UUID.nameUUIDFromBytes(("OfflinePlayer:" + proxyEntry.getName_2359()).getBytes(StandardCharsets.UTF_8));
            Session session = new Session(proxyEntry.getName_2359(), uUID, "", Optional.empty(), Optional.empty());
            ((MinecraftClientAccessor)MinecraftAccess.minecraft).setSession(session);
         }
      }
   }

   private String computeValue_3041() {
      StringBuilder stringBuilder = new StringBuilder();
      int i = 2 + (int)(Math.random() * 3.0);

      for (int j = 0; j < i; j++) {
         char c = "bcdfghjklmnpqrstvwz".charAt((int)(Math.random() * "bcdfghjklmnpqrstvwz".length()));
         stringBuilder.append(c);
         if (Math.random() < 0.12) {
            stringBuilder.append(c);
         }

         char d = "aeiouy".charAt((int)(Math.random() * "aeiouy".length()));
         stringBuilder.append(d);
         if (Math.random() < 0.1) {
            stringBuilder.append(d);
         }
      }

      if (Math.random() < 0.3) {
         stringBuilder.setCharAt(0, Character.toUpperCase(stringBuilder.charAt(0)));
      }

      if (Math.random() < 0.15) {
         stringBuilder.append('_');
      }

      if (Math.random() < 0.25) {
         int k = 1 + (int)(Math.random() * 3.0);

         for (int l = 0; l < k; l++) {
            stringBuilder.append((char)(48 + (int)(Math.random() * 10.0)));
         }
      }

      if (stringBuilder.length() < 5) {
         return this.computeValue_3041();
      } else {
         return stringBuilder.length() > 16 ? stringBuilder.substring(0, 16) : stringBuilder.toString();
      }
   }

   private void performAction_3042(String string) {
      String string1 = string == null ? "" : string.trim().replaceAll("[^A-Za-z0-9_]", "");
      if (string1.length() > 16) {
         string1 = string1.substring(0, 16);
      }

      String string2 = string1;
      if (!string2.isEmpty() && !this.computeValue_3062().stream().anyMatch(proxyEntry1 -> proxyEntry1.getName_2359().equalsIgnoreCase(string2))) {
         ProxyEntry proxyEntry = new ProxyEntry(string2);
         this.computeValue_3062().add(proxyEntry);
         this.performAction_3040(proxyEntry);
         NeneClient.getInstance().getProcessors().getProxyManagerField2_0675().performAction_2365();
         this.rows.add(new ScreenComponent212.NestedType385(proxyEntry));
         this.visualOrderDirty = true;
         this.field.performAction_2948();
         MenuRenderer.playClickSound(1.6F, 0.9F);
      } else {
         MenuRenderer.playClickSound(0.6F, 0.6F);
      }
   }

   private void performAction_3051(ScreenComponent212.NestedType385 nestedType385) {
      nestedType385.removing = true;
      boolean bl = nestedType385.acc.isSelected_2360();
      this.computeValue_3062().remove(nestedType385.acc);
      this.visualOrderDirty = true;
      NeneClient.getInstance().getProcessors().getProxyManagerField2_0675().performAction_2365();
      if (bl) {
         this.performAction_3040(this.computeValue_3062().stream().findFirst().orElse(null));
      }
   }

   private void performAction_3055(List<ScreenComponent212.NestedType385> list, float f) {
      int i = list.indexOf(this.pressed);
      int j = (int)list.stream().filter(nestedType385 -> nestedType385.acc.isFavorited_2357()).count();
      int k = this.pressed.acc.isFavorited_2357() ? 0 : j;
      int l = this.pressed.acc.isFavorited_2357() ? j - 1 : list.size() - 1;
      int m = Math.max(k, Math.min(l, Math.round(f / 32.0F)));
      if (i >= 0 && i != m) {
         list.remove(i);
         list.add(m, this.pressed);
         this.rows.clear();
         this.rows.addAll(list);
      }
   }

   private void performAction_3046() {
      List list = this.computeValue_3062();
      List list1 = this.rows.stream().map(nestedType385 -> nestedType385.acc).filter(list::contains).toList();
      list.clear();
      list.addAll(list1);
      NeneClient.getInstance().getProcessors().getProxyManagerField2_0675().performAction_2365();
   }

   private void performAction_3047() {
      String string = GLFW.glfwGetClipboardString(MinecraftAccess.minecraft.getWindow().getHandle());
      if (string != null) {
         String string1 = string.replaceAll("[^a-zA-Z0-9_]", "");
         this.performAction_3042(string1.substring(0, Math.min(16, string1.length())));
      }
   }

   static {
      ObfuscatorRuntime.registerClass(ScreenComponent212.class, 15);
   }

   class NestedType385 {
      final ProxyEntry acc;
      final float[] anim = new float[4];
      float slot = Float.NaN;
      float floatValueField1;
      float pop;
      boolean starHover;
      boolean removing;
      boolean lastFav;

      NestedType385(ProxyEntry proxyEntry) {
         this.acc = proxyEntry;
         this.lastFav = proxyEntry.isFavorited_2357();
      }

      boolean checkCondition_3063() {
         return this.removing && this.anim[3] < 0.01F;
      }

      void performAction_3064(
         Matrix3x2fStack matrix3x2fStack,
         RenderComponent184 renderComponent184,
         int i,
         float f,
         float g,
         float h,
         float j,
         float k,
         float l,
         int m,
         int n,
         float o,
         boolean bl
      ) {
         this.slot = Float.isNaN(this.slot) ? j : MenuRenderer.approach(this.slot, j, bl ? 40.0F : 16.0F);
         this.floatValueField1 = h + this.slot;
         boolean bl1 = !this.removing && n > k && n < l && UtilComponent279.checkCondition_3576(m, n, f, this.floatValueField1, g, 28.0F);
         this.starHover = bl1 && m > f + g - 26.0F;
         if (bl1) {
            ScreenComponent212.this.hovered = this;
         }

         boolean bl2 = this.acc.isFavorited_2357();
         if (bl2 != this.lastFav) {
            this.lastFav = bl2;
            this.pop = 1.0F;
         }

         this.pop = MenuRenderer.approach(this.pop, 0.0F, 7.0F);
         float[] p = new float[]{bl1 ? 1.0F : 0.0F, this.acc.isSelected_2360() ? 1.0F : 0.0F, bl2 ? 1.0F : 0.0F, this.removing ? 0.0F : 1.0F};

         for (int q = 0; q < 4; q++) {
            this.anim[q] = MenuRenderer.approach(this.anim[q], p[q], 14.0F);
         }

         float bd = this.anim[0];
         float r = this.anim[1];
         float s = this.anim[2];
         float t = o * this.anim[3];
         if (!(this.floatValueField1 + 28.0F < k - 2.0F) && !(this.floatValueField1 > l + 2.0F)) {
            if (!(t <= 0.001F)) {
               float u = (1.0F - this.anim[3]) * (this.removing ? 24.0F : -14.0F);
               float v = f + u;
               float w = this.floatValueField1;
               if (bl) {
                  MenuRenderer.fillRoundedRect(matrix3x2fStack, v - 2.0F, w - 1.0F, g + 4.0F, 34.0F, 9.0F, MenuRenderer.withAlpha(0, 0.35F * t));
                  MenuRenderer.fillRoundedRect(matrix3x2fStack, v - 1.0F, w - 1.0F, g + 2.0F, 30.0F, 8.0F, MenuRenderer.replaceAlpha(i, 0.15F * t));
               }

               MenuRenderer.fillRoundedRect(
                  matrix3x2fStack,
                  v,
                  w,
                  g,
                  28.0F,
                  6.0F,
                  MenuRenderer.withAlpha(MenuRenderer.blendRgb(1446428, 2300717, bd), MenuRenderer.glassAlpha(0.84F) * t)
               );
               if (r > 0.01F) {
                  MenuRenderer.fillRoundedRect(matrix3x2fStack, v, w, g, 28.0F, 6.0F, MenuRenderer.replaceAlpha(i, 0.1F * r * t));
                  MenuRenderer.fillRoundedRect(matrix3x2fStack, v, w + 7.0F, 2.0F, 14.0F, 1.0F, MenuRenderer.replaceAlpha(i, r * t));
                  MenuRenderer.strokeRoundedRect(matrix3x2fStack, v, w, g, 28.0F, 6.0F, 0.5F, MenuRenderer.replaceAlpha(i, 0.35F * r * t));
               } else {
                  MenuRenderer.strokeRoundedRect(
                     matrix3x2fStack,
                     v,
                     w,
                     g,
                     28.0F,
                     6.0F,
                     0.5F,
                     MenuRenderer.withAlpha(MenuRenderer.blendRgb(16777215, 16761415, s), (0.06F + 0.06F * bd + 0.18F * s) * t)
                  );
               }

               renderComponent184.performAction_2586(matrix3x2fStack, this.acc.computeValue_2361(), null, v + 8.0F, w + 4.0F, 20.0F, 20.0F, 4.0F, t);
               float x = v + 36.0F;
               float y = g - 36.0F - 34.0F;
               MenuRenderer.drawText(
                  matrix3x2fStack,
                  MenuRenderer.ellipsize(this.acc.getName_2359(), 7.25F, y),
                  x + bd * 1.5F,
                  w + 10.0F,
                  7.25F,
                  MenuRenderer.replaceAlpha(MenuRenderer.blendArgb(MenuRenderer.getTextColor(), i, r), t)
               );
               MenuRenderer.drawText(
                  matrix3x2fStack, "OFFLINE ACCOUNT", x + bd * 1.5F, w + 20.5F, 5.0F, MenuRenderer.withAlpha(16777215, 0.28F * (1.0F - r) * t)
               );
               MenuRenderer.drawText(matrix3x2fStack, "SELECTED", x + bd * 1.5F, w + 20.5F, 5.0F, MenuRenderer.replaceAlpha(i, 0.85F * r * t));
               if (s > 0.01F || bd > 0.01F) {
                  float z = 9.0F * (1.0F + 0.45F * this.pop);
                  float ba = v + g - 14.0F - z / 2.0F;
                  int bb = MenuRenderer.withAlpha(16777215, (this.starHover ? 0.55F : 0.18F) * bd * t);
                  int bc = MenuRenderer.withAlpha(16761415, t);
                  MenuRenderer.drawIcon(matrix3x2fStack, "\\", ba, w + 14.0F, z, MenuRenderer.blendArgb(bb, bc, s));
               }
            }
         }
      }
   }
}
