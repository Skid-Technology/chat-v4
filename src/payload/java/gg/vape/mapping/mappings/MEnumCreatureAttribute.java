package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.ForgeVersion;

public class MEnumCreatureAttribute extends Mapping {
   private MappingField Z;
   private MappingField B;
   private final MappingField S;
   private final MappingMethod a;
   private final MappingMethod G;

   private Object N(Object object, Object object2) {
      return this.a.invokeObject(object, object2);
   }

   public static Object X(MEnumCreatureAttribute mEnumCreatureAttribute, Object object, Object object2) {
      return mEnumCreatureAttribute.N(object, object2);
   }

   public static Object E(MEnumCreatureAttribute mEnumCreatureAttribute) {
      return mEnumCreatureAttribute.T();
   }

   public MEnumCreatureAttribute() {
      this(BlockData.W());
   }

   private MEnumCreatureAttribute(String[] stringArray) {
      super(MappedClasses.uB);
      if (stringArray != null) {
         if (ForgeVersion.MC_1_20_6.d()) {
            Class clazz = MappedClasses.Vo;
            boolean bl = true;
            String string = "type";
            this.Z = this.J(string, bl, clazz);
            Class<String> clazz2 = String.class;
            boolean bl2 = true;
            String string2 = "msgId";
            Class clazz3 = MappedClasses.Vz;
            this.S = this.registerInstanceFieldForOwner(clazz3, string2, bl2, clazz2);
            Class[] classArray = new Class[]{MappedClasses.Yl};
            Class clazz4 = MappedClasses.uB;
            boolean bl3 = true;
            String string3 = "playerAttack";
            Class clazz5 = MappedClasses.ZZ;
            this.a = this.registerInstanceMethodForOwner(clazz5, string3, bl3, clazz4, classArray);
         } else {
            Class<String> clazz = String.class;
            boolean bl = true;
            String string = "damageType";
            this.S = this.J(string, bl, clazz);
            Class[] classArray = new Class[]{MappedClasses.Yl};
            Class clazz6 = MappedClasses.uB;
            boolean bl4 = true;
            String string4 = "causePlayerDamage";
            this.a = this.registerStaticMethod(string4, bl4, clazz6, classArray);
         }

         if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray = new Class[0];
            Class clazz = MappedClasses.zc;
            boolean bl = true;
            String string = "getImmediateSource";
            this.G = this.Y(string, bl, clazz, classArray);
         } else {
            Class[] classArray = new Class[0];
            Class clazz = MappedClasses.zc;
            boolean bl = true;
            String string = "getSourceOfDamage";
            this.G = this.Y(string, bl, clazz, classArray);
         }

         if (ForgeVersion.MC_1_21_4.v()) {
            Class clazz = MappedClasses.uB;
            String string = ForgeVersion.c() >= 23 ? "FALL" : "fall";
            this.B = this.fieldBuilder(string, clazz).setStaticMember(true).buildField();
         }
      } else {
         Class[] classArray = new Class[]{MappedClasses.Yl};
         Class clazz = MappedClasses.uB;
         boolean bl = true;
         String string = "causePlayerDamage";
         this.a = this.registerStaticMethod(string, bl, clazz, classArray);
         if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray2 = new Class[0];
            Class clazz7 = MappedClasses.zc;
            boolean bl5 = true;
            String string5 = "getImmediateSource";
            this.Y(string5, bl5, clazz7, classArray2);
         }

         Class[] classArray3 = new Class[0];
         Class clazz8 = MappedClasses.zc;
         boolean bl6 = true;
         String string6 = "getSourceOfDamage";
         this.G = this.Y(string6, bl6, clazz8, classArray3);
         if (ForgeVersion.MC_1_21_4.v()) {
            Class clazz9 = MappedClasses.uB;
            String string7 = ForgeVersion.c() >= 23 ? "FALL" : "fall";
            this.B = this.fieldBuilder(string7, clazz9).setStaticMember(true).buildField();
         }

         this.S = null;
      }
   }

   private String B(Object object) {
      return (String)this.S.getObject(object);
   }

   private Object n(Object object) {
      return this.G.invokeObject(object);
   }

   private Object T() {
      return this.B.getObject(null);
   }

   private Object r(Object object) {
      return this.Z.getObject(object);
   }
}
