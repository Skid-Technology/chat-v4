package io.netty.handler.ssl;

import io.netty.buffer.ByteBufAllocator;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import org.apache.tomcat.jni.Pool;
import org.apache.tomcat.jni.SSL;
import org.apache.tomcat.jni.SSLContext;

public final class OpenSslServerContext extends SslContext {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(OpenSslServerContext.class);
   private static final List<String> DEFAULT_CIPHERS;
   private final long aprPool;
   private final List<String> ciphers = new ArrayList<>();
   private final List<String> unmodifiableCiphers = Collections.unmodifiableList(this.ciphers);
   private final long sessionCacheSize;
   private final long sessionTimeout;
   private final List<String> nextProtocols;
   private final long ctx;
   private final OpenSslSessionStats stats;

   public OpenSslServerContext(File certChainFile, File keyFile) throws SSLException {
      this(certChainFile, keyFile, null);
   }

   public OpenSslServerContext(File certChainFile, File keyFile, String keyPassword) throws SSLException {
      this(certChainFile, keyFile, keyPassword, null, null, 0L, 0L);
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public OpenSslServerContext(
      File certChainFile,
      File keyFile,
      String keyPassword,
      Iterable<String> ciphers,
      Iterable<String> nextProtocols,
      long sessionCacheSize,
      long sessionTimeout
   ) throws SSLException {
      OpenSsl.ensureAvailability();
      if (certChainFile == null) {
         throw new NullPointerException("certChainFile");
      } else if (!certChainFile.isFile()) {
         throw new IllegalArgumentException("certChainFile is not a file: " + certChainFile);
      } else if (keyFile == null) {
         throw new NullPointerException("keyPath");
      } else if (!keyFile.isFile()) {
         throw new IllegalArgumentException("keyPath is not a file: " + keyFile);
      } else {
         if (ciphers == null) {
            ciphers = DEFAULT_CIPHERS;
         }

         if (keyPassword == null) {
            keyPassword = "";
         }

         if (nextProtocols == null) {
            nextProtocols = Collections.emptyList();
         }

         for (String c : ciphers) {
            if (c == null) {
               break;
            }

            this.ciphers.add(c);
         }

         List<String> nextProtoList = new ArrayList<>();

         for (String p : nextProtocols) {
            if (p == null) {
               break;
            }

            nextProtoList.add(p);
         }

         this.nextProtocols = Collections.unmodifiableList(nextProtoList);
         this.aprPool = Pool.create(0L);
         boolean success = false;
         boolean var25 = false /* VF: Semaphore variable */;

         try {
            var25 = true;
            synchronized (OpenSslServerContext.class) {
               try {
                  this.ctx = SSLContext.make(this.aprPool, 6, 1);
               } catch (Exception var26) {
                  throw new SSLException("failed to create an SSL_CTX", var26);
               }

               SSLContext.setOptions(this.ctx, 4095);
               SSLContext.setOptions(this.ctx, 16777216);
               SSLContext.setOptions(this.ctx, 4194304);
               SSLContext.setOptions(this.ctx, 524288);
               SSLContext.setOptions(this.ctx, 1048576);
               SSLContext.setOptions(this.ctx, 65536);

               try {
                  StringBuilder cipherBuf = new StringBuilder();

                  for (String c : this.ciphers) {
                     cipherBuf.append(c);
                     cipherBuf.append(':');
                  }

                  cipherBuf.setLength(cipherBuf.length() - 1);
                  SSLContext.setCipherSuite(this.ctx, cipherBuf.toString());
               } catch (SSLException var27) {
                  throw var27;
               } catch (Exception var28) {
                  throw new SSLException("failed to set cipher suite: " + this.ciphers, var28);
               }

               SSLContext.setVerify(this.ctx, 0, 10);

               try {
                  if (!SSLContext.setCertificate(this.ctx, certChainFile.getPath(), keyFile.getPath(), keyPassword, 0)) {
                     throw new SSLException("failed to set certificate: " + certChainFile + " and " + keyFile + " (" + SSL.getLastError() + ')');
                  }
               } catch (SSLException var29) {
                  throw var29;
               } catch (Exception var30) {
                  throw new SSLException("failed to set certificate: " + certChainFile + " and " + keyFile, var30);
               }

               if (!SSLContext.setCertificateChainFile(this.ctx, certChainFile.getPath(), true)) {
                  String error = SSL.getLastError();
                  if (!error.startsWith("error:00000000:")) {
                     throw new SSLException("failed to set certificate chain: " + certChainFile + " (" + SSL.getLastError() + ')');
                  }
               }

               if (!nextProtoList.isEmpty()) {
                  StringBuilder nextProtocolBuf = new StringBuilder();

                  for (String p : nextProtoList) {
                     nextProtocolBuf.append(p);
                     nextProtocolBuf.append(',');
                  }

                  nextProtocolBuf.setLength(nextProtocolBuf.length() - 1);
                  SSLContext.setNextProtos(this.ctx, nextProtocolBuf.toString());
               }

               if (sessionCacheSize > 0L) {
                  this.sessionCacheSize = sessionCacheSize;
                  SSLContext.setSessionCacheSize(this.ctx, sessionCacheSize);
               } else {
                  this.sessionCacheSize = sessionCacheSize = SSLContext.setSessionCacheSize(this.ctx, 20480L);
                  SSLContext.setSessionCacheSize(this.ctx, sessionCacheSize);
               }

               if (sessionTimeout > 0L) {
                  this.sessionTimeout = sessionTimeout;
                  SSLContext.setSessionCacheTimeout(this.ctx, sessionTimeout);
               } else {
                  this.sessionTimeout = sessionTimeout = SSLContext.setSessionCacheTimeout(this.ctx, 300L);
                  SSLContext.setSessionCacheTimeout(this.ctx, sessionTimeout);
               }
            }

            success = true;
            var25 = false;
         } finally {
            if (var25) {
               this.destroyPools();
            }
         }

         this.stats = new OpenSslSessionStats(this.ctx);
      }
   }

   @Override
   public boolean isClient() {
      return false;
   }

   @Override
   public List<String> cipherSuites() {
      return this.unmodifiableCiphers;
   }

   @Override
   public long sessionCacheSize() {
      return this.sessionCacheSize;
   }

   @Override
   public long sessionTimeout() {
      return this.sessionTimeout;
   }

   @Override
   public List<String> nextProtocols() {
      return this.nextProtocols;
   }

   public long context() {
      return this.ctx;
   }

   public OpenSslSessionStats stats() {
      return this.stats;
   }

   @Override
   public SSLEngine newEngine(ByteBufAllocator alloc) {
      return this.nextProtocols.isEmpty()
         ? new OpenSslEngine(this.ctx, alloc, null)
         : new OpenSslEngine(this.ctx, alloc, this.nextProtocols.get(this.nextProtocols.size() - 1));
   }

   @Override
   public SSLEngine newEngine(ByteBufAllocator alloc, String peerHost, int peerPort) {
      throw new UnsupportedOperationException();
   }

   public void setTicketKeys(byte[] keys) {
      if (keys == null) {
         throw new NullPointerException("keys");
      } else {
         SSLContext.setSessionTicketKeys(this.ctx, keys);
      }
   }

   @Override
   protected void finalize() throws Throwable {
      super.finalize();
      synchronized (OpenSslServerContext.class) {
         if (this.ctx != 0L) {
            SSLContext.free(this.ctx);
         }
      }

      this.destroyPools();
   }

   private void destroyPools() {
      if (this.aprPool != 0L) {
         Pool.destroy(this.aprPool);
      }
   }

   static {
      List<String> ciphers = new ArrayList<>();
      Collections.addAll(
         ciphers,
         "ECDHE-RSA-AES128-GCM-SHA256",
         "ECDHE-RSA-RC4-SHA",
         "ECDHE-RSA-AES128-SHA",
         "ECDHE-RSA-AES256-SHA",
         "AES128-GCM-SHA256",
         "RC4-SHA",
         "RC4-MD5",
         "AES128-SHA",
         "AES256-SHA",
         "DES-CBC3-SHA"
      );
      DEFAULT_CIPHERS = Collections.unmodifiableList(ciphers);
      if (logger.isDebugEnabled()) {
         logger.debug("Default cipher suite (OpenSSL): " + ciphers);
      }
   }
}
