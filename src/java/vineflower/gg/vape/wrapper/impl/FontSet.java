package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class FontSet extends Wrapper {
   public FontGlyph getGlyph(int codePoint, boolean filterFishyGlyphs) {
      GlyphProvider glyphSource = this.getGlyphSource(filterFishyGlyphs);
      if (glyphSource.isNull()) {
         return null;
      } else {
         FontGlyph resolvedGlyph = glyphSource.getGlyph(codePoint);
         return resolvedGlyph != null ? resolvedGlyph : this.getMissingGlyph();
      }
   }

   public FontGlyph getMissingGlyph() {
      Object glyphHandle = vapeInstance.getMappingsMapperCompat().fontSet.getMissingGlyph(this.I);
      return glyphHandle != null ? new FontGlyph(glyphHandle) : null;
   }

   public FontGlyph getGlyph(char character) {
      return this.getGlyph(character, false);
   }

   public GlyphProvider getGlyphSource(boolean filterFishyGlyphs) {
      return new GlyphProvider(vapeInstance.getMappingsMapperCompat().fontSet.getSource(this.I, filterFishyGlyphs));
   }

   public GlyphInfo getGlyphInfo(char character) {
      FontGlyph fontGlyph = this.getGlyph(character);
      return fontGlyph != null ? fontGlyph.asGlyphInfo() : null;
   }

   public FontSet(Object handle) {
      super(handle);
   }
}
