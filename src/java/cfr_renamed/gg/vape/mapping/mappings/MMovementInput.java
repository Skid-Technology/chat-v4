/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingFieldBuilder;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MMovementInput
extends Mapping {
    private MappingField var_gg_vape_mapping_MappingField_y;
    private MappingField var_gg_vape_mapping_MappingField_p;
    private MappingField var_gg_vape_mapping_MappingField_q;
    private MappingField var_gg_vape_mapping_MappingField_b;
    private MappingField var_gg_vape_mapping_MappingField_K;
    private MappingField var_gg_vape_mapping_MappingField_V;

    public MMovementInput() {
        this(BlockData.W());
    }

    private MMovementInput(String[] stringArray) {
        super(MappedClasses.qO);
        MMovementInput mMovementInput;
        MMovementInput mMovementInput2;
        String string;
        boolean bl;
        Class<Boolean> clazz;
        String[] stringArray2 = stringArray;
        if (ForgeVersion.MC_1_21_6.d()) {
            clazz = MappedClasses.YG;
            bl = true;
            string = "moveVector";
            mMovementInput2 = this;
            this.var_gg_vape_mapping_MappingField_q = mMovementInput2.J(string, bl, clazz);
        } else {
            clazz = Float.TYPE;
            String string2 = "moveStrafe";
            mMovementInput = this;
            this.var_gg_vape_mapping_MappingField_V = ((MappingFieldBuilder)mMovementInput.fieldBuilder(string2, clazz).setNameForVersion(ForgeVersion.MC_1_21_0.n(), "leftImpulse")).buildField();
        }
        if (ForgeVersion.MC_1_21_4.d()) {
            clazz = MappedClasses.qn;
            bl = true;
            string = "keyPresses";
            mMovementInput2 = this;
            this.var_gg_vape_mapping_MappingField_y = mMovementInput2.J(string, bl, clazz);
            if (ForgeVersion.MC_1_21_6.v()) {
                Class<Float> clazz2 = Float.TYPE;
                boolean bl2 = true;
                String string2 = "forwardImpulse";
                MMovementInput mMovementInput22 = this;
                this.var_gg_vape_mapping_MappingField_p = this.J(string2, bl2, clazz2);
            }
        } else {
            clazz = Boolean.TYPE;
            String string3 = "jump";
            mMovementInput = this;
            this.var_gg_vape_mapping_MappingField_K = ((MappingFieldBuilder)mMovementInput.fieldBuilder(string3, clazz).setNameForVersion(ForgeVersion.MC_1_21_0.n(), "jumping")).buildField();
            Class<Boolean> clazz3 = Boolean.TYPE;
            String string32 = "sneak";
            MMovementInput mMovementInput3 = this;
            this.var_gg_vape_mapping_MappingField_b = ((MappingFieldBuilder)((MappingFieldBuilder)this.fieldBuilder(string32, clazz3).setNameForVersion(ForgeVersion.MC_1_21_0.n(), "shiftKeyDown")).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "sneaking")).buildField();
            if (ForgeVersion.MC_1_12_2.d()) {
                if (ForgeVersion.MC_1_17.d()) {
                    Class<Float> clazz4 = Float.TYPE;
                    boolean bl2 = true;
                    String string4 = "forwardImpulse";
                    MMovementInput mMovementInput4 = this;
                    this.var_gg_vape_mapping_MappingField_p = this.J(string4, bl2, clazz4);
                } else {
                    Class<Float> clazz5 = Float.TYPE;
                    boolean bl3 = Wrapper.isNativeAvailable;
                    String string5 = "field_192832_b";
                    MMovementInput mMovementInput5 = this;
                    this.var_gg_vape_mapping_MappingField_p = this.J(string5, bl3, clazz5);
                }
            } else {
                Class<Float> clazz6 = Float.TYPE;
                boolean bl4 = true;
                String string6 = "moveForward";
                MMovementInput mMovementInput6 = this;
                this.var_gg_vape_mapping_MappingField_p = this.J(string6, bl4, clazz6);
            }
        }
    }

    private float P(Object object) {
        return this.var_gg_vape_mapping_MappingField_V.getFloat(object);
    }

    private void C(Object object, Object object2) {
        this.var_gg_vape_mapping_MappingField_y.setObject(object, object2);
    }

    private void F(Object object, Object object2) {
        this.var_gg_vape_mapping_MappingField_q.setObject(object, object2);
    }

    public static boolean j(MMovementInput mMovementInput, Object object) {
        return mMovementInput.k(object);
    }

    public static void H(MMovementInput mMovementInput, Object object, boolean bl) {
        mMovementInput.l(object, bl);
    }

    public static Object W(MMovementInput mMovementInput, Object object) {
        return mMovementInput.O(object);
    }

    public static void x(MMovementInput mMovementInput, Object object, Object object2) {
        mMovementInput.C(object, object2);
    }

    private boolean k(Object object) {
        return this.var_gg_vape_mapping_MappingField_K.getBoolean(object);
    }

    private void l(Object object, boolean bl) {
        this.var_gg_vape_mapping_MappingField_K.setBoolean(object, bl);
    }

    public static void V(MMovementInput mMovementInput, Object object, boolean bl) {
        mMovementInput.t(object, bl);
    }

    private Object n(Object object) {
        return this.var_gg_vape_mapping_MappingField_y.getObject(object);
    }

    private void G(Object object, float f) {
        this.var_gg_vape_mapping_MappingField_p.setFloat(object, f);
    }

    private float f(Object object) {
        return this.var_gg_vape_mapping_MappingField_p.getFloat(object);
    }

    public static void j(MMovementInput mMovementInput, Object object, float f) {
        mMovementInput.v(object, f);
    }

    private boolean T(Object object) {
        return this.var_gg_vape_mapping_MappingField_b.getBoolean(object);
    }

    public static float f(MMovementInput mMovementInput, Object object) {
        return mMovementInput.f(object);
    }

    private Object O(Object object) {
        return this.var_gg_vape_mapping_MappingField_q.getObject(object);
    }

    public static void p(MMovementInput mMovementInput, Object object, float f) {
        mMovementInput.G(object, f);
    }

    public static boolean R(MMovementInput mMovementInput, Object object) {
        return mMovementInput.T(object);
    }

    private void t(Object object, boolean bl) {
        this.var_gg_vape_mapping_MappingField_b.setBoolean(object, bl);
    }

    public static float b(MMovementInput mMovementInput, Object object) {
        return mMovementInput.P(object);
    }

    private void v(Object object, float f) {
        this.var_gg_vape_mapping_MappingField_V.setFloat(object, f);
    }

    public static Object x(MMovementInput mMovementInput, Object object) {
        return mMovementInput.n(object);
    }
}

