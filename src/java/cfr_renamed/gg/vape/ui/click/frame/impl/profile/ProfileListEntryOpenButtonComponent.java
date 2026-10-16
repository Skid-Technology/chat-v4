/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.ui.click.frame.impl.profile;

import gg.vape.Vape;
import gg.vape.api.ApiResponse;
import gg.vape.api.ApiServices;
import gg.vape.config.Profile;
import gg.vape.config.ProfilesSyncPayloadBuilder;
import gg.vape.config.PublicProfile;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.AnimatedIconButtonComponent;
import gg.vape.ui.click.frame.FrameStackManager;
import gg.vape.ui.click.frame.impl.main.ClickGuiFrameManager;
import gg.vape.ui.click.frame.impl.profile.PublicProfilesFrame;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import org.jetbrains.annotations.Nullable;

public class ProfileListEntryOpenButtonComponent
extends AnimatedIconButtonComponent {
    private Profile profile;
    @Nullable
    private PublicProfile publicProfile;
    private boolean selectedProfile;
    private boolean publishedProfile;
    @Nullable
    private Runnable afterDelete;

    private void refreshActionMode() {
        boolean nowPublished;
        if (this.profile == null) {
            return;
        }
        this.selectedProfile = Vape.INSTANCE.getProfilesManager().getActiveProfile().equals(this.profile);
        this.publicProfile = this.profile.getPublicProfile();
        boolean bl = nowPublished = this.publicProfile != null;
        if (nowPublished != this.publishedProfile) {
            this.publishedProfile = nowPublished;
            if (!this.publishedProfile) {
                if (this.selectedProfile) {
                    this.w("You cannot delete your selected profile");
                } else {
                    this.w("Delete this profile");
                }
                this.setIconResource("newtrash");
                this.getBackgroundAnimation().setEndColor(ProfileListEntryOpenButtonComponent.J.var_java_awt_Color_d);
            } else {
                this.setIconResource("newpublicprofiles");
                this.w("Open this published profile");
                this.getBackgroundAnimation().setEndColor(ProfileListEntryOpenButtonComponent.J.var_java_awt_Color_a);
            }
        }
    }

    private void handleClick() {
        if (this.profile == null) {
            return;
        }
        if (!this.publishedProfile) {
            if (this.selectedProfile) {
                return;
            }
            UUID onlineId = this.profile.getOnlineId();
            Vape.INSTANCE.getProfilesManager().removeProfile(this.profile);
            if (onlineId != null) {
                ApiServices.getInstance().getUserDataApi().saveProfileData(ProfilesSyncPayloadBuilder.build(null, Collections.singletonList(onlineId)));
            }
            if (this.afterDelete != null) {
                this.afterDelete.run();
            }
        } else {
            FrameStackManager activeStack = ClientSettings.INSTANCE.getActiveStack();
            if (activeStack instanceof ClickGuiFrameManager) {
                this.openAsOverlay(this.publicProfile.getProfileId(), (ClickGuiFrameManager)activeStack);
            } else {
                PublicProfilesFrame.J(true, this.publicProfile.getProfileId());
            }
        }
    }

    private void openAsOverlay(long publicProfileId, ClickGuiFrameManager frameManager) {
        PublicProfilesFrame profilesFrame = ClientSettings.getFrame(PublicProfilesFrame.class);
        frameManager.setSidecarFrame(profilesFrame);
        ((CompletableFuture)ApiServices.getInstance().getPublicProfileApi().viewProfile(publicProfileId).whenCompleteAsync((response, error) -> ProfileListEntryOpenButtonComponent.handleProfileResponse(profilesFrame, response, error), (Executor)ClientSettings.UI_EXECUTOR)).exceptionally(ProfileListEntryOpenButtonComponent::ignoreLoadFailure);
    }

    public ProfileListEntryOpenButtonComponent(Profile profile, @Nullable Runnable afterDelete) {
        super("newtrash", ProfileListEntryOpenButtonComponent.J.var_java_awt_Color_d);
        this.profile = profile;
        this.afterDelete = afterDelete;
        this.setBorderRadius(2.0f);
        this.setBorderAlpha(1.0f);
        this.setIconScale(0.85);
        this.addClickListener(this::handleClick);
        this.refreshActionMode();
    }

    public ProfileListEntryOpenButtonComponent useOverlayStyle() {
        this.getBackgroundAnimation().setStartColor(ProfileListEntryOpenButtonComponent.J.var_java_awt_Color_l);
        this.setAnimatedBorderColor(ProfileListEntryOpenButtonComponent.J.var_java_awt_Color_l);
        this.setDisabledOverlayColor(ProfileListEntryOpenButtonComponent.J.var_java_awt_Color_m);
        return this;
    }

    @Override
    public void u() {
        this.refreshActionMode();
    }

    private static ApiResponse ignoreLoadFailure(Throwable error) {
        return null;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    private static void handleProfileResponse(PublicProfilesFrame profilesFrame, ApiResponse response, Throwable error) {
        if (error != null) {
            Vape.logThrowable(error);
            return;
        }
        if (!response.isSuccessful()) {
            Vape.debugLog("Failed to load public profile data: " + response.getError());
            return;
        }
        assert (response.getData() != null);
        profilesFrame.N((PublicProfile)response.getData());
    }

    public Profile getProfile() {
        return this.profile;
    }
}

