package aethereal.network;

import aethereal.command.CommandComponent014;
import aethereal.core.CoreComponent040;
import aethereal.discord.DiscordBuild;
import aethereal.discord.DiscordComponent042;
import aethereal.discord.DiscordComponent043;
import aethereal.discord.DiscordComponent050;
import aethereal.discord.DiscordComponent055;
import aethereal.discord.DiscordComponent058;
import aethereal.discord.DiscordComponent060;
import aethereal.discord.DiscordComponent066;
import aethereal.discord.DiscordComponent067;
import aethereal.discord.DiscordContract051;
import aethereal.discord.DiscordContract068;
import aethereal.discord.Platform;
import aethereal.lib.javassist.JavassistComponent128;
import aethereal.lib.javassist.OpCode;
import aethereal.lib.jsoup.JsoupContract137;
import aethereal.lib.log4j.Log4jComponent143;
import aethereal.lib.log4j.Log4jComponent144;
import aethereal.util.UtilComponent271;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class NetworkComponent170 {
   private static final Log4jComponent144 log4jComponent144Field1 = Log4jComponent143.computeValue_1311(NetworkComponent170.class);
   private final AtomicReference<NetworkContract171> atomicReferenceField2 = new AtomicReference<>(new NetworkContract171.NestedType368());
   private final AtomicLong atomicLongField3 = new AtomicLong(-1L);
   private final AtomicLong atomicLongField4 = new AtomicLong();
   private final ExecutorService executorServiceField5 = Executors.newSingleThreadExecutor(runnable -> {
      Thread thread = new Thread(runnable, "jDRPC-worker");
      thread.setDaemon(true);
      return thread;
   });
   private final DiscordComponent055 config;
   private final CommandComponent014 commandExecutor;
   private final DiscordComponent058 eventDispatcher;
   private final DiscordContract068 pipePathProvider;
   private final DiscordContract051 connectionFactory;
   private volatile JsoupContract137 jsoupContract137Field6;
   private volatile CoreComponent040 coreComponent040Field7;
   private volatile DiscordBuild discordBuildField8;
   private volatile Future<?> futureField9;
   private Consumer<NetworkContract171> consumerField10;

   public AtomicReference<NetworkContract171> getAtomicReferenceField2_2407() {
      return this.atomicReferenceField2;
   }

   public AtomicLong getAtomicLongField3_2406() {
      return this.atomicLongField3;
   }

   public AtomicLong getAtomicLongField4_2383() {
      return this.atomicLongField4;
   }

   public ExecutorService getExecutorServiceField5_2382() {
      return this.executorServiceField5;
   }

   public DiscordComponent055 getConfig_2379() {
      return this.config;
   }

   public CommandComponent014 getCommandExecutor_2398() {
      return this.commandExecutor;
   }

   public DiscordComponent058 getEventDispatcher_2402() {
      return this.eventDispatcher;
   }

   public DiscordContract068 getPipePathProvider_2403() {
      return this.pipePathProvider;
   }

   public DiscordContract051 getConnectionFactory_2401() {
      return this.connectionFactory;
   }

   public JsoupContract137 getJsoupContract137Field6_2380() {
      return this.jsoupContract137Field6;
   }

   public CoreComponent040 getCoreComponent040Field7_2399() {
      return this.coreComponent040Field7;
   }

   public DiscordBuild getDiscordBuildField8_2400() {
      return this.discordBuildField8;
   }

   public Future<?> getFutureField9_2405() {
      return this.futureField9;
   }

   public Consumer<NetworkContract171> getConsumerField10_2408() {
      return this.consumerField10;
   }

   public NetworkComponent170(DiscordComponent055 discordComponent055, CommandComponent014 commandComponent014, DiscordComponent058 discordComponent058) {
      this(discordComponent055, commandComponent014, discordComponent058, DiscordComponent067::locateAll, string -> {
         switch (Platform.platformField1) {
            case WINDOWS:
               return new DiscordComponent043(string);
            case MACOS:
            case LINUX:
               return new DiscordComponent042(string);
            default:
               throw new IncompatibleClassChangeError();
         }
      });
   }

   NetworkComponent170(
      DiscordComponent055 discordComponent055,
      CommandComponent014 commandComponent014,
      DiscordComponent058 discordComponent058,
      DiscordContract068 discordContract068,
      DiscordContract051 discordContract051
   ) {
      this.config = discordComponent055;
      this.commandExecutor = commandComponent014;
      this.eventDispatcher = discordComponent058;
      this.pipePathProvider = discordContract068;
      this.connectionFactory = discordContract051;
   }

   public NetworkContract171 computeValue_2381() {
      return this.atomicReferenceField2.get();
   }

   public void setConsumerField10_2388(Consumer<NetworkContract171> consumer) {
      this.consumerField10 = consumer;
   }

   private void performAction_2415(NetworkContract171 networkContract171) {
      this.atomicReferenceField2.set(networkContract171);
      Optional.ofNullable(this.consumerField10).ifPresent(consumer -> {
         try {
            consumer.accept(networkContract171);
         } catch (Exception exception) {
            log4jComponent144Field1.performAction_1314("State listener failed for {}", networkContract171, exception);
         }
      });
   }

   public void performAction_2423() {
      NetworkContract171 networkContract171 = this.atomicReferenceField2.get();
      if (!(networkContract171 instanceof NetworkContract171.NestedType366)
         && !(networkContract171 instanceof NetworkContract171.NestedType367)
         && !(networkContract171 instanceof NetworkContract171.NestedType370)) {
         long l = this.atomicLongField4.incrementAndGet();
         this.atomicLongField3.set(-1L);
         this.performAction_2396();
         this.commandExecutor.performAction_0210();
         this.performAction_2415(new NetworkContract171.NestedType367());

         try {
            NetworkComponent170.NestedType364 nestedType364 = this.computeValue_2404();
            this.checkCondition_2412(l, nestedType364, false);
         } catch (Exception exception) {
            if (!this.checkCondition_2410(l)) {
               log4jComponent144Field1.performAction_1316("Discarding stale connect failure for generation {}", l, exception);
            } else {
               this.performAction_2397();
               this.commandExecutor.performAction_0210();
               this.performAction_2415(new NetworkContract171.NestedType369(DiscordComponent060.computeValue_0891(exception)));
               if (exception instanceof DiscordComponent066) {
                  throw (DiscordComponent066)exception;
               } else {
                  throw new DiscordComponent050("Failed to connect", exception);
               }
            }
         }
      } else {
         log4jComponent144Field1.performAction_1316("Ignoring connect() in state {}", networkContract171);
      }
   }

   public void performAction_2391() {
      long l = this.atomicLongField4.incrementAndGet();
      this.atomicLongField3.set(-1L);
      this.performAction_2396();
      this.commandExecutor.performAction_0210();
      JsoupContract137 jsoupContract137 = this.jsoupContract137Field6;
      this.performAction_2397();
      if (jsoupContract137 != null) {
         try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("code", 1000);
            jsonObject.addProperty("message", "Client disconnecting");
            jsoupContract137.performAction_0713(new JavassistComponent128(OpCode.CLOSE, jsonObject));
         } catch (Exception exception) {
            log4jComponent144Field1.performAction_1316("Failed to send CLOSE frame during disconnect for generation {}", l, exception);
         }

         this.performAction_2414(jsoupContract137, "disconnect");
      }

      this.commandExecutor.performAction_0207(new DiscordComponent050("Disconnected"));
      this.performAction_2415(new NetworkContract171.NestedType365());
      this.eventDispatcher.performAction_0875();
      log4jComponent144Field1.performAction_1312("Disconnected from Discord");
   }

   public void performAction_2422() {
      this.atomicLongField4.incrementAndGet();
      this.atomicLongField3.set(-1L);
      this.performAction_2396();
      JsoupContract137 jsoupContract137 = this.jsoupContract137Field6;
      this.performAction_2397();
      if (jsoupContract137 != null) {
         this.performAction_2414(jsoupContract137, "shutdown");
      }

      this.executorServiceField5.shutdownNow();
      this.commandExecutor.performAction_0210();
      this.commandExecutor.performAction_0207(new DiscordComponent050("Shutdown"));
      this.performAction_2415(new NetworkContract171.NestedType365());
      this.eventDispatcher.performAction_0875();
      log4jComponent144Field1.performAction_1312("Shut down Discord IPC");
   }

   private void performAction_2411(long l, JsoupContract137 jsoupContract137) {
      this.futureField9 = this.executorServiceField5.submit(() -> {
         log4jComponent144Field1.performAction_1316("Read loop started for generation {}", l);

         while (this.checkCondition_2410(l) && jsoupContract137.checkCondition_0711() && !Thread.currentThread().isInterrupted()) {
            try {
               JavassistComponent128 javassistComponent128 = jsoupContract137.computeValue_0712();
               if (!this.checkCondition_2410(l)) {
                  return;
               }

               if (log4jComponent144Field1.isDelegate_1318()) {
                  String string = javassistComponent128.getData_1244() != null ? javassistComponent128.getData_1244().toString() : "null";
                  if (string.length() > 200) {
                     string = string.substring(0, 200) + "...";
                  }

                  log4jComponent144Field1.performAction_1316("Received frame: op={}, data={}", javassistComponent128.getOpCode_1243(), string);
               }

               switch (javassistComponent128.getOpCode_1243()) {
                  case CLOSE:
                     int i = UtilComponent271.computeValue_3549(javassistComponent128.getData_1244(), "code", 0);
                     String string1 = UtilComponent271.computeValue_3550(javassistComponent128.getData_1244(), "message", "Discord closed connection");
                     log4jComponent144Field1.performAction_1312("Received CLOSE frame from Discord: code={}, message={}", i, string1);
                     this.performAction_2409(i, string1, new DiscordComponent050(string1), l);
                     return;
                  case PING:
                     jsoupContract137.performAction_0713(new JavassistComponent128(OpCode.PONG, javassistComponent128.getData_1244()));
                     break;
                  case PONG:
                     log4jComponent144Field1.performAction_1316("Received PONG");
                     break;
                  case FRAME:
                     this.performAction_2418(javassistComponent128.getData_1244());
                     break;
                  default:
                     log4jComponent144Field1.performAction_1317("Unexpected opcode in read loop: {}", javassistComponent128.getOpCode_1243());
               }
            } catch (Exception exception) {
               if (!Thread.currentThread().isInterrupted()) {
                  log4jComponent144Field1.performAction_1314("Read loop error: {}", exception.getMessage(), exception);
                  this.performAction_2409(0, exception.getMessage(), exception, l);
               }
            }
         }

         log4jComponent144Field1.performAction_1316("Read loop ended for generation {}", l);
      });
   }

   private void performAction_2418(JsonObject jsonObject) {
      if (jsonObject != null) {
         String string = UtilComponent271.computeValue_3548(jsonObject, "evt").orElse(null);
         if ("ERROR".equals(string) && UtilComponent271.computeValue_3548(jsonObject, "nonce").isPresent()) {
            JsonObject jsonObject1 = UtilComponent271.computeValue_3547(jsonObject, "data").orElse(null);
            int i = UtilComponent271.computeValue_3549(jsonObject1, "code", 1000);
            String string1 = UtilComponent271.computeValue_3550(jsonObject1, "message", "Unknown error");
            this.eventDispatcher.performAction_0878(i, string1);
         }

         boolean bl = this.commandExecutor.checkCondition_0205(jsonObject);
         log4jComponent144Field1.performAction_1316("Frame handled by command executor: {}", bl);
         if (!bl) {
            String string2 = UtilComponent271.computeValue_3550(jsonObject, "cmd", "");
            if ("DISPATCH".equals(string2)) {
               UtilComponent271.computeValue_3548(jsonObject, "evt").ifPresent(string3 -> {
                  JsonObject jsonObject3 = UtilComponent271.computeValue_3547(jsonObject, "data").orElse(null);
                  this.eventDispatcher.performAction_0885(string3, jsonObject3);
               });
            }
         }
      }
   }

   private void performAction_2409(int i, String string, Throwable throwable, long l) {
      if (!this.atomicLongField4.compareAndSet(l, l + 1L)) {
         log4jComponent144Field1.performAction_1316("Ignoring stale disconnect for generation {}", l);
      } else {
         long m = l + 1L;
         this.performAction_2396();
         JsoupContract137 jsoupContract137 = this.jsoupContract137Field6;
         this.performAction_2397();
         this.performAction_2414(jsoupContract137, "disconnect");
         this.commandExecutor.performAction_0210();
         this.commandExecutor.performAction_0207(new DiscordComponent050("Disconnected", throwable));
         DiscordComponent058 discordComponent058 = this.eventDispatcher;
         String string1;
         if (string != null) {
            string1 = string;
         } else {
            string1 = throwable != null ? throwable.getMessage() : "Unknown";
         }

         discordComponent058.performAction_0868(i, string1);
         if (!this.config.isReconnect_0843()) {
            this.atomicLongField3.set(-1L);
            this.performAction_2415(new NetworkContract171.NestedType369(DiscordComponent060.computeValue_0891(throwable)));
         } else {
            this.performAction_2387(throwable, m);
         }
      }
   }

   private void performAction_2421(Throwable throwable, long l) {
      int i = 1;
      int j = this.config.getMaxReconnectAttempts_0856();
      long m = this.config.getReconnectBaseDelayMs_0850();
      long n = this.config.getReconnectMaxDelayMs_0844();
      Throwable throwable1 = throwable;

      while (!Thread.currentThread().isInterrupted() && this.checkCondition_2410(l) && (j == 0 || i <= j)) {
         this.performAction_2415(new NetworkContract171.NestedType370(i, DiscordComponent060.computeValue_0891(throwable1)));
         log4jComponent144Field1.performAction_1316("Reconnecting (attempt {})...", i);

         try {
            Thread.sleep(m);
            if (!this.checkCondition_2410(l)) {
               this.atomicLongField3.compareAndSet(l, -1L);
               return;
            }

            try {
               NetworkComponent170.NestedType364 nestedType364 = this.computeValue_2404();
               if (this.checkCondition_2412(l, nestedType364, true)) {
                  return;
               }

               i++;
               m = Math.min(m * 2L, n);
            } catch (Exception exception) {
               throwable1 = exception;
               log4jComponent144Field1.performAction_1316("Reconnect attempt {} failed: {}", i, exception.getMessage(), exception);
            }
         } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            this.atomicLongField3.compareAndSet(l, -1L);
            return;
         }
      }

      this.atomicLongField3.compareAndSet(l, -1L);
      if (this.checkCondition_2410(l)) {
         this.performAction_2415(new NetworkContract171.NestedType369(DiscordComponent060.computeValue_0891(throwable1)));
         log4jComponent144Field1.performAction_1313("Failed to reconnect after {} attempts", i - 1);
      }
   }

   NetworkComponent170.NestedType364 computeValue_2404() {
      List list = this.pipePathProvider.locateAll();
      boolean bl = this.config.getPreferredBuilds_0842().contains(DiscordBuild.ANY);

      for (String string : list) {
         try {
            NetworkComponent170.NestedType364 nestedType364 = this.computeValue_2420(string);
            if (bl || this.config.getPreferredBuilds_0842().contains(nestedType364.build)) {
               return nestedType364;
            }

            this.performAction_2414(nestedType364.connection, "skipping non-preferred build from " + string);
         } catch (Exception exception1) {
            log4jComponent144Field1.performAction_1316("Pipe {} unavailable: {}", string, exception1.getMessage(), exception1);
         }
      }

      for (String string1 : list) {
         try {
            return this.computeValue_2420(string1);
         } catch (Exception exception) {
            log4jComponent144Field1.performAction_1316("Pipe {} failed during second pass: {}", string1, exception.getMessage(), exception);
         }
      }

      throw new DiscordComponent066();
   }

   NetworkComponent170.NestedType364 computeValue_2420(String string) throws IOException {
      Object object = null;
      boolean bl = false;

      try {
         JsoupContract137 jsoupContract137 = this.connectionFactory.create(string);
         NetworkComponent170.NestedType364 nestedType364 = this.computeValue_2413(jsoupContract137);
         bl = true;
         return nestedType364;
      } finally {
         if (!bl) {
            this.performAction_2414((JsoupContract137)object, "failed handshake on " + string);
         }
      }
   }

   NetworkComponent170.NestedType364 computeValue_2413(JsoupContract137 jsoupContract137) throws IOException {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("v", 1);
      jsonObject.addProperty("client_id", String.valueOf(this.config.getClientId_0846()));
      jsoupContract137.performAction_0713(new JavassistComponent128(OpCode.HANDSHAKE, jsonObject));
      JavassistComponent128 javassistComponent128 = jsoupContract137.computeValue_0712();
      if (javassistComponent128.getOpCode_1243() == OpCode.CLOSE) {
         throw new DiscordComponent050("Discord rejected handshake");
      } else if (javassistComponent128.getOpCode_1243() != OpCode.FRAME) {
         throw new DiscordComponent050("Unexpected opcode in handshake response: " + javassistComponent128.getOpCode_1243());
      } else {
         JsonObject jsonObject1 = javassistComponent128.getData_1244();
         if (jsonObject1 != null && !jsonObject1.entrySet().isEmpty()) {
            UtilComponent271.computeValue_3548(jsonObject1, "cmd").filter(string1 -> !"DISPATCH".equals(string1)).ifPresent(string1 -> {
               throw new DiscordComponent050("Unexpected handshake command: " + string1);
            });
            UtilComponent271.computeValue_3548(jsonObject1, "evt").filter(string1 -> !"READY".equals(string1)).ifPresent(string1 -> {
               throw new DiscordComponent050("Unexpected handshake event: " + string1);
            });
            JsonObject jsonObject2 = UtilComponent271.computeValue_3547(jsonObject1, "data")
               .filter(jsonObject4 -> !jsonObject4.entrySet().isEmpty())
               .orElseThrow(() -> new DiscordComponent050("Malformed handshake response: missing data object"));
            JsonObject jsonObject3 = UtilComponent271.computeValue_3547(jsonObject2, "user")
               .filter(jsonObject4 -> !jsonObject4.entrySet().isEmpty())
               .orElseThrow(() -> new DiscordComponent050("No user in handshake response"));
            CoreComponent040 coreComponent040 = this.computeValue_2385(jsonObject3);
            String string = UtilComponent271.computeValue_3547(jsonObject2, "config")
               .flatMap(jsonObject4 -> UtilComponent271.computeValue_3548(jsonObject4, "api_endpoint"))
               .orElse(null);
            DiscordBuild discordBuild = DiscordBuild.computeValue_0717(string);
            return new NetworkComponent170.NestedType364(jsoupContract137, coreComponent040, discordBuild);
         } else {
            throw new DiscordComponent050("Empty handshake response");
         }
      }
   }

   private boolean checkCondition_2412(long l, NetworkComponent170.NestedType364 nestedType364, boolean bl) {
      if (!this.checkCondition_2410(l)) {
         this.performAction_2414(nestedType364.connection, "stale activation for generation " + l);
         return false;
      } else {
         this.jsoupContract137Field6 = nestedType364.connection;
         this.coreComponent040Field7 = nestedType364.user;
         this.discordBuildField8 = nestedType364.build;
         this.commandExecutor.performAction_0203();
         this.atomicLongField3.compareAndSet(l, -1L);
         this.performAction_2415(new NetworkContract171.NestedType366(nestedType364.user, nestedType364.build));
         this.eventDispatcher.performAction_0880(nestedType364.user);
         this.performAction_2411(l, nestedType364.connection);
         return true;
      }
   }

   private void performAction_2387(Throwable throwable, long l) {
      if (!this.atomicLongField3.compareAndSet(-1L, l)) {
         log4jComponent144Field1.performAction_1316("Reconnect already scheduled for generation {}", this.atomicLongField3.get());
      } else {
         try {
            this.executorServiceField5.submit(() -> this.performAction_2421(throwable, l));
         } catch (RejectedExecutionException rejectedExecutionException) {
            this.atomicLongField3.compareAndSet(l, -1L);
            if (this.checkCondition_2410(l)) {
               this.performAction_2415(new NetworkContract171.NestedType369(DiscordComponent060.computeValue_0891(throwable)));
            }

            log4jComponent144Field1.performAction_1314("Failed to schedule reconnect", rejectedExecutionException);
         }
      }
   }

   private void performAction_2396() {
      Future future = this.futureField9;
      if (future != null) {
         future.cancel(true);
         this.futureField9 = null;
      }
   }

   private void performAction_2397() {
      this.jsoupContract137Field6 = null;
      this.coreComponent040Field7 = null;
      this.discordBuildField8 = null;
   }

   private boolean checkCondition_2410(long l) {
      return this.atomicLongField4.get() == l;
   }

   private CoreComponent040 computeValue_2385(JsonObject jsonObject) {
      try {
         CoreComponent040 coreComponent040 = CoreComponent040.computeValue_0702(jsonObject);
         if (coreComponent040.getStringField1_0706() == null || coreComponent040.getStringField1_0706().isBlank()) {
            throw new DiscordComponent050("Handshake user is missing id");
         } else if (coreComponent040.getUsername_0703() != null && !coreComponent040.getUsername_0703().isBlank()) {
            coreComponent040.computeValue_0699();
            return coreComponent040;
         } else {
            throw new DiscordComponent050("Handshake user is missing username");
         }
      } catch (DiscordComponent050 discordComponent050) {
         throw discordComponent050;
      } catch (RuntimeException runtimeException) {
         throw new DiscordComponent050("Invalid user in handshake response", runtimeException);
      }
   }

   private void performAction_2414(JsoupContract137 jsoupContract137, String string) {
      if (jsoupContract137 != null) {
         try {
            jsoupContract137.close();
         } catch (Exception exception) {
            log4jComponent144Field1.performAction_1314("Failed to close connection ({})", string, exception);
         }
      }
   }

   static final class NestedType364 {
      final JsoupContract137 connection;
      final CoreComponent040 user;
      final DiscordBuild build;

      NestedType364(JsoupContract137 jsoupContract137, CoreComponent040 coreComponent040, DiscordBuild discordBuild) {
         this.connection = jsoupContract137;
         this.user = coreComponent040;
         this.build = discordBuild;
      }

      public JsoupContract137 getConnection_2429() {
         return this.connection;
      }

      public CoreComponent040 getUser_2427() {
         return this.user;
      }

      public DiscordBuild getBuild_2428() {
         return this.build;
      }
   }
}
