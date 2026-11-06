package aethereal.util;

import java.util.Arrays;
import java.util.List;

public final class UtilComponent266 {
   private UtilComponent266() {
   }

   public static Iterable<ClassLoader> computeValue_3509(ClassLoader... classLoaders) {
      return classLoaders != null && classLoaders.length != 0 ? Arrays.asList(classLoaders) : List.of(ClassLoader.getSystemClassLoader());
   }
}
