/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.ui.click.component;

import gg.vape.unmap.INamed;
import org.jetbrains.annotations.Nullable;

public interface OptionTextFormatter<T> {
    public String format(T var1);

    public static OptionTextFormatter<String> strings() {
        return OptionTextFormatter.strings("");
    }

    public static OptionTextFormatter<String> strings(@Nullable String fallbackText) {
        return value -> OptionTextFormatter.formatStringOrFallback(fallbackText, value);
    }

    public static <N extends INamed> OptionTextFormatter<N> namedValues(@Nullable String fallbackText) {
        return value -> OptionTextFormatter.formatNamedOrFallback(fallbackText, value);
    }

    public static String formatStringOrFallback(String fallbackText, String value) {
        return value != null ? value : fallbackText;
    }

    public static <N extends INamed> OptionTextFormatter<N> namedValues() {
        return OptionTextFormatter.namedValues("");
    }

    public static String formatNamedOrFallback(String fallbackText, INamed value) {
        return value != null ? value.getName() : fallbackText;
    }
}

