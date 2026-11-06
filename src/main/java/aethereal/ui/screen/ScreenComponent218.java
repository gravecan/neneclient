package aethereal.ui.screen;

import aethereal.config.SettingsPersistence;
import aethereal.core.Category;
import aethereal.core.Module;
import aethereal.core.NeneClient;
import aethereal.render.Animation;
import aethereal.render.RenderComponent184;
import aethereal.render.RenderComponent192;
import aethereal.render.pipeline.PipelineComponent196;
import aethereal.ui.element.TextField;
import aethereal.util.CursorUtil;
import aethereal.util.UtilComponent279;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Text;
import org.joml.Matrix3x2fStack;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class ScreenComponent218 extends Screen {
   private static final String[] CLIENT_ITEMS = new String[]{"Profile", "Themes", "Friends"};
   private static final float SIDE = 90.0F;
   private static final float PAD = 12.0F;
   private static final float DRAG_H = 36.0F;
   private static final float BRAND_Y = 10.0F;
   private static final float SEARCH_Y = 38.0F;
   private static final float SEARCH_H = 18.0F;
   private static final float LIST_START = 68.0F;
   private static final float SECTION_GAP = 14.0F;
   private static final float FOOT_H = 48.0F;
   private static final float CONTENT_TOP = 42.0F;
   private static final float CHIP_W = 58.0F;
   private static final float CHIP_H = 18.0F;
   private static float posX;
   private static float posY;
   private static boolean hasPos;
   private final Screen parent;
   private final TextField textFieldField1;
   private final Animation animationField2;
   private final List<ScreenComponent217> listField3;
   private final ScreenComponent217 searchPanel;
   private final ProfilePanel profilePage = new ProfilePanel();
   private final ScreenComponent227 themesPage = new ScreenComponent227();
   private final ScreenComponent216 friendsPage = new ScreenComponent216();
   private String stringField4;
   private int tab = SettingsPersistence.getGuiTab_0524();
   private int cat = SettingsPersistence.getGuiCategory_0526();
   private boolean closing;
   private boolean finished;
   private boolean opened;
   private boolean dragging;
   private boolean onlyEnabled;
   private float dragDX;
   private float dragDY;
   private float openT;
   private float contentFade;
   private float closeHover;
   private float chipHover;
   private float chipOn;
   private float shownCount;
   private float searchHover;
   private float selY = -1.0F;
   private final int catN;
   private final float[] itemHover;
   private final float[] itemSel;
   private final float[] catCount;
   private float rowH = 20.0F;
   private float rowStep = 22.0F;
   private float floatValueField5;
   private float floatValueField6;
   private float floatValueField7;
   private float floatValueField8;
   private float chipX;
   private float chipY;
   private DrawContext lateContext;
   private double lateMouseX;
   private double lateMouseY;
   private float lateDelta;
   private boolean latePending;
   private String lastSearch = null;
   private static final Comparator<Module> BY_NAME = Comparator.comparing(Module::getName, String.CASE_INSENSITIVE_ORDER);

   public ScreenComponent218(Text text, Screen screen) {
      super(text);
      this.parent = screen;
      this.textFieldField1 = new TextField(TextField.State384.GUI);
      this.animationField2 = new Animation();
      this.listField3 = new ArrayList<>();

      for (Category category : Category.values()) {
         this.listField3.add(new ScreenComponent217(category));
      }

      this.searchPanel = new ScreenComponent217(Category.values()[0]);
      this.catN = Category.values().length;
      this.itemHover = new float[this.catN + CLIENT_ITEMS.length];
      this.itemSel = new float[this.catN + CLIENT_ITEMS.length];
      this.catCount = new float[this.catN];
      this.textFieldField1.setStringField3_2969("Search modules...");
      this.performAction_3154();
      this.listField3.get(this.cat).getTime_3128();
   }

   public ScreenComponent218(Text text) {
      this(text, null);
   }

   protected void init() {
      if (!this.opened) {
         this.opened = true;
         MenuRenderer.playBackSound();
      }
   }

   public void renderBackground(DrawContext drawContext, int i, int j, float f) {
   }

   protected void applyBlur(DrawContext drawContext) {
   }

   public void renderInGameBackground(DrawContext drawContext) {
   }

   private ScreenComponent217 computeValue_3158() {
      return this.lastSearch != null && !this.lastSearch.isEmpty() ? this.searchPanel : this.listField3.get(this.cat);
   }

   private void performAction_3154() {
      String string = this.textFieldField1.getStringBuilderField4_2956().toString().trim().toLowerCase();
      List list = NeneClient.getInstance().getProcessors().getModules().getEntries().stream().toList();
      boolean bl = this.onlyEnabled;

      for (ScreenComponent217 screenComponent217 : this.listField3) {
         screenComponent217.setListField4_3127(
            list.stream()
               .filter(module -> module.getCategory() == screenComponent217.getCategory_3130())
               .filter(module -> !bl || module.isEnabled())
               .sorted(BY_NAME)
               .toList()
         );
      }

      this.searchPanel
         .setListField4_3127(
            list.stream()
               .filter(module -> !string.isEmpty() && module.getName().toLowerCase().contains(string))
               .filter(module -> !bl || module.isEnabled())
               .sorted(BY_NAME)
               .toList()
         );
      if (this.lastSearch != null && !this.lastSearch.equals(string)) {
         this.searchPanel.getTime_3128();
      }

      this.lastSearch = string;
   }

   public void render(DrawContext drawContext, int i, int j, float f) {
      MenuRenderer.updateFrameTime();
      this.openT = MenuRenderer.approach(this.openT, this.closing ? 0.0F : 1.0F, 8.0F);
      if (this.closing && this.openT <= 0.02F) {
         this.performAction_3153();
      } else {
         String string = this.textFieldField1.getStringBuilderField4_2956().toString().trim().toLowerCase();
         if (this.lastSearch == null || !this.lastSearch.equals(string)) {
            this.performAction_3154();
         }

         this.lateContext = drawContext;
         this.lateMouseX = UtilComponent279.computeValue_3580(i, 2);
         this.lateMouseY = UtilComponent279.computeValue_3580(j, 2);
         this.lateDelta = f;
         this.latePending = true;
      }
   }

   private static String computeValue_3156(String string) {
      return string != null && !string.isEmpty() ? Character.toUpperCase(string.charAt(0)) + string.substring(1).toLowerCase() : "";
   }

   private float computeValue_3148(int i) {
      float f = this.floatValueField6 + 68.0F;
      return i < this.catN ? f + i * this.rowStep : f + this.catN * this.rowStep + 14.0F + (i - this.catN) * this.rowStep;
   }

   private int computeValue_3167() {
      return this.tab == 0 ? this.cat : this.catN + this.tab - 1;
   }

   private void performAction_3157(int i) {
      if (this.tab != i) {
         this.tab = i;
         this.contentFade = 0.0F;
         MenuRenderer.playHoverSound();
         SettingsPersistence.performAction_0531(this.tab, this.cat);
      }
   }

   private boolean checkCondition_3152(double d, double e) {
      return UtilComponent279.checkCondition_3576(d, e, this.floatValueField5 + 10.0F, this.floatValueField6 + 38.0F, 70.0F, 18.0F);
   }

   public void performAction_3155() {
      if (this.latePending && this.lateContext != null) {
         this.latePending = false;
         DrawContext drawContext = this.lateContext;
         double d = this.lateMouseX;
         double e = this.lateMouseY;
         float f = this.lateDelta;
         RenderComponent192.performAction_2676(drawContext, 2);
         Matrix3x2fStack matrix3x2fStack = drawContext.getMatrices();
         MinecraftClient minecraftClient = MinecraftClient.getInstance();
         int i = minecraftClient.getWindow().getScaledWidth();
         int j = minecraftClient.getWindow().getScaledHeight();
         this.floatValueField7 = Math.min(420.0F, i - 32.0F);
         this.floatValueField8 = Math.min(300.0F, j - 28.0F);
         if (hasPos) {
            posX = Math.max(0.0F, Math.min(i - this.floatValueField7, posX));
            posY = Math.max(0.0F, Math.min(j - this.floatValueField8, posY));
            this.floatValueField5 = posX;
            this.floatValueField6 = posY;
         } else {
            this.floatValueField5 = (i - this.floatValueField7) * 0.5F;
            this.floatValueField6 = (j - this.floatValueField8) * 0.5F;
         }

         float g = this.floatValueField8 - 68.0F - 48.0F - 14.0F;
         this.rowStep = Math.max(14.0F, Math.min(24.0F, g / (this.catN + CLIENT_ITEMS.length)));
         this.rowH = this.rowStep - 3.0F;
         float h = MenuRenderer.easeOutCubic(this.openT);
         MenuRenderer.fillRoundedRect(matrix3x2fStack, 0.0F, 0.0F, i, j, 0.0F, MenuRenderer.withAlpha(0, MenuRenderer.glassAlpha(0.55F) * h));
         if (MenuRenderer.particlesOn) {
            MenuRenderer.drawParticles(matrix3x2fStack, i, j, h);
         }

         float k = this.floatValueField5 + this.floatValueField7 / 2.0F;
         float l = this.floatValueField6 + this.floatValueField8 / 2.0F;
         float m = 0.9F + 0.1F * MenuRenderer.easeOutBack(this.openT);
         matrix3x2fStack.pushMatrix();
         matrix3x2fStack.translate(k, l + (1.0F - h) * 18.0F);
         matrix3x2fStack.scale(m, m);
         matrix3x2fStack.translate(-k, -l);
         this.performAction_3150(drawContext, matrix3x2fStack, (int)d, (int)e, f, h);
         matrix3x2fStack.popMatrix();
         if (this.tab == 0 && this.openT > 0.95F) {
            this.computeValue_3158().performAction_3118(drawContext, d, e, f);
         }

         float n = this.floatValueField6 + this.floatValueField8 + 10.0F;
         if (n + 8.0F < j) {
            MenuRenderer.drawCenteredText(
               matrix3x2fStack,
               "RSHIFT CLOSE   |   LMB TOGGLE   |   RMB SETTINGS   |   MMB BIND   |   CTRL+F SEARCH   |   DRAG TOP BAR TO MOVE",
               i / 2.0F,
               n,
               5.5F,
               MenuRenderer.withAlpha(16777215, 0.28F * h)
            );
         }

         PipelineComponent196.performAction_2728();
         RenderComponent192.performAction_2675(drawContext);
      }
   }

   private void performAction_3150(DrawContext drawContext, Matrix3x2fStack matrix3x2fStack, int i, int j, float f, float g) {
      int k = MenuRenderer.getAccent();
      RenderComponent184 renderComponent184 = MenuRenderer.getRenderer();
      float h = MenuRenderer.time;
      renderComponent184.performAction_2580(
         matrix3x2fStack,
         this.floatValueField5,
         this.floatValueField6,
         this.floatValueField7,
         this.floatValueField8,
         14.0F,
         MenuRenderer.withAlpha(0, 0.0F),
         1.0F,
         MenuRenderer.withAlpha(0, 0.5F * g),
         20.0F
      );
      renderComponent184.performAction_2562(
         matrix3x2fStack,
         this.floatValueField5,
         this.floatValueField6,
         this.floatValueField7,
         this.floatValueField8,
         14.0F,
         MenuRenderer.withAlpha(MenuRenderer.PANEL, MenuRenderer.glassAlpha(0.82F)),
         g
      );
      MenuRenderer.strokeRoundedRect(
         matrix3x2fStack,
         this.floatValueField5,
         this.floatValueField6,
         this.floatValueField7,
         this.floatValueField8,
         14.0F,
         0.8F,
         MenuRenderer.replaceAlpha(k, (0.22F + 0.08F * (float)Math.sin(h * 1.5F)) * g)
      );
      MenuRenderer.fillRoundedRect(
         matrix3x2fStack,
         this.floatValueField5 + 1.0F,
         this.floatValueField6 + 1.0F,
         89.0F,
         this.floatValueField8 - 2.0F,
         13.0F,
         MenuRenderer.withAlpha(0, MenuRenderer.glassAlpha(0.16F) * g)
      );
      MenuRenderer.fillRoundedRect(
         matrix3x2fStack,
         this.floatValueField5 + 90.0F,
         this.floatValueField6 + 12.0F,
         1.0F,
         this.floatValueField8 - 24.0F,
         0.0F,
         MenuRenderer.withAlpha(16777215, 0.05F * g)
      );
      float l = Math.min(this.floatValueField7 - 90.0F, this.floatValueField8) * 0.6F;
      float m = this.floatValueField5 + 90.0F + (this.floatValueField7 - 90.0F - l) / 2.0F;
      float n = this.floatValueField6 + (this.floatValueField8 - l) / 2.0F + (float)Math.sin(h * 0.8F) * 3.0F;
      MenuRenderer.drawTexture(matrix3x2fStack, MenuRenderer.LOGO, m, n, l, l, MenuRenderer.withAlpha(MenuRenderer.ACCENT, 0.025F * g));
      float o = 18.0F;
      float p = this.floatValueField5 + 14.0F;
      float q = this.floatValueField6 + 10.0F;
      MenuRenderer.fillCircle(
         matrix3x2fStack, p + o / 2.0F, q + o / 2.0F, 14.0F, MenuRenderer.replaceAlpha(k, (0.08F + 0.05F * (0.5F + 0.5F * (float)Math.sin(h * 2.0F))) * g)
      );
      MenuRenderer.drawTexture(matrix3x2fStack, MenuRenderer.LOGO, p, q, o, o, MenuRenderer.withAlpha(MenuRenderer.ACCENT, g));
      MenuRenderer.drawText(matrix3x2fStack, "NeneClient", p + o + 7.0F, q + o / 2.0F, 8.5F, MenuRenderer.withAlpha(16777215, g));
      this.contentFade = MenuRenderer.approach(this.contentFade, 1.0F, 9.0F);
      float r = this.contentFade * g;
      float s = this.floatValueField5 + 90.0F + 12.0F;
      float t = this.floatValueField7 - 90.0F - 24.0F;
      float u = this.floatValueField8 - 42.0F - 12.0F;
      this.performAction_3160(drawContext, matrix3x2fStack, i, j, f, g, r, k);
      String string;
      if (this.tab == 0) {
         string = this.lastSearch != null && !this.lastSearch.isEmpty() ? "Search" : computeValue_3156(Category.values()[this.cat].name());
      } else {
         string = CLIENT_ITEMS[this.tab - 1];
      }

      float v = this.floatValueField6 + 21.0F;
      MenuRenderer.drawText(matrix3x2fStack, string, s, v, 10.5F, MenuRenderer.withAlpha(16777215, r));
      float w = this.floatValueField5 + this.floatValueField7 - 28.0F;
      float x = this.floatValueField6 + 12.0F;
      this.closeHover = MenuRenderer.approach(this.closeHover, UtilComponent279.checkCondition_3576(i, j, w, x, 16.0F, 16.0F) ? 1.0F : 0.0F, 14.0F);
      MenuRenderer.fillRoundedRect(matrix3x2fStack, w, x, 16.0F, 16.0F, 8.0F, MenuRenderer.replaceAlpha(k, (0.06F + 0.3F * this.closeHover) * g));
      MenuRenderer.drawCenteredText(
         matrix3x2fStack, "x", w + 8.0F, x + 8.0F, 7.0F, MenuRenderer.withAlpha(MenuRenderer.blendRgb(9077648, 16777215, this.closeHover), g)
      );
      if (this.tab == 0) {
         matrix3x2fStack.pushMatrix();
         matrix3x2fStack.translate(0.0F, (1.0F - this.contentFade) * 8.0F);
         this.chipX = w - 10.0F - 58.0F;
         this.chipY = this.floatValueField6 + 12.0F - 1.0F;
         boolean bl = UtilComponent279.checkCondition_3576(i, j, this.chipX, this.chipY, 58.0F, 18.0F);
         this.chipHover = MenuRenderer.approach(this.chipHover, bl ? 1.0F : 0.0F, 14.0F);
         this.chipOn = MenuRenderer.approach(this.chipOn, this.onlyEnabled ? 1.0F : 0.0F, 12.0F);
         MenuRenderer.fillRoundedRect(
            matrix3x2fStack, this.chipX, this.chipY, 58.0F, 18.0F, 6.0F, MenuRenderer.withAlpha(16777215, (0.05F + 0.05F * this.chipHover) * r)
         );
         if (this.chipOn > 0.01F) {
            MenuRenderer.fillRoundedRect(
               matrix3x2fStack, this.chipX, this.chipY, 58.0F, 18.0F, 6.0F, MenuRenderer.replaceAlpha(k, 0.2F * this.chipOn * r)
            );
            MenuRenderer.strokeRoundedRect(
               matrix3x2fStack, this.chipX, this.chipY, 58.0F, 18.0F, 6.0F, 0.5F, MenuRenderer.replaceAlpha(k, 0.55F * this.chipOn * r)
            );
         }

         MenuRenderer.fillCircle(
            matrix3x2fStack,
            this.chipX + 10.0F,
            this.chipY + 9.0F,
            2.2F,
            MenuRenderer.replaceAlpha(MenuRenderer.blendArgb(MenuRenderer.withAlpha(9077648, 1.0F), k, this.chipOn), r)
         );
         MenuRenderer.drawText(
            matrix3x2fStack,
            "ENABLED",
            this.chipX + 17.0F,
            this.chipY + 9.0F,
            5.5F,
            MenuRenderer.withAlpha(MenuRenderer.blendRgb(9077648, 16777215, Math.max(this.chipOn, this.chipHover)), r)
         );
         ScreenComponent217 screenComponent217 = this.computeValue_3158();
         int y = screenComponent217.getListField4_3120() == null ? 0 : screenComponent217.getListField4_3120().size();
         this.shownCount = MenuRenderer.approach(this.shownCount, y, 14.0F);
         MenuRenderer.drawRightAlignedText(
            matrix3x2fStack, Math.round(this.shownCount) + " MODULES", this.chipX - 10.0F, v, 5.5F, MenuRenderer.withAlpha(16777215, 0.35F * r)
         );
         screenComponent217.getVector4fField1_3110().set(s, this.floatValueField6 + 42.0F, t, u);
         screenComponent217.performAction_3119(drawContext, i, j, f, r);
         matrix3x2fStack.popMatrix();
      } else if (this.tab == 1) {
         this.textFieldField1.performAction_2950(false);
         this.profilePage.render(matrix3x2fStack, s, this.floatValueField6 + 42.0F, t, u, g, i, j);
      } else if (this.tab == 2) {
         this.textFieldField1.performAction_2950(false);
         this.themesPage.performAction_3273(matrix3x2fStack, s, this.floatValueField6 + 42.0F, t, u, r, i, j);
      } else {
         this.textFieldField1.performAction_2950(false);
         this.friendsPage.performAction_3107(drawContext, matrix3x2fStack, s, this.floatValueField6 + 42.0F, t, u, r, i, j, f);
      }
   }

   private void performAction_3160(DrawContext drawContext, Matrix3x2fStack matrix3x2fStack, int i, int j, float f, float g, float bd, int k) {
      Category[] categorys = Category.values();
      float h = this.floatValueField5 + 10.0F;
      float l = 70.0F;
      this.textFieldField1.setVector2fField2_2944(new Vector2f(l, 18.0F));
      this.textFieldField1.setVector2fField1_2966(new Vector2f(h, this.floatValueField6 + 38.0F));
      this.textFieldField1.performAction_2964(drawContext, i, j, f, g);
      float m = this.floatValueField6 + 68.0F;
      MenuRenderer.drawText(matrix3x2fStack, "MODULES", h + 4.0F, m - 10.0F, 5.5F, MenuRenderer.withAlpha(16777215, 0.3F * g));
      float n = m + this.catN * this.rowStep + 7.0F - 1.0F;
      MenuRenderer.drawText(matrix3x2fStack, "CLIENT", h + 4.0F, n, 5.5F, MenuRenderer.withAlpha(16777215, 0.3F * g));
      int[] o = new int[categorys.length];
      NeneClient.getInstance().getProcessors().getModules().getEntries().forEach(module -> {
         if (module.isEnabled()) {
            o[module.getCategory().ordinal()]++;
         }
      });
      float p = this.computeValue_3148(this.computeValue_3167());
      if (this.selY < 0.0F) {
         this.selY = p;
      }

      this.selY = MenuRenderer.approach(this.selY, p, 16.0F);
      MenuRenderer.fillRoundedRect(
         matrix3x2fStack, h - 2.0F, this.selY - 2.0F, l + 4.0F, this.rowH + 4.0F, 9.0F, MenuRenderer.replaceAlpha(k, 0.06F * g)
      );
      MenuRenderer.fillRoundedRect(matrix3x2fStack, h, this.selY, l, this.rowH, 7.0F, MenuRenderer.replaceAlpha(k, 0.16F * g));
      MenuRenderer.strokeRoundedRect(matrix3x2fStack, h, this.selY, l, this.rowH, 7.0F, 0.5F, MenuRenderer.replaceAlpha(k, 0.34F * g));
      int q = this.catN + CLIENT_ITEMS.length;

      for (int r = 0; r < q; r++) {
         float s = this.computeValue_3148(r);
         boolean bl = UtilComponent279.checkCondition_3576(i, j, h, s, l, this.rowH);
         this.itemHover[r] = MenuRenderer.approach(this.itemHover[r], bl ? 1.0F : 0.0F, 14.0F);
         this.itemSel[r] = MenuRenderer.approach(this.itemSel[r], r == this.computeValue_3167() ? 1.0F : 0.0F, 12.0F);
         if (r != this.computeValue_3167()) {
            MenuRenderer.fillRoundedRect(matrix3x2fStack, h, s, l, this.rowH, 7.0F, MenuRenderer.withAlpha(16777215, 0.05F * this.itemHover[r] * g));
         }

         float t = Math.max(this.itemSel[r], this.itemHover[r]);
         float u = this.itemHover[r] * 2.0F + this.itemSel[r] * 2.0F;
         int v = MenuRenderer.withAlpha(MenuRenderer.blendRgb(9077648, 16777215, t), g);
         float w = s + this.rowH / 2.0F;
         int x = MenuRenderer.replaceAlpha(MenuRenderer.blendArgb(v, k, this.itemSel[r]), g);
         if (r < this.catN) {
            MenuRenderer.drawIcon(matrix3x2fStack, categorys[r].getIcon_0560(), h + 11.0F + u, w, 8.0F, x);
            this.catCount[r] = MenuRenderer.approach(this.catCount[r], o[r], 10.0F);
            int y = Math.round(this.catCount[r]);
            float z = h + 25.0F + u;
            float ba = l - (z - h) - 8.0F;
            if (y > 0) {
               String string = String.valueOf(y);
               float bb = MenuRenderer.textWidth(string, 5.5F) + 9.0F;
               float bc = h + l - bb - 6.0F;
               MenuRenderer.fillRoundedRect(matrix3x2fStack, bc, w - 5.0F, bb, 10.0F, 5.0F, MenuRenderer.replaceAlpha(k, 0.22F * g));
               MenuRenderer.drawCenteredText(matrix3x2fStack, string, bc + bb / 2.0F, w, 5.5F, MenuRenderer.replaceAlpha(k, g));
               ba = Math.max(0.0F, bc - z - 4.0F);
            }

            MenuRenderer.drawText(matrix3x2fStack, MenuRenderer.ellipsize(computeValue_3156(categorys[r].name()), 6.5F, ba), z, w, 6.5F, v);
         } else {
            MenuRenderer.fillCircle(matrix3x2fStack, h + 11.0F + u, w, 1.8F, x);
            MenuRenderer.drawText(matrix3x2fStack, CLIENT_ITEMS[r - this.catN], h + 25.0F + u, w, 6.5F, v);
         }
      }

      MenuRenderer.drawUserCard(matrix3x2fStack, h, this.floatValueField6 + this.floatValueField8 - 10.0F - 38.0F, l, g);
   }

   private void performAction_3166(int i) {
      if (this.cat != i || this.tab != 0) {
         MenuRenderer.playSelectSound();
         this.listField3.get(i).getTime_3128();
         this.contentFade = 0.0F;
      }

      this.cat = i;
      this.tab = 0;
      SettingsPersistence.performAction_0531(this.tab, this.cat);
   }

   public boolean mouseClicked(Click click, boolean bl) {
      if (this.closing) {
         return true;
      }

      double d = UtilComponent279.computeValue_3580(click.x(), 2);
      double e = UtilComponent279.computeValue_3580(click.y(), 2);
      int i = click.button();
      if (!UtilComponent279.checkCondition_3576(d, e, this.floatValueField5, this.floatValueField6, this.floatValueField7, this.floatValueField8)) {
         this.close();
         return true;
      }

      if (this.tab != 0 && this.checkCondition_3152(d, e)) {
         this.tab = 0;
         this.contentFade = 0.0F;
         SettingsPersistence.performAction_0531(this.tab, this.cat);
      }

      if (this.tab == 0) {
         this.textFieldField1.performAction_2959(d, e, i);
      } else {
         this.textFieldField1.performAction_2950(false);
      }

      if (this.checkCondition_3152(d, e)) {
         return true;
      }

      if (UtilComponent279.checkCondition_3576(d, e, this.floatValueField5 + this.floatValueField7 - 28.0F, this.floatValueField6 + 12.0F, 16.0F, 16.0F)) {
         MenuRenderer.playClickSound(0.8F, 0.8F);
         this.close();
         return true;
      }

      if (i != 0
         || !(e >= this.floatValueField6)
         || !(e <= this.floatValueField6 + 36.0F)
         || this.tab == 0 && UtilComponent279.checkCondition_3576(d, e, this.chipX, this.chipY, 58.0F, 18.0F)) {
         float f = this.floatValueField5 + 10.0F;
         int j = this.catN + CLIENT_ITEMS.length;

         for (int k = 0; k < j; k++) {
            if (UtilComponent279.checkCondition_3576(d, e, f, this.computeValue_3148(k), 70.0F, this.rowH)) {
               if (k < this.catN) {
                  this.performAction_3166(k);
                  if (this.onlyEnabled) {
                     this.performAction_3154();
                  }
               } else {
                  this.performAction_3157(k - this.catN + 1);
               }

               return true;
            }
         }

         if (this.tab == 0 && i == 0 && UtilComponent279.checkCondition_3576(d, e, this.chipX, this.chipY, 58.0F, 18.0F)) {
            this.onlyEnabled = !this.onlyEnabled;
            MenuRenderer.playClickSound(this.onlyEnabled ? 1.3F : 0.9F, 0.6F);
            this.performAction_3154();
            this.listField3.get(this.cat).getTime_3128();
            this.searchPanel.getTime_3128();
            return true;
         } else if (this.tab == 1 && this.profilePage.mouseClicked(d, e, i)) {
            return true;
         } else if (this.tab == 2 && this.themesPage.checkCondition_3276(d, e, i)) {
            return true;
         } else if (this.tab == 3 && this.friendsPage.checkCondition_3108(d, e, i)) {
            return true;
         } else {
            return this.tab == 0 && this.computeValue_3158().checkCondition_3143(d, e, i) ? true : true;
         }
      } else {
         if (bl) {
            hasPos = false;
            this.dragging = false;
            MenuRenderer.playClickSound(1.3F, 0.5F);
         } else {
            this.dragging = true;
            this.dragDX = (float)d - this.floatValueField5;
            this.dragDY = (float)e - this.floatValueField6;
         }

         return true;
      }
   }

   public boolean mouseReleased(Click click) {
      if (this.dragging) {
         this.dragging = false;
         return true;
      }

      if (this.tab == 0 && !this.closing) {
         double d = UtilComponent279.computeValue_3580(click.x(), 2);
         double e = UtilComponent279.computeValue_3580(click.y(), 2);
         if (this.computeValue_3158().checkCondition_3141(d, e, click.button())) {
            return true;
         }
      }

      if (this.tab == 2) {
         this.themesPage.performAction_3270();
      }

      if (this.tab == 3) {
         this.friendsPage.performAction_3105();
      }

      return super.mouseReleased(click);
   }

   public boolean mouseDragged(Click click, double d, double e) {
      double f = UtilComponent279.computeValue_3580(click.x(), 2);
      double g = UtilComponent279.computeValue_3580(click.y(), 2);
      int i = click.button();
      if (this.dragging) {
         posX = (float)f - this.dragDX;
         posY = (float)g - this.dragDY;
         hasPos = true;
         return true;
      }

      if (this.tab == 0) {
         this.textFieldField1.performAction_2941(f, g, i);
         if (this.computeValue_3158().checkCondition_3113(f, g, i, UtilComponent279.computeValue_3580(d, 2), UtilComponent279.computeValue_3580(e, 2))) {
            return true;
         }
      }

      if (this.tab == 2 && this.themesPage.checkCondition_3274(f, g)) {
         return true;
      } else {
         return this.tab == 3 && this.friendsPage.checkCondition_3104(f, g, i) ? true : super.mouseDragged(click, d, e);
      }
   }

   public boolean mouseScrolled(double d, double e, double f, double g) {
      double h = UtilComponent279.computeValue_3580(d, 2);
      double i = UtilComponent279.computeValue_3580(e, 2);
      if (this.tab == 0 && !this.closing) {
         ScreenComponent217 screenComponent217 = this.computeValue_3158();
         Vector4f vector4f = screenComponent217.getVector4fField1_3110();
         if (UtilComponent279.checkCondition_3576(h, i, vector4f.x, vector4f.y, vector4f.z, vector4f.w)) {
            return screenComponent217.checkCondition_3112(h, i, g);
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

      if (i == 256) {
         for (ScreenComponent217 screenComponent217 : this.computeValue_3165()) {
            if (screenComponent217.checkCondition_3123()) {
               screenComponent217.checkCondition_3115(i, j, k);
               return true;
            }
         }

         if (this.textFieldField1.isFlagField7_2939()) {
            this.textFieldField1.performAction_2950(false);
            return true;
         } else {
            this.close();
            return true;
         }
      } else if (i == 344) {
         this.close();
         return true;
      } else if (i == 70 && (k & 2) != 0) {
         this.tab = 0;
         this.textFieldField1.performAction_2950(!this.textFieldField1.isFlagField7_2939());
         SettingsPersistence.performAction_0531(this.tab, this.cat);
         return true;
      } else if (this.textFieldField1.isFlagField7_2939()) {
         this.textFieldField1.performAction_2963(i, j, k);
         return true;
      } else if (this.tab == 3 && this.friendsPage.checkCondition_3102(i, j, k)) {
         return true;
      } else {
         return this.tab == 0 && this.computeValue_3158().checkCondition_3115(i, j, k) ? true : super.keyPressed(keyInput);
      }
   }

   public boolean charTyped(CharInput charInput) {
      char c = (char)charInput.codepoint();
      int i = charInput.modifiers();
      if (this.textFieldField1.isFlagField7_2939()) {
         this.textFieldField1.performAction_2958(c, i);
         return true;
      } else if (this.tab == 3 && this.friendsPage.checkCondition_3101(c, i)) {
         return true;
      } else {
         return this.tab == 0 && this.computeValue_3158().checkCondition_3111(c, i) ? true : super.charTyped(charInput);
      }
   }

   private List<ScreenComponent217> computeValue_3165() {
      ArrayList arrayList = new ArrayList<>(this.listField3);
      arrayList.add(this.searchPanel);
      return arrayList;
   }

   public void close() {
      if (!this.closing) {
         this.closing = true;
         this.dragging = false;
         MenuRenderer.close();
         CursorUtil.performAction_3422(CursorUtil.State404.DEFAULT);
         this.textFieldField1.performAction_2950(false);
         this.computeValue_3165().forEach(ScreenComponent217::performAction_3129);
      }
   }

   private void performAction_3153() {
      if (!this.finished) {
         this.finished = true;
         if (this.client != null) {
            this.client.setScreen(this.parent != null ? this.parent : (this.client.world == null ? new MainMenuScreen() : null));
         }
      }
   }

   public TextField getTextFieldField1_3146() {
      return this.textFieldField1;
   }

   public Animation getAnimationField2_3163() {
      return this.animationField2;
   }

   public List<ScreenComponent217> getListField3_3147() {
      return this.listField3;
   }

   public String getStringField4_3164() {
      return this.stringField4;
   }
}
