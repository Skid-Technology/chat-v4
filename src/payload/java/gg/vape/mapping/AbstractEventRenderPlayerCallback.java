package gg.vape.mapping;

public class AbstractEventRenderPlayerCallback extends InsertedCallbackMarker {
   private static String s;

   private static boolean isPlayerInstance(Object object) {
      return MappedClasses.Yl.isInstance(object) || MappedClasses.z5.isInstance(object);
   }

   public static String n() {
      return s;
   }

   public static void D(String string) {
      s = string;
   }

   public static boolean access$000(Object object) {
      return isPlayerInstance(object);
   }

   static {
      if (n() == null) {
         D("LTsfgb");
      }
   }
}
