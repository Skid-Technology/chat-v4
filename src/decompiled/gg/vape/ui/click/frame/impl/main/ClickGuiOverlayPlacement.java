/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl.main;

public enum ClickGuiOverlayPlacement {
    OVERLAY,
    DOCKED,
    DOCKED_SHIFT;

    private static final ClickGuiOverlayPlacement[] P;

    static {
        String[] stringArray = new String[]{"DOCKED_SHIFT", "DOCKED", "OVERLAY"};
        P = new ClickGuiOverlayPlacement[]{OVERLAY, DOCKED, DOCKED_SHIFT};
    }
}

