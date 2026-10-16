/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.Vape;
import gg.vape.wrapper.impl.Packet;

public class ShortStatePacketBridge
extends Packet {
    public short getTransactionId() {
        return Vape.INSTANCE.getMappingsMapperCompat().var_gg_vape_mapping_mappings_MShortPacketFieldBridge_F.getTransactionId(this.I);
    }

    public ShortStatePacketBridge(Object handle) {
        super(handle);
    }
}

