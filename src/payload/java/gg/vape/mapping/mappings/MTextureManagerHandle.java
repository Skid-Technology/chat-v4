package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MTextureManagerHandle extends Mapping {
   private final MappingMethod getDepthTextureMethod;
   private final MappingMethod getColorTextureMethod;
   private static boolean renderTargetControlFlowState;

   public static boolean getRenderTargetControlFlowState() {
      return renderTargetControlFlowState;
   }

   public Object getDepthTexture(Object renderTarget) {
      return this.getDepthTextureMethod.invokeObject(renderTarget);
   }

   public static void setRenderTargetControlFlowState(boolean state) {
      renderTargetControlFlowState = state;
   }

   public static boolean getDisabledControlFlowState() {
      boolean bl = getRenderTargetControlFlowState();
      return false;
   }

   public Object getColorTexture(Object renderTarget) {
      return this.getColorTextureMethod.invokeObject(renderTarget);
   }

   public MTextureManagerHandle() {
      this(getRenderTargetControlFlowState());
   }

   private MTextureManagerHandle(boolean bl) {
      super(MappedClasses.DA);
      Class[] classArray = new Class[0];
      Class clazz = MappedClasses.GPU_TEXTURE;
      boolean bl2 = true;
      String string = "getColorTexture";
      this.getColorTextureMethod = this.Y(string, bl2, clazz, classArray);
      Class[] classArray2 = new Class[0];
      Class clazz2 = MappedClasses.GPU_TEXTURE;
      boolean bl4 = true;
      String string2 = "getDepthTexture";
      this.getDepthTextureMethod = this.Y(string2, bl4, clazz2, classArray2);
   }

   static {
      setRenderTargetControlFlowState(true);
   }
}
