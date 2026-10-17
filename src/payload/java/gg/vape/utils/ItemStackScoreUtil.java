package gg.vape.utils;

import gg.vape.Vape;
import gg.vape.mapping.ItemMappingEntry;
import gg.vape.mapping.MappedClasses;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ArmorMaterial;
import gg.vape.wrapper.impl.AttributeModifier;
import gg.vape.wrapper.impl.ChestTypeHolder;
import gg.vape.wrapper.impl.Container;
import gg.vape.wrapper.impl.DataComponentMap;
import gg.vape.wrapper.impl.DataComponents;
import gg.vape.wrapper.impl.Enchantment;
import gg.vape.wrapper.impl.EnchantmentHelper;
import gg.vape.wrapper.impl.EntityEquipmentSlot;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EnumCreatureAttribute;
import gg.vape.wrapper.impl.EquipmentSlotGroup;
import gg.vape.wrapper.impl.Equippable;
import gg.vape.wrapper.impl.FoodProperties;
import gg.vape.wrapper.impl.Foods;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemArmor;
import gg.vape.wrapper.impl.ItemAttributeModifiers;
import gg.vape.wrapper.impl.ItemAttributeModifiersComponent;
import gg.vape.wrapper.impl.ItemAttributeModifiersComponent$Entry;
import gg.vape.wrapper.impl.ItemCameraTransformBase;
import gg.vape.wrapper.impl.ItemCameraTransformLeaf;
import gg.vape.wrapper.impl.ItemCameraTransformSubtypeValue;
import gg.vape.wrapper.impl.ItemCameraTransformType;
import gg.vape.wrapper.impl.ItemSplashPotion;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.ItemSword;
import gg.vape.wrapper.impl.ItemTool;
import gg.vape.wrapper.impl.MonsterAttributesBridge;
import gg.vape.wrapper.impl.NonNullList;
import gg.vape.wrapper.impl.PotionEffect;
import gg.vape.wrapper.impl.PotionRegistry;
import gg.vape.wrapper.impl.ResourceKey;
import gg.vape.wrapper.impl.ResourceKeyHolder;
import gg.vape.wrapper.impl.ResourceLocation;
import gg.vape.wrapper.impl.Slot;
import gg.vape.wrapper.impl.StatusEffect;
import gg.vape.wrapper.impl.ToolMaterial;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemStackScoreUtil {
   private static final List<ItemStack> w = new ArrayList<>();

   public static float a$src$F$2aw1mh(ItemStack itemStack) {
      if (itemStack != null && !itemStack.isNull()) {
         Item item = itemStack.getItem();
         if (!h(item) && !I(item)) {
            return 0.0F;
         } else {
            float f = 0.0F;
            if (ForgeVersion.MC_1_21_4.d()) {
               DataComponentMap dataComponentMap = item.g();
               Object object = dataComponentMap.E(DataComponents.E());
               ItemAttributeModifiersComponent itemAttributeModifiersComponent = new ItemAttributeModifiersComponent(object);
               EquipmentSlotGroup equipmentSlotGroup = MonsterAttributesBridge.t();

               for (ItemAttributeModifiersComponent$Entry itemAttributeModifiersComponent$Entry : itemAttributeModifiersComponent.getEntries()) {
                  EquipmentSlotGroup equipmentSlotGroup2 = itemAttributeModifiersComponent$Entry.getEquipmentSlotGroup();
                  AttributeModifier attributeModifier = itemAttributeModifiersComponent$Entry.getModifier();
                  if (equipmentSlotGroup2.equals(equipmentSlotGroup)) {
                     double d = attributeModifier.getAmount();
                     f += (float)d;
                  }
               }
            } else if (h(item)) {
               ItemTool itemTool = new ItemTool(item.getObject());
               f = 4.0F + itemTool.p$src$F$7elb4m();
            } else {
               ItemSword itemSword = new ItemSword(item.getObject());
               f = itemSword.R();
            }

            return f;
         }
      } else {
         return 0.0F;
      }
   }

   public static boolean V$src$Z$dcbuai(ItemStack itemStack) {
      return I(itemStack.getItem());
   }

   public static boolean g$src$Z$z0w7fv(ItemStack itemStack) {
      return T(itemStack.getItem());
   }

   public static double d(ItemStack itemStack) {
      if (itemStack.isNull()) {
         return 0.0;
      } else {
         Item item = itemStack.getItem();
         return !R(item) ? 0.0 : P(itemStack) + EnchantmentHelper.q(0, itemStack);
      }
   }

   public static boolean o(ItemStack itemStack, boolean bl) {
      if (itemStack.isNull() || itemStack.getItem().isNull()) {
         return false;
      } else if (bl && D(itemStack)) {
         return true;
      } else if (!itemStack.getItem().isInstance(MappedClasses.Di)) {
         return false;
      } else {
         ItemSplashPotion itemSplashPotion = new ItemSplashPotion(itemStack.getItem().getObject());
         if (itemSplashPotion.getRawPotionEffects(itemStack) == null) {
            return false;
         } else {
            int n = 6;
            int n2 = 10;
            if (ForgeVersion.MC_1_20_6.d()) {
               n--;
               n2--;
            }

            StatusEffect statusEffect = PotionRegistry.z.getStatusEffect();
            StatusEffect statusEffect2 = PotionRegistry.i.getStatusEffect();
            if (!ForgeVersion.MC_1_20_6.d() || statusEffect != null && statusEffect2 != null) {
               if (ForgeVersion.MC_1_20_6.d() && statusEffect != null && statusEffect2 != null) {
                  boolean var12 = true;
               } else {
                  boolean var10000 = false;
               }

               for (PotionEffect potionEffect : itemSplashPotion.getPotionEffects(itemStack)) {
                  int n3;
                  StatusEffect statusEffect3;
                  if (ForgeVersion.MC_1_20_6.d()
                     ? statusEffect.equals(statusEffect3 = potionEffect.i()) || statusEffect2.equals(statusEffect3)
                     : (n3 = potionEffect.C()) == n || n3 == n2) {
                     return true;
                  }
               }

               return false;
            } else {
               Vape.debugLog("WTF?");
               return false;
            }
         }
      }
   }

   public static double T(ItemStack itemStack) {
      double d = p(itemStack);
      if (d == 0.0) {
         return d;
      } else if (!m(itemStack.getItem())) {
         return d;
      } else {
         int n = EnchantmentHelper.q(Enchantment.efficiency().getId(), itemStack);
         n = n > 0 ? n * n + 1 : 0;
         d += n;
         d += 0.1 * Math.max(0, EnchantmentHelper.q(Enchantment.unbreaking().getId(), itemStack));
         d += 0.1 * Math.max(0, EnchantmentHelper.q(Enchantment.fortune().getId(), itemStack));
         return d + 0.1 * Math.max(0, EnchantmentHelper.q(Enchantment.mending().getId(), itemStack));
      }
   }

   public static boolean D(ItemStack itemStack) {
      return v(itemStack.getItem());
   }

   public static double a(ItemStack itemStack) {
      double d = a$src$F$2aw1mh(itemStack);
      return d + EnchantmentHelper.C(itemStack, EnumCreatureAttribute.undefined());
   }

   public static float I$src$F$dh3k81(ItemStack itemStack) {
      if (itemStack != null && !itemStack.isNull()) {
         Item item = itemStack.getItem();
         if (!h(item) && !I(item)) {
            return 0.0F;
         } else {
            float f = a$src$F$2aw1mh(itemStack);
            return EnchantmentHelper.q(Enchantment.sharpness().getId(), itemStack) * 1.25F
               + f
               + EnchantmentHelper.q(Enchantment.fireAspect().getId(), itemStack) * 0.01F;
         }
      } else {
         return 0.0F;
      }
   }

   public static float O(EntityPlayerSP entityPlayerSP) {
      float f = 0.0F;
      Container container = entityPlayerSP.F$src$Lgg_vape_wrapper_impl_Container_$152y6lm();

      for (int i = 0; i < 45; i++) {
         Slot slot = container.getSlot(i);
         float f2;
         if (slot.hasStack() && (f2 = I$src$F$dh3k81(slot.getStack())) > f) {
            f = f2;
         }
      }

      return f;
   }

   public static double u(ItemStack itemStack) {
      double d = p(itemStack);
      if (d == 0.0) {
         return d;
      } else if (!k(itemStack.getItem())) {
         return d;
      } else {
         d += 0.1 * Math.max(0, EnchantmentHelper.q(Enchantment.unbreaking().getId(), itemStack));
         return d + 0.1 * Math.max(0, EnchantmentHelper.q(Enchantment.mending().getId(), itemStack));
      }
   }

   public static boolean y(ItemStack itemStack) {
      return k(itemStack.getItem());
   }

   public static List<ItemStack> S() {
      if (w.isEmpty()) {
         w();
      }

      return w;
   }

   public static double k(ItemStack itemStack) {
      double d = a(itemStack);
      d += 0.01 * Math.max(0, EnchantmentHelper.q(Enchantment.smite().getId(), itemStack));
      d += 0.01 * Math.max(0, EnchantmentHelper.q(Enchantment.baneOfArthropods().getId(), itemStack));
      d += 0.01 * Math.max(0, EnchantmentHelper.q(Enchantment.fireAspect().getId(), itemStack));
      d += 0.05 * Math.max(0, EnchantmentHelper.q(Enchantment.knockback().getId(), itemStack));
      d += 0.01 * Math.max(0, EnchantmentHelper.q(Enchantment.looting().getId(), itemStack));
      d += 0.01 * Math.max(0, EnchantmentHelper.q(Enchantment.mending().getId(), itemStack));
      d += 0.01 * Math.max(0, EnchantmentHelper.q(Enchantment.unbreaking().getId(), itemStack));
      return d + 0.05 * Math.max(0, EnchantmentHelper.q(Enchantment.sweepingEdge().getId(), itemStack));
   }

   public static double V(ItemStack itemStack) {
      double d = 0.0;
      if (!itemStack.isNull() && !itemStack.getItem().isNull() && I(itemStack.getItem())) {
         if (ForgeVersion.MC_1_21_4.d()) {
            d = a$src$F$2aw1mh(itemStack);
         } else {
            ItemAttributeModifiers itemAttributeModifiers = itemStack.o();
            if (itemAttributeModifiers.size() > 0) {
               int n = ForgeVersion.MC_1_12_2.L() ? 1 : 0;
               AttributeModifier attributeModifier = new AttributeModifier(itemAttributeModifiers.values().toArray()[n]);
               d = attributeModifier.getAmount();
            }
         }

         d += EnchantmentHelper.C(itemStack, EnumCreatureAttribute.undefined());
         if (!T(itemStack.getItem())) {
            return d;
         } else {
            d += 0.1 * Math.max(0, EnchantmentHelper.q(Enchantment.efficiency().getId(), itemStack));
            d += 0.01 * Math.max(0, EnchantmentHelper.q(Enchantment.unbreaking().getId(), itemStack));
            d += 0.01 * Math.max(0, EnchantmentHelper.q(Enchantment.fortune().getId(), itemStack));
            d += 0.01 * Math.max(0, EnchantmentHelper.q(Enchantment.smite().getId(), itemStack));
            d += 0.01 * Math.max(0, EnchantmentHelper.q(Enchantment.baneOfArthropods().getId(), itemStack));
            return d + 0.1 * Math.max(0, EnchantmentHelper.q(Enchantment.mending().getId(), itemStack));
         }
      } else {
         return d;
      }
   }

   public static int t(ItemStack itemStack) {
      return !itemStack.isNull() && !itemStack.getItem().isNull() ? H(itemStack.getItem()) : -1;
   }

   public static boolean l(ItemStack itemStack) {
      return R(itemStack.getItem());
   }

   public static int H(Item item) {
      if (item.isNull()) {
         return -1;
      } else if (!R(item)) {
         return -1;
      } else {
         int n = -1;
         if (ForgeVersion.MC_1_21_4.d()) {
            DataComponentMap dataComponentMap = item.g();
            Object object = dataComponentMap.E(DataComponents.k());
            if (object != null) {
               Equippable equippable = new Equippable(object);
               EntityEquipmentSlot entityEquipmentSlot = equippable.m$src$Lgg_vape_wrapper_impl_EntityEquipmentSlot_$bzr9md();
               n = entityEquipmentSlot.W();
            }
         } else {
            ItemArmor itemArmor = new ItemArmor(item);
            n = itemArmor.b$src$I$1fmozr4();
         }

         if (ForgeVersion.MC_1_16_5.d()) {
            n = 3 - n;
         }

         return n;
      }
   }

   public static boolean A(ItemStack itemStack) {
      return g(itemStack.getItem());
   }

   public static boolean T(Item item) {
      if (ForgeVersion.MC_1_21_4.d()) {
         String string = item.A();
         return string != null && !string.isEmpty() ? string.contains("_axe") : false;
      } else {
         return item.isInstance(MappedClasses.YP);
      }
   }

   public static boolean i(ItemStack itemStack) {
      return o(itemStack, false);
   }

   public static Item g(String string) {
      Iterator iterator = Item.Y().D().iterator();
      String string2 = "";

      while (iterator.hasNext()) {
         Object object;
         if (ForgeVersion.MC_1_7_10.L()) {
            string2 = (String)iterator.next();
            object = Item.Y().S(string2);
         } else {
            ResourceLocation resourceLocation = new ResourceLocation(iterator.next());
            string2 = resourceLocation.getResourcePath();
            object = Item.Y().S(resourceLocation.getObject());
         }

         string2 = string2.replace("minecraft:", "");
         string2 = string2.toLowerCase();
         if (MappedClasses.lb.isInstance(object)) {
            Item item = new Item(object);
            if (string2.equalsIgnoreCase(string)) {
               return item;
            }
         }
      }

      return null;
   }

   private static int lambda$populateItems$0(ItemStack itemStack, ItemStack itemStack2) {
      ItemMappingEntry itemMappingEntry = Vape.INSTANCE.getItemStackResolver().resolve(itemStack);
      ItemMappingEntry itemMappingEntry2 = Vape.INSTANCE.getItemStackResolver().resolve(itemStack2);
      int n2 = itemMappingEntry != null && itemMappingEntry.getLegacyId() != null ? itemMappingEntry.getLegacyId() : itemStack.getItem().P();
      int n = itemMappingEntry2 != null && itemMappingEntry2.getLegacyId() != null ? itemMappingEntry2.getLegacyId() : itemStack2.getItem().P();
      if (n2 == n) {
         n2 += itemMappingEntry != null && itemMappingEntry.getMetadata() != null ? itemMappingEntry.getMetadata() : 0;
         n += itemMappingEntry2 != null && itemMappingEntry2.getMetadata() != null ? itemMappingEntry2.getMetadata() : 0;
      }

      return Integer.compare(n2, n);
   }

   public static boolean R(Item item) {
      if (ForgeVersion.MC_1_21_6.d()) {
         Equippable equippable = new Equippable(item.b().w(DataComponents.k()));
         return equippable.isNull() ? false : equippable.m$src$Lgg_vape_wrapper_impl_EntityEquipmentSlot_$bzr9md().v();
      } else if (ForgeVersion.MC_1_21_4.d()) {
         EquipmentSlotGroup equipmentSlotGroup = MonsterAttributesBridge.K();
         DataComponentMap dataComponentMap = item.g();
         Object object = dataComponentMap.E(DataComponents.E());
         ItemAttributeModifiersComponent itemAttributeModifiersComponent = new ItemAttributeModifiersComponent(object);

         for (ItemAttributeModifiersComponent$Entry itemAttributeModifiersComponent$Entry : itemAttributeModifiersComponent.getEntries()) {
            EquipmentSlotGroup equipmentSlotGroup2 = itemAttributeModifiersComponent$Entry.getEquipmentSlotGroup();
            if (equipmentSlotGroup2.equals(equipmentSlotGroup)) {
               return true;
            }
         }

         return false;
      } else {
         return item.isInstance(MappedClasses.qU);
      }
   }

   public static boolean v(Item item) {
      if (ForgeVersion.MC_1_21_4.d()) {
         DataComponentMap dataComponentMap = item.g();
         Object object = dataComponentMap.E(DataComponents.d());
         if (MappedClasses.foodPropertiesClass.isInstance(object)) {
            FoodProperties foodProperties = new FoodProperties(object);
            if (Foods.mushroomStew().equals(foodProperties)) {
               return true;
            }
         }

         return false;
      } else {
         return item.isInstance(MappedClasses.z);
      }
   }

   public static boolean m(Item item) {
      if (ForgeVersion.MC_1_21_4.d()) {
         String string = item.A();
         return string != null && !string.isEmpty() ? string.contains("_pickaxe") : false;
      } else {
         return item.isInstance(MappedClasses.DU);
      }
   }

   public static boolean h(Item item) {
      if (ForgeVersion.MC_1_21_4.d()) {
         String string = item.A();
         return string != null && !string.isEmpty() ? string.contains("_sword") : false;
      } else {
         return item.isInstance(MappedClasses.V5);
      }
   }

   public static double P(ItemStack itemStack) {
      if (itemStack.isNull()) {
         return 0.0;
      } else {
         Item item = itemStack.getItem();
         if (!R(item)) {
            return 0.0;
         } else {
            double d = 0.0;
            if (ForgeVersion.MC_1_21_4.d()) {
               DataComponentMap dataComponentMap = item.g();
               Object object = dataComponentMap.E(DataComponents.E());
               ItemAttributeModifiersComponent itemAttributeModifiersComponent = new ItemAttributeModifiersComponent(object);
               EquipmentSlotGroup equipmentSlotGroup = MonsterAttributesBridge.K();

               for (ItemAttributeModifiersComponent$Entry itemAttributeModifiersComponent$Entry : itemAttributeModifiersComponent.getEntries()) {
                  EquipmentSlotGroup equipmentSlotGroup2 = itemAttributeModifiersComponent$Entry.getEquipmentSlotGroup();
                  AttributeModifier attributeModifier = itemAttributeModifiersComponent$Entry.getModifier();
                  if (equipmentSlotGroup2.equals(equipmentSlotGroup)) {
                     double d2 = attributeModifier.getAmount();
                     d += d2;
                  }
               }
            } else {
               ItemArmor itemArmor = new ItemArmor(item);
               d = itemArmor.S();
            }

            return d;
         }
      }
   }

   public static double L(ItemStack itemStack) {
      if (itemStack.isNull()) {
         return 0.0;
      } else {
         Item item = itemStack.getItem();
         if (!R(item)) {
            return 0.0;
         } else {
            double d = I(itemStack);
            int n3 = EnchantmentHelper.q(Enchantment.featherFalling().getId(), itemStack);
            if (n3 > 0) {
               d += n3 * 0.1;
            }

            int n2;
            if ((n2 = EnchantmentHelper.q(Enchantment.fireProtection().getId(), itemStack)) > 0) {
               d += n2 * 0.1;
            }

            int n;
            if ((n = EnchantmentHelper.q(Enchantment.blastProtection().getId(), itemStack)) > 0) {
               d += n * 0.1;
            }

            return d;
         }
      }
   }

   public static double I(ItemStack itemStack) {
      if (itemStack.isNull()) {
         return 0.0;
      } else {
         Item item = itemStack.getItem();
         if (!R(item)) {
            return 0.0;
         } else {
            double d = d(itemStack);
            double d2 = e(itemStack);
            return d + d2;
         }
      }
   }

   public static boolean g(Item item) {
      if (ForgeVersion.MC_1_21_4.d()) {
         String string = item.A();
         return string != null && !string.isEmpty() ? string.contains("_shovel") : false;
      } else {
         return item.isInstance(MappedClasses.FM);
      }
   }

   public static boolean T$src$Z$2fnsig(ItemStack itemStack) {
      if (itemStack.isNull() || itemStack.getItem().isNull()) {
         return false;
      } else if (!l(itemStack)) {
         return false;
      } else {
         if (ForgeVersion.MC_1_21_4.d()) {
            DataComponentMap dataComponentMap = itemStack.getItem().g();
            Object object = dataComponentMap.E(DataComponents.k());
            ResourceKey resourceKey;
            if (object != null && (resourceKey = new Equippable(object).j()) != null && resourceKey.equals(ResourceKeyHolder.gold())) {
               return true;
            }
         } else {
            ItemArmor itemArmor = new ItemArmor(itemStack.getItem());
            if (ArmorMaterial.gold().equals(itemArmor.Y$src$Lgg_vape_wrapper_impl_ArmorMaterial_$11f9rp6())) {
               return true;
            }
         }

         return false;
      }
   }

   public static boolean I(Item item) {
      if (ForgeVersion.MC_1_21_4.d()) {
         DataComponentMap dataComponentMap = item.g();
         Object object = dataComponentMap.E(DataComponents.F());
         return object != null;
      } else {
         return item.isInstance(MappedClasses.Ye);
      }
   }

   private static void w() {
      for (Object e : Item.Y().D()) {
         Object object;
         if (ForgeVersion.MC_1_7_10.L()) {
            object = Item.Y().S(e);
         } else {
            Wrapper wrapper = new ResourceLocation(e);
            object = Item.Y().S(wrapper.getObject());
         }

         if (MappedClasses.lb.isInstance(object)) {
            Wrapper wrapper = new Item(object);
            List list = new ArrayList();
            if (ForgeVersion.MC_1_12_2.d()) {
               list = (List)NonNullList.create().getObject();
            }

            ((Item)wrapper).D((Item)wrapper, list);
            if (!list.isEmpty()) {
               for (Object e2 : list) {
                  ItemStack itemStack = new ItemStack(e2);
                  if (itemStack.getItem().P() != 62) {
                     w.add(itemStack);
                  }
               }
            } else if (ForgeVersion.MC_1_7_10.Y()) {
               w.add(((Item)wrapper).b());
            }
         }
      }

      w.sort(ItemStackScoreUtil::lambda$populateItems$0);
   }

   public static boolean k(Item item) {
      if (ForgeVersion.MC_1_21_4.d()) {
         String string = item.A();
         return string != null && !string.isEmpty() ? string.contains("_hoe") : false;
      } else {
         return item.isInstance(MappedClasses.Ff);
      }
   }

   public static double g(ItemStack itemStack) {
      double d = p(itemStack);
      if (d == 0.0) {
         return d;
      } else if (!g(itemStack.getItem())) {
         return d;
      } else {
         int n = EnchantmentHelper.q(Enchantment.efficiency().getId(), itemStack);
         n = n > 0 ? n * n + 1 : 0;
         d += n;
         d += 0.1 * Math.max(0, EnchantmentHelper.q(Enchantment.unbreaking().getId(), itemStack));
         d += 0.1 * Math.max(0, EnchantmentHelper.q(Enchantment.fortune().getId(), itemStack));
         return d + 0.1 * Math.max(0, EnchantmentHelper.q(Enchantment.mending().getId(), itemStack));
      }
   }

   public static double e(ItemStack itemStack) {
      if (ForgeVersion.MC_1_12_2.v()) {
         return 0.0;
      } else if (itemStack.isNull()) {
         return 0.0;
      } else {
         Item item = itemStack.getItem();
         if (!R(item)) {
            return 0.0;
         } else {
            double d = 0.0;
            if (ForgeVersion.MC_1_21_4.d()) {
               DataComponentMap dataComponentMap = item.g();
               Object object = dataComponentMap.E(DataComponents.E());
               ItemAttributeModifiersComponent itemAttributeModifiersComponent = new ItemAttributeModifiersComponent(object);
               EquipmentSlotGroup equipmentSlotGroup = MonsterAttributesBridge.H();

               for (ItemAttributeModifiersComponent$Entry itemAttributeModifiersComponent$Entry : itemAttributeModifiersComponent.getEntries()) {
                  EquipmentSlotGroup equipmentSlotGroup2 = itemAttributeModifiersComponent$Entry.getEquipmentSlotGroup();
                  AttributeModifier attributeModifier = itemAttributeModifiersComponent$Entry.getModifier();
                  if (equipmentSlotGroup2.equals(equipmentSlotGroup)) {
                     double d2 = attributeModifier.getAmount();
                     d += d2;
                  }
               }
            } else {
               ItemArmor itemArmor = new ItemArmor(item);
               d = itemArmor.p$src$F$1fue3zl();
            }

            return d;
         }
      }
   }

   public static boolean Y(ItemStack itemStack) {
      return h(itemStack.getItem());
   }

   public static boolean K(ItemStack itemStack) {
      if (itemStack.isNull() || itemStack.getItem().isNull()) {
         return false;
      } else if (!I(itemStack.getItem())) {
         return false;
      } else if (ForgeVersion.MC_1_21_4.d()) {
         DataComponentMap components = itemStack.getItem().g();
         Object repairableHandle = components.E(DataComponents.X());
         if (repairableHandle == null) {
            return false;
         } else {
            ItemCameraTransformType repairable = new ItemCameraTransformType(repairableHandle);
            ItemCameraTransformBase items = repairable.getItems();
            if (!items.isInstance(MappedClasses.zo)) {
               return false;
            } else {
               ItemCameraTransformLeaf namedItems = new ItemCameraTransformLeaf(items);
               ItemCameraTransformSubtypeValue itemTagKey = namedItems.getKey();
               return itemTagKey.equals(ChestTypeHolder.goldToolMaterials());
            }
         }
      } else {
         ItemSword itemSword = new ItemSword(itemStack.getItem());
         return itemSword.s().equals(ToolMaterial.S());
      }
   }

   public static double f(ItemStack itemStack) {
      double d = 2.0;
      int n = EnchantmentHelper.q(Enchantment.power().getId(), itemStack);
      if (n > 0) {
         d += n * 0.5 + 0.5;
      }

      return d;
   }

   public static double p(ItemStack itemStack) {
      if (itemStack.isNull() || itemStack.getItem().isNull() || !I(itemStack.getItem())) {
         return 0.0;
      } else {
         return ForgeVersion.MC_1_21_4.d() ? a$src$F$2aw1mh(itemStack) : new ItemSword(itemStack.getItem().getObject()).Q$src$F$vp4c40();
      }
   }

   public static double O(ItemStack itemStack) {
      double d = f(itemStack);
      d += 0.1 * Math.max(0, EnchantmentHelper.q(Enchantment.punch().getId(), itemStack));
      d += 0.1 * Math.max(0, EnchantmentHelper.q(Enchantment.flame().getId(), itemStack));
      d += 1 * Math.max(0, EnchantmentHelper.q(Enchantment.infinity().getId(), itemStack));
      d += 0.1 * Math.max(0, EnchantmentHelper.q(Enchantment.mending().getId(), itemStack));
      return d + 0.1 * Math.max(0, EnchantmentHelper.q(Enchantment.unbreaking().getId(), itemStack));
   }

   public static boolean Z(Item item) {
      return item.isInstance(MappedClasses.Vb)
         || item.isInstance(MappedClasses.YH)
         || item.isInstance(MappedClasses.ZH)
         || item.isInstance(MappedClasses.Y5)
         || item.isInstance(MappedClasses.YJ);
   }
}
