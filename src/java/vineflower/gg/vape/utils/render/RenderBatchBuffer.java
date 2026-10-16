package gg.vape.utils.render;

import gg.vape.Vape;
import gg.vape.wrapper.impl.GL20;
import gg.vape.wrapper.impl.GlStateManager;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public class RenderBatchBuffer {
   private RenderMatrix4f modelMatrix;
   private final FloatBufferObject vertexBufferObject;
   private IntBuffer indexBuffer;
   private GlImageTexture texture;
   private RenderBatchShaderProgram shaderProgram;
   private float lineWidth;
   private FloatBuffer vertexBuffer;
   private int vertexStride = 0;
   private PrimitiveTopology topology;
   private final VertexArrayObject vertexArrayObject;
   private final IntBufferObject indexBufferObject;

   public void stageBatch(RenderBatch renderBatch) {
      this.texture = renderBatch.getTexture();

      for (RenderBatchBuilder renderBatchBuilder : renderBatch.getBuilders()) {
         this.vertexBuffer.put(renderBatchBuilder.getVertexData());
         this.indexBuffer.put(renderBatchBuilder.getIndices());
      }

      this.topology = renderBatch.getTopology();
      this.modelMatrix = renderBatch.getModelMatrix();
      this.lineWidth = renderBatch.getLineWidth();
   }

   public void deleteResources() {
      this.vertexArrayObject.delete();
      this.vertexBufferObject.delete();
      this.indexBufferObject.delete();
   }

   public int getIndexCapacity() {
      return this.indexBuffer.capacity();
   }

   public void draw() {
      int vertexFloatCount = this.vertexBuffer.position();
      int indexCount = this.indexBuffer.position();
      if (vertexFloatCount != 0 && indexCount != 0) {
         GL20.w(this.shaderProgram.modelUniformLocation, false, this.modelMatrix.toFloatBuffer());
         if (this.topology == PrimitiveTopology.LINES || this.topology == PrimitiveTopology.LINES_LOOP) {
            OpenGlBackendHolder.backend.enableCapability(2848);
         }

         if (this.topology == null) {
            Vape.debugLog("Drawmode null: something fucked up");
         } else {
            int previousTextureId = GL11.glGetInteger(32873);
            if (this.texture != null) {
               GlTextureUnitState.saveAndClearTextureUnitZero();
               this.texture.bind();
            }

            ((Buffer)this.vertexBuffer).flip();
            ((Buffer)this.indexBuffer).flip();
            this.vertexBufferObject.upload(this.vertexBuffer);
            this.indexBufferObject.upload(this.indexBuffer);
            GL11.glDrawElements(this.topology.getOpenGlMode(), indexCount, 5125, 0L);
            this.clearStagingBuffers();
            if (this.texture != null) {
               GlStateManager.bindTexture(previousTextureId);
               GlTextureUnitState.restoreTextureUnitZero();
            }

            if (this.topology == PrimitiveTopology.LINES || this.topology == PrimitiveTopology.LINES_LOOP) {
               OpenGlBackendHolder.backend.disableCapability(2848);
            }
         }
      } else {
         throw new IllegalStateException("Number of vertices can't be 0");
      }
   }

   public RenderBatchBuffer(RenderBatchShaderProgram shaderProgram, int maxVertices, VertexAttributeType... vertexAttributes) {
      for (VertexAttributeType vertexAttribute : vertexAttributes) {
         this.vertexStride = this.vertexStride + vertexAttribute.count;
      }

      this.shaderProgram = shaderProgram;
      this.topology = null;
      this.vertexBuffer = BufferUtils.createFloatBuffer(maxVertices * this.vertexStride * 4);
      this.indexBuffer = BufferUtils.createIntBuffer(maxVertices * 6);
      this.vertexArrayObject = new VertexArrayObject();
      this.vertexArrayObject.bindAndRememberPrevious();
      this.vertexBufferObject = new FloatBufferObject();
      this.vertexBufferObject.bind();
      GL15.glBufferData(34962, this.vertexBuffer.capacity() * 4L, 35048);
      int attributeOffset = 0;

      for (int attributeIndex = 0; attributeIndex < vertexAttributes.length; attributeIndex++) {
         VertexAttributeType vertexAttribute = vertexAttributes[attributeIndex];
         org.lwjgl.opengl.GL20.glVertexAttribPointer(
            attributeIndex, vertexAttribute.count, vertexAttribute.type, vertexAttribute.normalized, this.vertexStride * 4, attributeOffset * 4L
         );
         org.lwjgl.opengl.GL20.glEnableVertexAttribArray(attributeIndex);
         attributeOffset += vertexAttribute.count;
      }

      this.indexBufferObject = new IntBufferObject();
      this.indexBufferObject.bind();
      GL15.glBufferData(34963, this.indexBuffer.capacity() * 4L, 35048);
      this.configureTextureSampler();
      this.vertexArrayObject.restorePreviousBinding();
   }

   public int getVertexCapacity() {
      return this.vertexBuffer.capacity();
   }

   public void bindResources() {
      this.vertexArrayObject.bindAndRememberPrevious();
      this.shaderProgram.bind();
      this.vertexBufferObject.bind();
      this.indexBufferObject.bind();
      GL20.w(this.shaderProgram.projectionUniformLocation, false, BufferedGuiRenderPrimitives.projectionMatrix.toFloatBuffer());
      GL20.w(this.shaderProgram.viewUniformLocation, false, BufferedGuiRenderPrimitives.viewMatrix.toFloatBuffer());
   }

   public int getVertexStride() {
      return this.vertexStride;
   }

   private void configureTextureSampler() {
      int previousProgramId = GL11.glGetInteger(35725);

      try {
         if (this.shaderProgram == null || this.shaderProgram.programId <= 0) {
            throw new IllegalStateException("Universal shader program was not created");
         }

         org.lwjgl.opengl.GL20.glUseProgram(this.shaderProgram.programId);
         int samplerUniformLocation = org.lwjgl.opengl.GL20.glGetUniformLocation(this.shaderProgram.programId, "imgTexture");
         if (samplerUniformLocation < 0) {
            throw new IllegalStateException("Failed to resolve shader uniform 'imgTexture' (location=" + samplerUniformLocation + ")");
         }

         org.lwjgl.opengl.GL20.glUniform1i(samplerUniformLocation, 0);
         int openGlError = GL11.glGetError();
         if (openGlError != 0) {
            throw new IllegalStateException("OpenGL error " + openGlError + " after glUniform1i(imgTexture, 0)");
         }
      } catch (Throwable var7) {
         throw RenderBatchManager.initializationFailure("mesh uniform setup", var7);
      } finally {
         org.lwjgl.opengl.GL20.glUseProgram(previousProgramId);
      }
   }

   private static Throwable propagateThrowable(Throwable throwable) {
      return throwable;
   }

   public void clearStagingBuffers() {
      ((Buffer)this.indexBuffer).clear();
      ((Buffer)this.vertexBuffer).clear();
   }
}
