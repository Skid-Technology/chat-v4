package gg.vape.ui.font;

public class NotoFontOption extends FontOption {
   public NotoFontOption(String string) {
      super(string);
   }

   @Override
   public FontFamily b() {
      return FontFamily.NOTO;
   }
}
