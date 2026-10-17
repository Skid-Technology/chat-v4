package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MNetHandlerPlayClientImpl;
import java.util.Collection;

public class NetHandlerPlayClientImpl extends NetHandlerPlayClient {
   public Collection getPlayerInfoMap() {
      return MNetHandlerPlayClientImpl.H(vapeInstance.getMappings().hB, this.I);
   }

   public boolean M() {
      return MNetHandlerPlayClientImpl.d(vapeInstance.getMappings().hB, this.I);
   }

   public void addToSendQueue(Packet packet) {
      MNetHandlerPlayClientImpl.I(vapeInstance.getMappings().hB, this.I, packet.getObject());
   }

   public boolean d() {
      return ForgeVersion.MC_1_20_6.d() ? true : MNetHandlerPlayClientImpl.X(vapeInstance.getMappings().hB, this.I);
   }

   public NetHandlerPlayClientImpl(Object object) {
      super(object);
   }

   public NetworkManager a() {
      return new NetworkManager(MNetHandlerPlayClientImpl.z(vapeInstance.getMappings().hB, this.I));
   }
}
