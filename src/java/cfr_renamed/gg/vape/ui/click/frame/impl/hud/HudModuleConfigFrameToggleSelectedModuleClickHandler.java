/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl.hud;

import gg.vape.module.none.ClientSettings;
import gg.vape.module.render.hud.HudModule;
import gg.vape.module.render.hud.HudModuleGroup;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.impl.hud.HudModuleConfigFrame;
import gg.vape.ui.click.frame.impl.hud.HudModuleSelectorFrame;

class HudModuleConfigFrameToggleSelectedModuleClickHandler
implements GuiClickListener {
    private final HudModuleConfigFrame configFrame;

    @Override
    public void onPrimaryClick() {
        HudModule hudModule;
        hudModule.setFavorite(!(hudModule = this.configFrame.getSelectedModule()).isFavorite());
        if (ClientSettings.getFrame(HudModuleSelectorFrame.class).getSelectedGroup() == HudModuleGroup.FAVORITE) {
            ClientSettings.getFrame(HudModuleSelectorFrame.class).getModuleListPanel().refreshModules();
        }
    }

    HudModuleConfigFrameToggleSelectedModuleClickHandler(HudModuleConfigFrame hudModuleConfigFrame) {
        this.configFrame = hudModuleConfigFrame;
    }
}

