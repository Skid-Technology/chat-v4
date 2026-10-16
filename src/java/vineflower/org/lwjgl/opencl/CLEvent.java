package org.lwjgl.opencl;

public final class CLEvent extends CLObjectChild<CLContext> {
   private static final CLEvent.CLEventUtil util = (CLEvent.CLEventUtil)CLPlatform.<CLEvent>getInfoUtilInstance(CLEvent.class, "CL_EVENT_UTIL");
   private final CLCommandQueue queue;

   CLEvent(long pointer, CLContext context) {
      this(pointer, context, null);
   }

   CLEvent(long pointer, CLCommandQueue queue) {
      this(pointer, queue.getParent(), queue);
   }

   CLEvent(long pointer, CLContext context, CLCommandQueue queue) {
      super(pointer, context);
      if (this.isValid()) {
         this.queue = queue;
         if (queue == null) {
            context.getCLEventRegistry().registerObject(this);
         } else {
            queue.getCLEventRegistry().registerObject(this);
         }
      } else {
         this.queue = null;
      }
   }

   public CLCommandQueue getCLCommandQueue() {
      return this.queue;
   }

   public int getInfoInt(int param_name) {
      return util.getInfoInt(this, param_name);
   }

   public long getProfilingInfoLong(int param_name) {
      return util.getProfilingInfoLong(this, param_name);
   }

   CLObjectRegistry<CLEvent> getParentRegistry() {
      return this.queue == null ? this.getParent().getCLEventRegistry() : this.queue.getCLEventRegistry();
   }

   @Override
   int release() {
      int var1;
      try {
         var1 = super.release();
      } finally {
         if (!this.isValid()) {
            if (this.queue == null) {
               this.getParent().getCLEventRegistry().unregisterObject(this);
            } else {
               this.queue.getCLEventRegistry().unregisterObject(this);
            }
         }
      }

      return var1;
   }

   interface CLEventUtil extends InfoUtil<CLEvent> {
      long getProfilingInfoLong(CLEvent var1, int var2);
   }
}
