package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class MEntity extends Mapping {
   private MappingMethod X;
   public MappingMethod jw;
   private MappingMethod ju;
   public final MappingField jH;
   private MappingMethod jU;
   private final MappingMethod j_;
   private MappingMethod jE;
   public MappingMethod jh;
   public MappingMethod K;
   public final MappingField U;
   public MappingMethod p;
   private MappingMethod jV;
   private MappingField T;
   private final MappingField E;
   private MappingField H;
   private MappingMethod e;
   private static int F;
   public MappingMethod jm;
   public final MappingField j8;
   public MappingMethod jC;
   private MappingMethod j4;
   public MappingMethod jL;
   private MappingMethod x;
   public MappingMethod je;
   private MappingMethod jt;
   public MappingMethod jQ;
   private final MappingMethod ja;
   private MappingField jl;
   public final MappingField jk;
   public final MappingField jg;
   private MappingMethod n;
   private MappingMethod jo;
   private MappingMethod a;
   private MappingField z;
   private final MappingField jF;
   public MappingField jd;
   private MappingMethod B1;
   public MappingField jB;
   private final MappingField g;
   private MappingMethod js;
   public MappingField jq;
   private final MappingMethod Z;
   public final MappingField jR;
   private final MappingMethod jY;
   private final MappingMethod j1;
   private MappingField j6;
   public MappingField jy;
   private final MappingField N;
   private MappingField S;
   private final MappingMethod jI;
   private MappingMethod jS;
   public MappingField jD;
   public MappingMethod jA;
   private MappingField i;
   public MappingField jp;
   public final MappingField j7;
   public MappingField w;
   private MappingMethod C;
   public MappingMethod jf;
   private MappingMethod l;
   private MappingMethod D;
   public final MappingField P;
   private MappingMethod jx;
   public final MappingField jK;
   public final MappingField jv;
   private static final String[] cb;
   private MappingField jM;
   public MappingField r;
   private MappingField y;
   private MappingMethod jn;
   public MappingMethod J;
   public final MappingField b;
   public final MappingMethod jO;
   public final MappingField A;
   public MappingMethod j0;
   public MappingMethod jz;
   private MappingMethod B;
   public final MappingField d;
   private MappingMethod jN;
   public MappingMethod V;
   private MappingField jP;
   private MappingMethod jj;
   private MappingField M;
   public final MappingField s;
   private static final String[] gb = new String[144];
   private final MappingMethod u;
   private MappingField k;
   public MappingField j3;
   public final MappingField j5;
   public MappingField L;
   private MappingField I;
   private static final Map hb = new HashMap(13);
   private MappingMethod v;
   private final MappingMethod jX;
   public final MappingField m;
   public MappingMethod q;
   public MappingMethod jW;
   private final MappingMethod W;
   private final MappingField f;
   public MappingMethod jG;
   public final MappingField h;
   private MappingMethod j;
   private MappingMethod Y;
   public MappingField j9;
   private final MappingMethod G;
   private MappingField jZ;
   public MappingMethod jc;
   public MappingMethod Q;
   public MappingMethod jb;
   public final MappingField jJ;
   public final MappingField ji;
   private MappingMethod o;
   private MappingMethod jr;
   private final MappingMethod j2;
   public final MappingField jT;
   public MappingField c;
   public MappingMethod O;

   private float j(Object object) {
      return this.jv.getFloat(object);
   }

   private void S(Object object, double d) {
      this.f.setDouble(object, d);
   }

   public static boolean n$src$Z$15p2a9x(MEntity mEntity, Object object) {
      return mEntity.D$src$Z$h0hi83(object);
   }

   public static float W$src$F$ez5czm(MEntity mEntity, Object object) {
      return mEntity.I(object);
   }

   public static float m(MEntity mEntity, Object object) {
      return mEntity.o(object);
   }

   public static void s(MEntity mEntity, Object object, double d, double d2, double d3) {
      mEntity.a(object, d, d2, d3);
   }

   private void z(Object object, Object object2) {
      this.T.setObject(object, object2);
   }

   private boolean T(Object object) {
      return this.jC.invokeBoolean(object);
   }

   private Object l(Object object, float f) {
      return this.D.invokeObject(object, f);
   }

   private void j(Object object, Object object2) {
      this.p.invokeVoid(object, object2);
   }

   private void a(Object object, Object object2) {
      this.jK.setObject(object, object2);
   }

   public static void A(MEntity mEntity, Object object, double d) {
      mEntity.g(object, d);
   }

   public static int T() {
      return F;
   }

   public static boolean U$src$Z$1t5jlgs(MEntity mEntity, Object object) {
      return mEntity.H(object);
   }

   private void g(Object object, double d) {
      this.jJ.setDouble(object, d);
   }

   public static void z(MEntity mEntity, Object object, boolean bl) {
      mEntity.Z(object, bl);
   }

   public static Object S$src$Ljava_lang_Object_$1hbhkom(MEntity mEntity, Object object) {
      return mEntity.n(object);
   }

   public static boolean W$src$Z$ez5dgu(MEntity mEntity, Object object) {
      return mEntity.n$src$Z$t9d06l(object);
   }

   private Object x(Object object) {
      return this.jA.invokeObject(object);
   }

   public static double u(MEntity mEntity, Object object) {
      return mEntity.E(object);
   }

   public static void c(MEntity mEntity, Object object, double d, double d2, double d3) {
      mEntity.F(object, d, d2, d3);
   }

   private boolean g(Object object) {
      return this.jm.invokeBoolean(object);
   }

   public static boolean A(MEntity mEntity, Object object, double d, double d2, double d3) {
      return mEntity.C(object, d, d2, d3);
   }

   private void v(Object object, boolean bl, boolean bl2, Object object2) {
      this.jN.invokeVoid(object, bl, bl2, object2);
   }

   private double W(Object object) {
      return this.jq.getDouble(object);
   }

   public static Object W(MEntity mEntity, Object object, Object object2) {
      return mEntity.Q(object, object2);
   }

   public static void q(MEntity mEntity, Object object, double d) {
      mEntity.z(object, d);
   }

   public static void A(MEntity mEntity, Object object, boolean bl) {
      mEntity.p(object, bl);
   }

   private boolean O(Object object) {
      return this.jf.invokeBoolean(object);
   }

   private Object n(Object object) {
      return this.jH.getObject(object);
   }

   public static void O(MEntity mEntity, Object object, double d, boolean bl, Object object2, Object object3) {
      mEntity.j(object, d, bl, object2, object3);
   }

   private double t(Object object) {
      return this.jR.getDouble(object);
   }

   private void T(Object object, double d) {
      this.w.setDouble(object, d);
   }

   private float S(Object object) {
      return this.jp.getFloat(object);
   }

   private void u(Object object, double d, boolean bl, Object object2, Object object3) {
      this.j.invokeVoid(object, d, bl, object2, object3);
   }

   private int j$src$I$hy2k2i(Object object) {
      return this.A.getInt(object);
   }

   private double u(Object object) {
      return this.E.getDouble(object);
   }

   public static void O(MEntity mEntity, Object object, float f) {
      mEntity.g(object, f);
   }

   public static Object M(MEntity mEntity, Object object) {
      return mEntity.k(object);
   }

   private void D(Object object, float f, float f2, float f3, float f4) {
      if (ForgeVersion.MC_1_12_2.d()) {
         this.jb.invokeVoid(object, f, f2, f3, f4);
      } else {
         this.jb.invokeVoid(object, f, f3, f4);
      }
   }

   public static int s(MEntity mEntity, Object object) {
      return mEntity.j$src$I$hy2k2i(object);
   }

   public static Object Y$src$Ljava_lang_Object_$qb2bao(MEntity mEntity, Object object) {
      return mEntity.W$src$Ljava_lang_Object_$g6hqq2(object);
   }

   private void w(Object object, float f) {
      this.jT.setFloat(object, f);
   }

   private static String b(int n, long l) {
      throw new UnsupportedOperationException("bootstrap retained from authoritative classfile");
   }

   public static double Y(MEntity mEntity, Object object, Object object2) {
      return mEntity.q(object, object2);
   }

   private void w(Object object, double d) {
      this.L.setDouble(object, d);
   }

   private void F(Object object, double d, double d2, double d3) {
      this.j0.invokeVoid(object, d, d2, d3);
   }

   public static void k(MEntity mEntity, Object object, double d, boolean bl) {
      mEntity.Y(object, d, bl);
   }

   public static float c(MEntity mEntity, Object object) {
      return mEntity.V(object);
   }

   public static double D(MEntity mEntity, Object object) {
      return mEntity.v(object);
   }

   public static void h(MEntity mEntity, Object object, float f, Object object2) {
      mEntity.Z(object, f, object2);
   }

   public static void W(MEntity mEntity, Object object, float f) {
      mEntity.a(object, f);
   }

   public static double l(MEntity mEntity, Object object) {
      return mEntity.F(object);
   }

   public static Object P$src$Ljava_lang_Object_$vb56e1(MEntity mEntity, Object object) {
      return mEntity.X$src$Ljava_lang_Object_$14coh2h(object);
   }

   private boolean s$src$Z$1p5r2b6(Object object) {
      return this.o.invokeBoolean(object);
   }

   private void v(Object object, boolean bl) {
      this.d.setBoolean(object, bl);
   }

   private static String b(byte[] byArray) {
      int n = 0;
      int n2 = byArray.length;
      char[] cArray = new char[n2];

      for (int i = 0; i < n2; i++) {
         int n3 = 255 & byArray[i];
         if (n3 < 192) {
            cArray[n++] = (char)n3;
         } else if (n3 < 224) {
            char c = (char)((char)(n3 & 31) << 6);
            int var12 = byArray[++i];
            c = (char)(c | (char)(var12 & 63));
            cArray[n++] = c;
         } else if (i < n2 - 2) {
            char c = (char)((char)(n3 & 15) << '\f');
            int var13 = byArray[++i];
            c = (char)(c | (char)(var13 & 63) << 6);
            var13 = byArray[++i];
            c = (char)(c | (char)(var13 & 63));
            cArray[n++] = c;
         }
      }

      return new String(cArray, 0, n);
   }

   private int i(Object object) {
      return this.ji.getInt(object);
   }

   private UUID M(Object object) {
      return (UUID)this.jX.invokeObject(object);
   }

   private void F(Object object, double d) {
      this.j9.setDouble(object, d);
   }

   public static void t(MEntity mEntity, Object object, double d) {
      mEntity.w(object, d);
   }

   public static UUID v$src$Ljava_util_UUID_$1bst88r(MEntity mEntity, Object object) {
      return mEntity.M(object);
   }

   public static void f(MEntity mEntity, Object object, float f) {
      mEntity.V(object, f);
   }

   private Object S$src$Ljava_lang_Object_$1pjyxam(Object object) {
      return this.jV.invokeObject(object);
   }

   public static double k(MEntity mEntity, Object object) {
      return mEntity.m(object);
   }

   private static CallSite b(Lookup lookup, String string, MethodType methodType) {
      MutableCallSite mutableCallSite = new MutableCallSite(methodType);

      try {
         mutableCallSite.setTarget(
            MethodHandles.explicitCastArguments(
               MethodHandles.insertArguments(cfr_ldc_0().asCollector(Object[].class, methodType.parameterCount()), 0, lookup, mutableCallSite, string),
               methodType
            )
         );
         return mutableCallSite;
      } catch (Exception var5) {
         throw new RuntimeException("a/bY : " + string + " : " + methodType.toString(), var5);
      }
   }

   public static long R(MEntity mEntity, Object object) {
      return mEntity.E$src$J$11ll49g(object);
   }

   public static Random M$src$Ljava_util_Random_$1y5810s(MEntity mEntity, Object object) {
      return mEntity.h$src$Ljava_util_Random_$1prf3nt(object);
   }

   private float s(Object object) {
      return this.j5.getFloat(object);
   }

   private boolean K$src$Z$j1yr7u(Object object) {
      return this.M.getBoolean(object);
   }

   public static void x(MEntity mEntity, Object object, Object object2, double d, double d2, double d3) {
      mEntity.S(object, object2, d, d2, d3);
   }

   private void l(Object object, Object object2, Object object3) {
      this.jG.invokeVoid(object, object2, object3);
   }

   private float v$src$F$1fvxv49(Object object) {
      return this.jP.getFloat(object);
   }

   public static double i(MEntity mEntity, Object object) {
      return mEntity.u(object);
   }

   public static void K(MEntity mEntity, Object object, boolean bl) {
      mEntity.O(object, bl);
   }

   public static boolean E$src$Z$14e11lo(MEntity mEntity, Object object) {
      return mEntity.K$src$Z$j1yr7u(object);
   }

   public static int P() {
      int n = T();
      return n == 0 ? 76 : 0;
   }

   private boolean v$src$Z$1fvxvlh(Object object) {
      return ForgeVersion.MC_1_17.d() ? this.H.getObject(object) != null : this.jd.getBoolean(object);
   }

   public static boolean m$src$Z$v9pdac(MEntity mEntity, Object object) {
      return mEntity.s$src$Z$1p5r2b6(object);
   }

   private float d(Object object) {
      return this.J.invokeFloat(object);
   }

   public static void O(MEntity mEntity, Object object) {
      mEntity.V$src$V$weqftl(object);
   }

   public static boolean I$src$Z$b2cnkw(MEntity mEntity, Object object) {
      return mEntity.I$src$Z$1cwvkco(object);
   }

   private void Q(Object object, double d) {
      this.E.setDouble(object, d);
   }

   public static Object K$src$Ljava_lang_Object_$1tnokji(MEntity mEntity, Object object) {
      return mEntity.Q(object);
   }

   public static boolean v(MEntity mEntity, Object object, Object object2) {
      return mEntity.U(object, object2);
   }

   public static void a(MEntity mEntity, Object object, boolean bl) {
      mEntity.u(object, bl);
   }

   public static void r(MEntity mEntity, Object object, float f) {
      mEntity.B(object, f);
   }

   public static float r(MEntity mEntity, Object object) {
      return mEntity.t$src$F$apqm9z(object);
   }

   private void l(Object object, double d, boolean bl, Object object2, Object object3) {
      this.j.invokeVoid(object, d, bl, object2, object3);
   }

   public static float h(MEntity mEntity, Object object) {
      return mEntity.S(object);
   }

   public static Object W$src$Ljava_lang_Object_$bmu1rm(MEntity mEntity, Object object) {
      return mEntity.x(object);
   }

   public static void o(MEntity mEntity, Object object, double d) {
      mEntity.s(object, d);
   }

   private boolean m$src$Z$8o9drg(Object object) {
      return this.j_.invokeBoolean(object);
   }

   public static boolean D$src$Z$tyo4m3(MEntity mEntity, Object object) {
      return mEntity.W$src$Z$1gzu2c6(object);
   }

   public static boolean e(MEntity mEntity, Object object) {
      return mEntity.i$src$Z$1we3014(object);
   }

   private boolean x$src$Z$m112gn(Object object) {
      return this.e.invokeBoolean(object);
   }

   private double r(Object object) {
      return this.j9.getDouble(object);
   }

   private void u(Object object, boolean bl) {
      this.jh.invokeVoid(object, bl);
   }

   private void Y(Object object, double d, boolean bl) {
      this.j.invokeVoid(object, d, bl);
   }

   public static boolean c$src$Z$1x2cbcq(MEntity mEntity, Object object) {
      return mEntity.v$src$Z$1fvxvlh(object);
   }

   public static void y(MEntity mEntity, Object object, double d, double d2, double d3) {
      mEntity.f(object, d, d2, d3);
   }

   private boolean I$src$Z$1cwvkco(Object object) {
      return this.x.invokeBoolean(object);
   }

   public static void q(MEntity mEntity, Object object, double d, boolean bl, Object object2, Object object3) {
      mEntity.u(object, d, bl, object2, object3);
   }

   private Object K(Object object) {
      return this.jQ.invokeObject(object);
   }

   public static void K(MEntity mEntity, Object object, double d) {
      mEntity.S(object, d);
   }

   public static void N(MEntity mEntity, Object object) {
      mEntity.b(object);
   }

   private void j(Object object, double d, boolean bl, Object object2, Object object3) {
      this.j.invokeVoid(object, d, bl, object2, object3);
   }

   private float t$src$F$apqm9z(Object object) {
      return this.jT.getFloat(object);
   }

   private Object k(Object object) {
      return this.l.invokeObject(object);
   }

   public static float H(MEntity mEntity, Object object) {
      return mEntity.E$src$F$11ll460(object);
   }

   private float A$src$F$qaaogk(Object object) {
      return this.jk.getFloat(object);
   }

   public static void d(MEntity mEntity, Object object, Object object2) {
      mEntity.a(object, object2);
   }

   public static void Q(MEntity mEntity, Object object, double d) {
      mEntity.r(object, d);
   }

   public static void P(MEntity mEntity, Object object, float f, float f2) {
      mEntity.N(object, f, f2);
   }

   public static double W(MEntity mEntity, Object object) {
      return mEntity.q(object);
   }

   private double E(Object object) {
      return this.f.getDouble(object);
   }

   private boolean C(Object object, double d, double d2, double d3) {
      return this.G.invokeBoolean(object, d, d2, d3);
   }

   private Object Q(Object object) {
      return this.X.invokeObject(object);
   }

   public static void m(MEntity mEntity, Object object, double d) {
      mEntity.T(object, d);
   }

   private void g(Object object, float f) {
      this.S.setFloat(object, f);
   }

   public static void m(MEntity mEntity, Object object, double d, double d2, double d3, float f, float f2) {
      mEntity.O(object, d, d2, d3, f, f2);
   }

   private String D$src$Ljava_lang_String_$baft5b(Object object) {
      return (String)this.jA.invokeObject(object);
   }

   private double v(Object object) {
      return this.jJ.getDouble(object);
   }

   private void i$src$V$1we2zxo(Object object) {
      this.ju.invokeVoidNoArgs(object);
   }

   private void Z(Object object, int n) {
      this.s.setInt(object, n);
   }

   private void z(Object object, boolean bl) {
      this.jW.invokeVoid(object, bl);
   }

   private void b(Object object) {
      if (this.a != null) {
         this.a.invokeVoidNoArgs(object);
      }
   }

   private void j(Object object, double d) {
      this.jq.setDouble(object, d);
   }

   public static Object T(MEntity mEntity, Object object) {
      return mEntity.p(object);
   }

   public static boolean P$src$Z$d0r0iv(MEntity mEntity, Object object) {
      return mEntity.Z$src$Z$17q0vmh(object);
   }

   private void W(Object object, boolean bl) {
      this.jg.setBoolean(object, bl);
   }

   private void B(Object object, float f) {
      this.jD.setFloat(object, f);
   }

   private float E$src$F$11ll460(Object object) {
      return this.S.getFloat(object);
   }

   private void V(Object object, double d) {
      this.N.setDouble(object, d);
   }

   private void w(Object object, Object object2) {
      this.j7.setObject(object, object2);
   }

   private Object X$src$Ljava_lang_Object_$14coh2h(Object object) {
      return this.y.getObject(object);
   }

   public static float j(MEntity mEntity, Object object) {
      return mEntity.d(object);
   }

   private double Z(Object object) {
      return this.N.getDouble(object);
   }

   public static Object R$src$Ljava_lang_Object_$19zdfx3(MEntity mEntity, Object object) {
      return mEntity.u$src$Ljava_lang_Object_$v0z5bg(object);
   }

   private Object U(Object object) {
      return this.j6.getObject(object);
   }

   public static void D(MEntity mEntity, Object object, boolean bl) {
      mEntity.v(object, bl);
   }

   public static void t(MEntity mEntity, Object object, Object object2, boolean bl) {
      mEntity.M(object, object2, bl);
   }

   private boolean C$src$Z$1vghxs2(Object object) {
      return this.jr.invokeBoolean(object);
   }

   public static float S(MEntity mEntity, Object object) {
      return mEntity.s(object);
   }

   public static float Q(MEntity mEntity, Object object, Object object2) {
      return mEntity.getDistanceToEntity(object, object2);
   }

   private Object h(Object object, Object object2) {
      return this.C.invokeObject(object, object2);
   }

   public static Object g(MEntity mEntity, Object object) {
      return mEntity.C(object);
   }

   private void L(Object object, float f) {
      this.jk.setFloat(object, f);
   }

   public static void C$src$V$jjb7j2(MEntity mEntity, Object object) {
      mEntity.t$src$V$apqmnr(object);
   }

   public static double E(MEntity mEntity, Object object) {
      return mEntity.W(object);
   }

   public static double C(MEntity mEntity, Object object) {
      return mEntity.Z(object);
   }

   private Object Q(Object object, Object object2) {
      return this.j4.invokeObject(object, object2);
   }

   private double w(Object object) {
      return this.r.getDouble(object);
   }

   public static Object t(MEntity mEntity, Object object) {
      return mEntity.G(object);
   }

   public static double p(MEntity mEntity, Object object) {
      return mEntity.A(object);
   }

   private void S(Object object, Object object2, double d, double d2, double d3) {
      this.j0.invokeVoid(object, object2, d, d2, d3);
   }

   public static void u(MEntity mEntity, Object object, Object object2) {
      mEntity.w(object, object2);
   }

   private void V(Object object, float f) {
      if (ForgeVersion.MC_1_21_6.d()) {
         this.b.setDouble(object, f);
      } else {
         this.b.setFloat(object, f);
      }
   }

   private boolean B(Object object) {
      return this.K.invokeBoolean(object);
   }

   public void k(Object object, float f) {
      this.jP.setFloat(object, f);
   }

   private Object W$src$Ljava_lang_Object_$g6hqq2(Object object) {
      return this.j7.getObject(object);
   }

   private static Object b(Lookup lookup, MutableCallSite mutableCallSite, String string, Object[] objectArray) {
      int n = (Integer)objectArray[0];
      long l = (Long)objectArray[1];
      String string2 = b(n, l);
      MethodHandle methodHandle = MethodHandles.constant(String.class, string2);
      mutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, int.class, long.class));
      return string2;
   }

   public static boolean R$src$Z$xvgui1(MEntity mEntity, Object object) {
      return mEntity.O(object);
   }

   private Object i$src$Ljava_lang_Object_$p36l2w(Object object) {
      return this.k.getObject(object);
   }

   private void O(Object object, boolean bl) {
      this.M.setBoolean(object, bl);
   }

   private void z(Object object, double d) {
      this.r.setDouble(object, d);
   }

   private Object G(Object object) {
      return this.T.getObject(object);
   }

   public static Object v(MEntity mEntity, Object object) {
      return mEntity.S$src$Ljava_lang_Object_$1pjyxam(object);
   }

   private void r(Object object, double d) {
      this.jR.setDouble(object, d);
   }

   private double F(Object object) {
      return this.jB.getDouble(object);
   }

   public static void I(MEntity mEntity, Object object, boolean bl) {
      mEntity.f(object, bl);
   }

   private Random h$src$Ljava_util_Random_$1prf3nt(Object object) {
      return (Random)this.m.getObject(object);
   }

   private void t$src$V$apqmnr(Object object) {
      this.Y.invokeVoidNoArgs(object);
   }

   public static void E(MEntity mEntity, Object object, float f) {
      mEntity.L(object, f);
   }

   public static String z$src$Ljava_lang_String_$ch83r5(MEntity mEntity, Object object) {
      return mEntity.D$src$Ljava_lang_String_$baft5b(object);
   }

   public static boolean O$src$Z$2le3ja(MEntity mEntity, Object object) {
      return mEntity.Y(object);
   }

   private void m(Object object, boolean bl) {
      this.jM.setBoolean(object, bl);
   }

   public static float l$src$F$kucftj(MEntity mEntity, Object object) {
      return mEntity.j(object);
   }

   public static void U(int n) {
      F = n;
   }

   public static boolean j$src$Z$1z0qoap(MEntity mEntity, Object object) {
      return mEntity.M$src$Z$1o86024(object);
   }

   public static void n(MEntity mEntity, Object object, double d) {
      mEntity.F(object, d);
   }

   private boolean W$src$Z$1gzu2c6(Object object) {
      return this.P.getBoolean(object);
   }

   public static void G(MEntity mEntity, Object object, int n, boolean bl) {
      mEntity.C(object, n, bl);
   }

   private double m(Object object) {
      return this.j8.getDouble(object);
   }

   private void l$src$V$1kx4efb(Object object, float f) {
      this.j5.setFloat(object, f);
   }

   private boolean D$src$Z$h0hi83(Object object) {
      return this.g.getBoolean(object);
   }

   public static boolean u$src$Z$17ngn7w(MEntity mEntity, Object object) {
      return mEntity.S$src$Z$15ojmmq(object);
   }

   private void Z(Object object, boolean bl) {
      this.jZ.setBoolean(object, bl);
   }

   public static boolean s$src$Z$msqt8q(MEntity mEntity, Object object) {
      return mEntity.N(object);
   }

   public static void n(MEntity mEntity, Object object) {
      mEntity.i$src$V$1we2zxo(object);
   }

   public static void p(MEntity mEntity, Object object, float f) {
      mEntity.l$src$V$1kx4efb(object, f);
   }

   private float getDistanceToEntity(Object object, Object object2) {
      return this.jc.invokeFloat(object, object2);
   }

   private boolean n$src$Z$t9d06l(Object object) {
      return this.U.getBoolean(object);
   }

   private boolean P(Object object) {
      return this.Z.invokeBoolean(object);
   }

   private float o(Object object) {
      return ForgeVersion.MC_1_21_6.d() ? (float)this.b.getDouble(object) : this.b.getFloat(object);
   }

   public static Object F(MEntity mEntity, Object object, Object object2) {
      return mEntity.h(object, object2);
   }

   public static void Q(MEntity mEntity, Object object, float f) {
      mEntity.w(object, f);
   }

   public static void J(MEntity mEntity, Object object, boolean bl) {
      mEntity.J(object, bl);
   }

   public static double P(MEntity mEntity, Object object) {
      return mEntity.w(object);
   }

   private Object t$src$Ljava_lang_Object_$6usez1(Object object) {
      return this.jO.invokeObject(object);
   }

   private Object u$src$Ljava_lang_Object_$v0z5bg(Object object) {
      return this.jS.invokeObject(object);
   }

   private Object h(Object object) {
      return this.jt.invokeObject(object);
   }

   private void S$src$V$15ojmja(Object object) {
      this.js.invokeVoidNoArgs(object);
   }

   private int X(Object object) {
      return this.s.getInt(object);
   }

   public static boolean a(MEntity mEntity, Object object) {
      return mEntity.T(object);
   }

   public static double q(MEntity mEntity, Object object) {
      return mEntity.r(object);
   }

   private void m(Object object, UUID uUID) {
      this.jF.setObject(object, uUID);
   }

   private void s(Object object, double d) {
      this.jB.setDouble(object, d);
   }

   private void V$src$V$weqftl(Object object) {
      this.n.invokeVoidNoArgs(object);
   }

   public static void f(MEntity mEntity, Object object, UUID uUID) {
      mEntity.m(object, uUID);
   }

   public static int X(MEntity mEntity, Object object) {
      return mEntity.i(object);
   }

   private Object p(Object object) {
      return this.jK.getObject(object);
   }

   public static void h(MEntity mEntity, Object object, double d) {
      mEntity.V(object, d);
   }

   private boolean p$src$Z$1yfk90v(Object object) {
      return this.d.getBoolean(object);
   }

   private void Y(Object object, Object object2) {
      this.y.setObject(object, object2);
   }

   public static int m$src$I$v9pcvp(MEntity mEntity, Object object) {
      return mEntity.X(object);
   }

   public static boolean V(MEntity mEntity, Object object) {
      return mEntity.p$src$Z$1yfk90v(object);
   }

   public static void i(MEntity mEntity, Object object, Object object2) {
      mEntity.Y(object, object2);
   }

   private void f(Object object, boolean bl) {
      this.I.setBoolean(object, bl);
   }

   public static void h(MEntity mEntity, Object object, int n) {
      mEntity.Z(object, n);
   }

   private boolean k$src$Z$12j66wa(Object object) {
      return this.jn.invokeBoolean(object);
   }

   public static boolean T$src$Z$1iq6oh7(MEntity mEntity, Object object) {
      return mEntity.d$src$Z$10hoxwj(object);
   }

   public static void U(MEntity mEntity, Object object, boolean bl) {
      mEntity.z(object, bl);
   }

   private void y(Object object, Object object2) {
      this.i.setObject(object, object2);
   }

   private void M(Object object, Object object2, boolean bl) {
      if (ForgeVersion.MC_1_8_9.d()) {
         this.u.invokeVoid(object, object2);
      } else {
         this.u.invokeVoid(object, object2, bl);
      }
   }

   public static Object X$src$Ljava_lang_Object_$iyy6j5(MEntity mEntity, Object object) {
      return mEntity.I$src$Ljava_lang_Object_$wxfrrs(object);
   }

   private void p(Object object, boolean bl) {
      this.U.setBoolean(object, bl);
   }

   private boolean S$src$Z$15ojmmq(Object object) {
      return this.jM.getBoolean(object);
   }

   public static void G(MEntity mEntity, Object object, double d, boolean bl, Object object2, Object object3) {
      mEntity.l(object, d, bl, object2, object3);
   }

   public static Object o(MEntity mEntity, Object object) {
      return mEntity.J(object);
   }

   public static boolean r$src$Z$cddw95(MEntity mEntity, Object object) {
      return mEntity.P(object);
   }

   private Object J(Object object) {
      return this.jx.invokeObject(object);
   }

   public static void i(MEntity mEntity, Object object, boolean bl) {
      mEntity.m(object, bl);
   }

   public static void a(MEntity mEntity, Object object, double d) {
      mEntity.Q(object, d);
   }

   private boolean M$src$Z$1o86024(Object object) {
      return this.jj.invokeBoolean(object);
   }

   private boolean U(Object object, Object object2) {
      return this.B1.invokeBoolean(object, object2);
   }

   private void a(Object object, double d, double d2, double d3) {
      this.p.invokeVoid(object, d, d2, d3);
   }

   private boolean N(Object object) {
      return this.jI.invokeBoolean(object);
   }

   public static boolean l$src$Z$kucgar(MEntity mEntity, Object object) {
      return mEntity.r$src$Z$14knfw1(object);
   }

   private void N(Object object, float f, float f2) {
      this.jY.invokeVoid(object, f, f2);
   }

   private void C(Object object, int n, boolean bl) {
      this.j2.invokeVoid(object, n, bl);
   }

   private void K(Object object, boolean bl) {
      this.g.setBoolean(object, bl);
   }

   public static Object I(MEntity mEntity, Object object) {
      return mEntity.U(object);
   }

   public static Object y$src$Ljava_lang_Object_$1bzedu8(MEntity mEntity, Object object) {
      return mEntity.K(object);
   }

   public static boolean J(MEntity mEntity, Object object) {
      return mEntity.k$src$Z$12j66wa(object);
   }

   public static boolean o$src$Z$1g4f79i(MEntity mEntity, Object object) {
      return mEntity.V$src$Z$weqfx1(object);
   }

   private Object I$src$Ljava_lang_Object_$wxfrrs(Object object) {
      return this.B.invokeObject(object);
   }

   private boolean Y(Object object) {
      return this.jg.getBoolean(object);
   }

   private boolean H(Object object) {
      return this.h.getBoolean(object);
   }

   private Object C(Object object) {
      return this.jl.getObject(object);
   }

   public static float y(MEntity mEntity, Object object) {
      return mEntity.D(object);
   }

   private float D(Object object) {
      return ForgeVersion.MC_1_16_5.d() ? this.jy.getFloat(object) : this.je.invokeFloat(object);
   }

   private double A(Object object) {
      return this.w.getDouble(object);
   }

   public static void e(MEntity mEntity, Object object, boolean bl, boolean bl2, Object object2) {
      mEntity.v(object, bl, bl2, object2);
   }

   public static void k(MEntity mEntity, Object object, Object object2, Object object3) {
      mEntity.l(object, object2, object3);
   }

   private void f(Object object, double d, double d2, double d3) {
      this.j1.invokeVoid(object, d, d2, d3);
   }

   private float V(Object object) {
      return ForgeVersion.MC_1_20_6.d() ? this.jU.invokeFloat(object) : this.jD.getFloat(object);
   }

   public static void W$src$V$1v5iu3a(MEntity mEntity, Object object, Object object2) {
      mEntity.j(object, object2);
   }

   public static boolean L(MEntity mEntity, Object object) {
      return mEntity.C$src$Z$1vghxs2(object);
   }

   public static void h$src$V$1e60u83(MEntity mEntity, Object object) {
      mEntity.S$src$V$15ojmja(object);
   }

   private void a(Object object, float f) {
      this.jv.setFloat(object, f);
   }

   public static void N(MEntity mEntity, Object object, boolean bl) {
      mEntity.W(object, bl);
   }

   private long E$src$J$11ll49g(Object object) {
      return ForgeVersion.MC_1_12_2.d() ? this.c.getLong(object) : this.c.getInt(object);
   }

   private boolean r$src$Z$14knfw1(Object object) {
      return this.jo.invokeBoolean(object);
   }

   public static boolean M$src$Z$1grsbj8(MEntity mEntity, Object object) {
      return mEntity.m$src$Z$8o9drg(object);
   }

   private void W(Object object, double d) {
      this.j8.setDouble(object, d);
   }

   public static void E(MEntity mEntity, Object object, Object object2) {
      mEntity.z(object, object2);
   }

   public static Object c$src$Ljava_lang_Object_$sn3kyu(MEntity mEntity, Object object) {
      return mEntity.t$src$Ljava_lang_Object_$6usez1(object);
   }

   public static float U(MEntity mEntity, Object object) {
      return mEntity.A$src$F$qaaogk(object);
   }

   private boolean V$src$Z$weqfx1(Object object) {
      return this.z.getBoolean(object);
   }

   private boolean d$src$Z$10hoxwj(Object object) {
      return this.jw.invokeBoolean(object);
   }

   private double q(Object object) {
      return this.L.getDouble(object);
   }

   public static boolean i$src$Z$1oldrb4(MEntity mEntity, Object object) {
      return mEntity.B(object);
   }

   private void Z(Object object, float f, Object object2) {
      this.jb.invokeVoid(object, f, object2);
   }

   public static double Y(MEntity mEntity, Object object) {
      return mEntity.t(object);
   }

   private float I(Object object) {
      return this.j3.getFloat(object);
   }

   public static boolean K$src$Z$vx2hk2(MEntity mEntity, Object object) {
      return mEntity.g(object);
   }

   public static void E(MEntity mEntity, Object object, double d) {
      mEntity.W(object, d);
   }

   public static void G(MEntity mEntity, Object object, boolean bl) {
      mEntity.K(object, bl);
   }

   private void J(Object object, boolean bl) {
      this.W.invokeVoid(object, bl);
   }

   public static void i(MEntity mEntity, Object object, double d) {
      mEntity.j(object, d);
   }

   public static void o(MEntity mEntity, Object object, boolean bl) {
      mEntity.s(object, bl);
   }

   public static Object z(MEntity mEntity, Object object) {
      return mEntity.h(object);
   }

   private double q(Object object, Object object2) {
      return this.v.invokeDouble(object, object2);
   }

   public static boolean q$src$Z$1y0z9k(MEntity mEntity, Object object) {
      return mEntity.x$src$Z$m112gn(object);
   }

   public static void V(MEntity mEntity, Object object, float f, float f2, float f3, float f4) {
      mEntity.D(object, f, f2, f3, f4);
   }

   private boolean Z$src$Z$17q0vmh(Object object) {
      return this.I.getBoolean(object);
   }

   private void s(Object object, boolean bl) {
      this.P.setBoolean(object, bl);
   }

   public static float K(MEntity mEntity, Object object) {
      return mEntity.v$src$F$1fvxv49(object);
   }

   public MEntity() {
      this(T());
   }

   private MEntity(int n) {
      super(MappedClasses.zc);
      Class<Integer> clazz = int.class;
      boolean bl = true;
      String string = "entityId";
      this.s = this.J(string, bl, clazz);
      Class<Float> clazz2 = float.class;
      boolean bl2 = true;
      String string2 = "rotationYaw";
      this.jv = this.J(string2, bl2, clazz2);
      Class<Float> clazz3 = float.class;
      boolean bl3 = true;
      String string3 = "rotationPitch";
      this.jT = this.J(string3, bl3, clazz3);
      Class<Boolean> clazz4 = boolean.class;
      boolean bl4 = true;
      String string4 = "onGround";
      this.U = this.J(string4, bl4, clazz4);
      Class<Integer> clazz5 = int.class;
      boolean bl5 = true;
      String string5 = "ticksExisted";
      this.A = this.J(string5, bl5, clazz5);
      Class<Double> clazz6 = double.class;
      boolean bl6 = true;
      String string6 = "lastTickPosX";
      this.j8 = this.J(string6, bl6, clazz6);
      Class<Double> clazz7 = double.class;
      boolean bl7 = true;
      String string7 = "lastTickPosY";
      this.jR = this.J(string7, bl7, clazz7);
      Class<Double> clazz8 = double.class;
      boolean bl8 = true;
      String string8 = "lastTickPosZ";
      this.jJ = this.J(string8, bl8, clazz8);
      Class<Float> clazz9 = float.class;
      boolean bl9 = true;
      String string9 = "prevRotationYaw";
      this.j5 = this.J(string9, bl9, clazz9);
      Class<Float> clazz10 = float.class;
      boolean bl10 = true;
      String string10 = "prevRotationPitch";
      this.jk = this.J(string10, bl10, clazz10);
      Class<Double> clazz11 = double.class;
      boolean bl11 = true;
      String string11 = "prevPosX";
      this.f = this.J(string11, bl11, clazz11);
      Class<Double> clazz12 = double.class;
      boolean bl12 = true;
      String string12 = "prevPosY";
      this.N = this.J(string12, bl12, clazz12);
      Class<Double> clazz13 = double.class;
      boolean bl13 = true;
      String string13 = "prevPosZ";
      this.E = this.J(string13, bl13, clazz13);
      Class clazz14 = MappedClasses.uk;
      boolean bl14 = true;
      String string14 = "boundingBox";
      this.j7 = this.J(string14, bl14, clazz14);
      Class clazz15 = MappedClasses.zc;
      boolean bl15 = true;
      String string15 = "ridingEntity";
      this.jK = this.J(string15, bl15, clazz15);
      Class<Random> clazz16 = Random.class;
      boolean bl16 = true;
      String string16 = "rand";
      this.m = this.J(string16, bl16, clazz16);
      Class<Boolean> clazz17 = boolean.class;
      boolean bl17 = true;
      String string17 = "noClip";
      this.jg = this.J(string17, bl17, clazz17);
      Class<Float> clazz18 = float.class;
      boolean bl18 = true;
      String string18 = "fallDistance";
      this.b = this.J(string18, bl18, clazz18);
      Class<Integer> clazz19 = int.class;
      boolean bl19 = true;
      String string19 = "hurtResistantTime";
      this.ji = this.J(string19, bl19, clazz19);
      Class<UUID> clazz20 = UUID.class;
      boolean bl20 = true;
      String string20 = "entityUniqueID";
      this.jF = this.J(string20, bl20, clazz20);
      Class<Boolean> clazz21 = boolean.class;
      boolean bl21 = true;
      String string21 = "preventEntitySpawning";
      this.h = this.J(string21, bl21, clazz21);
      if (ForgeVersion.MC_1_16_5.d()) {
         Class clazz22 = MappedClasses.qP;
         boolean bl22 = true;
         String string22 = "positionVec";
         this.j6 = this.J(string22, bl22, clazz22);
         Class clazz23 = MappedClasses.qP;
         boolean bl23 = true;
         String string23 = "motion";
         this.y = this.J(string23, bl23, clazz23);
         if (ForgeVersion.MC_1_17.d()) {
            Class clazz24 = MappedClasses.c;
            boolean bl24 = true;
            String string24 = "removalReason";
            this.H = this.J(string24, bl24, clazz24);
         } else {
            Class<Boolean> clazz25 = boolean.class;
            boolean bl25 = true;
            String string25 = "removed";
            this.jd = this.J(string25, bl25, clazz25);
         }

         Class clazz26 = MappedClasses.Ve;
         boolean bl26 = true;
         String string26 = "size";
         this.jl = this.J(string26, bl26, clazz26);
         Class<Boolean> clazz27 = boolean.class;
         boolean bl27 = true;
         String string27 = "collidedHorizontally";
         this.P = this.J(string27, bl27, clazz27);
         Class<Boolean> clazz28 = boolean.class;
         boolean bl28 = true;
         String string28 = "collidedVertically";
         this.d = this.J(string28, bl28, clazz28);
         Class<Float> clazz29 = float.class;
         boolean bl29 = true;
         String string29 = "eyeHeight";
         this.jy = this.J(string29, bl29, clazz29);
         Class[] classArray = new Class[]{MappedClasses.zc};
         Class<Float> clazz30 = float.class;
         boolean bl30 = true;
         String string30 = "getDistance";
         this.jc = this.Y(string30, bl30, clazz30, classArray);
         Class[] classArray2 = new Class[0];
         Class clazz31 = MappedClasses.Yr;
         String string31 = "getName";
         this.jA = this.methodBuilder(string31, clazz31, classArray2).setOwnerClassForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.Yp).buildMethod();
         Class[] classArray3 = new Class[]{double.class, double.class};
         Class<Void> clazz32 = void.class;
         boolean bl31 = true;
         String string32 = "rotateTowards";
         this.jY = this.Y(string32, bl31, clazz32, classArray3);
         Class[] classArray4 = new Class[0];
         Class<Void> clazz33 = void.class;
         boolean bl32 = true;
         String string33 = "tick";
         this.V = this.Y(string33, bl32, clazz33, classArray4);
         if (ForgeVersion.MC_1_17.d() && ForgeVersion.MC_1_20_6.v()) {
            Class[] classArray5 = new Class[]{float.class};
            Class clazz34 = MappedClasses.qP;
            boolean bl33 = false;
            String string34 = "m_20299_";
            this.D = this.Y(string34, bl33, clazz34, classArray5);
         } else {
            Class[] classArray6 = new Class[]{float.class};
            Class clazz35 = MappedClasses.qP;
            boolean bl34 = true;
            String string35 = "getEyePosition";
            this.D = this.Y(string35, bl34, clazz35, classArray6);
         }

         Class[] classArray7 = new Class[]{MappedClasses.qC};
         Class<Double> clazz36 = double.class;
         boolean bl35 = true;
         String string36 = "getFluidHeight";
         this.v = this.Y(string36, bl35, clazz36, classArray7);
         if (ForgeVersion.MC_26_1.v()) {
            Class[] classArray8 = new Class[0];
            Class<Void> clazz37 = void.class;
            String string37 = "updateFluidOnEyes";
            this.a = this.methodBuilder(string37, clazz37, classArray8)
               .setNameForVersion(ForgeVersion.MC_1_20_6.b(), "func_205012_q")
               .setMappedMemberForVersion(ForgeVersion.MC_1_20_6.b(), Wrapper.isNativeAvailable)
               .buildMethod();
         }

         Class[] classArray9 = new Class[0];
         Class clazz38 = MappedClasses.lf;
         String string38 = "getBlockPosBelowThatAffectsMyMovement";
         this.l = this.methodBuilder(string38, clazz38, classArray9)
            .setNameForVersion(ForgeVersion.MC_1_20_6.b(), "func_226270_aj_")
            .setMappedMemberForVersion(ForgeVersion.MC_1_20_6.b(), Wrapper.isNativeAvailable)
            .buildMethod();
         if (ForgeVersion.MC_1_21_4.d()) {
            Class[] classArray10 = new Class[0];
            Class<Void> clazz39 = void.class;
            String string39 = "checkBelowWorld";
            this.js = this.methodBuilder(string39, clazz39, classArray10).buildMethod();
            Class[] classArray11 = new Class[0];
            Class clazz40 = MappedClasses.lf;
            String string40 = "getOnPosLegacy";
            this.jV = this.methodBuilder(string40, clazz40, classArray11).buildMethod();
            Class[] classArray12 = new Class[]{MappedClasses.qP};
            Class clazz41 = MappedClasses.qP;
            String string41 = "collide";
            this.j4 = this.methodBuilder(string41, clazz41, classArray12).buildMethod();
         }

         Class[] classArray13 = new Class[0];
         Class<Double> clazz42 = double.class;
         boolean bl36 = true;
         String string42 = "getPosY";
         this.jz = this.Y(string42, bl36, clazz42, classArray13);
         Class[] classArray14 = new Class[0];
         Class<Void> clazz43 = void.class;
         boolean bl37 = true;
         String string43 = "remove";
         this.ju = this.Y(string43, bl37, clazz43, classArray14);
         Class[] classArray15 = new Class[0];
         Class<Boolean> clazz44 = boolean.class;
         boolean bl38 = true;
         String string44 = "isSpectator";
         this.jn = this.Y(string44, bl38, clazz44, classArray15);
         Class[] classArray16 = new Class[0];
         Class clazz45 = MappedClasses.zc;
         boolean bl39 = true;
         String string45 = "getLowestRidingEntity";
         this.X = this.Y(string45, bl39, clazz45, classArray16);
         Class[] classArray17 = new Class[0];
         Class<Boolean> clazz46 = boolean.class;
         boolean bl40 = true;
         String string46 = "isPickable";
         this.x = this.Y(string46, bl40, clazz46, classArray17);
         Class[] classArray18 = new Class[0];
         Class<Boolean> clazz47 = boolean.class;
         boolean bl41 = true;
         String string47 = "isVisuallySwimming";
         this.e = this.Y(string47, bl41, clazz47, classArray18);
         if (ForgeVersion.MC_26_1.d()) {
            Class[] classArray19 = new Class[0];
            Class<Boolean> clazz48 = boolean.class;
            boolean bl42 = true;
            String string48 = "updateFluidInteraction";
            this.jj = this.Y(string48, bl42, clazz48, classArray19);
         } else {
            Class[] classArray20 = new Class[0];
            Class<Boolean> clazz49 = boolean.class;
            boolean bl43 = true;
            String string49 = "updateInWaterStateAndDoFluidPushing";
            this.jj = this.Y(string49, bl43, clazz49, classArray20);
         }

         Class[] classArray21 = new Class[0];
         Class clazz50 = MappedClasses.qP;
         boolean bl44 = true;
         String string50 = "getLookAngle";
         this.jS = this.Y(string50, bl44, clazz50, classArray21);
         Class[] classArray22 = new Class[0];
         Class clazz51 = MappedClasses.lf;
         String string51 = "blockPosition";
         this.jt = this.methodBuilder(string51, clazz51, classArray22).setOwnerClassForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.lD).buildMethod();
         if (ForgeVersion.MC_1_20_6.d()) {
            Class[] classArray23 = new Class[0];
            Class<Float> clazz52 = float.class;
            boolean bl45 = true;
            String string52 = "getYRot";
            this.Q = this.Y(string52, bl45, clazz52, classArray23);
            Class[] classArray24 = new Class[0];
            Class<Float> clazz53 = float.class;
            boolean bl46 = true;
            String string53 = "getXRot";
            this.q = this.Y(string53, bl46, clazz53, classArray24);
            Class[] classArray25 = new Class[0];
            Class<Boolean> clazz54 = boolean.class;
            boolean bl47 = true;
            String string54 = "onGround";
            this.O = this.Y(string54, bl47, clazz54, classArray25);
            Class[] classArray26 = new Class[0];
            Class<Void> clazz55 = void.class;
            String string55 = "refreshDimensions";
            this.Y = this.methodBuilder(string55, clazz55, classArray26).buildMethod();
            Class[] classArray27 = new Class[0];
            Class clazz56 = MappedClasses.Zl;
            String string56 = "getInBlockState";
            this.B = this.methodBuilder(string56, clazz56, classArray27).buildMethod();
            Class clazz57 = MappedClasses.Zl;
            String string57 = "inBlockState";
            this.i = this.fieldBuilder(string57, clazz57).buildField();
            Class<Boolean> clazz58 = boolean.class;
            String string58 = "minorHorizontalCollision";
            this.jM = this.fieldBuilder(string58, clazz58).buildField();
            Class<Boolean> clazz59 = boolean.class;
            String string59 = "isInPowderSnow";
            this.I = this.fieldBuilder(string59, clazz59).buildField();
            Class<Boolean> clazz60 = boolean.class;
            String string60 = "wasInPowderSnow";
            this.M = this.fieldBuilder(string60, clazz60).buildField();
            Class clazz61 = MappedClasses.qP;
            String string61 = "stuckSpeedMultiplier";
            this.T = this.fieldBuilder(string61, clazz61).buildField();
            Class[] classArray28 = new Class[]{MappedClasses.qP};
            Class clazz62 = MappedClasses.qP;
            String string62 = "limitPistonMovement";
            this.C = this.methodBuilder(string62, clazz62, classArray28).buildMethod();
            Class<Boolean> clazz63 = boolean.class;
            String string63 = "verticalCollisionBelow";
            this.jZ = this.fieldBuilder(string63, clazz63).buildField();
            Class[] classArray29 = new Class[]{boolean.class, boolean.class, MappedClasses.qP};
            Class<Void> clazz64 = void.class;
            String string64 = "setOnGroundWithMovement";
            this.jN = this.methodBuilder(string64, clazz64, classArray29).buildMethod();
         }
      } else {
         Class<Double> clazz65 = double.class;
         boolean bl48 = true;
         String string65 = "posX";
         this.w = this.J(string65, bl48, clazz65);
         Class<Double> clazz66 = double.class;
         boolean bl49 = true;
         String string66 = "posY";
         this.jq = this.J(string66, bl49, clazz66);
         Class<Double> clazz67 = double.class;
         boolean bl50 = true;
         String string67 = "posZ";
         this.j9 = this.J(string67, bl50, clazz67);
         Class<Double> clazz68 = double.class;
         boolean bl51 = true;
         String string68 = "motionX";
         this.L = this.J(string68, bl51, clazz68);
         Class<Double> clazz69 = double.class;
         boolean bl52 = true;
         String string69 = "motionY";
         this.r = this.J(string69, bl52, clazz69);
         Class<Double> clazz70 = double.class;
         boolean bl53 = true;
         String string70 = "motionZ";
         this.jB = this.J(string70, bl53, clazz70);
         Class<Boolean> clazz71 = boolean.class;
         boolean bl54 = true;
         String string71 = "isDead";
         this.jd = this.J(string71, bl54, clazz71);
         Class<Float> clazz72 = float.class;
         boolean bl55 = true;
         String string72 = "height";
         this.j3 = this.J(string72, bl55, clazz72);
         Class<Float> clazz73 = float.class;
         boolean bl56 = true;
         String string73 = "width";
         this.jp = this.J(string73, bl56, clazz73);
         Class<Boolean> clazz74 = boolean.class;
         boolean bl57 = true;
         String string74 = ForgeVersion.c() >= 23 ? "collidedHorizontally" : "isCollidedHorizontally";
         this.P = this.J(string74, bl57, clazz74);
         Class<Boolean> clazz75 = boolean.class;
         boolean bl58 = true;
         String string75 = ForgeVersion.c() >= 23 ? "collidedVertically" : "isCollidedVertically";
         this.d = this.J(string75, bl58, clazz75);
         Class[] classArrayx = new Class[]{MappedClasses.zc};
         Class<Float> clazz76 = float.class;
         boolean bl59 = true;
         String string76 = ForgeVersion.c() >= 23 ? "getDistance" : "getDistanceToEntity";
         this.jc = this.Y(string76, bl59, clazz76, classArrayx);
         Class[] classArray30 = new Class[0];
         Class<Boolean> clazz77 = boolean.class;
         boolean bl60 = true;
         String string77 = "isRiding";
         this.jw = this.Y(string77, bl60, clazz77, classArray30);
         Class[] classArray31 = new Class[0];
         Class<String> clazz78 = String.class;
         boolean bl61 = true;
         String string78 = ForgeVersion.MC_1_7_10.L() ? "getCommandSenderName" : "getName";
         this.jA = this.Y(string78, bl61, clazz78, classArray31);
         Class[] classArray32 = new Class[0];
         Class<Float> clazz79 = float.class;
         boolean bl62 = true;
         String string79 = "getEyeHeight";
         this.je = this.Y(string79, bl62, clazz79, classArray32);
         Class[] classArray33 = new Class[]{float.class, float.class};
         Class<Void> clazz80 = void.class;
         boolean bl63 = true;
         String string80 = "setAngles";
         this.jY = this.Y(string80, bl63, clazz80, classArray33);
         Class[] classArray34 = new Class[0];
         Class<Void> clazz81 = void.class;
         boolean bl64 = true;
         String string81 = "onUpdate";
         this.V = this.Y(string81, bl64, clazz81, classArray34);
         Class[] classArray35 = new Class[]{MappedClasses.ZN};
         Class<Boolean> clazz82 = boolean.class;
         boolean bl65 = true;
         String string82 = "isInsideOfMaterial";
         this.B1 = this.Y(string82, bl65, clazz82, classArray35);
      }

      if (ForgeVersion.MC_1_7_10.L()) {
         Class<Boolean> clazz83 = boolean.class;
         boolean bl66 = Wrapper.isNativeAvailable;
         String string83 = "field_70135_K";
         this.z = this.J(string83, bl66, clazz83);
         Class<Float> clazz84 = float.class;
         boolean bl67 = true;
         String string84 = "yOffset";
         this.jP = this.J(string84, bl67, clazz84);
         if (Wrapper.vapeInstance.isVanillaMinecraftPresent()) {
            Class<Float> clazz85 = float.class;
            boolean bl68 = true;
            String string85 = "yOffset2";
            this.S = this.J(string85, bl68, clazz85);
         } else {
            Class<Float> clazz86 = float.class;
            boolean bl69 = true;
            String string86 = "ySize";
            this.S = this.J(string86, bl69, clazz86);
         }

         Class[] classArrayx = new Class[]{double.class, boolean.class};
         Class<Void> clazz87 = void.class;
         boolean bl70 = true;
         String string87 = "updateFallState";
         this.j = this.Y(string87, bl70, clazz87, classArrayx);
         if (Wrapper.vapeInstance.isVanillaMinecraftPresent()) {
            Class[] classArray36 = new Class[0];
            Class clazz88 = MappedClasses.Yr;
            boolean bl71 = true;
            String string88 = "getFormattedCommandSenderName";
            this.jO = this.Y(string88, bl71, clazz88, classArray36);
         } else {
            Class[] classArray37 = new Class[0];
            Class clazz89 = MappedClasses.Yr;
            boolean bl72 = Wrapper.isNativeAvailable;
            String string89 = "func_145748_c_";
            this.jO = this.Y(string89, bl72, clazz89, classArray37);
         }

         Class[] classArray38 = new Class[]{MappedClasses.zc, boolean.class};
         Class<Void> clazz90 = void.class;
         boolean bl73 = true;
         String string90 = "copyDataFrom";
         this.u = this.Y(string90, bl73, clazz90, classArray38);
      } else {
         if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArrayx = new Class[]{double.class, boolean.class, MappedClasses.Zl, MappedClasses.lf};
            Class<Void> clazz91 = void.class;
            boolean bl74 = Wrapper.isNativeAvailable;
            String string91 = "func_184231_a";
            this.j = this.Y(string91, bl74, clazz91, classArrayx);
         } else if (ForgeVersion.MC_1_12_2.d()) {
            Class[] classArrayx = new Class[]{double.class, boolean.class, MappedClasses.Vv, MappedClasses.lf};
            Class<Void> clazz92 = void.class;
            boolean bl75 = true;
            String string92 = "updateFallState";
            this.j = this.Y(string92, bl75, clazz92, classArrayx);
         } else {
            Class[] classArrayx = new Class[]{double.class, boolean.class, MappedClasses.Zk, MappedClasses.lf};
            Class<Void> clazz93 = void.class;
            boolean bl76 = true;
            String string93 = "updateFallState";
            this.j = this.Y(string93, bl76, clazz93, classArrayx);
         }

         if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArrayx = new Class[0];
            Class clazz94 = MappedClasses.uk;
            String string94 = "getBoundingBox";
            this.jQ = this.methodBuilder(string94, clazz94, classArrayx).setOwnerClassForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.lD).buildMethod();
         } else {
            Class[] classArrayx = new Class[0];
            Class clazz95 = MappedClasses.uk;
            boolean bl77 = true;
            String string95 = "getEntityBoundingBox";
            this.jQ = this.Y(string95, bl77, clazz95, classArrayx);
         }

         Class[] classArrayx = new Class[0];
         Class clazz96 = MappedClasses.Yr;
         String string96 = "getDisplayName";
         this.jO = this.methodBuilder(string96, clazz96, classArrayx).setOwnerClassForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.Yp).buildMethod();
         Class[] classArray39 = new Class[]{MappedClasses.zc};
         Class<Void> clazz97 = void.class;
         boolean bl78 = true;
         String string97 = "copyDataFromOld";
         this.u = this.Y(string97, bl78, clazz97, classArray39);
      }

      if (ForgeVersion.MC_1_12_2.d()) {
         Class clazz98 = MappedClasses.YU;
         boolean bl79 = true;
         String string98 = "world";
         this.jH = this.J(string98, bl79, clazz98);
         if (ForgeVersion.MC_1_16_5.d()) {
            Class clazz99 = MappedClasses.qP;
            boolean bl80 = true;
            String string99 = "field_242272_av";
            this.k = this.J(string99, bl80, clazz99);
            Class[] classArrayx = new Class[]{MappedClasses.k, MappedClasses.qP};
            Class<Void> clazz100 = void.class;
            boolean bl81 = true;
            String string100 = "move";
            this.jG = this.Y(string100, bl81, clazz100, classArrayx);
            Class[] classArray40 = new Class[]{float.class, MappedClasses.qP};
            Class<Void> clazz101 = void.class;
            boolean bl82 = true;
            String string101 = "moveRelative";
            this.jb = this.Y(string101, bl82, clazz101, classArray40);
         } else {
            Class<Long> clazz102 = long.class;
            boolean bl83 = true;
            String string102 = "serverPosY";
            this.c = this.J(string102, bl83, clazz102);
            Class[] classArrayx = new Class[]{MappedClasses.k, double.class, double.class, double.class};
            Class<Void> clazz103 = void.class;
            boolean bl84 = Wrapper.isNativeAvailable;
            String string103 = "func_70091_d";
            this.j0 = this.Y(string103, bl84, clazz103, classArrayx);
            Class[] classArray41 = new Class[]{float.class, float.class, float.class, float.class};
            Class<Void> clazz104 = void.class;
            boolean bl85 = Wrapper.isNativeAvailable;
            String string104 = "func_191958_b";
            this.jb = this.Y(string104, bl85, clazz104, classArray41);
         }

         if (ForgeVersion.MC_1_20_6.v()) {
            Class[] classArrayx = new Class[0];
            Class<Boolean> clazz105 = boolean.class;
            boolean bl86 = true;
            String string105 = "hasNoGravity";
            this.jo = this.Y(string105, bl86, clazz105, classArrayx);
            Class[] classArray42 = new Class[0];
            Class<Boolean> clazz106 = boolean.class;
            boolean bl87 = true;
            String string106 = "canPassengerSteer";
            this.jr = this.Y(string106, bl87, clazz106, classArray42);
         }
      } else {
         Class clazz107 = MappedClasses.YU;
         boolean bl88 = true;
         String string107 = "worldObj";
         this.jH = this.J(string107, bl88, clazz107);
         Class<Integer> clazz108 = int.class;
         boolean bl89 = true;
         String string108 = "serverPosY";
         this.c = this.J(string108, bl89, clazz108);
         Class[] classArrayx = new Class[]{float.class, float.class, float.class};
         Class<Void> clazz109 = void.class;
         boolean bl90 = true;
         String string109 = "moveFlying";
         this.jb = this.Y(string109, bl90, clazz109, classArrayx);
         Class[] classArray43 = new Class[]{double.class, double.class, double.class};
         Class<Void> clazz110 = void.class;
         boolean bl91 = true;
         String string110 = "moveEntity";
         this.j0 = this.Y(string110, bl91, clazz110, classArray43);
      }

      if (ForgeVersion.MC_1_20_6.d()) {
         Class[] classArrayx = new Class[0];
         Class<Float> clazz111 = float.class;
         boolean bl92 = true;
         String string111 = "maxUpStep";
         this.jU = this.Y(string111, bl92, clazz111, classArrayx);
      } else {
         Class<Float> clazz112 = float.class;
         boolean bl93 = true;
         String string112 = "stepHeight";
         this.jD = this.J(string112, bl93, clazz112);
      }

      Class[] classArrayx = new Class[0];
      Class<Boolean> clazz113 = boolean.class;
      boolean bl94 = true;
      String string113 = "isSprinting";
      this.K = this.Y(string113, bl94, clazz113, classArrayx);
      Class[] classArray44 = new Class[0];
      Class<Boolean> clazz114 = boolean.class;
      boolean bl95 = true;
      String string114 = "isBurning";
      this.Z = this.Y(string114, bl95, clazz114, classArray44);
      Class[] classArray45 = new Class[0];
      Class<Boolean> clazz115 = boolean.class;
      boolean bl96 = true;
      String string115 = "isInvisible";
      this.jm = this.Y(string115, bl96, clazz115, classArray45);
      Class[] classArray46 = new Class[0];
      Class<Boolean> clazz116 = boolean.class;
      boolean bl97 = true;
      String string116 = "isSneaking";
      this.jf = this.Y(string116, bl97, clazz116, classArray46);
      Class[] classArray47 = new Class[0];
      Class<Boolean> clazz117 = boolean.class;
      boolean bl98 = true;
      String string117 = "canBeCollidedWith";
      this.jC = this.Y(string117, bl98, clazz117, classArray47);
      Class[] classArray48 = new Class[0];
      Class<Float> clazz118 = float.class;
      boolean bl99 = true;
      String string118 = "getCollisionBorderSize";
      this.J = this.Y(string118, bl99, clazz118, classArray48);
      if (ForgeVersion.MC_1_20_6.d()) {
         Class[] classArray49 = new Class[]{double.class, double.class, double.class};
         Class<Boolean> clazz119 = boolean.class;
         boolean bl100 = true;
         String string119 = "isFree";
         this.G = this.Y(string119, bl100, clazz119, classArray49);
      } else if (ForgeVersion.MC_1_16_5.d()) {
         Class[] classArray50 = new Class[]{double.class, double.class, double.class};
         Class<Boolean> clazz120 = boolean.class;
         boolean bl101 = Wrapper.isNativeAvailable;
         String string120 = "isOffsetPositionInLiquid";
         this.G = this.Y(string120, bl101, clazz120, classArray50);
      } else {
         Class[] classArray51 = new Class[]{double.class, double.class, double.class};
         Class<Boolean> clazz121 = boolean.class;
         boolean bl102 = true;
         String string121 = "isOffsetPositionInLiquid";
         this.G = this.Y(string121, bl102, clazz121, classArray51);
      }

      Class[] classArray52 = new Class[]{double.class, double.class, double.class};
      Class<Void> clazz122 = void.class;
      String string122 = "setPosition";
      this.j1 = this.methodBuilder(string122, clazz122, classArray52).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "setPos").buildMethod();
      if (!Wrapper.isNativeAvailable) {
         Class[] classArray53 = new Class[0];
         Class<Boolean> clazz123 = boolean.class;
         boolean bl103 = false;
         String string123 = "canRiderInteract";
         this.o = this.Y(string123, bl103, clazz123, classArray53);
      }

      Class[] classArray54 = new Class[]{double.class, double.class, double.class};
      Class<Void> clazz124 = void.class;
      boolean bl104 = true;
      String string124 = "setVelocity";
      this.p = this.Y(string124, bl104, clazz124, classArray54);
      Class[] classArray55 = new Class[0];
      Class<Boolean> clazz125 = boolean.class;
      boolean bl105 = true;
      String string125 = "isInWater";
      this.j_ = this.Y(string125, bl105, clazz125, classArray55);
      Class<Boolean> clazz126 = boolean.class;
      String string126 = "inWater";
      this.g = this.fieldBuilder(string126, clazz126)
         .setNameForVersion(ForgeVersion.MC_1_16_5.S(), "field_70171_ac")
         .setMappedMemberForVersion(ForgeVersion.MC_1_16_5.S(), Wrapper.isNativeAvailable)
         .setNameForVersion(ForgeVersion.MC_1_16_5.N(), "wasTouchingWater")
         .buildField();
      if (ForgeVersion.MC_1_8_9.d() && ForgeVersion.MC_1_12_2.B()) {
         Class[] classArray56 = new Class[0];
         Class<Boolean> clazz127 = boolean.class;
         String string127 = "handleWaterMovement";
         this.n = this.methodBuilder(string127, clazz127, classArray56).buildMethod();
      }

      if (ForgeVersion.MC_1_7_10.Y()) {
         Class[] classArray57 = new Class[0];
         Class<Boolean> clazz128 = boolean.class;
         boolean bl106 = true;
         String string128 = "isInLava";
         this.jI = this.Y(string128, bl106, clazz128, classArray57);
      } else {
         Class[] classArray58 = new Class[0];
         Class<Boolean> clazz129 = boolean.class;
         boolean bl107 = true;
         String string129 = "handleLavaMovement";
         this.jI = this.Y(string129, bl107, clazz129, classArray58);
      }

      Class[] classArray59 = new Class[]{int.class, boolean.class};
      Class<Void> clazz130 = void.class;
      boolean bl108 = true;
      String string130 = "setFlag";
      this.j2 = this.Y(string130, bl108, clazz130, classArray59);
      Class[] classArray60 = new Class[0];
      Class<UUID> clazz131 = UUID.class;
      String string131 = "getUniqueID";
      this.jX = this.methodBuilder(string131, clazz131, classArray60)
         .setNameForVersion(ForgeVersion.MC_1_16_5.n(), "getUUID")
         .setOwnerClassForVersion(ForgeVersion.MC_1_21_10.n(), MappedClasses.VM)
         .setOwnerClassForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.lD)
         .buildMethod();
      if (ForgeVersion.MC_1_16_5.d()) {
         Class[] classArray61 = new Class[]{double.class, double.class, double.class, float.class, float.class};
         Class<Void> clazz132 = void.class;
         boolean bl109 = Wrapper.isNativeAvailable;
         String string132 = "setPositionAndRotation";
         this.ja = this.Y(string132, bl109, clazz132, classArray61);
      } else {
         Class[] classArray62 = new Class[]{double.class, double.class, double.class, float.class, float.class};
         Class<Void> clazz133 = void.class;
         boolean bl110 = true;
         String string133 = "setPositionAndRotation";
         this.ja = this.Y(string133, bl110, clazz133, classArray62);
      }

      Class[] classArray63 = new Class[]{boolean.class};
      Class<Void> clazz134 = void.class;
      boolean bl111 = true;
      String string134 = "setInvisible";
      this.W = this.Y(string134, bl111, clazz134, classArray63);
      Class[] classArray64 = new Class[]{boolean.class};
      Class<Void> clazz135 = void.class;
      boolean bl112 = true;
      String string135 = "setSprinting";
      this.jW = this.Y(string135, bl112, clazz135, classArray64);
      Class[] classArray65 = new Class[]{boolean.class};
      Class<Void> clazz136 = void.class;
      boolean bl113 = true;
      String string136 = "setSneaking";
      this.jh = this.Y(string136, bl113, clazz136, classArray65);
      if (ForgeVersion.MC_1_8_9.L()) {
         Class[] classArray66 = new Class[]{float.class};
         Class clazz137 = MappedClasses.qP;
         boolean bl114 = true;
         String string137 = "getLook";
         this.jL = this.Y(string137, bl114, clazz137, classArray66);
      }

      if (ForgeVersion.MC_1_21_4.d()) {
         Class[] classArray67 = new Class[0];
         Class clazz138 = MappedClasses.ZZ;
         boolean bl115 = true;
         String string138 = "damageSources";
         this.jx = this.Y(string138, bl115, clazz138, classArray67);
      }
   }

   private boolean i$src$Z$1we3014(Object object) {
      return this.jZ.getBoolean(object);
   }

   public static void Q$src$V$1g5b9e8(MEntity mEntity, Object object, Object object2) {
      mEntity.y(object, object2);
   }

   private void O(Object object, double d, double d2, double d3, float f, float f2) {
      this.ja.invokeVoid(object, d, d2, d3, f, f2);
   }

   public static Object U$src$Ljava_lang_Object_$1vzpu7o(MEntity mEntity, Object object) {
      return mEntity.i$src$Ljava_lang_Object_$p36l2w(object);
   }

   public static Object L(MEntity mEntity, Object object, float f) {
      return mEntity.l(object, f);
   }

   static MethodHandle cfr_ldc_0() {
      try {
         return MethodHandles.lookup()
            .findStatic(
               MEntity.class,
               "b",
               MethodType.fromMethodDescriptorString(
                  "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/invoke/MutableCallSite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;", null
               )
            );
      } catch (IllegalAccessException | NoSuchMethodException var1) {
         throw new IllegalArgumentException(var1);
      }
   }

   static {
      U(8);
      String[] stringArray = new String[]{
         "t\u0013gX&\u0016:\u009acÌUkÂ\rî\r",
         "`»\u0005ÑvWE\u0015",
         "\u0015ä\fí\u0088\u0088\u0098\u000f¾\u001c_\u001beßÞ¡",
         "\\\u0093\u0086\b\u000bV²\u0091",
         "Í\u0086ßÅ\"àVL\u0083¥¾\u007fX¤À\u001e",
         "\u0005¢\u0014¡\u0015ÜÇg",
         "MþW¿Û'¾Æ\u001câ\u0000¡é\tÜµ",
         "\u0006°·Ä{\u0085}\u008a³\u007f\u0083YþêÌZ",
         "n\u001c\u0088Ü\u001eº\u0083b1ß\"cª\u0006\u009b¤",
         "Ó7G,\u0016ÖðxSÁ\u008e\u0018ogaÇ",
         "\u008fIìX\u0017\u0084a¤",
         "\u009c\u0082þåyB\u00adCÉ\u0099¿ù·\u009a\u001e°",
         "Eº\u000bf÷Q\u0098¡UÁ\"\u000f¦Í\u0096¦Ì\u001d© Ê§jý´\bCA\u0011\u000eÝô",
         "\u0082\u008f0é\u0090 \u0091`á\bóV\u001bvX:¹àÂ®\u00146N+",
         "F*\bA¦okz´TS\u008e!Ù~Ùç¸\fü\u0004\u0011.ö",
         "£\u0080\u0092ÀqXñT\u0099NzR\u008d\u001aCÔ££?ªKi¥\u0098",
         "?\u0084ûL³Wr~",
         "FðÁ \u001e\u0015AíIãs&\u008dy·\u0093",
         "ÎNvã\u0000wK\u0082hì\u0010\u0090X½Ä\u008c",
         "ü#ÿ/ª\u0080Ôh",
         "XAÅ\u0011¸KÉ\u0015ùU»éÛ¡M%Ü¤©¬~éH¥",
         "\u0013_ßã\u000e\fÊÒQ\tìüQ¼ë6÷ç§-E\u007fZp",
         "©U×\u0014ÝÝ9Â",
         "gM\u001c¥I\u0014\u0099Y",
         "KB×·¯m`\"",
         "½\u0002Dà3dìi êÀ\f\u0011\u0017²6",
         "\u0092\u001c×\u001flï¹T0ãµ8¿CÊ6",
         "\u009c\u0098£xª\u0094S\u0015\u001fC\u0086ð?\u000fÚâ",
         "çtSöFkn\u0098",
         "²yTÚò#*¡",
         "8\u0086\rÄð\t°.\u0090´\u0018\u001bXE\u0093M",
         "Øvû\u000eu\u0084\fà-\u0087ßs\u0011o\u009dx",
         "Û\u0017Á¬â\\ÔÄ\u00adÛ\u0017ï;ßF/",
         "\u0015\u0080G8DÒ\u00ad*\u0010Ô]\u0094U\u001dD·\u000eø\u0093?}í@?",
         "v6¾\u0099\u001b÷\u0083\r",
         "Òìºlä\f\u0018Ø¨ã³XÖ8¥\u0097\u0086\u009e]\u0094\u0083þ¡\u001a",
         "&ÊË\u0093+~\u0086 êìç\u0096\u0080Ô3\"",
         "o\u009e³\u0090HlõõÑQî\u008b÷\u0005!ìÀõJ^6!âñ",
         "ìeDÍò¨íR\u009b\u0096Æ\u0018áþ8\u0004",
         "\u0093\u0019Vÿ9? AÅZU\u001bUXo\u0097",
         "ú\u009d=\u0097\u0086\u001ep\u000b²ð-R¤â¦½càÌø:\u0017w]",
         "\u0097=ÍÂØ\u0087\u000e+\u009aª\u001c»\u00adÖ\\L",
         "\u008cÒìÜâ\têþÒ\rýÉwÀãñ",
         "¤\u00055\u009f\u008dð m\u009d\u0014×x\u009b:Â,",
         ",\bg\f#6xb?÷ø¼.?$;ª<@]#û´|",
         "¦àê³\u0086N\u0086B ®)è\fr²C",
         "#È\u008a'üê¡\u000e.\u0014\t&nsU6",
         "\u0011Ù\u00185:Dr\u0019\u0097¡\u0080 `\u0082jTyÇÔty¦0\u0092Ø\u0004\u0097B¥\u000e±\u0000",
         "\u0004+ ³#â£\u0084VP\u0002é\n\u0097\u0092\u0097",
         "0g\u0004\u0092\u009ap!\u0010¦\u001bIýl\u0019- ",
         "\u0084\u000b\u0000\u001c+ô½\"\nX£\u0098|D`®",
         "éO\u0088\u0094S!`÷¹½A\u0007\u001a\u001e\u000fI",
         "ÄvHÏ\u009b\u000fÕ9D_½¾=\u0015yZ",
         "DÒí\u009cÛ@\u0091\u0082",
         "Î$¾ÇBñ}ýîOê\u0011<I,{",
         "@jæ{çÂ\u0084\u001f~ã¡\u0085\u0003«xy9ÜÚqm\u00006\\",
         "Ü\u0097Ðv\u0092Û·\"",
         "£äJ¿Y\u008cPä(\"Î^Û¾5(",
         "Q§»\u0092\u0098n¤ê\u0093¡ã8qI+\u007fs@\u008bÍYj9GähßGþ¤yÁ Û\u0012¥û§ò²",
         "\u009f\u000e\u0004]qMÞ\u001fÚ\u0091jZl\"Ô+",
         "Eê7?\u0003ÞÒ$\u00138§y:uSp",
         "{\u0018(Eý½ë\u0097Hçã\u0011ºtßu",
         "\u0017K\u0097\u0099\bÌZ;",
         "³<Ñ&\u009e*ø\u008bø¨\u009b\u001e\u009e$4Ý",
         "Õ\u0084¾l¤0¨ÅÆ\u001e\u001bp7\u008fÀ\u0082",
         "\u0087\"\u0098ôìR\u000e·myÂ\u0095A\u00ad£m67Çóæ\u0013ÊBôÕ\u0097¬\u00851XÊÊ90é\u0089§÷Ã",
         "XZ\u0084á\u0000§4è«©k\u007f\u0012ôc$\u008e\b\u0014\u001e(½ú\u0007",
         "\u008dhe¿Ù3Ó÷:>ÁSÛ\u009a.ß",
         ">\u00193»=\u0087\rr",
         "¯³\u0014Û(\u0094n<",
         "©0^½¨ëUz\rë~I^²º\u0012",
         "¿\u0018¼f\u00adW¾N\u00861P\u001cjhñ\u0080",
         "o\u001aâ¾ðÆé\u0096\u009fÆå¦¾\u0096ïT",
         "½\u0011¬\u0082£\u008egÌiÆ\u0002\u009aÚfCñ",
         "ÍH\u009ckå½Qà>fÆÜØ¦,\u008f",
         "ÔÊ%õ(ÉdùW#·´ß\u00ad]Ü`á¥û\u008e§k!",
         "T7\u0096\u009aÖ'\u000bc@êÁ©£3÷8",
         "PôìkÄH\u0005µ",
         " \u0000.ïýÏÁI\u001b\u0015\u0085\u0012Ab\u008eÄ",
         "wF`¾+\u0093\u0000'¶ºÒYk+X\u001d",
         "\u009a{·Â\u0017iP\u001f\u008aûX6=\u009c\u0002\u001aû\u001b ÈbAôq",
         "\u0084\u0010\u0004¡ÜjþëÑ\u0089ÈVÙ\u0096?\u001e\u001f\u001d5\u0096\u009f¨\u009e¼",
         "\u0081{ßð\u007fð1)dðîçFÓÏ\u000b",
         "Æ2o\u0013Û®g\u0013(L\u0019p/T\u008b\u0010",
         "áVGû7.yo",
         "\u001a\u000eÈ\u008fl5@\u0012(r®\u0081>ªss",
         "F\u000bA·UFÇõ",
         "ÎÑ/}$E9>\u0088ìå/4\u0087#æ3\u001fQx »XÀ",
         "â,§\u001foÆÃÇ¹!\u0001øÞ¢û\u001d",
         "\u0096Ê\u0011¤ÈÌa\u0092µ\u0087a\u000f\b2m+",
         "dî¯©iR\u0005IH\u0092«3MÐ÷\u0093",
         "}_Úâ\u0012\u009a\u001e,",
         "öó\u0001\u0016«\u0098\u0095m",
         "[\u0011\u0002\u0087¥ÔH\u0011>¸²\u008cÊ@\f$V:o\u001f\u008fR\u001cN",
         "^\u0093[}G+\u0001ñ\u007f)ý4/²\u000bYXö·\u0019P_úL",
         "*ü\u001fÎ\u001a\u001f\u0013\n\u0017ídiéJÓ¬",
         "'¸Có/·\u00885",
         "A\u008bx77\u0014\u0006\u000b\u001e\u00924}Ì,cF",
         "Ý\u0011\u0086ªtb\u001a¤gÏ\u0017\u000fL\u0085\u0005B\u0098¥\u007fÕå\u001ar»",
         "q¶¦oíR^Å",
         "C§7\u0087Â\u0004\u0002q\u008bQ\u009e\u008c\u0014Ä\u0091\u0005",
         "ÐW\u0093é\u0013\u0090Å°\u009fúAøt\u008a>Ã",
         "Ã[Õ\u0081Çê\u001f\u00009b\u0088à øo\u0089û-6\u0092½gÆ\u0080",
         "\u0084¼|\u0017N\u0007\u0019\r\u0005#<Fj\u000b\u0006\u0014",
         "É&%µ\\%j«",
         "÷d^\u0019c³\u009fçÜþË#] \u0094M",
         "¼\u0095æïv¥Õ·7K$\u0093nÇM#\u0006ðsú\u0089ÀBÑ",
         "\u0011ùN¹Z \u0087\u0087¢ØñêÁ\u008b\u008dá",
         "åÃüg\u0005mÞM¥Ã\u0098\u0081\u008e¸\u0098ó",
         "56\u0011éX°,\u0088",
         "\u0011à¶:|Ûáã",
         "\u007fª!óX\u001a>\u001eÍæ]\u000e¦T\u0014î",
         "\u008eÀã©Î\u001eO\u0014 ÄY\u009e¼\u0092Q\\",
         "÷/\u00adV ¹æs\u009bÚ)MÌ+g«",
         "\f#ìOü24@¤|k³\u0015Y©q",
         "\u0090Û\u0012! %\u00adÇ\u001bT¼¸#Ù\u0017ÜÒª\u0095§Ç\r¤\\",
         ":ó+%4/3þ¢¾\u007f ¢v\u0002\u0005",
         "\u0087~\u0086Ì\u001f\u001dD\u0097\u0088«ç\u0081ÇÂù(\u0082\u0093Ã\u000fXsÐ\u0010",
         "£\u008d0\u009f\u008c~\u0083ì\t\u0082+cÛîw\u007f/AuÓ\u009eª(ã",
         "6/L\\¯§¤\nÃ\u0085\u0090´±)¹Q",
         "\tì\u0090ýTeR>6½\u0006,òó®s",
         "\u008b]ò&\u00981\u0017*",
         "ó\u0096\u0092ØÏ\u0004þw\u0099m\u009d\u009fc´$\n",
         "U Î©Ô\u0083\u0011©nÎ\u008c\r\u008eoêc",
         "\u001c|\u008bKm\u0099Sç¶.\u0018Lg\u008eÐ\u008f",
         "A\u0007än\f\u0093Æ\u0090µ\u000fî\u001dÕ\u0013\u0086\u0002\u000b\u0090*H$6iU",
         "\u001bV`×R\u0002Ä\u0080æ\txØ\u0089Q2Ó",
         "D¤¢½ð\u0090\nX¶NÖJg\u000b\u0085\u008eÖ\u000e\f(Iö(\u0017\\VZJù©PS",
         "\u000bªn}\u0095Í\u0080h\u009d\u0019DÀ0®»¿",
         "B\u001e²¡Óf»ø",
         "õ´ÕYó_4ï",
         "\u0091\u0094\u0098\u0098,ö\tL0Ì¨æâ¿.ë",
         "þ\u0015a3iÊ -~\u0016ó¿Û\u009aÄ\u009bx\u007fkn1g\u001f«",
         "ba£\u0080q*SÞ¾òÂ\u009b\u009f\u0086!\u0017\u00152x0Ñ$Mòâ\u0006ücÔ=k\u0092",
         "\u0002\u008cu\u009c\u0097xÓæÇ Y\u000fº\u00adìÍ",
         "\u008f¼\u0095\u001c7é\u0089°í|\u009c0bE\u0080È\tÍvÁheõ)",
         "Á\u000eéÞ¯²u\u0014*\u0004$¢Ç:Âÿ\u0098\u0018@N\u008e«L+",
         "iby\u001e>ò2ö\u009eOØjqÊ  ",
         "Ì<\u0012Ö}&\u001e\b\u0085r>±T'!|",
         "ly\u000fÜÊ:\u0088ù,RXpò\u0089\f\u0085",
         "y÷n\u008e;mOYÖÎð6fw\u0085\u0017",
         "Ç\u0011Ó5qSþ\u0001",
         "u\u0087©>\u0002èwè",
         "\u0097ù\u0081H4}E\u0091ò\u0010©gãa\u00196\u0085o§4d\u009ecÏ"
      };
      cb = stringArray;
   }
}
