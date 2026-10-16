/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm.commons;

import gg.vape.shaded.org.objectweb.asm.Attribute;
import gg.vape.shaded.org.objectweb.asm.ByteVector;
import gg.vape.shaded.org.objectweb.asm.ClassReader;
import gg.vape.shaded.org.objectweb.asm.ClassWriter;
import gg.vape.shaded.org.objectweb.asm.Label;

public final class ModuleTargetAttribute
extends Attribute {
    public String platform;

    public ModuleTargetAttribute(String platform) {
        super("ModuleTarget");
        this.platform = platform;
    }

    public ModuleTargetAttribute() {
        this(null);
    }

    protected Attribute read(ClassReader classReader, int offset, int length, char[] charBuffer, int codeOffset, Label[] labels) {
        return new ModuleTargetAttribute(classReader.readUTF8(offset, charBuffer));
    }

    protected ByteVector write(ClassWriter classWriter, byte[] code, int codeLength, int maxStack, int maxLocals) {
        ByteVector byteVector = new ByteVector();
        byteVector.putShort(this.platform == null ? 0 : classWriter.newUTF8(this.platform));
        return byteVector;
    }
}

