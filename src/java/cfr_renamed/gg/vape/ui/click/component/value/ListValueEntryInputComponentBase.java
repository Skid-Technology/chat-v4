/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.component.value;

import gg.vape.ui.click.component.TextInputComponentBase;

public class ListValueEntryInputComponentBase
extends TextInputComponentBase {
    private int legacyIndex;
    private boolean legacyMode;

    @Override
    public double getAvailableTextWidth() {
        return this.A() - 35.0;
    }

    public int getLegacyIndex() {
        return this.legacyIndex;
    }

    @Override
    public double C() {
        return 20.0;
    }

    public ListValueEntryInputComponentBase(boolean highlighted, String placeholder) {
        this(highlighted, placeholder, false);
    }

    public boolean isLegacyMode() {
        return this.legacyMode;
    }

    public ListValueEntryInputComponentBase(boolean highlighted, String placeholder, boolean legacyMode) {
        super(placeholder);
        this.legacyMode = legacyMode;
        this.actionButtonColor = highlighted ? ListValueEntryInputComponentBase.J.var_java_awt_Color_d : ListValueEntryInputComponentBase.J.var_java_awt_Color_B;
    }

    @Override
    public void submit() {
    }

    @Override
    public double x() {
        return 110.0;
    }
}

