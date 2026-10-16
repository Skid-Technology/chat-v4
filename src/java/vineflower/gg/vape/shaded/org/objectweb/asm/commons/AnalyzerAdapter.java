package gg.vape.shaded.org.objectweb.asm.commons;

import gg.vape.shaded.org.objectweb.asm.ConstantDynamic;
import gg.vape.shaded.org.objectweb.asm.Handle;
import gg.vape.shaded.org.objectweb.asm.Label;
import gg.vape.shaded.org.objectweb.asm.MethodVisitor;
import gg.vape.shaded.org.objectweb.asm.Opcodes;
import gg.vape.shaded.org.objectweb.asm.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyzerAdapter extends MethodVisitor {
   public List<Object> locals;
   public List<Object> stack;
   private List<Label> labels;
   public Map<Object, Object> uninitializedTypes;
   private int maxStack;
   private int maxLocals;
   private String owner;

   public AnalyzerAdapter(String owner, int access, String name, String descriptor, MethodVisitor methodVisitor) {
      this(589824, owner, access, name, descriptor, methodVisitor);
      if (this.getClass() != AnalyzerAdapter.class) {
         throw new IllegalStateException();
      }
   }

   protected AnalyzerAdapter(int api, String owner, int access, String name, String descriptor, MethodVisitor methodVisitor) {
      super(api, methodVisitor);
      this.owner = owner;
      this.locals = new ArrayList<>();
      this.stack = new ArrayList<>();
      this.uninitializedTypes = new HashMap<>();
      if ((access & 8) == 0) {
         if ("<init>".equals(name)) {
            this.locals.add(Opcodes.UNINITIALIZED_THIS);
         } else {
            this.locals.add(owner);
         }
      }

      for (Type argumentType : Type.getArgumentTypes(descriptor)) {
         switch (argumentType.getSort()) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
               this.locals.add(Opcodes.INTEGER);
               break;
            case 6:
               this.locals.add(Opcodes.FLOAT);
               break;
            case 7:
               this.locals.add(Opcodes.LONG);
               this.locals.add(Opcodes.TOP);
               break;
            case 8:
               this.locals.add(Opcodes.DOUBLE);
               this.locals.add(Opcodes.TOP);
               break;
            case 9:
               this.locals.add(argumentType.getDescriptor());
               break;
            case 10:
               this.locals.add(argumentType.getInternalName());
               break;
            default:
               throw new AssertionError();
         }
      }

      this.maxLocals = this.locals.size();
   }

   @Override
   public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
      if (type != -1) {
         throw new IllegalArgumentException("AnalyzerAdapter only accepts expanded frames (see ClassReader.EXPAND_FRAMES)");
      } else {
         super.visitFrame(type, numLocal, local, numStack, stack);
         if (this.locals != null) {
            this.locals.clear();
            this.stack.clear();
         } else {
            this.locals = new ArrayList<>();
            this.stack = new ArrayList<>();
         }

         visitFrameTypes(numLocal, local, this.locals);
         visitFrameTypes(numStack, stack, this.stack);
         this.maxLocals = Math.max(this.maxLocals, this.locals.size());
         this.maxStack = Math.max(this.maxStack, this.stack.size());
      }
   }

   private static void visitFrameTypes(int numTypes, Object[] frameTypes, List<Object> result) {
      for (int i = 0; i < numTypes; i++) {
         Object frameType = frameTypes[i];
         result.add(frameType);
         if (frameType == Opcodes.LONG || frameType == Opcodes.DOUBLE) {
            result.add(Opcodes.TOP);
         }
      }
   }

   @Override
   public void visitInsn(int opcode) {
      super.visitInsn(opcode);
      this.execute(opcode, 0, null);
      if (opcode >= 172 && opcode <= 177 || opcode == 191) {
         this.locals = null;
         this.stack = null;
      }
   }

   @Override
   public void visitIntInsn(int opcode, int operand) {
      super.visitIntInsn(opcode, operand);
      this.execute(opcode, operand, null);
   }

   @Override
   public void visitVarInsn(int opcode, int varIndex) {
      super.visitVarInsn(opcode, varIndex);
      boolean isLongOrDouble = opcode == 22 || opcode == 24 || opcode == 55 || opcode == 57;
      this.maxLocals = Math.max(this.maxLocals, varIndex + (isLongOrDouble ? 2 : 1));
      this.execute(opcode, varIndex, null);
   }

   @Override
   public void visitTypeInsn(int opcode, String type) {
      if (opcode == 187) {
         if (this.labels == null) {
            Label label = new Label();
            this.labels = new ArrayList<>(3);
            this.labels.add(label);
            if (this.mv != null) {
               this.mv.visitLabel(label);
            }
         }

         for (Label label : this.labels) {
            this.uninitializedTypes.put(label, type);
         }
      }

      super.visitTypeInsn(opcode, type);
      this.execute(opcode, 0, type);
   }

   @Override
   public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
      super.visitFieldInsn(opcode, owner, name, descriptor);
      this.execute(opcode, 0, descriptor);
   }

   @Override
   public void visitMethodInsn(int opcodeAndSource, String owner, String name, String descriptor, boolean isInterface) {
      if (this.api < 327680 && (opcodeAndSource & 256) == 0) {
         super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface);
      } else {
         super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface);
         int opcode = opcodeAndSource & -257;
         if (this.locals == null) {
            this.labels = null;
         } else {
            this.pop(descriptor);
            if (opcode != 184) {
               Object value = this.pop();
               if (opcode == 183 && name.equals("<init>")) {
                  Object initializedValue;
                  if (value == Opcodes.UNINITIALIZED_THIS) {
                     initializedValue = this.owner;
                  } else {
                     initializedValue = owner;
                  }

                  for (int i = 0; i < this.locals.size(); i++) {
                     if (this.locals.get(i) == value) {
                        this.locals.set(i, initializedValue);
                     }
                  }

                  for (int ix = 0; ix < this.stack.size(); ix++) {
                     if (this.stack.get(ix) == value) {
                        this.stack.set(ix, initializedValue);
                     }
                  }
               }
            }

            this.pushDescriptor(descriptor);
            this.labels = null;
         }
      }
   }

   @Override
   public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
      super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
      if (this.locals == null) {
         this.labels = null;
      } else {
         this.pop(descriptor);
         this.pushDescriptor(descriptor);
         this.labels = null;
      }
   }

   @Override
   public void visitJumpInsn(int opcode, Label label) {
      super.visitJumpInsn(opcode, label);
      this.execute(opcode, 0, null);
      if (opcode == 167) {
         this.locals = null;
         this.stack = null;
      }
   }

   @Override
   public void visitLabel(Label label) {
      super.visitLabel(label);
      if (this.labels == null) {
         this.labels = new ArrayList<>(3);
      }

      this.labels.add(label);
   }

   @Override
   public void visitLdcInsn(Object value) {
      super.visitLdcInsn(value);
      if (this.locals == null) {
         this.labels = null;
      } else {
         if (value instanceof Integer) {
            this.push(Opcodes.INTEGER);
         } else if (value instanceof Long) {
            this.push(Opcodes.LONG);
            this.push(Opcodes.TOP);
         } else if (value instanceof Float) {
            this.push(Opcodes.FLOAT);
         } else if (value instanceof Double) {
            this.push(Opcodes.DOUBLE);
            this.push(Opcodes.TOP);
         } else if (value instanceof String) {
            this.push("java/lang/String");
         } else if (value instanceof Type) {
            int sort = ((Type)value).getSort();
            if (sort != 10 && sort != 9) {
               if (sort != 11) {
                  throw new IllegalArgumentException();
               }

               this.push("java/lang/invoke/MethodType");
            } else {
               this.push("java/lang/Class");
            }
         } else if (value instanceof Handle) {
            this.push("java/lang/invoke/MethodHandle");
         } else {
            if (!(value instanceof ConstantDynamic)) {
               throw new IllegalArgumentException();
            }

            this.pushDescriptor(((ConstantDynamic)value).getDescriptor());
         }

         this.labels = null;
      }
   }

   @Override
   public void visitIincInsn(int varIndex, int increment) {
      super.visitIincInsn(varIndex, increment);
      this.maxLocals = Math.max(this.maxLocals, varIndex + 1);
      this.execute(132, varIndex, null);
   }

   @Override
   public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
      super.visitTableSwitchInsn(min, max, dflt, labels);
      this.execute(170, 0, null);
      this.locals = null;
      this.stack = null;
   }

   @Override
   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
      super.visitLookupSwitchInsn(dflt, keys, labels);
      this.execute(171, 0, null);
      this.locals = null;
      this.stack = null;
   }

   @Override
   public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
      super.visitMultiANewArrayInsn(descriptor, numDimensions);
      this.execute(197, numDimensions, descriptor);
   }

   @Override
   public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
      char firstDescriptorChar = descriptor.charAt(0);
      this.maxLocals = Math.max(this.maxLocals, index + (firstDescriptorChar != 'J' && firstDescriptorChar != 'D' ? 1 : 2));
      super.visitLocalVariable(name, descriptor, signature, start, end, index);
   }

   @Override
   public void visitMaxs(int maxStack, int maxLocals) {
      if (this.mv != null) {
         this.maxStack = Math.max(this.maxStack, maxStack);
         this.maxLocals = Math.max(this.maxLocals, maxLocals);
         this.mv.visitMaxs(this.maxStack, this.maxLocals);
      }
   }

   private Object get(int local) {
      this.maxLocals = Math.max(this.maxLocals, local + 1);
      return local < this.locals.size() ? this.locals.get(local) : Opcodes.TOP;
   }

   private void set(int local, Object type) {
      this.maxLocals = Math.max(this.maxLocals, local + 1);

      while (local >= this.locals.size()) {
         this.locals.add(Opcodes.TOP);
      }

      this.locals.set(local, type);
   }

   private void push(Object type) {
      this.stack.add(type);
      this.maxStack = Math.max(this.maxStack, this.stack.size());
   }

   private void pushDescriptor(String fieldOrMethodDescriptor) {
      String descriptor = fieldOrMethodDescriptor.charAt(0) == '(' ? Type.getReturnType(fieldOrMethodDescriptor).getDescriptor() : fieldOrMethodDescriptor;
      switch (descriptor.charAt(0)) {
         case 'B':
         case 'C':
         case 'I':
         case 'S':
         case 'Z':
            this.push(Opcodes.INTEGER);
            return;
         case 'D':
            this.push(Opcodes.DOUBLE);
            this.push(Opcodes.TOP);
            return;
         case 'E':
         case 'G':
         case 'H':
         case 'K':
         case 'M':
         case 'N':
         case 'O':
         case 'P':
         case 'Q':
         case 'R':
         case 'T':
         case 'U':
         case 'W':
         case 'X':
         case 'Y':
         default:
            throw new AssertionError();
         case 'F':
            this.push(Opcodes.FLOAT);
            return;
         case 'J':
            this.push(Opcodes.LONG);
            this.push(Opcodes.TOP);
            return;
         case 'L':
            this.push(descriptor.substring(1, descriptor.length() - 1));
            break;
         case 'V':
            return;
         case '[':
            this.push(descriptor);
      }
   }

   private Object pop() {
      return this.stack.remove(this.stack.size() - 1);
   }

   private void pop(int numSlots) {
      int size = this.stack.size();
      int end = size - numSlots;

      for (int i = size - 1; i >= end; i--) {
         this.stack.remove(i);
      }
   }

   private void pop(String descriptor) {
      char firstDescriptorChar = descriptor.charAt(0);
      if (firstDescriptorChar == '(') {
         int numSlots = 0;
         Type[] types = Type.getArgumentTypes(descriptor);

         for (Type type : types) {
            numSlots += type.getSize();
         }

         this.pop(numSlots);
      } else if (firstDescriptorChar != 'J' && firstDescriptorChar != 'D') {
         this.pop(1);
      } else {
         this.pop(2);
      }
   }

   private void execute(int opcode, int intArg, String stringArg) {
      if (opcode == 168 || opcode == 169) {
         throw new IllegalArgumentException("JSR/RET are not supported");
      } else if (this.locals == null) {
         this.labels = null;
      } else {
         label89:
         switch (opcode) {
            case 0:
            case 116:
            case 117:
            case 118:
            case 119:
            case 145:
            case 146:
            case 147:
            case 167:
            case 177:
               break;
            case 1:
               this.push(Opcodes.NULL);
               break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 16:
            case 17:
               this.push(Opcodes.INTEGER);
               break;
            case 9:
            case 10:
               this.push(Opcodes.LONG);
               this.push(Opcodes.TOP);
               break;
            case 11:
            case 12:
            case 13:
               this.push(Opcodes.FLOAT);
               break;
            case 14:
            case 15:
               this.push(Opcodes.DOUBLE);
               this.push(Opcodes.TOP);
               break;
            case 18:
            case 19:
            case 20:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 168:
            case 169:
            case 182:
            case 183:
            case 184:
            case 185:
            case 186:
            case 196:
            default:
               throw new IllegalArgumentException(stringConcat$2(opcode));
            case 21:
            case 23:
            case 25:
               this.push(this.get(intArg));
               break;
            case 22:
            case 24:
               this.push(this.get(intArg));
               this.push(Opcodes.TOP);
               break;
            case 46:
            case 51:
            case 52:
            case 53:
            case 96:
            case 100:
            case 104:
            case 108:
            case 112:
            case 120:
            case 122:
            case 124:
            case 126:
            case 128:
            case 130:
            case 136:
            case 142:
            case 149:
            case 150:
               this.pop(2);
               this.push(Opcodes.INTEGER);
               break;
            case 47:
            case 143:
               this.pop(2);
               this.push(Opcodes.LONG);
               this.push(Opcodes.TOP);
               break;
            case 48:
            case 98:
            case 102:
            case 106:
            case 110:
            case 114:
            case 137:
            case 144:
               this.pop(2);
               this.push(Opcodes.FLOAT);
               break;
            case 49:
            case 138:
               this.pop(2);
               this.push(Opcodes.DOUBLE);
               this.push(Opcodes.TOP);
               break;
            case 50: {
               this.pop(1);
               Object value1 = this.pop();
               if (value1 instanceof String) {
                  this.pushDescriptor(((String)value1).substring(1));
               } else if (value1 == Opcodes.NULL) {
                  this.push(value1);
               } else {
                  this.push("java/lang/Object");
               }
               break;
            }
            case 54:
            case 56:
            case 58: {
               Object value1 = this.pop();
               this.set(intArg, value1);
               if (intArg > 0) {
                  Object value2x = this.get(intArg - 1);
                  if (value2x == Opcodes.LONG || value2x == Opcodes.DOUBLE) {
                     this.set(intArg - 1, Opcodes.TOP);
                  }
               }
               break;
            }
            case 55:
            case 57: {
               this.pop(1);
               Object value1 = this.pop();
               this.set(intArg, value1);
               this.set(intArg + 1, Opcodes.TOP);
               if (intArg > 0) {
                  Object value2x = this.get(intArg - 1);
                  if (value2x == Opcodes.LONG || value2x == Opcodes.DOUBLE) {
                     this.set(intArg - 1, Opcodes.TOP);
                  }
               }
               break;
            }
            case 79:
            case 81:
            case 83:
            case 84:
            case 85:
            case 86:
               this.pop(3);
               break;
            case 80:
            case 82:
               this.pop(4);
               break;
            case 87:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 170:
            case 171:
            case 172:
            case 174:
            case 176:
            case 191:
            case 194:
            case 195:
            case 198:
            case 199:
               this.pop(1);
               break;
            case 88:
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            case 165:
            case 166:
            case 173:
            case 175:
               this.pop(2);
               break;
            case 89: {
               Object value1 = this.pop();
               this.push(value1);
               this.push(value1);
               break;
            }
            case 90: {
               Object value1 = this.pop();
               Object value2 = this.pop();
               this.push(value1);
               this.push(value2);
               this.push(value1);
               break;
            }
            case 91: {
               Object value1 = this.pop();
               Object value2 = this.pop();
               Object value3 = this.pop();
               this.push(value1);
               this.push(value3);
               this.push(value2);
               this.push(value1);
               break;
            }
            case 92: {
               Object value1 = this.pop();
               Object value2 = this.pop();
               this.push(value2);
               this.push(value1);
               this.push(value2);
               this.push(value1);
               break;
            }
            case 93: {
               Object value1 = this.pop();
               Object value2 = this.pop();
               Object value3 = this.pop();
               this.push(value2);
               this.push(value1);
               this.push(value3);
               this.push(value2);
               this.push(value1);
               break;
            }
            case 94: {
               Object value1 = this.pop();
               Object value2 = this.pop();
               Object value3 = this.pop();
               Object t4 = this.pop();
               this.push(value2);
               this.push(value1);
               this.push(t4);
               this.push(value3);
               this.push(value2);
               this.push(value1);
               break;
            }
            case 95: {
               Object value1 = this.pop();
               Object value2 = this.pop();
               this.push(value1);
               this.push(value2);
               break;
            }
            case 97:
            case 101:
            case 105:
            case 109:
            case 113:
            case 127:
            case 129:
            case 131:
               this.pop(4);
               this.push(Opcodes.LONG);
               this.push(Opcodes.TOP);
               break;
            case 99:
            case 103:
            case 107:
            case 111:
            case 115:
               this.pop(4);
               this.push(Opcodes.DOUBLE);
               this.push(Opcodes.TOP);
               break;
            case 121:
            case 123:
            case 125:
               this.pop(3);
               this.push(Opcodes.LONG);
               this.push(Opcodes.TOP);
               break;
            case 132:
               this.set(intArg, Opcodes.INTEGER);
               break;
            case 133:
            case 140:
               this.pop(1);
               this.push(Opcodes.LONG);
               this.push(Opcodes.TOP);
               break;
            case 134:
               this.pop(1);
               this.push(Opcodes.FLOAT);
               break;
            case 135:
            case 141:
               this.pop(1);
               this.push(Opcodes.DOUBLE);
               this.push(Opcodes.TOP);
               break;
            case 139:
            case 190:
            case 193:
               this.pop(1);
               this.push(Opcodes.INTEGER);
               break;
            case 148:
            case 151:
            case 152:
               this.pop(4);
               this.push(Opcodes.INTEGER);
               break;
            case 178:
               this.pushDescriptor(stringArg);
               break;
            case 179:
               this.pop(stringArg);
               break;
            case 180:
               this.pop(1);
               this.pushDescriptor(stringArg);
               break;
            case 181:
               this.pop(stringArg);
               this.pop();
               break;
            case 187:
               this.push(this.labels.get(0));
               break;
            case 188:
               this.pop();
               switch (intArg) {
                  case 4:
                     this.pushDescriptor("[Z");
                     break label89;
                  case 5:
                     this.pushDescriptor("[C");
                     break label89;
                  case 6:
                     this.pushDescriptor("[F");
                     break label89;
                  case 7:
                     this.pushDescriptor("[D");
                     break label89;
                  case 8:
                     this.pushDescriptor("[B");
                     break label89;
                  case 9:
                     this.pushDescriptor("[S");
                     break label89;
                  case 10:
                     this.pushDescriptor("[I");
                     break label89;
                  case 11:
                     this.pushDescriptor("[J");
                     break label89;
                  default:
                     throw new IllegalArgumentException(stringConcat$0(intArg));
               }
            case 189:
               this.pop();
               this.pushDescriptor(stringConcat$1(Type.getObjectType(stringArg)));
               break;
            case 192:
               this.pop();
               this.pushDescriptor(Type.getObjectType(stringArg).getDescriptor());
               break;
            case 197:
               this.pop(intArg);
               this.pushDescriptor(stringArg);
         }

         this.labels = null;
      }
   }
}
