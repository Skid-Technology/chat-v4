package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;

public class MGlyphProvider extends Mapping {
   private static int[] glyphSourceControlFlowState;
   private static final String GET_GLYPH_METHOD_NAME = "getGlyph";
   public MappingMethod getGlyphMethod;

   public static int[] getGlyphSourceControlFlowState() {
      return glyphSourceControlFlowState;
   }

   private Object invokeGetGlyph(Object glyphSourceHandle, int codePoint) {
      return this.getGlyphMethod.invokeObject(glyphSourceHandle, codePoint);
   }

   public MGlyphProvider() {
      this(getGlyphSourceControlFlowState());
   }

   private MGlyphProvider(int[] controlFlowState) {
      super(MappedClasses.Yx);
      Class[] parameterTypes = new Class[]{int.class};
      Class returnType = MappedClasses.qd;
      boolean methodPublic = true;
      String methodName = GET_GLYPH_METHOD_NAME;
      this.getGlyphMethod = this.Y(methodName, methodPublic, returnType, parameterTypes);
      if (GuiComponent.getLegacyComponentState() == null) {
         setGlyphSourceControlFlowState(new int[3]);
      }
   }

   public static Object getGlyph(MGlyphProvider mapping, Object glyphSourceHandle, int codePoint) {
      return mapping.invokeGetGlyph(glyphSourceHandle, codePoint);
   }

   public static void setGlyphSourceControlFlowState(int[] controlFlowState) {
      glyphSourceControlFlowState = controlFlowState;
   }

   static {
      setGlyphSourceControlFlowState(null);
   }
}
