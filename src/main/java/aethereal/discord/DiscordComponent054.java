package aethereal.discord;

import aethereal.command.CommandComponent014;
import aethereal.core.CoreComponent040;
import aethereal.lib.jsoup.JsoupContract137;
import aethereal.network.NetworkComponent170;
import aethereal.network.NetworkContract171;
import aethereal.util.UtilComponent283;
import com.google.gson.JsonObject;
import java.io.Closeable;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiscordComponent054 implements Closeable {
   private final CommandComponent014 commandExecutor;
   private final DiscordComponent058 eventDispatcher;
   private final NetworkComponent170 connectionManager;
   private final ExecutorService asyncExecutor;

   private DiscordComponent054(DiscordComponent055 discordComponent055) {
      this(
         new CommandComponent014(discordComponent055.getCommandTimeoutMs_0848(), discordComponent055.getMaxCommandsPerSecond_0851()),
         new DiscordComponent058(),
         computeValue_0831(),
         discordComponent055
      );
   }

   private DiscordComponent054(
      CommandComponent014 commandComponent014,
      DiscordComponent058 discordComponent058,
      ExecutorService executorService,
      DiscordComponent055 discordComponent055
   ) {
      this(commandComponent014, discordComponent058, new NetworkComponent170(discordComponent055, commandComponent014, discordComponent058), executorService);
   }

   DiscordComponent054(
      CommandComponent014 commandComponent014,
      DiscordComponent058 discordComponent058,
      NetworkComponent170 networkComponent170,
      ExecutorService executorService
   ) {
      this.commandExecutor = commandComponent014;
      this.eventDispatcher = discordComponent058;
      this.connectionManager = networkComponent170;
      this.asyncExecutor = executorService;
   }

   public static DiscordComponent054 computeValue_0832(long l) {
      return new DiscordComponent054(DiscordComponent055.computeValue_0849().computeValue_0862(l).computeValue_0860());
   }

   public static DiscordComponent054 computeValue_0839(DiscordComponent055 discordComponent055) {
      return new DiscordComponent054(discordComponent055);
   }

   public void getConnectionManager_0826() {
      this.connectionManager.performAction_2423();
   }

   public CompletableFuture<Void> computeValue_0830() {
      return CompletableFuture.runAsync(this::getConnectionManager_0826, this.asyncExecutor);
   }

   public void getConnectionManager_0823() {
      this.connectionManager.performAction_2391();
   }

   public boolean checkCondition_0816() {
      return this.connectionManager.computeValue_2381() instanceof NetworkContract171.NestedType366;
   }

   public NetworkContract171 getConnectionManager_0829() {
      return this.connectionManager.computeValue_2381();
   }

   public Optional<CoreComponent040> computeValue_0822() {
      return Optional.ofNullable(this.connectionManager.getCoreComponent040Field7_2399());
   }

   public Optional<DiscordBuild> computeValue_0825() {
      return Optional.ofNullable(this.connectionManager.getDiscordBuildField8_2400());
   }

   public JsonObject computeValue_0834(DiscordComponent044 discordComponent044) throws IOException {
      JsoupContract137 jsoupContract137 = this.computeValue_0828();
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("pid", UtilComponent283.computeValue_3614());
      jsonObject.add("activity", discordComponent044.computeValue_0739());
      return this.commandExecutor.computeValue_0204(jsoupContract137, "SET_ACTIVITY", jsonObject, null);
   }

   public CompletableFuture<JsonObject> computeValue_0835(DiscordComponent044 discordComponent044) {
      return this.computeValue_0838(() -> this.computeValue_0834(discordComponent044));
   }

   public JsonObject computeValue_0824() throws IOException {
      JsoupContract137 jsoupContract137 = this.computeValue_0828();
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("pid", UtilComponent283.computeValue_3614());
      return this.commandExecutor.computeValue_0204(jsoupContract137, "SET_ACTIVITY", jsonObject, null);
   }

   public CompletableFuture<JsonObject> computeValue_0815() {
      return this.computeValue_0838(this::computeValue_0824);
   }

   public void performAction_0836(DiscordContract053 discordContract053) {
      this.eventDispatcher.performAction_0882(discordContract053);
   }

   public void performAction_0819(DiscordContract053 discordContract053) {
      this.eventDispatcher.performAction_0871(discordContract053);
   }

   public JsonObject computeValue_0833(EventType eventType) throws IOException {
      if (!eventType.isSubscribable_0720()) {
         throw new IllegalArgumentException(eventType.name() + " is not subscribable");
      }

      JsoupContract137 jsoupContract137 = this.computeValue_0828();
      return this.commandExecutor.computeValue_0204(jsoupContract137, "SUBSCRIBE", null, eventType.getValue_0718());
   }

   public JsonObject computeValue_0817(EventType eventType) throws IOException {
      if (!eventType.isSubscribable_0720()) {
         throw new IllegalArgumentException(eventType.name() + " is not subscribable");
      }

      JsoupContract137 jsoupContract137 = this.computeValue_0828();
      return this.commandExecutor.computeValue_0204(jsoupContract137, "UNSUBSCRIBE", null, eventType.getValue_0718());
   }

   public JsonObject computeValue_0840(String string) throws IOException {
      JsoupContract137 jsoupContract137 = this.computeValue_0828();
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("user_id", string);
      return this.commandExecutor.computeValue_0204(jsoupContract137, "SEND_ACTIVITY_JOIN_INVITE", jsonObject, null);
   }

   public JsonObject computeValue_0821(String string) throws IOException {
      JsoupContract137 jsoupContract137 = this.computeValue_0828();
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("user_id", string);
      return this.commandExecutor.computeValue_0204(jsoupContract137, "CLOSE_ACTIVITY_JOIN_REQUEST", jsonObject, null);
   }

   @Override
   public void close() {
      this.connectionManager.performAction_2422();
      this.asyncExecutor.shutdownNow();
   }

   private JsoupContract137 computeValue_0828() {
      return Optional.ofNullable(this.connectionManager.getJsoupContract137Field6_2380()).orElseThrow(() -> new DiscordComponent050("Not connected"));
   }

   private static ExecutorService computeValue_0831() {
      return Executors.newSingleThreadExecutor(runnable -> {
         Thread thread = new Thread(runnable, "jDRPC-async");
         thread.setDaemon(true);
         return thread;
      });
   }

   private <T> CompletableFuture<T> computeValue_0838(DiscordComponent054.Contract301<T> contract301) {
      return CompletableFuture.supplyAsync(() -> {
         try {
            return (T)contract301.get();
         } catch (IOException iOException) {
            throw new RuntimeException(iOException);
         }
      }, this.asyncExecutor);
   }

   @FunctionalInterface
   interface Contract301<T> {
      T get() throws IOException;
   }
}
