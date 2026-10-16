/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.UUID;

public class MGameProfile
extends Mapping {
    private final MappingField A;
    private static String[] W;
    private MappingMethod u;
    private final MappingMethod e;
    private MappingField U;

    public static String[] I() {
        return W;
    }

    private Object W(UUID uUID, String string) {
        return this.e.newInstance(uUID, string);
    }

    public static void n(String[] stringArray) {
        W = stringArray;
    }

    private UUID j(Object object) {
        if (ForgeVersion.MC_1_21_10.d()) {
            return (UUID)this.U.getObject(object);
        }
        return (UUID)this.u.invokeObject(object, new Object[0]);
    }

    private String K(Object object) {
        return (String)this.A.getObject(object);
    }

    public static String P(MGameProfile mGameProfile, Object object) {
        return mGameProfile.K(object);
    }

    public static Object N(MGameProfile mGameProfile, UUID uUID, String string) {
        return mGameProfile.W(uUID, string);
    }

    public static UUID h(MGameProfile mGameProfile, Object object) {
        return mGameProfile.j(object);
    }

    public MGameProfile() {
        this(MGameProfile.I());
    }

    private MGameProfile(String[] stringArray) {
        super(MappedClasses.VD);
        MGameProfile mGameProfile;
        String string;
        boolean bl;
        Class clazz;
        String[] stringArray2 = stringArray;
        if (ForgeVersion.MC_1_21_10.d()) {
            clazz = UUID.class;
            bl = false;
            string = "id";
            mGameProfile = this;
            this.U = mGameProfile.J(string, bl, clazz);
        } else {
            Class[] classArray = new Class[]{};
            Class<UUID> clazz2 = UUID.class;
            boolean bl2 = false;
            String string2 = "getId";
            MGameProfile mGameProfile2 = this;
            this.u = mGameProfile2.Y(string2, bl2, clazz2, classArray);
        }
        clazz = String.class;
        bl = false;
        string = "name";
        mGameProfile = this;
        this.A = mGameProfile.J(string, bl, clazz);
        Class[] classArray = new Class[]{UUID.class, String.class};
        Class<Void> clazz2 = Void.TYPE;
        boolean bl2 = false;
        String string2 = "<init>";
        MGameProfile mGameProfile2 = this;
        this.e = this.Y(string2, bl2, clazz2, classArray);
    }

    public static MappingField z(MGameProfile mGameProfile) {
        return mGameProfile.A;
    }

    static {
        MGameProfile.n(new String[3]);
    }
}

