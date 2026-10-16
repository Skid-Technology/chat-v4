package io.netty.channel.epoll;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ConnectTimeoutException;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.EventLoop;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class EpollSocketChannel extends AbstractEpollChannel implements SocketChannel {
   private static final String EXPECTED_TYPES = " (expected: "
      + StringUtil.simpleClassName(ByteBuf.class)
      + ", "
      + StringUtil.simpleClassName(DefaultFileRegion.class)
      + ')';
   private final EpollSocketChannelConfig config = new EpollSocketChannelConfig(this);
   private ChannelPromise connectPromise;
   private ScheduledFuture<?> connectTimeoutFuture;
   private SocketAddress requestedRemoteAddress;
   private volatile InetSocketAddress local;
   private volatile InetSocketAddress remote;
   private volatile boolean inputShutdown;
   private volatile boolean outputShutdown;

   EpollSocketChannel(Channel parent, int fd) {
      super(parent, fd, 1, true);
      this.remote = Native.remoteAddress(fd);
      this.local = Native.localAddress(fd);
   }

   public EpollSocketChannel() {
      super(Native.socketStreamFd(), 1);
   }

   @Override
   protected AbstractEpollChannel.AbstractEpollUnsafe newUnsafe() {
      return new EpollSocketChannel.EpollSocketUnsafe();
   }

   @Override
   protected SocketAddress localAddress0() {
      return this.local;
   }

   @Override
   protected SocketAddress remoteAddress0() {
      return this.remote;
   }

   @Override
   protected void doBind(SocketAddress local) throws Exception {
      InetSocketAddress localAddress = (InetSocketAddress)local;
      Native.bind(this.fd, localAddress.getAddress(), localAddress.getPort());
      this.local = Native.localAddress(this.fd);
   }

   private boolean writeBytes(ChannelOutboundBuffer in, ByteBuf buf) throws Exception {
      int readableBytes = buf.readableBytes();
      if (readableBytes == 0) {
         in.remove();
         return true;
      } else {
         boolean done = false;
         long writtenBytes = 0L;
         if (buf.hasMemoryAddress()) {
            long memoryAddress = buf.memoryAddress();
            int readerIndex = buf.readerIndex();
            int writerIndex = buf.writerIndex();

            while (true) {
               int localFlushedAmount = Native.writeAddress(this.fd, memoryAddress, readerIndex, writerIndex);
               if (localFlushedAmount <= 0) {
                  this.setEpollOut();
                  break;
               }

               writtenBytes += localFlushedAmount;
               if (writtenBytes == readableBytes) {
                  done = true;
                  break;
               }

               readerIndex += localFlushedAmount;
            }

            in.removeBytes(writtenBytes);
            return done;
         } else if (buf.nioBufferCount() != 1) {
            ByteBuffer[] nioBuffers = buf.nioBuffers();
            return this.writeBytesMultiple(in, nioBuffers, nioBuffers.length, readableBytes);
         } else {
            int readerIndex = buf.readerIndex();
            ByteBuffer nioBuf = buf.internalNioBuffer(readerIndex, buf.readableBytes());

            while (true) {
               int pos = nioBuf.position();
               int limit = nioBuf.limit();
               int localFlushedAmountx = Native.write(this.fd, nioBuf, pos, limit);
               if (localFlushedAmountx > 0) {
                  ((Buffer)nioBuf).position(pos + localFlushedAmountx);
                  writtenBytes += localFlushedAmountx;
                  if (writtenBytes != readableBytes) {
                     continue;
                  }

                  done = true;
                  break;
               }

               this.setEpollOut();
               break;
            }

            in.removeBytes(writtenBytes);
            return done;
         }
      }
   }

   private boolean writeBytesMultiple(ChannelOutboundBuffer param1, IovArray param2) throws IOException {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IllegalStateException: No successor exists for {Do}:33
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.Statement.getFirstSuccessor(Statement.java:834)
      //   at org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.flattenStatement(FlattenStatementsHelper.java:289)
      //   at org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.flattenStatement(FlattenStatementsHelper.java:478)
      //   at org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.flattenStatement(FlattenStatementsHelper.java:257)
      //   at org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.flattenStatement(FlattenStatementsHelper.java:478)
      //   at org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.flattenStatement(FlattenStatementsHelper.java:458)
      //   at org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.flattenStatement(FlattenStatementsHelper.java:458)
      //   at org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.flattenStatement(FlattenStatementsHelper.java:474)
      //   at org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.buildDirectGraph(FlattenStatementsHelper.java:43)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SFormsConstructor.splitVariables(SFormsConstructor.java:72)
      //   at org.jetbrains.java.decompiler.modules.decompiler.StackVarsProcessor.simplifyStackVars(StackVarsProcessor.java:52)
      //   at org.jetbrains.java.decompiler.modules.decompiler.StackVarsProcessor.simplifyStackVars(StackVarsProcessor.java:40)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:299)
      //
      // Bytecode:
      // 00: aload 2
      // 01: invokevirtual io/netty/channel/epoll/IovArray.size ()J
      // 04: lstore 3
      // 05: aload 2
      // 06: invokevirtual io/netty/channel/epoll/IovArray.count ()I
      // 09: istore 5
      // 0b: getstatic io/netty/channel/epoll/EpollSocketChannel.$assertionsDisabled Z
      // 0e: ifne 1f
      // 11: lload 3
      // 12: lconst_0
      // 13: lcmp
      // 14: ifne 1f
      // 17: new java/lang/AssertionError
      // 1a: dup
      // 1b: invokespecial java/lang/AssertionError.<init> ()V
      // 1e: athrow
      // 1f: getstatic io/netty/channel/epoll/EpollSocketChannel.$assertionsDisabled Z
      // 22: ifne 32
      // 25: iload 5
      // 27: ifne 32
      // 2a: new java/lang/AssertionError
      // 2d: dup
      // 2e: invokespecial java/lang/AssertionError.<init> ()V
      // 31: athrow
      // 32: bipush 0
      // 33: istore 6
      // 35: lconst_0
      // 36: lstore 7
      // 38: bipush 0
      // 39: istore 9
      // 3b: iload 9
      // 3d: iload 5
      // 3f: iadd
      // 40: istore 10
      // 42: aload 0
      // 43: getfield io/netty/channel/epoll/EpollSocketChannel.fd I
      // 46: aload 2
      // 47: iload 9
      // 49: invokevirtual io/netty/channel/epoll/IovArray.memoryAddress (I)J
      // 4c: iload 5
      // 4e: invokestatic io/netty/channel/epoll/Native.writevAddresses (IJI)J
      // 51: lstore 11
      // 53: lload 11
      // 55: lconst_0
      // 56: lcmp
      // 57: ifne 61
      // 5a: aload 0
      // 5b: invokevirtual io/netty/channel/epoll/EpollSocketChannel.setEpollOut ()V
      // 5e: goto ad
      // 61: lload 3
      // 62: lload 11
      // 64: lsub
      // 65: lstore 3
      // 66: lload 7
      // 68: lload 11
      // 6a: ladd
      // 6b: lstore 7
      // 6d: lload 3
      // 6e: lconst_0
      // 6f: lcmp
      // 70: ifne 79
      // 73: bipush 1
      // 74: istore 6
      // 76: goto ad
      // 79: aload 2
      // 7a: iload 9
      // 7c: lload 11
      // 7e: invokevirtual io/netty/channel/epoll/IovArray.processWritten (IJ)J
      // 81: lstore 13
      // 83: lload 13
      // 85: ldc2_w -1
      // 88: lcmp
      // 89: ifne 8f
      // 8c: goto aa
      // 8f: iinc 9 1
      // 92: iinc 5 -1
      // 95: lload 11
      // 97: lload 13
      // 99: lsub
      // 9a: lstore 11
      // 9c: iload 9
      // 9e: iload 10
      // a0: if_icmpge aa
      // a3: lload 11
      // a5: lconst_0
      // a6: lcmp
      // a7: ifgt 79
      // aa: goto 42
      // ad: aload 1
      // ae: lload 7
      // b0: invokevirtual io/netty/channel/ChannelOutboundBuffer.removeBytes (J)V
      // b3: iload 6
      // b5: ireturn
   }

   private boolean writeBytesMultiple(ChannelOutboundBuffer in, ByteBuffer[] nioBuffers, int nioBufferCnt, long expectedWrittenBytes) throws IOException {
      assert expectedWrittenBytes != 0L;

      boolean done = false;
      long writtenBytes = 0L;
      int offset = 0;
      int end = offset + nioBufferCnt;

      while (true) {
         long localWrittenBytes = Native.writev(this.fd, nioBuffers, offset, nioBufferCnt);
         if (localWrittenBytes == 0L) {
            this.setEpollOut();
            break;
         }

         expectedWrittenBytes -= localWrittenBytes;
         writtenBytes += localWrittenBytes;
         if (expectedWrittenBytes == 0L) {
            done = true;
            break;
         }

         do {
            ByteBuffer buffer = nioBuffers[offset];
            int pos = buffer.position();
            int bytes = buffer.limit() - pos;
            if (bytes > localWrittenBytes) {
               ((Buffer)buffer).position(pos + (int)localWrittenBytes);
               break;
            }

            offset++;
            nioBufferCnt--;
            localWrittenBytes -= bytes;
         } while (offset < end && localWrittenBytes > 0L);
      }

      in.removeBytes(writtenBytes);
      return done;
   }

   private boolean writeFileRegion(ChannelOutboundBuffer in, DefaultFileRegion region) throws Exception {
      long regionCount = region.count();
      if (region.transfered() >= regionCount) {
         in.remove();
         return true;
      } else {
         long baseOffset = region.position();
         boolean done = false;
         long flushedAmount = 0L;

         for (int i = this.config().getWriteSpinCount() - 1; i >= 0; i--) {
            long offset = region.transfered();
            long localFlushedAmount = Native.sendfile(this.fd, region, baseOffset, offset, regionCount - offset);
            if (localFlushedAmount == 0L) {
               this.setEpollOut();
               break;
            }

            flushedAmount += localFlushedAmount;
            if (region.transfered() >= regionCount) {
               done = true;
               break;
            }
         }

         if (flushedAmount > 0L) {
            in.progress(flushedAmount);
         }

         if (done) {
            in.remove();
         }

         return done;
      }
   }

   @Override
   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
      while (true) {
         int msgCount = in.size();
         if (msgCount == 0) {
            this.clearEpollOut();
            return;
         } else {
            label18:
            if (msgCount > 1 && in.current() instanceof ByteBuf ? this.doWriteMultiple(in) : this.doWriteSingle(in)) {
               continue;
            }
         }
      }
   }

   private boolean doWriteSingle(ChannelOutboundBuffer in) throws Exception {
      Object msg = in.current();
      if (msg instanceof ByteBuf) {
         ByteBuf buf = (ByteBuf)msg;
         if (!this.writeBytes(in, buf)) {
            return false;
         }
      } else {
         if (!(msg instanceof DefaultFileRegion)) {
            throw new Error();
         }

         DefaultFileRegion region = (DefaultFileRegion)msg;
         if (!this.writeFileRegion(in, region)) {
            return false;
         }
      }

      return true;
   }

   private boolean doWriteMultiple(ChannelOutboundBuffer in) throws Exception {
      if (PlatformDependent.hasUnsafe()) {
         IovArray array = IovArray.get(in);
         int cnt = array.count();
         if (cnt >= 1) {
            if (!this.writeBytesMultiple(in, array)) {
               return false;
            }
         } else {
            in.removeBytes(0L);
         }
      } else {
         ByteBuffer[] buffers = in.nioBuffers();
         int cnt = in.nioBufferCount();
         if (cnt >= 1) {
            if (!this.writeBytesMultiple(in, buffers, cnt, in.nioBufferSize())) {
               return false;
            }
         } else {
            in.removeBytes(0L);
         }
      }

      return true;
   }

   @Override
   protected Object filterOutboundMessage(Object msg) {
      if (!(msg instanceof ByteBuf)) {
         if (msg instanceof DefaultFileRegion) {
            return msg;
         } else {
            throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
         }
      } else {
         ByteBuf buf = (ByteBuf)msg;
         if (!buf.hasMemoryAddress() && (PlatformDependent.hasUnsafe() || !buf.isDirect())) {
            buf = this.newDirectBuffer(buf);

            assert buf.hasMemoryAddress();
         }

         return buf;
      }
   }

   public EpollSocketChannelConfig config() {
      return this.config;
   }

   @Override
   public boolean isInputShutdown() {
      return this.inputShutdown;
   }

   @Override
   public boolean isOutputShutdown() {
      return this.outputShutdown || !this.isActive();
   }

   @Override
   public ChannelFuture shutdownOutput() {
      return this.shutdownOutput(this.newPromise());
   }

   @Override
   public ChannelFuture shutdownOutput(final ChannelPromise promise) {
      EventLoop loop = this.eventLoop();
      if (loop.inEventLoop()) {
         try {
            Native.shutdown(this.fd, false, true);
            this.outputShutdown = true;
            promise.setSuccess();
         } catch (Throwable var4) {
            promise.setFailure(var4);
         }
      } else {
         loop.execute(new Runnable() {
            @Override
            public void run() {
               EpollSocketChannel.this.shutdownOutput(promise);
            }
         });
      }

      return promise;
   }

   @Override
   public ServerSocketChannel parent() {
      return (ServerSocketChannel)super.parent();
   }

   final class EpollSocketUnsafe extends AbstractEpollChannel.AbstractEpollUnsafe {
      private RecvByteBufAllocator.Handle allocHandle;

      private void closeOnRead(ChannelPipeline pipeline) {
         EpollSocketChannel.this.inputShutdown = true;
         if (EpollSocketChannel.this.isOpen()) {
            if (Boolean.TRUE.equals(EpollSocketChannel.this.config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
               this.clearEpollIn0();
               pipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
            } else {
               this.close(this.voidPromise());
            }
         }
      }

      private boolean handleReadException(ChannelPipeline pipeline, ByteBuf byteBuf, Throwable cause, boolean close) {
         if (byteBuf != null) {
            if (byteBuf.isReadable()) {
               this.readPending = false;
               pipeline.fireChannelRead(byteBuf);
            } else {
               byteBuf.release();
            }
         }

         pipeline.fireChannelReadComplete();
         pipeline.fireExceptionCaught(cause);
         if (!close && !(cause instanceof IOException)) {
            return false;
         } else {
            this.closeOnRead(pipeline);
            return true;
         }
      }

      @Override
      public void connect(final SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
         if (promise.setUncancellable() && this.ensureOpen(promise)) {
            try {
               if (EpollSocketChannel.this.connectPromise != null) {
                  throw new IllegalStateException("connection attempt already made");
               }

               boolean wasActive = EpollSocketChannel.this.isActive();
               if (this.doConnect((InetSocketAddress)remoteAddress, (InetSocketAddress)localAddress)) {
                  this.fulfillConnectPromise(promise, wasActive);
               } else {
                  EpollSocketChannel.this.connectPromise = promise;
                  EpollSocketChannel.this.requestedRemoteAddress = remoteAddress;
                  int connectTimeoutMillis = EpollSocketChannel.this.config().getConnectTimeoutMillis();
                  if (connectTimeoutMillis > 0) {
                     EpollSocketChannel.this.connectTimeoutFuture = EpollSocketChannel.this.eventLoop().schedule(new Runnable() {
                        @Override
                        public void run() {
                           ChannelPromise connectPromise = EpollSocketChannel.this.connectPromise;
                           ConnectTimeoutException cause = new ConnectTimeoutException("connection timed out: " + remoteAddress);
                           if (connectPromise != null && connectPromise.tryFailure(cause)) {
                              EpollSocketUnsafe.this.close(EpollSocketUnsafe.this.voidPromise());
                           }
                        }
                     }, connectTimeoutMillis, TimeUnit.MILLISECONDS);
                  }

                  promise.addListener(new ChannelFutureListener() {
                     public void operationComplete(ChannelFuture future) throws Exception {
                        if (future.isCancelled()) {
                           if (EpollSocketChannel.this.connectTimeoutFuture != null) {
                              EpollSocketChannel.this.connectTimeoutFuture.cancel(false);
                           }

                           EpollSocketChannel.this.connectPromise = null;
                           EpollSocketUnsafe.this.close(EpollSocketUnsafe.this.voidPromise());
                        }
                     }
                  });
               }
            } catch (Throwable var6) {
               Throwable t = var6;
               if (var6 instanceof ConnectException) {
                  Throwable newT = new ConnectException(var6.getMessage() + ": " + remoteAddress);
                  newT.setStackTrace(var6.getStackTrace());
                  t = newT;
               }

               this.closeIfClosed();
               promise.tryFailure(t);
            }
         }
      }

      private void fulfillConnectPromise(ChannelPromise promise, boolean wasActive) {
         if (promise != null) {
            EpollSocketChannel.this.active = true;
            boolean promiseSet = promise.trySuccess();
            if (!wasActive && EpollSocketChannel.this.isActive()) {
               EpollSocketChannel.this.pipeline().fireChannelActive();
            }

            if (!promiseSet) {
               this.close(this.voidPromise());
            }
         }
      }

      private void fulfillConnectPromise(ChannelPromise promise, Throwable cause) {
         if (promise != null) {
            promise.tryFailure(cause);
            this.closeIfClosed();
         }
      }

      private void finishConnect() {
         assert EpollSocketChannel.this.eventLoop().inEventLoop();

         boolean connectStillInProgress = false;

         try {
            boolean wasActive = EpollSocketChannel.this.isActive();
            if (!this.doFinishConnect()) {
               connectStillInProgress = true;
               return;
            }

            this.fulfillConnectPromise(EpollSocketChannel.this.connectPromise, wasActive);
         } catch (Throwable var7) {
            Throwable t = var7;
            if (var7 instanceof ConnectException) {
               Throwable newT = new ConnectException(var7.getMessage() + ": " + EpollSocketChannel.this.requestedRemoteAddress);
               newT.setStackTrace(var7.getStackTrace());
               t = newT;
            }

            this.fulfillConnectPromise(EpollSocketChannel.this.connectPromise, t);
         } finally {
            if (EpollSocketChannel.this.connectTimeoutFuture != null) {
               EpollSocketChannel.this.connectTimeoutFuture.cancel(false);
            }

            EpollSocketChannel.this.connectPromise = null;
         }
      }

      @Override
      void epollOutReady() {
         if (EpollSocketChannel.this.connectPromise != null) {
            this.finishConnect();
         } else {
            super.epollOutReady();
         }
      }

      private boolean doConnect(InetSocketAddress remoteAddress, InetSocketAddress localAddress) throws Exception {
         if (localAddress != null) {
            AbstractEpollChannel.checkResolvable(localAddress);
            Native.bind(EpollSocketChannel.this.fd, localAddress.getAddress(), localAddress.getPort());
         }

         boolean success = false;

         try {
            AbstractEpollChannel.checkResolvable(remoteAddress);
            boolean connected = Native.connect(EpollSocketChannel.this.fd, remoteAddress.getAddress(), remoteAddress.getPort());
            EpollSocketChannel.this.remote = remoteAddress;
            EpollSocketChannel.this.local = Native.localAddress(EpollSocketChannel.this.fd);
            if (!connected) {
               EpollSocketChannel.this.setEpollOut();
            }

            success = true;
            return connected;
         } finally {
            if (!success) {
               EpollSocketChannel.this.doClose();
            }
         }
      }

      private boolean doFinishConnect() throws Exception {
         if (Native.finishConnect(EpollSocketChannel.this.fd)) {
            EpollSocketChannel.this.clearEpollOut();
            return true;
         } else {
            EpollSocketChannel.this.setEpollOut();
            return false;
         }
      }

      private int doReadBytes(ByteBuf byteBuf) throws Exception {
         int writerIndex = byteBuf.writerIndex();
         int localReadAmount;
         if (byteBuf.hasMemoryAddress()) {
            localReadAmount = Native.readAddress(EpollSocketChannel.this.fd, byteBuf.memoryAddress(), writerIndex, byteBuf.capacity());
         } else {
            ByteBuffer buf = byteBuf.internalNioBuffer(writerIndex, byteBuf.writableBytes());
            localReadAmount = Native.read(EpollSocketChannel.this.fd, buf, buf.position(), buf.limit());
         }

         if (localReadAmount > 0) {
            byteBuf.writerIndex(writerIndex + localReadAmount);
         }

         return localReadAmount;
      }

      @Override
      void epollRdHupReady() {
         if (EpollSocketChannel.this.isActive()) {
            this.epollInReady();
         } else {
            this.closeOnRead(EpollSocketChannel.this.pipeline());
         }
      }

      @Override
      void epollInReady() {
         ChannelConfig config = EpollSocketChannel.this.config();
         ChannelPipeline pipeline = EpollSocketChannel.this.pipeline();
         ByteBufAllocator allocator = config.getAllocator();
         RecvByteBufAllocator.Handle allocHandle = this.allocHandle;
         if (allocHandle == null) {
            this.allocHandle = allocHandle = config.getRecvByteBufAllocator().newHandle();
         }

         ByteBuf byteBuf = null;
         boolean close = false;

         try {
            int totalReadAmount = 0;

            int localReadAmount;
            int writable;
            do {
               byteBuf = allocHandle.allocate(allocator);
               writable = byteBuf.writableBytes();
               localReadAmount = this.doReadBytes(byteBuf);
               if (localReadAmount <= 0) {
                  byteBuf.release();
                  close = localReadAmount < 0;
                  break;
               }

               this.readPending = false;
               pipeline.fireChannelRead(byteBuf);
               byteBuf = null;
               if (totalReadAmount >= Integer.MAX_VALUE - localReadAmount) {
                  allocHandle.record(totalReadAmount);
                  totalReadAmount = localReadAmount;
               } else {
                  totalReadAmount += localReadAmount;
               }
            } while (localReadAmount >= writable);

            pipeline.fireChannelReadComplete();
            allocHandle.record(totalReadAmount);
            if (close) {
               this.closeOnRead(pipeline);
               close = false;
            }
         } catch (Throwable var13) {
            boolean closed = this.handleReadException(pipeline, byteBuf, var13, close);
            if (!closed) {
               EpollSocketChannel.this.eventLoop().execute(new Runnable() {
                  @Override
                  public void run() {
                     EpollSocketUnsafe.this.epollInReady();
                  }
               });
            }
         } finally {
            if (!config.isAutoRead() && !this.readPending) {
               this.clearEpollIn0();
            }
         }
      }
   }
}
