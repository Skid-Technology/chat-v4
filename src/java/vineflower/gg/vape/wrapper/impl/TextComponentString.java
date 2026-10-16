package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MTextComponentString;

public class TextComponentString extends ITextComponent {
   public TextComponentString(Object handle) {
      super(handle);
   }

   public String getText() {
      return MTextComponentString.getText(vapeInstance.getMappingsMapperCompat().textComponentString, this.getObject());
   }

   public static TextComponentString create(String text) {
      return new TextComponentString(MTextComponentString.create(vapeInstance.getMappingsMapperCompat().textComponentString, text));
   }

   public void setText(String text) {
      MTextComponentString.setText(vapeInstance.getMappingsMapperCompat().textComponentString, this.getObject(), text);
   }
}
