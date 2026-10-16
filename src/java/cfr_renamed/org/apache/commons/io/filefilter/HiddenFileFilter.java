/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;

public class HiddenFileFilter
extends AbstractFileFilter
implements Serializable {
    final public static IOFileFilter HIDDEN = new HiddenFileFilter();
    final public static IOFileFilter VISIBLE = new NotFileFilter(HIDDEN);

    protected HiddenFileFilter() {
    }

    @Override
    public boolean accept(File file) {
        return file.isHidden();
    }
}

