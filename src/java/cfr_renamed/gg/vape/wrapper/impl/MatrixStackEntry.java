/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MMatrixStackEntry;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.Matrix4f;

public class MatrixStackEntry
extends Wrapper {
    public Matrix4f getMatrix() {
        return new Matrix4f(MMatrixStackEntry.getMatrix(MatrixStackEntry.vapeInstance.getMappingsMapperCompat().CK, this.I));
    }

    public MatrixStackEntry(Object wrappedObject) {
        super(wrappedObject);
    }
}

