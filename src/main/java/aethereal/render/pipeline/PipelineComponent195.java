package aethereal.render.pipeline;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class PipelineComponent195 {
   public static final Pattern PATTERN = Pattern.compile("\\$\\{(rgba|rgb)\\((\\d{1,3}),(\\d{1,3}),(\\d{1,3})(?:,(\\d{1,3}))?\\)}|\\$\\{reset}", 2);
   private static final HashMap<String, String> FORMATS = new HashMap<>();

   public static String computeValue_2704(String string) {
      if (string == null || string.isEmpty()) {
         return string;
      }

      if (string.indexOf("${") < 0) {
         return string;
      }

      String string1 = string;

      for (Entry entry : FORMATS.entrySet()) {
         string1 = string1.replace((CharSequence)entry.getKey(), (CharSequence)entry.getValue());
      }

      return string1;
   }

   public static String computeValue_2710(int i, int j, int k) {
      return "${rgb(" + i + "," + j + "," + k + ")}";
   }

   public static String computeValue_2711(int i, int j, int k, int l) {
      return "${rgba(" + i + "," + j + "," + k + "," + l + ")}";
   }

   public static String computeValue_2708(int i) {
      return computeValue_2711(computeValue_2707(i), computeValue_2717(i), computeValue_2709(i), computeValue_2719(i));
   }

   public static Text computeValue_2713(String string) {
      MutableText mutableText = Text.empty();

      for (int i = 0; i < string.length(); i++) {
         int j = computeValue_2720(i);
         mutableText.append(Text.literal(String.valueOf(string.charAt(i))).styled(style -> style.withColor(j)));
      }

      return mutableText;
   }

   public static int computeValue_2718(int i, int j, float f) {
      return computeValue_2702(i, j, f);
   }

   public static String computeValue_2698() {
      return "${reset}";
   }

   public static String computeValue_2703(String string) {
      if (string == null || string.isEmpty()) {
         return string;
      } else {
         return string.indexOf("${") < 0 ? string : PATTERN.matcher(string).replaceAll("");
      }
   }

   public static String computeValue_2705() {
      return "rgb";
   }

   public static String computeValue_2706() {
      return "rgba";
   }

   public static String computeValue_2715(String string, int i, int j, int k) {
      return PATTERN.matcher(string).replaceAll(Matcher.quoteReplacement(computeValue_2710(i, j, k)));
   }

   public static String computeValue_2716(String string, int i, int j, int k, int l) {
      return PATTERN.matcher(string).replaceAll(Matcher.quoteReplacement(computeValue_2711(i, j, k, l)));
   }

   public static PipelineComponent195.NestedType375 computeValue_2714(String string, int i, int j) {
      if (string == null || i < 0 || i >= string.length() - 1) {
         return null;
      }

      if (string.charAt(i) == '$' && string.charAt(i + 1) == '{') {
         Matcher matcher = PATTERN.matcher(string);
         if (!matcher.find(i) || matcher.start() != i) {
            return null;
         }

         if (matcher.group().equalsIgnoreCase(computeValue_2698())) {
            return new PipelineComponent195.NestedType375(j, matcher.end() - i);
         }

         String string1 = matcher.group(1).toLowerCase();
         int k = computeValue_2700(Integer.parseInt(matcher.group(2)));
         int l = computeValue_2700(Integer.parseInt(matcher.group(3)));
         int m = computeValue_2700(Integer.parseInt(matcher.group(4)));
         int n = string1.equals(computeValue_2706()) && matcher.group(5) != null ? computeValue_2700(Integer.parseInt(matcher.group(5))) : computeValue_2719(j);
         return new PipelineComponent195.NestedType375(computeValue_2699(k, l, m, n), matcher.end() - i);
      } else {
         return null;
      }
   }

   public static String computeValue_2701(String string) {
      return string != null && !string.isEmpty() ? computeValue_2703(computeValue_2704(string)) : string;
   }

   private static int computeValue_2700(int i) {
      return Math.max(0, Math.min(255, i));
   }

   public static int computeValue_2707(int i) {
      return i >> 16 & 0xFF;
   }

   public static int computeValue_2717(int i) {
      return i >> 8 & 0xFF;
   }

   public static int computeValue_2709(int i) {
      return i & 0xFF;
   }

   public static int computeValue_2719(int i) {
      return i >> 24 & 0xFF;
   }

   public static int computeValue_2699(int i, int j, int k, int l) {
      return l << 24 | i << 16 | j << 8 | k;
   }

   public static int computeValue_2720(int i) {
      return -1;
   }

   public static int computeValue_2702(int i, int j, float f) {
      return i;
   }

   static {
      FORMATS.put("${black}", Formatting.BLACK.toString());
      FORMATS.put("${dark_blue}", Formatting.DARK_BLUE.toString());
      FORMATS.put("${dark_green}", Formatting.DARK_GREEN.toString());
      FORMATS.put("${dark_aqua}", Formatting.DARK_AQUA.toString());
      FORMATS.put("${dark_red}", Formatting.DARK_RED.toString());
      FORMATS.put("${dark_purple}", Formatting.DARK_PURPLE.toString());
      FORMATS.put("${orange}", Formatting.GOLD.toString());
      FORMATS.put("${gray}", Formatting.GRAY.toString());
      FORMATS.put("${dark_gray}", Formatting.DARK_GRAY.toString());
      FORMATS.put("${blue}", Formatting.BLUE.toString());
      FORMATS.put("${green}", Formatting.GREEN.toString());
      FORMATS.put("${aqua}", Formatting.AQUA.toString());
      FORMATS.put("${red}", Formatting.RED.toString());
      FORMATS.put("${purple}", Formatting.LIGHT_PURPLE.toString());
      FORMATS.put("${yellow}", Formatting.YELLOW.toString());
      FORMATS.put("${white}", Formatting.WHITE.toString());
      FORMATS.put("${bold}", Formatting.BOLD.toString());
      FORMATS.put("${reset}", Formatting.RESET.toString());
   }

   public record NestedType375(int color, int length) {
   }
}
