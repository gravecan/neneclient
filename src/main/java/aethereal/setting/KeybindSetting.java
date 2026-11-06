package aethereal.setting;

import aethereal.core.Action;
import aethereal.ui.element.KeybindElement;
import aethereal.ui.element.SettingElement;

public class KeybindSetting extends Setting<Integer> {
   private Action actionField1;
   private Action actionField2;
   private final int type;

   public Action getActionField1_2845() {
      return this.actionField1;
   }

   public Action getActionField2_2842() {
      return this.actionField2;
   }

   public int getType_2844() {
      return this.type;
   }

   public KeybindSetting(String string, Integer integer) {
      super(string, integer);
      this.type = 1;
   }

   public KeybindSetting(String string, Integer integer, int i) {
      super(string, integer);
      this.type = i;
   }

   public KeybindSetting computeValue_2847(Action action) {
      this.actionField1 = action;
      return this;
   }

   public KeybindSetting computeValue_2843(Action action) {
      this.actionField2 = action;
      return this;
   }

   @Override
   public SettingElement<?> createElement() {
      return new KeybindElement(this);
   }
}
