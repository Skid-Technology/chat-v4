package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;

@Beta
@GwtCompatible(emulated = true)
public interface SortedMultiset<E> extends SortedMultisetBridge<E>, SortedIterable<E> {
   @Override
   Comparator<? super E> comparator();

   Multiset.Entry<E> firstEntry();

   Multiset.Entry<E> lastEntry();

   Multiset.Entry<E> pollFirstEntry();

   Multiset.Entry<E> pollLastEntry();

   NavigableSet<E> elementSet();

   @Override
   Set<Multiset.Entry<E>> entrySet();

   @Override
   Iterator<E> iterator();

   SortedMultiset<E> descendingMultiset();

   SortedMultiset<E> headMultiset(E var1, BoundType var2);

   SortedMultiset<E> subMultiset(E var1, BoundType var2, E var3, BoundType var4);

   SortedMultiset<E> tailMultiset(E var1, BoundType var2);
}
