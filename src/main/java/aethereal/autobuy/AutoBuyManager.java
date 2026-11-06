package aethereal.autobuy;

import aethereal.api.ApiAnnotation001;
import aethereal.config.ListProcessor;
import aethereal.core.ObfuscatorRuntime;
import aethereal.lib.json.JsonComponent135;
import aethereal.lib.json.JsonComponent136;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutoBuyManager extends ListProcessor<AutoBuyEntry> {
   @ApiAnnotation001
   @Override
   protected List<AutoBuyEntry> computeValue_0041(String string) {
      if (this.listField3.isEmpty()) {
         this.listField3.addAll(Arrays.asList(AutoBuyEntry.values()));
      }

      JsonComponent135 jsonComponent135 = new JsonComponent135(string);

      for (int i = 0; i < jsonComponent135.getDelegate_1250(); i++) {
         JsonComponent136 jsonComponent136 = jsonComponent135.computeValue_1252(i);
         String string1 = jsonComponent136.computeValue_1260("name");

         for (Object object : this.listField3) {
            if (!(object instanceof AutoBuyEntry autoBuyEntry)) {
               throw new ClassCastException();
            }

            if (autoBuyEntry.getDisplayName_0029().equals(string1)) {
               if (jsonComponent136.checkCondition_1258("status")) {
                  autoBuyEntry.setFlagField3_0028(jsonComponent136.checkCondition_1261("status"));
               }

               if (jsonComponent136.checkCondition_1258("price")) {
                  autoBuyEntry.setDoubleValueField2_0026(jsonComponent136.computeValue_1264("price"));
               }
            }
         }
      }

      return new ArrayList<>(this.listField3);
   }

   @ApiAnnotation001
   @Override
   protected String computeValue_0042(List<AutoBuyEntry> list) {
      JsonComponent135 jsonComponent135 = new JsonComponent135();

      for (AutoBuyEntry autoBuyEntry : list) {
         JsonComponent136 jsonComponent136 = new JsonComponent136();
         if (!(autoBuyEntry instanceof AutoBuyEntry autoBuyEntry1)) {
            throw new ClassCastException();
         }

         jsonComponent136.computeValue_1272("name", autoBuyEntry1.getDisplayName_0029());
         jsonComponent136.computeValue_1273("status", autoBuyEntry1.isFlagField3_0039());
         jsonComponent136.computeValue_1270("price", autoBuyEntry1.getDoubleValueField2_0038());
         jsonComponent135.computeValue_1253(jsonComponent136);
      }

      return jsonComponent135.computeValue_1254(2);
   }

   public AutoBuyManager() {
      this.listField3.addAll(Arrays.asList(AutoBuyEntry.values()));
   }

   @Override
   protected String computeValue_0040() {
      return "autobuy.json";
   }

   static {
      ObfuscatorRuntime.registerClass(AutoBuyManager.class, 20);
   }
}
