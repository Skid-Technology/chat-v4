package org.lwjgl.opencl;

import org.lwjgl.PointerBuffer;

public final class CLCommandQueue extends CLObjectChild<CLContext> {
   private static final InfoUtil<CLCommandQueue> util = CLPlatform.getInfoUtilInstance(CLCommandQueue.class, "CL_COMMAND_QUEUE_UTIL");
   private final CLDevice device;
   private final CLObjectRegistry<CLEvent> clEvents;

   CLCommandQueue(long pointer, CLContext context, CLDevice device) {
      super(pointer, context);
      if (this.isValid()) {
         this.device = device;
         this.clEvents = new CLObjectRegistry<>();
         context.getCLCommandQueueRegistry().registerObject(this);
      } else {
         this.device = null;
         this.clEvents = null;
      }
   }

   public CLDevice getCLDevice() {
      return this.device;
   }

   public CLEvent getCLEvent(long id) {
      return this.clEvents.getObject(id);
   }

   public int getInfoInt(int param_name) {
      return util.getInfoInt(this, param_name);
   }

   CLObjectRegistry<CLEvent> getCLEventRegistry() {
      return this.clEvents;
   }

   void registerCLEvent(PointerBuffer event) {
      if (event != null) {
         new CLEvent(event.get(event.position()), this);
      }
   }

   @Override
   int release() {
      int var1;
      try {
         var1 = super.release();
      } finally {
         if (!this.isValid()) {
            this.getParent().getCLCommandQueueRegistry().unregisterObject(this);
         }
      }

      return var1;
   }
}
