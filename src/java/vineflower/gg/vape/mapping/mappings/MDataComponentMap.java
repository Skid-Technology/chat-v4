package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.Set;

public class MDataComponentMap extends Mapping {
   private MappingMethod D;
   private MappingField T;
   private MappingMethod F;
   private MappingMethod j;
   private static boolean n;

   public boolean V(Object object, Object object2) {
      return this.D.invokeBoolean(object, object2);
   }

   public static boolean N() {
      boolean bl = v();
      return false;
   }

   public Object j(Object object, Object object2) {
      return this.F.invokeObject(object, object2);
   }

   private Object N$src$Ljava_lang_Object_$1l5xr4h() {
      return this.T.getObject(null);
   }

   public static Object m(MDataComponentMap mDataComponentMap) {
      return mDataComponentMap.N$src$Ljava_lang_Object_$1l5xr4h();
   }

   public static Object w(MDataComponentMap mDataComponentMap, Object object) {
      return mDataComponentMap.l(object);
   }

   public MDataComponentMap() {
      this(N());
   }

   private MDataComponentMap(boolean bl) {
      super(MappedClasses.zD);
      if (bl) {
         GuiComponent.setLegacyComponentState(new GuiComponent[5]);
         Class[] classArray = new Class[]{MappedClasses.Fz};
         Class<Object> clazz = Object.class;
         boolean bl2 = true;
         String string = "get";
         this.F = this.Y(string, bl2, clazz, classArray);
         Class[] classArray2 = new Class[0];
         Class<Set> clazz2 = Set.class;
         boolean bl3 = true;
         String string2 = "keySet";
         this.j = this.Y(string2, bl3, clazz2, classArray2);
         Class[] classArray3 = new Class[]{MappedClasses.Fz};
         Class<Boolean> clazz3 = boolean.class;
         boolean bl4 = true;
         String string3 = "has";
         this.D = this.Y(string3, bl4, clazz3, classArray3);
         Class clazz4 = MappedClasses.zD;
         boolean bl5 = true;
         String string4 = "EMPTY";
         this.T = this.registerStaticField(string4, bl5, clazz4);
      } else {
         if (ForgeVersion.MC_1_21_6.d()) {
            Class[] classArray = new Class[]{MappedClasses.Fz};
            Class<Object> clazz = Object.class;
            boolean bl6 = true;
            String string = "get";
            Class clazz5 = MappedClasses.uS;
            this.F = this.registerInstanceMethodForOwner(clazz5, string, bl6, clazz, classArray);
         } else {
            Class[] classArray = new Class[]{MappedClasses.Fz};
            Class<Object> clazz = Object.class;
            boolean bl7 = true;
            String string = "get";
            this.F = this.Y(string, bl7, clazz, classArray);
         }

         Class[] classArray = new Class[0];
         Class<Set> clazz = Set.class;
         boolean bl8 = true;
         String string = "keySet";
         this.j = this.Y(string, bl8, clazz, classArray);
         Class[] classArray4 = new Class[]{MappedClasses.Fz};
         Class<Boolean> clazz6 = boolean.class;
         boolean bl9 = true;
         String string5 = "has";
         this.D = this.Y(string5, bl9, clazz6, classArray4);
         Class clazz7 = MappedClasses.zD;
         boolean bl10 = true;
         String string6 = "EMPTY";
         this.T = this.registerStaticField(string6, bl10, clazz7);
      }
   }

   public static boolean v() {
      return n;
   }

   private Object l(Object object) {
      return this.j.invokeObject(object);
   }

   public static void d(boolean bl) {
      n = bl;
   }

   static {
      d(true);
   }
}
