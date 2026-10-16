/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import org.apache.commons.io.filefilter.IOFileFilter;

public class FalseFileFilter
implements IOFileFilter,
Serializable {
    final public static IOFileFilter FALSE;
    final public static IOFileFilter INSTANCE;

    protected FalseFileFilter() {
    }

    @Override
    public boolean accept(File file) {
        return false;
    }

    @Override
    public boolean accept(File dir, String name) {
        return false;
    }

    static {
        INSTANCE = FALSE = new FalseFileFilter();
    }
}

