package gg.vape.utils.render;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class RemoteImageTextureLoader {
   private static String legacyMarker;

   public static void setLegacyMarker(String legacyMarker) {
      RemoteImageTextureLoader.legacyMarker = legacyMarker;
   }

   public static String getLegacyMarker() {
      return legacyMarker;
   }

   public static byte[] download(String urlString) {
      try {
         URL url = new URL(urlString);
         HttpURLConnection connection = (HttpURLConnection)url.openConnection();
         connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
         connection.setRequestProperty("Accept-Encoding", "gzip");
         connection.setConnectTimeout(10000);
         connection.setReadTimeout(10000);
         int responseCode = connection.getResponseCode();
         switch (responseCode) {
            case 301:
            case 302:
            case 303:
            case 307:
            case 308:
               String redirectLocation = connection.getHeaderField("Location");
               connection.disconnect();
               return download(redirectLocation);
            case 304:
            case 305:
            case 306:
            default:
               byte[] var9;
               try {
                  InputStream responseStream = connection.getInputStream();

                  try {
                     InputStream decodedStream = (InputStream)("gzip".equalsIgnoreCase(connection.getContentEncoding())
                        ? new GZIPInputStream(responseStream)
                        : responseStream);

                     try {
                        ByteArrayOutputStream output = new ByteArrayOutputStream();

                        try {
                           byte[] buffer = new byte[8192];

                           int bytesRead;
                           while ((bytesRead = decodedStream.read(buffer)) != -1) {
                              output.write(buffer, 0, bytesRead);
                           }

                           var9 = output.toByteArray();
                        } catch (Throwable var22) {
                           try {
                              output.close();
                           } catch (Throwable var21) {
                              var22.addSuppressed(var21);
                           }

                           throw var22;
                        }

                        output.close();
                     } catch (Throwable var23) {
                        if (decodedStream != null) {
                           try {
                              decodedStream.close();
                           } catch (Throwable var20) {
                              var23.addSuppressed(var20);
                           }
                        }

                        throw var23;
                     }

                     if (decodedStream != null) {
                        decodedStream.close();
                     }
                  } catch (Throwable var24) {
                     if (responseStream != null) {
                        try {
                           responseStream.close();
                        } catch (Throwable var19) {
                           var24.addSuppressed(var19);
                        }
                     }

                     throw var24;
                  }

                  if (responseStream != null) {
                     responseStream.close();
                  }
               } finally {
                  connection.disconnect();
               }

               return var9;
         }
      } catch (Throwable var26) {
         return null;
      }
   }

   private static Throwable identityThrowable(Throwable throwable) {
      return throwable;
   }

   static {
      setLegacyMarker("Gazrnb");
   }
}
