package gg.vape.utils;

import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EnumHand;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemArmor;
import gg.vape.wrapper.impl.ItemStack;
import java.util.Arrays;

public class ItemStackFingerprint {
   private static final long I = -7046029254386353131L;

   public static long t(ItemStack[] itemStackArray) {
      long l = -7046029254386353131L;

      for (ItemStack itemStack : itemStackArray) {
         if (itemStack != null) {
            long l2 = t(itemStack);
            l ^= l2 + -7046029254386353131L + (l << 6) + (l >> 2);
         }
      }

      return l;
   }

   public static long t(ItemStack itemStack) {
      Item item = itemStack.getItem();
      int n2 = item.P();
      int n3 = itemStack.L();
      int[] nArray = EnchantmentUtil.e(itemStack);
      long l = d(n2, n3, nArray);
      int n4 = item.a();
      boolean bl = n4 > 0 && n3 > 0;
      int n;
      if (ItemStackScoreUtil.R(item) && ForgeVersion.MC_1_8_9.B() && (n = new ItemArmor(item).Y(itemStack)) != -1) {
         l = l * 31L + n;
      }

      return l * 31L + (bl ? 1 : 0);
   }

   public static int h(int n, int n2) {
      if (n < 0 || n >= 65536) {
         throw new IllegalArgumentException("enchantId must be between 0 and 65535");
      } else if (n2 >= 0 && n2 < 65536) {
         return (n & 65535) << 16 | n2 & 65535;
      } else {
         throw new IllegalArgumentException("enchantLevel must be between 0 and 65535");
      }
   }

   public static int d(int n, int n2) {
      if (n < 0 || n >= 65536) {
         throw new IllegalArgumentException("itemId must be between 0 and 65535");
      } else if (n2 >= 0 && n2 < 65536) {
         return (n & 65535) << 16 | n2 & 65535;
      } else {
         throw new IllegalArgumentException("metaId must be between 0 and 65535");
      }
   }

   public static ItemStack[] T$src$ALgg_vape_wrapper_impl_ItemStack_$f6ukg1(EntityPlayer entityPlayer) {
      boolean bl = ForgeVersion.MC_1_12_2.d();
      if (bl) {
         ItemStack[] itemStackArray = new ItemStack[6];
         ItemStack itemStack = entityPlayer.B$src$Lgg_vape_wrapper_impl_ItemStack_$impdvt();
         if (itemStack.isNotNull()) {
            itemStackArray[0] = itemStack;
         }

         Object[] objectArray = entityPlayer.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().i();
         int n = objectArray.length;

         for (int i = 0; i < n; i++) {
            ItemStack itemStack2 = new ItemStack(objectArray[n - 1 - i]);
            if (!itemStack2.isNull()) {
               itemStackArray[i + 1] = itemStack2;
            }
         }

         ItemStack itemStack3 = entityPlayer.i(EnumHand.offHand());
         if (itemStack3 != null && itemStack3.isNotNull()) {
            itemStackArray[5] = itemStack3;
         }

         return itemStackArray;
      } else {
         ItemStack[] itemStackArrayx = new ItemStack[5];
         ItemStack itemStackx = entityPlayer.B$src$Lgg_vape_wrapper_impl_ItemStack_$impdvt();
         if (itemStackx.isNotNull()) {
            itemStackArrayx[0] = itemStackx;
         }

         Object[] objectArray = entityPlayer.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().i();
         int n = objectArray.length;

         for (int ix = 0; ix < n; ix++) {
            ItemStack itemStack4 = new ItemStack(objectArray[n - 1 - ix]);
            if (!itemStack4.isNull()) {
               itemStackArrayx[ix + 1] = itemStack4;
            }
         }

         return itemStackArrayx;
      }
   }

   private static IllegalArgumentException a(IllegalArgumentException illegalArgumentException) {
      return illegalArgumentException;
   }

   public static long d(int n, int n2, int[] nArray) {
      int n3 = d(n, n2);
      int n4 = b(nArray);
      return (n4 & 4294967295L) << 32 | n3 & 4294967295L;
   }

   public static int b(int[] nArray) {
      if (nArray == null) {
         return 0;
      } else if (nArray.length % 2 != 0) {
         throw new IllegalArgumentException("enchantments array length must be even " + nArray);
      } else {
         int n2 = nArray.length / 2;
         int[] nArray2 = new int[n2];

         for (int n = 0; n < n2; n++) {
            nArray2[n] = h(nArray[2 * n], nArray[2 * n + 1]);
         }

         Arrays.sort(nArray2);
         int var8 = 1;

         for (int n3 : nArray2) {
            var8 = 31 * var8 + n3;
         }

         return var8;
      }
   }

   public static long T(EntityPlayer entityPlayer) {
      return t(T$src$ALgg_vape_wrapper_impl_ItemStack_$f6ukg1(entityPlayer));
   }
}
