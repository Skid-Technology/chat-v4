package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.List;

public class MContainer extends Mapping {
   private MappingMethod getCarriedMethod;
   private final MappingField windowIdField;
   private final MappingMethod getSlotMethod;
   private final MappingField slotsField;

   private Object getCarried(Object container) {
      return this.getCarriedMethod.invokeObject(container);
   }

   public static Object getCarried(MContainer mapping, Object container) {
      return mapping.getCarried(container);
   }

   public List getSlots(Object container) {
      return (List)this.slotsField.getObject(container);
   }

   private Object getSlot(Object container, int slotIndex) {
      return this.getSlotMethod.invokeObject(container, slotIndex);
   }

   public int getWindowId(Object container) {
      return this.windowIdField.getInt(container);
   }

   public static Object getSlot(MContainer mapping, Object container, int slotIndex) {
      return mapping.getSlot(container, slotIndex);
   }

   public MContainer() {
      this(MSlot.getSlotControlFlowState());
   }

   private MContainer(int[] nArray) {
      super(MappedClasses.X);
      if (nArray != null) {
         Class<List> clazz = List.class;
         boolean bl = true;
         String string = "inventorySlots";
         this.slotsField = this.J(string, bl, clazz);
         Class<Integer> clazz2 = int.class;
         boolean bl2 = true;
         String string2 = "windowId";
         this.windowIdField = this.J(string2, bl2, clazz2);
         Class[] classArray = new Class[]{int.class};
         Class clazz3 = MappedClasses.YQ;
         boolean bl3 = true;
         String string3 = "getSlot";
         this.getSlotMethod = this.Y(string3, bl3, clazz3, classArray);
      } else {
         if (ForgeVersion.MC_1_17.d()) {
            Class clazz = MappedClasses.Vd;
            boolean bl = true;
            String string = "slots";
            this.slotsField = this.J(string, bl, clazz);
            Class[] classArray = new Class[0];
            Class clazz4 = MappedClasses.VK;
            boolean bl4 = true;
            String string4 = "getCarried";
            this.getCarriedMethod = this.Y(string4, bl4, clazz4, classArray);
         } else {
            Class<List> clazz = List.class;
            boolean bl = true;
            String string = "inventorySlots";
            this.slotsField = this.J(string, bl, clazz);
         }

         Class<Integer> clazz = int.class;
         boolean bl = true;
         String string = "windowId";
         this.windowIdField = this.J(string, bl, clazz);
         Class[] classArray = new Class[]{int.class};
         Class clazz5 = MappedClasses.YQ;
         boolean bl5 = true;
         String string5 = "getSlot";
         this.getSlotMethod = this.Y(string5, bl5, clazz5, classArray);
      }
   }
}
