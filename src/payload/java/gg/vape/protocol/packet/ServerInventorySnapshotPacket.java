package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.friend.activity.ActivityItemStackPayload;
import gg.vape.protocol.ZeusPacketBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.jetbrains.annotations.Nullable;

public class ServerInventorySnapshotPacket implements ZeusSerializablePacket {
   private static final String b = "Too many items in inventory";
   private Map<Integer, @Nullable ActivityItemStackPayload> inventoryItems;
   private int heldItemSlot;
   private long userId;

   public ServerInventorySnapshotPacket(UserModel userModel, int heldItemSlot, Map<Integer, @Nullable ActivityItemStackPayload> inventoryItems) {
      this.userId = userModel.getId();
      this.heldItemSlot = heldItemSlot;
      this.inventoryItems = inventoryItems;
   }

   public int getHeldItemSlot() {
      return this.heldItemSlot;
   }

   @Override
   public void o(ZeusPacketBuffer zeusPacketBuffer) {
      zeusPacketBuffer.writeLong(this.userId);
      zeusPacketBuffer.writeVarInt(this.heldItemSlot);
      zeusPacketBuffer.writeVarInt(this.inventoryItems.size());

      for (Entry<Integer, ActivityItemStackPayload> entry : this.inventoryItems.entrySet()) {
         zeusPacketBuffer.writeVarInt(entry.getKey());
         zeusPacketBuffer.writeBoolean(entry.getValue() != null);
         if (entry.getValue() != null) {
            entry.getValue().writeTo(zeusPacketBuffer);
         }
      }
   }

   @Override
   public void S(ZeusPacketBuffer zeusPacketBuffer) {
      this.userId = zeusPacketBuffer.readLong();
      this.heldItemSlot = zeusPacketBuffer.readVarInt();
      int n = zeusPacketBuffer.readVarInt();
      if (n > 40) {
         throw new RuntimeException("Too many items in inventory");
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

   public long getUserId() {
      return this.userId;
   }

   private static RuntimeException a(RuntimeException runtimeException) {
      return runtimeException;
   }

   public ServerInventorySnapshotPacket() {
   }
}
