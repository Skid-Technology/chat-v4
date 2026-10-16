/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventEntityRendererMouseUpdate;
import gg.vape.event.impl.EventMouseOverUpdate;
import gg.vape.event.impl.EventPostEntityRendererMouseUpdate;
import gg.vape.event.impl.EventPostRenderHand;
import gg.vape.event.impl.EventPostRenderTick;
import gg.vape.event.impl.EventPreEntityRendererMouseUpdate;
import gg.vape.event.impl.EventPreRenderHand;
import gg.vape.event.impl.EventPreRenderTick;
import gg.vape.mapping.EventInjectionSpec;
import gg.vape.mapping.EventRender2DStaticCallback;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.expr.EventFogDensityInjectionExprEditor;
import gg.vape.mapping.expr.EventRender2DInjectionExprEditor;
import gg.vape.mapping.expr.EventRender2DStaticCallbackExprEditor;
import gg.vape.runtime.NativeBridge;
import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.CtBehavior;
import gg.vape.shaded.javassist.NotFoundException;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.concurrent.atomic.AtomicBoolean;

public class EntityRendererEventMappingTask
extends JavassistMappingTask {
    @Override
    public void transform() {
        if (ForgeVersion.MC_1_21_4.b().y()) {
            EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(Vape.INSTANCE.getMappings().RY.var_gg_vape_mapping_MappingMethod_J, EventPreRenderTick.class);
            eventInjectionSpec.setConstructorArguments("$1");
            eventInjectionSpec.setCancelableReturnGuard(false);
            eventInjectionSpec.setInsertBefore(true);
            this.registerEventInjection(eventInjectionSpec);
            this.i();
        }
        if (ForgeVersion.MC_1_16_5.v()) {
            this.c(Vape.INSTANCE.getMappings().RY.var_gg_vape_mapping_MappingMethod_B, EventPreRenderHand.class, "");
            this.k(Vape.INSTANCE.getMappings().RY.var_gg_vape_mapping_MappingMethod_B, EventPostRenderHand.class, "");
        }
        if (ForgeVersion.MC_1_21_11.d()) {
            this.c(Vape.INSTANCE.getMappings().RY.var_gg_vape_mapping_MappingMethod_C, EventPreEntityRendererMouseUpdate.class, "");
            this.k(Vape.INSTANCE.getMappings().RY.var_gg_vape_mapping_MappingMethod_C, EventEntityRendererMouseUpdate.class, "$1");
            this.k(Vape.INSTANCE.getMappings().RY.var_gg_vape_mapping_MappingMethod_a, EventPostEntityRendererMouseUpdate.class, "");
        } else {
            this.c(Vape.INSTANCE.getMappings().RY.var_gg_vape_mapping_MappingMethod_a, EventPreEntityRendererMouseUpdate.class, "");
            this.k(Vape.INSTANCE.getMappings().RY.var_gg_vape_mapping_MappingMethod_a, EventPostEntityRendererMouseUpdate.class, "");
        }
        if (ForgeVersion.MC_26_1.v() && Vape.INSTANCE.getMappings().RY.var_gg_vape_mapping_MappingMethod_k != null && !Vape.INSTANCE.getMappings().RY.var_gg_vape_mapping_MappingMethod_k.hasResolutionFailed()) {
            this.k(Vape.INSTANCE.getMappings().RY.var_gg_vape_mapping_MappingMethod_k, EventMouseOverUpdate.class, "$1");
        }
        this.J(this::lambda$create$0);
        if (ForgeVersion.MC_26_1.d()) {
            this.J(this::lambda$create$1);
        }
        this.J(this::lambda$create$2);
    }

    private void J$src$V$f5bloi() throws CannotCompileException {
        if (ForgeVersion.MC_1_20_6.d() || NativeBridge.isBadlion189Runtime()) {
            return;
        }
        String string = ForgeVersion.MC_1_17.d() ? "(IIF)V" : (ForgeVersion.MC_1_16_5.d() ? "()V" : "(IF)V");
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        CtBehavior ctBehavior = this.F(Vape.INSTANCE.getMappings().RY.var_gg_vape_mapping_MappingMethod_J);
        ctBehavior.instrument(new EventRender2DInjectionExprEditor(this, string, atomicBoolean));
    }

    private Object lambda$create$0() throws CannotCompileException, NotFoundException {
        this.J$src$V$f5bloi();
        return null;
    }

    private Object lambda$create$2() throws CannotCompileException, NotFoundException {
        this.r();
        return null;
    }

    private void r() throws CannotCompileException {
        if (ForgeVersion.MC_1_16_5.v()) {
            CtBehavior ctBehavior = this.F(Vape.INSTANCE.getMappings().RY.var_gg_vape_mapping_MappingMethod_L);
            MappingMethod mappingMethod = Vape.INSTANCE.getMappings().hi.var_gg_vape_mapping_MappingMethod_A;
            AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            ctBehavior.instrument(new EventFogDensityInjectionExprEditor(this, mappingMethod, atomicBoolean));
        }
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    private void i() {
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().RY.var_gg_vape_mapping_MappingMethod_J;
        EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventPostRenderTick.class);
        eventInjectionSpec.setConstructorArguments("$1");
        eventInjectionSpec.setCancelableReturnGuard(false);
        eventInjectionSpec.setInsertBefore(false);
        this.registerEventInjection(eventInjectionSpec);
    }

    private void B$src$V$f0x8xm() throws CannotCompileException {
        EntityRendererEventMappingTask.p(EventRender2DStaticCallback.class);
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().Ca.var_gg_vape_mapping_MappingMethod_z;
        MappingMethod mappingMethod2 = Vape.INSTANCE.getMappings().RY.var_gg_vape_mapping_MappingMethod_J;
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        CtBehavior ctBehavior = this.F(mappingMethod2);
        ctBehavior.instrument(new EventRender2DStaticCallbackExprEditor(this, atomicBoolean, mappingMethod));
    }

    private Object lambda$create$1() throws CannotCompileException, NotFoundException {
        this.B$src$V$f0x8xm();
        return null;
    }

    public EntityRendererEventMappingTask() {
        super(MappedClasses.FW);
    }
}

