package gg.vape.value;

public interface ValueChangeValidator<V extends Value<T, V>, T> {
   boolean isValidChange(V var1, T var2, T var3);
}
