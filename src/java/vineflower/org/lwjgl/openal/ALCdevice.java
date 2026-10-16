package org.lwjgl.openal;

import java.util.HashMap;

public final class ALCdevice {
   final long device;
   private boolean valid;
   private final HashMap<Long, ALCcontext> contexts = new HashMap<>();

   ALCdevice(long device) {
      this.device = device;
      this.valid = true;
   }

   @Override
   public boolean equals(Object device) {
      return device instanceof ALCdevice ? ((ALCdevice)device).device == this.device : super.equals(device);
   }

   void addContext(ALCcontext context) {
      synchronized (this.contexts) {
         this.contexts.put(context.context, context);
      }
   }

   void removeContext(ALCcontext context) {
      synchronized (this.contexts) {
         this.contexts.remove(context.context);
      }
   }

   void setInvalid() {
      this.valid = false;
      synchronized (this.contexts) {
         for (ALCcontext context : this.contexts.values()) {
            context.setInvalid();
         }
      }

      this.contexts.clear();
   }

   public boolean isValid() {
      return this.valid;
   }
}
