package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class FontManager extends Wrapper {
   public FontSet K() {
      Object object = vapeInstance.getMappingsMapperCompat().qb.x(this.I);
      return object != null ? new FontSet(object) : null;
   }

   public FontSet h() {
      Object object = vapeInstance.getMappingsMapperCompat().qb.u(this.I);
      return object != null ? new FontSet(object) : null;
   }

   public FontManager(Object object) {
      super(object);
   }

   public FontSet w(String string) {
      Object object = vapeInstance.getMappingsMapperCompat().qb.x(this.I, string);
      return object != null ? new FontSet(object) : null;
   }
}
