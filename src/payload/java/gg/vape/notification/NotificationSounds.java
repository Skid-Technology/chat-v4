package gg.vape.notification;

public class NotificationSounds {
   public static final SoundClip PARTY_INVITE;
   public static final SoundClip MESSAGE_RECEIVED;
   public static final SoundClip PING;

   private static String decodeUtf8(byte[] bytes) {
      int outputIndex = 0;
      int length = bytes.length;
      char[] characters = new char[length];

      for (int inputIndex = 0; inputIndex < length; inputIndex++) {
         int value = 255 & bytes[inputIndex];
         if (value < 192) {
            characters[outputIndex++] = (char)value;
         } else if (value < 224) {
            char decoded = (char)((char)(value & 31) << 6);
            int var12 = bytes[++inputIndex];
            decoded = (char)(decoded | (char)(var12 & 63));
            characters[outputIndex++] = decoded;
         } else if (inputIndex < length - 2) {
            char decoded = (char)((char)(value & 15) << '\f');
            int var13 = bytes[++inputIndex];
            decoded = (char)(decoded | (char)(var13 & 63) << 6);
            var13 = bytes[++inputIndex];
            decoded = (char)(decoded | (char)(var13 & 63));
            characters[outputIndex++] = decoded;
         }
      }

      return new String(characters, 0, outputIndex);
   }

   static {
      try {
         String[] resourceNames = new String[]{"party_invite", "ping", "message_rec"};
         PING = new SoundClip(resourceNames[1]);
         PARTY_INVITE = new SoundClip(resourceNames[0]);
         MESSAGE_RECEIVED = new SoundClip(resourceNames[2]);
      } catch (Exception var1) {
         throw new ExceptionInInitializerError(var1);
      }
   }
}
