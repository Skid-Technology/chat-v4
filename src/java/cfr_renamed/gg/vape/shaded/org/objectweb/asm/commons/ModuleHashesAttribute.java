/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm.commons;

import gg.vape.shaded.org.objectweb.asm.Attribute;
import gg.vape.shaded.org.objectweb.asm.ByteVector;
import gg.vape.shaded.org.objectweb.asm.ClassReader;
import gg.vape.shaded.org.objectweb.asm.ClassWriter;
import gg.vape.shaded.org.objectweb.asm.Label;
import java.util.ArrayList;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class ModuleHashesAttribute
extends Attribute {
    public String algorithm;
    public List<String> modules;
    public List<byte[]> hashes;

    public ModuleHashesAttribute(String algorithm, List<String> modules, List<byte[]> hashes) {
        super("ModuleHashes");
        this.algorithm = algorithm;
        this.modules = modules;
        this.hashes = hashes;
    }

    public ModuleHashesAttribute() {
        this(null, null, null);
    }

    @Override
    protected Attribute read(ClassReader classReader, int offset, int length, char[] charBuffer, int codeAttributeOffset, Label[] labels) {
        int currentOffset = offset;
        String hashAlgorithm = classReader.readUTF8(currentOffset, charBuffer);
        int numModules = classReader.readUnsignedShort(currentOffset += 2);
        currentOffset += 2;
        ArrayList<String> moduleList = new ArrayList<String>(numModules);
        ArrayList<byte[]> hashList = new ArrayList<byte[]>(numModules);
        for (int i = 0; i < numModules; ++i) {
            String module = classReader.readModule(currentOffset, charBuffer);
            moduleList.add(module);
            int hashLength = classReader.readUnsignedShort(currentOffset += 2);
            currentOffset += 2;
            byte[] hash = new byte[hashLength];
            for (int j = 0; j < hashLength; ++j) {
                hash[j] = (byte)classReader.readByte(currentOffset);
                ++currentOffset;
            }
            hashList.add(hash);
        }
        return new ModuleHashesAttribute(hashAlgorithm, moduleList, hashList);
    }

    @Override
    protected ByteVector write(ClassWriter classWriter, byte[] code, int codeLength, int maxStack, int maxLocals) {
        ByteVector byteVector = new ByteVector();
        byteVector.putShort(classWriter.newUTF8(this.algorithm));
        if (this.modules == null) {
            byteVector.putShort(0);
        } else {
            int numModules = this.modules.size();
            byteVector.putShort(numModules);
            for (int i = 0; i < numModules; ++i) {
                String module = this.modules.get(i);
                byte[] hash = this.hashes.get(i);
                byteVector.putShort(classWriter.newModule(module)).putShort(hash.length).putByteArray(hash, 0, hash.length);
            }
        }
        return byteVector;
    }
}

