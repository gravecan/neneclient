package aethereal.module.render;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent088;
import aethereal.setting.ActionSetting;
import aethereal.setting.FloatSetting;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Hand;

@ModuleRegister(name = "View Model", description = "Changes the position and size of objects in the hand", category = Category.Render)
public class ViewModel extends Module {
   private final FloatSetting floatSettingField1 = new FloatSetting("Main hand X", 0.0F, -2.0F, 2.0F, 0.1F);
   private final FloatSetting floatSettingField2 = new FloatSetting("Main hand Y", 0.0F, -2.0F, 2.0F, 0.1F);
   private final FloatSetting floatSettingField3 = new FloatSetting("Main hand Z", 0.0F, -2.0F, 2.0F, 0.1F);
   private final FloatSetting floatSettingField4 = new FloatSetting("Second hand X", 0.0F, -2.0F, 2.0F, 0.1F);
   private final FloatSetting floatSettingField5 = new FloatSetting("Second hand Y", 0.0F, -2.0F, 2.0F, 0.1F);
   private final FloatSetting floatSettingField6 = new FloatSetting("Second hand Z", 0.0F, -2.0F, 2.0F, 0.1F);
   private final ActionSetting actionSettingField7 = new ActionSetting("Reset positions", () -> this.getSettings().forEach(setting -> {
      if (setting instanceof FloatSetting floatSetting) {
         floatSetting.setValue(floatSetting.getDefaultValue());
      }
   }));

   public ViewModel() {
      this.addSettings(
         this.floatSettingField1,
         this.floatSettingField2,
         this.floatSettingField3,
         this.floatSettingField4,
         this.floatSettingField5,
         this.floatSettingField6,
         this.actionSettingField7
      );
   }

   @EventTarget
   public void onEventComponent088_2315(EventComponent088 eventComponent088) {
      MatrixStack matrixStack = eventComponent088.getMatrices_1048();
      if (eventComponent088.getHand_1046().equals(Hand.MAIN_HAND)) {
         matrixStack.translate(this.floatSettingField1.getValue(), this.floatSettingField2.getValue(), this.floatSettingField3.getValue());
      } else {
         matrixStack.translate(this.floatSettingField4.getValue(), this.floatSettingField5.getValue(), this.floatSettingField6.getValue());
      }
   }
}
