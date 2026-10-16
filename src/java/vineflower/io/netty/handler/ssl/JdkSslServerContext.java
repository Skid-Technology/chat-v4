package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.EncryptedPrivateKeyInfo;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSessionContext;

public final class JdkSslServerContext extends JdkSslContext {
   private final SSLContext ctx;
   private final List<String> nextProtocols;

   public JdkSslServerContext(File certChainFile, File keyFile) throws SSLException {
      this(certChainFile, keyFile, null);
   }

   public JdkSslServerContext(File certChainFile, File keyFile, String keyPassword) throws SSLException {
      this(certChainFile, keyFile, keyPassword, null, null, 0L, 0L);
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public JdkSslServerContext(
      File certChainFile,
      File keyFile,
      String keyPassword,
      Iterable<String> ciphers,
      Iterable<String> nextProtocols,
      long sessionCacheSize,
      long sessionTimeout
   ) throws SSLException {
      super(ciphers);
      if (certChainFile == null) {
         throw new NullPointerException("certChainFile");
      } else if (keyFile == null) {
         throw new NullPointerException("keyFile");
      } else {
         if (keyPassword == null) {
            keyPassword = "";
         }

         if (nextProtocols != null && nextProtocols.iterator().hasNext()) {
            if (!JettyNpnSslEngine.isAvailable()) {
               throw new SSLException("NPN/ALPN unsupported: " + nextProtocols);
            }

            List<String> list = new ArrayList<>();

            for (String p : nextProtocols) {
               if (p == null) {
                  break;
               }

               list.add(p);
            }

            this.nextProtocols = Collections.unmodifiableList(list);
         } else {
            this.nextProtocols = Collections.emptyList();
         }

         String algorithm = Security.getProperty("ssl.KeyManagerFactory.algorithm");
         if (algorithm == null) {
            algorithm = "SunX509";
         }

         try {
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(null, null);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            KeyFactory rsaKF = KeyFactory.getInstance("RSA");
            KeyFactory dsaKF = KeyFactory.getInstance("DSA");
            ByteBuf encodedKeyBuf = PemReader.readPrivateKey(keyFile);
            byte[] encodedKey = new byte[encodedKeyBuf.readableBytes()];
            encodedKeyBuf.readBytes(encodedKey).release();
            char[] keyPasswordChars = keyPassword.toCharArray();
            PKCS8EncodedKeySpec encodedKeySpec = generateKeySpec(keyPasswordChars, encodedKey);

            PrivateKey key;
            try {
               key = rsaKF.generatePrivate(encodedKeySpec);
            } catch (InvalidKeySpecException var35) {
               key = dsaKF.generatePrivate(encodedKeySpec);
            }

            List<Certificate> certChain = new ArrayList<>();
            ByteBuf[] certs = PemReader.readCertificates(certChainFile);
            boolean var34 = false /* VF: Semaphore variable */;

            try {
               var34 = true;

               for (ByteBuf buf : certs) {
                  certChain.add(cf.generateCertificate(new ByteBufInputStream(buf)));
               }

               var34 = false;
            } finally {
               if (var34) {
                  for (ByteBuf buf : certs) {
                     buf.release();
                  }
               }
            }

            for (ByteBuf buf : certs) {
               buf.release();
            }

            ks.setKeyEntry("key", key, keyPasswordChars, certChain.toArray(new Certificate[certChain.size()]));
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm);
            kmf.init(ks, keyPasswordChars);
            this.ctx = SSLContext.getInstance("TLS");
            this.ctx.init(kmf.getKeyManagers(), null, null);
            SSLSessionContext sessCtx = this.ctx.getServerSessionContext();
            if (sessionCacheSize > 0L) {
               sessCtx.setSessionCacheSize((int)Math.min(sessionCacheSize, 2147483647L));
            }

            if (sessionTimeout > 0L) {
               sessCtx.setSessionTimeout((int)Math.min(sessionTimeout, 2147483647L));
            }
         } catch (Exception var37) {
            throw new SSLException("failed to initialize the server-side SSL context", var37);
         }
      }
   }

   @Override
   public boolean isClient() {
      return false;
   }

   @Override
   public List<String> nextProtocols() {
      return this.nextProtocols;
   }

   @Override
   public SSLContext context() {
      return this.ctx;
   }

   private static PKCS8EncodedKeySpec generateKeySpec(char[] password, byte[] key) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException {
      if (password != null && password.length != 0) {
         EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = new EncryptedPrivateKeyInfo(key);
         SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(encryptedPrivateKeyInfo.getAlgName());
         PBEKeySpec pbeKeySpec = new PBEKeySpec(password);
         SecretKey pbeKey = keyFactory.generateSecret(pbeKeySpec);
         Cipher cipher = Cipher.getInstance(encryptedPrivateKeyInfo.getAlgName());
         cipher.init(2, pbeKey, encryptedPrivateKeyInfo.getAlgParameters());
         return encryptedPrivateKeyInfo.getKeySpec(cipher);
      } else {
         return new PKCS8EncodedKeySpec(key);
      }
   }
}
