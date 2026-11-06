package aethereal.util;

import aethereal.lib.log4j.Log4jContract148;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.locks.ReentrantLock;

public final class UtilComponent285 {
   public static final List<Log4jContract148> listField1 = new ArrayList<>();
   public static final ReentrantLock reentrantLockField2 = new ReentrantLock();

   private UtilComponent285() {
   }

   public static <T> T computeValue_3621(Class<T> class_) {
      return ServiceLoader.<T>load(class_).findFirst().orElseThrow();
   }

   public static void performAction_3620(Log4jContract148 log4jContract148) {
      listField1.add(log4jContract148);
   }

   public static void performAction_3622(URL uRL, ClassLoader classLoader) {
   }
}
