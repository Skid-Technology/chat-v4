package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MMainWindow extends Mapping {
   private MappingField O;
   private MappingField G;
   private MappingField K;
   private MappingField i;
   private MappingField Y;
   private MappingField a;

   public static int C(MMainWindow mMainWindow, Object object) {
      return mMainWindow.a(object);
   }

   private float U(Object object) {
      return this.a == null ? 0.0F : this.a.getFloat(object);
   }

   private float g(Object object) {
      return this.K.getFloat(object);
   }

   private int a(Object object) {
      return this.Y == null ? 0 : this.Y.getInt(object);
   }

   private float F(Object object) {
      return this.O.getFloat(object);
   }

   public static float e(MMainWindow mMainWindow, Object object) {
      return mMainWindow.F(object);
   }

   public static float E(MMainWindow mMainWindow, Object object) {
      return mMainWindow.r(object);
   }

   private int o(Object object) {
      return this.G == null ? 0 : this.G.getInt(object);
   }

   public static int T(MMainWindow mMainWindow, Object object) {
      return mMainWindow.o(object);
   }

   public static float W(MMainWindow mMainWindow, Object object) {
      return mMainWindow.U(object);
   }

   public MMainWindow() {
      this(MTextureManager.getInitialControlFlowState());
   }

   private MMainWindow(int n) {
      super(MappedClasses.Yc);
      if (n != 0) {
         Class<Float> clazz = float.class;
         boolean bl = true;
         String string = "v";
         this.O = this.J(string, bl, clazz);
         if (GuiComponent.getLegacyComponentState() == null) {
            int var19 = n + 1;
            MTextureManager.setTextureManagerControlFlowState(var19);
         }
      } else {
         if (ForgeVersion.MC_26_1.d()) {
            Class<Float> clazz = float.class;
            boolean bl = true;
            String string = "u0";
            this.K = this.J(string, bl, clazz);
            Class<Float> clazz2 = float.class;
            boolean bl2 = true;
            String string2 = "v0";
            this.O = this.J(string2, bl2, clazz2);
            Class<Float> clazz3 = float.class;
            boolean bl3 = true;
            String string3 = "u1";
            this.i = this.J(string3, bl3, clazz3);
            Class<Float> clazz4 = float.class;
            boolean bl4 = true;
            String string4 = "v1";
            this.a = this.J(string4, bl4, clazz4);
         } else {
            Class<Integer> clazz = int.class;
            boolean bl = true;
            String string = "x";
            this.G = this.J(string, bl, clazz);
            Class<Integer> clazz5 = int.class;
            boolean bl5 = true;
            String string5 = "y";
            this.Y = this.J(string5, bl5, clazz5);
            Class<Float> clazz6 = float.class;
            boolean bl6 = true;
            String string6 = "u";
            this.K = this.J(string6, bl6, clazz6);
            Class<Float> clazz7 = float.class;
            boolean bl7 = true;
            String string7 = "v";
            this.O = this.J(string7, bl7, clazz7);
         }

         if (GuiComponent.getLegacyComponentState() == null) {
            int n2 = n + 1;
            MTextureManager.setTextureManagerControlFlowState(n2);
         }
      }
   }

   public static float y(MMainWindow mMainWindow, Object object) {
      return mMainWindow.g(object);
   }

   private float r(Object object) {
      return this.i == null ? 0.0F : this.i.getFloat(object);
   }
}
