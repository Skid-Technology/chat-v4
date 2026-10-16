/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MRayTraceContext
extends Mapping {
    private final static String CONSTRUCTOR = "<init>";
    private final MappingMethod constructor = this.Y("<init>", false, Void.TYPE, MappedClasses.qP, MappedClasses.qP, MappedClasses.DS, MappedClasses.Dm, MappedClasses.zc);

    public MRayTraceContext() {
        super(MappedClasses.Fc);
    }

    public Object create(Object startVec, Object endVec, Object fluidMode, Object blockMode, Object entity) {
        return this.constructor.newInstance(startVec, endVec, fluidMode, blockMode, entity);
    }
}

