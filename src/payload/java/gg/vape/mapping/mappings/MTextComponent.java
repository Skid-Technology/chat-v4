package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MTextComponent extends Mapping {
   private MappingMethod h;
   private MappingMethod N;
   private MappingMethod Q;

   public MTextComponent() {
      this(MMutableTextComponent.e());
   }

   private MTextComponent(int[] nArray) {
      super(MappedClasses.YO);
      if (nArray != null) {
         GuiComponent.setLegacyComponentState(new GuiComponent[2]);
         Class[] classArray = new Class[0];
         Class<String> clazz = String.class;
         boolean bl = false;
         String string = "getString";
         this.h = this.Y(string, bl, clazz, classArray);
         Class[] classArray2 = new Class[0];
         Class clazz2 = MappedClasses.t;
         boolean bl2 = true;
         String string2 = "getVisualOrderText";
         Class clazz3 = MappedClasses.Yr;
         this.N = this.registerInstanceMethodForOwner(clazz3, string2, bl2, clazz2, classArray2);
      } else {
         if (ForgeVersion.MC_1_20_6.d()) {
            Class[] classArray = new Class[0];
            Class<String> clazz = String.class;
            boolean bl = false;
            String string = "toString";
            this.h = this.Y(string, bl, clazz, classArray);
            Class[] classArray3 = new Class[]{MappedClasses.YT};
            Class clazz4 = MappedClasses.YO;
            boolean bl3 = true;
            String string3 = "create";
            this.Q = this.registerStaticMethod(string3, bl3, clazz4, classArray3);
         } else {
            Class[] classArray = new Class[0];
            Class<String> clazz = String.class;
            boolean bl = false;
            String string = "getString";
            this.h = this.Y(string, bl, clazz, classArray);
         }

         Class[] classArray = new Class[0];
         Class clazz = MappedClasses.t;
         boolean bl = true;
         String string = "getVisualOrderText";
         Class clazz5 = MappedClasses.Yr;
         this.N = this.registerInstanceMethodForOwner(clazz5, string, bl, clazz, classArray);
      }
   }

   private Object r(Object object) {
      return this.Q.invokeObject(null, object);
   }

   public static Object n(MTextComponent mTextComponent, Object object) {
      return mTextComponent.r(object);
   }

   public static Object B(MTextComponent mTextComponent, Object object) {
      return mTextComponent.n(object);
   }

   private Object Z(Object object) {
      return this.h.invokeObject(object);
   }

   private Object n(Object object) {
      return this.h.invokeObject(object);
   }

   public static Object s(MTextComponent mTextComponent, Object object) {
      return mTextComponent.Z(object);
   }
}
