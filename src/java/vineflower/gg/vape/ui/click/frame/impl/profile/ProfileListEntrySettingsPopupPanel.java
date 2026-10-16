package gg.vape.ui.click.frame.impl.profile;

import gg.vape.ui.click.component.GlyphIconComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.TextInputComponentBase;
import gg.vape.ui.click.component.TruncatedTextComponent;
import gg.vape.utils.render.GuiRenderPrimitives;

class ProfileListEntrySettingsPopupPanel extends PanelComponent {
   private final ProfileGlyphIconPanel publicProfileIcon;
   private final GlyphIconComponent renameButton;
   private final TextInputComponentBase renameInput;
   private final TruncatedTextComponent nameDisplay;

   @Override
   public void c() {
      this.nameDisplay.K(this.G$src$D$1b2f02a() + this.A() / 2.0 - this.nameDisplay.getRenderedWidth() / 2.0);
      this.nameDisplay.S(this.n() + 18.0);
      this.renameButton.K(this.nameDisplay.G$src$D$1b2f02a() - 8.0);
      this.renameButton.S(this.nameDisplay.n() + 1.0);
      this.renameInput.K(this.G$src$D$1b2f02a() + this.A() / 2.0 - this.renameInput.A() / 2.0);
      this.renameInput.S(this.nameDisplay.n() - 8.0);
      boolean renaming = this.renameInput.V$src$Z$1xhop3l();
      TruncatedTextComponent var2 = this.nameDisplay;
      TruncatedTextComponent var10000;
      boolean var10001;
      if (!renaming) {
         var10000 = var2;
         var10001 = true;
      } else {
         var10000 = var2;
         var10001 = false;
      }

      label30: {
         var10000.setVisible(var10001);
         GlyphIconComponent var3 = this.renameButton;
         if (!renaming) {
            var5 = var3;
            if (this.w$src$Z$e457mb()) {
               var10001 = true;
               break label30;
            }
         } else {
            var5 = var3;
         }

         var10001 = false;
      }

      var5.setVisible(var10001);
      if (this.publicProfileIcon != null) {
         this.publicProfileIcon.K(this.nameDisplay.G$src$D$1b2f02a() + this.nameDisplay.A() + 3.0);
         this.publicProfileIcon.S(this.nameDisplay.n() + 1.0);
         ProfileGlyphIconPanel var4 = this.publicProfileIcon;
         ProfileGlyphIconPanel var6;
         if (!renaming) {
            var6 = var4;
            var10001 = true;
         } else {
            var6 = var4;
            var10001 = false;
         }

         var6.setVisible(var10001);
      }

      super.c();
      if (renaming) {
         GuiRenderPrimitives.a(this.renameInput.G$src$D$1b2f02a() + 10.0, this.renameInput.n() + 17.0, this.renameInput.A() - 31.0, 1.0F, J.y);
      }
   }

   ProfileListEntrySettingsPopupPanel(
      double width,
      double height,
      TruncatedTextComponent nameDisplay,
      GlyphIconComponent renameButton,
      TextInputComponentBase renameInput,
      ProfileGlyphIconPanel publicProfileIcon
   ) {
      super(width, height);
      this.nameDisplay = nameDisplay;
      this.renameButton = renameButton;
      this.renameInput = renameInput;
      this.publicProfileIcon = publicProfileIcon;
   }
}
