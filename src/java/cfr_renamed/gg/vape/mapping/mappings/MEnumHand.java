/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.wrapper.Wrapper;

public class MEnumHand
extends Mapping {
    private final MappingField offHandField;
    private final MappingField mainHandField = this.registerStaticField("MAIN_HAND", Wrapper.isNativeAvailable, MappedClasses.Yf);

    public MEnumHand() {
        super(MappedClasses.Yf);
        this.offHandField = this.registerStaticField("OFF_HAND", Wrapper.isNativeAvailable, MappedClasses.Yf);
    }

    public Object getOffHand() {
        return this.offHandField.getObject(null);
    }

    public Object getMainHand() {
        return this.mainHandField.getObject(null);
    }
}

