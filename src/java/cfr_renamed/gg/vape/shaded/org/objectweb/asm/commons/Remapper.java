/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm.commons;

import gg.vape.shaded.org.objectweb.asm.ConstantDynamic;
import gg.vape.shaded.org.objectweb.asm.Handle;
import gg.vape.shaded.org.objectweb.asm.Type;
import gg.vape.shaded.org.objectweb.asm.commons.SignatureRemapper;
import gg.vape.shaded.org.objectweb.asm.signature.SignatureReader;
import gg.vape.shaded.org.objectweb.asm.signature.SignatureVisitor;
import gg.vape.shaded.org.objectweb.asm.signature.SignatureWriter;

public abstract class Remapper {
    public String mapDesc(String descriptor) {
        return this.mapType(Type.getType(descriptor)).getDescriptor();
    }

    private Type mapType(Type type) {
        switch (type.getSort()) {
            case 9: {
                StringBuilder remappedDescriptor = new StringBuilder();
                for (int i = 0; i < type.getDimensions(); ++i) {
                    remappedDescriptor.append('[');
                }
                remappedDescriptor.append(this.mapType(type.getElementType()).getDescriptor());
                return Type.getType(remappedDescriptor.toString());
            }
            case 10: {
                String remappedInternalName = this.map(type.getInternalName());
                return remappedInternalName != null ? Type.getObjectType(remappedInternalName) : type;
            }
            case 11: {
                return Type.getMethodType(this.mapMethodDesc(type.getDescriptor()));
            }
        }
        return type;
    }

    public String mapType(String internalName) {
        if (internalName == null) {
            return null;
        }
        return this.mapType(Type.getObjectType(internalName)).getInternalName();
    }

    public String[] mapTypes(String[] internalNames) {
        String[] remappedInternalNames = null;
        for (int i = 0; i < internalNames.length; ++i) {
            String internalName = internalNames[i];
            String remappedInternalName = this.mapType(internalName);
            if (remappedInternalName == null) continue;
            if (remappedInternalNames == null) {
                remappedInternalNames = (String[])internalNames.clone();
            }
            remappedInternalNames[i] = remappedInternalName;
        }
        return remappedInternalNames != null ? remappedInternalNames : internalNames;
    }

    public String mapMethodDesc(String methodDescriptor) {
        if ("()V".equals(methodDescriptor)) {
            return methodDescriptor;
        }
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

    public Object mapValue(Object value) {
        if (value instanceof Type) {
            return this.mapType((Type)value);
        }
        if (value instanceof Handle) {
            String string;
            String string2;
            String string3;
            int n;
            Handle handle;
            Handle handle2;
            String string4;
            String string5;
            int n2;
            Handle handle3;
            Handle handle4;
            Handle handle5;
            Handle handle6 = (Handle)value;
            boolean isFieldHandle = handle6.getTag() <= 4;
            String string6 = this.mapType(handle6.getOwner());
            int n3 = handle6.getTag();
            Handle handle7 = handle5;
            Handle handle8 = handle5;
            if (isFieldHandle) {
                handle4 = handle8;
                handle3 = handle7;
                n2 = n3;
                string5 = string6;
                string4 = this.mapFieldName(handle6.getOwner(), handle6.getName(), handle6.getDesc());
            } else {
                handle4 = handle8;
                handle3 = handle7;
                n2 = n3;
                string5 = string6;
                string4 = this.mapMethodName(handle6.getOwner(), handle6.getName(), handle6.getDesc());
            }
            String string7 = string4;
            String string8 = string5;
            int n4 = n2;
            Handle handle9 = handle3;
            Handle handle10 = handle4;
            if (isFieldHandle) {
                handle2 = handle10;
                handle = handle9;
                n = n4;
                string3 = string8;
                string2 = string7;
                string = this.mapDesc(handle6.getDesc());
            } else {
                handle2 = handle10;
                handle = handle9;
                n = n4;
                string3 = string8;
                string2 = string7;
                string = this.mapMethodDesc(handle6.getDesc());
            }
            handle(n, string3, string2, string, handle6.isInterface());
            return handle2;
        }
        if (value instanceof ConstantDynamic) {
            ConstantDynamic constantDynamic = (ConstantDynamic)value;
            int bootstrapMethodArgumentCount = constantDynamic.getBootstrapMethodArgumentCount();
            Object[] remappedBootstrapMethodArguments = new Object[bootstrapMethodArgumentCount];
            for (int i = 0; i < bootstrapMethodArgumentCount; ++i) {
                remappedBootstrapMethodArguments[i] = this.mapValue(constantDynamic.getBootstrapMethodArgument(i));
            }
            String descriptor = constantDynamic.getDescriptor();
            return new ConstantDynamic(this.mapInvokeDynamicMethodName(constantDynamic.getName(), descriptor), this.mapDesc(descriptor), (Handle)this.mapValue(constantDynamic.getBootstrapMethod()), remappedBootstrapMethodArguments);
        }
        return value;
    }

    public String mapSignature(String signature, boolean typeSignature) {
        if (signature == null) {
            return null;
        }
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
        }
        int originSplit = name.lastIndexOf(47);
        int remappedSplit = remappedInnerName.lastIndexOf(47);
        if (originSplit != -1 && remappedSplit != -1 && name.substring(originSplit).equals(remappedInnerName.substring(remappedSplit))) {
            return innerName;
        }
        if (remappedInnerName.contains("$")) {
            int index;
            for (index = remappedInnerName.lastIndexOf(36) + 1; index < remappedInnerName.length() && Character.isDigit(remappedInnerName.charAt(index)); ++index) {
            }
            return remappedInnerName.substring(index);
        }
        return innerName;
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

