/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.asm.helper.DescUtils;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingFieldBuilder;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MEnumFacing
extends Mapping {
    public MappingMethod var_gg_vape_mapping_MappingMethod_g;
    public MappingField var_gg_vape_mapping_MappingField_z;
    private final MappingField var_gg_vape_mapping_MappingField_o;
    private final MappingField var_gg_vape_mapping_MappingField_s;
    public MappingField var_gg_vape_mapping_MappingField_i;
    private final MappingField var_gg_vape_mapping_MappingField_b;
    public MappingField var_gg_vape_mapping_MappingField_k;
    public MappingField var_gg_vape_mapping_MappingField_T;
    private final MappingField var_gg_vape_mapping_MappingField_p;
    public MappingField var_gg_vape_mapping_MappingField_m;
    public MappingField var_gg_vape_mapping_MappingField_C;
    public MappingMethod var_gg_vape_mapping_MappingMethod_x;
    public MappingMethod var_gg_vape_mapping_MappingMethod_P;
    public MappingMethod var_gg_vape_mapping_MappingMethod_I;
    public MappingField var_gg_vape_mapping_MappingField_F;
    public MappingMethod var_gg_vape_mapping_MappingMethod_L;
    private final MappingField var_gg_vape_mapping_MappingField_n;
    public MappingField var_gg_vape_mapping_MappingField_v;
    private final MappingField var_gg_vape_mapping_MappingField_K;
    public MappingField var_gg_vape_mapping_MappingField_A;

    public int b(Object object) {
        return this.var_gg_vape_mapping_MappingField_z.getInt(object);
    }

    public int A(Object object) {
        return this.var_gg_vape_mapping_MappingField_T.getInt(object);
    }

    public int v(Object object) {
        return this.var_gg_vape_mapping_MappingField_i.getInt(object);
    }

    public Object[] U() {
        return this.var_gg_vape_mapping_MappingField_C.getObjectArray(null);
    }

    public Object[] H() {
        return this.var_gg_vape_mapping_MappingMethod_P.invokeObjectArray(null, new Object[0]);
    }

    public Object c(Object object) {
        if (this.var_gg_vape_mapping_MappingField_F != null) {
            return this.var_gg_vape_mapping_MappingField_F.getObject(object).toString();
        }
        return this.var_gg_vape_mapping_MappingMethod_L.invokeObject(object, new Object[0]);
    }

    public Object e(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_I.invokeObject(object, new Object[0]);
    }

    public static MappingField R(MEnumFacing mEnumFacing) {
        return mEnumFacing.var_gg_vape_mapping_MappingField_K;
    }

    public int s(Object object) {
        return this.var_gg_vape_mapping_MappingField_m.getInt(object);
    }

    public static MappingField E(MEnumFacing mEnumFacing) {
        return mEnumFacing.var_gg_vape_mapping_MappingField_n;
    }

    public static MappingField b(MEnumFacing mEnumFacing) {
        return mEnumFacing.var_gg_vape_mapping_MappingField_o;
    }

    public static MappingField z(MEnumFacing mEnumFacing) {
        return mEnumFacing.var_gg_vape_mapping_MappingField_s;
    }

    public int p(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_g.invokeInt(object, new Object[0]);
    }

    public MEnumFacing() {
        this(BlockData.W());
    }

    private MEnumFacing(String[] stringArray) {
        super(MappedClasses.q0);
        Class clazz;
        MEnumFacing mEnumFacing;
        String[] stringArray2 = stringArray;
        if (ForgeVersion.MC_1_7_10.Y()) {
            Class[] classArray = new Class[]{};
            Class<String> clazz2 = String.class;
            boolean bl = true;
            String string = "getName2";
            mEnumFacing = this;
            this.var_gg_vape_mapping_MappingMethod_L = mEnumFacing.Y(string, bl, clazz2, classArray);
            Class[] classArray2 = new Class[]{};
            Class<?> clazz22 = DescUtils.getArrayType(MappedClasses.q0);
            boolean bl2 = false;
            String string2 = "values";
            MEnumFacing mEnumFacing2 = this;
            this.var_gg_vape_mapping_MappingMethod_P = this.registerStaticMethod(string2, bl2, clazz22, classArray2);
            Class<Integer> clazz3 = Integer.TYPE;
            boolean bl3 = true;
            String string3 = "opposite";
            MEnumFacing mEnumFacing3 = this;
            this.var_gg_vape_mapping_MappingField_z = this.J(string3, bl3, clazz3);
            Class[] classArray3 = new Class[]{};
            Class<Integer> clazz4 = Integer.TYPE;
            boolean bl4 = false;
            String string4 = "ordinal";
            MEnumFacing mEnumFacing4 = this;
            this.var_gg_vape_mapping_MappingMethod_g = this.Y(string4, bl4, clazz4, classArray3);
            Class[] classArray4 = new Class[]{};
            Class clazz5 = MappedClasses.Vr;
            boolean bl5 = true;
            String string5 = "getDirectionVec";
            MEnumFacing mEnumFacing5 = this;
            this.var_gg_vape_mapping_MappingMethod_I = this.Y(string5, bl5, clazz5, classArray4);
            Class[] classArray5 = new Class[]{};
            Class<Integer> clazz6 = Integer.TYPE;
            boolean bl6 = true;
            String string6 = "getHorizontalIndex";
            MEnumFacing mEnumFacing6 = this;
            this.var_gg_vape_mapping_MappingMethod_x = this.Y(string6, bl6, clazz6, classArray5);
            if (ForgeVersion.MC_1_16_5.v()) {
                Class<?> clazz7 = DescUtils.getArrayType(MappedClasses.q0);
                boolean bl7 = true;
                String string7 = "HORIZONTALS";
                MEnumFacing mEnumFacing7 = this;
                this.var_gg_vape_mapping_MappingField_k = this.registerStaticField(string7, bl7, clazz7);
            }
        } else {
            clazz = String.class;
            boolean bl = true;
            String string = "name";
            Class<Enum> clazz8 = Enum.class;
            mEnumFacing = this;
            this.var_gg_vape_mapping_MappingField_F = mEnumFacing.registerInstanceFieldForOwner(clazz8, string, bl, clazz);
            if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
                Class clazz9 = MappedClasses.q0;
                String string8 = "faceList";
                MEnumFacing mEnumFacing8 = this;
                this.var_gg_vape_mapping_MappingField_C = ((MappingFieldBuilder)this.fieldBuilder(string8, clazz9).setStaticMember(true)).setArrayDimensions(1).buildField();
            } else {
                Class clazz10 = MappedClasses.q0;
                String string9 = "field_82609_l";
                MEnumFacing mEnumFacing9 = this;
                this.var_gg_vape_mapping_MappingField_C = ((MappingFieldBuilder)((MappingFieldBuilder)this.fieldBuilder(string9, clazz10).setStaticMember(true)).setMappedMember(Wrapper.isNativeAvailable)).setArrayDimensions(1).buildField();
            }
            Class<Integer> clazz11 = Integer.TYPE;
            boolean bl8 = true;
            String string10 = "order_b";
            MEnumFacing mEnumFacing10 = this;
            this.var_gg_vape_mapping_MappingField_i = this.J(string10, bl8, clazz11);
            Class<Integer> clazz12 = Integer.TYPE;
            boolean bl9 = true;
            String string11 = "order_a";
            MEnumFacing mEnumFacing11 = this;
            this.var_gg_vape_mapping_MappingField_v = this.J(string11, bl9, clazz12);
            Class<Integer> clazz13 = Integer.TYPE;
            boolean bl10 = true;
            String string12 = "frontOffsetX";
            MEnumFacing mEnumFacing12 = this;
            this.var_gg_vape_mapping_MappingField_A = this.J(string12, bl10, clazz13);
            Class<Integer> clazz14 = Integer.TYPE;
            boolean bl11 = true;
            String string13 = "frontOffsetY";
            MEnumFacing mEnumFacing13 = this;
            this.var_gg_vape_mapping_MappingField_T = this.J(string13, bl11, clazz14);
            Class<Integer> clazz15 = Integer.TYPE;
            boolean bl12 = true;
            String string14 = "frontOffsetZ";
            MEnumFacing mEnumFacing14 = this;
            this.var_gg_vape_mapping_MappingField_m = this.J(string14, bl12, clazz15);
        }
        clazz = MappedClasses.q0;
        boolean bl = true;
        String string = "DOWN";
        MEnumFacing mEnumFacing2 = this;
        this.var_gg_vape_mapping_MappingField_n = mEnumFacing2.registerStaticField(string, bl, clazz);
        Class clazz16 = MappedClasses.q0;
        boolean bl13 = true;
        String string15 = "UP";
        MEnumFacing mEnumFacing15 = this;
        this.var_gg_vape_mapping_MappingField_s = this.registerStaticField(string15, bl13, clazz16);
        Class clazz17 = MappedClasses.q0;
        boolean bl14 = true;
        String string16 = "NORTH";
        MEnumFacing mEnumFacing16 = this;
        this.var_gg_vape_mapping_MappingField_o = this.registerStaticField(string16, bl14, clazz17);
        Class clazz18 = MappedClasses.q0;
        boolean bl15 = true;
        String string17 = "SOUTH";
        MEnumFacing mEnumFacing17 = this;
        this.var_gg_vape_mapping_MappingField_K = this.registerStaticField(string17, bl15, clazz18);
        Class clazz19 = MappedClasses.q0;
        boolean bl16 = true;
        String string18 = "WEST";
        MEnumFacing mEnumFacing18 = this;
        this.var_gg_vape_mapping_MappingField_p = this.registerStaticField(string18, bl16, clazz19);
        Class clazz20 = MappedClasses.q0;
        boolean bl17 = true;
        String string19 = "EAST";
        MEnumFacing mEnumFacing19 = this;
        this.var_gg_vape_mapping_MappingField_b = this.registerStaticField(string19, bl17, clazz20);
    }

    public int V(Object object) {
        return this.var_gg_vape_mapping_MappingField_A.getInt(object);
    }

    public Object[] A() {
        return this.var_gg_vape_mapping_MappingField_k.getObjectArray(null);
    }

    public static MappingField n(MEnumFacing mEnumFacing) {
        return mEnumFacing.var_gg_vape_mapping_MappingField_p;
    }

    public int i(Object object) {
        return this.var_gg_vape_mapping_MappingField_v.getInt(object);
    }

    public static MappingField s(MEnumFacing mEnumFacing) {
        return mEnumFacing.var_gg_vape_mapping_MappingField_b;
    }

    public int S(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_x.invokeInt(object, new Object[0]);
    }
}

