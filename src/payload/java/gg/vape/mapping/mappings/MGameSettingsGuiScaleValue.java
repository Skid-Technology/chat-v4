package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MGameSettingsGuiScaleValue extends Mapping {
   private static final String ANTIALIASING_LEVEL_FIELD = "antialiasingLevel";
   private static String controlFlowMarker;
   private final MappingField antialiasingLevelField = this.registerStaticField(ANTIALIASING_LEVEL_FIELD, false, int.class);

   public int getAntialiasingLevel() {
      return this.antialiasingLevelField.hasResolutionFailed() ? 1 : this.antialiasingLevelField.getInt(null);
   }

   public static String getControlFlowMarker() {
      return controlFlowMarker;
   }

   public MGameSettingsGuiScaleValue() {
      this(getControlFlowMarker());
   }

   private MGameSettingsGuiScaleValue(String marker) {
      super(MappedClasses.Ym);
   }

   public static void setControlFlowMarker(String marker) {
      controlFlowMarker = marker;
   }

   static {
      setControlFlowMarker("x0yBJc");
   }
}
