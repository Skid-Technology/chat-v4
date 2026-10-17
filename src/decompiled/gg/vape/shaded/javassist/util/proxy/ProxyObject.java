/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.util.proxy;

import gg.vape.shaded.javassist.util.proxy.MethodHandler;
import gg.vape.shaded.javassist.util.proxy.Proxy;

public interface ProxyObject
extends Proxy {
    @Override
    public void setHandler(MethodHandler var1);

    public MethodHandler getHandler();
}

