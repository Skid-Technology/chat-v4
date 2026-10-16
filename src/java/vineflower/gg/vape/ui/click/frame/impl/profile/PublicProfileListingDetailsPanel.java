package gg.vape.ui.click.frame.impl.profile;

import com.google.gson.JsonObject;
import gg.vape.Vape;
import gg.vape.api.ApiResponse;
import gg.vape.api.ApiServices;
import gg.vape.api.PagedResult;
import gg.vape.config.Profile;
import gg.vape.config.ProfileRemoteMetadata;
import gg.vape.config.ProfileSnapshot;
import gg.vape.config.PublicProfile;
import gg.vape.config.PublicProfileReview;
import gg.vape.config.PublicProfileReviewDisplayType;
import gg.vape.manager.client.OnlineConnectionManager;
import gg.vape.manager.client.PublicProfileManager;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.none.ClientSettings;
import gg.vape.sync.RemoteProfileData;
import gg.vape.ui.click.component.ActionButtonGroupComponent;
import gg.vape.ui.click.component.CenteredGlyphComponent;
import gg.vape.ui.click.component.CollapsiblePanelComponent;
import gg.vape.ui.click.component.ConfirmationDialogComponent;
import gg.vape.ui.click.component.DualTextLabelRowComponent;
import gg.vape.ui.click.component.FlowLayoutComponent;
import gg.vape.ui.click.component.GlyphIconComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.component.PagedResultListComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.component.SkeletonPlaceholderComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.TwoLineTextDisplayComponent;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.ui.click.layout.WrappingFlowLayout;
import gg.vape.utils.ClipboardUtil;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import org.jetbrains.annotations.Nullable;

public class PublicProfileListingDetailsPanel extends PublicProfileSnapshotPanelBase {
   private IconButtonComponent likeButton = new IconButtonComponent("like active@2x", 0.8);
   private IconButtonComponent dislikeButton = new IconButtonComponent("dislike active@2x", 0.8);
   private PagedResultListComponent reviewsList;

   private void deleteOwnReview() {
      if (this.publicProfile.getViewerReview() != null) {
         PublicProfileReview rev = this.publicProfile.getViewerReview();
         Vape.INSTANCE.getPublicProfileManager().removeCachedUserReview(this.publicProfile.getProfileId());
         rev.delete(
            this.publicProfile,
            () -> {
               this.publicProfile.setViewerReview(null);
               if (this.publicProfile.getReviews() != null && this.publicProfile.getReviews().getContent() != null) {
                  this.publicProfile
                     .getReviews()
                     .getContent()
                     .removeIf(r -> r.getCommentId() == rev.getCommentId() || OnlineConnectionManager.INSTANCE.isCurrentUser(r.getCommenter()));
                  this.publicProfile.getReviews().setTotalElements(this.publicProfile.getReviews().getContent().size());
               }

               this.setupFooter();
               this.rebuildReviews();
            }
         );
      }
   }

   @Override
   protected void e() {
      super.e();
      if (this.snapshot != null && this.publicProfile != null) {
         if (this.publicProfile.getViewerReview() == null) {
            if (this.publicProfile.getReviews() != null && this.publicProfile.getReviews().getContent() != null) {
               for (PublicProfileReview r : this.publicProfile.getReviews().getContent()) {
                  if (OnlineConnectionManager.INSTANCE.isCurrentUser(r.getCommenter())) {
                     this.publicProfile.setViewerReview(r);
                     break;
                  }
               }
            }

            if (this.publicProfile.getViewerReview() == null) {
               PublicProfileReview cached = Vape.INSTANCE.getPublicProfileManager().getCachedUserReview(this.publicProfile.getProfileId());
               if (cached != null) {
                  this.publicProfile.setViewerReview(cached);
               }
            }
         }

         this.setupFooter();
      } else {
         this.showLoadingState();
      }
   }

   private void changeOwnReview(boolean positive) {
      if (this.publicProfile.getViewerReview() != null) {
         PublicProfileReview rev = this.publicProfile.getViewerReview();
         Vape.INSTANCE.getPublicProfileManager().removeCachedUserReview(this.publicProfile.getProfileId());
         rev.delete(
            this.publicProfile,
            () -> {
               this.publicProfile.setViewerReview(null);
               if (this.publicProfile.getReviews() != null && this.publicProfile.getReviews().getContent() != null) {
                  this.publicProfile
                     .getReviews()
                     .getContent()
                     .removeIf(r -> r.getCommentId() == rev.getCommentId() || OnlineConnectionManager.INSTANCE.isCurrentUser(r.getCommenter()));
                  this.publicProfile.getReviews().setTotalElements(this.publicProfile.getReviews().getContent().size());
               }

               this.setupFooter();
               this.rebuildReviews();
               this.openReviewComposer(positive);
            }
         );
      } else {
         this.openReviewComposer(positive);
      }
   }

   private static ApiResponse ignoreDownloadFailure(Throwable throwable) {
      return null;
   }

   private void showDetailsPanel() {
      assert this.publicProfile != null;

      this.b$src$V$s019hq();
      double d = this.gg.A();
      this.getClass();
      double d2 = d - 5.0;
      PanelComponent panelComponent2 = new PanelComponent(d2, 12.0);
      panelComponent2.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
      panelComponent2.setShowDisabledOverlay(false);
      this.gg.h(panelComponent2);
      SimpleTextLabelComponent simpleTextLabelComponent = new SimpleTextLabelComponent("Details", 0.9);
      simpleTextLabelComponent.setOffsetX(0.0F);
      simpleTextLabelComponent.setBold(true);
      simpleTextLabelComponent.setTextColor(J.A);
      panelComponent2.h(simpleTextLabelComponent);
      simpleTextLabelComponent.o(panelComponent2.A());
      FlowLayoutComponent flowLayoutComponent = new FlowLayoutComponent(d2);
      flowLayoutComponent.setShowDisabledOverlay(false);
      CollapsiblePanelComponent collapsiblePanelComponent = new CollapsiblePanelComponent(this.publicProfile.getDescription(), d2);
      SimpleTextLabelComponent simpleTextLabelComponent2 = new SimpleTextLabelComponent(
         "Created: " + PublicProfileDateFormatUtil.H(this.publicProfile.getCreationDate()), 0.8
      );
      simpleTextLabelComponent2.Y(8.0);
      simpleTextLabelComponent2.o(d2);
      simpleTextLabelComponent2.setOffsetX(0.0F);
      collapsiblePanelComponent.getContentLayout().h(new SpacerComponent(0.0, 6.0));
      collapsiblePanelComponent.getContentLayout().h(simpleTextLabelComponent2);
      PanelComponent panelComponent3 = new PanelComponent(d2, 10.0);
      panelComponent3.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().Q(true);
      panelComponent3.setShowDisabledOverlay(false);
      SimpleTextLabelComponent simpleTextLabelComponent3 = new SimpleTextLabelComponent("Share code: " + this.publicProfile.getUppercaseShareCode(), 0.8);
      simpleTextLabelComponent3.Y(8.0);
      simpleTextLabelComponent3.setOffsetX(0.0F);
      panelComponent3.h(simpleTextLabelComponent3);
      GlyphIconComponent glyphIconComponent = new GlyphIconComponent("newcopy", 4.0, 4.0, 8.0, 8.0, J.W, J.f, J.l);
      glyphIconComponent.setOutlineAlpha(0.75F);
      glyphIconComponent.setOffsetX(2.0);
      glyphIconComponent.setOffsetY(2.0);
      panelComponent3.h(new SpacerComponent(4.0, 0.0));
      panelComponent3.h(glyphIconComponent);
      glyphIconComponent.setClickListener(this::copyShareCode);
      collapsiblePanelComponent.getContentLayout().h(panelComponent3);
      flowLayoutComponent.h(collapsiblePanelComponent);
      flowLayoutComponent.h(new SpacerComponent(0.0, 8.0));
      this.gg.h(flowLayoutComponent);
      if (!this.publicProfile.getTags().isEmpty()) {
         PanelComponent panelComponent = new PanelComponent(d2, 12.0);
         panelComponent.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
         panelComponent.setShowDisabledOverlay(false);
         this.gg.h(panelComponent);

         for (String stringArray2 : this.publicProfile.getTags()) {
            panelComponent.h(new PublicProfileFilterTokenComponent(stringArray2));
            panelComponent.h(new SpacerComponent(2.0, 0.0));
         }
      }

      this.gg.h(new SpacerComponent(0.0, 5.0));
      PanelComponent panelComponent = new PanelComponent(d2, 40.0);
      panelComponent.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
      panelComponent.setShowDisabledOverlay(false);
      this.gg.h(panelComponent);
      String[] stringArray3 = new String[]{"Positive reviews", "Last updated", "Downloads"};
      String[] stringArray = new String[]{
         String.valueOf(this.publicProfile.getLikes()),
         PublicProfileDateFormatUtil.i(this.publicProfile.getLatestDate()),
         String.valueOf(this.publicProfile.getDownloads())
      };
      panelComponent.h(new SpacerComponent(0.0, 5.0), "wrap");

      for (int function = 0; function < stringArray3.length; function++) {
         String string = stringArray3[function];
         String string2 = stringArray[function];
         TwoLineTextDisplayComponent twoLineTextDisplayComponent = new TwoLineTextDisplayComponent(string, string2);
         twoLineTextDisplayComponent.o(panelComponent.A() / stringArray3.length - 2.0);
         twoLineTextDisplayComponent.Y(29.0);
         panelComponent.h(new PaddedComponent(1.0, twoLineTextDisplayComponent), "widthwrap");
      }

      panelComponent.h(new SpacerComponent(0.0, 5.0), "wrap");
      this.reviewsList = new PagedResultListComponent(d2, 50.0, 2);
      this.reviewsList.setLoadThreshold(6);
      this.reviewsList.N(new WrappingFlowLayout(this.reviewsList));
      this.reviewsList.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
      this.reviewsList.setShowDisabledOverlay(false);
      this.reviewsList.N(false);
      this.reviewsList.setScrollContainer(this.gg);
      Function<PublicProfileReview, PublicProfileReviewComponent> reviewFactory = this::createReviewComponent;
      this.reviewsList.setPlaceholderSupplier(() -> createEmptyReviewComponent(reviewFactory));
      this.reviewsList.setPageLoader(() -> this.loadReviewsPage(reviewFactory));
      this.reviewsList.setPageMetadata(this.publicProfile.getReviews());
      this.gg.h(this.reviewsList);
      this.rebuildReviews();
   }

   private void handleDownloadComplete(ApiResponse apiResponse, Throwable throwable) {
      if (throwable != null) {
         Vape.logThrowable(throwable);
         PublicProfileManager.showWarning("Failed to download profile.");
      } else if (!apiResponse.isSuccessful()) {
         Vape.debugLog("Failed to download public profile: " + apiResponse.getError());
         PublicProfileManager.showWarning("Failed to view public profile: " + apiResponse.getError());
      } else {
         RemoteProfileData remoteProfileData = (RemoteProfileData)apiResponse.getData();

         assert remoteProfileData != null;

         JsonObject jsonObject = this.sanitizeRemoteProfileData(remoteProfileData);
         Profile profile = new Profile(remoteProfileData.getName(), remoteProfileData.getVapeVersion());
         profile.loadJson(jsonObject);
         profile.setPublishedData(jsonObject);
         profile.setRemoteMetadata(
            new ProfileRemoteMetadata(
               this.publicProfile.getProfileId(), Math.max(1L, this.publicProfile.getVersion()), Math.max(1L, this.publicProfile.getVersion())
            )
         );
         Vape.INSTANCE.getProfilesManager().addProfile(profile);
         this.setupFooter();
         PublicProfileManager.showInfo("Successfully downloaded " + remoteProfileData.getName());
      }
   }

   private PublicProfileReviewComponent createReviewComponent(PublicProfileReview review) {
      return new PublicProfileReviewComponent(this.publicProfile, review, this.reviewsList.A(), PublicProfileReviewDisplayType.OTHER)
         .setDeleteCallback(this::rebuildReviews);
   }

   private static ApiResponse ignoreUpdateFailure(Throwable throwable) {
      return null;
   }

   private CompletableFuture updateDownloadedProfile(Profile profile) {
      return ApiServices.getInstance()
         .getPublicProfileApi()
         .downloadProfileUpdate(this.publicProfile.getProfileId())
         .whenCompleteAsync((response, error) -> this.handleProfileUpdate(profile, response, error), ClientSettings.UI_EXECUTOR)
         .exceptionally(PublicProfileListingDetailsPanel::ignoreUpdateFailure);
   }

   private void showLoadingState() {
      this.showLoadingPlaceholders();
      this.gg.setVisible(false);
      this.gb.setVisible(false);
      double d = this.getRightPanel().A();
      this.getClass();
      PanelComponent panelComponent = new PanelComponent(d - 10.0, this.getRightPanel().L());
      panelComponent.setShowDisabledOverlay(false);
      panelComponent.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
      this.getRightPanel().h(panelComponent);
      panelComponent.h(new SkeletonPlaceholderComponent(30.0, 10.0));
      panelComponent.h(new SpacerComponent(0.0, 2.0));
      double d2 = panelComponent.A();
      this.getClass();
      panelComponent.h(new SkeletonPlaceholderComponent(d2 - 5.0, 20.0));
      panelComponent.h(new SpacerComponent(0.0, 2.0));
      panelComponent.h(new SkeletonPlaceholderComponent(panelComponent.A() / 2.0, 10.0));
      panelComponent.h(new SpacerComponent(0.0, 5.0));
      double d3 = panelComponent.A();
      this.getClass();
      panelComponent.h(new SkeletonPlaceholderComponent(d3 - 5.0, 30.0));
      panelComponent.h(new SpacerComponent(0.0, 5.0));
      panelComponent.h(new SkeletonPlaceholderComponent(50.0, 10.0), "widthwrap");
      panelComponent.h(new SpacerComponent(panelComponent.A() - 100.0, 2.0), "widthwrap");
      panelComponent.h(new SkeletonPlaceholderComponent(45.0, 10.0));
      panelComponent.h(new SpacerComponent(0.0, 5.0));
      double d4 = panelComponent.A();
      this.getClass();
      panelComponent.h(new SkeletonPlaceholderComponent(d4 - 5.0, 68.0));
   }

   private void startPositiveReview() {
      this.chooseReview(true);
   }

   private void setupFooter() {
      if (this.likeButton == null) {
         this.likeButton = new IconButtonComponent("like active@2x", 0.8);
         this.dislikeButton = new IconButtonComponent("dislike active@2x", 0.8);
      }

      this.s$src$V$1l7a8uk();
      this.gb.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
      this.gb.h(new SpacerComponent(0.0, 8.0), "wrap");
      this.gb.h(new SpacerComponent(5.0, 0.0));
      if (OnlineConnectionManager.INSTANCE.isCurrentUser(this.publicProfile.getOwner())) {
         this.gb.h(new SpacerComponent(45.0, 0.0));
      } else {
         this.likeButton.setHoverColor(J.O);
         this.likeButton.setClickListener(this::startPositiveReview);
         this.dislikeButton.setHoverColor(J.c);
         this.dislikeButton.setClickListener(this::startNegativeReview);
         this.updateVoteButtonColors();
         Object object = new ActionButtonGroupComponent(this.likeButton, this.dislikeButton);
         ((GuiComponent)object).o(45.0);
         ((GuiComponent)object).Y(15.0);
         this.gb.h((GuiComponent)object);
      }

      Object object = Vape.INSTANCE.getProfilesManager().getProfileByPublicProfileId(this.publicProfile.getProfileId());
      if (object == null) {
         this.gb.h(new SpacerComponent(15.0, 0.0));
         TextButton textButton = new TextButton("Download", 0.8, J.B, J.O);
         textButton.setDeriveTextColorFromBackground(false);
         textButton.setNormalTextColor(Color.WHITE);
         textButton.o(144.0);
         textButton.Y(15.0);
         textButton.setSingleFutureClickListener(this::downloadProfile);
         this.gb.h(textButton);
      } else {
         Profile downloadedProfile = (Profile)object;
         ProfileRemoteMetadata meta = downloadedProfile.getRemoteMetadata();
         boolean isUpToDate = false;
         if (meta != null) {
            if (meta.getVersion() > 0L && this.publicProfile.getVersion() > 0L) {
               isUpToDate = meta.getVersion() >= this.publicProfile.getVersion();
            } else {
               isUpToDate = !meta.hasNewerPublishedVersion();
            }
         }

         if (isUpToDate) {
            this.gb.h(new SpacerComponent(68.0, 0.0));
            GlyphIconComponent glyphIconComponent = new GlyphIconComponent("info", 8.0, 8.0, 8.0, 8.0, J.W, J.W, null);
            this.gb.h(new PaddedComponent(4.0, 0.0, 0.0, 0.0, glyphIconComponent));
            SimpleTextLabelComponent simpleTextLabelComponent = new SimpleTextLabelComponent("Downloaded and up to date", 0.8, J.Z, true);
            simpleTextLabelComponent.setOffsetX(3.0F);
            simpleTextLabelComponent.o(70.0);
            simpleTextLabelComponent.Y(17.0);
            this.gb.h(simpleTextLabelComponent);
         } else {
            this.gb.h(new SpacerComponent(12.0, 0.0));
            CenteredGlyphComponent centeredGlyphComponent = new CenteredGlyphComponent("info", 8.0F, 8.0F);
            centeredGlyphComponent.o(10.0);
            centeredGlyphComponent.Y(12.0);
            this.gb.h(centeredGlyphComponent);
            SimpleTextLabelComponent simpleTextLabelComponent = new SimpleTextLabelComponent("Previously downloaded", 0.8, J.Z, true);
            simpleTextLabelComponent.o(75.0);
            simpleTextLabelComponent.Y(17.0);
            this.gb.h(simpleTextLabelComponent);
            TextButton textButton = new TextButton("Update", 0.8, J.B, J.O);
            textButton.o(60.0);
            textButton.Y(15.0);
            textButton.setDeriveTextColorFromBackground(false);
            textButton.setNormalTextColor(Color.WHITE);
            textButton.setSingleFutureClickListener(() -> this.updateDownloadedProfile(downloadedProfile));
            this.gb.h(textButton);
         }
      }
   }

   private void updateVoteButtonColors() {
      if (this.publicProfile.getViewerReview() != null) {
         if (this.publicProfile.getViewerReview().isLiked()) {
            this.likeButton.setNormalColor(J.B);
            this.dislikeButton.setNormalColor(J.W);
         } else {
            this.dislikeButton.setNormalColor(J.d);
            this.likeButton.setNormalColor(J.W);
         }
      } else {
         this.likeButton.setNormalColor(J.W);
         this.dislikeButton.setNormalColor(J.W);
      }
   }

   private CompletableFuture loadReviewsPage(Function function) {
      return ApiServices.getInstance()
         .getPublicProfileApi()
         .getDelayedReviewPage(this.publicProfile, this.reviewsList.getNextPageIndex())
         .thenApplyAsync(response -> this.mapReviewsResponse(function, response), ClientSettings.UI_EXECUTOR);
   }

   private void handleProfileUpdate(Profile profile, ApiResponse apiResponse, Throwable throwable) {
      if (throwable != null) {
         Vape.logThrowable(throwable);
      } else if (!apiResponse.isSuccessful()) {
         Vape.debugLog("Failed to down profile update: " + apiResponse.getError());
         PublicProfileManager.showWarning("Failed to download profile update: " + apiResponse.getError());
      } else {
         RemoteProfileData remoteProfileData = (RemoteProfileData)apiResponse.getData();

         assert remoteProfileData != null;

         JsonObject jsonObject = this.sanitizeRemoteProfileData(remoteProfileData);
         profile.loadJson(jsonObject);
         profile.setRemoteMetadata(
            new ProfileRemoteMetadata(
               this.publicProfile.getProfileId(), Math.max(1L, this.publicProfile.getVersion()), Math.max(1L, this.publicProfile.getVersion())
            )
         );
         this.setupFooter();
         PublicProfileManager.showInfo("Successfully updated " + remoteProfileData.getName());
      }
   }

   private void copyShareCode() {
      ClipboardUtil.setText(this.publicProfile.getUppercaseShareCode());
      PublicProfileManager.showInfo("Copied share code to clipboard");
   }

   private List mapReviewsResponse(Function function, ApiResponse apiResponse) {
      if (!apiResponse.isSuccessful()) {
         return null;
      } else {
         assert apiResponse.getData() != null;

         PagedResult<PublicProfileReview> reviews = (PagedResult<PublicProfileReview>)apiResponse.getData();
         this.reviewsList.setPageMetadata(reviews);
         ArrayList arrayList = new ArrayList();

         for (PublicProfileReview publicProfileReview : reviews.getContent()) {
            arrayList.add(function.apply(publicProfileReview));
         }

         return arrayList;
      }
   }

   private void startNegativeReview() {
      this.chooseReview(false);
   }

   private static GuiComponent createEmptyReviewComponent(Function function) {
      return (PublicProfileReviewComponent)function.apply(null);
   }

   private CompletableFuture downloadProfile() {
      return ApiServices.getInstance()
         .getPublicProfileApi()
         .downloadProfile(this.publicProfile.getProfileId())
         .whenCompleteAsync(this::handleDownloadComplete, ClientSettings.UI_EXECUTOR)
         .exceptionally(PublicProfileListingDetailsPanel::ignoreDownloadFailure);
   }

   private void refreshAfterReview(boolean positive) {
      this.rebuildReviews();
      this.chooseReview(positive);
   }

   public PublicProfileListingDetailsPanel(
      PublicProfilesFrame publicProfilesFrame, @Nullable PublicProfile publicProfile, @Nullable ProfileSnapshot profileSnapshot
   ) {
      super(publicProfilesFrame, publicProfile, profileSnapshot);
      this.setDetailsCallback(this::showDetailsPanel);
      this.e();
   }

   @Override
   protected void customizeHeader(PanelComponent panelComponent) {
      if (this.publicProfile != null) {
         panelComponent.h(new SpacerComponent(0.0, 2.0));
         PanelComponent panelComponent2 = new PanelComponent(this.getLeftPanel().A(), 10.0);
         panelComponent2.setShowDisabledOverlay(false);
         panelComponent2.h(new SpacerComponent(5.0, 0.0));
         PublicProfileUserAvatarComponent publicProfileUserAvatarComponent = new PublicProfileUserAvatarComponent(this.publicProfile.getOwner(), 10.0, 10.0);
         panelComponent2.h(publicProfileUserAvatarComponent);
         SimpleTextLabelComponent simpleTextLabelComponent = new SimpleTextLabelComponent(
            "By " + (this.publicProfile.getOwner() != null ? this.publicProfile.getOwner().getUsername() : "Anonymous")
         );
         simpleTextLabelComponent.setBold(true);
         simpleTextLabelComponent.Y(10.0);
         panelComponent2.h(simpleTextLabelComponent);
         panelComponent.h(panelComponent2);
      }
   }

   private void openReviewComposer(boolean positive) {
      this.s$src$V$1l7a8uk();
      this.gb.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
      this.gb.h(new SpacerComponent(0.0, 8.0), "wrap");
      this.gb.h(new SpacerComponent(5.0, 0.0));
      PublicProfileReviewComposerComponent publicProfileReviewComposerComponent = new PublicProfileReviewComposerComponent(
         this.publicProfile, positive, false, this::setupFooter, this::rebuildReviews
      );
      publicProfileReviewComposerComponent.o(this.gb.A() - 5.0);
      publicProfileReviewComposerComponent.Y(this.gb.L() - 8.0);
      this.gb.h(publicProfileReviewComposerComponent);
   }

   private void chooseReview(boolean positive) {
      if (!OnlineConnectionManager.INSTANCE.isCurrentUser(this.publicProfile.getOwner())) {
         if (this.publicProfile.getViewerReview() != null) {
            if (this.publicProfile.getViewerReview().isLiked() == positive) {
               ConfirmationDialogComponent.showStandard(
                  this.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa(),
                  "Are you sure you want to delete your review?",
                  "Delete",
                  "newtrash",
                  this::deleteOwnReview
               );
            } else {
               ConfirmationDialogComponent.showStandard(
                  this.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa(),
                  "Are you sure you want to change your review?",
                  "Confirm",
                  "reset_circle",
                  () -> this.changeOwnReview(positive)
               );
            }
         } else {
            this.openReviewComposer(positive);
         }
      }
   }

   private JsonObject sanitizeRemoteProfileData(RemoteProfileData remoteProfileData) {
      JsonObject jsonObject = remoteProfileData.toJson();
      JsonObject jsonObject2 = jsonObject.getAsJsonObject("data");
      if (jsonObject2 == null) {
         return jsonObject;
      } else {
         JsonObject jsonObject3 = jsonObject2.getAsJsonObject("enabled");
         if (jsonObject3 == null) {
            return jsonObject;
         } else {
            for (Mod mod : Vape.INSTANCE.getModManager().collectMods()) {
               if (mod.getCategory() == Category.OTHER) {
                  jsonObject3.remove(mod.getName());
               }
            }

            return jsonObject;
         }
      }
   }

   private void rebuildReviews() {
      this.reviewsList.removeMarkedChildren();
      PanelComponent panelComponent = new PanelComponent(this.reviewsList.A(), 8.0);
      panelComponent.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
      panelComponent.setShowDisabledOverlay(false);
      this.reviewsList.h(panelComponent);
      DualTextLabelRowComponent reviewsLabel = new DualTextLabelRowComponent("Reviews", String.valueOf(this.publicProfile.getReviewCount()), 8.0, 0.8);
      panelComponent.h(reviewsLabel);
      PanelComponent panelComponent2 = new PanelComponent(65.0, panelComponent.L());
      panelComponent2.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
      panelComponent2.setShowDisabledOverlay(false);
      panelComponent.h(panelComponent2, "alignright");
      SimpleTextLabelComponent simpleTextLabelComponent = new SimpleTextLabelComponent(
         this.publicProfile.getApprovalPercentage() + "% positive reviews", 0.8, J.B
      );
      panelComponent2.h(simpleTextLabelComponent);
      this.reviewsList.h(new SpacerComponent(0.0, 5.0), "wrap");
      if (this.publicProfile.getViewerReview() == null) {
         if (this.publicProfile.getReviews() != null && this.publicProfile.getReviews().getContent() != null) {
            for (PublicProfileReview r : this.publicProfile.getReviews().getContent()) {
               if (OnlineConnectionManager.INSTANCE.isCurrentUser(r.getCommenter())) {
                  this.publicProfile.setViewerReview(r);
                  break;
               }
            }
         }

         if (this.publicProfile.getViewerReview() == null) {
            PublicProfileReview cached = Vape.INSTANCE.getPublicProfileManager().getCachedUserReview(this.publicProfile.getProfileId());
            if (cached != null) {
               this.publicProfile.setViewerReview(cached);
            }
         }
      }

      PublicProfileReview publicProfileReview = this.publicProfile.getViewerReview();
      if (publicProfileReview != null) {
         PublicProfileReviewComponent publicProfileReviewComponent = new PublicProfileReviewComponent(
               this.publicProfile, publicProfileReview, this.reviewsList.A(), PublicProfileReviewDisplayType.SELF
            )
            .setDeleteCallback(this::rebuildReviews);
         this.reviewsList.h(publicProfileReviewComponent);
         this.reviewsList.h(new SpacerComponent(0.0, 2.0));
      }

      if (this.publicProfile.getReviews() != null && this.publicProfile.getReviews().getContent() != null) {
         for (PublicProfileReview publicProfileReview2 : this.publicProfile.getReviews().getContent()) {
            if (publicProfileReview == null || publicProfileReview.getCommentId() != publicProfileReview2.getCommentId()) {
               this.reviewsList
                  .h(new PublicProfileReviewComponent(this.publicProfile, publicProfileReview2, this.reviewsList.A(), PublicProfileReviewDisplayType.OTHER));
               this.reviewsList.h(new SpacerComponent(0.0, 2.0));
            }
         }
      }

      this.reviewsList.setExplicitHeight(this.reviewsList.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().y());
      this.reviewsList.t(this.reviewsList.getExplicitHeight());
      this.updateVoteButtonColors();
   }
}
