package aethereal.ui.element;

import aethereal.setting.StringSetting;
import net.minecraft.client.gui.DrawContext;
import org.joml.Vector2f;

public class StringElement extends SettingElement<StringSetting> {
   private TextField textFieldField1;

   @Override
   public boolean checkCondition_2915(double d, double e, int i) {
      TextField textField = this.computeValue_3038();
      if (textField != null) {
         textField.performAction_2959(d, e, i);
         return textField.isFlagField7_2939();
      } else {
         return false;
      }
   }

   @Override
   public boolean checkCondition_2916(double d, double e, int i, double f, double g) {
      if (this.textFieldField1 == null) {
         return false;
      }

      this.textFieldField1.performAction_2941(d, e, i);
      return this.textFieldField1.isFlagField7_2939();
   }

   @Override
   public boolean checkCondition_2983(int i, int j, int k) {
      if (this.textFieldField1 != null && this.textFieldField1.isFlagField7_2939()) {
         this.textFieldField1.performAction_2963(i, j, k);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean checkCondition_3025(char c, int i) {
      if (this.textFieldField1 != null && this.textFieldField1.isFlagField7_2939()) {
         this.textFieldField1.performAction_2958(c, i);
         return true;
      } else {
         return false;
      }
   }

   public StringElement(StringSetting stringSetting) {
      super(stringSetting);
      this.vector4fField3.w = 12.0F;
   }

   private TextField computeValue_3038() {
      if (this.textFieldField1 == null) {
         this.textFieldField1 = new TextField(TextField.State384.GUI_SETTING, this.setting.isNumbers_2868());
         this.textFieldField1.setStringField3_2969(this.setting.getName());
         this.textFieldField1.getStringBuilderField4_2956().append(this.setting.getCurrentValue());
      }

      return this.textFieldField1;
   }

   @Override
   public void performAction_2918(DrawContext drawContext, double d, double e, float f, float g) {
      TextField textField = this.computeValue_3038();
      textField.setVector2fField2_2944(new Vector2f(this.vector4fField3.z, this.vector4fField3.w));
      textField.setVector2fField1_2966(new Vector2f(this.vector4fField3.x, this.vector4fField3.y));
      String string = this.setting.getCurrentValue();
      if (!textField.isFlagField7_2939() && !textField.getStringBuilderField4_2956().toString().equals(string)) {
         textField.getStringBuilderField4_2956().setLength(0);
         textField.getStringBuilderField4_2956().append(string != null ? string : "");
      }

      textField.performAction_2964(drawContext, d, e, f, g);
      if (textField.isFlagField7_2939()) {
         String string1 = textField.getStringBuilderField4_2956().toString();
         if (!string1.equals(string)) {
            this.setting.setValue(string1);
         }
      }
   }
}
