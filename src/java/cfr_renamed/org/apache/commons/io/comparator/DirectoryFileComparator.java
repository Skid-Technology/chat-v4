/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.io.comparator.AbstractFileComparator;
import org.apache.commons.io.comparator.ReverseComparator;

public class DirectoryFileComparator
extends AbstractFileComparator
implements Serializable {
    final public static Comparator<File> DIRECTORY_COMPARATOR = new DirectoryFileComparator();
    final public static Comparator<File> DIRECTORY_REVERSE = new ReverseComparator(DIRECTORY_COMPARATOR);

    @Override
    public int compare(File file1, File file2) {
        return this.getType(file1) - this.getType(file2);
    }

    private int getType(File file) {
        if (file.isDirectory()) {
            return 1;
        }
        return 2;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public List sort(List x0) {
        return super.sort(x0);
    }

    @Override
    public File[] sort(File[] x0) {
        return super.sort(x0);
    }

    @Override
    public int compare(Object x0, Object x1) {
        return this.compare((File)x0, (File)x1);
    }
}

