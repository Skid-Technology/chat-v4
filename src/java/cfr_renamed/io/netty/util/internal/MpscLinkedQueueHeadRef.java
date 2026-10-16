/*
 * Decompiled with CFR 0.152.
 */
package io.netty.util.internal;

import io.netty.util.internal.MpscLinkedQueueNode;
import io.netty.util.internal.MpscLinkedQueuePad0;
import io.netty.util.internal.PlatformDependent;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

abstract class MpscLinkedQueueHeadRef<E>
extends MpscLinkedQueuePad0<E>
implements Serializable {
    private final static long serialVersionUID = 8467054865577874285L;
    private final static AtomicReferenceFieldUpdater<MpscLinkedQueueHeadRef, MpscLinkedQueueNode> UPDATER;
    private transient volatile MpscLinkedQueueNode<E> headRef;

    MpscLinkedQueueHeadRef() {
    }

    protected final MpscLinkedQueueNode<E> headRef() {
        return this.headRef;
    }

    protected final void setHeadRef(MpscLinkedQueueNode<E> headRef) {
        this.headRef = headRef;
    }

    protected final void lazySetHeadRef(MpscLinkedQueueNode<E> headRef) {
        UPDATER.lazySet(this, headRef);
    }

    static {
        AtomicReferenceFieldUpdater<MpscLinkedQueueHeadRef, Object> updater = PlatformDependent.newAtomicReferenceFieldUpdater(MpscLinkedQueueHeadRef.class, "headRef");
        if (updater == null) {
            updater = AtomicReferenceFieldUpdater.newUpdater(MpscLinkedQueueHeadRef.class, MpscLinkedQueueNode.class, "headRef");
        }
        UPDATER = updater;
    }
}

