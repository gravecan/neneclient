package aethereal.discord;

import com.google.gson.JsonObject;
import java.util.Optional;

public final class DiscordComponent048 {
   private final String join;
   private final String spectate;
   private final String match;

   public DiscordComponent048(String string, String string1, String string2) {
      this.join = string;
      this.spectate = string1;
      this.match = string2;
   }

   public String getJoin_0797() {
      return this.join;
   }

   public String getSpectate_0798() {
      return this.spectate;
   }

   public String getMatch_0800() {
      return this.match;
   }

   public JsonObject computeValue_0794() {
      JsonObject jsonObject = new JsonObject();
      Optional.ofNullable(this.join).ifPresent(string -> jsonObject.addProperty("join", string));
      Optional.ofNullable(this.spectate).ifPresent(string -> jsonObject.addProperty("spectate", string));
      Optional.ofNullable(this.match).ifPresent(string -> jsonObject.addProperty("match", string));
      return jsonObject;
   }
}
