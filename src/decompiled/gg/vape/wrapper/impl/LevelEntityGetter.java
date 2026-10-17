/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.AxisAlignedBB;
import java.util.function.Consumer;

public class LevelEntityGetter
extends Wrapper {
    public LevelEntityGetter(Object wrappedObject) {
        super(wrappedObject);
    }

    public void forEachEntityInBounds(AxisAlignedBB boundingBox, Consumer consumer) {
        LevelEntityGetter.vapeInstance.getMappingsMapperCompat().levelEntityGetter.forEachEntityInBounds(this.I, boundingBox.getObject(), consumer);
    }
}

