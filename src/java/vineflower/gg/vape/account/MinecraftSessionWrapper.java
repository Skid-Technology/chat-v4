package gg.vape.account;

import gg.vape.mapping.mappings.MSession;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.SessionType;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

public class MinecraftSessionWrapper extends Wrapper {
   public static MinecraftSessionWrapper createModern(
      String username, UUID profileId, String accessToken, Optional<String> xuid, Optional<String> clientId, SessionType accountType
   ) {
      return new MinecraftSessionWrapper(MSession.B(vapeInstance.getMappings().hw, username, profileId, accessToken, xuid, clientId, accountType.getObject()));
   }

   public MinecraftSessionWrapper(Object session) {
      super(session);
   }

   public String getUsername() {
      return vapeInstance.getMappings().hw.s(this.I);
   }

   public UUID getProfileId() {
      if (ForgeVersion.MC_1_20_6.d()) {
         Object id = vapeInstance.getMappings().hw.V(this.I);
         if (id instanceof UUID) {
            return (UUID)id;
         } else if (id == null) {
            return UUID.fromString("00000000-0000-0000-0000-000000000000");
         } else {
            String s = id.toString();

            try {
               return UUID.fromString(s.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
            } catch (Exception var8) {
               String name = this.getUsername();
               StringBuilder var5 = new StringBuilder().append("OfflinePlayer:");
               StringBuilder var12;
               if (name != null) {
                  var12 = var5;
                  if (!name.isEmpty()) {
                     return UUID.nameUUIDFromBytes(var5.append(name).toString().getBytes(StandardCharsets.UTF_8));
                  }
               } else {
                  var12 = var5;
               }

               return UUID.nameUUIDFromBytes(var12.append(s).toString().getBytes(StandardCharsets.UTF_8));
            }
         }
      } else {
         Object rawObj = vapeInstance.getMappings().hw.V(this.I);
         if (rawObj instanceof UUID) {
            return (UUID)rawObj;
         } else {
            String rawProfileId = (String)rawObj;
            if (rawProfileId != null && !rawProfileId.isEmpty()) {
               try {
                  return UUID.fromString(rawProfileId.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
               } catch (Exception var7) {
                  String name = this.getUsername();
                  StringBuilder var6 = new StringBuilder().append("OfflinePlayer:");
                  StringBuilder var10000;
                  if (name != null) {
                     var10000 = var6;
                     if (!name.isEmpty()) {
                        return UUID.nameUUIDFromBytes(var6.append(name).toString().getBytes(StandardCharsets.UTF_8));
                     }
                  } else {
                     var10000 = var6;
                  }

                  return UUID.nameUUIDFromBytes(var10000.append(rawProfileId).toString().getBytes(StandardCharsets.UTF_8));
               }
            } else {
               return UUID.fromString("00000000-0000-0000-0000-000000000000");
            }
         }
      }
   }

   public static MinecraftSessionWrapper createLegacy(String username, String profileId, String accessToken, String sessionType) {
      return new MinecraftSessionWrapper(MSession.t(vapeInstance.getMappings().hw, username, profileId, accessToken, sessionType));
   }
}
