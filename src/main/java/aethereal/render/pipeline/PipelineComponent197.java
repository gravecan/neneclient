package aethereal.render.pipeline;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PipelineComponent197 {
   private static final Logger LOGGER = LoggerFactory.getLogger("client/Font");
   private final Identifier jsonId;
   private final Identifier textureId;
   private final Map<Integer, PipelineComponent200> glyphs;
   private float atlasWidth = 512.0F;
   private float atlasHeight = 512.0F;
   private float fontSize = 32.0F;
   private float lineHeight = 40.0F;
   private float distanceRange = 4.0F;
   private boolean yOriginBottom = false;
   private final AtomicBoolean loaded = new AtomicBoolean(false);
   final Map<String, Float> widthCache = new LinkedHashMap<String, Float>(512, 0.75F, true) {
      @Override
      protected boolean removeEldestEntry(Entry<String, Float> entry) {
         return this.size() > 2048;
      }
   };

   public PipelineComponent197(Identifier identifier, Identifier identifier1) {
      this.jsonId = identifier;
      this.textureId = identifier1;
      this.glyphs = new HashMap<>();
   }

   public void performAction_2739() {
      if (!this.loaded.get()) {
         synchronized (this) {
            if (!this.loaded.get()) {
               this.performAction_2741();
            }
         }
      }
   }

   public void performAction_2740() {
      if (!this.loaded.get()) {
         synchronized (this) {
            if (!this.loaded.get()) {
               this.performAction_2741();
            }
         }
      }
   }

   private void performAction_2741() {
      try {
         MinecraftClient minecraftClient = MinecraftClient.getInstance();
         if (minecraftClient == null || minecraftClient.getResourceManager() == null) {
            return;
         }

         Optional optional = minecraftClient.getResourceManager().getResource(this.jsonId);
         if (optional.isEmpty()) {
            LOGGER.warn("Font JSON not found: {}", this.jsonId);
            this.loaded.set(true);
            return;
         }

         try (
            InputStream inputStream = ((Resource)optional.get()).getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         ) {
            JsonObject jsonObject = JsonParser.parseReader(inputStreamReader).getAsJsonObject();
            this.performAction_2738(jsonObject);
            this.loaded.set(true);
            LOGGER.info("Loaded font: {} with {} glyphs", this.jsonId, this.glyphs.size());
         }
      } catch (Exception exception) {
         LOGGER.error("Failed to load font: {}", this.jsonId, exception);
         this.loaded.set(true);
      }
   }

   private void performAction_2738(JsonObject jsonObject) {
      float f = 1.0F;
      if (jsonObject.has("atlas")) {
         JsonObject jsonObject1 = jsonObject.getAsJsonObject("atlas");
         this.atlasWidth = this.computeValue_2747(jsonObject1, "width", 512.0F);
         this.atlasHeight = this.computeValue_2747(jsonObject1, "height", 512.0F);
         this.fontSize = this.computeValue_2747(jsonObject1, "size", 32.0F);
         this.distanceRange = this.computeValue_2747(jsonObject1, "distanceRange", 4.0F);
         if (jsonObject1.has("yOrigin")) {
            String string = jsonObject1.get("yOrigin").getAsString();
            this.yOriginBottom = string.equalsIgnoreCase("bottom");
         }

         LOGGER.info(
            "Atlas: {}x{}, size={}, distanceRange={}, yOrigin={}",
            new Object[]{this.atlasWidth, this.atlasHeight, this.fontSize, this.distanceRange, this.yOriginBottom ? "bottom" : "top"}
         );
      }

      if (jsonObject.has("metrics")) {
         JsonObject jsonObject3 = jsonObject.getAsJsonObject("metrics");
         f = this.computeValue_2747(jsonObject3, "emSize", 1.0F);
         float g = this.computeValue_2747(jsonObject3, "lineHeight", 1.2F);
         this.lineHeight = g * this.fontSize;
      }

      if (jsonObject.has("glyphs")) {
         for (JsonElement jsonElement : jsonObject.getAsJsonArray("glyphs")) {
            JsonObject jsonObject2 = jsonElement.getAsJsonObject();
            this.performAction_2746(jsonObject2, f);
         }
      }
   }

   private void performAction_2746(JsonObject jsonObject, float w) {
      int i = -1;
      if (jsonObject.has("unicode")) {
         i = jsonObject.get("unicode").getAsInt();
      } else if (jsonObject.has("char")) {
         String string = jsonObject.get("char").getAsString();
         if (!string.isEmpty()) {
            i = string.codePointAt(0);
         }
      } else if (jsonObject.has("id")) {
         i = jsonObject.get("id").getAsInt();
      }

      if (i >= 0) {
         float r = this.computeValue_2747(jsonObject, "advance", 0.0F) * this.fontSize;
         if (r == 0.0F) {
            r = this.computeValue_2747(jsonObject, "xadvance", 0.0F);
         }

         float f = 0.0F;
         float g = 0.0F;
         float h = 0.0F;
         float j = 0.0F;
         float k = 0.0F;
         float l = 0.0F;
         if (jsonObject.has("atlasBounds")) {
            JsonObject jsonObject1 = jsonObject.getAsJsonObject("atlasBounds");
            float m = this.computeValue_2747(jsonObject1, "left", 0.0F);
            float n = this.computeValue_2747(jsonObject1, "bottom", 0.0F);
            float o = this.computeValue_2747(jsonObject1, "right", 0.0F);
            float p = this.computeValue_2747(jsonObject1, "top", 0.0F);
            f = m;
            h = o - m;
            j = p - n;
            if (this.yOriginBottom) {
               g = this.atlasHeight - p;
            } else {
               g = n;
            }
         } else if (jsonObject.has("x") && jsonObject.has("y") && jsonObject.has("width") && jsonObject.has("height")) {
            f = this.computeValue_2747(jsonObject, "x", 0.0F);
            g = this.computeValue_2747(jsonObject, "y", 0.0F);
            h = this.computeValue_2747(jsonObject, "width", 0.0F);
            j = this.computeValue_2747(jsonObject, "height", 0.0F);
         }

         if (jsonObject.has("planeBounds")) {
            JsonObject jsonObject2 = jsonObject.getAsJsonObject("planeBounds");
            float s = this.computeValue_2747(jsonObject2, "left", 0.0F);
            float t = this.computeValue_2747(jsonObject2, "bottom", 0.0F);
            float u = this.computeValue_2747(jsonObject2, "right", 0.0F);
            float v = this.computeValue_2747(jsonObject2, "top", 0.0F);
            k = s * this.fontSize;
            float q = 0.95F;
            l = (q - v) * this.fontSize;
         } else if (jsonObject.has("xoffset") && jsonObject.has("yoffset")) {
            k = this.computeValue_2747(jsonObject, "xoffset", 0.0F);
            l = this.computeValue_2747(jsonObject, "yoffset", 0.0F);
         }

         this.glyphs.put(i, new PipelineComponent200(i, f, g, h, j, k, l, r, this.atlasWidth, this.atlasHeight));
      }
   }

   private float computeValue_2747(JsonObject jsonObject, String string, float f) {
      return jsonObject.has(string) ? jsonObject.get(string).getAsFloat() : f;
   }

   public PipelineComponent200 computeValue_2745(int i) {
      return this.glyphs.get(i);
   }

   public boolean checkCondition_2748(int i) {
      return this.glyphs.containsKey(i);
   }

   public Identifier getTextureId_2737() {
      return this.textureId;
   }

   public float getFontSize_2742() {
      return this.fontSize;
   }

   public float getLineHeight_2749() {
      return this.lineHeight;
   }

   public float getAtlasWidth_2743() {
      return this.atlasWidth;
   }

   public float getAtlasHeight_2750() {
      return this.atlasHeight;
   }

   public float getDistanceRange_2751() {
      return this.distanceRange;
   }

   public boolean isLoaded_2736() {
      return this.loaded.get();
   }

   public int getGlyphs_2744() {
      return this.glyphs.size();
   }
}
