/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MEmptyItemAttributeModifiers
extends Mapping {
    private final static String CREATE_METHOD_NAME = "create";
    private final MappingMethod createMethod = this.registerStaticMethod("create", false, MappedClasses.V1, new Class[0]);

    public static Object create(MEmptyItemAttributeModifiers mapping) {
        return mapping.invokeCreate();
    }

    private Object invokeCreate() {
        return this.createMethod.invokeObject(null, new Object[0]);
    }

    public MEmptyItemAttributeModifiers() {
        super(MappedClasses.V1);
    }
}

