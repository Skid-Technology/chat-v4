package gg.vape.shaded.org.objectweb.asm.commons;

import gg.vape.shaded.org.objectweb.asm.ClassVisitor;
import gg.vape.shaded.org.objectweb.asm.FieldVisitor;
import gg.vape.shaded.org.objectweb.asm.MethodVisitor;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class SerialVersionUIDAdder extends ClassVisitor {
   private static final String CLINIT = "<clinit>";
   private boolean computeSvuid;
   private boolean hasSvuid;
   private int access;
   private String name;
   private String[] interfaces;
   private Collection<SerialVersionUIDAdder.Item> svuidFields;
   private boolean hasStaticInitializer;
   private Collection<SerialVersionUIDAdder.Item> svuidConstructors;
   private Collection<SerialVersionUIDAdder.Item> svuidMethods;

   public SerialVersionUIDAdder(ClassVisitor classVisitor) {
      this(589824, classVisitor);
      if (this.getClass() != SerialVersionUIDAdder.class) {
         throw new IllegalStateException();
      }
   }

   protected SerialVersionUIDAdder(int api, ClassVisitor classVisitor) {
      super(api, classVisitor);
   }

   @Override
   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
      this.computeSvuid = (access & 16384) == 0;
      if (this.computeSvuid) {
         this.name = name;
         this.access = access;
         this.interfaces = (String[])interfaces.clone();
         this.svuidFields = new ArrayList<>();
         this.svuidConstructors = new ArrayList<>();
         this.svuidMethods = new ArrayList<>();
      }

      super.visit(version, access, name, signature, superName, interfaces);
   }

   @Override
   public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
      if (this.computeSvuid) {
         if ("<clinit>".equals(name)) {
            this.hasStaticInitializer = true;
         }

         int mods = access & 3391;
         if ((access & 2) == 0) {
            if ("<init>".equals(name)) {
               this.svuidConstructors.add(new SerialVersionUIDAdder.Item(name, mods, descriptor));
            } else if (!"<clinit>".equals(name)) {
               this.svuidMethods.add(new SerialVersionUIDAdder.Item(name, mods, descriptor));
            }
         }
      }

      return super.visitMethod(access, name, descriptor, signature, exceptions);
   }

   @Override
   public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
      if (this.computeSvuid) {
         if ("serialVersionUID".equals(name)) {
            this.computeSvuid = false;
            this.hasSvuid = true;
         }

         if ((access & 2) == 0 || (access & 136) == 0) {
            int mods = access & 223;
            this.svuidFields.add(new SerialVersionUIDAdder.Item(name, mods, desc));
         }
      }

      return super.visitField(access, name, desc, signature, value);
   }

   @Override
   public void visitInnerClass(String innerClassName, String outerName, String innerName, int innerClassAccess) {
      if (this.name != null && this.name.equals(innerClassName)) {
         this.access = innerClassAccess;
      }

      super.visitInnerClass(innerClassName, outerName, innerName, innerClassAccess);
   }

   @Override
   public void visitEnd() {
      if (this.computeSvuid && !this.hasSvuid) {
         try {
            this.addSVUID(this.computeSVUID());
         } catch (IOException var2) {
            throw new IllegalStateException(stringConcat$0(this.name), var2);
         }
      }

      super.visitEnd();
   }

   public boolean hasSVUID() {
      return this.hasSvuid;
   }

   protected void addSVUID(long svuid) {
      FieldVisitor fieldVisitor = super.visitField(24, "serialVersionUID", "J", null, svuid);
      if (fieldVisitor != null) {
         fieldVisitor.visitEnd();
      }
   }

   protected long computeSVUID() throws IOException {
      long svuid = 0L;
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

      try {
         DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

         try {
            dataOutputStream.writeUTF(this.name.replace('/', '.'));
            int mods = this.access;
            if ((mods & 512) != 0) {
               mods = this.svuidMethods.isEmpty() ? mods & -1025 : mods | 1024;
            }

            dataOutputStream.writeInt(mods & 1553);
            Arrays.sort((Object[])this.interfaces);

            for (String interfaceName : this.interfaces) {
               dataOutputStream.writeUTF(interfaceName.replace('/', '.'));
            }

            writeItems(this.svuidFields, dataOutputStream, false);
            if (this.hasStaticInitializer) {
               dataOutputStream.writeUTF("<clinit>");
               dataOutputStream.writeInt(8);
               dataOutputStream.writeUTF("()V");
            }

            writeItems(this.svuidConstructors, dataOutputStream, true);
            writeItems(this.svuidMethods, dataOutputStream, true);
            dataOutputStream.flush();
            byte[] hashBytes = this.computeSHAdigest(byteArrayOutputStream.toByteArray());

            for (int i = Math.min(hashBytes.length, 8) - 1; i >= 0; i--) {
               svuid = svuid << 8 | hashBytes[i] & 255;
            }
         } catch (Throwable var12) {
            try {
               dataOutputStream.close();
            } catch (Throwable var11) {
            }

            throw var12;
         }

         dataOutputStream.close();
      } catch (Throwable var13) {
         try {
            byteArrayOutputStream.close();
         } catch (Throwable var10) {
         }

         throw var13;
      }

      byteArrayOutputStream.close();
      return svuid;
   }

   protected byte[] computeSHAdigest(byte[] value) {
      try {
         return MessageDigest.getInstance("SHA").digest(value);
      } catch (NoSuchAlgorithmException var3) {
         throw new UnsupportedOperationException(var3);
      }
   }

   private static void writeItems(Collection<SerialVersionUIDAdder.Item> itemCollection, DataOutput dataOutputStream, boolean dotted) throws IOException {
      SerialVersionUIDAdder.Item[] items = itemCollection.toArray(new SerialVersionUIDAdder.Item[0]);
      Arrays.sort((Object[])items);

      for (SerialVersionUIDAdder.Item item : items) {
         dataOutputStream.writeUTF(item.name);
         dataOutputStream.writeInt(item.access);
         dataOutputStream.writeUTF(dotted ? item.descriptor.replace('/', '.') : item.descriptor);
      }
   }

   private static final class Item implements Comparable<SerialVersionUIDAdder.Item> {
      final String name;
      final int access;
      final String descriptor;

      Item(String name, int access, String descriptor) {
         this.name = name;
         this.access = access;
         this.descriptor = descriptor;
      }

      public int compareTo(SerialVersionUIDAdder.Item item) {
         int result = this.name.compareTo(item.name);
         if (result == 0) {
            result = this.descriptor.compareTo(item.descriptor);
         }

         return result;
      }

      @Override
      public boolean equals(Object other) {
         return other instanceof SerialVersionUIDAdder.Item ? this.compareTo((SerialVersionUIDAdder.Item)other) == 0 : false;
      }

      @Override
      public int hashCode() {
         return this.name.hashCode() ^ this.descriptor.hashCode();
      }
   }
}
