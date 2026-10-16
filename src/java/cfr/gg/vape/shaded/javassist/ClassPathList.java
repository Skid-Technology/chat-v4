/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist;

import gg.vape.shaded.javassist.ClassPath;

final class ClassPathList {
    ClassPathList next;
    ClassPath path;

    ClassPathList(ClassPath p, ClassPathList n) {
        this.next = n;
        this.path = p;
    }
}

