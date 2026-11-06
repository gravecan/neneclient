package aethereal.ui.screen;

import aethereal.core.Category;
import aethereal.core.Module;
import aethereal.core.NeneClient;
import aethereal.render.RenderComponent184;
import aethereal.render.RenderComponent186;
import aethereal.render.RenderComponent192;
import aethereal.render.RenderComponent193;
import aethereal.render.pipeline.PipelineComponent196;
import aethereal.ui.element.KeybindElement;
import aethereal.ui.element.SettingElement;
import aethereal.ui.element.TextField;
import aethereal.util.CursorUtil;
import aethereal.util.KeyUtil;
import aethereal.util.UtilComponent279;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Text;
import org.joml.Matrix3x2fStack;
import org.joml.Vector2f;

public class ScreenComponent215 extends Screen {
   private static final float floatValueField1 = 124.0F;
   private static final float HEAD = 26.0F;
   private static final float ROW = 20.0F;
   private static final float ROW_GAP = 2.0F;
   private static final float PAD_B = 4.0F;
   private static final float GAP = 10.0F;
   private static final float MAX_BODY = 290.0F;
   private static final float TOP = 46.0F;
   private static final Comparator<Module> BY_NAME = Comparator.comparing(Module::getName, String.CASE_INSENSITIVE_ORDER);
   private static final Map<Category, ScreenComponent215.NestedType390> STATE = new EnumMap<>(Category.class);
   private static ScreenComponent215 current;
   private final Screen parent;
   private final Runnable onClosed;
   private final TextField search = new TextField(TextField.State384.GUI);
   private final List<ScreenComponent215.NestedType390> order = new ArrayList<>();
   private final Map<Module, ScreenComponent215.NestedType391> rows = new IdentityHashMap<>();
   private final List<Module> all;
   private boolean closing;
   private boolean finished;
   private boolean opened;
   private float openT;
   private float openStart;
   private ScreenComponent215.NestedType390 dragP;
   private float dragDX;
   private float dragDY;
   private Module hoverMod;
   private float hoverTime;
   private float tipA;
   private int integerField2;
   private int integerField3;
   private int accent;
   private float delta;

   public static void performAction_3096(Runnable runnable) {
      MinecraftClient minecraftClient = MinecraftClient.getInstance();
      if (!(minecraftClient.currentScreen instanceof ScreenComponent215)) {
         minecraftClient.setScreen(new ScreenComponent215(Text.literal("Dropdown"), runnable, minecraftClient.currentScreen));
      }
   }

   public static void performAction_3089() {
      if (current != null) {
         current.close();
      }
   }

   public ScreenComponent215(Text text, Runnable runnable, Screen screen) {
      super(text);
      this.onClosed = runnable;
      this.parent = screen;
      this.all = NeneClient.getInstance().getProcessors().getModules().getEntries().stream().toList();
      this.search.setStringField3_2969("Search modules...");

      for (Category category : Category.values()) {
         ScreenComponent215.NestedType390 nestedType390 = STATE.computeIfAbsent(category, ScreenComponent215.NestedType390::new);
         nestedType390.mods = this.all.stream().filter(module -> module.getCategory() == category).sorted(BY_NAME).toList();
         nestedType390.vis = new ArrayList<>(nestedType390.mods);
         this.order.add(nestedType390);
      }

      current = this;
   }

   protected void init() {
      if (!this.opened) {
         this.opened = true;
         this.openStart = MenuRenderer.time;
         MenuRenderer.playBackSound();
      }
   }

   public void renderBackground(DrawContext drawContext, int i, int j, float f) {
   }

   protected void applyBlur(DrawContext drawContext) {
   }

   public void renderInGameBackground(DrawContext drawContext) {
   }

   public boolean shouldPause() {
      return false;
   }

   public void render(DrawContext drawContext, int i, int j, float f) {
      MenuRenderer.updateFrameTime();
      this.openT = MenuRenderer.approach(this.openT, this.closing ? 0.0F : 1.0F, 8.0F);
      if (this.closing && this.openT <= 0.02F) {
         this.performAction_3088();
      } else {
         this.delta = f;
         double d = UtilComponent279.computeValue_3580(i, 2);
         double e = UtilComponent279.computeValue_3580(j, 2);
         RenderComponent192.performAction_2676(drawContext, 2);
         Matrix3x2fStack matrix3x2fStack = drawContext.getMatrices();
         MinecraftClient minecraftClient = MinecraftClient.getInstance();
         this.integerField2 = minecraftClient.getWindow().getScaledWidth();
         this.integerField3 = minecraftClient.getWindow().getScaledHeight();
         this.accent = MenuRenderer.getAccent();
         float g = MenuRenderer.easeOutCubic(this.openT);
         this.performAction_3090();
         this.performAction_3086(d, e);
         MenuRenderer.fillRoundedRect(matrix3x2fStack, 0.0F, 0.0F, this.integerField2, this.integerField3, 0.0F, MenuRenderer.withAlpha(0, 0.5F * g));
         if (MenuRenderer.particlesOn) {
            MenuRenderer.drawParticles(matrix3x2fStack, this.integerField2, this.integerField3, g);
         }

         this.performAction_3097(drawContext, matrix3x2fStack, d, e, g);
         ScreenComponent215.NestedType390 nestedType390 = null;

         for (int k = this.order.size() - 1; k >= 0; k--) {
            ScreenComponent215.NestedType390 nestedType3901 = this.order.get(k);
            if (nestedType3901.show > 0.5F
               && UtilComponent279.checkCondition_3576(d, e, nestedType3901.floatValueField1, nestedType3901.floatValueField2, 124.0F, nestedType3901.totalH)) {
               nestedType390 = nestedType3901;
               break;
            }
         }

         Module module2 = null;

         for (int l = 0; l < this.order.size(); l++) {
            ScreenComponent215.NestedType390 nestedType3902 = this.order.get(l);
            Module module = this.computeValue_3098(drawContext, matrix3x2fStack, nestedType3902, l, d, e, g, nestedType3902 == nestedType390);
            if (module != null) {
               module2 = module;
            }
         }

         if (this.openT > 0.9F) {
            for (ScreenComponent215.NestedType390 nestedType3903 : this.order) {
               if (!(nestedType3903.show < 0.5F) && !(nestedType3903.openAnim < 0.5F)) {
                  for (Module module1 : nestedType3903.vis) {
                     if (module1.isExpanded() && !(module1.getHoverAnimation().getFloatValueField4_2530() <= 0.01F)) {
                        for (SettingElement settingElement : module1.getSettingElements()) {
                           settingElement.performAction_2917(drawContext, d, e, f);
                        }
                     }
                  }
               }
            }
         }

         this.performAction_3099(matrix3x2fStack, d, e, module2, g);
         MenuRenderer.drawCenteredText(
            matrix3x2fStack,
            "LMB TOGGLE   |   RMB SETTINGS   |   MMB BIND   |   DRAG HEADER   |   RMB HEADER COLLAPSE   |   CTRL+F SEARCH   |   RSHIFT CLOSE",
            this.integerField2 / 2.0F,
            this.integerField3 - 12.0F,
            5.5F,
            MenuRenderer.withAlpha(16777215, 0.28F * g)
         );
         PipelineComponent196.performAction_2728();
         RenderComponent192.performAction_2675(drawContext);
      }
   }

   private void performAction_3090() {
      int i = this.order.size();
      float f = i * 124.0F + (i - 1) * 10.0F;
      float g = 10.0F;
      if (f > this.integerField2 - 16.0F) {
         g = Math.max(4.0F, (this.integerField2 - 16.0F - i * 124.0F) / Math.max(1, i - 1));
         f = i * 124.0F + (i - 1) * g;
      }

      float h = Math.max(8.0F, (this.integerField2 - f) / 2.0F);
      int j = 0;

      for (Category category : Category.values()) {
         ScreenComponent215.NestedType390 nestedType390 = STATE.get(category);
         if (nestedType390.floatValueField1 < 0.0F) {
            nestedType390.floatValueField1 = h + j * (124.0F + g);
         }

         j++;
      }
   }

   private float computeValue_3092(Module module) {
      float f = 0.0F;

      for (SettingElement settingElement : module.getSettingElements()) {
         f += (settingElement.getVector4fField3_3024().w() + 4.0F) * settingElement.getAnimationField2_3030().getFloatValueField4_2530();
      }

      return f;
   }

   private float computeValue_3087(Module module) {
      return 20.0F + this.computeValue_3092(module) * module.getHoverAnimation().getFloatValueField4_2530() + 2.0F;
   }

   private void performAction_3086(double d, double e) {
      String string = this.search.getStringBuilderField4_2956().toString().trim().toLowerCase(Locale.ROOT);

      for (ScreenComponent215.NestedType390 nestedType390 : this.order) {
         ArrayList arrayList = new ArrayList();

         for (Module module : nestedType390.mods) {
            if (string.isEmpty() || module.getName().toLowerCase(Locale.ROOT).contains(string)) {
               arrayList.add(module);
            }
         }

         nestedType390.vis = arrayList;
         float f = 0.0F;

         for (Module module1 : arrayList) {
            module1.getHoverAnimation().performAction_2535(0.0F, 1.0F, 0.5F, RenderComponent186.contract372Field9, this.delta);
            module1.getHoverAnimation().performAction_2528(module1.isExpanded());
            f += this.computeValue_3087(module1);
         }

         nestedType390.contentH = f;
         nestedType390.bodyMax = Math.min(290.0F, Math.max(60.0F, this.integerField3 - nestedType390.floatValueField2 - 26.0F - 16.0F));
         nestedType390.openAnim = MenuRenderer.approach(nestedType390.openAnim, nestedType390.open ? 1.0F : 0.0F, 10.0F);
         nestedType390.show = MenuRenderer.approach(nestedType390.show, !string.isEmpty() && arrayList.isEmpty() ? 0.0F : 1.0F, 10.0F);
         float g = Math.min(f, nestedType390.bodyMax) * nestedType390.openAnim;
         nestedType390.bodyH = g;
         nestedType390.totalH = 26.0F + (g > 0.5F ? g + 4.0F : 0.0F);
         nestedType390.scrollT = Math.max(0.0F, Math.min(Math.max(0.0F, f - nestedType390.bodyMax), nestedType390.scrollT));
         nestedType390.scroll = MenuRenderer.approach(nestedType390.scroll, nestedType390.scrollT, 14.0F);
      }
   }

   private void performAction_3097(DrawContext drawContext, Matrix3x2fStack matrix3x2fStack, double d, double e, float f) {
      float g = 22.0F;
      MenuRenderer.drawTexture(matrix3x2fStack, MenuRenderer.LOGO, 16.0F, 12.0F, g, g, MenuRenderer.withAlpha(MenuRenderer.ACCENT, f));
      MenuRenderer.drawText(matrix3x2fStack, "NENECLIENT", 44.0F, 18.0F, 7.5F, MenuRenderer.withAlpha(16777215, f));
      MenuRenderer.drawText(matrix3x2fStack, "DROPDOWN", 44.0F, 28.0F, 5.5F, MenuRenderer.replaceAlpha(this.accent, 0.9F * f));
      this.search.setVector2fField2_2944(new Vector2f(220.0F, 20.0F));
      this.search.setVector2fField1_2966(new Vector2f((this.integerField2 - 220.0F) / 2.0F, 12.0F));
      this.search.performAction_2964(drawContext, d, e, this.delta, f);
   }

   private Module computeValue_3098(
      DrawContext drawContext, Matrix3x2fStack matrix3x2fStack, ScreenComponent215.NestedType390 nestedType390, int i, double d, double e, float f, boolean bl
   ) {
      float g = MenuRenderer.easeOutCubic(MenuRenderer.clamp01((MenuRenderer.time - this.openStart - i * 0.06F) / 0.4F));
      float h = f * g * nestedType390.show;
      if (h <= 0.01F) {
         return null;
      }

      RenderComponent184 renderComponent184 = MenuRenderer.getRenderer();
      float j = nestedType390.floatValueField1;
      float k = nestedType390.floatValueField2 - (1.0F - g) * 14.0F;
      boolean bl1 = i == this.order.size() - 1;
      int l = MenuRenderer.withAlpha(1774628, 0.96F * h);
      renderComponent184.performAction_2580(
         matrix3x2fStack,
         j,
         k,
         124.0F,
         nestedType390.totalH,
         8.0F,
         MenuRenderer.withAlpha(0, 0.0F),
         1.0F,
         MenuRenderer.withAlpha(0, 0.45F * h),
         12.0F
      );
      MenuRenderer.fillRoundedRect(matrix3x2fStack, j, k, 124.0F, nestedType390.totalH, 8.0F, MenuRenderer.withAlpha(MenuRenderer.PANEL, 0.92F * h));
      MenuRenderer.fillRoundedRect(matrix3x2fStack, j, k, 124.0F, 26.0F, 8.0F, l);
      if (nestedType390.bodyH > 0.5F) {
         MenuRenderer.fillRoundedRect(matrix3x2fStack, j, k + 26.0F - 8.0F, 124.0F, 8.0F, 0.0F, l);
      }

      MenuRenderer.strokeRoundedRect(
         matrix3x2fStack, j, k, 124.0F, nestedType390.totalH, 8.0F, 0.6F, MenuRenderer.replaceAlpha(this.accent, (bl1 ? 0.34F : 0.16F) * h)
      );
      float m = k + 13.0F;
      MenuRenderer.drawIcon(matrix3x2fStack, nestedType390.cat.getIcon_0560(), j + 13.0F, m, 8.5F, MenuRenderer.replaceAlpha(this.accent, h));
      MenuRenderer.drawText(matrix3x2fStack, nestedType390.cat.name(), j + 27.0F, m, 7.0F, MenuRenderer.withAlpha(16777215, h));
      int n = 0;

      for (Module module : nestedType390.mods) {
         if (module.isEnabled()) {
            n++;
         }
      }

      float bs = j + 124.0F - 13.0F;
      if (n > 0) {
         String string1 = String.valueOf(n);
         float o = MenuRenderer.textWidth(string1, 5.5F) + 9.0F;
         float p = bs - 11.0F - o;
         MenuRenderer.fillRoundedRect(matrix3x2fStack, p, m - 5.0F, o, 10.0F, 5.0F, MenuRenderer.replaceAlpha(this.accent, 0.22F * h));
         MenuRenderer.drawCenteredText(matrix3x2fStack, string1, p + o / 2.0F, m, 5.5F, MenuRenderer.replaceAlpha(this.accent, h));
      }

      matrix3x2fStack.pushMatrix();
      matrix3x2fStack.translate(bs, m);
      matrix3x2fStack.rotate(nestedType390.openAnim * (float) (Math.PI / 2));
      MenuRenderer.drawCenteredText(matrix3x2fStack, ">", 0.0F, 0.0F, 7.0F, MenuRenderer.withAlpha(12103872, h));
      matrix3x2fStack.popMatrix();
      MenuRenderer.fillRoundedRect(
         matrix3x2fStack, j + 9.0F, k + 26.0F - 1.0F, 106.0F, 1.0F, 0.5F, MenuRenderer.replaceAlpha(this.accent, 0.5F * h * nestedType390.openAnim)
      );
      if (nestedType390.bodyH <= 0.5F) {
         return null;
      }

      float bt = k + 26.0F + 3.0F;
      float bu = nestedType390.bodyH;
      float bv = bt - nestedType390.scroll;
      Module module1 = null;
      RenderComponent193.performAction_2681(matrix3x2fStack, j, bt, 124.0F, bu);
      float q = bv;

      for (Module module2 : nestedType390.vis) {
         ScreenComponent215.NestedType391 nestedType391 = this.rows.computeIfAbsent(module2, module3 -> new ScreenComponent215.NestedType391());
         float r = module2.getHoverAnimation().getFloatValueField4_2530();
         float s = 20.0F + this.computeValue_3092(module2) * r;
         nestedType391.floatValueField1 = j + 4.0F;
         nestedType391.floatValueField2 = q;
         nestedType391.floatValueField3 = 116.0F;
         nestedType391.floatValueField4 = s;
         float t = s + 2.0F;
         q += t;
         boolean bl2 = module2.isEnabled();
         boolean bl3 = module2.isBinding();
         boolean bl4 = module2.getKeybind() != -1;
         boolean bl5 = bl
            && e >= bt
            && e <= bt + bu
            && UtilComponent279.checkCondition_3576(d, e, nestedType391.floatValueField1, nestedType391.floatValueField2, nestedType391.floatValueField3, 20.0F);
         if (bl5) {
            module1 = module2;
         }

         nestedType391.hover = MenuRenderer.approach(nestedType391.hover, bl5 ? 1.0F : 0.0F, 14.0F);
         nestedType391.press = MenuRenderer.approach(nestedType391.press, 0.0F, 12.0F);
         if (nestedType391.tog < 0.0F) {
            nestedType391.tog = bl2 ? 1.0F : 0.0F;
         }

         nestedType391.tog = MenuRenderer.approach(nestedType391.tog, bl2 ? 1.0F : 0.0F, 12.0F);
         nestedType391.bind = MenuRenderer.approach(nestedType391.bind, !bl4 && !bl3 ? (bl5 ? 0.4F : 0.0F) : 1.0F, 12.0F);
         if (!(nestedType391.floatValueField2 + s < bt) && !(nestedType391.floatValueField2 > bt + bu)) {
            float u = nestedType391.press * 1.0F;
            float v = nestedType391.floatValueField1 + u;
            float w = nestedType391.floatValueField2 + u;
            float x = nestedType391.floatValueField3 - u * 2.0F;
            float y = s - u * 2.0F;
            if (nestedType391.hover > 0.01F) {
               MenuRenderer.fillRoundedRect(matrix3x2fStack, v, w, x, y, 5.0F, MenuRenderer.withAlpha(16777215, 0.055F * nestedType391.hover * h));
            }

            if (nestedType391.tog > 0.01F) {
               MenuRenderer.fillRoundedRect(matrix3x2fStack, v, w, x, y, 5.0F, MenuRenderer.replaceAlpha(this.accent, 0.13F * nestedType391.tog * h));
               MenuRenderer.fillRoundedRect(
                  matrix3x2fStack, v, w + 5.0F, 2.0F, 10.0F, 1.0F, MenuRenderer.replaceAlpha(this.accent, nestedType391.tog * h)
               );
               MenuRenderer.strokeRoundedRect(
                  matrix3x2fStack, v, w, x, y, 5.0F, 0.5F, MenuRenderer.replaceAlpha(this.accent, 0.28F * nestedType391.tog * h)
               );
            }

            if (nestedType391.ripple >= 0.0F) {
               nestedType391.ripple = nestedType391.ripple + MenuRenderer.floatValueField1 * 1.8F;
               if (nestedType391.ripple >= 1.0F) {
                  nestedType391.ripple = -1.0F;
               } else {
                  RenderComponent193.performAction_2681(matrix3x2fStack, v, w, x, 20.0F);
                  float z = MenuRenderer.easeOutCubic(nestedType391.ripple) * x * 0.9F;
                  MenuRenderer.fillCircle(
                     matrix3x2fStack,
                     v + nestedType391.floatValueField5,
                     w + nestedType391.floatValueField6,
                     z,
                     MenuRenderer.replaceAlpha(this.accent, 0.22F * (1.0F - nestedType391.ripple) * h)
                  );
                  RenderComponent193.performAction_2680(matrix3x2fStack);
               }
            }

            float bz = w + 10.0F;
            float ba = 16.0F;
            float bb = 8.0F;
            float bc = v + x - 7.0F - ba;
            boolean bl6 = module2.getSettingElements().stream().anyMatch(settingElement1 -> settingElement1.checkCondition_3023());
            float bd = bc - 8.0F;
            float be = bl6 ? bd - 6.0F : bc - 5.0F;
            String string = bl3 ? "?" : (bl4 ? KeyUtil.getLabel_3478(module2.getKeybind()) : "");
            float bf = string.isEmpty() ? 0.0F : MenuRenderer.textWidth(string, 5.5F) + 8.0F;
            float bg = be - bf;
            float bh = v + 9.0F + nestedType391.hover * 1.5F;
            float bi = (bf > 0.0F ? bg - 4.0F : be) - bh;
            int bj = MenuRenderer.replaceAlpha(MenuRenderer.blendArgb(MenuRenderer.getTextColor(), this.accent, nestedType391.tog), h);
            MenuRenderer.drawText(
               matrix3x2fStack, MenuRenderer.ellipsize(module2.getName().toUpperCase(Locale.ROOT), 6.5F, bi), bh, bz, 6.5F, bj
            );
            MenuRenderer.fillRoundedRect(
               matrix3x2fStack,
               bc - 2.0F,
               bz - bb / 2.0F - 2.0F,
               ba + 4.0F,
               bb + 4.0F,
               6.0F,
               MenuRenderer.replaceAlpha(this.accent, 0.14F * nestedType391.tog * h)
            );
            MenuRenderer.fillRoundedRect(
               matrix3x2fStack,
               bc,
               bz - bb / 2.0F,
               ba,
               bb,
               4.0F,
               MenuRenderer.withAlpha(MenuRenderer.blendRgb(3814213, this.accent & 16777215, nestedType391.tog), 0.96F * h)
            );
            float bk = 5.0F + 1.5F * (float)Math.sin(Math.PI * nestedType391.tog);
            float bm = bc + 1.5F + (ba - 3.0F - bk) * nestedType391.tog;
            MenuRenderer.fillRoundedRect(matrix3x2fStack, bm, bz - 2.5F, bk, 5.0F, 2.5F, MenuRenderer.withAlpha(16777215, h));
            if (bl6) {
               int bn = MenuRenderer.replaceAlpha(
                  MenuRenderer.blendArgb(MenuRenderer.getMutedTextColor(), this.accent, r), (0.55F + 0.45F * nestedType391.hover) * h
               );

               for (int bo = -1; bo <= 1; bo++) {
                  MenuRenderer.fillCircle(matrix3x2fStack, bd + bo * 2.6F * r, bz + bo * 2.6F * (1.0F - r), 1.0F, bn);
               }
            }

            if (bf > 0.0F && nestedType391.bind > 0.02F) {
               float ca = bl3 ? 0.6F + 0.4F * (float)Math.sin(MenuRenderer.time * 8.0F) : 1.0F;
               float cc = nestedType391.bind * ca * h;
               MenuRenderer.fillRoundedRect(matrix3x2fStack, bg, bz - 4.5F, bf, 9.0F, 3.0F, MenuRenderer.replaceAlpha(this.accent, 0.16F * cc));
               MenuRenderer.strokeRoundedRect(matrix3x2fStack, bg, bz - 4.5F, bf, 9.0F, 3.0F, 0.5F, MenuRenderer.replaceAlpha(this.accent, 0.45F * cc));
               MenuRenderer.drawText(
                  matrix3x2fStack, string, bg + 4.0F, bz, 5.5F, MenuRenderer.replaceAlpha(MenuRenderer.getTextColor(), cc)
               );
            }

            if (r > 0.0F) {
               MenuRenderer.fillRoundedRect(
                  matrix3x2fStack, v + 6.0F, w + 20.0F - 0.5F, x - 12.0F, 1.0F, 0.0F, MenuRenderer.withAlpha(16777215, 0.06F * r * h)
               );
               RenderComponent193.performAction_2681(matrix3x2fStack, v, w, x, y);
               float cb = w + 20.0F + 1.0F - 4.0F * (1.0F - r);
               float cd = 0.0F;

               for (SettingElement settingElement : module2.getSettingElements()) {
                  settingElement.getAnimationField2_3030().performAction_2528(settingElement.checkCondition_3023());
                  settingElement.getAnimationField2_3030().performAction_2535(0.0F, 1.0F, 0.4F, RenderComponent186.contract372Field9, this.delta);
                  float bp = settingElement.getAnimationField2_3030().getFloatValueField4_2530();
                  if (bp > 0.0F) {
                     float bq = cb + cd - 4.0F * (1.0F - bp);
                     float br = cb + (bq - cb) * r;
                     settingElement.getVector4fField3_3024().set(v + 2.0F, br, x - 4.0F, settingElement.getVector4fField3_3024().w());
                     settingElement.performAction_2918(drawContext, d, e, this.delta, r * bp * h);
                     cd += (settingElement.getVector4fField3_3024().w() + 4.0F) * bp;
                  }
               }

               RenderComponent193.performAction_2680(matrix3x2fStack);
            }
         }
      }

      RenderComponent193.performAction_2680(matrix3x2fStack);
      if (nestedType390.contentH > nestedType390.bodyMax + 1.0F && nestedType390.openAnim > 0.9F) {
         float bw = MenuRenderer.clamp01(nestedType390.scroll / (nestedType390.contentH - nestedType390.bodyMax));
         float bx = Math.max(14.0F, bu * bu / nestedType390.contentH);
         float by = bt + bw * (bu - bx);
         MenuRenderer.fillRoundedRect(matrix3x2fStack, j + 124.0F - 3.0F, bt, 1.5F, bu, 0.75F, MenuRenderer.withAlpha(16777215, 0.04F * h));
         MenuRenderer.fillRoundedRect(matrix3x2fStack, j + 124.0F - 3.0F, by, 1.5F, bx, 0.75F, MenuRenderer.replaceAlpha(this.accent, 0.8F * h));
      }

      return module1;
   }

   private void performAction_3099(Matrix3x2fStack matrix3x2fStack, double d, double e, Module module, float f) {
      if (module != null && module == this.hoverMod) {
         this.hoverTime = this.hoverTime + MenuRenderer.floatValueField1;
      } else {
         this.hoverTime = 0.0F;
      }

      this.hoverMod = module;
      String string = module != null && module.getDescription() != null ? module.getDescription().toUpperCase(Locale.ROOT) : "";
      boolean bl = !string.isEmpty() && this.hoverTime > 0.45F && this.dragP == null;
      this.tipA = MenuRenderer.approach(this.tipA, bl ? 1.0F : 0.0F, 12.0F);
      if (!(this.tipA <= 0.01F) && !string.isEmpty()) {
         float g = MenuRenderer.textWidth(string, 5.5F) + 14.0F;
         float h = 16.0F;
         float i = Math.min((float)d + 12.0F, this.integerField2 - g - 6.0F);
         float j = Math.min((float)e + 14.0F, this.integerField3 - h - 6.0F);
         float k = this.tipA * f;
         MenuRenderer.fillRoundedRect(matrix3x2fStack, i, j, g, h, 5.0F, MenuRenderer.withAlpha(1183000, 0.96F * k));
         MenuRenderer.strokeRoundedRect(matrix3x2fStack, i, j, g, h, 5.0F, 0.5F, MenuRenderer.replaceAlpha(this.accent, 0.45F * k));
         MenuRenderer.drawText(matrix3x2fStack, string, i + 7.0F, j + h / 2.0F, 5.5F, MenuRenderer.withAlpha(16777215, 0.85F * k));
      }
   }

   private List<SettingElement<?>> computeValue_3100() {
      ArrayList arrayList = new ArrayList();

      for (ScreenComponent215.NestedType390 nestedType390 : this.order) {
         for (Module module : nestedType390.vis) {
            if (module.isExpanded()) {
               for (SettingElement settingElement : module.getSettingElements()) {
                  if (settingElement.checkCondition_3023()) {
                     arrayList.add(settingElement);
                  }
               }
            }
         }
      }

      return arrayList;
   }

   private void performAction_3095(ScreenComponent215.NestedType390 nestedType390) {
      this.order.remove(nestedType390);
      this.order.add(nestedType390);
   }

   public boolean mouseClicked(Click click, boolean bl) {
      if (this.closing) {
         return true;
      }

      double d = UtilComponent279.computeValue_3580(click.x(), 2);
      double e = UtilComponent279.computeValue_3580(click.y(), 2);
      int i = click.button();
      this.search.performAction_2959(d, e, i);
      if (UtilComponent279.checkCondition_3576(d, e, (this.integerField2 - 220.0F) / 2.0F, 12.0F, 220.0F, 20.0F)) {
         return true;
      }

      for (Module module : this.all) {
         if (module.isBinding()) {
            module.setKeybind(-100 + i);
            module.setBinding(false);
            MenuRenderer.playClickSound(1.5F, 0.8F);
            return true;
         }
      }

      for (int j = this.order.size() - 1; j >= 0; j--) {
         ScreenComponent215.NestedType390 nestedType390 = this.order.get(j);
         if (!(nestedType390.show < 0.5F)
            && UtilComponent279.checkCondition_3576(d, e, nestedType390.floatValueField1, nestedType390.floatValueField2, 124.0F, nestedType390.totalH)) {
            this.performAction_3095(nestedType390);
            if (e < nestedType390.floatValueField2 + 26.0F) {
               if (i == 0) {
                  this.dragP = nestedType390;
                  this.dragDX = (float)d - nestedType390.floatValueField1;
                  this.dragDY = (float)e - nestedType390.floatValueField2;
                  MenuRenderer.playClickSound(1.1F, 0.4F);
               } else if (i == 1) {
                  nestedType390.open = !nestedType390.open;
                  MenuRenderer.playClickSound(nestedType390.open ? 1.3F : 0.9F, 0.6F);
               }

               return true;
            }

            if (nestedType390.openAnim < 0.5F) {
               return true;
            }

            float f = nestedType390.floatValueField2 + 26.0F + 3.0F;
            if (!(e < f) && !(e > f + nestedType390.bodyH)) {
               for (Module module1 : nestedType390.vis) {
                  ScreenComponent215.NestedType391 nestedType391 = this.rows.get(module1);
                  if (nestedType391 != null
                     && UtilComponent279.checkCondition_3576(
                        d, e, nestedType391.floatValueField1, nestedType391.floatValueField2, nestedType391.floatValueField3, 20.0F
                     )) {
                     nestedType391.press = 1.0F;
                     nestedType391.ripple = 0.0F;
                     nestedType391.floatValueField5 = (float)d - nestedType391.floatValueField1;
                     nestedType391.floatValueField6 = (float)e - nestedType391.floatValueField2;
                     if (i == 0) {
                        module1.toggle();
                        MenuRenderer.playToggleSound(module1.isEnabled());
                     } else if (i == 1) {
                        module1.setExpanded(!module1.isExpanded());
                        MenuRenderer.playClickSound(module1.isExpanded() ? 1.3F : 0.9F, 0.6F);
                     } else if (i == 2) {
                        module1.setBinding(!module1.isBinding());
                        MenuRenderer.playClickSound(1.7F, 0.6F);
                     }

                     return true;
                  }
               }

               for (SettingElement settingElement1 : this.computeValue_3100()) {
                  if (settingElement1.checkCondition_2915(d, e, i)) {
                     return true;
                  }
               }

               return true;
            }

            return true;
         }
      }

      for (SettingElement settingElement : this.computeValue_3100()) {
         if (settingElement.checkCondition_2915(d, e, i)) {
            return true;
         }
      }

      return true;
   }

   public boolean mouseReleased(Click click) {
      double d = UtilComponent279.computeValue_3580(click.x(), 2);
      double e = UtilComponent279.computeValue_3580(click.y(), 2);
      if (this.dragP != null) {
         this.dragP = null;
         return true;
      }

      for (SettingElement settingElement : this.computeValue_3100()) {
         if (settingElement.checkCondition_2911(d, e, click.button())) {
            return true;
         }
      }

      return super.mouseReleased(click);
   }

   public boolean mouseDragged(Click click, double d, double e) {
      double f = UtilComponent279.computeValue_3580(click.x(), 2);
      double g = UtilComponent279.computeValue_3580(click.y(), 2);
      int i = click.button();
      if (this.dragP != null) {
         this.dragP.floatValueField1 = Math.max(0.0F, Math.min(this.integerField2 - 124.0F, (float)f - this.dragDX));
         this.dragP.floatValueField2 = Math.max(0.0F, Math.min(this.integerField3 - 26.0F - 4.0F, (float)g - this.dragDY));
         return true;
      }

      this.search.performAction_2941(f, g, i);
      double h = UtilComponent279.computeValue_3580(d, 2);
      double j = UtilComponent279.computeValue_3580(e, 2);

      for (SettingElement settingElement : this.computeValue_3100()) {
         if (settingElement.checkCondition_2916(f, g, i, h, j)) {
            return true;
         }
      }

      return super.mouseDragged(click, d, e);
   }

   public boolean mouseScrolled(double d, double e, double f, double g) {
      double h = UtilComponent279.computeValue_3580(d, 2);
      double i = UtilComponent279.computeValue_3580(e, 2);

      for (SettingElement settingElement : this.computeValue_3100()) {
         if (settingElement.checkCondition_3026(h, i, g)) {
            return true;
         }
      }

      for (int j = this.order.size() - 1; j >= 0; j--) {
         ScreenComponent215.NestedType390 nestedType390 = this.order.get(j);
         if (!(nestedType390.show < 0.5F)
            && UtilComponent279.checkCondition_3576(h, i, nestedType390.floatValueField1, nestedType390.floatValueField2, 124.0F, nestedType390.totalH)) {
            nestedType390.scrollT -= (float)g * 18.0F;
            nestedType390.scrollT = Math.max(0.0F, Math.min(Math.max(0.0F, nestedType390.contentH - nestedType390.bodyMax), nestedType390.scrollT));
            return true;
         }
      }

      return super.mouseScrolled(d, e, f, g);
   }

   public boolean keyPressed(KeyInput keyInput) {
      int i = keyInput.key();
      int j = keyInput.scancode();
      int k = keyInput.comp_4797();
      if (this.closing) {
         return true;
      }

      for (Module module : this.all) {
         if (module.isBinding()) {
            if (i != 261 && i != 259 && i != 256) {
               module.setKeybind(i);
            } else {
               module.setKeybind(-1);
            }

            module.setBinding(false);
            MenuRenderer.playClickSound(1.5F, 0.8F);
            return true;
         }
      }

      if (i == 256) {
         for (SettingElement settingElement1 : this.computeValue_3100()) {
            if (settingElement1 instanceof KeybindElement keybindElement && keybindElement.isFlagField1_2981()) {
               settingElement1.checkCondition_2983(i, j, k);
               return true;
            }
         }

         if (this.search.isFlagField7_2939()) {
            this.search.performAction_2950(false);
            return true;
         } else {
            this.close();
            return true;
         }
      } else {
         if (i == 344) {
            this.close();
            return true;
         }

         if (i == 70 && (k & 2) != 0) {
            this.search.performAction_2950(!this.search.isFlagField7_2939());
            return true;
         }

         if (this.search.isFlagField7_2939()) {
            this.search.performAction_2963(i, j, k);
            return true;
         }

         for (SettingElement settingElement : this.computeValue_3100()) {
            if (settingElement.checkCondition_2983(i, j, k)) {
               return true;
            }
         }

         return super.keyPressed(keyInput);
      }
   }

   public boolean charTyped(CharInput charInput) {
      char c = (char)charInput.codepoint();
      int i = charInput.modifiers();
      if (this.search.isFlagField7_2939()) {
         this.search.performAction_2958(c, i);
         return true;
      }

      for (SettingElement settingElement : this.computeValue_3100()) {
         if (settingElement.checkCondition_3025(c, i)) {
            return true;
         }
      }

      return super.charTyped(charInput);
   }

   public void close() {
      if (!this.closing) {
         this.closing = true;
         this.dragP = null;
         MenuRenderer.close();
         CursorUtil.performAction_3422(CursorUtil.State404.DEFAULT);
         this.search.performAction_2950(false);

         for (Module module : this.all) {
            module.setBinding(false);
            if (module.isExpanded()) {
               for (SettingElement settingElement : module.getSettingElements()) {
                  if (settingElement instanceof KeybindElement keybindElement) {
                     keybindElement.performAction_2982();
                  }
               }
            }
         }
      }
   }

   private void performAction_3088() {
      if (!this.finished) {
         this.finished = true;
         if (current == this) {
            current = null;
         }

         if (this.client != null) {
            this.client.setScreen(this.parent);
         }

         if (this.onClosed != null) {
            this.onClosed.run();
         }
      }
   }

   private static final class NestedType390 {
      final Category cat;
      float floatValueField1 = -1.0F;
      float floatValueField2 = 46.0F;
      boolean open = true;
      float openAnim = 1.0F;
      float show = 1.0F;
      float scroll;
      float scrollT;
      float contentH;
      float bodyMax;
      float bodyH;
      float totalH;
      List<Module> mods = new ArrayList<>();
      List<Module> vis = new ArrayList<>();

      NestedType390(Category category) {
         this.cat = category;
      }
   }

   private static final class NestedType391 {
      float floatValueField1;
      float floatValueField2;
      float floatValueField3;
      float floatValueField4;
      float hover;
      float press;
      float tog = -1.0F;
      float bind;
      float ripple = -1.0F;
      float floatValueField5;
      float floatValueField6;
   }
}
