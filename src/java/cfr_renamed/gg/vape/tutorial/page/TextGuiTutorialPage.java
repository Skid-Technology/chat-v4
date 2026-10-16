/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.tutorial.page;

import gg.vape.module.none.ClientSettings;
import gg.vape.module.none.TextGuiSettingsFrame;
import gg.vape.tutorial.MultiComponentHighlightTutorialAction;
import gg.vape.tutorial.SingleComponentHighlightTutorialAction;
import gg.vape.tutorial.TutorialPage;
import gg.vape.tutorial.TutorialTargetSelector;
import gg.vape.tutorial.TutorialTooltipPlacement;
import gg.vape.tutorial.selector.SettingsFrameHeaderPrimaryButtonTargetSelector;
import gg.vape.tutorial.selector.SettingsFrameHeaderSecondaryButtonTargetSelector;
import gg.vape.tutorial.selector.TextGuiQuickActionsSettingsButtonTargetSelector;
import gg.vape.tutorial.selector.TextGuiSettingsNonHeaderComponentTargetSelector;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.ClickGuiQuickActionsComponent;
import gg.vape.ui.click.frame.SettingsFrameHeaderComponent;
import gg.vape.ui.click.frame.impl.quickactions.QuickActionsFrame;

public class TextGuiTutorialPage
extends TutorialPage {
    public TextGuiTutorialPage() {
        super("Overlays");
        this.addAction(new MultiComponentHighlightTutorialAction(() -> ClientSettings.settingsSearchFrame, (TutorialTargetSelector)new TextGuiQuickActionsSettingsButtonTargetSelector(this, ClickGuiQuickActionsComponent.class), "Overlays Menu", "Open the overlays menu by clicking this button", true).setTooltipPlacement(TutorialTooltipPlacement.RIGHT));
        this.addAction(new SingleComponentHighlightTutorialAction(() -> ClientSettings.getFrame(QuickActionsFrame.class), "Overlays menu", "Here you can enable overlays in Vape. Overlays are frames that will render on-top of your screen while in game.", false).setTooltipPlacement(TutorialTooltipPlacement.RIGHT));
        this.addAction(new SingleComponentHighlightTutorialAction(() -> {
            QuickActionsFrame frame = ClientSettings.getFrame(QuickActionsFrame.class);
            return frame != null ? frame.m$src$Lgg_vape_ui_click_frame_impl_quickactions_QuickA$1kmfigl() : null;
        }, "Text GUI", "Enable the Text GUI by clicking this button", true).setTooltipPlacement(TutorialTooltipPlacement.RIGHT));
        this.addAction(new SingleComponentHighlightTutorialAction(() -> ClientSettings.getFrame(TextGuiSettingsFrame.class), "Text GUI", "This is the Text GUI overlay. It will show you a list of the modules that you have enabled", false).setTooltipPlacement(TutorialTooltipPlacement.RIGHT));
        this.addAction(new SingleComponentHighlightTutorialAction(() -> {
            TextGuiSettingsFrame frame = ClientSettings.getFrame(TextGuiSettingsFrame.class);
            return frame != null ? frame.K$src$Lgg_vape_ui_click_frame_impl_TextGuiOverlayCompo$1shgn4i() : null;
        }, "Text GUI", "The enabled modules will appear below here", false).setTooltipPlacement(TutorialTooltipPlacement.RIGHT).setVerticalOffset(50.0));
        this.addAction(new MultiComponentHighlightTutorialAction(() -> ClientSettings.getFrame(TextGuiSettingsFrame.class), (TutorialTargetSelector)new SettingsFrameHeaderPrimaryButtonTargetSelector(this, SettingsFrameHeaderComponent.class), "Open the Text GUI settings", "Click the highlighted gear icon to open the Text GUI settings.", true).setTooltipPlacement(TutorialTooltipPlacement.RIGHT));
        this.addAction(new MultiComponentHighlightTutorialAction(() -> ClientSettings.getFrame(TextGuiSettingsFrame.class), (TutorialTargetSelector)new TextGuiSettingsNonHeaderComponentTargetSelector(this, GuiComponent.class), "Text GUI settings", "Here you can adjust settings for the Text GUI", false).setTooltipPlacement(TutorialTooltipPlacement.RIGHT));
        this.addAction(new MultiComponentHighlightTutorialAction(() -> ClientSettings.getFrame(TextGuiSettingsFrame.class), (TutorialTargetSelector)new SettingsFrameHeaderSecondaryButtonTargetSelector(this, SettingsFrameHeaderComponent.class), "Close the Text GUI settings", "Click the highlighted button to close the Text GUI settings.", true).setTooltipPlacement(TutorialTooltipPlacement.RIGHT));
    }
}

