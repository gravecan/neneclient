package platform.client.processors.draw.fonts;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public final class FontsComponent286 {
   private FontsComponent286.NestedType412 nestedType412Field1;
   private FontsComponent286.NestedType416 nestedType416Field2;
   private List<FontsComponent286.NestedType414> listField3;
   @SerializedName("kerning")
   private List<FontsComponent286.NestedType415> listField4;

   public FontsComponent286.NestedType412 getNestedType412Field1_3624() {
      return this.nestedType412Field1;
   }

   public FontsComponent286.NestedType416 getNestedType416Field2_3625() {
      return this.nestedType416Field2;
   }

   public List<FontsComponent286.NestedType414> getListField3_3626() {
      return this.listField3;
   }

   public List<FontsComponent286.NestedType415> getListField4_3623() {
      return this.listField4;
   }

   public static final class NestedType412 {
      @SerializedName("distanceRange")
      private final float range;
      private final float width;
      private final float height;

      public NestedType412(float f, float g, float h) {
         this.range = f;
         this.width = g;
         this.height = h;
      }

      @SerializedName("distanceRange")
      public float getRange_3629() {
         return this.range;
      }

      public float getWidth_3628() {
         return this.width;
      }

      public float getHeight_3627() {
         return this.height;
      }
   }

   public static final class NestedType413 {
      private final float left;
      private final float top;
      private final float right;
      private final float bottom;

      public NestedType413(float f, float g, float h, float i) {
         this.left = f;
         this.top = g;
         this.right = h;
         this.bottom = i;
      }

      public float getLeft_3630() {
         return this.left;
      }

      public float getTop_3631() {
         return this.top;
      }

      public float getRight_3632() {
         return this.right;
      }

      public float getBottom_3633() {
         return this.bottom;
      }
   }

   public static final class NestedType414 {
      private final int unicode;
      private final float advance;
      private final FontsComponent286.NestedType413 planeBounds;
      private final FontsComponent286.NestedType413 atlasBounds;

      public NestedType414(int i, float f, FontsComponent286.NestedType413 nestedType413, FontsComponent286.NestedType413 nestedType4131) {
         this.unicode = i;
         this.advance = f;
         this.planeBounds = nestedType413;
         this.atlasBounds = nestedType4131;
      }

      public int getUnicode_3637() {
         return this.unicode;
      }

      public float getAdvance_3635() {
         return this.advance;
      }

      public FontsComponent286.NestedType413 getPlaneBounds_3636() {
         return this.planeBounds;
      }

      public FontsComponent286.NestedType413 getAtlasBounds_3634() {
         return this.atlasBounds;
      }
   }

   public static final class NestedType415 {
      @SerializedName("unicode1")
      private final int leftChar;
      @SerializedName("unicode2")
      private final int rightChar;
      private final float advance;

      public NestedType415(int i, int j, float f) {
         this.leftChar = i;
         this.rightChar = j;
         this.advance = f;
      }

      @SerializedName("unicode1")
      public int getLeftChar_3639() {
         return this.leftChar;
      }

      @SerializedName("unicode2")
      public int getRightChar_3640() {
         return this.rightChar;
      }

      public float getAdvance_3638() {
         return this.advance;
      }
   }

   public static final class NestedType416 {
      private float lineHeight;
      private float floatValueField1;
      private float floatValueField2;

      public float getLineHeight_3641() {
         return this.lineHeight;
      }

      public float getFloatValueField1_3642() {
         return this.floatValueField1;
      }

      public float getFloatValueField2_3643() {
         return this.floatValueField2;
      }

      public float computeValue_3644() {
         return this.lineHeight + this.floatValueField2;
      }
   }
}
