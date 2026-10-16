package gg.vape.shaded.org.objectweb.asm.util;

import gg.vape.shaded.org.objectweb.asm.signature.SignatureVisitor;
import java.util.EnumSet;

public class CheckSignatureAdapter extends SignatureVisitor {
   public static final int CLASS_SIGNATURE = 0;
   public static final int METHOD_SIGNATURE = 1;
   public static final int TYPE_SIGNATURE = 2;
   private static final EnumSet<CheckSignatureAdapter.State> VISIT_FORMAL_TYPE_PARAMETER_STATES = EnumSet.of(
      CheckSignatureAdapter.State.EMPTY, CheckSignatureAdapter.State.FORMAL, CheckSignatureAdapter.State.BOUND
   );
   private static final EnumSet<CheckSignatureAdapter.State> VISIT_CLASS_BOUND_STATES = EnumSet.of(CheckSignatureAdapter.State.FORMAL);
   private static final EnumSet<CheckSignatureAdapter.State> VISIT_INTERFACE_BOUND_STATES = EnumSet.of(
      CheckSignatureAdapter.State.FORMAL, CheckSignatureAdapter.State.BOUND
   );
   private static final EnumSet<CheckSignatureAdapter.State> VISIT_SUPER_CLASS_STATES = EnumSet.of(
      CheckSignatureAdapter.State.EMPTY, CheckSignatureAdapter.State.FORMAL, CheckSignatureAdapter.State.BOUND
   );
   private static final EnumSet<CheckSignatureAdapter.State> VISIT_INTERFACE_STATES = EnumSet.of(CheckSignatureAdapter.State.SUPER);
   private static final EnumSet<CheckSignatureAdapter.State> VISIT_PARAMETER_TYPE_STATES = EnumSet.of(
      CheckSignatureAdapter.State.EMPTY, CheckSignatureAdapter.State.FORMAL, CheckSignatureAdapter.State.BOUND, CheckSignatureAdapter.State.PARAM
   );
   private static final EnumSet<CheckSignatureAdapter.State> VISIT_RETURN_TYPE_STATES = EnumSet.of(
      CheckSignatureAdapter.State.EMPTY, CheckSignatureAdapter.State.FORMAL, CheckSignatureAdapter.State.BOUND, CheckSignatureAdapter.State.PARAM
   );
   private static final EnumSet<CheckSignatureAdapter.State> VISIT_EXCEPTION_TYPE_STATES = EnumSet.of(CheckSignatureAdapter.State.RETURN);
   private static final String INVALID = "Invalid ";
   private final int type;
   private CheckSignatureAdapter.State state;
   private boolean canBeVoid;
   private final SignatureVisitor signatureVisitor;

   public CheckSignatureAdapter(int type, SignatureVisitor signatureVisitor) {
      this(589824, type, signatureVisitor);
   }

   protected CheckSignatureAdapter(int api, int type, SignatureVisitor signatureVisitor) {
      super(api);
      this.type = type;
      this.state = CheckSignatureAdapter.State.EMPTY;
      this.signatureVisitor = signatureVisitor;
   }

   @Override
   public void visitFormalTypeParameter(String name) {
      if (this.type != 2 && VISIT_FORMAL_TYPE_PARAMETER_STATES.contains(this.state)) {
         this.checkIdentifier(name, "formal type parameter");
         this.state = CheckSignatureAdapter.State.FORMAL;
         if (this.signatureVisitor != null) {
            this.signatureVisitor.visitFormalTypeParameter(name);
         }
      } else {
         throw new IllegalStateException();
      }
   }

   @Override
   public SignatureVisitor visitClassBound() {
      if (this.type != 2 && VISIT_CLASS_BOUND_STATES.contains(this.state)) {
         this.state = CheckSignatureAdapter.State.BOUND;
         return new CheckSignatureAdapter(2, this.signatureVisitor == null ? null : this.signatureVisitor.visitClassBound());
      } else {
         throw new IllegalStateException();
      }
   }

   @Override
   public SignatureVisitor visitInterfaceBound() {
      if (this.type != 2 && VISIT_INTERFACE_BOUND_STATES.contains(this.state)) {
         return new CheckSignatureAdapter(2, this.signatureVisitor == null ? null : this.signatureVisitor.visitInterfaceBound());
      } else {
         throw new IllegalStateException();
      }
   }

   @Override
   public SignatureVisitor visitSuperclass() {
      if (this.type == 0 && VISIT_SUPER_CLASS_STATES.contains(this.state)) {
         this.state = CheckSignatureAdapter.State.SUPER;
         return new CheckSignatureAdapter(2, this.signatureVisitor == null ? null : this.signatureVisitor.visitSuperclass());
      } else {
         throw new IllegalStateException();
      }
   }

   @Override
   public SignatureVisitor visitInterface() {
      if (this.type == 0 && VISIT_INTERFACE_STATES.contains(this.state)) {
         return new CheckSignatureAdapter(2, this.signatureVisitor == null ? null : this.signatureVisitor.visitInterface());
      } else {
         throw new IllegalStateException();
      }
   }

   @Override
   public SignatureVisitor visitParameterType() {
      if (this.type == 1 && VISIT_PARAMETER_TYPE_STATES.contains(this.state)) {
         this.state = CheckSignatureAdapter.State.PARAM;
         return new CheckSignatureAdapter(2, this.signatureVisitor == null ? null : this.signatureVisitor.visitParameterType());
      } else {
         throw new IllegalStateException();
      }
   }

   @Override
   public SignatureVisitor visitReturnType() {
      if (this.type == 1 && VISIT_RETURN_TYPE_STATES.contains(this.state)) {
         this.state = CheckSignatureAdapter.State.RETURN;
         CheckSignatureAdapter checkSignatureAdapter = new CheckSignatureAdapter(
            2, this.signatureVisitor == null ? null : this.signatureVisitor.visitReturnType()
         );
         checkSignatureAdapter.canBeVoid = true;
         return checkSignatureAdapter;
      } else {
         throw new IllegalStateException();
      }
   }

   @Override
   public SignatureVisitor visitExceptionType() {
      if (this.type == 1 && VISIT_EXCEPTION_TYPE_STATES.contains(this.state)) {
         return new CheckSignatureAdapter(2, this.signatureVisitor == null ? null : this.signatureVisitor.visitExceptionType());
      } else {
         throw new IllegalStateException();
      }
   }

   @Override
   public void visitBaseType(char descriptor) {
      if (this.type == 2 && this.state == CheckSignatureAdapter.State.EMPTY) {
         if (descriptor == 'V') {
            if (!this.canBeVoid) {
               throw new IllegalArgumentException("Base type descriptor can't be V");
            }
         } else if ("ZCBSIFJD".indexOf(descriptor) == -1) {
            throw new IllegalArgumentException("Base type descriptor must be one of ZCBSIFJD");
         }

         this.state = CheckSignatureAdapter.State.SIMPLE_TYPE;
         if (this.signatureVisitor != null) {
            this.signatureVisitor.visitBaseType(descriptor);
         }
      } else {
         throw new IllegalStateException();
      }
   }

   @Override
   public void visitTypeVariable(String name) {
      if (this.type == 2 && this.state == CheckSignatureAdapter.State.EMPTY) {
         this.checkIdentifier(name, "type variable");
         this.state = CheckSignatureAdapter.State.SIMPLE_TYPE;
         if (this.signatureVisitor != null) {
            this.signatureVisitor.visitTypeVariable(name);
         }
      } else {
         throw new IllegalStateException();
      }
   }

   @Override
   public SignatureVisitor visitArrayType() {
      if (this.type == 2 && this.state == CheckSignatureAdapter.State.EMPTY) {
         this.state = CheckSignatureAdapter.State.SIMPLE_TYPE;
         return new CheckSignatureAdapter(2, this.signatureVisitor == null ? null : this.signatureVisitor.visitArrayType());
      } else {
         throw new IllegalStateException();
      }
   }

   @Override
   public void visitClassType(String name) {
      if (this.type == 2 && this.state == CheckSignatureAdapter.State.EMPTY) {
         this.checkClassName(name, "class name");
         this.state = CheckSignatureAdapter.State.CLASS_TYPE;
         if (this.signatureVisitor != null) {
            this.signatureVisitor.visitClassType(name);
         }
      } else {
         throw new IllegalStateException();
      }
   }

   @Override
   public void visitInnerClassType(String name) {
      if (this.state != CheckSignatureAdapter.State.CLASS_TYPE) {
         throw new IllegalStateException();
      } else {
         this.checkIdentifier(name, "inner class name");
         if (this.signatureVisitor != null) {
            this.signatureVisitor.visitInnerClassType(name);
         }
      }
   }

   @Override
   public void visitTypeArgument() {
      if (this.state != CheckSignatureAdapter.State.CLASS_TYPE) {
         throw new IllegalStateException();
      } else {
         if (this.signatureVisitor != null) {
            this.signatureVisitor.visitTypeArgument();
         }
      }
   }

   @Override
   public SignatureVisitor visitTypeArgument(char wildcard) {
      if (this.state != CheckSignatureAdapter.State.CLASS_TYPE) {
         throw new IllegalStateException();
      } else if ("+-=".indexOf(wildcard) == -1) {
         throw new IllegalArgumentException("Wildcard must be one of +-=");
      } else {
         return new CheckSignatureAdapter(2, this.signatureVisitor == null ? null : this.signatureVisitor.visitTypeArgument(wildcard));
      }
   }

   @Override
   public void visitEnd() {
      if (this.state != CheckSignatureAdapter.State.CLASS_TYPE) {
         throw new IllegalStateException();
      } else {
         this.state = CheckSignatureAdapter.State.END;
         if (this.signatureVisitor != null) {
            this.signatureVisitor.visitEnd();
         }
      }
   }

   private void checkClassName(String name, String message) {
      if (name != null && name.length() != 0) {
         for (int i = 0; i < name.length(); i++) {
            if (".;[<>:".indexOf(name.charAt(i)) != -1) {
               throw new IllegalArgumentException(stringConcat$1(message, name));
            }
         }
      } else {
         throw new IllegalArgumentException(stringConcat$0(message));
      }
   }

   private void checkIdentifier(String name, String message) {
      if (name != null && name.length() != 0) {
         for (int i = 0; i < name.length(); i++) {
            if (".;[/<>:".indexOf(name.charAt(i)) != -1) {
               throw new IllegalArgumentException(stringConcat$3(message, name));
            }
         }
      } else {
         throw new IllegalArgumentException(stringConcat$2(message));
      }
   }

   private static enum State {
      EMPTY,
      FORMAL,
      BOUND,
      SUPER,
      PARAM,
      RETURN,
      SIMPLE_TYPE,
      CLASS_TYPE,
      END;
   }
}
