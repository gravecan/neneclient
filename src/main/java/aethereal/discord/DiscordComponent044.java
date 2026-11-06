package aethereal.discord;

import aethereal.util.UtilComponent256;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class DiscordComponent044 {
   private final ActivityType activityTypeField1;
   private final String state;
   private final String details;
   private final String url;
   private final DiscordComponent049 timestamps;
   private final DiscordComponent045 assets;
   private final DiscordComponent047 party;
   private final DiscordComponent048 secrets;
   private final List<DiscordComponent046> listField2;
   private final Boolean instance;

   public ActivityType getActivityTypeField1_0734() {
      return this.activityTypeField1;
   }

   public String getState_0753() {
      return this.state;
   }

   public String getDetails_0730() {
      return this.details;
   }

   public String getUrl_0729() {
      return this.url;
   }

   public DiscordComponent049 getTimestamps_0738() {
      return this.timestamps;
   }

   public DiscordComponent045 getAssets_0735() {
      return this.assets;
   }

   public DiscordComponent047 getParty_0736() {
      return this.party;
   }

   public DiscordComponent048 getSecrets_0737() {
      return this.secrets;
   }

   public List<DiscordComponent046> getListField2_0751() {
      return this.listField2;
   }

   public Boolean getInstance_0740() {
      return this.instance;
   }

   public DiscordComponent044(
      ActivityType activityType,
      String string,
      String string1,
      String string2,
      DiscordComponent049 discordComponent049,
      DiscordComponent045 discordComponent045,
      DiscordComponent047 discordComponent047,
      DiscordComponent048 discordComponent048,
      List<DiscordComponent046> list,
      Boolean boolean_
   ) {
      this.activityTypeField1 = activityType != null ? activityType : ActivityType.PLAYING;
      this.state = string;
      this.details = string1;
      this.url = string2;
      this.timestamps = discordComponent049;
      this.assets = discordComponent045;
      this.party = discordComponent047;
      this.secrets = discordComponent048;
      this.listField2 = list != null ? List.copyOf(list) : null;
      this.instance = boolean_;
      if (this.state == null || this.state.length() >= 2 && this.state.length() <= 128) {
         if (this.details == null || this.details.length() >= 2 && this.details.length() <= 128) {
            if (this.listField2 != null && this.listField2.size() > 2) {
               throw new IllegalArgumentException("Activity supports a maximum of 2 buttons, got " + this.listField2.size());
            }

            if (this.activityTypeField1 == ActivityType.STREAMING && this.url == null) {
               throw new IllegalArgumentException("Streaming activity type requires a URL");
            }

            if (this.activityTypeField1 == ActivityType.STREAMING) {
               UtilComponent256.performAction_3458(this.url, "Streaming URL", -1);
            }
         } else {
            throw new IllegalArgumentException("Activity details must be 2-128 characters, got " + this.details.length());
         }
      } else {
         throw new IllegalArgumentException("Activity state must be 2-128 characters, got " + this.state.length());
      }
   }

   public Optional<String> getState_0741() {
      return Optional.ofNullable(this.state);
   }

   public Optional<String> getDetails_0725() {
      return Optional.ofNullable(this.details);
   }

   public Optional<String> getUrl_0755() {
      return Optional.ofNullable(this.url);
   }

   public Optional<DiscordComponent049> getTimestamps_0728() {
      return Optional.ofNullable(this.timestamps);
   }

   public Optional<DiscordComponent045> getAssets_0752() {
      return Optional.ofNullable(this.assets);
   }

   public Optional<DiscordComponent047> getParty_0727() {
      return Optional.ofNullable(this.party);
   }

   public Optional<DiscordComponent048> getSecrets_0731() {
      return Optional.ofNullable(this.secrets);
   }

   public Optional<List<DiscordComponent046>> getListField2_0733() {
      return Optional.ofNullable(this.listField2);
   }

   public Optional<Boolean> getInstance_0754() {
      return Optional.ofNullable(this.instance);
   }

   public JsonObject computeValue_0739() {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("type", this.activityTypeField1.getValue_0708());
      this.getState_0741().ifPresent(string -> jsonObject.addProperty("state", string));
      this.getDetails_0725().ifPresent(string -> jsonObject.addProperty("details", string));
      this.getUrl_0755().ifPresent(string -> jsonObject.addProperty("url", string));
      this.getTimestamps_0728().ifPresent(discordComponent049 -> jsonObject.add("timestamps", discordComponent049.computeValue_0801()));
      this.getAssets_0752().ifPresent(discordComponent045 -> jsonObject.add("assets", discordComponent045.computeValue_0773()));
      this.getParty_0727().ifPresent(discordComponent047 -> jsonObject.add("party", discordComponent047.computeValue_0785()));
      this.getSecrets_0731().ifPresent(discordComponent048 -> jsonObject.add("secrets", discordComponent048.computeValue_0794()));
      this.getListField2_0733().filter(list -> !list.isEmpty()).ifPresent(list -> {
         JsonArray jsonArray = new JsonArray();
         list.forEach(discordComponent046 -> jsonArray.add(discordComponent046.computeValue_0782()));
         jsonObject.add("buttons", jsonArray);
      });
      this.getInstance_0754().ifPresent(boolean_ -> jsonObject.addProperty("instance", boolean_));
      return jsonObject;
   }

   public static final class NestedType300 {
      private String stringField1;
      private String stringField2;
      private String stringField3;
      private DiscordComponent049 discordComponent049Field4;
      private DiscordComponent045 discordComponent045Field5;
      private DiscordComponent047 discordComponent047Field6;
      private DiscordComponent048 discordComponent048Field7;
      private Boolean booleanField8;
      private ActivityType activityTypeField9 = ActivityType.PLAYING;
      private final List<DiscordComponent046> listField10 = new ArrayList<>();

      public DiscordComponent044.NestedType300 computeValue_0763(ActivityType activityType) {
         this.activityTypeField9 = activityType;
         return this;
      }

      public DiscordComponent044.NestedType300 computeValue_0767(String string) {
         this.stringField1 = string;
         return this;
      }

      public DiscordComponent044.NestedType300 computeValue_0757(String string) {
         this.stringField2 = string;
         return this;
      }

      public DiscordComponent044.NestedType300 computeValue_0759(String string) {
         this.stringField3 = string;
         return this;
      }

      public DiscordComponent044.NestedType300 computeValue_0762(long l) {
         this.discordComponent049Field4 = new DiscordComponent049(
            l, this.discordComponent049Field4 != null ? this.discordComponent049Field4.getEnd_0802() : null
         );
         return this;
      }

      public DiscordComponent044.NestedType300 computeValue_0756(long l) {
         this.discordComponent049Field4 = new DiscordComponent049(
            this.discordComponent049Field4 != null ? this.discordComponent049Field4.getStart_0805() : null, l
         );
         return this;
      }

      public DiscordComponent044.NestedType300 computeValue_0766(DiscordComponent049 discordComponent049) {
         this.discordComponent049Field4 = discordComponent049;
         return this;
      }

      public DiscordComponent044.NestedType300 computeValue_0772(String string) {
         this.discordComponent045Field5 = new DiscordComponent045(
            string,
            this.discordComponent045Field5 != null ? this.discordComponent045Field5.getLargeText_0778() : null,
            this.discordComponent045Field5 != null ? this.discordComponent045Field5.getSmallImage_0780() : null,
            this.discordComponent045Field5 != null ? this.discordComponent045Field5.getSmallText_0774() : null
         );
         return this;
      }

      public DiscordComponent044.NestedType300 computeValue_0770(String string, String string1) {
         this.discordComponent045Field5 = new DiscordComponent045(
            string,
            string1,
            this.discordComponent045Field5 != null ? this.discordComponent045Field5.getSmallImage_0780() : null,
            this.discordComponent045Field5 != null ? this.discordComponent045Field5.getSmallText_0774() : null
         );
         return this;
      }

      public DiscordComponent044.NestedType300 computeValue_0758(String string, String string1) {
         this.discordComponent045Field5 = new DiscordComponent045(
            this.discordComponent045Field5 != null ? this.discordComponent045Field5.getLargeImage_0777() : null,
            this.discordComponent045Field5 != null ? this.discordComponent045Field5.getLargeText_0778() : null,
            string,
            string1
         );
         return this;
      }

      public DiscordComponent044.NestedType300 computeValue_0764(DiscordComponent045 discordComponent045) {
         this.discordComponent045Field5 = discordComponent045;
         return this;
      }

      public DiscordComponent044.NestedType300 computeValue_0768(String string, int i, int j) {
         this.discordComponent047Field6 = DiscordComponent047.computeValue_0790(string, i, j);
         return this;
      }

      public DiscordComponent044.NestedType300 computeValue_0769(String string, int i, int j, int k) {
         this.discordComponent047Field6 = DiscordComponent047.computeValue_0791(string, i, j, k);
         return this;
      }

      public DiscordComponent044.NestedType300 computeValue_0765(DiscordComponent048 discordComponent048) {
         this.discordComponent048Field7 = discordComponent048;
         return this;
      }

      public DiscordComponent044.NestedType300 computeValue_0760(String string, String string1) {
         this.listField10.add(new DiscordComponent046(string, string1));
         return this;
      }

      public DiscordComponent044.NestedType300 computeValue_0771(boolean bl) {
         this.booleanField8 = bl;
         return this;
      }

      public DiscordComponent044 computeValue_0761() {
         return new DiscordComponent044(
            this.activityTypeField9,
            this.stringField1,
            this.stringField2,
            this.stringField3,
            this.discordComponent049Field4,
            this.discordComponent045Field5,
            this.discordComponent047Field6,
            this.discordComponent048Field7,
            this.listField10.isEmpty() ? null : this.listField10,
            this.booleanField8
         );
      }
   }
}
