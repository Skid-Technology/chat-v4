/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.font;

import gg.vape.Vape;
import gg.vape.ui.font.BaseFontOption;
import gg.vape.ui.font.FontFamily;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.unmap.ModeSelection;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Properties;

public class FontOption
extends ModeSelection {
    private String var_java_lang_String_j;
    private String var_java_lang_String_C;
    private Properties var_java_util_Properties_g;
    private HashMap<String, String> var_java_util_HashMap_java_lang_String,_java_lang_String__N = new HashMap();

    public SmoothFontRenderer k(float f, boolean bl) {
        return Vape.INSTANCE.getFontManager().b(this.b(), f, bl);
    }

    public void g(BaseFontOption baseFontOption) {
        for (Object object : this.var_java_util_Properties_g.keySet()) {
            String string = (String)object;
            if (string.startsWith("language.")) continue;
            String string2 = baseFontOption.p().getProperty(string);
            String string3 = this.var_java_util_Properties_g.getProperty(string);
            if (string3 == null || string3.length() <= 0) continue;
            this.j(string2, string3);
        }
    }

    public Properties p() {
        return this.var_java_util_Properties_g;
    }

    @Override
    public String toString() {
        return this.var_java_lang_String_C;
    }

    @Override
    public String getName() {
        return this.var_java_lang_String_C;
    }

    public SmoothFontRenderer w(float f) {
        return this.k(f, false);
    }

    @Override
    public String getSerializedName() {
        return this.var_java_lang_String_j;
    }

    public FontOption(String string) {
        super("");
        this.var_java_util_Properties_g = new Properties();
        this.var_java_lang_String_j = string;
        this.p$src$V$169egi9();
    }

    private void p$src$V$169egi9() {
        String string = this.var_java_lang_String_j.toLowerCase() + ".properties.txt";
        byte[] byArray = Vape.readResource(string);
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byArray);
            this.var_java_util_Properties_g.load(new InputStreamReader((InputStream)byteArrayInputStream, StandardCharsets.UTF_8));
            this.var_java_lang_String_C = this.var_java_util_Properties_g.getProperty("language.native");
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
        }
    }

    public void j(String string, String string2) {
        this.var_java_util_HashMap_java_lang_String,_java_lang_String__N.put(string, string2);
    }

    public String s(String string) {
        return this.var_java_util_HashMap_java_lang_String,_java_lang_String__N.getOrDefault(string, string);
    }

    public FontFamily b() {
        return FontFamily.PROXIMA;
    }
}

