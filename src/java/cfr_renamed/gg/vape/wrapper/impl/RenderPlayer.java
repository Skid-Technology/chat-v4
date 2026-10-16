/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.ModelBiped;
import gg.vape.wrapper.impl.ModelBipedSkeletonBridge;
import gg.vape.wrapper.impl.PlayerModel;
import gg.vape.wrapper.impl.RenderLivingBase;

public class RenderPlayer
extends RenderLivingBase<EntityPlayer> {
    public RenderPlayer(Object renderPlayerHandle) {
        super(renderPlayerHandle);
    }

    public ModelBiped getModelBipedMain() {
        return new ModelBiped(RenderPlayer.vapeInstance.getMappingsMapperCompat().renderPlayer.getMainModel(this.I));
    }

    @Override
    public PlayerModel getMainModel() {
        return new PlayerModel(RenderPlayer.vapeInstance.getMappingsMapperCompat().renderPlayer.getMainModel(this.I));
    }

    @Override
    public ModelBipedSkeletonBridge getMainModel() {
        return this.getMainModel();
    }
}

