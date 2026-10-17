package gg.vape.ui.click.component.value;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.frame.AnchoredPopupFrame;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.FrameStackManager;
import gg.vape.ui.click.frame.impl.main.ClickGuiMainFrame;
import gg.vape.ui.click.frame.impl.main.ClickGuiModulesSidecarPanel;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlayPlacement;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlaySpec;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlayTransitionMode;
import gg.vape.ui.click.frame.impl.main.ClickGuiSidecarPanelBase;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.value.BooleanValue;
import gg.vape.value.ListValue;
import gg.vape.value.ToggleableListEntry;
import java.awt.Color;
import java.util.List;

public class ListValueComponent extends AbstractListValueComponent {
   private boolean blockedList;
   private FrameStackManager frameStackManager;
   private ListValue listValue;
   private ListValueDropdownLayer dropdownLayer;
   protected AnchoredPopupFrame popupFrame;
   private ValueComponentMode mode = ValueComponentMode.MAIN;
   private static final double ANIMATION_DURATION = 0.08;
   private final ColorAnimation iconColorAnimation;
   private boolean useAnchoredPopup;
   private String displayTitle;
   private final ColorAnimation backgroundAnimation = new ColorAnimation(0.08, J.S, J.a);

   private void renderDropdown() {
      this.onDisable();
      SmoothFontRenderer titleFont = this.getFontRenderer(0.9);
      SmoothFontRenderer summaryFont = this.getFontRenderer(0.75);
      Color panelColor = J.i;
      Color primaryColor = this.isHovered() ? J.A : (this.isExpanded() ? J.A : J.Z);
      Color secondaryColor = J.h;
      float iconY = (float)(this.n() + this.L() / 2.0) - 3.0F;
      double titleHeight = titleFont.d(this.listValue.getName());
      double titleY = this.n() + this.L() / 2.0 - titleHeight / 2.0 - 2.5;
      double summaryY = titleY + 7.5;
      GuiRenderPrimitives.d(
         this.G$src$D$1b2f02a() + 5.0,
         this.n() + 2.5,
         this.A() - 10.0,
         this.L() - 5.0,
         this.isExpanded() ? J.z() : this.getHoverAnimation().getInterpolatedColor()
      );
      GuiRenderPrimitives.d(this.G$src$D$1b2f02a() + 5.0 + 0.5, this.n() + 2.5 + 0.5, this.A() - 10.0 - 1.0, this.L() - 5.0 - 1.0, panelColor);
      titleFont.d(this.displayTitle, this.G$src$D$1b2f02a() + 15.0 + 8.0, titleY, primaryColor);
      titleFont.d("" + this.getEntryCount(), this.G$src$D$1b2f02a() + this.A() - 10.0 - titleFont.N("10"), titleY, primaryColor);
      summaryFont.d(this.buildEntrySummary(summaryFont, this.A() - 35.0), this.G$src$D$1b2f02a() + 15.0 + 8.0, summaryY, secondaryColor);
      if (this.blockedList) {
         ImageRenderer.drawImage(primaryColor, (float)this.G$src$D$1b2f02a() + 10.0F + 0.5F, iconY, "newblockedlist", 6.0F, 6.0F, false);
         ImageRenderer.drawImage(J.d, (float)this.G$src$D$1b2f02a() + 10.0F - 0.5F, iconY, "newblocked", 6.0F, 6.0F, false);
      } else {
         ImageRenderer.drawImage(primaryColor, (float)this.G$src$D$1b2f02a() + 10.0F + 0.5F, iconY, "newallowedlist", 6.0F, 6.0F, false);
         ImageRenderer.drawImage(J.B, (float)this.G$src$D$1b2f02a() + 10.0F + 0.5F, iconY, "newallowed", 6.0F, 6.0F, false);
      }
   }

   private void openEditor() {
      Frame frame;
      if (this.getParentFrameComponent() != null
         && (frame = this.getParentFrameComponent().L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa()) instanceof ClickGuiMainFrame) {
         ClickGuiMainFrame clickGuiMainFrame = (ClickGuiMainFrame)frame;
         ClickGuiModulesSidecarPanel clickGuiModulesSidecarPanel = new ClickGuiModulesSidecarPanel(null);
         clickGuiModulesSidecarPanel.setFavoriteVisible(false);
         clickGuiModulesSidecarPanel.setToggleVisible(false);
         ClickGuiOverlaySpec clickGuiOverlaySpec = ClickGuiOverlaySpec.builder()
            .title(this.displayTitle)
            .sidecarIcon(this.blockedList ? "newblocked" : "newallowed")
            .sidecar(clickGuiModulesSidecarPanel)
            .placement(ClickGuiOverlayPlacement.DOCKED_SHIFT)
            .transitionMode(ClickGuiOverlayTransitionMode.PUSH)
            .initializeSidecar(ListValueComponent::hideSidecarDivider)
            .initializeContent(this::populateOverlayContent)
            .build();
         clickGuiMainFrame.showOverlay(clickGuiOverlaySpec);
      } else {
         if (this.useAnchoredPopup) {
            if (this.dropdownLayer == null) {
               this.dropdownLayer = new ListValueDropdownLayer(this);
            }

            this.dropdownLayer.refreshContents();
            this.popupFrame = ClientSettings.createPopup(this, this.dropdownLayer.getOptionsPanel(), AnchoredPopupFrame.class);
            this.popupFrame.t(true);
         } else {
            this.setExpanded(!this.isExpanded());
            if (this.isExpanded()) {
               if (this.dropdownLayer == null) {
                  this.dropdownLayer = new ListValueDropdownLayer(this);
               }

               this.frameStackManager = ClientSettings.INSTANCE.getActiveStack();
               this.frameStackManager.q(this.dropdownLayer);
               this.dropdownLayer.refreshContents();
            }
         }
      }
   }

   private void populateOverlayContent(PanelComponent panelComponent) {
      panelComponent.removeMarkedChildren();
      ListValueOptionsPanel listValueOptionsPanel = new ListValueOptionsPanel(
         this.listValue, this.blockedList, panelComponent.A(), panelComponent.L() - 0.1, true
      );
      listValueOptionsPanel.refreshEntries();
      panelComponent.h(listValueOptionsPanel);
   }

   public ListValueComponent(ListValue listValue) {
      this.iconColorAnimation = new ColorAnimation(0.08, J.V, J.f);
      this.listValue = listValue;
      this.bindValue(listValue);
      this.addClickListener(this::openEditorFromClick);
      String normalizedName = listValue.getName().toLowerCase();
      this.blockedList = normalizedName.contains("blacklist") || normalizedName.contains("blocked");
      this.displayTitle = normalizedName.contains("whitelist")
         ? "Whitelist"
         : (
            normalizedName.contains("blacklist")
               ? "Blacklist"
               : (
                  normalizedName.contains("allowed")
                     ? "Allowed Items"
                     : (
                        normalizedName.contains("blocked")
                           ? "Blocked Items"
                           : normalizedName.substring(0, 1).toUpperCase() + normalizedName.substring(1).replaceAll("-", " ")
                     )
               )
         );
   }

   public ValueComponentMode getMode() {
      return this.mode;
   }

   @Override
   public void H() {
      if (this.mode == ValueComponentMode.STANDALONE) {
         this.renderStandalone();
      } else {
         this.renderDropdown();
      }
   }

   public ListValue getListValue() {
      return this.listValue;
   }

   private int getEntryCount() {
      return ((List)this.listValue.getValue()).size();
   }

   private String buildEntrySummary(SmoothFontRenderer fontRenderer, double maximumWidth) {
      StringBuilder summary = new StringBuilder();

      for (Object entry : (List)this.listValue.getValue()) {
         if (!(entry instanceof ToggleableListEntry) || ((ToggleableListEntry)entry).isEnabled()) {
            if (summary.length() < 1) {
               summary.append(entry.toString());
            } else {
               String nextEntry = ", " + entry.toString();
               if (!(fontRenderer.N(summary.toString() + nextEntry) < maximumWidth)) {
                  summary.append("...");
                  break;
               }

               summary.append(nextEntry);
            }
         }
      }

      if (summary.length() < 1) {
         summary.append("None");
      }

      return summary.toString();
   }

   private void openEditorFromClick() {
      this.openEditor();
   }

   @Override
   public void setExpanded(boolean expanded) {
      super.setExpanded(expanded);
      if (!expanded && this.dropdownLayer != null) {
         this.frameStackManager.m(this.dropdownLayer);
      }
   }

   private static void hideSidecarDivider(ClickGuiSidecarPanelBase clickGuiSidecarPanelBase) {
      clickGuiSidecarPanelBase.setDividerVisible(false);
   }

   private void renderStandalone() {
      this.onDisable();
      SmoothFontRenderer titleFont = this.getFontRenderer(0.85);
      SmoothFontRenderer summaryFont = this.getFontRenderer(0.7);
      SmoothFontRenderer countFont = this.getFontRenderer(0.68);
      boolean specialParentStyle = this.hasSingleBooleanParentEntry();
      if (specialParentStyle) {
         this.backgroundAnimation.u(this.isHovered());
         this.iconColorAnimation.u(this.isHovered());
         double left = this.G$src$D$1b2f02a() + 5.0;
         double top = this.n() + 0.5;
         double width = this.A() - 10.0;
         double height = this.L() - 1.0;
         Color backgroundColor = this.backgroundAnimation.getInterpolatedColor();
         Color primaryColor = J.A;
         Color secondaryColor = J.C;
         Color badgeColor = J.F;
         Color iconColor = this.iconColorAnimation.getInterpolatedColor();
         double titleY = top + 3.0;
         double summaryY = titleY + titleFont.d(this.displayTitle) + 1.0;
         double countTextWidth = countFont.N("" + this.getEntryCount());
         double badgeWidth = Math.max(11.0, countTextWidth + 6.0);
         double badgeHeight = 10.0;
         double badgeX = left + width - 4.0 - badgeWidth;
         double badgeY = top + 3.0;
         float iconSize = 6.0F;
         float iconX = (float)(left + 6.0);
         float iconY = (float)(top + (height - iconSize) / 2.0);
         double textX = left + 17.0;
         double summaryWidth = Math.max(0.0, badgeX - textX - 4.0);
         GuiRenderPrimitives.B(left, top, width, height, backgroundColor, 3.0F);
         GuiRenderPrimitives.B(badgeX, badgeY, badgeWidth, badgeHeight, badgeColor, 2.4F);
         titleFont.d(this.displayTitle, textX, titleY, primaryColor);
         summaryFont.d(this.buildEntrySummary(summaryFont, summaryWidth), textX, summaryY, secondaryColor);
         countFont.d("" + this.getEntryCount(), badgeX + (badgeWidth - countTextWidth) / 2.0, badgeY + 1.5, primaryColor);
         if (this.blockedList) {
            ImageRenderer.drawImage(iconColor, iconX + 0.5F, iconY, "newblockedlist", iconSize, iconSize, false);
            ImageRenderer.drawImage(J.d, iconX - 0.5F, iconY, "newblocked", iconSize, iconSize, false);
         } else {
            ImageRenderer.drawImage(iconColor, iconX + 0.5F, iconY, "newallowedlist", iconSize, iconSize, false);
            ImageRenderer.drawImage(J.B, iconX + 0.5F, iconY, "newallowed", iconSize, iconSize, false);
         }
      } else {
         double left = this.G$src$D$1b2f02a() + 5.0;
         double top = this.n() + 0.5;
         double width = this.A() - 10.0;
         double height = this.L() - 1.0;
         Color backgroundColor = this.isHovered() ? J.a : J.S;
         Color primaryColor = this.isHovered() ? J.A : J.Z;
         Color secondaryColor = J.h;
         Color badgeColor = this.isHovered() ? J.F : J.a;
         double titleY = top + 3.0;
         double summaryY = titleY + titleFont.d(this.displayTitle) + 1.0;
         double countTextWidth = countFont.N("" + this.getEntryCount());
         double badgeWidth = Math.max(11.0, countTextWidth + 6.0);
         double badgeHeight = 10.0;
         double badgeX = left + width - 4.0 - badgeWidth;
         double badgeY = top + 3.0;
         float iconSize = 6.0F;
         float iconX = (float)(left + 6.0);
         float iconY = (float)(top + (height - iconSize) / 2.0);
         double textX = left + 17.0;
         double summaryWidth = Math.max(0.0, badgeX - textX - 4.0);
         GuiRenderPrimitives.B(left, top, width, height, backgroundColor, 3.0F);
         GuiRenderPrimitives.B(badgeX, badgeY, badgeWidth, badgeHeight, badgeColor, 2.4F);
         titleFont.d(this.displayTitle, textX, titleY, primaryColor);
         summaryFont.d(this.buildEntrySummary(summaryFont, summaryWidth), textX, summaryY, secondaryColor);
         countFont.d("" + this.getEntryCount(), badgeX + (badgeWidth - countTextWidth) / 2.0, badgeY + 1.5, primaryColor);
         if (this.blockedList) {
            ImageRenderer.drawImage(primaryColor, iconX + 0.5F, iconY, "newblockedlist", iconSize, iconSize, false);
            ImageRenderer.drawImage(J.d, iconX - 0.5F, iconY, "newblocked", iconSize, iconSize, false);
         } else {
            ImageRenderer.drawImage(primaryColor, iconX + 0.5F, iconY, "newallowedlist", iconSize, iconSize, false);
            ImageRenderer.drawImage(J.B, iconX + 0.5F, iconY, "newallowed", iconSize, iconSize, false);
         }
      }
   }

   @Override
   public void c() {
      if (!this.useAnchoredPopup && this.dropdownLayer != null) {
         this.dropdownLayer.updatePosition();
      }

      super.c();
   }

   public void setUseAnchoredPopup(boolean useAnchoredPopup) {
      this.useAnchoredPopup = useAnchoredPopup;
   }

   @Override
   public double C() {
      return this.mode == ValueComponentMode.STANDALONE ? 23.0 : super.C();
   }

   private boolean hasSingleBooleanParentEntry() {
      if (!(this.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa() instanceof ClickGuiMainFrame)) {
         return false;
      } else {
         return !(this.listValue.getParent() instanceof BooleanValue) ? false : ((BooleanValue)this.listValue.getParent()).getDependentValues().size() == 1;
      }
   }

   public boolean isBlockedList() {
      return this.blockedList;
   }

   public String getDisplayTitle() {
      return this.displayTitle;
   }

   public void setMode(ValueComponentMode mode) {
      this.mode = mode;
   }
}
