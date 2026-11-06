package aethereal.core;

import aethereal.network.NetworkComponent176;

public class CoreComponent036 {
   private final NetworkComponent176 security;
   private final String stringField1;
   private String payload;

   public void setPayload_0659(String string) {
      this.payload = string;
   }

   public NetworkComponent176 getSecurity_0658() {
      return this.security;
   }

   public String getStringField1_0656() {
      return this.stringField1;
   }

   public String getPayload_0657() {
      return this.payload;
   }

   public CoreComponent036(String string, String string1, NetworkComponent176 networkComponent176) {
      this.stringField1 = string;
      this.payload = string1;
      this.security = networkComponent176;
   }
}
