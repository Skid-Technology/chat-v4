package gg.vape.wrapper.impl;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.mappings.MItemStack;
import gg.vape.wrapper.Wrapper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;

public class ItemStack extends Wrapper {
   private static int[] f;

   public String x() {
      if (ForgeVersion.MC_1_16_5.d()) {
         try {
            ITextComponent iTextComponent = new ITextComponent(MItemStack.c(vapeInstance.getMappings().q8, this.I));
            return iTextComponent.getFormattedText();
         } catch (Throwable var4) {
            Item item = this.getItem();
            return item.isNull() ? "" : item.Y$src$Ljava_lang_String_$uel3xb();
         }
      } else {
         try {
            return MItemStack.k(vapeInstance.getMappings().q8, this.I);
         } catch (Exception var3) {
            return "";
         }
      }
   }

   public boolean W(ItemStack itemStack) {
      return ForgeVersion.MC_1_17.d()
         ? vapeInstance.getMappings().q8.e(this.I, itemStack.getItem().getObject())
         : vapeInstance.getMappings().q8.e(this.I, itemStack.getObject());
   }

   public void i(Object object) {
      vapeInstance.getMappings().q8.D(this.I, object);
   }

   public boolean W(DataComponentTypeBridge dataComponentTypeBridge) {
      return ForgeVersion.MC_1_21_4.d() ? vapeInstance.getMappings().q8.Y(this.getObject(), dataComponentTypeBridge.getObject()) : true;
   }

   public static ItemStack S(Item item) {
      if (item == null || item.isNull()) {
         return new ItemStack(null);
      } else if (ForgeVersion.MC_26_1.d()) {
         try {
            Holder holder = item.Q();
            if (holder.isNotNull()) {
               return new ItemStack(vapeInstance.getMappings().q8.O(holder.getObject(), 1));
            }
         } catch (Throwable var2) {
         }

         return new ItemStack(null);
      } else {
         return new ItemStack(vapeInstance.getMappings().q8.p(item.getObject()));
      }
   }

   @Override
   public boolean isNull() {
      if (super.isNull()) {
         return true;
      } else if (ForgeVersion.MC_1_21_11.d()) {
         return vapeInstance.getMappings().q8.j(this.getObject());
      } else if (this.toString().contains("tile.air")) {
         return true;
      } else if (this.toString().contains("minecraft:air")) {
         return true;
      } else {
         String string = this.x();
         return string.isEmpty() || string.equals("Air");
      }
   }

   public void s(int n) {
      MItemStack.b(vapeInstance.getMappings().q8, this.I, n);
   }

   public void Y(int n) {
      vapeInstance.getMappings().q8.k(this.I, n);
   }

   public Object l() {
      return vapeInstance.getMappings().q8.r(this.I);
   }

   public ItemStack(Object object) {
      super(object);
   }

   public static ItemStack G(Item item) {
      if (item != null && !item.isNull()) {
         if (ForgeVersion.MC_26_1.d()) {
            try {
               Holder holder = item.Q();
               if (holder.isNotNull()) {
                  return new ItemStack(vapeInstance.getMappings().q8.O(holder.getObject(), 1));
               }
            } catch (Throwable var2) {
            }
         }

         return S(item);
      } else {
         return new ItemStack(null);
      }
   }

   public Object A(EntityPlayer entityPlayer, Object object) {
      return ForgeVersion.MC_1_8_9.Y()
         ? MItemStack.g(vapeInstance.getMappings().q8, this.I, entityPlayer.getObject(), object)
         : MItemStack.X(vapeInstance.getMappings().q8, this.I, entityPlayer.getObject(), (Boolean)object);
   }

   public ItemStack k() {
      return new ItemStack(MItemStack.J(vapeInstance.getMappings().q8, this.I));
   }

   public boolean r() {
      if (!ForgeVersion.MC_26_1.d()) {
         if (this.isNull()) {
            return true;
         } else if (this.t() <= 0) {
            return true;
         } else {
            Item item = this.getItem();
            if (item.isNull()) {
               return true;
            } else if (ForgeVersion.MC_1_12_2.v()) {
               return this.isNull() ? true : item.P() == 0;
            } else {
               return false;
            }
         }
      } else {
         return this.isNull() || vapeInstance.getMappings().q8.j(this.getObject());
      }
   }

   public static int[] Q() {
      return f;
   }

   public void v(Enchantment enchantment, int n) {
      MItemStack.r(vapeInstance.getMappings().q8, this.I, enchantment.getObject(), n);
   }

   public ArrayList<Enchantment> p() {
      PotionVersionRange potionVersionRange = EnchantmentHelper.m(this);
      Iterator iterator = potionVersionRange.entrySet().iterator();
      ArrayList<Enchantment> arrayList = new ArrayList<>();

      while (iterator.hasNext()) {
         Holder holder = new Holder(new ObjectToIntMapEntry(iterator.next()).getKey());
         arrayList.add(new Enchantment(holder.N()));
      }

      return arrayList;
   }

   public static void C(int[] nArray) {
      f = nArray;
   }

   public int y() {
      return MItemStack.A(vapeInstance.getMappings().q8, this.I);
   }

   public ItemAttributeModifiers o() {
      if (ForgeVersion.MC_1_20_6.d()) {
         EmptyItemAttributeModifiers emptyItemAttributeModifiers = EmptyItemAttributeModifiers.create();
         this.k(EntityEquipmentSlot.L(), emptyItemAttributeModifiers::put);
         return emptyItemAttributeModifiers;
      } else {
         return ForgeVersion.MC_1_12_2.d()
            ? new ItemAttributeModifiers(MItemStack.g(vapeInstance.getMappings().q8, this.I, EntityEquipmentSlot.L().getObject()))
            : new ItemAttributeModifiers(MItemStack.A$src$Ljava_lang_Object_$2ove6c(vapeInstance.getMappings().q8, this.I));
      }
   }

   public Holder H() {
      return ForgeVersion.MC_26_1.d() ? new Holder(null) : new Holder(MItemStack.M(vapeInstance.getMappings().q8, this.I));
   }

   public float V(int n, int n2, int n3) {
      WorldClient worldClient = Minecraft.theWorld();
      if (ForgeVersion.MC_1_12_2.d()) {
         BlockState blockState = worldClient.getBlockState(BlockPos.create(n, n2, n3));
         return MItemStack.u(vapeInstance.getMappings().q8, this.I, blockState.getObject());
      } else {
         return MItemStack.u(vapeInstance.getMappings().q8, this.I, worldClient.getBlockByPos(n, n2, n3).getObject());
      }
   }

   public List<String> z() {
      ArrayList<String> arrayList = new ArrayList<>();
      TagCompound tagCompound = new TagCompound(this.l());
      if (tagCompound.isNull()) {
         return arrayList;
      } else {
         TagBase tagBase = tagCompound.getTag("display");
         if (!tagBase.isNull() && tagBase.isInstance(MappedClasses.Yg)) {
            TagCompound tagCompound2 = (TagCompound)tagBase;
            TagList tagList = tagCompound2.getTagList("Lore", 8);

            for (int i = 0; i < tagList.tagCount(); i++) {
               String string = tagList.a(i);
               arrayList.add(string);
            }

            return arrayList;
         } else {
            return arrayList;
         }
      }
   }

   public EnumActionResult C(World world, EntityPlayer entityPlayer, EnumHand enumHand) {
      return new EnumActionResult(vapeInstance.getMappings().q8.n(this.getObject(), world.getObject(), entityPlayer.getObject(), enumHand.getObject()));
   }

   public TagList a() {
      return new TagList(MItemStack.H(vapeInstance.getMappings().q8, this.I));
   }

   public void k(EntityEquipmentSlot entityEquipmentSlot, BiConsumer biConsumer) {
      MItemStack.Q(vapeInstance.getMappings().q8, this.I, entityEquipmentSlot.getObject(), biConsumer);
   }

   private static Throwable a(Throwable throwable) {
      return throwable;
   }

   public int P() {
      return MItemStack.l(vapeInstance.getMappings().q8, this.I);
   }

   public Item getItem() {
      return new Item(vapeInstance.getMappings().q8.V(this.I));
   }

   public int L() {
      return MItemStack.v(vapeInstance.getMappings().q8, this.I);
   }

   public Object w(DataComponentType dataComponentType) {
      return MItemStack.A(vapeInstance.getMappings().q8, this.I, dataComponentType.getObject());
   }

   public boolean x$src$Z$1nwfctq() {
      if (ForgeVersion.MC_1_20_6.d()) {
         return EnchantmentHelper.Q(this);
      } else {
         TagCompound tagCompound = new TagCompound(this.l());
         return tagCompound.isNull() ? false : tagCompound.hasKeyOfType("ench", 9) || tagCompound.hasKeyOfType("Enchantments", 9);
      }
   }

   public int t() {
      return vapeInstance.getMappings().q8.n(this.I);
   }

   public int getStackSize() {
      return this.t();
   }

   public boolean e(ItemStack itemStack) {
      return this.W(itemStack) && this.x().equals(itemStack.x());
   }

   public String f() {
      return ForgeVersion.MC_1_21_0.d() ? this.getItem().A() : vapeInstance.getMappings().q8.F(this.getObject());
   }

   static {
      C(null);
   }
}
