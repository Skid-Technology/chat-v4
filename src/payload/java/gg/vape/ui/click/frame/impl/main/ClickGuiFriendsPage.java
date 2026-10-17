package gg.vape.ui.click.frame.impl.main;

import gg.vape.Vape;
import gg.vape.friend.Friend;
import gg.vape.friend.FriendEntry;
import gg.vape.friend.LocalOnlineFriend;
import gg.vape.friend.OnlineFriend;
import gg.vape.friend.OnlineStatus;
import gg.vape.friend.PartyInvite;
import gg.vape.friend.PartyManager;
import gg.vape.friend.ui.FriendAliasEditInputComponent;
import gg.vape.friend.ui.OnlineFriendUiHelper;
import gg.vape.friend.ui.PartyMemberEntryComponent;
import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.FriendModuleInteractiveComponent;
import gg.vape.ui.click.component.GlyphIconComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.LabeledTextInputComponent;
import gg.vape.ui.click.component.MultilineTextBlockComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.ui.click.component.input.TrailingActionTextInputComponent;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.ui.click.frame.FrameScrollbarPlacement;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.PlayerInfo;
import gg.vape.wrapper.impl.WorldClient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;

public class ClickGuiFriendsPage extends ClickGuiPageBase {
   private PanelComponent searchResultsPanel;
   private final ClickGuiMainFrame mainFrame;
   private ClickGuiFriendSourceMode sourceMode = ClickGuiFriendSourceMode.MINECRAFT;
   private PanelComponent contentPanel;
   private GuiComponent searchResultsHeader;
   private LabeledTextInputComponent searchInput;
   private PanelComponent friendsPanel;
   private String searchQuery = "";

   private void populateSearchResults(String searchQuery) {
      this.searchResultsPanel.removeMarkedChildren();
      ClickGuiFriendsFriendActionComponent clickGuiFriendsFriendActionComponent = new ClickGuiFriendsFriendActionComponent(
         "\"" + searchQuery + "\"", "Search Result"
      );
      clickGuiFriendsFriendActionComponent.o(this.contentPanel.A());
      boolean alreadyFriend = Vape.INSTANCE.getFriendManager().isFriend(searchQuery);
      clickGuiFriendsFriendActionComponent.setRemoveMode(alreadyFriend);
      clickGuiFriendsFriendActionComponent.setAddPrimaryClickListener(() -> this.addFriendByName(searchQuery));
      clickGuiFriendsFriendActionComponent.setRemovePrimaryClickListener(() -> this.removeFriendByName(searchQuery));
      this.searchResultsPanel.h(new PaddedComponent(0.0, 3.0, 0.0, 0.0, clickGuiFriendsFriendActionComponent));
      ArrayList<String> arrayList = new ArrayList<>();
      WorldClient worldClient = Minecraft.theWorld();
      if (worldClient.isNotNull()) {
         for (Object object3 : worldClient.X()) {
            EntityPlayer entityPlayer = new EntityPlayer(object3);
            if (!entityPlayer.isNull()) {
               String nearbyPlayerName = entityPlayer.getName();
               if (nearbyPlayerName.toLowerCase().contains(searchQuery) && !arrayList.contains(nearbyPlayerName)) {
                  arrayList.add(nearbyPlayerName);
                  ClickGuiFriendsFriendActionComponent nearbyPlayerComponent = new ClickGuiFriendsFriendActionComponent(
                     nearbyPlayerName, "Nearby", entityPlayer
                  );
                  nearbyPlayerComponent.o(this.contentPanel.A());
                  boolean nearbyPlayerIsFriend = Vape.INSTANCE.getFriendManager().isFriend(nearbyPlayerName);
                  nearbyPlayerComponent.setRemoveMode(nearbyPlayerIsFriend);
                  nearbyPlayerComponent.setAddPrimaryClickListener(() -> this.addFriendByName(nearbyPlayerName));
                  nearbyPlayerComponent.setRemovePrimaryClickListener(() -> this.removeFriendByName(nearbyPlayerName));
                  this.searchResultsPanel.h(new PaddedComponent(0.0, 3.0, 0.0, 0.0, nearbyPlayerComponent));
               }
            }
         }
      }

      Collection<?> playerInfoMap;
      if (ForgeVersion.MC_1_8_9.d() && (playerInfoMap = Minecraft.N().getPlayerInfoMap()) != null && !playerInfoMap.isEmpty()) {
         for (Object playerInfoHandle : playerInfoMap) {
            PlayerInfo playerInfo = new PlayerInfo(playerInfoHandle);
            String onlinePlayerName = playerInfo.v().getName();
            if (onlinePlayerName.toLowerCase().contains(searchQuery) && !arrayList.contains(onlinePlayerName)) {
               arrayList.add(onlinePlayerName);
               ClickGuiFriendsFriendActionComponent clickGuiFriendsFriendActionComponent2 = new ClickGuiFriendsFriendActionComponent(
                  onlinePlayerName, "Online Player", null, playerInfo
               );
               clickGuiFriendsFriendActionComponent2.o(this.contentPanel.A());
               boolean bl3 = Vape.INSTANCE.getFriendManager().isFriend(onlinePlayerName);
               clickGuiFriendsFriendActionComponent2.setRemoveMode(bl3);
               clickGuiFriendsFriendActionComponent2.setAddPrimaryClickListener(() -> this.addFriendByName(onlinePlayerName));
               clickGuiFriendsFriendActionComponent2.setRemovePrimaryClickListener(() -> this.removeFriendByName(onlinePlayerName));
               this.searchResultsPanel.h(new PaddedComponent(0.0, 3.0, 0.0, 0.0, clickGuiFriendsFriendActionComponent2));
            }
         }
      }
   }

   private Boolean isOnlineSource() {
      return this.sourceMode == ClickGuiFriendSourceMode.ONLINE;
   }

   private void renderOnlinePlaceholder() {
      this.getMainContent().removeMarkedChildren();
      SimpleTextLabelComponent simpleTextLabelComponent = new SimpleTextLabelComponent("Under construction. Use frames GUI mode to use Vape friends for now.");
      simpleTextLabelComponent.setFontScale(0.8);
      simpleTextLabelComponent.setTextColor(J.A);
      simpleTextLabelComponent.setOffsetX(0.0F);
      this.getMainContent().h(new PaddedComponent(0.0, 12.0, 0.0, 0.0, simpleTextLabelComponent), "wrap");
      this.updateMainChildWidths();
   }

   private void renderSidebar() {
      GuiComponent guiComponent = this.getSidebarHeader().f().get(0);
      this.getSidebarHeader().removeMarkedChildren();
      GlyphIconComponent glyphIconComponent = new GlyphIconComponent("newsettings", 6.0, 6.0, 10.0, 10.0, null, null, null);
      glyphIconComponent.setCenterVertically(true);
      glyphIconComponent.setCenterHorizontally(true);
      glyphIconComponent.setShowDisabledOverlay(true);
      glyphIconComponent.addClickListener(this::openThemeSettings);
      this.getSidebarHeader().h(guiComponent);
      this.getSidebarHeader()
         .h(
            new SpacerComponent(
               this.getSidebarHeader().A() - this.getSidebarHeader().l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().C() - glyphIconComponent.A(), 0.0
            )
         );
      this.getSidebarHeader().h(new PaddedComponent(4.0, 0.0, 0.0, 0.0, glyphIconComponent));
      this.getSidebarContent().removeMarkedChildren();
      FriendModuleInteractiveComponent friendModuleInteractiveComponent = new FriendModuleInteractiveComponent(
         "Minecraft Friends", null, this::isMinecraftSource, null, "expandarrow"
      );
      friendModuleInteractiveComponent.addClickListener(this::selectMinecraftSource);
      FriendModuleInteractiveComponent friendModuleInteractiveComponent2 = new FriendModuleInteractiveComponent(
         "VAPE Friends", null, this::isOnlineSource, ClickGuiFriendsPage::getOnlineSourceBadgeCount, "expandarrow"
      );
      friendModuleInteractiveComponent2.addClickListener(this::selectOnlineSource);
      this.getSidebarContent().h(new PaddedComponent(0.0, 3.0, 0.0, 0.0, friendModuleInteractiveComponent), "wrap");
      this.getSidebarContent().h(new PaddedComponent(0.0, 3.0, 0.0, 0.0, friendModuleInteractiveComponent2), "wrap");
      PartyManager partyManager = Vape.INSTANCE.getOnlineManager().getPartyManager();
      boolean bl = partyManager.getCurrentParty() != null;
      LocalOnlineFriend localOnlineFriend = Vape.INSTANCE.getOnlineManager().getLocalFriend();
      OnlineStatus onlineStatus = localOnlineFriend != null ? localOnlineFriend.getStatus() : null;
      String string = this.firstNonBlank(localOnlineFriend != null ? localOnlineFriend.getDisplayName() : null, "You");
      String string2 = onlineStatus != null ? onlineStatus.getDisplayName() : "Offline";
      ClickGuiFriendsFriendStatusComponent clickGuiFriendsFriendStatusComponent = new ClickGuiFriendsFriendStatusComponent(string, string2, bl);
      clickGuiFriendsFriendStatusComponent.setHorizontalInset(0.0);
      clickGuiFriendsFriendStatusComponent.setStatusColor(onlineStatus != null ? onlineStatus.getColor() : null);
      clickGuiFriendsFriendStatusComponent.setStatusText(string2);
      clickGuiFriendsFriendStatusComponent.setPartyVisible(bl);
      clickGuiFriendsFriendStatusComponent.setClickListener(this::openOnlineSettings);
      this.getSidebarContent()
         .h(
            new SpacerComponent(
               0.0,
               this.getSidebarContent().L()
                  - this.getSidebarContent().l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().y()
                  - clickGuiFriendsFriendStatusComponent.L()
                  - 1.0
            ),
            "wrap"
         );
      this.updateSidebarChildWidths();
   }

   public ClickGuiFriendsPage(ClickGuiMainFrame clickGuiMainFrame, double d, double d2, double d3) {
      super(d, d2, d3, 0.0, "Friends");
      this.mainFrame = clickGuiMainFrame;
      this.renderSidebar();
      this.renderCurrentSource();
   }

   private void selectOnlineSource() {
      if (this.sourceMode != ClickGuiFriendSourceMode.ONLINE) {
         this.sourceMode = ClickGuiFriendSourceMode.ONLINE;
         this.renderCurrentSource();
      }
   }

   private static List<OnlineFriend> createOnlineStatusGroup(OnlineStatus onlineStatus) {
      return new ArrayList<>();
   }

   private void resizeChildRows(PanelComponent panelComponent, double d) {
      for (GuiComponent guiComponent : panelComponent.f()) {
         if (guiComponent instanceof PaddedComponent) {
            ClickGuiFriendsFriendRequestComponent clickGuiFriendsFriendRequestComponent = ((PaddedComponent)guiComponent)
               .t(ClickGuiFriendsFriendRequestComponent.class);
            if (clickGuiFriendsFriendRequestComponent != null) {
               clickGuiFriendsFriendRequestComponent.o(d);
            } else {
               ClickGuiFriendsFriendActionComponent clickGuiFriendsFriendActionComponent = ((PaddedComponent)guiComponent)
                  .t(ClickGuiFriendsFriendActionComponent.class);
               if (clickGuiFriendsFriendActionComponent != null) {
                  clickGuiFriendsFriendActionComponent.o(d);
               } else {
                  SimpleTextLabelComponent simpleTextLabelComponent = ((PaddedComponent)guiComponent).t(SimpleTextLabelComponent.class);
                  if (simpleTextLabelComponent != null) {
                     simpleTextLabelComponent.o(d);
                  }
               }
            }
         } else if (guiComponent instanceof ClickGuiFriendsFriendRequestComponent) {
            guiComponent.o(d);
         } else if (guiComponent instanceof ClickGuiFriendsFriendActionComponent) {
            guiComponent.o(d);
         }
      }
   }

   private void openFriendSettings(FriendEntry friendEntry) {
      this.mainFrame
         .showOverlay(
            ClickGuiOverlaySpec.builder()
               .title(friendEntry.getName())
               .sidecarIcon("newsettings")
               .placement(ClickGuiOverlayPlacement.DOCKED)
               .initializeContent(panel -> this.populateFriendSettings(friendEntry, panel))
               .build()
         );
   }

   private static int getOnlineStatusOrder(OnlineFriend onlineFriend) {
      OnlineStatus onlineStatus = onlineFriend.getStatus();
      return onlineStatus != null ? onlineStatus.ordinal() : Integer.MAX_VALUE;
   }

   private void removeFriend(FriendEntry friendEntry) {
      Vape.INSTANCE.getFriendManager().removeFriend(friendEntry);
      Vape.INSTANCE.getNotificationManager().showInfo("§cRemoved§r " + friendEntry.getName() + " from friends", "", 2000L);
      this.refreshSearchResults();
   }

   private static void restoreAlias(TrailingActionTextInputComponent trailingActionTextInputComponent, String[] stringArray) {
      trailingActionTextInputComponent.setText(stringArray[0]);
   }

   private void updateMainChildWidths() {
      double d = this.getMainContent().A();

      for (GuiComponent guiComponent : this.getMainContent().f()) {
         if (guiComponent instanceof PaddedComponent) {
            ClickGuiFriendsFriendRequestComponent clickGuiFriendsFriendRequestComponent = ((PaddedComponent)guiComponent)
               .t(ClickGuiFriendsFriendRequestComponent.class);
            if (clickGuiFriendsFriendRequestComponent != null) {
               clickGuiFriendsFriendRequestComponent.o(d);
            } else {
               ClickGuiFriendsFriendActionComponent clickGuiFriendsFriendActionComponent = ((PaddedComponent)guiComponent)
                  .t(ClickGuiFriendsFriendActionComponent.class);
               if (clickGuiFriendsFriendActionComponent != null) {
                  clickGuiFriendsFriendActionComponent.o(d);
               } else {
                  PartyMemberEntryComponent partyMemberEntryComponent = ((PaddedComponent)guiComponent).t(PartyMemberEntryComponent.class);
                  if (partyMemberEntryComponent != null) {
                     partyMemberEntryComponent.o(d);
                  } else {
                     ClickGuiFriendsFriendStatusComponent clickGuiFriendsFriendStatusComponent = ((PaddedComponent)guiComponent)
                        .t(ClickGuiFriendsFriendStatusComponent.class);
                     if (clickGuiFriendsFriendStatusComponent != null) {
                        clickGuiFriendsFriendStatusComponent.o(Math.min(d, ClickGuiFriendsFriendStatusComponent.getPreferredWidth()));
                     } else {
                        ClickGuiFriendsFriendListComponent clickGuiFriendsFriendListComponent = ((PaddedComponent)guiComponent)
                           .t(ClickGuiFriendsFriendListComponent.class);
                        if (clickGuiFriendsFriendListComponent != null) {
                           clickGuiFriendsFriendListComponent.o(d);
                        } else {
                           TrailingActionTextInputComponent trailingActionTextInputComponent = ((PaddedComponent)guiComponent)
                              .t(TrailingActionTextInputComponent.class);
                           if (trailingActionTextInputComponent != null) {
                              trailingActionTextInputComponent.o(d);
                           } else {
                              LabeledTextInputComponent labeledTextInputComponent = ((PaddedComponent)guiComponent).t(LabeledTextInputComponent.class);
                              if (labeledTextInputComponent != null) {
                                 labeledTextInputComponent.o(d - 0.0);
                              }
                           }
                        }
                     }
                  }
               }
            }
         } else if (guiComponent instanceof ClickGuiFriendsFriendRequestComponent) {
            guiComponent.o(d);
         } else if (guiComponent instanceof ClickGuiFriendsFriendActionComponent) {
            guiComponent.o(d);
         } else if (guiComponent instanceof PartyMemberEntryComponent) {
            guiComponent.o(d);
         } else if (guiComponent instanceof ClickGuiFriendsFriendStatusComponent) {
            guiComponent.o(Math.min(d, ClickGuiFriendsFriendStatusComponent.getPreferredWidth()));
         } else if (guiComponent instanceof ClickGuiFriendsFriendListComponent) {
            guiComponent.o(d);
         } else if (guiComponent instanceof TrailingActionTextInputComponent) {
            guiComponent.o(d);
         } else if (guiComponent instanceof LabeledTextInputComponent) {
            guiComponent.o(d - 0.0);
         } else if (guiComponent instanceof PanelComponent) {
            this.resizeChildRows((PanelComponent)guiComponent, d);
         }
      }
   }

   private static Integer getOnlineSourceBadgeCount() {
      return 0;
   }

   private void removeFriendByName(String string) {
      FriendEntry friendEntry = Vape.INSTANCE.getFriendManager().findTargetedFriend(string);
      if (friendEntry != null) {
         Vape.INSTANCE.getFriendManager().removeFriend(friendEntry);
         Vape.INSTANCE.getNotificationManager().showInfo("§cRemoved§r " + string + " from friends", "", 2000L);
         this.refreshSearchResults();
      }
   }

   private void populateFriends(String string) {
      boolean bl = !string.isEmpty();
      if (bl) {
         for (FriendEntry friendEntry : Vape.INSTANCE.getFriendManager().getFriends()) {
            String friendName = friendEntry.getName();
            String string2 = friendEntry.getDisplayName();
            boolean bl4 = friendName != null && friendName.toLowerCase().contains(string);
            boolean bl3 = string2 != null && string2.toLowerCase().contains(string);
            if (bl4 || bl3) {
               ClickGuiFriendsFriendRequestComponent friendComponent = new ClickGuiFriendsFriendRequestComponent(friendEntry);
               friendComponent.o(this.contentPanel.A());
               friendComponent.setRemovePrimaryClickListener(() -> this.removeFriend(friendEntry));
               friendComponent.setSettingsPrimaryClickListener(() -> this.openFriendSettings(friendEntry));
               this.friendsPanel.h(new PaddedComponent(0.0, 3.0, 0.0, 0.0, friendComponent));
            }
         }

         if (this.friendsPanel.f().isEmpty()) {
         }
      } else {
         for (FriendEntry friendEntryx : Vape.INSTANCE.getFriendManager().getFriends()) {
            ClickGuiFriendsFriendRequestComponent clickGuiFriendsFriendRequestComponent = new ClickGuiFriendsFriendRequestComponent(friendEntryx);
            clickGuiFriendsFriendRequestComponent.o(this.contentPanel.A());
            clickGuiFriendsFriendRequestComponent.setRemovePrimaryClickListener(() -> this.removeFriend(friendEntryx));
            clickGuiFriendsFriendRequestComponent.setSettingsPrimaryClickListener(() -> this.openFriendSettings(friendEntryx));
            this.friendsPanel.h(new PaddedComponent(0.0, 3.0, 0.0, 0.0, clickGuiFriendsFriendRequestComponent));
         }

         if (this.friendsPanel.f().isEmpty()) {
            MultilineTextBlockComponent multilineTextBlockComponent = new MultilineTextBlockComponent(
               "INFO", "Enter a username in the search bar to add a friend."
            );
            multilineTextBlockComponent.setWidth(this.contentPanel.A());
            this.friendsPanel.h(new PaddedComponent(3.0, 3.0, 0.0, 0.0, multilineTextBlockComponent));
         }
      }
   }

   private void addFriendByName(String string) {
      Vape.INSTANCE.getFriendManager().addFriend(new Friend(string, string));
      Vape.INSTANCE.getNotificationManager().showInfo("§aAdded§r " + string + " to friends", "", 2000L);
      this.refreshSearchResults();
   }

   private void renderCurrentSource() {
      switch (ClickGuiFriendSourceModeSwitchMap.Z[this.sourceMode.ordinal()]) {
         case 1:
            this.renderOnlinePlaceholder();
            break;
         default:
            this.renderMinecraftFriendsContent();
      }
   }

   private void selectMinecraftSource() {
      if (this.sourceMode != ClickGuiFriendSourceMode.MINECRAFT) {
         this.sourceMode = ClickGuiFriendSourceMode.MINECRAFT;
         this.renderCurrentSource();
      }
   }

   private void createFriendsSection(String string) {
      this.contentPanel.h(new PaddedComponent(0.0, 3.0, 0.0, 0.0, this.createSectionLabel("Your Friends")), "wrap");
      this.friendsPanel = new PanelComponent(this.contentPanel.A(), 0.0);
      this.friendsPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
      this.friendsPanel.setShowDisabledOverlay(false);
      this.friendsPanel.F(FrameScrollbarPlacement.OUTSIDE);
      this.populateFriends(string);
      this.contentPanel.h(this.friendsPanel);
   }

   private void updatePanelHeights() {
      if (this.contentPanel != null && this.friendsPanel != null) {
         double d = this.contentPanel.L();
         boolean bl = this.searchResultsPanel != null;
         if (bl) {
            double d2 = Math.max(0.0, d - 22.0);
            double d3 = Math.min(this.searchResultsPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().y() + 1.0, d2 * 0.5);
            this.searchResultsPanel.setExplicitHeight(d3);
            this.searchResultsPanel.Y(d3);
            this.searchResultsPanel.t(d3);
            double d4 = d2 - d3 - 6.0;
            this.friendsPanel.setExplicitHeight(d4);
            this.friendsPanel.Y(d4);
            this.friendsPanel.t(d4);
         } else {
            double d5 = 11.0;
            double d6 = Math.max(0.0, d - 11.0);
            this.friendsPanel.setExplicitHeight(d6);
            this.friendsPanel.Y(d6);
            this.friendsPanel.t(d6);
         }
      }
   }

   private void renderOnlineFriendsContent() {
      int onlineFriend = 0;
      this.getMainContent().removeMarkedChildren();
      PartyManager partyManager = Vape.INSTANCE.getOnlineManager().getPartyManager();
      Collection<PartyInvite> collection = partyManager.getInvites();
      boolean bl = partyManager.getCurrentParty() != null;
      this.getMainContent().h(new PaddedComponent(0.0, 3.0, 0.0, 0.0, this.createSectionLabel("Party")), "wrap");
      boolean bl2 = false;
      if (bl) {
         PartyMemberEntryComponent partyMemberEntryComponent = new PartyMemberEntryComponent(() -> partyManager.getCurrentParty());
         partyMemberEntryComponent.o(this.getMainContent().A());
         this.getMainContent().h(new PaddedComponent(0.0, 3.0, 0.0, 0.0, partyMemberEntryComponent));
         bl2 = true;
      }

      for (PartyInvite partyInvite : collection) {
         Object object = new PartyMemberEntryComponent(partyInvite);
         ((GuiComponent)object).o(this.getMainContent().A());
         this.getMainContent().h(new PaddedComponent(0.0, 3.0, 0.0, 0.0, (GuiComponent)object));
         bl2 = true;
      }

      if (!bl2) {
         this.getMainContent().h(new PaddedComponent(0.0, 6.0, 0.0, 0.0, this.createInfoLabel("No active parties yet.")), "wrap");
      } else {
         this.getMainContent().h(new SpacerComponent(0.0, 6.0));
      }

      ArrayList<OnlineFriend> arrayList = new ArrayList<>(Vape.INSTANCE.getOnlineFriendManager().getFriends());
      arrayList.sort(
         Comparator.comparingInt(ClickGuiFriendsPage::getOnlineStatusOrder)
            .thenComparing(ClickGuiFriendsPage::getOnlineFriendName, String.CASE_INSENSITIVE_ORDER)
      );
      int n = arrayList.size();
      Object object = n > 0 ? "VAPE Friends (" + n + ")" : "VAPE Friends";
      this.getMainContent().h(new PaddedComponent(0.0, 3.0, 0.0, 0.0, this.createSectionLabel((String)object)), "wrap");
      if (arrayList.isEmpty()) {
         this.getMainContent().h(new PaddedComponent(0.0, 0.0, 0.0, 0.0, this.createInfoLabel("No VAPE friends online yet.")), "wrap");
         this.updateMainChildWidths();
      } else {
         EnumMap<OnlineStatus, List<OnlineFriend>> enumMap = new EnumMap<>(OnlineStatus.class);

         for (OnlineStatus iterator : OnlineStatus.values()) {
            enumMap.put(iterator, new ArrayList<>());
         }

         ArrayList<OnlineFriend> uncategorizedFriends = new ArrayList<>();

         for (OnlineFriend onlineFriend2 : arrayList) {
            OnlineStatus iterator = onlineFriend2.getStatus();
            if (iterator == null) {
               uncategorizedFriends.add(onlineFriend2);
            } else {
               enumMap.computeIfAbsent(iterator, ClickGuiFriendsPage::createOnlineStatusGroup).add(onlineFriend2);
            }
         }

         boolean bl32 = false;

         for (OnlineStatus onlineStatus : OnlineStatus.values()) {
            List<OnlineFriend> list = enumMap.getOrDefault(onlineStatus, Collections.emptyList());
            if (!list.isEmpty()) {
               if (bl32) {
                  this.getMainContent().h(new SpacerComponent(0.0, 4.0));
               }

               SimpleTextLabelComponent simpleTextLabelComponent = this.createWrappedInfoLabel(onlineStatus.getDisplayName());
               this.getMainContent().h(new PaddedComponent(0.0, 3.0, 0.0, 0.0, simpleTextLabelComponent), "wrap");

               for (OnlineFriend onlineFriend2x : list) {
                  ClickGuiFriendsFriendListComponent clickGuiFriendsFriendListComponent = new ClickGuiFriendsFriendListComponent(onlineFriend2x);
                  clickGuiFriendsFriendListComponent.setChatActionVisible(onlineStatus != OnlineStatus.OFFLINE);
                  clickGuiFriendsFriendListComponent.o(this.getMainContent().A());
                  clickGuiFriendsFriendListComponent.setSettingsClickListener(() -> this.openOnlineFriend(onlineFriend2x));
                  this.getMainContent().h(new PaddedComponent(0.0, 3.0, 0.0, 0.0, clickGuiFriendsFriendListComponent), "wrap");
               }

               bl32 = true;
            }
         }

         if (!uncategorizedFriends.isEmpty()) {
            this.getMainContent().h(new SpacerComponent(0.0, 4.0));
            SimpleTextLabelComponent simpleTextLabelComponent = this.createWrappedInfoLabel("Other");
            this.getMainContent().h(new PaddedComponent(0.0, 3.0, 0.0, 0.0, simpleTextLabelComponent), "wrap");

            for (OnlineFriend onlineFriend3 : uncategorizedFriends) {
               ClickGuiFriendsFriendListComponent object2 = new ClickGuiFriendsFriendListComponent(onlineFriend3);
               object2.setChatActionVisible(false);
               object2.o(this.getMainContent().A());
               object2.setSettingsClickListener(() -> this.openOnlineFriend(onlineFriend3));
               this.getMainContent().h(new PaddedComponent(0.0, 3.0, 0.0, 0.0, object2), "wrap");
            }
         }

         this.updateMainChildWidths();
      }
   }

   private void openOnlineFriend(OnlineFriend onlineFriend) {
      this.mainFrame.showOverlay(ClickGuiFriendsFriendCardFactory.createOverlay(onlineFriend));
   }

   private void populateFriendSettings(FriendEntry friendEntry, PanelComponent panelComponent) {
      SimpleTextLabelComponent simpleTextLabelComponent = new SimpleTextLabelComponent("Alias");
      simpleTextLabelComponent.setFontScale(0.7);
      simpleTextLabelComponent.setTextColor(J.A);
      simpleTextLabelComponent.setBold(true);
      simpleTextLabelComponent.setOffsetX(0.0F);
      simpleTextLabelComponent.setExtraHeight(2);
      panelComponent.h(new PaddedComponent(8.0, 0.0, 8.0, 8.0, simpleTextLabelComponent));
      String[] stringArray = new String[]{
         friendEntry.getAlias() != null && !friendEntry.getAlias().equals(friendEntry.getName()) ? friendEntry.getAlias() : ""
      };
      TextButton textButton = new TextButton("Y", 0.7, J.B, J.O, null, 2.0F, 1.0F, 12.0, 13.0);
      textButton.setUppercase(true);
      textButton.setUseAlternateFont(true);
      textButton.setDeriveTextColorFromBackground(false);
      textButton.setNormalTextColor(J.A);
      textButton.setVisible(false);
      TextButton textButton2 = new TextButton("N", 0.7, J.d, J.c, null, 2.0F, 1.0F, 12.0, 13.0);
      textButton2.setUppercase(true);
      textButton2.setUseAlternateFont(true);
      textButton2.setDeriveTextColorFromBackground(false);
      textButton2.setNormalTextColor(J.A);
      textButton2.setVisible(false);
      ArrayList<TextButton> arrayList = new ArrayList<>();
      arrayList.add(textButton2);
      arrayList.add(textButton);
      FriendAliasEditInputComponent friendAliasEditInputComponent = new FriendAliasEditInputComponent(
         this, "Set alias...", arrayList, stringArray, textButton, textButton2, friendEntry
      );
      friendAliasEditInputComponent.setUseExplicitWidth(true);
      friendAliasEditInputComponent.setUseExplicitHeight(true);
      friendAliasEditInputComponent.o(20.0);
      friendAliasEditInputComponent.Y(20.0);
      friendAliasEditInputComponent.setBorderThickness(0.75F);
      friendAliasEditInputComponent.setCornerRadius(4.0F);
      friendAliasEditInputComponent.setBorderAnimation(ColorAnimation.Y(J.s));
      friendAliasEditInputComponent.setBackgroundColorOrNull(null);
      friendAliasEditInputComponent.P(stringArray[0]);
      textButton.addClickListener(() -> this.saveFriendAlias(friendEntry, friendAliasEditInputComponent, stringArray, textButton, textButton2));
      textButton2.addClickListener(() -> restoreAlias(friendAliasEditInputComponent, stringArray));
      panelComponent.h(new PaddedComponent(0.0, 2.0, 0.0, 0.0, friendAliasEditInputComponent));
      ClickGuiFriendsModeToggleComponent clickGuiFriendsModeToggleComponent = new ClickGuiFriendsModeToggleComponent("Active", 0.8, friendEntry);
      clickGuiFriendsModeToggleComponent.o(panelComponent.A() - 16.0);
      clickGuiFriendsModeToggleComponent.setHorizontalInset(0.0);
      clickGuiFriendsModeToggleComponent.setShowDisabledOverlay(false);
      clickGuiFriendsModeToggleComponent.synchronizeAnimationsImmediately();
      panelComponent.h(new PaddedComponent(0.0, 0.0, 5.0, 5.0, clickGuiFriendsModeToggleComponent));
   }

   private void saveFriendAlias(
      FriendEntry friendEntry,
      TrailingActionTextInputComponent trailingActionTextInputComponent,
      String[] stringArray,
      TextButton textButton,
      TextButton textButton2
   ) {
      this.updateFriendAlias(friendEntry, trailingActionTextInputComponent.getText());
      stringArray[0] = trailingActionTextInputComponent.getText();
      textButton.setVisible(false);
      textButton2.setVisible(false);
   }

   private void createSearchResultsSection(String string) {
      boolean bl = !string.isEmpty();
      if (bl) {
         this.searchResultsHeader = new PaddedComponent(0.0, 3.0, 0.0, 0.0, this.createSectionLabel("Results"));
         this.contentPanel.h(this.searchResultsHeader, "wrap");
         this.searchResultsPanel = new PanelComponent(this.contentPanel.A(), 30.0);
         this.searchResultsPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
         this.searchResultsPanel.setShowDisabledOverlay(false);
         this.searchResultsPanel.F(FrameScrollbarPlacement.OUTSIDE);
         this.contentPanel.h(this.searchResultsPanel);
         this.contentPanel.h(new SpacerComponent(0.0, 6.0));
      }
   }

   public static void refreshSearchResults(ClickGuiFriendsPage page) {
      page.refreshSearchResults();
   }

   private SimpleTextLabelComponent createWrappedInfoLabel(String string) {
      SimpleTextLabelComponent simpleTextLabelComponent = new SimpleTextLabelComponent(string);
      simpleTextLabelComponent.setFontScale(0.625);
      simpleTextLabelComponent.setTextColor(J.C);
      simpleTextLabelComponent.setBold(true);
      simpleTextLabelComponent.setOffsetX(0.0F);
      simpleTextLabelComponent.setExtraHeight(2);
      return simpleTextLabelComponent;
   }

   private void renderMinecraftFriendsContent() {
      this.getMainContent().removeMarkedChildren();
      this.searchInput = new ClickGuiFriendsNameInputListener(this, "Search friends...");
      this.searchInput.o(this.getMainContent().A());
      this.searchInput.setLeftInset(0.0F);
      this.searchInput.setHorizontalInset(0.0);
      this.searchInput.setRightInset(0.0F);
      this.searchInput.setVerticalInset(0.0F);
      this.searchInput.setUseExplicitHeight(true);
      this.searchInput.setSearchIconTrailing(false);
      this.searchInput.Y(16.0);
      this.searchInput.setBorderThickness(0.75F);
      this.searchInput.setCornerRadius(4.0F);
      this.searchInput.setBorderAnimation(ColorAnimation.Y(J.s));
      this.searchInput.setBackgroundColorOrNull(null);
      this.getMainContent().h(this.searchInput);
      if (this.searchQuery != null && !this.searchQuery.isEmpty()) {
         this.searchInput.setText(this.searchQuery);
      }

      this.getMainContent().h(new SpacerComponent(0.0, 6.0));
      String string = this.searchInput != null ? this.searchInput.getText() : "";
      String string2 = string == null ? "" : string.trim().toLowerCase();
      double d = this.getMainContent().L() - this.getMainContent().l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().y() - 1.0;
      if (d < 0.0) {
         d = 0.0;
      }

      this.contentPanel = new PanelComponent(this.getMainContent().A(), d);
      this.contentPanel.N(false);
      this.contentPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
      this.contentPanel.setShowDisabledOverlay(false);
      this.contentPanel.t(this.contentPanel.L());
      this.contentPanel.F(FrameScrollbarPlacement.OUTSIDE);
      this.createSearchResultsSection(string2);
      this.createFriendsSection(string2);
      this.updatePanelHeights();
      this.getMainContent().h(this.contentPanel);
      this.updateMainChildWidths();
   }

   private void openOnlineSettings() {
      this.mainFrame.showOverlay(ClickGuiFriendsThemeConfigFactory.createOverlay());
   }

   private SimpleTextLabelComponent createInfoLabel(String string) {
      SimpleTextLabelComponent simpleTextLabelComponent = new SimpleTextLabelComponent(string);
      simpleTextLabelComponent.setFontScale(0.625);
      simpleTextLabelComponent.setTextColor(J.C);
      simpleTextLabelComponent.setOffsetX(0.0F);
      simpleTextLabelComponent.setExtraHeight(2);
      return simpleTextLabelComponent;
   }

   private String firstNonBlank(String string, String string2) {
      return string != null && !string.trim().isEmpty() ? string : string2;
   }

   private void refreshSearchResults() {
      if (this.contentPanel != null) {
         String string = this.searchInput != null ? this.searchInput.getText() : "";
         String string2 = string == null ? "" : string.trim().toLowerCase();
         boolean bl2 = !string2.isEmpty();
         if (bl2) {
            boolean bl4 = this.searchResultsPanel == null;
            boolean bl5 = false;
            if (bl4) {
               this.contentPanel.removeMarkedChildren();
               this.createSearchResultsSection(string2);
               this.populateSearchResults(string2);
               this.createFriendsSection(string2);
               this.updatePanelHeights();
               this.contentPanel.H(true);
               this.updateMainChildWidths();
            } else {
               if (this.searchResultsPanel != null) {
                  this.populateSearchResults(string2);
                  this.searchResultsPanel.H(true);
               }

               if (this.friendsPanel != null) {
                  this.friendsPanel.removeMarkedChildren();
                  this.populateFriends(string2);
                  this.friendsPanel.H(true);
               }

               this.contentPanel.H(true);
               this.updatePanelHeights();
               this.updateMainChildWidths();
            }
         } else {
            boolean bl6 = false;
            boolean bl = this.searchResultsPanel != null;
            if (bl) {
               this.contentPanel.removeMarkedChildren();
               this.searchResultsPanel = null;
               this.searchResultsHeader = null;
               this.createFriendsSection(string2);
               this.updatePanelHeights();
               this.contentPanel.H(true);
               this.updateMainChildWidths();
            } else {
               if (this.searchResultsPanel != null) {
               }

               if (this.friendsPanel != null) {
                  this.friendsPanel.removeMarkedChildren();
                  this.populateFriends(string2);
                  this.friendsPanel.H(true);
               }

               this.contentPanel.H(true);
               this.updatePanelHeights();
               this.updateMainChildWidths();
            }
         }
      }
   }

   private void updateFriendAlias(FriendEntry friendEntry, String string) {
      if (friendEntry instanceof Friend) {
         Friend friend = (Friend)friendEntry;
         String string2 = string == null ? "" : string.trim();
         if (string2.isEmpty()) {
            string2 = friend.getName();
         }

         friend.setAlias(string2);
      }

      Vape.INSTANCE.getFriendManager().refreshPlayerNames();
      OnlineFriendUiHelper.refreshMinecraftFriends();
   }

   public static String setSearchQuery(ClickGuiFriendsPage page, String string) {
      page.searchQuery = string;
      return page.searchQuery;
   }

   private Boolean isMinecraftSource() {
      return this.sourceMode == ClickGuiFriendSourceMode.MINECRAFT;
   }

   private void updateSidebarChildWidths() {
      double d = this.getSidebarContent().A();

      for (GuiComponent guiComponent : this.getSidebarContent().f()) {
         if (guiComponent instanceof PaddedComponent) {
            FriendModuleInteractiveComponent friendModuleInteractiveComponent = ((PaddedComponent)guiComponent).t(FriendModuleInteractiveComponent.class);
            if (friendModuleInteractiveComponent != null) {
               friendModuleInteractiveComponent.o(d);
            }
         } else if (guiComponent instanceof FriendModuleInteractiveComponent) {
            guiComponent.o(d);
         }
      }
   }

   private static String getOnlineFriendName(OnlineFriend onlineFriend) {
      String string = onlineFriend.getDisplayName();
      return string == null ? "" : string;
   }

   public static void updateFriendAlias(ClickGuiFriendsPage page, FriendEntry friendEntry, String string) {
      page.updateFriendAlias(friendEntry, string);
   }

   private void openThemeSettings() {
      this.mainFrame.showOverlay(ClickGuiThemeOverlayFactory.m(this.mainFrame));
   }

   private SimpleTextLabelComponent createSectionLabel(String string) {
      SimpleTextLabelComponent simpleTextLabelComponent = new SimpleTextLabelComponent(string);
      simpleTextLabelComponent.setFontScale(0.75);
      simpleTextLabelComponent.setTextColor(J.A);
      simpleTextLabelComponent.setBold(true);
      simpleTextLabelComponent.setOffsetX(0.0F);
      simpleTextLabelComponent.setExtraHeight(2);
      return simpleTextLabelComponent;
   }
}
