package org.lwjgl;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

abstract class J2SESysImplementation extends DefaultSysImplementation {
   @Override
   public long getTime() {
      return System.currentTimeMillis();
   }

   @Override
   public void alert(String title, String message) {
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception var4) {
         LWJGLUtil.log("Caught exception while setting LAF: " + var4);
      }

      JOptionPane.showMessageDialog(null, message, title, 2);
   }

   @Override
   public String getClipboard() {
      try {
         Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
         Transferable transferable = clipboard.getContents(null);
         if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return (String)transferable.getTransferData(DataFlavor.stringFlavor);
         }
      } catch (Exception var3) {
         LWJGLUtil.log("Exception while getting clipboard: " + var3);
      }

      return null;
   }
}
