package gg.vape.protocol.packet;

import gg.vape.friend.activity.ActivityItemStackPayload;
import gg.vape.protocol.ZeusPacketBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.jetbrains.annotations.Nullable;

public class ClientInventoryUpdatePacket implements ZeusSerializablePacket {
   private static final String b = "Too many items in inventory";
   private Map<Integer, @Nullable ActivityItemStackPayload> inventoryItems;
   private static String v;

   public ClientInventoryUpdatePacket() {
   }

   @Override
   public void S(ZeusPacketBuffer zeusPacketBuffer) {
      int n = zeusPacketBuffer.readVarInt();
      if (n > 40) {
         throw new RuntimeException(b);
      } else {
         this.inventoryItems = new HashMap<>();

         for (int i = 0; i < n; i++) {
            int n2 = zeusPacketBuffer.readVarInt();
            boolean bl = zeusPacketBuffer.readBoolean();
            this.inventoryItems.put(n2, bl ? new ActivityItemStackPayload(zeusPacketBuffer) : null);
         }
      }
   }

   public Map<Integer, @Nullable ActivityItemStackPayload> getInventoryItems() {
      return this.inventoryItems;
   }

   public static void d(String string) {
      v = string;
   }

   private static RuntimeException a(RuntimeException runtimeException) {
      return runtimeException;
   }

   public ClientInventoryUpdatePacket(Map<Integer, @Nullable ActivityItemStackPayload> inventoryItems) {
      this.inventoryItems = inventoryItems;
   }

   public static String A() {
      return v;
   }

   @Override
   public void o(ZeusPacketBuffer zeusPacketBuffer) {
      zeusPacketBuffer.writeVarInt(this.inventoryItems.size());

      for (Entry<Integer, ActivityItemStackPayload> entry : this.inventoryItems.entrySet()) {
         zeusPacketBuffer.writeVarInt(entry.getKey());
         zeusPacketBuffer.writeBoolean(entry.getValue() != null);
         if (entry.getValue() != null) {
            entry.getValue().writeTo(zeusPacketBuffer);
         }
      }
   }

   static {
      d("omJQZ");
   }
}
