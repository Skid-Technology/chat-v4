package gg.vape.ui.click.frame.impl.profile;

import gg.vape.Vape;
import gg.vape.api.ApiResponse;
import gg.vape.api.ApiServices;
import gg.vape.config.PublicProfile;
import gg.vape.config.PublicProfileReview;
import gg.vape.manager.client.OnlineConnectionManager;
import gg.vape.manager.client.PublicProfileManager;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.FilledSpacerComponent;
import gg.vape.ui.click.component.FlowLayoutComponent;
import gg.vape.ui.click.component.GlyphIconComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.gui.AnimatedCenteredTextLabelComponent;
import gg.vape.ui.click.component.input.SmallTextInputComponent;
import gg.vape.ui.click.component.layout.PaddedComponent;
import java.awt.Color;
import java.util.concurrent.CompletableFuture;

public class PublicProfileReviewComposerComponent extends GuiComponent {
   private final boolean editingExistingReview;
   private final PublicProfile publicProfile;
   private final SmallTextInputComponent feedbackInput;
   static final boolean ASSERTIONS_DISABLED = !PublicProfileReviewComposerComponent.class.desiredAssertionStatus();
   private final Runnable reviewUpdatedCallback;
   private FlowLayoutComponent actionLayout = new FlowLayoutComponent(100.0);
   private final Runnable closeCallback;
   private final GlyphIconComponent submitButton;
   private final PublicProfileUserAvatarComponent avatar;
   private final PanelComponent container;

   @Override
   public double C() {
      return this.container.L();
   }

   private static ApiResponse lambda$submit$5(Throwable throwable) {
      return null;
   }

   private void lambda$submit$4(boolean bl, boolean bl2, ApiResponse apiResponse, Throwable throwable) {
      if (throwable != null) {
         PublicProfileManager.showWarning("Failed to leave review.");
         Vape.logThrowable(throwable);
      } else if (!apiResponse.isSuccessful()) {
         PublicProfileManager.showWarning("Failed to leave review: " + apiResponse.getError());
      } else if (!ASSERTIONS_DISABLED && apiResponse.getData() == null) {
         throw new AssertionError();
      } else {
         if (this.editingExistingReview) {
            PublicProfileManager.showInfo("Review updated!");
         } else {
            PublicProfileManager.showInfo("Review posted!");
         }

         if (this.publicProfile.getViewerReview() == null) {
            if (bl) {
               this.publicProfile.setLikes(this.publicProfile.getLikes() + 1L);
            } else {
               this.publicProfile.setDislikes(this.publicProfile.getDislikes() + 1L);
            }
         }

         PublicProfileReview review = (PublicProfileReview)apiResponse.getData();
         this.publicProfile.setViewerReview(review);
         if (this.publicProfile.getReviews() != null && this.publicProfile.getReviews().getContent() != null) {
            this.publicProfile
               .getReviews()
               .getContent()
               .removeIf(r -> r.getCommentId() == review.getCommentId() || OnlineConnectionManager.INSTANCE.isCurrentUser(r.getCommenter()));
            this.publicProfile.getReviews().getContent().add(0, review);
            this.publicProfile.getReviews().setTotalElements(this.publicProfile.getReviews().getContent().size());
         }

         Vape.INSTANCE.getPublicProfileManager().cacheUserReview(this.publicProfile.getProfileId(), review);
         this.reviewUpdatedCallback.run();
         if (bl2) {
            this.closeCallback.run();
         }
      }
   }

   private CompletableFuture handleSubmitReview(Runnable runnable, boolean bl) {
      runnable.run();
      String string = this.feedbackInput.getText().trim();
      if (!bl && string.length() < 10) {
         PublicProfileManager.showWarning("Failed to leave review: please leave a longer review");
         return CompletableFuture.completedFuture(null);
      } else {
         return this.Y(bl, string, true);
      }
   }

   private void handleKeyTyped(Color color, boolean bl, char c, int n) {
      this.submitButton.setNormalColor(color);
      this.submitButton.setInteractionDisabled(false);
   }

   private void updatePlaceholder(boolean bl) {
      if (bl) {
         this.feedbackInput.setPlaceholderText("Share additional feedback? (Optional)");
         this.feedbackInput.setPlaceholderColor(J.B);
      } else {
         this.feedbackInput.setPlaceholderText("Please leave a review...");
         this.feedbackInput.setPlaceholderColor(J.I);
      }
   }

   public SmallTextInputComponent k() {
      return this.feedbackInput;
   }

   private void handleCancel() {
      CompletableFuture.runAsync(this.closeCallback, ClientSettings.UI_EXECUTOR);
   }

   public PublicProfileReviewComposerComponent(PublicProfile publicProfile, boolean bl, boolean bl2, Runnable runnable, Runnable runnable2) {
      this.publicProfile = publicProfile;
      this.editingExistingReview = bl2;
      this.closeCallback = runnable;
      this.reviewUpdatedCallback = runnable2;
      this.container = new PanelComponent(20.0, 20.0);
      this.container.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
      this.container.setShowDisabledOverlay(false);
      this.avatar = new PublicProfileUserAvatarComponent(Vape.INSTANCE.getAccountInfo().getUserId(), 15.0, 15.0);
      this.avatar.setInset(2.0F);
      Color color = bl ? J.B : J.d;
      Color color2 = bl ? J.O : J.c;
      this.submitButton = new GlyphIconComponent("submit@2x", 6.0, 6.0, 6.0, 12.0, color, color2, null);
      this.submitButton.setNormalColor(color);
      this.submitButton.setInteractionDisabled(false);
      this.submitButton.setCenterVertically(true);
      AnimatedCenteredTextLabelComponent animatedCenteredTextLabelComponent = new AnimatedCenteredTextLabelComponent(bl && !bl2 ? "No thanks" : "Cancel", J.l);
      animatedCenteredTextLabelComponent.o(35.0);
      animatedCenteredTextLabelComponent.Y(12.0);
      animatedCenteredTextLabelComponent.setFontScale(0.75);
      animatedCenteredTextLabelComponent.setClickListener(this::handleCancel);
      this.feedbackInput = new SmallTextInputComponent("");
      PublicProfileReview publicProfileReview = publicProfile.getViewerReview();
      if (publicProfileReview != null) {
         this.feedbackInput.setText(publicProfileReview.getMessage());
         this.feedbackInput.addKeyTypedListener((arg_0, arg_1) -> this.handleKeyTyped(color, bl, arg_0, arg_1));
         this.handleKeyTyped(color, bl, ' ', 0);
         Runnable runnable3 = () -> this.updatePlaceholder(bl);
         runnable3.run();
         this.submitButton.setSingleFutureClickListener(() -> this.handleSubmitReview(runnable3, bl));
         this.actionLayout.h(this.submitButton);
         this.actionLayout.h(new FilledSpacerComponent(12.0, 12.0, 1.0, 6.0, J.l));
         this.actionLayout.h(animatedCenteredTextLabelComponent);
         this.actionLayout.setShowDisabledOverlay(false);
         this.container.addChildren(this.avatar, this.feedbackInput, new PaddedComponent(2.0, 0.0, 0.0, 0.0, this.actionLayout));
         this.addChildren(this.container);
         if (bl && !bl2) {
            this.Y(true, publicProfileReview.getMessage(), false);
         }
      } else {
         this.feedbackInput.addKeyTypedListener((arg_0, arg_1) -> this.handleKeyTyped(color, bl, arg_0, arg_1));
         this.handleKeyTyped(color, bl, ' ', 0);
         Runnable runnable4 = () -> this.updatePlaceholder(bl);
         runnable4.run();
         this.submitButton.setSingleFutureClickListener(() -> this.handleSubmitReview(runnable4, bl));
         this.actionLayout.h(this.submitButton);
         this.actionLayout.h(new FilledSpacerComponent(12.0, 12.0, 1.0, 6.0, J.l));
         this.actionLayout.h(animatedCenteredTextLabelComponent);
         this.actionLayout.setShowDisabledOverlay(false);
         this.container.addChildren(this.avatar, this.feedbackInput, new PaddedComponent(2.0, 0.0, 0.0, 0.0, this.actionLayout));
         this.addChildren(this.container);
         if (bl && !bl2) {
            this.Y(true, "", false);
         }
      }
   }

   @Override
   public void H() {
      this.container.K(this.G$src$D$1b2f02a());
      this.container.S(this.n());
      this.container.o(this.A());
      this.container.Y(this.L());
      this.feedbackInput.o(this.A() - (this.actionLayout.A() + 8.0) - this.avatar.A());
      this.container.l$src$V$1mibm4x();
   }

   private CompletableFuture<ApiResponse<PublicProfileReview>> Y(boolean bl, String string, boolean bl2) {
      return ApiServices.getInstance()
         .getPublicProfileApi()
         .createReview(this.publicProfile, bl, string)
         .whenCompleteAsync((arg_0, arg_1) -> this.lambda$submit$4(bl, bl2, arg_0, arg_1), ClientSettings.UI_EXECUTOR)
         .exceptionally(PublicProfileReviewComposerComponent::lambda$submit$5);
   }

   public PublicProfileUserAvatarComponent g$src$Lgg_vape_ui_click_frame_impl_profile_PublicProfi$1wft5vq() {
      return this.avatar;
   }

   @Override
   public double x() {
      return this.container.A();
   }
}
