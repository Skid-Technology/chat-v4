package gg.vape.ui.click.frame.impl.profile;

import gg.vape.Vape;
import gg.vape.unmap.ImageParser$Format;
import gg.vape.utils.render.GlImageTexture;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import javax.imageio.ImageIO;
import org.jetbrains.annotations.Nullable;

public class PublicProfileUserAvatarTextureCache {
   private static final long e = -7575910208031915729L;
   private final Map<Long, GlImageTexture> Z = new ConcurrentHashMap<>();
   private static final PublicProfileUserAvatarTextureCache Y = new PublicProfileUserAvatarTextureCache();

   private InputStream v(BufferedImage bufferedImage) {
      try {
         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
         ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
         return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
      } catch (IOException var3) {
         Vape.logThrowable(var3);
         return null;
      }
   }

   private BufferedImage W(long l, int n, int n2) {
      BufferedImage bufferedImage = new BufferedImage(n, n2, 1);
      Random random = new Random(this.v(l));
      float f = random.nextFloat();
      float f2 = 0.8F;
      float f3 = 0.8F;
      int n3 = Color.HSBtoRGB(f, f2, f3);
      if (l != -1L) {
         for (int i = 0; i < n2; i++) {
            for (int j = 0; j < n / 2; j++) {
               boolean bl = random.nextBoolean();
               int n4 = bl ? n3 : Color.WHITE.getRGB();
               bufferedImage.setRGB(j, i, n4);
               bufferedImage.setRGB(n - j - 1, i, n4);
            }
         }
      }

      return bufferedImage;
   }

   private long v(long l) {
      ByteBuffer byteBuffer = ByteBuffer.allocate(8);
      byteBuffer.putLong(l);
      byte[] byArray = byteBuffer.array();

      MessageDigest messageDigest;
      try {
         messageDigest = MessageDigest.getInstance("SHA-256");
      } catch (NoSuchAlgorithmException var8) {
         Vape.logThrowable(var8);
         return 0L;
      }

      byte[] byArray2 = messageDigest.digest(byArray);
      ByteBuffer byteBuffer2 = ByteBuffer.wrap(byArray2);
      return byteBuffer2.getLong();
   }

   private static Exception a(Exception exception) {
      return exception;
   }

   @Nullable
   public GlImageTexture s(long l) {
      GlImageTexture glImageTexture = this.Z.get(l);
      if (glImageTexture != null) {
         return glImageTexture;
      } else {
         try {
            BufferedImage bufferedImage = this.W(l, 6, 6);
            InputStream inputStream = this.v(bufferedImage);
            GlImageTexture glImageTexture2 = new GlImageTexture(inputStream, 9728, 33071, ImageParser$Format.RGBA);
            this.Z.put(l, glImageTexture2);
            return glImageTexture2;
         } catch (Exception var7) {
            Vape.logThrowable(var7);
            return null;
         }
      }
   }

   public static PublicProfileUserAvatarTextureCache q() {
      return Y;
   }
}
