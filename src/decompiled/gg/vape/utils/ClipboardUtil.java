/*
 * Decompiled with CFR 0.152.
 */
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
                ClipboardUtil.detectClipboardBackend();
            }
            if (clipboardBackend == 2) {
                try {
                    Minecraft.r().y(string);
                    return;
                }
                catch (Throwable t) {
                    clipboardBackend = 1;
                }
            }
            if (clipboardBackend == 1) {
                StringSelection stringSelection = new StringSelection(string);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, stringSelection);
            }
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
        }
    }

    public static String getText() {
        try {
            Transferable transferable;
            if (clipboardBackend == 0) {
                ClipboardUtil.detectClipboardBackend();
            }
            if (clipboardBackend == 2) {
                try {
                    return Minecraft.r().F();
                }
                catch (Throwable t) {
                    clipboardBackend = 1;
                }
            }
            if (clipboardBackend == 1 && (transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null)) != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                return (String)transferable.getTransferData(DataFlavor.stringFlavor);
            }
            return "";
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
            return "";
        }
    }

    private static Exception propagateException(Exception exception) {
        return exception;
    }

    private static void detectClipboardBackend() {
        boolean headless = GraphicsEnvironment.isHeadless();
        int selectedBackend = headless ? 2 : 1;
        clipboardBackend = selectedBackend;
    }
}

