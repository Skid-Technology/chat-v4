/*
 * Decompiled with CFR 0.152.
 */
package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.ToNumberPolicy;
import com.google.gson.ToNumberStrategy;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ObjectTypeAdapter
extends TypeAdapter<Object> {
    private static final TypeAdapterFactory DOUBLE_FACTORY = ObjectTypeAdapter.newFactory(ToNumberPolicy.DOUBLE);
    private final Gson gson;
    private final ToNumberStrategy toNumberStrategy;

    private ObjectTypeAdapter(Gson gson, ToNumberStrategy toNumberStrategy) {
        this.gson = gson;
        this.toNumberStrategy = toNumberStrategy;
    }

    private static TypeAdapterFactory newFactory(final ToNumberStrategy toNumberStrategy) {
        return new TypeAdapterFactory(){

            @Override
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
                if (type.getRawType() == Object.class) {
                    return new ObjectTypeAdapter(gson, toNumberStrategy);
                }
                return null;
            }
        };
    }

    public static TypeAdapterFactory getFactory(ToNumberStrategy toNumberStrategy) {
        if (toNumberStrategy == ToNumberPolicy.DOUBLE) {
            return DOUBLE_FACTORY;
        }
        return ObjectTypeAdapter.newFactory(toNumberStrategy);
    }

    private Object tryBeginNesting(JsonReader in, JsonToken peeked) throws IOException {
        switch (peeked) {
            case BEGIN_ARRAY: {
                in.beginArray();
                return new ArrayList();
            }
            case BEGIN_OBJECT: {
                in.beginObject();
                return new LinkedTreeMap();
            }
        }
        return null;
    }

    private Object readTerminal(JsonReader in, JsonToken peeked) throws IOException {
        switch (peeked) {
            case STRING: {
                return in.nextString();
            }
            case NUMBER: {
                return this.toNumberStrategy.readNumber(in);
            }
            case BOOLEAN: {
                return in.nextBoolean();
            }
            case NULL: {
                in.nextNull();
                return null;
            }
        }
        throw new IllegalStateException("Unexpected token: " + (Object)((Object)peeked));
    }

    @Override
    public Object read(JsonReader in) throws IOException {
        JsonToken peeked = in.peek();
        Object current = this.tryBeginNesting(in, peeked);
        if (current == null) {
            return this.readTerminal(in, peeked);
        }
        ArrayDeque<Object> stack = new ArrayDeque<Object>();
        while (true) {
            if (in.hasNext()) {
                Object value;
                boolean isNesting;
                String name = null;
                if (current instanceof Map) {
                    name = in.nextName();
                }
                boolean bl = isNesting = (value = this.tryBeginNesting(in, peeked = in.peek())) != null;
                if (value == null) {
                    value = this.readTerminal(in, peeked);
                }
                if (current instanceof List) {
                    List list = (List)current;
                    list.add(value);
                } else {
                    Map map = (Map)current;
                    map.put(name, value);
                }
                if (!isNesting) continue;
                stack.addLast(current);
                current = value;
                continue;
            }
            if (current instanceof List) {
                in.endArray();
            } else {
                in.endObject();
            }
            if (stack.isEmpty()) {
                return current;
            }
            current = stack.removeLast();
        }
    }

    @Override
    public void write(JsonWriter out, Object value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        TypeAdapter<?> typeAdapter = this.gson.getAdapter(value.getClass());
        if (typeAdapter instanceof ObjectTypeAdapter) {
            out.beginObject();
            out.endObject();
            return;
        }
        typeAdapter.write(out, value);
    }
}

