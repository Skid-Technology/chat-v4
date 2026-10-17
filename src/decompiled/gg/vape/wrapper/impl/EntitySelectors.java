/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import com.google.common.base.Predicate;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.Entity;

public class EntitySelectors
extends Wrapper {
    public static Predicate getTeamCollisionPredicate(Entity entity) {
        return (Predicate)EntitySelectors.vapeInstance.getMappings().entitySelectors.getCollisionPredicate(entity.getObject());
    }

    public EntitySelectors(Object selectorsHandle) {
        super(selectorsHandle);
    }

    public static java.util.function.Predicate pushableBy(Entity entity) {
        return (java.util.function.Predicate)EntitySelectors.vapeInstance.getMappings().entitySelectors.getCollisionPredicate(entity.getObject());
    }
}

