package gg.vape.shaded.javassist.bytecode;

import gg.vape.shaded.javassist.bytecode.annotation.TypeAnnotationsWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TypeAnnotationsAttribute extends AttributeInfo {
   public static final String visibleTag = "RuntimeVisibleTypeAnnotations";
   public static final String invisibleTag = "RuntimeInvisibleTypeAnnotations";

   public TypeAnnotationsAttribute(ConstPool cp, String attrname, byte[] info) {
      super(cp, attrname, info);
   }

   TypeAnnotationsAttribute(ConstPool cp, int n, DataInputStream in) throws IOException {
      super(cp, n, in);
   }

   public int numAnnotations() {
      return ByteArray.readU16bit(this.info, 0);
   }

   @Override
   public AttributeInfo copy(ConstPool newCp, Map<String, String> classnames) {
      TypeAnnotationsAttribute.Copier copier = new TypeAnnotationsAttribute.Copier(this.info, this.constPool, newCp, classnames);

      try {
         copier.annotationArray();
         return new TypeAnnotationsAttribute(newCp, this.getName(), copier.close());
      } catch (Exception var5) {
         throw new RuntimeException(var5);
      }
   }

   @Override
   void renameClass(String oldname, String newname) {
      Map<String, String> map = new HashMap<>();
      map.put(oldname, newname);
      this.renameClass(map);
   }

   @Override
   void renameClass(Map<String, String> classnames) {
      TypeAnnotationsAttribute.Renamer renamer = new TypeAnnotationsAttribute.Renamer(this.info, this.getConstPool(), classnames);

      try {
         renamer.annotationArray();
      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }

   @Override
   void getRefClasses(Map<String, String> classnames) {
      this.renameClass(classnames);
   }

   static class Copier extends AnnotationsAttribute.Copier {
      TypeAnnotationsAttribute.SubCopier sub;

      Copier(byte[] attrInfo, ConstPool src, ConstPool dest, Map<String, String> map) {
         super(attrInfo, src, dest, map, false);
         TypeAnnotationsWriter w = new TypeAnnotationsWriter(this.output, dest);
         this.writer = w;
         this.sub = new TypeAnnotationsAttribute.SubCopier(attrInfo, src, dest, map, w);
      }

      @Override
      int annotationArray(int pos, int num) throws Exception {
         this.writer.numAnnotations(num);

         for (int i = 0; i < num; i++) {
            int targetType = this.info[pos] & 255;
            pos = this.sub.targetInfo(pos + 1, targetType);
            pos = this.sub.typePath(pos);
            pos = this.annotation(pos);
         }

         return pos;
      }
   }

   static class Renamer extends AnnotationsAttribute.Renamer {
      TypeAnnotationsAttribute.SubWalker sub;

      Renamer(byte[] attrInfo, ConstPool cp, Map<String, String> map) {
         super(attrInfo, cp, map);
         this.sub = new TypeAnnotationsAttribute.SubWalker(attrInfo);
      }

      @Override
      int annotationArray(int pos, int num) throws Exception {
         for (int i = 0; i < num; i++) {
            int targetType = this.info[pos] & 255;
            pos = this.sub.targetInfo(pos + 1, targetType);
            pos = this.sub.typePath(pos);
            pos = this.annotation(pos);
         }

         return pos;
      }
   }

   static class SubCopier extends TypeAnnotationsAttribute.SubWalker {
      ConstPool srcPool;
      ConstPool destPool;
      Map<String, String> classnames;
      TypeAnnotationsWriter writer;

      SubCopier(byte[] attrInfo, ConstPool src, ConstPool dest, Map<String, String> map, TypeAnnotationsWriter w) {
         super(attrInfo);
         this.srcPool = src;
         this.destPool = dest;
         this.classnames = map;
         this.writer = w;
      }

      @Override
      void typeParameterTarget(int pos, int targetType, int typeParameterIndex) throws Exception {
         this.writer.typeParameterTarget(targetType, typeParameterIndex);
      }

      @Override
      void supertypeTarget(int pos, int superTypeIndex) throws Exception {
         this.writer.supertypeTarget(superTypeIndex);
      }

      @Override
      void typeParameterBoundTarget(int pos, int targetType, int typeParameterIndex, int boundIndex) throws Exception {
         this.writer.typeParameterBoundTarget(targetType, typeParameterIndex, boundIndex);
      }

      @Override
      void emptyTarget(int pos, int targetType) throws Exception {
         this.writer.emptyTarget(targetType);
      }

      @Override
      void formalParameterTarget(int pos, int formalParameterIndex) throws Exception {
         this.writer.formalParameterTarget(formalParameterIndex);
      }

      @Override
      void throwsTarget(int pos, int throwsTypeIndex) throws Exception {
         this.writer.throwsTarget(throwsTypeIndex);
      }

      @Override
      int localvarTarget(int pos, int targetType, int tableLength) throws Exception {
         this.writer.localVarTarget(targetType, tableLength);
         return super.localvarTarget(pos, targetType, tableLength);
      }

      @Override
      void localvarTarget(int pos, int targetType, int startPc, int length, int index) throws Exception {
         this.writer.localVarTargetTable(startPc, length, index);
      }

      @Override
      void catchTarget(int pos, int exceptionTableIndex) throws Exception {
         this.writer.catchTarget(exceptionTableIndex);
      }

      @Override
      void offsetTarget(int pos, int targetType, int offset) throws Exception {
         this.writer.offsetTarget(targetType, offset);
      }

      @Override
      void typeArgumentTarget(int pos, int targetType, int offset, int typeArgumentIndex) throws Exception {
         this.writer.typeArgumentTarget(targetType, offset, typeArgumentIndex);
      }

      @Override
      int typePath(int pos, int pathLength) throws Exception {
         this.writer.typePath(pathLength);
         return super.typePath(pos, pathLength);
      }

      @Override
      void typePath(int pos, int typePathKind, int typeArgumentIndex) throws Exception {
         this.writer.typePathPath(typePathKind, typeArgumentIndex);
      }
   }

   static class SubWalker {
      byte[] info;

      SubWalker(byte[] attrInfo) {
         this.info = attrInfo;
      }

      final int targetInfo(int pos, int type) throws Exception {
         switch (type) {
            case 0:
            case 1: {
               int index = this.info[pos] & 255;
               this.typeParameterTarget(pos, type, index);
               return pos + 1;
            }
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 24:
            case 25:
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
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            default:
               throw new RuntimeException("invalid target type: " + type);
            case 16: {
               int index = ByteArray.readU16bit(this.info, pos);
               this.supertypeTarget(pos, index);
               return pos + 2;
            }
            case 17:
            case 18:
               int param = this.info[pos] & 255;
               int bound = this.info[pos + 1] & 255;
               this.typeParameterBoundTarget(pos, type, param, bound);
               return pos + 2;
            case 19:
            case 20:
            case 21:
               this.emptyTarget(pos, type);
               return pos;
            case 22: {
               int index = this.info[pos] & 255;
               this.formalParameterTarget(pos, index);
               return pos + 1;
            }
            case 23: {
               int index = ByteArray.readU16bit(this.info, pos);
               this.throwsTarget(pos, index);
               return pos + 2;
            }
            case 64:
            case 65:
               int len = ByteArray.readU16bit(this.info, pos);
               return this.localvarTarget(pos + 2, type, len);
            case 66: {
               int index = ByteArray.readU16bit(this.info, pos);
               this.catchTarget(pos, index);
               return pos + 2;
            }
            case 67:
            case 68:
            case 69:
            case 70: {
               int offset = ByteArray.readU16bit(this.info, pos);
               this.offsetTarget(pos, type, offset);
               return pos + 2;
            }
            case 71:
            case 72:
            case 73:
            case 74:
            case 75: {
               int offset = ByteArray.readU16bit(this.info, pos);
               int index = this.info[pos + 2] & 255;
               this.typeArgumentTarget(pos, type, offset, index);
               return pos + 3;
            }
         }
      }

      void typeParameterTarget(int pos, int targetType, int typeParameterIndex) throws Exception {
      }

      void supertypeTarget(int pos, int superTypeIndex) throws Exception {
      }

      void typeParameterBoundTarget(int pos, int targetType, int typeParameterIndex, int boundIndex) throws Exception {
      }

      void emptyTarget(int pos, int targetType) throws Exception {
      }

      void formalParameterTarget(int pos, int formalParameterIndex) throws Exception {
      }

      void throwsTarget(int pos, int throwsTypeIndex) throws Exception {
      }

      int localvarTarget(int pos, int targetType, int tableLength) throws Exception {
         for (int i = 0; i < tableLength; i++) {
            int start = ByteArray.readU16bit(this.info, pos);
            int length = ByteArray.readU16bit(this.info, pos + 2);
            int index = ByteArray.readU16bit(this.info, pos + 4);
            this.localvarTarget(pos, targetType, start, length, index);
            pos += 6;
         }

         return pos;
      }

      void localvarTarget(int pos, int targetType, int startPc, int length, int index) throws Exception {
      }

      void catchTarget(int pos, int exceptionTableIndex) throws Exception {
      }

      void offsetTarget(int pos, int targetType, int offset) throws Exception {
      }

      void typeArgumentTarget(int pos, int targetType, int offset, int typeArgumentIndex) throws Exception {
      }

      final int typePath(int pos) throws Exception {
         int len = this.info[pos++] & 255;
         return this.typePath(pos, len);
      }

      int typePath(int pos, int pathLength) throws Exception {
         for (int i = 0; i < pathLength; i++) {
            int kind = this.info[pos] & 255;
            int index = this.info[pos + 1] & 255;
            this.typePath(pos, kind, index);
            pos += 2;
         }

         return pos;
      }

      void typePath(int pos, int typePathKind, int typeArgumentIndex) throws Exception {
      }
   }

   static class TAWalker extends AnnotationsAttribute.Walker {
      TypeAnnotationsAttribute.SubWalker subWalker;

      TAWalker(byte[] attrInfo) {
         super(attrInfo);
         this.subWalker = new TypeAnnotationsAttribute.SubWalker(attrInfo);
      }

      @Override
      int annotationArray(int pos, int num) throws Exception {
         for (int i = 0; i < num; i++) {
            int targetType = this.info[pos] & 255;
            pos = this.subWalker.targetInfo(pos + 1, targetType);
            pos = this.subWalker.typePath(pos);
            pos = this.annotation(pos);
         }

         return pos;
      }
   }
}
