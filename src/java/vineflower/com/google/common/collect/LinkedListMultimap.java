package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(serializable = true, emulated = true)
public class LinkedListMultimap<K, V> extends AbstractMultimap<K, V> implements ListMultimap<K, V>, Serializable {
   private transient LinkedListMultimap.Node<K, V> head;
   private transient LinkedListMultimap.Node<K, V> tail;
   private transient Map<K, LinkedListMultimap.KeyList<K, V>> keyToKeyList;
   private transient int size;
   private transient int modCount;
   @GwtIncompatible("java serialization not supported")
   private static final long serialVersionUID = 0L;

   public static <K, V> LinkedListMultimap<K, V> create() {
      return new LinkedListMultimap<>();
   }

   public static <K, V> LinkedListMultimap<K, V> create(int expectedKeys) {
      return new LinkedListMultimap<>(expectedKeys);
   }

   public static <K, V> LinkedListMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) {
      return new LinkedListMultimap<>(multimap);
   }

   LinkedListMultimap() {
      this.keyToKeyList = Maps.newHashMap();
   }

   private LinkedListMultimap(int expectedKeys) {
      this.keyToKeyList = new HashMap<>(expectedKeys);
   }

   private LinkedListMultimap(Multimap<? extends K, ? extends V> multimap) {
      this(multimap.keySet().size());
      this.putAll(multimap);
   }

   private LinkedListMultimap.Node<K, V> addNode(@Nullable K key, @Nullable V value, @Nullable LinkedListMultimap.Node<K, V> nextSibling) {
      LinkedListMultimap.Node<K, V> node = new LinkedListMultimap.Node<>(key, value);
      if (this.head == null) {
         this.head = this.tail = node;
         this.keyToKeyList.put(key, new LinkedListMultimap.KeyList<>(node));
         this.modCount++;
      } else if (nextSibling == null) {
         this.tail.next = node;
         node.previous = this.tail;
         this.tail = node;
         LinkedListMultimap.KeyList<K, V> keyList = this.keyToKeyList.get(key);
         if (keyList == null) {
            this.keyToKeyList.put(key, new LinkedListMultimap.KeyList<>(node));
            this.modCount++;
         } else {
            keyList.count++;
            LinkedListMultimap.Node<K, V> keyTail = keyList.tail;
            keyTail.nextSibling = node;
            node.previousSibling = keyTail;
            keyList.tail = node;
         }
      } else {
         LinkedListMultimap.KeyList<K, V> keyList = this.keyToKeyList.get(key);
         keyList.count++;
         node.previous = nextSibling.previous;
         node.previousSibling = nextSibling.previousSibling;
         node.next = nextSibling;
         node.nextSibling = nextSibling;
         if (nextSibling.previousSibling == null) {
            this.keyToKeyList.get(key).head = node;
         } else {
            nextSibling.previousSibling.nextSibling = node;
         }

         if (nextSibling.previous == null) {
            this.head = node;
         } else {
            nextSibling.previous.next = node;
         }

         nextSibling.previous = node;
         nextSibling.previousSibling = node;
      }

      this.size++;
      return node;
   }

   private void removeNode(LinkedListMultimap.Node<K, V> node) {
      if (node.previous != null) {
         node.previous.next = node.next;
      } else {
         this.head = node.next;
      }

      if (node.next != null) {
         node.next.previous = node.previous;
      } else {
         this.tail = node.previous;
      }

      if (node.previousSibling == null && node.nextSibling == null) {
         LinkedListMultimap.KeyList<K, V> keyList = this.keyToKeyList.remove(node.key);
         keyList.count = 0;
         this.modCount++;
      } else {
         LinkedListMultimap.KeyList<K, V> keyList = this.keyToKeyList.get(node.key);
         keyList.count--;
         if (node.previousSibling == null) {
            keyList.head = node.nextSibling;
         } else {
            node.previousSibling.nextSibling = node.nextSibling;
         }

         if (node.nextSibling == null) {
            keyList.tail = node.previousSibling;
         } else {
            node.nextSibling.previousSibling = node.previousSibling;
         }
      }

      this.size--;
   }

   private void removeAllNodes(@Nullable Object key) {
      Iterators.clear(new LinkedListMultimap.ValueForKeyIterator(key));
   }

   private static void checkElement(@Nullable Object node) {
      if (node == null) {
         throw new NoSuchElementException();
      }
   }

   @Override
   public int size() {
      return this.size;
   }

   @Override
   public boolean isEmpty() {
      return this.head == null;
   }

   @Override
   public boolean containsKey(@Nullable Object key) {
      return this.keyToKeyList.containsKey(key);
   }

   @Override
   public boolean containsValue(@Nullable Object value) {
      return this.values().contains(value);
   }

   @Override
   public boolean put(@Nullable K key, @Nullable V value) {
      this.addNode(key, value, null);
      return true;
   }

   @Override
   public List<V> replaceValues(@Nullable K key, Iterable<? extends V> values) {
      List<V> oldValues = this.getCopy(key);
      ListIterator<V> keyValues = new LinkedListMultimap.ValueForKeyIterator(key);
      Iterator<? extends V> newValues = values.iterator();

      while (keyValues.hasNext() && newValues.hasNext()) {
         keyValues.next();
         keyValues.set((V)newValues.next());
      }

      while (keyValues.hasNext()) {
         keyValues.next();
         keyValues.remove();
      }

      while (newValues.hasNext()) {
         keyValues.add((V)newValues.next());
      }

      return oldValues;
   }

   private List<V> getCopy(@Nullable Object key) {
      return Collections.unmodifiableList(Lists.newArrayList(new LinkedListMultimap.ValueForKeyIterator(key)));
   }

   @Override
   public List<V> removeAll(@Nullable Object key) {
      List<V> oldValues = this.getCopy(key);
      this.removeAllNodes(key);
      return oldValues;
   }

   @Override
   public void clear() {
      this.head = null;
      this.tail = null;
      this.keyToKeyList.clear();
      this.size = 0;
      this.modCount++;
   }

   @Override
   public List<V> get(@Nullable final K key) {
      return new AbstractSequentialList<V>() {
         @Override
         public int size() {
            LinkedListMultimap.KeyList<K, V> keyList = LinkedListMultimap.this.keyToKeyList.get(key);
            return keyList == null ? 0 : keyList.count;
         }

         @Override
         public ListIterator<V> listIterator(int index) {
            return LinkedListMultimap.this.new ValueForKeyIterator(key, index);
         }
      };
   }

   @Override
   Set<K> createKeySet() {
      return new Sets.ImprovedAbstractSet<K>() {
         @Override
         public int size() {
            return LinkedListMultimap.this.keyToKeyList.size();
         }

         @Override
         public Iterator<K> iterator() {
            return LinkedListMultimap.this.new DistinctKeyIterator();
         }

         @Override
         public boolean contains(Object key) {
            return LinkedListMultimap.this.containsKey(key);
         }

         @Override
         public boolean remove(Object o) {
            return !LinkedListMultimap.this.removeAll(o).isEmpty();
         }
      };
   }

   public List<V> values() {
      return (List<V>)super.values();
   }

   List<V> createValues() {
      return new AbstractSequentialList<V>() {
         @Override
         public int size() {
            return LinkedListMultimap.this.size;
         }

         @Override
         public ListIterator<V> listIterator(int index) {
            final LinkedListMultimap<K, V>.NodeIterator nodeItr = LinkedListMultimap.this.new NodeIterator(index);
            return new TransformedListIterator<Entry<K, V>, V>(nodeItr) {
               V transform(Entry<K, V> entry) {
                  return entry.getValue();
               }

               @Override
               public void set(V value) {
                  nodeItr.setValue(value);
               }
            };
         }
      };
   }

   public List<Entry<K, V>> entries() {
      return (List<Entry<K, V>>)super.entries();
   }

   List<Entry<K, V>> createEntries() {
      return new AbstractSequentialList<Entry<K, V>>() {
         @Override
         public int size() {
            return LinkedListMultimap.this.size;
         }

         @Override
         public ListIterator<Entry<K, V>> listIterator(int index) {
            return LinkedListMultimap.this.new NodeIterator(index);
         }
      };
   }

   @Override
   Iterator<Entry<K, V>> entryIterator() {
      throw new AssertionError("should never be called");
   }

   @Override
   Map<K, Collection<V>> createAsMap() {
      return new Multimaps.AsMap<>(this);
   }

   @GwtIncompatible("java.io.ObjectOutputStream")
   private void writeObject(ObjectOutputStream stream) throws IOException {
      stream.defaultWriteObject();
      stream.writeInt(this.size());

      for (Entry<K, V> entry : this.entries()) {
         stream.writeObject(entry.getKey());
         stream.writeObject(entry.getValue());
      }
   }

   @GwtIncompatible("java.io.ObjectInputStream")
   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
      stream.defaultReadObject();
      this.keyToKeyList = Maps.newLinkedHashMap();
      int size = stream.readInt();

      for (int i = 0; i < size; i++) {
         K key = (K)stream.readObject();
         V value = (V)stream.readObject();
         this.put(key, value);
      }
   }

   private class DistinctKeyIterator implements Iterator<K> {
      final Set<K> seenKeys = Sets.newHashSetWithExpectedSize(LinkedListMultimap.this.keySet().size());
      LinkedListMultimap.Node<K, V> next = LinkedListMultimap.this.head;
      LinkedListMultimap.Node<K, V> current;
      int expectedModCount = LinkedListMultimap.this.modCount;

      private DistinctKeyIterator() {
      }

      private void checkForConcurrentModification() {
         if (LinkedListMultimap.this.modCount != this.expectedModCount) {
            throw new ConcurrentModificationException();
         }
      }

      @Override
      public boolean hasNext() {
         this.checkForConcurrentModification();
         return this.next != null;
      }

      @Override
      public K next() {
         this.checkForConcurrentModification();
         LinkedListMultimap.checkElement(this.next);
         this.current = this.next;
         this.seenKeys.add(this.current.key);

         do {
            this.next = this.next.next;
         } while (this.next != null && !this.seenKeys.add(this.next.key));

         return this.current.key;
      }

      @Override
      public void remove() {
         this.checkForConcurrentModification();
         CollectPreconditions.checkRemove(this.current != null);
         LinkedListMultimap.this.removeAllNodes(this.current.key);
         this.current = null;
         this.expectedModCount = LinkedListMultimap.this.modCount;
      }
   }

   private static class KeyList<K, V> {
      LinkedListMultimap.Node<K, V> head;
      LinkedListMultimap.Node<K, V> tail;
      int count;

      KeyList(LinkedListMultimap.Node<K, V> firstNode) {
         this.head = firstNode;
         this.tail = firstNode;
         firstNode.previousSibling = null;
         firstNode.nextSibling = null;
         this.count = 1;
      }
   }

   private static final class Node<K, V> extends AbstractMapEntry<K, V> {
      final K key;
      V value;
      LinkedListMultimap.Node<K, V> next;
      LinkedListMultimap.Node<K, V> previous;
      LinkedListMultimap.Node<K, V> nextSibling;
      LinkedListMultimap.Node<K, V> previousSibling;

      Node(@Nullable K key, @Nullable V value) {
         this.key = key;
         this.value = value;
      }

      @Override
      public K getKey() {
         return this.key;
      }

      @Override
      public V getValue() {
         return this.value;
      }

      @Override
      public V setValue(@Nullable V newValue) {
         V result = this.value;
         this.value = newValue;
         return result;
      }
   }

   private class NodeIterator implements ListIterator<Entry<K, V>> {
      int nextIndex;
      LinkedListMultimap.Node<K, V> next;
      LinkedListMultimap.Node<K, V> current;
      LinkedListMultimap.Node<K, V> previous;
      int expectedModCount = LinkedListMultimap.this.modCount;

      NodeIterator(int index) {
         int size = LinkedListMultimap.this.size();
         Preconditions.checkPositionIndex(index, size);
         if (index >= size / 2) {
            this.previous = LinkedListMultimap.this.tail;
            this.nextIndex = size;

            while (index++ < size) {
               this.previous();
            }
         } else {
            this.next = LinkedListMultimap.this.head;

            while (index-- > 0) {
               this.next();
            }
         }

         this.current = null;
      }

      private void checkForConcurrentModification() {
         if (LinkedListMultimap.this.modCount != this.expectedModCount) {
            throw new ConcurrentModificationException();
         }
      }

      @Override
      public boolean hasNext() {
         this.checkForConcurrentModification();
         return this.next != null;
      }

      public LinkedListMultimap.Node<K, V> next() {
         this.checkForConcurrentModification();
         LinkedListMultimap.checkElement(this.next);
         this.previous = this.current = this.next;
         this.next = this.next.next;
         this.nextIndex++;
         return this.current;
      }

      @Override
      public void remove() {
         this.checkForConcurrentModification();
         CollectPreconditions.checkRemove(this.current != null);
         if (this.current != this.next) {
            this.previous = this.current.previous;
            this.nextIndex--;
         } else {
            this.next = this.current.next;
         }

         LinkedListMultimap.this.removeNode(this.current);
         this.current = null;
         this.expectedModCount = LinkedListMultimap.this.modCount;
      }

      @Override
      public boolean hasPrevious() {
         this.checkForConcurrentModification();
         return this.previous != null;
      }

      public LinkedListMultimap.Node<K, V> previous() {
         this.checkForConcurrentModification();
         LinkedListMultimap.checkElement(this.previous);
         this.next = this.current = this.previous;
         this.previous = this.previous.previous;
         this.nextIndex--;
         return this.current;
      }

      @Override
      public int nextIndex() {
         return this.nextIndex;
      }

      @Override
      public int previousIndex() {
         return this.nextIndex - 1;
      }

      public void set(Entry<K, V> e) {
         throw new UnsupportedOperationException();
      }

      public void add(Entry<K, V> e) {
         throw new UnsupportedOperationException();
      }

      void setValue(V value) {
         Preconditions.checkState(this.current != null);
         this.current.value = value;
      }
   }

   private class ValueForKeyIterator implements ListIterator<V> {
      final Object key;
      int nextIndex;
      LinkedListMultimap.Node<K, V> next;
      LinkedListMultimap.Node<K, V> current;
      LinkedListMultimap.Node<K, V> previous;

      ValueForKeyIterator(@Nullable Object key) {
         this.key = key;
         LinkedListMultimap.KeyList<K, V> keyList = LinkedListMultimap.this.keyToKeyList.get(key);
         this.next = keyList == null ? null : keyList.head;
      }

      public ValueForKeyIterator(@Nullable Object key, int index) {
         LinkedListMultimap.KeyList<K, V> keyList = LinkedListMultimap.this.keyToKeyList.get(key);
         int size = keyList == null ? 0 : keyList.count;
         Preconditions.checkPositionIndex(index, size);
         if (index >= size / 2) {
            this.previous = keyList == null ? null : keyList.tail;
            this.nextIndex = size;

            while (index++ < size) {
               this.previous();
            }
         } else {
            this.next = keyList == null ? null : keyList.head;

            while (index-- > 0) {
               this.next();
            }
         }

         this.key = key;
         this.current = null;
      }

      @Override
      public boolean hasNext() {
         return this.next != null;
      }

      @Override
      public V next() {
         LinkedListMultimap.checkElement(this.next);
         this.previous = this.current = this.next;
         this.next = this.next.nextSibling;
         this.nextIndex++;
         return this.current.value;
      }

      @Override
      public boolean hasPrevious() {
         return this.previous != null;
      }

      @Override
      public V previous() {
         LinkedListMultimap.checkElement(this.previous);
         this.next = this.current = this.previous;
         this.previous = this.previous.previousSibling;
         this.nextIndex--;
         return this.current.value;
      }

      @Override
      public int nextIndex() {
         return this.nextIndex;
      }

      @Override
      public int previousIndex() {
         return this.nextIndex - 1;
      }

      @Override
      public void remove() {
         CollectPreconditions.checkRemove(this.current != null);
         if (this.current != this.next) {
            this.previous = this.current.previousSibling;
            this.nextIndex--;
         } else {
            this.next = this.current.nextSibling;
         }

         LinkedListMultimap.this.removeNode(this.current);
         this.current = null;
      }

      @Override
      public void set(V value) {
         Preconditions.checkState(this.current != null);
         this.current.value = value;
      }

      @Override
      public void add(V value) {
         this.previous = LinkedListMultimap.this.addNode((K)this.key, value, this.next);
         this.nextIndex++;
         this.current = null;
      }
   }
}
