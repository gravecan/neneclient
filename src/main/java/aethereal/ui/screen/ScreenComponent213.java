package aethereal.ui.screen;

import aethereal.autobuy.AutoBuyEntry;
import aethereal.config.ThemeInfo;
import aethereal.config.ThemeManager;
import aethereal.core.Action;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.handler.HandlerComponent108;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent184;
import aethereal.render.RenderComponent185;
import aethereal.render.RenderComponent188;
import aethereal.render.pipeline.PipelineComponent196;
import aethereal.util.UtilComponent279;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import org.joml.Matrix3x2fStack;
import org.joml.Vector2f;

public class ScreenComponent213 extends Screen implements MinecraftAccess {
   private final ScreenComponent223 screenComponent223Field1 = new ScreenComponent223(2, 75.0F, 101.25F);
   private final List<AutoBuyEntry> listField2 = Arrays.stream(AutoBuyEntry.values())
      .filter(autoBuyEntry -> autoBuyEntry.getItem_0031() == Items.SPLASH_POTION)
      .toList();
   private Vector2f vector2fField3 = null;
   private Vector2f vector2fField4 = null;
   private int integerField5 = -1;
   private int integerField6 = -1;

   public ScreenComponent223 getScreenComponent223Field1_3069() {
      return this.screenComponent223Field1;
   }

   public int getIntegerField6_3068() {
      return this.integerField6;
   }

   public ScreenComponent213(Text text) {
      super(text);
      this.performAction_3065(this.screenComponent223Field1.getNestedType394ArrayField1_3232());
   }

   public void performAction_3065(int i) {
      this.screenComponent223Field1.performAction_3237(Math.max(i, 1));

      for (int j = 0; j < this.screenComponent223Field1.getNestedType394ArrayField1_3232(); j++) {
         this.screenComponent223Field1.performAction_3240(j, ItemStack.EMPTY, this.computeValue_3071(j), true);
      }
   }

   public void performAction_3074(int i, AutoBuyEntry autoBuyEntry) {
      this.screenComponent223Field1
         .performAction_3241(i, autoBuyEntry.computeValue_0025(), autoBuyEntry.getDisplayName_0029(), this.computeValue_3071(i), true);
   }

   public boolean mouseClicked(Click click, boolean bl1) {
      double d = click.x();
      double e = click.y();
      int i = click.button();
      if (i == 0 && this.integerField5 != -1 && this.vector2fField3 != null && this.vector2fField4 != null) {
         for (int n = 0; n < this.listField2.size(); n++) {
            float f = this.vector2fField3.y + n * 18.0F;
            if (UtilComponent279.checkCondition_3576(d, e, this.vector2fField3.x, f, this.vector2fField4.x, 18.0F)) {
               AutoBuyEntry autoBuyEntry = this.listField2.get(n);

               for (int o = 0; o < this.screenComponent223Field1.getNestedType394ArrayField1_3232(); o++) {
                  if (autoBuyEntry.checkCondition_0027(this.screenComponent223Field1.computeValue_3236(o))) {
                     this.performAction_3067();
                     return true;
                  }
               }

               int p = this.integerField5;
               this.screenComponent223Field1
                  .performAction_3241(p, autoBuyEntry.computeValue_0025(), autoBuyEntry.getDisplayName_0029(), this.computeValue_3071(p), true);
               this.performAction_3067();
               return true;
            }
         }

         this.performAction_3067();
         return true;
      } else {
         if (i == 2) {
            int m = this.screenComponent223Field1.getNestedType394ArrayField1_3232();
            this.screenComponent223Field1.performAction_3237(m + 1);
            this.screenComponent223Field1.performAction_3240(m, ItemStack.EMPTY, this.computeValue_3071(m), true);
            return true;
         }

         Vector2f vector2f = new Vector2f(this.width / 2.0F, this.height / 2.0F);
         int j;
         if (i == 0
            && (j = this.screenComponent223Field1.computeValue_3234(d, e, vector2f)) >= 0
            && !this.screenComponent223Field1.computeValue_3236(j).isEmpty()) {
            this.integerField6 = j;
            return true;
         }

         int k = this.screenComponent223Field1.getNestedType394ArrayField1_3232();
         boolean bl = this.screenComponent223Field1.checkCondition_3233(d, e, i, vector2f);
         if (this.screenComponent223Field1.getNestedType394ArrayField1_3232() != k) {
            this.performAction_3067();

            for (int l = 0; l < this.screenComponent223Field1.getNestedType394ArrayField1_3232(); l++) {
               this.screenComponent223Field1.performAction_3238(l, this.computeValue_3071(l));
            }
         }

         return bl;
      }
   }

   public boolean mouseReleased(Click click) {
      if (click.button() == 0 && this.integerField6 != -1) {
         this.performAction_3073(this.screenComponent223Field1.computeValue_3234(click.x(), click.y(), new Vector2f(this.width / 2.0F, this.height / 2.0F)));
         return true;
      } else {
         return super.mouseReleased(click);
      }
   }

   public void performAction_3073(int i) {
      if (this.integerField6 != -1) {
         if (i >= 0 && i != this.integerField6) {
            this.screenComponent223Field1.performAction_3230(this.integerField6, i);
         } else {
            this.screenComponent223Field1.computeValue_3227(this.integerField6).execute();
         }

         this.integerField6 = -1;
      }
   }

   public void render(DrawContext drawContext, int i, int j, float f) {
      this.screenComponent223Field1.performAction_3242(drawContext, i, j, new Vector2f(this.width / 2.0F, this.height / 2.0F));
      if (this.integerField6 != -1
         && this.screenComponent223Field1.computeValue_3234(i, j, new Vector2f(this.width / 2.0F, this.height / 2.0F)) != this.integerField6) {
         NeneClient.getInstance()
            .getProcessors()
            .getRenderComponent185Field4_0678()
            .performAction_2592(drawContext, this.screenComponent223Field1.computeValue_3236(this.integerField6), i - 8, j - 8, 300, 1.0F, 1.0F, false);
      }

      if (this.integerField5 != -1 && this.vector2fField3 != null) {
         this.performAction_3076(drawContext, i, j);
      }

      PipelineComponent196.performAction_2728();
   }

   private void performAction_3076(DrawContext drawContext, int i, int j) {
      Matrix3x2fStack matrix3x2fStack = drawContext.getMatrices();
      RenderComponent184 renderComponent184 = NeneClient.getInstance().getProcessors().getRenderComponent184Field3_0677();
      RenderComponent185 renderComponent185 = NeneClient.getInstance().getProcessors().getRenderComponent185Field4_0678();
      ThemeManager themeManager = NeneClient.getInstance().getProcessors().getThemes();
      renderComponent184.performAction_2580(
         matrix3x2fStack,
         this.vector2fField3.x,
         this.vector2fField3.y,
         this.vector2fField4.x,
         this.vector2fField4.y,
         8.0F,
         themeManager.computeValue_0557(ThemeInfo.BACKGROUND_GUI).computeValue_0547(),
         1.0F,
         themeManager.computeValue_0557(ThemeInfo.BACKGROUND_GUI).computeValue_0547(),
         11.0F
      );
      renderComponent184.performAction_2576(
         matrix3x2fStack,
         this.vector2fField3.x,
         this.vector2fField3.y,
         this.vector2fField4.x,
         this.vector2fField4.y,
         8.0F,
         1.0F,
         RenderComponent183.computeValue_2558(255, 255, 255, 15)
      );

      for (int k = 0; k < this.listField2.size(); k++) {
         AutoBuyEntry autoBuyEntry = this.listField2.get(k);
         float f = this.vector2fField3.y + k * 18.0F;
         boolean bl = UtilComponent279.checkCondition_3576(i, j, this.vector2fField3.x, f, this.vector2fField4.x, 18.0F);
         if (bl) {
            renderComponent184.performAction_2577(
               matrix3x2fStack,
               this.vector2fField3.x + 2.0F,
               f + 1.0F,
               this.vector2fField4.x - 4.0F,
               16.0F,
               6.0F,
               RenderComponent183.computeValue_2558(255, 255, 255, 12)
            );
         }

         renderComponent185.performAction_2592(
            drawContext, autoBuyEntry.computeValue_0025(), this.vector2fField3.x + 5.0F, f + 9.0F - 6.0F, 0, 1.0F, 0.75F, false
         );
         RenderComponent188.renderComponent187Field5
            .performAction_2630(
               matrix3x2fStack,
               autoBuyEntry.getDisplayName_0029(),
               this.vector2fField3.x + 5.0F + 12.0F + 4.0F,
               f + 9.0F - RenderComponent188.renderComponent187Field5.computeValue_2622(6.5F) / 2.0F,
               6.5F,
               bl ? RenderComponent183.computeValue_2558(255, 255, 255, 220) : RenderComponent183.computeValue_2558(255, 255, 255, 150)
            );
      }
   }

   private Action computeValue_3071(int i) {
      return () -> {
         if (this.screenComponent223Field1.computeValue_3236(i).isEmpty()) {
            this.integerField5 = i;
            this.vector2fField3 = this.computeValue_3072(i);
            this.vector2fField4 = this.computeValue_3070();
         } else {
            HandlerComponent108.getUSEABLE_1160().performAction_1225(this.screenComponent223Field1.computeValue_3236(i));
            minecraft.player.closeScreen();
         }
      };
   }

   private void performAction_3067() {
      this.integerField5 = -1;
      this.vector2fField3 = null;
      this.vector2fField4 = null;
   }

   private Vector2f computeValue_3070() {
      float f = 0.0F;

      for (AutoBuyEntry autoBuyEntry : this.listField2) {
         f = Math.max(f, RenderComponent188.renderComponent187Field5.computeValue_2626(autoBuyEntry.getDisplayName_0029(), 6.5F));
      }

      return new Vector2f(26.0F + f + 10.0F, this.listField2.size() * 18.0F);
   }

   private Vector2f computeValue_3072(int i) {
      double d = 6.28318530719644 / this.screenComponent223Field1.getNestedType394ArrayField1_3232();
      double e = -1.5707963569506784 + d * i;
      return new Vector2f(
         this.width / 2.0F + (float)Math.cos(e + d / 2.0) * 88.125F + 12.0F, this.height / 2.0F + (float)Math.sin(e + d / 2.0) * 88.125F + 12.0F
      );
   }
}
