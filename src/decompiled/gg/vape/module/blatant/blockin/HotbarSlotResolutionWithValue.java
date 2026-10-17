/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.module.blatant.blockin;

import gg.vape.module.blatant.blockin.HotbarSlotResolution;
import gg.vape.module.blatant.blockin.HotbarSlotResolutionStatus;
import org.jetbrains.annotations.Nullable;

public class HotbarSlotResolutionWithValue<T>
extends HotbarSlotResolution<HotbarSlotResolutionWithValue<T>> {
    @Nullable
    private T value;

    HotbarSlotResolutionWithValue(HotbarSlotResolutionStatus status, @Nullable String message, @Nullable T value) {
        super(status, message);
        this.value = value;
    }

    public HotbarSlotResolutionWithValue() {
        this(HotbarSlotResolutionStatus.PENDING, null, null);
    }

    @Nullable
    public T getValue() {
        return this.value;
    }

    public HotbarSlotResolutionWithValue<T> setValue(@Nullable T value) {
        this.value = value;
        return this;
    }
}

