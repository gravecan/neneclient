package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;
import net.minecraft.client.sound.SoundInstance;

public class EventComponent071 extends CancellableEvent implements Event {
   private final SoundInstance sound;
   private float volume;

   public SoundInstance getSound_0970() {
      return this.sound;
   }

   public void setVolume_0971(float f) {
      this.volume = f;
   }

   public float getVolume_0969() {
      return this.volume;
   }

   public EventComponent071(SoundInstance soundInstance, float f) {
      this.sound = soundInstance;
      this.volume = f;
   }
}
