/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.ui.unmap;

import com.google.gson.JsonObject;
import gg.vape.Vape;
import gg.vape.utils.Base64Util;
import gg.vape.utils.BlockUtil;
import gg.vape.utils.MutableColor;
import java.awt.Color;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import org.jetbrains.annotations.Nullable;

public class SearchBlock {
    private final AtomicBoolean var_java_util_concurrent_atomic_AtomicBoolean_D = new AtomicBoolean(true);
    private MutableColor var_gg_vape_utils_MutableColor_Y;
    private final AtomicBoolean var_java_util_concurrent_atomic_AtomicBoolean_l = new AtomicBoolean(false);
    private String var_java_lang_String_P;
    private int var_int_K = -1;
    private static int[] var_int_arr_N;
    @Nullable
    private Predicate<Character> var_java_util_function_Predicate_java_lang_Character__R;
    private int var_int_H;

    public JsonObject I() {
        JsonObject jsonObject = new JsonObject();
        String string = "b64:" + Base64Util.encodeUtf8Base64(this.var_java_lang_String_P);
        jsonObject.addProperty("blockname", string);
        jsonObject.addProperty("color2", this.var_gg_vape_utils_MutableColor_Y.l());
        jsonObject.addProperty("enabled", this.var_java_util_concurrent_atomic_AtomicBoolean_D.get());
        jsonObject.addProperty("tracersEnabled", this.var_java_util_concurrent_atomic_AtomicBoolean_l.get());
        return jsonObject;
    }

    private void o(String string) {
        try {
            this.var_int_K = Integer.parseInt(string);
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
    }

    public void M(boolean bl) {
        this.var_java_util_concurrent_atomic_AtomicBoolean_D.set(bl);
    }

    public void H(boolean bl) {
        this.var_java_util_concurrent_atomic_AtomicBoolean_l.set(bl);
    }

    public Color B() {
        return this.var_gg_vape_utils_MutableColor_Y;
    }

    public boolean b(int n, int n2) {
        if (!this.var_java_util_concurrent_atomic_AtomicBoolean_D.get()) {
            return false;
        }
        if (this.var_int_H == -1) {
            return false;
        }
        if (this.var_java_util_function_Predicate_java_lang_Character__R != null) {
            return this.var_java_util_function_Predicate_java_lang_Character__R.test(Character.valueOf(BlockUtil.t(n, n2)));
        }
        return this.var_int_H == n && (this.var_int_K == -1 || this.var_int_K == n2 || n2 == -1);
    }

    @Nullable
    public Predicate<Character> E() {
        return this.var_java_util_function_Predicate_java_lang_Character__R;
    }

    private void X() {
        String string = this.var_java_lang_String_P;
        Predicate<Character> predicate = Vape.INSTANCE.getItemHelper().findCharacterRule(string);
        if (predicate != null) {
            this.var_java_util_function_Predicate_java_lang_Character__R = predicate;
            return;
        }
        if (string.contains(":")) {
            String[] stringArray = string.split(":");
            if (stringArray.length == 2) {
                this.b(stringArray[0]);
                this.o(stringArray[1]);
            }
        } else {
            this.b(string);
        }
    }

    public void c(int n) {
        this.var_gg_vape_utils_MutableColor_Y = new MutableColor(n);
    }

    public int M() {
        return this.var_int_H;
    }

    private void b(String string) {
        int n = 0;
        int n2 = -1;
        try {
            n = Integer.parseInt(string);
        }
        catch (NumberFormatException numberFormatException) {
            int[] nArray = BlockUtil.r(string);
            n = nArray[0];
            n2 = nArray[1];
        }
        this.var_int_H = n;
        this.var_int_K = n2;
    }

    public AtomicBoolean I$src$Ljava_util_concurrent_atomic_AtomicBoolean_$10pq3bz() {
        return this.var_java_util_concurrent_atomic_AtomicBoolean_D;
    }

    public static void m(int[] nArray) {
        var_int_arr_N = nArray;
    }

    public SearchBlock(String string, int n) {
        this.var_gg_vape_utils_MutableColor_Y = new MutableColor(n);
        this.var_java_lang_String_P = string;
        this.X();
    }

    public String d() {
        return this.var_java_lang_String_P;
    }

    public boolean T() {
        return this.var_java_util_concurrent_atomic_AtomicBoolean_D.get();
    }

    public static int[] J() {
        return var_int_arr_N;
    }

    public AtomicBoolean x() {
        return this.var_java_util_concurrent_atomic_AtomicBoolean_l;
    }

    public int i() {
        return this.var_int_K;
    }

    public boolean W() {
        return this.var_java_util_concurrent_atomic_AtomicBoolean_l.get();
    }

    public SearchBlock(JsonObject jsonObject) {
        if (jsonObject.get("blockname") != null && !jsonObject.get("blockname").isJsonNull()) {
            this.var_java_lang_String_P = jsonObject.get("blockname").getAsString();
            if (this.var_java_lang_String_P.startsWith("b64:")) {
                this.var_java_lang_String_P = Base64Util.decodeUtf8Base64(this.var_java_lang_String_P.split(":")[1]);
            }
            this.X();
        }
        if (jsonObject.get("color") != null && !jsonObject.get("color").isJsonNull()) {
            this.var_gg_vape_utils_MutableColor_Y = new MutableColor(jsonObject.get("color").getAsInt());
        }
        if (jsonObject.get("color2") != null && !jsonObject.get("color2").isJsonNull()) {
            Color color = new Color(jsonObject.get("color2").getAsInt());
            this.var_gg_vape_utils_MutableColor_Y = new MutableColor(color);
        }
        if (jsonObject.get("enabled") != null && !jsonObject.get("enabled").isJsonNull()) {
            this.var_java_util_concurrent_atomic_AtomicBoolean_D.set(jsonObject.get("enabled").getAsBoolean());
        }
        if (jsonObject.get("tracersEnabled") != null && !jsonObject.get("tracersEnabled").isJsonNull()) {
            this.var_java_util_concurrent_atomic_AtomicBoolean_l.set(jsonObject.get("tracersEnabled").getAsBoolean());
        }
    }

    private static NumberFormatException a(NumberFormatException numberFormatException) {
        return numberFormatException;
    }

    public JsonObject com_google_gson_JsonObject_I() {
        return this.I();
    }

    static {
        SearchBlock.m(null);
    }
}

