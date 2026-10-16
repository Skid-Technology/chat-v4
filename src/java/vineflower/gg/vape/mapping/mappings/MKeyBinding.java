package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MKeyBinding extends Mapping {
   public MappingMethod s;
   public MappingMethod q;
   public final MappingMethod c;
   private final MappingField B;
   private MappingMethod T;
   public final MappingMethod p;
   private static boolean e;
   public MappingMethod Z;
   private MappingMethod X;
   public final MappingMethod z;
   public MappingField E;
   private final MappingField x;

   public void d(Object object, boolean bl) {
      this.q.invokeVoid(null, object, bl);
   }

   public static boolean U() {
      return e;
   }

   public void x(Object object) {
      this.z.invokeVoid(null, object);
   }

   public void s(int n) {
      this.z.invokeVoid(null, n);
   }

   public Object l(Object object) {
      return this.E.getObject(object);
   }

   public void o(Object object, boolean bl) {
      this.s.invokeVoid(object, bl);
   }

   public MKeyBinding() {
      super(MappedClasses.DR);
      Class<Boolean> clazz = boolean.class;
      boolean bl = true;
      String string = "pressed";
      this.x = this.J(string, bl, clazz);
      Class<Integer> clazz2 = int.class;
      boolean bl2 = true;
      String string2 = "pressTime";
      this.B = this.J(string2, bl2, clazz2);
      if (U()) {
         Class[] classArray = new Class[0];
         Class<Boolean> clazz3 = boolean.class;
         boolean bl3 = true;
         String string3 = "isPressed";
         this.c = this.Y(string3, bl3, clazz3, classArray);
         Class[] classArray2 = new Class[0];
         Class<Void> clazz4 = void.class;
         boolean bl4 = true;
         String string4 = "unPressAllKeys";
         this.T = this.registerStaticMethod(string4, bl4, clazz4, classArray2);
         Class[] classArray3 = new Class[]{int.class};
         Class<Void> clazz5 = void.class;
         boolean bl5 = true;
         String string5 = "onTick";
         this.z = this.registerStaticMethod(string5, bl5, clazz5, classArray3);
         if (GuiComponent.getLegacyComponentState() == null) {
            O(false);
         }

         this.p = null;
      } else {
         Class[] classArray = new Class[0];
         Class<Boolean> clazz6 = boolean.class;
         boolean bl6 = true;
         String string6 = "isPressed";
         this.p = this.Y(string6, bl6, clazz6, classArray);
         if (Wrapper.vapeInstance.isVanillaMinecraftPresent() && ForgeVersion.MC_1_16_5.v()) {
            Class[] classArray4 = new Class[0];
            Class<Boolean> clazz7 = boolean.class;
            boolean bl7 = true;
            String string7 = ForgeVersion.MC_1_7_10.L() ? "getIsKeyPressed" : "isKeyDown";
            this.c = this.Y(string7, bl7, clazz7, classArray4);
         } else {
            Class[] classArray5 = new Class[0];
            Class<Boolean> clazz8 = boolean.class;
            boolean bl8 = Wrapper.isNativeAvailable;
            String string8 = "func_151470_d";
            this.c = this.Y(string8, bl8, clazz8, classArray5);
         }

         Class[] classArray6 = new Class[0];
         Class<Void> clazz9 = void.class;
         boolean bl9 = true;
         String string9 = "unPressAllKeys";
         this.T = this.registerStaticMethod(string9, bl9, clazz9, classArray6);
         if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray7 = new Class[]{boolean.class};
            Class<Void> clazz10 = void.class;
            boolean bl10 = true;
            String string10 = "setPressed";
            this.s = this.Y(string10, bl10, clazz10, classArray7);
            Class[] classArray8 = new Class[0];
            Class<Void> clazz11 = void.class;
            boolean bl11 = true;
            String string11 = "unpressKey";
            this.X = this.Y(string11, bl11, clazz11, classArray8);
            Class clazz12 = MappedClasses.zp;
            boolean bl12 = true;
            String string12 = "keyCode";
            this.E = this.J(string12, bl12, clazz12);
            Class[] classArray9 = new Class[]{MappedClasses.zp, boolean.class};
            Class<Void> clazz13 = void.class;
            boolean bl13 = true;
            String string13 = "setKeyBindState";
            this.q = this.registerStaticMethod(string13, bl13, clazz13, classArray9);
            Class[] classArray10 = new Class[]{MappedClasses.zp};
            Class<Void> clazz14 = void.class;
            boolean bl14 = true;
            String string14 = "onTick";
            this.z = this.registerStaticMethod(string14, bl14, clazz14, classArray10);
         } else {
            Class[] classArray11 = new Class[0];
            Class<Integer> clazz15 = int.class;
            boolean bl15 = true;
            String string15 = "getKeyCode";
            this.Z = this.Y(string15, bl15, clazz15, classArray11);
            Class[] classArray12 = new Class[]{int.class, boolean.class};
            Class<Void> clazz16 = void.class;
            boolean bl16 = true;
            String string16 = "setKeyBindState";
            this.q = this.registerStaticMethod(string16, bl16, clazz16, classArray12);
            Class[] classArray13 = new Class[]{int.class};
            Class<Void> clazz17 = void.class;
            boolean bl17 = true;
            String string17 = "onTick";
            this.z = this.registerStaticMethod(string17, bl17, clazz17, classArray13);
         }

         if (GuiComponent.getLegacyComponentState() == null) {
            O(true);
         }
      }
   }

   public void g(int n, boolean bl) {
      this.q.invokeVoid(null, n, bl);
   }

   public boolean M(Object object) {
      return this.p.invokeBoolean(object);
   }

   public static boolean j() {
      boolean bl = U();
      return !bl;
   }

   public void S(Object object) {
      this.X.invokeVoidNoArgs(object);
   }

   public static void O(boolean bl) {
      e = bl;
   }

   public void q(Object object, int n) {
      this.B.setInt(object, n);
   }

   public int n(Object object) {
      return this.B.getInt(object);
   }

   public void R() {
      this.T.invokeVoidNoArgs(null);
   }

   public boolean S$src$Z$wvm5qt(Object object) {
      return this.x.getBoolean(object);
   }

   public boolean a(Object object) {
      return this.c.invokeBoolean(object);
   }

   public int B(Object object) {
      return this.Z.invokeInt(object);
   }

   static {
      O(false);
   }
}
