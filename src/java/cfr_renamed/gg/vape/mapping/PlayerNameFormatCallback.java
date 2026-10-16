/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.event.impl.EventNameFormat;
import gg.vape.mapping.InsertedEventCallback;
import gg.vape.module.render.entity.RenderEntityContextCache;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.ITextComponent;

public class PlayerNameFormatCallback
implements InsertedEventCallback {
    public static boolean var_boolean_s;
    private final Object var_java_lang_Object_U;
    private ITextComponent var_gg_vape_wrapper_impl_ITextComponent_I;

    private ITextComponent grabDisplayName(EntityPlayer entityPlayer) {
        var_boolean_s = true;
        ITextComponent iTextComponent = RenderEntityContextCache.getDisplayName(entityPlayer);
        var_boolean_s = false;
        return iTextComponent;
    }

    @Override
    public boolean fire() {
        if (var_boolean_s) {
            var_boolean_s = false;
            return false;
        }
        EntityPlayer entityPlayer = new EntityPlayer(this.var_java_lang_Object_U);
        ITextComponent iTextComponent = RenderEntityContextCache.getCustomName(entityPlayer);
        if (iTextComponent != null) {
            this.var_gg_vape_wrapper_impl_ITextComponent_I = iTextComponent;
        } else {
            iTextComponent = this.grabDisplayName(entityPlayer);
            this.var_gg_vape_wrapper_impl_ITextComponent_I = new ITextComponent(iTextComponent);
            EventNameFormat eventNameFormat = new EventNameFormat(entityPlayer, this.var_gg_vape_wrapper_impl_ITextComponent_I);
            eventNameFormat.fire();
            this.var_gg_vape_wrapper_impl_ITextComponent_I = eventNameFormat.getDisplayName();
            RenderEntityContextCache.setCustomName(entityPlayer, this.var_gg_vape_wrapper_impl_ITextComponent_I);
        }
        return true;
    }

    public PlayerNameFormatCallback(Object object) {
        this.var_java_lang_Object_U = object;
    }

    public ITextComponent getDisplayName() {
        return this.var_gg_vape_wrapper_impl_ITextComponent_I;
    }

    public Object getRawDisplayName() {
        return this.var_gg_vape_wrapper_impl_ITextComponent_I.getObject();
    }

    static {
        var_boolean_s = false;
    }
}

