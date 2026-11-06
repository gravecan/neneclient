package aethereal.setting;

import aethereal.ui.element.ColorElement;
import aethereal.ui.element.SettingElement;

public class ColorSetting extends Setting<Integer> {
   public ColorSetting(String string, Integer integer) {
      super(string, integer);
   }

   @Override
   public SettingElement<?> createElement() {
      return new ColorElement(this);
   }
}
