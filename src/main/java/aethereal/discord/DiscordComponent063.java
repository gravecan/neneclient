package aethereal.discord;

import java.lang.reflect.Method;

public final class DiscordComponent063 {
   private DiscordComponent063() {
   }

   public static Method computeValue_0894(Class<?> class_, String string, Class<?>... class_) {
      try {
         return class_.getMethod(string, class_x);
      } catch (NoSuchMethodException noSuchMethodException) {
         return null;
      }
   }

   public static Object computeValue_0896(Object object, String string, Object... objects) throws ReflectiveOperationException {
      Class[] class_ = new Class[objects.length];

      for (int i = 0; i < objects.length; i++) {
         class_[i] = objects[i] == null ? Object.class : objects[i].getClass();
      }

      Method method = object.getClass().getMethod(string, class_);
      return method.invoke(object, objects);
   }

   public static Object computeValue_0895(Object object, String string) throws ReflectiveOperationException {
      return object.getClass().getMethod(string).invoke(object);
   }
}
