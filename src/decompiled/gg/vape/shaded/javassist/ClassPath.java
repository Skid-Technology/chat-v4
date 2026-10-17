/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist;

import gg.vape.shaded.javassist.NotFoundException;
import java.io.InputStream;
import java.net.URL;

public interface ClassPath {
    public InputStream openClassfile(String var1) throws NotFoundException;

    public URL find(String var1);
}

