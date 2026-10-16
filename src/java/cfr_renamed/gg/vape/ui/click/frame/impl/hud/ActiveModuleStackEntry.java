/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl.hud;

import gg.vape.module.Mod;
import gg.vape.module.ModuleDisplayInfo;

public class ActiveModuleStackEntry {
    final public Mod module;
    final public ModuleDisplayInfo displayInfo;

    public ActiveModuleStackEntry(Mod mod, ModuleDisplayInfo moduleDisplayInfo) {
        this.module = mod;
        this.displayInfo = moduleDisplayInfo;
    }
}

