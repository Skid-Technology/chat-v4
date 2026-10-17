package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MModelPlayer extends Mapping {
   private final MappingField q;
   private final MappingField E;
   private final MappingMethod n;
   private final MappingMethod x;
   private final MappingMethod P;
   private final MappingField y;
   private static String m;
   private final MappingMethod H;
   private final MappingField b;
   private final MappingField M;
   private final MappingMethod u;

   private boolean h(Object object) {
      return this.q.getBoolean(object);
   }

   private boolean r(Object object) {
      return this.b.getBoolean(object);
   }

   private void D(Object object, boolean bl) {
      this.E.setBoolean(object, bl);
   }

   public static float Y(MModelPlayer mModelPlayer, Object object) {
      return mModelPlayer.p(object);
   }

   public static boolean v(MModelPlayer mModelPlayer, Object object) {
      return mModelPlayer.h(object);
   }

   public static boolean a(MModelPlayer mModelPlayer, Object object) {
      return mModelPlayer.b(object);
   }

   private void A(Object object, float f) {
      this.x.invokeVoid(object, f);
   }

   public static boolean u(MModelPlayer mModelPlayer, Object object) {
      return mModelPlayer.t(object);
   }

   private float p(Object object) {
      return this.H.invokeFloat(object);
   }

   private float k(Object object) {
      return this.u.invokeFloat(object);
   }

   public MModelPlayer() {
      super(MappedClasses.q9);
      Class[] classArray = new Class[0];
      Class<Void> clazz = void.class;
      boolean bl = false;
      String string = "<init>";
      this.n = this.Y(string, bl, clazz, classArray);
      Class<Boolean> clazz2 = boolean.class;
      boolean bl2 = true;
      String string2 = "isCreativeMode";
      this.b = this.J(string2, bl2, clazz2);
      Class<Boolean> clazz3 = boolean.class;
      boolean bl3 = true;
      String string3 = "disableDamage";
      this.M = this.J(string3, bl3, clazz3);
      Class<Boolean> clazz4 = boolean.class;
      boolean bl4 = true;
      String string4 = "allowFlying";
      this.E = this.J(string4, bl4, clazz4);
      Class<Boolean> clazz5 = boolean.class;
      boolean bl5 = true;
      String string5 = "allowEdit";
      this.y = this.J(string5, bl5, clazz5);
      if (n() != null) {
         Class<Boolean> clazz6 = boolean.class;
         boolean bl6 = true;
         String string6 = "isFlying";
         this.q = this.J(string6, bl6, clazz6);
         Class[] classArray2 = new Class[0];
         Class<Float> clazz7 = float.class;
         boolean bl7 = true;
         String string7 = "getWalkSpeed";
         this.H = this.Y(string7, bl7, clazz7, classArray2);
         Class[] classArray3 = new Class[0];
         Class<Float> clazz8 = float.class;
         boolean bl8 = true;
         String string8 = "getFlySpeed";
         this.u = this.Y(string8, bl8, clazz8, classArray3);
         Class[] classArray4 = new Class[]{float.class};
         Class<Void> clazz9 = void.class;
         boolean bl9 = true;
         String string9 = "setFlySpeed";
         this.x = this.Y(string9, bl9, clazz9, classArray4);
         if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray5 = new Class[]{float.class};
            Class<Void> clazz10 = void.class;
            boolean bl10 = true;
            String string10 = "setWalkSpeed";
            this.P = this.Y(string10, bl10, clazz10, classArray5);
         } else {
            Class[] classArray6 = new Class[]{float.class};
            Class<Void> clazz11 = void.class;
            boolean bl11 = true;
            String string11 = "setPlayerWalkSpeed";
            this.P = this.Y(string11, bl11, clazz11, classArray6);
         }

         if (GuiComponent.getLegacyComponentState() == null) {
            W("m4zzKb");
         }
      } else {
         Class<Boolean> clazz12 = boolean.class;
         boolean bl12 = true;
         String string12 = "isFlying";
         this.q = this.J(string12, bl12, clazz12);
         Class[] classArray7 = new Class[0];
         Class<Float> clazz13 = float.class;
         boolean bl13 = true;
         String string13 = "getWalkSpeed";
         this.H = this.Y(string13, bl13, clazz13, classArray7);
         Class[] classArray8 = new Class[0];
         Class<Float> clazz14 = float.class;
         boolean bl14 = true;
         String string14 = "getFlySpeed";
         this.u = this.Y(string14, bl14, clazz14, classArray8);
         Class[] classArray9 = new Class[]{float.class};
         Class<Void> clazz15 = void.class;
         boolean bl15 = true;
         String string15 = "setFlySpeed";
         this.P = this.Y(string15, bl15, clazz15, classArray9);
         this.x = null;
         if (GuiComponent.getLegacyComponentState() == null) {
            W("m4zzKb");
         }
      }
   }

   public static Object C(MModelPlayer mModelPlayer) {
      return mModelPlayer.b();
   }

   public static String n() {
      return m;
   }

   public static void E(MModelPlayer mModelPlayer, Object object, float f) {
      mModelPlayer.A(object, f);
   }

   public static void H(MModelPlayer mModelPlayer, Object object, float f) {
      mModelPlayer.L(object, f);
   }

   public static boolean k(MModelPlayer mModelPlayer, Object object) {
      return mModelPlayer.r(object);
   }

   public static void W(String string) {
      m = string;
   }

   private boolean t(Object object) {
      return this.M.getBoolean(object);
   }

   private boolean v(Object object) {
      return this.E.getBoolean(object);
   }

   public static boolean D(MModelPlayer mModelPlayer, Object object) {
      return mModelPlayer.v(object);
   }

   public static void t(MModelPlayer mModelPlayer, Object object, boolean bl) {
      mModelPlayer.z(object, bl);
   }

   public static float p(MModelPlayer mModelPlayer, Object object) {
      return mModelPlayer.k(object);
   }

   private void L(Object object, float f) {
      this.P.invokeVoid(object, f);
   }

   private Object b() {
      return this.n.newInstance();
   }

   public static void y(MModelPlayer mModelPlayer, Object object, boolean bl) {
      mModelPlayer.D(object, bl);
   }

   private boolean b(Object object) {
      return this.y.getBoolean(object);
   }

   private void z(Object object, boolean bl) {
      this.q.setBoolean(object, bl);
   }

   static {
      W("idI0Xb");
   }
}
