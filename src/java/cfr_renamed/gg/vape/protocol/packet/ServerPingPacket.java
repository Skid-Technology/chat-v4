/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.PingAudience;
import gg.vape.protocol.packet.PingTargetData;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ServerPingPacket
implements ZeusSerializablePacket {
    private PingTargetData var_gg_vape_protocol_packet_PingTargetData_c;
    private PingAudience var_gg_vape_protocol_packet_PingAudience_n;
    private long var_long_C;
    private static int var_int_A;

    public static int q() {
        int n = ServerPingPacket.D();
        if (n == 0) {
            return 28;
        }
        return 0;
    }

    public ServerPingPacket() {
    }

    public ServerPingPacket(PingAudience pingAudience, long l, PingTargetData pingTargetData) {
        this.var_gg_vape_protocol_packet_PingAudience_n = pingAudience;
        this.var_long_C = l;
        this.var_gg_vape_protocol_packet_PingTargetData_c = pingTargetData;
    }

    public long s() {
        return this.var_long_C;
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.var_gg_vape_protocol_packet_PingAudience_n = zeusPacketBuffer.readEnum(PingAudience.class);
        this.var_long_C = zeusPacketBuffer.readLong();
        this.var_gg_vape_protocol_packet_PingTargetData_c = new PingTargetData(zeusPacketBuffer);
    }

    public static void s(int n) {
        var_int_A = n;
    }

    public static int D() {
        return var_int_A;
    }

    public PingTargetData m() {
        return this.var_gg_vape_protocol_packet_PingTargetData_c;
    }

    public PingAudience R() {
        return this.var_gg_vape_protocol_packet_PingAudience_n;
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeEnum(this.var_gg_vape_protocol_packet_PingAudience_n);
        zeusPacketBuffer.writeLong(this.var_long_C);
        this.var_gg_vape_protocol_packet_PingTargetData_c.n(zeusPacketBuffer);
    }

    static {
        if (ServerPingPacket.q() != 0) {
            ServerPingPacket.s(84);
        }
    }
}

