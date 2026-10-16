/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.component;

import gg.vape.ui.click.component.OptionTextFormatter;
import gg.vape.unmap.ModeSelection;

public class ModeEntryTextFormatter<T extends ModeSelection>
implements OptionTextFormatter<T> {
    final public static OptionTextFormatter<ModeSelection> DEFAULT = new ModeEntryTextFormatter<ModeSelection>();

    public String formatValue(T value) {
        return value != null ? ((ModeSelection)value).toString() : "";
    }

    @Override
    public String format(T value) {
        return this.formatValue(value);
    }

    @Override
    public String format(Object object) {
        return this.format((T)((ModeSelection)object));
    }
}

