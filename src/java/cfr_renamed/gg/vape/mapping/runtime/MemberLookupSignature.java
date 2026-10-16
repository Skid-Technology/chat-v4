/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.runtime;

public class MemberLookupSignature {
    final public String runtimeName;
    final public Class<?> resolvedType;
    final public Class<?>[] parameterTypes;
    private final Boolean mappedMemberOverride;

    public MemberLookupSignature(String runtimeName, Boolean mappedMemberOverride, Class<?> resolvedType, Class<?> ... parameterTypes) {
        this.runtimeName = runtimeName;
        this.resolvedType = resolvedType;
        this.parameterTypes = parameterTypes;
        this.mappedMemberOverride = mappedMemberOverride;
    }

    public Boolean getMappedMemberOverride() {
        return this.mappedMemberOverride;
    }
}

