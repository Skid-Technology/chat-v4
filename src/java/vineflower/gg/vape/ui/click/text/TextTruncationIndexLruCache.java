package gg.vape.ui.click.text;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class TextTruncationIndexLruCache extends LinkedHashMap<Integer, Integer> {
   final TextTruncationIndexCache owner;

   public TextTruncationIndexLruCache(TextTruncationIndexCache owner, int initialCapacity, float loadFactor, boolean accessOrder) {
      super(initialCapacity, loadFactor, accessOrder);
      this.owner = owner;
   }

   @Override
   protected boolean removeEldestEntry(Entry<Integer, Integer> entry) {
      return this.size() > 512;
   }
}
