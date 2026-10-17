/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ResourceLocationConstantPair;

public class MResourceLocationConstantsBridge
extends Mapping {
    private final MappingField guiField = this.registerStaticField("GUI", true, MappedClasses.zC);
    private final MappingField itemsField = this.registerStaticField("ITEMS", true, MappedClasses.zC);

    public Object getItems() {
        return this.itemsField.getObject(null);
    }

    public Object getGui() {
        return this.guiField.getObject(null);
    }

    public MResourceLocationConstantsBridge() {
        this(ResourceLocationConstantPair.getControlFlowState());
    }

    private MResourceLocationConstantsBridge(GuiComponent[] controlFlowState) {
        super(MappedClasses.qq);
        if (controlFlowState != null) {
            GuiComponent.setLegacyComponentState(new GuiComponent[5]);
        }
    }
}

