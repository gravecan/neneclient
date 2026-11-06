package aethereal.setting;

import aethereal.ui.element.FloatElement;
import aethereal.ui.element.SettingElement;

public class FloatSetting extends Setting<Float> {
   public float minimum;
   public float maximum;
   public float increment;
   public float initialValue;
   public boolean scroll;

   public FloatSetting(String string, float f, float g, float h, float i) {
      this(string, f, g, h, i, false);
   }

   public FloatSetting(String string, float f, float g, float h, float i, boolean bl) {
      super(string, f);
      this.minimum = g;
      this.maximum = h;
      this.initialValue = f;
      this.increment = i;
      this.scroll = bl;
   }

   @Override
   public SettingElement<?> createElement() {
      return new FloatElement(this);
   }
}
