package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;

public class MFontSet extends Mapping {
   private MappingMethod sourceMethod;
   private MappingField missingGlyphField;
   private static String fontSetControlFlowMarker;

   public static void setFontSetControlFlowMarker(String marker) {
      fontSetControlFlowMarker = marker;
   }

   public Object getMissingGlyph(Object fontSetHandle) {
      return this.missingGlyphField.getObject(fontSetHandle);
   }

   public MFontSet() {
      this(getFontSetControlFlowMarker());
   }

   private MFontSet(String controlFlowMarker) {
      super(MappedClasses.D9);
      Class missingGlyphFieldType = MappedClasses.qd;
      boolean missingGlyphFieldPublic = true;
      String missingGlyphFieldName = "missingGlyph";
      this.missingGlyphField = this.J(missingGlyphFieldName, missingGlyphFieldPublic, missingGlyphFieldType);
      if (controlFlowMarker != null) {
         Class[] sourceParameterTypes = new Class[]{boolean.class};
         Class sourceReturnType = MappedClasses.Yx;
         boolean sourcePublic = true;
         String sourceMethodName = "source";
         this.sourceMethod = this.Y(sourceMethodName, sourcePublic, sourceReturnType, sourceParameterTypes);
      } else {
         Class[] sourceParameterTypes = new Class[]{boolean.class};
         Class sourceReturnType = MappedClasses.Yx;
         boolean sourcePublic = true;
         String sourceMethodName = "source";
         this.sourceMethod = this.Y(sourceMethodName, sourcePublic, sourceReturnType, sourceParameterTypes);
         GuiComponent.setLegacyComponentState(new GuiComponent[5]);
      }
   }

   public static String getFontSetControlFlowMarker() {
      return fontSetControlFlowMarker;
   }

   public Object getSource(Object fontSetHandle, boolean filterFishyGlyphs) {
      return this.sourceMethod.invokeObject(fontSetHandle, filterFishyGlyphs);
   }

   static {
      setFontSetControlFlowMarker("A5Xu0b");
   }
}
