package io.netty.handler.ssl;

import io.netty.buffer.ByteBufAllocator;
import java.io.File;
import java.util.List;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManagerFactory;

public abstract class SslContext {
   public static SslProvider defaultServerProvider() {
      return OpenSsl.isAvailable() ? SslProvider.OPENSSL : SslProvider.JDK;
   }

   public static SslProvider defaultClientProvider() {
      return SslProvider.JDK;
   }

   public static SslContext newServerContext(File certChainFile, File keyFile) throws SSLException {
      return newServerContext(null, certChainFile, keyFile, null, null, null, 0L, 0L);
   }

   public static SslContext newServerContext(File certChainFile, File keyFile, String keyPassword) throws SSLException {
      return newServerContext(null, certChainFile, keyFile, keyPassword, null, null, 0L, 0L);
   }

   public static SslContext newServerContext(
      File certChainFile,
      File keyFile,
      String keyPassword,
      Iterable<String> ciphers,
      Iterable<String> nextProtocols,
      long sessionCacheSize,
      long sessionTimeout
   ) throws SSLException {
      return newServerContext(null, certChainFile, keyFile, keyPassword, ciphers, nextProtocols, sessionCacheSize, sessionTimeout);
   }

   public static SslContext newServerContext(SslProvider provider, File certChainFile, File keyFile) throws SSLException {
      return newServerContext(provider, certChainFile, keyFile, null, null, null, 0L, 0L);
   }

   public static SslContext newServerContext(SslProvider provider, File certChainFile, File keyFile, String keyPassword) throws SSLException {
      return newServerContext(provider, certChainFile, keyFile, keyPassword, null, null, 0L, 0L);
   }

   public static SslContext newServerContext(
      SslProvider provider,
      File certChainFile,
      File keyFile,
      String keyPassword,
      Iterable<String> ciphers,
      Iterable<String> nextProtocols,
      long sessionCacheSize,
      long sessionTimeout
   ) throws SSLException {
      if (provider == null) {
         provider = OpenSsl.isAvailable() ? SslProvider.OPENSSL : SslProvider.JDK;
      }

      switch (provider) {
         case JDK:
            return new JdkSslServerContext(certChainFile, keyFile, keyPassword, ciphers, nextProtocols, sessionCacheSize, sessionTimeout);
         case OPENSSL:
            return new OpenSslServerContext(certChainFile, keyFile, keyPassword, ciphers, nextProtocols, sessionCacheSize, sessionTimeout);
         default:
            throw new Error(provider.toString());
      }
   }

   public static SslContext newClientContext() throws SSLException {
      return newClientContext(null, null, null, null, null, 0L, 0L);
   }

   public static SslContext newClientContext(File certChainFile) throws SSLException {
      return newClientContext(null, certChainFile, null, null, null, 0L, 0L);
   }

   public static SslContext newClientContext(TrustManagerFactory trustManagerFactory) throws SSLException {
      return newClientContext(null, null, trustManagerFactory, null, null, 0L, 0L);
   }

   public static SslContext newClientContext(File certChainFile, TrustManagerFactory trustManagerFactory) throws SSLException {
      return newClientContext(null, certChainFile, trustManagerFactory, null, null, 0L, 0L);
   }

   public static SslContext newClientContext(
      File certChainFile,
      TrustManagerFactory trustManagerFactory,
      Iterable<String> ciphers,
      Iterable<String> nextProtocols,
      long sessionCacheSize,
      long sessionTimeout
   ) throws SSLException {
      return newClientContext(null, certChainFile, trustManagerFactory, ciphers, nextProtocols, sessionCacheSize, sessionTimeout);
   }

   public static SslContext newClientContext(SslProvider provider) throws SSLException {
      return newClientContext(provider, null, null, null, null, 0L, 0L);
   }

   public static SslContext newClientContext(SslProvider provider, File certChainFile) throws SSLException {
      return newClientContext(provider, certChainFile, null, null, null, 0L, 0L);
   }

   public static SslContext newClientContext(SslProvider provider, TrustManagerFactory trustManagerFactory) throws SSLException {
      return newClientContext(provider, null, trustManagerFactory, null, null, 0L, 0L);
   }

   public static SslContext newClientContext(SslProvider provider, File certChainFile, TrustManagerFactory trustManagerFactory) throws SSLException {
      return newClientContext(provider, certChainFile, trustManagerFactory, null, null, 0L, 0L);
   }

   public static SslContext newClientContext(
      SslProvider provider,
      File certChainFile,
      TrustManagerFactory trustManagerFactory,
      Iterable<String> ciphers,
      Iterable<String> nextProtocols,
      long sessionCacheSize,
      long sessionTimeout
   ) throws SSLException {
      if (provider != null && provider != SslProvider.JDK) {
         throw new SSLException("client context unsupported for: " + provider);
      } else {
         return new JdkSslClientContext(certChainFile, trustManagerFactory, ciphers, nextProtocols, sessionCacheSize, sessionTimeout);
      }
   }

   SslContext() {
   }

   public final boolean isServer() {
      return !this.isClient();
   }

   public abstract boolean isClient();

   public abstract List<String> cipherSuites();

   public abstract long sessionCacheSize();

   public abstract long sessionTimeout();

   public abstract List<String> nextProtocols();

   public abstract SSLEngine newEngine(ByteBufAllocator var1);

   public abstract SSLEngine newEngine(ByteBufAllocator var1, String var2, int var3);

   public final SslHandler newHandler(ByteBufAllocator alloc) {
      return newHandler(this.newEngine(alloc));
   }

   public final SslHandler newHandler(ByteBufAllocator alloc, String peerHost, int peerPort) {
      return newHandler(this.newEngine(alloc, peerHost, peerPort));
   }

   private static SslHandler newHandler(SSLEngine engine) {
      return new SslHandler(engine);
   }
}
