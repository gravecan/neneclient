package aethereal.module.client;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent073;
import aethereal.event.EventComponent101;
import aethereal.setting.StringSetting;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.text.Text;

@ModuleRegister(name = "Name Protect", description = "Replaces your name in rendered chat, HUD and scoreboard text", category = Category.Client)
public class NameProtect extends Module {
   private final StringSetting protectedName = new StringSetting("Protected name", "Protected");
   private String sourceName = "";
   private Pattern sourcePattern;

   public NameProtect() {
      this.addSettings(this.protectedName);
   }

   public StringSetting getProtectedName_1466() {
      return this.protectedName;
   }

   public String computeValue_1467(String string) {
      if (string != null && !string.isEmpty()) {
         String string1 = minecraft != null && minecraft.getSession() != null ? minecraft.getSession().getUsername() : "";
         if (string1 != null && !string1.isBlank()) {
            if (!string1.equals(this.sourceName)) {
               this.sourceName = string1;
               this.sourcePattern = Pattern.compile(Pattern.quote(string1), 2);
            }

            String string2 = this.protectedName.getCurrentValue() != null && !this.protectedName.getCurrentValue().isBlank()
               ? this.protectedName.getCurrentValue()
               : "Protected";
            return this.sourcePattern == null ? string : this.sourcePattern.matcher(string).replaceAll(Matcher.quoteReplacement(string2));
         } else {
            return string;
         }
      } else {
         return string;
      }
   }

   @EventTarget
   public void onEventComponent073_1464(EventComponent073 eventComponent073) {
      eventComponent073.setText_0996(this.computeValue_1467(eventComponent073.getText_0995()));
   }

   @EventTarget
   public void onEventComponent101_1465(EventComponent101 eventComponent101) {
      Text text = eventComponent101.getTitle_1099();
      if (text != null) {
         String string = this.computeValue_1467(text.getString());
         if (!string.equals(text.getString())) {
            eventComponent101.setTitle_1100(Text.literal(string).setStyle(text.getStyle()));
         }
      }
   }
}
