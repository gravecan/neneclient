package aethereal.config;

import aethereal.api.ApiAnnotation001;
import aethereal.core.ObfuscatorRuntime;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public abstract class ListProcessor<T> extends PersistentProcessor {
   protected final File fileField1 = new File(minecraft.runDirectory, "config/profiles");
   protected final File fileField2 = new File(this.fileField1, "general");
   protected final List<T> listField3 = new ArrayList<>();

   protected abstract String computeValue_0040();

   @ApiAnnotation001
   @Override
   public void load() {
      try {
         List list = this.listField3;
         if (this.computeValue_0040() != null) {
            File file = this.getFileField2_0355();
            if (!file.exists()) {
               file.mkdirs();
            }

            File file1 = new File(file, this.computeValue_0040());
            String string = file1.exists() ? Files.readString(file1.toPath()) : "";
            List list1 = this.computeValue_0041(string.length() == 0 ? "[]" : string);
            if (list1 != null) {
               list.clear();
               list.addAll(list1);
            }

            if (string.length() == 0) {
               Files.writeString(file1.toPath(), this.computeValue_0042(list));
            }
         }
      } catch (Exception exception) {
         throw new RuntimeException(exception);
      }
   }

   @ApiAnnotation001
   protected abstract List<T> computeValue_0041(String string) throws Exception;

   public final String computeValue_0360() throws Exception {
      return this.computeValue_0042(this.listField3);
   }

   public final void performAction_0356(String string) throws Exception {
      this.computeValue_0041(string);
   }

   @ApiAnnotation001
   protected abstract String computeValue_0042(List<T> list) throws Exception;

   public File getFileField1_0359() {
      return this.fileField1;
   }

   public File getFileField2_0355() {
      return this.fileField2;
   }

   public List<T> getEntries() {
      return this.listField3;
   }

   @Override
   public void save() {
      if (this.computeValue_0040() != null) {
         try {
            File file = this.getFileField2_0355();
            if (!file.exists()) {
               file.mkdirs();
            }

            File file1 = new File(file, this.computeValue_0040());
            Files.writeString(file1.toPath(), this.computeValue_0042(this.listField3));
         } catch (Exception exception) {
            throw new RuntimeException(exception);
         }
      }
   }

   public void performAction_0357() {
      this.save();
   }

   static {
      ObfuscatorRuntime.registerClass(ListProcessor.class, 23);
   }
}
