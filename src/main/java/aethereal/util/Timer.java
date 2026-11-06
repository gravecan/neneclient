package aethereal.util;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Timer implements Serializable {
   private static final long serialVersionUID = 9175191792439630013L;
   private final String name;
   private Timer.State stateField1;
   private long longValueField2;
   private final int integerField3;
   private static long longValueField4 = 1000000000L;
   private static long longValueField5 = longValueField4 * 60L;
   private static long longValueField6 = longValueField5 * 60L;
   private ThreadLocal<Long> threadLocalField7 = new ThreadLocal<Long>() {
      public Long computeValue_3489() {
         return 0L;
      }
   };

   public Timer(String string) {
      this(string, 0);
   }

   public Timer(String string, int i) {
      this.name = string;
      this.stateField1 = Timer.State.Stopped;
      this.integerField3 = i > 0 ? i : 0;
   }

   public synchronized void performAction_3481() {
      this.threadLocalField7.set(System.nanoTime());
      this.longValueField2 = 0L;
      this.stateField1 = Timer.State.Started;
   }

   public synchronized void performAction_3487() {
      if (this.stateField1 == Timer.State.Stopped) {
         this.performAction_3481();
      } else {
         this.performAction_3488();
      }
   }

   public synchronized String computeValue_3482() {
      this.longValueField2 = this.longValueField2 + (System.nanoTime() - this.threadLocalField7.get());
      this.threadLocalField7.set(0L);
      this.stateField1 = Timer.State.Stopped;
      return this.toString();
   }

   public synchronized void performAction_3486() {
      this.longValueField2 = this.longValueField2 + (System.nanoTime() - this.threadLocalField7.get());
      this.threadLocalField7.set(0L);
      this.stateField1 = Timer.State.Paused;
   }

   public synchronized void performAction_3488() {
      this.threadLocalField7.set(System.nanoTime());
      this.stateField1 = Timer.State.Started;
   }

   public String getName_3480() {
      return this.name;
   }

   public long computeValue_3479() {
      return this.longValueField2 / 1000000L;
   }

   public long getLongValueField2_3483() {
      return this.longValueField2;
   }

   public Timer.State getStateField1_3484() {
      return this.stateField1;
   }

   @Override
   public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      this.performAction_3485(stringBuilder);
      return stringBuilder.toString();
   }

   public void performAction_3485(StringBuilder stringBuilder) {
      stringBuilder.append("Timer ").append(this.name);
      switch (this.stateField1) {
         case Started:
            stringBuilder.append(" started");
            break;
         case Stopped:
            long l = this.longValueField2;
            long m = l / longValueField6;
            long n = l % longValueField6;
            long o = n / longValueField5;
            long p = n % longValueField5;
            long q = p / longValueField4;
            long r = p % longValueField4;
            String string = "";
            if (m > 0L) {
               string = string + m + " hours ";
            }

            if (o > 0L || m > 0L) {
               string = string + o + " minutes ";
            }

            DecimalFormat decimalFormat = new DecimalFormat("#0");
            String string1 = string + decimalFormat.format(q) + ".";
            DecimalFormat decimalFormat1 = new DecimalFormat("000000000");
            stringBuilder.append(" stopped. Elapsed time: ").append(string1 + decimalFormat1.format(r) + " seconds");
            if (this.integerField3 > 0) {
               long s = this.longValueField2 / this.integerField3;
               long t = s / longValueField6;
               long u = s % longValueField6;
               long v = u / longValueField5;
               long w = u % longValueField5;
               long x = w / longValueField4;
               long y = w % longValueField4;
               String string2 = "";
               if (t > 0L) {
                  string2 = string2 + t + " hours ";
               }

               if (v > 0L || t > 0L) {
                  string2 = string2 + v + " minutes ";
               }

               DecimalFormat decimalFormat2 = new DecimalFormat("#0");
               String string3 = string2 + decimalFormat2.format(x) + ".";
               DecimalFormat decimalFormat3 = new DecimalFormat("000000000");
               stringBuilder.append(" Average per iteration: ").append(string3 + decimalFormat3.format(y) + " seconds");
            }
            break;
         case Paused:
            stringBuilder.append(" paused");
            break;
         default:
            stringBuilder.append(' ').append(this.stateField1);
      }
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (!(object instanceof Timer timer1)) {
         return false;
      } else if (this.longValueField2 == timer1.longValueField2 && this.threadLocalField7 == timer1.threadLocalField7) {
         if (this.name != null) {
            if (!this.name.equals(timer1.name)) {
               return false;
            }
         } else if (timer1.name != null) {
            return false;
         }

         return this.stateField1 != null ? this.stateField1.equals(timer1.stateField1) : timer1.stateField1 == null;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int i = this.name != null ? this.name.hashCode() : 0;
      int j = 29 * i + (this.stateField1 != null ? this.stateField1.hashCode() : 0);
      long l = this.threadLocalField7.get();
      return 29 * (29 * j + (int)(l ^ l >>> 32)) + (int)(this.longValueField2 ^ this.longValueField2 >>> 32);
   }

   public enum State {
      Started,
      Stopped,
      Paused;
   }
}
