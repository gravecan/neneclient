package aethereal.staff;

import aethereal.api.ApiAnnotation001;
import aethereal.config.ListProcessor;
import aethereal.core.ObfuscatorRuntime;
import aethereal.lib.json.JsonComponent135;
import aethereal.lib.json.JsonComponent136;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class StaffManager extends ListProcessor<StaffEntry> {
   @ApiAnnotation001
   @Override
   protected List<StaffEntry> computeValue_0041(String string) throws Exception {
      JsonComponent135 jsonComponent135 = new JsonComponent135(string);
      ArrayList arrayList = new ArrayList();

      for (int i = 0; i < jsonComponent135.getDelegate_1250(); i++) {
         arrayList.add(new StaffEntry(jsonComponent135.computeValue_1252(i).computeValue_1260("name")));
      }

      return arrayList;
   }

   @ApiAnnotation001
   @Override
   protected String computeValue_0042(List<StaffEntry> list) throws Exception {
      JsonComponent135 jsonComponent135 = new JsonComponent135();

      for (StaffEntry staffEntry : list) {
         JsonComponent136 jsonComponent136 = new JsonComponent136();
         jsonComponent136.computeValue_1272("name", staffEntry.getName_2870());
         jsonComponent135.computeValue_1253(jsonComponent136);
      }

      return jsonComponent135.computeValue_1254(2);
   }

   @Override
   protected String computeValue_0040() {
      return "staff.json";
   }

   public List<StaffEntry> computeValue_2878() {
      return new ArrayList<>(this.listField3);
   }

   public void performAction_2875(String string) {
      if (!this.checkCondition_2876(string)) {
         this.listField3.add(new StaffEntry(string));
      }
   }

   public void performAction_2883(String string) {
      this.listField3.removeIf(staffEntry -> staffEntry.getName_2870().equalsIgnoreCase(string));
   }

   public boolean checkCondition_2876(String string) {
      return this.listField3.stream().anyMatch(staffEntry -> staffEntry.getName_2870().equalsIgnoreCase(string));
   }

   public void performAction_2874(Set<String> set) {
      Set set1 = set.stream()
         .filter(string1 -> string1 != null && !string1.isBlank())
         .map(string1 -> string1.toLowerCase(Locale.ROOT))
         .collect(Collectors.toSet());
      this.listField3.removeIf(staffEntry -> staffEntry.isAutomatic_2869() && !set1.contains(staffEntry.getName_2870().toLowerCase(Locale.ROOT)));

      for (String string : set) {
         if (string != null && !string.isBlank() && !this.checkCondition_2876(string)) {
            this.listField3.add(new StaffEntry(string, true));
         }
      }
   }

   public void getListField3_2882() {
      this.listField3.clear();
   }

   static {
      ObfuscatorRuntime.registerClass(StaffManager.class, 37);
   }
}
