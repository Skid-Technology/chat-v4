/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.network;

import gg.vape.utils.network.PacketDispatchTask;
import gg.vape.utils.network.TimedPacketDispatchTaskMarker;

public class TimedPacketDispatchTask {
    private final PacketDispatchTask var_gg_vape_utils_network_PacketDispatchTask_X;
    private final long var_long_M;

    private TimedPacketDispatchTask(PacketDispatchTask vo_22) {
        this.var_gg_vape_utils_network_PacketDispatchTask_X = vo_22;
        this.var_long_M = System.currentTimeMillis();
    }

    public PacketDispatchTask z() {
        return this.var_gg_vape_utils_network_PacketDispatchTask_X;
    }

    public TimedPacketDispatchTask(PacketDispatchTask vo_22, TimedPacketDispatchTaskMarker u_02) {
        this(vo_22);
    }

    public boolean j(long l) {
        long l2 = System.currentTimeMillis();
        boolean bl = l2 - this.var_long_M > l;
        return bl;
    }
}

