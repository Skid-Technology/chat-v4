package gg.vape.asm.transform.impl;

import gg.vape.Vape;
import gg.vape.asm.helper.IndexedLocal;
import gg.vape.asm.transform.ClassTransformer;
import gg.vape.event.impl.EventPostAttack;
import gg.vape.event.impl.EventPreAttack;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.PlayerControllerMPEventMappingTask;

public class PlayerControllerMPTransformer extends ClassTransformer {
   private static int opaqueState;

   public static int getOpaqueState() {
      return opaqueState;
   }

   public PlayerControllerMPTransformer() {
      super(MappedClasses.ld);
   }

   public static void setOpaqueState(int state) {
      opaqueState = state;
   }

   @Override
   public void transform() {
      if (PlayerControllerMPEventMappingTask.U) {
         MappingMethod mappingMethod = Vape.INSTANCE.getMappings().hj.i;
         this.injectEventAtEntry(mappingMethod, EventPreAttack.class, new IndexedLocal(2).setDescriptorClass(Object.class));
         this.injectEventAtExit(mappingMethod, EventPostAttack.class, new IndexedLocal(2).setDescriptorClass(Object.class));
      }
   }

   public static int opaquePredicate() {
      int state = getOpaqueState();
      return state == 0 ? 52 : 0;
   }

   static {
      if (opaquePredicate() != 0) {
         setOpaqueState(19);
      }
   }
}
