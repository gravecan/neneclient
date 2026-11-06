package aethereal.config;

import aethereal.render.RenderComponent183;
import java.util.Objects;

public class Theme {
   private String name;
   private int integerField1;
   private int integerField2;
   private int integerField3;
   private int integerField4;

   public void performAction_0552(String string) {
      if (!Objects.equals(this.name, string)) {
         this.name = string;
         SettingsPersistence.performAction_0527();
      }
   }

   public void performAction_0550(int i) {
      if (this.integerField1 != i) {
         this.integerField1 = i;
         SettingsPersistence.performAction_0527();
      }
   }

   public void performAction_0546(int i) {
      if (this.integerField2 != i) {
         this.integerField2 = i;
         SettingsPersistence.performAction_0527();
      }
   }

   public void performAction_0553(int i) {
      if (this.integerField3 != i) {
         this.integerField3 = i;
         SettingsPersistence.performAction_0527();
      }
   }

   public void performAction_0545(int i) {
      if (this.integerField4 != i) {
         this.integerField4 = i;
         SettingsPersistence.performAction_0527();
      }
   }

   public Theme() {
   }

   public Theme(String string, int i, int j, int k, int l) {
      this.name = string;
      this.integerField1 = i;
      this.integerField2 = j;
      this.integerField3 = k;
      this.integerField4 = l;
   }

   public String getName_0549() {
      return this.name;
   }

   public int getIntegerField1_0554() {
      return this.integerField1;
   }

   public int getIntegerField2_0555() {
      return this.integerField2;
   }

   public int getIntegerField3_0544() {
      return this.integerField3;
   }

   public int getIntegerField4_0551() {
      return this.integerField4;
   }

   public int computeValue_0547() {
      return RenderComponent183.computeValue_2558(this.integerField1, this.integerField2, this.integerField3, this.integerField4);
   }

   public void performAction_0543(int i) {
      int[] j = RenderComponent183.computeValue_2555(i);
      if (this.integerField1 != j[0] || this.integerField2 != j[1] || this.integerField3 != j[2] || this.integerField4 != j[3]) {
         this.integerField1 = j[0];
         this.integerField2 = j[1];
         this.integerField3 = j[2];
         this.integerField4 = j[3];
         SettingsPersistence.performAction_0527();
      }
   }

   public float computeValue_0548() {
      return this.integerField4 / 255.0F;
   }
}
