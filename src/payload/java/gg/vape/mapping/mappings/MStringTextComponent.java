package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MStringTextComponent extends Mapping {
   private final MappingMethod I;
   private MappingMethod P;
   private MappingMethod E;

   private Object n(String string) {
      return this.E.invokeObject(null, string);
   }

   public static Object s(MStringTextComponent mStringTextComponent, String string) {
      return mStringTextComponent.n(string);
   }

   public Object i(String string) {
      return this.I.newInstance(string);
   }

   public String W(Object object) {
      return (String)this.P.invokeObject(object);
   }

   public MStringTextComponent() {
      this(MMutableTextComponent.e());
   }

   private MStringTextComponent(int[] nArray) {
      super(MappedClasses.qT);
      if (nArray != null) {
         Class[] classArray = new Class[]{String.class};
         Class<Void> clazz = void.class;
         boolean bl = false;
         String string = "<init>";
         this.I = this.Y(string, bl, clazz, classArray);
         if (ForgeVersion.MC_1_20_6.d()) {
            Class[] classArray2 = new Class[0];
            Class<String> clazz2 = String.class;
            boolean bl2 = true;
            String string2 = "getText";
            this.P = this.Y(string2, bl2, clazz2, classArray2);
         }

         if (GuiComponent.getLegacyComponentState() == null) {
            MMutableTextComponent.U(new int[4]);
         }
      } else {
         Class[] classArrayx = new Class[]{String.class};
         Class<Void> clazzx = void.class;
         boolean blx = false;
         String stringx = "<init>";
         this.I = this.Y(stringx, blx, clazzx, classArrayx);
         if (ForgeVersion.MC_1_20_6.d()) {
            Class[] classArray3 = new Class[]{String.class};
            Class<?> clazz3 = MappedClasses.YD;
            String string3 = "create";
            this.E = this.methodBuilder(string3, clazz3, classArray3).setStaticMember(true).setOwnerClass(MappedClasses.YD).buildMethod();
            Class[] classArray4 = new Class[0];
            Class<String> clazz4 = String.class;
            String string4 = "text";
            this.P = this.methodBuilder(string4, clazz4, classArray4).setOwnerClass(MappedClasses.YD).buildMethod();
         } else if (ForgeVersion.MC_1_16_5_ACTUAL.d()) {
            Class[] classArray5 = new Class[0];
            Class<String> clazz5 = String.class;
            boolean bl3 = true;
            String string5 = "getText";
            this.P = this.Y(string5, bl3, clazz5, classArray5);
         }

         if (GuiComponent.getLegacyComponentState() == null) {
            MMutableTextComponent.U(new int[4]);
         }
      }
   }
}
