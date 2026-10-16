/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.Nullable;

@GwtCompatible
public final class Functions {
    private Functions() {
    }

    public static Function<Object, String> toStringFunction() {
        return ToStringFunction.INSTANCE;
    }

    public static <E> Function<E, E> identity() {
        return IdentityFunction.INSTANCE;
    }

    public static <K, V> Function<K, V> forMap(Map<K, V> map) {
        return new FunctionForMapNoDefault<K, V>(map);
    }

    public static <K, V> Function<K, V> forMap(Map<K, ? extends V> map, @Nullable V defaultValue) {
        return new ForMapWithDefault<K, V>(map, defaultValue);
    }

    public static <A, B, C> Function<A, C> compose(Function<B, C> g, Function<A, ? extends B> f) {
        return new FunctionComposition<A, B, C>(g, f);
    }

    public static <T> Function<T, Boolean> forPredicate(Predicate<T> predicate) {
        return new PredicateFunction(predicate);
    }

    public static <E> Function<Object, E> constant(@Nullable E value) {
        return new ConstantFunction<E>(value);
    }

    @Beta
    public static <T> Function<Object, T> forSupplier(Supplier<T> supplier) {
        return new SupplierFunction(supplier);
    }

    private static class SupplierFunction<T>
    implements Function<Object, T>,
    Serializable {
        private final Supplier<T> supplier;
        private final static long serialVersionUID = 0L;

        private SupplierFunction(Supplier<T> supplier) {
            this.supplier = Preconditions.checkNotNull(supplier);
        }

        @Override
        public T apply(@Nullable Object input) {
            return this.supplier.get();
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj instanceof SupplierFunction) {
                SupplierFunction that = (SupplierFunction)obj;
                return this.supplier.equals(that.supplier);
            }
            return false;
        }

        public int hashCode() {
            return this.supplier.hashCode();
        }

        public String toString() {
            return "forSupplier(" + this.supplier + ")";
        }
    }

    private static class ConstantFunction<E>
    implements Function<Object, E>,
    Serializable {
        private final E value;
        private final static long serialVersionUID = 0L;

        public ConstantFunction(@Nullable E value) {
            this.value = value;
        }

        @Override
        public E apply(@Nullable Object from) {
            return this.value;
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj instanceof ConstantFunction) {
                ConstantFunction that = (ConstantFunction)obj;
                return Objects.equal(this.value, that.value);
            }
            return false;
        }

        public int hashCode() {
            return this.value == null ? 0 : this.value.hashCode();
        }

        public String toString() {
            return "constant(" + this.value + ")";
        }
    }

    private static class PredicateFunction<T>
    implements Function<T, Boolean>,
    Serializable {
        private final Predicate<T> predicate;
        private final static long serialVersionUID = 0L;

        private PredicateFunction(Predicate<T> predicate) {
            this.predicate = Preconditions.checkNotNull(predicate);
        }

        @Override
        public Boolean apply(@Nullable T t) {
            return this.predicate.apply(t);
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj instanceof PredicateFunction) {
                PredicateFunction that = (PredicateFunction)obj;
                return this.predicate.equals(that.predicate);
            }
            return false;
        }

        public int hashCode() {
            return this.predicate.hashCode();
        }

        public String toString() {
            return "forPredicate(" + this.predicate + ")";
        }

        @Override
        public Object apply(Object x0) {
            return this.apply(x0);
        }
    }

    private static class FunctionComposition<A, B, C>
    implements Function<A, C>,
    Serializable {
        private final Function<B, C> var_com_google_common_base_Function_B,_C__g;
        private final Function<A, ? extends B> var_com_google_common_base_Function_A,___extends_B__f;
        private final static long serialVersionUID = 0L;

        public FunctionComposition(Function<B, C> g, Function<A, ? extends B> f) {
            this.var_com_google_common_base_Function_B,_C__g = Preconditions.checkNotNull(g);
            this.var_com_google_common_base_Function_A,___extends_B__f = Preconditions.checkNotNull(f);
        }

        @Override
        public C apply(@Nullable A a) {
            return this.var_com_google_common_base_Function_B,_C__g.apply(this.var_com_google_common_base_Function_A,___extends_B__f.apply(a));
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj instanceof FunctionComposition) {
                FunctionComposition that = (FunctionComposition)obj;
                return this.var_com_google_common_base_Function_A,___extends_B__f.equals(that.var_com_google_common_base_Function_A,___extends_B__f) && this.var_com_google_common_base_Function_B,_C__g.equals(that.var_com_google_common_base_Function_B,_C__g);
            }
            return false;
        }

        public int hashCode() {
            return this.var_com_google_common_base_Function_A,___extends_B__f.hashCode() ^ this.var_com_google_common_base_Function_B,_C__g.hashCode();
        }

        public String toString() {
            return this.var_com_google_common_base_Function_B,_C__g + "(" + this.var_com_google_common_base_Function_A,___extends_B__f + ")";
        }
    }

    private static class ForMapWithDefault<K, V>
    implements Function<K, V>,
    Serializable {
        final Map<K, ? extends V> map;
        final V defaultValue;
        private final static long serialVersionUID = 0L;

        ForMapWithDefault(Map<K, ? extends V> map, @Nullable V defaultValue) {
            this.map = Preconditions.checkNotNull(map);
            this.defaultValue = defaultValue;
        }

        @Override
        public V apply(@Nullable K key) {
            V result = this.map.get(key);
            return result != null || this.map.containsKey(key) ? result : this.defaultValue;
        }

        @Override
        public boolean equals(@Nullable Object o) {
            if (o instanceof ForMapWithDefault) {
                ForMapWithDefault that = (ForMapWithDefault)o;
                return this.map.equals(that.map) && Objects.equal(this.defaultValue, that.defaultValue);
            }
            return false;
        }

        public int hashCode() {
            return Objects.hashCode(this.map, this.defaultValue);
        }

        public String toString() {
            return "forMap(" + this.map + ", defaultValue=" + this.defaultValue + ")";
        }
    }

    private static class FunctionForMapNoDefault<K, V>
    implements Function<K, V>,
    Serializable {
        final Map<K, V> map;
        private final static long serialVersionUID = 0L;

        FunctionForMapNoDefault(Map<K, V> map) {
            this.map = Preconditions.checkNotNull(map);
        }

        @Override
        public V apply(@Nullable K key) {
            V result = this.map.get(key);
            Preconditions.checkArgument(result != null || this.map.containsKey(key), "Key '%s' not present in map", key);
            return result;
        }

        @Override
        public boolean equals(@Nullable Object o) {
            if (o instanceof FunctionForMapNoDefault) {
                FunctionForMapNoDefault that = (FunctionForMapNoDefault)o;
                return this.map.equals(that.map);
            }
            return false;
        }

        public int hashCode() {
            return this.map.hashCode();
        }

        public String toString() {
            return "forMap(" + this.map + ")";
        }
    }

    private static enum IdentityFunction implements Function<Object, Object>
    {
        INSTANCE;


        @Override
        @Nullable
        public Object apply(@Nullable Object o) {
            return o;
        }

        public String toString() {
            return "identity";
        }
    }

    private static enum ToStringFunction implements Function<Object, String>
    {
        INSTANCE;


        @Override
        public String apply(Object o) {
            Preconditions.checkNotNull(o);
            return o.toString();
        }

        public String toString() {
            return "toString";
        }

        @Override
        public Object apply(Object x0) {
            return this.apply(x0);
        }
    }
}

