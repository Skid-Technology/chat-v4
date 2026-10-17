/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.value;

import com.google.gson.JsonObject;
import gg.vape.value.Value;
import java.util.Arrays;
import java.util.Objects;

public class ValueSnapshot<T extends Value<R, ?>, R> {
    private final T sourceValue;
    private R value;

    public void setValue(Object object) {
        if (object instanceof Object[]) {
            this.value = Arrays.copyOf((Object[])object, ((Object[])object).length);
            return;
        }
        this.value = object;
    }

    public R getValue() {
        return this.value;
    }

    public boolean isDefault() {
        Object defaultValue = ((Value)this.sourceValue).getDefaultValue();
        if (defaultValue instanceof Object[]) {
            return Arrays.equals((Object[])defaultValue, (Object[])this.value);
        }
        if (defaultValue instanceof double[]) {
            return Arrays.equals((double[])defaultValue, (double[])this.value);
        }
        return Objects.equals(defaultValue, this.value);
    }

    public T getSourceValue() {
        return this.sourceValue;
    }

    public void loadJson(JsonObject jsonObject) {
        ((Value)this.sourceValue).setPersistenceSuppressed(true);
        Object originalValue = ((Value)this.sourceValue).getValueCompat();
        ((Value)this.sourceValue).reset();
        if (((Value)this.sourceValue).loadJson(jsonObject)) {
            this.value = ((Value)this.sourceValue).getValueCompat();
        }
        ((Value)this.sourceValue).setValue(originalValue);
        ((Value)this.sourceValue).setPersistenceSuppressed(false);
    }

    public ValueSnapshot(T sourceValue) {
        this.sourceValue = sourceValue;
        this.value = ((Value)sourceValue).getDefaultValue();
    }

    public JsonObject toJson() {
        Object originalValue = ((Value)this.sourceValue).getValueCompat();
        ((Value)this.sourceValue).setValue(this.getValue());
        JsonObject jsonObject = ((Value)this.sourceValue).toJson(false);
        ((Value)this.sourceValue).setValue(originalValue);
        return jsonObject;
    }
}

