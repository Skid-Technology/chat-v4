package gg.vape.unmap;

import java.awt.Color;

class ColorContrastUtil {
   private static final int MIN_ALPHA_SEARCH_PRECISION = 1;
   private static final double XYZ_WHITE_REFERENCE_Y = 100.0;
   private static final ThreadLocal<double[]> TEMPORARY_XYZ_ARRAY = new ThreadLocal<>();
   private static final double XYZ_WHITE_REFERENCE_Z = 108.883;
   private static final double XYZ_WHITE_REFERENCE_X = 95.047;
   private static final double XYZ_KAPPA = 903.3;
   private static final int LEGACY_CONSTANT;
   private static final double XYZ_EPSILON = 0.008856;

   private static double pivotXyzComponent(double component) {
      return component > 0.008856 ? Math.pow(component, 0.3333333333333333) : (903.3 * component + 16.0) / 116.0;
   }

   public static void colorToLab(int colorRgb, double[] outLab) {
      Color color = new Color(colorRgb);
      rgbToLab(color.getRed(), color.getGreen(), color.getBlue(), outLab);
   }

   public static int xyzToColor(double x, double y, double z) {
      double linearRed = (x * 3.2406 + y * -1.5372 + z * -0.4986) / 100.0;
      double linearGreen = (x * -0.9689 + y * 1.8758 + z * 0.0415) / 100.0;
      double linearBlue = (x * 0.0557 + y * -0.204 + z * 1.057) / 100.0;
      linearRed = linearRed > 0.0031308 ? 1.055 * Math.pow(linearRed, 0.4166666666666667) - 0.055 : 12.92 * linearRed;
      linearGreen = linearGreen > 0.0031308 ? 1.055 * Math.pow(linearGreen, 0.4166666666666667) - 0.055 : 12.92 * linearGreen;
      linearBlue = linearBlue > 0.0031308 ? 1.055 * Math.pow(linearBlue, 0.4166666666666667) - 0.055 : 12.92 * linearBlue;
      return new Color(
            clampInt((int)Math.round(linearRed * 255.0), 0, 255),
            clampInt((int)Math.round(linearGreen * 255.0), 0, 255),
            clampInt((int)Math.round(linearBlue * 255.0), 0, 255)
         )
         .getRGB();
   }

   public static int hslToColor(float[] hsl) {
      float hue = hsl[0];
      float saturation = hsl[1];
      float lightness = hsl[2];
      float chroma = (1.0F - Math.abs(2.0F * lightness - 1.0F)) * saturation;
      float match = lightness - 0.5F * chroma;
      float secondComponent = chroma * (1.0F - Math.abs(hue / 60.0F % 2.0F - 1.0F));
      int hueSector = (int)hue / 60;
      int red = 0;
      int green = 0;
      int blue = 0;
      switch (hueSector) {
         case 0:
            red = Math.round(255.0F * (chroma + match));
            green = Math.round(255.0F * (secondComponent + match));
            blue = Math.round(255.0F * match);
            break;
         case 1:
            red = Math.round(255.0F * (secondComponent + match));
            green = Math.round(255.0F * (chroma + match));
            blue = Math.round(255.0F * match);
            break;
         case 2:
            red = Math.round(255.0F * match);
            green = Math.round(255.0F * (chroma + match));
            blue = Math.round(255.0F * (secondComponent + match));
            break;
         case 3:
            red = Math.round(255.0F * match);
            green = Math.round(255.0F * (secondComponent + match));
            blue = Math.round(255.0F * (chroma + match));
            break;
         case 4:
            red = Math.round(255.0F * (secondComponent + match));
            green = Math.round(255.0F * match);
            blue = Math.round(255.0F * (chroma + match));
            break;
         case 5:
         case 6:
            red = Math.round(255.0F * (chroma + match));
            green = Math.round(255.0F * match);
            blue = Math.round(255.0F * (secondComponent + match));
      }

      red = clampInt(red, 0, 255);
      green = clampInt(green, 0, 255);
      blue = clampInt(blue, 0, 255);
      return new Color(red, green, blue).getRGB();
   }

   public static void rgbToXyz(int redChannel, int greenChannel, int blueChannel, double[] outXyz) {
      if (outXyz.length != 3) {
         throw new IllegalArgumentException("outXyz must have a length of 3.");
      } else {
         double red = redChannel / 255.0;
         red = red < 0.04045 ? red / 12.92 : Math.pow((red + 0.055) / 1.055, 2.4);
         double green = greenChannel / 255.0;
         green = green < 0.04045 ? green / 12.92 : Math.pow((green + 0.055) / 1.055, 2.4);
         double blue = blueChannel / 255.0;
         blue = blue < 0.04045 ? blue / 12.92 : Math.pow((blue + 0.055) / 1.055, 2.4);
         outXyz[0] = 100.0 * (red * 0.4124 + green * 0.3576 + blue * 0.1805);
         outXyz[1] = 100.0 * (red * 0.2126 + green * 0.7152 + blue * 0.0722);
         outXyz[2] = 100.0 * (red * 0.0193 + green * 0.1192 + blue * 0.9505);
      }
   }

   private static float constrain(float value, float min, float max) {
      return value < min ? min : (value > max ? max : value);
   }

   public static void colorToXyz(int colorRgb, double[] outXyz) {
      Color color = new Color(colorRgb);
      rgbToXyz(color.getRed(), color.getGreen(), color.getBlue(), outXyz);
   }

   private ColorContrastUtil() {
   }

   public static double calculateContrast(int foregroundRgb, int backgroundRgb) {
      foregroundRgb = compositeColors(foregroundRgb, backgroundRgb);
      double foregroundLuminance = calculateLuminance(foregroundRgb) + 0.05;
      double backgroundLuminance = calculateLuminance(backgroundRgb) + 0.05;
      return Math.max(foregroundLuminance, backgroundLuminance) / Math.min(foregroundLuminance, backgroundLuminance);
   }

   private static int clampInt(int value, int min, int max) {
      return value < min ? min : (value > max ? max : value);
   }

   public static void xyzToLab(double x, double y, double z, double[] outLab) {
      if (outLab.length != 3) {
         throw new IllegalArgumentException("outLab must have a length of 3.");
      } else {
         double xComponent = pivotXyzComponent(x / 95.047);
         double yComponent = pivotXyzComponent(y / 100.0);
         double zComponent = pivotXyzComponent(z / 108.883);
         outLab[0] = Math.max(0.0, 116.0 * yComponent - 16.0);
         outLab[1] = 500.0 * (xComponent - yComponent);
         outLab[2] = 200.0 * (yComponent - zComponent);
      }
   }

   public static void rgbToLab(int red, int green, int blue, double[] outLab) {
      rgbToXyz(red, green, blue, outLab);
      xyzToLab(outLab[0], outLab[1], outLab[2], outLab);
   }

   public static void colorToHsl(int colorRgb, float[] outHsl) {
      Color color = new Color(colorRgb);
      rgbToHsl(color.getRed(), color.getGreen(), color.getBlue(), outHsl);
   }

   public static void rgbToHsl(int redChannel, int greenChannel, int blueChannel, float[] outHsl) {
      float red = redChannel / 255.0F;
      float green = greenChannel / 255.0F;
      float blue = blueChannel / 255.0F;
      float maxChannel = Math.max(red, Math.max(green, blue));
      float minChannel = Math.min(red, Math.min(green, blue));
      float chroma = maxChannel - minChannel;
      float lightness = (maxChannel + minChannel) / 2.0F;
      float hue;
      float saturation;
      if (maxChannel == minChannel) {
         saturation = 0.0F;
         hue = 0.0F;
      } else {
         hue = maxChannel == red ? (green - blue) / chroma % 6.0F : (maxChannel == green ? (blue - red) / chroma + 2.0F : (red - green) / chroma + 4.0F);
         saturation = chroma / (1.0F - Math.abs(2.0F * lightness - 1.0F));
      }

      hue = hue * 60.0F % 360.0F;
      if (hue < 0.0F) {
         hue += 360.0F;
      }

      outHsl[0] = constrain(hue, 0.0F, 360.0F);
      outHsl[1] = constrain(saturation, 0.0F, 1.0F);
      outHsl[2] = constrain(lightness, 0.0F, 1.0F);
   }

   public static void labToXyz(double lightness, double aComponent, double bComponent, double[] outXyz) {
      double yIntermediate = (lightness + 16.0) / 116.0;
      double xIntermediate = aComponent / 500.0 + yIntermediate;
      double zIntermediate = yIntermediate - bComponent / 200.0;
      double xCube = Math.pow(xIntermediate, 3.0);
      double x = xCube > 0.008856 ? xCube : (116.0 * xIntermediate - 16.0) / 903.3;
      double y = lightness > 7.9996247999999985 ? Math.pow(yIntermediate, 3.0) : lightness / 903.3;
      double zCube = Math.pow(zIntermediate, 3.0);
      double z = zCube > 0.008856 ? zCube : (116.0 * zIntermediate - 16.0) / 903.3;
      outXyz[0] = x * 95.047;
      outXyz[1] = y * 100.0;
      outXyz[2] = z * 108.883;
   }

   public static int labToColor(double lightness, double aComponent, double bComponent) {
      double[] xyz = getTemporaryXyzArray();
      labToXyz(lightness, aComponent, bComponent, xyz);
      return xyzToColor(xyz[0], xyz[1], xyz[2]);
   }

   private static IllegalArgumentException propagateIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
      return illegalArgumentException;
   }

   public static double[] getTemporaryXyzArray() {
      double[] dArray = TEMPORARY_XYZ_ARRAY.get();
      if (dArray == null) {
         dArray = new double[3];
         TEMPORARY_XYZ_ARRAY.set(dArray);
      }

      return dArray;
   }

   public static int compositeColors(int foregroundRgb, int backgroundRgb) {
      int foregroundAlpha = 255;
      int backgroundAlpha = 255;
      int compositeAlpha = compositeAlpha(foregroundAlpha, backgroundAlpha);
      Color color = new Color(foregroundRgb);
      Color color2 = new Color(backgroundRgb);
      int red = compositeComponent(color.getRed(), foregroundAlpha, color2.getRed(), backgroundAlpha, compositeAlpha);
      int green = compositeComponent(color.getGreen(), foregroundAlpha, color2.getGreen(), backgroundAlpha, compositeAlpha);
      int blue = compositeComponent(color.getBlue(), foregroundAlpha, color2.getBlue(), backgroundAlpha, compositeAlpha);
      return new Color(red, green, blue).getRGB();
   }

   private static int compositeComponent(int foreground, int foregroundAlpha, int background, int backgroundAlpha, int compositeAlpha) {
      return compositeAlpha == 0 ? 0 : (255 * foreground * foregroundAlpha + background * backgroundAlpha * (255 - foregroundAlpha)) / (compositeAlpha * 255);
   }

   private static int compositeAlpha(int foregroundAlpha, int backgroundAlpha) {
      return 255 - (255 - backgroundAlpha) * (255 - foregroundAlpha) / 255;
   }

   public static double calculateLuminance(int colorRgb) {
      double[] dArray = getTemporaryXyzArray();
      colorToXyz(colorRgb, dArray);
      return dArray[1] / 100.0;
   }

   static {
      long legacyConstantSeed = 8524902361867485194L;
      LEGACY_CONSTANT = (int)legacyConstantSeed;
   }
}
