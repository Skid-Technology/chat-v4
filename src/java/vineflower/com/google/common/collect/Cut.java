package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Booleans;
import java.io.Serializable;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

@GwtCompatible
abstract class Cut<C extends Comparable> implements Comparable<Cut<C>>, Serializable {
   final C endpoint;
   private static final long serialVersionUID = 0L;

   Cut(@Nullable C endpoint) {
      this.endpoint = endpoint;
   }

   abstract boolean isLessThan(C var1);

   abstract BoundType typeAsLowerBound();

   abstract BoundType typeAsUpperBound();

   abstract Cut<C> withLowerBoundType(BoundType var1, DiscreteDomain<C> var2);

   abstract Cut<C> withUpperBoundType(BoundType var1, DiscreteDomain<C> var2);

   abstract void describeAsLowerBound(StringBuilder var1);

   abstract void describeAsUpperBound(StringBuilder var1);

   abstract C leastValueAbove(DiscreteDomain<C> var1);

   abstract C greatestValueBelow(DiscreteDomain<C> var1);

   Cut<C> canonical(DiscreteDomain<C> domain) {
      return this;
   }

   public int compareTo(Cut<C> that) {
      if (that == belowAll()) {
         return 1;
      } else if (that == aboveAll()) {
         return -1;
      } else {
         int result = Range.compareOrThrow(this.endpoint, that.endpoint);
         return result != 0 ? result : Booleans.compare(this instanceof Cut.AboveValue, that instanceof Cut.AboveValue);
      }
   }

   C endpoint() {
      return this.endpoint;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof Cut) {
         Cut<C> that = (Cut<C>)obj;

         try {
            int compareResult = this.compareTo(that);
            return compareResult == 0;
         } catch (ClassCastException var4) {
         }
      }

      return false;
   }

   static <C extends Comparable> Cut<C> belowAll() {
      return Cut.BelowAll.INSTANCE;
   }

   static <C extends Comparable> Cut<C> aboveAll() {
      return Cut.AboveAll.INSTANCE;
   }

   static <C extends Comparable> Cut<C> belowValue(C endpoint) {
      return new Cut.BelowValue<>(endpoint);
   }

   static <C extends Comparable> Cut<C> aboveValue(C endpoint) {
      return new Cut.AboveValue<>(endpoint);
   }

   private static final class AboveAll extends Cut<Comparable<?>> {
      private static final Cut.AboveAll INSTANCE = new Cut.AboveAll();
      private static final long serialVersionUID = 0L;

      private AboveAll() {
         super(null);
      }

      @Override
      Comparable<?> endpoint() {
         throw new IllegalStateException("range unbounded on this side");
      }

      @Override
      boolean isLessThan(Comparable<?> value) {
         return false;
      }

      @Override
      BoundType typeAsLowerBound() {
         throw new AssertionError("this statement should be unreachable");
      }

      @Override
      BoundType typeAsUpperBound() {
         throw new IllegalStateException();
      }

      @Override
      Cut<Comparable<?>> withLowerBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> domain) {
         throw new AssertionError("this statement should be unreachable");
      }

      @Override
      Cut<Comparable<?>> withUpperBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> domain) {
         throw new IllegalStateException();
      }

      @Override
      void describeAsLowerBound(StringBuilder sb) {
         throw new AssertionError();
      }

      @Override
      void describeAsUpperBound(StringBuilder sb) {
         sb.append("+∞)");
      }

      @Override
      Comparable<?> leastValueAbove(DiscreteDomain<Comparable<?>> domain) {
         throw new AssertionError();
      }

      @Override
      Comparable<?> greatestValueBelow(DiscreteDomain<Comparable<?>> domain) {
         return domain.maxValue();
      }

      @Override
      public int compareTo(Cut<Comparable<?>> o) {
         return o == this ? 0 : 1;
      }

      @Override
      public String toString() {
         return "+∞";
      }

      private Object readResolve() {
         return INSTANCE;
      }
   }

   private static final class AboveValue<C extends Comparable> extends Cut<C> {
      private static final long serialVersionUID = 0L;

      AboveValue(C endpoint) {
         super(Preconditions.checkNotNull(endpoint));
      }

      @Override
      boolean isLessThan(C value) {
         return Range.compareOrThrow(this.endpoint, value) < 0;
      }

      @Override
      BoundType typeAsLowerBound() {
         return BoundType.OPEN;
      }

      @Override
      BoundType typeAsUpperBound() {
         return BoundType.CLOSED;
      }

      @Override
      Cut<C> withLowerBoundType(BoundType boundType, DiscreteDomain<C> domain) {
         switch (boundType) {
            case CLOSED:
               C next = domain.next(this.endpoint);
               return next == null ? Cut.belowAll() : belowValue(next);
            case OPEN:
               return this;
            default:
               throw new AssertionError();
         }
      }

      @Override
      Cut<C> withUpperBoundType(BoundType boundType, DiscreteDomain<C> domain) {
         switch (boundType) {
            case CLOSED:
               return this;
            case OPEN:
               C next = domain.next(this.endpoint);
               return next == null ? Cut.aboveAll() : belowValue(next);
            default:
               throw new AssertionError();
         }
      }

      @Override
      void describeAsLowerBound(StringBuilder sb) {
         sb.append('(').append(this.endpoint);
      }

      @Override
      void describeAsUpperBound(StringBuilder sb) {
         sb.append(this.endpoint).append(']');
      }

      @Override
      C leastValueAbove(DiscreteDomain<C> domain) {
         return domain.next(this.endpoint);
      }

      @Override
      C greatestValueBelow(DiscreteDomain<C> domain) {
         return this.endpoint;
      }

      @Override
      Cut<C> canonical(DiscreteDomain<C> domain) {
         C next = this.leastValueAbove(domain);
         return next != null ? belowValue(next) : Cut.aboveAll();
      }

      @Override
      public int hashCode() {
         return ~this.endpoint.hashCode();
      }

      @Override
      public String toString() {
         return "/" + this.endpoint + "\\";
      }
   }

   private static final class BelowAll extends Cut<Comparable<?>> {
      private static final Cut.BelowAll INSTANCE = new Cut.BelowAll();
      private static final long serialVersionUID = 0L;

      private BelowAll() {
         super(null);
      }

      @Override
      Comparable<?> endpoint() {
         throw new IllegalStateException("range unbounded on this side");
      }

      @Override
      boolean isLessThan(Comparable<?> value) {
         return true;
      }

      @Override
      BoundType typeAsLowerBound() {
         throw new IllegalStateException();
      }

      @Override
      BoundType typeAsUpperBound() {
         throw new AssertionError("this statement should be unreachable");
      }

      @Override
      Cut<Comparable<?>> withLowerBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> domain) {
         throw new IllegalStateException();
      }

      @Override
      Cut<Comparable<?>> withUpperBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> domain) {
         throw new AssertionError("this statement should be unreachable");
      }

      @Override
      void describeAsLowerBound(StringBuilder sb) {
         sb.append("(-∞");
      }

      @Override
      void describeAsUpperBound(StringBuilder sb) {
         throw new AssertionError();
      }

      @Override
      Comparable<?> leastValueAbove(DiscreteDomain<Comparable<?>> domain) {
         return domain.minValue();
      }

      @Override
      Comparable<?> greatestValueBelow(DiscreteDomain<Comparable<?>> domain) {
         throw new AssertionError();
      }

      @Override
      Cut<Comparable<?>> canonical(DiscreteDomain<Comparable<?>> domain) {
         try {
            return Cut.belowValue(domain.minValue());
         } catch (NoSuchElementException var3) {
            return this;
         }
      }

      @Override
      public int compareTo(Cut<Comparable<?>> o) {
         return o == this ? 0 : -1;
      }

      @Override
      public String toString() {
         return "-∞";
      }

      private Object readResolve() {
         return INSTANCE;
      }
   }

   private static final class BelowValue<C extends Comparable> extends Cut<C> {
      private static final long serialVersionUID = 0L;

      BelowValue(C endpoint) {
         super(Preconditions.checkNotNull(endpoint));
      }

      @Override
      boolean isLessThan(C value) {
         return Range.compareOrThrow(this.endpoint, value) <= 0;
      }

      @Override
      BoundType typeAsLowerBound() {
         return BoundType.CLOSED;
      }

      @Override
      BoundType typeAsUpperBound() {
         return BoundType.OPEN;
      }

      @Override
      Cut<C> withLowerBoundType(BoundType boundType, DiscreteDomain<C> domain) {
         switch (boundType) {
            case CLOSED:
               return this;
            case OPEN:
               C previous = domain.previous(this.endpoint);
               return (Cut<C>)(previous == null ? Cut.belowAll() : new Cut.AboveValue<>(previous));
            default:
               throw new AssertionError();
         }
      }

      @Override
      Cut<C> withUpperBoundType(BoundType boundType, DiscreteDomain<C> domain) {
         switch (boundType) {
            case CLOSED:
               C previous = domain.previous(this.endpoint);
               return (Cut<C>)(previous == null ? Cut.aboveAll() : new Cut.AboveValue<>(previous));
            case OPEN:
               return this;
            default:
               throw new AssertionError();
         }
      }

      @Override
      void describeAsLowerBound(StringBuilder sb) {
         sb.append('[').append(this.endpoint);
      }

      @Override
      void describeAsUpperBound(StringBuilder sb) {
         sb.append(this.endpoint).append(')');
      }

      @Override
      C leastValueAbove(DiscreteDomain<C> domain) {
         return this.endpoint;
      }

      @Override
      C greatestValueBelow(DiscreteDomain<C> domain) {
         return domain.previous(this.endpoint);
      }

      @Override
      public int hashCode() {
         return this.endpoint.hashCode();
      }

      @Override
      public String toString() {
         return "\\" + this.endpoint + "/";
      }
   }
}
