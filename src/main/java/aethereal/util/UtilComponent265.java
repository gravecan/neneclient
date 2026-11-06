package aethereal.util;

import aethereal.config.ThemeInfo;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.render.RenderComponent183;
import aethereal.ui.shader.ShaderComponent231;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.HoverEvent.ShowText;

public class UtilComponent265 implements MinecraftAccess {
   private UtilComponent265() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   public static void performAction_3505(Object object) {
      performAction_3508("[NeneClient 1.21.4]", object);
   }

   public static void performAction_3508(String string, Object object) {
      if (minecraft.player != null) {
         MutableText mutableText;
         if (string != null && !string.isEmpty()) {
            mutableText = computeValue_3507(string).copy().append(Text.literal("")).append(computeValue_3504(object));
         } else {
            mutableText = computeValue_3504(object);
         }

         minecraft.player.sendMessage(mutableText, false);
      }
   }

   public static MutableText computeValue_3504(Object object) {
      if (object instanceof MutableText mutableText) {
         return mutableText;
      } else {
         return object instanceof Text text ? text.copy() : Text.literal(("&7" + object).replace('&', '§'));
      }
   }

   public static MutableText computeValue_3506(Object object, Text text) {
      String string;
      if (object instanceof Text text1) {
         string = text1.getString();
      } else {
         string = String.valueOf(object);
      }

      String string1 = string;
      return Text.literal(string1.replace('&', '§')).setStyle(Style.EMPTY.withHoverEvent(new ShowText(text.copy())));
   }

   private static MutableText computeValue_3507(String string) {
      int i = NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547();
      return ShaderComponent231.computeValue_3296(string + " » ", i, RenderComponent183.computeValue_2552(i, 0.5F), 1, 5.0F);
   }
}
