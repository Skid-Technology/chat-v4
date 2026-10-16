package org.lwjgl.opencl;

public final class CLSampler extends CLObjectChild<CLContext> {
   private static final InfoUtil<CLSampler> util = CLPlatform.getInfoUtilInstance(CLSampler.class, "CL_SAMPLER_UTIL");

   CLSampler(long pointer, CLContext context) {
      super(pointer, context);
      if (this.isValid()) {
         context.getCLSamplerRegistry().registerObject(this);
      }
   }

   public int getInfoInt(int param_name) {
      return util.getInfoInt(this, param_name);
   }

   public long getInfoLong(int param_name) {
      return util.getInfoLong(this, param_name);
   }

   @Override
   int release() {
      int var1;
      try {
         var1 = super.release();
      } finally {
         if (!this.isValid()) {
            this.getParent().getCLSamplerRegistry().unregisterObject(this);
         }
      }

      return var1;
   }
}
