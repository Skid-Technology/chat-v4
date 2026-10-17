package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MVec3d extends Mapping {
   private final MappingField i;
   private final MappingField Q;
   public final MappingMethod k;
   private MappingMethod l;
   private final MappingMethod J;
   private MappingMethod a;
   private final MappingMethod Z;
   private MappingField S;
   private final MappingMethod U;
   private final MappingField m;

   public Object q(double d, double d2, double d3) {
      return this.k.newInstance(d, d2, d3);
   }

   public double z(Object object) {
      return this.Q.getDouble(object);
   }

   public Object M(Object object, Object object2, double d) {
      return this.l.invokeObject(object, object2, d);
   }

   public double P(Object object) {
      return this.m.getDouble(object);
   }

   public void K(Object object, double d) {
      this.Q.setDouble(object, d);
   }

   public MVec3d() {
      this(BlockData.W());
   }

   private MVec3d(String[] stringArray) {
      super(MappedClasses.qP);
      if (ForgeVersion.MC_1_16_5.d()) {
         Class<Double> clazz = double.class;
         boolean bl = true;
         String string = "x";
         this.i = this.J(string, bl, clazz);
         Class<Double> clazz2 = double.class;
         boolean bl2 = true;
         String string2 = "y";
         this.Q = this.J(string2, bl2, clazz2);
         Class<Double> clazz3 = double.class;
         boolean bl3 = true;
         String string3 = "z";
         this.m = this.J(string3, bl3, clazz3);
         Class[] classArray = new Class[]{double.class};
         Class clazz4 = MappedClasses.qP;
         boolean bl4 = true;
         String string4 = "scale";
         this.a = this.Y(string4, bl4, clazz4, classArray);
         if (ForgeVersion.MC_1_17.d()) {
            Class[] classArray2 = new Class[]{double.class, double.class, double.class};
            Class clazz5 = MappedClasses.qP;
            boolean bl5 = true;
            String string5 = "add";
            this.Z = this.Y(string5, bl5, clazz5, classArray2);
         } else {
            Class[] classArray3 = new Class[]{double.class, double.class, double.class};
            Class clazz6 = MappedClasses.qP;
            boolean bl6 = Wrapper.isNativeAvailable;
            String string6 = "func_72441_c";
            this.Z = this.Y(string6, bl6, clazz6, classArray3);
         }

         if (ForgeVersion.MC_1_21_4.d()) {
            Class[] classArray4 = new Class[]{MappedClasses.u9, double.class};
            Class clazz7 = MappedClasses.qP;
            String string7 = "with";
            this.l = this.methodBuilder(string7, clazz7, classArray4).buildMethod();
            Class clazz8 = MappedClasses.qP;
            String string8 = "ZERO";
            this.S = this.fieldBuilder(string8, clazz8).setStaticMember(true).buildField();
         }
      } else {
         Class<Double> clazzx = double.class;
         boolean blx = true;
         String stringx = ForgeVersion.c() >= 23 ? "x" : "xCoord";
         this.i = this.J(stringx, blx, clazzx);
         Class<Double> clazz9 = double.class;
         boolean bl7 = true;
         String string9 = ForgeVersion.c() >= 23 ? "y" : "yCoord";
         this.Q = this.J(string9, bl7, clazz9);
         Class<Double> clazz10 = double.class;
         boolean bl8 = true;
         String string10 = ForgeVersion.c() >= 23 ? "z" : "zCoord";
         this.m = this.J(string10, bl8, clazz10);
         Class[] classArrayx = new Class[]{double.class, double.class, double.class};
         Class clazz11 = MappedClasses.qP;
         boolean bl9 = true;
         String string11 = ForgeVersion.c() >= 23 ? "add" : "addVector";
         this.Z = this.Y(string11, bl9, clazz11, classArrayx);
      }

      Class[] classArrayx = new Class[]{MappedClasses.qP};
      Class<Double> clazzx = double.class;
      boolean blx = true;
      String stringx = "distanceTo";
      this.J = this.Y(stringx, blx, clazzx, classArrayx);
      if (ForgeVersion.MC_1_12_2.d()) {
         Class[] classArray5 = new Class[]{MappedClasses.qP};
         Class<Double> clazz12 = double.class;
         boolean bl10 = Wrapper.isNativeAvailable;
         String string12 = "func_72436_e";
         this.U = this.Y(string12, bl10, clazz12, classArray5);
      } else {
         Class[] classArray6 = new Class[]{MappedClasses.qP};
         Class<Double> clazz13 = double.class;
         boolean bl11 = true;
         String string13 = "squareDistanceTo";
         this.U = this.Y(string13, bl11, clazz13, classArray6);
      }

      Class[] classArray7 = new Class[]{double.class, double.class, double.class};
      Class<Void> clazz14 = void.class;
      boolean bl12 = false;
      String string14 = "<init>";
      this.k = this.Y(string14, bl12, clazz14, classArray7);
   }

   public void Z(Object object, double d) {
      this.m.setDouble(object, d);
   }

   public Object H(Object object, double d) {
      return this.a.invokeObject(object, d);
   }

   public double S(Object object, Object object2) {
      return this.J.invokeDouble(object, object2);
   }

   public double O(Object object) {
      return this.i.getDouble(object);
   }

   public void V(Object object, double d) {
      this.i.setDouble(object, d);
   }

   public Object O() {
      return this.S.getObject(null);
   }

   public double A(Object object, Object object2) {
      return this.U.invokeDouble(object, object2);
   }

   public Object Y(Object object, double d, double d2, double d3) {
      return this.Z.invokeObject(object, d, d2, d3);
   }
}
