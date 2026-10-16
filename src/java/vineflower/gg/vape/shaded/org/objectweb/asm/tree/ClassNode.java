package gg.vape.shaded.org.objectweb.asm.tree;

import gg.vape.shaded.org.objectweb.asm.AnnotationVisitor;
import gg.vape.shaded.org.objectweb.asm.Attribute;
import gg.vape.shaded.org.objectweb.asm.ClassVisitor;
import gg.vape.shaded.org.objectweb.asm.FieldVisitor;
import gg.vape.shaded.org.objectweb.asm.MethodVisitor;
import gg.vape.shaded.org.objectweb.asm.ModuleVisitor;
import gg.vape.shaded.org.objectweb.asm.RecordComponentVisitor;
import gg.vape.shaded.org.objectweb.asm.TypePath;
import java.util.ArrayList;
import java.util.List;

public class ClassNode extends ClassVisitor {
   public int version;
   public int access;
   public String name;
   public String signature;
   public String superName;
   public List<String> interfaces = new ArrayList<>();
   public String sourceFile;
   public String sourceDebug;
   public ModuleNode module;
   public String outerClass;
   public String outerMethod;
   public String outerMethodDesc;
   public List<AnnotationNode> visibleAnnotations;
   public List<AnnotationNode> invisibleAnnotations;
   public List<TypeAnnotationNode> visibleTypeAnnotations;
   public List<TypeAnnotationNode> invisibleTypeAnnotations;
   public List<Attribute> attrs;
   public List<InnerClassNode> innerClasses = new ArrayList<>();
   public String nestHostClass;
   public List<String> nestMembers;
   public List<String> permittedSubclasses;
   public List<RecordComponentNode> recordComponents;
   public List<FieldNode> fields = new ArrayList<>();
   public List<MethodNode> methods = new ArrayList<>();

   public ClassNode() {
      this(589824);
      if (this.getClass() != ClassNode.class) {
         throw new IllegalStateException();
      }
   }

   public ClassNode(int api) {
      super(api);
   }

   @Override
   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
      this.version = version;
      this.access = access;
      this.name = name;
      this.signature = signature;
      this.superName = superName;
      this.interfaces = Util.asArrayList(interfaces);
   }

   @Override
   public void visitSource(String file, String debug) {
      this.sourceFile = file;
      this.sourceDebug = debug;
   }

   @Override
   public ModuleVisitor visitModule(String name, int access, String version) {
      this.module = new ModuleNode(name, access, version);
      return this.module;
   }

   @Override
   public void visitNestHost(String nestHost) {
      this.nestHostClass = nestHost;
   }

   @Override
   public void visitOuterClass(String owner, String name, String descriptor) {
      this.outerClass = owner;
      this.outerMethod = name;
      this.outerMethodDesc = descriptor;
   }

   @Override
   public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
      AnnotationNode annotation = new AnnotationNode(descriptor);
      if (visible) {
         this.visibleAnnotations = Util.add(this.visibleAnnotations, annotation);
      } else {
         this.invisibleAnnotations = Util.add(this.invisibleAnnotations, annotation);
      }

      return annotation;
   }

   @Override
   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
      TypeAnnotationNode typeAnnotation = new TypeAnnotationNode(typeRef, typePath, descriptor);
      if (visible) {
         this.visibleTypeAnnotations = Util.add(this.visibleTypeAnnotations, typeAnnotation);
      } else {
         this.invisibleTypeAnnotations = Util.add(this.invisibleTypeAnnotations, typeAnnotation);
      }

      return typeAnnotation;
   }

   @Override
   public void visitAttribute(Attribute attribute) {
      this.attrs = Util.add(this.attrs, attribute);
   }

   @Override
   public void visitNestMember(String nestMember) {
      this.nestMembers = Util.add(this.nestMembers, nestMember);
   }

   @Override
   public void visitPermittedSubclass(String permittedSubclass) {
      this.permittedSubclasses = Util.add(this.permittedSubclasses, permittedSubclass);
   }

   @Override
   public void visitInnerClass(String name, String outerName, String innerName, int access) {
      InnerClassNode innerClass = new InnerClassNode(name, outerName, innerName, access);
      this.innerClasses.add(innerClass);
   }

   @Override
   public RecordComponentVisitor visitRecordComponent(String name, String descriptor, String signature) {
      RecordComponentNode recordComponent = new RecordComponentNode(name, descriptor, signature);
      this.recordComponents = Util.add(this.recordComponents, recordComponent);
      return recordComponent;
   }

   @Override
   public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
      FieldNode field = new FieldNode(access, name, descriptor, signature, value);
      this.fields.add(field);
      return field;
   }

   @Override
   public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
      MethodNode method = new MethodNode(access, name, descriptor, signature, exceptions);
      this.methods.add(method);
      return method;
   }

   @Override
   public void visitEnd() {
   }

   public void check(int api) {
      if (api < 589824 && this.permittedSubclasses != null) {
         throw new UnsupportedClassVersionException();
      } else if (api >= 524288 || (this.access & 65536) == 0 && this.recordComponents == null) {
         if (api >= 458752 || this.nestHostClass == null && this.nestMembers == null) {
            if (api < 393216 && this.module != null) {
               throw new UnsupportedClassVersionException();
            } else {
               if (api < 327680) {
                  if (this.visibleTypeAnnotations != null && !this.visibleTypeAnnotations.isEmpty()) {
                     throw new UnsupportedClassVersionException();
                  }

                  if (this.invisibleTypeAnnotations != null && !this.invisibleTypeAnnotations.isEmpty()) {
                     throw new UnsupportedClassVersionException();
                  }
               }

               if (this.visibleAnnotations != null) {
                  for (int i = this.visibleAnnotations.size() - 1; i >= 0; i--) {
                     this.visibleAnnotations.get(i).check(api);
                  }
               }

               if (this.invisibleAnnotations != null) {
                  for (int i = this.invisibleAnnotations.size() - 1; i >= 0; i--) {
                     this.invisibleAnnotations.get(i).check(api);
                  }
               }

               if (this.visibleTypeAnnotations != null) {
                  for (int i = this.visibleTypeAnnotations.size() - 1; i >= 0; i--) {
                     this.visibleTypeAnnotations.get(i).check(api);
                  }
               }

               if (this.invisibleTypeAnnotations != null) {
                  for (int i = this.invisibleTypeAnnotations.size() - 1; i >= 0; i--) {
                     this.invisibleTypeAnnotations.get(i).check(api);
                  }
               }

               if (this.recordComponents != null) {
                  for (int i = this.recordComponents.size() - 1; i >= 0; i--) {
                     this.recordComponents.get(i).check(api);
                  }
               }

               for (int i = this.fields.size() - 1; i >= 0; i--) {
                  this.fields.get(i).check(api);
               }

               for (int i = this.methods.size() - 1; i >= 0; i--) {
                  this.methods.get(i).check(api);
               }
            }
         } else {
            throw new UnsupportedClassVersionException();
         }
      } else {
         throw new UnsupportedClassVersionException();
      }
   }

   public void accept(ClassVisitor classVisitor) {
      String[] interfacesArray = new String[this.interfaces.size()];
      this.interfaces.toArray(interfacesArray);
      classVisitor.visit(this.version, this.access, this.name, this.signature, this.superName, interfacesArray);
      if (this.sourceFile != null || this.sourceDebug != null) {
         classVisitor.visitSource(this.sourceFile, this.sourceDebug);
      }

      if (this.module != null) {
         this.module.accept(classVisitor);
      }

      if (this.nestHostClass != null) {
         classVisitor.visitNestHost(this.nestHostClass);
      }

      if (this.outerClass != null) {
         classVisitor.visitOuterClass(this.outerClass, this.outerMethod, this.outerMethodDesc);
      }

      if (this.visibleAnnotations != null) {
         int i = 0;

         for (int n = this.visibleAnnotations.size(); i < n; i++) {
            AnnotationNode annotation = this.visibleAnnotations.get(i);
            annotation.accept(classVisitor.visitAnnotation(annotation.desc, true));
         }
      }

      if (this.invisibleAnnotations != null) {
         int i = 0;

         for (int n = this.invisibleAnnotations.size(); i < n; i++) {
            AnnotationNode annotation = this.invisibleAnnotations.get(i);
            annotation.accept(classVisitor.visitAnnotation(annotation.desc, false));
         }
      }

      if (this.visibleTypeAnnotations != null) {
         int i = 0;

         for (int n = this.visibleTypeAnnotations.size(); i < n; i++) {
            TypeAnnotationNode typeAnnotation = this.visibleTypeAnnotations.get(i);
            typeAnnotation.accept(classVisitor.visitTypeAnnotation(typeAnnotation.typeRef, typeAnnotation.typePath, typeAnnotation.desc, true));
         }
      }

      if (this.invisibleTypeAnnotations != null) {
         int i = 0;

         for (int n = this.invisibleTypeAnnotations.size(); i < n; i++) {
            TypeAnnotationNode typeAnnotation = this.invisibleTypeAnnotations.get(i);
            typeAnnotation.accept(classVisitor.visitTypeAnnotation(typeAnnotation.typeRef, typeAnnotation.typePath, typeAnnotation.desc, false));
         }
      }

      if (this.attrs != null) {
         int i = 0;

         for (int n = this.attrs.size(); i < n; i++) {
            classVisitor.visitAttribute(this.attrs.get(i));
         }
      }

      if (this.nestMembers != null) {
         int i = 0;

         for (int n = this.nestMembers.size(); i < n; i++) {
            classVisitor.visitNestMember(this.nestMembers.get(i));
         }
      }

      if (this.permittedSubclasses != null) {
         int i = 0;

         for (int n = this.permittedSubclasses.size(); i < n; i++) {
            classVisitor.visitPermittedSubclass(this.permittedSubclasses.get(i));
         }
      }

      int i = 0;

      for (int n = this.innerClasses.size(); i < n; i++) {
         this.innerClasses.get(i).accept(classVisitor);
      }

      if (this.recordComponents != null) {
         i = 0;

         for (int n = this.recordComponents.size(); i < n; i++) {
            this.recordComponents.get(i).accept(classVisitor);
         }
      }

      i = 0;

      for (int n = this.fields.size(); i < n; i++) {
         this.fields.get(i).accept(classVisitor);
      }

      i = 0;

      for (int n = this.methods.size(); i < n; i++) {
         this.methods.get(i).accept(classVisitor);
      }

      classVisitor.visitEnd();
   }
}
