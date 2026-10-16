/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.account;

import gg.vape.mapping.mappings.MSession;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.SessionType;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

public class MinecraftSessionWrapper
extends Wrapper {
    public static MinecraftSessionWrapper createModern(String username, UUID profileId, String accessToken, Optional<String> xuid, Optional<String> clientId, SessionType accountType) {
        return new MinecraftSessionWrapper(MSession.B(MinecraftSessionWrapper.vapeInstance.getMappings().hw, username, profileId, accessToken, xuid, clientId, accountType.getObject()));
    }

    public MinecraftSessionWrapper(Object session) {
        super(session);
    }

    public String getUsername() {
        return MinecraftSessionWrapper.vapeInstance.getMappings().hw.s(this.I);
    }

    public UUID getProfileId() {
        block8: {
            if (!ForgeVersion.MC_1_20_6.d()) break block8;
            Object id = MinecraftSessionWrapper.vapeInstance.getMappings().hw.V(this.I);
            if (id instanceof UUID) {
                return (UUID)id;
            }
            if (id == null) {
                return UUID.fromString("00000000-0000-0000-0000-000000000000");
            }
            String s = id.toString();
            try {
                return UUID.fromString(s.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
            }
            catch (Exception e) {
                String string;
                StringBuilder stringBuilder;
                block11: {
                    block10: {
                        StringBuilder stringBuilder2;
                        block9: {
                            String name = this.getUsername();
                            stringBuilder2 = new StringBuilder().append("OfflinePlayer:");
                            if (name == null) break block9;
                            stringBuilder = stringBuilder2;
                            if (name.isEmpty()) break block10;
                            string = name;
                            break block11;
                        }
                        stringBuilder = stringBuilder2;
                    }
                    string = s;
                }
                return UUID.nameUUIDFromBytes(stringBuilder.append(string).toString().getBytes(StandardCharsets.UTF_8));
            }
        }
        Object rawObj = MinecraftSessionWrapper.vapeInstance.getMappings().hw.V(this.I);
        if (rawObj instanceof UUID) {
            return (UUID)rawObj;
        }
        String rawProfileId = (String)rawObj;
        if (rawProfileId == null || rawProfileId.isEmpty()) {
            return UUID.fromString("00000000-0000-0000-0000-000000000000");
        }
        try {
            return UUID.fromString(rawProfileId.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
        }
        catch (Exception e) {
            String string;
            StringBuilder stringBuilder;
            block14: {
                block13: {
                    StringBuilder stringBuilder3;
                    block12: {
                        String name = this.getUsername();
                        stringBuilder3 = new StringBuilder().append("OfflinePlayer:");
                        if (name == null) break block12;
                        stringBuilder = stringBuilder3;
                        if (name.isEmpty()) break block13;
                        string = name;
                        break block14;
                    }
                    stringBuilder = stringBuilder3;
                }
                string = rawProfileId;
            }
            return UUID.nameUUIDFromBytes(stringBuilder.append(string).toString().getBytes(StandardCharsets.UTF_8));
        }
    }

    public static MinecraftSessionWrapper createLegacy(String username, String profileId, String accessToken, String sessionType) {
        return new MinecraftSessionWrapper(MSession.t(MinecraftSessionWrapper.vapeInstance.getMappings().hw, username, profileId, accessToken, sessionType));
    }
}

