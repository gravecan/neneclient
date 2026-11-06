package aethereal.lib.javassist;

import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;

public class JavassistComponent125 extends CodeIterator {
   public JavassistComponent125(CodeAttribute codeAttribute) {
      super(codeAttribute);
   }

   public int computeValue_1238(int i) {
      return this.byteAt(i);
   }

   public int computeValue_1236(int i) {
      return this.u16bitAt(i);
   }

   public int computeValue_1237(int i) {
      return this.s32bitAt(i);
   }
}
