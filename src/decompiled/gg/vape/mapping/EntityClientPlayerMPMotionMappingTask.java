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
    private String y;
    private String q;
    private String u;
    private CtClass p;
    private String M;
    private String b;
    private MappingMethod J = null;

    public static String z(EntityClientPlayerMPMotionMappingTask entityClientPlayerMPMotionMappingTask) {
        return entityClientPlayerMPMotionMappingTask.u;
    }

    public static String Q(EntityClientPlayerMPMotionMappingTask entityClientPlayerMPMotionMappingTask) {
        return entityClientPlayerMPMotionMappingTask.b;
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
                    this.J = Vape.INSTANCE.getMappings().q_.M;
                    this.q = Vape.INSTANCE.getMappings().RQ.n.getResolvedName();
                    this.M = Vape.INSTANCE.getMappings().RQ.n.getDescriptor();
                    string2 = ForgeVersion.MC_1_16_5.d() ? Vape.INSTANCE.getMappings().Rr.jz.getResolvedName() : null;
                    this.y = Vape.INSTANCE.getMappings().Rr.jv.getResolvedName();
                    this.u = Vape.INSTANCE.getMappings().Rr.jT.getResolvedName();
                    this.b = Vape.INSTANCE.getMappings().Rr.U.getResolvedName();
                    this.p = this.i(MappedClasses.uk);
                    string3 = EventMotion.class.getName();
                    ctBehavior = this.F(this.J);
                    this.c(this.J, EventPreMotion.class, "$0");
                    this.k(this.J, EventPostMotion.class, "$0");
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
        return entityClientPlayerMPMotionMappingTask.y;
    }
}

