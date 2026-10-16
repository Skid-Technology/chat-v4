/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.bytecode;

import gg.vape.shaded.javassist.bytecode.AttributeInfo;
import gg.vape.shaded.javassist.bytecode.ConstPool;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

public class DeprecatedAttribute
extends AttributeInfo {
    final public static String tag = "Deprecated";

    DeprecatedAttribute(ConstPool cp, int n, DataInputStream in) throws IOException {
        super(cp, n, in);
    }

    public DeprecatedAttribute(ConstPool cp) {
        super(cp, tag, new byte[0]);
    }

    @Override
    public AttributeInfo copy(ConstPool newCp, Map<String, String> classnames) {
        return new DeprecatedAttribute(newCp);
    }
}

