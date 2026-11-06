package aethereal.util;

import aethereal.lib.reflections.ReflectionsComponent152;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jetbrains.annotations.Nullable;

public interface UtilContract281 {
   List<String> listField1 = Arrays.asList("boolean", "char", "byte", "short", "int", "long", "float", "double", "void");
   List<Class<?>> listField2 = Arrays.asList(boolean.class, char.class, byte.class, short.class, int.class, long.class, float.class, double.class, void.class);
   List<String> listField3 = Arrays.asList("Z", "C", "B", "S", "I", "J", "F", "D", "V");

   default String computeValue_3604(AnnotatedElement annotatedElement) {
      if (annotatedElement.getClass().equals(Class.class)) {
         return this.computeValue_3595((Class<?>)annotatedElement);
      } else if (annotatedElement.getClass().equals(Constructor.class)) {
         return this.computeValue_3605((Constructor<?>)annotatedElement);
      } else if (annotatedElement.getClass().equals(Method.class)) {
         return this.computeValue_3607((Method)annotatedElement);
      } else {
         return annotatedElement.getClass().equals(Field.class) ? this.computeValue_3606((Field)annotatedElement) : null;
      }
   }

   default String computeValue_3595(Class<?> class_) {
      int i = 0;

      while (class_.isArray()) {
         i++;
         class_ = class_.getComponentType();
      }

      return class_.getName() + String.join("", Collections.nCopies(i, "[]"));
   }

   default String computeValue_3605(Constructor<?> constructor) {
      return String.format("%s.<init>(%s)", constructor.getName(), String.join(", ", this.computeValue_3612(constructor.getParameterTypes())));
   }

   default String computeValue_3607(Method method) {
      return String.format(
         "%s.%s(%s)", method.getDeclaringClass().getName(), method.getName(), String.join(", ", this.computeValue_3612(method.getParameterTypes()))
      );
   }

   default String computeValue_3606(Field field) {
      return String.format("%s.%s", field.getDeclaringClass().getName(), field.getName());
   }

   default Collection<String> computeValue_3608(Collection<? extends AnnotatedElement> collection) {
      return collection.stream().map(this::computeValue_3604).filter(string -> Objects.nonNull(string)).collect(Collectors.toList());
   }

   default Collection<String> computeValue_3612(AnnotatedElement... annotatedElements) {
      return this.computeValue_3608(Arrays.asList(annotatedElements));
   }

   default <T> T computeValue_3597(String string, Class<T> class_, ClassLoader... classLoaders) {
      if (class_.equals(Class.class)) {
         return (T)this.computeValue_3599(string, classLoaders);
      } else if (class_.equals(Constructor.class)) {
         return (T)this.computeValue_3600(string, classLoaders);
      } else if (class_.equals(Method.class)) {
         return (T)this.computeValue_3603(string, classLoaders);
      } else if (class_.equals(Field.class)) {
         return (T)this.computeValue_3601(string, classLoaders);
      } else {
         return (T)(class_.equals(Member.class) ? this.computeValue_3602(string, classLoaders) : null);
      }
   }

   default Class<?> computeValue_3599(String string, ClassLoader... classLoaders) {
      if (listField1.contains(string)) {
         return listField2.get(listField1.indexOf(string));
      }

      String string1;
      if (string.contains("[")) {
         int i = string.indexOf("[");
         String string3 = string.substring(0, i);
         String string4 = string.substring(i).replace("]", "");
         String string2;
         if (listField1.contains(string3)) {
            string2 = listField3.get(listField1.indexOf(string3));
         } else {
            string2 = "L" + string3 + ";";
         }

         string1 = string4 + string2;
      } else {
         string1 = string;
      }

      for (ClassLoader classLoader : UtilComponent266.computeValue_3509(classLoaders)) {
         if (string1.contains("[")) {
            try {
               return Class.forName(string1, false, classLoader);
            } catch (Throwable throwable1) {
            }
         }

         try {
            return classLoader.loadClass(string1);
         } catch (Throwable throwable) {
         }
      }

      return null;
   }

   default Member computeValue_3602(String string, ClassLoader... classLoaders) throws ReflectionsComponent152 {
      int i = string.lastIndexOf(40);
      String string1 = i != -1 ? string.substring(0, i) : string;
      String string2 = i != -1 ? string.substring(i + 1, string.lastIndexOf(41)) : "";
      int j = Math.max(string1.lastIndexOf(46), string1.lastIndexOf("$"));
      String string3 = string1.substring(0, j);
      String string4 = string1.substring(j + 1);
      Class[] class_x = null;
      if (!string2.isEmpty()) {
         String[] strings = string2.split(",");
         class_x = Arrays.stream(strings).map(string5 -> this.computeValue_3599(string5.trim(), classLoaders)).toArray(Class[]::new);
      }

      try {
         for (Class class_x = this.computeValue_3599(string3, classLoaders); class_x != null; class_x = class_x.getSuperclass()) {
            try {
               if (string.contains("(")) {
                  if (!string.contains("init>")) {
                     return class_x.isInterface() ? class_x.getMethod(string4, class_x) : class_x.getDeclaredMethod(string4, class_x);
                  }

                  return class_x.isInterface() ? class_x.getConstructor(class_x) : class_x.getDeclaredConstructor(class_x);
               }

               return class_x.isInterface() ? class_x.getField(string4) : class_x.getDeclaredField(string4);
            } catch (Exception exception) {
            }
         }

         return null;
      } catch (Exception exception1) {
         return null;
      }
   }

   @Nullable
   default <T extends AnnotatedElement> T computeValue_3598(String string, Class<T> class_, ClassLoader[] classLoaders) {
      Member member = this.computeValue_3602(string, classLoaders);
      return (T)(member != null && member.getClass().equals(class_) ? member : null);
   }

   @Nullable
   default Method computeValue_3603(String string, ClassLoader... classLoaders) throws ReflectionsComponent152 {
      return this.computeValue_3598(string, Method.class, classLoaders);
   }

   default Constructor<?> computeValue_3600(String string, ClassLoader... classLoaders) throws ReflectionsComponent152 {
      return this.computeValue_3598(string, Constructor.class, classLoaders);
   }

   @Nullable
   default Field computeValue_3601(String string, ClassLoader... classLoaders) {
      return this.computeValue_3598(string, Field.class, classLoaders);
   }

   default <T> Collection<T> computeValue_3609(Collection<String> collection, Class<T> class_, ClassLoader... classLoaders) {
      return collection.stream()
         .map(string -> this.computeValue_3597(string, class_, classLoaders))
         .filter(Objects::nonNull)
         .collect(Collectors.toCollection(LinkedHashSet::new));
   }

   default Collection<Class<?>> computeValue_3610(Collection<String> collection, ClassLoader... classLoaders) {
      return this.computeValue_3609(collection, Class.class, classLoaders);
   }
}
