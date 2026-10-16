package gg.vape.shaded.javassist;

import java.io.InputStream;
import java.net.URL;

public interface ClassPath {
   InputStream openClassfile(String var1) throws NotFoundException;

   URL find(String var1);
}
