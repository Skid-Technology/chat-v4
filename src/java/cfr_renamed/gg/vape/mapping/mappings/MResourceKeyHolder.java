/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.mappings.MEquippable;
import gg.vape.ui.click.component.GuiComponent;

public class MResourceKeyHolder
extends Mapping {
    private final static String GOLD_FIELD_NAME = "GOLD";
    private final MappingField goldField = this.registerStaticField("GOLD", true, MappedClasses.qB);

    public MResourceKeyHolder() {
        this(MEquippable.p());
    }

    private MResourceKeyHolder(String controlFlowMarker) {
        super(MappedClasses.qL);
        if (GuiComponent.getLegacyComponentState() == null) {
            MEquippable.z("Xwix5b");
        }
    }

    public Object getGold() {
        return this.goldField.getObject(null);
    }
}

