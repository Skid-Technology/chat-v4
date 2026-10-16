package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.Set;

public class MRegistrySimple extends Mapping {
   private final MappingMethod e;
   private final MappingMethod c;

   private Object f(Object object, Object object2) {
      return this.e.invokeObject(object, object2);
   }

   public Set g(Object object) {
      return (Set)this.c.invokeObject(object);
   }

   public MRegistrySimple() {
      this(BlockData.W());
   }

   private MRegistrySimple(String[] stringArray) {
      super(MappedClasses.Vs);
      if (stringArray != null) {
         if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray = new Class[0];
            Class<Set> clazz = Set.class;
            boolean bl = true;
            String string = "keySet";
            Class clazz2 = MappedClasses.Fk;
            this.c = this.registerInstanceMethodForOwner(clazz2, string, bl, clazz, classArray);
            Class[] classArray2 = new Class[]{MappedClasses.zC};
            Class<Object> clazz3 = Object.class;
            String string2 = "getOrDefault";
            this.e = this.methodBuilder(string2, clazz3, classArray2)
               .setNameForVersion(ForgeVersion.MC_1_21_4.n(), "getValue")
               .setNameForVersion(ForgeVersion.MC_1_17.n(), "get")
               .setOwnerClassForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.Fk)
               .buildMethod();
         } else {
            Class[] classArray = new Class[0];
            Class<Set> clazz = Set.class;
            boolean bl = true;
            String string = "getKeys";
            this.c = this.Y(string, bl, clazz, classArray);
            Class[] classArray3 = new Class[]{Object.class};
            Class<Object> clazz4 = Object.class;
            boolean bl2 = true;
            String string3 = "getObject";
            this.e = this.Y(string3, bl2, clazz4, classArray3);
         }
      } else {
         Class[] classArray = new Class[]{Object.class};
         Class<Object> clazz = Object.class;
         boolean bl = true;
         String string = "getObject";
         this.e = this.Y(string, bl, clazz, classArray);
         this.c = null;
      }
   }

   public static Object s(MRegistrySimple mRegistrySimple, Object object, Object object2) {
      return mRegistrySimple.f(object, object2);
   }
}
