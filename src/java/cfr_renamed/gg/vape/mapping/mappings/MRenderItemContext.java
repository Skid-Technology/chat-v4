/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MRenderItemContext
extends Mapping {
    private final static String GUI_FIELD_NAME = "GUI";
    private final MappingField guiField = this.registerStaticField("GUI", true, MappedClasses.YK);

    public static Object getGui(MRenderItemContext mapping) {
        return mapping.readGui();
    }

    public MRenderItemContext() {
        super(MappedClasses.YK);
    }

    private Object readGui() {
        return this.guiField.getObject(null);
    }
}

