/*
 * Decompiled with CFR 0.152.
 */
package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;

@Beta
@GwtCompatible
public abstract class Escaper {
    private final Function<String, String> asFunction = new Function<String, String>(){

        @Override
        public String apply(String from) {
            return Escaper.this.escape(from);
        }

        @Override
        public Object apply(Object x0) {
            return this.apply((String)x0);
        }
    };

    protected Escaper() {
    }

    public abstract String escape(String var1);

    public final Function<String, String> asFunction() {
        return this.asFunction;
    }
}

