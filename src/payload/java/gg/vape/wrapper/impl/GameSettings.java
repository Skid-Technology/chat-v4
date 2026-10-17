package gg.vape.wrapper.impl;

import gg.vape.Vape;
import gg.vape.mapping.mappings.MGameSettings;
import gg.vape.wrapper.Wrapper;
import java.util.List;

public class GameSettings extends Wrapper {
   private static String[] L;

   public KeyBinding x$src$Lgg_vape_wrapper_impl_KeyBinding_$1cf7isg() {
      return new KeyBinding(MGameSettings.E(vapeInstance.getMappings().RM, this.getObject()));
   }

   public boolean k() {
      if (ForgeVersion.MC_1_20_6.d()) {
         try {
            GameSettingsValue gameSettingsValue = new GameSettingsValue(MGameSettings.R(vapeInstance.getMappings().RM, this.I));
            Object val = gameSettingsValue.i();
            if (val instanceof Boolean) {
               return (Boolean)val;
            }
         } catch (Throwable var3) {
         }

         return false;
      } else {
         return MGameSettings.L(vapeInstance.getMappings().RM, this.I);
      }
   }

   public void O(boolean bl) {
      if (ForgeVersion.MC_1_20_6.d()) {
         GameSettingsValue gameSettingsValue = new GameSettingsValue(MGameSettings.R(vapeInstance.getMappings().RM, this.I));
         gameSettingsValue.a(bl);
      } else {
         MGameSettings.z(vapeInstance.getMappings().RM, this.I, bl);
      }
   }

   public void y(float f) {
      if (ForgeVersion.MC_1_20_6.d()) {
         GameSettingsValue gameSettingsValue = new GameSettingsValue(MGameSettings.H$src$Ljava_lang_Object_$1jqua4d(vapeInstance.getMappings().RM, this.I));
         gameSettingsValue.f((double)f);
      } else {
         MGameSettings.U(vapeInstance.getMappings().RM, this.I, f);
      }
   }

   public boolean m$src$Z$1s8ei5l() {
      return MGameSettings.h$src$Z$1cffm5f(vapeInstance.getMappings().RM, this.I);
   }

   public KeyBinding y$src$Lgg_vape_wrapper_impl_KeyBinding_$1hvjjoh() {
      return new KeyBinding(MGameSettings.Y(vapeInstance.getMappings().RM, this.getObject()));
   }

   public KeyBinding getInventoryKey() {
      return this.j();
   }

   public KeyBinding getForwardKey() {
      return this.Y();
   }

   public KeyBinding getBackKey() {
      return this.s();
   }

   public KeyBinding getLeftKey() {
      return this.x$src$Lgg_vape_wrapper_impl_KeyBinding_$1cf7isg();
   }

   public KeyBinding getRightKey() {
      return this.g$src$Lgg_vape_wrapper_impl_KeyBinding_$qqn5n3();
   }

   public KeyBinding getJumpKey() {
      return this.O();
   }

   public KeyBinding getSneakKey() {
      return this.r();
   }

   public boolean Y$src$Z$1rxemad() {
      return ForgeVersion.MC_1_16_5.d() ? true : MGameSettings.F$src$Z$nptw01(vapeInstance.getMappings().RM, this.I);
   }

   public void P(boolean bl) {
      MGameSettings.m(vapeInstance.getMappings().RM, this.I, bl);
   }

   public List<String> f$src$Ljava_util_List_$1i0ug5l() {
      return MGameSettings.o(vapeInstance.getMappings().RM, this.I);
   }

   public KeyBinding Y() {
      return new KeyBinding(MGameSettings.l(vapeInstance.getMappings().RM, this.getObject()));
   }

   public int v() {
      if (ForgeVersion.MC_1_20_6.d()) {
         try {
            GameSettingsValue gameSettingsValue = new GameSettingsValue(MGameSettings.B(vapeInstance.getMappings().RM, this.I));
            Object val = gameSettingsValue.i();
            if (val instanceof Number) {
               return ((Number)val).intValue();
            }
         } catch (Throwable var3) {
         }

         return 0;
      } else {
         return MGameSettings.t(vapeInstance.getMappings().RM, this.I);
      }
   }

   public static void Y(String[] stringArray) {
      L = stringArray;
   }

   public KeyBinding F() {
      return new KeyBinding(MGameSettings.f(Vape.INSTANCE.getMappings().RM, this.getObject()));
   }

   public KeyBinding g$src$Lgg_vape_wrapper_impl_KeyBinding_$qqn5n3() {
      return new KeyBinding(MGameSettings.F(vapeInstance.getMappings().RM, this.getObject()));
   }

   public int x() {
      if (ForgeVersion.MC_1_16_5.d()) {
         Object object = MGameSettings.g(vapeInstance.getMappings().RM, this.I);
         int n = 0;

         for (PointOfView pointOfView : PointOfView.values()) {
            if (pointOfView.getObject() == object) {
               return n;
            }

            n++;
         }

         return 0;
      } else {
         return MGameSettings.A(vapeInstance.getMappings().RM, this.I);
      }
   }

   public float g() {
      return MGameSettings.s(vapeInstance.getMappings().RM, this.I);
   }

   public boolean M() {
      return MGameSettings.U$src$Z$16z50cw(vapeInstance.getMappings().RM, this.I);
   }

   public GameSettings(Object object) {
      super(object);
   }

   public KeyBinding O() {
      return new KeyBinding(MGameSettings.u(vapeInstance.getMappings().RM, this.getObject()));
   }

   public float y() {
      if (ForgeVersion.MC_1_20_6.d()) {
         try {
            GameSettingsValue gameSettingsValue = new GameSettingsValue(MGameSettings.k(vapeInstance.getMappings().RM, this.I));
            Object val = gameSettingsValue.i();
            if (val instanceof Number) {
               return ((Number)val).floatValue();
            }
         } catch (Throwable var3) {
         }

         return 70.0F;
      } else {
         return MGameSettings.V(vapeInstance.getMappings().RM, this.I);
      }
   }

   public void k(float f) {
      MGameSettings.c(vapeInstance.getMappings().RM, this.I, f);
   }

   public int d() {
      return GameSettingsGuiScaleValue.getAntialiasingLevel();
   }

   public KeyBinding getKeyBindUseItem() {
      return this.b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362();
   }

   public KeyBinding b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362() {
      return new KeyBinding(MGameSettings.U(Vape.INSTANCE.getMappings().RM, this.getObject()));
   }

   public KeyBinding j() {
      return new KeyBinding(MGameSettings.p(vapeInstance.getMappings().RM, this.getObject()));
   }

   public KeyBinding v$src$Lgg_vape_wrapper_impl_KeyBinding_$11ijh0e() {
      return new KeyBinding(MGameSettings.s$src$Ljava_lang_Object_$4nr0aa(vapeInstance.getMappings().RM, this.I));
   }

   public void R(int n) {
      if (ForgeVersion.MC_1_20_6.d()) {
         GameSettingsValue gameSettingsValue = new GameSettingsValue(MGameSettings.r(vapeInstance.getMappings().RM, this.getObject()));
         gameSettingsValue.a(n);
      } else {
         MGameSettings.D(vapeInstance.getMappings().RM, this.I, n);
      }
   }

   public KeyBinding d$src$Lgg_vape_wrapper_impl_KeyBinding_$adn2z0() {
      return new KeyBinding(MGameSettings.v(vapeInstance.getMappings().RM, this.getObject()));
   }

   public boolean U() {
      return MGameSettings.D(vapeInstance.getMappings().RM, this.I);
   }

   public KeyBinding s() {
      return new KeyBinding(MGameSettings.y(vapeInstance.getMappings().RM, this.getObject()));
   }

   public int T() {
      if (ForgeVersion.MC_1_20_6.d()) {
         GameSettingsValue gameSettingsValue = new GameSettingsValue(MGameSettings.r(vapeInstance.getMappings().RM, this.getObject()));
         return (Integer)gameSettingsValue.i();
      } else {
         return MGameSettings.h(vapeInstance.getMappings().RM, this.getObject());
      }
   }

   public void m(boolean bl) {
      MGameSettings.O(vapeInstance.getMappings().RM, this.I, bl);
   }

   public float f() {
      if (ForgeVersion.MC_1_20_6.d()) {
         try {
            GameSettingsValue gameSettingsValue = new GameSettingsValue(MGameSettings.w(vapeInstance.getMappings().RM, this.I));
            Object val = gameSettingsValue.i();
            if (val instanceof Number) {
               return ((Number)val).floatValue();
            }
         } catch (Throwable var3) {
         }

         return 1.0F;
      } else {
         return MGameSettings.H(vapeInstance.getMappings().RM, this.I);
      }
   }

   public void F(boolean bl) {
      MGameSettings.Q(vapeInstance.getMappings().RM, this.I, bl);
   }

   public void I(int n) {
      if (ForgeVersion.MC_1_16_5.d()) {
         PointOfView pointOfView = PointOfView.values()[n];
         MGameSettings.E(vapeInstance.getMappings().RM, this.I, pointOfView.getObject());
      } else {
         MGameSettings.r(vapeInstance.getMappings().RM, this.I, n);
      }
   }

   public KeyBinding r() {
      return new KeyBinding(MGameSettings.X(vapeInstance.getMappings().RM, this.I));
   }

   public static String[] E() {
      return L;
   }

   public float b() {
      if (ForgeVersion.MC_1_20_6.d()) {
         try {
            GameSettingsValue gameSettingsValue = new GameSettingsValue(MGameSettings.H$src$Ljava_lang_Object_$1jqua4d(vapeInstance.getMappings().RM, this.I));
            Object val = gameSettingsValue.i();
            if (val instanceof Number) {
               return ((Number)val).floatValue();
            }
         } catch (Throwable var3) {
         }

         return 1.0F;
      } else {
         return MGameSettings.C(vapeInstance.getMappings().RM, this.I);
      }
   }

   static {
      if (E() == null) {
         Y(new String[1]);
      }
   }
}
