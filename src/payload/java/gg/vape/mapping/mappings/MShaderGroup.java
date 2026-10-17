package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.List;

public class MShaderGroup extends Mapping {
   private MappingMethod K;
   private final MappingField Y;
   private static String[] k;
   private MappingMethod r;

   public MShaderGroup() {
      this(V());
   }

   private MShaderGroup(String[] stringArray) {
      super(MappedClasses.Fo);
      if (stringArray != null) {
         Class<List> clazz = List.class;
         boolean bl = true;
         String string = "listShaders";
         this.Y = this.J(string, bl, clazz);
         if (ForgeVersion.MC_1_21_4.d()) {
            this.K = null;
         } else if (ForgeVersion.MC_1_20_6.d()) {
            Class[] classArray = new Class[]{MappedClasses.Dt, MappedClasses.Z5, MappedClasses.ll, MappedClasses.zC};
            Class<Void> clazz2 = void.class;
            boolean bl2 = false;
            String string2 = "<init>";
            this.K = this.Y(string2, bl2, clazz2, classArray);
         } else {
            Class[] classArray = new Class[]{MappedClasses.Dt, MappedClasses.qY, MappedClasses.ll, MappedClasses.zC};
            Class<Void> clazz3 = void.class;
            boolean bl3 = false;
            String string3 = "<init>";
            this.K = this.Y(string3, bl3, clazz3, classArray);
         }

         if (!ForgeVersion.MC_1_21_0.d()) {
            Class[] classArray = new Class[]{int.class, int.class};
            Class<Void> clazz4 = void.class;
            boolean bl4 = true;
            String string4 = "createBindFramebuffers";
            this.r = this.Y(string4, bl4, clazz4, classArray);
         }

         if (GuiComponent.getLegacyComponentState() == null) {
            H(new String[2]);
         }
      } else {
         Class<List> clazzx = List.class;
         boolean blx = true;
         String stringx = "listShaders";
         this.Y = this.J(stringx, blx, clazzx);
         if (ForgeVersion.MC_1_21_4.d()) {
            Class[] classArray = new Class[]{MappedClasses.Dt, MappedClasses.Z5, MappedClasses.ll, MappedClasses.zC};
            Class<Void> clazz5 = void.class;
            boolean bl5 = false;
            String string5 = "<init>";
            this.K = this.Y(string5, bl5, clazz5, classArray);
         }

         Class[] classArray = new Class[]{MappedClasses.Dt, MappedClasses.qY, MappedClasses.ll, MappedClasses.zC};
         Class<Void> clazz6 = void.class;
         boolean bl6 = false;
         String string6 = "<init>";
         this.K = this.Y(string6, bl6, clazz6, classArray);
         if (!ForgeVersion.MC_1_21_0.d()) {
            Class[] classArray2 = new Class[]{int.class, int.class};
            Class<Void> clazz7 = void.class;
            boolean bl7 = true;
            String string7 = "createBindFramebuffers";
            this.r = this.Y(string7, bl7, clazz7, classArray2);
         }

         if (GuiComponent.getLegacyComponentState() == null) {
            H(new String[2]);
         }
      }
   }

   public void G(Object object, int n, int n2) {
      this.r.invokeVoid(object, n, n2);
   }

   public static String[] V() {
      return k;
   }

   public Object k(Object object, Object object2, Object object3, Object object4) {
      return this.K == null ? null : this.K.newInstance(object, object2, object3, object4);
   }

   public static void H(String[] stringArray) {
      k = stringArray;
   }

   public List h(Object object) {
      return (List)this.Y.getObject(object);
   }

   static {
      H(new String[3]);
   }
}
