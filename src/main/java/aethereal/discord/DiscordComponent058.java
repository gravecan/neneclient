package aethereal.discord;

import aethereal.core.CoreComponent040;
import aethereal.lib.log4j.Log4jComponent143;
import aethereal.lib.log4j.Log4jComponent144;
import aethereal.util.UtilComponent271;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DiscordComponent058 {
   private static final Log4jComponent144 log4jComponent144Field1 = Log4jComponent143.computeValue_1311(DiscordComponent058.class);
   private final List<DiscordContract053> listField2 = new CopyOnWriteArrayList<>();

   public void performAction_0882(DiscordContract053 discordContract053) {
      this.listField2.add(discordContract053);
   }

   public void performAction_0871(DiscordContract053 discordContract053) {
      this.listField2.remove(discordContract053);
   }

   public void performAction_0880(CoreComponent040 coreComponent040) {
      this.performAction_0884("READY", discordContract053 -> discordContract053.performAction_0813(coreComponent040));
   }

   public void performAction_0878(int i, String string) {
      this.performAction_0884("ERROR", discordContract053 -> discordContract053.performAction_0812(i, string));
   }

   public void performAction_0868(int i, String string) {
      this.performAction_0884("DISCONNECT", discordContract053 -> discordContract053.performAction_0808(i, string));
   }

   public void performAction_0875() {
      this.performAction_0884("CLOSE", discordContract053 -> discordContract053.performAction_0810());
   }

   public void performAction_0885(String string, JsonObject jsonObject) {
      switch (string) {
         case "ACTIVITY_JOIN":
            UtilComponent271.computeValue_3548(jsonObject, "secret")
               .ifPresent(string2 -> this.performAction_0884(string, discordContract053 -> discordContract053.performAction_0814(string2)));
            break;
         case "ACTIVITY_SPECTATE":
            UtilComponent271.computeValue_3548(jsonObject, "secret")
               .ifPresent(string2 -> this.performAction_0884(string, discordContract053 -> discordContract053.performAction_0811(string2)));
            break;
         case "ACTIVITY_JOIN_REQUEST":
            UtilComponent271.computeValue_3547(jsonObject, "user").ifPresent(jsonObject1 -> {
               try {
                  CoreComponent040 coreComponent040 = CoreComponent040.computeValue_0702(jsonObject1);
                  this.performAction_0884(string, discordContract053 -> discordContract053.performAction_0809(coreComponent040));
               } catch (RuntimeException runtimeException) {
                  log4jComponent144Field1.performAction_1314("Failed to parse user payload for event {}", string, runtimeException);
               }
            });
            break;
         default:
            log4jComponent144Field1.performAction_1316("Unknown event type: {}", string);
      }
   }

   private void performAction_0884(String string, DiscordComponent058.Contract303 contract303) {
      for (DiscordContract053 discordContract053 : this.listField2) {
         try {
            contract303.accept(discordContract053);
         } catch (Exception exception) {
            log4jComponent144Field1.performAction_1314("Error in listener for event {}", string, exception);
         }
      }
   }

   @FunctionalInterface
   interface Contract303 {
      void accept(DiscordContract053 discordContract053) throws Exception;
   }
}
