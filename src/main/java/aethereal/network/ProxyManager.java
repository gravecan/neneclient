package aethereal.network;

import aethereal.api.ApiAnnotation001;
import aethereal.config.ListProcessor;
import aethereal.core.ObfuscatorRuntime;
import aethereal.lib.json.JsonComponent135;
import aethereal.lib.json.JsonComponent136;
import java.io.File;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class ProxyManager extends ListProcessor<ProxyEntry> {
   private ProxyEntry selected;
   private boolean safeToSave = true;

   @ApiAnnotation001
   @Override
   protected List<ProxyEntry> computeValue_0041(String string) throws Exception {
      JsonComponent135 jsonComponent135 = new JsonComponent135(string);
      ArrayList arrayList = new ArrayList();

      for (int i = 0; i < jsonComponent135.getDelegate_1250(); i++) {
         JsonComponent136 jsonComponent136 = jsonComponent135.computeValue_1252(i);
         ProxyEntry proxyEntry = new ProxyEntry(jsonComponent136.computeValue_1260("name"));
         proxyEntry.setSelected_2358(jsonComponent136.checkCondition_1275("selected"));
         proxyEntry.setFavorited_2362(jsonComponent136.checkCondition_1275("favorited"));
         arrayList.add(proxyEntry);
      }

      return arrayList;
   }

   @ApiAnnotation001
   @Override
   protected String computeValue_0042(List<ProxyEntry> list) throws Exception {
      JsonComponent135 jsonComponent135 = new JsonComponent135();

      for (ProxyEntry proxyEntry : list) {
         JsonComponent136 jsonComponent136 = new JsonComponent136();
         if (!(proxyEntry instanceof ProxyEntry proxyEntry1)) {
            throw new ClassCastException();
         }

         jsonComponent136.computeValue_1272("name", proxyEntry1.getName_2359());
         jsonComponent136.computeValue_1273("selected", proxyEntry1.isSelected_2360());
         jsonComponent136.computeValue_1273("favorited", proxyEntry1.isFavorited_2357());
         jsonComponent135.computeValue_1253(jsonComponent136);
      }

      return jsonComponent135.computeValue_1254(2);
   }

   @Override
   public void load() {
      try {
         super.load();
      } catch (RuntimeException runtimeException) {
         this.safeToSave = false;
         System.err.println("[XenoClient] Account list could not be loaded; preserving the existing file: " + runtimeException.getMessage());
         return;
      }

      this.selected = this.listField3
         .stream()
         .filter(ProxyEntry::isSelected_2360)
         .findFirst()
         .orElse(this.listField3.isEmpty() ? null : this.listField3.get(0));
      if (this.selected != null) {
         this.performAction_2368(this.selected);
      }
   }

   @Override
   public void save() {
      if (this.safeToSave) {
         if (this.selected != null && this.listField3.contains(this.selected)) {
            this.listField3.forEach(proxyEntry -> proxyEntry.setSelected_2358(proxyEntry == this.selected));
         }

         this.performAction_0357();
      }
   }

   @Override
   public List<ProxyEntry> getEntries() {
      return this.listField3;
   }

   public ProxyEntry getSelected_2367() {
      return this.selected;
   }

   public void performAction_2368(ProxyEntry proxyEntry) {
      this.listField3.forEach(proxyEntry2 -> proxyEntry2.setSelected_2358(proxyEntry2 == proxyEntry));
      this.selected = proxyEntry;
      this.performAction_0357();
   }

   @Override
   public void performAction_0357() {
      if (this.safeToSave) {
         this.performAction_2366();
      }
   }

   public void performAction_2365() {
      if (!this.safeToSave) {
         Path path = new File(this.getFileField2_0355(), this.computeValue_0040()).toPath();
         if (Files.exists(path)) {
            Path path1 = path.resolveSibling(path.getFileName() + ".corrupt.bak");

            try {
               int i = 1;

               while (Files.exists(path1)) {
                  path1 = path.resolveSibling(path.getFileName() + ".corrupt-" + i++ + ".bak");
               }

               Files.copy(path, path1);
            } catch (Exception exception) {
               System.err.println("[XenoClient] Could not back up malformed account list; keeping it untouched: " + exception.getMessage());
               return;
            }
         }

         this.safeToSave = true;
      }

      this.performAction_2366();
   }

   private void performAction_2366() {
      Path path = new File(this.getFileField2_0355(), this.computeValue_0040()).toPath();
      Path path1 = path.resolveSibling(path.getFileName() + ".tmp");

      try {
         Files.createDirectories(path.getParent());
         Files.writeString(path1, this.computeValue_0042(this.listField3));

         try {
            Files.move(path1, path, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
         } catch (AtomicMoveNotSupportedException atomicMoveNotSupportedException) {
            Files.move(path1, path, StandardCopyOption.REPLACE_EXISTING);
         }
      } catch (Exception exception1) {
         System.err.println("[XenoClient] Could not save account list: " + exception1.getMessage());

         try {
            Files.deleteIfExists(path1);
         } catch (Exception exception) {
         }
      }
   }

   @Override
   protected String computeValue_0040() {
      return "accounts.json";
   }

   static {
      ObfuscatorRuntime.registerClass(ProxyManager.class, 19);
   }
}
