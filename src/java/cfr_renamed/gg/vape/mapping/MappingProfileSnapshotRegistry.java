/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMemberSnapshot;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.StringPair;
import java.util.ArrayList;

public class MappingProfileSnapshotRegistry {
    private static int var_int_X;
    private static int var_int_H;
    private static ArrayList<MappingMemberSnapshot> var_java_util_ArrayList_gg_vape_mapping_MappingMemberSnapshot__k;
    private static int var_int_p;
    private static ArrayList<StringPair> var_java_util_ArrayList_gg_vape_utils_StringPair__W;
    private static ArrayList<MappingMemberSnapshot> var_java_util_ArrayList_gg_vape_mapping_MappingMemberSnapshot__y;
    private static int[] var_int_arr_O;
    private static int var_int_v;
    private static int var_int_N;

    public static void h() {
    }

    public static void Y() {
        var_java_util_ArrayList_gg_vape_utils_StringPair__W.clear();
        var_int_v = 0;
    }

    public static void X(MappingMethod mappingMethod) {
        if (!Vape.INSTANCE.isMappingsRemapped() && !Vape.INSTANCE.isForgeRemapInactive()) {
            return;
        }
        MappingMemberSnapshot mappingMemberSnapshot = new MappingMemberSnapshot(mappingMethod.getMappingOwner(), mappingMethod.getOwnerClass(), mappingMethod.getOriginalName(), mappingMethod.getDeclaredReturnType(), mappingMethod.getDeclaredParameterTypes(), mappingMethod.getRuntimeName(), mappingMethod.getResolvedReturnType(), mappingMethod.getResolvedParameterTypes(), mappingMethod.isMappedMember());
        var_java_util_ArrayList_gg_vape_mapping_MappingMemberSnapshot__k.add(mappingMemberSnapshot);
        ++var_int_H;
    }

    public static void y(int[] nArray) {
        var_int_arr_O = nArray;
    }

    public static void j() {
        ++var_int_N;
    }

    public static int[] T() {
        return var_int_arr_O;
    }

    public static void y() {
        var_int_p = 0;
        var_int_N = 0;
        var_int_H = 0;
        var_int_X = 0;
    }

    public static void i() {
        ++var_int_v;
    }

    public static void n(MappingField mappingField) {
        if (!Vape.INSTANCE.isMappingsRemapped() && !Vape.INSTANCE.isForgeRemapInactive()) {
            return;
        }
        MappingMemberSnapshot mappingMemberSnapshot = new MappingMemberSnapshot(mappingField.getMappingOwner(), mappingField.getOwnerClass(), mappingField.getOriginalName(), mappingField.getDeclaredFieldType(), null, mappingField.getRuntimeName(), mappingField.getResolvedFieldType(), null, mappingField.isMappedMember());
        var_java_util_ArrayList_gg_vape_mapping_MappingMemberSnapshot__y.add(mappingMemberSnapshot);
        ++var_int_p;
    }

    public static void X() {
    }

    public static void O() {
        ++var_int_X;
    }

    public static void v(String string, String string2) {
        var_java_util_ArrayList_gg_vape_utils_StringPair__W.add(new StringPair(string, string2));
    }

    static {
        var_int_v = 0;
        var_int_p = 0;
        var_int_N = 0;
        var_int_H = 0;
        var_int_X = 0;
        var_java_util_ArrayList_gg_vape_utils_StringPair__W = new ArrayList();
        var_java_util_ArrayList_gg_vape_mapping_MappingMemberSnapshot__y = new ArrayList();
        var_java_util_ArrayList_gg_vape_mapping_MappingMemberSnapshot__k = new ArrayList();
        MappingProfileSnapshotRegistry.y(new int[5]);
    }
}

