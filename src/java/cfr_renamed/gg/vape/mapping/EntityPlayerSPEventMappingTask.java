/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventMotion;
import gg.vape.event.impl.EventPostLocalPlayerTick;
import gg.vape.event.impl.EventPostMotion;
import gg.vape.event.impl.EventPreLocalPlayerTick;
import gg.vape.event.impl.EventPreMotion;
import gg.vape.event.impl.EventSetSprinting;
import gg.vape.mapping.EntityPlayerSPMotionExprEditor;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;
import gg.vape.runtime.NativeBridge;
import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.CtBehavior;
import gg.vape.shaded.javassist.CtClass;
import gg.vape.shaded.javassist.NotFoundException;
import gg.vape.wrapper.impl.ForgeVersion;

public class EntityPlayerSPEventMappingTask
extends JavassistMappingTask {
    private String var_java_lang_String_W;
    private String var_java_lang_String_y;
    private String var_java_lang_String_N;
    private MappingMethod var_gg_vape_mapping_MappingMethod_j = null;
    private String var_java_lang_String_f;
    private String var_java_lang_String_Z;
    private String var_java_lang_String_L;

    private Object lambda$create$0() throws CannotCompileException, NotFoundException {
        this.m();
        return null;
    }

    public static String L(EntityPlayerSPEventMappingTask entityPlayerSPEventMappingTask) {
        return entityPlayerSPEventMappingTask.var_java_lang_String_N;
    }

    private void Q$src$V$gyu8sk() {
        if (ForgeVersion.MC_1_21_10.d()) {
            return;
        }
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().CC.var_gg_vape_mapping_MappingMethod_z;
        this.c(mappingMethod, EventPreLocalPlayerTick.class, "$0");
        this.k(mappingMethod, EventPostLocalPlayerTick.class, "$0");
    }

    public static String T(EntityPlayerSPEventMappingTask entityPlayerSPEventMappingTask) {
        return entityPlayerSPEventMappingTask.var_java_lang_String_y;
    }

    public EntityPlayerSPEventMappingTask() {
        super(MappedClasses.z5);
    }

    public static String S(EntityPlayerSPEventMappingTask entityPlayerSPEventMappingTask) {
        return entityPlayerSPEventMappingTask.var_java_lang_String_f;
    }

    private void p() {
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().Rr.jW;
        this.c(mappingMethod, EventSetSprinting.class, "$0, $1");
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    @Override
    public void transform() {
        this.p();
        this.Q$src$V$gyu8sk();
        if (ForgeVersion.MC_1_7_10.Y()) {
            this.J(this::lambda$create$0);
        }
    }

    public static String O(EntityPlayerSPEventMappingTask entityPlayerSPEventMappingTask) {
        return entityPlayerSPEventMappingTask.var_java_lang_String_W;
    }

    public static String B(EntityPlayerSPEventMappingTask entityPlayerSPEventMappingTask) {
        return entityPlayerSPEventMappingTask.var_java_lang_String_Z;
    }

    private void m() throws CannotCompileException, NotFoundException {
        this.var_gg_vape_mapping_MappingMethod_j = Vape.INSTANCE.getMappings().CC.var_gg_vape_mapping_MappingMethod_k;
        if (ForgeVersion.MC_1_20_6.d()) {
            this.var_java_lang_String_L = Vape.INSTANCE.getMappings().Rr.var_gg_vape_mapping_MappingMethod_Q.getResolvedName();
            this.var_java_lang_String_Z = Vape.INSTANCE.getMappings().Rr.var_gg_vape_mapping_MappingMethod_q.getResolvedName();
            this.var_java_lang_String_y = Vape.INSTANCE.getMappings().Rr.var_gg_vape_mapping_MappingMethod_O.getResolvedName();
        } else {
            this.var_java_lang_String_W = Vape.INSTANCE.getMappings().Rr.jv.getResolvedName();
            this.var_java_lang_String_f = Vape.INSTANCE.getMappings().Rr.jT.getResolvedName();
            this.var_java_lang_String_N = Vape.INSTANCE.getMappings().Rr.var_gg_vape_mapping_MappingField_U.getResolvedName();
        }
        String string2 = EventMotion.class.getName();
        CtBehavior ctBehavior = this.F(this.var_gg_vape_mapping_MappingMethod_j);
        this.c(this.var_gg_vape_mapping_MappingMethod_j, EventPreMotion.class, "$0");
        this.k(this.var_gg_vape_mapping_MappingMethod_j, EventPostMotion.class, "$0");
        boolean bl = Vape.INSTANCE.isForgeAbsent();
        if (bl) {
            String string = "aaa";
            if (NativeBridge.gc("aaa") == null) {
                bl = false;
            }
        }
        boolean bl2 = bl;
        CtClass ctClass = this.i(MappedClasses.zc);
        ctBehavior.instrument(new EntityPlayerSPMotionExprEditor(this, ctClass, string2, bl2));
    }

    public static String N(EntityPlayerSPEventMappingTask entityPlayerSPEventMappingTask) {
        return entityPlayerSPEventMappingTask.var_java_lang_String_L;
    }
}

