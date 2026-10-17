package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEnchantmentHelper;
import gg.vape.utils.EnchantmentUtil;
import gg.vape.wrapper.Wrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class EnchantmentHelper extends Wrapper {
   public static float C(ItemStack itemStack, EnumCreatureAttribute enumCreatureAttribute) {
      if (ForgeVersion.MC_1_21_0.d()) {
         return d(itemStack, enumCreatureAttribute);
      } else {
         Object object = enumCreatureAttribute == null ? null : enumCreatureAttribute.getObject();
         return MEnchantmentHelper.w(vapeInstance.getMappings().Rk, itemStack.getObject(), object);
      }
   }

   public EnchantmentHelper(Object object) {
      super(object);
   }

   public static HashMap<Holder, Integer> Z(ItemStack itemStack) {
      HashMap<Holder, Integer> hashMap = new HashMap<>();
      if (!itemStack.isNull() && itemStack.t() > 0) {
         PotionVersionRange potionVersionRange = new PotionVersionRange(itemStack.w(DataComponents.O()));

         for (Object entryObject : potionVersionRange.entrySet()) {
            ObjectToIntMapEntry entry = new ObjectToIntMapEntry(entryObject);
            Holder holder = new Holder(entry.getKey());
            hashMap.put(holder, entry.getIntValue());
         }

         return hashMap;
      } else {
         return hashMap;
      }
   }

   public static boolean Q(ItemStack itemStack) {
      return MEnchantmentHelper.z(vapeInstance.getMappings().Rk, itemStack.getObject());
   }

   public static EnchantmentModifierDamage getDamageModifier() {
      return ForgeVersion.MC_1_16_5.d() ? null : new EnchantmentModifierDamage(MEnchantmentHelper.getDamageModifier(vapeInstance.getMappings().Rk));
   }

   public static void J(EnchantmentModifier enchantmentModifier, ItemStack[] itemStackArray) {
      if (ForgeVersion.MC_1_12_2.d()) {
         ArrayList<Object> arrayList = new ArrayList<>();

         for (ItemStack itemStack : itemStackArray) {
            arrayList.add(itemStack.getObject());
         }

         MEnchantmentHelper.T(vapeInstance.getMappings().Rk, enchantmentModifier.getObject(), arrayList);
      } else {
         Object[] objectArray = new Object[itemStackArray.length];

         for (int i = 0; i < itemStackArray.length; i++) {
            objectArray[i] = itemStackArray[i].getObject();
         }

         MEnchantmentHelper.o(vapeInstance.getMappings().Rk, enchantmentModifier.getObject(), objectArray);
      }
   }

   private static float d(ItemStack itemStack, EnumCreatureAttribute enumCreatureAttribute) {
      if (itemStack != null && !itemStack.isNull()) {
         float f = 0.0F;
         Enchantment enchantment = Enchantment.sharpness();
         int n;
         if (enchantment != null && !enchantment.isNull() && (n = q(enchantment.getId(), itemStack)) > 0) {
            f += 0.5F + 0.5F * n;
         }

         EnumCreatureAttribute enumCreatureAttribute2;
         if (enumCreatureAttribute != null
            && !enumCreatureAttribute.isNull()
            && (enumCreatureAttribute2 = EnumCreatureAttribute.undefined()) != null
            && !enumCreatureAttribute2.isNull()
            && enumCreatureAttribute.getObject() != enumCreatureAttribute2.getObject()) {
            Enchantment enchantment3 = Enchantment.smite();
            if (enchantment3 != null && !enchantment3.isNull() && q(enchantment3.getId(), itemStack) > 0) {
            }

            Enchantment enchantment2;
            if ((enchantment2 = Enchantment.baneOfArthropods()) != null && !enchantment2.isNull() && q(enchantment2.getId(), itemStack) > 0) {
            }
         }

         return f;
      } else {
         return 0.0F;
      }
   }

   public static int q(int n, ItemStack itemStack) {
      if (n < 0) {
         return 0;
      } else if (ForgeVersion.MC_1_12_2.d()) {
         Enchantment enchantment = Enchantment.getEnchantmentById(n);
         return ForgeVersion.MC_1_21_4.d()
            ? q(enchantment, itemStack)
            : MEnchantmentHelper.W(vapeInstance.getMappings().Rk, enchantment.getObject(), itemStack.getObject());
      } else {
         return MEnchantmentHelper.h(vapeInstance.getMappings().Rk, n, itemStack.getObject());
      }
   }

   public static int e(String string, ItemStack itemStack) {
      if (!ForgeVersion.MC_1_21_4.d()) {
         return 0;
      } else {
         Optional<Holder> optional = EnchantmentUtil.K(string);
         return !optional.isPresent() ? 0 : MEnchantmentHelper.X(vapeInstance.getMappings().Rk, optional.get().getObject(), itemStack.getObject());
      }
   }

   private static void lambda$getEnchantmentLevel$0(ResourceKey resourceKey, int[] nArray, Holder holder, Integer n) {
      if (holder.F(resourceKey) && nArray[0] > n) {
         nArray[0] = n;
      }
   }

   public static int B(ItemStack[] itemStackArray, DamageSource damageSource) {
      if (ForgeVersion.MC_1_21_0.d()) {
         return 0;
      } else if (ForgeVersion.MC_1_12_2.d()) {
         ArrayList<Object> arrayList = new ArrayList<>();

         for (ItemStack itemStack : itemStackArray) {
            arrayList.add(itemStack.getObject());
         }

         return MEnchantmentHelper.N(vapeInstance.getMappings().Rk, arrayList, damageSource.getObject());
      } else {
         Object[] objectArray = new Object[itemStackArray.length];

         for (int i = 0; i < itemStackArray.length; i++) {
            objectArray[i] = itemStackArray[i].getObject();
         }

         return MEnchantmentHelper.p(vapeInstance.getMappings().Rk, objectArray, damageSource.getObject());
      }
   }

   public static float N(Object object, ItemStack itemStack, Entity entity, DamageSource damageSource, float f) {
      return MEnchantmentHelper.F(vapeInstance.getMappings().Rk, object, itemStack.getObject(), entity.getObject(), damageSource.getObject(), f);
   }

   public static int q(Enchantment enchantment, ItemStack itemStack) {
      if (ForgeVersion.MC_1_21_4.d()) {
         WorldClient worldClient = Minecraft.theWorld();
         if (worldClient.isNull()) {
            return 0;
         } else {
            RegistryAccess registryAccess = worldClient.e();
            Registry registry = registryAccess.lookupOrThrow(ResourceKeyEnchantmentBridge.enchantment());
            Holder holder = registry.J(enchantment.getObject());
            return MEnchantmentHelper.X(vapeInstance.getMappings().Rk, holder.getObject(), itemStack.getObject());
         }
      } else {
         return MEnchantmentHelper.X(vapeInstance.getMappings().Rk, enchantment.getObject(), itemStack.getObject());
      }
   }

   public static int y(Entity entity) {
      return ForgeVersion.MC_1_21_0.d()
         ? a(Enchantments.O(), new EntityLiving(entity.getObject()))
         : MEnchantmentHelper.a(vapeInstance.getMappings().Rk, entity.getObject());
   }

   public static PotionVersionRange m(ItemStack itemStack) {
      return new PotionVersionRange(MEnchantmentHelper.l(vapeInstance.getMappings().Rk, itemStack.getObject()));
   }

   public static int a(ResourceKey resourceKey, EntityLiving entityLiving) {
      ArrayList<ItemStack> arrayList = entityLiving.I$src$Ljava_util_ArrayList_$15zosdi();
      arrayList.add(entityLiving.B$src$Lgg_vape_wrapper_impl_ItemStack_$impdvt());
      int[] nArray = new int[]{0};

      for (ItemStack itemStack : arrayList) {
         HashMap<Holder, Integer> hashMap = Z(itemStack);
         hashMap.forEach((arg_0, arg_1) -> lambda$getEnchantmentLevel$0(resourceKey, nArray, arg_0, arg_1));
      }

      return nArray[0];
   }
}
