package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MRayTraceContext_BlockMode;
import gg.vape.wrapper.Wrapper;

public class RayTraceContext$FluidMode extends Wrapper {
   public static RayTraceContext$FluidMode visual() {
      return new RayTraceContext$FluidMode(MRayTraceContext_BlockMode.getVisual(vapeInstance.getMappingsMapperCompat().rayTraceBlockMode));
   }

   public RayTraceContext$FluidMode(Object handle) {
      super(handle);
   }

   public static RayTraceContext$FluidMode outline() {
      return new RayTraceContext$FluidMode(MRayTraceContext_BlockMode.getOutline(vapeInstance.getMappingsMapperCompat().rayTraceBlockMode));
   }

   public static RayTraceContext$FluidMode collider() {
      return new RayTraceContext$FluidMode(MRayTraceContext_BlockMode.getCollider(vapeInstance.getMappingsMapperCompat().rayTraceBlockMode));
   }
}
