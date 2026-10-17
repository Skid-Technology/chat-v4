/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.tools.reflect;

import gg.vape.shaded.javassist.tools.reflect.ClassMetaobject;
import gg.vape.shaded.javassist.tools.reflect.Metaobject;

public interface Metalevel {
    public ClassMetaobject _getClass();

    public Metaobject _getMetaobject();

    public void _setMetaobject(Metaobject var1);
}

