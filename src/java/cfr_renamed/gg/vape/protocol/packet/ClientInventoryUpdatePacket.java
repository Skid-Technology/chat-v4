/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.protocol.packet;

import gg.vape.friend.activity.ActivityItemStackPayload;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

public class ClientInventoryUpdatePacket
implements ZeusSerializablePacket {
    private final static String var_java_lang_String_b;
    private Map<Integer, @Nullable ActivityItemStackPayload> inventoryItems;
    private static String var_java_lang_String_v;

    public ClientInventoryUpdatePacket() {
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        int n = zeusPacketBuffer.readVarInt();
        if (n > 40) {
            throw new RuntimeException(var_java_lang_String_b);
        }
        this.inventoryItems = new HashMap<Integer, ActivityItemStackPayload>();
        for (int i = 0; i < n; ++i) {
            int n2 = zeusPacketBuffer.readVarInt();
            boolean bl = zeusPacketBuffer.readBoolean();
            this.inventoryItems.put(n2, bl ? new ActivityItemStackPayload(zeusPacketBuffer) : null);
        }
    }

    public Map<Integer, @Nullable ActivityItemStackPayload> getInventoryItems() {
        return this.inventoryItems;
    }

    public static void d(String string) {
        var_java_lang_String_v = string;
    }

    private static RuntimeException a(RuntimeException runtimeException) {
        return runtimeException;
    }

    public ClientInventoryUpdatePacket(Map<Integer, @Nullable ActivityItemStackPayload> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }

    public static String A() {
        return var_java_lang_String_v;
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeVarInt(this.inventoryItems.size());
        for (Map.Entry<Integer, ActivityItemStackPayload> entry : this.inventoryItems.entrySet()) {
            zeusPacketBuffer.writeVarInt(entry.getKey());
            zeusPacketBuffer.writeBoolean(entry.getValue() != null);
            if (entry.getValue() == null) continue;
            entry.getValue().writeTo(zeusPacketBuffer);
        }
    }

    static {
        ClientInventoryUpdatePacket.d("omJQZ");
        var_java_lang_String_b = "Too many items in inventory";
    }
}

