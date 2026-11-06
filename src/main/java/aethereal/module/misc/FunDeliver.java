package aethereal.module.misc;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent074;
import aethereal.network.NetworkComponent173;
import aethereal.setting.FloatSetting;
import aethereal.setting.StringSetting;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@ModuleRegister(name = "Fun Deliver", description = "Automatic issuance of currency for FunPay orders", category = Category.Misc)
public class FunDeliver extends Module implements MinecraftAccess {
   private final StringSetting stringSettingField1 = new StringSetting("Enter your Golden-Key", "").withoutPersistence();
   private final FloatSetting floatSettingField2 = new FloatSetting("Processing Cost Trigger", 1.0F, 0.5F, 10.0F, 0.01F, true);
   private final FloatSetting floatSettingField3 = new FloatSetting("Sell \u200b\u200bfor an amount from (kk)", 10.0F, 1.0F, 50.0F, 1.0F, true);
   private ScheduledExecutorService scheduledExecutorServiceField4 = Executors.newSingleThreadScheduledExecutor();
   private NetworkComponent173 networkComponent173Field5;

   @Override
   public void onEnable() {
   }

   @EventTarget
   public void onEventComponent074_1928(EventComponent074 eventComponent074) {
   }

   private void performAction_1929() {
   }

   public StringSetting getStringSettingField1_1930() {
      return this.stringSettingField1;
   }

   public FloatSetting getFloatSettingField2_1926() {
      return this.floatSettingField2;
   }

   public FloatSetting getFloatSettingField3_1927() {
      return this.floatSettingField3;
   }

   public FunDeliver() {
      this.addSettings(this.stringSettingField1, this.floatSettingField2, this.floatSettingField3);
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.scheduledExecutorServiceField4.shutdownNow();
   }
}
