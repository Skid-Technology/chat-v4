/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MEntityRenderer;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Minecraft;

public class MFogRenderer
extends Mapping {
    public MappingMethod setupFogMethod;
    private MappingMethod getBufferMethod;

    public MFogRenderer() {
        this(MEntityRenderer.X());
    }

    private MFogRenderer(int initializationState) {
        super(MappedClasses.FOG_RENDERER);
        MFogRenderer mappings;
        String methodName;
        boolean remap;
        Class<Void> returnType;
        Class[] parameterTypes;
        int unusedInitializationState = initializationState;
        if (ForgeVersion.MC_1_21_6.d()) {
            parameterTypes = new Class[]{MappedClasses.FOG_TYPE};
            returnType = MappedClasses.qk;
            remap = true;
            methodName = "getBuffer";
            mappings = this;
            this.getBufferMethod = mappings.Y(methodName, remap, returnType, parameterTypes);
        }
        if (ForgeVersion.MC_1_21_10.d()) {
            this.setupFogMethod = null;
        } else if (Minecraft.isNativeAvailable) {
            if (ForgeVersion.MC_1_20_6.d()) {
                parameterTypes = new Class[]{MappedClasses.lt, MappedClasses.FOG_TYPE, Float.TYPE, Boolean.TYPE, Float.TYPE};
                returnType = Void.TYPE;
                remap = true;
                methodName = "setupFog";
                mappings = this;
                this.setupFogMethod = mappings.registerStaticMethod(methodName, remap, returnType, parameterTypes);
            } else {
                parameterTypes = new Class[]{MappedClasses.lt, MappedClasses.FOG_TYPE, Float.TYPE, Boolean.TYPE};
                returnType = Void.TYPE;
                remap = true;
                methodName = "setupFog";
                mappings = this;
                this.setupFogMethod = mappings.registerStaticMethod(methodName, remap, returnType, parameterTypes);
            }
        } else {
            parameterTypes = new Class[]{MappedClasses.lt, MappedClasses.FOG_TYPE, Float.TYPE, Boolean.TYPE, Float.TYPE};
            returnType = Void.TYPE;
            remap = false;
            methodName = "setupFog";
            mappings = this;
            this.setupFogMethod = mappings.registerStaticMethod(methodName, remap, returnType, parameterTypes);
        }
    }

    public Object getBuffer(Object fogRenderer, Object fogType) {
        return this.getBufferMethod.invokeObject(fogRenderer, fogType);
    }
}

