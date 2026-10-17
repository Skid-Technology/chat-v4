/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.tutorial;

import gg.vape.Vape;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.Frame;

public class TutorialFrame
extends Frame {
    private static final String eb;
    private static GuiComponent[] zb;

    public static GuiComponent[] l$src$ALgg_vape_ui_click_component_GuiComponent_$xp34x4() {
        return zb;
    }

    @Override
    public void F() {
        this.l$src$V$1mibm4x();
        Vape.INSTANCE.getTutorialManager().refreshCurrentAction();
        this.l$src$V$1mibm4x();
        for (GuiComponent guiComponent : this.f()) {
            guiComponent.F();
        }
        ClientSettings.queueFrameOpen(this);
    }

    @Override
    public void H() {
        this.F();
    }

    @Override
    public void c() {
        Vape.INSTANCE.getTutorialManager().refreshCurrentAction();
        super.c();
    }

    public static void n(GuiComponent[] guiComponentArray) {
        zb = guiComponentArray;
    }

    @Override
    public void Y() {
    }

    @Override
    public void v() {
    }

    @Override
    public String getName() {
        return eb;
    }

    static {
        TutorialFrame.n(new GuiComponent[4]);
        eb = "tutorial";
    }
}

