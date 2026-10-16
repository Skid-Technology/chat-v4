/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.tutorial.page;

import gg.vape.module.none.ClientSettings;
import gg.vape.tutorial.MultiComponentHighlightTutorialAction;
import gg.vape.tutorial.SingleComponentHighlightTutorialAction;
import gg.vape.tutorial.TutorialPage;
import gg.vape.tutorial.TutorialTargetSelector;
import gg.vape.tutorial.TutorialTooltipPlacement;
import gg.vape.tutorial.selector.HudTutorialKeystrokesHudModuleRowSelector;
import gg.vape.tutorial.selector.HudTutorialNamedHudModuleRowSelector;
import gg.vape.ui.click.frame.impl.hud.HudEditorReturnToMainLayerFrame;
import gg.vape.ui.click.frame.impl.hud.HudModuleListEntry;
import gg.vape.ui.click.frame.impl.hud.HudModuleListPanel;
import gg.vape.ui.click.frame.impl.hud.HudModuleSelectorFrame;
import gg.vape.ui.click.frame.impl.hud.KeystrokesHudFrame;

public class HudTutorialPage
extends TutorialPage {
    public HudTutorialPage() {
        super("Legit mod menu");
        this.addAction(new SingleComponentHighlightTutorialAction(() -> {
            if (ClientSettings.moduleSearchFrame != null && ClientSettings.moduleSearchFrame.n$src$Lgg_vape_ui_click_frame_impl_ModuleSearchFrameHe$xia8v2() != null) {
                return ClientSettings.moduleSearchFrame.n$src$Lgg_vape_ui_click_frame_impl_ModuleSearchFrameHe$xia8v2().G$src$Lgg_vape_ui_click_component_IconButtonComponent_$1pnwa51();
            }
            return null;
        }, "Click this button to open the legit mod menu", "Click the highlighted button to open the Legit mod menu.", true).setTooltipPlacement(TutorialTooltipPlacement.BOTTOM));
        this.addAction(new SingleComponentHighlightTutorialAction(() -> ClientSettings.getFrame(HudModuleSelectorFrame.class), "This is the legit mod menu", "It includes various features generally included inside legit clients, or as mods.", false).queueMessage("This is the legit mod menu", "None of these features are detectable and do not necessarily provide any unfair advantage.").setTooltipPlacement(TutorialTooltipPlacement.TOP));
        this.addAction(new MultiComponentHighlightTutorialAction(() -> ClientSettings.getFrame(HudModuleListPanel.class), (TutorialTargetSelector)new HudTutorialNamedHudModuleRowSelector(this, HudModuleListEntry.class), "Legit mods", "There are many useful HUD modules such as ArmorStatus or Keystrokes that you can use.", false).queueMessage("Legit mods", "These modules are highly customizable and can be positioned anywhere on your screen."));
        this.addAction(new MultiComponentHighlightTutorialAction(() -> ClientSettings.getFrame(HudModuleListPanel.class), (TutorialTargetSelector)new HudTutorialKeystrokesHudModuleRowSelector(this, HudModuleListEntry.class), "Enable the keystrokes mod", "Click on the Keystrokes mod to enable it.", true));
        this.addAction(new SingleComponentHighlightTutorialAction(() -> ClientSettings.getFrame(KeystrokesHudFrame.class), "HUD mods", "Certain mods such as Keystrokes is a HUD mod. It will render on top of your screen while in game", false).queueMessage("HUD mods", "You can freely move these HUD mods anywhere on your screen by dragging it").setTooltipPlacement(TutorialTooltipPlacement.RIGHT));
        this.addAction(new SingleComponentHighlightTutorialAction(() -> {
            HudEditorReturnToMainLayerFrame frame = ClientSettings.getFrame(HudEditorReturnToMainLayerFrame.class);
            return frame != null && frame.getHeader() != null ? frame.getHeader().getReturnButton() : null;
        }, "Click this button to return to the cheat menu", "Click the highlighted return button to go back to the main cheat menu.", true).setTooltipPlacement(TutorialTooltipPlacement.BOTTOM));
    }
}

