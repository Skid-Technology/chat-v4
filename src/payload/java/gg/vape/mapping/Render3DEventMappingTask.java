package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.wrapper.impl.ForgeVersion;

public class Render3DEventMappingTask extends JavassistMappingTask {
   private static final String c = "$1";

   public Render3DEventMappingTask() {
      super(MappedClasses.zs);
   }

   @Override
   public void transform() {
      MappingMethod mappingMethod = Vape.INSTANCE.getMappings().renderGlobal.renderLevelMethod;
      this.k(mappingMethod, EventRender3DCallback.class, ForgeVersion.MC_26_2.d() ? "$5" : "$1");
   }
}
