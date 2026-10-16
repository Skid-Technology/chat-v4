/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.config;

import gg.vape.config.ProfileModuleSnapshot;
import java.util.Comparator;

public class ProfileModuleSnapshotOrderComparator
implements Comparator<ProfileModuleSnapshot> {
    @Override
    public int compare(ProfileModuleSnapshot left, ProfileModuleSnapshot right) {
        return Integer.compare(right.getSortPriority(), left.getSortPriority());
    }

    @Override
    public int compare(Object object, Object object2) {
        return this.compare((ProfileModuleSnapshot)object, (ProfileModuleSnapshot)object2);
    }
}

