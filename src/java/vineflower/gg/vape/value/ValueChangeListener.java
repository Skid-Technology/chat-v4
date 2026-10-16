package gg.vape.value;

public interface ValueChangeListener<V extends Value<?, V>> {
   void onValueChanged(V var1);
}
