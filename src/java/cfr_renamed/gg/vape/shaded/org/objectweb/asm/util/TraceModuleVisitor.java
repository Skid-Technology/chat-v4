/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm.util;

import gg.vape.shaded.org.objectweb.asm.ModuleVisitor;
import gg.vape.shaded.org.objectweb.asm.util.Printer;

public final class TraceModuleVisitor
extends ModuleVisitor {
    final public Printer var_gg_vape_shaded_org_objectweb_asm_util_Printer_p;

    public TraceModuleVisitor(Printer printer) {
        this(null, printer);
    }

    public TraceModuleVisitor(ModuleVisitor moduleVisitor, Printer printer) {
        super(589824, moduleVisitor);
        this.var_gg_vape_shaded_org_objectweb_asm_util_Printer_p = printer;
    }

    public void visitMainClass(String mainClass) {
        this.var_gg_vape_shaded_org_objectweb_asm_util_Printer_p.visitMainClass(mainClass);
        super.visitMainClass(mainClass);
    }

    public void visitPackage(String packaze) {
        this.var_gg_vape_shaded_org_objectweb_asm_util_Printer_p.visitPackage(packaze);
        super.visitPackage(packaze);
    }

    public void visitRequire(String module, int access, String version) {
        this.var_gg_vape_shaded_org_objectweb_asm_util_Printer_p.visitRequire(module, access, version);
        super.visitRequire(module, access, version);
    }

    public void visitExport(String packaze, int access, String ... modules) {
        this.var_gg_vape_shaded_org_objectweb_asm_util_Printer_p.visitExport(packaze, access, modules);
        super.visitExport(packaze, access, modules);
    }

    public void visitOpen(String packaze, int access, String ... modules) {
        this.var_gg_vape_shaded_org_objectweb_asm_util_Printer_p.visitOpen(packaze, access, modules);
        super.visitOpen(packaze, access, modules);
    }

    public void visitUse(String use) {
        this.var_gg_vape_shaded_org_objectweb_asm_util_Printer_p.visitUse(use);
        super.visitUse(use);
    }

    public void visitProvide(String service, String ... providers) {
        this.var_gg_vape_shaded_org_objectweb_asm_util_Printer_p.visitProvide(service, providers);
        super.visitProvide(service, providers);
    }

    public void visitEnd() {
        this.var_gg_vape_shaded_org_objectweb_asm_util_Printer_p.visitModuleEnd();
        super.visitEnd();
    }
}

