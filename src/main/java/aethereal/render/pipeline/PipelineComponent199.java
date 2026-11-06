package aethereal.render.pipeline;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PipelineComponent199 {
   private static final Logger LOGGER = LoggerFactory.getLogger("client/FontRenderer");
   private final PipelineComponent198 pipeline;
   private final Map<String, PipelineComponent197> fonts;
   private boolean initialized = false;

   public PipelineComponent199() {
      this.pipeline = new PipelineComponent198();
      this.fonts = new HashMap<>();
   }

   public void performAction_2789(String string, String string1) {
      Identifier identifier = Identifier.of("neneclient", "fonts/" + string1 + ".json");
      Identifier identifier1 = Identifier.of("neneclient", "fonts/" + string1 + ".png");
      PipelineComponent197 pipelineComponent197 = new PipelineComponent197(identifier, identifier1);
      this.fonts.put(string, pipelineComponent197);
      LOGGER.info("Registered font: {} -> {}", string, string1);
   }

   public void performAction_2798(Map<String, String> map) {
      for (Entry entry : map.entrySet()) {
         this.performAction_2789((String)entry.getKey(), (String)entry.getValue());
      }
   }

   public void performAction_2788() {
      if (!this.initialized) {
         MinecraftClient minecraftClient = MinecraftClient.getInstance();
         if (minecraftClient != null && minecraftClient.getResourceManager() != null) {
            LOGGER.info("Initializing {} fonts...", this.fonts.size());
            long l = System.currentTimeMillis();

            for (Entry entry : this.fonts.entrySet()) {
               ((PipelineComponent197)entry.getValue()).performAction_2739();
            }

            this.initialized = true;
            LOGGER.info("All fonts initialized in {}ms", System.currentTimeMillis() - l);
         }
      }
   }

   public boolean isInitialized_2781() {
      return this.initialized;
   }

   public PipelineComponent197 computeValue_2791(String string) {
      return this.fonts.get(string);
   }

   public PipelineComponent198 getPipeline_2790() {
      return this.pipeline;
   }

   public void performAction_2794(String string, String string1, float f, float g, float h, int i) {
      PipelineComponent197 pipelineComponent197 = this.fonts.get(string);
      if (pipelineComponent197 != null) {
         this.pipeline.performAction_2765(pipelineComponent197, string1, f, g, h, i, 0.0F, 0, 0.0F);
      }
   }

   public void performAction_2795(String string, String string1, float f, float g, float h, int i, float j) {
      PipelineComponent197 pipelineComponent197 = this.fonts.get(string);
      if (pipelineComponent197 != null) {
         this.pipeline.performAction_2765(pipelineComponent197, string1, f, g, h, i, 0.0F, 0, j);
      }
   }

   public void performAction_2796(String string, String string1, float f, float g, float h, int i, float j, int k) {
      PipelineComponent197 pipelineComponent197 = this.fonts.get(string);
      if (pipelineComponent197 != null) {
         this.pipeline.performAction_2765(pipelineComponent197, string1, f, g, h, i, j, k, 0.0F);
      }
   }

   public void performAction_2797(String string, String string1, float f, float g, float h, int i, float j, int k, float l) {
      PipelineComponent197 pipelineComponent197 = this.fonts.get(string);
      if (pipelineComponent197 != null) {
         this.pipeline.performAction_2765(pipelineComponent197, string1, f, g, h, i, j, k, l);
      }
   }

   public void performAction_2793(String string, String string1, float f, float g, float h, float i, int j) {
      PipelineComponent197 pipelineComponent197 = this.fonts.get(string);
      if (pipelineComponent197 != null) {
         this.pipeline.performAction_2763(pipelineComponent197, string1, f, g, h, i, j);
      }
   }

   public void performAction_2783(String string, String string1, float f, float g, float h, float i, int j) {
      PipelineComponent197 pipelineComponent197 = this.fonts.get(string);
      if (pipelineComponent197 != null) {
         this.pipeline.performAction_2754(pipelineComponent197, string1, f, g, h, i, j);
      }
   }

   public void performAction_2787(String string, String string1, float f, float g, float h, float i, int j) {
      PipelineComponent197 pipelineComponent197 = this.fonts.get(string);
      if (pipelineComponent197 != null) {
         this.pipeline.performAction_2757(pipelineComponent197, string1, f, g, h, i, j);
      }
   }

   public void performAction_2784(String string, String string1, float f, float g, float h, int i) {
      PipelineComponent197 pipelineComponent197 = this.fonts.get(string);
      if (pipelineComponent197 != null) {
         float j = this.pipeline.computeValue_2762(pipelineComponent197, string1, h);
         this.pipeline.performAction_2765(pipelineComponent197, string1, f - j / 2.0F, g, h, i, 0.0F, 0, 0.0F);
      }
   }

   public void performAction_2785(String string, String string1, float f, float g, float h, int i, float j) {
      PipelineComponent197 pipelineComponent197 = this.fonts.get(string);
      if (pipelineComponent197 != null) {
         float k = this.pipeline.computeValue_2762(pipelineComponent197, string1, h);
         float l = this.pipeline.computeValue_2753(pipelineComponent197, string1, h);
         float m = f;
         float n = g + l / 2.0F;
         this.pipeline.performAction_2766(pipelineComponent197, string1, f - k / 2.0F, g, h, i, 0.0F, 0, j, m, n);
      }
   }

   public float computeValue_2792(String string, String string1, float f) {
      PipelineComponent197 pipelineComponent197 = this.fonts.get(string);
      return pipelineComponent197 == null ? 0.0F : this.pipeline.computeValue_2762(pipelineComponent197, string1, f);
   }

   public float computeValue_2782(String string, String string1, float f) {
      return this.computeValue_2792(string, string1, f);
   }

   public float computeValue_2786(String string, float f) {
      PipelineComponent197 pipelineComponent197 = this.fonts.get(string);
      return pipelineComponent197 == null ? f : pipelineComponent197.getLineHeight_2749() / pipelineComponent197.getFontSize_2742() * f;
   }

   public float computeValue_2799(String string, float f) {
      return this.computeValue_2786(string, f);
   }

   public void getPipeline_2800() {
      this.pipeline.performAction_2772();
   }

   public void close() {
      this.pipeline.close();
      this.fonts.clear();
      this.initialized = false;
   }
}
