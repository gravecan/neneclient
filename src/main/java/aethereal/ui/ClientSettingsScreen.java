package aethereal.ui;

import aethereal.config.SettingsPersistence;
import aethereal.core.Module;
import aethereal.core.NeneClient;
import aethereal.module.client.Screenshare;
import aethereal.util.KeyUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public final class ClientSettingsScreen extends Screen {
   public static String USERNAME = MinecraftClient.getInstance().getSession().getUsername();
   public static String RANK = "";
   public static int ACCENT = 16722474;
   public static final int ACCENT_DARK = 11540504;
   public static float GUI_OPACITY = 0.97F;
   private static final Identifier LOGO = Identifier.of("neneclient", "pictures/logo.png");
   public static Supplier<List<ClientSettingsScreen.NestedType381>> PROVIDER = null;
   private static final int integerField1 = 440;
   private static final int integerField2 = 284;
   private static final int HEAD = 36;
   private static final int integerField3 = 108;
   private static final int PAD = 10;
   private static final int integerField4 = 32;
   private static final int GAP = 8;
   private static final String[] TABS = new String[]{"MODULES", "HUD", "CONFIGS", "PROFILE", "SETTINGS"};
   private static final String[] CATS = new String[]{"COMBAT", "MISC", "RENDER", "VISUALS", "CLIENT"};
   private static boolean glassEffect = true;
   private static boolean ssEnabled;
   private final Screen parent;
   private final List<ClientSettingsScreen.NestedType381> mods = new ArrayList<>();
   private int tab = 0;
   private int cat = 3;
   private String search = "";
   private boolean searchFocus;
   private float open;
   private float tabX = -1.0F;
   private float catY = -1.0F;
   private float scroll;
   private float scrollTarget;
   private float closeHover;
   private final float[] tabHover = new float[TABS.length];
   private final float[] catHover = new float[CATS.length];
   private long last = System.nanoTime();

   public ClientSettingsScreen(Screen screen) {
      super(Text.literal("NeneClient"));
      this.parent = screen;
   }

   protected void init() {
      this.tab = SettingsPersistence.getClientTab_0538();
      this.cat = SettingsPersistence.getClientCategory_0528();
      this.mods.clear();
      if (PROVIDER != null) {
         this.mods.addAll(PROVIDER.get());
      } else {
         this.mods.addAll(computeValue_2909());
      }

      if (this.mods.isEmpty()) {
         this.performAction_2893();
      }

      if (this.mods.stream().noneMatch(nestedType381 -> nestedType381.cat.equals("CLIENT") && nestedType381.name.equalsIgnoreCase("SS"))) {
         this.mods.add(new ClientSettingsScreen.NestedType381("CLIENT", "SS", "Hide the client GUI for screenshots", "", () -> ssEnabled, boolean_ -> {
            ssEnabled = boolean_;
            if (boolean_) {
               this.performAction_2892();
            } else {
               Screenshare.performAction_1469();
            }
         }));
      }

      this.open = 0.0F;
   }

   private static List<ClientSettingsScreen.NestedType381> computeValue_2909() {
      NeneClient neneClient = NeneClient.getInstance();
      if (neneClient != null && neneClient.getProcessors() != null && neneClient.getProcessors().getModules() != null) {
         ArrayList arrayList = new ArrayList();

         for (Module module : neneClient.getProcessors().getModules().getEntries()) {
            String string = switch (module.getCategory()) {
               case Combat -> "COMBAT";
               case Render -> "RENDER";
               case Visuals -> "VISUALS";
               case Client -> "CLIENT";
               case Movement, Player, Misc -> "MISC";
            };
            String string1 = module.getKeybind() == -1 ? "" : KeyUtil.getLabel_3478(module.getKeybind());
            arrayList.add(
               new ClientSettingsScreen.NestedType381(string, module.getName(), module.getDescription(), string1, module::isEnabled, module::setEnabled)
            );
         }

         return arrayList;
      } else {
         return List.of();
      }
   }

   private void performAction_2892() {
      ssEnabled = true;
      Screenshare.performAction_1468();
   }

   public static void performAction_2896(float f, int i, boolean bl) {
      GUI_OPACITY = Float.isFinite(f) ? Math.max(0.25F, Math.min(1.0F, f)) : 0.97F;
      ACCENT = i & 16777215;
      glassEffect = bl;
   }

   public static boolean isGlassEffect_2884() {
      return glassEffect;
   }

   private void performAction_2893() {
      String[][] strings = new String[][]{
         {"VISUALS", "CustomBlockOutline", "Themed block outline", ""},
         {"VISUALS", "Target ESP", "Animated 3D rings", "F7"},
         {"VISUALS", "Luma", "Iridescent butterflies", ""},
         {"VISUALS", "Sky", "Replaces the sky", "NUM3"},
         {"VISUALS", "Aurora", "Living ribbons of light", ""},
         {"VISUALS", "MotionBlur", "Cinematic motion blur", ""},
         {"COMBAT", "KillAura", "Attacks nearby targets", "R"},
         {"COMBAT", "Velocity", "Reduces knockback", ""},
         {"MISC", "AutoTool", "Picks the best tool", ""},
         {"RENDER", "Fullbright", "Max brightness", "B"},
         {"CLIENT", "Notifications", "Toast notifications", ""}
      };

      for (String[] strings1 : strings) {
         boolean[] bl = new boolean[]{strings1[1].equals("CustomBlockOutline")};
         this.mods.add(new ClientSettingsScreen.NestedType381(strings1[0], strings1[1], strings1[2], strings1[3], () -> bl[0], boolean1 -> bl[0] = boolean1));
      }
   }

   public void render(DrawContext drawContext, int i, int j, float h) {
      long l = System.nanoTime();
      float f = Math.min(0.05F, (float)(l - this.last) / 1.0E9F);
      this.last = l;
      this.open = computeValue_2886(this.open, 1.0F, f * 5.0F);
      float g = computeValue_2895(this.open);
      drawContext.fill(0, 0, this.width, this.height, computeValue_2897(0, 0.5F * g));
      int k = (this.width - 440) / 2;
      int m = (this.height - 284) / 2 + (int)((1.0F - g) * 18.0F);
      performAction_2904(drawContext, k - 2, m - 2, 444, 288, 8, computeValue_2897(ACCENT, 0.1F * g));
      performAction_2904(drawContext, k - 1, m - 1, 442, 286, 7, computeValue_2897(ACCENT, 0.35F * g));
      performAction_2904(drawContext, k, m, 440, 284, 6, computeValue_2897(glassEffect ? 1513763 : 920338, GUI_OPACITY * g));
      performAction_2904(drawContext, k, m, 440, 36, 6, computeValue_2897(1446172, g));
      drawContext.fill(k, m + 36 - 1, k + 440, m + 36, computeValue_2897(16777215, 0.06F * g));
      drawContext.drawTexture(RenderPipelines.GUI_TEXTURED, LOGO, k + 10, m + 6, 0.0F, 0.0F, 256, 256, 256, 256, computeValue_2897(16777215, g));
      this.performAction_2903(drawContext, k, m, i, j, f, g);
      this.performAction_2888(drawContext, k, m, i, j, f, g);
      this.performAction_2891(drawContext, k, m, i, j, f, g);
      int n = k + 108 + 10;
      int o = m + 36 + 10;
      short s = 312;
      short t = 228;
      if (this.tab == 4) {
         this.performAction_2905(drawContext, n, o, s, t, i, j, g);
      } else if (this.tab == 0) {
         this.performAction_2906(drawContext, n, o, s, t, i, j, f, g);
      } else if (this.tab == 3) {
         this.renderProfile(drawContext, n, o, s, t, g);
      } else {
         String string = TABS[this.tab] + "  -  COMING SOON";
         drawContext.drawText(
            this.textRenderer, string, n + (s - this.textRenderer.getWidth(string)) / 2, o + t / 2, computeValue_2897(16777215, 0.35F * g), false
         );
      }
   }

   private void performAction_2905(DrawContext drawContext, int i, int j, int k, int s, int t, int u, float f) {
      drawContext.drawText(this.textRenderer, "GUI APPEARANCE", i, j + 2, computeValue_2897(16777215, 0.9F * f), false);
      drawContext.drawText(this.textRenderer, "Glass, transparency and accent color", i, j + 16, computeValue_2897(16777215, 0.38F * f), false);
      int l = j + 42;
      performAction_2904(drawContext, i, l, k, 48, 6, computeValue_2897(16777215, 0.045F * f));
      drawContext.drawText(this.textRenderer, "TRANSPARENCY", i + 10, l + 9, computeValue_2897(16777215, 0.9F * f), false);
      drawContext.drawText(this.textRenderer, Math.round(GUI_OPACITY * 100.0F) + "%", i + k - 42, l + 9, computeValue_2897(ACCENT, f), false);
      drawContext.fill(i + 10, l + 31, i + k - 10, l + 34, computeValue_2897(16777215, 0.12F * f));
      drawContext.fill(i + 10, l + 31, i + 10 + (int)((k - 20) * GUI_OPACITY), l + 34, computeValue_2897(ACCENT, f));
      int m = l + 58;
      performAction_2904(drawContext, i, m, k, 48, 6, computeValue_2897(16777215, 0.045F * f));
      drawContext.drawText(this.textRenderer, "ACCENT COLOR", i + 10, m + 9, computeValue_2897(16777215, 0.9F * f), false);
      String[] strings = new String[]{"RED", "PURPLE", "BLUE", "GREEN", "ORANGE"};
      int[] n = new int[]{16722474, 11032055, 3574783, 3525002, 16752451};
      int o = (k - 20) / n.length;

      for (int p = 0; p < n.length; p++) {
         int q = i + 10 + p * o;
         performAction_2904(drawContext, q, m + 27, o - 4, 13, 4, computeValue_2897(n[p], f));
         if (ACCENT == n[p]) {
            drawContext.fill(q + 3, m + 42, q + o - 7, m + 43, computeValue_2897(16777215, f));
         }
      }

      int r = m + 58;
      performAction_2904(drawContext, i, r, k, 34, 6, computeValue_2897(16777215, 0.045F * f));
      drawContext.drawText(this.textRenderer, "GLASS EFFECT", i + 10, r + 12, computeValue_2897(16777215, 0.9F * f), false);
      performAction_2904(drawContext, i + k - 43, r + 9, 32, 16, 8, computeValue_2897(glassEffect ? ACCENT : 4209480, f));
      performAction_2904(drawContext, i + (glassEffect ? k - 25 : k - 41), r + 11, 12, 12, 6, computeValue_2897(16777215, f));
      drawContext.drawText(
         this.textRenderer, "Uses a lightweight translucent finish; no costly live blur.", i, r + 43, computeValue_2897(16777215, 0.35F * f), false
      );
   }

   private void performAction_2903(DrawContext drawContext, int i, int j, int k, int l, float f, float g) {
      int m = i + 46;
      float h = -1.0F;
      float n = 0.0F;

      for (int o = 0; o < TABS.length; o++) {
         int p = this.textRenderer.getWidth(TABS[o]) + 16;
         boolean bl = checkCondition_2899(k, l, m, j + 7, p, 22);
         this.tabHover[o] = computeValue_2886(this.tabHover[o], bl ? 1.0F : 0.0F, f * 10.0F);
         if (o == this.tab) {
            h = m;
            n = p;
         }

         performAction_2904(drawContext, m, j + 7, p, 22, 5, computeValue_2897(16777215, (0.04F + 0.05F * this.tabHover[o]) * g));
         int q = o == this.tab ? 16777215 : computeValue_2898(9077648, 16777215, this.tabHover[o]);
         drawContext.drawText(this.textRenderer, TABS[o], m + 8, j + 14, computeValue_2897(q, g), false);
         m += p + 5;
      }

      if (this.tabX < 0.0F) {
         this.tabX = h;
      }

      this.tabX = this.tabX + (h - this.tabX) * Math.min(1.0F, f * 14.0F);
      drawContext.fill((int)this.tabX + 6, j + 36 - 2, (int)(this.tabX + n) - 6, j + 36, computeValue_2897(ACCENT, g));
   }

   private void performAction_2888(DrawContext drawContext, int i, int j, int k, int l, float f, float g) {
      int m = i + 440 - 26;
      int n = j + 9;
      this.closeHover = computeValue_2886(this.closeHover, checkCondition_2899(k, l, m, n, 18, 18) ? 1.0F : 0.0F, f * 10.0F);
      performAction_2904(drawContext, m, n, 18, 18, 5, computeValue_2897(ACCENT, 0.25F * this.closeHover * g));
      drawContext.drawText(this.textRenderer, "x", m + 6, n + 5, computeValue_2897(computeValue_2898(9077648, 16777215, this.closeHover), g), false);
   }

   private void performAction_2891(DrawContext drawContext, int i, int j, int k, int l, float f, float g) {
      int m = i + 8;
      int n = j + 36 + 10;
      drawContext.fill(i + 108, j + 36 + 8, i + 108 + 1, j + 284 - 8, computeValue_2897(16777215, 0.05F * g));
      float h = n + this.cat * 28;
      if (this.catY < 0.0F) {
         this.catY = h;
      }

      this.catY = this.catY + (h - this.catY) * Math.min(1.0F, f * 14.0F);
      performAction_2904(drawContext, m, (int)this.catY, 92, 24, 5, computeValue_2897(ACCENT, 0.16F * g));
      drawContext.fill(m, (int)this.catY + 5, m + 2, (int)this.catY + 19, computeValue_2897(ACCENT, g));

      for (int o = 0; o < CATS.length; o++) {
         int p = n + o * 28;
         boolean bl = checkCondition_2899(k, l, m, p, 92, 24);
         this.catHover[o] = computeValue_2886(this.catHover[o], bl ? 1.0F : 0.0F, f * 10.0F);
         if (o != this.cat) {
            performAction_2904(drawContext, m, p, 92, 24, 5, computeValue_2897(16777215, 0.05F * this.catHover[o] * g));
         }

         int q = o == this.cat ? 16777215 : computeValue_2898(9077648, 16777215, this.catHover[o]);
         drawContext.drawText(this.textRenderer, CATS[o], m + 12 + (int)(this.catHover[o] * 2.0F), p + 8, computeValue_2897(q, g), false);
      }

      int r = j + 284 - 46;
      byte b = 92;
      performAction_2904(drawContext, m, r, b, 38, 6, computeValue_2897(16777215, 0.05F * g));
      performAction_2904(drawContext, m + 6, r + 7, 24, 24, 5, computeValue_2897(ACCENT, g));
      String string = USERNAME.substring(0, 1).toUpperCase(Locale.ROOT);
      drawContext.drawText(this.textRenderer, string, m + 6 + 12 - this.textRenderer.getWidth(string) / 2, r + 15, computeValue_2897(16777215, g), false);
      drawContext.drawText(this.textRenderer, USERNAME, m + 36, r + 8, computeValue_2897(16777215, g), false);
      if (!RANK.isBlank()) {
         int s = this.textRenderer.getWidth(RANK) + 8;
         performAction_2904(drawContext, m + 36, r + 20, s, 11, 3, computeValue_2897(ACCENT, 0.28F * g));
         drawContext.drawText(this.textRenderer, RANK, m + 40, r + 22, computeValue_2897(16739179, g), false);
      }
   }

   private List<ClientSettingsScreen.NestedType381> computeValue_2910() {
      ArrayList arrayList = new ArrayList();
      String string = this.search.toLowerCase(Locale.ROOT);

      for (ClientSettingsScreen.NestedType381 nestedType381 : this.mods) {
         boolean bl = string.isEmpty() ? nestedType381.cat.equals(CATS[this.cat]) : true;
         if (bl && nestedType381.name.toLowerCase(Locale.ROOT).contains(string)) {
            arrayList.add(nestedType381);
         }
      }

      return arrayList;
   }

   private void performAction_2906(DrawContext drawContext, int i, int j, int k, int l, int m, int n, float f, float g) {
      int o = k - 70;
      performAction_2904(drawContext, i, j, o, 20, 6, computeValue_2897(16777215, (this.searchFocus ? 0.1F : 0.05F) * g));
      if (this.searchFocus) {
         performAction_2904(drawContext, i, j + 19, o, 1, 0, computeValue_2897(ACCENT, g));
      }

      String string = this.search.isEmpty() && !this.searchFocus
         ? "SEARCH MODULES"
         : this.search + (this.searchFocus && System.currentTimeMillis() / 500L % 2L == 0L ? "_" : "");
      drawContext.drawText(this.textRenderer, string, i + 8, j + 6, computeValue_2897(16777215, (this.search.isEmpty() ? 0.35F : 0.9F) * g), false);
      List list = this.computeValue_2910();
      long p = list.stream().filter(nestedType3811 -> nestedType3811.bind != null && !nestedType3811.bind.isBlank()).count();
      String string1 = list.size() + " MODULES  ·  " + p + " BINDS";
      drawContext.drawText(this.textRenderer, string1, i + k - this.textRenderer.getWidth(string1), j + 6, computeValue_2897(16777215, 0.35F * g), false);
      int q = j + 28;
      int r = l - 28;
      int s = (k - 8) / 2;
      int t = (list.size() + 1) / 2;
      int u = Math.max(0, t * 40 - r);
      this.scrollTarget = Math.max(0.0F, Math.min(u, this.scrollTarget));
      this.scroll = this.scroll + (this.scrollTarget - this.scroll) * Math.min(1.0F, f * 14.0F);
      drawContext.enableScissor(i - 2, q, i + k + 2, q + r);

      for (int v = 0; v < list.size(); v++) {
         ClientSettingsScreen.NestedType381 nestedType381 = (ClientSettingsScreen.NestedType381)list.get(v);
         int w = i + v % 2 * (s + 8);
         int x = q + v / 2 * 40 - (int)this.scroll;
         if (x + 32 >= q && x <= q + r) {
            boolean bl = nestedType381.get.getAsBoolean();
            boolean bl1 = checkCondition_2899(m, n, w, x, s, 32) && n >= q && n <= q + r;
            nestedType381.hover = computeValue_2886(nestedType381.hover, bl1 ? 1.0F : 0.0F, f * 10.0F);
            if (nestedType381.tog < 0.0F) {
               nestedType381.tog = bl ? 1.0F : 0.0F;
            }

            nestedType381.tog = computeValue_2886(nestedType381.tog, bl ? 1.0F : 0.0F, f * 9.0F);
            int y = x - (int)(nestedType381.hover * 1.0F);
            performAction_2904(drawContext, w, y, s, 32, 6, computeValue_2897(computeValue_2898(1511965, 2366252, nestedType381.hover), g));
            if (nestedType381.tog > 0.01F) {
               performAction_2904(drawContext, w, y, s, 32, 6, computeValue_2897(ACCENT, 0.1F * nestedType381.tog * g));
               drawContext.fill(w, y + 7, w + 2, y + 32 - 7, computeValue_2897(ACCENT, nestedType381.tog * g));
            }

            drawContext.drawText(
               this.textRenderer,
               nestedType381.name.toUpperCase(Locale.ROOT),
               w + 10,
               y + 7,
               computeValue_2897(computeValue_2898(14538468, ACCENT, nestedType381.tog), g),
               false
            );
            String string2 = this.computeValue_2901(nestedType381.desc.toUpperCase(Locale.ROOT), s - 80);
            drawContext.drawText(this.textRenderer, string2, w + 10, y + 19, computeValue_2897(16777215, 0.3F * g), false);
            int z = w + s - 10;
            byte b = 24;
            byte c = 12;
            int ba = z - b;
            int bb = y + (32 - c) / 2;
            performAction_2904(drawContext, ba, bb, b, c, 6, computeValue_2897(computeValue_2898(3814210, ACCENT, nestedType381.tog), g));
            int bc = ba + 2 + (int)(nestedType381.tog * (b - c));
            performAction_2904(drawContext, bc, bb + 2, c - 4, c - 4, 4, computeValue_2897(16777215, g));
            if (nestedType381.bind != null && !nestedType381.bind.isEmpty()) {
               int bd = this.textRenderer.getWidth(nestedType381.bind) + 8;
               performAction_2904(drawContext, ba - bd - 6, y + 10, bd, 12, 3, computeValue_2897(ACCENT, 0.18F * g));
               drawContext.fill(ba - bd - 6, y + 12, ba - bd - 5, y + 20, computeValue_2897(ACCENT, 0.85F * g));
               drawContext.drawText(
                  this.textRenderer, nestedType381.bind.toUpperCase(Locale.ROOT), ba - bd - 2, y + 12, computeValue_2897(16777215, 0.92F * g), false
               );
            }
         }
      }

      drawContext.disableScissor();
      if (u > 0) {
         int be = Math.max(16, r * r / (r + u));
         int bf = q + (int)((r - be) * (this.scroll / u));
         performAction_2904(drawContext, i + k + 3, bf, 2, be, 1, computeValue_2897(ACCENT, 0.7F * g));
      }
   }

   private void renderProfile(DrawContext context, int x, int y, int width, int height, float opacity) {
      performAction_2904(context, x, y, width, 198, 8, computeValue_2897(0xFFFFFF, 0.05F * opacity));
      String username = MinecraftClient.getInstance().getSession().getUsername();
      String initial = username.isEmpty() ? "?" : username.substring(0, 1).toUpperCase(Locale.ROOT);
      performAction_2904(context, x + 14, y + 14, 62, 62, 10, computeValue_2897(ACCENT, opacity));
      context.drawText(this.textRenderer, initial, x + 45 - this.textRenderer.getWidth(initial) / 2,
         y + 41, computeValue_2897(0xFFFFFF, opacity), true);
      context.drawText(this.textRenderer, username, x + 90, y + 26, computeValue_2897(0xFFFFFF, opacity), true);
      context.drawText(this.textRenderer, "LOCAL PROFILE", x + 90, y + 46,
         computeValue_2897(ACCENT, opacity), false);
      context.drawText(this.textRenderer, "NENECLIENT", x + 90, y + 66,
         computeValue_2897(0xFFFFFF, 0.55F * opacity), false);
      context.drawText(this.textRenderer, "No client activation required.", x + 14, y + 106,
         computeValue_2897(0xFFFFFF, 0.75F * opacity), false);
      context.drawText(this.textRenderer, "Minecraft account login is unchanged.", x + 14, y + 128,
         computeValue_2897(0xFFFFFF, 0.45F * opacity), false);
   }

   public boolean mouseClicked(Click click, boolean bl) {
      int i = (int)click.x();
      int j = (int)click.y();
      int k = click.button();
      int l = (this.width - 440) / 2;
      int m = (this.height - 284) / 2;
      if (!checkCondition_2899(i, j, l, m, 440, 284)) {
         return super.mouseClicked(click, bl);
      }

      if (checkCondition_2899(i, j, l + 440 - 26, m + 9, 18, 18)) {
         this.performAction_2894();
         this.close();
         return true;
      }

      int n = l + 46;

      for (int o = 0; o < TABS.length; o++) {
         int p = this.textRenderer.getWidth(TABS[o]) + 16;
         if (checkCondition_2899(i, j, n, m + 7, p, 22)) {
            if (this.tab != o) {
               this.performAction_2894();
               this.tab = o;
               SettingsPersistence.performAction_0523(this.tab, this.cat);
            }

            return true;
         }

         n += p + 5;
      }

      for (int y = 0; y < CATS.length; y++) {
         if (checkCondition_2899(i, j, l + 8, m + 36 + 10 + y * 28, 92, 24)) {
            if (this.cat != y) {
               this.performAction_2894();
               this.scroll = this.scrollTarget = 0.0F;
               this.search = "";
            }

            this.cat = y;
            this.tab = 0;
            SettingsPersistence.performAction_0523(this.tab, this.cat);
            return true;
         }
      }

      if (this.tab == 4) {
         int ba = l + 108 + 10;
         int bc = m + 36 + 10;
         short bd = 312;
         int be = bc + 42 + 31;
         if (checkCondition_2899(i, j, ba + 8, be - 10, bd - 16, 22)) {
            GUI_OPACITY = Math.max(0.25F, Math.min(1.0F, (i - ba - 10.0F) / (bd - 20.0F)));
            SettingsPersistence.performAction_0527();
            this.performAction_2894();
            return true;
         } else {
            int bf = bc + 42 + 58 + 27;
            if (checkCondition_2899(i, j, ba + 8, bf - 4, bd - 16, 22)) {
               int bh = Math.max(0, Math.min(4, (i - ba - 10) / ((bd - 20) / 5)));
               ACCENT = new int[]{16722474, 11032055, 3574783, 3525002, 16752451}[bh];
               SettingsPersistence.performAction_0527();
               this.performAction_2894();
               return true;
            } else {
               int bg = bc + 42 + 58 + 58 + 9;
               if (checkCondition_2899(i, j, ba + bd - 50, bg - 4, 44, 24)) {
                  glassEffect = !glassEffect;
                  SettingsPersistence.performAction_0527();
                  this.performAction_2894();
                  return true;
               } else {
                  return true;
               }
            }
         }
      } else {
         if (this.tab != 0) {
            return true;
         }

         int z = l + 108 + 10;
         int bb = m + 36 + 10;
         short s = 312;
         short t = 228;
         this.searchFocus = checkCondition_2899(i, j, z, bb, s - 70, 20);
         if (this.searchFocus) {
            this.performAction_2894();
            return true;
         }

         int q = bb + 28;
         int r = t - 28;
         int u = (s - 8) / 2;
         if (j >= q && j <= q + r) {
            List list = this.computeValue_2910();

            for (int v = 0; v < list.size(); v++) {
               int w = z + v % 2 * (u + 8);
               int x = q + v / 2 * 40 - (int)this.scroll;
               if (checkCondition_2899(i, j, w, x, u, 32)) {
                  if (k == 0) {
                     ClientSettingsScreen.NestedType381 nestedType381 = (ClientSettingsScreen.NestedType381)list.get(v);
                     nestedType381.set.accept(!nestedType381.get.getAsBoolean());
                     this.performAction_2894();
                  }

                  return true;
               }
            }

            return true;
         } else {
            return true;
         }
      }
   }

   public boolean mouseScrolled(double e, double f, double g, double d) {
      this.scrollTarget -= (float)d * 28.0F;
      return true;
   }

   public boolean charTyped(CharInput charInput) {
      char c = (char)charInput.codepoint();
      if (this.searchFocus && this.search.length() < 24 && c >= ' ') {
         this.search = this.search + c;
         this.scrollTarget = 0.0F;
         return true;
      } else {
         return super.charTyped(charInput);
      }
   }

   public boolean keyPressed(KeyInput keyInput) {
      int i = keyInput.key();
      if (this.searchFocus) {
         if (i == 259 && !this.search.isEmpty()) {
            this.search = this.search.substring(0, this.search.length() - 1);
            return true;
         }

         if (i == 257 || i == 256) {
            this.searchFocus = false;
            return true;
         }
      }

      return super.keyPressed(keyInput);
   }

   public void close() {
      if (this.client != null) {
         this.client.setScreen(this.parent);
      }
   }

   public static boolean checkCondition_2885() {
      return Screenshare.isActive_1470();
   }

   public boolean shouldPause() {
      return false;
   }

   public void renderBackground(DrawContext drawContext, int i, int j, float f) {
   }

   private void performAction_2894() {
      MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.ui(SoundEvents.UI_BUTTON_CLICK, 1.2F));
   }

   private String computeValue_2901(String string, int i) {
      if (this.textRenderer.getWidth(string) <= i) {
         return string;
      }

      while (string.length() > 1 && this.textRenderer.getWidth(string + "..") > i) {
         string = string.substring(0, string.length() - 1);
      }

      return string + "..";
   }

   private static boolean checkCondition_2899(int i, int j, int k, int l, int m, int n) {
      return i >= k && i <= k + m && j >= l && j <= l + n;
   }

   private static float computeValue_2886(float f, float g, float h) {
      return f < g ? Math.min(g, f + h) : Math.max(g, f - h);
   }

   private static float computeValue_2895(float f) {
      float g = 1.0F - f;
      return 1.0F - g * g * g;
   }

   private static int computeValue_2897(int i, float f) {
      int j = (int)(Math.max(0.0F, Math.min(1.0F, f)) * 255.0F);
      return j << 24 | i & 16777215;
   }

   private static int computeValue_2898(int i, int j, float f) {
      int k = (int)((i >> 16 & 0xFF) * (1.0F - f) + (j >> 16 & 0xFF) * f);
      int l = (int)((i >> 8 & 0xFF) * (1.0F - f) + (j >> 8 & 0xFF) * f);
      int m = (int)((i & 0xFF) * (1.0F - f) + (j & 0xFF) * f);
      return k << 16 | l << 8 | m;
   }

   private static void performAction_2904(DrawContext drawContext, int i, int j, int k, int l, int m, int n) {
      if (m > 0 && k >= m * 2 && l >= m * 2) {
         for (int o = 0; o < m; o++) {
            double d = m - o - 0.5;
            int p = m - (int)Math.round(Math.sqrt(m * m - d * d));
            drawContext.fill(i + p, j + o, i + k - p, j + o + 1, n);
            drawContext.fill(i + p, j + l - o - 1, i + k - p, j + l - o, n);
         }

         drawContext.fill(i, j + m, i + k, j + l - m, n);
      } else {
         drawContext.fill(i, j, i + k, j + l, n);
      }
   }

   public static final class NestedType381 {
      public final String name;
      public final String desc;
      public final String cat;
      public String bind;
      public final BooleanSupplier get;
      public final Consumer<Boolean> set;
      float hover;
      float tog = -1.0F;

      public NestedType381(String string, String string1, String string2, String string3, BooleanSupplier booleanSupplier, Consumer<Boolean> consumer) {
         this.cat = string;
         this.name = string1;
         this.desc = string2;
         this.bind = string3;
         this.get = booleanSupplier;
         this.set = consumer;
      }
   }
}
