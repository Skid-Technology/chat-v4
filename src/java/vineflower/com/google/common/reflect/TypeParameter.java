package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import javax.annotation.Nullable;

@Beta
public abstract class TypeParameter<T> extends TypeCapture<T> {
   final TypeVariable<?> typeVariable;

   protected TypeParameter() {
      Type type = this.capture();
      Preconditions.checkArgument(type instanceof TypeVariable, "%s should be a type variable.", type);
      this.typeVariable = (TypeVariable<?>)type;
   }

   @Override
   public final int hashCode() {
      return this.typeVariable.hashCode();
   }

   @Override
   public final boolean equals(@Nullable Object o) {
      if (o instanceof TypeParameter) {
         TypeParameter<?> that = (TypeParameter<?>)o;
         return this.typeVariable.equals(that.typeVariable);
      } else {
         return false;
      }
   }

   @Override
   public String toString() {
      return this.typeVariable.toString();
   }
}
