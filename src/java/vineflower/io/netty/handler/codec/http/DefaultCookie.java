package io.netty.handler.codec.http;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class DefaultCookie implements Cookie {
   private final String name;
   private String value;
   private String domain;
   private String path;
   private String comment;
   private String commentUrl;
   private boolean discard;
   private Set<Integer> ports = Collections.emptySet();
   private Set<Integer> unmodifiablePorts = this.ports;
   private long maxAge = Long.MIN_VALUE;
   private int version;
   private boolean secure;
   private boolean httpOnly;

   public DefaultCookie(String name, String value) {
      if (name == null) {
         throw new NullPointerException("name");
      } else {
         name = name.trim();
         if (name.isEmpty()) {
            throw new IllegalArgumentException("empty name");
         } else {
            for (int i = 0; i < name.length(); i++) {
               char c = name.charAt(i);
               if (c > 127) {
                  throw new IllegalArgumentException("name contains non-ascii character: " + name);
               }

               switch (c) {
                  case '\t':
                  case '\n':
                  case '\u000b':
                  case '\f':
                  case '\r':
                  case ' ':
                  case ',':
                  case ';':
                  case '=':
                     throw new IllegalArgumentException("name contains one of the following prohibited characters: =,; \\t\\r\\n\\v\\f: " + name);
               }
            }

            if (name.charAt(0) == '$') {
               throw new IllegalArgumentException("name starting with '$' not allowed: " + name);
            } else {
               this.name = name;
               this.setValue(value);
            }
         }
      }
   }

   @Override
   public String getName() {
      return this.name;
   }

   @Override
   public String getValue() {
      return this.value;
   }

   @Override
   public void setValue(String value) {
      if (value == null) {
         throw new NullPointerException("value");
      } else {
         this.value = value;
      }
   }

   @Override
   public String getDomain() {
      return this.domain;
   }

   @Override
   public void setDomain(String domain) {
      this.domain = validateValue("domain", domain);
   }

   @Override
   public String getPath() {
      return this.path;
   }

   @Override
   public void setPath(String path) {
      this.path = validateValue("path", path);
   }

   @Override
   public String getComment() {
      return this.comment;
   }

   @Override
   public void setComment(String comment) {
      this.comment = validateValue("comment", comment);
   }

   @Override
   public String getCommentUrl() {
      return this.commentUrl;
   }

   @Override
   public void setCommentUrl(String commentUrl) {
      this.commentUrl = validateValue("commentUrl", commentUrl);
   }

   @Override
   public boolean isDiscard() {
      return this.discard;
   }

   @Override
   public void setDiscard(boolean discard) {
      this.discard = discard;
   }

   @Override
   public Set<Integer> getPorts() {
      if (this.unmodifiablePorts == null) {
         this.unmodifiablePorts = Collections.unmodifiableSet(this.ports);
      }

      return this.unmodifiablePorts;
   }

   @Override
   public void setPorts(int... ports) {
      if (ports == null) {
         throw new NullPointerException("ports");
      } else {
         int[] portsCopy = (int[])ports.clone();
         if (portsCopy.length == 0) {
            this.unmodifiablePorts = this.ports = Collections.emptySet();
         } else {
            Set<Integer> newPorts = new TreeSet<>();

            for (int p : portsCopy) {
               if (p <= 0 || p > 65535) {
                  throw new IllegalArgumentException("port out of range: " + p);
               }

               newPorts.add(p);
            }

            this.ports = newPorts;
            this.unmodifiablePorts = null;
         }
      }
   }

   @Override
   public void setPorts(Iterable<Integer> ports) {
      Set<Integer> newPorts = new TreeSet<>();

      for (int p : ports) {
         if (p <= 0 || p > 65535) {
            throw new IllegalArgumentException("port out of range: " + p);
         }

         newPorts.add(p);
      }

      if (newPorts.isEmpty()) {
         this.unmodifiablePorts = this.ports = Collections.emptySet();
      } else {
         this.ports = newPorts;
         this.unmodifiablePorts = null;
      }
   }

   @Override
   public long getMaxAge() {
      return this.maxAge;
   }

   @Override
   public void setMaxAge(long maxAge) {
      this.maxAge = maxAge;
   }

   @Override
   public int getVersion() {
      return this.version;
   }

   @Override
   public void setVersion(int version) {
      this.version = version;
   }

   @Override
   public boolean isSecure() {
      return this.secure;
   }

   @Override
   public void setSecure(boolean secure) {
      this.secure = secure;
   }

   @Override
   public boolean isHttpOnly() {
      return this.httpOnly;
   }

   @Override
   public void setHttpOnly(boolean httpOnly) {
      this.httpOnly = httpOnly;
   }

   @Override
   public int hashCode() {
      return this.getName().hashCode();
   }

   @Override
   public boolean equals(Object o) {
      if (!(o instanceof Cookie)) {
         return false;
      } else {
         Cookie that = (Cookie)o;
         if (!this.getName().equalsIgnoreCase(that.getName())) {
            return false;
         } else {
            if (this.getPath() == null) {
               if (that.getPath() != null) {
                  return false;
               }
            } else {
               if (that.getPath() == null) {
                  return false;
               }

               if (!this.getPath().equals(that.getPath())) {
                  return false;
               }
            }

            if (this.getDomain() == null) {
               return that.getDomain() == null;
            } else {
               return that.getDomain() == null ? false : this.getDomain().equalsIgnoreCase(that.getDomain());
            }
         }
      }
   }

   public int compareTo(Cookie c) {
      int v = this.getName().compareToIgnoreCase(c.getName());
      if (v != 0) {
         return v;
      } else {
         if (this.getPath() == null) {
            if (c.getPath() != null) {
               return -1;
            }
         } else {
            if (c.getPath() == null) {
               return 1;
            }

            v = this.getPath().compareTo(c.getPath());
            if (v != 0) {
               return v;
            }
         }

         if (this.getDomain() == null) {
            return c.getDomain() != null ? -1 : 0;
         } else {
            return c.getDomain() == null ? 1 : this.getDomain().compareToIgnoreCase(c.getDomain());
         }
      }
   }

   @Override
   public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append(this.getName());
      buf.append('=');
      buf.append(this.getValue());
      if (this.getDomain() != null) {
         buf.append(", domain=");
         buf.append(this.getDomain());
      }

      if (this.getPath() != null) {
         buf.append(", path=");
         buf.append(this.getPath());
      }

      if (this.getComment() != null) {
         buf.append(", comment=");
         buf.append(this.getComment());
      }

      if (this.getMaxAge() >= 0L) {
         buf.append(", maxAge=");
         buf.append(this.getMaxAge());
         buf.append('s');
      }

      if (this.isSecure()) {
         buf.append(", secure");
      }

      if (this.isHttpOnly()) {
         buf.append(", HTTPOnly");
      }

      return buf.toString();
   }

   private static String validateValue(String name, String value) {
      if (value == null) {
         return null;
      } else {
         value = value.trim();
         if (value.isEmpty()) {
            return null;
         } else {
            for (int i = 0; i < value.length(); i++) {
               char c = value.charAt(i);
               switch (c) {
                  case '\n':
                  case '\u000b':
                  case '\f':
                  case '\r':
                  case ';':
                     throw new IllegalArgumentException(name + " contains one of the following prohibited characters: " + ";\\r\\n\\f\\v (" + value + ')');
               }
            }

            return value;
         }
      }
   }
}
