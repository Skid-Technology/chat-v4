package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;

public class MRenderBlocks extends Mapping {
   public final MappingMethod renderBlockByRenderTypeMethod;
   private final MappingField renderAllFacesField = this.J("renderAllFaces", true, boolean.class);
   public final MappingMethod renderStandardBlockMethod;
   public final MappingMethod renderFaceZNegMethod;
   public final MappingMethod renderStandardBlockWithColorMultiplierMethod;
   public final MappingMethod renderFaceYPosMethod;
   public final MappingMethod renderFaceYNegMethod;
   public final MappingMethod renderFaceXPosMethod;
   public final MappingMethod renderFaceZPosMethod;
   public final MappingMethod renderFaceXNegMethod;

   public MRenderBlocks() {
      this(MEntityRenderer.X());
   }

   private MRenderBlocks(int rendererControlFlowState) {
      super(MappedClasses.q5);
      this.renderBlockByRenderTypeMethod = this.Y("renderBlockByRenderType", true, boolean.class, MappedClasses.Zk, int.class, int.class, int.class);
      this.renderStandardBlockMethod = this.Y("renderStandardBlock", true, boolean.class, MappedClasses.Zk, int.class, int.class, int.class);
      this.renderStandardBlockWithColorMultiplierMethod = this.Y(
         "renderStandardBlockWithColorMultiplier",
         true,
         boolean.class,
         MappedClasses.Zk,
         int.class,
         int.class,
         int.class,
         float.class,
         float.class,
         float.class
      );
      this.renderFaceXNegMethod = this.Y("renderFaceXNeg", true, void.class, MappedClasses.Zk, double.class, double.class, double.class, MappedClasses.Z9);
      this.renderFaceXPosMethod = this.Y("renderFaceXPos", true, void.class, MappedClasses.Zk, double.class, double.class, double.class, MappedClasses.Z9);
      this.renderFaceYNegMethod = this.Y("renderFaceYNeg", true, void.class, MappedClasses.Zk, double.class, double.class, double.class, MappedClasses.Z9);
      this.renderFaceYPosMethod = this.Y("renderFaceYPos", true, void.class, MappedClasses.Zk, double.class, double.class, double.class, MappedClasses.Z9);
      this.renderFaceZNegMethod = this.Y("renderFaceZNeg", true, void.class, MappedClasses.Zk, double.class, double.class, double.class, MappedClasses.Z9);
      this.renderFaceZPosMethod = this.Y("renderFaceZPos", true, void.class, MappedClasses.Zk, double.class, double.class, double.class, MappedClasses.Z9);
      if (rendererControlFlowState != 0) {
         GuiComponent.setLegacyComponentState(new GuiComponent[3]);
      }
   }

   public boolean renderStandardBlockWithColorMultiplier(Object renderBlocksHandle, Object blockHandle, int x, int y, int z, float red, float green, float blue) {
      return this.renderStandardBlockWithColorMultiplierMethod.invokeBoolean(renderBlocksHandle, blockHandle, x, y, z, red, green, blue);
   }

   public void setRenderAllFaces(Object renderBlocksHandle, boolean renderAllFaces) {
      this.renderAllFacesField.setBoolean(renderBlocksHandle, renderAllFaces);
   }
}
