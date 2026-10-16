/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.EnumActionResult;
import gg.vape.wrapper.impl.GlStateManagerTexGenCoord;
import gg.vape.wrapper.impl.ItemRenderContext;
import gg.vape.wrapper.impl.ItemStack;

public class ItemActionResult
extends EnumActionResult {
    public static ItemActionResult create(GlStateManagerTexGenCoord swingSource, ItemRenderContext itemContext) {
        return new ItemActionResult(ItemActionResult.vapeInstance.getMappingsMapperCompat().itemActionResult.create(swingSource.getObject(), itemContext.getObject()));
    }

    public boolean consumesAction() {
        return ItemActionResult.vapeInstance.getMappingsMapperCompat().itemActionResult.consumesAction(this.getObject());
    }

    public ItemActionResult withoutItem() {
        return new ItemActionResult(ItemActionResult.vapeInstance.getMappingsMapperCompat().itemActionResult.withoutItem(this.getObject()));
    }

    public ItemStack getHeldItemTransformedTo() {
        return new ItemStack(ItemActionResult.vapeInstance.getMappingsMapperCompat().itemActionResult.getHeldItemTransformedTo(this.getObject()));
    }

    public ItemActionResult heldItemTransformedTo(ItemStack itemStack) {
        return new ItemActionResult(ItemActionResult.vapeInstance.getMappingsMapperCompat().itemActionResult.heldItemTransformedTo(this.getObject(), itemStack.getObject()));
    }

    public boolean wasItemInteraction() {
        return ItemActionResult.vapeInstance.getMappingsMapperCompat().itemActionResult.wasItemInteraction(this.getObject());
    }

    public ItemActionResult(Object handle) {
        super(handle);
    }
}

