package gg.vape.friend;

public class TargetEntry {
   private static int[] obfuscationState;
   private final TargetType type;

   public TargetType getType() {
      return this.type;
   }

   public static void setTargetObfuscationState(int[] state) {
      obfuscationState = state;
   }

   public static int[] getTargetObfuscationState() {
      return obfuscationState;
   }

   public TargetEntry(TargetType targetType) {
      this.type = targetType;
   }

   static {
      if (getTargetObfuscationState() == null) {
         setTargetObfuscationState(new int[4]);
      }
   }
}
