/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl.profile;

import gg.vape.Vape;
import gg.vape.config.Profile;
import gg.vape.ui.click.component.TextInputComponentBase;

public class ProfileCreateNameInputComponent
extends TextInputComponentBase {
    private final Profile profile;

    @Override
    public void submit() {
        if (!this.hasNonBlankText()) {
            this.setText("");
            return;
        }
        String profileName = this.getText();
        Profile existingProfile = Vape.INSTANCE.getProfilesManager().getProfileByName(profileName);
        if (existingProfile != null) {
            return;
        }
        this.profile.setName(profileName);
        this.profile.setDirty(true);
        Vape.INSTANCE.getProfilesManager().addProfile(this.profile, true);
        Vape.INSTANCE.getProfilesManager().setActiveProfile(this.profile);
        this.setText("");
    }

    @Override
    public double C() {
        return 14.5;
    }

    @Override
    public double x() {
        return 55.0;
    }

    @Override
    public boolean V$src$Z$1xhop3l() {
        return true;
    }

    @Override
    public double getComponentWidth() {
        return this.A() + 2.5;
    }

    public ProfileCreateNameInputComponent(String placeholder, Profile profile) {
        super(placeholder);
        this.profile = profile;
        this.setShowDisabledOverlay(false);
        this.setMaxLength(48);
    }
}

