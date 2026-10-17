/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import java.util.List;

public class MGlStateManagerFogStateBridge
extends Mapping {
    private static final String ITEM_STATES_FIELD_NAME = "itemStates";
    private final MappingField itemStatesField = this.J("itemStates", true, List.class);

    public MGlStateManagerFogStateBridge() {
        super(MappedClasses.zM);
    }

    public List getItemStates(Object fogState) {
        return (List)this.itemStatesField.getObject(fogState);
    }
}

