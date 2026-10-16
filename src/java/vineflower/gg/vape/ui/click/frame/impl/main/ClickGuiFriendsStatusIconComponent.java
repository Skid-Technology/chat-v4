package gg.vape.ui.click.frame.impl.main;

import gg.vape.friend.OnlineStatus;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.utils.render.GlImageTexture;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.utils.render.RemoteImageTextureManager;
import java.awt.Color;
import org.jetbrains.annotations.Nullable;

final class ClickGuiFriendsStatusIconComponent extends GuiComponent {
   private static final String OFFLINE_AVATAR_ASSET = "avatar offline@2x";
   private boolean emphasized;
   private double statusDotSize;
   final ClickGuiFriendsFriendListComponent owner;

   @Override
   public double x() {
      return 10.0;
   }

   @Nullable
   private GlImageTexture loadAvatarTexture() {
      if (!this.owner.getFriend().isVisible()) {
         return null;
      } else {
         String string = this.owner.getFriend().getMinecraftUsername();
         if (string != null && !string.isEmpty()) {
            return this.owner.getFriend().getStatus() == OnlineStatus.OFFLINE ? null : RemoteImageTextureManager.getInstance().getTexture(string, 32);
         } else {
            return null;
         }
      }
   }

   @Override
   public void H() {
      double d = this.G$src$D$1b2f02a();
      double d2 = this.n();
      GlImageTexture glImageTexture = this.loadAvatarTexture();
      GuiRenderPrimitives.B(d, d2, 10.0, 10.0, J.m, 5.0F);
      if (glImageTexture != null) {
         GuiRenderPrimitives.u((float)d, (float)d2, 10.0F, 0.8F, Color.WHITE, glImageTexture);
      } else {
         ImageRenderer.drawImage(Color.WHITE, (float)d, (float)d2, "avatar offline@2x", 10.0F, 10.0F, false);
      }

      double d3 = 10.0;
      Color color = this.emphasized ? J.M : J.E;
      GuiRenderPrimitives.m((float)d, (float)d2, 10.0F, 1.0F, 0.8F, color);
      OnlineStatus onlineStatus = this.owner.getFriend().getStatus();
      if (onlineStatus != null) {
         Color color2 = onlineStatus.getColor();
         double d4 = d + 10.0 - this.statusDotSize;
         double d5 = d2 + 10.0 - this.statusDotSize;
         GuiRenderPrimitives.V((float)(d4 - 1.0), (float)(d5 - 1.0), (float)(this.statusDotSize + 2.0), 0.8F, J.m);
         GuiRenderPrimitives.V((float)d4, (float)d5, (float)this.statusDotSize, 0.8F, color2);
      }
   }

   @Override
   public void setHovered(boolean bl) {
      this.emphasized = bl;
   }

   ClickGuiFriendsStatusIconComponent(
      ClickGuiFriendsFriendListComponent clickGuiFriendsFriendListComponent, ClickGuiFriendsRowActions clickGuiFriendsRowActions
   ) {
      this(clickGuiFriendsFriendListComponent);
   }

   @Override
   public double C() {
      return 10.0;
   }

   private ClickGuiFriendsStatusIconComponent(ClickGuiFriendsFriendListComponent clickGuiFriendsFriendListComponent) {
      this.owner = clickGuiFriendsFriendListComponent;
      this.statusDotSize = 5.0;
      this.setAcceptsMouseInput(false);
      this.setShowDisabledOverlay(false);
   }

   public void setStatusDotSize(double size) {
      this.statusDotSize = size;
   }
}
