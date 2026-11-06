package aethereal.render;

public class RenderComponent186 {
   public static final double doubleValueField1 = 1.7015791506357025;
   public static final double doubleValueField2 = 2.5949079670432127;
   public static final double doubleValueField3 = 2.701578217685146;
   public static final double doubleValueField4 = 2.094396299644887;
   public static final double doubleValueField5 = 1.3962634699317127;
   public static final RenderComponent186.Contract372 contract372Field6 = f -> (float)(1.0 - Math.cos(f * 3.1415928959671753 / 2.0));
   public static final RenderComponent186.Contract372 contract372Field7 = f -> (float)Math.sin(f * 3.1415928959671753 / 2.0);
   public static final RenderComponent186.Contract372 contract372Field8 = f -> (float)(-(Math.cos(3.1415928959671753 * f) - 1.0) / 2.0);
   public static final RenderComponent186.Contract372 contract372Field9 = f -> (float)(1.0 - Math.sqrt(1.0 - Math.pow(f, 2.0)));
   public static final RenderComponent186.Contract372 contract372Field10 = f -> (float)Math.sqrt(1.0 - Math.pow(f - 1.0, 2.0));
   public static final RenderComponent186.Contract372 contract372Field11 = f -> (float)(
      f < 0.5 ? (1.0 - Math.sqrt(1.0 - Math.pow(2.0 * f, 2.0))) / 2.0 : (Math.sqrt(1.0 - Math.pow(-2.0 * f + 2.0, 2.0)) + 1.0) / 2.0
   );
   public static final RenderComponent186.Contract372 contract372Field12 = f -> f != 0.0 && f != 1.0
      ? (float)(Math.pow(-2.0, 10.0 * f - 10.0) * Math.sin((f * 10.0 - 10.75) * 2.094396299644887))
      : f;
   public static final RenderComponent186.Contract372 contract372Field13 = f -> f != 0.0 && f != 1.0
      ? (float)(Math.pow(2.0, -10.0 * f) * Math.sin((f * 10.0 - 0.75) * 2.094396299644887) + 1.0)
      : f;
   public static final RenderComponent186.Contract372 contract372Field14 = f -> f != 0.0 && f != 1.0
      ? (float)(
         f < 0.5
            ? -(Math.pow(2.0, 20.0 * f - 10.0) * Math.sin((20.0 * f - 11.125) * 1.3962634699317127)) / 2.0
            : Math.pow(2.0, -20.0 * f + 10.0) * Math.sin((20.0 * f - 11.125) * 1.3962634699317127) / 2.0 + 1.0
      )
      : f;
   public static final RenderComponent186.Contract372 contract372Field15 = f -> f != 0.0 ? (float)Math.pow(2.0, 10.0 * f - 10.0) : f;
   public static final RenderComponent186.Contract372 contract372Field16 = f -> f != 1.0 ? (float)(1.0 - Math.pow(2.0, -10.0 * f)) : f;
   public static final RenderComponent186.Contract372 contract372Field17 = f -> f != 0.0 && f != 1.0
      ? (float)(f < 0.5 ? Math.pow(2.0, 20.0 * f - 10.0) / 2.0 : (2.0 - Math.pow(2.0, -20.0 * f + 10.0)) / 2.0)
      : f;
   public static final RenderComponent186.Contract372 contract372Field18 = f -> (float)(
      2.701578217685146 * Math.pow(f, 3.0) - 1.7015791506357025 * Math.pow(f, 2.0)
   );
   public static final RenderComponent186.Contract372 contract372Field19 = f -> (float)(
      1.0 + 2.701578217685146 * Math.pow(f - 1.0, 3.0) + 1.7015791506357025 * Math.pow(f - 1.0, 2.0)
   );
   public static final RenderComponent186.Contract372 contract372Field20 = f -> f;
   public static final RenderComponent186.Contract372 contract372Field21 = f -> (float)(
      f < 0.5
         ? Math.pow(2.0 * f, 2.0) * (7.189816336825345 * f - 2.5949079670432127) / 2.0
         : (Math.pow(2.0 * f - 2.0, 2.0) * (3.594908086491756 * (f * 2.0 - 2.0) + 2.5949079670432127) + 2.0) / 2.0
   );
   public static final RenderComponent186.Contract372 contract372Field22 = f -> {
      if (f < 0.36363636363636365) {
         return (float)(7.5625 * Math.pow(f, 2.0));
      } else {
         return f < 0.7272727272727273
            ? (float)(7.5625 * Math.pow(f - 0.5454545454545454, 2.0) + 0.75)
            : (float)(
               f < 0.9090909090909091 ? 7.5625 * Math.pow(f - 0.8181818181818182, 2.0) + 0.9375 : 7.5625 * Math.pow(f - 0.9545454545454546, 2.0) + 0.984375
            );
      }
   };
   public static final RenderComponent186.Contract372 contract372Field23 = f -> (float)(1.0 - contract372Field22.ease((float)(1.0 - f)));
   public static final RenderComponent186.Contract372 contract372Field24 = f -> (float)(
      f < 0.5 ? (1.0 - contract372Field22.ease((float)(1.0 - 2.0 * f))) / 2.0 : (1.0 + contract372Field22.ease((float)(2.0 * f - 1.0))) / 2.0
   );
   public static final RenderComponent186.Contract372 contract372Field25 = f -> f < 0.5
      ? 16.0F * f * f * f * f * f
      : (float)(1.0 - Math.pow(-2.0F * f + 2.0F, 5.0) / 2.0);

   @FunctionalInterface
   public interface Contract372 {
      float ease(float f);
   }
}
