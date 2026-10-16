package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MItem;
import gg.vape.wrapper.Wrapper;
import java.util.List;
import java.util.Map;

public class Item extends Wrapper {
   private static String T;

   public void D(Item item, List list) {
      if (!ForgeVersion.MC_1_20_6.d()) {
         MItem.V(vapeInstance.getMappings().CF, this.I, item.getObject(), TooltipFlagBridge.searchTab().getObject(), list);
      }
   }

   private static Throwable a(Throwable throwable) {
      return throwable;
   }

   public static Item T(int n) {
      return new Item(MItem.W(vapeInstance.getMappings().CF, n));
   }

   public String O() {
      return MItem.k(vapeInstance.getMappings().CF, this.getObject());
   }

   public static Map y() {
      return ForgeVersion.MC_1_8_9.d() ? MItem.U(vapeInstance.getMappings().CF) : null;
   }

   public boolean p() {
      return ForgeVersion.MC_1_16_5.d() ? false : MItem.N(vapeInstance.getMappings().CF, this.I);
   }

   public Holder Q() {
      return new Holder(MItem.F(vapeInstance.getMappings().CF, this.I));
   }

   public int P() {
      return f(this);
   }

   public Item(Object object) {
      super(object);
   }

   public static RegistryNamespaced Y() {
      return new RegistryNamespaced(MItem.Z(vapeInstance.getMappings().CF));
   }

   public static String L() {
      return T;
   }

   public static int f(Item item) {
      return MItem.l(vapeInstance.getMappings().CF, item.getObject());
   }

   public static Item L(String string) {
      if (ForgeVersion.MC_1_16_5.d()) {
         ResourceLocation resourceLocation = ResourceLocation.create(string);
         Object object = Y().S(resourceLocation.getObject());
         return object == null ? null : new Item(object);
      } else if (ForgeVersion.MC_1_7_10.L()) {
         try {
            int n = Integer.parseInt(string);
            return T(n);
         } catch (NumberFormatException var3) {
            return null;
         }
      } else {
         Object object = MItem.r(vapeInstance.getMappings().CF, string);
         return object == null ? null : new Item(object);
      }
   }

   public int a() {
      return ForgeVersion.MC_1_20_6.d() ? this.b().y() : MItem.d(vapeInstance.getMappings().CF, this.I);
   }

   public DataComponentMap g() {
      return new DataComponentMap(MItem.K(vapeInstance.getMappings().CF, this.I));
   }

   public int I(ItemStack itemStack, EntityLivingBase entityLivingBase) {
      return ForgeVersion.MC_1_21_4.d()
         ? vapeInstance.getMappings().CF.c(this.I, itemStack.getObject(), entityLivingBase.getObject())
         : MItem.Q(vapeInstance.getMappings().CF, this.I, itemStack.getObject());
   }

   public String A() {
      return MItem.X(vapeInstance.getMappings().CF, this.I);
   }

   public String h(ItemStack itemStack) {
      if (ForgeVersion.MC_1_16_5.d()) {
         ITextComponent iTextComponent = new ITextComponent(MItem.h(vapeInstance.getMappings().CF, this.getObject(), itemStack.getObject()));
         return iTextComponent.getFormattedText();
      } else {
         return MItem.h(vapeInstance.getMappings().CF, this.getObject(), itemStack.getObject()).toString();
      }
   }

   public static void H(String string) {
      T = string;
   }

   public String Y$src$Ljava_lang_String_$uel3xb() {
      if (ForgeVersion.MC_1_21_0.d()) {
         return ITextComponent.P(this.A()).getFormattedText();
      } else {
         ITextComponent iTextComponent = new ITextComponent(MItem.i(vapeInstance.getMappings().CF, this.I));
         return iTextComponent.getFormattedText();
      }
   }

   public ItemStack b() {
      try {
         return new ItemStack(MItem.z(vapeInstance.getMappings().CF, this.I));
      } catch (Throwable var2) {
         if (ForgeVersion.MC_26_1.d()) {
            return ItemStack.S(this);
         } else if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         } else if (var2 instanceof Error) {
            throw (Error)var2;
         } else {
            throw new RuntimeException(var2);
         }
      }
   }

   public String getItemStackDisplayName(ItemStack itemStack) {
      return MItem.o(vapeInstance.getMappings().CF, this.getObject(), itemStack.getObject());
   }

   static {
      if (L() == null) {
         H("fCfOlb");
      }
   }
}
