package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.wrapper.impl.ForgeVersion;

public class MMouseEvent extends Mapping {
   public MappingField Q;
   public MappingField Z;

   public MMouseEvent() {
      this(MEntityRenderer.n());
   }

   private MMouseEvent(int n) {
      super(MappedClasses.U);
      if (n != 0) {
         if (ForgeVersion.MC_1_17.d()) {
            Class<Integer> clazz = int.class;
            boolean bl = true;
            String string = "state";
            this.Q = this.J(string, bl, clazz);
            Class<Boolean> clazz2 = boolean.class;
            boolean bl2 = true;
            String string2 = "enabled";
            this.Z = this.J(string2, bl2, clazz2);
         } else {
            Class<Integer> clazz = int.class;
            boolean bl = true;
            String string = "capability";
            this.Q = this.J(string, bl, clazz);
            Class<Boolean> clazz3 = boolean.class;
            boolean bl3 = true;
            String string3 = "currentState";
            this.Z = this.J(string3, bl3, clazz3);
         }
      } else {
         Class<Boolean> clazz = boolean.class;
         boolean bl = true;
         String string = "currentState";
         this.Z = this.J(string, bl, clazz);
      }
   }

   public int n(Object object) {
      return this.Q.getInt(object);
   }

   public boolean J(Object object) {
      return this.Z.getBoolean(object);
   }

   public void T(Object object, boolean bl) {
      this.Z.setBoolean(object, bl);
   }
}
