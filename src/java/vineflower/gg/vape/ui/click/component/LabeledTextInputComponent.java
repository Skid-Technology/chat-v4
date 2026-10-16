package gg.vape.ui.click.component;

import gg.vape.ui.click.component.gui.TextLabel;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class LabeledTextInputComponent extends TextInputComponentBase {
   private final TextLabel doneLabel;
   private double inputHeight = 20.0;
   private boolean editingFavorites;
   private boolean showEditButton;
   private final SquareIconButtonComponent clearButton;
   private String placeholder;
   private final IconButtonComponent editButton;
   protected IconButtonComponent searchIcon = new IconButtonComponent("newsearch", 0.75);
   private boolean searchIconTrailing;
   private boolean outlined;
   private double inputWidth = 110.0;

   @Override
   public void o(double width) {
      super.o(width);
      this.inputWidth = width;
   }

   private void clearInputText() {
      this.setText("");
   }

   public boolean isSearchIconTrailing() {
      return this.searchIconTrailing;
   }

   @Override
   public double C() {
      return this.inputHeight;
   }

   public void setOutlined(boolean outlined) {
      this.outlined = outlined;
   }

   public IconButtonComponent getEditButton() {
      return this.editButton;
   }

   public IconButtonComponent getSearchIcon() {
      return this.searchIcon;
   }

   public LabeledTextInputComponent(String placeholder, boolean searchIconTrailing, boolean outlined) {
      super(placeholder);
      this.editButton = new IconButtonComponent("newedit", 0.65F);
      this.doneLabel = new TextLabel("Done", 0.65, false);
      this.clearButton = new SquareIconButtonComponent("newclose", 1.0);
      this.actionButton.setVisible(false);
      this.editButton.setVisible(false);
      this.doneLabel.setVisible(false);
      this.doneLabel.setTextColor(J.Z);
      this.clearButton.setVisible(false);
      this.clearButton.o(10.0);
      this.clearButton.Y(10.0);
      this.clearButton.addClickListener(this::clearInputText);
      this.addChildren(this.searchIcon, this.editButton, this.doneLabel, this.clearButton);
      this.searchIconTrailing = searchIconTrailing;
      this.outlined = outlined;
      this.placeholder = placeholder;
      if (outlined) {
         this.setBackgroundVisible(false);
      }
   }

   public SquareIconButtonComponent getClearButton() {
      return this.clearButton;
   }

   public boolean isShowEditButton() {
      return this.showEditButton;
   }

   public TextLabel getDoneLabel() {
      return this.doneLabel;
   }

   public boolean isOutlined() {
      return this.outlined;
   }

   public LabeledTextInputComponent(String placeholder) {
      this(placeholder, true, false);
   }

   public void setSearchIconTrailing(boolean searchIconTrailing) {
      this.searchIconTrailing = searchIconTrailing;
   }

   @Override
   public double x() {
      return this.inputWidth;
   }

   @Override
   public void H() {
      if (this.editingFavorites) {
         this.editButton.setVisible(false);
         this.searchIcon.setVisible(false);
         this.doneLabel.setVisible(true);
         this.doneLabel.K(this.G$src$D$1b2f02a() + this.getComponentWidth() - this.getLeftInset() - this.doneLabel.getTextWidth() - 4.0);
         this.doneLabel.S(this.n());
         this.doneLabel.Y(this.L());
         this.doneLabel.o(this.doneLabel.getTextWidth());
         this.doneLabel.setTextColor(J.A);
         this.doneLabel.setUseAlternateFont(true);
         this.doneLabel.setFontScale(0.8);
         this.setDisabled(true);
         this.setPlaceholderText("Editing favorites");
         this.setPlaceholderColor(J.I);
      } else {
         this.setPlaceholderColor(J.A);
         this.doneLabel.setVisible(false);
         this.searchIcon.setVisible(true);
         this.setDisabled(false);
         this.setPlaceholderText(this.placeholder);
         boolean hasText = this.getText() != null && !this.getText().isEmpty();
         if (hasText) {
            this.editButton.setVisible(false);
            this.clearButton.setVisible(true);
            double clearButtonX = this.G$src$D$1b2f02a() + this.getComponentWidth() - this.getLeftInset() - this.clearButton.A() - 4.0;
            this.clearButton.K(clearButtonX);
            this.clearButton.S(this.n() + (this.L() - this.clearButton.L()) / 2.0);
         } else {
            this.clearButton.setVisible(false);
            this.editButton.setVisible(this.showEditButton);
            if (this.showEditButton) {
               this.editButton.K(this.G$src$D$1b2f02a() + this.getComponentWidth() - this.getLeftInset() - 8.0 - 6.0);
               this.editButton.S(this.n());
               this.editButton.Y(this.L());
               this.editButton.o(8.0);
               Color borderColor = this.borderAnimation.getInterpolatedColor();
               this.editButton
                  .setNormalColor(new Color(borderColor.getRed(), borderColor.getGreen(), borderColor.getBlue(), Math.max(borderColor.getAlpha() - 6, 0)));
            }
         }
      }

      double editButtonOffset = this.showEditButton && !this.editingFavorites ? 12.0 : 0.0;
      if (this.editingFavorites) {
         double var10000 = this.doneLabel.getTextWidth() + 4.0;
      } else {
         double var7 = 0.0;
      }

      if (!this.editingFavorites) {
         if (this.searchIconTrailing) {
            this.searchIcon.K(this.G$src$D$1b2f02a() + this.getComponentWidth() - this.getLeftInset() - 8.0 - 6.0 - editButtonOffset);
         } else {
            this.searchIcon.K(this.G$src$D$1b2f02a() + this.getLeftInset() + 5.0);
         }

         this.searchIcon.S(this.n());
         this.searchIcon.Y(this.L());
         this.searchIcon.o(8.0);
      }

      if (this.outlined) {
         this.setTextColor(J.Z);
         GuiRenderPrimitives.P(
            this.G$src$D$1b2f02a() + this.getLeftInset(),
            this.n() + this.getVerticalInset() / 2.0F,
            this.getComponentWidth() - this.getLeftInset() * 2.0F,
            this.L() - this.getVerticalInset(),
            this.isDisabled() ? J.y.darker() : J.y,
            2.0F,
            0.75F,
            1.0F
         );
      }
   }

   public void setEditingFavorites(boolean editingFavorites) {
      this.editingFavorites = editingFavorites;
   }

   @Override
   public float getRightInset() {
      return 5.0F + (this.searchIcon.V$src$Z$1xhop3l() ? (this.searchIconTrailing ? 0.0F : 12.0F) : 0.0F);
   }

   public boolean isEditingFavorites() {
      return this.editingFavorites;
   }

   @Override
   public double getAvailableTextWidth() {
      boolean hasText = this.getText() != null && !this.getText().isEmpty();
      double trailingControlWidth = this.editingFavorites
         ? this.doneLabel.getTextWidth() + 8.0
         : (hasText ? this.clearButton.A() + 8.0 : (this.showEditButton ? 12.0 : 0.0));
      return this.getComponentWidth()
         - (this.searchIcon.V$src$Z$1xhop3l() ? (this.searchIconTrailing ? 16.0F : 4.0F) : 0.0F)
         - this.getRightInset()
         - this.getLeftInset() * 2.0F
         - trailingControlWidth;
   }

   @Override
   public void submit() {
   }

   @Override
   public void Y(double height) {
      super.Y(height);
      this.inputHeight = height;
   }

   public void setShowEditButton(boolean showEditButton) {
      this.showEditButton = showEditButton;
   }
}
