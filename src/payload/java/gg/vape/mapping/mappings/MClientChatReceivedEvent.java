package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MClientChatReceivedEvent extends Mapping {
   private final MappingMethod n;
   private final MappingField a;

   public Object V(Object object) {
      return this.n.invokeObject(object);
   }

   public MClientChatReceivedEvent() {
      this(MSPacketEntityVelocity.getPacketMappingControlFlowState());
   }

   private MClientChatReceivedEvent(int[] nArray) {
      super(MappedClasses.Zu);
      if (!Wrapper.vapeInstance.isVanillaMinecraftPresent() && !ForgeVersion.MC_1_8_9.Y()) {
         Class[] classArray = new Class[0];
         Class clazz = MappedClasses.Yr;
         boolean bl = Wrapper.isNativeAvailable;
         String string = "func_148915_c";
         this.n = this.Y(string, bl, clazz, classArray);
         Class clazz2 = MappedClasses.Yr;
         boolean bl2 = Wrapper.isNativeAvailable;
         String string2 = "field_148919_a";
         this.a = this.J(string2, bl2, clazz2);
      } else {
         if (ForgeVersion.MC_1_7_10.L()) {
            Class[] classArray = new Class[0];
            Class clazz = MappedClasses.Yr;
            boolean bl = true;
            String string = "func_148915_c";
            this.n = this.Y(string, bl, clazz, classArray);
         } else {
            Class[] classArray = new Class[0];
            Class clazz = MappedClasses.Yr;
            boolean bl = true;
            String string = "getChatComponent";
            this.n = this.Y(string, bl, clazz, classArray);
         }

         Class clazz = MappedClasses.Yr;
         boolean bl = true;
         String string = "chatComponent";
         this.a = this.J(string, bl, clazz);
      }
   }

   public void i(Object object, Object object2) {
      this.a.setObject(object, object2);
   }
}
