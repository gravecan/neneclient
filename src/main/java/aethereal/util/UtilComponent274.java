package aethereal.util;

import java.lang.ref.WeakReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

public class UtilComponent274 {
   private static final Object objectField1 = new Object() {
      @Override
      public String toString() {
         return "null";
      }
   };

   UtilComponent274() {
   }

   static Object computeValue_3554(Object object) {
      return object == null ? objectField1 : object;
   }

   static <T> T computeValue_3553(Object object) {
      return object == objectField1 ? null : UtilComponent263.computeValue_3503(object);
   }

   static class NestedType408<T> implements UtilContract273<T> {
      private final T value;

      NestedType408(T object) {
         this.value = (T)object;
      }

      @Override
      public T computeValue_3552() {
         return this.value;
      }

      public boolean checkCondition_3555() {
         return true;
      }

      public void performAction_3556(T t) {
         throw new UnsupportedOperationException();
      }

      @Override
      public String toString() {
         return String.valueOf(this.value);
      }
   }

   static class NestedType409<T> implements UtilContract273<T> {
      private final Supplier<T> supplier;
      private Object objectField1;

      public NestedType409(Supplier<T> supplier) {
         this.supplier = supplierx;
      }

      @Override
      public T computeValue_3552() {
         Object object = this.objectField1;
         if (object == null) {
            object = this.supplier.get();
            this.objectField1 = UtilComponent274.computeValue_3554(object);
         }

         return UtilComponent274.computeValue_3553(object);
      }

      public boolean checkCondition_3557() {
         return this.objectField1 != null;
      }

      public void setObjectField1_3558(T object) {
         this.objectField1 = object;
      }
   }

   static class NestedType410<T> implements UtilContract273<T> {
      private final Lock lockField1 = new ReentrantLock();
      private final Supplier<T> supplier;
      private volatile Object objectField2;

      NestedType410(Supplier<T> supplier) {
         this.supplier = supplierx;
      }

      @Override
      public T computeValue_3552() {
         Object object = this.objectField2;
         if (object == null) {
            this.lockField1.lock();

            try {
               object = this.objectField2;
               if (object == null) {
                  object = this.supplier.get();
                  this.objectField2 = UtilComponent274.computeValue_3554(object);
               }
            } finally {
               this.lockField1.unlock();
            }
         }

         return UtilComponent274.computeValue_3553(object);
      }

      public void setObjectField2_3561(T object) {
         this.objectField2 = object;
      }

      public void performAction_3559() {
         this.objectField2 = null;
      }

      public boolean checkCondition_3560() {
         return this.objectField2 != null;
      }

      @Override
      public String toString() {
         return this.checkCondition_3560() ? String.valueOf(this.objectField2) : "Lazy value not initialized";
      }
   }

   static class NestedType411<T> implements UtilContract273<T> {
      private final WeakReference<T> weakReferenceField1;

      NestedType411(T object) {
         this.weakReferenceField1 = new WeakReference<>((T)object);
      }

      @Override
      public T computeValue_3552() {
         return this.weakReferenceField1.get();
      }

      public boolean checkCondition_3562() {
         return true;
      }

      public void performAction_3563(T t) {
         throw new UnsupportedOperationException();
      }

      @Override
      public String toString() {
         return String.valueOf(this.computeValue_3552());
      }
   }
}
