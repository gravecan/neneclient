package aethereal.config;

import aethereal.lib.json.JsonComponent136;
import aethereal.setting.BooleanSetting;
import aethereal.setting.ChoiceSetting;
import aethereal.setting.ColorSetting;
import aethereal.setting.FloatSetting;
import aethereal.setting.KeybindSetting;
import aethereal.setting.MultiSelectSetting;
import aethereal.setting.Setting;
import aethereal.setting.StringSetting;
import org.json.JSONObject;

public final class ConfigComponent018 {
   private ConfigComponent018() {
   }

   public static Object computeValue_0362(Setting<?> setting) {
      if (!(setting instanceof MultiSelectSetting multiSelectSetting)) {
         return setting.getCurrentValue();
      } else {
         JsonComponent136 jsonComponent136 = new JsonComponent136();

         for (BooleanSetting booleanSetting : multiSelectSetting.getCurrentValue()) {
            jsonComponent136.computeValue_1273(booleanSetting.getName(), booleanSetting.getCurrentValue());
         }

         return jsonComponent136;
      }
   }

   public static void performAction_0363(Setting<?> setting, Object object) {
      if (setting instanceof BooleanSetting booleanSetting && object instanceof Boolean boolean_) {
         booleanSetting.setValue(boolean_);
      } else if (setting instanceof ChoiceSetting choiceSetting && object instanceof String string2) {
         if (choiceSetting.getListField1_2849().stream().anyMatch(string4 -> string4.equalsIgnoreCase(string2))) {
            choiceSetting.setValue(choiceSetting.getListField1_2849().stream().filter(string4 -> string4.equalsIgnoreCase(string2)).findFirst().orElse(string2));
         }
      } else if (setting instanceof StringSetting stringSetting && object instanceof String string1) {
         if (string1.length() <= 4096) {
            stringSetting.setValue(string1);
         }
      } else if (setting instanceof FloatSetting floatSetting && object instanceof Number number2) {
         float f = number2.floatValue();
         if (Float.isFinite(f)) {
            float g = Math.min(floatSetting.minimum, floatSetting.maximum);
            float h = Math.max(floatSetting.minimum, floatSetting.maximum);
            floatSetting.setValue(Math.max(g, Math.min(h, f)));
         }
      } else if (setting instanceof KeybindSetting keybindSetting && object instanceof Number number1) {
         long l = number1.longValue();
         if (l >= -1L && l <= 348L) {
            keybindSetting.setValue((int)l);
         }
      } else if (setting instanceof ColorSetting colorSetting && object instanceof Number number) {
         colorSetting.setValue(number.intValue());
      } else {
         if (setting instanceof MultiSelectSetting multiSelectSetting) {
            if (object instanceof JsonComponent136 jsonComponent136) {
               object = jsonComponent136.getDelegate_1262();
            }

            if (object instanceof JSONObject jSONObject) {
               for (String string : jSONObject.keySet()) {
                  BooleanSetting booleanSetting1 = multiSelectSetting.computeValue_2852(string);
                  if (booleanSetting1 != null && jSONObject.opt(string) instanceof Boolean boolean_x) {
                     booleanSetting1.setValue(boolean_x);
                  }
               }
            }
         }
      }
   }
}
