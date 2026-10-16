package gg.vape.ui.click.component.value;

import gg.vape.Vape;
import gg.vape.manager.SearchManager;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.impl.main.ClickGuiMainFrame;
import gg.vape.ui.click.frame.impl.main.ClickGuiModulesSidecarPanel;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlayPlacement;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlaySpec;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlayTransitionMode;
import gg.vape.ui.click.frame.impl.main.ClickGuiSidecarPanelBase;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.ui.unmap.SearchBlock;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;

public class SearchBlockListComponent extends AbstractListValueComponent {
   private ValueComponentMode mode;
   private String title;
   SearchManager searchManager = Vape.INSTANCE.getSearch();
   private SearchBlockListDropdownLayer dropdownLayer;

   @Override
   public double C() {
      return this.mode == ValueComponentMode.STANDALONE ? 23.0 : super.C();
   }

   private void openEditor() {
      Frame frame;
      if (this.mode == ValueComponentMode.STANDALONE
         && this.getParentFrameComponent() != null
         && (frame = this.getParentFrameComponent().L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa()) instanceof ClickGuiMainFrame) {
         ClickGuiMainFrame clickGuiMainFrame = (ClickGuiMainFrame)frame;
         ClickGuiModulesSidecarPanel clickGuiModulesSidecarPanel = new ClickGuiModulesSidecarPanel(null);
         clickGuiModulesSidecarPanel.setFavoriteVisible(false);
         clickGuiModulesSidecarPanel.setToggleVisible(false);
         ClickGuiOverlaySpec clickGuiOverlaySpec = ClickGuiOverlaySpec.builder()
            .title(this.title)
            .sidecarIcon("newallowed")
            .sidecar(clickGuiModulesSidecarPanel)
            .placement(ClickGuiOverlayPlacement.DOCKED_SHIFT)
            .transitionMode(ClickGuiOverlayTransitionMode.PUSH)
            .initializeSidecar(SearchBlockListComponent::hideSidecarDivider)
            .initializeContent(this::populateEditorContent)
            .build();
         clickGuiMainFrame.showOverlay(clickGuiOverlaySpec);
      } else {
         this.setExpanded(!this.isExpanded());
         if (this.isExpanded()) {
            this.dropdownLayer.refreshContents();
         }
      }
   }

   private String truncateToWidth(SmoothFontRenderer fontRenderer, String text, double maximumWidth) {
      if (!text.isEmpty() && !(fontRenderer.N(text) <= maximumWidth)) {
         int lowerBound = 0;
         int upperBound = text.length();
         int bestLength = 0;

         while (lowerBound <= upperBound) {
            int midpoint = (lowerBound + upperBound) / 2;
            String candidate = text.substring(0, midpoint);
            if (fontRenderer.N(candidate) <= maximumWidth) {
               bestLength = midpoint;
               lowerBound = midpoint + 1;
            } else {
               upperBound = midpoint - 1;
            }
         }

         return text.substring(0, bestLength);
      } else {
         return text;
      }
   }

   private static void hideSidecarDivider(ClickGuiSidecarPanelBase clickGuiSidecarPanelBase) {
      clickGuiSidecarPanelBase.setDividerVisible(false);
   }

   public String getTitle() {
      return this.title;
   }

   private void populateEditorContent(PanelComponent panelComponent) {
      panelComponent.removeMarkedChildren();
      Runnable refreshContent = () -> this.refreshEditorContent(panelComponent);
      SearchBlockListAddInputComponent searchBlockListAddInputComponent = new SearchBlockListAddInputComponent("Block name / ID", refreshContent);
      searchBlockListAddInputComponent.setExplicitWidth(panelComponent.A() - 1.0);
      panelComponent.h(searchBlockListAddInputComponent);

      for (SearchBlock searchBlock : this.searchManager.getSearchBlocks()) {
         SearchBlockEditorComponent searchBlockEditorComponent = new SearchBlockEditorComponent(searchBlock);
         searchBlockEditorComponent.setExplicitWidth(panelComponent.A() - 1.0);
         searchBlockEditorComponent.setRemoveClickListener(new SearchBlockRemoveClickListener(this, searchBlock, refreshContent));
         panelComponent.h(searchBlockEditorComponent);
      }
   }

   private void renderStandalone() {
      this.onDisable();
      SmoothFontRenderer titleFont = this.getFontRenderer(0.85);
      SmoothFontRenderer summaryFont = this.getFontRenderer(0.7);
      SmoothFontRenderer countFont = this.getFontRenderer(0.68);
      double left = this.G$src$D$1b2f02a() + 5.0;
      double top = this.n() + 0.5;
      double width = this.A() - 10.0;
      double height = this.L() - 1.0;
      Color backgroundColor = this.isHovered() ? J.a : J.S;
      Color primaryColor = this.isHovered() ? J.A : J.Z;
      Color secondaryColor = J.h;
      double titleY = top + 3.0;
      double summaryY = titleY + titleFont.d(this.title) + 1.0;
      double countTextWidth = countFont.N("" + this.getEnabledCount());
      double countBadgeWidth = Math.max(11.0, countTextWidth + 6.0);
      double countBadgeHeight = 10.0;
      double countBadgeX = left + width - 4.0 - countBadgeWidth;
      double countBadgeY = top + 3.0;
      float iconSize = 6.0F;
      float iconX = (float)(left + 6.0);
      float iconY = (float)(top + (height - iconSize) / 2.0);
      double textX = left + 17.0;
      double summaryWidth = Math.max(0.0, countBadgeX - textX - 4.0);
      GuiRenderPrimitives.B(left, top, width, height, backgroundColor, 3.0F);
      GuiRenderPrimitives.B(countBadgeX, countBadgeY, countBadgeWidth, countBadgeHeight, this.isHovered() ? J.F : J.a, 2.4F);
      titleFont.d(this.title, textX, titleY, primaryColor);
      summaryFont.d(this.buildEnabledSummary(summaryFont, summaryWidth), textX, summaryY, secondaryColor);
      countFont.d("" + this.getEnabledCount(), countBadgeX + (countBadgeWidth - countTextWidth) / 2.0, countBadgeY + 1.5, primaryColor);
      ImageRenderer.drawImage(primaryColor, iconX + 0.5F, iconY, "newallowedlist", iconSize, iconSize, false);
      ImageRenderer.drawImage(J.B, iconX + 0.5F, iconY, "newallowed", iconSize, iconSize, false);
   }

   @Override
   public void H() {
      if (this.mode == ValueComponentMode.STANDALONE) {
         this.renderStandalone();
      } else {
         this.renderDropdown();
      }
   }

   public SearchBlockListComponent() {
      this(ValueComponentMode.MAIN);
   }

   public static void openEditorCompat(SearchBlockListComponent component) {
      component.openEditor();
   }

   private void renderDropdown() {
      this.onDisable();
      this.dropdownLayer.updatePosition();
      SmoothFontRenderer titleFont = this.getFontRenderer(0.9);
      SmoothFontRenderer summaryFont = this.getFontRenderer(0.75);
      Color panelColor = J.i;
      Color primaryColor = this.isHovered() ? J.A : (this.isExpanded() ? J.A : J.Z);
      Color secondaryColor = J.h;
      float iconY = (float)(this.n() + this.L() / 2.0) - 3.0F;
      double titleHeight = titleFont.d(this.title);
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
      titleFont.d(this.title, this.G$src$D$1b2f02a() + 15.0 + 8.0, titleY, primaryColor);
      titleFont.d("" + this.getEnabledCount(), this.G$src$D$1b2f02a() + this.A() - 10.0 - titleFont.N("10"), titleY, primaryColor);
      summaryFont.d(this.buildEnabledSummary(summaryFont, this.A() - 35.0), this.G$src$D$1b2f02a() + 15.0 + 8.0, summaryY, secondaryColor);
      ImageRenderer.drawImage(primaryColor, (float)this.G$src$D$1b2f02a() + 10.0F + 0.5F, iconY, "newallowedlist", 6.0F, 6.0F, false);
      ImageRenderer.drawImage(J.B, (float)this.G$src$D$1b2f02a() + 10.0F + 0.5F, iconY, "newallowed", 6.0F, 6.0F, false);
   }

   private void refreshEditorContent(PanelComponent panelComponent) {
      this.populateEditorContent(panelComponent);
   }

   private int getEnabledCount() {
      int enabledCount = 0;

      for (SearchBlock searchBlock : this.searchManager.getSearchBlocks()) {
         if (searchBlock.T()) {
            enabledCount++;
         }
      }

      return enabledCount;
   }

   public void setMode(ValueComponentMode mode) {
      this.mode = mode;
   }

   private String buildEnabledSummary(SmoothFontRenderer fontRenderer, double maximumWidth) {
      StringBuilder summary = new StringBuilder();

      for (SearchBlock searchBlock : this.searchManager.getSearchBlocks()) {
         if (searchBlock.T()) {
            String blockName = searchBlock.d();
            String nextEntry = summary.length() == 0 ? blockName : ", " + blockName;
            if (!(fontRenderer.N(summary + nextEntry) <= maximumWidth)) {
               summary.append(nextEntry);
               double textWidth = Math.max(0.0, maximumWidth - fontRenderer.N("..."));
               return this.truncateToWidth(fontRenderer, summary.toString(), textWidth) + "...";
            }

            summary.append(nextEntry);
         }
      }

      return summary.length() == 0 ? "None" : summary.toString();
   }

   public SearchBlockListComponent(ValueComponentMode valueComponentMode) {
      this.mode = ValueComponentMode.MAIN;
      this.mode = valueComponentMode;
      this.title = "Search blocks";
      this.addClickListener(new SearchBlockListOpenClickListener(this));
      if (valueComponentMode != ValueComponentMode.STANDALONE) {
         this.dropdownLayer = new SearchBlockListDropdownLayer(this);
         ClientSettings.INSTANCE.getActiveStack().q(this.dropdownLayer);
      }
   }

   public ValueComponentMode getMode() {
      return this.mode;
   }
}
