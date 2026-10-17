package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class MSlot extends Mapping {
   private final MappingField inventoryOrContainerField;
   private final MappingField slotNumberField;
   private static int[] slotControlFlowState;
   private MappingField slotIndexField;
   private final MappingMethod getStackMethod;

   public Object getStack(Object slot) {
      return this.getStackMethod.invokeObject(slot);
   }

   public static int[] getSlotControlFlowState() {
      return slotControlFlowState;
   }

   public static int getSlotIndex(MSlot mapping, Object slot) {
      return mapping.getSlotIndex(slot);
   }

   private int getSlotIndex(Object slot) {
      return this.slotIndexField.getInt(slot);
   }

   public static void setSlotControlFlowState(int[] state) {
      slotControlFlowState = state;
   }

   private int getSlotNumber(Object slot) {
      return this.slotNumberField.getInt(slot);
   }

   public MSlot() {
      super(MappedClasses.YQ);
      Class[] classArray = new Class[0];
      Class clazz = MappedClasses.VK;
      boolean bl = true;
      String string = "getStack";
      this.getStackMethod = this.Y(string, bl, clazz, classArray);
      Class<Integer> clazz2 = int.class;
      String string2 = "slotNumber";
      this.slotNumberField = this.fieldBuilder(string2, clazz2).buildField();
      Class clazz3 = MappedClasses.l0;
      String string3 = "inventory";
      this.inventoryOrContainerField = this.fieldBuilder(string3, clazz3).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "container").buildField();
      Class<Integer> clazz4 = int.class;
      String string4 = "slotIndex";
      this.slotIndexField = this.fieldBuilder(string4, clazz4).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "index").buildField();
   }

   public static int getSlotNumber(MSlot mapping, Object slot) {
      return mapping.getSlotNumber(slot);
   }

   public static Object getInventoryOrContainer(MSlot mapping, Object slot) {
      return mapping.getInventoryOrContainer(slot);
   }

   private Object getInventoryOrContainer(Object slot) {
      return this.inventoryOrContainerField.getObject(slot);
   }

   static {
      setSlotControlFlowState(null);
   }
}
