/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;

public class MS08PacketPlayerPosLook
extends Mapping {
    private static String[] mappingControlFlowState;
    private final MappingField yawField = this.J("yRot", true, Float.TYPE);
    private final MappingField pitchField;

    public MS08PacketPlayerPosLook() {
        super(MappedClasses.Dg);
        if (MS08PacketPlayerPosLook.getMappingControlFlowState() != null) {
            this.pitchField = this.J("xRot", true, Float.TYPE);
            return;
        }
        this.pitchField = this.J("xRot", true, Float.TYPE);
        GuiComponent.setLegacyComponentState(new GuiComponent[1]);
    }

    public static String[] getMappingControlFlowState() {
        return mappingControlFlowState;
    }

    public float getPitch(Object packet) {
        return this.pitchField.getFloat(packet);
    }

    public void setYaw(Object packet, float yaw) {
        this.yawField.setFloat(packet, yaw);
    }

    public float getYaw(Object packet) {
        return this.yawField.getFloat(packet);
    }

    public void setPitch(Object packet, float pitch) {
        this.pitchField.setFloat(packet, pitch);
    }

    public static void setMappingControlFlowState(String[] state) {
        mappingControlFlowState = state;
    }

    static {
        MS08PacketPlayerPosLook.setMappingControlFlowState(new String[1]);
    }
}

