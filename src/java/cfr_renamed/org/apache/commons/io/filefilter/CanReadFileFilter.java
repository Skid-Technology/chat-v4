/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.CanWriteFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;

public class CanReadFileFilter
extends AbstractFileFilter
implements Serializable {
    final public static IOFileFilter CAN_READ = new CanReadFileFilter();
    final public static IOFileFilter CANNOT_READ = new NotFileFilter(CAN_READ);
    final public static IOFileFilter READ_ONLY = new AndFileFilter(CAN_READ, CanWriteFileFilter.CANNOT_WRITE);

    protected CanReadFileFilter() {
    }

    @Override
    public boolean accept(File file) {
        return file.canRead();
    }
}

