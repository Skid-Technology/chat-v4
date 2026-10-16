/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MPlayerDiggingAction
extends Mapping {
    private final static String RELEASE_USE_ITEM_FIELD_NAME = "RELEASE_USE_ITEM";
    private final MappingField releaseUseItemField = this.registerStaticField("RELEASE_USE_ITEM", true, MappedClasses.FL);

    public MPlayerDiggingAction() {
        super(MappedClasses.FL);
    }

    public Object getReleaseUseItem() {
        return this.releaseUseItemField.getObject(null);
    }
}

