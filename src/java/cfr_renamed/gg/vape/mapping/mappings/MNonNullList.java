/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.ForgeVersion;

public class MNonNullList
extends Mapping {
    private final MappingMethod createMethod;

    public Object create() {
        if (ForgeVersion.MC_1_17.d()) {
            return this.createMethod.invokeObject(null, new Object[0]);
        }
        return this.createMethod.newInstance(new Object[0]);
    }

    public MNonNullList() {
        this(BlockData.W());
    }

    private MNonNullList(String[] nonNullListMappingState) {
        super(MappedClasses.Vd);
        if (nonNullListMappingState != null) {
            this.createMethod = ForgeVersion.MC_1_17.d() ? this.registerStaticMethod("create", true, MappedClasses.Vd, new Class[0]) : this.Y("<init>", false, Void.TYPE, new Class[0]);
            return;
        }
        this.createMethod = this.Y("<init>", false, Void.TYPE, new Class[0]);
    }
}

