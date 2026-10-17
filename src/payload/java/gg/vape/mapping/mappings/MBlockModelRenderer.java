package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MBlockModelRenderer extends Mapping {
   public MappingMethod renderModelAmbientOcclusionMethod;
   public MappingMethod renderModelMethod;

   public boolean renderModelAmbientOcclusion(
      Object renderer,
      Object blockAccess,
      Object model,
      Object block,
      Object blockState,
      Object blockPosition,
      Object buffer,
      boolean checkSides,
      long positionSeed
   ) {
      if (ForgeVersion.MC_1_12_2.d()) {
         return this.renderModelAmbientOcclusionMethod.invokeBoolean(renderer, blockAccess, model, blockState, blockPosition, buffer, checkSides, positionSeed);
      } else {
         return Wrapper.isNativeAvailable
            ? this.renderModelAmbientOcclusionMethod.invokeBoolean(renderer, blockAccess, model, block, blockPosition, buffer, checkSides)
            : this.renderModelAmbientOcclusionMethod.invokeBoolean(renderer, blockAccess, model, block, blockPosition, buffer, checkSides);
      }
   }

   public MBlockModelRenderer() {
      this(MEntityRenderer.X());
   }

   private MBlockModelRenderer(int controlFlowState) {
      super(MappedClasses.VU);
      if (ForgeVersion.MC_1_12_2.d()) {
         if (ForgeVersion.MC_1_16_5.v()) {
            this.renderModelMethod = this.Y(
               "func_187493_a",
               Wrapper.isNativeAvailable,
               boolean.class,
               MappedClasses.zR,
               MappedClasses.lc,
               MappedClasses.Vv,
               MappedClasses.lf,
               MappedClasses.lX,
               boolean.class,
               long.class
            );
            this.renderModelAmbientOcclusionMethod = this.Y(
               "func_187498_b",
               Wrapper.isNativeAvailable,
               boolean.class,
               MappedClasses.zR,
               MappedClasses.lc,
               MappedClasses.Vv,
               MappedClasses.lf,
               MappedClasses.lX,
               boolean.class,
               long.class
            );
         }
      } else {
         if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.renderModelMethod = this.Y(
               "renderModel", true, boolean.class, MappedClasses.zR, MappedClasses.lc, MappedClasses.Vv, MappedClasses.lf, MappedClasses.lX, boolean.class
            );
         } else {
            this.renderModelMethod = this.Y(
               "func_178267_a",
               Wrapper.isNativeAvailable,
               boolean.class,
               MappedClasses.zR,
               MappedClasses.lc,
               MappedClasses.Vv,
               MappedClasses.lf,
               MappedClasses.lX,
               boolean.class
            );
         }

         if (Wrapper.isNativeAvailable && !Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.renderModelAmbientOcclusionMethod = this.Y(
               "a", false, boolean.class, MappedClasses.zR, MappedClasses.lc, MappedClasses.Zk, MappedClasses.lf, MappedClasses.lX, boolean.class
            );
         } else {
            this.renderModelAmbientOcclusionMethod = this.Y(
               "renderModelAmbientOcclusion",
               true,
               boolean.class,
               MappedClasses.zR,
               MappedClasses.lc,
               MappedClasses.Zk,
               MappedClasses.lf,
               MappedClasses.lX,
               boolean.class
            );
         }
      }
   }
}
