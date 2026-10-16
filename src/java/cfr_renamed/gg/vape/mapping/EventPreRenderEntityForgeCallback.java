/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.event.impl.EventPreRenderLiving;
import gg.vape.mapping.InsertedEventCallback;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.PreRenderEntityForgeEvent;

public class EventPreRenderEntityForgeCallback
implements InsertedEventCallback {
    private final double var_double_v;
    private final Object var_java_lang_Object_L;
    private final EntityLivingBase var_gg_vape_wrapper_impl_EntityLivingBase_a;
    private final double var_double_U;
    private final double var_double_T;

    @Override
    public boolean fire() {
        boolean bl = new EventPreRenderLiving(this.var_gg_vape_wrapper_impl_EntityLivingBase_a, this.var_double_U, this.var_double_v, this.var_double_T, null).fire();
        if (this.var_java_lang_Object_L != null) {
            PreRenderEntityForgeEvent preRenderEntityForgeEvent = new PreRenderEntityForgeEvent(this.var_java_lang_Object_L);
            preRenderEntityForgeEvent.setCancelled(bl);
        }
        return bl;
    }

    public EventPreRenderEntityForgeCallback(Object object, double d, double d2, double d3) {
        this(null, object, d, d2, d3);
    }

    public double getPosZ() {
        return this.var_double_T;
    }

    public EventPreRenderEntityForgeCallback(Object object, Object object2, double d, double d2, double d3) {
        this.var_java_lang_Object_L = object;
        this.var_gg_vape_wrapper_impl_EntityLivingBase_a = new EntityLivingBase(object2);
        this.var_double_U = d;
        this.var_double_v = d2;
        this.var_double_T = d3;
    }

    public double getPosX() {
        return this.var_double_U;
    }

    public double getPosY() {
        return this.var_double_v;
    }

    public Object getForgeEvent() {
        return this.var_java_lang_Object_L;
    }

    public EntityLivingBase getClientPlayer() {
        return this.var_gg_vape_wrapper_impl_EntityLivingBase_a;
    }
}

