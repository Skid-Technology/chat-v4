package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public class MTextComponentTranslation extends Mapping {
   private final MappingMethod i;
   private MappingField j;
   private MappingMethod O;
   private MappingMethod B;
   private MappingMethod p;
   private MappingMethod T;

   public static String a(MTextComponentTranslation mTextComponentTranslation, Object object) {
      return mTextComponentTranslation.k$src$Ljava_lang_String_$1srlxuy(object);
   }

   public List k(Object object) {
      return (List)this.j.getObject(object);
   }

   private Object[] d(Object object) {
      return this.B.invokeObjectArray(object);
   }

   private String R(Object object) {
      return (String)this.O.invokeObject(object);
   }

   public static Object B(MTextComponentTranslation mTextComponentTranslation, String string, Object[] objectArray) {
      return mTextComponentTranslation.g(string, objectArray);
   }

   public MTextComponentTranslation() {
      this(BlockData.W());
   }

   private MTextComponentTranslation(String[] stringArray) {
      super(MappedClasses.ux);
      Class<List> clazz = List.class;
      boolean bl = true;
      String string = "children";
      this.j = this.J(string, bl, clazz);
      if (ForgeVersion.MC_1_20_6.d()) {
         Class[] classArray = new Class[]{String.class, String.class, Object[].class};
         Class<Void> clazz2 = void.class;
         boolean bl2 = false;
         String string2 = "<init>";
         this.i = this.Y(string2, bl2, clazz2, classArray);
      } else {
         Class[] classArray = new Class[]{String.class, Object[].class};
         Class<Void> clazz3 = void.class;
         boolean bl3 = false;
         String string3 = "<init>";
         this.i = this.Y(string3, bl3, clazz3, classArray);
      }

      if (ForgeVersion.MC_1_16_5.d()) {
         Class[] classArray = new Class[0];
         Class<String> clazz4 = String.class;
         boolean bl4 = true;
         String string4 = "getKey";
         this.O = this.Y(string4, bl4, clazz4, classArray);
         Class[] classArray2 = new Class[0];
         Class<Object[]> clazz5 = Object[].class;
         boolean bl5 = true;
         String string5 = "getArgs";
         this.B = this.Y(string5, bl5, clazz5, classArray2);
      }

      if (ForgeVersion.MC_1_20_6.d()) {
         Class[] classArray = new Class[]{String.class, String.class, Object[].class};
         this.T = this.registerConstructor(classArray);
         Class[] classArray3 = new Class[0];
         Class<String> clazz6 = String.class;
         boolean bl6 = true;
         String string6 = "getFallback";
         this.p = this.Y(string6, bl6, clazz6, classArray3);
      }
   }

   public static Object k(MTextComponentTranslation mTextComponentTranslation, String string, String string2, Object[] objectArray) {
      return mTextComponentTranslation.J(string, string2, objectArray);
   }

   private String k$src$Ljava_lang_String_$1srlxuy(Object object) {
      return (String)this.p.invokeObject(object);
   }

   public static Object[] x(MTextComponentTranslation mTextComponentTranslation, Object object) {
      return mTextComponentTranslation.d(object);
   }

   private Object J(String string, @Nullable String string2, Object[] objectArray) {
      return this.T.newInstance(string, string2, objectArray);
   }

   public static String C(MTextComponentTranslation mTextComponentTranslation, Object object) {
      return mTextComponentTranslation.R(object);
   }

   private Object g(String string, Object... objectArray) {
      return ForgeVersion.MC_1_20_6.d() ? this.i.newInstance(string, null, objectArray) : this.i.newInstance(string, objectArray);
   }
}
