package aethereal.ui.element;

import aethereal.config.SettingsPersistence;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.ui.widget.WidgetComponent249;
import aethereal.util.UtilComponent279;

public class ElementComponent210 implements MinecraftAccess {
   private WidgetComponent249 widgetComponent249Field1;
   private float floatValueField2;
   private float floatValueField3;
   private float width;
   private float height;
   private final String name;
   private double doubleValueField4 = 0.0;
   private double doubleValueField5 = 0.0;
   private int integerField6 = 0;

   public void setWidgetComponent249Field1_3016(WidgetComponent249 widgetComponent249) {
      this.widgetComponent249Field1 = widgetComponent249;
   }

   public void performAction_3015(float f) {
      if (Float.compare(this.floatValueField2, f) != 0) {
         this.floatValueField2 = f;
         SettingsPersistence.performAction_0527();
      }
   }

   public void performAction_3003(float f) {
      if (Float.compare(this.floatValueField3, f) != 0) {
         this.floatValueField3 = f;
         SettingsPersistence.performAction_0527();
      }
   }

   public void performAction_3006(float f) {
      if (Float.compare(this.width, f) != 0) {
         this.width = f;
         SettingsPersistence.performAction_0527();
      }
   }

   public void performAction_3018(float f) {
      if (Float.compare(this.height, f) != 0) {
         this.height = f;
         SettingsPersistence.performAction_0527();
      }
   }

   public void setDoubleValueField4_3008(double d) {
      this.doubleValueField4 = d;
   }

   public void setDoubleValueField5_3014(double d) {
      this.doubleValueField5 = d;
   }

   public void setIntegerField6_3004(int i) {
      this.integerField6 = i;
   }

   public WidgetComponent249 getWidgetComponent249Field1_3013() {
      return this.widgetComponent249Field1;
   }

   public float getWidth_3005() {
      return this.width;
   }

   public float getHeight_3002() {
      return this.height;
   }

   public double getDoubleValueField4_3012() {
      return this.doubleValueField4;
   }

   public double getDoubleValueField5_3017() {
      return this.doubleValueField5;
   }

   public String getName_3019() {
      return this.name;
   }

   public int getIntegerField6_3020() {
      return this.integerField6;
   }

   public ElementComponent210(String string, float f, float g, float h, float i) {
      this.name = string;
      this.floatValueField2 = f;
      this.floatValueField3 = g;
      this.width = h;
      this.height = i;
      NeneClient.getInstance().getProcessors().getDragProcessorField7_0680().getEntries().add(this);
   }

   public float computeValue_3007() {
      return UtilComponent279.computeValue_3577(
         this.floatValueField2,
         0.0F,
         minecraft.getWindow().getFramebufferWidth() / minecraft.getWindow().calculateScaleFactor(2, minecraft.forcesUnicodeFont()) - this.width
      );
   }

   public float computeValue_3009() {
      return UtilComponent279.computeValue_3577(
         this.floatValueField3,
         0.0F,
         minecraft.getWindow().getFramebufferHeight() / minecraft.getWindow().calculateScaleFactor(2, minecraft.forcesUnicodeFont()) - this.height
      );
   }

   public float getFloatValueField2_3010() {
      return this.floatValueField2;
   }

   public float getFloatValueField3_3011() {
      return this.floatValueField3;
   }
}
