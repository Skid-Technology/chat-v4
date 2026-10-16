package gg.vape.protocol;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class ZeusClientChannelInitializer extends ChannelInitializer<SocketChannel> {
   final ZeusConnectionManager d;

   public ZeusClientChannelInitializer(ZeusConnectionManager zeusConnectionManager) {
      this.d = zeusConnectionManager;
   }

   protected void initChannel(SocketChannel socketChannel) {
      this.initSocketChannel(socketChannel);
   }

   protected void initSocketChannel(SocketChannel socketChannel) {
      ChannelPipeline channelPipeline = socketChannel.pipeline();
      channelPipeline.addLast(new ReadTimeoutHandler(30))
         .addLast(new ZeusFrameDecoder())
         .addLast(new ZeusPacketDecoder(ZeusPacketDirection.CLIENT, true))
         .addLast(new ZeusFrameEncoder())
         .addLast(new ZeusPacketEncoder(ZeusPacketDirection.SERVER, true))
         .addLast(ZeusConnectionManager.m(this.d));
   }
}
