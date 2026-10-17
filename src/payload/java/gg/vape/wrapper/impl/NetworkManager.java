package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MNetworkManager;
import gg.vape.wrapper.Wrapper;

public class NetworkManager extends Wrapper {
   public void G(Packet packet) {
      if (ForgeVersion.MC_1_20_6.d()) {
         MNetworkManager.m(vapeInstance.getMappingsMapperCompat().Do, this.I, packet.getObject(), null, true);
      } else {
         MNetworkManager.B(vapeInstance.getMappingsMapperCompat().Do, this.I, packet.getObject());
      }
   }

   public EntityFishHookState getChannel() {
      return new EntityFishHookState(MNetworkManager.getChannel(vapeInstance.getMappingsMapperCompat().Do, this.I));
   }

   public NetworkPacketHandle c() {
      return new NetworkPacketHandle(MNetworkManager.U(vapeInstance.getMappingsMapperCompat().Do, this.I));
   }

   public NetworkManager(Object object) {
      super(object);
   }
}
