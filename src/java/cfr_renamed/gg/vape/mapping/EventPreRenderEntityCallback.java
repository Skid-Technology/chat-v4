/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.event.impl.EventPreRenderLiving;
import gg.vape.mapping.InsertedEventCallback;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.ITextComponent;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Render;
import gg.vape.wrapper.impl.RenderManager;
import gg.vape.wrapper.impl.RenderStateBridge;

public class EventPreRenderEntityCallback
implements InsertedEventCallback {
    private final double var_double_g;
    private final Object var_java_lang_Object_M;
    private final double var_double_b;
    private final double var_double_W;
    private final Object var_java_lang_Object_q;

    public static void call(Object object, Object object2, Object object3, Object object4, int n) {
        boolean bl = new EventPreRenderEntityCallback(object, object3).fire();
        if (bl) {
            RenderStateBridge renderStateBridge = new RenderStateBridge(object2);
            renderStateBridge.Z(new ITextComponent(null));
        }
    }

    public static void call(Object object, Object object2, float f, Object object3, Object object4, int n) {
        Object object5 = new Render(object3).a(new Entity(object), f).getObject();
        EventPreRenderEntityCallback.call(object, object5, object2, object4, n);
    }

    @Override
    public boolean fire() {
        if (EventPreRenderLiving.getEventListeners().hasListeners()) {
            EventPreRenderLiving eventPreRenderLiving = new EventPreRenderLiving(this.var_java_lang_Object_M, this.var_double_W, this.var_double_g, this.var_double_b, this.var_java_lang_Object_q);
            return eventPreRenderLiving.fire();
        }
        return false;
    }

    public EventPreRenderEntityCallback(Object object, double d, double d2, double d3) {
        this.var_java_lang_Object_M = object;
        this.var_java_lang_Object_q = null;
        this.var_double_W = d;
        this.var_double_g = d2;
        this.var_double_b = d3;
    }

    public EventPreRenderEntityCallback(Object object, Object object2) {
        this.var_java_lang_Object_q = object2;
        this.var_java_lang_Object_M = object;
        Entity entity = new Entity(object);
        this.var_double_W = entity.M() + (entity.z() - entity.M()) * (double)Minecraft.getTimer().renderPartialTicks() - RenderManager.getInterpolatedRenderPosX();
        this.var_double_g = entity.W() + (entity.N() - entity.W()) * (double)Minecraft.getTimer().renderPartialTicks() - RenderManager.getInterpolatedRenderPosY();
        this.var_double_b = entity.m$src$D$fwnne5() + (entity.h() - entity.m$src$D$fwnne5()) * (double)Minecraft.getTimer().renderPartialTicks() - RenderManager.getInterpolatedRenderPosZ();
    }

    public static void call(Object object) {
        new EventPreRenderEntityCallback(object, null).fire();
    }
}

