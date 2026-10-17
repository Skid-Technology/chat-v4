/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MPacketIdFactory;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MCPacketPlayer_PositionRotation
extends Mapping {
    private MappingMethod positionRotationPacketConstructor;

    public MCPacketPlayer_PositionRotation() {
        this(MPacketIdFactory.getPacketMappingControlFlowState());
    }

    private MCPacketPlayer_PositionRotation(GuiComponent[] controlFlowState) {
        super(MappedClasses.FK);
        MappingMethod mappingMethod = ForgeVersion.MC_1_21_11.d() ? this.Y("<init>", false, Void.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE, Boolean.TYPE, Boolean.TYPE) : (this.positionRotationPacketConstructor = ForgeVersion.MC_1_7_10.Y() ? this.Y("<init>", false, Void.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE, Boolean.TYPE) : this.Y("<init>", false, Void.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE, Boolean.TYPE));
        if (GuiComponent.getLegacyComponentState() == null) {
            MPacketIdFactory.setPacketMappingControlFlowState(new GuiComponent[2]);
        }
    }

    public Object createLegacyPositionRotationPacket(double x, double feetY, double stanceY, double z, float yaw, float pitch, boolean onGround) {
        return this.positionRotationPacketConstructor.newInstance(x, feetY, stanceY, z, Float.valueOf(yaw), Float.valueOf(pitch), onGround);
    }

    public Object createPositionRotationPacket(double x, double y, double z, float yaw, float pitch, boolean onGround) {
        return this.positionRotationPacketConstructor.newInstance(x, y, z, Float.valueOf(yaw), Float.valueOf(pitch), onGround);
    }

    public Object createPositionRotationPacket(double x, double y, double z, float yaw, float pitch, boolean onGround, boolean horizontalCollision) {
        return this.positionRotationPacketConstructor.newInstance(x, y, z, Float.valueOf(yaw), Float.valueOf(pitch), onGround, horizontalCollision);
    }
}

