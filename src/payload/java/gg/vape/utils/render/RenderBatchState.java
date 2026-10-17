package gg.vape.utils.render;

import gg.vape.Vape;
import gg.vape.module.blatant.invwalk.InvWalkKeyLayout;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL33;

public class RenderBatchState {
   private FloatBuffer instanceDataBuffer;
   private int cubeVertexBufferId;
   private int instanceBufferId;
   private static RenderBatchState instance;
   private int vertexArrayId;
   private int cubeIndexBufferId;
   private int previousFramebufferId;
   private int instanceCount;
   private int previousElementArrayBufferId;
   private int instanceCapacity;
   private static final int INSTANCE_FLOAT_COUNT = 7;
   private int previousArrayBufferId;
   private int previousVertexArrayId;
   private boolean initialized;
   private int previousProgramId;
   private static final int CUBE_INDEX_COUNT = 24;

   public void cleanup() {
      if (this.initialized) {
         GL15.glDeleteBuffers(this.cubeVertexBufferId);
         GL15.glDeleteBuffers(this.instanceBufferId);
         GL15.glDeleteBuffers(this.cubeIndexBufferId);
         GL30.glDeleteVertexArrays(this.vertexArrayId);
         this.initialized = false;
         Vape.debugLog("InstancedBlockRenderer cleaned up");
      }
   }

   private void restoreGlBindings() {
      GL30.glBindVertexArray(this.previousVertexArrayId);
      GL20.glUseProgram(this.previousProgramId);
      GL15.glBindBuffer(34962, this.previousArrayBufferId);
      GL15.glBindBuffer(34963, this.previousElementArrayBufferId);
      GL30.glBindFramebuffer(36160, this.previousFramebufferId);
   }

   public void addInstance(float x, float y, float z, float red, float green, float blue, float alpha) {
      if (this.instanceCount >= this.instanceCapacity) {
         this.growInstanceCapacity();
      }

      this.instanceDataBuffer.put(x);
      this.instanceDataBuffer.put(y);
      this.instanceDataBuffer.put(z);
      this.instanceDataBuffer.put(red);
      this.instanceDataBuffer.put(green);
      this.instanceDataBuffer.put(blue);
      this.instanceDataBuffer.put(alpha);
      this.instanceCount++;
   }

   public void render(RenderMatrix4f projectionMatrix, RenderMatrix4f viewMatrix, RenderMatrix4f modelMatrix) {
      if (this.instanceCount != 0 && this.initialized) {
         RenderBatchShaderProgram shaderProgram = InvWalkKeyLayout.blockEspShader;
         if (shaderProgram != null) {
            this.captureGlBindings();
            int targetFramebufferId = RenderBatchManager.getInstance().getTargetFramebufferId();
            if (targetFramebufferId != -1) {
               GL30.glBindFramebuffer(36160, targetFramebufferId);
            }

            if (GL11.glIsEnabled(2884)) {
               GL11.glDisable(2884);
               GL30.glBindVertexArray(this.vertexArrayId);
               shaderProgram.bind();
               FloatBuffer projectionBuffer = projectionMatrix.toFloatBuffer();
               FloatBuffer viewBuffer = viewMatrix.toFloatBuffer();
               FloatBuffer modelBuffer = modelMatrix.toFloatBuffer();
               gg.vape.wrapper.impl.GL20.w(shaderProgram.projectionUniformLocation, false, projectionBuffer);
               gg.vape.wrapper.impl.GL20.w(shaderProgram.viewUniformLocation, false, viewBuffer);
               gg.vape.wrapper.impl.GL20.w(shaderProgram.modelUniformLocation, false, modelBuffer);
               ((Buffer)this.instanceDataBuffer).flip();
               GL15.glBindBuffer(34962, this.instanceBufferId);
               GL15.glBufferSubData(34962, 0L, this.instanceDataBuffer);
               GL31.glDrawElementsInstanced(1, CUBE_INDEX_COUNT, 5125, 0L, this.instanceCount);
               GL11.glEnable(2884);
               this.restoreGlBindings();
            } else {
               GL30.glBindVertexArray(this.vertexArrayId);
               shaderProgram.bind();
               FloatBuffer projectionBuffer = projectionMatrix.toFloatBuffer();
               FloatBuffer viewBuffer = viewMatrix.toFloatBuffer();
               FloatBuffer modelBuffer = modelMatrix.toFloatBuffer();
               gg.vape.wrapper.impl.GL20.w(shaderProgram.projectionUniformLocation, false, projectionBuffer);
               gg.vape.wrapper.impl.GL20.w(shaderProgram.viewUniformLocation, false, viewBuffer);
               gg.vape.wrapper.impl.GL20.w(shaderProgram.modelUniformLocation, false, modelBuffer);
               ((Buffer)this.instanceDataBuffer).flip();
               GL15.glBindBuffer(34962, this.instanceBufferId);
               GL15.glBufferSubData(34962, 0L, this.instanceDataBuffer);
               GL31.glDrawElementsInstanced(1, CUBE_INDEX_COUNT, 5125, 0L, this.instanceCount);
               this.restoreGlBindings();
            }
         }
      }
   }

   public boolean isInitialized() {
      return this.initialized;
   }

   private void initialize() {
      if (!this.initialized) {
         if (InvWalkKeyLayout.blockEspShader == null) {
            InvWalkKeyLayout.initializeShaders();
         }

         this.vertexArrayId = GL30.glGenVertexArrays();
         GL30.glBindVertexArray(this.vertexArrayId);
         float[] cubeVertices = new float[]{
            0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, 1.0F, 1.0F
         };
         FloatBuffer cubeVertexBuffer = BufferUtils.createFloatBuffer(cubeVertices.length);
         ((Buffer)cubeVertexBuffer.put(cubeVertices)).flip();
         this.cubeVertexBufferId = GL15.glGenBuffers();
         GL15.glBindBuffer(34962, this.cubeVertexBufferId);
         GL15.glBufferData(34962, cubeVertexBuffer, 35044);
         GL20.glVertexAttribPointer(0, 3, 5126, false, 12, 0L);
         GL20.glEnableVertexAttribArray(0);
         int[] cubeIndices = new int[]{0, 1, 1, 2, 2, 3, 3, 0, 4, 5, 5, 6, 6, 7, 7, 4, 0, 4, 1, 5, 2, 6, 3, 7};
         IntBuffer cubeIndexBuffer = BufferUtils.createIntBuffer(cubeIndices.length);
         ((Buffer)cubeIndexBuffer.put(cubeIndices)).flip();
         this.cubeIndexBufferId = GL15.glGenBuffers();
         GL15.glBindBuffer(34963, this.cubeIndexBufferId);
         GL15.glBufferData(34963, cubeIndexBuffer, 35044);
         this.instanceBufferId = GL15.glGenBuffers();
         GL15.glBindBuffer(34962, this.instanceBufferId);
         GL15.glBufferData(34962, (long)this.instanceCapacity * INSTANCE_FLOAT_COUNT * 4L, 35048);
         GL20.glVertexAttribPointer(1, 3, 5126, false, 28, 0L);
         GL20.glEnableVertexAttribArray(1);
         GL33.glVertexAttribDivisor(1, 1);
         GL20.glVertexAttribPointer(2, 4, 5126, false, 28, 12L);
         GL20.glEnableVertexAttribArray(2);
         GL33.glVertexAttribDivisor(2, 1);
         GL30.glBindVertexArray(0);
         this.initialized = true;
         Vape.debugLog("InstancedBlockRenderer initialized (capacity: " + this.instanceCapacity + " blocks)");
      }
   }

   private void growInstanceCapacity() {
      int newCapacity = this.instanceCapacity * 2;
      FloatBuffer expandedBuffer = BufferUtils.createFloatBuffer(newCapacity * INSTANCE_FLOAT_COUNT);
      ((Buffer)this.instanceDataBuffer).flip();
      expandedBuffer.put(this.instanceDataBuffer);
      this.instanceDataBuffer = expandedBuffer;
      GL15.glBindBuffer(34962, this.instanceBufferId);
      GL15.glBufferData(34962, (long)newCapacity * INSTANCE_FLOAT_COUNT * 4L, 35048);
      this.instanceCapacity = newCapacity;
   }

   private RenderBatchState() {
      this.instanceDataBuffer = BufferUtils.createFloatBuffer(this.instanceCapacity * INSTANCE_FLOAT_COUNT);
      this.instanceCapacity = 4096;
      this.initialized = false;
   }

   public static void cleanupInstance() {
      if (instance != null) {
         instance.cleanup();
         instance = null;
      }
   }

   public static RenderBatchState getInstance() {
      if (instance == null) {
         instance = new RenderBatchState();
      }

      return instance;
   }

   public int getInstanceCount() {
      return this.instanceCount;
   }

   public void beginBatch() {
      if (!this.initialized) {
         this.initialize();
      }

      ((Buffer)this.instanceDataBuffer).clear();
      this.instanceCount = 0;
   }

   private void captureGlBindings() {
      this.previousVertexArrayId = GL11.glGetInteger(34229);
      this.previousProgramId = GL11.glGetInteger(35725);
      this.previousArrayBufferId = GL11.glGetInteger(34964);
      this.previousElementArrayBufferId = GL11.glGetInteger(34965);
      this.previousFramebufferId = GL11.glGetInteger(36006);
   }
}
