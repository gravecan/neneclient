package aethereal.staff;

import aethereal.render.Animation;

public class StaffEntry {
   private String name;
   private final Animation animationField1 = new Animation();
   private final boolean automatic;

   public void setName_2872(String string) {
      this.name = string;
   }

   public StaffEntry() {
      this.automatic = false;
   }

   public String getName_2870() {
      return this.name;
   }

   public Animation getAnimationField1_2871() {
      return this.animationField1;
   }

   public StaffEntry(String string) {
      this.name = string;
      this.automatic = false;
   }

   public StaffEntry(String string, boolean bl) {
      this.name = string;
      this.automatic = bl;
   }

   public boolean isAutomatic_2869() {
      return this.automatic;
   }
}
