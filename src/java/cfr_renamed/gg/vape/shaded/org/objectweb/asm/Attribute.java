/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm;

import gg.vape.shaded.org.objectweb.asm.ByteVector;
import gg.vape.shaded.org.objectweb.asm.ClassReader;
import gg.vape.shaded.org.objectweb.asm.ClassWriter;
import gg.vape.shaded.org.objectweb.asm.Label;
import gg.vape.shaded.org.objectweb.asm.SymbolTable;

public class Attribute {
    final public String type;
    private ByteVector cachedContent;
    Attribute nextAttribute;

    protected Attribute(String type) {
        this.type = type;
    }

    public boolean isUnknown() {
        return true;
    }

    public boolean isCodeAttribute() {
        return false;
    }

    @Deprecated
    protected Label[] getLabels() {
        return new Label[0];
    }

    protected Attribute read(ClassReader classReader, int offset, int length, char[] charBuffer, int codeAttributeOffset, Label[] labels) {
        Attribute attribute = new Attribute(this.type);
        attribute.cachedContent = new ByteVector(classReader.readBytes(offset, length));
        return attribute;
    }

    public static Attribute read(Attribute attribute, ClassReader classReader, int offset, int length, char[] charBuffer, int codeAttributeOffset, Label[] labels) {
        return attribute.read(classReader, offset, length, charBuffer, codeAttributeOffset, labels);
    }

    public static Label readLabel(ClassReader classReader, int bytecodeOffset, Label[] labels) {
        return classReader.readLabel(bytecodeOffset, labels);
    }

    private ByteVector maybeWrite(ClassWriter classWriter, byte[] code, int codeLength, int maxStack, int maxLocals) {
        if (this.cachedContent == null) {
            this.cachedContent = this.write(classWriter, code, codeLength, maxStack, maxLocals);
        }
        return this.cachedContent;
    }

    protected ByteVector write(ClassWriter classWriter, byte[] code, int codeLength, int maxStack, int maxLocals) {
        return this.cachedContent;
    }

    public static byte[] write(Attribute attribute, ClassWriter classWriter, byte[] code, int codeLength, int maxStack, int maxLocals) {
        ByteVector content = attribute.maybeWrite(classWriter, code, codeLength, maxStack, maxLocals);
        byte[] result = new byte[content.length];
        System.arraycopy(content.data, 0, result, 0, content.length);
        return result;
    }

    final int getAttributeCount() {
        int count = 0;
        Attribute attribute = this;
        while (attribute != null) {
            ++count;
            attribute = attribute.nextAttribute;
        }
        return count;
    }

    final int computeAttributesSize(SymbolTable symbolTable) {
        byte[] code = null;
        boolean codeLength = false;
        int maxStack = -1;
        int maxLocals = -1;
        return this.computeAttributesSize(symbolTable, code, 0, -1, -1);
    }

    final int computeAttributesSize(SymbolTable symbolTable, byte[] code, int codeLength, int maxStack, int maxLocals) {
        ClassWriter classWriter = symbolTable.classWriter;
        int size = 0;
        Attribute attribute = this;
        while (attribute != null) {
            symbolTable.addConstantUtf8(attribute.type);
            size += 6 + attribute.maybeWrite((ClassWriter)classWriter, (byte[])code, (int)codeLength, (int)maxStack, (int)maxLocals).length;
            attribute = attribute.nextAttribute;
        }
        return size;
    }

    static int computeAttributesSize(SymbolTable symbolTable, int accessFlags, int signatureIndex) {
        int size = 0;
        if ((accessFlags & 0x1000) != 0 && symbolTable.getMajorVersion() < 49) {
            symbolTable.addConstantUtf8("Synthetic");
            size += 6;
        }
        if (signatureIndex != 0) {
            symbolTable.addConstantUtf8("Signature");
            size += 8;
        }
        if ((accessFlags & 0x20000) != 0) {
            symbolTable.addConstantUtf8("Deprecated");
            size += 6;
        }
        return size;
    }

    final void putAttributes(SymbolTable symbolTable, ByteVector output) {
        byte[] code = null;
        boolean codeLength = false;
        int maxStack = -1;
        int maxLocals = -1;
        this.putAttributes(symbolTable, code, 0, -1, -1, output);
    }

    final void putAttributes(SymbolTable symbolTable, byte[] code, int codeLength, int maxStack, int maxLocals, ByteVector output) {
        ClassWriter classWriter = symbolTable.classWriter;
        Attribute attribute = this;
        while (attribute != null) {
            ByteVector attributeContent = attribute.maybeWrite(classWriter, code, codeLength, maxStack, maxLocals);
            output.putShort(symbolTable.addConstantUtf8(attribute.type)).putInt(attributeContent.length);
            output.putByteArray(attributeContent.data, 0, attributeContent.length);
            attribute = attribute.nextAttribute;
        }
    }

    static void putAttributes(SymbolTable symbolTable, int accessFlags, int signatureIndex, ByteVector output) {
        if ((accessFlags & 0x1000) != 0 && symbolTable.getMajorVersion() < 49) {
            output.putShort(symbolTable.addConstantUtf8("Synthetic")).putInt(0);
        }
        if (signatureIndex != 0) {
            output.putShort(symbolTable.addConstantUtf8("Signature")).putInt(2).putShort(signatureIndex);
        }
        if ((accessFlags & 0x20000) != 0) {
            output.putShort(symbolTable.addConstantUtf8("Deprecated")).putInt(0);
        }
    }

    static final class Set {
        private final static int SIZE_INCREMENT = 6;
        private int size;
        private Attribute[] data = new Attribute[6];

        Set() {
        }

        void addAttributes(Attribute attributeList) {
            Attribute attribute = attributeList;
            while (attribute != null) {
                if (!this.contains(attribute)) {
                    this.add(attribute);
                }
                attribute = attribute.nextAttribute;
            }
        }

        Attribute[] toArray() {
            Attribute[] result = new Attribute[this.size];
            System.arraycopy(this.data, 0, result, 0, this.size);
            return result;
        }

        private boolean contains(Attribute attribute) {
            for (int i = 0; i < this.size; ++i) {
                if (!this.data[i].type.equals(attribute.type)) continue;
                return true;
            }
            return false;
        }

        private void add(Attribute attribute) {
            if (this.size >= this.data.length) {
                Attribute[] newData = new Attribute[this.data.length + 6];
                System.arraycopy(this.data, 0, newData, 0, this.size);
                this.data = newData;
            }
            this.data[this.size++] = attribute;
        }
    }
}

