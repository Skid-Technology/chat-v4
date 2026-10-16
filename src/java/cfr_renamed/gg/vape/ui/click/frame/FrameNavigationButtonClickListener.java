/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.FrameNavigationButtonComponent;

class FrameNavigationButtonClickListener
implements GuiClickListener {
    final String var_java_lang_String_d;
    final FrameNavigationButtonComponent var_gg_vape_ui_click_frame_FrameNavigationButtonComponent_D;

    FrameNavigationButtonClickListener(FrameNavigationButtonComponent frameNavigationButtonComponent, String string) {
        this.var_gg_vape_ui_click_frame_FrameNavigationButtonComponent_D = frameNavigationButtonComponent;
        this.var_java_lang_String_d = string;
    }

    @Override
    public void onPrimaryClick() {
        FrameNavigationButtonComponent frameNavigationButtonComponent = this.var_gg_vape_ui_click_frame_FrameNavigationButtonComponent_D;
        boolean bl = !FrameNavigationButtonComponent.K(this.var_gg_vape_ui_click_frame_FrameNavigationButtonComponent_D);
        FrameNavigationButtonComponent.u(frameNavigationButtonComponent, bl);
        FrameNavigationButtonComponent.d(this.var_gg_vape_ui_click_frame_FrameNavigationButtonComponent_D).J();
        ClientSettings.toggleCategoryFrame(this.var_java_lang_String_d);
    }
}

