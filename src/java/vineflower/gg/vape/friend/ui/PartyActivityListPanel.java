package gg.vape.friend.ui;

import gg.vape.Vape;
import gg.vape.friend.OnlineFriend;
import gg.vape.friend.OnlineFriendActivityState;
import gg.vape.friend.PartyState;
import gg.vape.manager.client.OnlineConnectionManager;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.frame.SettingsFrameHeaderComponent;
import gg.vape.ui.click.frame.impl.hud.HudModuleConfigFrameBase;
import gg.vape.ui.click.frame.impl.hud.HudSettingsFrameBase;
import gg.vape.ui.click.frame.impl.main.ClickGuiFrameManager;
import gg.vape.wrapper.impl.Minecraft;
import java.util.ArrayList;
import java.util.List;

public class PartyActivityListPanel extends PanelComponent {
   private int activityHash;
   private static final String[] PREVIEW_NAMES = new String[]{"Player1", "Player2"};
   private final List<GuiComponent> previewComponents;
   private boolean inventoryVisible;
   private boolean previewVisible;
   private static final int OBFUSCATION_SEED;
   private final List<OnlineFriendActivityPanel> activityPanels = new ArrayList<>();
   private final OnlineActivityPanelOptions options;
   private int refreshTick;

   @Override
   public void I() {
      this.c();
   }

   @Override
   public double A() {
      return 114.0;
   }

   public PartyActivityListPanel() {
      super(114.0, 0.0);
      this.previewComponents = new ArrayList<>();
      this.setShowDisabledOverlay(false);
      this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
      this.options = OnlineActivityPanelOptions.INSTANCE;
   }

   @Override
   public double C() {
      return this.shouldShowEditorPreview() ? 108.0 : this.activityPanels.size() * 62;
   }

   @Override
   public void H() {
      boolean clickGuiActive = ClientSettings.INSTANCE != null && ClientSettings.INSTANCE.getActiveStack() instanceof ClickGuiFrameManager;
      double panelY = ClientSettings.INSTANCE.isInputEnabled()
         ? this.getParentFrameComponent().n()
         : (
            clickGuiActive
               ? this.getParentFrameComponent().n()
               : (
                  ((HudSettingsFrameBase)this.getParentFrameComponent()).q()
                     ? this.getParentFrameComponent().n() + 20.0
                     : this.getParentFrameComponent().n() + 107.0
               )
         );
      this.S(panelY);
      boolean showPreview = this.shouldShowEditorPreview();
      if (showPreview) {
         if (!this.previewVisible) {
            this.addEditorPreview();
         }

         this.l$src$V$1mibm4x();
      } else {
         if (this.previewVisible) {
            this.removeEditorPreview();
         }

         this.l$src$V$1mibm4x();
      }
   }

   private boolean shouldShowEditorPreview() {
      return this.activityPanels.isEmpty() && !ClientSettings.INSTANCE.inputEnabled && HudModuleConfigFrameBase.isHudEditorContext();
   }

   private void rebuildActivityPanels(List<OnlineFriendActivityState> activities) {
      this.clearActivityPanels();
      if (OnlineConnectionManager.INSTANCE.getSettings().getShowSelf().getEffectiveValue()) {
         this.activityPanels.add(Vape.INSTANCE.getOnlineManager().getLocalFriend().getActivityPanel());
      }

      for (OnlineFriendActivityState activity : activities) {
         this.activityPanels.add(new OnlineFriendActivityPanel(activity));
      }

      for (OnlineFriendActivityPanel activityPanel : this.activityPanels) {
         this.h(new SpacerComponent(0.0, 2.0));
         activityPanel.setInventoryVisible(this.inventoryVisible);
         this.h(activityPanel);
      }

      this.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa().l$src$V$1mibm4x();
   }

   @Override
   public double x() {
      return 114.0;
   }

   public void setInventoryVisible(boolean visible) {
      this.inventoryVisible = visible;

      for (GuiComponent child : this.f()) {
         if (child instanceof OnlineFriendActivityPanel) {
            ((OnlineFriendActivityPanel)child).setInventoryVisible(visible);
         }
      }
   }

   private void addEditorPreview() {
      for (int i = 0; i < 2; i++) {
         OnlineFriend previewFriend = new OnlineFriend(PREVIEW_NAMES[i]);
         previewFriend.setMinecraftUsername("Steve");
         OnlineFriendActivityState previewState = new OnlineFriendActivityState(previewFriend);
         OnlineFriendActivityPanel previewPanel = new OnlineFriendActivityPanel(previewState);
         SpacerComponent spacer = new SpacerComponent(0.0, 2.0);
         this.previewComponents.add(spacer);
         this.previewComponents.add(previewPanel);
         this.h(spacer);
         this.h(previewPanel);
      }

      this.previewVisible = true;
      this.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa().l$src$V$1mibm4x();
   }

   public void requestRefresh() {
      this.activityHash = 0;
   }

   private void removeEditorPreview() {
      for (GuiComponent previewComponent : this.previewComponents) {
         this.removeChild(previewComponent);
      }

      this.previewComponents.clear();
      this.previewVisible = false;
      this.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa().l$src$V$1mibm4x();
   }

   private void clearActivityPanels() {
      this.activityHash = 0;

      for (GuiComponent child : this.f()) {
         if (!(child instanceof SettingsFrameHeaderComponent)) {
            this.removeChild(child);
         }
      }

      this.activityPanels.clear();
      this.previewComponents.clear();
      this.previewVisible = false;
   }

   @Override
   public void u() {
      super.u();
      if (!Minecraft.thePlayer().isNull()) {
         PartyState partyState = Vape.INSTANCE.getOnlineManager().getPartyManager().getCurrentParty();
         if (partyState == null) {
            if (!this.shouldShowEditorPreview()) {
               this.clearActivityPanels();
            }
         } else {
            List<OnlineFriendActivityState> activities = this.options.getPartyActivities();
            int currentHash = activities.hashCode();
            if (this.activityHash != currentHash) {
               this.rebuildActivityPanels(activities);
               this.activityHash = currentHash;
            }

            for (OnlineFriendActivityPanel activityPanel : this.activityPanels) {
               activityPanel.getActivityState().updateLocalActivity();
               if (this.refreshTick % 20 == 19) {
                  activityPanel.resolveTrackedPlayer();
               }
            }

            this.refreshTick++;
            if (this.refreshTick >= 20) {
               this.refreshTick = 0;
            }
         }
      }
   }

   static {
      long obfuscationSeed = -8029747256032755710L;
      OBFUSCATION_SEED = (int)obfuscationSeed;
   }
}
