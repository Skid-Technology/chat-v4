package gg.vape.friend;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiComponentContract;
import java.awt.Color;

public enum FriendRelationColorState {
   FRIEND(ClientSettings.INSTANCE.getAccentColor()),
   SYNCED(GuiComponentContract.J.T),
   ENEMY(GuiComponentContract.J.d);

   Color color;
   private static final FriendRelationColorState[] VALUES = new FriendRelationColorState[]{FRIEND, SYNCED, ENEMY};

   private FriendRelationColorState(Color color) {
      this.color = color;
   }

   public Color getColor() {
      return this.color;
   }

   public void setColor(Color color) {
      this.color = color;
   }

   static {
      String[] var10000 = new String[]{"ENEMY", "FRIEND", "SYNCED"};
   }
}
