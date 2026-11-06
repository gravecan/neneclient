package aethereal.lib.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jComponent144 {
   private final Logger delegate;

   public Log4jComponent144(Class<?> class_) {
      this.delegate = LogManager.getLogger(class_);
   }

   public void performAction_1316(String string, Object... objects) {
      this.delegate.info(string, objects);
   }

   public void performAction_1312(String string, Object... objects) {
      this.delegate.debug(string, objects);
   }

   public void performAction_1314(String string, Object... objects) {
      this.delegate.error(string, objects);
   }

   public void performAction_1317(String string, Object... objects) {
      this.delegate.warn(string, objects);
   }

   public boolean isDelegate_1318() {
      return this.delegate.isDebugEnabled();
   }

   public void performAction_1313(String string, Object... objects) {
      this.delegate.warn(string, objects);
   }

   public void performAction_1315(String string, Throwable throwable) {
      this.delegate.warn(string, throwable);
   }
}
