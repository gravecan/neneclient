package aethereal.config;

import aethereal.api.ApiAnnotation001;
import aethereal.core.ObfuscatorRuntime;
import aethereal.lib.json.JsonComponent136;
import java.util.ArrayList;
import java.util.List;

public class ThemeManager extends ListProcessor<Theme> {
   private ThemeType themeTypeField1 = ThemeType.DARK;

   public ThemeManager() {
      this.listField3.addAll(this.computeValue_0559());
   }

   @ApiAnnotation001
   @Override
   protected List<Theme> computeValue_0041(String string) throws Exception {
      if (string != null && !string.isBlank() && !string.trim().startsWith("[")) {
         JsonComponent136 jsonComponent136 = new JsonComponent136(string);
         this.themeTypeField1 = ThemeType.valueOf(jsonComponent136.computeValue_1257("type", ThemeType.DARK.name()));
         List list = this.getEntries();
         if (list == null) {
            throw new NullPointerException();
         }

         list.clear();
         ThemeInfo[] themeInfos = ThemeInfo.values();
         if (themeInfos == null) {
            throw new NullPointerException();
         }

         for (ThemeInfo themeInfo : themeInfos) {
            if (themeInfo == null) {
               throw new NullPointerException();
            }

            Theme theme = themeInfo.computeValue_0335(this.themeTypeField1);
            List list1 = this.getEntries();
            if (theme == null) {
               throw new NullPointerException();
            }

            Theme theme1 = new Theme(
               theme.getName_0549(), theme.getIntegerField1_0554(), theme.getIntegerField2_0555(), theme.getIntegerField3_0544(), theme.getIntegerField4_0551()
            );
            if (list1 == null) {
               throw new NullPointerException();
            }

            list1.add(theme1);
         }

         JsonComponent136 jsonComponent1361 = jsonComponent136.computeValue_1268("colors");
         if (jsonComponent1361 != null) {
            for (ThemeInfo themeInfo1 : ThemeInfo.values()) {
               if (jsonComponent1361.computeValue_1269(themeInfo1.getLight_0334().getName_0549()) instanceof Number number1) {
                  this.computeValue_0557(themeInfo1).performAction_0543(number1.intValue());
               }
            }
         }

         if (jsonComponent136.checkCondition_1258("primary") && jsonComponent136.computeValue_1269("primary") instanceof Number number) {
            this.computeValue_0557(ThemeInfo.PRIMARY).performAction_0543(number.intValue());
         }

         return null;
      } else {
         return this.computeValue_0559();
      }
   }

   @ApiAnnotation001
   @Override
   protected String computeValue_0042(List<Theme> list) throws Exception {
      JsonComponent136 jsonComponent136 = new JsonComponent136();
      jsonComponent136.computeValue_1272("type", this.themeTypeField1.name());
      jsonComponent136.computeValue_1270("primary", this.computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547());
      JsonComponent136 jsonComponent1361 = new JsonComponent136();

      for (ThemeInfo themeInfo : ThemeInfo.values()) {
         jsonComponent1361.computeValue_1272(themeInfo.getLight_0334().getName_0549(), this.computeValue_0557(themeInfo).computeValue_0547());
      }

      jsonComponent136.computeValue_1272("colors", jsonComponent1361);
      return jsonComponent136.computeValue_1263(2);
   }

   public ThemeType getThemeTypeField1_0556() {
      return this.themeTypeField1;
   }

   @Override
   protected String computeValue_0040() {
      return "theme.json";
   }

   public Theme computeValue_0557(ThemeInfo themeInfo) {
      return this.listField3
         .stream()
         .filter(theme -> theme.getName_0549().equalsIgnoreCase(themeInfo.getLight_0334().getName_0549()))
         .findFirst()
         .orElse(themeInfo.computeValue_0335(this.themeTypeField1));
   }

   private List<Theme> computeValue_0559() {
      ArrayList arrayList = new ArrayList();

      for (ThemeInfo themeInfo : ThemeInfo.values()) {
         Theme theme = themeInfo.computeValue_0335(this.themeTypeField1);
         arrayList.add(
            new Theme(
               theme.getName_0549(), theme.getIntegerField1_0554(), theme.getIntegerField2_0555(), theme.getIntegerField3_0544(), theme.getIntegerField4_0551()
            )
         );
      }

      return arrayList;
   }

   static {
      ObfuscatorRuntime.registerClass(ThemeManager.class, 38);
   }
}
