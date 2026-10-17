package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.mapping.ItemMappingEntry;
import gg.vape.module.none.ClientSettings;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterRule;
import gg.vape.module.utility.inventory.cleaner.ItemFilterSelection;
import gg.vape.module.utility.inventory.cleaner.MaterialFilterCondition;
import gg.vape.ui.click.component.GlyphIconComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.ui.click.frame.AnchoredPopupFrame;
import gg.vape.ui.click.frame.ScrollableFrameComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.util.ArrayList;
import org.jetbrains.annotations.Nullable;

public class MaterialFilterSelectionList extends ScrollableFrameComponent {
   private final InventoryFilterRule filterRule;
   private final MaterialFilterCondition materialCondition;
   private static final String ADD_ICON = "newadd";

   private void addPickerSelection(ItemPickerSelection pickerSelection) {
      if (pickerSelection != null) {
         ItemFilterSelection selection = new ItemFilterSelection();
         selection.setSelection(pickerSelection);
         this.materialCondition.addSelection(selection);
         this.addSelectionRow(selection);
      }
   }

   private void removePickerSelection(ItemPickerSelection pickerSelection) {
      ItemFilterSelection selection = this.materialCondition
         .findSelectionById(
            pickerSelection.getLeft() != null ? (String)pickerSelection.getLeft() : ((ItemMappingEntry)pickerSelection.getRight()).getResourceKey()
         );
      if (selection != null) {
         MaterialFilterSelectionRow selectionRow = this.findSelectionRow(selection);
         if (selectionRow != null) {
            this.removeSelectionRow(selectionRow);
         }
      }
   }

   @Override
   public void H() {
      super.H();
      GuiRenderPrimitives.P(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L() + 2.0, J.y, 2.0F, 0.75F, 1.0F);
   }

   private void openItemPicker(GlyphIconComponent addButton) {
      ArrayList<String> selectedItemIds = new ArrayList<>();

      for (ItemFilterSelection selection : this.materialCondition.getSelections()) {
         selectedItemIds.add(selection.getItemName());
      }

      InventoryItemPickerPanel pickerPanel = new InventoryItemPickerPanel(
         this.filterRule, true, this.filterRule.getItemSelection().getMatcher(), selectedItemIds, this::addPickerSelection
      );
      pickerPanel.setOnExistingSelection(this::removePickerSelection);
      AnchoredPopupFrame popup = ClientSettings.createPopup(addButton, pickerPanel, AnchoredPopupFrame.class);
      popup.O(false);
      popup.C$src$V$nadrmg();
      popup.q(this.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa(), popup);
      popup.addGlobalMouseListener(new MaterialFilterSelectionListClosePopupMouseListener(this, popup));
   }

   @Nullable
   public MaterialFilterSelectionRow findSelectionRow(ItemFilterSelection selection) {
      for (GuiComponent child : this.f()) {
         PaddedComponent paddedChild;
         MaterialFilterSelectionRow selectionRow;
         if (child instanceof PaddedComponent
            && (selectionRow = (paddedChild = (PaddedComponent)child).t(MaterialFilterSelectionRow.class)) != null
            && selection.equals(selectionRow.getSelection())) {
            return selectionRow;
         }
      }

      return null;
   }

   public MaterialFilterSelectionList(InventoryFilterRule filterRule, MaterialFilterCondition materialCondition, double width) {
      super(width, 14.0);
      this.filterRule = filterRule;
      this.materialCondition = materialCondition;
      this.setShowDisabledOverlay(false);
      GlyphIconComponent glyphIconComponent = new GlyphIconComponent("newadd", 7.0, 7.0, 14.0, 14.0, J.B, J.O, null);
      glyphIconComponent.setBackgroundAnimationColors(J.z, J.M);
      glyphIconComponent.setCenterHorizontally(true);
      glyphIconComponent.setCenterVertically(true);
      glyphIconComponent.addClickListener(() -> this.openItemPicker(glyphIconComponent));
      this.h(new PaddedComponent(3.0, 0.0, 3.0, 0.0, glyphIconComponent));
   }

   public void addSelectionRow(ItemFilterSelection selection) {
      MaterialFilterSelectionRow selectionRow = new MaterialFilterSelectionRow(selection);
      selectionRow.addMouseListener(new MaterialFilterSelectionRemoveClickHandler(this, selectionRow));
      this.addChildren(new PaddedComponent(0.0, 0.0, 0.0, 0.0, selectionRow));
   }

   public void removeSelectionRow(MaterialFilterSelectionRow selectionRow) {
      ArrayList<PaddedComponent> rowsToRemove = new ArrayList<>();
      this.materialCondition.removeSelection(selectionRow.getSelection());

      for (GuiComponent child : this.f()) {
         PaddedComponent paddedChild;
         if (child instanceof PaddedComponent && selectionRow.equals((paddedChild = (PaddedComponent)child).t(MaterialFilterSelectionRow.class))) {
            rowsToRemove.add(paddedChild);
         }
      }

      for (GuiComponent childx : rowsToRemove) {
         this.removeChild(childx);
      }
   }
}
