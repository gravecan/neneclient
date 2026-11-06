package aethereal.config;

import aethereal.api.ApiAnnotation001;
import aethereal.core.EventTarget;
import aethereal.core.ObfuscatorRuntime;
import aethereal.event.BackendEvent;
import aethereal.network.NetworkComponent176;
import aethereal.util.UtilComponent265;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class ResourcePackProcessor extends PersistentProcessor {
   @ApiAnnotation001
   @Override
   public void load() {
   }

   @Override
   public void save() {
   }

   @EventTarget
   public void onBackendEvent_0522(BackendEvent backendEvent) {
      if (backendEvent.checkCondition_0972() && "resource-packs".equals(backendEvent.getPacket_0974().getStringField1_0656())) {
         NetworkComponent176 networkComponent176 = backendEvent.getPacket_0974().getSecurity_0658();
         String string = backendEvent.getPacket_0974().getPayload_0657();
         String string1 = networkComponent176.computeValue_2474(string, "pack");
         String string2 = networkComponent176.computeValue_2474(string, "archive");
         if (string1 != null && string2 != null) {
            File file = new File(minecraft.runDirectory, "resourcepacks");
            if (!file.exists()) {
               file.mkdirs();
            }

            try {
               Files.write(new File(file, string1 + ".zip").toPath(), Base64.getDecoder().decode(string2));
            } catch (IOException iOException) {
               UtilComponent265.performAction_3505("&c✖ &7Failed to save resource pack &a" + string1);
               return;
            }

            UtilComponent265.performAction_3505("&a✔ &7Resource pack &a" + string1 + "&7 has been successfully added to the list of available resource packs.");
         }
      }
   }

   static {
      ObfuscatorRuntime.registerClass(ResourcePackProcessor.class, 35);
   }
}
