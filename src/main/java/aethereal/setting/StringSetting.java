package aethereal.setting;

import aethereal.ui.element.SettingElement;
import aethereal.ui.element.StringElement;

public class StringSetting extends Setting<String> {
   private final boolean numbers;

   public boolean isNumbers_2868() {
      return this.numbers;
   }

   public StringSetting(String string, String string1) {
      super(string, string1);
      this.numbers = false;
   }

   public StringSetting(String string, String string1, boolean bl) {
      super(string, string1);
      this.numbers = bl;
   }

   @Override
   public SettingElement<?> createElement() {
      return new StringElement(this);
   }
}
