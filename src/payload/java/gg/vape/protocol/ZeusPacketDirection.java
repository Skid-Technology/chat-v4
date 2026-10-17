package gg.vape.protocol;

public enum ZeusPacketDirection {
   CLIENT,
   SERVER;

   private static final ZeusPacketDirection[] P;
   private static String[] Q;

   public static void U(String[] stringArray) {
      Q = stringArray;
   }

   public static String[] F() {
      return Q;
   }

   static {
      if (F() != null) {
         U(new String[5]);
      }

      String[] var10000 = new String[]{"CLIENT", "SERVER"};
      P = new ZeusPacketDirection[]{CLIENT, SERVER};
   }
}
