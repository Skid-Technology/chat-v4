/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.event.impl.EventPlayerUseItem;
import gg.vape.mapping.InsertedEventCallback;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EnumHand;
import gg.vape.wrapper.impl.ItemStack;

public class PlayerUseItemCallback
implements InsertedEventCallback {
    private final EntityPlayer var_gg_vape_wrapper_impl_EntityPlayer_h;
    private final EnumHand var_gg_vape_wrapper_impl_EnumHand_r;

    @Override
    public boolean fire() {
        ItemStack itemStack = this.var_gg_vape_wrapper_impl_EntityPlayer_h.i(this.var_gg_vape_wrapper_impl_EnumHand_r);
        EventPlayerUseItem eventPlayerUseItem = new EventPlayerUseItem(itemStack.getObject());
        return eventPlayerUseItem.fire();
    }

    public PlayerUseItemCallback(Object object, Object object2) {
        this.var_gg_vape_wrapper_impl_EntityPlayer_h = new EntityPlayer(object);
        this.var_gg_vape_wrapper_impl_EnumHand_r = new EnumHand(object2);
    }
}

