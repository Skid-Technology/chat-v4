/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.mappings.MItemAttributeModifiersComponent;
import gg.vape.ui.click.component.GuiComponent;

public class MItemAttributeModifiersComponent$Entry
extends Mapping {
    private final MappingField attributeField = this.J("attribute", true, MappedClasses.Vo);
    private final MappingField modifierField = this.J("modifier", true, MappedClasses.z_);

    public Object getModifier(Object entry) {
        return this.modifierField.getObject(entry);
    }

    public Object getAttribute(Object entry) {
        return this.attributeField.getObject(entry);
    }

    public MItemAttributeModifiersComponent$Entry() {
        this(MItemAttributeModifiersComponent.getItemAttributeModifiersControlFlowState());
    }

    private MItemAttributeModifiersComponent$Entry(GuiComponent[] controlFlowState) {
        super(MappedClasses.zP);
        if (GuiComponent.getLegacyComponentState() == null) {
            MItemAttributeModifiersComponent.setItemAttributeModifiersControlFlowState(new GuiComponent[5]);
        }
    }
}

