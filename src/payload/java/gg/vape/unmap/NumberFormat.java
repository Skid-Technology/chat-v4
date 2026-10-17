package gg.vape.unmap;

public class NumberFormat {
   private final int precision;

   public NumberFormat(int precision) {
      if (precision <= 0) {
         precision = -1;
      }

      this.precision = precision;
   }

   public NumberFormat(String pattern) {
      this(pattern.length() - (pattern.indexOf(".") + 1));
   }

   public double truncate(Double value) {
      return this.truncate(value.doubleValue());
   }

   public double truncate(double value) {
      return Double.valueOf(this.format(value));
   }

   public String format(Double value) {
      return this.format(value.doubleValue());
   }

   public String format(double value) {
      String text = String.valueOf(value).replaceAll(",", ".");
      if (text.contains("E")) {
         return text;
      } else if (text.contains(".")) {
         int endIndex = Math.min(text.indexOf(46) + this.precision + 1, text.length());
         return text.substring(0, endIndex);
      } else {
         return text;
      }
   }

   public int getPrecision() {
      return this.precision;
   }
}
