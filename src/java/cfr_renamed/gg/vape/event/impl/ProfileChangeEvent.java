/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.event.impl;

import gg.vape.config.Profile;
import gg.vape.event.EventListeners;
import gg.vape.event.IEvent;
import org.jetbrains.annotations.Nullable;

public class ProfileChangeEvent
implements IEvent {
    private final static EventListeners EVENT_LISTENERS = new EventListeners();
    private final Profile newProfile;
    @Nullable
    private final Profile previousProfile;

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    public ProfileChangeEvent(@Nullable Profile previousProfile, Profile newProfile) {
        this.previousProfile = previousProfile;
        this.newProfile = newProfile;
    }

    public Profile getNewProfile() {
        return this.newProfile;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public Profile getPreviousProfile() {
        return this.previousProfile;
    }
}

