/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MNetworkManager;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.Collection;

public class MNetHandlerPlayClientImpl
extends Mapping {
    private MappingField var_gg_vape_mapping_MappingField_p;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_F;
    private MappingMethod var_gg_vape_mapping_MappingMethod_r;
    private final MappingField var_gg_vape_mapping_MappingField_u;
    private MappingMethod var_gg_vape_mapping_MappingMethod_h;

    public static boolean X(MNetHandlerPlayClientImpl mNetHandlerPlayClientImpl, Object object) {
        return mNetHandlerPlayClientImpl.i(object);
    }

    private Object h(Object object) {
        return this.var_gg_vape_mapping_MappingField_u.getObject(object);
    }

    private void Q(Object object, Object object2) {
        this.var_gg_vape_mapping_MappingMethod_F.invokeVoid(object, object2);
    }

    public MNetHandlerPlayClientImpl() {
        this(MNetworkManager.Q());
    }

    private MNetHandlerPlayClientImpl(String[] stringArray) {
        super(MappedClasses.F1);
        String string;
        boolean bl;
        Class<Object> clazz;
        Class[] classArray;
        MNetHandlerPlayClientImpl mNetHandlerPlayClientImpl;
        MNetHandlerPlayClientImpl mNetHandlerPlayClientImpl2;
        String string2;
        boolean bl2;
        Class clazz2;
        String[] stringArray2 = stringArray;
        if (ForgeVersion.MC_1_20_6.v()) {
            clazz2 = Boolean.TYPE;
            bl2 = true;
            string2 = "doneLoadingTerrain";
            mNetHandlerPlayClientImpl2 = this;
            this.var_gg_vape_mapping_MappingField_p = mNetHandlerPlayClientImpl2.J(string2, bl2, clazz2);
        }
        if (ForgeVersion.MC_1_20_6.d()) {
            clazz2 = MappedClasses.FO;
            bl2 = true;
            string2 = "connection";
            Class clazz22 = MappedClasses.Yt;
            mNetHandlerPlayClientImpl = this;
            this.var_gg_vape_mapping_MappingField_u = mNetHandlerPlayClientImpl.registerInstanceFieldForOwner(clazz22, string2, bl2, clazz2);
        } else {
            clazz2 = MappedClasses.FO;
            bl2 = true;
            string2 = "netManager";
            mNetHandlerPlayClientImpl2 = this;
            this.var_gg_vape_mapping_MappingField_u = mNetHandlerPlayClientImpl2.J(string2, bl2, clazz2);
        }
        if (ForgeVersion.MC_1_20_6.d()) {
            classArray = new Class[]{MappedClasses.Fm};
            clazz = Void.TYPE;
            bl = true;
            string = "send";
            Class clazz3 = MappedClasses.Yt;
            MNetHandlerPlayClientImpl mNetHandlerPlayClientImpl3 = this;
            this.var_gg_vape_mapping_MappingMethod_F = mNetHandlerPlayClientImpl3.registerInstanceMethodForOwner(clazz3, string, bl, clazz, classArray);
        } else if (ForgeVersion.MC_1_12_2.d()) {
            classArray = new Class[]{MappedClasses.Fm};
            clazz = Void.TYPE;
            bl = true;
            string = "sendPacket";
            mNetHandlerPlayClientImpl = this;
            this.var_gg_vape_mapping_MappingMethod_F = mNetHandlerPlayClientImpl.Y(string, bl, clazz, classArray);
        } else {
            classArray = new Class[]{MappedClasses.Fm};
            clazz = Void.TYPE;
            bl = true;
            string = "addToSendQueue";
            mNetHandlerPlayClientImpl = this;
            this.var_gg_vape_mapping_MappingMethod_F = mNetHandlerPlayClientImpl.Y(string, bl, clazz, classArray);
        }
        if (ForgeVersion.MC_1_7_10.Y()) {
            classArray = new Class[]{};
            clazz = Collection.class;
            bl = true;
            string = "getPlayerInfoMap";
            mNetHandlerPlayClientImpl = this;
            this.var_gg_vape_mapping_MappingMethod_h = mNetHandlerPlayClientImpl.Y(string, bl, clazz, classArray);
        }
        if (ForgeVersion.MC_1_21_11.d()) {
            classArray = new Class[]{};
            clazz = Boolean.TYPE;
            String string3 = "hasClientLoaded";
            mNetHandlerPlayClientImpl2 = this;
            this.var_gg_vape_mapping_MappingMethod_r = mNetHandlerPlayClientImpl2.methodBuilder(string3, clazz, classArray).buildMethod();
        }
        if (GuiComponent.getLegacyComponentState() == null) {
            MNetworkManager.Z(new String[2]);
        }
    }

    public static Collection H(MNetHandlerPlayClientImpl mNetHandlerPlayClientImpl, Object object) {
        return mNetHandlerPlayClientImpl.N(object);
    }

    public static boolean d(MNetHandlerPlayClientImpl mNetHandlerPlayClientImpl, Object object) {
        return mNetHandlerPlayClientImpl.K(object);
    }

    public static void I(MNetHandlerPlayClientImpl mNetHandlerPlayClientImpl, Object object, Object object2) {
        mNetHandlerPlayClientImpl.Q(object, object2);
    }

    private Collection N(Object object) {
        return (Collection)this.var_gg_vape_mapping_MappingMethod_h.invokeObject(object, new Object[0]);
    }

    private boolean i(Object object) {
        return this.var_gg_vape_mapping_MappingField_p.getBoolean(object);
    }

    private boolean K(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_r.invokeBoolean(object, new Object[0]);
    }

    public static Object z(MNetHandlerPlayClientImpl mNetHandlerPlayClientImpl, Object object) {
        return mNetHandlerPlayClientImpl.h(object);
    }
}

