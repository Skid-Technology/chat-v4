package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class MEntityEquipmentSlot extends Mapping {
   private MappingMethod a;
   private final MappingField W;
   private final MappingField c;

   public static Object Q(MEntityEquipmentSlot mEntityEquipmentSlot) {
      return mEntityEquipmentSlot.R();
   }

   private boolean N(Object object) {
      return this.a.invokeBoolean(object);
   }

   public MEntityEquipmentSlot() {
      this(MSlot.getSlotControlFlowState());
   }

   private MEntityEquipmentSlot(int[] nArray) {
      super(MappedClasses.FY);
      if (nArray != null) {
         Class<Integer> clazz = int.class;
         boolean bl = true;
         String string = "index";
         this.c = this.J(string, bl, clazz);
         Class[] classArray = new Class[0];
         Class clazz2 = MappedClasses.FY;
         boolean bl2 = true;
         String string2 = "MAINHAND";
         this.a = this.Y(string2, bl2, clazz2, classArray);
         this.W = null;
      } else {
         Class<Integer> clazz = int.class;
         boolean bl = true;
         String string = "index";
         this.c = this.J(string, bl, clazz);
         Class clazz3 = MappedClasses.FY;
         boolean bl3 = true;
         String string3 = "MAINHAND";
         this.W = this.registerStaticField(string3, bl3, clazz3);
         if (ForgeVersion.MC_1_21_6.d()) {
            Class[] classArray = new Class[0];
            Class<Boolean> clazz4 = boolean.class;
            boolean bl4 = true;
            String string4 = "isArmor";
            this.a = this.Y(string4, bl4, clazz4, classArray);
         }
      }
   }

   private Object R() {
      return this.W.getObject(null);
   }

   public static boolean S(MEntityEquipmentSlot mEntityEquipmentSlot, Object object) {
      return mEntityEquipmentSlot.N(object);
   }

   public static int c(MEntityEquipmentSlot mEntityEquipmentSlot, Object object) {
      return mEntityEquipmentSlot.m(object);
   }

   private int m(Object object) {
      return this.c.getInt(object);
   }
}
