package aethereal.ui.screen;

import aethereal.core.MinecraftAccess;
import aethereal.handler.HandlerComponent108;
import aethereal.render.pipeline.PipelineComponent196;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.joml.Vector2f;

public class ScreenComponent225 extends Screen implements MinecraftAccess {
   private final ScreenComponent223 screenComponent223Field1 = new ScreenComponent223(3, 75.0F, 101.25F);
   private boolean flagField2;

   public boolean isFlagField2_3253() {
      return this.flagField2;
   }

   public void setFlagField2_3251(boolean bl) {
      this.flagField2 = bl;
   }

   public ScreenComponent225(Text text) {
      super(text);

      for (int i = 0; i < 3; i++) {
         int j = i;
         this.screenComponent223Field1.performAction_3240(j, ItemStack.EMPTY, () -> this.performAction_3249(j), true);
      }
   }

   private void performAction_3249(int i) {
      ItemStack itemStack = this.screenComponent223Field1.computeValue_3236(i);
      if (itemStack.isEmpty()) {
         minecraft.setScreen(new InventoryScreen(minecraft.player));
         this.setFlagField2_3251(true);
      } else {
         HandlerComponent108.getINVENTORY_1158().performAction_1182(itemStack, 45, 1);
         minecraft.player.closeScreen();
      }
   }

   public boolean mouseClicked(Click click, boolean bl) {
      return this.screenComponent223Field1.checkCondition_3233(click.x(), click.y(), click.button(), new Vector2f(this.width / 2.0F, this.height / 2.0F));
   }

   public void render(DrawContext drawContext, int i, int j, float f) {
      this.screenComponent223Field1.performAction_3242(drawContext, i, j, new Vector2f(this.width / 2.0F, this.height / 2.0F));
      PipelineComponent196.performAction_2728();
   }

   public void performAction_3252() {
      if (minecraft.getWindow().getFramebufferWidth() > 0 && minecraft.getWindow().getFramebufferHeight() > 0) {
         double d = minecraft.mouse.getX() * this.width / minecraft.getWindow().getFramebufferWidth();
         double e = minecraft.mouse.getY() * this.height / minecraft.getWindow().getFramebufferHeight();
         int i = this.screenComponent223Field1.computeValue_3234(d, e, new Vector2f(this.width / 2.0F, this.height / 2.0F));
         if (i >= 0) {
            ItemStack itemStack = this.screenComponent223Field1.computeValue_3236(i);
            if (!itemStack.isEmpty()) {
               this.performAction_3249(i);
            }
         }
      }
   }

   public int getScreenComponent223Field1_3250() {
      return this.screenComponent223Field1.getIntegerField2_3231();
   }

   public void performAction_3248(int i) {
      this.screenComponent223Field1.setIntegerField2_3229(i);
   }

   public void performAction_3254(int i, ItemStack itemStack) {
      this.screenComponent223Field1.performAction_3239(i, itemStack);
   }

   public ItemStack computeValue_3247(int i) {
      return this.screenComponent223Field1.computeValue_3236(i);
   }
}
