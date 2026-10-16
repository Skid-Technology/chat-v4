/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.comparator.AbstractFileComparator;
import org.apache.commons.io.comparator.ReverseComparator;

public class NameFileComparator
extends AbstractFileComparator
implements Serializable {
    final public static Comparator<File> NAME_COMPARATOR = new NameFileComparator();
    final public static Comparator<File> NAME_REVERSE = new ReverseComparator(NAME_COMPARATOR);
    final public static Comparator<File> NAME_INSENSITIVE_COMPARATOR = new NameFileComparator(IOCase.INSENSITIVE);
    final public static Comparator<File> NAME_INSENSITIVE_REVERSE = new ReverseComparator(NAME_INSENSITIVE_COMPARATOR);
    final public static Comparator<File> NAME_SYSTEM_COMPARATOR = new NameFileComparator(IOCase.SYSTEM);
    final public static Comparator<File> NAME_SYSTEM_REVERSE = new ReverseComparator(NAME_SYSTEM_COMPARATOR);
    private final IOCase caseSensitivity;

    public NameFileComparator() {
        this.caseSensitivity = IOCase.SENSITIVE;
    }

    public NameFileComparator(IOCase caseSensitivity) {
        this.caseSensitivity = caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity;
    }

    @Override
    public int compare(File file1, File file2) {
        return this.caseSensitivity.checkCompareTo(file1.getName(), file2.getName());
    }

    @Override
    public String toString() {
        return super.toString() + "[caseSensitivity=" + this.caseSensitivity + "]";
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

