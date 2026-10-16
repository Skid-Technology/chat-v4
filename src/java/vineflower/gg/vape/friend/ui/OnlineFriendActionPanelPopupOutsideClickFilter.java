package gg.vape.friend.ui;

import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.frame.PopupFrame;
import java.awt.Point;

public class OnlineFriendActionPanelPopupOutsideClickFilter implements GuiMouseListener {
   private final OnlineFriendActionPanel actionPanel;
   private final PopupFrame popupFrame;

   @Override
   public boolean Q(Point point) {
      return this.actionPanel.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa().getBounds().R(point) && !this.popupFrame.getBounds().R(point)
         ? true
         : GuiMouseListener.super.Q(point);
   }

   public OnlineFriendActionPanelPopupOutsideClickFilter(OnlineFriendActionPanel onlineFriendActionPanel, PopupFrame popupFrame) {
      this.actionPanel = onlineFriendActionPanel;
      this.popupFrame = popupFrame;
   }
}
