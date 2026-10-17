/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MAbstractClientPlayer
extends Mapping {
    private final MappingMethod keyEventConstructor = this.registerConstructor(Integer.TYPE, Integer.TYPE, Integer.TYPE);

    public MAbstractClientPlayer() {
        super(MappedClasses.YM);
    }

    public Object createKeyEvent(int keyCode, int scanCode, int modifiers) {
        return this.keyEventConstructor.newInstance(keyCode, scanCode, modifiers);
    }
}

