/*
 * Decompiled with CFR 0.152.
 */
package com.google.gson.stream;

final class JsonScope {
    final static int EMPTY_ARRAY = 1;
    final static int NONEMPTY_ARRAY = 2;
    final static int EMPTY_OBJECT = 3;
    final static int DANGLING_NAME = 4;
    final static int NONEMPTY_OBJECT = 5;
    final static int EMPTY_DOCUMENT = 6;
    final static int NONEMPTY_DOCUMENT = 7;
    final static int CLOSED = 8;

    JsonScope() {
    }
}

