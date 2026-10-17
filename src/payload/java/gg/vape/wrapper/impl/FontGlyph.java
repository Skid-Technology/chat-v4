package gg.vape.wrapper.impl;

import gg.vape.mapping.MappedClasses;
import gg.vape.wrapper.Wrapper;

public class FontGlyph extends Wrapper {
   public boolean isGlyphInfo() {
      return MappedClasses.v != null && MappedClasses.v.isInstance(this.I);
   }

   public GlyphInfo asGlyphInfo() {
      return this.isGlyphInfo() ? new GlyphInfo(this.I) : null;
   }

   public FontGlyphInfo getInfo() {
      Object info = vapeInstance.getMappingsMapperCompat().De.getInfo(this.I);
      return info != null ? new FontGlyphInfo(info) : null;
   }

   public FontGlyph(Object wrappedObject) {
      super(wrappedObject);
   }
}
