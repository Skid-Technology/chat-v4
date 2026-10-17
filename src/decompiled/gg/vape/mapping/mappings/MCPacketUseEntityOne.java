/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MCPacketUseEntityOne
extends Mapping {
    private final MappingMethod playerAbilitiesPacketConstructor = this.Y("<init>", false, Void.TYPE, MappedClasses.q9);

    public MCPacketUseEntityOne() {
        super(MappedClasses.z1);
    }

    public Object createPlayerAbilitiesPacket(Object playerCapabilities) {
        return this.playerAbilitiesPacketConstructor.newInstance(playerCapabilities);
    }
}

