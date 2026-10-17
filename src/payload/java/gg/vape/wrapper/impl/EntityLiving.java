package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEntityLiving;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class EntityLiving extends EntityLivingBase {
   public Map T$src$Ljava_util_Map_$f5d6t2() {
      return MEntityLiving.l(vapeInstance.getMappings().W, this.I);
   }

   public Iterable V$src$Ljava_lang_Iterable_$149sooa() {
      return (Iterable)MEntityLiving.F(vapeInstance.getMappings().W, this.I);
   }

   public ItemStack d(int n) {
      if (ForgeVersion.MC_1_16_5.d()) {
         EquipmentSlotSet equipmentSlotSet = EquipmentSlotSet.T(n);
         return equipmentSlotSet == null ? null : new ItemStack(MEntityLiving.L(vapeInstance.getMappings().W, this.I, equipmentSlotSet.getObject()));
      } else {
         return new ItemStack(MEntityLiving.v(vapeInstance.getMappings().W, this.I, n));
      }
   }

   public ArrayList<ItemStack> I$src$Ljava_util_ArrayList_$15zosdi() {
      ArrayList<ItemStack> arrayList = new ArrayList<>();
      if (ForgeVersion.MC_1_8_9.Y()) {
         if (ForgeVersion.MC_1_21_6.v()) {
            Iterator iterator = this.V$src$Ljava_lang_Iterable_$149sooa().iterator();

            while (iterator.hasNext()) {
               arrayList.add(new ItemStack(iterator.next()));
            }
         } else {
            for (Object e : InventoryListBridge.armor().getSlots()) {
               EquipmentSlotSet equipmentSlotSet = new EquipmentSlotSet(e);
               ItemStack itemStack;
               if (equipmentSlotSet.y().equals(MappedFieldSingletonWrapper.humanoidArmor())
                  && (itemStack = new ItemStack(MEntityLiving.L(vapeInstance.getMappings().W, this.I, equipmentSlotSet.getObject()))).isNotNull()
                  && !itemStack.r()) {
                  arrayList.add(itemStack);
               }
            }
         }
      } else {
         for (int i = 1; i <= 4; i++) {
            arrayList.add(this.d(i));
         }
      }

      return arrayList;
   }

   public boolean F$src$Z$3efut5() {
      return MEntityLiving.S(vapeInstance.getMappings().W, this.I);
   }

   public EntityLiving(Object object) {
      super(object);
   }

   public void C(boolean bl) {
      MEntityLiving.b(vapeInstance.getMappings().W, this.I, bl);
   }
}
