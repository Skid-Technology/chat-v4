/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEnumFacing;
import gg.vape.utils.MathUtil;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.InlineVec3i;
import gg.vape.wrapper.impl.Vec3i;

public class EnumFacing
extends Wrapper {
    private Integer var_java_lang_Integer_C;
    final public static int var_int_o;
    private static EnumFacing var_gg_vape_wrapper_impl_EnumFacing_f;
    private static EnumFacing[] var_gg_vape_wrapper_impl_EnumFacing_arr_i;
    private static EnumFacing var_gg_vape_wrapper_impl_EnumFacing_s;
    final public static int var_int_e = 0;
    final public static int var_int_t;
    private EnumFacing var_gg_vape_wrapper_impl_EnumFacing_v;
    private static EnumFacing var_gg_vape_wrapper_impl_EnumFacing_K;
    private static EnumFacing[] var_gg_vape_wrapper_impl_EnumFacing_arr_w;
    private static EnumFacing var_gg_vape_wrapper_impl_EnumFacing_A;
    final public static int var_int_W = 1;
    private Integer var_java_lang_Integer_S;
    private static EnumFacing var_gg_vape_wrapper_impl_EnumFacing_H;
    private static EnumFacing var_gg_vape_wrapper_impl_EnumFacing_P;
    final public static int var_int_X;
    final public static int var_int_q;
    private Vec3i directionVector;

    public static EnumFacing[] t() {
        if (var_gg_vape_wrapper_impl_EnumFacing_arr_i == null) {
            Object[] objectArray = ForgeVersion.MC_1_7_10.Y() ? EnumFacing.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MEnumFacing_O.H() : EnumFacing.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MEnumFacing_O.U();
            EnumFacing[] enumFacingArray = new EnumFacing[6];
            for (Object object : objectArray) {
                EnumFacing enumFacing;
                enumFacingArray[enumFacing.Y()] = enumFacing = new EnumFacing(object);
            }
            var_gg_vape_wrapper_impl_EnumFacing_arr_i = enumFacingArray;
        }
        return var_gg_vape_wrapper_impl_EnumFacing_arr_i;
    }

    public static EnumFacing[] c$src$ALgg_vape_wrapper_impl_EnumFacing_$1i3g4ft() {
        if (var_gg_vape_wrapper_impl_EnumFacing_arr_w == null) {
            EnumFacing[] enumFacingArray = new EnumFacing[4];
            Object[] objectArray = null;
            if (ForgeVersion.MC_1_7_10.Y() && ForgeVersion.MC_1_16_5.v()) {
                objectArray = EnumFacing.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MEnumFacing_O.A();
            }
            if (objectArray != null) {
                for (int i = 0; i < objectArray.length; ++i) {
                    enumFacingArray[i] = new EnumFacing(objectArray[i]);
                }
            } else {
                EnumFacing[] enumFacingArray2;
                for (EnumFacing enumFacing : enumFacingArray2 = EnumFacing.t()) {
                    int n = enumFacing.c();
                    if (n == -1) continue;
                    enumFacingArray[n] = enumFacing;
                }
            }
            var_gg_vape_wrapper_impl_EnumFacing_arr_w = enumFacingArray;
        }
        return var_gg_vape_wrapper_impl_EnumFacing_arr_w;
    }

    public static EnumFacing p(double d) {
        return EnumFacing.E(MathUtil.floor(d / 90.0 + 0.5) & 3);
    }

    public int o() {
        return this.getDirectionVector().getZ();
    }

    public int C() {
        return this.getDirectionVector().getY();
    }

    public static EnumFacing F$src$Lgg_vape_wrapper_impl_EnumFacing_$glfxl5() {
        if (var_gg_vape_wrapper_impl_EnumFacing_f == null) {
            var_gg_vape_wrapper_impl_EnumFacing_f = new EnumFacing(MEnumFacing.z(EnumFacing.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MEnumFacing_O).getObject(null));
        }
        return var_gg_vape_wrapper_impl_EnumFacing_f;
    }

    public static EnumFacing E(int n) {
        EnumFacing[] enumFacingArray = EnumFacing.c$src$ALgg_vape_wrapper_impl_EnumFacing_$1i3g4ft();
        return enumFacingArray[MathUtil.abs(n % enumFacingArray.length)];
    }

    private static IllegalStateException a(IllegalStateException illegalStateException) {
        return illegalStateException;
    }

    public static EnumFacing T(int n) {
        return EnumFacing.t()[n];
    }

    public EnumFacing(Object object) {
        super(object);
    }

    public static EnumFacing M() {
        if (var_gg_vape_wrapper_impl_EnumFacing_A == null) {
            var_gg_vape_wrapper_impl_EnumFacing_A = new EnumFacing(MEnumFacing.R(EnumFacing.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MEnumFacing_O).getObject(null));
        }
        return var_gg_vape_wrapper_impl_EnumFacing_A;
    }

    public static EnumFacing g$src$Lgg_vape_wrapper_impl_EnumFacing_$1ii8mzu() {
        if (var_gg_vape_wrapper_impl_EnumFacing_H == null) {
            var_gg_vape_wrapper_impl_EnumFacing_H = ForgeVersion.MC_1_7_10.Y() ? new EnumFacing(MEnumFacing.s(EnumFacing.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MEnumFacing_O).getObject(null)) : new EnumFacing(MEnumFacing.n(EnumFacing.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MEnumFacing_O).getObject(null));
        }
        return var_gg_vape_wrapper_impl_EnumFacing_H;
    }

    public static EnumFacing X() {
        if (var_gg_vape_wrapper_impl_EnumFacing_P == null) {
            var_gg_vape_wrapper_impl_EnumFacing_P = ForgeVersion.MC_1_7_10.Y() ? new EnumFacing(MEnumFacing.n(EnumFacing.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MEnumFacing_O).getObject(null)) : new EnumFacing(MEnumFacing.s(EnumFacing.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MEnumFacing_O).getObject(null));
        }
        return var_gg_vape_wrapper_impl_EnumFacing_P;
    }

    public Vec3i getDirectionVector() {
        if (this.directionVector == null) {
            if (ForgeVersion.MC_1_7_10.Y()) {
                this.directionVector = new Vec3i(EnumFacing.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MEnumFacing_O.e(this.I));
            } else {
                int n = EnumFacing.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MEnumFacing_O.V(this.I);
                int n2 = EnumFacing.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MEnumFacing_O.A(this.I);
                int n3 = EnumFacing.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MEnumFacing_O.s(this.I);
                this.directionVector = new InlineVec3i(n, n2, n3);
            }
        }
        return this.directionVector;
    }

    public static EnumFacing w() {
        if (var_gg_vape_wrapper_impl_EnumFacing_K == null) {
            var_gg_vape_wrapper_impl_EnumFacing_K = new EnumFacing(MEnumFacing.b(EnumFacing.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MEnumFacing_O).getObject(null));
        }
        return var_gg_vape_wrapper_impl_EnumFacing_K;
    }

    public static EnumFacing B() {
        if (var_gg_vape_wrapper_impl_EnumFacing_s == null) {
            var_gg_vape_wrapper_impl_EnumFacing_s = new EnumFacing(MEnumFacing.E(EnumFacing.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MEnumFacing_O).getObject(null));
        }
        return var_gg_vape_wrapper_impl_EnumFacing_s;
    }

    public EnumFacing getOpposite() {
        if (this.var_gg_vape_wrapper_impl_EnumFacing_v == null) {
            int n = ForgeVersion.MC_1_7_10.Y() ? EnumFacing.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MEnumFacing_O.b(this.I) : EnumFacing.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MEnumFacing_O.v(this.I);
            this.var_gg_vape_wrapper_impl_EnumFacing_v = EnumFacing.T(n);
        }
        return this.var_gg_vape_wrapper_impl_EnumFacing_v;
    }

    public static EnumFacing g(String string) {
        if (string != null) {
            switch (string.toLowerCase()) {
                case "down": {
                    return EnumFacing.B();
                }
                case "up": {
                    return EnumFacing.F$src$Lgg_vape_wrapper_impl_EnumFacing_$glfxl5();
                }
                case "north": {
                    return EnumFacing.w();
                }
                case "south": {
                    return EnumFacing.M();
                }
                case "west": {
                    return EnumFacing.X();
                }
                case "east": {
                    return EnumFacing.g$src$Lgg_vape_wrapper_impl_EnumFacing_$1ii8mzu();
                }
            }
        }
        throw new IllegalStateException("Unmatched EnumFacing Name");
    }

    public String h() {
        return String.valueOf(EnumFacing.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MEnumFacing_O.c(this.I));
    }

    public int Y() {
        if (this.var_java_lang_Integer_C == null) {
            this.var_java_lang_Integer_C = ForgeVersion.MC_1_7_10.Y() ? Integer.valueOf(EnumFacing.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MEnumFacing_O.p(this.I)) : Integer.valueOf(EnumFacing.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MEnumFacing_O.i(this.I));
        }
        return this.var_java_lang_Integer_C;
    }

    public int g() {
        return this.getDirectionVector().getX();
    }

    public int c() {
        if (this.var_java_lang_Integer_S == null) {
            if (ForgeVersion.MC_1_7_10.Y() && ForgeVersion.MC_1_16_5.v()) {
                this.var_java_lang_Integer_S = EnumFacing.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MEnumFacing_O.S(this.I);
            } else {
                int n = -1;
                switch (this.Y()) {
                    case 3: {
                        n = 0;
                        break;
                    }
                    case 4: {
                        n = 1;
                        break;
                    }
                    case 2: {
                        n = 2;
                        break;
                    }
                    case 5: {
                        n = 3;
                    }
                }
                this.var_java_lang_Integer_S = n;
            }
        }
        return this.var_java_lang_Integer_S;
    }

    static {
        long[] lArray = new long[]{1773040238696857602L, 8069781883149352964L, 4717156233235136517L, -8897107917115752445L};
        var_int_t = (int)lArray[0];
        var_int_o = (int)lArray[3];
        var_int_X = (int)lArray[2];
        var_int_q = (int)lArray[1];
    }
}

