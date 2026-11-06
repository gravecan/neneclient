package aethereal.module.render;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent087;
import aethereal.setting.BooleanSetting;
import aethereal.setting.ChoiceSetting;
import aethereal.setting.FloatSetting;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RotationAxis;

@ModuleRegister(name = "Swing Animation", description = "Sets the hand wave animation", category = Category.Visuals)
public class SwingAnimation extends Module {
   private final BooleanSetting booleanSettingField1 = new BooleanSetting("Consider enabled Aura", true);
   private final ChoiceSetting choiceSettingField2 = new ChoiceSetting("Animation mode", "Mod 1", "Mod 1", "Mod 2", "Mod 3", "Mod 4", "Mod 5");
   private final FloatSetting floatSettingField3 = new FloatSetting("Rotation angle", 75.0F, 0.0F, 360.0F, 1.0F)
      .withVisibility(() -> this.choiceSettingField2.checkCondition_2850("Mod 1"));
   private final FloatSetting floatSettingField4 = new FloatSetting("Tip tilt", -20.0F, -90.0F, 90.0F, 1.0F)
      .withVisibility(() -> !this.choiceSettingField2.checkCondition_2850("Mod 5"));
   private final FloatSetting floatSettingField5 = new FloatSetting("Swing intensity", 5.0F, 1.0F, 10.0F, 1.0F);

   public BooleanSetting getBooleanSettingField1_2311() {
      return this.booleanSettingField1;
   }

   public ChoiceSetting getChoiceSettingField2_2312() {
      return this.choiceSettingField2;
   }

   public FloatSetting getFloatSettingField3_2308() {
      return this.floatSettingField3;
   }

   public FloatSetting getFloatSettingField4_2313() {
      return this.floatSettingField4;
   }

   public FloatSetting getFloatSettingField5_2307() {
      return this.floatSettingField5;
   }

   public SwingAnimation() {
      this.addSettings(this.booleanSettingField1, this.choiceSettingField2, this.floatSettingField3, this.floatSettingField4, this.floatSettingField5);
   }

   @EventTarget
   public void onEventComponent087_2310(EventComponent087 eventComponent087) {
      if ((
            !this.booleanSettingField1.getCurrentValue()
               || NeneClient.getInstance().getProcessors().getModules().getAuraField3_0402().getLivingEntityField2_1528() != null
         )
         && eventComponent087.getHand_1044() == Hand.MAIN_HAND) {
         MatrixStack matrixStack = eventComponent087.getMatrices_1042();
         float f = (float)Math.sin(eventComponent087.getSwingProgress_1043() * 3.1415936112270124);
         float g = this.floatSettingField5.getCurrentValue() * 10.0F;
         int i = eventComponent087.getArmX_1045();
         matrixStack.translate(
            i * (this.choiceSettingField2.checkCondition_2850("Mod 5") ? 0.5F : 0.72F),
            -0.5F,
            this.choiceSettingField2.checkCondition_2850("Mod 5") ? -0.72F : -1.0F
         );
         if (!this.choiceSettingField2.checkCondition_2850("Mod 5")) {
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-this.floatSettingField4.getCurrentValue()));
         }

         switch ((String)this.choiceSettingField2.getCurrentValue()) {
            case "Mod 1":
               matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i * 90));
               matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(i * -70));
               matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-this.floatSettingField3.getCurrentValue() - g * f));
               break;
            case "Mod 2":
               matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i * 90));
               matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(i * -65));
               matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-65.0F + g * f));
               break;
            case "Mod 3":
               matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i * -90));
               matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(i * 60));
               matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(30.0F));
               matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(i * g * f));
               break;
            case "Mod 4":
               matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i * 90));
               matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(i * -75));
               matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-45.0F - g * f));
               matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i * g * f * 0.5F));
               break;
            case "Mod 5":
               float h = g / 80.0F;
               float j = f * f;
               float k = (float)Math.sin(eventComponent087.getSwingProgress_1043() * eventComponent087.getSwingProgress_1043() * 3.1415936112270124);
               matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i * (45.0F + k * -20.0F * h)));
               matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(i * j * -22.0F * h));
               matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(j * -85.0F * h));
               matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i * -45.0F));
         }

         eventComponent087.performAction_0597(true);
      }
   }
}
