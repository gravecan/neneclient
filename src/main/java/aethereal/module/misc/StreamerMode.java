package aethereal.module.misc;

import aethereal.core.Category;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.friend.Friend;
import aethereal.setting.BooleanSetting;
import java.util.regex.Pattern;

@ModuleRegister(name = "Streamer Mode", description = "Hides personal data when streaming and recording", category = Category.Client)
public class StreamerMode extends Module {
   private final BooleanSetting booleanSettingField1 = new BooleanSetting("Hide player skins", true);
   private final BooleanSetting booleanSettingField2 = new BooleanSetting("Hide friends' names", true);
   private final BooleanSetting booleanSettingField3 = new BooleanSetting("Hide anarchy number", true);

   public BooleanSetting getBooleanSettingField1_2003() {
      return this.booleanSettingField1;
   }

   public BooleanSetting getBooleanSettingField2_2001() {
      return this.booleanSettingField2;
   }

   public BooleanSetting getBooleanSettingField3_2002() {
      return this.booleanSettingField3;
   }

   public StreamerMode() {
      this.addSettings(this.booleanSettingField1, this.booleanSettingField2, this.booleanSettingField3);
   }

   public String computeValue_2004(String string) {
      String string1 = minecraft.getSession() != null && minecraft.getSession().getUsername() != null ? minecraft.getSession().getUsername() : "";
      String string2 = string.replaceAll("(?i)" + Pattern.quote(string1), "Protected");
      if (this.booleanSettingField2.getCurrentValue()
         && NeneClient.getInstance() != null
         && NeneClient.getInstance().getProcessors() != null
         && NeneClient.getInstance().getProcessors().getFriends() != null) {
         for (Friend friend : NeneClient.getInstance().getProcessors().getFriends().computeValue_1108()) {
            string2 = Pattern.compile(Pattern.quote(friend.getName_1103()), 2).matcher(string2).replaceAll("Protected");
         }
      }

      if (this.booleanSettingField3.getCurrentValue()) {
         string2 = string2.replaceAll("Anarchy-\\d+", "Anarchy-???");
      }

      return string2;
   }
}
