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
import gg.vape.tutorial.page.profile.FirstActiveProfileListEntrySelector;
import gg.vape.tutorial.page.profile.SecondActiveProfileListEntrySelector;
import gg.vape.tutorial.selector.ProfileCreateNameInputTargetSelector;
import gg.vape.tutorial.selector.ProfilesNavigationButtonTargetSelector;
import gg.vape.ui.click.frame.FrameNavigationButtonComponent;
import gg.vape.ui.click.frame.impl.profile.ProfileCreatePanelComponent;
import gg.vape.ui.click.frame.impl.profile.ProfileListEntryComponent;
import gg.vape.ui.click.frame.impl.profile.ProfilesSettingsFrame;

public class ProfilesTutorialPage
extends TutorialPage {
    public ProfilesTutorialPage() {
        super("Profiles");
        this.addAction(new MultiComponentHighlightTutorialAction(() -> ClientSettings.settingsSearchFrame, (TutorialTargetSelector)new ProfilesNavigationButtonTargetSelector(this, FrameNavigationButtonComponent.class), "Open the profiles frame", "Click the highlighted button to open the Profiles frame.", true).setTooltipPlacement(TutorialTooltipPlacement.RIGHT));
        this.addAction(new SingleComponentHighlightTutorialAction(() -> ClientSettings.getFrame(ProfilesSettingsFrame.class), "Profiles", "Profiles allow you to keep a set of settings contained within a profile.", false).queueMessage("Profiles", "This allows you to easily switch between various settings that are more suited between different situations.").setTooltipPlacement(TutorialTooltipPlacement.RIGHT));
        this.addAction(new MultiComponentHighlightTutorialAction(() -> ClientSettings.getFrame(ProfilesSettingsFrame.class), (TutorialTargetSelector)new ProfileCreateNameInputTargetSelector(this, ProfileCreatePanelComponent.class), "Creating profiles", "You can add a profile by entering the name for the profile here. When you create a new profile, it starts off with the settings of your currently selected profile.", false).setTooltipPlacement(TutorialTooltipPlacement.BOTTOM));
        this.addAction(new MultiComponentHighlightTutorialAction(() -> ClientSettings.getFrame(ProfilesSettingsFrame.class), (TutorialTargetSelector)new FirstActiveProfileListEntrySelector(this, ProfileListEntryComponent.class), "Selected profile", "The profile that you currently have selected will be enabled the next time you launch Vape.", false).setTooltipPlacement(TutorialTooltipPlacement.BOTTOM));
        this.addAction(new MultiComponentHighlightTutorialAction(() -> ClientSettings.getFrame(ProfilesSettingsFrame.class), (TutorialTargetSelector)new SecondActiveProfileListEntrySelector(this, ProfileListEntryComponent.class), "Enabled modules", "A profile will remember the modules that you currently have enabled, but by default it will not automatically enable them.", false).setTooltipPlacement(TutorialTooltipPlacement.BOTTOM));
        this.addAction(new SingleComponentHighlightTutorialAction(() -> {
            ProfilesSettingsFrame frame = ClientSettings.getFrame(ProfilesSettingsFrame.class);
            return frame != null ? frame.q$src$Lgg_vape_ui_click_component_IconButtonComponent_$1bvowkh() : null;
        }, "Open the general settings for profiles", "Click the highlighted gear icon to open the general settings for profiles.", true).setTooltipPlacement(TutorialTooltipPlacement.RIGHT));
        this.addAction(new SingleComponentHighlightTutorialAction(() -> {
            ProfilesSettingsFrame frame = ClientSettings.getFrame(ProfilesSettingsFrame.class);
            return frame != null ? frame.getAutoEnableModulesToggle() : null;
        }, "Auto-enabling modules", "If you enable this option, your modules for a profile will automatically be re-enabled upon launch, or when switching to that profile", false).queueMessage("Auto-enabling modules", "We recommend keeping this disabled in most circumstances").setTooltipPlacement(TutorialTooltipPlacement.RIGHT));
    }
}

