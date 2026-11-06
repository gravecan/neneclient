package aethereal.util;

public class UtilComponent268 {
   private long longValueField1;
   private long longValueField2;
   private long longValueField3;

   public void setLongValueField1_3515(long l) {
      this.longValueField1 = l;
   }

   public void setLongValueField2_3519(long l) {
      this.longValueField2 = l;
   }

   public UtilComponent268() {
      this.performAction_3524();
   }

   public boolean checkCondition_3522(long l) {
      return System.currentTimeMillis() - l >= this.longValueField1;
   }

   public boolean checkCondition_3523(long l, long m) {
      return System.currentTimeMillis() - (l + this.longValueField3 % (m + 1L)) >= this.longValueField1;
   }

   public boolean checkCondition_3516(long l) {
      return this.longValueField2 >= l;
   }

   public boolean checkCondition_3517(long l, long m) {
      return this.longValueField2 >= l + this.longValueField3 % (m + 1L);
   }

   public void performAction_3518() {
      this.longValueField2++;
   }

   public void performAction_3524() {
      this.longValueField1 = System.currentTimeMillis();
      this.longValueField3 = (long)(Math.random() * 9.223372E18F);
      this.longValueField2 = 0L;
   }

   public long computeValue_3521() {
      return System.currentTimeMillis() - this.longValueField1;
   }

   public long getLongValueField2_3520() {
      return this.longValueField2;
   }
}
