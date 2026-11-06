package aethereal.setting;

import aethereal.ui.element.BooleanElement;
import aethereal.ui.element.SettingElement;

public class BooleanSetting extends Setting<Boolean> {
   public BooleanSetting(String string, Boolean boolean_) {
      super(string, boolean_);
   }

   @Override
   public SettingElement<?> createElement() {
      return new BooleanElement(this);
   }
}
