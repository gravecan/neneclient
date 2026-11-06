package aethereal.module.render;

import aethereal.config.ThemeInfo;
import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.DrawEvent;
import aethereal.render.RenderComponent183;
import aethereal.setting.ChoiceSetting;
import aethereal.setting.ColorSetting;
import aethereal.util.UtilComponent252;
import aethereal.util.UtilComponent279;
import aethereal.util.UtilComponent284;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import org.joml.Matrix4f;

@ModuleRegister(name = "Entity Box", description = "Displays boxes around entities", category = Category.Render)
public class EntityBox extends Module {
   private final ChoiceSetting choiceSettingField1 = new ChoiceSetting("Rendering type", "Square", "Square", "Angles", "Fill", "Disabled");
   private final ChoiceSetting choiceSettingField2 = new ChoiceSetting("Color Source", "Client", "Client", "Static");
   private final ChoiceSetting choiceSettingField3 = new ChoiceSetting("Health Bar", "Disabled", "Disabled", "Standard")
      .withVisibility(() -> this.choiceSettingField1.checkCondition_2850("Square") || this.choiceSettingField1.checkCondition_2850("Angles"));
   private final ColorSetting colorSettingField4 = new ColorSetting("Rendering color", RenderComponent183.computeValue_2558(255, 255, 255, 255))
      .withVisibility(() -> this.choiceSettingField2.checkCondition_2850("Static"));

   public EntityBox() {
      this.addSettings(this.choiceSettingField1, this.choiceSettingField2, this.choiceSettingField3, this.colorSettingField4);
   }

   @EventTarget
   public void onDrawEvent_2245(DrawEvent drawEvent) {
      if (this.choiceSettingField1.checkCondition_2850("Fill")) {
         if (drawEvent.checkCondition_1002()) {
            for (Entity entity : minecraft.world.getEntities()) {
               if (this.checkCondition_2243(entity)) {
                  drawEvent.getRenderComponent185Field2_0999()
                     .performAction_2595(
                        drawEvent.getStack2_1004(),
                        entity.getBoundingBox()
                           .offset(UtilComponent279.computeValue_3585(entity, drawEvent.getTickNeneClient_0997()).subtract(entity.getEntityPos())),
                        this.choiceSettingField2.checkCondition_2850("Static")
                           ? this.colorSettingField4.getCurrentValue()
                           : NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(),
                        0.75F,
                        true
                     );
               }
            }
         }
      } else {
         if (drawEvent.checkCondition_1001()
            && (this.choiceSettingField1.checkCondition_2850("Square") || this.choiceSettingField1.checkCondition_2850("Angles"))) {
            Matrix4f matrix4f = new Matrix4f();

            for (LivingEntity livingEntity : minecraft.world
               .getEntitiesByClass(LivingEntity.class, minecraft.player.getBoundingBox().expand(256.0), livingEntity1 -> true)) {
               if (livingEntity != minecraft.player && this.checkCondition_2243(livingEntity)) {
                  Box box = livingEntity.getBoundingBox()
                     .offset(UtilComponent279.computeValue_3585(livingEntity, drawEvent.getTickNeneClient_0997()).subtract(livingEntity.getEntityPos()));
                  float[] f = UtilComponent284.computeValue_3616(box);
                  if (f != null) {
                     boolean bl = !this.choiceSettingField3.checkCondition_2850("Disabled");
                     float g = bl
                        ? Math.min(
                           Math.max(0.0F, UtilComponent252.NestedType400.computeValue_3409(livingEntity)) / Math.max(1.0F, livingEntity.getMaxHealth()), 1.0F
                        )
                        : 0.0F;
                     int i = RenderComponent183.computeValue_2553(
                        RenderComponent183.computeValue_2558(255, 0, 0, 255), RenderComponent183.computeValue_2558(0, 255, 0, 255), g
                     );
                     int j = this.choiceSettingField2.checkCondition_2850("Static")
                        ? this.colorSettingField4.getCurrentValue()
                        : RenderComponent183.computeValue_2556(
                           NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(), 255
                        );
                     drawEvent.getRenderComponent185Field2_0999()
                        .performAction_2597(matrix4f, f[0], f[1], f[2], f[3], j, this.choiceSettingField1.checkCondition_2850("Angles"), bl, g, i);
                  }
               }
            }
         }
      }
   }

   private boolean checkCondition_2243(Entity entity) {
      return !(entity instanceof PlayerEntity) && !(entity instanceof ItemEntity)
         ? false
         : entity != minecraft.player || !minecraft.options.getPerspective().isFirstPerson();
   }
}
