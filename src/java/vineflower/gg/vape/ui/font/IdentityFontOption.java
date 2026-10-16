package gg.vape.ui.font;

public class IdentityFontOption extends BaseFontOption {
   @Override
   public String s(String string) {
      return string;
   }

   public IdentityFontOption(String string) {
      super(string);
   }
}
