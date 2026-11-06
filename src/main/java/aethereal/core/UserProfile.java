package aethereal.core;

public class UserProfile {
   public final String uid;
   public final String username;
   public final String hwid;
   public final String role;
   public final String expire;
   public final String token;

   public String getUid_0686() {
      return this.uid;
   }

   public String getUsername_0687() {
      return this.username;
   }

   public String getHwid_0688() {
      return this.hwid;
   }

   public String getRole_0689() {
      return this.role;
   }

   public String getExpire_0684() {
      return this.expire;
   }

   public String getToken_0685() {
      return this.token;
   }

   public UserProfile(String string, String string1, String string2, String string3, String string4, String string5) {
      this.uid = string;
      this.username = string1;
      this.hwid = string2;
      this.role = string3;
      this.expire = string4;
      this.token = string5;
   }
}
