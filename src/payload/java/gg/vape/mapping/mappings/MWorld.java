package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class MWorld extends Mapping {
   private MappingField E;
   private MappingField K;
   private final MappingMethod y;
   private final MappingMethod Q;
   private MappingMethod P;
   private MappingMethod B;
   private MappingMethod L;
   private MappingField levelExtractorField;
   private MappingMethod W;
   private MappingMethod N;
   private MappingMethod h;
   private MappingMethod s;
   private final MappingMethod D;
   private MappingMethod e;
   private MappingMethod v;
   public MappingMethod O;
   private MappingMethod M;
   private MappingMethod J;
   private final MappingMethod w;
   private final MappingField m;
   private final MappingField I;
   private static boolean k;
   private MappingMethod j;
   private MappingMethod F;
   private final MappingField A;
   private final MappingField U;
   private final MappingField G;
   public final MappingMethod T;
   private MappingMethod Y;
   private MappingMethod a;
   private MappingMethod x;
   public final MappingMethod o;
   public MappingMethod q;
   public MappingMethod p;
   private final MappingMethod g;
   private MappingMethod V;
   private final MappingMethod z;
   private MappingField f;
   private MappingField S;
   private MappingField worldProviderField;
   private MappingMethod X;
   private MappingMethod l;
   private final MappingField Z;
   private MappingMethod i;
   private MappingMethod H;

   public boolean r(Object object, Object object2) {
      return this.j.invokeBoolean(object, object2);
   }

   private void W(Object object, int n, int n2, int n3, int n4, int n5, int n6) {
      if (ForgeVersion.MC_26_2.d()) {
         this.L.invokeVoid(this.levelExtractorField.getObject(object), n, n2, n3, n4, n5, n6);
      } else {
         this.L.invokeVoid(object, n, n2, n3, n4, n5, n6);
      }
   }

   private void A(Object object, Object object2) {
      this.g.invokeVoid(object, object2);
   }

   public static Object b(MWorld mWorld, Object object, int n) {
      return mWorld.b(object, n);
   }

   public static void f(MWorld mWorld, Object object, int n, int n2, int n3, int n4, int n5, int n6) {
      mWorld.W(object, n, n2, n3, n4, n5, n6);
   }

   public static boolean q(MWorld mWorld, Object object, int n, int n2, int n3, int n4, int n5, int n6) {
      return mWorld.y(object, n, n2, n3, n4, n5, n6);
   }

   public Object j(Object object, Object object2) {
      return this.a.invokeObject(object, object2);
   }

   public static Object V(MWorld mWorld, Object object, int n, int n2) {
      return mWorld.t(object, n, n2);
   }

   public MWorld() {
      super(MappedClasses.YU);
      Class<List> clazz = List.class;
      boolean bl = true;
      String string = "loadedTileEntityList";
      this.m = this.J(string, bl, clazz);
      Class<Boolean> clazz2 = boolean.class;
      boolean bl2 = true;
      String string2 = "isRemote";
      this.U = this.J(string2, bl2, clazz2);
      Class<Float> clazz3 = float.class;
      boolean bl3 = true;
      String string3 = "rainingStrength";
      this.Z = this.J(string3, bl3, clazz3);
      Class<Float> clazz4 = float.class;
      boolean bl4 = true;
      String string4 = "prevRainingStrength";
      this.I = this.J(string4, bl4, clazz4);
      Class<Float> clazz5 = float.class;
      boolean bl5 = true;
      String string5 = "thunderingStrength";
      this.A = this.J(string5, bl5, clazz5);
      boolean bl6 = Q();
      Class<Float> clazz6 = float.class;
      boolean bl7 = true;
      String string6 = "prevThunderingStrength";
      this.G = this.J(string6, bl7, clazz6);
      Class[] classArray = new Class[]{int.class};
      Class clazz7 = MappedClasses.zc;
      boolean bl8 = true;
      String string7 = "getEntityByID";
      this.O = this.Y(string7, bl8, clazz7, classArray);
      if (ForgeVersion.MC_1_7_10.L()) {
         Class[] classArray2 = new Class[]{int.class, int.class};
         Class clazz8 = MappedClasses.VJ;
         boolean bl9 = true;
         String string8 = "getChunkFromBlockCoords";
         this.w = this.Y(string8, bl9, clazz8, classArray2);
         Class[] classArray3 = new Class[]{int.class, int.class, int.class, int.class, int.class, int.class};
         Class<Boolean> clazz9 = boolean.class;
         boolean bl10 = true;
         String string9 = "checkChunksExist";
         this.h = this.Y(string9, bl10, clazz9, classArray3);
         Class[] classArray4 = new Class[]{int.class, int.class, int.class};
         Class clazz10 = MappedClasses.Zk;
         boolean bl11 = true;
         String string10 = "getBlock";
         this.V = this.Y(string10, bl11, clazz10, classArray4);
         if (Wrapper.vapeInstance.isVanillaMinecraftPresent()) {
            Class[] classArray5 = new Class[]{MappedClasses.qP, MappedClasses.qP, boolean.class, boolean.class, boolean.class};
            Class clazz11 = MappedClasses.DT;
            boolean bl12 = true;
            String string11 = "rayTraceBlocks";
            this.o = this.Y(string11, bl12, clazz11, classArray5);
         } else {
            Class[] classArray6 = new Class[]{MappedClasses.qP, MappedClasses.qP, boolean.class, boolean.class, boolean.class};
            Class clazz12 = MappedClasses.DT;
            boolean bl13 = Wrapper.isNativeAvailable;
            String string12 = "func_147447_a";
            this.o = this.Y(string12, bl13, clazz12, classArray6);
         }

         Class[] classArray7 = new Class[]{int.class, int.class, int.class};
         Class<Integer> clazz13 = int.class;
         boolean bl14 = true;
         String string13 = "getBlockMetadata";
         this.M = this.Y(string13, bl14, clazz13, classArray7);
         Class[] classArray8 = new Class[]{int.class, int.class, int.class};
         Class<Boolean> clazz14 = boolean.class;
         boolean bl15 = true;
         String string14 = "blockExists";
         this.D = this.Y(string14, bl15, clazz14, classArray8);
      } else {
         if (ForgeVersion.MC_1_17.d()) {
            Class[] classArray9 = new Class[0];
            Class clazz15 = MappedClasses.LEVEL_ENTITY_GETTER;
            boolean bl16 = true;
            String string15 = "getEntities";
            this.x = this.Y(string15, bl16, clazz15, classArray9);
            Class[] classArray10 = new Class[]{MappedClasses.lf};
            Class clazz16 = MappedClasses.ZI;
            String string16 = "getBlockEntity";
            this.a = this.methodBuilder(string16, clazz16, classArray10).setOwnerClassForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.zJ).buildMethod();
         }

         if (ForgeVersion.MC_1_16_5.d()) {
            if (ForgeVersion.MC_1_17.d()) {
               Class[] classArray11 = new Class[]{int.class, int.class};
               Class<Boolean> clazz17 = boolean.class;
               boolean bl17 = true;
               String string17 = "hasChunk";
               Class clazz18 = MappedClasses.F7;
               this.P = this.registerInstanceMethodForOwner(clazz18, string17, bl17, clazz17, classArray11);
            } else {
               Class[] classArray12 = new Class[]{MappedClasses.lf};
               Class<Boolean> clazz19 = boolean.class;
               boolean bl18 = true;
               String string18 = "isBlockLoaded";
               Class clazz20 = MappedClasses.F7;
               this.j = this.registerInstanceMethodForOwner(clazz20, string18, bl18, clazz19, classArray12);
            }

            Class[] classArray13 = new Class[]{MappedClasses.lf};
            Class clazz21 = MappedClasses.VJ;
            boolean bl19 = true;
            String string19 = "getChunkAt";
            this.w = this.Y(string19, bl19, clazz21, classArray13);
            Class[] classArray14 = new Class[]{MappedClasses.lf};
            Class clazz22 = MappedClasses.Zl;
            String string20 = "getBlockState";
            this.p = this.methodBuilder(string20, clazz22, classArray14).setOwnerClassForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.zJ).buildMethod();
            Class[] classArray15 = new Class[]{MappedClasses.Fc};
            Class clazz23 = MappedClasses.qF;
            boolean bl20 = Wrapper.isNativeAvailable;
            String string21 = "func_217299_a";
            Class clazz24 = MappedClasses.zJ;
            this.o = this.registerInstanceMethodForOwner(clazz24, string21, bl20, clazz23, classArray15);
            Class[] classArray16 = new Class[]{MappedClasses.lf};
            Class<Boolean> clazz25 = boolean.class;
            boolean bl21 = true;
            String string22 = "isValid";
            this.D = this.registerStaticMethod(string22, bl21, clazz25, classArray16);
            Class[] classArray17 = new Class[]{MappedClasses.lf};
            Class clazz26 = MappedClasses.uK;
            boolean bl22 = true;
            String string23 = "getBiome";
            Class clazz27 = MappedClasses.F7;
            this.e = this.registerInstanceMethodForOwner(clazz27, string23, bl22, clazz26, classArray17);
            if (ForgeVersion.MC_1_21_4.d()) {
               Class[] classArray18 = new Class[0];
               Class<Integer> clazz28 = int.class;
               String string24 = "getMinY";
               this.B = this.methodBuilder(string24, clazz28, classArray18).setOwnerClass(MappedClasses.VS).buildMethod();
            }
         } else {
            Class[] classArray19 = new Class[]{MappedClasses.lf};
            Class<Boolean> clazz29 = boolean.class;
            boolean bl23 = true;
            String string25 = "isBlockLoaded";
            this.j = this.Y(string25, bl23, clazz29, classArray19);
            Class[] classArray20 = new Class[]{MappedClasses.lf};
            Class clazz30 = MappedClasses.VJ;
            boolean bl24 = true;
            String string26 = ForgeVersion.c() >= 23 ? "getChunk" : "getChunkFromBlockCoords";
            this.w = this.Y(string26, bl24, clazz30, classArray20);
            Class[] classArray21 = new Class[]{MappedClasses.lf};
            Class clazz31 = MappedClasses.Vv;
            boolean bl25 = true;
            String string27 = "getBlockState";
            this.p = this.Y(string27, bl25, clazz31, classArray21);
            Class[] classArray22 = new Class[]{MappedClasses.qP, MappedClasses.qP, boolean.class, boolean.class, boolean.class};
            Class clazz32 = MappedClasses.DT;
            boolean bl26 = true;
            String string28 = "rayTraceBlocks";
            this.o = this.Y(string28, bl26, clazz32, classArray22);
            Class[] classArray23 = new Class[]{MappedClasses.lf};
            Class<Boolean> clazz33 = boolean.class;
            boolean bl27 = true;
            String string29 = "isValid";
            this.D = this.Y(string29, bl27, clazz33, classArray23);
            if (ForgeVersion.MC_1_8_9.L() && Vape.INSTANCE.isVanillaMinecraftPresent()) {
               Class[] classArray24 = new Class[]{MappedClasses.lf, MappedClasses.lf};
               Class<Boolean> clazz34 = boolean.class;
               boolean bl28 = true;
               String string30 = "isAreaLoaded";
               this.h = this.Y(string30, bl28, clazz34, classArray24);
            } else {
               Class[] classArray25 = new Class[]{MappedClasses.lf, MappedClasses.lf};
               Class<Boolean> clazz35 = boolean.class;
               boolean bl29 = Wrapper.isNativeAvailable;
               String string31 = "func_175707_a";
               this.h = this.Y(string31, bl29, clazz35, classArray25);
            }
         }
      }

      if (ForgeVersion.MC_1_12_2.d()) {
         if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray26 = new Class[]{MappedClasses.zc, MappedClasses.uk};
            Class<Stream> clazz36 = Stream.class;
            boolean bl30 = true;
            String string32 = "getCollisionShapes";
            Class clazz37 = MappedClasses.YL;
            this.y = this.registerInstanceMethodForOwner(clazz37, string32, bl30, clazz36, classArray26);
         } else {
            Class[] classArray27 = new Class[]{MappedClasses.zc, MappedClasses.uk};
            Class<List> clazz38 = List.class;
            boolean bl31 = true;
            String string33 = "getCollisionBoxes";
            this.y = this.Y(string33, bl31, clazz38, classArray27);
            Class[] classArray28 = new Class[0];
            Class clazz39 = MappedClasses.FU;
            boolean bl32 = true;
            String string34 = "getBiomeProvider";
            this.J = this.Y(string34, bl32, clazz39, classArray28);
         }
      } else {
         if (ForgeVersion.MC_1_8_9.L()) {
            Class[] classArray29 = new Class[]{MappedClasses.uk};
            Class<Boolean> clazz40 = boolean.class;
            boolean bl33 = true;
            String string35 = "isFlammableWithin";
            this.W = this.Y(string35, bl33, clazz40, classArray29);
         } else {
            Class[] classArray30 = new Class[]{MappedClasses.uk};
            Class<Boolean> clazz41 = boolean.class;
            boolean bl34 = Wrapper.isNativeAvailable;
            String string36 = "func_147470_e";
            this.W = this.Y(string36, bl34, clazz41, classArray30);
         }

         Class[] classArray31 = new Class[]{MappedClasses.zc, MappedClasses.uk};
         Class<List> clazz42 = List.class;
         boolean bl35 = true;
         String string37 = "getCollidingBoundingBoxes";
         this.y = this.Y(string37, bl35, clazz42, classArray31);
         Class[] classArray32 = new Class[0];
         Class clazz43 = MappedClasses.FU;
         boolean bl36 = true;
         String string38 = "getWorldChunkManager";
         this.J = this.Y(string38, bl36, clazz43, classArray32);
      }

      if (ForgeVersion.MC_1_16_5.d()) {
         Class[] classArray33 = new Class[0];
         Class<Iterable> clazz44 = Iterable.class;
         boolean bl37 = true;
         String string39 = "getAllEntities";
         Class clazz45 = MappedClasses.Z;
         this.H = this.registerInstanceMethodForOwner(clazz45, string39, bl37, clazz44, classArray33);
         if (ForgeVersion.MC_1_20_6.d()) {
            Class[] classArray34 = new Class[]{MappedClasses.zc, MappedClasses.uk, Predicate.class};
            Class<List> clazz46 = List.class;
            boolean bl38 = true;
            String string40 = "getEntities";
            Class<?> clazz47 = MappedClasses.z7;
            this.T = this.registerInstanceMethodForOwner(clazz47, string40, bl38, clazz46, classArray34);
         } else {
            Class[] classArray35 = new Class[]{MappedClasses.zc, MappedClasses.uk, Predicate.class};
            Class<List> clazz48 = List.class;
            boolean bl39 = true;
            String string41 = "getEntitiesInAABBexcluding";
            this.T = this.Y(string41, bl39, clazz48, classArray35);
         }

         if (ForgeVersion.MC_1_17.d()) {
            Class[] classArray36 = new Class[]{int.class, MappedClasses.c};
            Class<Void> clazz49 = void.class;
            boolean bl40 = true;
            String string42 = "removeEntity";
            Class clazz50 = MappedClasses.Z;
            this.g = this.registerInstanceMethodForOwner(clazz50, string42, bl40, clazz49, classArray36);
         } else {
            Class[] classArray37 = new Class[]{MappedClasses.zc};
            Class<Void> clazz51 = void.class;
            boolean bl41 = true;
            String string43 = "removeEntity";
            Class clazz52 = MappedClasses.Z;
            this.g = this.registerInstanceMethodForOwner(clazz52, string43, bl41, clazz51, classArray37);
         }

         Class[] classArray38 = new Class[]{MappedClasses.lf};
         Class clazz53 = MappedClasses.Dw;
         String string44 = "getFluidState";
         this.v = this.methodBuilder(string44, clazz53, classArray38).setOwnerClassForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.zJ).buildMethod();
         Class[] classArray39 = new Class[]{int.class, int.class};
         Class clazz54 = MappedClasses.VJ;
         boolean bl42 = true;
         String string45 = "getChunk";
         this.z = this.Y(string45, bl42, clazz54, classArray39);
         Class[] classArray40 = new Class[0];
         Class clazz55 = MappedClasses.lg;
         boolean bl43 = true;
         String string46 = "getChunkProvider";
         Class clazz56 = MappedClasses.uE;
         this.Q = this.registerInstanceMethodForOwner(clazz56, string46, bl43, clazz55, classArray40);
         Class[] classArray41 = new Class[]{int.class, MappedClasses.zc};
         Class<Void> clazz57 = void.class;
         boolean bl44 = true;
         String string47 = "addEntityImpl";
         Class clazz58 = MappedClasses.Z;
         this.q = this.registerInstanceMethodForOwner(clazz58, string47, bl44, clazz57, classArray41);
         if (ForgeVersion.MC_26_2.d()) {
            this.levelExtractorField = this.registerInstanceFieldForOwner(MappedClasses.Z, "levelExtractor", true, MappedClasses.LEVEL_EXTRACTOR);
            this.L = this.registerInstanceMethodForOwner(
               MappedClasses.LEVEL_EXTRACTOR, "setBlocksDirty", true, void.class, int.class, int.class, int.class, int.class, int.class, int.class
            );
         } else {
            Class[] classArray42 = new Class[]{int.class, int.class, int.class, int.class, int.class, int.class};
            Class<Void> clazz59 = void.class;
            boolean bl45 = true;
            String string48 = "markBlockRangeForRenderUpdate";
            Class clazz60 = MappedClasses.zs;
            this.L = this.registerInstanceMethodForOwner(clazz60, string48, bl45, clazz59, classArray42);
         }

         if (ForgeVersion.MC_1_20_6.v()) {
            Class[] classArray43 = new Class[]{MappedClasses.zc, MappedClasses.uk, BiPredicate.class};
            Class<Boolean> clazz61 = boolean.class;
            boolean bl46 = Wrapper.isNativeAvailable;
            String string49 = "func_242405_a";
            Class clazz62 = MappedClasses.YL;
            this.Y = this.registerInstanceMethodForOwner(clazz62, string49, bl46, clazz61, classArray43);
         } else {
            Class[] classArray44 = new Class[]{MappedClasses.zc, MappedClasses.uk};
            Class<Boolean> clazz63 = boolean.class;
            String string50 = "collidesWithSuffocatingBlock";
            this.i = this.methodBuilder(string50, clazz63, classArray44).setOwnerClass(MappedClasses.YL).buildMethod();
         }

         if (ForgeVersion.MC_1_21_4.d()) {
            Class[] classArray45 = new Class[]{MappedClasses.ZS};
            Class clazz64 = MappedClasses.qF;
            String string51 = "clip";
            this.s = this.methodBuilder(string51, clazz64, classArray45).setOwnerClass(MappedClasses.zJ).buildMethod();
         }
      } else {
         Class<List> clazz65 = List.class;
         boolean bl47 = true;
         String string52 = "loadedEntityList";
         this.E = this.J(string52, bl47, clazz65);
         Class<List> clazz66 = List.class;
         boolean bl48 = true;
         String string53 = "playerEntities";
         this.f = this.J(string53, bl48, clazz66);
         Class clazz67 = MappedClasses.WORLD_PROVIDER;
         boolean bl49 = true;
         String string54 = "provider";
         this.worldProviderField = this.J(string54, bl49, clazz67);
         if (ForgeVersion.MC_1_12_2.d()) {
            Class[] classArray46 = new Class[]{MappedClasses.zc, MappedClasses.uk, MappedClasses.lH};
            Class<List> clazz68 = List.class;
            boolean bl50 = true;
            String string55 = "getEntitiesInAABBexcluding";
            this.T = this.Y(string55, bl50, clazz68, classArray46);
         } else {
            Class[] classArray47 = new Class[]{MappedClasses.zc, MappedClasses.uk};
            Class<List> clazz69 = List.class;
            boolean bl51 = true;
            String string56 = "getEntitiesWithinAABBExcludingEntity";
            this.T = this.Y(string56, bl51, clazz69, classArray47);
         }

         Class[] classArray48 = new Class[]{MappedClasses.zc};
         Class<Void> clazz70 = void.class;
         boolean bl52 = true;
         String string57 = "removeEntity";
         this.g = this.Y(string57, bl52, clazz70, classArray48);
         Class[] classArray49 = new Class[]{int.class, int.class};
         Class clazz71 = MappedClasses.VJ;
         boolean bl53 = true;
         String string58 = "getChunkFromChunkCoords";
         this.z = this.Y(string58, bl53, clazz71, classArray49);
         Class[] classArray50 = new Class[0];
         Class clazz72 = MappedClasses.lg;
         boolean bl54 = true;
         String string59 = "getChunkProvider";
         this.Q = this.Y(string59, bl54, clazz72, classArray50);
         Class[] classArray51 = new Class[]{MappedClasses.zc};
         Class<Void> clazz73 = void.class;
         boolean bl55 = true;
         String string60 = "onEntityAdded";
         this.q = this.Y(string60, bl55, clazz73, classArray51);
         Class[] classArray52 = new Class[]{int.class, int.class, int.class, int.class, int.class, int.class};
         Class<Void> clazz74 = void.class;
         boolean bl56 = true;
         String string61 = "markBlockRangeForRenderUpdate";
         this.L = this.Y(string61, bl56, clazz74, classArray52);
         Class[] classArray53 = new Class[]{long.class};
         Class<Void> clazz75 = void.class;
         boolean bl57 = true;
         String string62 = "setWorldTime";
         this.N = this.Y(string62, bl57, clazz75, classArray53);
      }

      if (ForgeVersion.MC_1_17.d()) {
         Class[] classArray54 = new Class[0];
         Class clazz76 = MappedClasses.Vt;
         boolean bl58 = true;
         String string63 = "getChunkSource";
         Class clazz77 = MappedClasses.uE;
         this.F = this.registerInstanceMethodForOwner(clazz77, string63, bl58, clazz76, classArray54);
      }

      if (ForgeVersion.MC_1_20_6.d()) {
         Class clazz78 = MappedClasses.ZZ;
         boolean bl59 = true;
         String string64 = "damageSources";
         this.K = this.J(string64, bl59, clazz78);
      }

      if (ForgeVersion.MC_1_8_9.B()) {
         Class[] classArray55 = new Class[]{MappedClasses.uk, MappedClasses.ZN};
         Class<Boolean> clazz79 = boolean.class;
         boolean bl60 = true;
         String string65 = "isAABBInMaterial";
         this.l = this.Y(string65, bl60, clazz79, classArray55);
      }

      if (ForgeVersion.MC_1_21_4.d()) {
         Class clazz80 = MappedClasses.Fd;
         boolean bl61 = true;
         String string66 = "registryAccess";
         this.S = this.J(string66, bl61, clazz80);
      }
   }

   private boolean M(Object object, Object object2, Object object3) {
      return this.h.invokeBoolean(object, object2, object3);
   }

   private boolean k(Object object, Object object2, Object object3) {
      return this.i.invokeBoolean(object, object2, object3);
   }

   private boolean V(Object object) {
      return this.U.getBoolean(object);
   }

   public static boolean E$src$Z$o9fqyv() {
      boolean bl = Q();
      return !bl;
   }

   public boolean d(Object object, int n, int n2) {
      return this.P.invokeBoolean(object, n, n2);
   }

   public Object u(Object object, Object object2) {
      return this.p.invokeObject(object, object2);
   }

   public List K(Object object, Object object2, Object object3) {
      return (List)this.T.invokeObject(object, object2, object3);
   }

   public List j$src$Ljava_util_List_$jm6ihn(Object object) {
      return (List)this.m.getObject(object);
   }

   public List X(Object object, Object object2, Object object3, Object object4) {
      return (List)this.T.invokeObject(object, object2, object3, object4);
   }

   public Iterable m(Object object) {
      return (Iterable)this.H.invokeObject(object);
   }

   public static void c(MWorld mWorld, Object object, Object object2) {
      mWorld.A(object, object2);
   }

   public Object u(Object object) {
      return this.S.getObject(object);
   }

   public Object E(Object object) {
      return this.Q.invokeObject(object);
   }

   public Object H(Object object, int n, int n2) {
      return this.w.invokeObject(object, n, n2);
   }

   public static boolean x(MWorld mWorld, Object object, Object object2, Object object3) {
      return mWorld.k(object, object2, object3);
   }

   private int b(Object object) {
      return this.B.invokeInt(object);
   }

   private boolean S(Object object, int n, int n2, int n3) {
      return ForgeVersion.MC_1_7_10.L() ? this.D.invokeBoolean(object, n, n2, n3) : this.D.invokeBoolean(object, BlockPos.create(n, n2, n3).getObject());
   }

   public void D(Object object, float f) {
      this.G.setFloat(object, f);
   }

   public Object g(Object object) {
      return this.J.invokeObject(object);
   }

   public static void a(MWorld mWorld, Object object, int n, Object object2) {
      mWorld.r(object, n, object2);
   }

   public Object a(Object object, Object object2) {
      return this.e.invokeObject(object, object2);
   }

   public static boolean K(MWorld mWorld, Object object, Object object2, Object object3) {
      return mWorld.M(object, object2, object3);
   }

   public float a(Object object) {
      return this.G.getFloat(object);
   }

   public void L(Object object, float f) {
      this.I.setFloat(object, f);
   }

   public float w(Object object) {
      return this.A.getFloat(object);
   }

   public Object y(Object object) {
      return this.K.getObject(object);
   }

   public static void g(boolean bl) {
      k = bl;
   }

   private void r(Object object, int n, Object object2) {
      this.g.invokeVoid(object, n, object2);
   }

   public static boolean Q() {
      return k;
   }

   public Object y(Object object, Object object2, Object object3) {
      return this.y.invokeObject(object, object2, object3);
   }

   public Object getEntityGetter(Object world) {
      return this.x.invokeObject(world);
   }

   public boolean H(Object object, Object object2, Object object3) {
      return this.l.invokeBoolean(object, object2, object3);
   }

   private int g(Object object, int n, int n2, int n3) {
      return this.M.invokeInt(object, n, n2, n3);
   }

   private boolean y(Object object, int n, int n2, int n3, int n4, int n5, int n6) {
      return this.h.invokeBoolean(object, n, n2, n3, n4, n5, n6);
   }

   public boolean u(Object object, Object object2, Object object3, BiPredicate biPredicate) {
      return this.Y.invokeBoolean(object, object2, object3, biPredicate);
   }

   public Object j(Object object) {
      return this.F.invokeObject(object);
   }

   public float Z(Object object) {
      return this.Z.getFloat(object);
   }

   public static int X(MWorld mWorld, Object object, int n, int n2, int n3) {
      return mWorld.g(object, n, n2, n3);
   }

   public List y$src$Ljava_util_List_$1xik31o(Object object) {
      return (List)this.E.getObject(object);
   }

   public static boolean w(MWorld mWorld, Object object) {
      return mWorld.V(object);
   }

   public static int i(MWorld mWorld, Object object) {
      return mWorld.b(object);
   }

   public Object l(Object object, Object object2, Object object3, boolean bl, boolean bl2, boolean bl3) {
      return this.o.invokeObject(object, object2, object3, bl, bl2, bl3);
   }

   public List T(Object object, Object object2, Object object3, Object object4) {
      return (List)this.T.invokeObject(object, object2, object3, object4);
   }

   public void R(Object object, float f) {
      this.A.setFloat(object, f);
   }

   public void V(Object object, long l) {
      this.N.invokeVoid(object, l);
   }

   public Object t(Object object, Object object2) {
      return this.s.invokeObject(object, object2);
   }

   public Object U(Object object, int n, int n2, int n3) {
      return this.V.invokeObject(object, n, n2, n3);
   }

   public void M(Object object, float f) {
      this.Z.setFloat(object, f);
   }

   private Object getWorldProviderHandle(Object world) {
      return this.worldProviderField.getObject(world);
   }

   private Object t(Object object, int n, int n2) {
      return this.z.invokeObject(object, n, n2);
   }

   public static boolean v(MWorld mWorld, Object object, int n, int n2, int n3) {
      return mWorld.S(object, n, n2, n3);
   }

   public List Q(Object object) {
      return (List)this.f.getObject(object);
   }

   public Object v(Object object, Object object2) {
      return this.v.invokeObject(object, object2);
   }

   public Object Z(Object object, Object object2) {
      return this.w.invokeObject(object, object2);
   }

   public float X(Object object) {
      return this.I.getFloat(object);
   }

   public Object i(Object object, Object object2) {
      return this.o.invokeObject(object, object2);
   }

   public static Object getWorldProvider(MWorld mappings, Object world) {
      return mappings.getWorldProviderHandle(world);
   }

   private Object b(Object object, int n) {
      return this.O.invokeObject(object, n);
   }

   static {
      g(false);
   }
}
