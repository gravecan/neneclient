package aethereal.lib.log4j;

import java.util.function.Supplier;

public class Log4jComponent149<T> {
   private final ThreadLocal<T> local;

   public Log4jComponent149(Supplier<T> supplier) {
      this.local = ThreadLocal.withInitial(supplier);
   }

   public T getLocal_1321() {
      return this.local.get();
   }

   public void performAction_1322(T object) {
      this.local.set((T)object);
   }
}
