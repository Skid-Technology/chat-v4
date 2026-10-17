package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MVisGraph extends Mapping {
   private static String controlFlowMarker;
   public MappingMethod computeVisibilityMethod;
   public MappingMethod setOpaqueCubeMethod;

   public static String getVisGraphControlFlowMarker() {
      return controlFlowMarker;
   }

   public static void setVisGraphControlFlowMarker(String marker) {
      controlFlowMarker = marker;
   }

   public MVisGraph() {
      this(getVisGraphControlFlowMarker());
   }

   private MVisGraph(String controlFlowMarker) {
      super(MappedClasses.VIS_GRAPH);
      if (controlFlowMarker != null) {
         Class[] parameterTypes = new Class[0];
         Class returnType = MappedClasses.SET_VISIBILITY;
         boolean remap = true;
         String methodName = "computeVisibility";
         this.computeVisibilityMethod = this.Y(methodName, remap, returnType, parameterTypes);
         if (ForgeVersion.MC_1_12_2.d()) {
            Class[] opaqueCubeParameterTypes = new Class[]{MappedClasses.lf};
            Class<Void> opaqueCubeReturnType = void.class;
            boolean remapOpaqueCubeMethod = true;
            String opaqueCubeMethodName = "setOpaqueCube";
            this.setOpaqueCubeMethod = this.Y(opaqueCubeMethodName, remapOpaqueCubeMethod, opaqueCubeReturnType, opaqueCubeParameterTypes);
         } else if (ForgeVersion.MC_1_7_10.Y()) {
            Class[] opaqueCubeParameterTypes = new Class[]{MappedClasses.lf};
            Class<Void> opaqueCubeReturnType = void.class;
            boolean remapOpaqueCubeMethod = Wrapper.isNativeAvailable;
            String opaqueCubeMethodName = "func_178606_a";
            this.setOpaqueCubeMethod = this.Y(opaqueCubeMethodName, remapOpaqueCubeMethod, opaqueCubeReturnType, opaqueCubeParameterTypes);
         }

         if (GuiComponent.getLegacyComponentState() == null) {
            setVisGraphControlFlowMarker("K5b3Uc");
         }
      } else {
         Class[] parameterTypesx = new Class[0];
         Class returnTypex = MappedClasses.SET_VISIBILITY;
         boolean remapx = true;
         String methodNamex = "computeVisibility";
         this.computeVisibilityMethod = this.Y(methodNamex, remapx, returnTypex, parameterTypesx);
         if (ForgeVersion.MC_1_12_2.d()) {
            Class[] opaqueCubeParameterTypes = new Class[]{MappedClasses.lf};
            Class<Void> opaqueCubeReturnType = void.class;
            boolean remapOpaqueCubeMethod = Wrapper.isNativeAvailable;
            String opaqueCubeMethodName = "func_178606_a";
            this.setOpaqueCubeMethod = this.Y(opaqueCubeMethodName, remapOpaqueCubeMethod, opaqueCubeReturnType, opaqueCubeParameterTypes);
         }

         if (GuiComponent.getLegacyComponentState() == null) {
            setVisGraphControlFlowMarker("K5b3Uc");
         }
      }
   }

   static {
      setVisGraphControlFlowMarker("qWXvyc");
   }
}
