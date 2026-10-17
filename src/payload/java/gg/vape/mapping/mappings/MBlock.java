package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.ForgeVersion;

public class MBlock extends Mapping {
   public MappingMethod J;
   private MappingMethod r;
   private MappingMethod E;
   private MappingMethod B;
   private MappingMethod Z;
   private MappingField x;
   public MappingField g;
   private MappingMethod P;
   private MappingMethod k;
   public MappingMethod u;
   private MappingField T;
   private MappingMethod H;
   private MappingMethod j;
   public MappingMethod S;
   private static GuiComponent[] Y;
   public final MappingField D;
   private MappingMethod O;
   private MappingMethod q;
   private MappingField o;
   public MappingMethod w;
   private MappingMethod K;
   private MappingField U;
   public MappingMethod v;
   public final MappingMethod n;
   private MappingMethod W;
   public MappingMethod M;
   private MappingMethod F;
   private final MappingField Q;
   public MappingMethod X;
   private MappingMethod playerRelativeBlockHardnessMethod;

   public int h(Object object) {
      return this.n.invokeInt(null, object);
   }

   public static GuiComponent[] m() {
      return Y;
   }

   public static int E(MBlock mBlock, Object object) {
      return mBlock.k(object);
   }

   public static void Y(GuiComponent[] guiComponentArray) {
      Y = guiComponentArray;
   }

   private Object z(Object object, Object object2, int n, int n2, int n3) {
      return this.v.invokeObject(object, object2, n, n2, n3);
   }

   private boolean X(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, float f, float f2, float f3) {
      return this.q.invokeBoolean(object, object2, object3, object4, object5, object6, object7, f, f2, f3);
   }

   private Object x(Object object, Object object2, Object object3, Object object4) {
      return ForgeVersion.MC_1_21_4.d()
         ? this.Z.invokeObject(object, object2, object3, object4, Boolean.FALSE)
         : this.Z.invokeObject(object, object2, object3, object4);
   }

   private Object N(Object object) {
      return this.X.invokeObject(object);
   }

   private void X(Object object, Object object2, Object object3, Object object4, Object object5, double d) {
      this.H.invokeVoid(object, object2, object3, object4, object5, d);
   }

   private int N(Object object, Object object2) {
      return this.k.invokeInt(object, object2);
   }

   public Object a(String string) {
      return this.F.invokeObject(null, string);
   }

   private void i(Object object, Object object2, int n, int n2, int n3, Object object3) {
      this.r.invokeVoid(object, object2, n, n2, n3, object3);
   }

   public static boolean L(
      MBlock mBlock, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, float f, float f2, float f3
   ) {
      return mBlock.i(object, object2, object3, object4, object5, object6, f, f2, f3);
   }

   public Object y(Object object, Object object2, Object object3, Object object4) {
      return this.j.invokeObject(object, object2, object3, object4);
   }

   public static Object N(MBlock mBlock, Object object) {
      return mBlock.B(object);
   }

   public static float w(MBlock mBlock, Object object) {
      return mBlock.T(object);
   }

   public static Object N(MBlock mBlock) {
      return mBlock.T();
   }

   public static int V(MBlock mBlock, Object object, Object object2, Object object3) {
      return mBlock.n(object, object2, object3);
   }

   private int O(Object object, Object object2, int n, int n2, int n3) {
      return this.k.invokeInt(object, object2, n, n2, n3);
   }

   private Object T() {
      return this.U.getObject(null);
   }

   public static boolean X(MBlock mBlock, Object object) {
      return mBlock.q(object);
   }

   public boolean Z(Object object) {
      return this.K.invokeBoolean(object);
   }

   private Object U(Object object, Object object2, Object object3) {
      return this.v.invokeObject(object, object2, object3);
   }

   public boolean r(Object object, Object object2) {
      return this.K.invokeBoolean(object, object2);
   }

   private Object V(Object object, Object object2, int n, int n2, int n3) {
      return ForgeVersion.MC_1_7_10.L()
         ? this.u.invokeObject(object, object2, n, n2, n3)
         : this.u.invokeObject(object, object2, BlockPos.create(n, n2, n3).getObject(), null);
   }

   public static int Q(MBlock mBlock, Object object, Object object2, int n, int n2, int n3) {
      return mBlock.O(object, object2, n, n2, n3);
   }

   public static int W(MBlock mBlock, Object object, Object object2) {
      return mBlock.N(object, object2);
   }

   public boolean E(Object object, Object object2) {
      return this.P.invokeBoolean(object, object2);
   }

   private boolean i(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, float f, float f2, float f3) {
      return this.q.invokeBoolean(object, object2, object3, object4, object5, object6, f, f2, f3);
   }

   public static void u(MBlock mBlock, Object object, Object object2, Object object3, Object object4, Object object5, double d) {
      mBlock.X(object, object2, object3, object4, object5, d);
   }

   public static String l(MBlock mBlock, Object object) {
      return mBlock.f(object);
   }

   private boolean q(Object object) {
      return this.o.getBoolean(object);
   }

   private Object W(Object object, Object object2) {
      return this.X.invokeObject(object, object2);
   }

   private Object B(Object object) {
      return this.T.getObject(object);
   }

   public boolean F$src$Z$6w97vr(Object object) {
      return this.P.invokeBoolean(object);
   }

   public static void v(MBlock mBlock, Object object, Object object2, int n, int n2, int n3, Object object3) {
      mBlock.i(object, object2, n, n2, n3, object3);
   }

   private int n(Object object, Object object2, Object object3) {
      return this.k.invokeInt(object, object2, object3);
   }

   private String f(Object object) {
      return (String)this.D.getObject(object);
   }

   private boolean k$src$Z$1m9upos(Object object) {
      return this.B.invokeBoolean(object);
   }

   public static Object h(MBlock mBlock, Object object) {
      return mBlock.N(object);
   }

   public static Object Q(MBlock mBlock, Object object, Object object2, Object object3, Object object4) {
      return mBlock.x(object, object2, object3, object4);
   }

   public static Object J(MBlock mBlock, Object object, Object object2, int n, int n2, int n3) {
      return mBlock.V(object, object2, n, n2, n3);
   }

   public static void y(MBlock mBlock, Object object, Object object2, Object object3) {
      mBlock.Z(object, object2, object3);
   }

   private int k(Object object) {
      return this.W.invokeInt(object);
   }

   public boolean C(Object object, Object object2, boolean bl) {
      return this.E.invokeBoolean(object, object2, bl);
   }

   public static Object d(MBlock mBlock, Object object, Object object2, Object object3) {
      return mBlock.U(object, object2, object3);
   }

   public MBlock() {
      this(m());
   }

   private MBlock(GuiComponent[] guiComponentArray) {
      super(MappedClasses.Zk);
      if (ForgeVersion.MC_1_8_9.L()) {
         this.playerRelativeBlockHardnessMethod = this.Y(
            "getPlayerRelativeBlockHardness", true, float.class, MappedClasses.Yl, MappedClasses.YU, MappedClasses.lf
         );
      }

      if (ForgeVersion.MC_1_20_6.v()) {
         Class<Float> clazz = float.class;
         boolean bl = true;
         if (ForgeVersion.MC_1_16_5.d() && ForgeVersion.MC_1_17.v()) {
            this.x = this.registerInstanceFieldForOwner(MappedClasses.Fj, "hardness", bl, clazz);
         } else {
            String string = "blockHardness";
            this.x = this.J(string, bl, clazz);
         }
      }

      if (!ForgeVersion.MC_1_16_5.d()) {
         if (ForgeVersion.MC_1_12_2.d()) {
            Class[] classArray = new Class[]{MappedClasses.Vv, MappedClasses.YU, MappedClasses.lf};
            Class clazz = MappedClasses.uk;
            boolean bl = true;
            String string = "getSelectedBoundingBox";
            this.v = this.Y(string, bl, clazz, classArray);
         } else if (ForgeVersion.MC_1_7_10.Y()) {
            Class[] classArray = new Class[]{MappedClasses.YU, MappedClasses.lf};
            Class clazz = MappedClasses.uk;
            boolean bl = true;
            String string = "getSelectedBoundingBox";
            this.v = this.Y(string, bl, clazz, classArray);
         } else {
            Class[] classArray = new Class[]{MappedClasses.YU, int.class, int.class, int.class};
            Class clazz = MappedClasses.uk;
            boolean bl = true;
            String string = "getSelectedBoundingBoxFromPool";
            this.v = this.Y(string, bl, clazz, classArray);
         }
      }

      if (ForgeVersion.MC_1_7_10.Y()) {
         Class clazz = MappedClasses.Vv;
         String string = "defaultBlockState";
         this.T = this.fieldBuilder(string, clazz)
            .setNameForVersion(ForgeVersion.MC_1_16_5.n(), "defaultState")
            .setTypeForVersion(ForgeVersion.MC_1_16_5.n(), MappedClasses.Zl)
            .buildField();
      }

      if (ForgeVersion.MC_1_16_5.d()) {
         Class[] classArray = new Class[]{MappedClasses.Zl};
         Class<Integer> clazz = int.class;
         boolean bl = true;
         String string = "getStateId";
         this.n = this.registerStaticMethod(string, bl, clazz, classArray);
         if (ForgeVersion.MC_1_20_6.v()) {
            Class clazz2 = MappedClasses.ZN;
            boolean bl2 = true;
            String string2 = "material";
            Class clazz3 = MappedClasses.za;
            this.g = this.registerInstanceFieldForOwner(clazz3, string2, bl2, clazz2);
         }

         Class<Boolean> clazz4 = boolean.class;
         boolean bl3 = true;
         String string3 = "canCollide";
         Class clazz5 = MappedClasses.za;
         this.o = this.registerInstanceFieldForOwner(clazz5, string3, bl3, clazz4);
         if (ForgeVersion.MC_1_20_6.d()) {
            Class[] classArray2 = new Class[]{MappedClasses.Zl, MappedClasses.YU, MappedClasses.lf, MappedClasses.Yl, MappedClasses.qF};
            Class clazz6 = MappedClasses.zr;
            boolean bl4 = true;
            String string4 = "useWithoutItem";
            Class clazz7 = MappedClasses.za;
            this.q = this.registerInstanceMethodForOwner(clazz7, string4, bl4, clazz6, classArray2);
         } else {
            Class[] classArray3 = new Class[]{MappedClasses.Zl, MappedClasses.YU, MappedClasses.lf, MappedClasses.Yl, MappedClasses.Yf, MappedClasses.qF};
            Class clazz8 = MappedClasses.zr;
            boolean bl5 = true;
            String string5 = "onBlockActivated";
            Class clazz9 = MappedClasses.za;
            this.q = this.registerInstanceMethodForOwner(clazz9, string5, bl5, clazz8, classArray3);
         }

         Class clazz10 = MappedClasses.Zl;
         boolean bl6 = true;
         String string6 = "defaultState";
         this.T = this.J(string6, bl6, clazz10);
         if (ForgeVersion.MC_1_21_0.d()) {
            if (ForgeVersion.MC_1_21_4.d()) {
               Class[] classArray4 = new Class[]{MappedClasses.F7, MappedClasses.lf, MappedClasses.Zl, boolean.class};
               Class clazz11 = MappedClasses.VK;
               boolean bl7 = true;
               String string7 = "getCloneItemStack";
               Class clazz12 = MappedClasses.za;
               this.Z = this.registerInstanceMethodForOwner(clazz12, string7, bl7, clazz11, classArray4);
            } else {
               Class[] classArray5 = new Class[]{MappedClasses.F7, MappedClasses.lf, MappedClasses.Zl};
               Class clazz13 = MappedClasses.VK;
               boolean bl8 = true;
               String string8 = "getCloneItemStack";
               this.Z = this.Y(string8, bl8, clazz13, classArray5);
            }
         } else {
            Class[] classArray6 = new Class[]{MappedClasses.zJ, MappedClasses.lf, MappedClasses.Zl};
            Class clazz14 = MappedClasses.VK;
            boolean bl9 = true;
            String string9 = "getItem";
            this.Z = this.Y(string9, bl9, clazz14, classArray6);
         }
      } else {
         Class[] classArrayx = new Class[]{MappedClasses.Zk};
         Class<Integer> clazzx = int.class;
         boolean blx = true;
         String stringx = "getIdFromBlock";
         this.n = this.registerStaticMethod(stringx, blx, clazzx, classArrayx);
         Class[] classArray7 = new Class[]{String.class};
         Class clazz15 = MappedClasses.Zk;
         boolean bl10 = true;
         String string10 = "getBlockFromName";
         this.F = this.registerStaticMethod(string10, bl10, clazz15, classArray7);
         if (ForgeVersion.MC_1_12_2.d()) {
            Class[] classArray8 = new Class[]{MappedClasses.Vv};
            Class clazz16 = MappedClasses.ZN;
            boolean bl11 = true;
            String string11 = "getMaterial";
            this.X = this.Y(string11, bl11, clazz16, classArray8);
         } else {
            Class[] classArray9 = new Class[0];
            Class clazz17 = MappedClasses.ZN;
            boolean bl12 = true;
            String string12 = "getMaterial";
            this.X = this.Y(string12, bl12, clazz17, classArray9);
         }
      }

      if (ForgeVersion.MC_1_12_2.v()) {
         Class[] classArrayxx = new Class[0];
         Class<Integer> clazzxx = int.class;
         boolean blxx = true;
         String stringxx = "getRenderType";
         this.W = this.Y(stringxx, blxx, clazzxx, classArrayxx);
      }

      if (ForgeVersion.MC_1_7_10.L()) {
         Class[] classArrayxx = new Class[]{MappedClasses.YU, int.class, int.class, int.class, MappedClasses.zc};
         Class<Void> clazzxx = void.class;
         boolean blxx = true;
         String stringxx = "onEntityWalking";
         this.r = this.Y(stringxx, blxx, clazzxx, classArrayxx);
         Class[] classArray10 = new Class[0];
         Class<Integer> clazz18 = int.class;
         boolean bl13 = true;
         String string13 = "getRenderBlockPass";
         this.w = this.Y(string13, bl13, clazz18, classArray10);
         Class[] classArray11 = new Class[]{MappedClasses.YU, int.class, int.class, int.class};
         Class clazz19 = MappedClasses.uk;
         boolean bl14 = true;
         String string14 = "getCollisionBoundingBoxFromPool";
         this.u = this.Y(string14, bl14, clazz19, classArray11);
         Class[] classArray12 = new Class[]{int.class, boolean.class};
         Class<Boolean> clazz20 = boolean.class;
         boolean bl15 = true;
         String string15 = Wrapper.vapeInstance.isVanillaMinecraftPresent() ? "canStopRayTrace" : "canCollideCheck";
         this.E = this.Y(string15, bl15, clazz20, classArray12);
         Class[] classArray13 = new Class[]{
            MappedClasses.YU, int.class, int.class, int.class, MappedClasses.Yl, int.class, float.class, float.class, float.class
         };
         Class<Boolean> clazz21 = boolean.class;
         boolean bl16 = true;
         String string16 = "onBlockActivated";
         this.q = this.Y(string16, bl16, clazz21, classArray13);
         Class[] classArray14 = new Class[0];
         Class<Boolean> clazz22 = boolean.class;
         boolean bl17 = true;
         String string17 = "isNormalCube";
         this.K = this.Y(string17, bl17, clazz22, classArray14);
      } else {
         if (ForgeVersion.MC_1_12_2.d() && ForgeVersion.MC_1_16_5.v()) {
            Class[] classArrayxx = new Class[]{
               MappedClasses.YU,
               MappedClasses.lf,
               MappedClasses.Vv,
               MappedClasses.Yl,
               MappedClasses.Yf,
               MappedClasses.q0,
               float.class,
               float.class,
               float.class
            };
            Class<Boolean> clazzxx = boolean.class;
            boolean blxx = true;
            String stringxx = "onBlockActivated";
            this.q = this.Y(stringxx, blxx, clazzxx, classArrayxx);
         } else if (ForgeVersion.MC_1_8_9.d() && ForgeVersion.MC_1_12_2.v()) {
            Class[] classArrayxx = new Class[]{
               MappedClasses.YU, MappedClasses.lf, MappedClasses.Vv, MappedClasses.Yl, MappedClasses.q0, float.class, float.class, float.class
            };
            Class<Boolean> clazzxx = boolean.class;
            boolean blxx = true;
            String stringxx = "onBlockActivated";
            this.q = this.Y(stringxx, blxx, clazzxx, classArrayxx);
         }

         if (ForgeVersion.MC_1_12_2.d()) {
            if (ForgeVersion.MC_1_16_5.v()) {
               Class[] classArrayxx = new Class[]{MappedClasses.Vv, MappedClasses.zR, MappedClasses.lf, MappedClasses.q0};
               Class<Boolean> clazzxx = boolean.class;
               boolean blxx = true;
               String stringxx = "shouldSideBeRendered";
               this.J = this.Y(stringxx, blxx, clazzxx, classArrayxx);
               Class[] classArray15 = new Class[]{MappedClasses.Vv};
               Class<Boolean> clazz23 = boolean.class;
               boolean bl18 = true;
               String string18 = "isBlockNormalCube";
               this.P = this.Y(string18, bl18, clazz23, classArray15);
               Class[] classArray16 = new Class[]{MappedClasses.Vv};
               Class<Boolean> clazz24 = boolean.class;
               boolean bl19 = true;
               String string19 = "isNormalCube";
               this.K = this.Y(string19, bl19, clazz24, classArray16);
               Class[] classArray17 = new Class[]{MappedClasses.Vv};
               Class<Float> clazz25 = float.class;
               boolean bl20 = true;
               String string20 = "getAmbientOcclusionLightValue";
               this.M = this.Y(string20, bl20, clazz25, classArray17);
               Class[] classArray18 = new Class[]{MappedClasses.Vv, MappedClasses.zR, MappedClasses.lf};
               Class clazz26 = MappedClasses.uk;
               boolean bl21 = true;
               String string21 = "getCollisionBoundingBox";
               this.u = this.Y(string21, bl21, clazz26, classArray18);
            }
         } else {
            Class[] classArrayxx = new Class[]{MappedClasses.YU, MappedClasses.lf, MappedClasses.Vv};
            Class clazzxx = MappedClasses.uk;
            boolean blxx = true;
            String stringxx = "getCollisionBoundingBox";
            this.u = this.Y(stringxx, blxx, clazzxx, classArrayxx);
            Class[] classArray19 = new Class[0];
            Class<Boolean> clazz27 = boolean.class;
            boolean bl22 = true;
            String string22 = "isBlockNormalCube";
            this.P = this.Y(string22, bl22, clazz27, classArray19);
            Class[] classArray20 = new Class[0];
            Class<Boolean> clazz28 = boolean.class;
            boolean bl23 = true;
            String string23 = "isNormalCube";
            this.K = this.Y(string23, bl23, clazz28, classArray20);
            Class[] classArray21 = new Class[0];
            Class<Float> clazz29 = float.class;
            boolean bl24 = true;
            String string24 = "getAmbientOcclusionLightValue";
            this.M = this.Y(string24, bl24, clazz29, classArray21);
            if (ForgeVersion.MC_1_16_5.v()) {
               Class[] classArray22 = new Class[]{MappedClasses.zR, MappedClasses.lf, MappedClasses.q0};
               Class<Boolean> clazz30 = boolean.class;
               boolean bl25 = true;
               String string25 = "shouldSideBeRendered";
               this.J = this.Y(string25, bl25, clazz30, classArray22);
            }
         }

         if (ForgeVersion.MC_1_16_5.v()) {
            Class[] classArrayxxx = new Class[0];
            Class clazzxxx = MappedClasses.E;
            boolean blxxx = true;
            String stringxxx = ForgeVersion.c() >= 23 ? "getRenderLayer" : "getBlockLayer";
            this.S = this.Y(stringxxx, blxxx, clazzxxx, classArrayxxx);
            Class[] classArray23 = new Class[]{MappedClasses.Vv, MappedClasses.zR, MappedClasses.lf};
            Class clazz31 = MappedClasses.Vv;
            boolean bl26 = true;
            String string26 = "getActualState";
            this.j = this.Y(string26, bl26, clazz31, classArray23);
            Class[] classArray24 = new Class[]{MappedClasses.Vv, boolean.class};
            Class<Boolean> clazz32 = boolean.class;
            boolean bl27 = true;
            String string27 = "canCollideCheck";
            this.E = this.Y(string27, bl27, clazz32, classArray24);
         }
      }

      if (ForgeVersion.MC_1_8_9.B()) {
         if (ForgeVersion.MC_1_7_10.L()) {
            Class[] classArrayxx = new Class[]{MappedClasses.YU, int.class, int.class, int.class};
            Class<Integer> clazzxx = int.class;
            boolean blxx = true;
            String stringxx = "getDamageValue";
            this.k = this.Y(stringxx, blxx, clazzxx, classArrayxx);
         } else {
            Class[] classArrayxx = new Class[]{MappedClasses.YU, MappedClasses.lf};
            Class<Integer> clazzxx = int.class;
            boolean blxx = true;
            String stringxx = "getDamageValue";
            this.k = this.Y(stringxx, blxx, clazzxx, classArrayxx);
         }
      } else if (ForgeVersion.MC_1_16_5.v()) {
         Class[] classArrayxx = new Class[]{MappedClasses.Vv};
         Class<Integer> clazzxx = int.class;
         boolean blxx = true;
         String stringxx = "getMetaFromState";
         this.k = this.Y(stringxx, blxx, clazzxx, classArrayxx);
      }

      if (ForgeVersion.MC_1_16_5.d()) {
         Class[] classArrayxx = new Class[0];
         Class<Boolean> clazzxx = boolean.class;
         String stringxx = "isSolid";
         this.O = this.methodBuilder(stringxx, clazzxx, classArrayxx)
            .setOwnerClass(ForgeVersion.MC_1_20_6.v() ? MappedClasses.ZN : MappedClasses.Fj)
            .buildMethod();
         Class<Float> clazz33 = float.class;
         boolean blxx = true;
         String string28 = "slipperiness";
         Class clazz34 = MappedClasses.za;
         this.Q = this.registerInstanceFieldForOwner(clazz34, string28, blxx, clazz33);
         if (ForgeVersion.MC_1_20_6.d()) {
            Class clazz35 = MappedClasses.lz;
            boolean bl28 = true;
            String string29 = "BLOCK";
            Class clazz36 = MappedClasses.R;
            this.U = this.registerStaticFieldForOwner(clazz36, string29, bl28, clazz35);
         } else {
            Class clazz37 = MappedClasses.lz;
            boolean bl29 = true;
            String string30 = "BLOCK";
            Class clazz38 = MappedClasses.Fk;
            this.U = this.registerStaticFieldForOwner(clazz38, string30, bl29, clazz37);
         }

         if (ForgeVersion.MC_1_21_4.d()) {
            Class<String> clazz39 = String.class;
            boolean bl30 = true;
            String string31 = "translationKey";
            Class clazz40 = MappedClasses.za;
            this.D = this.registerInstanceFieldForOwner(clazz40, string31, bl30, clazz39);
            Class[] classArray25 = new Class[]{MappedClasses.zc};
            Class<Boolean> clazz41 = boolean.class;
            String string32 = "canEntityWalkOnPowderSnow";
            this.B = this.methodBuilder(string32, clazz41, classArray25).setOwnerClass(MappedClasses.DM).setStaticMember(true).buildMethod();
            Class[] classArray26 = new Class[]{MappedClasses.zJ, MappedClasses.zc};
            Class<Void> clazz42 = void.class;
            String string33 = "updateEntityMovementAfterFallOn";
            this.H = this.methodBuilder(string33, clazz42, classArray26).buildMethod();
         } else {
            Class<String> clazz43 = String.class;
            boolean bl31 = true;
            String string34 = "translationKey";
            this.D = this.J(string34, bl31, clazz43);
         }
      } else {
         Class<Float> clazzxxx = float.class;
         boolean blxxx = true;
         String stringxxx = "slipperiness";
         this.Q = this.J(stringxxx, blxxx, clazzxxx);
         Class<String> clazz44 = String.class;
         boolean bl32 = true;
         String string35 = ForgeVersion.c() >= 23 ? "translationKey" : "unlocalizedName";
         this.D = this.J(string35, bl32, clazz44);
      }
   }

   public static Object d(MBlock mBlock, Object object, Object object2, int n, int n2, int n3) {
      return mBlock.z(object, object2, n, n2, n3);
   }

   public float F(Object object) {
      return ForgeVersion.MC_1_16_5.d() && ForgeVersion.MC_1_17.v() ? this.x.getFloat(this.T.getObject(object)) : this.x.getFloat(object);
   }

   public static Object E(MBlock mBlock, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
      return mBlock.d(object, object2, object3, object4, object5, object6, object7);
   }

   public static Object y(MBlock mBlock, Object object, Object object2, Object object3, Object object4) {
      return mBlock.o(object, object2, object3, object4);
   }

   private Object d(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
      return ForgeVersion.MC_1_20_6.d()
         ? this.q.invokeObject(object, object2, object3, object4, object5, object7)
         : this.q.invokeObject(object, object2, object3, object4, object5, object6, object7);
   }

   private boolean W(Object object, Object object2, int n, int n2, int n3, Object object3, int n4, float f, float f2, float f3) {
      return this.q.invokeBoolean(object, object2, n, n2, n3, object3, n4, f, f2, f3);
   }

   public static boolean G(
      MBlock mBlock, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, float f, float f2, float f3
   ) {
      return mBlock.X(object, object2, object3, object4, object5, object6, object7, f, f2, f3);
   }

   private Object o(Object object, Object object2, Object object3, Object object4) {
      return this.v.invokeObject(object, object2, object3, object4);
   }

   public static boolean w(MBlock mBlock, Object object, Object object2, int n, int n2, int n3, Object object3, int n4, float f, float f2, float f3) {
      return mBlock.W(object, object2, n, n2, n3, object3, n4, f, f2, f3);
   }

   public static boolean k$src$Z$8ngnn6(MBlock mBlock, Object object) {
      return mBlock.k$src$Z$1m9upos(object);
   }

   public boolean A(Object object) {
      return this.O.invokeBoolean(object);
   }

   public static Object z(MBlock mBlock, Object object, Object object2) {
      return mBlock.W(object, object2);
   }

   public static Object k(MBlock mBlock, Object object) {
      return mBlock.x(object);
   }

   private void Z(Object object, Object object2, Object object3) {
      this.H.invokeVoid(object, object2, object3);
   }

   private Object x(Object object) {
      return this.g.getObject(object);
   }

   private float T(Object object) {
      return this.Q.getFloat(object);
   }

   public float getPlayerRelativeBlockHardness(Object block, Object player, Object world, Object blockPos) {
      return this.playerRelativeBlockHardnessMethod.invokeFloat(block, player, world, blockPos);
   }

   static {
      Y(null);
   }
}
