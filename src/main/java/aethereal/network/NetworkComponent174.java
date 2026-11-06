package aethereal.network;

public class NetworkComponent174 {
   private final String stringField1;
   private final String category;
   private final String name;
   private final String buyerName;
   private final double price;
   private final String unit;
   private final int count;

   @Override
   public String toString() {
      String string = this.getStringField1_2455();
      String string1 = this.getCategory_2456();
      String string2 = this.getName_2457();
      String string3 = this.getBuyerName_2459();
      double d = this.getPrice_2453();
      String string4 = this.getUnit_2454();
      this.getCount_2458();
      return "OrderModel(id="
         + string
         + ", category="
         + string1
         + ", name="
         + string2
         + ", buyerName="
         + string3
         + ", price="
         + d
         + ", unit="
         + string
         + ", count="
         + string4
         + ")";
   }

   public NetworkComponent174(String string, String string1, String string2, String string3, double d, String string4, int i) {
      this.stringField1 = string;
      this.category = string1;
      this.name = string2;
      this.buyerName = string3;
      this.price = d;
      this.unit = string4;
      this.count = i;
   }

   public String getStringField1_2455() {
      return this.stringField1;
   }

   public String getCategory_2456() {
      return this.category;
   }

   public String getName_2457() {
      return this.name;
   }

   public String getBuyerName_2459() {
      return this.buyerName;
   }

   public double getPrice_2453() {
      return this.price;
   }

   public String getUnit_2454() {
      return this.unit;
   }

   public int getCount_2458() {
      return this.count;
   }
}
