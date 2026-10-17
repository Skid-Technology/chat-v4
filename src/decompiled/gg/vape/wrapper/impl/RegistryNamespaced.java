/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.RegistrySimple;

public class RegistryNamespaced
extends RegistrySimple {
    public Object getByValue(int id) {
        return RegistryNamespaced.vapeInstance.getMappingsMapperCompat().qc.getByValue(this.I, id);
    }

    public RegistryNamespaced(Object wrappedObject) {
        super(wrappedObject);
    }
}

