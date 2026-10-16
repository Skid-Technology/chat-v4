package gg.vape.shaded.org.objectweb.asm.commons;

import gg.vape.shaded.org.objectweb.asm.ClassVisitor;
import gg.vape.shaded.org.objectweb.asm.ConstantDynamic;
import gg.vape.shaded.org.objectweb.asm.Handle;
import gg.vape.shaded.org.objectweb.asm.Label;
import gg.vape.shaded.org.objectweb.asm.MethodVisitor;
import gg.vape.shaded.org.objectweb.asm.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeneratorAdapter extends LocalVariablesSorter {
   private static final String CLASS_DESCRIPTOR = "Ljava/lang/Class;";
   private static final Type BYTE_TYPE = Type.getObjectType("java/lang/Byte");
   private static final Type BOOLEAN_TYPE = Type.getObjectType("java/lang/Boolean");
   private static final Type SHORT_TYPE = Type.getObjectType("java/lang/Short");
   private static final Type CHARACTER_TYPE = Type.getObjectType("java/lang/Character");
   private static final Type INTEGER_TYPE = Type.getObjectType("java/lang/Integer");
   private static final Type FLOAT_TYPE = Type.getObjectType("java/lang/Float");
   private static final Type LONG_TYPE = Type.getObjectType("java/lang/Long");
   private static final Type DOUBLE_TYPE = Type.getObjectType("java/lang/Double");
   private static final Type NUMBER_TYPE = Type.getObjectType("java/lang/Number");
   private static final Type OBJECT_TYPE = Type.getObjectType("java/lang/Object");
   private static final Method BOOLEAN_VALUE = Method.getMethod("boolean booleanValue()");
   private static final Method CHAR_VALUE = Method.getMethod("char charValue()");
   private static final Method INT_VALUE = Method.getMethod("int intValue()");
   private static final Method FLOAT_VALUE = Method.getMethod("float floatValue()");
   private static final Method LONG_VALUE = Method.getMethod("long longValue()");
   private static final Method DOUBLE_VALUE = Method.getMethod("double doubleValue()");
   public static final int ADD = 96;
   public static final int SUB = 100;
   public static final int MUL = 104;
   public static final int DIV = 108;
   public static final int REM = 112;
   public static final int NEG = 116;
   public static final int SHL = 120;
   public static final int SHR = 122;
   public static final int USHR = 124;
   public static final int AND = 126;
   public static final int OR = 128;
   public static final int XOR = 130;
   public static final int EQ = 153;
   public static final int NE = 154;
   public static final int LT = 155;
   public static final int GE = 156;
   public static final int GT = 157;
   public static final int LE = 158;
   private final int access;
   private final String name;
   private final Type returnType;
   private final Type[] argumentTypes;
   private final List<Type> localTypes = new ArrayList<>();

   public GeneratorAdapter(MethodVisitor methodVisitor, int access, String name, String descriptor) {
      this(589824, methodVisitor, access, name, descriptor);
      if (this.getClass() != GeneratorAdapter.class) {
         throw new IllegalStateException();
      }
   }

   protected GeneratorAdapter(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
      super(api, access, descriptor, methodVisitor);
      this.access = access;
      this.name = name;
      this.returnType = Type.getReturnType(descriptor);
      this.argumentTypes = Type.getArgumentTypes(descriptor);
   }

   public GeneratorAdapter(int access, Method method, MethodVisitor methodVisitor) {
      this(methodVisitor, access, method.getName(), method.getDescriptor());
   }

   public GeneratorAdapter(int access, Method method, String signature, Type[] exceptions, ClassVisitor classVisitor) {
      this(
         access,
         method,
         classVisitor.visitMethod(access, method.getName(), method.getDescriptor(), signature, exceptions == null ? null : getInternalNames(exceptions))
      );
   }

   private static String[] getInternalNames(Type[] types) {
      String[] names = new String[types.length];

      for (int i = 0; i < names.length; i++) {
         names[i] = types[i].getInternalName();
      }

      return names;
   }

   public int getAccess() {
      return this.access;
   }

   public String getName() {
      return this.name;
   }

   public Type getReturnType() {
      return this.returnType;
   }

   public Type[] getArgumentTypes() {
      return (Type[])this.argumentTypes.clone();
   }

   public void push(boolean value) {
      this.push(value ? 1 : 0);
   }

   public void push(int value) {
      if (value >= -1 && value <= 5) {
         this.mv.visitInsn(3 + value);
      } else if (value >= -128 && value <= 127) {
         this.mv.visitIntInsn(16, value);
      } else if (value >= -32768 && value <= 32767) {
         this.mv.visitIntInsn(17, value);
      } else {
         this.mv.visitLdcInsn(value);
      }
   }

   public void push(long value) {
      if (value != 0L && value != 1L) {
         this.mv.visitLdcInsn(value);
      } else {
         this.mv.visitInsn(9 + (int)value);
      }
   }

   public void push(float value) {
      int bits = Float.floatToIntBits(value);
      if (bits != 0L && bits != 1065353216 && bits != 1073741824) {
         this.mv.visitLdcInsn(value);
      } else {
         this.mv.visitInsn(11 + (int)value);
      }
   }

   public void push(double value) {
      long bits = Double.doubleToLongBits(value);
      if (bits != 0L && bits != 4607182418800017408L) {
         this.mv.visitLdcInsn(value);
      } else {
         this.mv.visitInsn(14 + (int)value);
      }
   }

   public void push(String value) {
      if (value == null) {
         this.mv.visitInsn(1);
      } else {
         this.mv.visitLdcInsn(value);
      }
   }

   public void push(Type value) {
      if (value == null) {
         this.mv.visitInsn(1);
      } else {
         switch (value.getSort()) {
            case 0:
               this.mv.visitFieldInsn(178, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
               break;
            case 1:
               this.mv.visitFieldInsn(178, "java/lang/Boolean", "TYPE", "Ljava/lang/Class;");
               break;
            case 2:
               this.mv.visitFieldInsn(178, "java/lang/Character", "TYPE", "Ljava/lang/Class;");
               break;
            case 3:
               this.mv.visitFieldInsn(178, "java/lang/Byte", "TYPE", "Ljava/lang/Class;");
               break;
            case 4:
               this.mv.visitFieldInsn(178, "java/lang/Short", "TYPE", "Ljava/lang/Class;");
               break;
            case 5:
               this.mv.visitFieldInsn(178, "java/lang/Integer", "TYPE", "Ljava/lang/Class;");
               break;
            case 6:
               this.mv.visitFieldInsn(178, "java/lang/Float", "TYPE", "Ljava/lang/Class;");
               break;
            case 7:
               this.mv.visitFieldInsn(178, "java/lang/Long", "TYPE", "Ljava/lang/Class;");
               break;
            case 8:
               this.mv.visitFieldInsn(178, "java/lang/Double", "TYPE", "Ljava/lang/Class;");
               break;
            default:
               this.mv.visitLdcInsn(value);
         }
      }
   }

   public void push(Handle handle) {
      if (handle == null) {
         this.mv.visitInsn(1);
      } else {
         this.mv.visitLdcInsn(handle);
      }
   }

   public void push(ConstantDynamic constantDynamic) {
      if (constantDynamic == null) {
         this.mv.visitInsn(1);
      } else {
         this.mv.visitLdcInsn(constantDynamic);
      }
   }

   private int getArgIndex(int arg) {
      int index = (this.access & 8) == 0 ? 1 : 0;

      for (int i = 0; i < arg; i++) {
         index += this.argumentTypes[i].getSize();
      }

      return index;
   }

   private void loadInsn(Type type, int index) {
      this.mv.visitVarInsn(type.getOpcode(21), index);
   }

   private void storeInsn(Type type, int index) {
      this.mv.visitVarInsn(type.getOpcode(54), index);
   }

   public void loadThis() {
      if ((this.access & 8) != 0) {
         throw new IllegalStateException("no 'this' pointer within static method");
      } else {
         this.mv.visitVarInsn(25, 0);
      }
   }

   public void loadArg(int arg) {
      this.loadInsn(this.argumentTypes[arg], this.getArgIndex(arg));
   }

   public void loadArgs(int arg, int count) {
      int index = this.getArgIndex(arg);

      for (int i = 0; i < count; i++) {
         Type argumentType = this.argumentTypes[arg + i];
         this.loadInsn(argumentType, index);
         index += argumentType.getSize();
      }
   }

   public void loadArgs() {
      this.loadArgs(0, this.argumentTypes.length);
   }

   public void loadArgArray() {
      this.push(this.argumentTypes.length);
      this.newArray(OBJECT_TYPE);

      for (int i = 0; i < this.argumentTypes.length; i++) {
         this.dup();
         this.push(i);
         this.loadArg(i);
         this.box(this.argumentTypes[i]);
         this.arrayStore(OBJECT_TYPE);
      }
   }

   public void storeArg(int arg) {
      this.storeInsn(this.argumentTypes[arg], this.getArgIndex(arg));
   }

   public Type getLocalType(int local) {
      return this.localTypes.get(local - this.firstLocal);
   }

   @Override
   protected void setLocalType(int local, Type type) {
      int index = local - this.firstLocal;

      while (this.localTypes.size() < index + 1) {
         this.localTypes.add(null);
      }

      this.localTypes.set(index, type);
   }

   public void loadLocal(int local) {
      this.loadInsn(this.getLocalType(local), local);
   }

   public void loadLocal(int local, Type type) {
      this.setLocalType(local, type);
      this.loadInsn(type, local);
   }

   public void storeLocal(int local) {
      this.storeInsn(this.getLocalType(local), local);
   }

   public void storeLocal(int local, Type type) {
      this.setLocalType(local, type);
      this.storeInsn(type, local);
   }

   public void arrayLoad(Type type) {
      this.mv.visitInsn(type.getOpcode(46));
   }

   public void arrayStore(Type type) {
      this.mv.visitInsn(type.getOpcode(79));
   }

   public void pop() {
      this.mv.visitInsn(87);
   }

   public void pop2() {
      this.mv.visitInsn(88);
   }

   public void dup() {
      this.mv.visitInsn(89);
   }

   public void dup2() {
      this.mv.visitInsn(92);
   }

   public void dupX1() {
      this.mv.visitInsn(90);
   }

   public void dupX2() {
      this.mv.visitInsn(91);
   }

   public void dup2X1() {
      this.mv.visitInsn(93);
   }

   public void dup2X2() {
      this.mv.visitInsn(94);
   }

   public void swap() {
      this.mv.visitInsn(95);
   }

   public void swap(Type prev, Type type) {
      if (type.getSize() == 1) {
         if (prev.getSize() == 1) {
            this.swap();
         } else {
            this.dupX2();
            this.pop();
         }
      } else if (prev.getSize() == 1) {
         this.dup2X1();
         this.pop2();
      } else {
         this.dup2X2();
         this.pop2();
      }
   }

   public void math(int op, Type type) {
      this.mv.visitInsn(type.getOpcode(op));
   }

   public void not() {
      this.mv.visitInsn(4);
      this.mv.visitInsn(130);
   }

   public void iinc(int local, int amount) {
      this.mv.visitIincInsn(local, amount);
   }

   public void cast(Type from, Type to) {
      if (from != to) {
         if (from.getSort() < 1 || from.getSort() > 8 || to.getSort() < 1 || to.getSort() > 8) {
            throw new IllegalArgumentException(stringConcat$0(from, to));
         }

         InstructionAdapter.cast(this.mv, from, to);
      }
   }

   private static Type getBoxedType(Type type) {
      switch (type.getSort()) {
         case 1:
            return BOOLEAN_TYPE;
         case 2:
            return CHARACTER_TYPE;
         case 3:
            return BYTE_TYPE;
         case 4:
            return SHORT_TYPE;
         case 5:
            return INTEGER_TYPE;
         case 6:
            return FLOAT_TYPE;
         case 7:
            return LONG_TYPE;
         case 8:
            return DOUBLE_TYPE;
         default:
            return type;
      }
   }

   public void box(Type type) {
      if (type.getSort() != 10 && type.getSort() != 9) {
         if (type == Type.VOID_TYPE) {
            this.push((String)null);
         } else {
            Type boxedType = getBoxedType(type);
            this.newInstance(boxedType);
            if (type.getSize() == 2) {
               this.dupX2();
               this.dupX2();
               this.pop();
            } else {
               this.dupX1();
               this.swap();
            }

            this.invokeConstructor(boxedType, new Method("<init>", Type.VOID_TYPE, new Type[]{type}));
         }
      }
   }

   public void valueOf(Type type) {
      if (type.getSort() != 10 && type.getSort() != 9) {
         if (type == Type.VOID_TYPE) {
            this.push((String)null);
         } else {
            Type boxedType = getBoxedType(type);
            this.invokeStatic(boxedType, new Method("valueOf", boxedType, new Type[]{type}));
         }
      }
   }

   public void unbox(Type type) {
      Type boxedType = NUMBER_TYPE;
      Method unboxMethod;
      switch (type.getSort()) {
         case 0:
            return;
         case 1:
            boxedType = BOOLEAN_TYPE;
            unboxMethod = BOOLEAN_VALUE;
            break;
         case 2:
            boxedType = CHARACTER_TYPE;
            unboxMethod = CHAR_VALUE;
            break;
         case 3:
         case 4:
         case 5:
            unboxMethod = INT_VALUE;
            break;
         case 6:
            unboxMethod = FLOAT_VALUE;
            break;
         case 7:
            unboxMethod = LONG_VALUE;
            break;
         case 8:
            unboxMethod = DOUBLE_VALUE;
            break;
         default:
            unboxMethod = null;
      }

      if (unboxMethod == null) {
         this.checkCast(type);
      } else {
         this.checkCast(boxedType);
         this.invokeVirtual(boxedType, unboxMethod);
      }
   }

   public Label newLabel() {
      return new Label();
   }

   public void mark(Label label) {
      this.mv.visitLabel(label);
   }

   public Label mark() {
      Label label = new Label();
      this.mv.visitLabel(label);
      return label;
   }

   public void ifCmp(Type type, int mode, Label label) {
      switch (type.getSort()) {
         case 6:
            this.mv.visitInsn(mode != 156 && mode != 157 ? 150 : 149);
            break;
         case 7:
            this.mv.visitInsn(148);
            break;
         case 8:
            this.mv.visitInsn(mode != 156 && mode != 157 ? 152 : 151);
            break;
         case 9:
         case 10:
            if (mode == 153) {
               this.mv.visitJumpInsn(165, label);
               return;
            }

            if (mode == 154) {
               this.mv.visitJumpInsn(166, label);
               return;
            }

            throw new IllegalArgumentException(stringConcat$1(type));
         default:
            int intOp = -1;
            short var5;
            switch (mode) {
               case 153:
                  var5 = 159;
                  break;
               case 154:
                  var5 = 160;
                  break;
               case 155:
                  var5 = 161;
                  break;
               case 156:
                  var5 = 162;
                  break;
               case 157:
                  var5 = 163;
                  break;
               case 158:
                  var5 = 164;
                  break;
               default:
                  throw new IllegalArgumentException(stringConcat$2(mode));
            }

            this.mv.visitJumpInsn(var5, label);
            return;
      }

      this.mv.visitJumpInsn(mode, label);
   }

   public void ifICmp(int mode, Label label) {
      this.ifCmp(Type.INT_TYPE, mode, label);
   }

   public void ifZCmp(int mode, Label label) {
      this.mv.visitJumpInsn(mode, label);
   }

   public void ifNull(Label label) {
      this.mv.visitJumpInsn(198, label);
   }

   public void ifNonNull(Label label) {
      this.mv.visitJumpInsn(199, label);
   }

   public void goTo(Label label) {
      this.mv.visitJumpInsn(167, label);
   }

   public void ret(int local) {
      this.mv.visitVarInsn(169, local);
   }

   public void tableSwitch(int[] keys, TableSwitchGenerator generator) {
      float density;
      if (keys.length == 0) {
         density = 0.0F;
      } else {
         density = (float)keys.length / (keys[keys.length - 1] - keys[0] + 1);
      }

      this.tableSwitch(keys, generator, density >= 0.5F);
   }

   public void tableSwitch(int[] keys, TableSwitchGenerator generator, boolean useTable) {
      for (int i = 1; i < keys.length; i++) {
         if (keys[i] < keys[i - 1]) {
            throw new IllegalArgumentException("keys must be sorted in ascending order");
         }
      }

      Label defaultLabel = this.newLabel();
      Label endLabel = this.newLabel();
      if (keys.length > 0) {
         int numKeys = keys.length;
         if (useTable) {
            int min = keys[0];
            int max = keys[numKeys - 1];
            int range = max - min + 1;
            Label[] labels = new Label[range];
            Arrays.fill(labels, defaultLabel);

            for (int ix = 0; ix < numKeys; ix++) {
               labels[keys[ix] - min] = this.newLabel();
            }

            this.mv.visitTableSwitchInsn(min, max, defaultLabel, labels);

            for (int ix = 0; ix < range; ix++) {
               Label label = labels[ix];
               if (label != defaultLabel) {
                  this.mark(label);
                  generator.generateCase(ix + min, endLabel);
               }
            }
         } else {
            Label[] labels = new Label[numKeys];

            for (int ixx = 0; ixx < numKeys; ixx++) {
               labels[ixx] = this.newLabel();
            }

            this.mv.visitLookupSwitchInsn(defaultLabel, keys, labels);

            for (int ixx = 0; ixx < numKeys; ixx++) {
               this.mark(labels[ixx]);
               generator.generateCase(keys[ixx], endLabel);
            }
         }
      }

      this.mark(defaultLabel);
      generator.generateDefault();
      this.mark(endLabel);
   }

   public void returnValue() {
      this.mv.visitInsn(this.returnType.getOpcode(172));
   }

   private void fieldInsn(int opcode, Type ownerType, String name, Type fieldType) {
      this.mv.visitFieldInsn(opcode, ownerType.getInternalName(), name, fieldType.getDescriptor());
   }

   public void getStatic(Type owner, String name, Type type) {
      this.fieldInsn(178, owner, name, type);
   }

   public void putStatic(Type owner, String name, Type type) {
      this.fieldInsn(179, owner, name, type);
   }

   public void getField(Type owner, String name, Type type) {
      this.fieldInsn(180, owner, name, type);
   }

   public void putField(Type owner, String name, Type type) {
      this.fieldInsn(181, owner, name, type);
   }

   private void invokeInsn(int opcode, Type type, Method method, boolean isInterface) {
      String owner = type.getSort() == 9 ? type.getDescriptor() : type.getInternalName();
      this.mv.visitMethodInsn(opcode, owner, method.getName(), method.getDescriptor(), isInterface);
   }

   public void invokeVirtual(Type owner, Method method) {
      this.invokeInsn(182, owner, method, false);
   }

   public void invokeConstructor(Type type, Method method) {
      this.invokeInsn(183, type, method, false);
   }

   public void invokeStatic(Type owner, Method method) {
      this.invokeInsn(184, owner, method, false);
   }

   public void invokeInterface(Type owner, Method method) {
      this.invokeInsn(185, owner, method, true);
   }

   public void invokeDynamic(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
      this.mv.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
   }

   private void typeInsn(int opcode, Type type) {
      this.mv.visitTypeInsn(opcode, type.getInternalName());
   }

   public void newInstance(Type type) {
      this.typeInsn(187, type);
   }

   public void newArray(Type type) {
      InstructionAdapter.newarray(this.mv, type);
   }

   public void arrayLength() {
      this.mv.visitInsn(190);
   }

   public void throwException() {
      this.mv.visitInsn(191);
   }

   public void throwException(Type type, String message) {
      this.newInstance(type);
      this.dup();
      this.push(message);
      this.invokeConstructor(type, Method.getMethod("void <init> (String)"));
      this.throwException();
   }

   public void checkCast(Type type) {
      if (!type.equals(OBJECT_TYPE)) {
         this.typeInsn(192, type);
      }
   }

   public void instanceOf(Type type) {
      this.typeInsn(193, type);
   }

   public void monitorEnter() {
      this.mv.visitInsn(194);
   }

   public void monitorExit() {
      this.mv.visitInsn(195);
   }

   public void endMethod() {
      if ((this.access & 1024) == 0) {
         this.mv.visitMaxs(0, 0);
      }

      this.mv.visitEnd();
   }

   public void catchException(Label start, Label end, Type exception) {
      Label catchLabel = new Label();
      if (exception == null) {
         this.mv.visitTryCatchBlock(start, end, catchLabel, null);
      } else {
         this.mv.visitTryCatchBlock(start, end, catchLabel, exception.getInternalName());
      }

      this.mark(catchLabel);
   }
}
