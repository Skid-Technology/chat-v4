package gg.vape.shaded.org.objectweb.asm.tree.analysis;

import gg.vape.shaded.org.objectweb.asm.Type;
import java.util.List;

public class SimpleVerifier extends BasicVerifier {
   private static final Type OBJECT_TYPE = Type.getObjectType("java/lang/Object");
   private final Type currentClass;
   private final Type currentSuperClass;
   private final List<Type> currentClassInterfaces;
   private final boolean isInterface;
   private ClassLoader loader = this.getClass().getClassLoader();

   public SimpleVerifier() {
      this(null, null, false);
   }

   public SimpleVerifier(Type currentClass, Type currentSuperClass, boolean isInterface) {
      this(currentClass, currentSuperClass, null, isInterface);
   }

   public SimpleVerifier(Type currentClass, Type currentSuperClass, List<Type> currentClassInterfaces, boolean isInterface) {
      this(589824, currentClass, currentSuperClass, currentClassInterfaces, isInterface);
      if (this.getClass() != SimpleVerifier.class) {
         throw new IllegalStateException();
      }
   }

   protected SimpleVerifier(int api, Type currentClass, Type currentSuperClass, List<Type> currentClassInterfaces, boolean isInterface) {
      super(api);
      this.currentClass = currentClass;
      this.currentSuperClass = currentSuperClass;
      this.currentClassInterfaces = currentClassInterfaces;
      this.isInterface = isInterface;
   }

   public void setClassLoader(ClassLoader loader) {
      this.loader = loader;
   }

   @Override
   public BasicValue newValue(Type type) {
      if (type == null) {
         return BasicValue.UNINITIALIZED_VALUE;
      } else {
         boolean isArray = type.getSort() == 9;
         if (isArray) {
            switch (type.getElementType().getSort()) {
               case 1:
               case 2:
               case 3:
               case 4:
                  return new BasicValue(type);
            }
         }

         BasicValue value = super.newValue(type);
         if (BasicValue.REFERENCE_VALUE.equals(value)) {
            if (isArray) {
               value = this.newValue(type.getElementType());
               StringBuilder descriptor = new StringBuilder();

               for (int i = 0; i < type.getDimensions(); i++) {
                  descriptor.append('[');
               }

               descriptor.append(value.getType().getDescriptor());
               value = new BasicValue(Type.getType(descriptor.toString()));
            } else {
               value = new BasicValue(type);
            }
         }

         return value;
      }
   }

   @Override
   protected boolean isArrayValue(BasicValue value) {
      Type type = value.getType();
      return type != null && (type.getSort() == 9 || type.equals(NULL_TYPE));
   }

   @Override
   protected BasicValue getElementValue(BasicValue objectArrayValue) throws AnalyzerException {
      Type arrayType = objectArrayValue.getType();
      if (arrayType != null) {
         if (arrayType.getSort() == 9) {
            return this.newValue(Type.getType(arrayType.getDescriptor().substring(1)));
         }

         if (arrayType.equals(NULL_TYPE)) {
            return objectArrayValue;
         }
      }

      throw new AssertionError();
   }

   @Override
   protected boolean isSubTypeOf(BasicValue value, BasicValue expected) {
      Type type = value.getType();
      Type expectedType = expected.getType();
      if (type != null && expectedType != null) {
         if (type.equals(expectedType)) {
            return true;
         } else {
            switch (expectedType.getSort()) {
               case 5:
               case 6:
               case 7:
               case 8:
                  return false;
               case 9:
               case 10:
                  if (type.equals(NULL_TYPE)) {
                     return true;
                  } else {
                     int dim = 0;
                     if (type.getSort() == 9) {
                        dim = type.getDimensions();
                        type = type.getElementType();
                        if (type.getSort() != 10) {
                           dim--;
                           type = OBJECT_TYPE;
                        }
                     }

                     int expectedDim = 0;
                     if (expectedType.getSort() == 9) {
                        expectedDim = expectedType.getDimensions();
                        expectedType = expectedType.getElementType();
                        if (expectedType.getSort() != 10) {
                           return false;
                        }
                     }

                     if (dim < expectedDim) {
                        return false;
                     } else {
                        if (dim > expectedDim) {
                           type = OBJECT_TYPE;
                        }

                        if (this.isAssignableFrom(expectedType, type)) {
                           return true;
                        } else {
                           if (this.getClass(expectedType).isInterface()) {
                              return Object.class.isAssignableFrom(this.getClass(type));
                           }

                           return false;
                        }
                     }
                  }
               default:
                  throw new AssertionError();
            }
         }
      } else {
         return type == null && expectedType == null;
      }
   }

   @Override
   public BasicValue merge(BasicValue value1, BasicValue value2) {
      Type type1 = value1.getType();
      Type type2 = value2.getType();
      if (type1 == null || type2 == null) {
         return BasicValue.UNINITIALIZED_VALUE;
      } else if (type1.equals(type2)) {
         return value1;
      } else if (type1.getSort() != 10 && type1.getSort() != 9) {
         return BasicValue.UNINITIALIZED_VALUE;
      } else if (type2.getSort() != 10 && type2.getSort() != 9) {
         return BasicValue.UNINITIALIZED_VALUE;
      } else if (type1.equals(NULL_TYPE)) {
         return value2;
      } else if (type2.equals(NULL_TYPE)) {
         return value1;
      } else {
         int dim1 = 0;
         if (type1.getSort() == 9) {
            dim1 = type1.getDimensions();
            type1 = type1.getElementType();
            if (type1.getSort() != 10) {
               dim1--;
               type1 = OBJECT_TYPE;
            }
         }

         int dim2 = 0;
         if (type2.getSort() == 9) {
            dim2 = type2.getDimensions();
            type2 = type2.getElementType();
            if (type2.getSort() != 10) {
               dim2--;
               type2 = OBJECT_TYPE;
            }
         }

         if (dim1 != dim2) {
            return this.newArrayValue(OBJECT_TYPE, Math.min(dim1, dim2));
         } else if (this.isAssignableFrom(type1, type2)) {
            return this.newArrayValue(type1, dim1);
         } else if (this.isAssignableFrom(type2, type1)) {
            return this.newArrayValue(type2, dim1);
         } else {
            if (!this.isInterface(type1)) {
               while (!type1.equals(OBJECT_TYPE)) {
                  type1 = this.getSuperClass(type1);
                  if (this.isAssignableFrom(type1, type2)) {
                     return this.newArrayValue(type1, dim1);
                  }
               }
            }

            return this.newArrayValue(OBJECT_TYPE, dim1);
         }
      }
   }

   private BasicValue newArrayValue(Type type, int dimensions) {
      if (dimensions == 0) {
         return this.newValue(type);
      } else {
         StringBuilder descriptor = new StringBuilder();

         for (int i = 0; i < dimensions; i++) {
            descriptor.append('[');
         }

         descriptor.append(type.getDescriptor());
         return this.newValue(Type.getType(descriptor.toString()));
      }
   }

   protected boolean isInterface(Type type) {
      return this.currentClass != null && this.currentClass.equals(type) ? this.isInterface : this.getClass(type).isInterface();
   }

   protected Type getSuperClass(Type type) {
      if (this.currentClass != null && this.currentClass.equals(type)) {
         return this.currentSuperClass;
      } else {
         Class<?> superClass = this.getClass(type).getSuperclass();
         return superClass == null ? null : Type.getType(superClass);
      }
   }

   protected boolean isAssignableFrom(Type type1, Type type2) {
      if (type1.equals(type2)) {
         return true;
      } else if (this.currentClass != null && this.currentClass.equals(type1)) {
         Type superType2 = this.getSuperClass(type2);
         if (superType2 == null) {
            return false;
         } else {
            return !this.isInterface ? this.isAssignableFrom(type1, superType2) : type2.getSort() == 10 || type2.getSort() == 9;
         }
      } else if (this.currentClass == null || !this.currentClass.equals(type2)) {
         return this.getClass(type1).isAssignableFrom(this.getClass(type2));
      } else if (this.isAssignableFrom(type1, this.currentSuperClass)) {
         return true;
      } else {
         if (this.currentClassInterfaces != null) {
            for (Type currentClassInterface : this.currentClassInterfaces) {
               if (this.isAssignableFrom(type1, currentClassInterface)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   protected Class<?> getClass(Type type) {
      try {
         return type.getSort() == 9
            ? Class.forName(type.getDescriptor().replace('/', '.'), false, this.loader)
            : Class.forName(type.getClassName(), false, this.loader);
      } catch (ClassNotFoundException var3) {
         throw new TypeNotPresentException(var3.toString(), var3);
      }
   }
}
