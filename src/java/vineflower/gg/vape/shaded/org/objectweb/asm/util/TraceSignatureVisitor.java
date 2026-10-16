package gg.vape.shaded.org.objectweb.asm.util;

import gg.vape.shaded.org.objectweb.asm.signature.SignatureVisitor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class TraceSignatureVisitor extends SignatureVisitor {
   private static final String COMMA_SEPARATOR = ", ";
   private static final String EXTENDS_SEPARATOR = " extends ";
   private static final String IMPLEMENTS_SEPARATOR = " implements ";
   private static final Map<Character, String> BASE_TYPES;
   private final boolean isInterface;
   private final StringBuilder declaration;
   private StringBuilder returnType;
   private StringBuilder exceptions;
   private boolean formalTypeParameterVisited;
   private boolean interfaceBoundVisited;
   private boolean parameterTypeVisited;
   private boolean interfaceVisited;
   private int argumentStack;
   private int arrayStack;
   private String separator = "";

   public TraceSignatureVisitor(int accessFlags) {
      super(589824);
      this.isInterface = (accessFlags & 512) != 0;
      this.declaration = new StringBuilder();
   }

   private TraceSignatureVisitor(StringBuilder stringBuilder) {
      super(589824);
      this.isInterface = false;
      this.declaration = stringBuilder;
   }

   @Override
   public void visitFormalTypeParameter(String name) {
      this.declaration.append(this.formalTypeParameterVisited ? ", " : "<").append(name);
      this.formalTypeParameterVisited = true;
      this.interfaceBoundVisited = false;
   }

   @Override
   public SignatureVisitor visitClassBound() {
      this.separator = " extends ";
      this.startType();
      return this;
   }

   @Override
   public SignatureVisitor visitInterfaceBound() {
      this.separator = this.interfaceBoundVisited ? ", " : " extends ";
      this.interfaceBoundVisited = true;
      this.startType();
      return this;
   }

   @Override
   public SignatureVisitor visitSuperclass() {
      this.endFormals();
      this.separator = " extends ";
      this.startType();
      return this;
   }

   @Override
   public SignatureVisitor visitInterface() {
      if (this.interfaceVisited) {
         this.separator = ", ";
      } else {
         this.separator = this.isInterface ? " extends " : " implements ";
         this.interfaceVisited = true;
      }

      this.startType();
      return this;
   }

   @Override
   public SignatureVisitor visitParameterType() {
      this.endFormals();
      if (this.parameterTypeVisited) {
         this.declaration.append(", ");
      } else {
         this.declaration.append('(');
         this.parameterTypeVisited = true;
      }

      this.startType();
      return this;
   }

   @Override
   public SignatureVisitor visitReturnType() {
      this.endFormals();
      if (this.parameterTypeVisited) {
         this.parameterTypeVisited = false;
      } else {
         this.declaration.append('(');
      }

      this.declaration.append(')');
      this.returnType = new StringBuilder();
      return new TraceSignatureVisitor(this.returnType);
   }

   @Override
   public SignatureVisitor visitExceptionType() {
      if (this.exceptions == null) {
         this.exceptions = new StringBuilder();
      } else {
         this.exceptions.append(", ");
      }

      return new TraceSignatureVisitor(this.exceptions);
   }

   @Override
   public void visitBaseType(char descriptor) {
      String baseType = BASE_TYPES.get(descriptor);
      if (baseType == null) {
         throw new IllegalArgumentException();
      } else {
         this.declaration.append(baseType);
         this.endType();
      }
   }

   @Override
   public void visitTypeVariable(String name) {
      this.declaration.append(this.separator).append(name);
      this.separator = "";
      this.endType();
   }

   @Override
   public SignatureVisitor visitArrayType() {
      this.startType();
      this.arrayStack |= 1;
      return this;
   }

   @Override
   public void visitClassType(String name) {
      if ("java/lang/Object".equals(name)) {
         boolean needObjectClass = this.argumentStack % 2 != 0 || this.parameterTypeVisited;
         if (needObjectClass) {
            this.declaration.append(this.separator).append(name.replace('/', '.'));
         }
      } else {
         this.declaration.append(this.separator).append(name.replace('/', '.'));
      }

      this.separator = "";
      this.argumentStack *= 2;
   }

   @Override
   public void visitInnerClassType(String name) {
      if (this.argumentStack % 2 != 0) {
         this.declaration.append('>');
      }

      this.argumentStack /= 2;
      this.declaration.append('.');
      this.declaration.append(this.separator).append(name.replace('/', '.'));
      this.separator = "";
      this.argumentStack *= 2;
   }

   @Override
   public void visitTypeArgument() {
      if (this.argumentStack % 2 == 0) {
         this.argumentStack++;
         this.declaration.append('<');
      } else {
         this.declaration.append(", ");
      }

      this.declaration.append('?');
   }

   @Override
   public SignatureVisitor visitTypeArgument(char tag) {
      if (this.argumentStack % 2 == 0) {
         this.argumentStack++;
         this.declaration.append('<');
      } else {
         this.declaration.append(", ");
      }

      if (tag == '+') {
         this.declaration.append("? extends ");
      } else if (tag == '-') {
         this.declaration.append("? super ");
      }

      this.startType();
      return this;
   }

   @Override
   public void visitEnd() {
      if (this.argumentStack % 2 != 0) {
         this.declaration.append('>');
      }

      this.argumentStack /= 2;
      this.endType();
   }

   public String getDeclaration() {
      return this.declaration.toString();
   }

   public String getReturnType() {
      return this.returnType == null ? null : this.returnType.toString();
   }

   public String getExceptions() {
      return this.exceptions == null ? null : this.exceptions.toString();
   }

   private void endFormals() {
      if (this.formalTypeParameterVisited) {
         this.declaration.append('>');
         this.formalTypeParameterVisited = false;
      }
   }

   private void startType() {
      this.arrayStack *= 2;
   }

   private void endType() {
      if (this.arrayStack % 2 == 0) {
         this.arrayStack /= 2;
      } else {
         while (this.arrayStack % 2 != 0) {
            this.arrayStack /= 2;
            this.declaration.append("[]");
         }
      }
   }

   static {
      HashMap<Character, String> baseTypes = new HashMap<>();
      baseTypes.put('Z', "boolean");
      baseTypes.put('B', "byte");
      baseTypes.put('C', "char");
      baseTypes.put('S', "short");
      baseTypes.put('I', "int");
      baseTypes.put('J', "long");
      baseTypes.put('F', "float");
      baseTypes.put('D', "double");
      baseTypes.put('V', "void");
      BASE_TYPES = Collections.unmodifiableMap(baseTypes);
   }
}
