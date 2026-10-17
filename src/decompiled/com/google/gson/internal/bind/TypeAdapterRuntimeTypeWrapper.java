/*
 * Decompiled with CFR 0.152.
 */
package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.internal.bind.SerializationDelegatingTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

final class TypeAdapterRuntimeTypeWrapper<T>
extends TypeAdapter<T> {
    private final Gson context;
    private final TypeAdapter<T> delegate;
    private final Type type;

    TypeAdapterRuntimeTypeWrapper(Gson context, TypeAdapter<T> delegate, Type type) {
        this.context = context;
        this.delegate = delegate;
        this.type = type;
    }

    @Override
    public T read(JsonReader in) throws IOException {
        return this.delegate.read(in);
    }

    @Override
    public void write(JsonWriter out, T value) throws IOException {
        TypeAdapter<Object> chosen = this.delegate;
        Type runtimeType = TypeAdapterRuntimeTypeWrapper.getRuntimeTypeIfMoreSpecific(this.type, value);
        if (runtimeType != this.type) {
            TypeAdapter<?> runtimeTypeAdapter = this.context.getAdapter(TypeToken.get(runtimeType));
            chosen = !(runtimeTypeAdapter instanceof ReflectiveTypeAdapterFactory.Adapter) ? runtimeTypeAdapter : (!TypeAdapterRuntimeTypeWrapper.isReflective(this.delegate) ? this.delegate : runtimeTypeAdapter);
        }
        chosen.write(out, value);
    }

    private static boolean isReflective(TypeAdapter<?> typeAdapter) {
        TypeAdapter delegate;
        while (typeAdapter instanceof SerializationDelegatingTypeAdapter && (delegate = ((SerializationDelegatingTypeAdapter)typeAdapter).getSerializationDelegate()) != typeAdapter) {
            typeAdapter = delegate;
        }
        return typeAdapter instanceof ReflectiveTypeAdapterFactory.Adapter;
    }

    private static Type getRuntimeTypeIfMoreSpecific(Type type, Object value) {
        if (value != null && (type instanceof Class || type instanceof TypeVariable)) {
            type = value.getClass();
        }
        return type;
    }
}

