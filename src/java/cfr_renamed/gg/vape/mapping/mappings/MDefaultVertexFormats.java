/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MDefaultVertexFormats
extends Mapping {
    private final static String POSITION_COLOR_FIELD_NAME = "POSITION_COLOR";
    private final MappingField positionColorField = this.registerStaticField("POSITION_COLOR", true, MappedClasses.zG);

    public static Object getPositionColor(MDefaultVertexFormats mapping) {
        return mapping.readPositionColor();
    }

    private Object readPositionColor() {
        return this.positionColorField.getObject(null);
    }

    public MDefaultVertexFormats() {
        super(MappedClasses.Yo);
    }
}

