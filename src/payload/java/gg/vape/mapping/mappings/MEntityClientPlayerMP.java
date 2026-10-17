package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;

public class MEntityClientPlayerMP extends Mapping {
   private final MappingField E;
   private final MappingField x;
   private final MappingField a;
   private final MappingField f;
   private final MappingField Q;
   private final MappingMethod N;
   private final MappingField j;
   private final MappingField G;
   private final MappingField K;
   private final MappingField D;
   public MappingMethod M;
   private final MappingField k;
   private final MappingField Z;

   public static double p(MEntityClientPlayerMP mEntityClientPlayerMP, Object object) {
      return mEntityClientPlayerMP.V(object);
   }

   private float I(Object object) {
      return this.a.getFloat(object);
   }

   public static float K(MEntityClientPlayerMP mEntityClientPlayerMP, Object object) {
      return mEntityClientPlayerMP.I(object);
   }

   private void d(Object object, double d) {
      this.K.setDouble(object, d);
   }

   public static void S(MEntityClientPlayerMP mEntityClientPlayerMP, Object object, boolean bl) {
      mEntityClientPlayerMP.q(object, bl);
   }

   private boolean R(Object object) {
      return this.D.getBoolean(object);
   }

   private double V(Object object) {
      return this.x.getDouble(object);
   }

   public static void O(MEntityClientPlayerMP mEntityClientPlayerMP, Object object, float f) {
      mEntityClientPlayerMP.r(object, f);
   }

   public static boolean Q(MEntityClientPlayerMP mEntityClientPlayerMP, Object object) {
      return mEntityClientPlayerMP.R(object);
   }

   private void m(Object object, int n) {
      this.j.setInt(object, n);
   }

   public static boolean T(MEntityClientPlayerMP mEntityClientPlayerMP, Object object) {
      return mEntityClientPlayerMP.X(object);
   }

   public static void q(MEntityClientPlayerMP mEntityClientPlayerMP, Object object, double d) {
      mEntityClientPlayerMP.b(object, d);
   }

   private void Y(Object object, boolean bl) {
      this.D.setBoolean(object, bl);
   }

   public static int K$src$I$jaup0x(MEntityClientPlayerMP mEntityClientPlayerMP, Object object) {
      return mEntityClientPlayerMP.Q(object);
   }

   public static float l(MEntityClientPlayerMP mEntityClientPlayerMP, Object object) {
      return mEntityClientPlayerMP.G(object);
   }

   private Object A(Object object) {
      return this.f.getObject(object);
   }

   private void m(Object object, double d) {
      this.Q.setDouble(object, d);
   }

   public static Object W(MEntityClientPlayerMP mEntityClientPlayerMP, Object object) {
      return mEntityClientPlayerMP.A(object);
   }

   public static void D(MEntityClientPlayerMP mEntityClientPlayerMP, Object object, boolean bl) {
      mEntityClientPlayerMP.B(object, bl);
   }

   private void B(Object object, boolean bl) {
      this.Z.setBoolean(object, bl);
   }

   private void a(Object object, double d) {
      this.x.setDouble(object, d);
   }

   public static void v(MEntityClientPlayerMP mEntityClientPlayerMP, Object object, double d) {
      mEntityClientPlayerMP.d(object, d);
   }

   public static void D(MEntityClientPlayerMP mEntityClientPlayerMP, Object object, float f) {
      mEntityClientPlayerMP.g(object, f);
   }

   private int Q(Object object) {
      return this.j.getInt(object);
   }

   public static double g(MEntityClientPlayerMP mEntityClientPlayerMP, Object object) {
      return mEntityClientPlayerMP.j(object);
   }

   public static void n(MEntityClientPlayerMP mEntityClientPlayerMP, Object object, double d) {
      mEntityClientPlayerMP.a(object, d);
   }

   public MEntityClientPlayerMP() {
      super(MappedClasses.DI);
      Class clazz = MappedClasses.F1;
      boolean bl = true;
      String string = "sendQueue";
      this.f = this.J(string, bl, clazz);
      Class<Boolean> clazz2 = boolean.class;
      boolean bl2 = true;
      String string2 = "wasSprinting";
      this.D = this.J(string2, bl2, clazz2);
      Class<Boolean> clazz3 = boolean.class;
      boolean bl3 = true;
      String string3 = "wasSneaking";
      this.G = this.J(string3, bl3, clazz3);
      Class<Double> clazz4 = double.class;
      boolean bl4 = true;
      String string4 = "oldPosX";
      this.k = this.J(string4, bl4, clazz4);
      Class<Double> clazz5 = double.class;
      boolean bl5 = true;
      String string5 = "oldMinY";
      this.K = this.J(string5, bl5, clazz5);
      Class<Double> clazz6 = double.class;
      boolean bl6 = true;
      String string6 = "oldPosY";
      this.Q = this.J(string6, bl6, clazz6);
      Class<Double> clazz7 = double.class;
      boolean bl7 = true;
      String string7 = "oldPosZ";
      this.x = this.J(string7, bl7, clazz7);
      Class<Float> clazz8 = float.class;
      boolean bl8 = true;
      String string8 = "oldRotationYaw";
      this.a = this.J(string8, bl8, clazz8);
      Class<Float> clazz9 = float.class;
      boolean bl9 = true;
      String string9 = "oldRotationPitch";
      this.E = this.J(string9, bl9, clazz9);
      GuiComponent[] guiComponentArray = MEntityPlayerSP.r();
      Class<Integer> clazz10 = int.class;
      boolean bl10 = true;
      String string10 = "ticksSinceMovePacket";
      this.j = this.J(string10, bl10, clazz10);
      Class<Boolean> clazz11 = boolean.class;
      boolean bl11 = true;
      String string11 = "wasOnGround";
      this.Z = this.J(string11, bl11, clazz11);
      Class[] classArray = new Class[]{String.class};
      Class<Void> clazz12 = void.class;
      boolean bl12 = true;
      String string12 = "sendChatMessage";
      this.N = this.Y(string12, bl12, clazz12, classArray);
      Class[] classArray2 = new Class[0];
      Class<Void> clazz13 = void.class;
      boolean bl13 = true;
      String string13 = "sendMotionUpdates";
      this.M = this.Y(string13, bl13, clazz13, classArray2);
      if (GuiComponent.getLegacyComponentState() == null) {
         MEntityPlayerSP.W(new GuiComponent[5]);
      }
   }

   public static double H(MEntityClientPlayerMP mEntityClientPlayerMP, Object object) {
      return mEntityClientPlayerMP.Z(object);
   }

   private double j(Object object) {
      return this.Q.getDouble(object);
   }

   private double b(Object object) {
      return this.K.getDouble(object);
   }

   private void g(Object object, float f) {
      this.E.setFloat(object, f);
   }

   public static void z(MEntityClientPlayerMP mEntityClientPlayerMP, Object object, double d) {
      mEntityClientPlayerMP.m(object, d);
   }

   private void q(Object object, boolean bl) {
      this.G.setBoolean(object, bl);
   }

   private void A(Object object, String string) {
      this.N.invokeVoid(object, string);
   }

   private float G(Object object) {
      return this.E.getFloat(object);
   }

   private void b(Object object, double d) {
      this.k.setDouble(object, d);
   }

   private double Z(Object object) {
      return this.k.getDouble(object);
   }

   public static double x(MEntityClientPlayerMP mEntityClientPlayerMP, Object object) {
      return mEntityClientPlayerMP.b(object);
   }

   private void r(Object object, float f) {
      this.a.setFloat(object, f);
   }

   public static void e(MEntityClientPlayerMP mEntityClientPlayerMP, Object object, String string) {
      mEntityClientPlayerMP.A(object, string);
   }

   public static void F(MEntityClientPlayerMP mEntityClientPlayerMP, Object object, boolean bl) {
      mEntityClientPlayerMP.Y(object, bl);
   }

   private boolean X(Object object) {
      return this.G.getBoolean(object);
   }

   public static void Y(MEntityClientPlayerMP mEntityClientPlayerMP, Object object, int n) {
      mEntityClientPlayerMP.m(object, n);
   }
}
