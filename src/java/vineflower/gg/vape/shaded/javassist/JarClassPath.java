package gg.vape.shaded.javassist;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

final class JarClassPath implements ClassPath {
   Set<String> jarfileEntries;
   String jarfileURL;

   JarClassPath(String pathname) throws NotFoundException {
      JarFile jarfile = null;

      try {
         jarfile = new JarFile(pathname);
         this.jarfileEntries = new HashSet<>();

         for (JarEntry je : Collections.list(jarfile.entries())) {
            if (je.getName().endsWith(".class")) {
               this.jarfileEntries.add(je.getName());
            }
         }

         this.jarfileURL = new File(pathname).getCanonicalFile().toURI().toURL().toString();
         return;
      } catch (IOException var13) {
      } finally {
         if (null != jarfile) {
            try {
               jarfile.close();
            } catch (IOException var12) {
            }
         }
      }

      throw new NotFoundException(pathname);
   }

   @Override
   public InputStream openClassfile(String classname) throws NotFoundException {
      URL jarURL = this.find(classname);
      if (null != jarURL) {
         try {
            if (ClassPool.cacheOpenedJarFile) {
               return jarURL.openConnection().getInputStream();
            } else {
               URLConnection con = jarURL.openConnection();
               con.setUseCaches(false);
               return con.getInputStream();
            }
         } catch (IOException var4) {
            throw new NotFoundException("broken jar file?: " + classname);
         }
      } else {
         return null;
      }
   }

   @Override
   public URL find(String classname) {
      String jarname = classname.replace('.', '/') + ".class";
      if (this.jarfileEntries.contains(jarname)) {
         try {
            return new URL(String.format("jar:%s!/%s", this.jarfileURL, jarname));
         } catch (MalformedURLException var4) {
         }
      }

      return null;
   }

   @Override
   public String toString() {
      return this.jarfileURL == null ? "<null>" : this.jarfileURL;
   }
}
