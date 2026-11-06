package aethereal.lib.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

public class JavassistComponent123 extends ClassPool {
   public JavassistComponent123() {
   }

   public JavassistComponent123(boolean bl) {
      super(bl);
   }

   public CtClass computeValue_1235(String string) throws NotFoundException {
      return this.get(string);
   }
}
