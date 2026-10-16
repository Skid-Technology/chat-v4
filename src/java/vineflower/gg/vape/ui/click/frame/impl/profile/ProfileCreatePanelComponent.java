package gg.vape.ui.click.frame.impl.profile;

import gg.vape.Vape;
import gg.vape.api.ApiResponse;
import gg.vape.api.ApiServices;
import gg.vape.config.Profile;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.frame.CenteredPopupFrame;
import java.util.UUID;

public class ProfileCreatePanelComponent extends GuiComponent {
   private final ProfileCreateActionButtonComponent createButton;
   private Profile pendingProfile;
   private final ProfileCreateNameInputComponent nameInput;
   private final ProfileCreateActionButtonComponent publicProfilesButton = new ProfileCreateActionButtonComponent(
      "Public", true, false, 0.8, null, "newpublicprofiles", 0.8, null, J.l
   );
   private final ProfilesSettingsFrame settingsFrame;
   private final ProfileCreateDividerComponent divider;

   @Override
   public void I() {
   }

   public ProfileCreateNameInputComponent getNameInput() {
      return this.nameInput;
   }

   private static void handleProfileIdResponse(Profile profile, ApiResponse response, Throwable error) {
      if (error == null) {
         if (response.isSuccessful()) {
            profile.setOnlineId((UUID)response.getData());
         }
      }
   }

   @Override
   public void F() {
   }

   public Profile getPendingProfile() {
      return this.pendingProfile;
   }

   @Override
   public void u() {
   }

   private static ApiResponse ignoreProfileIdFailure(Throwable error) {
      return null;
   }

   private void startProfileCreation() {
      Profile activeProfile = Vape.INSTANCE.getProfilesManager().getActiveProfile();
      activeProfile.captureCurrentState();
      this.pendingProfile = activeProfile;
      Profile draftProfile = new Profile(activeProfile.getName(), "4.22");
      draftProfile.loadJson(activeProfile.toJson(true));
      draftProfile.setLocalId(UUID.randomUUID());
      draftProfile.setOnlineId(null);
      draftProfile.setPublicProfileFlag(false);
      draftProfile.setDraft(true);
      ApiServices.getInstance()
         .getUserDataApi()
         .reserveProfileId()
         .whenCompleteAsync((response, error) -> handleProfileIdResponse(draftProfile, response, error), ClientSettings.UI_EXECUTOR)
         .exceptionally(ProfileCreatePanelComponent::ignoreProfileIdFailure);
      Vape.INSTANCE.getProfilesManager().switchProfile(draftProfile);
      PanelComponent popupContent = new PanelComponent(this.settingsFrame.A(), this.settingsFrame.getContentLayout().L());
      popupContent.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
      ProfileCreateSubmitNameInputComponent submitNameInput = new ProfileCreateSubmitNameInputComponent(this, "Type name", draftProfile);
      submitNameInput.o(this.settingsFrame.A() - 2.0);
      submitNameInput.Y(22.5);
      popupContent.h(submitNameInput);
      popupContent.h(new ProfileModuleSnapshotListComponent(draftProfile, 105.0, 110.0));
      CenteredPopupFrame popup = ClientSettings.createPopup(this.settingsFrame.getContentLayout(), popupContent, CenteredPopupFrame.class);
      this.settingsFrame.setActivePopup(popup);
      this.settingsFrame.i$src$Lgg_vape_ui_click_frame_FrameToolbarComponent_$gnpgc6().showBackNavigation("New Profile", false);
   }

   public ProfileCreatePanelComponent(ProfilesSettingsFrame profilesSettingsFrame) {
      this.createButton = new ProfileCreateActionButtonComponent("Create new", true, false, 0.8, null, "newadd", 0.8, J.z(), J.l);
      this.nameInput = new ProfileCreateNameInputComponent("Type name", null);
      this.divider = new ProfileCreateDividerComponent();
      this.settingsFrame = profilesSettingsFrame;
      this.publicProfilesButton.addClickListener(ProfileCreatePanelComponent::openPublicProfiles);
      this.publicProfilesButton.w("Browse public profiles");
      this.createButton.addClickListener(this::startProfileCreation);
      this.createButton.w("Create a new profile");
      this.addChildren(this.createButton, this.publicProfilesButton, this.divider);
   }

   ProfilesSettingsFrame getSettingsFrame() {
      return this.settingsFrame;
   }

   @Override
   public void H() {
      this.createButton.setTextScale(0.7);
      this.publicProfilesButton.setTextScale(0.7);
      this.createButton.setIconOffset(2.0);
      this.createButton.K(this.G$src$D$1b2f02a() + 5.0);
      this.createButton.S(this.n());
      this.createButton.Y(this.L() - 5.5);
      this.publicProfilesButton.setIconOffset(1.0);
      this.publicProfilesButton.S(this.n());
      this.publicProfilesButton.K(this.G$src$D$1b2f02a() + this.A() - this.publicProfilesButton.A() - 5.0);
      this.publicProfilesButton.Y(this.L() - 5.5);
      this.divider.setVisible(false);
   }

   @Override
   public double C() {
      return 20.0;
   }

   @Override
   public void g(GuiMouseEvent event) {
   }

   public void setPendingProfile(Profile profile) {
      this.pendingProfile = profile;
   }

   private static void openPublicProfiles() {
      PublicProfilesFrame.w$src$V$fyo9a0();
   }

   @Override
   public double x() {
      return 110.0;
   }
}
