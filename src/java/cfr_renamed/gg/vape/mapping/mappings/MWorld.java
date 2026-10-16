/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.MappingMethodBuilder;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class MWorld
extends Mapping {
    private MappingField var_gg_vape_mapping_MappingField_E;
    private MappingField var_gg_vape_mapping_MappingField_K;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_y;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_Q;
    private MappingMethod var_gg_vape_mapping_MappingMethod_P;
    private MappingMethod var_gg_vape_mapping_MappingMethod_B;
    private MappingMethod var_gg_vape_mapping_MappingMethod_L;
    private MappingField levelExtractorField;
    private MappingMethod var_gg_vape_mapping_MappingMethod_W;
    private MappingMethod var_gg_vape_mapping_MappingMethod_N;
    private MappingMethod var_gg_vape_mapping_MappingMethod_h;
    private MappingMethod var_gg_vape_mapping_MappingMethod_s;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_D;
    private MappingMethod var_gg_vape_mapping_MappingMethod_e;
    private MappingMethod var_gg_vape_mapping_MappingMethod_v;
    public MappingMethod var_gg_vape_mapping_MappingMethod_O;
    private MappingMethod var_gg_vape_mapping_MappingMethod_M;
    private MappingMethod var_gg_vape_mapping_MappingMethod_J;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_w;
    private final MappingField var_gg_vape_mapping_MappingField_m;
    private final MappingField var_gg_vape_mapping_MappingField_I;
    private static boolean var_boolean_k;
    private MappingMethod var_gg_vape_mapping_MappingMethod_j;
    private MappingMethod var_gg_vape_mapping_MappingMethod_F;
    private final MappingField var_gg_vape_mapping_MappingField_A;
    private final MappingField var_gg_vape_mapping_MappingField_U;
    private final MappingField var_gg_vape_mapping_MappingField_G;
    final public MappingMethod var_gg_vape_mapping_MappingMethod_T;
    private MappingMethod var_gg_vape_mapping_MappingMethod_Y;
    private MappingMethod var_gg_vape_mapping_MappingMethod_a;
    private MappingMethod var_gg_vape_mapping_MappingMethod_x;
    final public MappingMethod var_gg_vape_mapping_MappingMethod_o;
    public MappingMethod var_gg_vape_mapping_MappingMethod_q;
    public MappingMethod var_gg_vape_mapping_MappingMethod_p;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_g;
    private MappingMethod var_gg_vape_mapping_MappingMethod_V;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_z;
    private MappingField var_gg_vape_mapping_MappingField_f;
    private MappingField var_gg_vape_mapping_MappingField_S;
    private MappingField worldProviderField;
    private MappingMethod var_gg_vape_mapping_MappingMethod_X;
    private MappingMethod var_gg_vape_mapping_MappingMethod_l;
    private final MappingField var_gg_vape_mapping_MappingField_Z;
    private MappingMethod var_gg_vape_mapping_MappingMethod_i;
    private MappingMethod var_gg_vape_mapping_MappingMethod_H;

    public boolean r(Object object, Object object2) {
        return this.var_gg_vape_mapping_MappingMethod_j.invokeBoolean(object, object2);
    }

    private void W(Object object, int n, int n2, int n3, int n4, int n5, int n6) {
        if (ForgeVersion.MC_26_2.d()) {
            this.var_gg_vape_mapping_MappingMethod_L.invokeVoid(this.levelExtractorField.getObject(object), n, n2, n3, n4, n5, n6);
            return;
        }
        this.var_gg_vape_mapping_MappingMethod_L.invokeVoid(object, n, n2, n3, n4, n5, n6);
    }

    private void A(Object object, Object object2) {
        this.var_gg_vape_mapping_MappingMethod_g.invokeVoid(object, object2);
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
        return this.var_gg_vape_mapping_MappingMethod_a.invokeObject(object, object2);
    }

    public static Object V(MWorld mWorld, Object object, int n, int n2) {
        return mWorld.t(object, n, n2);
    }

    public MWorld() {
        super(MappedClasses.YU);
        Class<List> clazz = List.class;
        boolean bl = true;
        String string = "loadedTileEntityList";
        MWorld mWorld = this;
        this.var_gg_vape_mapping_MappingField_m = this.J(string, bl, clazz);
        Class<Boolean> clazz2 = Boolean.TYPE;
        boolean bl2 = true;
        String string2 = "isRemote";
        MWorld mWorld2 = this;
        this.var_gg_vape_mapping_MappingField_U = this.J(string2, bl2, clazz2);
        Class<Float> clazz3 = Float.TYPE;
        boolean bl3 = true;
        String string3 = "rainingStrength";
        MWorld mWorld3 = this;
        this.var_gg_vape_mapping_MappingField_Z = this.J(string3, bl3, clazz3);
        Class<Float> clazz4 = Float.TYPE;
        boolean bl4 = true;
        String string4 = "prevRainingStrength";
        MWorld mWorld4 = this;
        this.var_gg_vape_mapping_MappingField_I = this.J(string4, bl4, clazz4);
        Class<Float> clazz5 = Float.TYPE;
        boolean bl5 = true;
        String string5 = "thunderingStrength";
        MWorld mWorld5 = this;
        this.var_gg_vape_mapping_MappingField_A = this.J(string5, bl5, clazz5);
        boolean bl6 = MWorld.Q();
        Class<Float> clazz6 = Float.TYPE;
        boolean bl7 = true;
        String string6 = "prevThunderingStrength";
        MWorld mWorld6 = this;
        this.var_gg_vape_mapping_MappingField_G = this.J(string6, bl7, clazz6);
        Class[] classArray = new Class[]{Integer.TYPE};
        Class clazz7 = MappedClasses.zc;
        boolean bl8 = true;
        String string7 = "getEntityByID";
        MWorld mWorld7 = this;
        this.var_gg_vape_mapping_MappingMethod_O = this.Y(string7, bl8, clazz7, classArray);
        if (ForgeVersion.MC_1_7_10.L()) {
            Class[] classArray2 = new Class[]{Integer.TYPE, Integer.TYPE};
            Class clazz8 = MappedClasses.VJ;
            boolean bl9 = true;
            String string8 = "getChunkFromBlockCoords";
            MWorld mWorld8 = this;
            this.var_gg_vape_mapping_MappingMethod_w = this.Y(string8, bl9, clazz8, classArray2);
            Class[] classArray3 = new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE};
            Class<Boolean> clazz9 = Boolean.TYPE;
            boolean bl10 = true;
            String string9 = "checkChunksExist";
            MWorld mWorld9 = this;
            this.var_gg_vape_mapping_MappingMethod_h = this.Y(string9, bl10, clazz9, classArray3);
            Class[] classArray4 = new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE};
            Class clazz10 = MappedClasses.Zk;
            boolean bl11 = true;
            String string10 = "getBlock";
            MWorld mWorld10 = this;
            this.var_gg_vape_mapping_MappingMethod_V = this.Y(string10, bl11, clazz10, classArray4);
            if (Wrapper.vapeInstance.isVanillaMinecraftPresent()) {
                Class[] classArray5 = new Class[]{MappedClasses.qP, MappedClasses.qP, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE};
                Class clazz11 = MappedClasses.DT;
                boolean bl12 = true;
                String string11 = "rayTraceBlocks";
                MWorld mWorld11 = this;
                this.var_gg_vape_mapping_MappingMethod_o = this.Y(string11, bl12, clazz11, classArray5);
            } else {
                Class[] classArray6 = new Class[]{MappedClasses.qP, MappedClasses.qP, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE};
                Class clazz12 = MappedClasses.DT;
                boolean bl13 = Wrapper.isNativeAvailable;
                String string12 = "func_147447_a";
                MWorld mWorld12 = this;
                this.var_gg_vape_mapping_MappingMethod_o = this.Y(string12, bl13, clazz12, classArray6);
            }
            Class[] classArray7 = new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE};
            Class<Integer> clazz13 = Integer.TYPE;
            boolean bl14 = true;
            String string13 = "getBlockMetadata";
            MWorld mWorld13 = this;
            this.var_gg_vape_mapping_MappingMethod_M = this.Y(string13, bl14, clazz13, classArray7);
            Class[] classArray8 = new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE};
            Class<Boolean> clazz14 = Boolean.TYPE;
            boolean bl15 = true;
            String string14 = "blockExists";
            MWorld mWorld14 = this;
            this.var_gg_vape_mapping_MappingMethod_D = this.Y(string14, bl15, clazz14, classArray8);
        } else {
            if (ForgeVersion.MC_1_17.d()) {
                Class[] classArray9 = new Class[]{};
                Class clazz15 = MappedClasses.LEVEL_ENTITY_GETTER;
                boolean bl16 = true;
                String string15 = "getEntities";
                MWorld mWorld15 = this;
                this.var_gg_vape_mapping_MappingMethod_x = this.Y(string15, bl16, clazz15, classArray9);
                Class[] classArray10 = new Class[]{MappedClasses.lf};
                Class clazz16 = MappedClasses.ZI;
                String string16 = "getBlockEntity";
                MWorld mWorld16 = this;
                this.var_gg_vape_mapping_MappingMethod_a = ((MappingMethodBuilder)this.methodBuilder(string16, clazz16, classArray10).setOwnerClassForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.zJ)).buildMethod();
            }
            if (ForgeVersion.MC_1_16_5.d()) {
                if (ForgeVersion.MC_1_17.d()) {
                    Class[] classArray11 = new Class[]{Integer.TYPE, Integer.TYPE};
                    Class<Boolean> clazz17 = Boolean.TYPE;
                    boolean bl17 = true;
                    String string17 = "hasChunk";
                    Class clazz18 = MappedClasses.F7;
                    MWorld mWorld17 = this;
                    this.var_gg_vape_mapping_MappingMethod_P = this.registerInstanceMethodForOwner(clazz18, string17, bl17, clazz17, classArray11);
                } else {
                    Class[] classArray12 = new Class[]{MappedClasses.lf};
                    Class<Boolean> clazz19 = Boolean.TYPE;
                    boolean bl18 = true;
                    String string18 = "isBlockLoaded";
                    Class clazz20 = MappedClasses.F7;
                    MWorld mWorld18 = this;
                    this.var_gg_vape_mapping_MappingMethod_j = this.registerInstanceMethodForOwner(clazz20, string18, bl18, clazz19, classArray12);
                }
                Class[] classArray13 = new Class[]{MappedClasses.lf};
                Class clazz21 = MappedClasses.VJ;
                boolean bl19 = true;
                String string19 = "getChunkAt";
                MWorld mWorld19 = this;
                this.var_gg_vape_mapping_MappingMethod_w = this.Y(string19, bl19, clazz21, classArray13);
                Class[] classArray14 = new Class[]{MappedClasses.lf};
                Class clazz22 = MappedClasses.Zl;
                String string20 = "getBlockState";
                MWorld mWorld20 = this;
                this.var_gg_vape_mapping_MappingMethod_p = ((MappingMethodBuilder)this.methodBuilder(string20, clazz22, classArray14).setOwnerClassForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.zJ)).buildMethod();
                Class[] classArray15 = new Class[]{MappedClasses.Fc};
                Class clazz23 = MappedClasses.qF;
                boolean bl20 = Wrapper.isNativeAvailable;
                String string21 = "func_217299_a";
                Class clazz24 = MappedClasses.zJ;
                MWorld mWorld21 = this;
                this.var_gg_vape_mapping_MappingMethod_o = this.registerInstanceMethodForOwner(clazz24, string21, bl20, clazz23, classArray15);
                Class[] classArray16 = new Class[]{MappedClasses.lf};
                Class<Boolean> clazz25 = Boolean.TYPE;
                boolean bl21 = true;
                String string22 = "isValid";
                MWorld mWorld22 = this;
                this.var_gg_vape_mapping_MappingMethod_D = this.registerStaticMethod(string22, bl21, clazz25, classArray16);
                Class[] classArray17 = new Class[]{MappedClasses.lf};
                Class clazz26 = MappedClasses.uK;
                boolean bl22 = true;
                String string23 = "getBiome";
                Class clazz27 = MappedClasses.F7;
                MWorld mWorld23 = this;
                this.var_gg_vape_mapping_MappingMethod_e = this.registerInstanceMethodForOwner(clazz27, string23, bl22, clazz26, classArray17);
                if (ForgeVersion.MC_1_21_4.d()) {
                    Class[] classArray18 = new Class[]{};
                    Class<Integer> clazz28 = Integer.TYPE;
                    String string24 = "getMinY";
                    MWorld mWorld24 = this;
                    this.var_gg_vape_mapping_MappingMethod_B = ((MappingMethodBuilder)this.methodBuilder(string24, clazz28, classArray18).setOwnerClass(MappedClasses.VS)).buildMethod();
                }
            } else {
                Class[] classArray19 = new Class[]{MappedClasses.lf};
                Class<Boolean> clazz29 = Boolean.TYPE;
                boolean bl23 = true;
                String string25 = "isBlockLoaded";
                MWorld mWorld25 = this;
                this.var_gg_vape_mapping_MappingMethod_j = this.Y(string25, bl23, clazz29, classArray19);
                Class[] classArray20 = new Class[]{MappedClasses.lf};
                Class clazz30 = MappedClasses.VJ;
                boolean bl24 = true;
                String string26 = ForgeVersion.c() >= 23 ? "getChunk" : "getChunkFromBlockCoords";
                MWorld mWorld26 = this;
                this.var_gg_vape_mapping_MappingMethod_w = this.Y(string26, bl24, clazz30, classArray20);
                Class[] classArray21 = new Class[]{MappedClasses.lf};
                Class clazz31 = MappedClasses.Vv;
                boolean bl25 = true;
                String string27 = "getBlockState";
                MWorld mWorld27 = this;
                this.var_gg_vape_mapping_MappingMethod_p = this.Y(string27, bl25, clazz31, classArray21);
                Class[] classArray22 = new Class[]{MappedClasses.qP, MappedClasses.qP, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE};
                Class clazz32 = MappedClasses.DT;
                boolean bl26 = true;
                String string28 = "rayTraceBlocks";
                MWorld mWorld28 = this;
                this.var_gg_vape_mapping_MappingMethod_o = this.Y(string28, bl26, clazz32, classArray22);
                Class[] classArray23 = new Class[]{MappedClasses.lf};
                Class<Boolean> clazz33 = Boolean.TYPE;
                boolean bl27 = true;
                String string29 = "isValid";
                MWorld mWorld29 = this;
                this.var_gg_vape_mapping_MappingMethod_D = this.Y(string29, bl27, clazz33, classArray23);
                if (ForgeVersion.MC_1_8_9.L() && Vape.INSTANCE.isVanillaMinecraftPresent()) {
                    Class[] classArray24 = new Class[]{MappedClasses.lf, MappedClasses.lf};
                    Class<Boolean> clazz34 = Boolean.TYPE;
                    boolean bl28 = true;
                    String string30 = "isAreaLoaded";
                    MWorld mWorld30 = this;
                    this.var_gg_vape_mapping_MappingMethod_h = this.Y(string30, bl28, clazz34, classArray24);
                } else {
                    Class[] classArray25 = new Class[]{MappedClasses.lf, MappedClasses.lf};
                    Class<Boolean> clazz35 = Boolean.TYPE;
                    boolean bl29 = Wrapper.isNativeAvailable;
                    String string31 = "func_175707_a";
                    MWorld mWorld31 = this;
                    this.var_gg_vape_mapping_MappingMethod_h = this.Y(string31, bl29, clazz35, classArray25);
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
                MWorld mWorld32 = this;
                this.var_gg_vape_mapping_MappingMethod_y = this.registerInstanceMethodForOwner(clazz37, string32, bl30, clazz36, classArray26);
            } else {
                Class[] classArray27 = new Class[]{MappedClasses.zc, MappedClasses.uk};
                Class<List> clazz38 = List.class;
                boolean bl31 = true;
                String string33 = "getCollisionBoxes";
                MWorld mWorld33 = this;
                this.var_gg_vape_mapping_MappingMethod_y = this.Y(string33, bl31, clazz38, classArray27);
                Class[] classArray28 = new Class[]{};
                Class clazz39 = MappedClasses.FU;
                boolean bl32 = true;
                String string34 = "getBiomeProvider";
                MWorld mWorld34 = this;
                this.var_gg_vape_mapping_MappingMethod_J = this.Y(string34, bl32, clazz39, classArray28);
            }
        } else {
            if (ForgeVersion.MC_1_8_9.L()) {
                Class[] classArray29 = new Class[]{MappedClasses.uk};
                Class<Boolean> clazz40 = Boolean.TYPE;
                boolean bl33 = true;
                String string35 = "isFlammableWithin";
                MWorld mWorld35 = this;
                this.var_gg_vape_mapping_MappingMethod_W = this.Y(string35, bl33, clazz40, classArray29);
            } else {
                Class[] classArray30 = new Class[]{MappedClasses.uk};
                Class<Boolean> clazz41 = Boolean.TYPE;
                boolean bl34 = Wrapper.isNativeAvailable;
                String string36 = "func_147470_e";
                MWorld mWorld36 = this;
                this.var_gg_vape_mapping_MappingMethod_W = this.Y(string36, bl34, clazz41, classArray30);
            }
            Class[] classArray31 = new Class[]{MappedClasses.zc, MappedClasses.uk};
            Class<List> clazz42 = List.class;
            boolean bl35 = true;
            String string37 = "getCollidingBoundingBoxes";
            MWorld mWorld37 = this;
            this.var_gg_vape_mapping_MappingMethod_y = this.Y(string37, bl35, clazz42, classArray31);
            Class[] classArray32 = new Class[]{};
            Class clazz43 = MappedClasses.FU;
            boolean bl36 = true;
            String string38 = "getWorldChunkManager";
            MWorld mWorld38 = this;
            this.var_gg_vape_mapping_MappingMethod_J = this.Y(string38, bl36, clazz43, classArray32);
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray33 = new Class[]{};
            Class<Iterable> clazz44 = Iterable.class;
            boolean bl37 = true;
            String string39 = "getAllEntities";
            Class clazz45 = MappedClasses.var_java_lang_Class_Z;
            MWorld mWorld39 = this;
            this.var_gg_vape_mapping_MappingMethod_H = this.registerInstanceMethodForOwner(clazz45, string39, bl37, clazz44, classArray33);
            if (ForgeVersion.MC_1_20_6.d()) {
                Class[] classArray34 = new Class[]{MappedClasses.zc, MappedClasses.uk, Predicate.class};
                Class<List> clazz46 = List.class;
                boolean bl38 = true;
                String string40 = "getEntities";
                Class<?> clazz47 = MappedClasses.z7;
                MWorld mWorld40 = this;
                this.var_gg_vape_mapping_MappingMethod_T = this.registerInstanceMethodForOwner(clazz47, string40, bl38, clazz46, classArray34);
            } else {
                Class[] classArray35 = new Class[]{MappedClasses.zc, MappedClasses.uk, Predicate.class};
                Class<List> clazz48 = List.class;
                boolean bl39 = true;
                String string41 = "getEntitiesInAABBexcluding";
                MWorld mWorld41 = this;
                this.var_gg_vape_mapping_MappingMethod_T = this.Y(string41, bl39, clazz48, classArray35);
            }
            if (ForgeVersion.MC_1_17.d()) {
                Class[] classArray36 = new Class[]{Integer.TYPE, MappedClasses.var_java_lang_Class_c};
                Class<Void> clazz49 = Void.TYPE;
                boolean bl40 = true;
                String string42 = "removeEntity";
                Class clazz50 = MappedClasses.var_java_lang_Class_Z;
                MWorld mWorld42 = this;
                this.var_gg_vape_mapping_MappingMethod_g = this.registerInstanceMethodForOwner(clazz50, string42, bl40, clazz49, classArray36);
            } else {
                Class[] classArray37 = new Class[]{MappedClasses.zc};
                Class<Void> clazz51 = Void.TYPE;
                boolean bl41 = true;
                String string43 = "removeEntity";
                Class clazz52 = MappedClasses.var_java_lang_Class_Z;
                MWorld mWorld43 = this;
                this.var_gg_vape_mapping_MappingMethod_g = this.registerInstanceMethodForOwner(clazz52, string43, bl41, clazz51, classArray37);
            }
            Class[] classArray38 = new Class[]{MappedClasses.lf};
            Class clazz53 = MappedClasses.Dw;
            String string44 = "getFluidState";
            MWorld mWorld44 = this;
            this.var_gg_vape_mapping_MappingMethod_v = ((MappingMethodBuilder)this.methodBuilder(string44, clazz53, classArray38).setOwnerClassForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.zJ)).buildMethod();
            Class[] classArray39 = new Class[]{Integer.TYPE, Integer.TYPE};
            Class clazz54 = MappedClasses.VJ;
            boolean bl42 = true;
            String string45 = "getChunk";
            MWorld mWorld45 = this;
            this.var_gg_vape_mapping_MappingMethod_z = this.Y(string45, bl42, clazz54, classArray39);
            Class[] classArray40 = new Class[]{};
            Class clazz55 = MappedClasses.lg;
            boolean bl43 = true;
            String string46 = "getChunkProvider";
            Class clazz56 = MappedClasses.uE;
            MWorld mWorld46 = this;
            this.var_gg_vape_mapping_MappingMethod_Q = this.registerInstanceMethodForOwner(clazz56, string46, bl43, clazz55, classArray40);
            Class[] classArray41 = new Class[]{Integer.TYPE, MappedClasses.zc};
            Class<Void> clazz57 = Void.TYPE;
            boolean bl44 = true;
            String string47 = "addEntityImpl";
            Class clazz58 = MappedClasses.var_java_lang_Class_Z;
            MWorld mWorld47 = this;
            this.var_gg_vape_mapping_MappingMethod_q = this.registerInstanceMethodForOwner(clazz58, string47, bl44, clazz57, classArray41);
            if (ForgeVersion.MC_26_2.d()) {
                this.levelExtractorField = this.registerInstanceFieldForOwner(MappedClasses.var_java_lang_Class_Z, "levelExtractor", true, MappedClasses.LEVEL_EXTRACTOR);
                this.var_gg_vape_mapping_MappingMethod_L = this.registerInstanceMethodForOwner(MappedClasses.LEVEL_EXTRACTOR, "setBlocksDirty", true, Void.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE);
            } else {
                Class[] classArray42 = new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE};
                Class<Void> clazz59 = Void.TYPE;
                boolean bl45 = true;
                String string48 = "markBlockRangeForRenderUpdate";
                Class clazz60 = MappedClasses.zs;
                MWorld mWorld48 = this;
                this.var_gg_vape_mapping_MappingMethod_L = this.registerInstanceMethodForOwner(clazz60, string48, bl45, clazz59, classArray42);
            }
            if (ForgeVersion.MC_1_20_6.v()) {
                Class[] classArray43 = new Class[]{MappedClasses.zc, MappedClasses.uk, BiPredicate.class};
                Class<Boolean> clazz61 = Boolean.TYPE;
                boolean bl46 = Wrapper.isNativeAvailable;
                String string49 = "func_242405_a";
                Class clazz62 = MappedClasses.YL;
                MWorld mWorld49 = this;
                this.var_gg_vape_mapping_MappingMethod_Y = this.registerInstanceMethodForOwner(clazz62, string49, bl46, clazz61, classArray43);
            } else {
                Class[] classArray44 = new Class[]{MappedClasses.zc, MappedClasses.uk};
                Class<Boolean> clazz63 = Boolean.TYPE;
                String string50 = "collidesWithSuffocatingBlock";
                MWorld mWorld50 = this;
                this.var_gg_vape_mapping_MappingMethod_i = ((MappingMethodBuilder)this.methodBuilder(string50, clazz63, classArray44).setOwnerClass(MappedClasses.YL)).buildMethod();
            }
            if (ForgeVersion.MC_1_21_4.d()) {
                Class[] classArray45 = new Class[]{MappedClasses.ZS};
                Class clazz64 = MappedClasses.qF;
                String string51 = "clip";
                MWorld mWorld51 = this;
                this.var_gg_vape_mapping_MappingMethod_s = ((MappingMethodBuilder)this.methodBuilder(string51, clazz64, classArray45).setOwnerClass(MappedClasses.zJ)).buildMethod();
            }
        } else {
            Class<List> clazz65 = List.class;
            boolean bl47 = true;
            String string52 = "loadedEntityList";
            MWorld mWorld52 = this;
            this.var_gg_vape_mapping_MappingField_E = this.J(string52, bl47, clazz65);
            Class<List> clazz66 = List.class;
            boolean bl48 = true;
            String string53 = "playerEntities";
            MWorld mWorld53 = this;
            this.var_gg_vape_mapping_MappingField_f = this.J(string53, bl48, clazz66);
            Class clazz67 = MappedClasses.WORLD_PROVIDER;
            boolean bl49 = true;
            String string54 = "provider";
            MWorld mWorld54 = this;
            this.worldProviderField = this.J(string54, bl49, clazz67);
            if (ForgeVersion.MC_1_12_2.d()) {
                Class[] classArray46 = new Class[]{MappedClasses.zc, MappedClasses.uk, MappedClasses.lH};
                Class<List> clazz68 = List.class;
                boolean bl50 = true;
                String string55 = "getEntitiesInAABBexcluding";
                MWorld mWorld55 = this;
                this.var_gg_vape_mapping_MappingMethod_T = this.Y(string55, bl50, clazz68, classArray46);
            } else {
                Class[] classArray47 = new Class[]{MappedClasses.zc, MappedClasses.uk};
                Class<List> clazz69 = List.class;
                boolean bl51 = true;
                String string56 = "getEntitiesWithinAABBExcludingEntity";
                MWorld mWorld56 = this;
                this.var_gg_vape_mapping_MappingMethod_T = this.Y(string56, bl51, clazz69, classArray47);
            }
            Class[] classArray48 = new Class[]{MappedClasses.zc};
            Class<Void> clazz70 = Void.TYPE;
            boolean bl52 = true;
            String string57 = "removeEntity";
            MWorld mWorld57 = this;
            this.var_gg_vape_mapping_MappingMethod_g = this.Y(string57, bl52, clazz70, classArray48);
            Class[] classArray49 = new Class[]{Integer.TYPE, Integer.TYPE};
            Class clazz71 = MappedClasses.VJ;
            boolean bl53 = true;
            String string58 = "getChunkFromChunkCoords";
            MWorld mWorld58 = this;
            this.var_gg_vape_mapping_MappingMethod_z = this.Y(string58, bl53, clazz71, classArray49);
            Class[] classArray50 = new Class[]{};
            Class clazz72 = MappedClasses.lg;
            boolean bl54 = true;
            String string59 = "getChunkProvider";
            MWorld mWorld59 = this;
            this.var_gg_vape_mapping_MappingMethod_Q = this.Y(string59, bl54, clazz72, classArray50);
            Class[] classArray51 = new Class[]{MappedClasses.zc};
            Class<Void> clazz73 = Void.TYPE;
            boolean bl55 = true;
            String string60 = "onEntityAdded";
            MWorld mWorld60 = this;
            this.var_gg_vape_mapping_MappingMethod_q = this.Y(string60, bl55, clazz73, classArray51);
            Class[] classArray52 = new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE};
            Class<Void> clazz74 = Void.TYPE;
            boolean bl56 = true;
            String string61 = "markBlockRangeForRenderUpdate";
            MWorld mWorld61 = this;
            this.var_gg_vape_mapping_MappingMethod_L = this.Y(string61, bl56, clazz74, classArray52);
            Class[] classArray53 = new Class[]{Long.TYPE};
            Class<Void> clazz75 = Void.TYPE;
            boolean bl57 = true;
            String string62 = "setWorldTime";
            MWorld mWorld62 = this;
            this.var_gg_vape_mapping_MappingMethod_N = this.Y(string62, bl57, clazz75, classArray53);
        }
        if (ForgeVersion.MC_1_17.d()) {
            Class[] classArray54 = new Class[]{};
            Class clazz76 = MappedClasses.Vt;
            boolean bl58 = true;
            String string63 = "getChunkSource";
            Class clazz77 = MappedClasses.uE;
            MWorld mWorld63 = this;
            this.var_gg_vape_mapping_MappingMethod_F = this.registerInstanceMethodForOwner(clazz77, string63, bl58, clazz76, classArray54);
        }
        if (ForgeVersion.MC_1_20_6.d()) {
            Class clazz78 = MappedClasses.ZZ;
            boolean bl59 = true;
            String string64 = "damageSources";
            MWorld mWorld64 = this;
            this.var_gg_vape_mapping_MappingField_K = this.J(string64, bl59, clazz78);
        }
        if (ForgeVersion.MC_1_8_9.B()) {
            Class[] classArray55 = new Class[]{MappedClasses.uk, MappedClasses.ZN};
            Class<Boolean> clazz79 = Boolean.TYPE;
            boolean bl60 = true;
            String string65 = "isAABBInMaterial";
            MWorld mWorld65 = this;
            this.var_gg_vape_mapping_MappingMethod_l = this.Y(string65, bl60, clazz79, classArray55);
        }
        if (ForgeVersion.MC_1_21_4.d()) {
            Class clazz80 = MappedClasses.Fd;
            boolean bl61 = true;
            String string66 = "registryAccess";
            MWorld mWorld66 = this;
            this.var_gg_vape_mapping_MappingField_S = this.J(string66, bl61, clazz80);
        }
    }

    private boolean M(Object object, Object object2, Object object3) {
        return this.var_gg_vape_mapping_MappingMethod_h.invokeBoolean(object, object2, object3);
    }

    private boolean k(Object object, Object object2, Object object3) {
        return this.var_gg_vape_mapping_MappingMethod_i.invokeBoolean(object, object2, object3);
    }

    private boolean V(Object object) {
        return this.var_gg_vape_mapping_MappingField_U.getBoolean(object);
    }

    public static boolean E$src$Z$o9fqyv() {
        boolean bl = MWorld.Q();
        return !bl;
    }

    public boolean d(Object object, int n, int n2) {
        return this.var_gg_vape_mapping_MappingMethod_P.invokeBoolean(object, n, n2);
    }

    public Object u(Object object, Object object2) {
        return this.var_gg_vape_mapping_MappingMethod_p.invokeObject(object, object2);
    }

    public List K(Object object, Object object2, Object object3) {
        return (List)this.var_gg_vape_mapping_MappingMethod_T.invokeObject(object, object2, object3);
    }

    public List j$src$Ljava_util_List_$jm6ihn(Object object) {
        return (List)this.var_gg_vape_mapping_MappingField_m.getObject(object);
    }

    public List X(Object object, Object object2, Object object3, Object object4) {
        return (List)this.var_gg_vape_mapping_MappingMethod_T.invokeObject(object, object2, object3, object4);
    }

    public Iterable m(Object object) {
        return (Iterable)this.var_gg_vape_mapping_MappingMethod_H.invokeObject(object, new Object[0]);
    }

    public static void c(MWorld mWorld, Object object, Object object2) {
        mWorld.A(object, object2);
    }

    public Object u(Object object) {
        return this.var_gg_vape_mapping_MappingField_S.getObject(object);
    }

    public Object E(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_Q.invokeObject(object, new Object[0]);
    }

    public Object H(Object object, int n, int n2) {
        return this.var_gg_vape_mapping_MappingMethod_w.invokeObject(object, n, n2);
    }

    public static boolean x(MWorld mWorld, Object object, Object object2, Object object3) {
        return mWorld.k(object, object2, object3);
    }

    private int b(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_B.invokeInt(object, new Object[0]);
    }

    private boolean S(Object object, int n, int n2, int n3) {
        if (ForgeVersion.MC_1_7_10.L()) {
            return this.var_gg_vape_mapping_MappingMethod_D.invokeBoolean(object, n, n2, n3);
        }
        return this.var_gg_vape_mapping_MappingMethod_D.invokeBoolean(object, BlockPos.create(n, n2, n3).getObject());
    }

    public void D(Object object, float f) {
        this.var_gg_vape_mapping_MappingField_G.setFloat(object, f);
    }

    public Object g(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_J.invokeObject(object, new Object[0]);
    }

    public static void a(MWorld mWorld, Object object, int n, Object object2) {
        mWorld.r(object, n, object2);
    }

    public Object a(Object object, Object object2) {
        return this.var_gg_vape_mapping_MappingMethod_e.invokeObject(object, object2);
    }

    public static boolean K(MWorld mWorld, Object object, Object object2, Object object3) {
        return mWorld.M(object, object2, object3);
    }

    public float a(Object object) {
        return this.var_gg_vape_mapping_MappingField_G.getFloat(object);
    }

    public void L(Object object, float f) {
        this.var_gg_vape_mapping_MappingField_I.setFloat(object, f);
    }

    public float w(Object object) {
        return this.var_gg_vape_mapping_MappingField_A.getFloat(object);
    }

    public Object y(Object object) {
        return this.var_gg_vape_mapping_MappingField_K.getObject(object);
    }

    public static void g(boolean bl) {
        var_boolean_k = bl;
    }

    private void r(Object object, int n, Object object2) {
        this.var_gg_vape_mapping_MappingMethod_g.invokeVoid(object, n, object2);
    }

    public static boolean Q() {
        return var_boolean_k;
    }

    public Object y(Object object, Object object2, Object object3) {
        return this.var_gg_vape_mapping_MappingMethod_y.invokeObject(object, object2, object3);
    }

    public Object getEntityGetter(Object world) {
        return this.var_gg_vape_mapping_MappingMethod_x.invokeObject(world, new Object[0]);
    }

    public boolean H(Object object, Object object2, Object object3) {
        return this.var_gg_vape_mapping_MappingMethod_l.invokeBoolean(object, object2, object3);
    }

    private int g(Object object, int n, int n2, int n3) {
        return this.var_gg_vape_mapping_MappingMethod_M.invokeInt(object, n, n2, n3);
    }

    private boolean y(Object object, int n, int n2, int n3, int n4, int n5, int n6) {
        return this.var_gg_vape_mapping_MappingMethod_h.invokeBoolean(object, n, n2, n3, n4, n5, n6);
    }

    public boolean u(Object object, Object object2, Object object3, BiPredicate biPredicate) {
        return this.var_gg_vape_mapping_MappingMethod_Y.invokeBoolean(object, object2, object3, biPredicate);
    }

    public Object j(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_F.invokeObject(object, new Object[0]);
    }

    public float Z(Object object) {
        return this.var_gg_vape_mapping_MappingField_Z.getFloat(object);
    }

    public static int X(MWorld mWorld, Object object, int n, int n2, int n3) {
        return mWorld.g(object, n, n2, n3);
    }

    public List y$src$Ljava_util_List_$1xik31o(Object object) {
        return (List)this.var_gg_vape_mapping_MappingField_E.getObject(object);
    }

    public static boolean w(MWorld mWorld, Object object) {
        return mWorld.V(object);
    }

    public static int i(MWorld mWorld, Object object) {
        return mWorld.b(object);
    }

    public Object l(Object object, Object object2, Object object3, boolean bl, boolean bl2, boolean bl3) {
        return this.var_gg_vape_mapping_MappingMethod_o.invokeObject(object, object2, object3, bl, bl2, bl3);
    }

    public List T(Object object, Object object2, Object object3, Object object4) {
        return (List)this.var_gg_vape_mapping_MappingMethod_T.invokeObject(object, object2, object3, object4);
    }

    public void R(Object object, float f) {
        this.var_gg_vape_mapping_MappingField_A.setFloat(object, f);
    }

    public void V(Object object, long l) {
        this.var_gg_vape_mapping_MappingMethod_N.invokeVoid(object, l);
    }

    public Object t(Object object, Object object2) {
        return this.var_gg_vape_mapping_MappingMethod_s.invokeObject(object, object2);
    }

    public Object U(Object object, int n, int n2, int n3) {
        return this.var_gg_vape_mapping_MappingMethod_V.invokeObject(object, n, n2, n3);
    }

    public void M(Object object, float f) {
        this.var_gg_vape_mapping_MappingField_Z.setFloat(object, f);
    }

    private Object getWorldProviderHandle(Object world) {
        return this.worldProviderField.getObject(world);
    }

    private Object t(Object object, int n, int n2) {
        return this.var_gg_vape_mapping_MappingMethod_z.invokeObject(object, n, n2);
    }

    public static boolean v(MWorld mWorld, Object object, int n, int n2, int n3) {
        return mWorld.S(object, n, n2, n3);
    }

    public List Q(Object object) {
        return (List)this.var_gg_vape_mapping_MappingField_f.getObject(object);
    }

    public Object v(Object object, Object object2) {
        return this.var_gg_vape_mapping_MappingMethod_v.invokeObject(object, object2);
    }

    public Object Z(Object object, Object object2) {
        return this.var_gg_vape_mapping_MappingMethod_w.invokeObject(object, object2);
    }

    public float X(Object object) {
        return this.var_gg_vape_mapping_MappingField_I.getFloat(object);
    }

    public Object i(Object object, Object object2) {
        return this.var_gg_vape_mapping_MappingMethod_o.invokeObject(object, object2);
    }

    public static Object getWorldProvider(MWorld mappings, Object world) {
        return mappings.getWorldProviderHandle(world);
    }

    private Object b(Object object, int n) {
        return this.var_gg_vape_mapping_MappingMethod_O.invokeObject(object, n);
    }

    static {
        MWorld.g(false);
    }
}

