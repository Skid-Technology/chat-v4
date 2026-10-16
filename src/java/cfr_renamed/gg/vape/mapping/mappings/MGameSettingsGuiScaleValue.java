/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MGameSettingsGuiScaleValue
extends Mapping {
    private final static String ANTIALIASING_LEVEL_FIELD;
    private static String controlFlowMarker;
    private final MappingField antialiasingLevelField = this.registerStaticField(ANTIALIASING_LEVEL_FIELD, false, Integer.TYPE);

    public int getAntialiasingLevel() {
        if (this.antialiasingLevelField.hasResolutionFailed()) {
            return 1;
        }
        return this.antialiasingLevelField.getInt(null);
    }

    public static String getControlFlowMarker() {
        return controlFlowMarker;
    }

    public MGameSettingsGuiScaleValue() {
        this(MGameSettingsGuiScaleValue.getControlFlowMarker());
    }

    private MGameSettingsGuiScaleValue(String marker) {
        super(MappedClasses.Ym);
    }

    public static void setControlFlowMarker(String marker) {
        controlFlowMarker = marker;
    }

    static {
        MGameSettingsGuiScaleValue.setControlFlowMarker("x0yBJc");
        ANTIALIASING_LEVEL_FIELD = "antialiasingLevel";
    }
}

