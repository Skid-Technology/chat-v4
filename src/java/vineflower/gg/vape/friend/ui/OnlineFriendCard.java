package gg.vape.friend.ui;

import gg.vape.Vape;
import gg.vape.friend.OnlineFriend;
import gg.vape.friend.OnlineStatus;
import gg.vape.ui.click.MouseClickButton;
import gg.vape.ui.click.animation.DoubleAnimation;
import gg.vape.ui.click.component.AnimatedPanelComponent;
import gg.vape.ui.click.component.FlowLayoutComponent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.component.MarqueeTextRendererComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.SquareIconButtonComponent;
import gg.vape.ui.click.component.TruncatedTextComponent;
import gg.vape.ui.notification.NotificationMessage;
import gg.vape.ui.notification.NotificationType;
import gg.vape.utils.ClipboardUtil;
import gg.vape.utils.render.GlImageTexture;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.RemoteImageTextureManager;
import java.awt.Color;

public class OnlineFriendCard extends PanelComponent {
   private final SpacerComponent closeButtonSpacer;
   private final AnimatedPanelComponent headerPanel = new AnimatedPanelComponent(99.0, 24.0);
   private final OnlineFriend friend;
   private boolean popupMode;
   private final OnlineFriendDetailsPanel detailsPanel;
   private final DoubleAnimation hoverAnimation;
   private final TruncatedTextComponent nameLabel;
   private final DoubleAnimation secondaryHoverAnimation;
   private final FlowLayoutComponent popupActions;
   private final IconButtonComponent closeButton;
   private final PanelComponent actionsContainer = new PanelComponent(20.0, 15.0);
   private final SpacerComponent compactActionSpacer;
   private boolean detailsExpanded;
   private final SpacerComponent compactActionSeparator;
   private final IconButtonComponent chatButton;
   private final IconButtonComponent inviteButton;
   private final FlowLayoutComponent compactActions;
   private final MarqueeTextRendererComponent marqueeTextRenderer = new MarqueeTextRendererComponent(this);

   @Override
   public void u() {
      this.hoverAnimation.u(this.w$src$Z$e457mb() || this.popupMode);
      if (this.popupMode) {
         this.getFriend().setUnreadMessage(false);
      }
   }

   public OnlineFriendCard(OnlineFriend onlineFriend) {
      super(99.0, 24.0);
      this.secondaryHoverAnimation = new DoubleAnimation(0.15, 0.0, 180.0);
      this.hoverAnimation = new DoubleAnimation(0.15, 0.0, 180.0);
      this.closeButton = new SquareIconButtonComponent("newclose", 1.0, new Color(0, 0, 0, 0), J.l, 10.0, 10.0);
      this.chatButton = new SquareIconButtonComponent("chat@2x", 0.5, new Color(0, 0, 0, 0), J.l, 8.0, 8.0);
      this.inviteButton = new SquareIconButtonComponent("party hover@2x", 0.5, new Color(0, 0, 0, 0), J.l, 8.0, 8.0);
      this.compactActionSpacer = new SpacerComponent(0.0, 0.0);
      this.compactActionSeparator = new SpacerComponent(10.0, 0.0);
      this.closeButtonSpacer = new SpacerComponent(6.0, 2.0);
      this.popupActions = new FlowLayoutComponent(20.0);
      this.compactActions = new OnlineFriendCardVisibleChildrenHeightComponent(this, 20.0);
      this.friend = onlineFriend;
      this.detailsPanel = new OnlineFriendDetailsPanel(this, this.friend);
      this.nameLabel = new TruncatedTextComponent(
         onlineFriend.getDisplayName(), "...", "Right click to copy username to clipboard", 75.0, 0.8, J.A, false, false
      );
      this.headerPanel.addMouseListener(new OnlineFriendCardToggleDetailsMouseListener(this));
      this.headerPanel.setShowDisabledOverlay(false);
      this.actionsContainer.setShowDisabledOverlay(false);
      this.actionsContainer.h(this.popupActions);
      this.actionsContainer.h(this.compactActions);
      this.actionsContainer.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().Q(true);
      this.popupActions.addChildren(this.closeButtonSpacer, this.closeButton);
      this.compactActions.addChildren(this.compactActionSeparator, this.inviteButton, this.compactActionSpacer, this.chatButton);
      this.inviteButton.w("Invite to party");
      this.chatButton.w("Open chat");
      this.popupActions.setVisible(false);
      this.headerPanel.addChildren(new SpacerComponent(18.0, 0.0), this.nameLabel);
      this.headerPanel.h(this.actionsContainer, "alignright");
      this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
      this.setShowDisabledOverlay(false);
      this.detailsPanel.setVisible(false);
      this.addChildren(this.headerPanel, this.detailsPanel);
      this.popupActions.setShowDisabledOverlay(false);
      this.compactActions.setShowDisabledOverlay(false);
      this.compactActions.setVisible(false);
   }

   @Override
   public void z(boolean bl) {
   }

   static void handleCardClick(OnlineFriendCard card, MouseClickButton mouseClickButton) {
      card.handleCardClick(mouseClickButton);
   }

   public OnlineFriend getFriend() {
      return this.friend;
   }

   private String getStatusText() {
      OnlineStatus onlineStatus = this.friend.getStatus();
      if (onlineStatus == OnlineStatus.OFFLINE) {
         return onlineStatus.getDisplayName();
      } else {
         String string2 = "";
         String string3 = null;
         String string4 = "";
         if (this.friend.isVisible()) {
            string3 = this.friend.getMinecraftUsername();
         }

         if (string3 != null) {
            string2 = string3;
            string4 = " - ";
         }

         String string;
         if ((string = this.friend.getMinecraftServer()) != null) {
            string2 = string2 + string4 + string;
         } else if (string3 == null) {
            string2 = string2 + string4 + onlineStatus.getDisplayName();
         }

         return string2;
      }
   }

   private void handleCardClick(MouseClickButton mouseClickButton) {
      if (mouseClickButton.equals(MouseClickButton.RIGHT_CLICK)) {
         ClipboardUtil.setText(this.getFriend().getDisplayName());
         OnlineFriendUiHelper.showNotification(
            new NotificationMessage(NotificationType.SUCCESS, "Copied " + this.getFriend().getDisplayName() + " to clipboard")
         );
      } else if (!this.popupMode) {
         this.detailsExpanded = !this.detailsExpanded;
         this.detailsPanel.setVisible(this.detailsExpanded);
         if (this.detailsExpanded) {
            this.setExplicitHeight(this.headerPanel.L() + this.detailsPanel.L());
         } else {
            this.setExplicitHeight(24.0);
         }
      }
   }

   public IconButtonComponent getChatButton() {
      return this.chatButton;
   }

   static FlowLayoutComponent getCompactActions(OnlineFriendCard card) {
      return card.compactActions;
   }

   @Override
   public void c() {
      if (this.popupMode) {
         GuiRenderPrimitives.C(this.G$src$D$1b2f02a() - 3.0, this.n() - 3.0, this.A() + 6.0, this.L() + 6.0, J.i);
      }

      if (this.friend.getStatus().equals(OnlineStatus.ONLINE)) {
         int n = Math.max(this.secondaryHoverAnimation.getInterpolatedValue().intValue(), this.hoverAnimation.getInterpolatedValue().intValue());
         double d = this.L() - 2.5;
         double d2 = this.A();
         double d3 = this.n();
         double d4 = this.G$src$D$1b2f02a();
         GuiRenderPrimitives.d(d4, d3, d2, d, J.m);
         GuiRenderPrimitives.P(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L() - 2.5, new Color(40, 40, 40, n), 3.0F, 1.0F, 1.0F);
         GlImageTexture glImageTexture = RemoteImageTextureManager.getInstance()
            .getTexture(this.friend.isVisible() && !this.friend.getStatus().equals(OnlineStatus.OFFLINE) ? this.friend.getMinecraftUsername() : "Steve", 32);
         if (glImageTexture != null) {
            GuiRenderPrimitives.m((float)this.G$src$D$1b2f02a() + 4.0F, (float)this.n() + 5.0F, 11.0F, 1.5F, 1.0F, this.friend.getStatus().getColor());
            float f = 1.0F;
            float f2 = 9.0F;
            float f3 = (float)this.n() + 6.0F;
            float f4 = (float)this.G$src$D$1b2f02a() + 5.0F;
            GuiRenderPrimitives.u(f4, f3, f2, f, Color.WHITE, glImageTexture);
         }

         this.nameLabel.setText(this.friend.getDisplayName());
         this.nameLabel
            .setMaxWidth(
               79.0
                  - this.compactActions.getVisibleChildrenWidth()
                  - (this.popupActions.V$src$Z$1xhop3l() ? this.popupActions.getVisibleChildrenWidth() - this.closeButtonSpacer.A() + 3.0 : 0.0)
            );
         TruncatedTextComponent truncatedTextComponent = this.nameLabel;
         truncatedTextComponent.setTextColor(J.A);
         this.nameLabel.S(this.n() + 4.0);
         double d5 = 0.7;
         double d6 = this.A() - 18.0 - 4.0;
         double d7 = this.n() + 12.0;
         double d8 = this.G$src$D$1b2f02a() + 18.0;
         String string = this.getStatusText();
         MarqueeTextRendererComponent renderer = this.marqueeTextRenderer;
         renderer.render(string, d8, d7, d6, d5, J.h);
         super.c();
         this.updateDisplayMode();
         if (this.friend.hasUnreadMessage()) {
            GuiRenderPrimitives.V(this.chatButton.G$src$D$1b2f02a() + 5.5, this.chatButton.n() + 0.5, 2.0, 1.0, J.d);
         }
      } else {
         int nx = Math.max(this.secondaryHoverAnimation.getInterpolatedValue().intValue(), this.hoverAnimation.getInterpolatedValue().intValue());
         double dx = this.L() - 2.5;
         double d9 = this.A();
         double d10 = this.n();
         double d11 = this.G$src$D$1b2f02a();
         GuiRenderPrimitives.d(d11, d10, d9, dx, new Color(27, 27, 27));
         GuiRenderPrimitives.P(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L() - 2.5, new Color(40, 40, 40, nx), 3.0F, 1.0F, 1.0F);
         GlImageTexture glImageTexturex = RemoteImageTextureManager.getInstance()
            .getTexture(this.friend.isVisible() && !this.friend.getStatus().equals(OnlineStatus.OFFLINE) ? this.friend.getMinecraftUsername() : "Steve", 32);
         if (glImageTexturex != null) {
            GuiRenderPrimitives.m((float)this.G$src$D$1b2f02a() + 4.0F, (float)this.n() + 5.0F, 11.0F, 1.5F, 1.0F, this.friend.getStatus().getColor());
            float f = 1.0F;
            float f5 = 9.0F;
            float f6 = (float)this.n() + 6.0F;
            float f7 = (float)this.G$src$D$1b2f02a() + 5.0F;
            GuiRenderPrimitives.u(f7, f6, f5, f, new Color(255, 255, 255, 150), glImageTexturex);
         }

         this.nameLabel.setText(this.friend.getDisplayName());
         this.nameLabel
            .setMaxWidth(
               79.0
                  - this.compactActions.getVisibleChildrenWidth()
                  - (this.popupActions.V$src$Z$1xhop3l() ? this.popupActions.getVisibleChildrenWidth() - this.closeButtonSpacer.A() + 3.0 : 0.0)
            );
         TruncatedTextComponent truncatedTextComponent = this.nameLabel;
         truncatedTextComponent.setTextColor(new Color(110, 110, 110));
         this.nameLabel.S(this.n() + 4.0);
         double d12 = 0.7;
         double d13 = this.A() - 18.0 - 4.0;
         double d14 = this.n() + 12.0;
         double d15 = this.G$src$D$1b2f02a() + 18.0;
         String string = this.getStatusText();
         MarqueeTextRendererComponent renderer = this.marqueeTextRenderer;
         renderer.render(string, d15, d14, d13, d12, new Color(68, 68, 68));
         super.c();
         this.updateDisplayMode();
         if (this.friend.hasUnreadMessage()) {
            GuiRenderPrimitives.V(this.chatButton.G$src$D$1b2f02a() + 5.5, this.chatButton.n() + 0.5, 2.0, 1.0, J.d);
         }
      }
   }

   public OnlineFriendDetailsPanel getDetailsPanel() {
      return this.detailsPanel;
   }

   public IconButtonComponent getCloseButton() {
      return this.closeButton;
   }

   public void setPopupMode(boolean popupMode) {
      this.popupMode = popupMode;
      this.updateDisplayMode();
   }

   public IconButtonComponent getInviteButton() {
      return this.inviteButton;
   }

   public void updateDisplayMode() {
      if (this.popupMode) {
         this.getDetailsPanel().showChat();
         this.popupActions.setVisible(true);
         this.compactActions.setVisible(false);
         this.detailsPanel.setVisible(true);
      } else {
         this.getDetailsPanel().showActions();
         this.popupActions.setVisible(false);
         this.compactActions.setVisible(true);
         boolean bl2 = Vape.INSTANCE.getOnlineManager().getPartyManager().getCurrentParty() != null;
         boolean bl3 = bl2 && Vape.INSTANCE.getOnlineManager().getPartyManager().getCurrentParty().getMembers().contains(this.friend);
         boolean bl = bl2 && !bl3 && this.friend.getStatus().equals(OnlineStatus.ONLINE);
         if (bl2 && Vape.INSTANCE.getOnlineManager().getPartyManager().getCurrentParty().getInvitedUsers().contains(this.friend)) {
            bl = false;
         }

         boolean bl5 = this.friend.hasChatHistory();
         this.chatButton.setVisible(bl5);
         this.inviteButton.setVisible(bl);
         boolean bl6 = bl5 ^ bl;
         this.compactActionSeparator.setVisible(bl6);
         this.compactActionSpacer.setVisible(!bl6);
         this.detailsPanel.setVisible(this.detailsExpanded);
      }
   }

   @Override
   public double C() {
      return this.popupMode ? 24.0 + this.detailsPanel.L() : super.C();
   }
}
