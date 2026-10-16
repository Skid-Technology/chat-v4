package io.netty.channel.rxtx;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import io.netty.channel.AbstractChannel;
import io.netty.channel.ChannelPromise;
import io.netty.channel.oio.OioByteStreamChannel;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

public class RxtxChannel extends OioByteStreamChannel {
   private static final RxtxDeviceAddress LOCAL_ADDRESS = new RxtxDeviceAddress("localhost");
   private final RxtxChannelConfig config;
   private boolean open = true;
   private RxtxDeviceAddress deviceAddress;
   private SerialPort serialPort;

   public RxtxChannel() {
      super(null);
      this.config = new DefaultRxtxChannelConfig(this);
   }

   public RxtxChannelConfig config() {
      return this.config;
   }

   @Override
   public boolean isOpen() {
      return this.open;
   }

   @Override
   protected AbstractChannel.AbstractUnsafe newUnsafe() {
      return new RxtxChannel.RxtxUnsafe();
   }

   @Override
   protected void doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
      RxtxDeviceAddress remote = (RxtxDeviceAddress)remoteAddress;
      CommPortIdentifier cpi = CommPortIdentifier.getPortIdentifier(remote.value());
      CommPort commPort = cpi.open(this.getClass().getName(), 1000);
      commPort.enableReceiveTimeout(this.config().getOption(RxtxChannelOption.READ_TIMEOUT));
      this.deviceAddress = remote;
      this.serialPort = (SerialPort)commPort;
   }

   protected void doInit() throws Exception {
      this.serialPort
         .setSerialPortParams(
            this.config().getOption(RxtxChannelOption.BAUD_RATE),
            this.config().getOption(RxtxChannelOption.DATA_BITS).value(),
            this.config().getOption(RxtxChannelOption.STOP_BITS).value(),
            this.config().getOption(RxtxChannelOption.PARITY_BIT).value()
         );
      this.serialPort.setDTR(this.config().getOption(RxtxChannelOption.DTR));
      this.serialPort.setRTS(this.config().getOption(RxtxChannelOption.RTS));
      this.activate(this.serialPort.getInputStream(), this.serialPort.getOutputStream());
   }

   public RxtxDeviceAddress localAddress() {
      return (RxtxDeviceAddress)super.localAddress();
   }

   public RxtxDeviceAddress remoteAddress() {
      return (RxtxDeviceAddress)super.remoteAddress();
   }

   protected RxtxDeviceAddress localAddress0() {
      return LOCAL_ADDRESS;
   }

   protected RxtxDeviceAddress remoteAddress0() {
      return this.deviceAddress;
   }

   @Override
   protected void doBind(SocketAddress localAddress) throws Exception {
      throw new UnsupportedOperationException();
   }

   @Override
   protected void doDisconnect() throws Exception {
      this.doClose();
   }

   @Override
   protected void doClose() throws Exception {
      this.open = false;

      try {
         super.doClose();
      } finally {
         if (this.serialPort != null) {
            this.serialPort.removeEventListener();
            this.serialPort.close();
            this.serialPort = null;
         }
      }
   }

   private final class RxtxUnsafe extends AbstractChannel.AbstractUnsafe {
      private RxtxUnsafe() {
      }

      @Override
      public void connect(SocketAddress remoteAddress, SocketAddress localAddress, final ChannelPromise promise) {
         if (promise.setUncancellable() && this.ensureOpen(promise)) {
            try {
               final boolean wasActive = RxtxChannel.this.isActive();
               RxtxChannel.this.doConnect(remoteAddress, localAddress);
               int waitTime = RxtxChannel.this.config().getOption(RxtxChannelOption.WAIT_TIME);
               if (waitTime > 0) {
                  RxtxChannel.this.eventLoop().schedule(new Runnable() {
                     @Override
                     public void run() {
                        try {
                           RxtxChannel.this.doInit();
                           RxtxUnsafe.this.safeSetSuccess(promise);
                           if (!wasActive && RxtxChannel.this.isActive()) {
                              RxtxChannel.this.pipeline().fireChannelActive();
                           }
                        } catch (Throwable var2) {
                           RxtxUnsafe.this.safeSetFailure(promise, var2);
                           RxtxUnsafe.this.closeIfClosed();
                        }
                     }
                  }, waitTime, TimeUnit.MILLISECONDS);
               } else {
                  RxtxChannel.this.doInit();
                  this.safeSetSuccess(promise);
                  if (!wasActive && RxtxChannel.this.isActive()) {
                     RxtxChannel.this.pipeline().fireChannelActive();
                  }
               }
            } catch (Throwable var6) {
               this.safeSetFailure(promise, var6);
               this.closeIfClosed();
            }
         }
      }
   }
}
