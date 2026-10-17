package gg.vape.runtime;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class ForgePayloadClassLoader extends URLClassLoader {
   private static final String PAYLOAD_CLASS_PREFIX = "gg.vape.";
   private static final String AUXILIARY_CLASS_PREFIX = "func.skidline.";
   private static final String PAYLOAD_ENTRY_PREFIX = "gg/vape/";
   private static final String AUXILIARY_ENTRY_PREFIX = "func/skidline/";

   public ForgePayloadClassLoader(URL[] urls, ClassLoader parent) {
      super(urls, parent);
   }

   @Override
   protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
      synchronized (this.getClassLoadingLock(name)) {
         Class<?> loaded = this.findLoadedClass(name);
         if (loaded == null && isPayloadClass(name)) {
            loaded = this.findClass(name);
         }

         if (loaded == null) {
            loaded = super.loadClass(name, false);
         }

         if (resolve) {
            this.resolveClass(loaded);
         }

         return loaded;
      }
   }

   public Map<String, ClassLoader> buildPackageRoutingMap(Map<String, ClassLoader> currentRoutes) throws IOException {
      Map<String, ClassLoader> routes = new HashMap<>();
      if (currentRoutes != null) {
         routes.putAll(currentRoutes);
      }

      Set<String> payloadPackages = new HashSet<>();

      for (URL url : this.getURLs()) {
         collectPayloadPackages(url, payloadPackages);
      }

      if (payloadPackages.isEmpty()) {
         throw new IOException("payload JAR contains no product classes");
      } else {
         for (String packageName : payloadPackages) {
            routes.put(packageName, this);
         }

         return routes;
      }
   }

   private static void collectPayloadPackages(URL url, Set<String> packages) throws IOException {
      if (!"file".equalsIgnoreCase(url.getProtocol())) {
         throw new IOException("unsupported payload URL: " + url);
      } else {
         File payload;
         try {
            URI uri = url.toURI();
            payload = new File(uri);
         } catch (URISyntaxException var8) {
            throw new IOException("invalid payload URL: " + url, var8);
         }

         if (!payload.isFile()) {
            throw new IOException("payload URL is not a JAR file: " + payload);
         } else {
            JarFile jar = new JarFile(payload);

            try {
               Enumeration<JarEntry> entries = jar.entries();

               while (entries.hasMoreElements()) {
                  String entryName = entries.nextElement().getName();
                  if ((entryName.startsWith("gg/vape/") || entryName.startsWith("func/skidline/")) && entryName.endsWith(".class")) {
                     int separator = entryName.lastIndexOf(47);
                     if (separator > 0) {
                        packages.add(entryName.substring(0, separator).replace('/', '.'));
                     }
                  }
               }
            } catch (Throwable var9) {
               try {
                  jar.close();
               } catch (Throwable var7) {
                  var9.addSuppressed(var7);
               }

               throw var9;
            }

            jar.close();
         }
      }
   }

   private static boolean isPayloadClass(String name) {
      return name.startsWith("gg.vape.") || name.startsWith("func.skidline.");
   }

   static {
      ClassLoader.registerAsParallelCapable();
   }
}
