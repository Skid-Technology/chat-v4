package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import java.util.function.BiConsumer;
import java.util.function.Function;

public enum GroupOption {
   OPEN_INVITES(Boolean.class, false, GroupOption::lambda$static$0, ZeusPacketBuffer::readBoolean);

   private static final GroupOption[] valuesCache = new GroupOption[]{OPEN_INVITES};
   private final Class<?> valueType;
   private final BiConsumer<ZeusPacketBuffer, Object> valueWriter;
   private final Function<ZeusPacketBuffer, Object> valueReader;
   private final Object defaultValue;

   public BiConsumer<ZeusPacketBuffer, Object> getValueWriter() {
      return this.valueWriter;
   }

   public Class<?> getValueType() {
      return this.valueType;
   }

   private static String a(byte[] byArray) {
      int n = 0;
      int n2 = byArray.length;
      char[] cArray = new char[n2];

      for (int i = 0; i < n2; i++) {
         int n3 = 255 & byArray[i];
         if (n3 < 192) {
            cArray[n++] = (char)n3;
         } else if (n3 < 224) {
            char c = (char)((char)(n3 & 31) << 6);
            int var12 = byArray[++i];
            c = (char)(c | (char)(var12 & 63));
            cArray[n++] = c;
         } else if (i < n2 - 2) {
            char c = (char)((char)(n3 & 15) << '\f');
            int var13 = byArray[++i];
            c = (char)(c | (char)(var13 & 63) << 6);
            var13 = byArray[++i];
            c = (char)(c | (char)(var13 & 63));
            cArray[n++] = c;
         }
      }

      return new String(cArray, 0, n);
   }

   public Object getDefaultValue() {
      return this.defaultValue;
   }

   private GroupOption(
      Class<?> valueType, Object defaultValue, BiConsumer<ZeusPacketBuffer, Object> valueWriter, Function<ZeusPacketBuffer, Object> valueReader
   ) {
      this.valueType = valueType;
      this.defaultValue = defaultValue;
      this.valueWriter = valueWriter;
      this.valueReader = valueReader;
   }

   public Function<ZeusPacketBuffer, Object> getValueReader() {
      return this.valueReader;
   }

   private static void lambda$static$0(ZeusPacketBuffer zeusPacketBuffer, Object object) {
      zeusPacketBuffer.writeBoolean((Boolean)object);
   }

   static {
      String string = "OPEN_INVITES";
   }
}
