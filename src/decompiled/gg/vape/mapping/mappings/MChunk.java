/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingFieldBuilder;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.MappingMethodBuilder;
import gg.vape.mapping.mappings.MTickingBlockEntity;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class MChunk
extends Mapping {
    private MappingField k;
    private MappingMethod c;
    private MappingField O;
    private final MappingField p;
    private MappingField r;
    private MappingMethod N;
    private MappingField q;
    private MappingMethod o;

    public int M(Object object) {
        return this.k.getInt(object);
    }

    public int A(Object object) {
        return this.O.getInt(object);
    }

    public Object[] D(Object object) {
        return this.r.getObjectArray(object);
    }

    public Object N(Object object, Object object2, Object object3) {
        return this.N.invokeObject(object, object2, object3);
    }

    public boolean c(Object object) {
        return this.p.getBoolean(object);
    }

    public Object D(Object object, int n, int n2, Object object2) {
        return this.N.invokeObject(object, n, n2, object2);
    }

    public Object j(Object object) {
        return this.q.getObject(object);
    }

    public int I(Object object, int n) {
        return this.o.invokeInt(object, n);
    }

    public MChunk() {
        this(MTickingBlockEntity.getTickingBlockEntityControlFlowState());
    }

    private MChunk(int[] nArray) {
        super(MappedClasses.VJ);
        String string;
        String string2;
        MChunk mChunk;
        String string3;
        Class<Object> clazz;
        Class[] classArray;
        MChunk mChunk2;
        Class<Boolean> clazz2;
        int[] nArray2 = nArray;
        if (ForgeVersion.MC_1_8_9.L()) {
            clazz2 = Integer.TYPE;
            boolean bl = true;
            String string4 = "xPosition";
            mChunk2 = this;
            this.k = mChunk2.J(string4, bl, clazz2);
            Class<Integer> clazz22 = Integer.TYPE;
            boolean bl2 = true;
            String string22 = "zPosition";
            MChunk mChunk22 = this;
            this.O = this.J(string22, bl2, clazz22);
        }
        if (ForgeVersion.MC_1_7_10.L()) {
            classArray = new Class[]{Integer.TYPE, Integer.TYPE, MappedClasses.FU};
            clazz = MappedClasses.uK;
            boolean bl = true;
            string3 = "getBiomeGenForWorldCoords";
            mChunk = this;
            this.N = mChunk.Y(string3, bl, clazz, classArray);
        } else {
            if (ForgeVersion.MC_1_16_5.v()) {
                classArray = new Class[]{MappedClasses.lf, MappedClasses.FU};
                Class clazz3 = MappedClasses.uK;
                boolean bl = true;
                string3 = "getBiome";
                mChunk = this;
                this.N = mChunk.Y(string3, bl, clazz3, classArray);
            }
            clazz2 = MappedClasses.F3;
            string2 = "sections";
            MChunk mChunk3 = this;
            this.r = ((MappingFieldBuilder)((MappingFieldBuilder)mChunk3.fieldBuilder(string2, clazz2).setOwnerClassForVersion(ForgeVersion.MC_1_20_6.n(), MappedClasses.V)).setNameForVersion(ForgeVersion.MC_1_16_5.b(), "storageArrays")).buildField();
        }
        if (ForgeVersion.MC_1_20_6.v()) {
            classArray = new Class[]{MappedClasses.zc, MappedClasses.uk, List.class, MappedClasses.lH};
            clazz = Void.TYPE;
            string = "getEntitiesWithinAABBForEntity";
            mChunk2 = this;
            this.c = ((MappingMethodBuilder)mChunk2.methodBuilder(string, clazz, classArray).setParameterTypesForVersion(ForgeVersion.MC_1_7_10.S(), MappedClasses.uk, List.class, MappedClasses.Zt).setParameterTypesForVersion(ForgeVersion.MC_1_16_5.n(), MappedClasses.zc, MappedClasses.uk, List.class, Predicate.class).setMappedMemberForVersion(ForgeVersion.MC_1_16_5.n(), Wrapper.isNativeAvailable)).buildMethod();
        } else {
            classArray = new Class[]{Integer.TYPE};
            clazz = Integer.TYPE;
            string = "getSectionIndex";
            mChunk2 = this;
            this.o = ((MappingMethodBuilder)mChunk2.methodBuilder(string, clazz, classArray).setOwnerClass(MappedClasses.VS)).buildMethod();
        }
        clazz2 = Boolean.TYPE;
        string2 = ForgeVersion.c() >= 23 ? "loaded" : "isChunkLoaded";
        MChunk mChunk4 = this;
        this.p = mChunk4.fieldBuilder(string2, clazz2).buildField();
        if (ForgeVersion.MC_1_17.d()) {
            Class<Map> clazz3 = Map.class;
            String string32 = "blockEntities";
            MChunk mChunk3 = this;
            this.q = ((MappingFieldBuilder)this.fieldBuilder(string32, clazz3).setOwnerClassForVersion(ForgeVersion.MC_1_20_6.n(), MappedClasses.V)).buildField();
        }
    }

    public void j(Object object, Object object2, Object object3, List list, Object object4) {
        this.c.invokeVoid(object, object2, object3, list, object4);
    }
}

