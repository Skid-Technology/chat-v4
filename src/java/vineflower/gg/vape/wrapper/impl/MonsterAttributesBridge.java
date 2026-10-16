package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class MonsterAttributesBridge extends Wrapper {
   public MonsterAttributesBridge(Object object) {
      super(object);
   }

   public static EquipmentSlotGroup K() {
      if (ForgeVersion.MC_1_20_6.d()) {
         Holder holder = new Holder(vapeInstance.getMappings().qr.W());
         return new EquipmentSlotGroup(holder.N());
      } else {
         return ForgeVersion.MC_1_16_5.d() ? new EquipmentSlotGroup(vapeInstance.getMappings().qr.W()) : null;
      }
   }

   public static EquipmentSlotGroup B() {
      if (ForgeVersion.MC_1_20_6.d()) {
         Holder holder = new Holder(vapeInstance.getMappings().qr.N());
         return new EquipmentSlotGroup(holder.N());
      } else {
         return new EquipmentSlotGroup(vapeInstance.getMappings().qr.N());
      }
   }

   public static EquipmentSlotGroup t() {
      if (ForgeVersion.MC_1_20_6.d()) {
         Holder holder = new Holder(vapeInstance.getMappings().qr.w());
         return new EquipmentSlotGroup(holder.N());
      } else {
         return new EquipmentSlotGroup(vapeInstance.getMappings().qr.w());
      }
   }

   public static EquipmentSlotGroup q() {
      if (ForgeVersion.MC_1_20_6.d()) {
         Holder holder = new Holder(vapeInstance.getMappings().qr.o());
         return new EquipmentSlotGroup(holder.N());
      } else {
         throw new UnsupportedOperationException("Attribute unavailable in this version");
      }
   }

   public static Holder m$src$Lgg_vape_wrapper_impl_Holder_$1lgjxui() {
      if (ForgeVersion.MC_1_20_6.d()) {
         return new Holder(vapeInstance.getMappings().qr.J());
      } else {
         throw new UnsupportedOperationException("Holder class unavailable in this version");
      }
   }

   public static EquipmentSlotGroup H() {
      if (ForgeVersion.MC_1_20_6.d()) {
         Holder holder = new Holder(vapeInstance.getMappings().qr.J());
         return new EquipmentSlotGroup(holder.N());
      } else {
         return ForgeVersion.MC_1_16_5.d() ? new EquipmentSlotGroup(vapeInstance.getMappings().qr.J()) : null;
      }
   }

   public static Holder L() {
      if (ForgeVersion.MC_1_20_6.d()) {
         return new Holder(vapeInstance.getMappings().qr.W());
      } else {
         throw new UnsupportedOperationException("Holder class unavailable in this version");
      }
   }

   private static UnsupportedOperationException a(UnsupportedOperationException unsupportedOperationException) {
      return unsupportedOperationException;
   }

   public static Holder g() {
      if (ForgeVersion.MC_1_20_6.d()) {
         return new Holder(vapeInstance.getMappings().qr.o());
      } else {
         throw new UnsupportedOperationException("Holder class unavailable in this version");
      }
   }

   public static Holder D() {
      if (ForgeVersion.MC_1_20_6.d()) {
         return new Holder(vapeInstance.getMappings().qr.y());
      } else {
         throw new UnsupportedOperationException("Holder class unavailable in this version");
      }
   }

   public static EquipmentSlotGroup c() {
      if (ForgeVersion.MC_1_20_6.d()) {
         Holder holder = new Holder(vapeInstance.getMappings().qr.y());
         return new EquipmentSlotGroup(holder.N());
      } else {
         throw new UnsupportedOperationException("Attribute unavailable in this version");
      }
   }

   public static Holder V() {
      if (ForgeVersion.MC_1_20_6.d()) {
         return new Holder(vapeInstance.getMappings().qr.w());
      } else {
         throw new UnsupportedOperationException("Holder class unavailable in this version");
      }
   }

   public static Holder U() {
      if (ForgeVersion.MC_1_20_6.d()) {
         return new Holder(vapeInstance.getMappings().qr.N());
      } else {
         throw new UnsupportedOperationException("Holder class unavailable in this version");
      }
   }
}
