package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ScaledResolution;

public class MGuiScreen extends Mapping {
   private MappingField e;
   private final MappingField Z;
   private final MappingField D;

   private int f(Object object) {
      return this.Z.getInt(object);
   }

   private int A(Object object) {
      return this.D.getInt(object);
   }

   public Object S(Object object) {
      return this.e.getObject(object);
   }

   public MGuiScreen() {
      this(ScaledResolution.q());
   }

   private MGuiScreen(int n) {
      super(MappedClasses.VW);
      if (n != 0) {
         Class<Integer> clazz = int.class;
         boolean bl = true;
         String string = "width";
         this.Z = this.J(string, bl, clazz);
         Class<Integer> clazz2 = int.class;
         boolean bl2 = true;
         String string2 = "height";
         this.e = this.J(string2, bl2, clazz2);
         this.D = null;
      } else {
         Class<Integer> clazz = int.class;
         boolean bl = true;
         String string = "width";
         this.Z = this.J(string, bl, clazz);
         Class<Integer> clazz3 = int.class;
         boolean bl3 = true;
         String string3 = "height";
         this.D = this.J(string3, bl3, clazz3);
         if (ForgeVersion.MC_1_16_5.d()) {
            Class clazz4 = MappedClasses.Yr;
            boolean bl4 = true;
            String string4 = "title";
            this.e = this.J(string4, bl4, clazz4);
         }
      }
   }

   public static int U(MGuiScreen mGuiScreen, Object object) {
      return mGuiScreen.f(object);
   }

   public static int D(MGuiScreen mGuiScreen, Object object) {
      return mGuiScreen.A(object);
   }
}
