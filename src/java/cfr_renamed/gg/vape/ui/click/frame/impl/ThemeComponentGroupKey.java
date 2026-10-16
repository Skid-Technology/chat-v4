/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.ui.click.frame.impl;

import java.util.Objects;
import org.jetbrains.annotations.Nullable;

public final class ThemeComponentGroupKey {
    @Nullable
    private final String var_java_lang_String_h;
    private final String var_java_lang_String_Z;

    @Nullable
    public String u() {
        return this.var_java_lang_String_h;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        ThemeComponentGroupKey he_12 = (ThemeComponentGroupKey)object;
        boolean bl = Objects.equals(this.var_java_lang_String_Z, he_12.var_java_lang_String_Z) && Objects.equals(this.var_java_lang_String_h, he_12.var_java_lang_String_h);
        return bl;
    }

    public int hashCode() {
        return Objects.hash(this.var_java_lang_String_Z, this.var_java_lang_String_h);
    }

    public String h() {
        return this.var_java_lang_String_Z;
    }

    public ThemeComponentGroupKey(String string, @Nullable String string2) {
        this.var_java_lang_String_Z = string;
        this.var_java_lang_String_h = string2;
    }
}

