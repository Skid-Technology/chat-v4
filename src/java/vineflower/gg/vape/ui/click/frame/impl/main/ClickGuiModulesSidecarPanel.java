package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.component.IconGlyphComponent;
import gg.vape.ui.click.component.IconShape;
import gg.vape.ui.click.component.ShapeIconComponent;
import java.util.function.Supplier;
import org.jetbrains.annotations.Nullable;

public class ClickGuiModulesSidecarPanel extends ClickGuiSidecarPanelBase {
   private final ShapeIconComponent toggleButton;
   private final IconGlyphComponent favoriteIcon;
   @Nullable
   private Supplier<String> toggleLabelSupplier;
   @Nullable
   private Runnable savedBackAction;

   public ClickGuiModulesSidecarPanel(@Nullable Runnable runnable) {
      this.getLeadingIcon().setIconResource("moduleback");
      this.getLeadingIcon().setVisible(true);
      this.favoriteIcon = new IconGlyphComponent("newstar", 6.0F, 6.0F, J.W);
      this.favoriteIcon.setVisible(false);
      this.favoriteIcon.o(10.0);
      this.favoriteIcon.Y(7.0);
      this.toggleButton = new ShapeIconComponent(IconShape.ROUNDED_RECT, "", 12.0, 12.0, 4.0, 2.0F, J.F, J.A, 0.6);
      this.toggleButton.setVisible(false);
      this.toggleButton.o(14.0);
      this.toggleButton.Y(10.0);
      this.addChildren(this.favoriteIcon, this.toggleButton);
      this.addTrailingComponent(this.toggleButton);
      this.addTrailingComponent(this.favoriteIcon);
   }

   public void setFavoriteHighlighted(boolean highlighted) {
      this.favoriteIcon.setColor(highlighted ? J.I : J.W);
   }

   public void setToggleVisible(boolean visible) {
      this.toggleButton.setVisible(visible);
   }

   public void setToggleAction(@Nullable Runnable action) {
      this.toggleButton.clearMouseListeners();
      if (action != null) {
         this.toggleButton.addMouseListener(new ClickGuiModulesSidecarPrimaryMouseListener(action));
      }
   }

   @Override
   public void H() {
      if (this.toggleLabelSupplier != null) {
         String label = this.toggleLabelSupplier.get();
         ShapeIconComponent var2 = this.toggleButton;
         ShapeIconComponent var10000;
         String var10001;
         if (label != null) {
            var10000 = var2;
            var10001 = label;
         } else {
            var10000 = var2;
            var10001 = "";
         }

         label19: {
            var10000.setText(var10001);
            ShapeIconComponent var3 = this.toggleButton;
            if (label != null) {
               var10000 = var3;
               if (!label.isEmpty()) {
                  var5 = true;
                  break label19;
               }
            } else {
               var10000 = var3;
            }

            var5 = false;
         }

         var10000.setVisible(var5);
      }

      super.H();
   }

   @Override
   public void setLeadingIconKey(@Nullable String string) {
   }

   public void setToggleLabel(@Nullable String label) {
      if (label == null) {
         label = "";
      }

      this.toggleButton.setText(label);
      this.toggleButton.setVisible(!label.isEmpty());
   }

   public void setFavoriteAction(@Nullable Runnable action) {
      this.favoriteIcon.clearMouseListeners();
      if (action != null) {
         this.favoriteIcon.addMouseListener(new ClickGuiModulesSidecarSecondaryMouseListener(action));
      }
   }

   public void setToggleLabelSupplier(@Nullable Supplier<String> supplier) {
      this.toggleLabelSupplier = supplier;
      if (supplier == null) {
         this.setToggleLabel("");
      }
   }

   @Override
   public void setBackAction(@Nullable Runnable runnable) {
      if (runnable != null && this.savedBackAction == null) {
         this.savedBackAction = runnable;
      }

      Runnable runnable2 = runnable != null ? runnable : this.savedBackAction;
      super.setBackAction(runnable2);
      if (runnable2 != null) {
         this.getCloseButton().setVisible(false);
         this.getLeadingIcon().setVisible(true);
      }
   }

   public void setFavoriteVisible(boolean visible) {
      this.favoriteIcon.setVisible(visible);
   }
}
