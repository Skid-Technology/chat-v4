package gg.vape.utils.render;

import java.nio.IntBuffer;
import org.lwjgl.opengl.GL15;

public class IntBufferObject {
   private int bufferId = GL15.glGenBuffers();

   public void upload(IntBuffer intBuffer) {
      GL15.glBufferSubData(34963, 0L, intBuffer);
   }

   public void bind() {
      GL15.glBindBuffer(34963, this.bufferId);
   }

   public void delete() {
      GL15.glDeleteBuffers(this.bufferId);
   }

   public void unbind() {
      GL15.glBindBuffer(34963, 0);
   }
}
