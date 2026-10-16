/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class MStyle
extends Mapping {
    private MappingField EMPTY;
    private MappingMethod applyLegacyFormat;

    public MStyle() {
        super(MappedClasses.Va);
        if (ForgeVersion.MC_26_2.d()) {
            this.EMPTY = this.registerStaticField("EMPTY", true, MappedClasses.Va);
            this.applyLegacyFormat = this.Y("applyLegacyFormat", true, MappedClasses.Va, MappedClasses.l5);
        }
    }

    public Object getEmpty() {
        if (this.EMPTY == null) {
            return null;
        }
        return this.EMPTY.getObject(null);
    }

    public Object applyLegacyFormat(Object style, Object chatFormatting) {
        if (this.applyLegacyFormat == null) {
            return style;
        }
        return this.applyLegacyFormat.invokeObject(style, chatFormatting);
    }
}

