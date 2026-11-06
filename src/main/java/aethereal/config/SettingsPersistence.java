package aethereal.config;

import aethereal.core.Category;
import aethereal.lib.json.JsonComponent135;
import aethereal.lib.json.JsonComponent136;
import aethereal.ui.ClientSettingsScreen;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.MinecraftClient;

public final class SettingsPersistence {
   private static final int VERSION = 1;
   private static final long DEBOUNCE_MS = 450L;
   private static final ScheduledExecutorService scheduledExecutorServiceField1 = Executors.newSingleThreadScheduledExecutor(runnable -> {
      Thread thread = new Thread(runnable, "XenoClient-Settings");
      thread.setDaemon(true);
      return thread;
   });
   private static final Object LOCK = new Object();
   private static volatile ListProcessor<?> modules;
   private static volatile ListProcessor<?> theme;
   private static volatile ListProcessor<?> hud;
   private static volatile boolean ready;
   private static volatile boolean loading;
   private static volatile boolean shuttingDown;
   private static volatile boolean preserveNewerFile;
   private static ScheduledFuture<?> pendingSave;
   private static int guiTab;
   private static int guiCategory;
   private static int clientTab;
   private static int clientCategory = 3;

   private SettingsPersistence() {
   }

   public static void performAction_0533(ListProcessor<?> listProcessor, ListProcessor<?> listProcessor1, ListProcessor<?> listProcessor2) {
      synchronized (LOCK) {
         if (ready) {
            return;
         }

         modules = listProcessor;
         theme = listProcessor1;
         hud = listProcessor2;
         loading = true;
      }

      Path path = computeValue_0530();
      boolean bl = Files.isRegularFile(path);
      if (bl) {
         try {
            JsonComponent136 jsonComponent136 = new JsonComponent136(Files.readString(path, StandardCharsets.UTF_8));
            int i = jsonComponent136.computeValue_1271("version", 0);
            if (i > 1) {
               preserveNewerFile = true;
               System.err.println("[XenoClient] Settings file version " + i + " is newer than supported; leaving it untouched.");
            } else {
               performAction_0535(jsonComponent136, "modules", listProcessor);
               performAction_0535(jsonComponent136, "theme", listProcessor1);
               performAction_0535(jsonComponent136, "hud", listProcessor2);
               performAction_0534(jsonComponent136.computeValue_1268("gui"));
            }
         } catch (Throwable throwable) {
            System.err.println("[XenoClient] Could not read settings.json; keeping settings loaded from the existing config files: " + throwable.getMessage());
         }
      }

      synchronized (LOCK) {
         loading = false;
         ready = true;
      }

      if (!bl) {
         performAction_0539();
      }
   }

   private static void performAction_0535(JsonComponent136 jsonComponent136, String string, ListProcessor<?> listProcessor) {
      if (jsonComponent136.checkCondition_1258(string) && listProcessor != null) {
         try {
            listProcessor.performAction_0356(String.valueOf(jsonComponent136.computeValue_1269(string)));
         } catch (Throwable throwable) {
            System.err.println("[XenoClient] Ignoring invalid '" + string + "' settings: " + throwable.getMessage());
         }
      }
   }

   private static void performAction_0534(JsonComponent136 jsonComponent136) {
      if (jsonComponent136 != null) {
         guiTab = computeValue_0532(jsonComponent136.computeValue_1271("tab", guiTab), 0, 3);
         guiCategory = computeValue_0532(jsonComponent136.computeValue_1271("category", guiCategory), 0, Category.values().length - 1);
         clientTab = computeValue_0532(jsonComponent136.computeValue_1271("clientTab", clientTab), 0, 4);
         clientCategory = computeValue_0532(jsonComponent136.computeValue_1271("clientCategory", clientCategory), 0, 4);
         float f = jsonComponent136.computeValue_1269("opacity") instanceof Number number ? number.floatValue() : ClientSettingsScreen.GUI_OPACITY;
         ClientSettingsScreen.performAction_2896(
            f, jsonComponent136.computeValue_1271("accent", ClientSettingsScreen.ACCENT), jsonComponent136.checkCondition_1274("glass", true)
         );
      }
   }

   private static int computeValue_0532(int i, int j, int k) {
      return Math.max(j, Math.min(k, i));
   }

   public static void performAction_0527() {
      if (ready && !loading && !shuttingDown && !preserveNewerFile) {
         synchronized (LOCK) {
            if (pendingSave != null) {
               pendingSave.cancel(false);
            }

            pendingSave = scheduledExecutorServiceField1.schedule(SettingsPersistence::performAction_0529, 450L, TimeUnit.MILLISECONDS);
         }
      }
   }

   public static void performAction_0539() {
      performAction_0527();
   }

   private static void performAction_0529() {
      MinecraftClient minecraftClient = MinecraftClient.getInstance();
      if (minecraftClient != null && !shuttingDown) {
         try {
            minecraftClient.execute(() -> {
               if (!shuttingDown) {
                  try {
                     String string = computeValue_0537();
                     scheduledExecutorServiceField1.execute(() -> performAction_0540(string));
                  } catch (Throwable throwable1) {
                     System.err.println("[XenoClient] Could not serialize settings: " + throwable1.getMessage());
                  }
               }
            });
         } catch (Throwable throwable) {
            System.err.println("[XenoClient] Could not schedule settings snapshot: " + throwable.getMessage());
         }
      }
   }

   private static String computeValue_0537() throws Exception {
      JsonComponent136 jsonComponent136 = new JsonComponent136();
      jsonComponent136.computeValue_1272("version", 1);
      if (modules != null) {
         jsonComponent136.computeValue_1272("modules", new JsonComponent136(modules.computeValue_0360()));
      }

      if (theme != null) {
         jsonComponent136.computeValue_1272("theme", new JsonComponent136(theme.computeValue_0360()));
      }

      if (hud != null) {
         jsonComponent136.computeValue_1272("hud", new JsonComponent135(hud.computeValue_0360()));
      }

      JsonComponent136 jsonComponent1361 = new JsonComponent136();
      jsonComponent1361.computeValue_1272("tab", guiTab);
      jsonComponent1361.computeValue_1272("category", guiCategory);
      jsonComponent1361.computeValue_1272("clientTab", clientTab);
      jsonComponent1361.computeValue_1272("clientCategory", clientCategory);
      jsonComponent1361.computeValue_1272("opacity", ClientSettingsScreen.GUI_OPACITY);
      jsonComponent1361.computeValue_1272("accent", ClientSettingsScreen.ACCENT);
      jsonComponent1361.computeValue_1273("glass", ClientSettingsScreen.isGlassEffect_2884());
      jsonComponent136.computeValue_1272("gui", jsonComponent1361);
      return jsonComponent136.computeValue_1263(2);
   }

   private static Path computeValue_0530() {
      return MinecraftClient.getInstance().runDirectory.toPath().resolve("config").resolve(".neneclient").resolve("settings.json");
   }

   private static void performAction_0540(String string) {
      Path path = computeValue_0530();
      Path path1 = path.resolveSibling(path.getFileName() + ".tmp");

      try {
         Files.createDirectories(path.getParent());
         Files.writeString(path1, string, StandardCharsets.UTF_8);

         try {
            Files.setAttribute(path1, "dos:hidden", true);
         } catch (Exception exception1) {
         }

         try {
            Files.setPosixFilePermissions(path1, PosixFilePermissions.fromString("rw-------"));
         } catch (Exception exception) {
         }

         try {
            Files.move(path1, path, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
         } catch (AtomicMoveNotSupportedException atomicMoveNotSupportedException) {
            Files.move(path1, path, StandardCopyOption.REPLACE_EXISTING);
         }
      } catch (IOException iOException1) {
         System.err.println("[XenoClient] Could not safely write settings.json: " + iOException1.getMessage());

         try {
            Files.deleteIfExists(path1);
         } catch (IOException iOException) {
         }
      }
   }

   public static void performAction_0541() {
      if (ready && !preserveNewerFile) {
         synchronized (LOCK) {
            shuttingDown = true;
            if (pendingSave != null) {
               pendingSave.cancel(false);
            }
         }

         scheduledExecutorServiceField1.shutdown();

         try {
            scheduledExecutorServiceField1.awaitTermination(2L, TimeUnit.SECONDS);
         } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
         }

         try {
            performAction_0540(computeValue_0537());
         } catch (Throwable throwable) {
            System.err.println("[XenoClient] Final settings save failed: " + throwable.getMessage());
         }
      }
   }

   public static int getGuiTab_0524() {
      return guiTab;
   }

   public static int getGuiCategory_0526() {
      return guiCategory;
   }

   public static void performAction_0531(int i, int j) {
      guiTab = computeValue_0532(i, 0, 3);
      guiCategory = computeValue_0532(j, 0, Category.values().length - 1);
      performAction_0527();
   }

   public static int getClientTab_0538() {
      return clientTab;
   }

   public static int getClientCategory_0528() {
      return clientCategory;
   }

   public static void performAction_0523(int i, int j) {
      clientTab = computeValue_0532(i, 0, 4);
      clientCategory = computeValue_0532(j, 0, 4);
      performAction_0527();
   }
}
