package gg.vape.protocol;

import gg.vape.protocol.packet.HeartbeatPacket;
import gg.vape.protocol.packet.HeartbeatResponsePacket;
import gg.vape.protocol.packet.ServerDisconnectPacket;
import gg.vape.protocol.packet.ZeusSerializablePacket;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public enum ZeusProtocolState {
   UNAUTHENTICATED(ZeusUnauthenticatedProtocolState::register),
   AUTHENTICATED(ZeusAuthenticatedProtocolState::register);

   private final Map<ZeusPacketDirection, List<Class<? extends ZeusSerializablePacket>>> packetClassesByDirection = new HashMap<>();

   private ZeusProtocolState(Consumer<ZeusProtocolState> registerStatePackets) {
      for (ZeusPacketDirection zeusPacketDirection : ZeusPacketDirection.values()) {
         this.packetClassesByDirection.put(zeusPacketDirection, new ArrayList<>());
      }

      this.registerPacket(ZeusPacketDirection.CLIENT, HeartbeatResponsePacket.class);
      this.registerPacket(ZeusPacketDirection.SERVER, HeartbeatPacket.class);
      this.registerPacket(ZeusPacketDirection.CLIENT, ServerDisconnectPacket.class);
      registerStatePackets.accept(this);
   }

   protected void registerPacket(ZeusPacketDirection zeusPacketDirection, Class<? extends ZeusSerializablePacket> clazz) {
      List<Class<? extends ZeusSerializablePacket>> list = this.packetClassesByDirection.get(zeusPacketDirection);
      list.add(clazz);
   }

   private static InstantiationException a(InstantiationException instantiationException) {
      return instantiationException;
   }

   public int getPacketId(ZeusPacketDirection zeusPacketDirection, ZeusSerializablePacket zeusSerializablePacket) {
      List<Class<? extends ZeusSerializablePacket>> list = this.packetClassesByDirection.get(zeusPacketDirection);
      return list.indexOf(zeusSerializablePacket.getClass());
   }

   public ZeusSerializablePacket createPacket(ZeusPacketDirection zeusPacketDirection, int n) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
      List<Class<? extends ZeusSerializablePacket>> list = this.packetClassesByDirection.get(zeusPacketDirection);
      if (n >= 0 && n < list.size()) {
         Class<? extends ZeusSerializablePacket> clazz = list.get(n);
         if (clazz == null) {
            System.out.println("Failed to find packet with id " + n);
            return null;
         } else {
            return clazz.getDeclaredConstructor().newInstance();
         }
      } else {
         throw new RuntimeException("Attempted to get packet by id " + n + " in direction " + zeusPacketDirection + " but it's out of bounds.");
      }
   }
}
