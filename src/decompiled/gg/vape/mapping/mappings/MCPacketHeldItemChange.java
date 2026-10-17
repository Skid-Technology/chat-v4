/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MPacketIdFactory;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MCPacketHeldItemChange
extends Mapping {
    private final MappingMethod constructor;
    private final MappingField selectedSlotField = ForgeVersion.MC_1_8_9.L() ? this.J("slotId", true, Integer.TYPE) : (ForgeVersion.MC_1_7_10.L() && Wrapper.vapeInstance.isVanillaMinecraftPresent() ? this.J("slotId", true, Integer.TYPE) : this.J("field_149615_a", Wrapper.isNativeAvailable, Integer.TYPE));

    public MCPacketHeldItemChange() {
        this(MPacketIdFactory.getPacketMappingControlFlowState());
    }

    private MCPacketHeldItemChange(GuiComponent[] controlFlowState) {
        super(MappedClasses.e);
        this.constructor = this.Y("<init>", false, Void.TYPE, Integer.TYPE);
    }

    public void setSelectedSlot(Object packet, int selectedSlot) {
        this.selectedSlotField.setInt(packet, selectedSlot);
    }

    public int getSelectedSlot(Object packet) {
        return this.selectedSlotField.getInt(packet);
    }

    public Object createPacket(int selectedSlot) {
        return this.constructor.newInstance(selectedSlot);
    }
}

