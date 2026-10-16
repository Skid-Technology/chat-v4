/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MFontGlyph
extends Mapping {
    private static final String INFO_METHOD_NAME = "info";
    private final MappingMethod infoMethod = this.Y("info", true, MappedClasses.g, new Class[0]);

    public MFontGlyph() {
        super(MappedClasses.qd);
    }

    public Object getInfo(Object fontGlyph) {
        return this.infoMethod.invokeObject(fontGlyph, new Object[0]);
    }
}

