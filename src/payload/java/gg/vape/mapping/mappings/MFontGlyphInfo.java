package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MFontGlyphInfo extends Mapping {
   private final MappingMethod advanceMethod = this.Y("getAdvance", true, float.class);
   private final MappingMethod styledAdvanceMethod = this.Y("getAdvance", true, float.class, boolean.class);
   private final MappingMethod boldOffsetMethod = this.Y("getBoldOffset", true, float.class);
   private final MappingMethod shadowOffsetMethod = this.Y("getShadowOffset", true, float.class);
   private static int[] controlFlowState;

   public static void setFontGlyphInfoControlFlowState(int[] state) {
      controlFlowState = state;
   }

   public float getShadowOffset(Object glyphInfo) {
      return this.shadowOffsetMethod != null && !this.shadowOffsetMethod.hasResolutionFailed() ? this.shadowOffsetMethod.invokeFloat(glyphInfo) : 1.0F;
   }

   public float getBoldOffset(Object glyphInfo) {
      return this.boldOffsetMethod != null && !this.boldOffsetMethod.hasResolutionFailed() ? this.boldOffsetMethod.invokeFloat(glyphInfo) : 1.0F;
   }

   public static int[] getFontGlyphInfoControlFlowState() {
      return controlFlowState;
   }

   public float getAdvance(Object glyphInfo, boolean bold) {
      if (this.styledAdvanceMethod != null && !this.styledAdvanceMethod.hasResolutionFailed()) {
         return this.styledAdvanceMethod.invokeFloat(glyphInfo, bold);
      } else {
         float advance = this.getAdvance(glyphInfo);
         return bold ? advance + this.getBoldOffset(glyphInfo) : advance;
      }
   }

   public float getAdvance(Object glyphInfo) {
      return this.advanceMethod != null && !this.advanceMethod.hasResolutionFailed() ? this.advanceMethod.invokeFloat(glyphInfo) : 0.0F;
   }

   public MFontGlyphInfo() {
      super(MappedClasses.g);
   }

   static {
      setFontGlyphInfoControlFlowState(null);
   }
}
