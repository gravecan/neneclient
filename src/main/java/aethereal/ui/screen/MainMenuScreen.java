package aethereal.ui.screen;

import aethereal.api.ApiAnnotation001;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.core.ObfuscatorRuntime;
import aethereal.module.client.Screenshare;
import aethereal.render.Animation;
import aethereal.render.RenderComponent186;
import aethereal.render.RenderComponent192;
import aethereal.render.pipeline.PipelineComponent196;
import aethereal.ui.ClientSettingsScreen;
import aethereal.util.UtilComponent279;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.joml.Matrix3x2fStack;

public class MainMenuScreen extends Screen {
   private static final float BACKGROUND_ASPECT = 1.7760416F;
   private static final float FOCUS_Y = 0.55F;
   private static final float[] PARALLAX = new float[2];
   private static final Identifier identifierField1 = Identifier.of("neneclient", "pictures/main.png");
   private final Animation transition = new Animation();
   private final List<MainMenuScreen.NestedType393> items;
   private float introTime;
   private MainMenuScreen.NestedType393 pendingItem;
   private float pendingTime;
   private int integerField2 = -1;

   public MainMenuScreen() {
      super(Text.empty());
      this.transition.performAction_2535(0.0F, 1.0F, 0.15F, RenderComponent186.contract372Field7, 0.0F);
      if (MinecraftAccess.minecraft.currentScreen instanceof MainMenuScreen) {
         this.transition.setFloatValueField1_2526(1.0F);
         this.transition.setFloatValueField2_2536(1.0F);
         this.transition.setFloatValueField4_2525(1.0F);
         this.introTime = 2.0F;
      }

      this.items = List.of(
         new MainMenuScreen.NestedType393("Singleplayer", () -> MinecraftAccess.minecraft.setScreen(new SelectWorldScreen(null))),
         new MainMenuScreen.NestedType393("Multiplayer", () -> MinecraftAccess.minecraft.setScreen(new MultiplayerScreen(null))),
         new MainMenuScreen.NestedType393("Accounts", () -> MinecraftAccess.minecraft.setScreen(new ScreenComponent212())),
         new MainMenuScreen.NestedType393(
            "Minecraft Settings", () -> MinecraftAccess.minecraft.setScreen(new OptionsScreen(null, MinecraftAccess.minecraft.options))
         ),
         new MainMenuScreen.NestedType393("Quit Game", () -> MinecraftAccess.minecraft.scheduleStop())
      );
   }

   @ApiAnnotation001
   public void render(DrawContext drawContext, int i, int j, float f) {
      super.render(drawContext, i, j, f);
      MenuRenderer.updateFrameTime();
      this.introTime = this.introTime + MenuRenderer.floatValueField1;
      this.transition.performAction_2528(MinecraftAccess.minecraft.currentScreen instanceof MainMenuScreen);
      this.transition.performAction_2535(0.0F, 1.0F, 0.15F, RenderComponent186.contract372Field7, f);
      float g = Math.min(1.0F, this.transition.getFloatValueField4_2530() / 0.9F);
      double d = UtilComponent279.computeValue_3580(i, 2);
      double e = UtilComponent279.computeValue_3580(j, 2);
      RenderComponent192.performAction_2676(drawContext, 2);
      int k = MinecraftAccess.minecraft.getWindow().getScaledWidth();
      int l = MinecraftAccess.minecraft.getWindow().getScaledHeight();
      Matrix3x2fStack matrix3x2fStack = drawContext.getMatrices();
      this.performAction_3179(matrix3x2fStack, k, l, (int)d, (int)e, g);
      this.performAction_3178(drawContext, matrix3x2fStack, k, l, (float)d, (float)e, g);
      if (this.pendingItem != null) {
         this.pendingTime = this.pendingTime - MenuRenderer.floatValueField1;
         if (this.pendingTime <= 0.0F) {
            Runnable runnable = this.pendingItem.action;
            this.pendingItem = null;
            runnable.run();
         }
      }

      RenderComponent192.performAction_2675(drawContext);
      PipelineComponent196.performAction_2728();
   }

   private void performAction_3179(Matrix3x2fStack matrix3x2fStack, int i, int j, int k, int l, float f) {
      this.performAction_3175(matrix3x2fStack, i, j, k, l, f);
   }

   private void performAction_3175(Matrix3x2fStack matrix3x2fStack, int i, int j, int m, int n, float f) {
      float g = i;
      float h = j;
      float k = g;
      float l = h;
      if (g / h > 1.7760416F) {
         l = g / 1.7760416F;
      } else {
         k = h * 1.7760416F;
      }

      MenuRenderer.drawTexture(matrix3x2fStack, identifierField1, (i - k) * 0.5F, -(l - j) * 0.55F, k, l, MenuRenderer.withAlpha(16777215, f));
   }

   private void performAction_3178(DrawContext drawContext, Matrix3x2fStack matrix3x2fStack, int i, int j, float f, float g, float h) {
      float k = MenuRenderer.easeOutCubic(MenuRenderer.clamp01(this.introTime / 0.7F));
      float l = h * k;
      float m = i * 0.5F;
      boolean bl = j < 330;
      float n = bl ? 22.0F : 28.0F;
      float o = bl ? 5.0F : 8.0F;
      float p = bl ? 8.0F : 14.0F;
      int q = this.items.size();
      float r = q * n + (q - 1) * o + p;
      float s = Math.max(j * 0.43F, (j - r) * 0.56F);
      float t = m - Math.min(230.0F, i - 32.0F) * 0.5F - 12.0F;
      float u = s - 10.0F;
      float v = Math.min(230.0F, i - 32.0F) + 24.0F;
      float w = r + 20.0F;
      MenuRenderer.fillRoundedRect(matrix3x2fStack, t, u, v, w, 12.0F, MenuRenderer.withAlpha(16777215, 0.56F * l));
      MenuRenderer.strokeRoundedRect(matrix3x2fStack, t, u, v, w, 12.0F, 0.7F, MenuRenderer.withAlpha(1512730, 0.15F * l));

      for (int x = 0; x < q; x++) {
         MainMenuScreen.NestedType393 nestedType393 = this.items.get(x);
         nestedType393.floatValueField3 = Math.min(230.0F, i - 32.0F);
         nestedType393.floatValueField4 = n;
         nestedType393.floatValueField1 = m - nestedType393.floatValueField3 * 0.5F;
         nestedType393.floatValueField2 = s + x * (n + o) + (x >= 3 ? p : 0.0F);
         this.performAction_3180(matrix3x2fStack, nestedType393, x, f, g, l);
      }

      String string = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
      MenuRenderer.drawText(matrix3x2fStack, string, 18.0F, 22.0F, 7.0F, MenuRenderer.withAlpha(2565418, 0.72F * l));
      MenuRenderer.drawCenteredText(
         matrix3x2fStack, "RSHIFT  CLICKGUI   |   UP / DOWN + ENTER", m, j - 14.0F, 5.2F, MenuRenderer.withAlpha(2565418, 0.48F * l * k)
      );
   }

   private void performAction_3180(Matrix3x2fStack matrix3x2fStack, MainMenuScreen.NestedType393 nestedType393, int i, float f, float g, float h) {
      float j = i * 0.045F;
      float k = MenuRenderer.easeOutCubic(MenuRenderer.clamp01((this.introTime - 0.18F - j) / 0.42F));
      float l = k * h;
      nestedType393.drawY = nestedType393.floatValueField2 + (1.0F - k) * 10.0F;
      boolean bl = k > 0.72F
         && this.pendingItem == null
         && UtilComponent279.checkCondition_3576(
            f, g, nestedType393.floatValueField1, nestedType393.drawY, nestedType393.floatValueField3, nestedType393.floatValueField4
         );
      if (bl) {
         this.integerField2 = -1;
      }

      boolean bl1 = bl || this.integerField2 == i && this.pendingItem == null;
      if (bl1 && !nestedType393.wasHover) {
         MenuRenderer.playQuietClick();
      }

      nestedType393.wasHover = bl1;
      nestedType393.hover = MenuRenderer.approach(nestedType393.hover, bl1 ? 1.0F : 0.0F, 13.0F);
      nestedType393.press = MenuRenderer.approach(nestedType393.press, 0.0F, 12.0F);
      float m = nestedType393.floatValueField1 + nestedType393.floatValueField3 * 0.5F;
      float n = nestedType393.drawY + nestedType393.floatValueField4 * 0.5F;
      float o = MenuRenderer.easeOutCubic(nestedType393.hover);
      float p = MenuRenderer.textWidth(nestedType393.title, 8.5F);
      if (nestedType393.hover > 0.01F) {
         float q = p + 40.0F;
         MenuRenderer.fillRoundedRect(
            matrix3x2fStack,
            m - q * 0.5F,
            n - 10.0F,
            q,
            20.0F,
            10.0F,
            MenuRenderer.replaceAlpha(MenuRenderer.getAccent(), 0.05F * nestedType393.hover * l)
         );
      }

      int u = MenuRenderer.withAlpha(MenuRenderer.blendRgb(3486264, 11540504, nestedType393.hover), l);
      float r = 1.0F + 0.045F * o - 0.03F * nestedType393.press;
      matrix3x2fStack.pushMatrix();
      matrix3x2fStack.translate(m, n - nestedType393.press);
      matrix3x2fStack.scale(r, r);
      matrix3x2fStack.translate(-m, -(n - nestedType393.press));
      MenuRenderer.drawCenteredText(matrix3x2fStack, nestedType393.title, m, n - nestedType393.press, 8.5F, u);
      matrix3x2fStack.popMatrix();
      if (nestedType393.hover > 0.01F) {
         float s = Math.min(nestedType393.floatValueField3 * 0.65F, p + 20.0F);
         float t = s * o;
         MenuRenderer.fillRoundedRect(
            matrix3x2fStack,
            m - t * 0.5F,
            n + 8.0F,
            t,
            1.0F,
            0.5F,
            MenuRenderer.replaceAlpha(MenuRenderer.getAccent(), 0.8F * nestedType393.hover * l)
         );
      }
   }

   private void performAction_3177(MainMenuScreen.NestedType393 nestedType393) {
      nestedType393.press = 1.0F;
      MenuRenderer.playClickSound(1.05F, 0.75F);
      this.pendingItem = nestedType393;
      this.pendingTime = 0.12F;
   }

   @ApiAnnotation001
   public boolean mouseClicked(Click click, boolean bl) {
      if (click.button() == 0 && this.pendingItem == null) {
         double d = UtilComponent279.computeValue_3580(click.x(), 2);
         double e = UtilComponent279.computeValue_3580(click.y(), 2);

         for (MainMenuScreen.NestedType393 nestedType393 : this.items) {
            if (UtilComponent279.checkCondition_3576(
               d, e, nestedType393.floatValueField1, nestedType393.drawY, nestedType393.floatValueField3, nestedType393.floatValueField4
            )) {
               this.performAction_3177(nestedType393);
               return true;
            }
         }

         return super.mouseClicked(click, bl);
      } else {
         return super.mouseClicked(click, bl);
      }
   }

   public boolean keyPressed(KeyInput keyInput) {
      int i = keyInput.key();
      if (this.pendingItem == null) {
         int j = this.items.size();
         if (i == 264) {
            this.integerField2 = this.integerField2 < 0 ? 0 : (this.integerField2 + 1) % j;
            return true;
         }

         if (i == 265) {
            this.integerField2 = this.integerField2 < 0 ? j - 1 : (this.integerField2 + j - 1) % j;
            return true;
         }

         if ((i == 257 || i == 335) && this.integerField2 >= 0) {
            this.performAction_3177(this.items.get(this.integerField2));
            return true;
         }
      }

      if (i == 344 && !Screenshare.isActive_1470()) {
         MenuRenderer.playBackSound();
         MinecraftAccess.minecraft.setScreen(new ClientSettingsScreen(this));
         return true;
      } else {
         return super.keyPressed(keyInput);
      }
   }

   public void renderBackground(DrawContext drawContext, int i, int j, float f) {
   }

   protected void applyBlur(DrawContext drawContext) {
   }

   public void renderInGameBackground(DrawContext drawContext) {
   }

   public void close() {
   }

   public static void performAction_3174(DrawContext drawContext, int i, int j, int k, int l, float f) {
      if (i > 0 && j > 0) {
         float g = i * 0.025F;
         float h = j * 0.025F;
         if (Float.isNaN(PARALLAX[0]) || Float.isInfinite(PARALLAX[0])) {
            PARALLAX[0] = 0.0F;
         }

         if (Float.isNaN(PARALLAX[1]) || Float.isInfinite(PARALLAX[1])) {
            PARALLAX[1] = 0.0F;
         }

         float m = (float)k / i;
         float n = (float)l / j;
         PARALLAX[0] = PARALLAX[0] + (MathHelper.clamp((m - 0.5F) * 2.0F * g, -g * 0.9F, g * 0.9F) - PARALLAX[0]) * 0.03F;
         PARALLAX[1] = PARALLAX[1] + (MathHelper.clamp((n - 0.5F) * 2.0F * h, -h * 0.9F, h * 0.9F) - PARALLAX[1]) * 0.03F;
         float o = i + g * 2.0F;
         float p = j + h * 2.0F;
         float q = o;
         float r = p;
         if (o / p > 1.7760416F) {
            r = o / 1.7760416F;
         } else {
            q = p * 1.7760416F;
         }

         Matrix3x2fStack matrix3x2fStack = drawContext.getMatrices();
         matrix3x2fStack.pushMatrix();
         matrix3x2fStack.translate(i * 0.5F, j * 0.5F);
         matrix3x2fStack.scale(f, f);
         matrix3x2fStack.translate(-i * 0.5F, -j * 0.5F);
         NeneClient.getInstance()
            .getProcessors()
            .getRenderComponent184Field3_0677()
            .performAction_2585(matrix3x2fStack, identifierField1, (i - q) * 0.5F + PARALLAX[0], -(r - j) * 0.55F + PARALLAX[1], q, r, 0.0F, -1);
         matrix3x2fStack.popMatrix();
      }
   }

   static {
      ObfuscatorRuntime.registerClass(MainMenuScreen.class, 16);
   }

   private static final class NestedType393 {
      final String title;
      final Runnable action;
      float floatValueField1;
      float floatValueField2;
      float floatValueField3;
      float floatValueField4;
      float drawY;
      float hover;
      float press;
      boolean wasHover;

      NestedType393(String string, Runnable runnable) {
         this.title = string;
         this.action = runnable;
      }
   }
}
