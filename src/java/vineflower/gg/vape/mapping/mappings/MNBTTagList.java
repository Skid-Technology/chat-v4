package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MNBTTagList extends Mapping {
   private final MappingMethod F;
   public final MappingMethod r;
   private final MappingMethod I;
   private static String u;

   public static String s(MNBTTagList mNBTTagList, Object object, int n) {
      return mNBTTagList.o(object, n);
   }

   private int v(Object object) {
      return this.r.invokeInt(object);
   }

   public static void Z(String string) {
      u = string;
   }

   public static int R(MNBTTagList mNBTTagList, Object object) {
      return mNBTTagList.v(object);
   }

   public static String Y() {
      return u;
   }

   public static Object w(MNBTTagList mNBTTagList, Object object, int n) {
      return mNBTTagList.Z(object, n);
   }

   public MNBTTagList() {
      this(Y());
   }

   private MNBTTagList(String string) {
      super(MappedClasses.qt);
      if (string != null) {
         Class[] classArray = new Class[]{int.class};
         Class<String> clazz = String.class;
         boolean bl = true;
         String string2 = "getStringTagAt";
         this.F = this.Y(string2, bl, clazz, classArray);
         if (GuiComponent.getLegacyComponentState() == null) {
            Z("tnDLE");
         }

         this.r = null;
         this.I = null;
      } else {
         if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray = new Class[0];
            Class<Integer> clazz = int.class;
            boolean bl = true;
            String string3 = "size";
            this.r = this.Y(string3, bl, clazz, classArray);
            Class[] classArray2 = new Class[]{int.class};
            Class clazz2 = MappedClasses.Yg;
            boolean bl2 = true;
            String string4 = "getCompound";
            this.I = this.Y(string4, bl2, clazz2, classArray2);
            Class[] classArray3 = new Class[]{int.class};
            Class<String> clazz3 = String.class;
            boolean bl3 = true;
            String string5 = "getString";
            this.F = this.Y(string5, bl3, clazz3, classArray3);
         } else {
            Class[] classArray = new Class[0];
            Class<Integer> clazz = int.class;
            boolean bl = true;
            String string6 = "tagCount";
            this.r = this.Y(string6, bl, clazz, classArray);
            Class[] classArray4 = new Class[]{int.class};
            Class clazz4 = MappedClasses.Yg;
            boolean bl4 = true;
            String string7 = "getCompoundTagAt";
            this.I = this.Y(string7, bl4, clazz4, classArray4);
            Class[] classArray5 = new Class[]{int.class};
            Class<String> clazz5 = String.class;
            boolean bl5 = true;
            String string8 = "getStringTagAt";
            this.F = this.Y(string8, bl5, clazz5, classArray5);
         }

         if (GuiComponent.getLegacyComponentState() == null) {
            Z("tnDLE");
         }
      }
   }

   private Object Z(Object object, int n) {
      return this.I.invokeObject(object, n);
   }

   private String o(Object object, int n) {
      return ForgeVersion.MC_1_21_6.d() ? (String)this.F.invokeObject(object, n, "") : (String)this.F.invokeObject(object, n);
   }

   static {
      Z(null);
   }
}
