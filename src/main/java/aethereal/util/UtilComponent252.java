package aethereal.util;

import aethereal.core.MinecraftAccess;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.Item.TooltipContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.scoreboard.ReadableScoreboardScore;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.world.biome.BiomeKeys;
import platform.inject.accessors.BossBarHudAccessor;
import platform.inject.accessors.PlayerListHudAccessor;

public class UtilComponent252 implements MinecraftAccess {
   private UtilComponent252() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   public static String computeValue_3399() {
      return minecraft.player != null
            && minecraft.player.networkHandler != null
            && ((PlayerListHudAccessor)minecraft.inGameHud.getPlayerListHud()).getHeader() != null
         ? ((PlayerListHudAccessor)minecraft.inGameHud.getPlayerListHud()).getHeader().getString()
         : "";
   }

   public static String computeValue_3400() {
      return minecraft.player != null && minecraft.player.networkHandler != null
         ? Objects.requireNonNull(minecraft.player.networkHandler.getServerInfo()).address
         : "";
   }

   public static double computeValue_3402() {
      return Math.hypot(minecraft.player.getX() - minecraft.player.lastRenderX, minecraft.player.getZ() - minecraft.player.lastRenderZ) * 20.0;
   }

   public static int computeValue_3403() {
      return minecraft.player != null && Objects.requireNonNull(minecraft.getNetworkHandler()).getPlayerListEntry(minecraft.player.getUuid()) != null
         ? Objects.requireNonNull(minecraft.getNetworkHandler().getPlayerListEntry(minecraft.player.getUuid())).getLatency()
         : 0;
   }

   public static boolean checkCondition_3401() {
      if (minecraft.player != null && !((BossBarHudAccessor)minecraft.inGameHud.getBossBarHud()).getBossBars().isEmpty()) {
         for (ClientBossBar clientBossBar : ((BossBarHudAccessor)minecraft.inGameHud.getBossBarHud()).getBossBars().values()) {
            if (clientBossBar.getName().getString().toLowerCase(Locale.ROOT).contains("pvp")
               || clientBossBar.getName().getString().toLowerCase(Locale.ROOT).contains("pvp")
               || clientBossBar.getName().getString().toLowerCase(Locale.ROOT).contains("Duel")) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static int computeValue_3398() {
      if (minecraft.player != null && !((BossBarHudAccessor)minecraft.inGameHud.getBossBarHud()).getBossBars().isEmpty()) {
         for (ClientBossBar clientBossBar : ((BossBarHudAccessor)minecraft.inGameHud.getBossBarHud()).getBossBars().values()) {
            String string = clientBossBar.getName().getString().toLowerCase(Locale.ROOT);
            if (string.contains("pvp") || string.contains("pvp")) {
               Matcher matcher = Pattern.compile("(\\d+):(\\d+)").matcher(string);
               if (matcher.find()) {
                  return Integer.parseInt(matcher.group(1)) * 60 + Integer.parseInt(matcher.group(2));
               }

               Matcher matcher1 = Pattern.compile("(\\d+)").matcher(string);
               if (matcher1.find()) {
                  return Integer.parseInt(matcher1.group(1));
               }
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   public static final class NestedType400 {
      private NestedType400() {
         throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
      }

      public static boolean checkCondition_3406() {
         return UtilComponent252.computeValue_3399().toLowerCase().contains("funtime")
            || UtilComponent252.computeValue_3400().toLowerCase().contains("funtime");
      }

      public static boolean checkCondition_3407() {
         return checkCondition_3406()
            && MinecraftAccess.minecraft.world != null
            && MinecraftAccess.minecraft.world.getRegistryKey().getValue().toString().equals("minecraft:duels");
      }

      public static boolean checkCondition_3408() {
         return checkCondition_3406()
            && MinecraftAccess.minecraft.player.networkHandler.getBrand() != null
            && MinecraftAccess.minecraft.world.getBiome(MinecraftAccess.minecraft.player.getBlockPos()).matchesKey(BiomeKeys.SWAMP)
            && MinecraftAccess.minecraft.player.networkHandler.getBrand().contains("BotFilter (https://vk.cc/8hr1pU)");
      }

      public static int computeValue_3414() {
         return UtilComponent252.computeValue_3399() != null && UtilComponent252.computeValue_3399().contains("Anarchy-")
            ? Integer.parseInt(UtilComponent252.computeValue_3399().split("Anarchy-")[1].trim())
            : -1;
      }

      public static int computeValue_3405(ItemStack itemStack) {
         if (!itemStack.isEmpty()) {
            List list = itemStack.getTooltip(TooltipContext.DEFAULT, MinecraftAccess.minecraft.player, TooltipType.BASIC)
               .stream()
               .skip(1L)
               .map(text -> text.getString())
               .toList();
            if (!itemStack.getItem().getName().getString().contains("Product out of date") && itemStack.getItem() != Items.GRAY_DYE) {
               for (String string : list) {
                  if (string.contains("$ Price:")) {
                     String string1 = string.substring(string.indexOf("$ Price:") + "$ Price:".length())
                        .trim()
                        .replace(",", "")
                        .replace("$", "")
                        .replaceAll("\\s+", "");
                     if (!string1.isEmpty()) {
                        try {
                           return Integer.parseInt(string1) / (itemStack.getCount() > 0 ? itemStack.getCount() : 1);
                        } catch (NumberFormatException numberFormatException) {
                           return -1;
                        }
                     }
                  }
               }

               return -1;
            } else {
               return -1;
            }
         } else {
            return -1;
         }
      }

      public static float computeValue_3409(LivingEntity livingEntity) {
         if (MinecraftAccess.minecraft.world != null) {
            Scoreboard scoreboard = MinecraftAccess.minecraft.world.getScoreboard();

            for (ScoreboardObjective scoreboardObjective : scoreboard.getObjectives()) {
               ReadableScoreboardScore readableScoreboardScore = scoreboard.getScore(livingEntity, scoreboardObjective);
               if (readableScoreboardScore != null) {
                  return readableScoreboardScore.getScore();
               }
            }
         }

         return livingEntity.getHealth() + livingEntity.getAbsorptionAmount();
      }

      public static long computeValue_3413() {
         if (MinecraftAccess.minecraft.world != null) {
            for (Team team : MinecraftAccess.minecraft.world.getScoreboard().getTeams()) {
               String string = team.getPrefix().getString().toLowerCase(Locale.ROOT);
               if (string.contains("coins")) {
                  return Long.parseLong(string.substring(string.lastIndexOf("") + 1).replaceAll("[^0-9]", ""));
               }
            }

            return -1L;
         } else {
            return -1L;
         }
      }

      public static String computeValue_3404(ItemStack itemStack) {
         int i = Optional.ofNullable((CustomModelDataComponent)itemStack.get(DataComponentTypes.CUSTOM_MODEL_DATA))
            .filter(customModelDataComponent -> !customModelDataComponent.floats().isEmpty())
            .map(customModelDataComponent -> ((Float)customModelDataComponent.floats().getFirst()).intValue())
            .orElse(0);
         switch (i) {
            case 1:
               return "Talisman of Darkness";
            case 2:
               return "Talisman of the Vortex";
            case 3:
               return "Talisman of the Demon";
            case 4:
               return "Talisman of Discord";
            case 5:
               return "Talisman of Fury";
            case 6:
               return "Talisman of the Crusher";
            case 7:
               return "Talisman of the Punisher";
            case 8:
               return "Talisman of the Tyrant";
            default:
               return "Totem of immortality";
         }
      }
   }

   public static final class NestedType401 {
      private NestedType401() {
         throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
      }

      public static boolean checkCondition_3416() {
         return UtilComponent252.computeValue_3399().toLowerCase().contains("holyworld")
            || UtilComponent252.computeValue_3400().toLowerCase().contains("holyworld");
      }

      public static int computeValue_3415() {
         String string = UtilComponent252.computeValue_3399().trim().replaceAll("(?s).*\\n", "");
         return string.contains("Light") && string.contains("#") ? Integer.parseInt(string.replaceAll(".*#(\\d+).*", "$1")) : -1;
      }
   }

   public static final class NestedType402 {
      private NestedType402() {
         throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
      }

      public static boolean checkCondition_3417() {
         return UtilComponent252.computeValue_3399().toLowerCase().contains("reallyworld")
            || UtilComponent252.computeValue_3400().toLowerCase().contains("reallyworld");
      }
   }

   public static final class NestedType403 {
      private NestedType403() {
         throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
      }

      public static boolean checkCondition_3420() {
         return UtilComponent252.computeValue_3399().toLowerCase().contains("spookytime")
            || UtilComponent252.computeValue_3400().toLowerCase().contains("spookytime");
      }

      public static int computeValue_3418() {
         return UtilComponent252.computeValue_3399() != null && UtilComponent252.computeValue_3399().contains("Anarchy-")
            ? Integer.parseInt(UtilComponent252.computeValue_3399().split("Anarchy-")[1].trim())
            : -1;
      }

      public static int computeValue_3419(ItemStack itemStack) {
         if (!itemStack.isEmpty()) {
            List list = itemStack.getTooltip(TooltipContext.DEFAULT, MinecraftAccess.minecraft.player, TooltipType.BASIC)
               .stream()
               .skip(1L)
               .map(text -> text.getString())
               .toList();
            if (!itemStack.getItem().getName().getString().contains("Product out of date") && itemStack.getItem() != Items.GRAY_DYE) {
               for (String string : list) {
                  if (string.contains("$ Price:")) {
                     String string1 = string.substring(string.indexOf("$ Price:") + "$ Price:".length())
                        .trim()
                        .replace(",", "")
                        .replace("$", "")
                        .replaceAll("\\s+", "");
                     if (!string1.isEmpty()) {
                        try {
                           return Integer.parseInt(string1) / (itemStack.getCount() > 0 ? itemStack.getCount() : 1);
                        } catch (NumberFormatException numberFormatException) {
                           return -1;
                        }
                     }
                  }
               }

               return -1;
            } else {
               return -1;
            }
         } else {
            return -1;
         }
      }
   }
}
