package aethereal.discord;

import com.google.gson.JsonObject;
import java.util.Optional;

public final class DiscordComponent049 {
   private final Long start;
   private final Long end;

   public DiscordComponent049(Long long_, Long long_) {
      this.start = long_x;
      this.end = long_;
   }

   public Long getStart_0805() {
      return this.start;
   }

   public Long getEnd_0802() {
      return this.end;
   }

   public static DiscordComponent049 computeValue_0806(long l) {
      return new DiscordComponent049(l, null);
   }

   public static DiscordComponent049 computeValue_0803(long l) {
      return new DiscordComponent049(null, l);
   }

   public JsonObject computeValue_0801() {
      JsonObject jsonObject = new JsonObject();
      Optional.ofNullable(this.start).ifPresent(long_ -> jsonObject.addProperty("start", long_));
      Optional.ofNullable(this.end).ifPresent(long_ -> jsonObject.addProperty("end", long_));
      return jsonObject;
   }
}
