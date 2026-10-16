/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MFontGlyphInfo
extends Mapping {
    private final MappingMethod advanceMethod = this.Y("getAdvance", true, Float.TYPE, new Class[0]);
    private final MappingMethod styledAdvanceMethod = this.Y("getAdvance", true, Float.TYPE, Boolean.TYPE);
    private final MappingMethod boldOffsetMethod = this.Y("getBoldOffset", true, Float.TYPE, new Class[0]);
    private final MappingMethod shadowOffsetMethod = this.Y("getShadowOffset", true, Float.TYPE, new Class[0]);
    private static int[] controlFlowState;

    public static void setFontGlyphInfoControlFlowState(int[] state) {
        controlFlowState = state;
    }

    public float getShadowOffset(Object glyphInfo) {
        if (this.shadowOffsetMethod == null || this.shadowOffsetMethod.hasResolutionFailed()) {
            return 1.0f;
        }
        return this.shadowOffsetMethod.invokeFloat(glyphInfo, new Object[0]);
    }

    public float getBoldOffset(Object glyphInfo) {
        if (this.boldOffsetMethod == null || this.boldOffsetMethod.hasResolutionFailed()) {
            return 1.0f;
        }
        return this.boldOffsetMethod.invokeFloat(glyphInfo, new Object[0]);
    }

    public static int[] getFontGlyphInfoControlFlowState() {
        return controlFlowState;
    }

    public float getAdvance(Object glyphInfo, boolean bold) {
        if (this.styledAdvanceMethod == null || this.styledAdvanceMethod.hasResolutionFailed()) {
            float advance = this.getAdvance(glyphInfo);
            return bold ? advance + this.getBoldOffset(glyphInfo) : advance;
        }
        return this.styledAdvanceMethod.invokeFloat(glyphInfo, bold);
    }

    public float getAdvance(Object glyphInfo) {
        if (this.advanceMethod == null || this.advanceMethod.hasResolutionFailed()) {
            return 0.0f;
        }
        return this.advanceMethod.invokeFloat(glyphInfo, new Object[0]);
    }

    public MFontGlyphInfo() {
        super(MappedClasses.var_java_lang_Class_g);
    }

    static {
        MFontGlyphInfo.setFontGlyphInfoControlFlowState(null);
    }
}

