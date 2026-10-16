/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MEntityEnderPearl;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MEntityItem
extends Mapping {
    private final MappingMethod getItemMethod;

    public Object getItem(Object entityItemHandle) {
        return this.getItemMethod.invokeObject(entityItemHandle, new Object[0]);
    }

    public MEntityItem() {
        this(MEntityEnderPearl.getEntityControlFlowState());
    }

    private MEntityItem(GuiComponent[] entityControlFlowState) {
        super(MappedClasses.zW);
        if (entityControlFlowState != null) {
            this.getItemMethod = ForgeVersion.MC_1_16_5.d() ? this.Y("getItem", true, MappedClasses.VK, new Class[0]) : this.Y(ForgeVersion.c() >= 23 ? "getItem" : "getEntityItem", true, MappedClasses.VK, new Class[0]);
            return;
        }
        GuiComponent.setLegacyComponentState(new GuiComponent[4]);
        this.getItemMethod = this.Y(ForgeVersion.c() >= 23 ? "getItem" : "getEntityItem", true, MappedClasses.VK, new Class[0]);
    }
}

