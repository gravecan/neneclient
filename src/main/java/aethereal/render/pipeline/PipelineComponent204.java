package aethereal.render.pipeline;

import com.mojang.blaze3d.shaders.ShaderType;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

public final class PipelineComponent204 {
   private static final byte[] KEY = "Nightix//shader-veil//2026".getBytes(StandardCharsets.UTF_8);
   private static final String INCLUDE_DIR = "shaders/include/";

   private PipelineComponent204() {
   }

   public static String computeValue_2814(Identifier identifier, ShaderType shaderType) {
      if (identifier == null) {
         return null;
      }

      String string = identifier.getNamespace();
      if (!"neneclient".equals(string) && !"client".equals(string)) {
         return null;
      }

      String string1 = shaderType == ShaderType.VERTEX ? "vsh" : "fsh";
      String string2 = identifier.getPath() + "|" + string1;
      String string3 = null;
      String string4 = PipelineComponent203.SOURCES.get(string2);
      if (string4 != null) {
         string3 = computeValue_2810(string4);
      } else {
         string3 = computeValue_2813(Identifier.of(identifier.getNamespace(), "shaders/" + identifier.getPath() + "." + string1));
         if (string3 == null) {
            try (InputStream inputStream = PipelineComponent204.class
                  .getResourceAsStream("/assets/" + identifier.getNamespace() + "/shaders/" + identifier.getPath() + "." + string1)) {
               if (inputStream != null) {
                  string3 = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
               }
            } catch (Exception exception) {
            }
         }
      }

      if (string3 == null) {
         return null;
      }

      Identifier identifier1 = Identifier.of(identifier.getNamespace(), "shaders/" + identifier.getPath() + "." + string1);
      String string5 = computeValue_2812(string3, identifier1, new HashSet<>());
      return computeValue_2809(string5);
   }

   private static String computeValue_2809(String string) {
      StringBuilder stringBuilder = new StringBuilder(string.length());
      boolean bl = false;

      for (String string1 : string.split("\n", -1)) {
         if (string1.trim().startsWith("#version")) {
            if (bl) {
               continue;
            }

            bl = true;
         }

         stringBuilder.append(string1).append('\n');
      }

      return stringBuilder.toString();
   }

   private static String computeValue_2810(String string) {
      byte[] b = Base64.getDecoder().decode(string);
      byte[] c = new byte[b.length];

      for (int i = 0; i < b.length; i++) {
         c[i] = (byte)(b[i] ^ KEY[i % KEY.length]);
      }

      return new String(c, StandardCharsets.UTF_8);
   }

   private static String computeValue_2812(String string, Identifier identifier, Set<String> set) {
      StringBuilder stringBuilder = new StringBuilder(string.length() + 256);

      for (String string1 : string.split("\n", -1)) {
         String string2 = string1.trim();
         if (string2.startsWith("#moj_import")) {
            Identifier identifier1 = computeValue_2811(string2, identifier);
            if (identifier1 != null) {
               if (set.add(identifier1.toString())) {
                  String string3 = computeValue_2813(identifier1);
                  if (string3 != null) {
                     stringBuilder.append(computeValue_2812(string3, identifier1, set));
                     if (stringBuilder.length() > 0 && stringBuilder.charAt(stringBuilder.length() - 1) != '\n') {
                        stringBuilder.append('\n');
                     }
                  }
               }
               continue;
            }
         }

         stringBuilder.append(string1).append('\n');
      }

      return stringBuilder.toString();
   }

   private static Identifier computeValue_2811(String string, Identifier identifier) {
      int i = string.indexOf(60);
      int j = string.indexOf(62);
      if (i >= 0 && j > i) {
         String string4 = string.substring(i + 1, j).trim();
         Identifier identifier1 = Identifier.of(string4);
         return Identifier.of(identifier1.getNamespace(), "shaders/include/" + identifier1.getPath());
      } else {
         int k = string.indexOf(34);
         int l = string.lastIndexOf(34);
         if (k >= 0 && l > k) {
            String string1 = string.substring(k + 1, l).trim();
            String string2 = identifier.getPath();
            int m = string2.lastIndexOf(47);
            String string3 = m >= 0 ? string2.substring(0, m + 1) : "";
            return Identifier.of(identifier.getNamespace(), string3 + string1);
         } else {
            return null;
         }
      }
   }

   private static String computeValue_2813(Identifier identifier) {
      try {
         MinecraftClient minecraftClient = MinecraftClient.getInstance();
         if (minecraftClient != null && minecraftClient.getResourceManager() != null) {
            Optional optional = minecraftClient.getResourceManager().getResource(identifier);
            if (optional.isPresent()) {
               try (InputStream inputStream = ((Resource)optional.get()).getInputStream()) {
                  return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
               }
            }
         }
      } catch (Exception exception1) {
      }

      try (InputStream inputStream1 = PipelineComponent204.class.getResourceAsStream("/assets/" + identifier.getNamespace() + "/" + identifier.getPath())) {
         if (inputStream1 != null) {
            return new String(inputStream1.readAllBytes(), StandardCharsets.UTF_8);
         }
      } catch (Exception exception) {
      }

      return null;
   }
}
