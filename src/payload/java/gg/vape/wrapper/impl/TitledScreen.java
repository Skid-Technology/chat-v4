package gg.vape.wrapper.impl;

public class TitledScreen extends Screen {
   public String getDisplayedTitle() {
      if (ForgeVersion.MC_1_7_10.L()) {
         return "";
      } else if (ForgeVersion.MC_1_16_5.d()) {
         ITextComponent title = new ITextComponent(vapeInstance.getMappingsMapperCompat().titledScreen.getDisplayedTitle(this.I));
         return title.getFormattedText();
      } else {
         return (String)vapeInstance.getMappingsMapperCompat().titledScreen.getDisplayedTitle(this.I);
      }
   }

   public TitledScreen(Object titledScreenHandle) {
      super(titledScreenHandle);
   }
}
