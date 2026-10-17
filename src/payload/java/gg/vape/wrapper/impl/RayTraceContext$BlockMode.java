package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MRayTraceContext_FluidMode;
import gg.vape.wrapper.Wrapper;

public class RayTraceContext$BlockMode extends Wrapper {
   public RayTraceContext$BlockMode(Object handle) {
      super(handle);
   }

   public static RayTraceContext$BlockMode any() {
      return new RayTraceContext$BlockMode(MRayTraceContext_FluidMode.getAny(vapeInstance.getMappingsMapperCompat().rayTraceFluidMode));
   }

   public static RayTraceContext$BlockMode sourceOnly() {
      return new RayTraceContext$BlockMode(MRayTraceContext_FluidMode.getSourceOnly(vapeInstance.getMappingsMapperCompat().rayTraceFluidMode));
   }

   public static RayTraceContext$BlockMode none() {
      return new RayTraceContext$BlockMode(MRayTraceContext_FluidMode.getNone(vapeInstance.getMappingsMapperCompat().rayTraceFluidMode));
   }
}
