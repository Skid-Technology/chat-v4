package io.netty.bootstrap;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.internal.StringUtil;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractBootstrap<B extends AbstractBootstrap<B, C>, C extends Channel> implements Cloneable {
   private volatile EventLoopGroup group;
   private volatile ChannelFactory<? extends C> channelFactory;
   private volatile SocketAddress localAddress;
   private final Map<ChannelOption<?>, Object> options = new LinkedHashMap<>();
   private final Map<AttributeKey<?>, Object> attrs = new LinkedHashMap<>();
   private volatile ChannelHandler handler;

   AbstractBootstrap() {
   }

   AbstractBootstrap(AbstractBootstrap<B, C> bootstrap) {
      this.group = bootstrap.group;
      this.channelFactory = bootstrap.channelFactory;
      this.handler = bootstrap.handler;
      this.localAddress = bootstrap.localAddress;
      synchronized (bootstrap.options) {
         this.options.putAll(bootstrap.options);
      }

      synchronized (bootstrap.attrs) {
         this.attrs.putAll(bootstrap.attrs);
      }
   }

   public B group(EventLoopGroup group) {
      if (group == null) {
         throw new NullPointerException("group");
      } else if (this.group != null) {
         throw new IllegalStateException("group set already");
      } else {
         this.group = group;
         return (B)this;
      }
   }

   public B channel(Class<? extends C> channelClass) {
      if (channelClass == null) {
         throw new NullPointerException("channelClass");
      } else {
         return this.channelFactory(new AbstractBootstrap.BootstrapChannelFactory<>(channelClass));
      }
   }

   public B channelFactory(ChannelFactory<? extends C> channelFactory) {
      if (channelFactory == null) {
         throw new NullPointerException("channelFactory");
      } else if (this.channelFactory != null) {
         throw new IllegalStateException("channelFactory set already");
      } else {
         this.channelFactory = channelFactory;
         return (B)this;
      }
   }

   public B localAddress(SocketAddress localAddress) {
      this.localAddress = localAddress;
      return (B)this;
   }

   public B localAddress(int inetPort) {
      return this.localAddress(new InetSocketAddress(inetPort));
   }

   public B localAddress(String inetHost, int inetPort) {
      return this.localAddress(new InetSocketAddress(inetHost, inetPort));
   }

   public B localAddress(InetAddress inetHost, int inetPort) {
      return this.localAddress(new InetSocketAddress(inetHost, inetPort));
   }

   public <T> B option(ChannelOption<T> option, T value) {
      if (option == null) {
         throw new NullPointerException("option");
      } else {
         if (value == null) {
            synchronized (this.options) {
               this.options.remove(option);
            }
         } else {
            synchronized (this.options) {
               this.options.put(option, value);
            }
         }

         return (B)this;
      }
   }

   public <T> B attr(AttributeKey<T> key, T value) {
      if (key == null) {
         throw new NullPointerException("key");
      } else {
         if (value == null) {
            synchronized (this.attrs) {
               this.attrs.remove(key);
            }
         } else {
            synchronized (this.attrs) {
               this.attrs.put(key, value);
            }
         }

         return (B)this;
      }
   }

   public B validate() {
      if (this.group == null) {
         throw new IllegalStateException("group not set");
      } else if (this.channelFactory == null) {
         throw new IllegalStateException("channel or channelFactory not set");
      } else {
         return (B)this;
      }
   }

   public abstract B clone();

   public ChannelFuture register() {
      this.validate();
      return this.initAndRegister();
   }

   public ChannelFuture bind() {
      this.validate();
      SocketAddress localAddress = this.localAddress;
      if (localAddress == null) {
         throw new IllegalStateException("localAddress not set");
      } else {
         return this.doBind(localAddress);
      }
   }

   public ChannelFuture bind(int inetPort) {
      return this.bind(new InetSocketAddress(inetPort));
   }

   public ChannelFuture bind(String inetHost, int inetPort) {
      return this.bind(new InetSocketAddress(inetHost, inetPort));
   }

   public ChannelFuture bind(InetAddress inetHost, int inetPort) {
      return this.bind(new InetSocketAddress(inetHost, inetPort));
   }

   public ChannelFuture bind(SocketAddress localAddress) {
      this.validate();
      if (localAddress == null) {
         throw new NullPointerException("localAddress");
      } else {
         return this.doBind(localAddress);
      }
   }

   private ChannelFuture doBind(final SocketAddress localAddress) {
      final ChannelFuture regFuture = this.initAndRegister();
      final Channel channel = regFuture.channel();
      if (regFuture.cause() != null) {
         return regFuture;
      } else {
         final ChannelPromise promise;
         if (regFuture.isDone()) {
            promise = channel.newPromise();
            doBind0(regFuture, channel, localAddress, promise);
         } else {
            promise = new AbstractBootstrap.PendingRegistrationPromise(channel);
            regFuture.addListener(new ChannelFutureListener() {
               public void operationComplete(ChannelFuture future) throws Exception {
                  AbstractBootstrap.doBind0(regFuture, channel, localAddress, promise);
               }
            });
         }

         return promise;
      }
   }

   final ChannelFuture initAndRegister() {
      Channel channel = this.channelFactory().newChannel();

      try {
         this.init(channel);
      } catch (Throwable var3) {
         channel.unsafe().closeForcibly();
         return new DefaultChannelPromise(channel, GlobalEventExecutor.INSTANCE).setFailure(var3);
      }

      ChannelFuture regFuture = this.group().register(channel);
      if (regFuture.cause() != null) {
         if (channel.isRegistered()) {
            channel.close();
         } else {
            channel.unsafe().closeForcibly();
         }
      }

      return regFuture;
   }

   abstract void init(Channel var1) throws Exception;

   private static void doBind0(final ChannelFuture regFuture, final Channel channel, final SocketAddress localAddress, final ChannelPromise promise) {
      channel.eventLoop().execute(new Runnable() {
         @Override
         public void run() {
            if (regFuture.isSuccess()) {
               channel.bind(localAddress, promise).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            } else {
               promise.setFailure(regFuture.cause());
            }
         }
      });
   }

   public B handler(ChannelHandler handler) {
      if (handler == null) {
         throw new NullPointerException("handler");
      } else {
         this.handler = handler;
         return (B)this;
      }
   }

   final SocketAddress localAddress() {
      return this.localAddress;
   }

   final ChannelFactory<? extends C> channelFactory() {
      return this.channelFactory;
   }

   final ChannelHandler handler() {
      return this.handler;
   }

   public final EventLoopGroup group() {
      return this.group;
   }

   final Map<ChannelOption<?>, Object> options() {
      return this.options;
   }

   final Map<AttributeKey<?>, Object> attrs() {
      return this.attrs;
   }

   @Override
   public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append(StringUtil.simpleClassName(this));
      buf.append('(');
      if (this.group != null) {
         buf.append("group: ");
         buf.append(StringUtil.simpleClassName(this.group));
         buf.append(", ");
      }

      if (this.channelFactory != null) {
         buf.append("channelFactory: ");
         buf.append(this.channelFactory);
         buf.append(", ");
      }

      if (this.localAddress != null) {
         buf.append("localAddress: ");
         buf.append(this.localAddress);
         buf.append(", ");
      }

      synchronized (this.options) {
         if (!this.options.isEmpty()) {
            buf.append("options: ");
            buf.append(this.options);
            buf.append(", ");
         }
      }

      synchronized (this.attrs) {
         if (!this.attrs.isEmpty()) {
            buf.append("attrs: ");
            buf.append(this.attrs);
            buf.append(", ");
         }
      }

      if (this.handler != null) {
         buf.append("handler: ");
         buf.append(this.handler);
         buf.append(", ");
      }

      if (buf.charAt(buf.length() - 1) == '(') {
         buf.append(')');
      } else {
         buf.setCharAt(buf.length() - 2, ')');
         buf.setLength(buf.length() - 1);
      }

      return buf.toString();
   }

   private static final class BootstrapChannelFactory<T extends Channel> implements ChannelFactory<T> {
      private final Class<? extends T> clazz;

      BootstrapChannelFactory(Class<? extends T> clazz) {
         this.clazz = clazz;
      }

      @Override
      public T newChannel() {
         try {
            return (T)this.clazz.newInstance();
         } catch (Throwable var2) {
            throw new ChannelException("Unable to create Channel from class " + this.clazz, var2);
         }
      }

      @Override
      public String toString() {
         return StringUtil.simpleClassName(this.clazz) + ".class";
      }
   }

   private static final class PendingRegistrationPromise extends DefaultChannelPromise {
      private PendingRegistrationPromise(Channel channel) {
         super(channel);
      }

      @Override
      protected EventExecutor executor() {
         return (EventExecutor)(this.channel().isRegistered() ? super.executor() : GlobalEventExecutor.INSTANCE);
      }
   }
}
