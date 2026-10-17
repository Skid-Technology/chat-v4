/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import java.awt.Point;

class ClickGuiModulesSidecarPrimaryMouseListener
implements GuiMouseListener {
    private final Runnable action;

    @Override
    public void g(Point point, MouseClickButton button) {
        this.action.run();
    }

    ClickGuiModulesSidecarPrimaryMouseListener(Runnable action) {
        this.action = action;
    }
}

