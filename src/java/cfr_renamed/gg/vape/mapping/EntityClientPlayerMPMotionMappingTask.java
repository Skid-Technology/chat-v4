/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventMotion;
import gg.vape.event.impl.EventPostMotion;
import gg.vape.event.impl.EventPreMotion;
import gg.vape.mapping.EntityClientPlayerMPMotionExprEditor;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;
import gg.vape.runtime.NativeBridge;
import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.CtBehavior;
import gg.vape.shaded.javassist.CtClass;
import gg.vape.shaded.javassist.NotFoundException;
import gg.vape.wrapper.impl.ForgeVersion;

public class EntityClientPlayerMPMotionMappingTask
extends JavassistMappingTask {
    private String var_java_lang_String_y;
    private String var_java_lang_String_q;
    private String var_java_lang_String_u;
    private CtClass var_gg_vape_shaded_javassist_CtClass_p;
    private String var_java_lang_String_M;
    private String var_java_lang_String_b;
    private MappingMethod var_gg_vape_mapping_MappingMethod_J = null;

    public static String z(EntityClientPlayerMPMotionMappingTask entityClientPlayerMPMotionMappingTask) {
        return entityClientPlayerMPMotionMappingTask.var_java_lang_String_u;
    }

    public static String Q(EntityClientPlayerMPMotionMappingTask entityClientPlayerMPMotionMappingTask) {
        return entityClientPlayerMPMotionMappingTask.var_java_lang_String_b;
    }

    private static CannotCompileException a(CannotCompileException cannotCompileException) {
        return cannotCompileException;
    }

    private Object lambda$create$0() throws CannotCompileException, NotFoundException {
        this.s();
        return null;
    }

    public EntityClientPlayerMPMotionMappingTask(Class clazz) {
        super(clazz);
    }

    @Override
    public void transform() {
        this.J(this::lambda$create$0);
    }

    private void s() throws CannotCompileException, NotFoundException {
        boolean bl;
        CtBehavior ctBehavior;
        String string3;
        String string2;
        block4: {
            block3: {
                block2: {
                    this.var_gg_vape_mapping_MappingMethod_J = Vape.INSTANCE.getMappings().q_.var_gg_vape_mapping_MappingMethod_M;
                    this.var_java_lang_String_q = Vape.INSTANCE.getMappings().RQ.var_gg_vape_mapping_MappingField_n.getResolvedName();
                    this.var_java_lang_String_M = Vape.INSTANCE.getMappings().RQ.var_gg_vape_mapping_MappingField_n.getDescriptor();
                    string2 = ForgeVersion.MC_1_16_5.d() ? Vape.INSTANCE.getMappings().Rr.jz.getResolvedName() : null;
                    this.var_java_lang_String_y = Vape.INSTANCE.getMappings().Rr.jv.getResolvedName();
                    this.var_java_lang_String_u = Vape.INSTANCE.getMappings().Rr.jT.getResolvedName();
                    this.var_java_lang_String_b = Vape.INSTANCE.getMappings().Rr.var_gg_vape_mapping_MappingField_U.getResolvedName();
                    this.var_gg_vape_shaded_javassist_CtClass_p = this.i(MappedClasses.uk);
                    string3 = EventMotion.class.getName();
                    ctBehavior = this.F(this.var_gg_vape_mapping_MappingMethod_J);
                    this.c(this.var_gg_vape_mapping_MappingMethod_J, EventPreMotion.class, "$0");
                    this.k(this.var_gg_vape_mapping_MappingMethod_J, EventPostMotion.class, "$0");
                    bl = Vape.INSTANCE.isForgeAbsent();
                    if (!bl) break block2;
                    String string = "aaa";
                    if (NativeBridge.gc("aaa") == null) break block3;
                }
                if (!ForgeVersion.MC_1_7_10.L() || !Vape.INSTANCE.isVanillaMinecraftPresent()) break block4;
            }
            bl = false;
        }
        boolean bl2 = bl;
        CtClass ctClass = this.i(MappedClasses.zc);
        ctBehavior.instrument(new EntityClientPlayerMPMotionExprEditor(this, string2, string3, bl2, ctClass));
    }

    public static String j(EntityClientPlayerMPMotionMappingTask entityClientPlayerMPMotionMappingTask) {
        return entityClientPlayerMPMotionMappingTask.var_java_lang_String_y;
    }
}

