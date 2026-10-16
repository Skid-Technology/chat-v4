package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.List;

public class MRenderGlobal extends Mapping {
   public MappingMethod extractVisibleEntitiesMethod;
   private final MappingMethod loadRenderersMethod = this.Y(ForgeVersion.MC_26_2.d() ? "resetLevelRenderData" : "loadRenderers", true, void.class);
   public MappingMethod updateChunksMethod;
   public MappingMethod renderLevelMethod;
   public MappingMethod updateRenderersMethod;
   public MappingMethod renderEntitiesMethod;

   public MRenderGlobal() {
      super(MappedClasses.zs);
      int rendererControlFlowState = MEntityRenderer.X();
      if (ForgeVersion.MC_1_7_10.L()) {
         this.updateRenderersMethod = this.Y("updateRenderers", true, boolean.class, MappedClasses.zm, boolean.class);
      } else if (ForgeVersion.MC_26_2.d()) {
         this.updateChunksMethod = this.Y("compileSections", true, void.class, MappedClasses.zf);
      } else if (ForgeVersion.MC_1_20_6.d()) {
         this.updateChunksMethod = this.Y("compileSections", true, void.class, MappedClasses.lt);
      } else {
         this.updateChunksMethod = this.Y("updateChunks", true, void.class, long.class);
      }

      if (ForgeVersion.MC_26_2.d()) {
         this.extractVisibleEntitiesMethod = this.Y("submitEntities", true, void.class, MappedClasses.DQ, MappedClasses.z6, MappedClasses.l9);
      } else if (ForgeVersion.MC_1_21_10.d()) {
         this.extractVisibleEntitiesMethod = this.Y(
            "extractVisibleEntities", true, void.class, MappedClasses.lt, MappedClasses.qh, MappedClasses.uy, MappedClasses.z6
         );
      } else if (ForgeVersion.MC_1_21_4.d()) {
         this.renderEntitiesMethod = this.Y(
            "renderEntities", true, void.class, MappedClasses.DQ, MappedClasses.lp, MappedClasses.lt, MappedClasses.uy, List.class
         );
      }

      if (ForgeVersion.MC_1_20_6.d()) {
         if (ForgeVersion.MC_26_2.d()) {
            this.renderLevelMethod = this.Y(
               "render",
               true,
               void.class,
               MappedClasses.Ds,
               MappedClasses.uy,
               boolean.class,
               MappedClasses.zf,
               MappedClasses.ZA,
               MappedClasses.qk,
               MappedClasses.FC,
               boolean.class
            );
         } else if (ForgeVersion.MC_26_1.d()) {
            this.renderLevelMethod = this.Y(
               "renderLevel",
               true,
               void.class,
               MappedClasses.Ds,
               MappedClasses.uy,
               boolean.class,
               MappedClasses.zf,
               MappedClasses.ZA,
               MappedClasses.qk,
               MappedClasses.FC,
               boolean.class,
               MappedClasses.VY
            );
         } else {
            this.renderLevelMethod = this.Y(
               "renderLevel",
               true,
               void.class,
               float.class,
               long.class,
               boolean.class,
               MappedClasses.lt,
               MappedClasses.FW,
               MappedClasses.zH,
               MappedClasses.qr,
               MappedClasses.qr
            );
         }
      } else if (ForgeVersion.MC_1_16_5.d()) {
         this.renderLevelMethod = this.Y(
            "func_228426_a_",
            Wrapper.isNativeAvailable,
            void.class,
            MappedClasses.DQ,
            float.class,
            long.class,
            boolean.class,
            MappedClasses.lt,
            MappedClasses.FW,
            MappedClasses.zH,
            MappedClasses.qr
         );
      }
   }

   public void loadRenderers(Object renderGlobalHandle) {
      this.loadRenderersMethod.invokeVoidNoArgs(renderGlobalHandle);
   }
}
