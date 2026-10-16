package gg.vape.utils;

import gg.vape.Vape;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public class ClipboardUtil {
   private static int clipboardBackend = 0;

   public static void setText(String string) {
      try {
         if (clipboardBackend == 0) {
            detectClipboardBackend();
         }

         if (clipboardBackend == 2) {
            try {
               Minecraft.r().y(string);
               return;
            } catch (Throwable var3) {
               clipboardBackend = 1;
            }
         }

         if (clipboardBackend == 1) {
            StringSelection stringSelection = new StringSelection(string);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, stringSelection);
         }
      } catch (Exception var4) {
         Vape.logThrowable(var4);
      }
   }

   public static String getText() {
      try {
         if (clipboardBackend == 0) {
            detectClipboardBackend();
         }

         if (clipboardBackend == 2) {
            try {
               return Minecraft.r().F();
            } catch (Throwable var1) {
               clipboardBackend = 1;
            }
         }

         if (clipboardBackend == 1) {
            Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
               return (String)transferable.getTransferData(DataFlavor.stringFlavor);
            }
         }

         return "";
      } catch (Exception var2) {
         Vape.logThrowable(var2);
         return "";
      }
   }

   private static Exception propagateException(Exception exception) {
      return exception;
   }

   private static void detectClipboardBackend() {
      boolean headless = GraphicsEnvironment.isHeadless();
      int selectedBackend;
      clipboardBackend = selectedBackend = headless ? 2 : 1;
   }
}
