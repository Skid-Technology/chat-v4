/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.ForgeVersion;

public class MResourceLocation
extends Mapping {
    private MappingMethod createMethod;
    private final MappingMethod getPathMethod;

    public MResourceLocation() {
        this(BlockData.W());
    }

    private MResourceLocation(String[] resourceLocationMappingState) {
        super(MappedClasses.zC);
        if (resourceLocationMappingState != null) {
            this.getPathMethod = ForgeVersion.MC_1_16_5.d() ? this.Y("getPath", true, String.class, new Class[0]) : this.Y("getResourcePath", true, String.class, new Class[0]);
            this.createMethod = ForgeVersion.MC_1_21_0.d() ? this.registerStaticMethod("parse", true, MappedClasses.zC, String.class) : this.Y("<init>", false, Void.TYPE, String.class);
            return;
        }
        this.getPathMethod = this.Y("getResourcePath", true, String.class, new Class[0]);
        if (ForgeVersion.MC_1_21_0.d()) {
            this.registerStaticMethod("parse", true, MappedClasses.zC, String.class);
        }
        this.createMethod = this.Y("<init>", false, Void.TYPE, String.class);
    }

    public String getPath(Object resourceLocationHandle) {
        return (String)this.getPathMethod.invokeObject(resourceLocationHandle, new Object[0]);
    }

    public Object create(String location) {
        if (ForgeVersion.MC_1_21_0.d()) {
            return this.createMethod.invokeObject(null, location);
        }
        return this.createMethod.newInstance(location);
    }
}

