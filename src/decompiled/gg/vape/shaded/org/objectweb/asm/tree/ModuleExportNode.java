/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm.tree;

import gg.vape.shaded.org.objectweb.asm.ModuleVisitor;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ModuleExportNode {
    public String packaze;
    public int access;
    public List<String> modules;

    public ModuleExportNode(String packaze, int access, List<String> modules) {
        this.packaze = packaze;
        this.access = access;
        this.modules = modules;
    }

    public void accept(ModuleVisitor moduleVisitor) {
        moduleVisitor.visitExport(this.packaze, this.access, this.modules == null ? null : this.modules.toArray(new String[0]));
    }
}

