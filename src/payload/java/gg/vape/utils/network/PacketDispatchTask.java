package gg.vape.utils.network;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.wrapper.impl.NetworkManager;
import gg.vape.wrapper.impl.NetworkPacketHandle;
import gg.vape.wrapper.impl.Packet;

public class PacketDispatchTask {
   private Packet n;
   private NetworkManager h;
   private boolean N;

   public void t() {
      if (this.A()) {
         if (PacketDispatchMarkerRegistry.S(this.n)) {
            PacketDispatchMarkerRegistry.I(this.n);
            return;
         }

         PacketDispatchMarkerRegistry.u(this.n);
         NetworkPacketHandle networkPacketHandle = this.h.c();
         if (!networkPacketHandle.isInstance(MappedClasses.F1)) {
            return;
         }

         this.h.G(this.n);
      } else {
         try {
            this.n.processPacket(this.h.c());
         } catch (Exception var2) {
            if (this.A()) {
               Vape.logThrowable(var2);
            }
         }
      }
   }

   public boolean A() {
      return this.N;
   }

   public Packet s() {
      return this.n;
   }

   private static Exception a(Exception exception) {
      return exception;
   }

   @Override
   public boolean equals(Object object) {
      if (!(object instanceof PacketDispatchTask)) {
         return object instanceof Packet ? this.n.equals(object) : super.equals(object);
      } else {
         PacketDispatchTask packetDispatchTask = (PacketDispatchTask)object;
         return packetDispatchTask.s().equals(this.n) && packetDispatchTask.A() == this.N;
      }
   }

   public PacketDispatchTask(Packet packet, boolean bl, NetworkManager networkManager) {
      this.n = packet;
      this.N = bl;
      this.h = networkManager;
   }
}
