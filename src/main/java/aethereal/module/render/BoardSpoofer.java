package aethereal.module.render;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.EventComponent101;
import aethereal.setting.BooleanSetting;
import aethereal.setting.ChoiceSetting;
import aethereal.setting.MultiSelectSetting;
import aethereal.setting.StringSetting;
import java.util.Locale;
import java.util.Optional;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

@ModuleRegister(name = "Board Spoofer", description = "Replaces the values \u200b\u200bof donation, coins and tokens in Scoreboard", category = Category.Render)
public class BoardSpoofer extends Module {
   private final MultiSelectSetting multiSelectSettingField1 = new MultiSelectSetting(
      "Customization Items", new BooleanSetting("Rank", true), new BooleanSetting("Coins", true), new BooleanSetting("Tokens", true)
   );
   private final ChoiceSetting choiceSettingField2 = new ChoiceSetting(
         "Select a privilege", "Player", "Player", "Baron", "Guardian", "Hero", "Aspid", "Squid", "Head", "Elite", "Titan", "Prince", "Duke"
      )
      .withVisibility(() -> this.multiSelectSettingField1.computeValue_2852("Rank").getCurrentValue());
   private final StringSetting stringSettingField3 = new StringSetting("Number of Coins", "", true)
      .withVisibility(() -> this.multiSelectSettingField1.computeValue_2852("Coins").getCurrentValue());
   private final StringSetting stringSettingField4 = new StringSetting("Number of Tokens", "", true)
      .withVisibility(() -> this.multiSelectSettingField1.computeValue_2852("Tokens").getCurrentValue());

   public BoardSpoofer() {
      this.addSettings(this.multiSelectSettingField1, this.choiceSettingField2, this.stringSettingField3, this.stringSettingField4);
   }

   @EventTarget
   public void onEventComponent101_2230(EventComponent101 eventComponent101) {
      Text text = eventComponent101.getTitle_1099();
      if (this.multiSelectSettingField1.computeValue_2852("Rank").getCurrentValue()) {
         text = this.computeValue_2233(text, "Rank:", this.choiceSettingField2.getCurrentValue());
      }

      if (this.multiSelectSettingField1.computeValue_2852("Coins").getCurrentValue()) {
         Text text1 = text;
         Locale locale = Locale.US;
         Object[] objects = new Object[]{
            Long.parseLong(this.stringSettingField3.getCurrentValue().isEmpty() ? "0" : this.stringSettingField3.getCurrentValue())
         };
         text = this.computeValue_2233(text1, "Coins:", String.format(locale, "%,d", objects));
      }

      if (this.multiSelectSettingField1.computeValue_2852("Tokens").getCurrentValue()) {
         text = this.computeValue_2233(
            text, "Tokens: ", this.stringSettingField4.getCurrentValue().isEmpty() ? "0" : this.stringSettingField4.getCurrentValue()
         );
      }

      eventComponent101.setTitle_1100(text);
   }

   private Text computeValue_2233(Text text, String string, String string1) {
      MutableText mutableText = Text.empty();
      StringBuilder stringBuilder = new StringBuilder();
      text.visit((style, string4) -> {
         int i = stringBuilder.length();
         stringBuilder.append(string4);
         int j = stringBuilder.indexOf(string);
         if (j != -1 && j + string.length() > i) {
            mutableText.append(Text.literal(string4.substring(0, j + string.length() - i)).setStyle(style));
            mutableText.append(Text.literal(string1).setStyle("Rank:".equals(string) ? this.computeValue_2231(string1) : style));
            return Optional.of(true);
         } else {
            mutableText.append(Text.literal(string4).setStyle(style));
            return Optional.empty();
         }
      }, Style.EMPTY);
      return (Text)(mutableText.getSiblings().isEmpty() ? text : mutableText);
   }

   private Style computeValue_2231(String string) {
      switch (string) {
         case "Guardian":
            return Style.EMPTY.withFormatting(Formatting.YELLOW);
         case "Baron":
         case "Squid":
            return Style.EMPTY.withFormatting(Formatting.AQUA);
         case "Hero":
            return Style.EMPTY.withFormatting(Formatting.GREEN);
         case "Aspid":
            return Style.EMPTY.withFormatting(Formatting.DARK_AQUA);
         case "Head":
         case "Titan":
            return Style.EMPTY.withFormatting(Formatting.GOLD);
         case "Elite":
            return Style.EMPTY.withFormatting(Formatting.DARK_PURPLE);
         case "Prince":
            return Style.EMPTY.withFormatting(Formatting.RED);
         case "Duke":
            return Style.EMPTY.withFormatting(Formatting.DARK_RED);
         default:
            return Style.EMPTY.withFormatting(Formatting.WHITE);
      }
   }
}
