package aethereal.module.player;

import aethereal.core.Category;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.setting.FloatSetting;
import aethereal.util.UtilComponent268;

@ModuleRegister(name = "Item Scroller", description = "Allows you to quickly rearrange items in windows by scrolling", category = Category.Player)
public class ItemScroller extends Module {
   private final FloatSetting floatSettingField1 = new FloatSetting("Delay between slots", 50.0F, 0.0F, 100.0F, 1.0F);
   private final UtilComponent268 utilComponent268Field2 = new UtilComponent268();

   public FloatSetting getFloatSettingField1_2199() {
      return this.floatSettingField1;
   }

   public UtilComponent268 getUtilComponent268Field2_2200() {
      return this.utilComponent268Field2;
   }

   public ItemScroller() {
      this.addSettings(this.floatSettingField1);
   }
}
