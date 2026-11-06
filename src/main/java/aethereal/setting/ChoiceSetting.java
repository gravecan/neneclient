package aethereal.setting;

import aethereal.ui.element.ChoiceElement;
import aethereal.ui.element.SettingElement;
import java.util.Arrays;
import java.util.List;

public class ChoiceSetting extends Setting<String> {
   private final List<String> listField1;

   public List<String> getListField1_2849() {
      return this.listField1;
   }

   public ChoiceSetting(String string, String string1, String... strings) {
      super(string, string1);
      this.listField1 = Arrays.asList(strings);
   }

   public boolean checkCondition_2850(String string) {
      return this.getCurrentValue().equalsIgnoreCase(string);
   }

   @Override
   public SettingElement<?> createElement() {
      return new ChoiceElement(this);
   }
}
