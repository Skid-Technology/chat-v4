package gg.vape.utils.render;

import gg.vape.Vape;
import gg.vape.unmap.ImageParser;
import gg.vape.unmap.ImageParser$Format;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;

public class TextureAtlas {
   private int width = 128;
   private final Map<String, TextureAtlasRegion> regions;
   private final int growthStep;
   private int height = 128;
   private final List<Rectangle> occupiedRectangles;
   private BufferedImage image = new BufferedImage(this.width, this.height, 2);
   private GlImageTexture texture;
   private final int padding;

   private BufferedImage addEdgePadding(BufferedImage sourceImage) {
      int paddedWidth = sourceImage.getWidth() + 20;
      int paddedHeight = sourceImage.getHeight() + 20;
      BufferedImage paddedImage = new BufferedImage(paddedWidth, paddedHeight, 2);
      Graphics2D graphics = paddedImage.createGraphics();
      graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
      graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
      graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      graphics.drawImage(sourceImage, 10, 10, null);

      for (int sourceCoordinate = 0; sourceCoordinate < sourceImage.getWidth(); sourceCoordinate++) {
         int edgeColor = sourceImage.getRGB(sourceCoordinate, 0);

         for (int paddingOffset = 0; paddingOffset < 10; paddingOffset++) {
            paddedImage.setRGB(sourceCoordinate + 10, paddingOffset, edgeColor);
         }

         edgeColor = sourceImage.getRGB(sourceCoordinate, sourceImage.getHeight() - 1);

         for (int var9 = 0; var9 < 10; var9++) {
            paddedImage.setRGB(sourceCoordinate + 10, paddedHeight - 1 - var9, edgeColor);
         }
      }

      for (int var15 = 0; var15 < sourceImage.getHeight(); var15++) {
         int edgeColor = sourceImage.getRGB(0, var15);

         for (int paddingOffset = 0; paddingOffset < 10; paddingOffset++) {
            paddedImage.setRGB(paddingOffset, var15 + 10, edgeColor);
         }

         edgeColor = sourceImage.getRGB(sourceImage.getWidth() - 1, var15);

         for (int var11 = 0; var11 < 10; var11++) {
            paddedImage.setRGB(paddedWidth - 1 - var11, var15 + 10, edgeColor);
         }
      }

      graphics.dispose();
      return paddedImage;
   }

   private static int nextPowerOfTwo(int value) {
      int result = 1;

      while (result < value) {
         result *= 2;
      }

      return result;
   }

   private int[] findAvailablePosition(int imageWidth, int imageHeight) {
      int allocationWidth = imageWidth + 10;
      int allocationHeight = imageHeight + 10;

      for (int y = 0; y <= this.height - allocationHeight; y++) {
         for (int x = 0; x <= this.width - allocationWidth; x++) {
            Rectangle candidate = new Rectangle(x, y, allocationWidth, allocationHeight);
            boolean available = true;

            for (Rectangle occupied : this.occupiedRectangles) {
               if (candidate.intersects(occupied)) {
                  available = false;
                  break;
               }
            }

            if (available) {
               this.occupiedRectangles.add(candidate);
               return new int[]{x, y};
            }
         }
      }

      return null;
   }

   private void uploadTexture() {
      if (this.texture != null) {
         this.texture.delete();
      }

      int[] pixels = new int[this.image.getHeight() * this.image.getWidth()];
      this.image.getRGB(0, 0, this.image.getWidth(), this.image.getHeight(), pixels, 0, this.image.getWidth());
      ByteBuffer pixelBuffer = BufferUtils.createByteBuffer(this.image.getWidth() * this.image.getHeight() * 4);

      for (int y = 0; y < this.image.getHeight(); y++) {
         for (int x = 0; x < this.image.getWidth(); x++) {
            int color = pixels[y * this.image.getWidth() + x];
            pixelBuffer.put((byte)(color >> 16 & 0xFF));
            pixelBuffer.put((byte)(color >> 8 & 0xFF));
            pixelBuffer.put((byte)(color & 0xFF));
            pixelBuffer.put((byte)(color >> 24 & 0xFF));
         }
      }

      ((Buffer)pixelBuffer).flip();
      this.texture = GlImageTexture.create(this.width, this.height, pixelBuffer, 6408, 9987, 10496);
      ((Buffer)pixelBuffer).clear();
   }

   public GlImageTexture getTexture() {
      return this.texture;
   }

   public TextureAtlas() {
      this.growthStep = 128;
      this.padding = 10;
      this.regions = new HashMap<>();
      this.occupiedRectangles = new ArrayList<>();
   }

   private void expand() {
      int expandedWidth = nextPowerOfTwo(this.width + 128);
      int expandedHeight = nextPowerOfTwo(this.height + 128);
      BufferedImage expandedImage = new BufferedImage(expandedWidth, expandedHeight, 2);
      Graphics2D graphics = expandedImage.createGraphics();
      graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      graphics.drawImage(this.image, 0, 0, null);
      graphics.dispose();
      this.image = expandedImage;
      this.width = expandedWidth;
      this.height = expandedHeight;

      for (TextureAtlasRegion region : this.regions.values()) {
         region.minU = (float)region.x / this.width;
         region.minV = (float)region.y / this.height;
         region.maxU = (float)(region.x + region.width) / this.width;
         region.maxV = (float)(region.y + region.height) / this.height;
      }
   }

   public void saveDebugImage(String path) {
      try {
         File file = new File(path);
         ImageIO.write(this.image, "PNG", file);
      } catch (Exception var3) {
         Vape.logThrowable(var3);
      }
   }

   public void addImage(String regionName, byte[] imageData, boolean whiteFormat) {
      try {
         if (this.regions.containsKey(regionName)) {
            return;
         }

         ImageParser parser = new ImageParser(new ByteArrayInputStream(imageData));
         int imageHeight = parser.getHeight();
         int imageWidth = parser.getWidth();
         int bytesPerPixel = 4;
         ByteBuffer pixelBuffer = BufferUtils.createByteBuffer(4 * imageHeight * imageWidth);
         parser.decode(pixelBuffer, imageWidth * 4, whiteFormat ? ImageParser$Format.WHITE : ImageParser$Format.RGBA);
         ((Buffer)pixelBuffer).flip();
         byte[] rgbaBytes = new byte[pixelBuffer.remaining()];
         pixelBuffer.get(rgbaBytes);
         BufferedImage decodedImage = this.decodeRgba(rgbaBytes, imageWidth, imageHeight);
         decodedImage = this.addEdgePadding(decodedImage);
         imageWidth += 20;
         imageHeight += 20;

         int[] position;
         for (position = this.findAvailablePosition(imageWidth, imageHeight); position == null; position = this.findAvailablePosition(imageWidth, imageHeight)) {
            this.expand();
         }

         Graphics2D graphics = this.image.createGraphics();
         graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
         graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
         graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
         graphics.drawImage(decodedImage, position[0], position[1], null);
         graphics.dispose();
         this.registerRegion(regionName, position[0] + 10, position[1] + 10, imageWidth - 20, imageHeight - 20);
         this.uploadTexture();
      } catch (Exception var13) {
         Vape.logThrowable(var13);
      }
   }

   private void registerRegion(String regionName, int x, int y, int width, int height) {
      float minU = (float)x / this.width;
      float minV = (float)y / this.height;
      float maxU = (float)(x + width) / this.width;
      float maxV = (float)(y + height) / this.height;
      this.regions.put(regionName, new TextureAtlasRegion(x, y, width, height, minU, minV, maxU, maxV));
   }

   public TextureAtlasRegion getRegion(String regionName) {
      return this.regions.get(regionName);
   }

   private static Exception identityException(Exception exception) {
      return exception;
   }

   private BufferedImage decodeRgba(byte[] rgbaBytes, int width, int height) {
      if (rgbaBytes.length != width * height * 4) {
         throw new IllegalArgumentException("Unexpected image data length for decoded texture");
      } else {
         BufferedImage decodedImage = new BufferedImage(width, height, 2);
         int byteIndex = 0;

         for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
               int red = rgbaBytes[byteIndex++] & 255;
               int green = rgbaBytes[byteIndex++] & 255;
               int blue = rgbaBytes[byteIndex++] & 255;
               int alpha = rgbaBytes[byteIndex++] & 255;
               decodedImage.setRGB(x, y, alpha << 24 | red << 16 | green << 8 | blue);
            }
         }

         return decodedImage;
      }
   }
}
