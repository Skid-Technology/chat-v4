package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.asm.helper.DescUtils;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MEnumFacing extends Mapping {
   public MappingMethod g;
   public MappingField z;
   private final MappingField o;
   private final MappingField s;
   public MappingField i;
   private final MappingField b;
   public MappingField k;
   public MappingField T;
   private final MappingField p;
   public MappingField m;
   public MappingField C;
   public MappingMethod x;
   public MappingMethod P;
   public MappingMethod I;
   public MappingField F;
   public MappingMethod L;
   private final MappingField n;
   public MappingField v;
   private final MappingField K;
   public MappingField A;

   public int b(Object object) {
      return this.z.getInt(object);
   }

   public int A(Object object) {
      return this.T.getInt(object);
   }

   public int v(Object object) {
      return this.i.getInt(object);
   }

   public Object[] U() {
      return this.C.getObjectArray(null);
   }

   public Object[] H() {
      return this.P.invokeObjectArray(null);
   }

   public Object c(Object object) {
      return this.F != null ? this.F.getObject(object).toString() : this.L.invokeObject(object);
   }

   public Object e(Object object) {
      return this.I.invokeObject(object);
   }

   public static MappingField R(MEnumFacing mEnumFacing) {
      return mEnumFacing.K;
   }

   public int s(Object object) {
      return this.m.getInt(object);
   }

   public static MappingField E(MEnumFacing mEnumFacing) {
      return mEnumFacing.n;
   }

   public static MappingField b(MEnumFacing mEnumFacing) {
      return mEnumFacing.o;
   }

   public static MappingField z(MEnumFacing mEnumFacing) {
      return mEnumFacing.s;
   }

   public int p(Object object) {
      return this.g.invokeInt(object);
   }

   public MEnumFacing() {
      this(BlockData.W());
   }

   private MEnumFacing(String[] stringArray) {
      super(MappedClasses.q0);
      if (ForgeVersion.MC_1_7_10.Y()) {
         Class[] classArray = new Class[0];
         Class<String> clazz = String.class;
         boolean bl = true;
         String string = "getName2";
         this.L = this.Y(string, bl, clazz, classArray);
         Class[] classArray2 = new Class[0];
         Class<?> clazz2 = DescUtils.getArrayType(MappedClasses.q0);
         boolean bl2 = false;
         String string2 = "values";
         this.P = this.registerStaticMethod(string2, bl2, clazz2, classArray2);
         Class<Integer> clazz3 = int.class;
         boolean bl3 = true;
         String string3 = "opposite";
         this.z = this.J(string3, bl3, clazz3);
         Class[] classArray3 = new Class[0];
         Class<Integer> clazz4 = int.class;
         boolean bl4 = false;
         String string4 = "ordinal";
         this.g = this.Y(string4, bl4, clazz4, classArray3);
         Class[] classArray4 = new Class[0];
         Class clazz5 = MappedClasses.Vr;
         boolean bl5 = true;
         String string5 = "getDirectionVec";
         this.I = this.Y(string5, bl5, clazz5, classArray4);
         Class[] classArray5 = new Class[0];
         Class<Integer> clazz6 = int.class;
         boolean bl6 = true;
         String string6 = "getHorizontalIndex";
         this.x = this.Y(string6, bl6, clazz6, classArray5);
         if (ForgeVersion.MC_1_16_5.v()) {
            Class<?> clazz7 = DescUtils.getArrayType(MappedClasses.q0);
            boolean bl7 = true;
            String string7 = "HORIZONTALS";
            this.k = this.registerStaticField(string7, bl7, clazz7);
         }
      } else {
         Class<String> clazz = String.class;
         boolean bl = true;
         String string = "name";
         Class<Enum> clazz8 = Enum.class;
         this.F = this.registerInstanceFieldForOwner(clazz8, string, bl, clazz);
         if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            Class clazz9 = MappedClasses.q0;
            String string8 = "faceList";
            this.C = this.fieldBuilder(string8, clazz9).setStaticMember(true).setArrayDimensions(1).buildField();
         } else {
            Class clazz10 = MappedClasses.q0;
            String string9 = "field_82609_l";
            this.C = this.fieldBuilder(string9, clazz10).setStaticMember(true).setMappedMember(Wrapper.isNativeAvailable).setArrayDimensions(1).buildField();
         }

         Class<Integer> clazz11 = int.class;
         boolean bl8 = true;
         String string10 = "order_b";
         this.i = this.J(string10, bl8, clazz11);
         Class<Integer> clazz12 = int.class;
         boolean bl9 = true;
         String string11 = "order_a";
         this.v = this.J(string11, bl9, clazz12);
         Class<Integer> clazz13 = int.class;
         boolean bl10 = true;
         String string12 = "frontOffsetX";
         this.A = this.J(string12, bl10, clazz13);
         Class<Integer> clazz14 = int.class;
         boolean bl11 = true;
         String string13 = "frontOffsetY";
         this.T = this.J(string13, bl11, clazz14);
         Class<Integer> clazz15 = int.class;
         boolean bl12 = true;
         String string14 = "frontOffsetZ";
         this.m = this.J(string14, bl12, clazz15);
      }

      Class clazz = MappedClasses.q0;
      boolean bl = true;
      String string = "DOWN";
      this.n = this.registerStaticField(string, bl, clazz);
      Class clazz16 = MappedClasses.q0;
      boolean bl13 = true;
      String string15 = "UP";
      this.s = this.registerStaticField(string15, bl13, clazz16);
      Class clazz17 = MappedClasses.q0;
      boolean bl14 = true;
      String string16 = "NORTH";
      this.o = this.registerStaticField(string16, bl14, clazz17);
      Class clazz18 = MappedClasses.q0;
      boolean bl15 = true;
      String string17 = "SOUTH";
      this.K = this.registerStaticField(string17, bl15, clazz18);
      Class clazz19 = MappedClasses.q0;
      boolean bl16 = true;
      String string18 = "WEST";
      this.p = this.registerStaticField(string18, bl16, clazz19);
      Class clazz20 = MappedClasses.q0;
      boolean bl17 = true;
      String string19 = "EAST";
      this.b = this.registerStaticField(string19, bl17, clazz20);
   }

   public int V(Object object) {
      return this.A.getInt(object);
   }

   public Object[] A() {
      return this.k.getObjectArray(null);
   }

   public static MappingField n(MEnumFacing mEnumFacing) {
      return mEnumFacing.p;
   }

   public int i(Object object) {
      return this.v.getInt(object);
   }

   public static MappingField s(MEnumFacing mEnumFacing) {
      return mEnumFacing.b;
   }

   public int S(Object object) {
      return this.x.invokeInt(object);
   }
}
