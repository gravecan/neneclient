package aethereal.ui.element;

import aethereal.render.Animation;
import net.minecraft.client.gui.DrawContext;
import org.joml.Vector4f;

public class ElementComponent211 {
   private final Animation animationField1 = new Animation();
   private final String icon;
   private final String name;

   public Animation getAnimationField1_3035() {
      return this.animationField1;
   }

   public String getIcon_3033() {
      return this.icon;
   }

   public String getName_3034() {
      return this.name;
   }

   protected ElementComponent211(String string, String string1) {
      this.icon = string;
      this.name = string1;
   }

   public void performAction_0050(DrawContext drawContext, Vector4f vector4f, int i, int j, float f, float g) {
   }

   public float getFloatValueField9_0051(Vector4f vector4f) {
      return 0.0F;
   }

   public boolean checkCondition_0044(double d, double e, int i) {
      return false;
   }

   public boolean checkCondition_3032(double d, double e, int i) {
      return false;
   }

   public boolean checkCondition_0045(double d, double e, int i, double f, double g) {
      return false;
   }

   public boolean checkCondition_3036(double d, double e, double f) {
      return false;
   }

   public boolean checkCondition_0046(int i, int j, int k) {
      return false;
   }

   public boolean checkCondition_0043(char c, int i) {
      return false;
   }
}
