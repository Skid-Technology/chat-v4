/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MServerData
extends Mapping {
    private final static String SERVER_IP_FIELD_NAME = "serverIP";
    private final MappingField serverIpField = this.J("serverIP", true, String.class);

    private String readServerIp(Object serverData) {
        return (String)this.serverIpField.getObject(serverData);
    }

    public static String getServerIp(MServerData mapping, Object serverData) {
        return mapping.readServerIp(serverData);
    }

    public MServerData() {
        super(MappedClasses.uR);
    }
}

