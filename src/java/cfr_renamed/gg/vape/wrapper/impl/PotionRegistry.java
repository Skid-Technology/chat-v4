/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.PotionEffect;
import gg.vape.wrapper.impl.PotionEntry;
import gg.vape.wrapper.impl.PotionEntryResolveException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

public class PotionRegistry {
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_O;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_u;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_k;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_d;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_T;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_Z;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_w;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_W;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_v;
    private final static Map<Integer, PotionEntry> var_java_util_Map_java_lang_Integer,_gg_vape_wrapper_impl_PotionEntry__V;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_L;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_o;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_c;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_P;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_f;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_a;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_h;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_S;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_B;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_G;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_K;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_X;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_R;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_x;
    private static boolean var_boolean_A;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_e;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_z;
    private static boolean var_boolean_F;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_i;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_J;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_N;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_j;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_t;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_E;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_Q;
    private final static Map<Short, PotionEntry> var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_PotionEntry__b;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_C;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_y;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_r;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_M;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_l;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_U;
    final public static PotionEntry var_gg_vape_wrapper_impl_PotionEntry_H;

    public static List<PotionEntry> O() {
        ArrayList<PotionEntry> arrayList = new ArrayList<PotionEntry>();
        for (PotionEntry potionEntry : var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_PotionEntry__b.values()) {
            if (potionEntry.getResolvedObject() == null) continue;
            arrayList.add(potionEntry);
        }
        return arrayList;
    }

    public static boolean m() {
        boolean bl = PotionRegistry.g();
        return true;
    }

    @Nullable
    public static PotionEntry R(PotionEffect potionEffect) {
        return var_java_util_Map_java_lang_Integer,_gg_vape_wrapper_impl_PotionEntry__V.get(potionEffect.C());
    }

    @Nullable
    public static PotionEntry A(short s) {
        return var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_PotionEntry__b.get(s);
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public static void x(boolean bl) {
        var_boolean_A = bl;
    }

    public static boolean g() {
        return var_boolean_A;
    }

    public static List<PotionEntry> S() {
        return new ArrayList<PotionEntry>(var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_PotionEntry__b.values());
    }

    public static void d() {
        if (var_boolean_F) {
            return;
        }
        var_boolean_F = true;
        for (Field field : PotionRegistry.class.getDeclaredFields()) {
            if (!field.getType().equals(PotionEntry.class)) continue;
            PotionEntry potionEntry = null;
            try {
                potionEntry = (PotionEntry)field.get(null);
                potionEntry.resolve();
            }
            catch (PotionEntryResolveException potionEntryResolveException) {
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            if (potionEntry == null) continue;
            var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_PotionEntry__b.put(potionEntry.getLegacyId(), potionEntry);
            var_java_util_Map_java_lang_Integer,_gg_vape_wrapper_impl_PotionEntry__V.put(potionEntry.getResolvedId(), potionEntry);
        }
    }

    static {
        if (PotionRegistry.g()) {
            PotionRegistry.x(true);
        }
        String[] stringArray = new String[]{"Strength", "Weaving", "Health Boost", "Levitation", "Instant Health", "Absorption", "Glowing", "Dolphin's Grace", "Saturation", "Raid Omen", "Haste", "Mining Fatigue", "Slowness", "Luck", "Bad Omen", "Regeneration", "Infested", "Weakness", "Speed", "Hunger", "Fire Resistance", "Jump Boost", "Conduit Power", "Poison", "Hero of the Village", "Wind Charge", "Bad Luck", "Oozing", "Night Vision", "Slow Falling", "Nausea", "Resistance", "Invisibility", "Trial Omen", "Blindness", "Water Breathing", "Instant Damage", "Wither", "Darkness"};
        var_gg_vape_wrapper_impl_PotionEntry_U = PotionEntry.builder().setName(stringArray[18]).setLegacyId(0).addId(ForgeVersion.MC_1_7_10, 1).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        var_gg_vape_wrapper_impl_PotionEntry_o = PotionEntry.builder().setName(stringArray[12]).setLegacyId(1).addId(ForgeVersion.MC_1_7_10, 2).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        var_gg_vape_wrapper_impl_PotionEntry_t = PotionEntry.builder().setName(stringArray[0]).setLegacyId(2).addId(ForgeVersion.MC_1_7_10, 5).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        var_gg_vape_wrapper_impl_PotionEntry_z = PotionEntry.builder().setName(stringArray[4]).setLegacyId(3).addId(ForgeVersion.MC_1_7_10, 6).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        var_gg_vape_wrapper_impl_PotionEntry_i = PotionEntry.builder().setName(stringArray[15]).setLegacyId(4).addId(ForgeVersion.MC_1_7_10, 10).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        var_gg_vape_wrapper_impl_PotionEntry_P = PotionEntry.builder().setName(stringArray[31]).setLegacyId(5).addId(ForgeVersion.MC_1_7_10, 11).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        var_gg_vape_wrapper_impl_PotionEntry_W = PotionEntry.builder().setName(stringArray[20]).setLegacyId(6).addId(ForgeVersion.MC_1_7_10, 12).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        var_gg_vape_wrapper_impl_PotionEntry_Z = PotionEntry.builder().setName(stringArray[21]).setLegacyId(7).addId(ForgeVersion.MC_1_7_10, 8).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        var_gg_vape_wrapper_impl_PotionEntry_K = PotionEntry.builder().setName(stringArray[34]).setLegacyId(8).addId(ForgeVersion.MC_1_7_10, 15).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        var_gg_vape_wrapper_impl_PotionEntry_E = PotionEntry.builder().setName(stringArray[10]).setLegacyId(9).addId(ForgeVersion.MC_1_7_10, 3).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        var_gg_vape_wrapper_impl_PotionEntry_X = PotionEntry.builder().setName(stringArray[30]).setLegacyId(10).addId(ForgeVersion.MC_1_7_10, 9).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        var_gg_vape_wrapper_impl_PotionEntry_u = PotionEntry.builder().setName(stringArray[11]).setLegacyId(11).addId(ForgeVersion.MC_1_7_10, 4).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        var_gg_vape_wrapper_impl_PotionEntry_B = PotionEntry.builder().setName(stringArray[36]).setLegacyId(12).addId(ForgeVersion.MC_1_7_10, 7).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        var_gg_vape_wrapper_impl_PotionEntry_c = PotionEntry.builder().setName(stringArray[35]).setLegacyId(13).addId(ForgeVersion.MC_1_7_10, 13).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        var_gg_vape_wrapper_impl_PotionEntry_R = PotionEntry.builder().setName(stringArray[32]).setLegacyId(14).addId(ForgeVersion.MC_1_7_10, 14).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        var_gg_vape_wrapper_impl_PotionEntry_T = PotionEntry.builder().setName(stringArray[28]).setLegacyId(15).addId(ForgeVersion.MC_1_7_10, 16).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        var_gg_vape_wrapper_impl_PotionEntry_j = PotionEntry.builder().setName(stringArray[19]).setLegacyId(16).addId(ForgeVersion.MC_1_7_10, 17).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        var_gg_vape_wrapper_impl_PotionEntry_M = PotionEntry.builder().setName(stringArray[17]).setLegacyId(17).addId(ForgeVersion.MC_1_7_10, 18).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        var_gg_vape_wrapper_impl_PotionEntry_r = PotionEntry.builder().setName(stringArray[23]).setLegacyId(18).addId(ForgeVersion.MC_1_7_10, 19).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        var_gg_vape_wrapper_impl_PotionEntry_G = PotionEntry.builder().setName(stringArray[37]).setLegacyId(19).addId(ForgeVersion.MC_1_7_10, 20).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        var_gg_vape_wrapper_impl_PotionEntry_J = PotionEntry.builder().setName(stringArray[2]).setLegacyId(20).addId(ForgeVersion.MC_1_7_10, 21).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        var_gg_vape_wrapper_impl_PotionEntry_d = PotionEntry.builder().setName(stringArray[5]).setLegacyId(21).addId(ForgeVersion.MC_1_7_10, 22).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        var_gg_vape_wrapper_impl_PotionEntry_Q = PotionEntry.builder().setName(stringArray[8]).setLegacyId(22).addId(ForgeVersion.MC_1_7_10, 23).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        var_gg_vape_wrapper_impl_PotionEntry_v = PotionEntry.builder().setName(stringArray[6]).setLegacyId(23).addId(ForgeVersion.MC_1_12_2, 24).setSupportedVersion(ForgeVersion.MC_1_12_2).build();
        var_gg_vape_wrapper_impl_PotionEntry_h = PotionEntry.builder().setName(stringArray[3]).setLegacyId(24).addId(ForgeVersion.MC_1_12_2, 25).setSupportedVersion(ForgeVersion.MC_1_12_2).build();
        var_gg_vape_wrapper_impl_PotionEntry_L = PotionEntry.builder().setName(stringArray[13]).setLegacyId(25).addId(ForgeVersion.MC_1_12_2, 26).setSupportedVersion(ForgeVersion.MC_1_12_2).build();
        var_gg_vape_wrapper_impl_PotionEntry_C = PotionEntry.builder().setName(stringArray[26]).setLegacyId(26).addId(ForgeVersion.MC_1_12_2, 27).setSupportedVersion(ForgeVersion.MC_1_12_2).build();
        var_gg_vape_wrapper_impl_PotionEntry_k = PotionEntry.builder().setName(stringArray[29]).setLegacyId(27).addId(ForgeVersion.MC_1_16_5, 28).setSupportedVersion(ForgeVersion.MC_1_16_5).build();
        var_gg_vape_wrapper_impl_PotionEntry_f = PotionEntry.builder().setName(stringArray[22]).setLegacyId(28).addId(ForgeVersion.MC_1_16_5, 29).setSupportedVersion(ForgeVersion.MC_1_16_5).build();
        var_gg_vape_wrapper_impl_PotionEntry_H = PotionEntry.builder().setName(stringArray[7]).setLegacyId(29).addId(ForgeVersion.MC_1_16_5, 30).setSupportedVersion(ForgeVersion.MC_1_16_5).build();
        var_gg_vape_wrapper_impl_PotionEntry_a = PotionEntry.builder().setName(stringArray[14]).setLegacyId(30).addId(ForgeVersion.MC_1_16_5, 31).setSupportedVersion(ForgeVersion.MC_1_16_5).build();
        var_gg_vape_wrapper_impl_PotionEntry_y = PotionEntry.builder().setName(stringArray[24]).setLegacyId(31).addId(ForgeVersion.MC_1_16_5, 32).setSupportedVersion(ForgeVersion.MC_1_16_5).build();
        var_gg_vape_wrapper_impl_PotionEntry_S = PotionEntry.builder().setName(stringArray[38]).setLegacyId(32).setSupportedVersion(ForgeVersion.MC_1_21_0).build();
        var_gg_vape_wrapper_impl_PotionEntry_x = PotionEntry.builder().setName(stringArray[33]).setLegacyId(33).setSupportedVersion(ForgeVersion.MC_1_21_0).build();
        var_gg_vape_wrapper_impl_PotionEntry_w = PotionEntry.builder().setName(stringArray[9]).setLegacyId(34).setSupportedVersion(ForgeVersion.MC_1_21_0).build();
        var_gg_vape_wrapper_impl_PotionEntry_N = PotionEntry.builder().setName(stringArray[25]).setLegacyId(35).setSupportedVersion(ForgeVersion.MC_1_21_0).build();
        var_gg_vape_wrapper_impl_PotionEntry_l = PotionEntry.builder().setName(stringArray[1]).setLegacyId(36).setSupportedVersion(ForgeVersion.MC_1_21_0).build();
        var_gg_vape_wrapper_impl_PotionEntry_e = PotionEntry.builder().setName(stringArray[27]).setLegacyId(37).setSupportedVersion(ForgeVersion.MC_1_21_0).build();
        var_gg_vape_wrapper_impl_PotionEntry_O = PotionEntry.builder().setName(stringArray[16]).setLegacyId(38).setSupportedVersion(ForgeVersion.MC_1_21_0).build();
        var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_PotionEntry__b = new LinkedHashMap<Short, PotionEntry>();
        var_java_util_Map_java_lang_Integer,_gg_vape_wrapper_impl_PotionEntry__V = new LinkedHashMap<Integer, PotionEntry>();
        var_boolean_F = false;
    }
}

