package aethereal.config;

import aethereal.core.EventBus;
import aethereal.core.MinecraftAccess;

public abstract class PersistentProcessor implements MinecraftAccess {
   public abstract void load();

   public abstract void save();

   public PersistentProcessor() {
      EventBus.register(this);
   }
}
