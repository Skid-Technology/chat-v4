/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.bytecode;

import gg.vape.shaded.javassist.bytecode.AttributeInfo;
import gg.vape.shaded.javassist.bytecode.ByteArray;
import gg.vape.shaded.javassist.bytecode.ConstPool;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

public class SourceFileAttribute
extends AttributeInfo {
    public static final String tag = "SourceFile";

    SourceFileAttribute(ConstPool cp, int n, DataInputStream in) throws IOException {
        super(cp, n, in);
    }

    public SourceFileAttribute(ConstPool cp, String filename) {
        super(cp, tag);
        int index = cp.addUtf8Info(filename);
        byte[] bvalue = new byte[]{(byte)(index >>> 8), (byte)index};
        this.set(bvalue);
    }

    public String getFileName() {
        return this.getConstPool().getUtf8Info(ByteArray.readU16bit(this.get(), 0));
    }

    @Override
    public AttributeInfo copy(ConstPool newCp, Map<String, String> classnames) {
        return new SourceFileAttribute(newCp, this.getFileName());
    }
}

