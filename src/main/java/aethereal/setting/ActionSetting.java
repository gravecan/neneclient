package aethereal.setting;

import aethereal.core.Action;
import aethereal.ui.element.ActionElement;
import aethereal.ui.element.SettingElement;

public class ActionSetting extends Setting<Boolean> {
   private final Action action;

   public ActionSetting(String string, Action action) {
      super(string, false);
      this.action = actionx;
   }

   public void performAction_2848() {
      if (this.action != null) {
         this.action.execute();
      }
   }

   @Override
   public SettingElement<?> createElement() {
      return new ActionElement(this);
   }
}
