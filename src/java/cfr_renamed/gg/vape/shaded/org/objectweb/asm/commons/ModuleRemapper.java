/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm.commons;

import gg.vape.shaded.org.objectweb.asm.ModuleVisitor;
import gg.vape.shaded.org.objectweb.asm.commons.Remapper;

public class ModuleRemapper
extends ModuleVisitor {
    final protected Remapper remapper;

    public ModuleRemapper(ModuleVisitor moduleVisitor, Remapper remapper) {
        this(589824, moduleVisitor, remapper);
    }

    protected ModuleRemapper(int api, ModuleVisitor moduleVisitor, Remapper remapper) {
        super(api, moduleVisitor);
        this.remapper = remapper;
    }

    public void visitMainClass(String mainClass) {
        super.visitMainClass(this.remapper.mapType(mainClass));
    }

    public void visitPackage(String packaze) {
        super.visitPackage(this.remapper.mapPackageName(packaze));
    }

    public void visitRequire(String module, int access, String version) {
        super.visitRequire(this.remapper.mapModuleName(module), access, version);
    }

    public void visitExport(String packaze, int access, String ... modules) {
        String[] remappedModules = null;
        if (modules != null) {
            remappedModules = new String[modules.length];
            for (int i = 0; i < modules.length; ++i) {
                remappedModules[i] = this.remapper.mapModuleName(modules[i]);
            }
        }
        super.visitExport(this.remapper.mapPackageName(packaze), access, remappedModules);
    }

    public void visitOpen(String packaze, int access, String ... modules) {
        String[] remappedModules = null;
        if (modules != null) {
            remappedModules = new String[modules.length];
            for (int i = 0; i < modules.length; ++i) {
                remappedModules[i] = this.remapper.mapModuleName(modules[i]);
            }
        }
        super.visitOpen(this.remapper.mapPackageName(packaze), access, remappedModules);
    }

    public void visitUse(String service) {
        super.visitUse(this.remapper.mapType(service));
    }

    public void visitProvide(String service, String ... providers) {
        String[] remappedProviders = new String[providers.length];
        for (int i = 0; i < providers.length; ++i) {
            remappedProviders[i] = this.remapper.mapType(providers[i]);
        }
        super.visitProvide(this.remapper.mapType(service), remappedProviders);
    }
}

