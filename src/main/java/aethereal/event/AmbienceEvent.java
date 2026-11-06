package aethereal.event;

import aethereal.core.CancellableEvent;
import aethereal.core.Event;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.fog.FogData;
import net.minecraft.world.biome.Biome.Precipitation;
import org.joml.Vector4f;

public class AmbienceEvent {
   public static class NestedType310 extends CancellableEvent implements Event {
      private float red;
      private float green;
      private float blue;
      private float alpha;

      public void setRed_0956(float f) {
         this.red = f;
      }

      public void setGreen_0950(float f) {
         this.green = f;
      }

      public void setBlue_0951(float f) {
         this.blue = f;
      }

      public void setAlpha_0957(float f) {
         this.alpha = f;
      }

      public NestedType310(float f, float g, float h, float i) {
         this.red = f;
         this.green = g;
         this.blue = h;
         this.alpha = i;
      }

      public float getRed_0952() {
         return this.red;
      }

      public float getGreen_0953() {
         return this.green;
      }

      public float getBlue_0954() {
         return this.blue;
      }

      public float getAlpha_0955() {
         return this.alpha;
      }
   }

   public static class NestedType311 extends CancellableEvent implements Event {
      private Camera camera;
      private FogData fogData;
      private Vector4f color;

      public NestedType311(Camera camera, FogData fogData, Vector4f vector4f) {
         this.camera = camerax;
         this.fogData = fogDatax;
         this.color = vector4f;
      }

      public Camera getCamera_0958() {
         return this.camera;
      }

      public FogData getFogData_0959() {
         return this.fogData;
      }

      public Vector4f getColor_0960() {
         return this.color;
      }

      public void performAction_0961(float f, float g, float h, float i) {
         this.color.set(f, g, h, i);
      }
   }

   public static class NestedType312 extends CancellableEvent implements Event {
      private long time;

      public void setTime_0963(long l) {
         this.time = l;
      }

      public NestedType312(long l) {
         this.time = l;
      }

      public long getTime_0962() {
         return this.time;
      }
   }

   public static class NestedType313 extends CancellableEvent implements Event {
      private final AmbienceEvent.NestedType313.State417 type;
      private float value;
      private Precipitation value2;

      public AmbienceEvent.NestedType313.State417 getType_0965() {
         return this.type;
      }

      public float getValue_0964() {
         return this.value;
      }

      public void setValue_0967(float f) {
         this.value = f;
      }

      public Precipitation getValue2_0966() {
         return this.value2;
      }

      public void setValue2_0968(Precipitation precipitation) {
         this.value2 = precipitation;
      }

      public NestedType313(AmbienceEvent.NestedType313.State417 state417, float f) {
         this.type = state417;
         this.value = f;
      }

      public NestedType313(AmbienceEvent.NestedType313.State417 state417, Precipitation precipitation) {
         this.type = state417;
         this.value2 = precipitation;
      }

      public enum State417 {
         RAIN_GRADIENT,
         THUNDER_GRADIENT,
         PRECIPITATION_PARTICLES,
         PRECIPITATION;
      }
   }
}
