/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils;

import gg.vape.unmap.INamed;
import java.util.Comparator;

public final class NameComparator
implements Comparator<INamed> {
    @Override
    public int compare(INamed iNamed, INamed iNamed2) {
        return iNamed.getName().compareTo(iNamed2.getName());
    }

    @Override
    public int compare(Object object, Object object2) {
        return this.compare((INamed)object, (INamed)object2);
    }
}

