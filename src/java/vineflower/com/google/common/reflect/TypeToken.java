package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Primitives;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@Beta
public abstract class TypeToken<T> extends TypeCapture<T> implements Serializable {
   private final Type runtimeType;
   private transient TypeResolver typeResolver;

   protected TypeToken() {
      this.runtimeType = this.capture();
      Preconditions.checkState(
         !(this.runtimeType instanceof TypeVariable),
         "Cannot construct a TypeToken for a type variable.\nYou probably meant to call new TypeToken<%s>(getClass()) that can resolve the type variable for you.\nIf you do need to create a TypeToken of a type variable, please use TypeToken.of() instead.",
         this.runtimeType
      );
   }

   protected TypeToken(Class<?> declaringClass) {
      Type captured = super.capture();
      if (captured instanceof Class) {
         this.runtimeType = captured;
      } else {
         this.runtimeType = of(declaringClass).resolveType(captured).runtimeType;
      }
   }

   private TypeToken(Type type) {
      this.runtimeType = Preconditions.checkNotNull(type);
   }

   public static <T> TypeToken<T> of(Class<T> type) {
      return new TypeToken.SimpleTypeToken<>(type);
   }

   public static TypeToken<?> of(Type type) {
      return new TypeToken.SimpleTypeToken(type);
   }

   public final Class<? super T> getRawType() {
      return (Class<? super T>)getRawType(this.runtimeType);
   }

   private ImmutableSet<Class<? super T>> getImmediateRawTypes() {
      return (ImmutableSet<Class<? super T>>)getRawTypes(this.runtimeType);
   }

   public final Type getType() {
      return this.runtimeType;
   }

   public final <X> TypeToken<T> where(TypeParameter<X> typeParam, TypeToken<X> typeArg) {
      TypeResolver resolver = new TypeResolver().where(ImmutableMap.of(new TypeResolver.TypeVariableKey(typeParam.typeVariable), typeArg.runtimeType));
      return new TypeToken.SimpleTypeToken<>(resolver.resolveType(this.runtimeType));
   }

   public final <X> TypeToken<T> where(TypeParameter<X> typeParam, Class<X> typeArg) {
      return this.where(typeParam, of(typeArg));
   }

   public final TypeToken<?> resolveType(Type type) {
      Preconditions.checkNotNull(type);
      TypeResolver resolver = this.typeResolver;
      if (resolver == null) {
         resolver = this.typeResolver = TypeResolver.accordingTo(this.runtimeType);
      }

      return of(resolver.resolveType(type));
   }

   private Type[] resolveInPlace(Type[] types) {
      for (int i = 0; i < types.length; i++) {
         types[i] = this.resolveType(types[i]).getType();
      }

      return types;
   }

   private TypeToken<?> resolveSupertype(Type type) {
      TypeToken<?> supertype = this.resolveType(type);
      supertype.typeResolver = this.typeResolver;
      return supertype;
   }

   @Nullable
   final TypeToken<? super T> getGenericSuperclass() {
      if (this.runtimeType instanceof TypeVariable) {
         return this.boundAsSuperclass(((TypeVariable)this.runtimeType).getBounds()[0]);
      } else if (this.runtimeType instanceof WildcardType) {
         return this.boundAsSuperclass(((WildcardType)this.runtimeType).getUpperBounds()[0]);
      } else {
         Type superclass = this.getRawType().getGenericSuperclass();
         return (TypeToken<? super T>)(superclass == null ? null : this.resolveSupertype(superclass));
      }
   }

   @Nullable
   private TypeToken<? super T> boundAsSuperclass(Type bound) {
      TypeToken<?> token = of(bound);
      return (TypeToken<? super T>)(token.getRawType().isInterface() ? null : token);
   }

   final ImmutableList<TypeToken<? super T>> getGenericInterfaces() {
      if (this.runtimeType instanceof TypeVariable) {
         return this.boundsAsInterfaces(((TypeVariable)this.runtimeType).getBounds());
      } else if (this.runtimeType instanceof WildcardType) {
         return this.boundsAsInterfaces(((WildcardType)this.runtimeType).getUpperBounds());
      } else {
         ImmutableList.Builder<TypeToken<? super T>> builder = ImmutableList.builder();

         for (Type interfaceType : this.getRawType().getGenericInterfaces()) {
            TypeToken<? super T> resolvedInterface = (TypeToken<? super T>)this.resolveSupertype(interfaceType);
            builder.add(resolvedInterface);
         }

         return builder.build();
      }
   }

   private ImmutableList<TypeToken<? super T>> boundsAsInterfaces(Type[] bounds) {
      ImmutableList.Builder<TypeToken<? super T>> builder = ImmutableList.builder();

      for (Type bound : bounds) {
         TypeToken<? super T> boundType = (TypeToken<? super T>)of(bound);
         if (boundType.getRawType().isInterface()) {
            builder.add(boundType);
         }
      }

      return builder.build();
   }

   public final TypeToken<T>.TypeSet getTypes() {
      return new TypeToken.TypeSet();
   }

   public final TypeToken<? super T> getSupertype(Class<? super T> superclass) {
      Preconditions.checkArgument(superclass.isAssignableFrom(this.getRawType()), "%s is not a super class of %s", superclass, this);
      if (this.runtimeType instanceof TypeVariable) {
         return this.getSupertypeFromUpperBounds(superclass, ((TypeVariable)this.runtimeType).getBounds());
      } else if (this.runtimeType instanceof WildcardType) {
         return this.getSupertypeFromUpperBounds(superclass, ((WildcardType)this.runtimeType).getUpperBounds());
      } else {
         return superclass.isArray() ? this.getArraySupertype(superclass) : this.resolveSupertype(toGenericType(superclass).runtimeType);
      }
   }

   public final TypeToken<? extends T> getSubtype(Class<?> subclass) {
      Preconditions.checkArgument(!(this.runtimeType instanceof TypeVariable), "Cannot get subtype of type variable <%s>", this);
      if (this.runtimeType instanceof WildcardType) {
         return this.getSubtypeFromLowerBounds(subclass, ((WildcardType)this.runtimeType).getLowerBounds());
      } else {
         Preconditions.checkArgument(this.getRawType().isAssignableFrom(subclass), "%s isn't a subclass of %s", subclass, this);
         return this.isArray() ? this.getArraySubtype(subclass) : of(this.resolveTypeArgsForSubclass(subclass));
      }
   }

   public final boolean isAssignableFrom(TypeToken<?> type) {
      return this.isAssignableFrom(type.runtimeType);
   }

   public final boolean isAssignableFrom(Type type) {
      return isAssignable(Preconditions.checkNotNull(type), this.runtimeType);
   }

   public final boolean isArray() {
      return this.getComponentType() != null;
   }

   public final boolean isPrimitive() {
      return this.runtimeType instanceof Class && ((Class)this.runtimeType).isPrimitive();
   }

   public final TypeToken<T> wrap() {
      if (this.isPrimitive()) {
         Class<T> type = (Class<T>)this.runtimeType;
         return of(Primitives.wrap(type));
      } else {
         return this;
      }
   }

   private boolean isWrapper() {
      return Primitives.allWrapperTypes().contains(this.runtimeType);
   }

   public final TypeToken<T> unwrap() {
      if (this.isWrapper()) {
         Class<T> type = (Class<T>)this.runtimeType;
         return of(Primitives.unwrap(type));
      } else {
         return this;
      }
   }

   @Nullable
   public final TypeToken<?> getComponentType() {
      Type componentType = Types.getComponentType(this.runtimeType);
      return componentType == null ? null : of(componentType);
   }

   public final Invokable<T, Object> method(Method method) {
      Preconditions.checkArgument(of(method.getDeclaringClass()).isAssignableFrom(this), "%s not declared by %s", method, this);
      return new Invokable.MethodInvokable<T>(method) {
         @Override
         Type getGenericReturnType() {
            return TypeToken.this.resolveType(super.getGenericReturnType()).getType();
         }

         @Override
         Type[] getGenericParameterTypes() {
            return TypeToken.this.resolveInPlace(super.getGenericParameterTypes());
         }

         @Override
         Type[] getGenericExceptionTypes() {
            return TypeToken.this.resolveInPlace(super.getGenericExceptionTypes());
         }

         @Override
         public TypeToken<T> getOwnerType() {
            return TypeToken.this;
         }

         @Override
         public String toString() {
            return this.getOwnerType() + "." + super.toString();
         }
      };
   }

   public final Invokable<T, T> constructor(Constructor<?> constructor) {
      Preconditions.checkArgument(constructor.getDeclaringClass() == this.getRawType(), "%s not declared by %s", constructor, this.getRawType());
      return new Invokable.ConstructorInvokable<T>(constructor) {
         @Override
         Type getGenericReturnType() {
            return TypeToken.this.resolveType(super.getGenericReturnType()).getType();
         }

         @Override
         Type[] getGenericParameterTypes() {
            return TypeToken.this.resolveInPlace(super.getGenericParameterTypes());
         }

         @Override
         Type[] getGenericExceptionTypes() {
            return TypeToken.this.resolveInPlace(super.getGenericExceptionTypes());
         }

         @Override
         public TypeToken<T> getOwnerType() {
            return TypeToken.this;
         }

         @Override
         public String toString() {
            return this.getOwnerType() + "(" + Joiner.on(", ").join(this.getGenericParameterTypes()) + ")";
         }
      };
   }

   @Override
   public boolean equals(@Nullable Object o) {
      if (o instanceof TypeToken) {
         TypeToken<?> that = (TypeToken<?>)o;
         return this.runtimeType.equals(that.runtimeType);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.runtimeType.hashCode();
   }

   @Override
   public String toString() {
      return Types.toString(this.runtimeType);
   }

   protected Object writeReplace() {
      return of(new TypeResolver().resolveType(this.runtimeType));
   }

   final TypeToken<T> rejectTypeVariables() {
      (new TypeVisitor() {
         @Override
         void visitTypeVariable(TypeVariable<?> type) {
            throw new IllegalArgumentException(TypeToken.this.runtimeType + "contains a type variable and is not safe for the operation");
         }

         @Override
         void visitWildcardType(WildcardType type) {
            this.visit(type.getLowerBounds());
            this.visit(type.getUpperBounds());
         }

         @Override
         void visitParameterizedType(ParameterizedType type) {
            this.visit(type.getActualTypeArguments());
            this.visit(type.getOwnerType());
         }

         @Override
         void visitGenericArrayType(GenericArrayType type) {
            this.visit(type.getGenericComponentType());
         }
      }).visit(this.runtimeType);
      return this;
   }

   private static boolean isAssignable(Type from, Type to) {
      if (to.equals(from)) {
         return true;
      } else if (to instanceof WildcardType) {
         return isAssignableToWildcardType(from, (WildcardType)to);
      } else if (from instanceof TypeVariable) {
         return isAssignableFromAny(((TypeVariable)from).getBounds(), to);
      } else if (from instanceof WildcardType) {
         return isAssignableFromAny(((WildcardType)from).getUpperBounds(), to);
      } else if (from instanceof GenericArrayType) {
         return isAssignableFromGenericArrayType((GenericArrayType)from, to);
      } else if (to instanceof Class) {
         return isAssignableToClass(from, (Class<?>)to);
      } else if (to instanceof ParameterizedType) {
         return isAssignableToParameterizedType(from, (ParameterizedType)to);
      } else {
         return to instanceof GenericArrayType ? isAssignableToGenericArrayType(from, (GenericArrayType)to) : false;
      }
   }

   private static boolean isAssignableFromAny(Type[] fromTypes, Type to) {
      for (Type from : fromTypes) {
         if (isAssignable(from, to)) {
            return true;
         }
      }

      return false;
   }

   private static boolean isAssignableToClass(Type from, Class<?> to) {
      return to.isAssignableFrom(getRawType(from));
   }

   private static boolean isAssignableToWildcardType(Type from, WildcardType to) {
      return isAssignable(from, supertypeBound(to)) && isAssignableBySubtypeBound(from, to);
   }

   private static boolean isAssignableBySubtypeBound(Type from, WildcardType to) {
      Type toSubtypeBound = subtypeBound(to);
      if (toSubtypeBound == null) {
         return true;
      } else {
         Type fromSubtypeBound = subtypeBound(from);
         return fromSubtypeBound == null ? false : isAssignable(toSubtypeBound, fromSubtypeBound);
      }
   }

   private static boolean isAssignableToParameterizedType(Type from, ParameterizedType to) {
      Class<?> matchedClass = getRawType(to);
      if (!matchedClass.isAssignableFrom(getRawType(from))) {
         return false;
      } else {
         Type[] typeParams = matchedClass.getTypeParameters();
         Type[] toTypeArgs = to.getActualTypeArguments();
         TypeToken<?> fromTypeToken = of(from);

         for (int i = 0; i < typeParams.length; i++) {
            Type fromTypeArg = fromTypeToken.resolveType(typeParams[i]).runtimeType;
            if (!matchTypeArgument(fromTypeArg, toTypeArgs[i])) {
               return false;
            }
         }

         return true;
      }
   }

   private static boolean isAssignableToGenericArrayType(Type from, GenericArrayType to) {
      if (from instanceof Class) {
         Class<?> fromClass = (Class<?>)from;
         return !fromClass.isArray() ? false : isAssignable(fromClass.getComponentType(), to.getGenericComponentType());
      } else if (from instanceof GenericArrayType) {
         GenericArrayType fromArrayType = (GenericArrayType)from;
         return isAssignable(fromArrayType.getGenericComponentType(), to.getGenericComponentType());
      } else {
         return false;
      }
   }

   private static boolean isAssignableFromGenericArrayType(GenericArrayType from, Type to) {
      if (to instanceof Class) {
         Class<?> toClass = (Class<?>)to;
         return !toClass.isArray() ? toClass == Object.class : isAssignable(from.getGenericComponentType(), toClass.getComponentType());
      } else if (to instanceof GenericArrayType) {
         GenericArrayType toArrayType = (GenericArrayType)to;
         return isAssignable(from.getGenericComponentType(), toArrayType.getGenericComponentType());
      } else {
         return false;
      }
   }

   private static boolean matchTypeArgument(Type from, Type to) {
      if (from.equals(to)) {
         return true;
      } else {
         return to instanceof WildcardType ? isAssignableToWildcardType(from, (WildcardType)to) : false;
      }
   }

   private static Type supertypeBound(Type type) {
      return type instanceof WildcardType ? supertypeBound((WildcardType)type) : type;
   }

   private static Type supertypeBound(WildcardType type) {
      Type[] upperBounds = type.getUpperBounds();
      if (upperBounds.length == 1) {
         return supertypeBound(upperBounds[0]);
      } else if (upperBounds.length == 0) {
         return Object.class;
      } else {
         throw new AssertionError("There should be at most one upper bound for wildcard type: " + type);
      }
   }

   @Nullable
   private static Type subtypeBound(Type type) {
      return type instanceof WildcardType ? subtypeBound((WildcardType)type) : type;
   }

   @Nullable
   private static Type subtypeBound(WildcardType type) {
      Type[] lowerBounds = type.getLowerBounds();
      if (lowerBounds.length == 1) {
         return subtypeBound(lowerBounds[0]);
      } else if (lowerBounds.length == 0) {
         return null;
      } else {
         throw new AssertionError("Wildcard should have at most one lower bound: " + type);
      }
   }

   @VisibleForTesting
   static Class<?> getRawType(Type type) {
      return getRawTypes(type).iterator().next();
   }

   @VisibleForTesting
   static ImmutableSet<Class<?>> getRawTypes(Type type) {
      Preconditions.checkNotNull(type);
      final ImmutableSet.Builder<Class<?>> builder = ImmutableSet.builder();
      (new TypeVisitor() {
         @Override
         void visitTypeVariable(TypeVariable<?> t) {
            this.visit(t.getBounds());
         }

         @Override
         void visitWildcardType(WildcardType t) {
            this.visit(t.getUpperBounds());
         }

         @Override
         void visitParameterizedType(ParameterizedType t) {
            builder.add((Class<?>)t.getRawType());
         }

         @Override
         void visitClass(Class<?> t) {
            builder.add(t);
         }

         @Override
         void visitGenericArrayType(GenericArrayType t) {
            builder.add(Types.getArrayClass(TypeToken.getRawType(t.getGenericComponentType())));
         }
      }).visit(type);
      return builder.build();
   }

   @VisibleForTesting
   static <T> TypeToken<? extends T> toGenericType(Class<T> cls) {
      if (cls.isArray()) {
         Type arrayOfGenericType = Types.newArrayType(toGenericType(cls.getComponentType()).runtimeType);
         return (TypeToken<? extends T>)of(arrayOfGenericType);
      } else {
         TypeVariable<Class<T>>[] typeParams = cls.getTypeParameters();
         return (TypeToken<? extends T>)(typeParams.length > 0 ? of(Types.newParameterizedType(cls, typeParams)) : of(cls));
      }
   }

   private TypeToken<? super T> getSupertypeFromUpperBounds(Class<? super T> supertype, Type[] upperBounds) {
      for (Type upperBound : upperBounds) {
         TypeToken<? super T> bound = (TypeToken<? super T>)of(upperBound);
         if (of(supertype).isAssignableFrom(bound)) {
            return bound.getSupertype(supertype);
         }
      }

      throw new IllegalArgumentException(supertype + " isn't a super type of " + this);
   }

   private TypeToken<? extends T> getSubtypeFromLowerBounds(Class<?> subclass, Type[] lowerBounds) {
      int len$ = lowerBounds.length;
      int i$ = 0;
      if (i$ < len$) {
         Type lowerBound = lowerBounds[i$];
         TypeToken<? extends T> bound = (TypeToken<? extends T>)of(lowerBound);
         return bound.getSubtype(subclass);
      } else {
         throw new IllegalArgumentException(subclass + " isn't a subclass of " + this);
      }
   }

   private TypeToken<? super T> getArraySupertype(Class<? super T> supertype) {
      TypeToken componentType = Preconditions.checkNotNull(this.getComponentType(), "%s isn't a super type of %s", supertype, this);
      TypeToken<?> componentSupertype = componentType.getSupertype((Class<? super T>)supertype.getComponentType());
      return (TypeToken<? super T>)of(newArrayClassOrGenericArrayType(componentSupertype.runtimeType));
   }

   private TypeToken<? extends T> getArraySubtype(Class<?> subclass) {
      TypeToken<?> componentSubtype = this.getComponentType().getSubtype(subclass.getComponentType());
      return (TypeToken<? extends T>)of(newArrayClassOrGenericArrayType(componentSubtype.runtimeType));
   }

   private Type resolveTypeArgsForSubclass(Class<?> subclass) {
      if (this.runtimeType instanceof Class) {
         return subclass;
      } else {
         TypeToken<?> genericSubtype = toGenericType(subclass);
         Type supertypeWithArgsFromSubtype = genericSubtype.getSupertype(this.getRawType()).runtimeType;
         return new TypeResolver().where(supertypeWithArgsFromSubtype, this.runtimeType).resolveType(genericSubtype.runtimeType);
      }
   }

   private static Type newArrayClassOrGenericArrayType(Type componentType) {
      return Types.JavaVersion.JAVA7.newArrayType(componentType);
   }

   private final class ClassSet extends TypeToken<T>.TypeSet {
      private transient ImmutableSet<TypeToken<? super T>> classes;
      private static final long serialVersionUID = 0L;

      private ClassSet() {
      }

      @Override
      protected Set<TypeToken<? super T>> delegate() {
         ImmutableSet<TypeToken<? super T>> result = this.classes;
         if (result == null) {
            ImmutableList<TypeToken<? super T>> collectedTypes = (ImmutableList<TypeToken<? super T>>)TypeToken.TypeCollector.FOR_GENERIC_TYPE
               .classesOnly()
               .collectTypes(TypeToken.this);
            return this.classes = FluentIterable.from(collectedTypes).filter(TypeToken.TypeFilter.IGNORE_TYPE_VARIABLE_OR_WILDCARD).toSet();
         } else {
            return result;
         }
      }

      @Override
      public TypeToken<T>.TypeSet classes() {
         return this;
      }

      @Override
      public Set<Class<? super T>> rawTypes() {
         ImmutableList<Class<? super T>> collectedTypes = (ImmutableList<Class<? super T>>)TypeToken.TypeCollector.FOR_RAW_TYPE
            .classesOnly()
            .collectTypes(TypeToken.this.getImmediateRawTypes());
         return ImmutableSet.copyOf(collectedTypes);
      }

      @Override
      public TypeToken<T>.TypeSet interfaces() {
         throw new UnsupportedOperationException("classes().interfaces() not supported.");
      }

      private Object readResolve() {
         return TypeToken.this.getTypes().classes();
      }
   }

   private final class InterfaceSet extends TypeToken<T>.TypeSet {
      private final transient TypeToken<T>.TypeSet allTypes;
      private transient ImmutableSet<TypeToken<? super T>> interfaces;
      private static final long serialVersionUID = 0L;

      InterfaceSet(TypeToken<T>.TypeSet allTypes) {
         this.allTypes = allTypes;
      }

      @Override
      protected Set<TypeToken<? super T>> delegate() {
         ImmutableSet<TypeToken<? super T>> result = this.interfaces;
         return result == null ? (this.interfaces = FluentIterable.from(this.allTypes).filter(TypeToken.TypeFilter.INTERFACE_ONLY).toSet()) : result;
      }

      @Override
      public TypeToken<T>.TypeSet interfaces() {
         return this;
      }

      @Override
      public Set<Class<? super T>> rawTypes() {
         ImmutableList<Class<? super T>> collectedTypes = (ImmutableList<Class<? super T>>)TypeToken.TypeCollector.FOR_RAW_TYPE
            .collectTypes(TypeToken.this.getImmediateRawTypes());
         return FluentIterable.from(collectedTypes).filter(new Predicate<Class<?>>() {
            public boolean apply(Class<?> type) {
               return type.isInterface();
            }
         }).toSet();
      }

      @Override
      public TypeToken<T>.TypeSet classes() {
         throw new UnsupportedOperationException("interfaces().classes() not supported.");
      }

      private Object readResolve() {
         return TypeToken.this.getTypes().interfaces();
      }
   }

   private static final class SimpleTypeToken<T> extends TypeToken<T> {
      private static final long serialVersionUID = 0L;

      SimpleTypeToken(Type type) {
         super(type);
      }
   }

   private abstract static class TypeCollector<K> {
      static final TypeToken.TypeCollector<TypeToken<?>> FOR_GENERIC_TYPE = new TypeToken.TypeCollector<TypeToken<?>>() {
         Class<?> getRawType(TypeToken<?> type) {
            return type.getRawType();
         }

         Iterable<? extends TypeToken<?>> getInterfaces(TypeToken<?> type) {
            return type.getGenericInterfaces();
         }

         @Nullable
         TypeToken<?> getSuperclass(TypeToken<?> type) {
            return type.getGenericSuperclass();
         }
      };
      static final TypeToken.TypeCollector<Class<?>> FOR_RAW_TYPE = new TypeToken.TypeCollector<Class<?>>() {
         Class<?> getRawType(Class<?> type) {
            return type;
         }

         Iterable<? extends Class<?>> getInterfaces(Class<?> type) {
            return Arrays.asList(type.getInterfaces());
         }

         @Nullable
         Class<?> getSuperclass(Class<?> type) {
            return type.getSuperclass();
         }
      };

      private TypeCollector() {
      }

      final TypeToken.TypeCollector<K> classesOnly() {
         return new TypeToken.TypeCollector.ForwardingTypeCollector<K>(this) {
            @Override
            Iterable<? extends K> getInterfaces(K type) {
               return ImmutableSet.of();
            }

            @Override
            ImmutableList<K> collectTypes(Iterable<? extends K> types) {
               ImmutableList.Builder<K> builder = ImmutableList.builder();

               for (K type : types) {
                  if (!this.getRawType(type).isInterface()) {
                     builder.add(type);
                  }
               }

               return super.collectTypes(builder.build());
            }
         };
      }

      final ImmutableList<K> collectTypes(K type) {
         return this.collectTypes(ImmutableList.of(type));
      }

      ImmutableList<K> collectTypes(Iterable<? extends K> types) {
         Map<K, Integer> map = Maps.newHashMap();

         for (K type : types) {
            this.collectTypes(type, map);
         }

         return sortKeysByValue(map, Ordering.natural().reverse());
      }

      private int collectTypes(K type, Map<? super K, Integer> map) {
         Integer existing = map.get(this);
         if (existing != null) {
            return existing;
         } else {
            int aboveMe = this.getRawType(type).isInterface() ? 1 : 0;

            for (K interfaceType : this.getInterfaces(type)) {
               aboveMe = Math.max(aboveMe, this.collectTypes(interfaceType, map));
            }

            K superclass = this.getSuperclass(type);
            if (superclass != null) {
               aboveMe = Math.max(aboveMe, this.collectTypes(superclass, map));
            }

            map.put(type, aboveMe + 1);
            return aboveMe + 1;
         }
      }

      private static <K, V> ImmutableList<K> sortKeysByValue(final Map<K, V> map, final Comparator<? super V> valueComparator) {
         Ordering<K> keyOrdering = new Ordering<K>() {
            @Override
            public int compare(K left, K right) {
               return valueComparator.compare(map.get(left), map.get(right));
            }
         };
         return keyOrdering.immutableSortedCopy(map.keySet());
      }

      abstract Class<?> getRawType(K var1);

      abstract Iterable<? extends K> getInterfaces(K var1);

      @Nullable
      abstract K getSuperclass(K var1);

      private static class ForwardingTypeCollector<K> extends TypeToken.TypeCollector<K> {
         private final TypeToken.TypeCollector<K> delegate;

         ForwardingTypeCollector(TypeToken.TypeCollector<K> delegate) {
            this.delegate = delegate;
         }

         @Override
         Class<?> getRawType(K type) {
            return this.delegate.getRawType(type);
         }

         @Override
         Iterable<? extends K> getInterfaces(K type) {
            return this.delegate.getInterfaces(type);
         }

         @Override
         K getSuperclass(K type) {
            return this.delegate.getSuperclass(type);
         }
      }
   }

   private static enum TypeFilter implements Predicate<TypeToken<?>> {
      IGNORE_TYPE_VARIABLE_OR_WILDCARD {
         public boolean apply(TypeToken<?> type) {
            return !(type.runtimeType instanceof TypeVariable) && !(type.runtimeType instanceof WildcardType);
         }
      },
      INTERFACE_ONLY {
         public boolean apply(TypeToken<?> type) {
            return type.getRawType().isInterface();
         }
      };

      private TypeFilter() {
      }
   }

   public class TypeSet extends ForwardingSet<TypeToken<? super T>> implements Serializable {
      private transient ImmutableSet<TypeToken<? super T>> types;
      private static final long serialVersionUID = 0L;

      TypeSet() {
      }

      public TypeToken<T>.TypeSet interfaces() {
         return TypeToken.this.new InterfaceSet(this);
      }

      public TypeToken<T>.TypeSet classes() {
         return TypeToken.this.new ClassSet();
      }

      @Override
      protected Set<TypeToken<? super T>> delegate() {
         ImmutableSet<TypeToken<? super T>> filteredTypes = this.types;
         if (filteredTypes == null) {
            ImmutableList<TypeToken<? super T>> collectedTypes = (ImmutableList<TypeToken<? super T>>)TypeToken.TypeCollector.FOR_GENERIC_TYPE
               .collectTypes(TypeToken.this);
            return this.types = FluentIterable.from(collectedTypes).filter(TypeToken.TypeFilter.IGNORE_TYPE_VARIABLE_OR_WILDCARD).toSet();
         } else {
            return filteredTypes;
         }
      }

      public Set<Class<? super T>> rawTypes() {
         ImmutableList<Class<? super T>> collectedTypes = (ImmutableList<Class<? super T>>)TypeToken.TypeCollector.FOR_RAW_TYPE
            .collectTypes(TypeToken.this.getImmediateRawTypes());
         return ImmutableSet.copyOf(collectedTypes);
      }
   }
}
