package aethereal.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBus {
   private static final Map<Class<? extends Event>, List<EventBus.NestedType299>> mapField1 = new HashMap<>();
   private static final Set<String> a_logged = new HashSet<>();

   private EventBus() {
   }

   public static void register(Object object) {
      for (Method method : object.getClass().getDeclaredMethods()) {
         if (!checkCondition_0609(method)) {
            performAction_0611(method, object);
         }
      }
   }

   public static void registerForEvent(Object object, Class<? extends Event> class_) {
      for (Method method : object.getClass().getDeclaredMethods()) {
         if (!checkCondition_0610(method, class_)) {
            performAction_0611(method, object);
         }
      }
   }

   public static void unregister(Object object) {
      for (List list : mapField1.values()) {
         for (EventBus.NestedType299 nestedType299 : list) {
            if (nestedType299.getSource_0613().equals(object)) {
               list.remove(nestedType299);
            }
         }
      }

      performAction_0603(true);
   }

   public static void unregisterForEvent(Object object, Class<? extends Event> class_) {
      if (mapField1.containsKey(class_)) {
         for (EventBus.NestedType299 nestedType299 : mapField1.get(class_)) {
            if (nestedType299.getSource_0613().equals(object)) {
               mapField1.get(class_).remove(nestedType299);
            }
         }

         performAction_0603(true);
      }
   }

   private static void performAction_0611(Method method, Object object) {
      Class class_ = method.getParameterTypes()[0];
      final EventBus.NestedType299 nestedType299 = new EventBus.NestedType299(object, method, method.getAnnotation(EventTarget.class).priority());
      if (!nestedType299.getTarget_0614().isAccessible()) {
         nestedType299.getTarget_0614().setAccessible(true);
      }

      if (!mapField1.containsKey(class_)) {
         mapField1.put(class_, new CopyOnWriteArrayList<EventBus.NestedType299>() {
            private static final long serialVersionUID = 666L;

            {
               this.add(nestedType299);
            }
         });
      } else {
         boolean bl = false;

         for (EventBus.NestedType299 nestedType2991 : mapField1.get(class_)) {
            if (nestedType2991.getSource_0613() == object && nestedType2991.getTarget_0614().equals(method)) {
               bl = true;
               break;
            }
         }

         if (!bl) {
            mapField1.get(class_).add(nestedType299);
            performAction_0600(class_);
         }
      }
   }

   public static void performAction_0606(Class<? extends Event> class_) {
      Iterator iterator = mapField1.entrySet().iterator();

      while (iterator.hasNext()) {
         if (((Class)((Entry)iterator.next()).getKey()).equals(class_)) {
            iterator.remove();
            return;
         }
      }
   }

   public static void performAction_0603(boolean bl) {
      Iterator iterator = mapField1.entrySet().iterator();

      while (iterator.hasNext()) {
         if (!bl || ((List)((Entry)iterator.next()).getValue()).isEmpty()) {
            iterator.remove();
         }
      }
   }

   private static void performAction_0600(Class<? extends Event> class_) {
      CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();

      for (byte b : CoreComponent037.byteValueArrayField6) {
         for (EventBus.NestedType299 nestedType299 : mapField1.get(class_)) {
            if (nestedType299.getPriority_0612() == b) {
               copyOnWriteArrayList.add(nestedType299);
            }
         }
      }

      mapField1.put(class_, copyOnWriteArrayList);
   }

   private static boolean checkCondition_0609(Method method) {
      return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(EventTarget.class);
   }

   private static boolean checkCondition_0610(Method method, Class<? extends Event> class_) {
      return checkCondition_0609(method) || !method.getParameterTypes()[0].equals(class_);
   }

   public static Event post(Event event) {
      List list = mapField1.get(event.getClass());
      if (list != null) {
         for (EventBus.NestedType299 nestedType299 : list) {
            performAction_0604(nestedType299, event);
         }
      }

      return event;
   }

   private static void performAction_0604(EventBus.NestedType299 nestedType299, Event event) {
      try {
         nestedType299.getTarget_0614().invoke(nestedType299.getSource_0613(), event);
      } catch (IllegalAccessException illegalAccessException) {
      } catch (IllegalArgumentException illegalArgumentException) {
      } catch (InvocationTargetException invocationTargetException) {
         Throwable throwable = invocationTargetException.getCause() != null ? invocationTargetException.getCause() : invocationTargetException;
         String string = nestedType299.getSource_0613().getClass().getName()
            + "#"
            + nestedType299.getTarget_0614().getName()
            + ":"
            + throwable.getClass().getName();
         if (a_logged.add(string)) {
            System.err
               .println(
                  "[NeneClient] Exception in event handler "
                     + nestedType299.getSource_0613().getClass().getName()
                     + "#"
                     + nestedType299.getTarget_0614().getName()
               );
            throwable.printStackTrace();
         }
      }
   }

   static final class NestedType299 {
      private final Object source;
      private final Method target;
      private final byte priority;

      public NestedType299(Object object, Method method, byte b) {
         this.source = object;
         this.target = method;
         this.priority = b;
      }

      public Object getSource_0613() {
         return this.source;
      }

      public Method getTarget_0614() {
         return this.target;
      }

      public byte getPriority_0612() {
         return this.priority;
      }
   }
}
