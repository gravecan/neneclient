package aethereal.ui.element;

import aethereal.event.DrawEvent;
import aethereal.render.Animation;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent187;
import aethereal.setting.Setting;
import net.minecraft.client.gui.DrawContext;
import org.joml.Matrix3x2f;
import org.joml.Vector4f;

public class SettingElement<SettingType extends Setting<?>> {
   private final Animation animationField1 = new Animation();
   private final Animation animationField2 = new Animation();
   protected final Vector4f vector4fField3 = new Vector4f();
   protected final SettingType setting;
   protected float floatValueField4;

   public boolean checkCondition_2915(double d, double e, int i) {
      return false;
   }

   public boolean checkCondition_2911(double d, double e, int i) {
      return false;
   }

   public boolean checkCondition_2916(double d, double e, int i, double f, double g) {
      return false;
   }

   public boolean checkCondition_3026(double d, double e, double f) {
      return false;
   }

   public boolean checkCondition_3025(char c, int i) {
      return false;
   }

   public boolean checkCondition_2983(int i, int j, int k) {
      return false;
   }

   public void setFloatValueField4_3027(float f) {
      this.floatValueField4 = f;
   }

   public Animation getAnimationField1_3029() {
      return this.animationField1;
   }

   public Animation getAnimationField2_3030() {
      return this.animationField2;
   }

   public Vector4f getVector4fField3_3024() {
      return this.vector4fField3;
   }

   public SettingType getSetting_3021() {
      return this.setting;
   }

   public float getFloatValueField4_3022() {
      return this.floatValueField4;
   }

   public SettingElement(SettingType setting) {
      this.setting = (SettingType)settingx;
   }

   public boolean checkCondition_3023() {
      return this.setting.getVisibility().get();
   }

   public void performAction_2918(DrawContext drawContext, double d, double e, float f, float g) {
   }

   protected void performAction_3028(
      Matrix3x2f matrix3x2f,
      RenderComponent187 renderComponent187,
      String string,
      float f,
      float g,
      float h,
      float i,
      int j,
      float k,
      boolean bl,
      float l,
      float m
   ) {
      this.floatValueField4 = renderComponent187.computeValue_2633(
         matrix3x2f,
         string,
         f,
         g + (h - renderComponent187.computeValue_2622(i)) / 2.0F - 0.5F,
         i,
         RenderComponent183.computeValue_2549(j, l),
         k,
         bl,
         this.floatValueField4,
         m
      );
   }

   public void performAction_2917(DrawContext drawContext, double d, double e, float f) {
   }

   public void performAction_2984(DrawEvent drawEvent, float f, float g, float h, float i) {
   }
}
