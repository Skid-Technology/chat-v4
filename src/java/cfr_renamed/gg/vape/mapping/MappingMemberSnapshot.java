/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;

public class MappingMemberSnapshot {
    private Class[] var_java_lang_Class_arr_t;
    private Class var_java_lang_Class_g;
    private Class var_java_lang_Class_I;
    private boolean var_boolean_J;
    private String var_java_lang_String_Z;
    private Mapping var_gg_vape_mapping_Mapping_c;
    private Class[] var_java_lang_Class_arr_n;
    private String var_java_lang_String_b;
    private Class var_java_lang_Class_l;

    public String a() {
        return MappedClasses.b(this.var_java_lang_Class_I);
    }

    public String P() {
        if (this.var_java_lang_Class_g == null) {
            return "null";
        }
        if (this.var_java_lang_Class_arr_t == null) {
            return MappedClasses.b(this.var_java_lang_Class_g);
        }
        StringBuilder stringBuilder = new StringBuilder("(");
        for (Class clazz : this.var_java_lang_Class_arr_t) {
            String string = MappedClasses.b(clazz);
            stringBuilder.append(string).append(", ");
        }
        if (stringBuilder.length() > 1) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        }
        stringBuilder.append(")").append(MappedClasses.b(this.var_java_lang_Class_g));
        return stringBuilder.toString();
    }

    public String p() {
        if (this.var_java_lang_Class_l == null) {
            return "null";
        }
        if (this.var_java_lang_Class_arr_n == null) {
            return MappedClasses.b(this.var_java_lang_Class_l);
        }
        StringBuilder stringBuilder = new StringBuilder("(");
        for (Class clazz : this.var_java_lang_Class_arr_n) {
            String string = MappedClasses.b(clazz);
            stringBuilder.append(string).append(", ");
        }
        if (stringBuilder.length() > 1) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        }
        stringBuilder.append(")").append(MappedClasses.b(this.var_java_lang_Class_l));
        return stringBuilder.toString();
    }

    public MappingMemberSnapshot(Mapping mapping, Class clazz, String string, Class clazz2, Class[] classArray, String string2, Class clazz3, Class[] classArray2, boolean bl) {
        this.var_gg_vape_mapping_Mapping_c = mapping;
        this.var_java_lang_Class_I = clazz;
        this.var_java_lang_String_Z = string;
        this.var_java_lang_Class_g = clazz2;
        this.var_java_lang_Class_arr_t = classArray;
        this.var_java_lang_String_b = string2;
        this.var_java_lang_Class_l = clazz3;
        this.var_java_lang_Class_arr_n = classArray2;
        this.var_boolean_J = bl;
    }

    public String A() {
        StringBuilder stringBuilder = new StringBuilder("(");
        for (Class clazz : this.var_java_lang_Class_arr_t) {
            String string = clazz.getName();
            stringBuilder.append(string).append(", ");
        }
        if (stringBuilder.length() > 1) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        }
        stringBuilder.append(")").append(this.var_java_lang_Class_g.getName());
        return stringBuilder.toString();
    }
}

