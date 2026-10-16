package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

public class MTextureObjectHandle extends Mapping {
   private MappingField textureIdField;
   private MappingField framebufferCacheOrTextureIdField;
   private MappingField firstFramebufferDepthIdField;
   private MappingField firstFramebufferIdField;
   private MappingField backendField;
   private MappingField frameBufferCacheField;
   private MappingMethod getFboMethod;
   private MappingMethod directStateAccessMethod;

   public MTextureObjectHandle() {
      this(MRenderBatchFlushTarget.getGlCommandEncoderControlFlowState());
   }

   private MTextureObjectHandle(int n) {
      super(MappedClasses.uA);
      if (n != 0) {
         Class<Integer> clazz = int.class;
         boolean bl = true;
         String string = "id";
         this.framebufferCacheOrTextureIdField = this.J(string, bl, clazz);
         if (GuiComponent.getLegacyComponentState() == null) {
            int var19 = n + 1;
            MRenderBatchFlushTarget.setGlCommandEncoderControlFlowState(var19);
         }
      } else {
         Class<Integer> clazz = int.class;
         boolean bl = true;
         String string = "id";
         this.textureIdField = this.J(string, bl, clazz);
         if (ForgeVersion.MC_26_2.d()) {
            this.backendField = this.registerInstanceFieldForOwner(MappedClasses.GPU_DEVICE, "backend", true, MappedClasses.GPU_DEVICE_BACKEND);
            this.frameBufferCacheField = this.J("frameBufferCache", true, MappedClasses.FRAME_BUFFER_CACHE);
            this.getFboMethod = this.registerInstanceMethodForOwner(
               MappedClasses.FRAME_BUFFER_CACHE,
               "getFbo",
               true,
               int.class,
               MappedClasses.DIRECT_STATE_ACCESS,
               List.class,
               MappedClasses.FRAME_BUFFER_ATTACHMENT
            );
            this.directStateAccessMethod = this.registerInstanceMethodForOwner(
               MappedClasses.GL_DEVICE, "directStateAccess", true, MappedClasses.DIRECT_STATE_ACCESS
            );
         } else if (ForgeVersion.MC_1_21_11.d()) {
            Class<Integer> clazz2 = int.class;
            boolean bl2 = true;
            String string2 = "firstFboId";
            this.firstFramebufferIdField = this.J(string2, bl2, clazz2);
            Class<Integer> clazz3 = int.class;
            boolean bl3 = true;
            String string3 = "firstFboDepthId";
            this.firstFramebufferDepthIdField = this.J(string3, bl3, clazz3);
            Class clazz4 = MappedClasses.FG;
            boolean bl4 = true;
            String string4 = "fboCache";
            this.framebufferCacheOrTextureIdField = this.J(string4, bl4, clazz4);
         } else {
            Class clazz5 = MappedClasses.FG;
            boolean bl5 = true;
            String string5 = "fboCache";
            this.framebufferCacheOrTextureIdField = this.J(string5, bl5, clazz5);
         }

         if (GuiComponent.getLegacyComponentState() == null) {
            int n2 = n + 1;
            MRenderBatchFlushTarget.setGlCommandEncoderControlFlowState(n2);
         }
      }
   }

   public int d(Object texture, Object depthTexture) {
      try {
         if (ForgeVersion.MC_26_2.v()) {
            int n3 = depthTexture == null ? 0 : this.getTextureId(depthTexture);
            return this.resolveFramebufferId(texture, n3);
         } else {
            Object gpuDevice = Vape.INSTANCE.getMappings().f.getDevice();
            Object backend = this.backendField.getObject(gpuDevice);
            Object directStateAccess = this.directStateAccessMethod.invokeObject(backend);
            Object frameBufferCache = this.frameBufferCacheField.getObject(texture);
            return this.getFboMethod.invokeInt(frameBufferCache, directStateAccess, Collections.singletonList(texture), depthTexture);
         }
      } catch (Exception var7) {
         return -1;
      }
   }

   public int resolveFramebufferId(Object texture, int depthTextureId) {
      try {
         if (ForgeVersion.MC_26_2.d()) {
            return -1;
         } else if (ForgeVersion.MC_1_21_11.d()) {
            if (this.firstFramebufferIdField != null && this.firstFramebufferDepthIdField != null) {
               int n4 = this.firstFramebufferDepthIdField.getInt(texture);
               int n3;
               if (n4 == depthTextureId && (n3 = this.firstFramebufferIdField.getInt(texture)) != -1) {
                  return n3;
               }

               Object object3;
               int n2;
               if (this.framebufferCacheOrTextureIdField != null
                  && (object3 = this.framebufferCacheOrTextureIdField.getObject(texture)) != null
                  && (n2 = (Integer)object3.getClass().getMethod("get", int.class).invoke(object3, depthTextureId)) != 0) {
                  return n2;
               }
            }

            return -1;
         } else if (this.framebufferCacheOrTextureIdField == null) {
            return -1;
         } else {
            Object object4 = this.framebufferCacheOrTextureIdField.getObject(texture);
            if (object4 == null) {
               return -1;
            } else {
               Method method = object4.getClass().getMethod("get", int.class);
               Object object5 = method.invoke(object4, depthTextureId);
               return (Integer)object5;
            }
         }
      } catch (Exception var9) {
         return -1;
      }
   }

   public int getTextureId(Object texture) {
      return this.textureIdField.getInt(texture);
   }
}
