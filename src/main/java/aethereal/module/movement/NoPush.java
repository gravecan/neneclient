package aethereal.module.movement;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.PushEvent;
import aethereal.setting.BooleanSetting;
import aethereal.setting.MultiSelectSetting;

@ModuleRegister(name = "No Push", description = "Disables pushback from selected objects", category = Category.Movement)
public class NoPush extends Module {
   private final MultiSelectSetting multiSelectSettingField1 = new MultiSelectSetting(
      "Disable collision for",
      new BooleanSetting("Water and Lava", false),
      new BooleanSetting("Blocks", false),
      new BooleanSetting("Entity", false),
      new BooleanSetting("Border", false),
      new BooleanSetting("Fishing rods", false)
   );

   public MultiSelectSetting getMultiSelectSettingField1_2047() {
      return this.multiSelectSettingField1;
   }

   public NoPush() {
      this.addSettings(this.multiSelectSettingField1);
   }

   @EventTarget
   public void onPushEvent_2046(PushEvent pushEvent) {
      switch (pushEvent.getType_1019()) {
         case FLUIDS:
            pushEvent.performAction_0597(this.multiSelectSettingField1.computeValue_2852("Water and Lava").getCurrentValue());
            break;
         case BLOCKS:
            pushEvent.performAction_0597(this.multiSelectSettingField1.computeValue_2852("Blocks").getCurrentValue());
            break;
         case ENTITIES:
            pushEvent.performAction_0597(this.multiSelectSettingField1.computeValue_2852("Entity").getCurrentValue());
            break;
         case WORLD_BORDER:
            pushEvent.performAction_0597(this.multiSelectSettingField1.computeValue_2852("Border").getCurrentValue());
            break;
         case FISHING_HOOK:
            pushEvent.performAction_0597(this.multiSelectSettingField1.computeValue_2852("Fishing rods").getCurrentValue());
      }
   }
}
