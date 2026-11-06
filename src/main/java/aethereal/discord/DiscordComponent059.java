package aethereal.discord;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DiscordComponent059 {
   public static <L> void performAction_0886(Object object, Class<L> class_, L object1) {
      try {
         DiscordComponent063.computeValue_0896(object, "add" + class_.getSimpleName(), object1);
      } catch (ReflectiveOperationException reflectiveOperationException) {
         throw new IllegalArgumentException(
            "Unable to add listener for class "
               + object.getClass().getName()
               + " and public add"
               + class_.getSimpleName()
               + " method which takes a parameter of type "
               + class_.getName()
               + "."
         );
      }
   }

   public static <L> void performAction_0887(Object object, String string, Object object1, Class<L> class_, String... strings) {
      Object object2 = class_.cast(
         Proxy.newProxyInstance(object.getClass().getClassLoader(), new Class[]{class_}, new DiscordComponent059.NestedType304(object, string, strings))
      );
      performAction_0886(object1, class_, object2);
   }

   static final class NestedType304 implements InvocationHandler {
      private final Object target;
      private final String methodName;
      private final Set<String> setField1;

      NestedType304(Object object, String string, String[] strings) {
         this.target = object;
         this.methodName = string;
         this.setField1 = new HashSet<>(Arrays.asList(strings));
      }

      private boolean checkCondition_0888(Method method) {
         return DiscordComponent063.computeValue_0894(this.target.getClass(), this.methodName, method.getParameterTypes()) != null;
      }

      @Override
      public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
         if (!this.setField1.isEmpty() && !this.setField1.contains(method.getName())) {
            return null;
         } else {
            return this.checkCondition_0888(method)
               ? DiscordComponent063.computeValue_0896(this.target, this.methodName, objects)
               : DiscordComponent063.computeValue_0895(this.target, this.methodName);
         }
      }
   }
}
