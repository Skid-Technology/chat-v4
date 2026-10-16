/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame;

public interface CollapsibleFrame {
    public void w();

    public boolean q();

    default public void void_w() {
        this.w();
    }

    default public boolean boolean_q() {
        return this.q();
    }
}

