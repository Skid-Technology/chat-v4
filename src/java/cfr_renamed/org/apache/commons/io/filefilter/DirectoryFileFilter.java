/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;

public class DirectoryFileFilter
extends AbstractFileFilter
implements Serializable {
    final public static IOFileFilter DIRECTORY;
    final public static IOFileFilter INSTANCE;

    protected DirectoryFileFilter() {
    }

    @Override
    public boolean accept(File file) {
        return file.isDirectory();
    }

    static {
        INSTANCE = DIRECTORY = new DirectoryFileFilter();
    }
}

