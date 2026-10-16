/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl.online;

import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.frame.impl.online.OnlineConnectionSettingsFrame;
import java.awt.Point;

public class OnlineConnectionBackdropMouseListener
implements GuiMouseListener {
    final OnlineConnectionSettingsFrame var_gg_vape_ui_click_frame_impl_online_OnlineConnectionSettingsFrame_D;

    @Override
    public boolean Q(Point point) {
        return !this.var_gg_vape_ui_click_frame_impl_online_OnlineConnectionSettingsFrame_D.getBounds().R(point);
    }

    public OnlineConnectionBackdropMouseListener(OnlineConnectionSettingsFrame onlineConnectionSettingsFrame) {
        this.var_gg_vape_ui_click_frame_impl_online_OnlineConnectionSettingsFrame_D = onlineConnectionSettingsFrame;
    }
}

