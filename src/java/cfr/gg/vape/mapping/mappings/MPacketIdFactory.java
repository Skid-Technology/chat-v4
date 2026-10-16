/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;

public class MPacketIdFactory
extends Mapping {
    private final MappingMethod confirmTeleportPacketConstructor = this.registerConstructor(Integer.TYPE);
    private static GuiComponent[] packetMappingControlFlowState;

    public static GuiComponent[] getPacketMappingControlFlowState() {
        return packetMappingControlFlowState;
    }

    public static void setPacketMappingControlFlowState(GuiComponent[] state) {
        packetMappingControlFlowState = state;
    }

    public Object createConfirmTeleportPacket(int teleportId) {
        return this.confirmTeleportPacketConstructor.newInstance(teleportId);
    }

    public MPacketIdFactory() {
        super(MappedClasses.Yq);
    }

    static {
        if (MPacketIdFactory.getPacketMappingControlFlowState() == null) {
            MPacketIdFactory.setPacketMappingControlFlowState(new GuiComponent[2]);
        }
    }
}

