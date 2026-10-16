package org.lwjgl.opengl;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Container;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.nio.ByteBuffer;
import javax.swing.SwingUtilities;
import org.lwjgl.LWJGLException;

abstract class MacOSXCanvasPeerInfo extends MacOSXPeerInfo {
   private final AWTSurfaceLock awt_surface = new AWTSurfaceLock();
   public ByteBuffer window_handle;

   protected MacOSXCanvasPeerInfo(PixelFormat pixel_format, ContextAttribs attribs, boolean support_pbuffer) throws LWJGLException {
      super(pixel_format, attribs, true, true, support_pbuffer, true);
   }

   protected void initHandle(Canvas component) throws LWJGLException {
      boolean forceCALayer = true;
      boolean autoResizable = true;
      String javaVersion = System.getProperty("java.version");
      if (javaVersion.startsWith("1.5") || javaVersion.startsWith("1.6")) {
         forceCALayer = false;
      } else if (javaVersion.startsWith("1.7")) {
         autoResizable = false;
      }

      Insets insets = getInsets(component);
      int top = insets != null ? insets.top : 0;
      int left = insets != null ? insets.left : 0;
      this.window_handle = nInitHandle(
         this.awt_surface.lockAndGetHandle(component),
         this.getHandle(),
         this.window_handle,
         forceCALayer,
         autoResizable,
         component.getX() - left,
         component.getY() - top
      );
      if (javaVersion.startsWith("1.7")) {
         this.addComponentListener(component);
         reSetLayerBounds(component, this.getHandle());
      }
   }

   private void addComponentListener(final Canvas component) {
      ComponentListener[] components = component.getComponentListeners();

      for (int i = 0; i < components.length; i++) {
         ComponentListener c = components[i];
         if (c.toString() == "CanvasPeerInfoListener") {
            return;
         }
      }

      ComponentListener comp = new ComponentListener() {
         @Override
         public void componentHidden(ComponentEvent e) {
         }

         @Override
         public void componentMoved(ComponentEvent e) {
            MacOSXCanvasPeerInfo.reSetLayerBounds(component, MacOSXCanvasPeerInfo.this.getHandle());
         }

         @Override
         public void componentResized(ComponentEvent e) {
            MacOSXCanvasPeerInfo.reSetLayerBounds(component, MacOSXCanvasPeerInfo.this.getHandle());
         }

         @Override
         public void componentShown(ComponentEvent e) {
         }

         @Override
         public String toString() {
            return "CanvasPeerInfoListener";
         }
      };
      component.addComponentListener(comp);
   }

   private static native ByteBuffer nInitHandle(ByteBuffer var0, ByteBuffer var1, ByteBuffer var2, boolean var3, boolean var4, int var5, int var6) throws LWJGLException;

   private static native void nSetLayerPosition(ByteBuffer var0, int var1, int var2);

   private static native void nSetLayerBounds(ByteBuffer var0, int var1, int var2, int var3, int var4);

   private static void reSetLayerBounds(Canvas component, ByteBuffer peer_info_handle) {
      Component peer = SwingUtilities.getRoot(component);
      Point rtLoc = SwingUtilities.convertPoint(component.getParent(), component.getLocation(), peer);
      int x = (int)rtLoc.getX();
      int y = (int)rtLoc.getY();
      Insets insets = getInsets(component);
      int var10000;
      int var10001;
      if (insets != null) {
         var10000 = x;
         var10001 = insets.left;
      } else {
         var10000 = x;
         var10001 = 0;
      }

      x = var10000 - var10001;
      if (insets != null) {
         var10000 = y;
         var10001 = insets.top;
      } else {
         var10000 = y;
         var10001 = 0;
      }

      y = var10000 - var10001;
      y = peer.getHeight() - y - component.getHeight();
      nSetLayerBounds(peer_info_handle, x, y, component.getWidth(), component.getHeight());
   }

   @Override
   protected void doUnlock() throws LWJGLException {
      this.awt_surface.unlock();
   }

   private static Insets getInsets(Canvas component) {
      Container c = SwingUtilities.getRootPane(component);
      return c != null ? c.getInsets() : new Insets(0, 0, 0, 0);
   }
}
