/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.AbstractClientPlayer;
import gg.vape.wrapper.impl.GameProfile;
import gg.vape.wrapper.impl.World;

public class EntityOtherPlayerMP
extends AbstractClientPlayer {
    public EntityOtherPlayerMP(Object entityHandle) {
        super(entityHandle);
    }

    public static EntityOtherPlayerMP create(World world, GameProfile gameProfile) {
        return new EntityOtherPlayerMP(EntityOtherPlayerMP.vapeInstance.getMappingsMapperCompat().entityOtherPlayer.create(world.getObject(), gameProfile.getObject()));
    }
}

