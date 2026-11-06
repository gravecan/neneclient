package aethereal.core;

public enum Category {
   Combat("V"),
   Movement("I"),
   Render("t"),
   Player("L"),
   Misc("D"),
   Client("C"),
   Visuals("S");

   private final String icon;

   public String getIcon_0560() {
      return this.icon;
   }

   Category(String string1) {
      this.icon = string1;
   }
}
