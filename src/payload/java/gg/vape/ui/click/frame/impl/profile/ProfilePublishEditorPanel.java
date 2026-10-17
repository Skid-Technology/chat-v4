package gg.vape.ui.click.frame.impl.profile;

import com.google.gson.JsonObject;
import gg.vape.Vape;
import gg.vape.api.ApiResponse;
import gg.vape.api.ApiServices;
import gg.vape.api.PublicProfileJsonPayloadBuilder;
import gg.vape.config.LegacyPublicProfile;
import gg.vape.config.Profile;
import gg.vape.config.ProfileModuleSnapshot;
import gg.vape.config.ProfileSnapshot;
import gg.vape.config.PublicProfile;
import gg.vape.friend.ui.OnlineFriendUiHelper;
import gg.vape.manager.client.PublicProfileManager;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.InsetFilledSpacerComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.ProfileSelectionPopupComponent;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.TextInputComponentBase;
import gg.vape.ui.click.component.TruncatedTextComponent;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.ui.click.component.gui.TextLabel;
import gg.vape.ui.click.component.value.BooleanToggleComponent;
import gg.vape.ui.notification.NotificationType;
import gg.vape.value.ValueSnapshot;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ProfilePublishEditorPanel extends PublicProfileOverlayPanelBase {
   private ProfileSnapshot snapshot;
   private Profile sourceProfile;
   private CompactPublicProfileFilterTokenSelectorComponent tagSelector;
   private TextInputComponentBase nameInput;
   private BooleanToggleComponent anonymousToggle;
   private BooleanToggleComponent shareCodeOnlyToggle;
   private BooleanToggleComponent friendsOnlyToggle;
   private TruncatedTextComponent titleLabel;
   private TextInputComponentBase descriptionInput;
   private TextButton publishButton;

   private void showDetailsForm() {
      this.b$src$V$s019hq();
      double d = this.gg.A() - 3.0;
      SimpleTextLabelComponent simpleTextLabelComponent = new SimpleTextLabelComponent("NAME", 0.7, J.C, true);
      simpleTextLabelComponent.setOffsetX(0.0F);
      this.gg.h(simpleTextLabelComponent);
      if (this.nameInput == null) {
         this.nameInput = new ProfilePublishFirstFixedWidthNoSubmitInputComponent("+   Enter profile name...", d);
      }

      this.nameInput.setHorizontalInset(0.0);
      this.nameInput.setLeftInset(0.0F);
      this.nameInput.setRightInset(1.0F);
      this.nameInput.setTextColor(J.A);
      this.nameInput.setPlaceholderColor(J.Z);
      this.nameInput.getActionButton().setVisible(false);
      this.nameInput.setShowDisabledOverlay(false);
      this.nameInput.setBackgroundVisible(false);
      this.nameInput.addKeyTypedListener(this::handleNameInput);
      this.gg.h(this.nameInput);
      this.gg.h(new InsetFilledSpacerComponent(d, 2.0, 0.5, 0.0, J.l));
      this.gg.h(new SpacerComponent(0.0, 5.0));
      SimpleTextLabelComponent simpleTextLabelComponent2 = new SimpleTextLabelComponent("DESCRIPTION", 0.7, J.C, true);
      simpleTextLabelComponent2.setOffsetX(0.0F);
      this.gg.h(simpleTextLabelComponent2);
      if (this.descriptionInput == null) {
         this.descriptionInput = new ProfilePublishSecondFixedWidthNoSubmitInputComponent("+   Add Description (optional)", d);
      }

      this.descriptionInput.setHorizontalInset(0.0);
      this.descriptionInput.setLeftInset(0.0F);
      this.descriptionInput.setRightInset(1.0F);
      this.descriptionInput.setTextColor(J.A);
      this.descriptionInput.setPlaceholderColor(J.Z);
      this.descriptionInput.getActionButton().setVisible(false);
      this.descriptionInput.setShowDisabledOverlay(false);
      this.descriptionInput.setBackgroundVisible(false);
      this.gg.h(this.descriptionInput);
      this.gg.h(new InsetFilledSpacerComponent(d, 2.0, 0.5, 0.0, J.l));
      this.gg.h(new SpacerComponent(0.0, 5.0));
      SimpleTextLabelComponent simpleTextLabelComponent3 = new SimpleTextLabelComponent("TAGS", 0.7, J.C, true);
      simpleTextLabelComponent3.setOffsetX(0.0F);
      this.gg.h(simpleTextLabelComponent3, "widthwrap");
      SimpleTextLabelComponent simpleTextLabelComponent4 = new SimpleTextLabelComponent("Comma-Seperated", 0.7, J.h, false);
      double d2 = simpleTextLabelComponent4.getTextWidth();
      this.getClass();
      simpleTextLabelComponent4.o(d2 + 10.0);
      this.gg.h(simpleTextLabelComponent4, "alignright, wrap");
      if (this.tagSelector == null) {
         this.tagSelector = new CompactPublicProfileFilterTokenSelectorComponent("+   Add Tags (optional)", d, 20.0);
      }

      this.gg.h(this.tagSelector, "wrap");
      this.gg.h(new InsetFilledSpacerComponent(d, 2.0, 0.5, 0.0, J.l));
      this.gg.h(new SpacerComponent(0.0, 5.0));
      SimpleTextLabelComponent simpleTextLabelComponent5 = new SimpleTextLabelComponent("PREFERENCES", 0.7, J.C, true);
      simpleTextLabelComponent5.setOffsetX(0.0F);
      this.gg.h(simpleTextLabelComponent5);
      if (this.shareCodeOnlyToggle == null) {
         this.shareCodeOnlyToggle = new BooleanToggleComponent("Discoverable with a Share Code only", 0.8);
      }

      this.shareCodeOnlyToggle.setHorizontalInset(0.0);
      this.shareCodeOnlyToggle.setExplicitWidth(d);
      this.shareCodeOnlyToggle.setShowDisabledOverlay(false);
      this.gg.h(this.shareCodeOnlyToggle);
      if (this.friendsOnlyToggle == null) {
         this.friendsOnlyToggle = new BooleanToggleComponent("Friends only discovery", 0.8);
         this.shareCodeOnlyToggle.addMouseListener(new ProfilePublishEditorBooleanToggleClickHandler(this));
         this.friendsOnlyToggle.setVisible(false);
      }

      this.friendsOnlyToggle.setHorizontalInset(0.0);
      this.friendsOnlyToggle.setExplicitWidth(d);
      this.friendsOnlyToggle.setShowDisabledOverlay(false);
      this.gg.h(this.friendsOnlyToggle);
      if (this.anonymousToggle == null) {
         this.anonymousToggle = new BooleanToggleComponent("Upload anonymously", 0.8);
      }

      this.anonymousToggle.setHorizontalInset(0.0);
      this.anonymousToggle.setExplicitWidth(d);
      this.anonymousToggle.setShowDisabledOverlay(false);
      this.gg.h(this.anonymousToggle);
   }

   private void handlePublishComplete(ApiResponse apiResponse, Throwable throwable) {
      if (throwable != null) {
         Vape.logThrowable(throwable);
         PublicProfileManager.showWarning("Failed to publish profile.");
         this.profilesFrame.O(null);
      } else if (!apiResponse.isSuccessful()) {
         PublicProfileManager.showWarning("Failed to publish profile: " + apiResponse.getError());
      } else {
         this.profilesFrame.O(null);
         PublicProfile publicProfile = (PublicProfile)apiResponse.getData();

         assert publicProfile != null;

         Vape.INSTANCE.getPublicProfileManager().addProfile(publicProfile);
         this.profilesFrame.N(publicProfile);
      }
   }

   private static ApiResponse ignoreHandledPublishFailure(Throwable throwable) {
      return null;
   }

   private static int compareDefaultValuesLast(ValueSnapshot<?, ?> first, ValueSnapshot<?, ?> second) {
      return Boolean.compare(first.isDefault(), second.isDefault());
   }

   void toggleFriendsOnlyVisibility() {
      this.friendsOnlyToggle.setVisible(!this.friendsOnlyToggle.V$src$Z$1xhop3l());
   }

   private void showModuleDetails(ProfileModuleSnapshot moduleSnapshot) {
      this.b$src$V$s019hq();
      double d = this.gg.A();
      this.getClass();
      double d2 = d - 5.0;
      PanelComponent panelComponent = new PanelComponent(d2, 12.0);
      panelComponent.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
      panelComponent.setShowDisabledOverlay(false);
      this.gg.h(panelComponent);
      SimpleTextLabelComponent simpleTextLabelComponent = new SimpleTextLabelComponent(moduleSnapshot.getName(), 1.0);
      simpleTextLabelComponent.setBold(true);
      simpleTextLabelComponent.setTextColor(J.A);
      panelComponent.h(simpleTextLabelComponent);

      for (ValueSnapshot valueSnapshot : moduleSnapshot.getValueSnapshots()
         .stream()
         .sorted(ProfilePublishEditorPanel::compareDefaultValuesLast)
         .collect(Collectors.toList())) {
         ProfileSnapshotValueRowComponent profileSnapshotValueRowComponent = new ProfileSnapshotValueRowComponent(valueSnapshot);
         profileSnapshotValueRowComponent.o(this.gg.A() - 5.0);
         profileSnapshotValueRowComponent.setDisabledOverlayColor(J.m);
         this.gg.h(profileSnapshotValueRowComponent);
      }
   }

   private void setupFooter() {
      this.s$src$V$1l7a8uk();
      this.gb.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
      this.gb.h(new SpacerComponent(0.0, 10.0), "wrap");
      this.gb.h(new SpacerComponent(105.0, 0.0));
      TextLabel textLabel = new TextLabel("CANCEL", 0.7, true);
      textLabel.setTextColor(null);
      textLabel.setUseAlternateFont(true);
      textLabel.o(40.0);
      textLabel.Y(15.0);
      textLabel.addClickListener(this::cancelPublishing);
      this.gb.h(textLabel);
      this.publishButton = new TextButton("PUBLISH", 0.7, J.B, J.O);
      this.publishButton.setUseAlternateFont(true);
      this.publishButton.o(60.0);
      this.publishButton.Y(15.0);
      this.publishButton.setDeriveTextColorFromBackground(false);
      this.publishButton.setNormalTextColor(Color.WHITE);
      this.updatePublishButtonState();
      this.publishButton.setSingleFutureClickListener(this::publishProfile);
      this.gb.h(this.publishButton);
   }

   private void cancelPublishing() {
      this.profilesFrame.O(null);
   }

   private void changeSourceProfile(Profile profile) {
      ProfileSnapshot profileSnapshot = profile.createSnapshot(true);
      if (profileSnapshot == null) {
         OnlineFriendUiHelper.showNotification(NotificationType.WARNING, "Failed to change derived from.");
      } else {
         this.sourceProfile = profile;
         this.snapshot = profileSnapshot;
         this.e();
      }
   }

   public ProfilePublishEditorPanel(PublicProfilesFrame publicProfilesFrame, Profile profile) {
      super(publicProfilesFrame);
      this.sourceProfile = profile;
      Vape.INSTANCE.getProfilesManager().captureProfileState(this.sourceProfile);
      this.snapshot = this.sourceProfile.createSnapshot(false);
      this.e();
   }

   private void openModuleDetails(ProfileModuleSnapshot moduleSnapshot) {
      this.showModuleDetails(moduleSnapshot);
   }

   private void handleNameInput(char character, int keyCode) {
      this.titleLabel.setText(this.nameInput.getText());
      this.updatePublishButtonState();
   }

   private CompletableFuture<ApiResponse> publishProfile() {
      String string = this.nameInput.getText().trim();
      if (string.length() < 3) {
         PublicProfileManager.showWarning("Please provide a profile name!");
         this.nameInput.setPlaceholderColor(J.d);
         return null;
      } else {
         String string2 = this.descriptionInput.getText().trim();
         boolean bl = this.shareCodeOnlyToggle.isOn();
         boolean bl2 = this.friendsOnlyToggle.isOn();
         boolean bl3 = this.anonymousToggle.isOn();
         ArrayList<String> arrayList = new ArrayList<>(this.tagSelector.getTokenValues());
         String object;
         if (arrayList.size() < 5 && (object = LegacyPublicProfile.normalizeTag(this.tagSelector.getInput().getText().trim())) != null) {
            String string3 = LegacyPublicProfile.validateTag(object);
            if (string3 != null) {
               PublicProfileManager.showWarning(string3);
               return null;
            }

            arrayList.add(object);
            this.tagSelector.addToken(new PublicProfileFilterTokenComponent(object));
            this.tagSelector.getInput().setText("");
         }

         if (this.snapshot != null) {
            this.snapshot.applyToProfile();
         }

         Vape.INSTANCE.getProfilesManager().captureProfileState(this.sourceProfile);
         JsonObject profileData = this.sourceProfile.copyPublishedData();
         return ApiServices.getInstance()
            .getPublicProfileApi()
            .createProfile(
               PublicProfileJsonPayloadBuilder.build(string, "4.22", string2, arrayList, !bl, bl3, bl2, this.sourceProfile.getOnlineId(), profileData)
            )
            .whenCompleteAsync(this::handlePublishComplete, ClientSettings.UI_EXECUTOR)
            .exceptionally(ProfilePublishEditorPanel::ignoreHandledPublishFailure);
      }
   }

   @Override
   protected void e() {
      super.e();
      if (this.sourceProfile != null) {
         double d = 20.0;
         this.o(this.profilesFrame.A() - d);
         double d2 = this.profilesFrame.L() - this.profilesFrame.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().L() - 2.0 - d;
         this.getClass();
         this.Y(d2 - 5.0);
         PanelComponent panelComponent = this.getLeftPanel();
         this.getLeftPanel().setShowDisabledOverlay(false);
         this.getLeftPanel().l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
         this.h(panelComponent);
         double d3 = panelComponent.A();
         this.getClass();
         PanelComponent panelComponent2 = new PanelComponent(d3 - 10.0, 25.0);
         panelComponent2.setShowDisabledOverlay(false);
         panelComponent2.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
         PanelComponent panelComponent3 = this.getLeftPanel();
         this.getClass();
         panelComponent3.h(new SpacerComponent(5.0, 0.0), "widthwrap");
         panelComponent.h(panelComponent2);
         double d4 = panelComponent2.A();
         this.getClass();
         this.titleLabel = new TruncatedTextComponent("New Profile", "...", d4 - 10.0, 1.0, J.A, true);
         this.titleLabel.Y(0.0);
         this.titleLabel.setExplicitHeight(0.0);
         PanelComponent panelComponent4 = new PanelComponent(panelComponent2.A(), 8.0);
         panelComponent4.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
         panelComponent4.setShowDisabledOverlay(false);
         this.getClass();
         panelComponent4.h(new SpacerComponent(5.0, 0.0), "widthwrap");
         panelComponent4.h(this.titleLabel);
         panelComponent2.h(panelComponent4);
         this.getClass();
         panelComponent2.h(new SpacerComponent(0.0, 5.0));
         List<Profile> list = Vape.INSTANCE.getPublicProfileManager().getDerivedProfiles();
         ArrayList<Profile> arrayList = new ArrayList<>(Vape.INSTANCE.getProfilesManager().getProfiles());
         arrayList.removeIf(list::contains);
         ProfileSelectionPopupComponent profileSelectionPopupComponent = new ProfileSelectionPopupComponent(
            "Derived From", this.sourceProfile, arrayList.toArray(new Profile[0])
         );
         profileSelectionPopupComponent.Y(6.0);
         profileSelectionPopupComponent.o(panelComponent2.A());
         profileSelectionPopupComponent.setSelectionCallback(this::changeSourceProfile);
         profileSelectionPopupComponent.setShowDisabledOverlay(false);
         panelComponent2.h(profileSelectionPopupComponent, "widthwrap");
         PanelComponent panelComponent5 = new PanelComponent(panelComponent.A(), panelComponent.L() - panelComponent2.L() - 6.0);
         panelComponent.h(panelComponent5);
         panelComponent5.setShowDisabledOverlay(false);
         panelComponent5.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
         PublicProfileOverlayCloseButton publicProfileOverlayCloseButton = new PublicProfileOverlayCloseButton(
            "Details", 0.8, true, this, this::showDetailsForm
         );
         publicProfileOverlayCloseButton.setUseExplicitWidth(true);
         publicProfileOverlayCloseButton.o(panelComponent5.A());
         panelComponent5.h(publicProfileOverlayCloseButton);
         panelComponent5.h(new SpacerComponent(0.0, 6.0));
         List<ProfileModuleSnapshot> list2 = this.snapshot.getSortedModules(false);
         ProfileSnapshotModuleCountEmptyStateComponent profileSnapshotModuleCountEmptyStateComponent = new ProfileSnapshotModuleCountEmptyStateComponent(
            list2.size()
         );
         panelComponent5.h(profileSnapshotModuleCountEmptyStateComponent);
         PanelComponent panelComponent6 = new PanelComponent(
            panelComponent5.A(), panelComponent5.L() - profileSnapshotModuleCountEmptyStateComponent.L() - publicProfileOverlayCloseButton.L()
         );
         panelComponent6.setShowDisabledOverlay(false);
         panelComponent6.t(panelComponent6.L());
         panelComponent5.h(panelComponent6);
         panelComponent6.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");

         for (ProfileModuleSnapshot profileModuleSnapshot : list2) {
            PublicProfileOverlayCloseButton publicProfileOverlayCloseButton2 = new PublicProfileOverlayCloseButton(
               profileModuleSnapshot.getName(), 0.8, this, () -> this.openModuleDetails(profileModuleSnapshot)
            );
            publicProfileOverlayCloseButton2.setUseExplicitWidth(true);
            publicProfileOverlayCloseButton2.o(panelComponent6.A() - 2.0);
            panelComponent6.h(publicProfileOverlayCloseButton2);
         }

         this.setupFooter();
      }
   }

   private void updatePublishButtonState() {
      String string = this.nameInput.getText().trim();
      if (string.length() < 3) {
         this.publishButton.setBackgroundAnimationColors(J.l, J.l);
         this.publishButton.setNormalTextColor(J.C);
      } else {
         this.publishButton.setBackgroundAnimationColors(J.B, J.O);
         this.publishButton.setNormalTextColor(Color.WHITE);
      }

      this.nameInput.setPlaceholderColor(J.Z);
   }
}
