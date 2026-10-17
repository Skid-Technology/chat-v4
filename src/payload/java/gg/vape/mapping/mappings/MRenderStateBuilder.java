package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;

public class MRenderStateBuilder extends Mapping {
   private static final String DRAW_WITH_SHADER = "drawWithShader";
   private final MappingMethod drawWithShaderMethod = this.registerStaticMethod("drawWithShader", true, void.class, MappedClasses.qE);

   public MRenderStateBuilder() {
      this(MEnumHandBridge.s());
   }

   private MRenderStateBuilder(int controlFlowState) {
      super(MappedClasses.ug);
      if (controlFlowState != 0) {
         GuiComponent.setLegacyComponentState(new GuiComponent[1]);
      }
   }

   public void drawWithShader(Object renderStateHandle) {
      this.drawWithShaderMethod.invokeVoid(null, renderStateHandle);
   }
}
