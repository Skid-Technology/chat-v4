package org.lwjgl.opengl;

import org.lwjgl.LWJGLException;

public final class SharedDrawable extends DrawableGL {
   public SharedDrawable(Drawable drawable) throws LWJGLException {
      this.context = (ContextGL)((DrawableLWJGL)drawable).createSharedContext();
   }

   @Override
   public ContextGL createSharedContext() {
      throw new UnsupportedOperationException();
   }
}
