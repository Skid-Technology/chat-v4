package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class TextFormatting extends Wrapper {
   public static TextFormatting fromName(String name) {
      Object formattingHandle = vapeInstance.getMappingsMapperCompat().textFormatting.getByName(name);
      return formattingHandle != null ? new TextFormatting(formattingHandle) : null;
   }

   public TextFormatting(Object object) {
      super(object);
   }

   public Integer getColor() {
      return vapeInstance.getMappingsMapperCompat().textFormatting.getColor(this.getObject());
   }

   public String getName() {
      return vapeInstance.getMappingsMapperCompat().textFormatting.getName(this.getObject());
   }
}
