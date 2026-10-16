package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Map.Entry;

public class DefaultHttpHeaders extends HttpHeaders {
   private static final int BUCKET_SIZE = 17;
   private final DefaultHttpHeaders.HeaderEntry[] entries = new DefaultHttpHeaders.HeaderEntry[17];
   private final DefaultHttpHeaders.HeaderEntry head = new DefaultHttpHeaders.HeaderEntry();
   protected final boolean validate;

   private static int index(int hash) {
      return hash % 17;
   }

   public DefaultHttpHeaders() {
      this(true);
   }

   public DefaultHttpHeaders(boolean validate) {
      this.validate = validate;
      this.head.before = this.head.after = this.head;
   }

   void validateHeaderName0(CharSequence headerName) {
      validateHeaderName(headerName);
   }

   @Override
   public HttpHeaders add(HttpHeaders headers) {
      if (!(headers instanceof DefaultHttpHeaders)) {
         return super.add(headers);
      } else {
         DefaultHttpHeaders defaultHttpHeaders = (DefaultHttpHeaders)headers;

         for (DefaultHttpHeaders.HeaderEntry e = defaultHttpHeaders.head.after; e != defaultHttpHeaders.head; e = e.after) {
            this.add(e.key, e.value);
         }

         return this;
      }
   }

   @Override
   public HttpHeaders set(HttpHeaders headers) {
      if (!(headers instanceof DefaultHttpHeaders)) {
         return super.set(headers);
      } else {
         this.clear();
         DefaultHttpHeaders defaultHttpHeaders = (DefaultHttpHeaders)headers;

         for (DefaultHttpHeaders.HeaderEntry e = defaultHttpHeaders.head.after; e != defaultHttpHeaders.head; e = e.after) {
            this.add(e.key, e.value);
         }

         return this;
      }
   }

   @Override
   public HttpHeaders add(String name, Object value) {
      return this.add((CharSequence)name, value);
   }

   @Override
   public HttpHeaders add(CharSequence name, Object value) {
      CharSequence strVal;
      if (this.validate) {
         this.validateHeaderName0(name);
         strVal = toCharSequence(value);
         validateHeaderValue(strVal);
      } else {
         strVal = toCharSequence(value);
      }

      int h = hash(name);
      int i = index(h);
      this.add0(h, i, name, strVal);
      return this;
   }

   @Override
   public HttpHeaders add(String name, Iterable<?> values) {
      return this.add((CharSequence)name, values);
   }

   @Override
   public HttpHeaders add(CharSequence name, Iterable<?> values) {
      if (this.validate) {
         this.validateHeaderName0(name);
      }

      int h = hash(name);
      int i = index(h);

      for (Object v : values) {
         CharSequence vstr = toCharSequence(v);
         if (this.validate) {
            validateHeaderValue(vstr);
         }

         this.add0(h, i, name, vstr);
      }

      return this;
   }

   private void add0(int h, int i, CharSequence name, CharSequence value) {
      DefaultHttpHeaders.HeaderEntry e = this.entries[i];
      DefaultHttpHeaders.HeaderEntry newEntry;
      this.entries[i] = newEntry = new DefaultHttpHeaders.HeaderEntry(h, name, value);
      newEntry.next = e;
      newEntry.addBefore(this.head);
   }

   @Override
   public HttpHeaders remove(String name) {
      return this.remove((CharSequence)name);
   }

   @Override
   public HttpHeaders remove(CharSequence name) {
      if (name == null) {
         throw new NullPointerException("name");
      } else {
         int h = hash(name);
         int i = index(h);
         this.remove0(h, i, name);
         return this;
      }
   }

   private void remove0(int h, int i, CharSequence name) {
      DefaultHttpHeaders.HeaderEntry e = this.entries[i];
      if (e != null) {
         while (e.hash == h && equalsIgnoreCase(name, e.key)) {
            e.remove();
            DefaultHttpHeaders.HeaderEntry next = e.next;
            if (next == null) {
               this.entries[i] = null;
               return;
            }

            this.entries[i] = next;
            e = next;
         }

         while (true) {
            DefaultHttpHeaders.HeaderEntry next = e.next;
            if (next == null) {
               return;
            }

            if (next.hash == h && equalsIgnoreCase(name, next.key)) {
               e.next = next.next;
               next.remove();
            } else {
               e = next;
            }
         }
      }
   }

   @Override
   public HttpHeaders set(String name, Object value) {
      return this.set((CharSequence)name, value);
   }

   @Override
   public HttpHeaders set(CharSequence name, Object value) {
      CharSequence strVal;
      if (this.validate) {
         this.validateHeaderName0(name);
         strVal = toCharSequence(value);
         validateHeaderValue(strVal);
      } else {
         strVal = toCharSequence(value);
      }

      int h = hash(name);
      int i = index(h);
      this.remove0(h, i, name);
      this.add0(h, i, name, strVal);
      return this;
   }

   @Override
   public HttpHeaders set(String name, Iterable<?> values) {
      return this.set((CharSequence)name, values);
   }

   @Override
   public HttpHeaders set(CharSequence name, Iterable<?> values) {
      if (values == null) {
         throw new NullPointerException("values");
      } else {
         if (this.validate) {
            this.validateHeaderName0(name);
         }

         int h = hash(name);
         int i = index(h);
         this.remove0(h, i, name);

         for (Object v : values) {
            if (v == null) {
               break;
            }

            CharSequence strVal = toCharSequence(v);
            if (this.validate) {
               validateHeaderValue(strVal);
            }

            this.add0(h, i, name, strVal);
         }

         return this;
      }
   }

   @Override
   public HttpHeaders clear() {
      Arrays.fill(this.entries, null);
      this.head.before = this.head.after = this.head;
      return this;
   }

   @Override
   public String get(String name) {
      return this.get((CharSequence)name);
   }

   @Override
   public String get(CharSequence name) {
      if (name == null) {
         throw new NullPointerException("name");
      } else {
         int h = hash(name);
         int i = index(h);
         DefaultHttpHeaders.HeaderEntry e = this.entries[i];

         CharSequence value;
         for (value = null; e != null; e = e.next) {
            if (e.hash == h && equalsIgnoreCase(name, e.key)) {
               value = e.value;
            }
         }

         return value == null ? null : value.toString();
      }
   }

   @Override
   public List<String> getAll(String name) {
      return this.getAll((CharSequence)name);
   }

   @Override
   public List<String> getAll(CharSequence name) {
      if (name == null) {
         throw new NullPointerException("name");
      } else {
         LinkedList<String> values = new LinkedList<>();
         int h = hash(name);
         int i = index(h);

         for (DefaultHttpHeaders.HeaderEntry e = this.entries[i]; e != null; e = e.next) {
            if (e.hash == h && equalsIgnoreCase(name, e.key)) {
               values.addFirst(e.getValue());
            }
         }

         return values;
      }
   }

   @Override
   public List<Entry<String, String>> entries() {
      List<Entry<String, String>> all = new LinkedList<>();

      for (DefaultHttpHeaders.HeaderEntry e = this.head.after; e != this.head; e = e.after) {
         all.add(e);
      }

      return all;
   }

   @Override
   public Iterator<Entry<String, String>> iterator() {
      return new DefaultHttpHeaders.HeaderIterator();
   }

   @Override
   public boolean contains(String name) {
      return this.get(name) != null;
   }

   @Override
   public boolean contains(CharSequence name) {
      return this.get(name) != null;
   }

   @Override
   public boolean isEmpty() {
      return this.head == this.head.after;
   }

   @Override
   public boolean contains(String name, String value, boolean ignoreCaseValue) {
      return this.contains((CharSequence)name, (CharSequence)value, ignoreCaseValue);
   }

   @Override
   public boolean contains(CharSequence name, CharSequence value, boolean ignoreCaseValue) {
      if (name == null) {
         throw new NullPointerException("name");
      } else {
         int h = hash(name);
         int i = index(h);

         for (DefaultHttpHeaders.HeaderEntry e = this.entries[i]; e != null; e = e.next) {
            if (e.hash == h && equalsIgnoreCase(name, e.key)) {
               if (ignoreCaseValue) {
                  if (equalsIgnoreCase(e.value, value)) {
                     return true;
                  }
               } else if (e.value.equals(value)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   @Override
   public Set<String> names() {
      Set<String> names = new LinkedHashSet<>();

      for (DefaultHttpHeaders.HeaderEntry e = this.head.after; e != this.head; e = e.after) {
         names.add(e.getKey());
      }

      return names;
   }

   private static CharSequence toCharSequence(Object value) {
      if (value == null) {
         return null;
      } else if (value instanceof CharSequence) {
         return (CharSequence)value;
      } else if (value instanceof Number) {
         return value.toString();
      } else if (value instanceof Date) {
         return HttpHeaderDateFormat.get().format((Date)value);
      } else {
         return value instanceof Calendar ? HttpHeaderDateFormat.get().format(((Calendar)value).getTime()) : value.toString();
      }
   }

   void encode(ByteBuf buf) {
      for (DefaultHttpHeaders.HeaderEntry e = this.head.after; e != this.head; e = e.after) {
         e.encode(buf);
      }
   }

   private final class HeaderEntry implements Entry<String, String> {
      final int hash;
      final CharSequence key;
      CharSequence value;
      DefaultHttpHeaders.HeaderEntry next;
      DefaultHttpHeaders.HeaderEntry before;
      DefaultHttpHeaders.HeaderEntry after;

      HeaderEntry(int hash, CharSequence key, CharSequence value) {
         this.hash = hash;
         this.key = key;
         this.value = value;
      }

      HeaderEntry() {
         this.hash = -1;
         this.key = null;
         this.value = null;
      }

      void remove() {
         this.before.after = this.after;
         this.after.before = this.before;
      }

      void addBefore(DefaultHttpHeaders.HeaderEntry e) {
         this.after = e;
         this.before = e.before;
         this.before.after = this;
         this.after.before = this;
      }

      public String getKey() {
         return this.key.toString();
      }

      public String getValue() {
         return this.value.toString();
      }

      public String setValue(String value) {
         if (value == null) {
            throw new NullPointerException("value");
         } else {
            HttpHeaders.validateHeaderValue(value);
            CharSequence oldValue = this.value;
            this.value = value;
            return oldValue.toString();
         }
      }

      @Override
      public String toString() {
         return this.key.toString() + '=' + this.value.toString();
      }

      void encode(ByteBuf buf) {
         HttpHeaders.encode(this.key, this.value, buf);
      }
   }

   private final class HeaderIterator implements Iterator<Entry<String, String>> {
      private DefaultHttpHeaders.HeaderEntry current = DefaultHttpHeaders.this.head;

      private HeaderIterator() {
      }

      @Override
      public boolean hasNext() {
         return this.current.after != DefaultHttpHeaders.this.head;
      }

      public Entry<String, String> next() {
         this.current = this.current.after;
         if (this.current == DefaultHttpHeaders.this.head) {
            throw new NoSuchElementException();
         } else {
            return this.current;
         }
      }

      @Override
      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
}
