package aethereal.lib.javassist;

import javassist.CannotCompileException;
import javassist.NotFoundException;
import javassist.Translator;

public interface JavassistContract134 extends Translator {
   default void performAction_1247(JavassistComponent123 javassistComponent123) throws JavassistComponent132, JavassistComponent122 {
      try {
         this.start(javassistComponent123);
      } catch (NotFoundException notFoundException) {
         throw new JavassistComponent132(notFoundException.getMessage());
      } catch (CannotCompileException cannotCompileException) {
         throw new JavassistComponent122(cannotCompileException.getMessage(), cannotCompileException);
      }
   }

   default void performAction_1248(JavassistComponent123 javassistComponent123, String string) throws JavassistComponent132, JavassistComponent122 {
   }
}
