package aethereal.ui.screen;

import aethereal.core.Module;
import aethereal.core.NeneClient;
import aethereal.friend.Friend;
import aethereal.module.combat.NoFriendDamage;
import aethereal.ui.element.TextField;
import aethereal.util.UtilComponent279;
import java.util.List;
import net.minecraft.client.gui.DrawContext;
import org.joml.Matrix3x2fStack;
import org.joml.Vector2f;

public final class ScreenComponent216 {
   private final TextField name = new TextField(TextField.State384.GUI);
   private float lastDraw = -10.0F;
   private float floatValueField1;
   private float toggleHover;
   private float inputX;
   private float inputY;
   private float inputW;
   private float addX;
   private float addY;
   private float addW;
   private float toggleX;
   private float toggleY;
   private float toggleW;
   private float rowsX;
   private float rowsY;
   private float rowW;
   private float rowH;
   private float[] removeX = new float[0];
   private float[] removeY = new float[0];

   public void performAction_3107(DrawContext drawContext, Matrix3x2fStack matrix3x2fStack, float f, float g, float h, float bc, float i, int j, int k, float l) {
      if (MenuRenderer.time - this.lastDraw > 0.25F) {
         this.floatValueField1 = 0.0F;
      }

      this.lastDraw = MenuRenderer.time;
      this.floatValueField1 = this.floatValueField1 + MenuRenderer.floatValueField1;
      float m = MenuRenderer.easeOutCubic(MenuRenderer.clamp01(this.floatValueField1 / 0.32F));
      float n = i * m;
      float o = (1.0F - m) * 9.0F;
      ScreenComponent214.performAction_3077(matrix3x2fStack, "YOUR FRIENDS", f, g + 4.0F + o, h, n);
      MenuRenderer.drawText(
         matrix3x2fStack,
         "Manage trusted players and protect them from your attacks.",
         f,
         g + 17.0F + o,
         5.5F,
         MenuRenderer.withAlpha(11051439, 0.7F * n)
      );
      float p = g + 29.0F + o;
      float q = 38.0F;
      MenuRenderer.fillRoundedRect(matrix3x2fStack, f, p, h, q, 8.0F, MenuRenderer.withAlpha(16777215, 0.045F * n));
      MenuRenderer.strokeRoundedRect(matrix3x2fStack, f, p, h, q, 8.0F, 0.5F, MenuRenderer.withAlpha(16777215, 0.08F * n));
      this.inputX = f + 8.0F;
      this.inputY = p + 8.0F;
      this.inputW = Math.max(48.0F, h - 80.0F);
      this.name.setVector2fField1_2966(new Vector2f(this.inputX, this.inputY));
      this.name.setVector2fField2_2944(new Vector2f(this.inputW, 22.0F));
      this.name.performAction_2964(drawContext, j, k, l, n);
      this.addW = 54.0F;
      this.addX = f + h - this.addW - 8.0F;
      this.addY = this.inputY;
      boolean bl = UtilComponent279.checkCondition_3576(j, k, this.addX, this.addY, this.addW, 22.0F);
      MenuRenderer.fillRoundedRect(
         matrix3x2fStack,
         this.addX,
         this.addY,
         this.addW,
         22.0F,
         6.0F,
         MenuRenderer.replaceAlpha(MenuRenderer.getAccent(), (0.62F + (bl ? 0.2F : 0.0F)) * n)
      );
      MenuRenderer.drawCenteredText(
         matrix3x2fStack, "ADD FRIEND", this.addX + this.addW / 2.0F, this.addY + 11.0F, 5.5F, MenuRenderer.withAlpha(16777215, n)
      );
      float r = p + q + 8.0F;
      this.toggleW = Math.min(h, 200.0F);
      this.toggleX = f;
      this.toggleY = r;
      Module module = this.computeValue_3106();
      boolean bl1 = module != null && module.isEnabled();
      this.toggleHover = MenuRenderer.approach(
         this.toggleHover, UtilComponent279.checkCondition_3576(j, k, this.toggleX, this.toggleY, this.toggleW, 34.0F) ? 1.0F : 0.0F, 14.0F
      );
      MenuRenderer.fillRoundedRect(
         matrix3x2fStack,
         this.toggleX,
         this.toggleY,
         this.toggleW,
         34.0F,
         7.0F,
         MenuRenderer.withAlpha(MenuRenderer.blendRgb(1446428, 2300717, this.toggleHover), n)
      );
      MenuRenderer.strokeRoundedRect(
         matrix3x2fStack,
         this.toggleX,
         this.toggleY,
         this.toggleW,
         34.0F,
         7.0F,
         0.5F,
         MenuRenderer.withAlpha(16777215, (0.06F + 0.08F * this.toggleHover) * n)
      );
      MenuRenderer.drawText(
         matrix3x2fStack, "NO FRIEND DAMAGE", this.toggleX + 9.0F, this.toggleY + 13.0F, 6.0F, MenuRenderer.withAlpha(16777215, n)
      );
      MenuRenderer.drawText(
         matrix3x2fStack,
         "Prevent attacks against listed friends",
         this.toggleX + 9.0F,
         this.toggleY + 25.0F,
         5.0F,
         MenuRenderer.withAlpha(11051439, 0.72F * n)
      );
      float s = this.toggleX + this.toggleW - 34.0F;
      float t = this.toggleY + 10.0F;
      MenuRenderer.fillRoundedRect(
         matrix3x2fStack, s, t, 25.0F, 14.0F, 7.0F, MenuRenderer.withAlpha(bl1 ? MenuRenderer.getAccent() : 3814213, n)
      );
      float u = bl1 ? 19.0F : 7.0F;
      MenuRenderer.fillCircle(matrix3x2fStack, s + u, t + 7.0F, 5.0F, MenuRenderer.withAlpha(16777215, n));
      List list = NeneClient.getInstance().getProcessors().getFriends().computeValue_1108();
      float v = r + 44.0F;
      MenuRenderer.drawText(matrix3x2fStack, "FRIENDS", f, v + 6.0F, 5.5F, MenuRenderer.withAlpha(16777215, 0.45F * n));
      MenuRenderer.drawRightAlignedText(matrix3x2fStack, list.size() + " SAVED", f + h, v + 6.0F, 5.2F, MenuRenderer.withAlpha(16777215, 0.3F * n));
      this.rowsX = f;
      this.rowsY = v + 15.0F;
      this.rowW = h;
      this.rowH = 25.0F;
      this.removeX = new float[list.size()];
      this.removeY = new float[list.size()];
      if (list.isEmpty()) {
         float w = this.rowsY + 8.0F;
         MenuRenderer.fillRoundedRect(matrix3x2fStack, f, w, h, 42.0F, 7.0F, MenuRenderer.withAlpha(16777215, 0.025F * n));
         MenuRenderer.drawCenteredText(matrix3x2fStack, "No friends yet", f + h / 2.0F, w + 17.0F, 6.5F, MenuRenderer.withAlpha(16777215, 0.62F * n));
         MenuRenderer.drawCenteredText(
            matrix3x2fStack, "Add a player name above to get started", f + h / 2.0F, w + 30.0F, 5.0F, MenuRenderer.withAlpha(11051439, 0.48F * n)
         );
      }

      for (int bb = 0; bb < list.size(); bb++) {
         float x = bb * 0.045F;
         float y = MenuRenderer.easeOutCubic(MenuRenderer.clamp01((this.floatValueField1 - 0.12F - x) / 0.28F)) * n;
         float z = this.rowsY + bb * (this.rowH + 4.0F) + (1.0F - y / Math.max(n, 0.001F)) * 5.0F;
         boolean bl2 = UtilComponent279.checkCondition_3576(j, k, f, z, h, this.rowH);
         MenuRenderer.fillRoundedRect(
            matrix3x2fStack, f, z, h, this.rowH, 6.0F, MenuRenderer.withAlpha(MenuRenderer.blendRgb(1446428, 2300717, bl2 ? 0.8F : 0.0F), y)
         );
         MenuRenderer.fillCircle(matrix3x2fStack, f + 12.0F, z + this.rowH / 2.0F, 3.0F, MenuRenderer.replaceAlpha(4906624, y));
         MenuRenderer.drawText(
            matrix3x2fStack,
            MenuRenderer.ellipsize(((Friend)list.get(bb)).getName_1103(), 6.0F, h - 58.0F),
            f + 22.0F,
            z + this.rowH / 2.0F,
            6.0F,
            MenuRenderer.withAlpha(16777215, y)
         );
         float ba = 32.0F;
         this.removeX[bb] = f + h - ba - 5.0F;
         this.removeY[bb] = z;
         boolean bl3 = UtilComponent279.checkCondition_3576(j, k, this.removeX[bb], z, ba, this.rowH);
         MenuRenderer.fillRoundedRect(
            matrix3x2fStack,
            this.removeX[bb],
            z + 4.0F,
            ba,
            this.rowH - 8.0F,
            4.0F,
            MenuRenderer.withAlpha(16733028, (0.08F + (bl3 ? 0.16F : 0.0F)) * y)
         );
         MenuRenderer.drawCenteredText(
            matrix3x2fStack, "REMOVE", this.removeX[bb] + ba / 2.0F, z + this.rowH / 2.0F, 4.5F, MenuRenderer.withAlpha(16746386, y)
         );
      }
   }

   private Module computeValue_3106() {
      return NeneClient.getInstance()
         .getProcessors()
         .getModules()
         .getEntries()
         .stream()
         .filter(module -> module instanceof NoFriendDamage)
         .findFirst()
         .orElse(null);
   }

   private void as() {
      String string = this.name.getStringBuilderField4_2956().toString().trim();
      if (!string.isEmpty() && string.length() <= 32 && !string.chars().anyMatch(Character::isISOControl)) {
         NeneClient.getInstance().getProcessors().getFriends().performAction_1106(string);
         NeneClient.getInstance().getProcessors().getFriends().performAction_0357();
         this.name.performAction_2948();
         MenuRenderer.playClickSound(1.5F, 0.8F);
      }
   }

   public boolean checkCondition_3108(double d, double e, int i) {
      this.name.performAction_2959(d, e, i);
      if (i != 0) {
         return false;
      }

      if (UtilComponent279.checkCondition_3576(d, e, this.addX, this.addY, this.addW, 22.0F)) {
         this.as();
         return true;
      }

      if (UtilComponent279.checkCondition_3576(d, e, this.toggleX, this.toggleY, this.toggleW, 34.0F)) {
         Module module = this.computeValue_3106();
         if (module != null) {
            module.setEnabled(!module.isEnabled());
            MenuRenderer.playToggleSound(module.isEnabled());
         }

         return true;
      } else {
         List list = NeneClient.getInstance().getProcessors().getFriends().computeValue_1108();

         for (int j = 0; j < Math.min(list.size(), this.removeX.length); j++) {
            if (UtilComponent279.checkCondition_3576(d, e, this.removeX[j], this.removeY[j], 32.0F, this.rowH)) {
               NeneClient.getInstance().getProcessors().getFriends().performAction_1111(((Friend)list.get(j)).getName_1103());
               NeneClient.getInstance().getProcessors().getFriends().performAction_0357();
               MenuRenderer.playClickSound(0.85F, 0.8F);
               return true;
            }
         }

         return false;
      }
   }

   public boolean checkCondition_3102(int i, int j, int k) {
      if (!this.name.isFlagField7_2939()) {
         return false;
      } else if (i == 257) {
         this.as();
         return true;
      } else {
         this.name.performAction_2963(i, j, k);
         return true;
      }
   }

   public boolean checkCondition_3101(char c, int i) {
      if (!this.name.isFlagField7_2939()) {
         return false;
      }

      this.name.performAction_2958(c, i);
      return true;
   }

   public boolean checkCondition_3104(double d, double e, int i) {
      this.name.performAction_2941(d, e, i);
      return false;
   }

   public void performAction_3105() {
   }
}
