package aethereal.network;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.util.Identifier;

public class ProxyEntry {
   private String name;
   private boolean selected;
   private boolean favorited;

   public void setName_2363(String string) {
      this.name = string;
   }

   public void setSelected_2358(boolean bl) {
      this.selected = bl;
   }

   public void setFavorited_2362(boolean bl) {
      this.favorited = bl;
   }

   public ProxyEntry() {
   }

   public ProxyEntry(String string, boolean bl, boolean bl1) {
      this.name = string;
      this.selected = bl;
      this.favorited = bl1;
   }

   public String getName_2359() {
      return this.name;
   }

   public boolean isSelected_2360() {
      return this.selected;
   }

   public boolean isFavorited_2357() {
      return this.favorited;
   }

   public ProxyEntry(String string) {
      this.name = string;
      this.selected = true;
   }

   public Identifier computeValue_2361() {
      UUID uUID = this.name != null && !this.name.isEmpty()
         ? UUID.nameUUIDFromBytes(("OfflinePlayer:" + this.name).getBytes(StandardCharsets.UTF_8))
         : new UUID(0L, 0L);
      return DefaultSkinHelper.getSkinTextures(uUID).body().texturePath();
   }
}
