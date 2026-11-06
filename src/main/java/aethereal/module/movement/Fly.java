package aethereal.module.movement;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent074;
import aethereal.setting.FloatSetting;
import net.minecraft.client.input.Input;
import net.minecraft.util.math.Vec2f;
import platform.inject.accessors.InputAccessor;

@ModuleRegister(name = "Fly", description = "Allows you to fly freely around the world", category = Category.Movement)
public class Fly extends Module {
   private final FloatSetting floatSettingField1 = new FloatSetting("X and Z speed", 1.0F, 0.1F, 5.0F, 0.1F);
   private final FloatSetting floatSettingField2 = new FloatSetting("Speed \u200b\u200bY", 1.0F, 0.1F, 5.0F, 0.1F);

   public Fly() {
      this.addSettings(this.floatSettingField1, this.floatSettingField2);
   }

   @EventTarget
   public void onEventComponent074_2036(EventComponent074 eventComponent074) {
      this.performAction_2037();
   }

   private void performAction_2037() {
      float g = minecraft.player.getYaw();
      double d;
      if (minecraft.options.sneakKey.isPressed()) {
         d = -this.floatSettingField2.getCurrentValue();
      } else {
         d = minecraft.options.jumpKey.isPressed() ? this.floatSettingField2.getCurrentValue().floatValue() : 0.0;
      }

      double e = d;
      Input input = minecraft.player.input;
      Vec2f vec2f = input.getMovementInput();
      float h = vec2f.y;
      float i = vec2f.x;
      if (h == 0.0F && i == 0.0F) {
         minecraft.player.setVelocity(0.0, e, 0.0);
      } else {
         if (h != 0.0F) {
            float f;
            if (i != 0.0F) {
               f = (h > 0.0F ? -45.0F : 45.0F) * Math.signum(i);
            } else {
               f = 0.0F;
            }

            g += f;
            i = 0.0F;
            h = Math.signum(h);
            ((InputAccessor)input).setMovementVector(new Vec2f(i, h));
         }

         double j = Math.toRadians(g + 90.0F);
         double k = this.floatSettingField1.getCurrentValue().floatValue();
         double l = h * k * Math.cos(j) + i * k * Math.sin(j);
         double m = h * k * Math.sin(j) - i * k * Math.cos(j);
         minecraft.player.setVelocity(l, e, m);
      }
   }
}
