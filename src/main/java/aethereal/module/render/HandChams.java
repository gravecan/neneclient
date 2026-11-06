package aethereal.module.render;

import aethereal.config.ThemeInfo;
import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.HandEvent;
import aethereal.render.RenderComponent183;
import aethereal.setting.FloatSetting;
import aethereal.ui.shader.ShaderComponent233;
import net.minecraft.client.option.Perspective;

@ModuleRegister(name = "Hand Chams", description = "Adds a clean accent shader to the first-person hand", category = Category.Visuals)
public class HandChams extends Module {
   private final FloatSetting floatSettingField1 = new FloatSetting("Opacity", 0.6F, 0.0F, 1.0F, 0.05F);

   public HandChams() {
      this.addSettings(this.floatSettingField1);
   }

   @EventTarget
   public void onHandEvent_2260(HandEvent handEvent) {
      ShaderComponent233 shaderComponent233 = NeneClient.getInstance().getProcessors().getRenderComponent184Field3_0677().getShaderComponent233Field6_2569();
      if (minecraft.options.getPerspective() == Perspective.FIRST_PERSON) {
         if (handEvent.checkCondition_1009()) {
            shaderComponent233.performAction_3306();
         }

         if (handEvent.checkCondition_1010()) {
            float[] f = RenderComponent183.computeValue_2554(
               NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547()
            );
            f[3] = this.floatSettingField1.getCurrentValue();
            shaderComponent233.performAction_3305(f);
         }
      }
   }
}
