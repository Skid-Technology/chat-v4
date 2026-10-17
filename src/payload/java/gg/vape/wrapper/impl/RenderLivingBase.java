package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MRenderLivingBase;
import java.nio.FloatBuffer;
import java.util.List;

public class RenderLivingBase<T extends EntityLivingBase> extends Render<T> {
   public ModelBipedSkeletonBridge getMainModel() {
      return new ModelBipedSkeletonBridge(MRenderLivingBase.x(vapeInstance.getMappingsMapperCompat().CP, this.I));
   }

   public List<Object> getLayerRenderers() {
      return (List<Object>)MRenderLivingBase.i(vapeInstance.getMappingsMapperCompat().CP, this.I);
   }

   public RenderLivingBase(Object object) {
      super(object);
   }

   public void setLayerRenderers(List list) {
      MRenderLivingBase.O(vapeInstance.getMappingsMapperCompat().CP, this.I, list);
   }

   public FloatBuffer P() {
      return MRenderLivingBase.y(vapeInstance.getMappingsMapperCompat().CP, this.I);
   }
}
