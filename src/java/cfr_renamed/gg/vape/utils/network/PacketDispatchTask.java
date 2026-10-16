/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.network;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.utils.network.PacketDispatchMarkerRegistry;
import gg.vape.wrapper.impl.NetworkManager;
import gg.vape.wrapper.impl.NetworkPacketHandle;
import gg.vape.wrapper.impl.Packet;

public class PacketDispatchTask {
    private Packet var_gg_vape_wrapper_impl_Packet_n;
    private NetworkManager var_gg_vape_wrapper_impl_NetworkManager_h;
    private boolean var_boolean_N;

    public void t() {
        block6: {
            if (this.A()) {
                if (PacketDispatchMarkerRegistry.S(this.var_gg_vape_wrapper_impl_Packet_n)) {
                    PacketDispatchMarkerRegistry.I(this.var_gg_vape_wrapper_impl_Packet_n);
                    return;
                }
                PacketDispatchMarkerRegistry.u(this.var_gg_vape_wrapper_impl_Packet_n);
                NetworkPacketHandle networkPacketHandle = this.var_gg_vape_wrapper_impl_NetworkManager_h.c();
                if (!networkPacketHandle.isInstance(MappedClasses.F1)) {
                    return;
                }
                this.var_gg_vape_wrapper_impl_NetworkManager_h.G(this.var_gg_vape_wrapper_impl_Packet_n);
            } else {
                try {
                    this.var_gg_vape_wrapper_impl_Packet_n.processPacket(this.var_gg_vape_wrapper_impl_NetworkManager_h.c());
                }
                catch (Exception exception) {
                    if (!this.A()) break block6;
                    Vape.logThrowable(exception);
                }
            }
        }
    }

    public boolean A() {
        return this.var_boolean_N;
    }

    public Packet s() {
        return this.var_gg_vape_wrapper_impl_Packet_n;
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public boolean equals(Object object) {
        if (object instanceof PacketDispatchTask) {
            PacketDispatchTask packetDispatchTask = (PacketDispatchTask)object;
            return packetDispatchTask.s().equals(this.var_gg_vape_wrapper_impl_Packet_n) && packetDispatchTask.A() == this.var_boolean_N;
        }
        if (object instanceof Packet) {
            return this.var_gg_vape_wrapper_impl_Packet_n.equals(object);
        }
        return super.equals(object);
    }

    public PacketDispatchTask(Packet packet, boolean bl, NetworkManager networkManager) {
        this.var_gg_vape_wrapper_impl_Packet_n = packet;
        this.var_boolean_N = bl;
        this.var_gg_vape_wrapper_impl_NetworkManager_h = networkManager;
    }
}

