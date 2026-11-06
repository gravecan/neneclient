package aethereal.core;

public class Processor {
   private final Processor.State297 arch;
   private final Processor.State298 type;

   public Processor(Processor.State297 state297, Processor.State298 state298) {
      this.arch = state297;
      this.type = state298;
   }

   public Processor.State297 getArch_0571() {
      return this.arch;
   }

   public Processor.State298 getType_0572() {
      return this.type;
   }

   public boolean checkCondition_0568() {
      return Processor.State297.BIT_32 == this.arch;
   }

   public boolean checkCondition_0565() {
      return Processor.State297.BIT_64 == this.arch;
   }

   public boolean checkCondition_0569() {
      return Processor.State298.AARCH_64 == this.type;
   }

   public boolean checkCondition_0566() {
      return Processor.State298.IA_64 == this.type;
   }

   public boolean checkCondition_0567() {
      return Processor.State298.PPC == this.type;
   }

   public boolean checkCondition_0570() {
      return Processor.State298.RISC_V == this.type;
   }

   public boolean checkCondition_0573() {
      return Processor.State298.X86 == this.type;
   }

   @Override
   public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.type.getLabel_0575()).append(' ').append(this.arch.getLabel_0574());
      return stringBuilder.toString();
   }

   public enum State297 {
      BIT_32("32-bit"),
      BIT_64("64-bit"),
      UNKNOWN("Unknown");

      private final String label;

      State297(final String string1) {
         this.label = string1;
      }

      public String getLabel_0574() {
         return this.label;
      }
   }

   public enum State298 {
      AARCH_64("AArch64"),
      X86("x86"),
      IA_64("IA-64"),
      PPC("PPC"),
      RISC_V("RISC-V"),
      UNKNOWN("Unknown");

      private final String label;

      State298(final String string1) {
         this.label = string1;
      }

      public String getLabel_0575() {
         return this.label;
      }
   }
}
