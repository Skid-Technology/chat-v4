/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.ui.click.frame.impl.main;

import java.awt.Color;
import org.jetbrains.annotations.Nullable;

public final class ClickGuiModuleCardRenderState {
    private final Color var_java_awt_Color_t;
    private final String var_java_lang_String_R;

    public boolean boolean_n() {
        boolean bl = this.var_java_lang_String_R != null;
        return bl;
    }

    public boolean R() {
        boolean bl = this.var_java_awt_Color_t != null;
        return bl;
    }

    @Nullable
    public String java_lang_String_n() {
        return this.var_java_lang_String_R;
    }

    public static ClickGuiModuleCardRenderState b(Color color) {
        return new ClickGuiModuleCardRenderState(null, color);
    }

    public static ClickGuiModuleCardRenderState j(String string) {
        return new ClickGuiModuleCardRenderState(string, null);
    }

    @Nullable
    public Color u() {
        return this.var_java_awt_Color_t;
    }

    private ClickGuiModuleCardRenderState(@Nullable String string, @Nullable Color color) {
        this.var_java_lang_String_R = string;
        this.var_java_awt_Color_t = color;
    }

    public String n() {
        return this.java_lang_String_n();
    }

    public boolean n$src$Z$1c2q0zn() {
        return this.boolean_n();
    }
}

