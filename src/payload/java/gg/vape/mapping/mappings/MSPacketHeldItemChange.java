package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MSPacketHeldItemChange extends Mapping {
   private final MappingMethod closeWindowPacketConstructor = this.Y("<init>", false, void.class, int.class);

   public MSPacketHeldItemChange() {
      super(MappedClasses.l7);
   }

   public Object createCloseWindowPacket(int windowId) {
      return this.closeWindowPacketConstructor.newInstance(windowId);
   }
}
