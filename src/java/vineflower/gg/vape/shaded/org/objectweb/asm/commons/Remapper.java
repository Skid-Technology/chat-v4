package gg.vape.shaded.org.objectweb.asm.commons;

import gg.vape.shaded.org.objectweb.asm.ConstantDynamic;
import gg.vape.shaded.org.objectweb.asm.Handle;
import gg.vape.shaded.org.objectweb.asm.Type;
import gg.vape.shaded.org.objectweb.asm.signature.SignatureReader;
import gg.vape.shaded.org.objectweb.asm.signature.SignatureVisitor;
import gg.vape.shaded.org.objectweb.asm.signature.SignatureWriter;

public abstract class Remapper {
   public String mapDesc(String descriptor) {
      return this.mapType(Type.getType(descriptor)).getDescriptor();
   }

   private Type mapType(Type type) {
      switch (type.getSort()) {
         case 9:
            StringBuilder remappedDescriptor = new StringBuilder();

            for (int i = 0; i < type.getDimensions(); i++) {
               remappedDescriptor.append('[');
            }

            remappedDescriptor.append(this.mapType(type.getElementType()).getDescriptor());
            return Type.getType(remappedDescriptor.toString());
         case 10:
            String remappedInternalName = this.map(type.getInternalName());
            return remappedInternalName != null ? Type.getObjectType(remappedInternalName) : type;
         case 11:
            return Type.getMethodType(this.mapMethodDesc(type.getDescriptor()));
         default:
            return type;
      }
   }

   public String mapType(String internalName) {
      return internalName == null ? null : this.mapType(Type.getObjectType(internalName)).getInternalName();
   }

   public String[] mapTypes(String[] internalNames) {
      String[] remappedInternalNames = null;

      for (int i = 0; i < internalNames.length; i++) {
         String internalName = internalNames[i];
         String remappedInternalName = this.mapType(internalName);
         if (remappedInternalName != null) {
            if (remappedInternalNames == null) {
               remappedInternalNames = (String[])internalNames.clone();
            }

            remappedInternalNames[i] = remappedInternalName;
         }
      }

      return remappedInternalNames != null ? remappedInternalNames : internalNames;
   }

   public String mapMethodDesc(String methodDescriptor) {
      if ("()V".equals(methodDescriptor)) {
         return methodDescriptor;
      } else {
         StringBuilder stringBuilder = new StringBuilder("(");

         for (Type argumentType : Type.getArgumentTypes(methodDescriptor)) {
            stringBuilder.append(this.mapType(argumentType).getDescriptor());
         }

         Type returnType = Type.getReturnType(methodDescriptor);
         if (returnType == Type.VOID_TYPE) {
            stringBuilder.append(")V");
         } else {
            stringBuilder.append(')').append(this.mapType(returnType).getDescriptor());
         }

         return stringBuilder.toString();
      }
   }

   public Object mapValue(Object value) {
      if (value instanceof Type) {
         return this.mapType((Type)value);
      } else if (value instanceof Handle) {
         Handle handle = (Handle)value;
         boolean isFieldHandle = handle.getTag() <= 4;
         Handle var10000 = new Handle;
         String var9 = this.mapType(handle.getOwner());
         int var8 = handle.getTag();
         Handle var7 = var10000;
         Handle var6 = var10000;
         Handle var10001;
         int var10002;
         String var10003;
         String var10004;
         if (isFieldHandle) {
            var10000 = var6;
            var10001 = var7;
            var10002 = var8;
            var10003 = var9;
            var10004 = this.mapFieldName(handle.getOwner(), handle.getName(), handle.getDesc());
         } else {
            var10000 = var6;
            var10001 = var7;
            var10002 = var8;
            var10003 = var9;
            var10004 = this.mapMethodName(handle.getOwner(), handle.getName(), handle.getDesc());
         }

         String var14 = var10004;
         String var13 = var10003;
         int var12 = var10002;
         Handle var11 = var10001;
         Handle var10 = var10000;
         String var10005;
         if (isFieldHandle) {
            var10000 = var10;
            var10001 = var11;
            var10002 = var12;
            var10003 = var13;
            var10004 = var14;
            var10005 = this.mapDesc(handle.getDesc());
         } else {
            var10000 = var10;
            var10001 = var11;
            var10002 = var12;
            var10003 = var13;
            var10004 = var14;
            var10005 = this.mapMethodDesc(handle.getDesc());
         }

         var10001./* $VF: Unable to resugar constructor */<init>(var10002, var10003, var10004, var10005, handle.isInterface());
         return var10000;
      } else if (!(value instanceof ConstantDynamic)) {
         return value;
      } else {
         ConstantDynamic constantDynamic = (ConstantDynamic)value;
         int bootstrapMethodArgumentCount = constantDynamic.getBootstrapMethodArgumentCount();
         Object[] remappedBootstrapMethodArguments = new Object[bootstrapMethodArgumentCount];

         for (int i = 0; i < bootstrapMethodArgumentCount; i++) {
            remappedBootstrapMethodArguments[i] = this.mapValue(constantDynamic.getBootstrapMethodArgument(i));
         }

         String descriptor = constantDynamic.getDescriptor();
         return new ConstantDynamic(
            this.mapInvokeDynamicMethodName(constantDynamic.getName(), descriptor),
            this.mapDesc(descriptor),
            (Handle)this.mapValue(constantDynamic.getBootstrapMethod()),
            remappedBootstrapMethodArguments
         );
      }
   }

   public String mapSignature(String signature, boolean typeSignature) {
      if (signature == null) {
         return null;
      } else {
         SignatureReader signatureReader = new SignatureReader(signature);
         SignatureWriter signatureWriter = new SignatureWriter();
         SignatureVisitor signatureRemapper = this.createSignatureRemapper(signatureWriter);
         if (typeSignature) {
            signatureReader.acceptType(signatureRemapper);
         } else {
            signatureReader.accept(signatureRemapper);
         }

         return signatureWriter.toString();
      }
   }

   @Deprecated
   protected SignatureVisitor createRemappingSignatureAdapter(SignatureVisitor signatureVisitor) {
      return this.createSignatureRemapper(signatureVisitor);
   }

   protected SignatureVisitor createSignatureRemapper(SignatureVisitor signatureVisitor) {
      return new SignatureRemapper(signatureVisitor, this);
   }

   public String mapAnnotationAttributeName(String descriptor, String name) {
      return name;
   }

   public String mapInnerClassName(String name, String ownerName, String innerName) {
      String remappedInnerName = this.mapType(name);
      if (remappedInnerName.equals(name)) {
         return innerName;
      } else {
         int originSplit = name.lastIndexOf(47);
         int remappedSplit = remappedInnerName.lastIndexOf(47);
         if (originSplit != -1 && remappedSplit != -1 && name.substring(originSplit).equals(remappedInnerName.substring(remappedSplit))) {
            return innerName;
         } else if (!remappedInnerName.contains("$")) {
            return innerName;
         } else {
            originSplit = remappedInnerName.lastIndexOf(36) + 1;

            while (originSplit < remappedInnerName.length() && Character.isDigit(remappedInnerName.charAt(originSplit))) {
               originSplit++;
            }

            return remappedInnerName.substring(originSplit);
         }
      }
   }

   public String mapMethodName(String owner, String name, String descriptor) {
      return name;
   }

   public String mapInvokeDynamicMethodName(String name, String descriptor) {
      return name;
   }

   public String mapRecordComponentName(String owner, String name, String descriptor) {
      return name;
   }

   public String mapFieldName(String owner, String name, String descriptor) {
      return name;
   }

   public String mapPackageName(String name) {
      return name;
   }

   public String mapModuleName(String name) {
      return name;
   }

   public String map(String internalName) {
      return internalName;
   }
}
