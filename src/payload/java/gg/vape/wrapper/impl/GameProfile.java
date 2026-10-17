package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MGameProfile;
import gg.vape.wrapper.Wrapper;
import java.util.UUID;

public class GameProfile extends Wrapper {
   public GameProfile(Object object) {
      super(object);
   }

   public static GameProfile create(UUID uUID, String string) {
      return new GameProfile(MGameProfile.N(vapeInstance.getMappingsMapperCompat().d, uUID, string));
   }

   public String getName() {
      return MGameProfile.P(vapeInstance.getMappingsMapperCompat().d, this.I);
   }

   public void setName(String string) {
      MGameProfile.z(vapeInstance.getMappingsMapperCompat().d).setObject(this.I, string);
   }

   public UUID getUUID() {
      return MGameProfile.h(vapeInstance.getMappingsMapperCompat().d, this.I);
   }
}
