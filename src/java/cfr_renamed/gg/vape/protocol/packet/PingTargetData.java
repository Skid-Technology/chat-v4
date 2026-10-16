/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.PingTargetKind;
import org.jetbrains.annotations.Nullable;

public class PingTargetData {
    @Nullable
    private Double var_java_lang_Double_A;
    private final PingTargetKind var_gg_vape_protocol_packet_PingTargetKind_r;
    @Nullable
    private Long var_java_lang_Long_w;
    @Nullable
    private Double var_java_lang_Double_M;
    @Nullable
    private Integer var_java_lang_Integer_z;
    @Nullable
    private Double var_java_lang_Double_P;
    final static boolean var_boolean_b;
    @Nullable
    private Integer var_java_lang_Integer_f;
    @Nullable
    private Integer var_java_lang_Integer_Z;
    @Nullable
    private Integer var_java_lang_Integer_o;

    PingTargetData(int n, int n2, int n3) {
        this.var_gg_vape_protocol_packet_PingTargetKind_r = PingTargetKind.BLOCK;
        this.var_java_lang_Integer_o = n;
        this.var_java_lang_Integer_z = n2;
        this.var_java_lang_Integer_f = n3;
    }

    @Nullable
    public Double W() {
        return this.var_java_lang_Double_P;
    }

    @Nullable
    public Integer u() {
        return this.var_java_lang_Integer_z;
    }

    @Nullable
    public Long t() {
        return this.var_java_lang_Long_w;
    }

    PingTargetData(@Nullable Long l, int n, double d, double d2, double d3) {
        this.var_gg_vape_protocol_packet_PingTargetKind_r = PingTargetKind.ENTITY;
        this.var_java_lang_Long_w = l;
        this.var_java_lang_Integer_Z = n;
        this.var_java_lang_Double_P = d;
        this.var_java_lang_Double_A = d2;
        this.var_java_lang_Double_M = d3;
    }

    @Nullable
    public Double w() {
        return this.var_java_lang_Double_A;
    }

    public static PingTargetData a(@Nullable Long l, int n, double d, double d2, double d3) {
        return new PingTargetData(l, n, d, d2, d3);
    }

    @Nullable
    public Integer e() {
        return this.var_java_lang_Integer_Z;
    }

    public static PingTargetData C(double d, double d2, double d3) {
        return new PingTargetData(d, d2, d3);
    }

    public PingTargetData(ZeusPacketBuffer zeusPacketBuffer) {
        this.var_gg_vape_protocol_packet_PingTargetKind_r = zeusPacketBuffer.readEnum(PingTargetKind.class);
        if (this.var_gg_vape_protocol_packet_PingTargetKind_r == PingTargetKind.POSITION) {
            this.var_java_lang_Double_P = zeusPacketBuffer.readDouble();
            this.var_java_lang_Double_A = zeusPacketBuffer.readDouble();
            this.var_java_lang_Double_M = zeusPacketBuffer.readDouble();
        } else if (this.var_gg_vape_protocol_packet_PingTargetKind_r == PingTargetKind.BLOCK) {
            this.var_java_lang_Integer_o = zeusPacketBuffer.readInt();
            this.var_java_lang_Integer_z = zeusPacketBuffer.readInt();
            this.var_java_lang_Integer_f = zeusPacketBuffer.readInt();
        } else if (this.var_gg_vape_protocol_packet_PingTargetKind_r == PingTargetKind.ENTITY) {
            this.var_java_lang_Integer_Z = zeusPacketBuffer.readInt();
            this.var_java_lang_Long_w = zeusPacketBuffer.readLong();
            if (this.var_java_lang_Long_w == -1L) {
                this.var_java_lang_Long_w = null;
            }
            this.var_java_lang_Double_P = zeusPacketBuffer.readDouble();
            this.var_java_lang_Double_A = zeusPacketBuffer.readDouble();
            this.var_java_lang_Double_M = zeusPacketBuffer.readDouble();
        }
    }

    @Nullable
    public Integer y() {
        return this.var_java_lang_Integer_o;
    }

    @Nullable
    public Integer r() {
        return this.var_java_lang_Integer_f;
    }

    public void n(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeEnum(this.var_gg_vape_protocol_packet_PingTargetKind_r);
        if (this.var_gg_vape_protocol_packet_PingTargetKind_r == PingTargetKind.POSITION) {
            if (!(var_boolean_b || this.var_java_lang_Double_P != null && this.var_java_lang_Double_A != null && this.var_java_lang_Double_M != null)) {
                throw new AssertionError();
            }
            zeusPacketBuffer.writeDouble(this.var_java_lang_Double_P);
            zeusPacketBuffer.writeDouble(this.var_java_lang_Double_A);
            zeusPacketBuffer.writeDouble(this.var_java_lang_Double_M);
        } else if (this.var_gg_vape_protocol_packet_PingTargetKind_r == PingTargetKind.BLOCK) {
            if (!(var_boolean_b || this.var_java_lang_Integer_o != null && this.var_java_lang_Integer_z != null && this.var_java_lang_Integer_f != null)) {
                throw new AssertionError();
            }
            zeusPacketBuffer.writeInt(this.var_java_lang_Integer_o);
            zeusPacketBuffer.writeInt(this.var_java_lang_Integer_z);
            zeusPacketBuffer.writeInt(this.var_java_lang_Integer_f);
        } else if (this.var_gg_vape_protocol_packet_PingTargetKind_r == PingTargetKind.ENTITY) {
            if (!(var_boolean_b || this.var_java_lang_Integer_Z != null && this.var_java_lang_Double_P != null && this.var_java_lang_Double_A != null && this.var_java_lang_Double_M != null)) {
                throw new AssertionError();
            }
            zeusPacketBuffer.writeInt(this.var_java_lang_Integer_Z);
            ZeusPacketBuffer zeusPacketBuffer2 = zeusPacketBuffer;
            long l = this.var_java_lang_Long_w != null ? this.var_java_lang_Long_w : -1L;
            zeusPacketBuffer2.writeLong(l);
            zeusPacketBuffer.writeDouble(this.var_java_lang_Double_P);
            zeusPacketBuffer.writeDouble(this.var_java_lang_Double_A);
            zeusPacketBuffer.writeDouble(this.var_java_lang_Double_M);
        }
    }

    @Nullable
    public Double L() {
        return this.var_java_lang_Double_M;
    }

    public PingTargetKind K() {
        return this.var_gg_vape_protocol_packet_PingTargetKind_r;
    }

    public static PingTargetData Y(int n, int n2, int n3) {
        return new PingTargetData(n, n2, n3);
    }

    PingTargetData(double d, double d2, double d3) {
        this.var_gg_vape_protocol_packet_PingTargetKind_r = PingTargetKind.POSITION;
        this.var_java_lang_Double_P = d;
        this.var_java_lang_Double_A = d2;
        this.var_java_lang_Double_M = d3;
    }

    static {
        boolean bl = !PingTargetData.class.desiredAssertionStatus();
        var_boolean_b = bl;
    }
}

