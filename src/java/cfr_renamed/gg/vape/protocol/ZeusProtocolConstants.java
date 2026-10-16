/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol;

import gg.vape.protocol.ZeusProtocolState;
import io.netty.util.AttributeKey;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;

public class ZeusProtocolConstants {
    final public static int var_int_z;
    final public static Pattern var_java_util_regex_Pattern_y;
    final public static int var_int_e;
    public static AttributeKey<ZeusProtocolState> var_io_netty_util_AttributeKey_gg_vape_protocol_ZeusProtocolState__Q;
    final public static int var_int_C;
    final public static int var_int_s;
    final public static int var_int_U;
    final public static int var_int_a;

    private static AttributeKey<ZeusProtocolState> E(String string) {
        try {
            Constructor constructor = AttributeKey.class.getDeclaredConstructor(String.class);
            return (AttributeKey)constructor.newInstance(string);
        }
        catch (NoSuchMethodException constructor) {
        }
        catch (IllegalAccessException | InstantiationException | InvocationTargetException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
        return AttributeKey.valueOf(string);
    }

    static {
        String[] stringArray = new String[]{"^[a-zA-Z\\d_.-]*$", "connection_state"};
        long[] lArray = new long[]{-1775853824527826689L, 5551149997770145808L, 5142080514882011146L, -1319690179857874936L, -694266774610247552L};
        var_int_C = (int)lArray[3];
        var_int_a = (int)lArray[2];
        var_int_z = (int)lArray[1];
        var_int_s = (int)lArray[4];
        var_int_e = (int)lArray[0];
        var_int_U = (int)lArray[3];
        var_java_util_regex_Pattern_y = Pattern.compile(stringArray[0]);
        var_io_netty_util_AttributeKey_gg_vape_protocol_ZeusProtocolState__Q = ZeusProtocolConstants.E(stringArray[1]);
    }
}

