package gg.vape.ui.click.frame.impl.profile;

import gg.vape.ui.click.component.GuiClickListener;

public class ProfilesShowActiveRowsClickHandler implements GuiClickListener {
   private final ProfilesSettingsFrame profilesFrame;

   public ProfilesShowActiveRowsClickHandler(ProfilesSettingsFrame profilesFrame) {
      this.profilesFrame = profilesFrame;
   }

   @Override
   public void onPrimaryClick() {
      this.profilesFrame.showActiveProfileRows();
      this.profilesFrame.getDoneButton().setVisible(false);
      this.profilesFrame.getEditHiddenProfilesButton().setVisible(true);
   }
}
