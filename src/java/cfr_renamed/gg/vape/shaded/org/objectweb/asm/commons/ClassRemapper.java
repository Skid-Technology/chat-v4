/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm.commons;

import gg.vape.shaded.org.objectweb.asm.AnnotationVisitor;
import gg.vape.shaded.org.objectweb.asm.Attribute;
import gg.vape.shaded.org.objectweb.asm.ClassVisitor;
import gg.vape.shaded.org.objectweb.asm.FieldVisitor;
import gg.vape.shaded.org.objectweb.asm.MethodVisitor;
import gg.vape.shaded.org.objectweb.asm.ModuleVisitor;
import gg.vape.shaded.org.objectweb.asm.RecordComponentVisitor;
import gg.vape.shaded.org.objectweb.asm.TypePath;
import gg.vape.shaded.org.objectweb.asm.commons.AnnotationRemapper;
import gg.vape.shaded.org.objectweb.asm.commons.FieldRemapper;
import gg.vape.shaded.org.objectweb.asm.commons.MethodRemapper;
import gg.vape.shaded.org.objectweb.asm.commons.ModuleHashesAttribute;
import gg.vape.shaded.org.objectweb.asm.commons.ModuleRemapper;
import gg.vape.shaded.org.objectweb.asm.commons.RecordComponentRemapper;
import gg.vape.shaded.org.objectweb.asm.commons.Remapper;
import java.util.List;

public class ClassRemapper
extends ClassVisitor {
    final protected Remapper remapper;
    protected String className;

    public ClassRemapper(ClassVisitor classVisitor, Remapper remapper) {
        this(589824, classVisitor, remapper);
    }

    protected ClassRemapper(int api, ClassVisitor classVisitor, Remapper remapper) {
        super(api, classVisitor);
        this.remapper = remapper;
    }

    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.className = name;
        super.visit(version, access, this.remapper.mapType(name), this.remapper.mapSignature(signature, false), this.remapper.mapType(superName), interfaces == null ? null : this.remapper.mapTypes(interfaces));
    }

    public ModuleVisitor visitModule(String name, int flags, String version) {
        ModuleVisitor moduleVisitor = super.visitModule(this.remapper.mapModuleName(name), flags, version);
        return moduleVisitor == null ? null : this.createModuleRemapper(moduleVisitor);
    }

    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitAnnotation(this.remapper.mapDesc(descriptor), visible);
        return annotationVisitor == null ? null : this.createAnnotationRemapper(descriptor, annotationVisitor);
    }

    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitTypeAnnotation(typeRef, typePath, this.remapper.mapDesc(descriptor), visible);
        return annotationVisitor == null ? null : this.createAnnotationRemapper(descriptor, annotationVisitor);
    }

    public void visitAttribute(Attribute attribute) {
        if (attribute instanceof ModuleHashesAttribute) {
            ModuleHashesAttribute moduleHashesAttribute = (ModuleHashesAttribute)attribute;
            List<String> modules = moduleHashesAttribute.modules;
            for (int i = 0; i < modules.size(); ++i) {
                modules.set(i, this.remapper.mapModuleName(modules.get(i)));
            }
        }
        super.visitAttribute(attribute);
    }

    public RecordComponentVisitor visitRecordComponent(String name, String descriptor, String signature) {
        RecordComponentVisitor recordComponentVisitor = super.visitRecordComponent(this.remapper.mapRecordComponentName(this.className, name, descriptor), this.remapper.mapDesc(descriptor), this.remapper.mapSignature(signature, true));
        return recordComponentVisitor == null ? null : this.createRecordComponentRemapper(recordComponentVisitor);
    }

    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        FieldVisitor fieldVisitor = super.visitField(access, this.remapper.mapFieldName(this.className, name, descriptor), this.remapper.mapDesc(descriptor), this.remapper.mapSignature(signature, true), value == null ? null : this.remapper.mapValue(value));
        return fieldVisitor == null ? null : this.createFieldRemapper(fieldVisitor);
    }

    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        String remappedDescriptor = this.remapper.mapMethodDesc(descriptor);
        MethodVisitor methodVisitor = super.visitMethod(access, this.remapper.mapMethodName(this.className, name, descriptor), remappedDescriptor, this.remapper.mapSignature(signature, false), exceptions == null ? null : this.remapper.mapTypes(exceptions));
        return methodVisitor == null ? null : this.createMethodRemapper(methodVisitor);
    }

    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        super.visitInnerClass(this.remapper.mapType(name), outerName == null ? null : this.remapper.mapType(outerName), innerName == null ? null : this.remapper.mapInnerClassName(name, outerName, innerName), access);
    }

    public void visitOuterClass(String owner, String name, String descriptor) {
        super.visitOuterClass(this.remapper.mapType(owner), name == null ? null : this.remapper.mapMethodName(owner, name, descriptor), descriptor == null ? null : this.remapper.mapMethodDesc(descriptor));
    }

    public void visitNestHost(String nestHost) {
        super.visitNestHost(this.remapper.mapType(nestHost));
    }

    public void visitNestMember(String nestMember) {
        super.visitNestMember(this.remapper.mapType(nestMember));
    }

    public void visitPermittedSubclass(String permittedSubclass) {
        super.visitPermittedSubclass(this.remapper.mapType(permittedSubclass));
    }

    protected FieldVisitor createFieldRemapper(FieldVisitor fieldVisitor) {
        return new FieldRemapper(this.api, fieldVisitor, this.remapper);
    }

    protected MethodVisitor createMethodRemapper(MethodVisitor methodVisitor) {
        return new MethodRemapper(this.api, methodVisitor, this.remapper);
    }

    @Deprecated
    protected AnnotationVisitor createAnnotationRemapper(AnnotationVisitor annotationVisitor) {
        return new AnnotationRemapper(this.api, null, annotationVisitor, this.remapper);
    }

    protected AnnotationVisitor createAnnotationRemapper(String descriptor, AnnotationVisitor annotationVisitor) {
        return new AnnotationRemapper(this.api, descriptor, annotationVisitor, this.remapper).orDeprecatedValue(this.createAnnotationRemapper(annotationVisitor));
    }

    protected ModuleVisitor createModuleRemapper(ModuleVisitor moduleVisitor) {
        return new ModuleRemapper(this.api, moduleVisitor, this.remapper);
    }

    protected RecordComponentVisitor createRecordComponentRemapper(RecordComponentVisitor recordComponentVisitor) {
        return new RecordComponentRemapper(this.api, recordComponentVisitor, this.remapper);
    }
}

