package aethereal.ui.screen;

import aethereal.core.Category;
import aethereal.core.Module;
import aethereal.render.Animation;
import aethereal.render.RenderComponent186;
import aethereal.render.RenderComponent193;
import aethereal.ui.element.KeybindElement;
import aethereal.ui.element.SettingElement;
import aethereal.util.KeyUtil;
import aethereal.util.UtilComponent279;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.client.gui.DrawContext;
import org.joml.Matrix3x2fStack;
import org.joml.Vector4f;

public class ScreenComponent217 {
   private static final float HEAD = 29.0F;
   private static final float GAP = 5.0F;
   private static final float RADIUS = 6.0F;
   private final Vector4f vector4fField1 = new Vector4f(0.0F, 0.0F, 300.0F, 200.0F);
   private final Animation animationField2 = new Animation();
   private final Animation animationField3 = new Animation();
   private final Category category;
   private List<Module> listField4;
   private Module moduleField5;
   private final Map<Module, ScreenComponent217.NestedType392> cards = new IdentityHashMap<>();
   private float enterTime = -10.0F;
   private float emptyFade;

   public ScreenComponent217(Category category) {
      this.category = categoryx;
   }

   public void getTime_3128() {
      this.enterTime = MenuRenderer.time;
   }

   public void performAction_3119(DrawContext drawContext, int i, int j, float f, float g) {
      if (this.listField4 != null) {
         Matrix3x2fStack matrix3x2fStack = drawContext.getMatrices();
         float h = this.vector4fField1.x;
         float k = this.vector4fField1.y;
         float l = this.vector4fField1.z;
         float m = this.vector4fField1.w;
         float n = l;
         int o = MenuRenderer.getAccent();
         int p = this.listField4.size();
         float q = 0.0F;

         for (Module module : this.listField4) {
            q += this.computeValue_3124(module) + 5.0F;
         }

         if (p > 0) {
            q -= 5.0F;
         }

         float bz = this.animationField2.computeValue_2534(Math.min(0.0F, m - q), 0.0F, 8.0F);
         this.moduleField5 = null;
         float ca = 0.0F;
         RenderComponent193.performAction_2681(matrix3x2fStack, h - 2.0F, k, l + 4.0F, m);

         for (int r = 0; r < p; r++) {
            Module module1 = this.listField4.get(r);
            ScreenComponent217.NestedType392 nestedType392 = this.cards.computeIfAbsent(module1, module2 -> new ScreenComponent217.NestedType392());
            float s = this.computeValue_3124(module1);
            float t = h;
            float u = k + bz + ca;
            ca += s + 5.0F;
            nestedType392.floatValueField1 = t;
            nestedType392.floatValueField2 = u;
            nestedType392.floatValueField3 = n;
            nestedType392.floatValueField4 = s;
            module1.getHoverAnimation().performAction_2535(0.0F, 1.0F, 0.5F, RenderComponent186.contract372Field9, f);
            module1.getHoverAnimation().performAction_2528(module1.isExpanded());
            boolean bl = module1.isEnabled();
            boolean bl1 = module1.isBinding();
            boolean bl2 = module1.getKeybind() != -1;
            boolean bl3 = j >= k && j <= k + m && UtilComponent279.checkCondition_3576(i, j, t, u, n, 29.0F);
            if (bl3) {
               this.moduleField5 = module1;
            }

            nestedType392.hover = MenuRenderer.approach(nestedType392.hover, bl3 ? 1.0F : 0.0F, 14.0F);
            nestedType392.press = MenuRenderer.approach(nestedType392.press, 0.0F, 12.0F);
            if (nestedType392.tog < 0.0F) {
               nestedType392.tog = bl ? 1.0F : 0.0F;
            }

            nestedType392.tog = MenuRenderer.approach(nestedType392.tog, bl ? 1.0F : 0.0F, 12.0F);
            nestedType392.bind = MenuRenderer.approach(nestedType392.bind, !bl2 && !bl1 ? (bl3 ? 0.45F : 0.0F) : 1.0F, 12.0F);
            if (!(u + s < k) && !(u > k + m)) {
               float v = MenuRenderer.time - this.enterTime - (r >> 1) * 0.05F;
               float w = MenuRenderer.easeOutCubic(MenuRenderer.clamp01(v / 0.35F));
               float x = g * w;
               if (!(x <= 0.001F)) {
                  float y = u + (1.0F - w) * 12.0F;
                  float z = nestedType392.press * 1.3F;
                  float ba = t + z;
                  float bb = y + z;
                  float bc = n - z * 2.0F;
                  float bd = s - z * 2.0F;
                  if (nestedType392.hover > 0.01F) {
                     MenuRenderer.fillRoundedRect(
                        matrix3x2fStack,
                        ba - 1.0F,
                        bb - 1.0F,
                        bc + 2.0F,
                        bd + 2.0F,
                        7.0F,
                        MenuRenderer.withAlpha(16777215, 0.035F * nestedType392.hover * x)
                     );
                  }

                  MenuRenderer.fillRoundedRect(
                     matrix3x2fStack,
                     ba,
                     bb,
                     bc,
                     bd,
                     6.0F,
                     MenuRenderer.withAlpha(
                        MenuRenderer.blendRgb(1446428, 2300717, nestedType392.hover), MenuRenderer.glassAlpha(0.94F) * x
                     )
                  );
                  MenuRenderer.fillRoundedRect(
                     matrix3x2fStack,
                     ba + 6.0F,
                     bb + 0.5F,
                     bc - 12.0F,
                     1.0F,
                     0.5F,
                     MenuRenderer.withAlpha(16777215, (0.05F + 0.04F * nestedType392.hover) * x)
                  );
                  if (nestedType392.tog > 0.01F) {
                     MenuRenderer.fillRoundedRect(matrix3x2fStack, ba, bb, bc, bd, 6.0F, MenuRenderer.replaceAlpha(o, 0.1F * nestedType392.tog * x));
                     MenuRenderer.fillRoundedRect(
                        matrix3x2fStack, ba - 1.0F, bb + 6.0F, 5.0F, 17.0F, 2.5F, MenuRenderer.replaceAlpha(o, 0.14F * nestedType392.tog * x)
                     );
                     MenuRenderer.fillRoundedRect(
                        matrix3x2fStack, ba, bb + 8.0F, 2.0F, 13.0F, 1.0F, MenuRenderer.replaceAlpha(o, nestedType392.tog * x)
                     );
                     MenuRenderer.strokeRoundedRect(
                        matrix3x2fStack, ba, bb, bc, bd, 6.0F, 0.5F, MenuRenderer.replaceAlpha(o, 0.38F * nestedType392.tog * x)
                     );
                  }

                  if (nestedType392.hover > 0.01F) {
                     MenuRenderer.strokeRoundedRect(
                        matrix3x2fStack, ba, bb, bc, bd, 6.0F, 0.5F, MenuRenderer.withAlpha(16777215, 0.11F * nestedType392.hover * x)
                     );
                  }

                  if (nestedType392.ripple >= 0.0F) {
                     nestedType392.ripple = nestedType392.ripple + MenuRenderer.floatValueField1 * 1.7F;
                     if (nestedType392.ripple >= 1.0F) {
                        nestedType392.ripple = -1.0F;
                     } else {
                        RenderComponent193.performAction_2681(matrix3x2fStack, ba, bb, bc, bd);
                        float be = MenuRenderer.easeOutCubic(nestedType392.ripple) * bc * 0.95F;
                        MenuRenderer.fillCircle(
                           matrix3x2fStack,
                           ba + nestedType392.floatValueField5,
                           bb + nestedType392.floatValueField6,
                           be,
                           MenuRenderer.replaceAlpha(o, 0.22F * (1.0F - nestedType392.ripple) * x)
                        );
                        RenderComponent193.performAction_2680(matrix3x2fStack);
                     }
                  }

                  float cf = 24.0F;
                  float bf = 12.0F;
                  float bg = ba + bc - 11.0F - cf;
                  float bh = bb + 14.5F;
                  boolean bl4 = module1.getSettingElements().stream().anyMatch(settingElement1 -> settingElement1.checkCondition_3023());
                  float bi = bg - 10.0F;
                  float bj = bl4 ? bi - 7.0F : bg - 7.0F;
                  String string = bl1 ? "?" : (bl2 ? KeyUtil.getLabel_3478(module1.getKeybind()) : "NONE");
                  float bk = MenuRenderer.iconWidth("C", 6.0F);
                  float bm = 5.0F + bk + 3.0F + MenuRenderer.textWidth(string, 6.0F) + 5.0F;
                  float bn = bj - bm;
                  float bo = ba + 11.0F;
                  float bp = bn - 7.0F - bo;
                  int bq = MenuRenderer.replaceAlpha(MenuRenderer.blendArgb(MenuRenderer.getTextColor(), o, nestedType392.tog), x);
                  MenuRenderer.drawText(
                     matrix3x2fStack,
                     MenuRenderer.ellipsize(module1.getName().toUpperCase(Locale.ROOT), 7.25F, bp),
                     bo + nestedType392.hover * 1.5F,
                     bb + 13.0F,
                     7.25F,
                     bq
                  );
                  String string1 = module1.getDescription() == null ? "" : module1.getDescription().toUpperCase(Locale.ROOT);
                  if (!string1.isEmpty()) {
                     MenuRenderer.drawText(
                        matrix3x2fStack,
                        MenuRenderer.ellipsize(string1, 5.5F, bp),
                        bo + nestedType392.hover * 1.5F,
                        bb + 24.5F,
                        5.5F,
                        MenuRenderer.replaceAlpha(MenuRenderer.getMutedTextColor(), (0.72F + 0.28F * nestedType392.hover) * x)
                     );
                  }

                  MenuRenderer.fillRoundedRect(
                     matrix3x2fStack,
                     bg - 3.0F,
                     bh - bf / 2.0F - 3.0F,
                     cf + 6.0F,
                     bf + 6.0F,
                     9.0F,
                     MenuRenderer.replaceAlpha(o, 0.14F * nestedType392.tog * x)
                  );
                  MenuRenderer.fillRoundedRect(
                     matrix3x2fStack,
                     bg,
                     bh - bf / 2.0F,
                     cf,
                     bf,
                     6.0F,
                     MenuRenderer.withAlpha(MenuRenderer.blendRgb(3814213, o & 16777215, nestedType392.tog), 0.96F * x)
                  );
                  float br = 8.0F + 2.5F * (float)Math.sin(Math.PI * nestedType392.tog);
                  float bs = bg + 2.0F + (cf - 4.0F - br) * nestedType392.tog;
                  MenuRenderer.fillRoundedRect(matrix3x2fStack, bs, bh - 4.0F, br, 8.0F, 4.0F, MenuRenderer.withAlpha(16777215, x));
                  if (bl4) {
                     float bt = module1.getHoverAnimation().getFloatValueField4_2530();
                     int bu = MenuRenderer.replaceAlpha(
                        MenuRenderer.blendArgb(MenuRenderer.getMutedTextColor(), o, bt), (0.55F + 0.45F * nestedType392.hover) * x
                     );

                     for (int bv = -1; bv <= 1; bv++) {
                        MenuRenderer.fillCircle(matrix3x2fStack, bi + bv * 3.0F * bt, bh + bv * 3.0F * (1.0F - bt), 1.15F, bu);
                     }
                  }

                  if (nestedType392.bind > 0.02F) {
                     float cg = bl1 ? 0.6F + 0.4F * (float)Math.sin(MenuRenderer.time * 8.0F) : 1.0F;
                     float ci = nestedType392.bind * cg * x;
                     MenuRenderer.fillRoundedRect(matrix3x2fStack, bn, bh - 5.0F, bm, 10.0F, 3.0F, MenuRenderer.replaceAlpha(o, 0.16F * ci));
                     MenuRenderer.strokeRoundedRect(matrix3x2fStack, bn, bh - 5.0F, bm, 10.0F, 3.0F, 0.5F, MenuRenderer.replaceAlpha(o, 0.45F * ci));
                     MenuRenderer.drawIcon(
                        matrix3x2fStack, "C", bn + 5.0F, bh, 6.0F, MenuRenderer.replaceAlpha(MenuRenderer.getTextColor(), ci)
                     );
                     MenuRenderer.drawText(
                        matrix3x2fStack, string, bn + 5.0F + bk + 3.0F, bh, 6.0F, MenuRenderer.replaceAlpha(MenuRenderer.getTextColor(), ci)
                     );
                  }

                  float ch = module1.getHoverAnimation().getFloatValueField4_2530();
                  if (ch > 0.0F) {
                     MenuRenderer.fillRoundedRect(
                        matrix3x2fStack, ba + 9.0F, bb + 29.0F - 0.5F, bc - 18.0F, 1.0F, 0.0F, MenuRenderer.withAlpha(16777215, 0.07F * ch * x)
                     );
                     RenderComponent193.performAction_2681(matrix3x2fStack, ba, bb, bc, bd);
                     float cj = bb + 29.0F + 3.0F - 4.0F * (1.0F - ch);
                     float ck = 0.0F;

                     for (SettingElement settingElement : module1.getSettingElements()) {
                        settingElement.getAnimationField2_3030().performAction_2528(settingElement.checkCondition_3023());
                        settingElement.getAnimationField2_3030().performAction_2535(0.0F, 1.0F, 0.4F, RenderComponent186.contract372Field9, f);
                        float bw = settingElement.getAnimationField2_3030().getFloatValueField4_2530();
                        if (bw > 0.0F) {
                           float bx = cj + ck - 4.0F * (1.0F - bw);
                           float by = cj + (bx - cj) * ch;
                           settingElement.getVector4fField3_3024().set(ba + 4.5F, by, bc - 9.0F, settingElement.getVector4fField3_3024().w());
                           settingElement.performAction_2918(drawContext, i, j, f, ch * bw * x);
                           ck += (settingElement.getVector4fField3_3024().w() + 4.0F) * bw;
                        }
                     }

                     RenderComponent193.performAction_2680(matrix3x2fStack);
                  }
               }
            }
         }

         RenderComponent193.performAction_2680(matrix3x2fStack);
         this.emptyFade = MenuRenderer.approach(this.emptyFade, p == 0 ? 1.0F : 0.0F, 8.0F);
         if (this.emptyFade > 0.01F) {
            float cb = k + m / 2.0F + (1.0F - this.emptyFade) * 6.0F;
            MenuRenderer.fillCircle(matrix3x2fStack, h + l / 2.0F, cb - 22.0F, 11.0F, MenuRenderer.replaceAlpha(o, 0.1F * this.emptyFade * g));
            MenuRenderer.fillCircle(matrix3x2fStack, h + l / 2.0F, cb - 22.0F, 5.0F, MenuRenderer.replaceAlpha(o, 0.3F * this.emptyFade * g));
            MenuRenderer.drawCenteredText(
               matrix3x2fStack, "NO MODULES FOUND", h + l / 2.0F, cb, 8.0F, MenuRenderer.withAlpha(16777215, 0.5F * this.emptyFade * g)
            );
            MenuRenderer.drawCenteredText(
               matrix3x2fStack,
               "TRY ANOTHER SEARCH OR TURN OFF THE FILTER",
               h + l / 2.0F,
               cb + 12.0F,
               5.5F,
               MenuRenderer.withAlpha(16777215, 0.25F * this.emptyFade * g)
            );
         }

         if (q > m + 1.0F) {
            float cc = MenuRenderer.clamp01(-bz / (q - m));
            float cd = Math.max(18.0F, m * m / q);
            float ce = k + cc * (m - cd);
            MenuRenderer.fillRoundedRect(matrix3x2fStack, h + l + 4.0F, k, 2.0F, m, 1.0F, MenuRenderer.withAlpha(16777215, 0.04F * g));
            MenuRenderer.fillRoundedRect(matrix3x2fStack, h + l + 3.0F, ce - 0.5F, 4.0F, cd + 1.0F, 2.0F, MenuRenderer.replaceAlpha(o, 0.12F * g));
            MenuRenderer.fillRoundedRect(matrix3x2fStack, h + l + 4.0F, ce, 2.0F, cd, 1.0F, MenuRenderer.replaceAlpha(o, 0.8F * g));
         }
      }
   }

   public void performAction_3118(DrawContext drawContext, double d, double e, float f) {
      if (this.listField4 != null) {
         for (Module module : this.listField4) {
            if (module.isExpanded() && !(module.getHoverAnimation().getFloatValueField4_2530() <= 0.01F)) {
               for (SettingElement settingElement : module.getSettingElements()) {
                  settingElement.performAction_2917(drawContext, d, e, f);
               }
            }
         }
      }
   }

   private float computeValue_3124(Module module) {
      float f = 0.0F;

      for (SettingElement settingElement : module.getSettingElements()) {
         f += (settingElement.getVector4fField3_3024().w() + 4.0F) * settingElement.getAnimationField2_3030().getFloatValueField4_2530();
      }

      return 29.0F + f * module.getHoverAnimation().getFloatValueField4_2530();
   }

   private Module computeValue_3133(double d, double e) {
      if (this.listField4 == null) {
         return null;
      }

      if (!(d < this.vector4fField1.x)
         && !(d > this.vector4fField1.x + this.vector4fField1.z)
         && !(e < this.vector4fField1.y)
         && !(e > this.vector4fField1.y + this.vector4fField1.w)) {
         for (Module module : this.listField4) {
            ScreenComponent217.NestedType392 nestedType392 = this.cards.get(module);
            if (nestedType392 != null) {
               float f = Math.max(nestedType392.floatValueField2, this.vector4fField1.y);
               float g = Math.min(nestedType392.floatValueField2 + 29.0F, this.vector4fField1.y + this.vector4fField1.w);
               if (e >= f
                  && e <= g
                  && UtilComponent279.checkCondition_3576(
                     d, e, nestedType392.floatValueField1, nestedType392.floatValueField2, nestedType392.floatValueField3, 29.0F
                  )) {
                  return module;
               }
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public boolean checkCondition_3143(double d, double e, int i) {
      if (this.listField4 == null) {
         return false;
      }

      for (Module module : this.listField4) {
         if (module.isBinding()) {
            module.setKeybind(-100 + i);
            module.setBinding(false);
            MenuRenderer.playClickSound(1.5F, 0.8F);
            return true;
         }
      }

      Module module1 = this.computeValue_3133(d, e);
      if (module1 != null) {
         ScreenComponent217.NestedType392 nestedType392 = this.cards.get(module1);
         if (nestedType392 != null) {
            nestedType392.press = 1.0F;
            nestedType392.ripple = 0.0F;
            nestedType392.floatValueField5 = (float)d - nestedType392.floatValueField1;
            nestedType392.floatValueField6 = (float)e - nestedType392.floatValueField2;
         }

         if (i == 0) {
            module1.toggle();
            MenuRenderer.playToggleSound(module1.isEnabled());
            return true;
         }

         if (i == 1) {
            module1.setExpanded(!module1.isExpanded());
            MenuRenderer.playClickSound(module1.isExpanded() ? 1.3F : 0.9F, 0.6F);
            return true;
         }

         if (i == 2) {
            this.listField4.forEach(module2 -> this.performAction_3126(module2));
            MenuRenderer.playClickSound(1.7F, 0.6F);
            return true;
         }
      }

      return this.listField4
         .stream()
         .filter(Module::isExpanded)
         .flatMap(module2 -> module2.getSettingElements().stream())
         .filter(SettingElement::checkCondition_3023)
         .anyMatch(settingElement -> settingElement.checkCondition_2915(d, e, i));
   }

   public boolean checkCondition_3141(double d, double e, int i) {
      return this.listField4 == null
         ? false
         : this.listField4
            .stream()
            .filter(Module::isExpanded)
            .flatMap(module -> module.getSettingElements().stream())
            .filter(SettingElement::checkCondition_3023)
            .anyMatch(settingElement -> settingElement.checkCondition_2911(d, e, i));
   }

   public boolean checkCondition_3113(double d, double e, int i, double f, double g) {
      return this.listField4 == null
         ? false
         : this.listField4
            .stream()
            .filter(Module::isExpanded)
            .flatMap(module -> module.getSettingElements().stream())
            .filter(SettingElement::checkCondition_3023)
            .anyMatch(settingElement -> settingElement.checkCondition_2916(d, e, i, f, g));
   }

   public boolean checkCondition_3115(int i, int j, int k) {
      if (this.listField4 == null) {
         return false;
      }

      for (Module module : this.listField4) {
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

      return this.listField4
         .stream()
         .filter(Module::isExpanded)
         .flatMap(module1 -> module1.getSettingElements().stream())
         .filter(SettingElement::checkCondition_3023)
         .anyMatch(settingElement -> settingElement.checkCondition_2983(i, j, k));
   }

   public boolean checkCondition_3111(char c, int i) {
      return this.listField4 == null
         ? false
         : this.listField4
            .stream()
            .filter(Module::isExpanded)
            .flatMap(module -> module.getSettingElements().stream())
            .filter(SettingElement::checkCondition_3023)
            .anyMatch(settingElement -> settingElement.checkCondition_3025(c, i));
   }

   public boolean checkCondition_3112(double d, double e, double f) {
      if (!UtilComponent279.checkCondition_3576(d, e, this.vector4fField1.x, this.vector4fField1.y, this.vector4fField1.z, this.vector4fField1.w)) {
         return false;
      }

      if (this.listField4 != null
         && this.listField4
            .stream()
            .filter(Module::isExpanded)
            .flatMap(module -> module.getSettingElements().stream())
            .filter(SettingElement::checkCondition_3023)
            .anyMatch(settingElement -> settingElement.checkCondition_3026(d, e, f))) {
         return true;
      }

      this.animationField2.performAction_2532((float)f * 15.0F);
      return true;
   }

   public boolean checkCondition_3123() {
      if (this.listField4 == null) {
         return false;
      }

      for (Module module : this.listField4) {
         if (module.isBinding()) {
            return true;
         }

         if (module.isExpanded()) {
            for (SettingElement settingElement : module.getSettingElements()) {
               if (settingElement instanceof KeybindElement keybindElement && keybindElement.isFlagField1_2981()) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public void performAction_3129() {
      if (this.listField4 != null) {
         for (Module module : this.listField4) {
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

   private void performAction_3126(Module module) {
      module.setBinding(module == this.moduleField5 && !module.isBinding());
   }

   public void setListField4_3127(List<Module> list) {
      this.listField4 = list;
   }

   public void setModuleField5_3145(Module module) {
      this.moduleField5 = module;
   }

   public Vector4f getVector4fField1_3110() {
      return this.vector4fField1;
   }

   public Animation getAnimationField2_3131() {
      return this.animationField2;
   }

   public Animation getAnimationField3_3140() {
      return this.animationField3;
   }

   public Category getCategory_3130() {
      return this.category;
   }

   public List<Module> getListField4_3120() {
      return this.listField4;
   }

   public Module getModuleField5_3109() {
      return this.moduleField5;
   }

   private static final class NestedType392 {
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
