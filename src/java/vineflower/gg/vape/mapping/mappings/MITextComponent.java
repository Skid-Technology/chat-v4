package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.List;

public class MITextComponent extends Mapping {
   private final MappingMethod Q;
   private MappingMethod S;
   private MappingMethod C;
   private final MappingMethod v;
   private MappingMethod H;
   private final MappingMethod g;
   private MappingMethod L;
   private MappingMethod l;

   private String H(Object object) {
      return (String)this.Q.invokeObject(object);
   }

   private String y(Object object) {
      return (String)this.g.invokeObject(object);
   }

   private Object O(Object object) {
      return this.C.invokeObject(object);
   }

   public static Object y(MITextComponent mITextComponent, Object object) {
      return mITextComponent.O(object);
   }

   public static Object r(MITextComponent mITextComponent, Object object) {
      return mITextComponent.o(object);
   }

   private Object A(String string) {
      return this.S.invokeObject(null, string);
   }

   public static String b(MITextComponent mITextComponent, Object object) {
      return mITextComponent.H(object);
   }

   public static Object J(MITextComponent mITextComponent, String string) {
      return mITextComponent.w(string);
   }

   public static Object e(MITextComponent mITextComponent, Object object) {
      return mITextComponent.l(object);
   }

   private Object l(Object object) {
      return this.H.invokeObject(object);
   }

   public static Object f(MITextComponent mITextComponent, String string) {
      return mITextComponent.A(string);
   }

   private List<Object> B(Object object) {
      return (List<Object>)this.v.invokeObject(object);
   }

   public MITextComponent() {
      this(BlockData.W());
   }

   private MITextComponent(String[] stringArray) {
      super(MappedClasses.Yr);
      if (ForgeVersion.MC_1_21_0.d()) {
         Class[] classArray = new Class[]{String.class};
         Class clazz = MappedClasses.qQ;
         boolean bl = true;
         String string = "translatable";
         this.l = this.registerStaticMethod(string, bl, clazz, classArray);
      }

      if (ForgeVersion.MC_1_20_6.d()) {
         Class[] classArray = new Class[0];
         Class clazz = MappedClasses.YT;
         boolean bl = true;
         String string = "getContents";
         this.L = this.Y(string, bl, clazz, classArray);
         Class[] classArray2 = new Class[0];
         Class clazz2 = MappedClasses.Va;
         boolean bl2 = true;
         String string2 = "getStyle";
         this.H = this.Y(string2, bl2, clazz2, classArray2);
         Class[] classArray3 = new Class[]{String.class};
         Class<?> clazz3 = MappedClasses.uM;
         boolean bl3 = true;
         String string3 = "literal";
         this.S = this.registerStaticMethod(string3, bl3, clazz3, classArray3);
      }

      if (ForgeVersion.MC_1_16_5.d()) {
         Class[] classArray = new Class[0];
         Class<String> clazz = String.class;
         boolean bl = ForgeVersion.MC_1_20_6.v();
         String string = "getString";
         this.Q = this.Y(string, bl, clazz, classArray);
         if (ForgeVersion.MC_1_20_6.d()) {
            Class[] classArray4 = new Class[0];
            Class clazz4 = MappedClasses.YT;
            boolean bl4 = true;
            String string4 = "getContents";
            this.g = this.Y(string4, bl4, clazz4, classArray4);
         } else {
            Class[] classArray5 = new Class[0];
            Class<String> clazz5 = String.class;
            boolean bl5 = true;
            String string5 = "getUnformattedComponentText";
            this.g = this.Y(string5, bl5, clazz5, classArray5);
         }
      } else {
         Class[] classArray = new Class[0];
         Class<String> clazz = String.class;
         boolean bl = true;
         String string = "getFormattedText";
         this.Q = this.Y(string, bl, clazz, classArray);
         Class[] classArray6 = new Class[0];
         Class<String> clazz6 = String.class;
         boolean bl6 = true;
         String string6 = "getUnformattedText";
         this.g = this.Y(string6, bl6, clazz6, classArray6);
      }

      Class[] classArray = new Class[0];
      Class<List> clazz = List.class;
      boolean bl = true;
      String string = "getSiblings";
      this.v = this.Y(string, bl, clazz, classArray);
      if (ForgeVersion.MC_1_16_5.d()) {
         Class[] classArray7 = new Class[0];
         Class clazz7 = MappedClasses.YO;
         boolean bl7 = true;
         String string7 = "copy";
         this.C = this.Y(string7, bl7, clazz7, classArray7);
      }
   }

   private Object o(Object object) {
      return this.L.invokeObject(object);
   }

   private Object w(String string) {
      return this.l.invokeObject(null, string);
   }

   public static List V(MITextComponent mITextComponent, Object object) {
      return mITextComponent.B(object);
   }

   public static String A(MITextComponent mITextComponent, Object object) {
      return mITextComponent.y(object);
   }
}
