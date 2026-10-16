package org.lwjgl.opengl;

import java.awt.Canvas;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.Toolkit;
import java.security.AccessController;
import java.security.PrivilegedAction;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;

final class WindowsCanvasImplementation implements AWTCanvasImplementation {
   @Override
   public PeerInfo createPeerInfo(Canvas component, PixelFormat pixel_format, ContextAttribs attribs) throws LWJGLException {
      return new WindowsAWTGLCanvasPeerInfo(component, pixel_format);
   }

   @Override
   public GraphicsConfiguration findConfiguration(GraphicsDevice device, PixelFormat pixel_format) throws LWJGLException {
      return null;
   }

   static {
      Toolkit.getDefaultToolkit();
      AccessController.doPrivileged(new PrivilegedAction<Object>() {
         @Override
         public Object run() {
            try {
               System.loadLibrary("jawt");
            } catch (UnsatisfiedLinkError var2) {
               LWJGLUtil.log("Failed to load jawt: " + var2.getMessage());
            }

            return null;
         }
      });
   }
}
