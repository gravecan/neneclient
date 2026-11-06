package aethereal.setting;

import aethereal.ui.element.MultiSelectElement;
import aethereal.ui.element.SettingElement;
import java.util.Arrays;
import java.util.List;

public class MultiSelectSetting extends Setting<List<BooleanSetting>> {
   public MultiSelectSetting(String string, BooleanSetting... booleanSettings) {
      super(string, Arrays.asList(booleanSettings));
   }

   @Override
   public SettingElement<?> createElement() {
      return new MultiSelectElement(this);
   }

   public BooleanSetting computeValue_2852(String string) {
      return this.getCurrentValue().stream().filter(booleanSetting -> booleanSetting.getName().equalsIgnoreCase(string)).findFirst().orElse(null);
   }

   public BooleanSetting computeValue_2851(int i) {
      if (i >= 0 && i < this.getCurrentValue().size()) {
         return this.getCurrentValue().get(i);
      } else {
         throw new IndexOutOfBoundsException("Index " + i + " is out of bounds for size " + this.getCurrentValue().size());
      }
   }
}
