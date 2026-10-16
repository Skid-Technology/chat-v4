/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.bytecode;

import gg.vape.shaded.javassist.bytecode.ConstInfo;
import gg.vape.shaded.javassist.bytecode.ConstPool;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

class ModuleInfo
extends ConstInfo {
    final static int tag = 19;
    int name;

    public ModuleInfo(int moduleName, int index) {
        super(index);
        this.name = moduleName;
    }

    public ModuleInfo(DataInputStream in, int index) throws IOException {
        super(index);
        this.name = in.readUnsignedShort();
    }

    public int hashCode() {
        return this.name;
    }

    public boolean equals(Object obj) {
        return obj instanceof ModuleInfo && ((ModuleInfo)obj).name == this.name;
    }

    @Override
    public int getTag() {
        return 19;
    }

    public String getModuleName(ConstPool cp) {
        return cp.getUtf8Info(this.name);
    }

    @Override
    public int copy(ConstPool src, ConstPool dest, Map<String, String> map) {
        String moduleName = src.getUtf8Info(this.name);
        int newName = dest.addUtf8Info(moduleName);
        return dest.addModuleInfo(newName);
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        out.writeByte(19);
        out.writeShort(this.name);
    }

    @Override
    public void print(PrintWriter out) {
        out.print("Module #");
        out.println(this.name);
    }
}

