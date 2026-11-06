package aethereal.ui.screen;

import aethereal.core.NeneClient;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent184;
import aethereal.render.RenderComponent188;
import aethereal.render.RenderComponent193;
import java.util.Locale;
import java.util.Random;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.joml.Matrix3x2fStack;

public final class MenuRenderer {
   public static String USERNAME = MinecraftClient.getInstance().getSession().getUsername();
   public static String RANK = "";
   public static final Identifier LOGO = Identifier.of("neneclient", "pictures/logo.png");
   public static final int CARD = 1446428;
   public static final int CARD_HOVER = 2300717;
   public static int PANEL = 789008;
   public static final int OFF = 3814213;
   public static final int GOLD = 16761415;
   public static final int GREEN = 4906624;
   public static final int WHITE = 16777215;
   public static int ACCENT = 16722474;
   public static float glass = 0.58F;
   public static boolean particlesOn = true;
   public static float floatValueField1 = 0.016F;
   public static float time = 0.0F;
   private static long last = 0L;
   private static final int PCOUNT = 48;
   private static final float[] floatValueArrayField2 = new float[240];

   public static String getDisplayName() {
      return NeneClient.getInstance() != null
            && NeneClient.getInstance().getProcessors() != null
            && NeneClient.getInstance().getProcessors().getModules() != null
            && NeneClient.getInstance().getProcessors().getModules().getNameProtect_0399() != null
            && NeneClient.getInstance().getProcessors().getModules().getNameProtect_0399().isEnabled()
         ? NeneClient.getInstance().getProcessors().getModules().getNameProtect_0399().computeValue_1467(USERNAME)
         : USERNAME;
   }

   private MenuRenderer() {
   }

   public static void updateFrameTime() {
      long l = System.nanoTime();
      if (last == 0L) {
         last = l;
      }

      floatValueField1 = Math.min(0.05F, (float)(l - last) / 1.0E9F);
      last = l;
      time = time + floatValueField1;
   }

   public static float clamp01(float f) {
      return f < 0.0F ? 0.0F : Math.min(f, 1.0F);
   }

   public static float approach(float f, float g, float h) {
      float i = f + (g - f) * (1.0F - (float)Math.exp(-h * floatValueField1));
      return Math.abs(g - i) < 5.0E-4F ? g : i;
   }

   public static float easeOutCubic(float f) {
      f = clamp01(f);
      float g = 1.0F - f;
      return 1.0F - g * g * g;
   }

   public static float easeOutBack(float f) {
      f = clamp01(f);
      float g = 1.70158F;
      float h = g + 1.0F;
      float i = f - 1.0F;
      return 1.0F + h * i * i * i + g * i * i;
   }

   public static int getAccent() {
      return ACCENT;
   }

   public static void setAccent(int i, boolean bl) {
      ACCENT = i & 16777215;
   }

   public static void setPanelColor(int i) {
      PANEL = i & 16777215;
   }

   public static int getTextColor() {
      return 16777215;
   }

   public static int getMutedTextColor() {
      return 9077648;
   }

   public static float glassAlpha(float f) {
      return clamp01(f) * clamp01(glass);
   }

   public static int withAlpha(int i, float f) {
      return RenderComponent183.computeValue_2558(i >> 16 & 0xFF, i >> 8 & 0xFF, i & 0xFF, (int)(clamp01(f) * 255.0F));
   }

   public static int replaceAlpha(int i, float f) {
      int[] j = RenderComponent183.computeValue_2555(i);
      return RenderComponent183.computeValue_2558(j[0], j[1], j[2], (int)(clamp01(f) * 255.0F));
   }

   public static int blendArgb(int i, int j, float f) {
      int[] k = RenderComponent183.computeValue_2555(i);
      int[] l = RenderComponent183.computeValue_2555(j);
      return RenderComponent183.computeValue_2558(
         interpolateChannel(k[0], l[0], f), interpolateChannel(k[1], l[1], f), interpolateChannel(k[2], l[2], f), interpolateChannel(k[3], l[3], f)
      );
   }

   public static int blendRgb(int i, int j, float f) {
      return interpolateChannel(i >> 16 & 0xFF, j >> 16 & 0xFF, f) << 16
         | interpolateChannel(i >> 8 & 0xFF, j >> 8 & 0xFF, f) << 8
         | interpolateChannel(i & 0xFF, j & 0xFF, f);
   }

   private static int interpolateChannel(int i, int j, float f) {
      return (int)(i + (j - i) * clamp01(f));
   }

   public static RenderComponent184 getRenderer() {
      return NeneClient.getInstance().getProcessors().getRenderComponent184Field3_0677();
   }

   public static void fillRoundedRect(Matrix3x2fStack matrix3x2fStack, float f, float g, float h, float i, float j, int k) {
      getRenderer().performAction_2577(matrix3x2fStack, f, g, h, i, j, k);
   }

   public static void strokeRoundedRect(Matrix3x2fStack matrix3x2fStack, float f, float g, float h, float i, float j, float k, int l) {
      getRenderer().performAction_2576(matrix3x2fStack, f, g, h, i, j, k, l);
   }

   public static void fillCircle(Matrix3x2fStack matrix3x2fStack, float f, float g, float h, int i) {
      getRenderer().performAction_2577(matrix3x2fStack, f - h, g - h, h * 2.0F, h * 2.0F, h, i);
   }

   public static void drawTexture(Matrix3x2fStack matrix3x2fStack, Identifier identifier, float f, float g, float h, float i, int j) {
      getRenderer().performAction_2585(matrix3x2fStack, identifier, f, g, h, i, 0.0F, j);
   }

   public static float textWidth(String string, float f) {
      return RenderComponent188.renderComponent187Field3.computeValue_2626(string, f);
   }

   public static void drawText(Matrix3x2fStack matrix3x2fStack, String string, float f, float g, float h, int i) {
      RenderComponent188.renderComponent187Field3
         .performAction_2630(matrix3x2fStack, string, f, RenderComponent188.renderComponent187Field3.computeValue_2619(string, h, g), h, i);
   }

   public static void drawCenteredText(Matrix3x2fStack matrix3x2fStack, String string, float f, float g, float h, int i) {
      drawText(matrix3x2fStack, string, f - textWidth(string, h) / 2.0F, g, h, i);
   }

   public static void drawRightAlignedText(Matrix3x2fStack matrix3x2fStack, String string, float f, float g, float h, int i) {
      drawText(matrix3x2fStack, string, f - textWidth(string, h), g, h, i);
   }

   public static float iconWidth(String string, float f) {
      return RenderComponent188.renderComponent187Field1.computeValue_2618(string, f);
   }

   public static void drawIcon(Matrix3x2fStack matrix3x2fStack, String string, float f, float g, float h, int i) {
      RenderComponent188.renderComponent187Field1
         .performAction_2630(matrix3x2fStack, string, f, RenderComponent188.renderComponent187Field1.computeValue_2619(string, h, g), h, i);
   }

   public static String ellipsize(String string, float f, float g) {
      if (textWidth(string, f) <= g) {
         return string;
      }

      String string1 = string;

      while (string1.length() > 1 && textWidth(string1 + "..", f) > g) {
         string1 = string1.substring(0, string1.length() - 1);
      }

      return string1 + "..";
   }

   public static void playClickSound(float f, float g) {
      MinecraftClient minecraftClient = MinecraftClient.getInstance();
      if (minecraftClient != null && minecraftClient.getSoundManager() != null) {
         minecraftClient.getSoundManager().play(PositionedSoundInstance.ui(SoundEvents.UI_BUTTON_CLICK, f));
      }
   }

   public static void playSelectSound() {
      playClickSound(1.15F, 0.8F);
   }

   public static void playHoverSound() {
      playClickSound(1.35F, 0.7F);
   }

   public static void playToggleSound(boolean bl) {
      playClickSound(bl ? 1.65F : 0.85F, 0.9F);
   }

   public static void playBackSound() {
      playClickSound(0.7F, 0.8F);
   }

   public static void close() {
      playClickSound(0.6F, 0.7F);
   }

   public static void playQuietClick() {
      playClickSound(1.9F, 0.15F);
   }

   public static float drawRankBadge(Matrix3x2fStack matrix3x2fStack, float f, float g, float h) {
      if (RANK != null && !RANK.isBlank()) {
         String string = RANK.toUpperCase(Locale.ROOT);
         float i = 5.5F;
         float j = textWidth(string, i) + 12.0F;
         float k = 10.0F;
         float l = g - k / 2.0F;
         fillRoundedRect(matrix3x2fStack, f, l, j, k, 3.0F, withAlpha(16761415, 0.14F * h));
         strokeRoundedRect(matrix3x2fStack, f, l, j, k, 3.0F, 0.5F, withAlpha(16761415, 0.55F * h));
         float m = time * 0.55F % 1.8F / 1.8F;
         RenderComponent193.performAction_2681(matrix3x2fStack, f, l, j, k);
         fillRoundedRect(matrix3x2fStack, f - 8.0F + m * (j + 16.0F), l, 5.0F, k, 0.0F, withAlpha(16777215, 0.25F * h));
         RenderComponent193.performAction_2680(matrix3x2fStack);
         fillCircle(matrix3x2fStack, f + 5.0F, g, 1.4F, withAlpha(16761415, h));
         drawText(matrix3x2fStack, string, f + 9.0F, g, i, withAlpha(16761415, h));
         return j;
      } else {
         return 0.0F;
      }
   }

   public static void drawUserCard(Matrix3x2fStack matrix3x2fStack, float f, float g, float h, float i) {
      float j = 38.0F;
      int k = getAccent();
      fillRoundedRect(matrix3x2fStack, f, g, h, j, 7.0F, withAlpha(16777215, 0.05F * i));
      strokeRoundedRect(matrix3x2fStack, f, g, h, j, 7.0F, 0.5F, withAlpha(16777215, 0.08F * i));
      float l = 24.0F;
      float m = f + 7.0F;
      float n = g + (j - l) / 2.0F;
      fillRoundedRect(matrix3x2fStack, m - 1.5F, n - 1.5F, l + 3.0F, l + 3.0F, 9.0F, replaceAlpha(k, (0.18F + 0.1F * (float)Math.sin(time * 2.0F)) * i));
      fillRoundedRect(matrix3x2fStack, m, n, l, l, 7.0F, replaceAlpha(k, 0.95F * i));
      String string = getDisplayName();
      String string1 = string.isEmpty() ? "?" : string.substring(0, 1).toUpperCase(Locale.ROOT);
      drawCenteredText(matrix3x2fStack, string1, m + l / 2.0F, n + l / 2.0F, 9.0F, withAlpha(16777215, i));
      float o = 0.5F + 0.5F * (float)Math.sin(time * 3.0F);
      fillCircle(matrix3x2fStack, m + l - 1.0F, n + l - 1.0F, 3.4F, withAlpha(PANEL, i));
      fillCircle(matrix3x2fStack, m + l - 1.0F, n + l - 1.0F, 2.3F, withAlpha(4906624, (0.65F + 0.35F * o) * i));
      drawText(matrix3x2fStack, ellipsize(string, 7.0F, h - 44.0F), f + 39.0F, g + 13.0F, 7.0F, withAlpha(16777215, i));
      if (RANK != null && !RANK.isBlank()) {
         drawRankBadge(matrix3x2fStack, f + 39.0F, g + 26.0F, i);
      }
   }

   public static void drawParticles(Matrix3x2fStack matrix3x2fStack, float f, float g, float h) {
      int i = getAccent();

      for (int j = 0; j < 48; j++) {
         float k = floatValueArrayField2[j * 5];
         float l = floatValueArrayField2[j * 5 + 1];
         float m = floatValueArrayField2[j * 5 + 2];
         float n = floatValueArrayField2[j * 5 + 3];
         float o = floatValueArrayField2[j * 5 + 4];
         float p = (l - time * (0.012F + m * 0.03F)) % 1.0F;
         if (p < 0.0F) {
            p++;
         }

         float q = k * f + (float)Math.sin(time * 0.4F + n * 6.283F) * 14.0F;
         float r = (float)Math.sin(Math.PI * p);
         float s = 0.5F + 0.5F * (float)Math.sin(time * (1.0F + o * 2.0F) + n * 6.283F);
         float t = 1.0F + m * 1.8F;
         fillRoundedRect(matrix3x2fStack, q, p * g, t, t, t / 2.0F, replaceAlpha(i, (0.1F + 0.28F * s) * r * h));
      }
   }

   static {
      Random random = new Random(67L);

      for (int i = 0; i < floatValueArrayField2.length; i++) {
         floatValueArrayField2[i] = random.nextFloat();
      }
   }
}
