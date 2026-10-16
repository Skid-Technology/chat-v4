package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.ForgeVersion;

public class MRayTraceResult extends Mapping {
   private MappingMethod D;
   public final MappingField c;
   public MappingField I;
   private MappingMethod Z;
   private MappingMethod b;
   private MappingMethod d;
   public final MappingField E;
   public MappingField k;
   public MappingMethod F;
   public MappingField H;
   public MappingField a;
   public final MappingField N;
   public MappingField U;

   private Object M(Object object) {
      return ForgeVersion.MC_1_16_5.d() ? this.F.invokeObject(object) : this.H.getObject(object);
   }

   public MRayTraceResult() {
      this(BlockData.W());
   }

   private MRayTraceResult(String[] stringArray) {
      super(MappedClasses.DT);
      if (ForgeVersion.MC_1_7_10.L()) {
         Class<Integer> clazz = int.class;
         boolean bl = true;
         String string = "blockX";
         this.k = this.J(string, bl, clazz);
         Class<Integer> clazz2 = int.class;
         boolean bl2 = true;
         String string2 = "blockY";
         this.a = this.J(string2, bl2, clazz2);
         Class<Integer> clazz3 = int.class;
         boolean bl3 = true;
         String string3 = "blockZ";
         this.U = this.J(string3, bl3, clazz3);
         Class<Integer> clazz4 = int.class;
         boolean bl4 = true;
         String string4 = "sideHit";
         this.c = this.J(string4, bl4, clazz4);
      } else if (ForgeVersion.MC_1_16_5.d()) {
         Class clazz = MappedClasses.lf;
         boolean bl = true;
         String string = "pos";
         Class clazz5 = MappedClasses.qF;
         this.I = this.registerInstanceFieldForOwner(clazz5, string, bl, clazz);
         Class clazz6 = MappedClasses.q0;
         boolean bl5 = true;
         String string5 = "face";
         Class clazz7 = MappedClasses.qF;
         this.c = this.registerInstanceFieldForOwner(clazz7, string5, bl5, clazz6);
      } else {
         Class clazz = MappedClasses.lf;
         boolean bl = true;
         String string = "blockPos";
         this.I = this.J(string, bl, clazz);
         Class clazz8 = MappedClasses.q0;
         boolean bl6 = true;
         String string6 = "sideHit";
         this.c = this.J(string6, bl6, clazz8);
      }

      if (ForgeVersion.MC_1_16_5.v()) {
         Class clazz = MappedClasses.qP;
         boolean bl = true;
         String string = "hitVec";
         this.E = this.J(string, bl, clazz);
         Class clazz9 = MappedClasses.zc;
         boolean bl7 = true;
         String string7 = "entityHit";
         this.N = this.J(string7, bl7, clazz9);
         Class clazz10 = MappedClasses.lk;
         boolean bl8 = true;
         String string8 = "typeOfHit";
         this.H = this.J(string8, bl8, clazz10);
         Class[] classArray = new Class[]{MappedClasses.zc, MappedClasses.qP};
         Class<Void> clazz11 = void.class;
         boolean bl9 = false;
         String string9 = "<init>";
         this.D = this.Y(string9, bl9, clazz11, classArray);
         if (ForgeVersion.MC_1_7_10.Y()) {
            Class[] classArray2 = new Class[]{MappedClasses.lk, MappedClasses.qP, MappedClasses.q0, MappedClasses.lf};
            Class<Void> clazz12 = void.class;
            boolean bl10 = false;
            String string10 = "<init>";
            this.Z = this.Y(string10, bl10, clazz12, classArray2);
         }
      } else {
         Class clazz = MappedClasses.qP;
         boolean bl = true;
         String string = "hitResult";
         this.E = this.J(string, bl, clazz);
         Class clazz13 = MappedClasses.zc;
         boolean bl11 = true;
         String string11 = "entity";
         Class clazz14 = MappedClasses.zl;
         this.N = this.registerInstanceFieldForOwner(clazz14, string11, bl11, clazz13);
         Class[] classArray = new Class[0];
         Class clazz15 = MappedClasses.lk;
         boolean bl12 = true;
         String string12 = "getType";
         this.F = this.Y(string12, bl12, clazz15, classArray);
         Class[] classArray3 = new Class[]{MappedClasses.qP, MappedClasses.q0, MappedClasses.lf, boolean.class};
         Class<Void> clazz16 = void.class;
         boolean bl13 = false;
         String string13 = "<init>";
         Class clazz17 = MappedClasses.qF;
         this.b = this.registerInstanceMethodForOwner(clazz17, string13, bl13, clazz16, classArray3);
         Class[] classArray4 = new Class[]{MappedClasses.zc, MappedClasses.qP};
         Class<Void> clazz18 = void.class;
         boolean bl14 = false;
         String string14 = "<init>";
         Class clazz19 = MappedClasses.zl;
         this.d = this.registerInstanceMethodForOwner(clazz19, string14, bl14, clazz18, classArray4);
      }
   }

   public Object m(Object object, Object object2, Object object3, Object object4) {
      return this.Z.newInstance(object, object2, object3, object4);
   }

   public int n(Object object) {
      return this.c.getInt(object);
   }

   public Object y(Object object, Object object2) {
      return this.D.newInstance(object, object2);
   }

   public static Object Z(MRayTraceResult mRayTraceResult, Object object) {
      return mRayTraceResult.M(object);
   }

   public Object o(Object object, Object object2, Object object3, boolean bl) {
      return this.b.newInstance(object, object2, object3, bl);
   }

   public int z(Object object) {
      return this.U.getInt(object);
   }

   public Object M(Object object, Object object2) {
      return this.d.newInstance(object, object2);
   }

   public Object o(Object object) {
      return this.E.getObject(object);
   }

   public Object F(Object object) {
      return this.N.getObject(object);
   }

   public void a(Object object, Object object2) {
      this.N.setObject(object, object2);
   }

   public int a(Object object) {
      return this.k.getInt(object);
   }

   public Object D(Object object) {
      return this.I.getObject(object);
   }

   public Object P(Object object) {
      return this.c.getObject(object);
   }

   public int w(Object object) {
      return this.a.getInt(object);
   }
}
