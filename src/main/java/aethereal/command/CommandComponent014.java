package aethereal.command;

import aethereal.discord.RpcErrorCode;
import aethereal.lib.javassist.JavassistComponent128;
import aethereal.lib.javassist.OpCode;
import aethereal.lib.jsoup.JsoupContract137;
import aethereal.lib.log4j.Log4jComponent143;
import aethereal.lib.log4j.Log4jComponent144;
import aethereal.util.UtilComponent271;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class CommandComponent014 {
   private static final Log4jComponent144 log4jComponent144Field1 = Log4jComponent143.computeValue_1311(CommandComponent014.class);
   private final ConcurrentHashMap<String, CompletableFuture<JsonObject>> concurrentHashMapField2 = new ConcurrentHashMap<>();
   private final AtomicLong atomicLongField3 = new AtomicLong();
   private final AtomicBoolean atomicBooleanField4 = new AtomicBoolean(false);
   private final long commandTimeoutMs;
   private final CommandComponent014.NestedType295 nestedType295Field5;

   public CommandComponent014(long l, int i) {
      if (l <= 0L) {
         throw new IllegalArgumentException("commandTimeoutMs must be > 0");
      }

      if (i < 0) {
         throw new IllegalArgumentException("maxCommandsPerSecond must be >= 0");
      }

      this.commandTimeoutMs = l;
      this.nestedType295Field5 = i > 0 ? new CommandComponent014.NestedType295(i) : null;
   }

   public void performAction_0203() {
      this.atomicBooleanField4.set(true);
   }

   public void performAction_0210() {
      this.atomicBooleanField4.set(false);
   }

   public JsonObject computeValue_0204(JsoupContract137 jsoupContract137, String string, JsonObject jsonObject, String string1) throws IOException {
      this.performAction_0202();
      String string2 = String.valueOf(this.atomicLongField3.incrementAndGet());
      CompletableFuture completableFuture = new CompletableFuture();
      this.concurrentHashMapField2.put(string2, completableFuture);

      try {
         try {
            try {
               this.performAction_0206(string2, completableFuture);
               JsonObject jsonObject1 = new JsonObject();
               jsonObject1.addProperty("cmd", string);
               if (jsonObject != null) {
                  jsonObject1.add("args", jsonObject);
               }

               if (string1 != null) {
                  jsonObject1.addProperty("evt", string1);
               }

               jsonObject1.addProperty("nonce", string2);
               log4jComponent144Field1.performAction_1316("Sending command: {} (nonce: {})", string, string2);
               this.performAction_0209();
               this.performAction_0206(string2, completableFuture);
               jsoupContract137.performAction_0713(new JavassistComponent128(OpCode.FRAME, jsonObject1));
               JsonObject jsonObject2 = (JsonObject)completableFuture.get(this.commandTimeoutMs, TimeUnit.MILLISECONDS);
               this.concurrentHashMapField2.remove(string2, completableFuture);
               return jsonObject2;
            } catch (CommandComponent013 commandComponent0131) {
               throw commandComponent0131;
            } catch (TimeoutException timeoutException) {
               throw new IOException("Command timed out after " + this.commandTimeoutMs + " ms", timeoutException);
            }
         } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException("Interrupted while waiting for command response");
         } catch (ExecutionException executionException) {
            if (executionException.getCause() instanceof CommandComponent013 commandComponent013) {
               throw commandComponent013;
            } else {
               throw new IOException("Command failed", executionException.getCause());
            }
         } catch (Exception exception) {
            throw new IOException("Command timeout or error", exception);
         }
      } catch (Throwable throwable) {
         this.concurrentHashMapField2.remove(string2, completableFuture);
         throw throwable;
      }
   }

   public boolean checkCondition_0205(JsonObject jsonObject) {
      String string = UtilComponent271.computeValue_3548(jsonObject, "nonce").orElse(null);
      CompletableFuture completableFuture;
      if (string != null && (completableFuture = this.concurrentHashMapField2.remove(string)) != null) {
         String string1 = UtilComponent271.computeValue_3548(jsonObject, "evt").orElse(null);
         JsonObject jsonObject1 = UtilComponent271.computeValue_3547(jsonObject, "data").orElse(null);
         if ("ERROR".equals(string1)) {
            int i = UtilComponent271.computeValue_3549(jsonObject1, "code", 1000);
            String string2 = UtilComponent271.computeValue_3550(jsonObject1, "message", "Unknown error");
            completableFuture.completeExceptionally(new CommandComponent013(RpcErrorCode.computeValue_0723(i), string2));
            return true;
         } else {
            completableFuture.complete(jsonObject1 != null ? jsonObject1 : new JsonObject());
            return true;
         }
      } else {
         return false;
      }
   }

   public void performAction_0207(Throwable throwable) {
      this.atomicBooleanField4.set(false);
      this.concurrentHashMapField2.forEach((string, completableFuture) -> {
         if (this.concurrentHashMapField2.remove(string, completableFuture)) {
            log4jComponent144Field1.performAction_1316("Cancelling pending command: {}", string);
            completableFuture.completeExceptionally(throwable);
         }
      });
   }

   private void performAction_0202() throws IOException {
      if (!this.atomicBooleanField4.get()) {
         throw new IOException("Connection is not available");
      }
   }

   private void performAction_0206(String string, CompletableFuture<JsonObject> completableFuture) throws IOException {
      if (!this.atomicBooleanField4.get()) {
         this.concurrentHashMapField2.remove(string, completableFuture);
         throw new IOException("Connection is not available");
      }
   }

   private void performAction_0209() throws IOException {
      if (this.nestedType295Field5 != null) {
         try {
            this.nestedType295Field5.performAction_0211();
         } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException("Interrupted while waiting for command rate limiter");
         }
      }
   }

   static final class NestedType295 {
      private final int capacity;
      private double doubleValueField1;
      private long longValueField2 = System.nanoTime();

      NestedType295(int i) {
         this.capacity = i;
         this.doubleValueField1 = i;
      }

      synchronized void performAction_0211() throws InterruptedException {
         while (true) {
            this.performAction_0212();
            if (this.doubleValueField1 >= 1.0) {
               this.doubleValueField1--;
               return;
            }

            long l = (long)Math.ceil((1.0 - this.doubleValueField1) / this.capacity * 1.0E9);
            long m = Math.max(1L, l / 1000000L);
            int i = (int)Math.max(0L, l % 1000000L);
            this.wait(m, i);
         }
      }

      private void performAction_0212() {
         long l = System.nanoTime();
         double d = (l - this.longValueField2) / 1.0E9;
         this.doubleValueField1 = Math.min(this.capacity, this.doubleValueField1 + d * this.capacity);
         this.longValueField2 = l;
      }
   }
}
