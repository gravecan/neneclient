package aethereal.ambience;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.AmbienceEvent;
import aethereal.render.RenderComponent183;
import aethereal.setting.BooleanSetting;
import aethereal.setting.ChoiceSetting;
import aethereal.setting.ColorSetting;
import aethereal.setting.FloatSetting;
import java.time.LocalTime;
import net.minecraft.world.biome.Biome.Precipitation;

@ModuleRegister(name = "Ambience", description = "Sets the atmosphere and environment of the game world", category = Category.Render)
public class Ambience extends Module {
   private final ChoiceSetting choiceSettingField1 = new ChoiceSetting(
      "Time in the world", "No changes", "No changes", "Dawn", "Day", "Noon", "Sunset", "Night", "Midnight", "Your Time"
   );
   public final ChoiceSetting choiceSettingField2 = new ChoiceSetting("World weather", "No changes", "No changes", "Sunny", "Rain", "Snow", "Thunderstorm");
   private final BooleanSetting booleanSettingField3 = new BooleanSetting("Custom fog", false);
   private final ColorSetting colorSettingField4;
   private final FloatSetting floatSettingField5;
   private final FloatSetting floatSettingField6;

   public Ambience() {
      ColorSetting colorSetting = new ColorSetting("Fog color", RenderComponent183.computeValue_2551(255, 255, 255));
      BooleanSetting booleanSetting = this.booleanSettingField3;
      this.colorSettingField4 = colorSetting.withVisibility(booleanSetting::getValue);
      FloatSetting floatSetting = new FloatSetting("Fog Range", -8.0F, -8.0F, 25.0F, 1.0F);
      BooleanSetting booleanSetting1 = this.booleanSettingField3;
      this.floatSettingField5 = floatSetting.withVisibility(booleanSetting1::getValue);
      FloatSetting floatSetting1 = new FloatSetting("Fog density", 100.0F, 0.0F, 100.0F, 1.0F);
      BooleanSetting booleanSetting2 = this.booleanSettingField3;
      this.floatSettingField6 = floatSetting1.withVisibility(booleanSetting2::getValue);
      this.addSettings(
         this.choiceSettingField1,
         this.choiceSettingField2,
         this.booleanSettingField3,
         this.colorSettingField4,
         this.floatSettingField5,
         this.floatSettingField6
      );
   }

   @EventTarget
   public void onNestedType312_0022(AmbienceEvent.NestedType312 nestedType312) {
      nestedType312.setTime_0963(switch ((String)this.choiceSettingField1.getCurrentValue()) {
         case "Dawn" -> 23041L;
         case "Day" -> 1000L;
         case "Noon" -> 6000L;
         case "Sunset" -> 12610L;
         case "Night" -> 13000L;
         case "Midnight" -> 18000L;
         case "Your Time", "Real time" -> {
            LocalTime localTime = LocalTime.now();
            double d = localTime.getHour() + localTime.getMinute() / 60.0 + localTime.getSecond() / 3600.0;
            double e = (d - 6.0 + 24.0) % 24.0;
            yield (long)(e * 1000.0);
         }
         default -> nestedType312.getTime_0962();
      });
   }

   @EventTarget
   public void onNestedType311_0021(AmbienceEvent.NestedType311 nestedType311) {
      if (this.booleanSettingField3.getCurrentValue()) {
         float[] f = RenderComponent183.computeValue_2554(this.colorSettingField4.getCurrentValue());
         nestedType311.performAction_0961(f[0], f[1], f[2], f[3]);
      }
   }

   @EventTarget
   public void onNestedType313_0023(AmbienceEvent.NestedType313 nestedType313) {
      switch (nestedType313.getType_0965()) {
         case RAIN_GRADIENT:
            nestedType313.setValue_0967(switch ((String)this.choiceSettingField2.getCurrentValue()) {
               case "Sunny" -> 0.0F;
               case "Rain", "Thunderstorm" -> 1.0F;
               case "Snow" -> 0.9F;
               default -> nestedType313.getValue_0964();
            });
            break;
         case THUNDER_GRADIENT:
            nestedType313.setValue_0967(switch ((String)this.choiceSettingField2.getCurrentValue()) {
               case "Sunny", "Rain", "Snow" -> 0.0F;
               case "Thunderstorm" -> 1.0F;
               default -> nestedType313.getValue_0964();
            });
            break;
         case PRECIPITATION_PARTICLES:
            if (this.choiceSettingField2.checkCondition_2850("Snow")) {
               nestedType313.setValue_0967(0.0F);
            }
            break;
         case PRECIPITATION:
            if (this.choiceSettingField2.checkCondition_2850("Snow")) {
               nestedType313.setValue2_0968(Precipitation.SNOW);
            }
      }
   }
}
