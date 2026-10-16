/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MTimer
extends Mapping {
    private MappingField var_gg_vape_mapping_MappingField_Q;
    private MappingField var_gg_vape_mapping_MappingField_h;
    final public MappingField var_gg_vape_mapping_MappingField_j;
    final public MappingField var_gg_vape_mapping_MappingField_l;

    public static void k(MTimer mTimer, Object object, float f) {
        mTimer.u(object, f);
    }

    private float b(Object object) {
        return this.var_gg_vape_mapping_MappingField_l.getFloat(object);
    }

    public static float S(MTimer mTimer, Object object) {
        return mTimer.I(object);
    }

    private float I(Object object) {
        return this.var_gg_vape_mapping_MappingField_h.getFloat(object);
    }

    private float k(Object object) {
        return this.var_gg_vape_mapping_MappingField_j.getFloat(object);
    }

    public float Z(Object object) {
        return this.var_gg_vape_mapping_MappingField_Q.getFloat(object);
    }

    public MTimer() {
        this(BlockData.W());
    }

    private MTimer(String[] stringArray) {
        super(MappedClasses.YN);
        if (stringArray != null) {
            MTimer mTimer;
            String string;
            boolean bl;
            Class<Float> clazz;
            if (ForgeVersion.MC_1_12_2.d()) {
                clazz = Float.TYPE;
                bl = Wrapper.isNativeAvailable;
                string = "field_194148_c";
                mTimer = this;
                this.var_gg_vape_mapping_MappingField_j = mTimer.J(string, bl, clazz);
                if (ForgeVersion.MC_1_12_2.L()) {
                    Class<Float> clazz2 = Float.TYPE;
                    boolean bl2 = false;
                    String string2 = "field_194149_e";
                    MTimer mTimer2 = this;
                    this.var_gg_vape_mapping_MappingField_Q = this.J(string2, bl2, clazz2);
                } else {
                    Class<Float> clazz3 = Float.TYPE;
                    boolean bl3 = true;
                    String string3 = "msPerTick";
                    MTimer mTimer3 = this;
                    this.var_gg_vape_mapping_MappingField_Q = this.J(string3, bl3, clazz3);
                }
            } else {
                clazz = Float.TYPE;
                bl = true;
                string = "timerSpeed";
                mTimer = this;
                this.var_gg_vape_mapping_MappingField_h = mTimer.J(string, bl, clazz);
                Class<Float> clazz4 = Float.TYPE;
                boolean bl4 = true;
                String string4 = "elapsedPartialTicks";
                MTimer mTimer4 = this;
                this.var_gg_vape_mapping_MappingField_j = this.J(string4, bl4, clazz4);
            }
            clazz = Float.TYPE;
            bl = true;
            string = "renderPartialTicks";
            mTimer = this;
            this.var_gg_vape_mapping_MappingField_l = mTimer.J(string, bl, clazz);
            return;
        }
        Class<Float> clazz = Float.TYPE;
        boolean bl = true;
        String string = "elapsedPartialTicks";
        MTimer mTimer = this;
        this.var_gg_vape_mapping_MappingField_j = mTimer.J(string, bl, clazz);
        Class<Float> clazz5 = Float.TYPE;
        boolean bl5 = true;
        String string5 = "renderPartialTicks";
        MTimer mTimer5 = this;
        this.var_gg_vape_mapping_MappingField_l = this.J(string5, bl5, clazz5);
    }

    public static float H(MTimer mTimer, Object object) {
        return mTimer.k(object);
    }

    private void x(Object object, float f) {
        this.var_gg_vape_mapping_MappingField_h.setFloat(object, f);
    }

    private void u(Object object, float f) {
        this.var_gg_vape_mapping_MappingField_Q.setFloat(object, f);
    }

    public static float l(MTimer mTimer, Object object) {
        return mTimer.b(object);
    }

    public static void y(MTimer mTimer, Object object, float f) {
        mTimer.x(object, f);
    }
}

