package aethereal.discord;

import aethereal.api.ApiAnnotation001;
import aethereal.config.PersistentProcessor;
import aethereal.core.NeneClient;
import aethereal.core.ObfuscatorRuntime;
import java.io.IOException;

public class DiscordComponent057 extends PersistentProcessor {
   private DiscordComponent054 discordComponent054Field1;

   @ApiAnnotation001
   @Override
   public void load() {
   }

   @Override
   public void save() {
   }

   public DiscordComponent054 getDiscordComponent054Field1_0866() {
      return this.discordComponent054Field1;
   }

   public void performAction_0867(Void void_, Throwable throwable) {
      if (throwable == null) {
         try {
            this.discordComponent054Field1
               .computeValue_0834(
                  new DiscordComponent044.NestedType300()
                     .computeValue_0763(ActivityType.PLAYING)
                     .computeValue_0757("username: " + NeneClient.getInstance().getUser().getUsername_0687())
                     .computeValue_0767("build: " + (NeneClient.getInstance().computeValue_0645() != null ? "development" : "public"))
                     .computeValue_0762(System.currentTimeMillis() / 1000L)
                     .computeValue_0761()
               );
         } catch (IOException iOException) {
            throw new RuntimeException(iOException);
         }
      }
   }

   static {
      ObfuscatorRuntime.registerClass(DiscordComponent057.class, 25);
   }
}
