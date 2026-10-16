/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.asm.helper.DescUtils;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.Queue;

public class MNetworkManager
extends Mapping {
    private final MappingMethod var_gg_vape_mapping_MappingMethod_M;
    private final MappingField var_gg_vape_mapping_MappingField_Y;
    private MappingField var_gg_vape_mapping_MappingField_C;
    public MappingMethod var_gg_vape_mapping_MappingMethod_a;
    public MappingMethod var_gg_vape_mapping_MappingMethod_O;
    private static String[] var_java_lang_String_arr_r;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_p;
    private final MappingField channelField;

    public static Object getChannel(MNetworkManager mapping, Object networkManager) {
        return mapping.getChannel(networkManager);
    }

    public static void B(MNetworkManager mNetworkManager, Object object, Object object2) {
        mNetworkManager.r(object, object2);
    }

    private Queue E(Object object) {
        return (Queue)this.var_gg_vape_mapping_MappingField_C.getObject(object);
    }

    private Queue z(Object object) {
        return (Queue)this.var_gg_vape_mapping_MappingField_Y.getObject(object);
    }

    private Object getChannel(Object networkManager) {
        return this.channelField.getObject(networkManager);
    }

    private void U(Object object) {
        this.var_gg_vape_mapping_MappingMethod_p.invokeVoidNoArgs(object);
    }

    private Object q(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_M.invokeObject(object, new Object[0]);
    }

    private void i(Object object, Object object2, Object object3, boolean bl) {
        this.var_gg_vape_mapping_MappingMethod_O.invokeVoid(object, object2, object3, bl);
    }

    public MNetworkManager() {
        super(MappedClasses.FO);
        Class[] classArray;
        Class clazz = MappedClasses.q8;
        boolean bl = true;
        String string = "channel";
        MNetworkManager mNetworkManager = this;
        this.channelField = this.J(string, bl, clazz);
        Class<Queue> clazz2 = Queue.class;
        boolean bl2 = true;
        String string2 = "outboundPacketsQueue";
        MNetworkManager mNetworkManager2 = this;
        this.var_gg_vape_mapping_MappingField_Y = this.J(string2, bl2, clazz2);
        String[] stringArray = MNetworkManager.Q();
        if (ForgeVersion.MC_1_7_10.L()) {
            Class[] classArray2;
            Class<Queue> clazz3 = Queue.class;
            boolean bl3 = true;
            String string3 = "receivedPacketsQueue";
            MNetworkManager mNetworkManager3 = this;
            this.var_gg_vape_mapping_MappingField_C = this.J(string3, bl3, clazz3);
            if (Wrapper.vapeInstance.isVanillaMinecraftPresent()) {
                classArray2 = new Class[]{MappedClasses.Fm, DescUtils.getArrayType(MappedClasses.ZO)};
                Class<Void> clazz4 = Void.TYPE;
                boolean bl4 = true;
                String string4 = "scheduleOutboundPacket";
                MNetworkManager mNetworkManager4 = this;
                this.var_gg_vape_mapping_MappingMethod_O = this.Y(string4, bl4, clazz4, classArray2);
            } else {
                classArray2 = new Class[]{MappedClasses.Fm, DescUtils.getArrayType(MappedClasses.ZO)};
                Class<Void> clazz5 = Void.TYPE;
                boolean bl5 = Wrapper.isNativeAvailable;
                String string5 = "func_150725_a";
                MNetworkManager mNetworkManager5 = this;
                this.var_gg_vape_mapping_MappingMethod_O = this.Y(string5, bl5, clazz5, classArray2);
            }
        } else if (ForgeVersion.MC_1_20_6.d()) {
            classArray = new Class[]{MappedClasses.Fm, MappedClasses.var_java_lang_Class_H, Boolean.TYPE};
            Class<Void> clazz6 = Void.TYPE;
            boolean bl6 = true;
            String string6 = "sendPacket";
            MNetworkManager mNetworkManager6 = this;
            this.var_gg_vape_mapping_MappingMethod_O = this.Y(string6, bl6, clazz6, classArray);
        } else if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            classArray = new Class[]{MappedClasses.Fm};
            Class<Void> clazz7 = Void.TYPE;
            boolean bl7 = true;
            String string7 = "sendPacket";
            MNetworkManager mNetworkManager7 = this;
            this.var_gg_vape_mapping_MappingMethod_O = this.Y(string7, bl7, clazz7, classArray);
        } else {
            classArray = new Class[]{MappedClasses.Fm};
            Class<Void> clazz8 = Void.TYPE;
            boolean bl8 = Wrapper.isNativeAvailable;
            String string8 = "func_179290_a";
            MNetworkManager mNetworkManager8 = this;
            this.var_gg_vape_mapping_MappingMethod_O = this.Y(string8, bl8, clazz8, classArray);
        }
        classArray = new Class[]{};
        Class clazz9 = MappedClasses.Yy;
        boolean bl9 = true;
        String string9 = "getNetHandler";
        MNetworkManager mNetworkManager9 = this;
        this.var_gg_vape_mapping_MappingMethod_M = this.Y(string9, bl9, clazz9, classArray);
        Class[] classArray2 = new Class[]{};
        Class<Void> clazz10 = Void.TYPE;
        boolean bl10 = true;
        String string10 = "flushOutboundQueue";
        MNetworkManager mNetworkManager10 = this;
        this.var_gg_vape_mapping_MappingMethod_p = this.Y(string10, bl10, clazz10, classArray2);
        if (ForgeVersion.MC_26_1.d()) {
            Class[] classArray3 = new Class[]{MappedClasses.FH, MappedClasses.Fm};
            Class<Void> clazz11 = Void.TYPE;
            boolean bl11 = true;
            String string11 = "channelRead0";
            MNetworkManager mNetworkManager11 = this;
            this.var_gg_vape_mapping_MappingMethod_a = this.Y(string11, bl11, clazz11, classArray3);
        } else if (Vape.INSTANCE.isVanillaMinecraftPresent() || Vape.INSTANCE.isFabricMinecraftPresent()) {
            Class[] classArray4 = new Class[]{MappedClasses.FH, MappedClasses.Fm};
            Class<Void> clazz12 = Void.TYPE;
            boolean bl12 = true;
            String string12 = "channelRead0";
            MNetworkManager mNetworkManager12 = this;
            this.var_gg_vape_mapping_MappingMethod_a = this.Y(string12, bl12, clazz12, classArray4);
        } else {
            Class[] classArray5 = new Class[]{MappedClasses.FH, MappedClasses.Fm};
            Class<Void> clazz13 = Void.TYPE;
            boolean bl13 = false;
            String string13 = Wrapper.isNativeAvailable ? "a" : "channelRead0";
            MNetworkManager mNetworkManager13 = this;
            this.var_gg_vape_mapping_MappingMethod_a = this.Y(string13, bl13, clazz13, classArray5);
        }
        if (this.var_gg_vape_mapping_MappingMethod_a.hasResolutionFailed() && Wrapper.isNativeAvailable && ForgeVersion.MC_26_1.v()) {
            Class[] classArray6 = new Class[]{MappedClasses.FH, MappedClasses.Fm};
            Class<Void> clazz14 = Void.TYPE;
            boolean bl14 = true;
            String string14 = "channelRead0";
            MNetworkManager mNetworkManager14 = this;
            this.var_gg_vape_mapping_MappingMethod_a = this.Y(string14, bl14, clazz14, classArray6);
        }
    }

    public static void Z(String[] stringArray) {
        var_java_lang_String_arr_r = stringArray;
    }

    public static void m(MNetworkManager mNetworkManager, Object object, Object object2, Object object3, boolean bl) {
        mNetworkManager.i(object, object2, object3, bl);
    }

    private void r(Object object, Object object2) {
        this.var_gg_vape_mapping_MappingMethod_O.invokeVoid(object, object2);
    }

    public static Object U(MNetworkManager mNetworkManager, Object object) {
        return mNetworkManager.q(object);
    }

    public static String[] Q() {
        return var_java_lang_String_arr_r;
    }

    static {
        MNetworkManager.Z(new String[3]);
    }
}

